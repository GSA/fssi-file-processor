package test.java.gov.gsa.fssi.providers;

import java.util.ArrayList;
import java.util.List;

import main.java.gov.gsa.fssi.files.providers.Provider;
import main.java.gov.gsa.fssi.files.providers.utils.ProviderValidator;
import main.java.gov.gsa.fssi.helpers.mockdata.MockProvider;

import org.junit.Assert;
import org.junit.Test;

public class ProviderValidateTest {
	
	/**
	 * This should test to make sure that we are catching required Fields
	 */
	@Test
	public void Validate() {
		Provider provider = MockProvider.make("TEST", "TEST", "", "CSV", "strategicsourcing@gsa.gov");
		ProviderValidator providerValidator = new ProviderValidator();
		providerValidator.validate(provider);
		
		Assert.assertEquals("failure - ProviderLoadTest", true, provider.getStatus());
	}	
	
	
	/**
	 * This should test to make sure that we are catching required Fields
	 */
	@Test
	public void ValidateEmptyProviderName() {
		Provider provider = MockProvider.make("", "TEST", "", "CSV", "strategicsourcing@gsa.gov");
		ProviderValidator providerValidator = new ProviderValidator();
		providerValidator.validate(provider);
		
		Assert.assertEquals("failure - ProviderLoadTest", false, provider.getStatus());
	}	
	
	
	
	/**
	 * This should test to make sure that we are catching required Fields
	 */
	@Test
	public void ValidateNullProviderName() {
		Provider provider = MockProvider.make(null, "TEST", "", "CSV", "strategicsourcing@gsa.gov");
		ProviderValidator providerValidator = new ProviderValidator();
		providerValidator.validate(provider);
		
		Assert.assertEquals("failure - ProviderLoadTest", false, provider.getStatus());
	}	
	
	
	/**
	 * This should test to make sure that we are catching required Fields
	 */
	@Test
	public void ValidateEmptyProviderIdentifier() {
		Provider provider = MockProvider.make("TEST", "", "", "CSV", "strategicsourcing@gsa.gov");
		ProviderValidator providerValidator = new ProviderValidator();
		providerValidator.validate(provider);
		
		Assert.assertEquals("failure - ProviderLoadTest", false, provider.getStatus());
	}
	
	/**
	 * This should test to make sure that we are catching required Fields
	 */
	@Test
	public void ValidateNullProviderIdentifier() {
		Provider provider = MockProvider.make("TEST", null, "", "CSV", "strategicsourcing@gsa.gov");
		ProviderValidator providerValidator = new ProviderValidator();
		providerValidator.validate(provider);
		
		Assert.assertEquals("failure - ProviderLoadTest", false, provider.getStatus());
	}
		
	
	@Test
	public void ValidateAll() {
		List<Provider> providers = new ArrayList<Provider>();
		providers.add(MockProvider.make("TEST", "TEST", "", "CSV"));
		providers.add(MockProvider.make("TEST", "TEST", "", "CSV", "strategicsourcing@gsa.gov"));	
		ProviderValidator providerValidator = new ProviderValidator();
		providerValidator.validateAll(providers);
		
		for (Provider provider : providers) {
			Assert.assertEquals("failure - ProviderLoadTest", true, provider.getStatus());			
		}
	}	
	
	@Test
	public void ValidateAllWithErrors() {
		List<Provider> providers = new ArrayList<Provider>();
		providers.add(MockProvider.make("", "TEST", "", "CSV"));
		providers.add(MockProvider.make("TEST", null, "", "CSV", "strategicsourcing@gsa.gov"));	
		ProviderValidator providerValidator = new ProviderValidator();
		providerValidator.validateAll(providers);
		
		for (Provider provider : providers) {
			Assert.assertEquals("failure - ProviderLoadTest", true, provider.getStatus());			
		}
	}		
	
	
}
