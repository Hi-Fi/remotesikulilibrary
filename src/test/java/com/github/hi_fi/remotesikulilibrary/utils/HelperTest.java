package com.github.hi_fi.remotesikulilibrary.utils;

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

import org.apache.xmlrpc.client.XmlRpcClient;

@RunWith(PowerMockRunner.class)
@PrepareForTest( Helper.class )
public class HelperTest {

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

	
	@Test(expected = RuntimeException.class)
	public void testWriteImageByteArrayToDiskException() {
		Helper.writeImageByteArrayToDisk(null);
	}
	
	@Test(expected = RuntimeException.class)
	public void initializeConnectionWithIncorrectURL() {
		Helper.initializeConnection("Incorrect URI");
	}
	
	@Test
	public void initializeConnectionCorrectly() {
		XmlRpcClient mockClient = mock(XmlRpcClient.class);
		try {
			whenNew(XmlRpcClient.class).withNoArguments().thenReturn(mockClient);
			doReturn(true).when(mockClient).execute(Mockito.anyString(), Mockito.any(Object[].class));
			Helper.initializeConnection("http://localhost");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	

}
