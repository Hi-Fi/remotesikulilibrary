package com.github.hi_fi.remotesikulilibrary.impl;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.ws.commons.util.Base64;
import org.sikuli.script.Env;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Key;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;

import com.github.hi_fi.remotesikulilibrary.DTO.Locator;
import com.github.hi_fi.remotesikulilibrary.utils.Helper;
import com.github.hi_fi.remotesikulilibrary.utils.KeyMapper;
import com.github.hi_fi.remotesikulilibrary.utils.SikuliLogger;

/**
 * Handles both local and server side requests.
 * 
 * @author hifi
 *
 */
public class Server implements RemoteSikuliLibraryInterface {

	public String captureScreenshot(String[] remote) {
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

	public void clickItem(String imageNameOrText, Locator locator) {
		SikuliLogger.logDebug("Clicking item at Server class");
		imageNameOrText = locator.updateLocatorTarget(imageNameOrText);
		try {
			SikuliLogger.logDebug("Clicking item: " + imageNameOrText);
			if (locator.isImage()) {
				new Screen().click(new Pattern(imageNameOrText).similar(locator.getSimilarityasFloat())
						.targetOffset(locator.getxOffset(), locator.getyOffset()));
			} else if (locator.isText()) {
				new Screen().click(imageNameOrText);
			}
		} catch (FindFailed e) {
			this.handleFindFailed(locator.isRemote(), e);
		}
	}
	
	public void doubleClickItem(String imageNameOrText, Locator locator) {
		SikuliLogger.logDebug("Clicking item at Server class");
		imageNameOrText = locator.updateLocatorTarget(imageNameOrText);
		try {
			SikuliLogger.logDebug("Clicking item: " + imageNameOrText);
			if (locator.isImage()) {
				new Screen().doubleClick(new Pattern(imageNameOrText).similar(locator.getSimilarityasFloat())
						.targetOffset(locator.getxOffset(), locator.getyOffset()));
			} else if (locator.isText()) {
				new Screen().doubleClick(imageNameOrText);
			}
		} catch (FindFailed e) {
			this.handleFindFailed(locator.isRemote(), e);
		}
	}
	
	public void rightClickItem(String imageNameOrText, Locator locator) {
		SikuliLogger.logDebug("Clicking item at Server class");
		imageNameOrText = locator.updateLocatorTarget(imageNameOrText);
		try {
			SikuliLogger.logDebug("Clicking item: " + imageNameOrText);
			if (locator.isImage()) {
				new Screen().rightClick(new Pattern(imageNameOrText).similar(locator.getSimilarityasFloat())
						.targetOffset(locator.getxOffset(), locator.getyOffset()));
			} else if (locator.isText()) {
				new Screen().rightClick(imageNameOrText);
			}
		} catch (FindFailed e) {
			this.handleFindFailed(locator.isRemote(), e);
		}
	}

	public void waitUntilScreenContains(String imageNameOrText, Locator locator) {
		SikuliLogger.logDebug("Waiting for item at Server class");
		imageNameOrText = locator.updateLocatorTarget(imageNameOrText);
		try {
			SikuliLogger.logDebug("Waiting for item: " + imageNameOrText);
			Screen screen = new Screen();
			screen.setAutoWaitTimeout(Helper.getWaitTimeout());
			if (locator.isImage()) {
				screen.wait(new Pattern(imageNameOrText).similar(locator.getSimilarityasFloat()));
			} else if (locator.isText()) {
				screen.wait(imageNameOrText);
			}
		} catch (FindFailed e) {
			this.handleFindFailed(locator.isRemote(), e);
		}
	}

	public void inputText(String text, String imageNameOrText, Locator locator) {
		imageNameOrText = locator.updateLocatorTarget(imageNameOrText);
		try {
			SikuliLogger.logDebug("Clicking item: " + imageNameOrText);
			if (locator.isImage()) {
				new Screen().click(new Pattern(imageNameOrText).similar(locator.getSimilarityasFloat())
						.targetOffset(locator.getxOffset(), locator.getyOffset()));
			} else if (locator.isText()) {
				new Screen().click(imageNameOrText);
			}
		} catch (FindFailed e) {
			this.handleFindFailed(locator.isRemote(), e);
		}

		new Screen().paste(text);
	}

	public void typeKeys(String keys, String[] modifiers) {
		boolean numLockActive = Key.isLockOn(Key.C_NUM_LOCK);
		Screen screen = new Screen();
		if (numLockActive) {
			screen.type(Key.NUM_LOCK);
		}
		String modifierText = "";
		for (Object modifier : modifiers) {
			modifierText = KeyMapper.getKey(modifier.toString());
		}
		keys = KeyMapper.getKey(keys);
		new Screen().type(keys, modifierText);
		if (numLockActive) {
			screen.type(Key.NUM_LOCK);
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

	private void handleFindFailed(boolean remote, FindFailed e) {
		if (remote) {
			SikuliLogger.logError("Image/text not found at remote computer. Screenshot below.");
			boolean isDebug = Helper.isDebug();
			if (!isDebug) {
				Helper.enableDebug();
			}
			// SikuliLogger.logDebug("-IMAGEDATA-" +
			// Base64.encode(this.captureScreenshot()) + "-IMAGEDATA-");
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
