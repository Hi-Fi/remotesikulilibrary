package com.github.hi_fi.remotesikulilibrary.keywords;

import org.robotframework.javalib.annotation.ArgumentNames;
import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;

import com.github.hi_fi.remotesikulilibrary.DTO.Locator;
import com.github.hi_fi.remotesikulilibrary.utils.Helper;
import com.github.hi_fi.remotesikulilibrary.utils.SikuliLogger;

@RobotKeywords
public class Mouse {

	@RobotKeyword("Click either given image or text at the screen.")
	@ArgumentNames({"Image name or text to click", "Similarity of images=0.7", "X offset from centre of image=0", "Y offset from centre of image=0","*Technical arguments"})
	public void clickItem(String imageNameOrText, String[] arguments) {
		SikuliLogger.logDebug("Parsing parameter from arguments. There's "+arguments.length+" parameters to parse");
		Locator locator = new Locator(arguments);
		Helper.getLibrary().clickItem(imageNameOrText, locator);
	}
}
