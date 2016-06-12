package com.github.hifi.remotesikulilibrary.impl;

public interface RemoteSikuliLibraryInterface {
	
	public String captureScreenshot(Object...remote);
	
	public void clickImage(Object...remote);
	
	public void enableDebugging();

}
