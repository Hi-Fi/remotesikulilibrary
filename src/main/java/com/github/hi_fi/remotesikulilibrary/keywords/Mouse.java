package com.github.hi_fi.remotesikulilibrary.keywords;

import org.robotframework.javalib.annotation.ArgumentNames;
import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;

import com.github.hi_fi.remotesikulilibrary.DTO.Locator;
import com.github.hi_fi.remotesikulilibrary.utils.Helper;

@RobotKeywords
public class Mouse {

	@RobotKeyword("Clicks either given image or text at the screen.")
	@ArgumentNames({"Image name or text to click", "Similarity of images=0.7", "X offset from centre of image=0", "Y offset from centre of image=0","*Technical arguments"})
	public void clickItem(String imageNameOrText, String[] arguments) {
		Locator locator = new Locator(arguments);
		Helper.getLibrary().clickItem(imageNameOrText, locator);
	}
	
	@RobotKeyword("Right clicks either given image or text at the screen.")
	@ArgumentNames({"Image name or text to click", "Similarity of images=0.7", "X offset from centre of image=0", "Y offset from centre of image=0","*Technical arguments"})
	public void rightClickItem(String imageNameOrText, String[] arguments) {
		Locator locator = new Locator(arguments);
		Helper.getLibrary().rightClickItem(imageNameOrText, locator);
	}
	
	@RobotKeyword("Double clicks either given image or text at the screen.")
	@ArgumentNames({"Image name or text to click", "Similarity of images=0.7", "X offset from centre of image=0", "Y offset from centre of image=0","*Technical arguments"})
	public void doubleClickItem(String imageNameOrText, String[] arguments) {
		Locator locator = new Locator(arguments);
		Helper.getLibrary().doubleClickItem(imageNameOrText, locator);
	}
}
