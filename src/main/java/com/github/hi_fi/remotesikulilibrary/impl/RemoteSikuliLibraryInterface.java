package com.github.hi_fi.remotesikulilibrary.impl;

import com.github.hi_fi.remotesikulilibrary.DTO.Locator;

public interface RemoteSikuliLibraryInterface {
	
	public String captureScreenshot(String[] remote);
	
	public void enableDebugging();
	
	public void clickItem(String imageNameOrText, Locator locator);
	
	public void waitUntilScreenContains(String imageNameOrText, Locator locator);
	
	public void waitUntilScreenDoesNotContain(String imageNameOrText, Locator locator);

	public void inputText(String text, String imageNameOrText, Locator locator);

	public void typeKeys(String text, Object...modifiers);
	
	public void doubleClickItem(String imageNameOrText, Locator locator);
	
	public void rightClickItem(String imageNameOrText, Locator locator);
	
	public int startApp(String appCommand);
	
	public void closeApp(String appCommand);
	
	public void switchApp(String appCommand);
	
	public void updateRegionToFocusedApp();
	
	public void resetRegionToFullScreen();
	
	public String getClipboardContent();
}
