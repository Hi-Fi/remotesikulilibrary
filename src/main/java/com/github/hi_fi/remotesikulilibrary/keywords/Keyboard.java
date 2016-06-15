package com.github.hi_fi.remotesikulilibrary.keywords;

import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;

@RobotKeywords
public class Keyboard {
	
	@RobotKeyword("Inputs given text to given field. Input is logger by Sikuli, so don't use for passwords/things that can't come directly to log.")
	public void inputText(String text, String imageOrText, Object...args) {
		
	}

}
