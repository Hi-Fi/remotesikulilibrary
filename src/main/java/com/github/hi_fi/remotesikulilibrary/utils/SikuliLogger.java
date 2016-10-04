package com.github.hi_fi.remotesikulilibrary.utils;

import static org.apache.commons.lang.exception.ExceptionUtils.getStackTrace;

public class SikuliLogger {
	
	public enum Level {
		TRACE, DEBUG, INFO, HTML, WARN, ERROR
	}
	
	public static void logImage(String imagePath) {
		logHTML("<a href='"+imagePath+"' target='_blank'><img src='"+imagePath+"' style='width:80%; height: auto;'/></a>");
	}
	
	public static void logHTML(Object log) {
		System.out.println("*HTML* "+log);
	}
	
	public static void logError(Object log) {
		System.out.println("*ERROR* "+log);
	}
	
	public static void logDebug(Object log) {
		if (Helper.isDebug()) {
			System.out.println("*DEBUG* "+log);
		}
	}
	
	public static void logTrace(Object log) {
		System.out.println("*TRACE* "+log);
	}
	
	public static void logStackTrace(Throwable error) {
		logStackTrace(error, Level.TRACE);
	}
			
	public static void logStackTrace(Throwable error, Level logLevel) {
		System.out.println("*"+logLevel.toString()+"* "+ getStackTrace(error));
	}
	
	public static void log(Object log) {
		System.out.println("*INFO* "+log);
	}
}
