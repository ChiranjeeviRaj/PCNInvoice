package uk.pcn.invoice.util;

import uk.pcn.invoice.util.FileUtil;

import junit.framework.Assert;
import junit.framework.TestCase;

public class FileUtilTest extends TestCase {
	
	public void testgetClassPath(){
		Assert.assertEquals("E:/workspace-pcn/PCNInvoice/", FileUtil.getClassPath());
	}

}
