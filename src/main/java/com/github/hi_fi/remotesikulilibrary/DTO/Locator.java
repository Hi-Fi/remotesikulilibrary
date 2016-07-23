package com.github.hi_fi.remotesikulilibrary.DTO;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.ws.commons.util.Base64;
import org.apache.ws.commons.util.Base64.DecodingException;

import com.github.hi_fi.remotesikulilibrary.utils.Helper;
import com.github.hi_fi.remotesikulilibrary.utils.SikuliLogger;

public class Locator {
	
	private String imageData = "";
	private boolean image = false;
	private boolean text = false;
	private boolean remote = false;
	private int xOffset = 0;
	private int yOffset = 0;
	private double similarity = 0.7;
	
	
	public Locator(String[] args) {
		SikuliLogger.logDebug(args);
		if (args.length > 0 && NumberUtils.isNumber(args[0].toString())) {
			this.setSimilarity(Double.parseDouble(args[0].toString()));
			if (args.length == 3) {
				if (NumberUtils.isNumber(args[1].toString())) {
					this.setxOffset(Integer.parseInt(args[1].toString()));
				}
				if (NumberUtils.isNumber(args[2].toString())) {
					this.setyOffset(Integer.parseInt(args[2].toString()));
				}
			}
		}
		
		if (args.length > 0 && !NumberUtils.isNumber(args[args.length-1].toString()) && args[args.length-1].toString().length() > 10) {
			this.setRemote(true);
			this.setImage(true);
			this.setText(false);
			this.setImageData(args[args.length-1].toString());
		} else {
			this.setText(true);
			this.setImage(false);
		}
	}


	public String getImageData() {
		return imageData;
	}


	public void setImageData(String imageData) {
		this.imageData = imageData;
	}


	public boolean isImage() {
		return image;
	}


	public void setImage(boolean image) {
		this.image = image;
	}


	public boolean isText() {
		return text;
	}


	public void setText(boolean text) {
		this.text = text;
	}


	public boolean isRemote() {
		return remote;
	}


	public void setRemote(boolean remote) {
		this.remote = remote;
	}


	public int getxOffset() {
		return xOffset;
	}


	public void setxOffset(int xOffset) {
		this.xOffset = xOffset;
	}


	public int getyOffset() {
		return yOffset;
	}


	public void setyOffset(int yOffset) {
		this.yOffset = yOffset;
	}


	public double getSimilarity() {
		return similarity;
	}
	
	public float getSimilarityasFloat() {
		return (float) similarity;
	}


	public void setSimilarity(double similarity) {
		this.similarity = similarity;
	}

	public void encodeImageToBase64(String imageNameOrText) {
		try {
			SikuliLogger.logDebug("Checking if " + Helper.getImageDirectory() + "/" + imageNameOrText + " is image");
			File localImage = new File(Helper.getImageDirectory() + "/" + imageNameOrText);
			if (localImage.exists()) {
				this.imageData = Base64.encode(FileUtils.readFileToByteArray(localImage));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String updateLocatorTarget(String imageNameOrText) {
		if (this.isRemote() && this.getImageData().length() > 10) {
			SikuliLogger.logDebug("Parsing image from remote call");
			try {
				imageNameOrText = Helper.writeImageByteArrayToDisk(Base64.decode(this.getImageData()));
				this.setImage(true);
			} catch (DecodingException e) {
				SikuliLogger.logDebug(e.getStackTrace());
				throw new RuntimeException(e.getMessage());
			}
		} else if (!this.isRemote() && new File(Helper.getImageDirectory() + "/" + imageNameOrText).exists()) {
			imageNameOrText = Helper.getImageDirectory() + "/" + imageNameOrText;
			this.setImage(true);
		}
		return imageNameOrText;
	}
}
