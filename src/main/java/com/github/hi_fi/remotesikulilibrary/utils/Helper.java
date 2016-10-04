package com.github.hi_fi.remotesikulilibrary.utils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.sikuli.script.Region;
import org.sikuli.script.Screen;

import com.github.hi_fi.remotesikulilibrary.impl.Client;
import com.github.hi_fi.remotesikulilibrary.impl.RemoteSikuliLibraryInterface;
import com.github.hi_fi.remotesikulilibrary.impl.Server;

public class Helper {

	private static RemoteSikuliLibraryInterface library = new Server();
	private static XmlRpcClient remoteClient = null;
	private static boolean remote = false;
	private static boolean logDebug = false;
	private static String imageDirectory = "testdata/images";
	private static String screenshotDirectory = "images";
	private static double waitTimeout = 10.0;
	private static Region appRegion;
	
	public static Region getRegion() {
		if (appRegion == null) {
			appRegion = new Screen();
		}
		return appRegion;
	}
	
	public static Region setRegion(Region newRegion) {
		appRegion = newRegion;
		return appRegion;
	}
	
	public static Region resetRegion() {
		appRegion = new Screen();
		return appRegion;
	}
	
	public static XmlRpcClient getRemoteClient() {
		return remoteClient;
	}
	public static boolean isDebug() {
		return logDebug;
	}
	
	public static boolean isRemote() {
		return remote;
	}
	
	public static boolean enableDebug() {
		logDebug = true;
		return logDebug;
	}
	
	public static boolean disableDebug() {
		logDebug = false;
		return logDebug;
	}
	
	public static double setWaitTimeout(double timeout) {
		waitTimeout = timeout;
		return waitTimeout;
	}
	
	public static double getWaitTimeout() {
		return waitTimeout;
	}
	
	public static void setImageDirectory(String imageDirectory) {
		Helper.imageDirectory = imageDirectory;
	}
	
	public static String getImageDirectory() {
		return imageDirectory;
	}
	
	public static void setScreenshotDirectory(String screenshotDirectory) {
		Helper.screenshotDirectory = screenshotDirectory;
	}
	
	public static String getScreenshotDirectory() {
		return screenshotDirectory;
	}
	
	public static void initializeConnection(String URI) {
		SikuliLogger.log("Initializing connection to: "+URI);
		XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
	    try {
			config.setServerURL(new URL(URI));
		} catch (MalformedURLException e) {
			SikuliLogger.logDebug(e.getStackTrace());
			throw new RuntimeException(e.getMessage());
		}
	    remoteClient = new XmlRpcClient();
	    remoteClient.setConfig(config);
	    getRemoteKeywords();
	    library = new Client();
	    remote = true;
	}
	
	public static void closeConnection() {
		remoteClient = null;
		library = new Server();
		remote = false;
	}
	
	public static String writeImageByteArrayToDisk(byte[] imageData) {
		return writeImageByteArrayToDisk(imageData, true);
	}
	
	public static String writeImageByteArrayToDisk(byte[] imageData, boolean logImage) {
		String screenshotPath = screenshotDirectory+"/"+UUID.randomUUID()+".png";
		try {
			FileUtils.writeByteArrayToFile(new File(screenshotPath), imageData);
			if (logImage) { SikuliLogger.logImage(screenshotPath); }
		} catch (IOException e) {
			SikuliLogger.logDebug(e.getStackTrace());
			throw new RuntimeException(e.getMessage());
		}
		return screenshotPath;
	}
	
	public static RemoteSikuliLibraryInterface getLibrary() {
		return library;
	}
	
	private static void getRemoteKeywords() {
		try {
			remoteClient.execute("get_keyword_names", new Object[] {});
		} catch (XmlRpcException e) {
			remoteClient = null;
			throw new RuntimeException("Obtaining remote keywords failed");
		}
		SikuliLogger.logDebug("Tested initialized connection by getting keyword names");
	}

}
