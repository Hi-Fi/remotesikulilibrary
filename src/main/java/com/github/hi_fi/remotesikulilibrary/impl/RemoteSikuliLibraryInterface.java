package com.github.hi_fi.remotesikulilibrary.impl;

import com.github.hi_fi.remotesikulilibrary.DTO.Locator;

public interface RemoteSikuliLibraryInterface {
	
	public String captureScreenshot(String[] remote);
	
	public void enableDebugging();
	
	public void clickItem(String imageNameOrText, Locator locator);
	
	public void waitUntilScreenContains(String imageNameOrText, Locator locator);

	public void inputText(String text, String imageNameOrText, Locator locator);

	public void typeKeys(String text, String[] modifiers);

}
