package testObject;

import static org.junit.Assert.*;

import java.util.Properties;

import org.junit.Test;

public class DataSourceTest {

	@Test
	public void testGetInstanceConfig() {
		System.out.println("getInstanceConfig");
		Properties expResult = new Properties(configuration);
	}

}
