package test.java.gov.gsa.fssi.config;

import main.java.gov.gsa.fssi.config.Config;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigTest {
	protected static final Logger logger = LoggerFactory
			.getLogger(ConfigTest.class);

	/**
	 * This should test to make sure that we are catching required Fields
	 */
	@Test
	public void read() {
		Config config = new Config("./testfiles/", "config.properties");

		Assert.assertEquals("failure - ConfigTest", "./testfiles/providers/",
				config.getProperty(Config.PROVIDERS_DIRECTORY));
	}

}
