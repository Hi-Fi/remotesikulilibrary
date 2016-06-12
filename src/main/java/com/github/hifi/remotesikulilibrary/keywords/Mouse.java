package com.github.hifi.remotesikulilibrary.keywords;

import org.apache.commons.lang.math.NumberUtils;
import org.robotframework.javalib.annotation.ArgumentNames;
import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;

import com.github.hifi.remotesikulilibrary.utils.Helper;
import com.github.hifi.remotesikulilibrary.utils.SikuliLogger;

@RobotKeywords
public class Mouse {

	@RobotKeyword("Click either given image or text at the screen.")
	@ArgumentNames({"Image name or text to click", "Similarity of images=0.7", "X offset from centre of image=0", "Y offset from centre of image=0","Is call remote=false", "Base64 encoded image to click at remote case="})
	public void clickItem(String imageNameOrText, Object...arguments) {
		SikuliLogger.logDebug("Parsing parameter from arguments. There's "+arguments.length+" parameters to parse");
		double similarity = 0.7;
		int xOffset = 0;
		int yOffset = 0;
		boolean remote = false;
		String imageData = "";
		if (arguments.length > 0 && NumberUtils.isNumber(arguments[0].toString())) {
			similarity = Double.parseDouble(arguments[0].toString());
			if (arguments.length == 3) {
				if (NumberUtils.isNumber(arguments[1].toString())) {
					xOffset = Integer.parseInt(arguments[1].toString());
				}
				if (NumberUtils.isNumber(arguments[2].toString())) {
					yOffset = Integer.parseInt(arguments[2].toString());
				}
			}
		}
		
		if (arguments.length > 0 && !NumberUtils.isNumber(arguments[arguments.length-1].toString())) {
			remote = true;
			imageData = arguments[arguments.length-1].toString();
		}
		
		Helper.getLibrary().clickItem(imageNameOrText, similarity, xOffset, yOffset, remote, imageData);
	}
}
