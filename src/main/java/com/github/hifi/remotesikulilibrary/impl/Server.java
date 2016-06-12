package com.github.hifi.remotesikulilibrary.impl;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.ws.commons.util.Base64;
import org.sikuli.script.Screen;

import com.github.hifi.remotesikulilibrary.keywords.Configuration;
import com.github.hifi.remotesikulilibrary.utils.Helper;
import com.github.hifi.remotesikulilibrary.utils.SikuliLogger;

/**
 * Handles both local and server side requests.
 * @author hifi
 *
 */
public class Server implements RemoteSikuliLibraryInterface {

	public String captureScreenshot(Object... remote) {
		SikuliLogger.logDebug("Calling screenshot capture from server class");
		Screen s = new Screen();
		String temporaryScreenshotPath = s.capture().getFile();
		byte[] imageData;
		try {
			imageData = FileUtils.readFileToByteArray(new File(temporaryScreenshotPath));
		} catch (IOException e) {
			SikuliLogger.logDebug(e.getStackTrace());
			throw new RuntimeException(e.getMessage());
		}
		if (remote.length > 0) {
			SikuliLogger.logDebug("Returning base64 data of image from server location: "+temporaryScreenshotPath);
			return Base64.encode(imageData);
		} else {
			return Helper.writeImageByteArrayToDisk(imageData);
		}
		
	}

	public void clickImage(Object... remote) {
		// TODO Auto-generated method stub
		
	}

	public void enableDebugging() {
		Helper.enableDebug();
	}

}
