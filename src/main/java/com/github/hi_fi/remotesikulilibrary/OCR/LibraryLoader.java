package com.github.hi_fi.remotesikulilibrary.OCR;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.jar.JarEntry;

import org.apache.commons.lang.SystemUtils;
import org.sikuli.script.RunTime;

public class LibraryLoader {

	String tmpdir;
	String fLibsFolder;
	String[] neededLibs;
	RunTime runTime;
	String vSysArch;
	String os;
	
	public LibraryLoader() {
		tmpdir = System.getProperty("java.io.tmpdir");
		fLibsFolder = "OCR";
		vSysArch = System.getProperty("sikuli.arch");
	    if (null == vSysArch) {
	      vSysArch = System.getProperty("os.arch");
	    }
	    
	    if (vSysArch.contains("64")) {
	    	vSysArch = "x86"; 
	    } else {
	    	vSysArch = "x86";
	    }
	    
		if (SystemUtils.IS_OS_WINDOWS) {
			neededLibs = new String[] {"libtesseract-3.dll", "jnitesseract.dll"};
			os = "windows";
		} else if (SystemUtils.IS_OS_MAC) {
			neededLibs = new String[] {"libtesseract.3.dylib", "libjnitesseract.dylib"};
			os = "macosx";
		} else if (SystemUtils.IS_OS_LINUX) {
			neededLibs = new String[] {"libtesseract.so.3", "libjnitesseract.so"};
			os = "linux";
		}
		
		this.extractDllFromJar();
	}
	
	public void loadOCRLibraries() {
		for (String libraryName : neededLibs) {
			this.loadLibrary(libraryName);
		}
	}
	
	private void loadLibrary(String libraryName) {
		try {
			System.loadLibrary(libraryName);
		} catch (UnsatisfiedLinkError e) {
			this.loadLibraryFromExtractedJar(libraryName);
		}
	}
	
	private void loadLibraryFromExtractedJar(String libraryName) {
		System.load(new File(tmpdir + java.io.File.separator+ "org" + java.io.File.separator + "bytedeco" + java.io.File.separator + "javacpp" + java.io.File.separator + os + "-" + vSysArch, libraryName).getAbsolutePath());
	}
	
	private void extractDllFromJar() {
		String[] classes = System.getProperty("java.class.path").split(";");
		for (String classLocation : classes) {
			if (classLocation.contains(os+"-"+vSysArch)) {
				try {
					java.util.jar.JarFile jar = new java.util.jar.JarFile(classLocation);
					Enumeration<JarEntry> enumEntries = jar.entries();
					while (enumEntries.hasMoreElements()) {
					    java.util.jar.JarEntry file = (java.util.jar.JarEntry) enumEntries.nextElement();
					    java.io.File f = new java.io.File(tmpdir + java.io.File.separator + file.getName());
					    if (!f.exists()) {
						    if (file.isDirectory()) { // if its a directory, create it
						        f.mkdir();
						        continue;
						    }
						    java.io.InputStream is = jar.getInputStream(file); // get the input stream
						    java.io.FileOutputStream fos = new java.io.FileOutputStream(f);
						    while (is.available() > 0) {  // write contents of 'is' to 'fos'
						        fos.write(is.read());
						    }
						    fos.close();
						    is.close();
					    }
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
}
