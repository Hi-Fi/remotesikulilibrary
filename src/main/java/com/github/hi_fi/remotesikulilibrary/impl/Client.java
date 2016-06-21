package com.github.hi_fi.remotesikulilibrary.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.ws.commons.util.Base64;
import org.apache.ws.commons.util.Base64.DecodingException;
import org.apache.xmlrpc.XmlRpcException;

import com.github.hi_fi.remotesikulilibrary.DTO.Locator;
import com.github.hi_fi.remotesikulilibrary.utils.Helper;
import com.github.hi_fi.remotesikulilibrary.utils.SikuliLogger;

/**
 * Handles cases when using SikuliLibrary through Robot's RemoteLibrary
 * 
 * @author hifi
 *
 */
public class Client implements RemoteSikuliLibraryInterface {

	public String captureScreenshot(String[] remote) {
		try {
			return Helper.writeImageByteArrayToDisk(Base64.decode(this.executeRemoteCall("captureScreenshot", true)));
		} catch (DecodingException e) {
			SikuliLogger.logError("Decoding of remote image failed");
			SikuliLogger.logDebug(e.getStackTrace());
			throw new RuntimeException(e.getMessage());
		}
	}

	public void enableDebugging() {
		this.executeRemoteCall("enableDebugging");
	}

	public void clickItem(String imageNameOrText, Locator locator) {
		locator.encodeImageToBase64(imageNameOrText);
		locator.setRemote(true);
		
		this.executeRemoteCall("clickItem", imageNameOrText, locator.getSimilarity(), locator.getxOffset(), locator.getyOffset(), locator.isRemote(), locator.getImageData());
	}
	
	public void doubleClickItem(String imageNameOrText, Locator locator) {
		locator.encodeImageToBase64(imageNameOrText);
		locator.setRemote(true);
		
		this.executeRemoteCall("doubleClickItem", imageNameOrText, locator.getSimilarity(), locator.getxOffset(), locator.getyOffset(), locator.isRemote(), locator.getImageData());
	}
	
	public void rightClickItem(String imageNameOrText, Locator locator) {
		locator.encodeImageToBase64(imageNameOrText);
		locator.setRemote(true);
		
		this.executeRemoteCall("rightClickItem", imageNameOrText, locator.getSimilarity(), locator.getxOffset(), locator.getyOffset(), locator.isRemote(), locator.getImageData());
	}
	
	public void waitUntilScreenContains(String imageNameOrText, Locator locator) {
		locator.encodeImageToBase64(imageNameOrText);
		locator.setRemote(true);
		this.executeRemoteCall("waitUntilScreenContains", imageNameOrText, locator.getSimilarity(), locator.isRemote(), locator.getImageData());
	}
	
	public void waitUntilScreenDoesNotContain(String imageNameOrText, Locator locator) {
		locator.encodeImageToBase64(imageNameOrText);
		locator.setRemote(true);
		this.executeRemoteCall("waitUntilScreenDoesNotContain", imageNameOrText, locator.getSimilarity(), locator.isRemote(), locator.getImageData());
	}
	
	public void inputText(String text, String imageNameOrText, Locator locator) {
		locator.encodeImageToBase64(imageNameOrText);
		locator.setRemote(true);
		this.executeRemoteCall("inputText", text, imageNameOrText, locator.getSimilarity(), locator.getxOffset(), locator.getyOffset(), locator.isRemote(), locator.getImageData());
	}

	public void typeKeys(String keys, String...modifiers) {
		this.executeRemoteCall("typeKeys", keys, modifiers);
	}
	
	public int startApp(String appCommand) {
		return Integer.parseInt(this.executeRemoteCall("startApp", appCommand));
		
	}

	public void closeApp(String appCommand) {
		this.executeRemoteCall("closeApp", appCommand);
		
	}

	public void switchApp(String appCommand) {
		this.executeRemoteCall("switchApp", appCommand);
		
	}
	
	@SuppressWarnings("rawtypes")
	private String executeRemoteCall(String keyword, Object... params) {
		Map response = new HashMap();
		SikuliLogger.logDebug("Calling remotely keyword: " + keyword + " with " + params.length + " parameters");
		try {
			response = (Map) Helper.getRemoteClient().execute("run_keyword", new Object[] { keyword, params });
			SikuliLogger.logDebug("Response from remote call: " + response);
			if (response.get("status") == null || response.get("status").equals("FAIL")) {
				SikuliLogger.logError("Remote call to " + keyword + " failed.");
				if (response.get("output") != null) {
					SikuliLogger.logDebug("Output: " + response.get("output"));
					if (response.get("output").toString().contains("-IMAGEDATA-")) {
						String base64 = response.get("output").toString().split("-IMAGEDATA-")[1];
						SikuliLogger.logDebug("Parser data from output: " + base64);
						try {
							Helper.writeImageByteArrayToDisk(Base64.decode(base64));
						} catch (DecodingException e) {
							SikuliLogger.logDebug("Decoding of image failed");
						}
					}
				}
				SikuliLogger.logDebug("Traceback: " + response.get("traceback"));
				throw new RuntimeException(response.get("error").toString());
			} else {
				SikuliLogger.logDebug("Output: " + response.get("output"));
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
