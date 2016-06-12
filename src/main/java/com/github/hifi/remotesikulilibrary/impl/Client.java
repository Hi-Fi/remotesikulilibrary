package com.github.hifi.remotesikulilibrary.impl;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.ws.commons.util.Base64;
import org.apache.ws.commons.util.Base64.DecodingException;
import org.apache.xmlrpc.XmlRpcException;

import com.github.hifi.remotesikulilibrary.utils.Helper;
import com.github.hifi.remotesikulilibrary.utils.SikuliLogger;

/**
 * Handles cases when using SikuliLibrary through Robot's RemoteLibrary
 * 
 * @author hifi
 *
 */
public class Client implements RemoteSikuliLibraryInterface {

	public String captureScreenshot(Object... remote) {
		SikuliLogger.logDebug("Calling screenshot capture from client class");
		String response = this.executeRemoteCall("captureScreenshot", true);
		try {
			return Helper.writeImageByteArrayToDisk(Base64.decode(response));
		} catch (DecodingException e) {
			SikuliLogger.logDebug("Decoding of remote image failed");
			SikuliLogger.logDebug(e.getStackTrace());
			throw new RuntimeException(e.getMessage());
		}
	}

	public void enableDebugging() {
		this.executeRemoteCall("enableDebugging");
	}

	public void clickItem(String imageNameOrText, double similarity, int xOffset, int yOffset, boolean remote,
			Object... imageData) {
		Object image = "";
		try {
			SikuliLogger.logDebug("Checking if "+Helper.getImageDirectory()+"/"+imageNameOrText+" is image");
			File localImage = new File(Helper.getImageDirectory()+"/"+imageNameOrText);
			if (localImage.exists()) {
				image = Base64.encode(FileUtils.readFileToByteArray(localImage));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.executeRemoteCall("clickItem", imageNameOrText, similarity, xOffset, yOffset, true, image);
	}
	
	@SuppressWarnings("rawtypes")
	private String executeRemoteCall(String keyword, Object... params) {
		Map response = new HashMap();
		SikuliLogger.logDebug("Calling remotely keyword: " + keyword + " with " + params.length + " parameters");
		try {
			response = (Map) Helper.getRemoteClient().execute("run_keyword", new Object[] { keyword, params });
			SikuliLogger.logDebug("Response from remote call: " + response);
			if (response.get("status") == null || response.get("status").equals("FAIL")) {
				SikuliLogger.logError("Remote call to "+keyword+" failed.");
				if (response.get("output") != null) {
					SikuliLogger.logDebug("Output: "+response.get("output"));
					if (response.get("output").toString().contains("-IMAGEDATA-")) {
						String base64 = response.get("output").toString().split("-IMAGEDATA-")[1];
						SikuliLogger.logDebug("Parser data from output: "+base64);
						try {
							Helper.writeImageByteArrayToDisk(Base64.decode(base64));
						} catch (DecodingException e) {
							SikuliLogger.logDebug("Decoding of image failed");
						}
					}
				}
				SikuliLogger.logDebug("Traceback: "+response.get("traceback"));
				throw new RuntimeException(response.get("error").toString());
			} else {
				SikuliLogger.logDebug("Output: "+response.get("output"));
			}
		} catch (XmlRpcException e) {
			SikuliLogger.logDebug(e.getStackTrace());
			throw new RuntimeException(e.getMessage());
		}
		if (response.get("return") != null) {
			return response.get("return").toString();
		} else {
			return "No return value";
		}
	}
}
