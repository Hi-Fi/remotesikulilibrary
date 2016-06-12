package com.github.hifi.remotesikulilibrary.impl;

import java.util.HashMap;
import java.util.Map;

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

	public void clickImage(Object... remote) {
		// TODO Auto-generated method stub

	}

	@SuppressWarnings("rawtypes")
	public String executeRemoteCall(String keyword, Object... params) {
		Map response = new HashMap();
		SikuliLogger.logDebug("Calling remotely keyword: " + keyword + " with " + params.length + " parameters");
		try {
			response = (Map) Helper.getRemoteClient().execute("run_keyword", new Object[] { keyword, params });
			SikuliLogger.logDebug("Response from remote call: " + response);
			if (response.get("status") == null || response.get("status").equals("FAIL")) {
				SikuliLogger.logDebug(response.get("traceback"));
				throw new RuntimeException(response.get("error").toString());
			} else {
				SikuliLogger.logDebug(response.get("output"));
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

	public void enableDebugging() {
		this.executeRemoteCall("enableDebugging");
	}
}
