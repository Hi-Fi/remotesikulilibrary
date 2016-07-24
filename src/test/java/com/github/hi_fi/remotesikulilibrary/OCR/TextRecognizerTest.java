package com.github.hi_fi.remotesikulilibrary.OCR;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

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
		tr.findTextFromImage("Focus test app", "src/test/resources/testImages/focus_test_app.png");
	}

}
