package com.github.hi_fi.remotesikulilibrary.OCR;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import static org.powermock.api.mockito.PowerMockito.*;

import org.sikuli.script.Location;

import com.github.hi_fi.remotesikulilibrary.impl.Server;
import com.github.hi_fi.remotesikulilibrary.utils.Helper;

@RunWith(PowerMockRunner.class)
@PrepareForTest( TextRecognizer.class )
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
	public void testTextFinding() {
		TextRecognizer tr = new TextRecognizer();
		List<Location> results = tr.findTextFromImage("Focus test app", "src/test/resources/testImages/focus_test_app.png");
		assertEquals(1, results.size());
	}
	
	@Test
	public void testTextNotFinding() {
		TextRecognizer tr = new TextRecognizer();
		List<Location> results = tr.findTextFromImage("Not foundable text", "src/test/resources/testImages/focus_test_app.png");
		assertEquals(0, results.size());
	}
	
	@Test
	public void testSingleLetterFoundMultipleTimes() {
		Helper.enableDebug();
		TextRecognizer tr = new TextRecognizer();
		List<Location> results = tr.findTextFromImage("o", "src/test/resources/testImages/focus_test_app.png");
		assertEquals(5, results.size());
	}
	
	@Test
	public void testNormalTextSearchWithOneResult() {
		Server mockServer = mock(Server.class);
		Location location = new Location(0,0);
		try {
			whenNew(Server.class).withNoArguments().thenReturn(mockServer);
			doReturn("src/test/resources/testImages/focus_test_app.png").when(mockServer).captureRegion((String[]) Mockito.any());
			TextRecognizer tr = new TextRecognizer();
			location = tr.findText("Focus test app");
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		
		assertEquals(location.x, 189);
		assertEquals(location.y, 35);
	}
	
	@Test(expected = RuntimeException.class)
	public void testNormalTextSearchWithNoResults() {
		Helper.enableDebug();
		Server mockServer = mock(Server.class);
		TextRecognizer tr = new TextRecognizer();
		try {
			whenNew(Server.class).withNoArguments().thenReturn(mockServer);
			doReturn("src/test/resources/testImages/focus_test_app.png").when(mockServer).captureRegion((String[]) Mockito.any());
			tr = new TextRecognizer();
		} catch (Exception e) {
			e.printStackTrace();
			fail();
			
		}
		tr.findText("Not foundable text");
	}
	
	@Test
	public void testNormalTextSearchWithMoreThanOneResults() {
		Helper.enableDebug();
		Server mockServer = mock(Server.class);
		TextRecognizer tr = new TextRecognizer();
		try {
			whenNew(Server.class).withNoArguments().thenReturn(mockServer);
			doReturn("src/test/resources/testImages/focus_test_app.png").when(mockServer).captureRegion((String[]) Mockito.any());
			tr = new TextRecognizer();
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		Location location = tr.findText("s");
		assertEquals(178, location.x);
		assertEquals(35, location.y);
	}

}
