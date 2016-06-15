package com.github.hi_fi.remotesikulilibrary.keywords;

import org.robotframework.javalib.annotation.ArgumentNames;
import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;

import com.github.hi_fi.remotesikulilibrary.DTO.Locator;
import com.github.hi_fi.remotesikulilibrary.utils.Helper;

@RobotKeywords
public class Screen {
	
	@RobotKeyword("Captures visible screen using Sikuli")
	@ArgumentNames({"Is call remote=false"})
	public String captureScreenshot(Object...remote) {
		return Helper.getLibrary().captureScreenshot(remote);
	}
	
	@RobotKeyword("Wait until screen contains given item (image or text (of OCR is enabled))")
	@ArgumentNames({"Image or text to wait, similarity=0.7, Is call remote=false"})
	public void waitUntilScreenContains(String imageNameOrText, Object...arguments) {
		Locator locator = new Locator(arguments);
		Helper.getLibrary().waitUntilScreenContains(imageNameOrText, locator);
	}

}
