package com.github.hi_fi.remotesikulilibrary.impl;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.ws.commons.util.Base64;
import org.apache.ws.commons.util.Base64.DecodingException;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;

import com.github.hi_fi.remotesikulilibrary.utils.Helper;
import com.github.hi_fi.remotesikulilibrary.utils.SikuliLogger;

/**
 * Handles both local and server side requests.
 * @author hifi
 *
 */
public class Server implements RemoteSikuliLibraryInterface {

	public String captureScreenshot(Object... remote) {
		SikuliLogger.logDebug("Calling screenshot capture from server class");
		byte[] imageData = this.captureScreenshot();
		if (remote.length > 0) {
			SikuliLogger.logDebug("Returning base64 data of image");
			return Base64.encode(imageData);
		} else {
			return Helper.writeImageByteArrayToDisk(imageData);
		}
	}

	public void enableDebugging() {
		Helper.enableDebug();
	}

	public void clickItem(String imageNameOrText, double similarity, int xOffset, int yOffset, boolean remote,
			Object... imageData) {
		SikuliLogger.logDebug("Clicking item at Server class");
		if (remote && imageData.length > 0 && imageData[0].toString().length() > 0) {
			SikuliLogger.logDebug("Parsing image from remote call");
			try {
				imageNameOrText = Helper.writeImageByteArrayToDisk(Base64.decode(imageData[0].toString()));
			} catch (DecodingException e) {
				SikuliLogger.logDebug(e.getStackTrace());
				throw new RuntimeException(e.getMessage());
			}
		} else {
			imageNameOrText = Helper.getImageDirectory()+"/"+imageNameOrText;
		}
		try {
			SikuliLogger.logDebug("Clicking item: "+imageNameOrText);
			new Screen().click(new Pattern(imageNameOrText).similar((float) similarity).targetOffset(xOffset, yOffset));
		} catch (FindFailed e) {
			if (remote) {
				SikuliLogger.logError("Image not found at remote computer. Screenshot below.");
				boolean isDebug = Helper.isDebug();
				if (!isDebug) {Helper.enableDebug();}
				SikuliLogger.logDebug("-IMAGEDATA-"+Base64.encode(this.captureScreenshot())+"-IMAGEDATA-");
				if (!isDebug) {Helper.disableDebug();}
			} else {
				SikuliLogger.logError("Image not found at local computer. Screenshot below.");
				SikuliLogger.logImage(Helper.writeImageByteArrayToDisk(this.captureScreenshot()));
			}
			SikuliLogger.logDebug(e.getStackTrace());
			throw new RuntimeException(e.getMessage());
		}
	}
	
	private byte[] captureScreenshot() {
		String temporaryScreenshotPath = new Screen().capture().getFile();
		SikuliLogger.logDebug("Temporary image stored to: "+temporaryScreenshotPath);
		try {
			return FileUtils.readFileToByteArray(new File(temporaryScreenshotPath));
		} catch (IOException e) {
			SikuliLogger.logDebug(e.getStackTrace());
			throw new RuntimeException(e.getMessage());
		}
	}
}
