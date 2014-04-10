package uk.pcn.invoice.util;

import java.text.SimpleDateFormat;
import java.util.Date;



public class PCNUtil {
	
	public static String filePath;
	public static String invoiceNumStr;
	public static boolean lodonPostCodeCheck(String postCode){
		//E1,W1, NW1, SW1, SE1
		if(postCode != null && !postCode.isEmpty()){
		String[] londonPostCodes = {"E", "W", "NW", "SW", "SE"};
		String left = postCode.split(" ")[0];
		StringBuilder finalLeft = new StringBuilder();
		char temp;
		for (int i = 0; i < left.length(); i++) {
			temp = left.charAt(i);
			if(Character.isDigit(temp))
			{
				break;
			}
			finalLeft.append(temp);
		}
			//finalLeft.append(left.subSequence(0, 2));
			for (int i = 0; i < londonPostCodes.length; i++) {
				if(finalLeft.toString().equalsIgnoreCase(londonPostCodes[i]))
					return true;
			}
		}
		return false;
	}
	
	public static boolean lodonPostCodeCheck(String[] postCodesPerZone, String postCode){
		//E1,W1, NW1, SW1, SE1
		String left = postCode.split(" ")[0];
		StringBuilder finalLeft = new StringBuilder();
		char temp;
		for (int i = 0; i < left.length(); i++) {
			temp = left.charAt(i);
			if(Character.isDigit(temp)){
				break;
			}
			finalLeft.append(temp);
		}
			//finalLeft.append(left.subSequence(0, 2));
			for (int i = 0; i < postCodesPerZone.length; i++) {
				if(finalLeft.toString().equalsIgnoreCase(postCodesPerZone[i]))
					return true;
			}
		return false;
	}

	public static Date getCurrentDate(){
		//String date = new SimpleDateFormat("yyyy/M/dd").format(new Date());
		return new Date();
	}
	
}
