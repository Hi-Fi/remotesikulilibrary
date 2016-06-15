package com.github.hi_fi.remotesikulilibrary.keywords;

import org.robotframework.javalib.annotation.ArgumentNames;
import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;

import com.github.hi_fi.remotesikulilibrary.DTO.Locator;
import com.github.hi_fi.remotesikulilibrary.utils.Helper;
import com.github.hi_fi.remotesikulilibrary.utils.SikuliLogger;

@RobotKeywords
public class Keyboard {
	
	@RobotKeyword("Inputs given text to given field. Input is pasted to field, which allows support for international characters\n\n"
				 +"Locator is same than in item click/wait, so same variables available")
	@ArgumentNames({"Text to type", "Image name or text to click", "Similarity of images=0.7", "X offset from centre of image=0", "Y offset from centre of image=0","Is call remote=false", "Base64 encoded image to click at remote case="})
	public void inputText(String text, String imageNameOrText, Object...arguments) {
		SikuliLogger.logDebug("Parsing parameter from arguments. There's "+arguments.length+" parameters to parse");
		Locator locator = new Locator(arguments);
		Helper.getLibrary().inputText(text, imageNameOrText, locator);
	}

}
