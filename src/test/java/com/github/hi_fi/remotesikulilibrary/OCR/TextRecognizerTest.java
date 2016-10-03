package com.github.hi_fi.remotesikulilibrary.OCR;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.sun.jna.Native;

import net.sourceforge.tess4j.util.LoadLibs;

import static org.powermock.api.mockito.PowerMockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest( LoadLibs.class )
public class TextRecognizerTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testOCRNotAvailableUnsatisfiedLink() {
		mockStatic(LoadLibs.class);
		when(LoadLibs.getTesseractLibName()).thenReturn("Non_existing_dll");
		
		assertFalse(TextRecognizer.isOCRAvailable());
	}
	
	@Test
	public void testOCRNotAvailableNoClassDefFound() {
		mockStatic(LoadLibs.class);
		when(LoadLibs.getTesseractLibName()).thenReturn("Non_existing_dll");
		
		assertFalse(TextRecognizer.isOCRAvailable());
	}
}