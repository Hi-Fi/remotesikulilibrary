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
			+ "Locator is same than in item click/wait, so same variables available")
	@ArgumentNames({ "Text to type", "Image name or text to click", "Similarity of images=0.7",
			"X offset from centre of image=0", "Y offset from centre of image=0", "*Technical arguments" })
	public void inputText(String text, String imageNameOrText, String[] arguments) {
		Locator locator = new Locator(arguments);
		Helper.getLibrary().inputText(text, imageNameOrText, locator);
	}

	@RobotKeyword("Simulates keyboard typing one by one (or with modifiers)\n\n"
			    + "Please note that keyword is not taking any locators, so if typing needs to be done "
			    + "at some field/list, it has to be clicked first with e.g. @clickItem\n\n"
			    + "Multiple modifiers should be separated to different fieds"
			    + "This can be used also for fillig text fields, but only withn US-ASCII characters.\n"
			    + "Modifiers defined at http://sikulix-2014.readthedocs.io/en/latest/keys.html#key-modifiers-modifier-keys\n"
			    + "Supported special keys defined at: http://sikulix-2014.readthedocs.io/en/latest/keys.html#special-keys")
	@ArgumentNames({"Keys to press one by one", "*Modifiers held at the bottom during typing" })
	public void typeKeys(String text, String[] modifiers) {
		Helper.getLibrary().typeKeys(text, modifiers);
	}
	
	@RobotKeyword("Inputs given text to given field by selecting all text first. Input is pasted to field, which allows support for international characters\n\n"
			+ "Locator is same than in item click/wait, so same variables available")
	@ArgumentNames({ "Text to type", "Image name or text to click", "Similarity of images=0.7",
			"X offset from centre of image=0", "Y offset from centre of image=0", "*Technical arguments" })
	public void replaceTextInField(String text, String imageNameOrText, String[] arguments) {
		Locator locator = new Locator(arguments);
		Helper.getLibrary().clickItem(imageNameOrText, locator);
		Helper.getLibrary().typeKeys("a", "CTRL");
		Helper.getLibrary().inputText(text, null, locator);
	}

}
