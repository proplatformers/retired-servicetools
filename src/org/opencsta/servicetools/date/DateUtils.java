/*
This file is part of Open CSTA.

    Open CSTA is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Open CSTA is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with Open CSTA.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.opencsta.servicetools.date;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 
 * @author chrismylonas
 */
public class DateUtils {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
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

	/**
	 * @param dateFormat
	 * @return
	 */
	public static String now(String dateFormat) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		return sdf.format(cal.getTime());
	}
}
