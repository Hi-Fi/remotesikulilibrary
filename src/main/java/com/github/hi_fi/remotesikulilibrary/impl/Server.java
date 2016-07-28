package com.github.hi_fi.remotesikulilibrary.impl;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.ws.commons.util.Base64;
import org.sikuli.script.App;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Key;
import org.sikuli.script.Location;
import org.sikuli.script.Pattern;

import com.github.hi_fi.remotesikulilibrary.DTO.Locator;
import com.github.hi_fi.remotesikulilibrary.OCR.TextRecognizer;
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
	
	public void updateRegionToFocusedApp() {
		Helper.setRegion(App.focusedWindow());
		SikuliLogger.log(Helper.getRegion());
	}
	
	public void resetRegionToFullScreen() {
		Helper.resetRegion();
	}
	
	public String captureRegion(String[] remote) {
		SikuliLogger.logDebug("Calling region capture from server class");
		byte[] imageData = this.captureRegion();
		if (remote.length > 0) {
			SikuliLogger.logDebug("Returning base64 data of image");
			return Base64.encode(imageData);
		} else {
			return Helper.writeImageByteArrayToDisk(imageData);
		}
	}
	
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
				Helper.getRegion().click(new Pattern(imageNameOrText).similar(locator.getSimilarityasFloat())
						.targetOffset(locator.getxOffset(), locator.getyOffset()));
			} else if (locator.isText()) {
				Location location = new TextRecognizer().findText(imageNameOrText);
				Helper.getRegion().click(new Location(location.x + (double)locator.getxOffset(), 
										              location.y + (double)locator.getyOffset()));
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
				Helper.getRegion().doubleClick(new Pattern(imageNameOrText).similar(locator.getSimilarityasFloat())
						.targetOffset(locator.getxOffset(), locator.getyOffset()));
			} else if (locator.isText()) {
				Location location = new TextRecognizer().findText(imageNameOrText);
				Helper.getRegion().doubleClick(new Location(location.x + (double)locator.getxOffset(), 
													        location.y + (double)locator.getyOffset()));
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
				Helper.getRegion().rightClick(new Pattern(imageNameOrText).similar(locator.getSimilarityasFloat())
						.targetOffset(locator.getxOffset(), locator.getyOffset()));
			} else if (locator.isText()) {
				Location location = new TextRecognizer().findText(imageNameOrText);
				Helper.getRegion().rightClick(new Location(location.x + (double)locator.getxOffset(), 
													       location.y + (double)locator.getyOffset()));
			}
		} catch (FindFailed e) {
			this.handleFindFailed(locator.isRemote(), e);
		}
	}

	public void waitUntilScreenContains(String imageNameOrText, Locator locator) {
		imageNameOrText = locator.updateLocatorTarget(imageNameOrText);
		try {
			SikuliLogger.logDebug("Waiting for item: " + imageNameOrText);
			Helper.getRegion().setAutoWaitTimeout(Helper.getWaitTimeout());
			if (locator.isImage()) {
				Helper.getRegion().wait(new Pattern(imageNameOrText).similar(locator.getSimilarityasFloat()));
			} else if (locator.isText()) {
				new TextRecognizer().waitUntilTextIsVisible(imageNameOrText);
			}
		} catch (FindFailed e) {
			this.handleFindFailed(locator.isRemote(), e);
		}
	}
	
	public void waitUntilScreenDoesNotContain(String imageNameOrText, Locator locator) {
		imageNameOrText = locator.updateLocatorTarget(imageNameOrText);
		boolean vanished = true;
		Helper.getRegion().setAutoWaitTimeout(Helper.getWaitTimeout());
		if (locator.isImage()) {
			vanished = Helper.getRegion().waitVanish(new Pattern(imageNameOrText).similar(locator.getSimilarityasFloat()));
		} else if (locator.isText()) {
			vanished = new TextRecognizer().waitUntilTextIsNotVisible(imageNameOrText);
		}
		
		if (!vanished) {
			this.captureScreenshotInError(locator.isRemote());
			throw new RuntimeException("Given item didn't vanished in expected time");
		}
	}

	public void inputText(String text, String imageNameOrText, Locator locator) {
		imageNameOrText = locator.updateLocatorTarget(imageNameOrText);
		if (imageNameOrText != null) {
			try {
				SikuliLogger.logDebug("Clicking item: " + imageNameOrText);
				if (locator.isImage()) {
					Helper.getRegion().click(new Pattern(imageNameOrText).similar(locator.getSimilarityasFloat())
							.targetOffset(locator.getxOffset(), locator.getyOffset()));
				} else if (locator.isText()) {
					Location location = new TextRecognizer().findText(imageNameOrText);
					Helper.getRegion().click(new Location(location.x + (double)locator.getxOffset(), 
														  location.y + (double)locator.getyOffset()));
				}
			} catch (FindFailed e) {
				this.handleFindFailed(locator.isRemote(), e);
			}
		}

		Helper.getRegion().paste(text);
	}

	public void typeKeys(String keys, String... modifiers) {
		boolean numLockActive = Key.isLockOn(Key.C_NUM_LOCK);
		if (numLockActive) {
			Helper.getRegion().type(Key.NUM_LOCK);
		}
		String modifierText = "";
		for (Object modifier : modifiers) {
			modifierText = KeyMapper.getKey(modifier.toString());
		}
		keys = KeyMapper.getKey(keys);
		Helper.getRegion().type(keys, modifierText);
		if (numLockActive) {
			Helper.getRegion().type(Key.NUM_LOCK);
		}
	}
	
	public int startApp(String appCommand) {
		return App.open(appCommand).getPID();
	}

	public void closeApp(String appCommand) {
		if (isInteger(appCommand)) {
			try {
				new App(Integer.parseInt(appCommand)).close();
			} catch (NullPointerException e) {
				SikuliLogger.log("Application not found with given PID. Maybe it's closed earlier?");
				SikuliLogger.logDebug(e.getStackTrace());
			}
		} else {
			App.close(appCommand);
		}
	}

	public void switchApp(String appCommand) {
		if (isInteger(appCommand)) {
			new App(Integer.parseInt(appCommand)).focus();
		} else {
			App.focus(appCommand);
		}
	}
	
	private byte[] captureRegion() {
		String temporaryScreenshotPath = Helper.getRegion().saveScreenCapture();
		SikuliLogger.logDebug("Temporary image stored to: " + temporaryScreenshotPath);
		try {
			return FileUtils.readFileToByteArray(new File(temporaryScreenshotPath));
		} catch (IOException e) {
			SikuliLogger.logDebug(e.getStackTrace());
			throw new RuntimeException(e.getMessage());
		}
	}

	private byte[] captureScreenshot() {
		String temporaryScreenshotPath = Helper.getRegion().getScreen().capture().getFile();
		SikuliLogger.logDebug("Temporary image stored to: " + temporaryScreenshotPath);
		try {
			return FileUtils.readFileToByteArray(new File(temporaryScreenshotPath));
		} catch (IOException e) {
			SikuliLogger.logDebug(e.getStackTrace());
			throw new RuntimeException(e.getMessage());
		}
	}

	private void handleFindFailed(boolean remote, FindFailed e) {
		this.captureScreenshotInError(remote);
		SikuliLogger.logDebug(e.getStackTrace());
		throw new RuntimeException(e.getMessage());
	}
	
	private void captureScreenshotInError(boolean remote) {
		if (remote) {
			SikuliLogger.logError("Image/text not found at remote computer. Screenshot below.");
			boolean isDebug = Helper.isDebug();
			if (!isDebug) {
				Helper.enableDebug();
			}
			SikuliLogger.logDebug("-IMAGEDATA-" +
			Base64.encode(this.captureScreenshot()) + "-IMAGEDATA-");
			if (!isDebug) {
				Helper.disableDebug();
			}
		} else {
			SikuliLogger.logError("Image/text not found at local computer. Screenshot below.");
			SikuliLogger.logImage(Helper.writeImageByteArrayToDisk(this.captureScreenshot()));
		}
	}
	
	private boolean isInteger(String value) {
		boolean isInteger = false;
		try {
			Integer.parseInt(value);
			isInteger = true;
		} catch (NumberFormatException e) {
			
		}
		
		return isInteger;
	}
}
