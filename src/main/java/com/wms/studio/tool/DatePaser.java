/**
 * 
 */
package com.wms.studio.tool;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author WMS
 * @version 1.0
 * @date 2014年9月12日 上午9:20:29
 */
public class DatePaser {

	public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	public static Date parseDate(String value) {
		return parseDate(value, DEFAULT_DATE_FORMAT);
	}

	public static Date parseDate(String value, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			return sdf.parse(value);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String formatDate(Date date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	public static String formatDate(Date date) {
		return formatDate(date, DEFAULT_DATE_FORMAT);
	}
}
