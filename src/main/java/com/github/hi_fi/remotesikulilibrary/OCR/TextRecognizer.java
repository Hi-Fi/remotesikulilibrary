package com.github.hi_fi.remotesikulilibrary.OCR;

import static net.sourceforge.tess4j.ITessAPI.TRUE;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.sikuli.script.Image;
import org.sikuli.script.Location;

import com.github.hi_fi.remotesikulilibrary.impl.Server;
import com.github.hi_fi.remotesikulilibrary.utils.SikuliLogger;
import com.sun.jna.Pointer;

import net.sourceforge.tess4j.TessAPI1;
import net.sourceforge.tess4j.ITessAPI.TessBaseAPI;
import net.sourceforge.tess4j.ITessAPI.TessPageIteratorLevel;
import net.sourceforge.tess4j.util.LoadLibs;

public class TextRecognizer {

	public Location findText(String text) {
		Server server = new Server();
		String imageLocation = server.captureScreenshot(new String[]{});
		List<Location> coordinates = this.findTextFromImage(text, imageLocation);
		if (coordinates.size() == 0) {
			throw new RuntimeException("Text "+text+" not found from page");
		} else {
			if (coordinates.size() > 1) {
				SikuliLogger.log("Text found multiple times from page, clicking first occurrence");
			}
			return coordinates.get(0);
		}
	}
	
	public List<Location> findTextFromImage(String text, String imageLocation) {
		List<Location> coordinates = new ArrayList<Location>();
		File imageFile = new File(imageLocation);
		String datapath = LoadLibs.extractTessResources("tessdata").getAbsolutePath();
		String language = "eng";
		TessBaseAPI handle = TessAPI1.TessBaseAPICreate();
		BufferedImage image;
		try {
			image = ImageIO.read(imageFile);
			image = this.enlargeImageForOCR(image);
			// This also "fixes" the image; if resizing is done after this, there's memory access error
			image = Image.convertImageToGrayscale(image);
			ByteBuffer buf = Image.convertImageData(image);
			int bpp = image.getColorModel().getPixelSize();
			int bytespp = bpp / 8;
			int bytespl = (int) Math.ceil(image.getWidth() * bpp / 8.0);
			TessAPI1.TessBaseAPIInit3(handle, datapath, language);
			TessAPI1.TessBaseAPISetPageSegMode(handle, TessAPI1.TessPageSegMode.PSM_SPARSE_TEXT);
			TessAPI1.TessBaseAPISetImage(handle, buf, image.getWidth(), image.getHeight(), bytespp, bytespl);
			TessAPI1.TessBaseAPIRecognize(handle, null);
			TessAPI1.TessResultIterator ri = TessAPI1.TessBaseAPIGetIterator(handle);
			TessAPI1.TessPageIterator pi = TessAPI1.TessResultIteratorGetPageIterator(ri);
			TessAPI1.TessPageIteratorBegin(pi);
			int level = TessPageIteratorLevel.RIL_WORD;
			if (text.split(" ").length > 1) {
				level = TessPageIteratorLevel.RIL_TEXTLINE;
			}
			do {
				Pointer ptr = TessAPI1.TessResultIteratorGetUTF8Text(ri, level);
				String foundText = ptr.getString(0);
				TessAPI1.TessDeleteText(ptr);
				if (foundText.contains(text)) {
					IntBuffer leftB = IntBuffer.allocate(1);
					IntBuffer topB = IntBuffer.allocate(1);
					IntBuffer rightB = IntBuffer.allocate(1);
					IntBuffer bottomB = IntBuffer.allocate(1);
					TessAPI1.TessPageIteratorBoundingBox(pi, level, leftB, topB, rightB, bottomB);
					int left = leftB.get();
					int top = topB.get();
					int right = rightB.get();
					int bottom = bottomB.get();
					coordinates.add(new Location((right-left)/2, (top-bottom)/2));
				}
			} while (TessAPI1.TessPageIteratorNext(pi, level) == TRUE);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // require jai-imageio lib to read TIFF
		return coordinates;
	}
	
	private BufferedImage enlargeImageForOCR(BufferedImage image) {
		int targetHeight = image.getHeight()*3;
		int targetWidth = image.getWidth()*3;
		return Scalr.resize(image, targetWidth, targetHeight);
	}

}
