package com.github.hi_fi.remotesikulilibrary.DTO;

import java.io.IOException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
public class LocatorTest {
	
	Locator locator;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() {
		this.locator = new Locator(new String[] {"TestingLikeEncodedImage"});
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void locatorCreatedWithEmptyArguments() {
		String[] args = {};
		new Locator(args);
	}
	
	@Test(expected = RuntimeException.class)
	public void testExceptionThrownWhenLocalTargetImageNotFounddAndOCRIsOff() {
		locator.updateLocatorTarget("Non-existing");
	}
	
	@Test(expected = RuntimeException.class)
	public void exceptionThrownWhenRemoteTargetImageNotFound() {
		locator.setRemote(true);
		locator.setImageData("Incorrect image data");
		locator.updateLocatorTarget("Non-existing");
	}
	
	@Test(expected = RuntimeException.class)
	public void exceptionThrownIfImageNotFoundAtEncoding() {
		locator.encodeImageToBase64("Non-existing");
	}

}
