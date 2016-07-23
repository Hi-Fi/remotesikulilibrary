package com.github.hi_fi.remotesikulilibrary.DTO;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest( Locator.class )
public class LocatorTest {
	
	Locator locator;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		String[] arguments = {};
		this.locator = new Locator(arguments);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void noExceptionThrownWhenLocalTargetImageNotFound() {
		locator.updateLocatorTarget("Non-existing");
		assertFalse(locator.isImage());
	}
	
	@Test(expected = RuntimeException.class)
	public void exceptionThrownWhenRemoteTargetImageNotFound() {
		locator.setRemote(true);
		locator.setImageData("Incorrect image data");
		locator.updateLocatorTarget("Non-existing");
		assertFalse(locator.isImage());
	}
	
	@Test
	public void noExceptionThrownIfImageNotFound() {
		locator.encodeImageToBase64("Non-existing");
		assertTrue(locator.getImageData().length() == 0);
	}

}
