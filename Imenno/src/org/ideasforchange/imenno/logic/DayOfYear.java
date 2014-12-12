package org.ideasforchange.imenno.logic;

import java.util.Calendar;

import org.ideasforchange.imenno.DateUtils;

/**
 * 
 * A class to represent a date without the year (as it is irrelevant for the purpose of name days)
 * @author Daniel Hristov
 *
 */
public class DayOfYear {
	
	private Calendar calendar = null;

	public DayOfYear(int monthOfYear, int dayOfMonth) {
		calendar = Calendar.getInstance();
		calendar.set(Calendar.MONTH, monthOfYear);
		calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
	}
	
	public boolean isToday() {
		return DateUtils.isToday(calendar);
	}

	public int getMonth() {
		return calendar.get(Calendar.MONTH);
	}

	public int getDayOfMonth() {
		return calendar.get(Calendar.DAY_OF_MONTH);
	}
}
