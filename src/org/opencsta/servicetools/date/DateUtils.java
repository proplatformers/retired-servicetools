/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.opencsta.servicetools.date;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author cm
 */
public class DateUtils {
    public static void main(String[] args){
        System.out.println(DateUtils.now("yyyy-MM-dd"));
        System.out.println(DateUtils.now("dd MMMMM yyyy"));
        System.out.println(DateUtils.now("yyyyMMdd"));
        System.out.println(DateUtils.now("dd.MM.yy"));
        System.out.println(DateUtils.now("MM/dd/yy"));
        System.out.println(DateUtils.now("yyyy.MM.dd G 'at' hh:mm:ss z"));
        System.out.println(DateUtils.now("EEE, MMM d, ''yy"));
        System.out.println(DateUtils.now("h:mm a"));
        System.out.println(DateUtils.now("H:mm:ss:SSS"));
        System.out.println(DateUtils.now("K:mm a,z"));
        System.out.println(DateUtils.now("yyyy.MMMMM.dd GGG hh:mm aaa"));
    }

    public static String now(String dateFormat) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return sdf.format(cal.getTime());
    }
}
