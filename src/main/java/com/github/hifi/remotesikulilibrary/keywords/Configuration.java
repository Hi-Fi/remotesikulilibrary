package com.github.hifi.remotesikulilibrary.keywords;

import org.robotframework.javalib.annotation.ArgumentNames;
import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;
import org.robotframework.remoteserver.RemoteServer;

import com.github.hifi.remotesikulilibrary.RemoteSikuliLibrary;
import com.github.hifi.remotesikulilibrary.utils.Helper;
import com.github.hifi.remotesikulilibrary.utils.SikuliLogger;

@RobotKeywords
public class Configuration {
	
	private static RemoteServer remoteServer;
	
	@RobotKeyword("Creates XML-RPC-client for remote calls and sets test to use remote calls.")
	@ArgumentNames({"Full URL to remote server"})
	public void initializeConnection(String URI) {
		Helper.initializeConnection(URI);
	}
	
	
	@RobotKeyword("Enables debug level logging")
	public void enableDebugging() {
		Helper.getLibrary().enableDebugging();
		SikuliLogger.logDebug("Debugging enabled");
	}
	
	@RobotKeyword("Disables debug level logging")
	public void disableDebugging() {
		Helper.disableDebug();
	}
	
	@RobotKeyword("Set directory containing Sikuli's test images.\n\n"
				 +"Default directory is <i>testdata/images</i>.")
	public void setTestImageDirectory(String imageDirectory) {
		Helper.setImageDirectory(imageDirectory);
	}
	
	@RobotKeyword("Set directory where screenshots are stored.\n\n"
			 +"Default directory is <i>images</i>.")
	public void setScreenshotDirectory(String screenshotDirectory) {
		Helper.setScreenshotDirectory(screenshotDirectory);
	}
	
	@RobotKeyword("Starts remote server. Mainly used for testing.\n\n"
			     +"Returns server port.")
	@ArgumentNames({"Remote port=selected automatically"})
	public int startRemoteServer(String... port) {
		RemoteServer.configureLogging();
		RemoteServer server = new RemoteServer();
		remoteServer = server;
        remoteServer.putLibrary("/", new RemoteSikuliLibrary());
		if (port.length > 0) {
			remoteServer.setPort(Integer.parseInt(port[0]));
		}
        try {
        	remoteServer.start();
        	return remoteServer.getLocalPort();
		} catch (Exception e) {
			SikuliLogger.logDebug(e.getStackTrace());
			throw new RuntimeException(e.getMessage());
		}
	}
	
	@RobotKeyword("Stops remote server. Mainly used for testing.")
	public void stopRemoteServer() {
		try {
			remoteServer.stop();
			Helper.closeConnection();
		} catch (Exception e) {
			SikuliLogger.logDebug(e.getStackTrace());
			throw new RuntimeException(e.getMessage());
		}
	}

}
