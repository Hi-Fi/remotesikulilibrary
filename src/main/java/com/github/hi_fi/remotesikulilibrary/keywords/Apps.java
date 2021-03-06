package com.github.hi_fi.remotesikulilibrary.keywords;

import org.robotframework.javalib.annotation.ArgumentNames;
import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;

import com.github.hi_fi.remotesikulilibrary.utils.Helper;

@RobotKeywords
public class Apps {
	
	@RobotKeyword("Starts application. Application can be given as command (if in PATH) or with full path.")
	@ArgumentNames({"Application to open"})
	public int openApp(String appPath) {
		return Helper.getLibrary().startApp(appPath);
	}
	
	@RobotKeyword("Closes given application.\n\n"
			+"App can be identified with name (title), command or full path")
	@ArgumentNames({"App identifier"})
	public void closeApp(String appIdentifier) {
		Helper.getLibrary().closeApp(appIdentifier);
	}
	
	@RobotKeyword("Switch to given application.\n\n"
			     +"App can be identified with name (title), command or full path")
	@ArgumentNames({"App identifier"})
	public void switchApp(String appIdentifier) {
		Helper.getLibrary().switchApp(appIdentifier);
	}	
	
	@RobotKeyword("Assign only focused app as target of actions.")
	public void useFocusedAppAsRegion() {
		Helper.getLibrary().updateRegionToFocusedApp();
	}
	
	@RobotKeyword("Reset actions to target to whole screen.")
	public void resetRegionToFullScreen() {
		Helper.getLibrary().resetRegionToFullScreen();
	}
}
