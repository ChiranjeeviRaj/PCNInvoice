package uk.pcn.invoice.util;

import java.text.DecimalFormat;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Test;

public class XMLUtillTest extends TestCase {

	//file:/var/lib/openshift/53393c4a5973ca750300004d/jbossews/work/Catalina/localhost/_/WEB-INF/classes/uk/pcn/invoice/util/XmlUtils.class
	@Test
	public void test(){
		String path = "file:/var/lib/openshift/53393c4a5973ca750300004d/jbossews/work/Catalina/localhost/_/WEB-INF/classes/uk/pcn/invoice/util/XmlUtils.class";
		Assert.assertEquals("var/lib/openshift/53393c4a5973ca750300004d/jbossews/work/Catalina/localhost/", path.split("_")[0].replaceAll("file:/", ""));
	}
/*	@Test
	public void testRound(){
        double d = 1.1;
        DecimalFormat df = new DecimalFormat("#.##");
        System.out.print(df.format(d));
        
        
        double d1 = 1;
       // String srt =  ("%1$.2f", d1);
       // System.out.printf("%1$.2f", d1);
        
        double d2 = 2;
        DecimalFormat f = new DecimalFormat("##.00");  // this will helps you to always keeps in two decimal places
        System.out.println(f.format(d2)); 
        
        Assert.assertEquals(1.1,df.format(d));
	}*/
}


