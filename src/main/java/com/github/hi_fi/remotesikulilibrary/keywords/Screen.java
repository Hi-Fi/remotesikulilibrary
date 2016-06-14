package com.github.hi_fi.remotesikulilibrary.keywords;

import org.robotframework.javalib.annotation.ArgumentNames;
import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;

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
	public void waitUntilScreenContains(String imageNameOrText, Object...args) {
		double similarity = 0.7;
		boolean remote = false;
		String imageData = "";
		if (args.length > 0) {
			similarity = Double.parseDouble(args[0].toString());
			if (args.length > 1) {
				remote = Boolean.parseBoolean(args[1].toString());
				if (args.length > 2) {
					imageData = args[2].toString();
				}
			}
		}
		Helper.getLibrary().waitUntilScreenContains(imageNameOrText, similarity, remote, imageData);
	}

}
