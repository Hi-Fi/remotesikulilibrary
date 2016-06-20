package com.github.hi_fi.remotesikulilibrary.keywords;

import org.robotframework.javalib.annotation.ArgumentNames;
import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;

import com.github.hi_fi.remotesikulilibrary.DTO.Locator;
import com.github.hi_fi.remotesikulilibrary.utils.Helper;
import com.github.hi_fi.remotesikulilibrary.utils.SikuliLogger;

@RobotKeywords
public class Screen {
	
	@RobotKeyword("Captures visible screen using Sikuli")
	@ArgumentNames({"*Techical arguments"})
	public String captureScreenshot(String[] arguments) {
		return Helper.getLibrary().captureScreenshot(arguments);
	}
	
	@RobotKeyword("Wait until screen contains given item (image or text (of OCR is enabled))")
	@ArgumentNames({"Image or text to wait", "Similarity of images=0.7", "*Technical arguments"})
	public void waitUntilScreenContains(String imageNameOrText, String[] arguments) {
		Locator locator = new Locator(arguments);
		Helper.getLibrary().waitUntilScreenContains(imageNameOrText, locator);
	}
	
	@RobotKeyword("Wait until screen doesn't contains given item (image or text (of OCR is enabled))")
	@ArgumentNames({"Image or text to wait", "Similarity of images=0.7", "*Technical arguments"})
	public void waitUntilScreenDoesNotContains(String imageNameOrText, String[] arguments) {
		Locator locator = new Locator(arguments);
		Helper.getLibrary().waitUntilScreenDoesNotContain(imageNameOrText, locator);
	}
}
