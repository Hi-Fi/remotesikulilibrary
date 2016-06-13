package com.github.hi_fi.remotesikulilibrary.impl;

public interface RemoteSikuliLibraryInterface {
	
	public String captureScreenshot(Object...remote);
	
	public void enableDebugging();
	
	public void clickItem(String imageNameOrText, double similarity, int xOffset, int yOffset, boolean remote, Object...imageData);

}
