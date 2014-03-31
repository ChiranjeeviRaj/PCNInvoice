package uk.pcn.invoice.service;

import org.junit.Assert;
import org.junit.Test;



public class RateCardServiceTest extends AbstractServiceTest {
	
	
	protected void setUp() throws Exception {
	}


	@SuppressWarnings("deprecation")
	@Test
	public void testCalulateTotalPrice() {
		Assert.assertEquals(rateCardService.calulateTotalPrice(0.00, 261.00)==313.20, true);
	}

}
