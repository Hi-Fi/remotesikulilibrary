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

}
