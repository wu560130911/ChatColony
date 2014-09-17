/**
 * 
 */
package com.wms.studio;

import org.joda.time.DateTime;
import org.junit.Test;

import com.alibaba.fastjson.JSON;

/**
 * @author WMS
 * @version 1.0
 * @date 2014年9月14日 下午8:13:48
 */
public class DateTest {

	@Test
	public void test(){
		DateTime date = new DateTime();
		String str2 = date.toString("MM/dd/yyyy hh:mm:ss.SSSa");
        String s = JSON.toJSONStringWithDateFormat(date, "yyyy-MM-dd HH:mm:ss.SSS");
        System.out.println(s);
        System.out.println(str2);
	}
}
