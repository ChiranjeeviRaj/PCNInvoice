package uk.pcn.invoice.util;

import java.net.URL;

import org.springframework.util.StringUtils;

public class FileUtil {
	
	public static String getClassPath(){
		URL url = FileUtil.class.getResource("FileUtil.class"); 
		String[] split = StringUtils.split(url.toString(), "PCNInvoice");
		String path =  split[0].replaceAll("file:/", "");

		return path + "PCNInvoice/";
	}
	
}
