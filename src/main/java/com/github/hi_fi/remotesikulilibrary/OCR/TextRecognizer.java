package com.github.hi_fi.remotesikulilibrary.OCR;

import static net.sourceforge.tess4j.ITessAPI.TRUE;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import javax.imageio.ImageIO;

import com.github.hi_fi.remotesikulilibrary.impl.Server;
import com.sun.jna.Pointer;

import net.sourceforge.tess4j.TessAPI1;
import net.sourceforge.tess4j.ITessAPI.TessBaseAPI;
import net.sourceforge.tess4j.ITessAPI.TessPageIteratorLevel;
import net.sourceforge.tess4j.util.ImageIOHelper;
import net.sourceforge.tess4j.util.LoadLibs;

public class TextRecognizer {

	public void findText(String text) {
		Server server = new Server();
		String imageLocation = server.captureScreenshot(new String[]{});
		this.findTextFromImage(text, imageLocation);
	}
	
	public void findTextFromImage(String text, String imageLocation) {
		File imageData = new File(imageLocation);
		String datapath = LoadLibs.extractTessResources("tessdata").getAbsolutePath();
		String language = "eng";
		TessBaseAPI handle = TessAPI1.TessBaseAPICreate();
		BufferedImage image;
		try {
			image = ImageIO.read(imageData);
			ByteBuffer buf = ImageIOHelper.convertImageData(image);
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
			System.out.println("Bounding boxes:\nchar(s) left top right bottom confidence font-attributes");
			int level = TessPageIteratorLevel.RIL_TEXTLINE;
			do {
				Pointer ptr = TessAPI1.TessResultIteratorGetUTF8Text(ri, level);
				String word = ptr.getString(0);
				TessAPI1.TessDeleteText(ptr);
				float confidence = TessAPI1.TessResultIteratorConfidence(ri, level);
				IntBuffer leftB = IntBuffer.allocate(1);
				IntBuffer topB = IntBuffer.allocate(1);
				IntBuffer rightB = IntBuffer.allocate(1);
				IntBuffer bottomB = IntBuffer.allocate(1);
				TessAPI1.TessPageIteratorBoundingBox(pi, level, leftB, topB, rightB, bottomB);
				int left = leftB.get();
				int top = topB.get();
				int right = rightB.get();
				int bottom = bottomB.get();
				System.out.println(ptr.getString(0));
				if (ptr.getWideString(0).contains(text)) {
					System.out.println(String.format("%s %d %d %d %d %f", word, left, top, right, bottom, confidence));
					// System.out.println(String.format("%s %d %d %d %d", str, left,
					// height - bottom, right, height - top)); //
					// training box coordinates
				}
			} while (TessAPI1.TessPageIteratorNext(pi, level) == TRUE);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // require jai-imageio lib to read TIFF
	}

}
