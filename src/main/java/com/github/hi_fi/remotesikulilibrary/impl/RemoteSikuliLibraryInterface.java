package com.github.hi_fi.remotesikulilibrary.impl;

import com.github.hi_fi.remotesikulilibrary.DTO.Locator;

public interface RemoteSikuliLibraryInterface {
	
	public String captureScreenshot(Object...remote);
	
	public void enableDebugging();
	
	public void clickItem(String imageNameOrText, Locator locator);
	
	public void waitUntilScreenContains(String imageNameOrText, Locator locator);

}
