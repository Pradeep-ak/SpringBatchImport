package com.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Util {
	
	public static String getFileName(String pattern, String extension){
		return pattern + getDateStringForFileName() + extension;
		
	}
	
	public static String getDateStringForFileName(){
		DateFormat dateFormat = new SimpleDateFormat("dd");
		DateFormat suffixDateFormat = new SimpleDateFormat(" MMM yyyy");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);    
        String dateSuffix = suffixDateFormat.format(cal.getTime());
        int date = Integer.parseInt(dateFormat.format(cal.getTime()));
		int modulus = date % 10;
		String dateText = null;
		
		if (!(date > 10 && date < 20)) { // for all days except 11 to 19
			switch (modulus) {
				case 11:
					dateText = date + "th";
					break;
				case 1:
					dateText = date + "st";
					break;
				case 2:
					dateText = date + "nd";
					break;
				case 3:
					dateText = date + "rd";
					break;
				default:
					dateText = date + "th";
					break;
			}
		}else {
				dateText = date + "th";
		}
		return dateText + dateSuffix;
	}
	
	public static String getYesterdaysDate(){
		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		return dateFormat.format(cal.getTime());
	}
	
}
