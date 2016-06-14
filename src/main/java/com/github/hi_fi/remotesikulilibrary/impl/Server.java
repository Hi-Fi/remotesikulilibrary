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
 * 
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
		boolean withImage = false;
		SikuliLogger.logDebug("Clicking item at Server class");
		if (remote && imageData.length > 0 && imageData[0].toString().length() > 0) {
			SikuliLogger.logDebug("Parsing image from remote call");
			try {
				imageNameOrText = Helper.writeImageByteArrayToDisk(Base64.decode(imageData[0].toString()));
				withImage = true;
			} catch (DecodingException e) {
				SikuliLogger.logDebug(e.getStackTrace());
				throw new RuntimeException(e.getMessage());
			}
		} else if (!remote && new File(Helper.getImageDirectory() + "/" + imageNameOrText).exists()) {
			imageNameOrText = Helper.getImageDirectory() + "/" + imageNameOrText;
			withImage = true;
		}
		try {
			SikuliLogger.logDebug("Clicking item: " + imageNameOrText);
			if (withImage) {
				new Screen().click(new Pattern(imageNameOrText).similar((float) similarity).targetOffset(xOffset, yOffset));
			} else {
				new Screen().click(imageNameOrText);
			}
		} catch (FindFailed e) {
			this.handleFinfFailed(remote, e);
		}
	}

	private byte[] captureScreenshot() {
		String temporaryScreenshotPath = new Screen().capture().getFile();
		SikuliLogger.logDebug("Temporary image stored to: " + temporaryScreenshotPath);
		try {
			return FileUtils.readFileToByteArray(new File(temporaryScreenshotPath));
		} catch (IOException e) {
			SikuliLogger.logDebug(e.getStackTrace());
			throw new RuntimeException(e.getMessage());
		}
	}

	public void waitUntilScreenContains(String imageNameOrText, double similarity, boolean remote,
			Object... imageData) {
		SikuliLogger.logDebug("Clicking item at Server class");
		boolean withImage = false;
		if (remote && imageData.length > 0 && imageData[0].toString().length() > 0) {
			SikuliLogger.logDebug("Parsing image from remote call");
			try {
				imageNameOrText = Helper.writeImageByteArrayToDisk(Base64.decode(imageData[0].toString()));
				withImage = true;
			} catch (DecodingException e) {
				SikuliLogger.logDebug(e.getStackTrace());
				throw new RuntimeException(e.getMessage());
			}
		} else if (!remote && new File(Helper.getImageDirectory() + "/" + imageNameOrText).exists()) {
			imageNameOrText = Helper.getImageDirectory() + "/" + imageNameOrText;
			withImage = true;
		}
		
		try {
			SikuliLogger.logDebug("Waiting for item: " + imageNameOrText);
			Screen screen = new Screen();
			screen.setAutoWaitTimeout(Helper.getWaitTimeout());
			if (withImage) {
				screen.wait(new Pattern(imageNameOrText).similar((float) similarity));
			} else {
				screen.wait(imageNameOrText);
			}
		} catch (FindFailed e) {
			this.handleFinfFailed(remote, e);
		}
	}
	
	private void handleFinfFailed(boolean remote, FindFailed e) {
		if (remote) {
			SikuliLogger.logError("Image/text not found at remote computer. Screenshot below.");
			boolean isDebug = Helper.isDebug();
			if (!isDebug) {
				Helper.enableDebug();
			}
			SikuliLogger.logDebug("-IMAGEDATA-" + Base64.encode(this.captureScreenshot()) + "-IMAGEDATA-");
			if (!isDebug) {
				Helper.disableDebug();
			}
		} else {
			SikuliLogger.logError("Image/text not found at local computer. Screenshot below.");
			SikuliLogger.logImage(Helper.writeImageByteArrayToDisk(this.captureScreenshot()));
		}
		SikuliLogger.logDebug(e.getStackTrace());
		throw new RuntimeException(e.getMessage());
	}
}
