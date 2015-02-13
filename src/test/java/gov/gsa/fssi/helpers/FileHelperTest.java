package test.java.gov.gsa.fssi.helpers;

import main.java.gov.gsa.fssi.config.Config;

import org.junit.Assert;
import org.junit.Test;

public class FileHelperTest {
	/**
	 * 
	 */
	@Test
	public void testIsDirectory() {
		Config config = new Config("./testfiles/", "config.properties");

		Assert.assertEquals("failure - ConfigTest", "./testfiles/providers/",
				config.getProperty(Config.PROVIDERS_DIRECTORY));
	}
}
