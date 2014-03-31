package uk.pcn.invoice.common;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CustomerNamesTest {

	@Test
	public void testGetCustomerName() {
		boolean check = true;
		
		try{
		CustomerNames.getCustomerName("EMOTIVE (NUNEATON)");
		CustomerNames.getCustomerName("MANOR HYGIENE SUPPLIES LTD");
		}catch(Exception e){
			check = false;
		}
		assertEquals(true, check);
	}

}
