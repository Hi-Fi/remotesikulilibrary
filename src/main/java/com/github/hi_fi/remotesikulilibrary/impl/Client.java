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
	
	public void updateRegionToFocusedApp() {
		this.executeRemoteCall("useFocusedAppAsRegion");
	}
	
	public void resetRegionToFullScreen() {
		this.executeRemoteCall("resetRegionToFullScreen");
	}

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
	
	public void disableDebugging() {
		this.executeRemoteCall("disableDebugging");
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
	
	public void inputTextToField(String text, String imageNameOrText, Locator locator) {
		locator.encodeImageToBase64(imageNameOrText);
		locator.setRemote(true);
		this.executeRemoteCall("inputTextToField", text, imageNameOrText, locator.getSimilarity(), locator.getxOffset(), locator.getyOffset(), locator.isRemote(), locator.getImageData());
	}
	
	public void pasteText(String text) {
		this.executeRemoteCall("pasteText", text);
	}

	public void typeKeys(String keys, Object...modifiers) {
		this.executeRemoteCall("typeKeys", prepend(modifiers, keys));
	}
	
	public int startApp(String appCommand) {
		return Integer.parseInt(this.executeRemoteCall("openApp", appCommand));
		
	}

	public void closeApp(String appCommand) {
		this.executeRemoteCall("closeApp", appCommand);
		
	}

	public void switchApp(String appCommand) {
		this.executeRemoteCall("switchApp", appCommand);
		
	}
	
	public String getClipboardContent() {
		return this.executeRemoteCall("getClipboardContent");
	}
	
	public void setWaitTime(double waitTime) {
		this.executeRemoteCall("setWaitTime", waitTime);
	}
	
	public double getWaitTime() {
		return Double.parseDouble(this.executeRemoteCall("getWaitTime"));
	}
	
	public void setOCRStatus(boolean ocrAvailable) {
		if (ocrAvailable) {
			this.executeRemoteCall("enableOCR");
		} else {
			this.executeRemoteCall("disableOCR");
		}
	}
	
	@SuppressWarnings("rawtypes")
	private String executeRemoteCall(String keyword, Object... params) {
		Map response = new HashMap();
		SikuliLogger.logDebug("Calling remotely keyword: " + keyword + " with " + params.length + " parameters.");
		SikuliLogger.logDebug("Logging all parameter");
		for (Object param: params) {
			SikuliLogger.logDebug(param);
		}
		
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
	
	static <T> T[] append(T[] arr, T lastElement) {
	    final int N = arr.length;
	    arr = java.util.Arrays.copyOf(arr, N+1);
	    arr[N] = lastElement;
	    return arr;
	}
	static <T> T[] prepend(T[] arr, T firstElement) {
	    final int N = arr.length;
	    arr = java.util.Arrays.copyOf(arr, N+1);
	    System.arraycopy(arr, 0, arr, 1, N);
	    arr[0] = firstElement;
	    return arr;
	}
}
