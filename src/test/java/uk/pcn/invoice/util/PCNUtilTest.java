package uk.pcn.invoice.util;

import junit.framework.TestCase;

public class PCNUtilTest extends TestCase {

	public void testLodonPostCodeCheck() {
		assertEquals(false, PCNUtil.lodonPostCodeCheck("EC1V 0HB"));
	}

}
