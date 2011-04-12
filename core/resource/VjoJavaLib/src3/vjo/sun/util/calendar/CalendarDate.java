package vjo.sun.util.calendar;

/*jadclipse*/// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
//Jad home page: http://www.kpdus.com/jad.html
//Decompiler options: packimports(3) radix(10) lradix(10) 
//Source File Name:   CalendarDate.java

import java.lang.InternalError;

import vjo.lang.* ;
import vjo.lang.StringBuilder; 

import vjo.util.Locale;
import vjo.util.TimeZone;

//Referenced classes of package sun.util.calendar:
//         Era, CalendarUtils

public abstract class CalendarDate implements java.lang.Cloneable {

	protected CalendarDate() {
		this(TimeZone.getDefault());
	}

	protected CalendarDate(TimeZone timezone) {
		dayOfWeek = -2147483648;
		zoneinfo = timezone;
	}

	public Era getEra() {
		return era;
	}

	public CalendarDate setEra(Era era1) {
		if (era == era1) {
			return this;
		} else {
			era = era1;
			normalized = false;
			return this;
		}
	}

	public int getYear() {
		return year;
	}

	public CalendarDate setYear(int i) {
		if (year != i) {
			year = i;
			normalized = false;
		}
		return this;
	}

	public CalendarDate addYear(int i) {
		if (i != 0) {
			year += i;
			normalized = false;
		}
		return this;
	}

	public boolean isLeapYear() {
		return leapYear;
	}

	void setLeapYear(boolean flag) {
		leapYear = flag;
	}

	public int getMonth() {
		return month;
	}

	public CalendarDate setMonth(int i) {
		if (month != i) {
			month = i;
			normalized = false;
		}
		return this;
	}

	public CalendarDate addMonth(int i) {
		if (i != 0) {
			month += i;
			normalized = false;
		}
		return this;
	}

	public int getDayOfMonth() {
		return dayOfMonth;
	}

	public CalendarDate setDayOfMonth(int i) {
		if (dayOfMonth != i) {
			dayOfMonth = i;
			normalized = false;
		}
		return this;
	}

	public CalendarDate addDayOfMonth(int i) {
		if (i != 0) {
			dayOfMonth += i;
			normalized = false;
		}
		return this;
	}

	public int getDayOfWeek() {
		if (!isNormalized())
			dayOfWeek = -2147483648;
		return dayOfWeek;
	}

	public int getHours() {
		return hours;
	}

	public CalendarDate setHours(int i) {
		if (hours != i) {
			hours = i;
			normalized = false;
		}
		return this;
	}

	public CalendarDate addHours(int i) {
		if (i != 0) {
			hours += i;
			normalized = false;
		}
		return this;
	}

	public int getMinutes() {
		return minutes;
	}

	public CalendarDate setMinutes(int i) {
		if (minutes != i) {
			minutes = i;
			normalized = false;
		}
		return this;
	}

	public CalendarDate addMinutes(int i) {
		if (i != 0) {
			minutes += i;
			normalized = false;
		}
		return this;
	}

	public int getSeconds() {
		return seconds;
	}

	public CalendarDate setSeconds(int i) {
		if (seconds != i) {
			seconds = i;
			normalized = false;
		}
		return this;
	}

	public CalendarDate addSeconds(int i) {
		if (i != 0) {
			seconds += i;
			normalized = false;
		}
		return this;
	}

	public int getMillis() {
		return millis;
	}

	public CalendarDate setMillis(int i) {
		if (millis != i) {
			millis = i;
			normalized = false;
		}
		return this;
	}

	public CalendarDate addMillis(int i) {
		if (i != 0) {
			millis += i;
			normalized = false;
		}
		return this;
	}

	public long getTimeOfDay() {
		if (!isNormalized())
			return fraction = -9223372036854775808L;
		else
			return fraction;
	}

	public CalendarDate setDate(int i, int j, int k) {
		setYear(i);
		setMonth(j);
		setDayOfMonth(k);
		return this;
	}

	public CalendarDate addDate(int i, int j, int k) {
		addYear(i);
		addMonth(j);
		addDayOfMonth(k);
		return this;
	}

	public CalendarDate setTimeOfDay(int i, int j, int k, int l) {
		setHours(i);
		setMinutes(j);
		setSeconds(k);
		setMillis(l);
		return this;
	}

	public CalendarDate addTimeOfDay(int i, int j, int k, int l) {
		addHours(i);
		addMinutes(j);
		addSeconds(k);
		addMillis(l);
		return this;
	}

	protected void setTimeOfDay(long l) {
		fraction = l;
	}

	public boolean isNormalized() {
		return normalized;
	}

	public boolean isStandardTime() {
		return forceStandardTime;
	}

	public void setStandardTime(boolean flag) {
		forceStandardTime = flag;
	}

	public boolean isDaylightTime() {
		if (isStandardTime())
			return false;
		else
			return daylightSaving != 0;
	}

	protected void setLocale(Locale locale1) {
		locale = locale1;
	}

	public TimeZone getZone() {
		return zoneinfo;
	}

	public CalendarDate setZone(TimeZone timezone) {
		zoneinfo = timezone;
		return this;
	}

	public boolean isSameDate(CalendarDate calendardate) {
		return getDayOfWeek() == calendardate.getDayOfWeek()
				&& getMonth() == calendardate.getMonth()
				&& getYear() == calendardate.getYear()
				&& getEra() == calendardate.getEra();
	}

	public boolean equals(Object obj) {
		if (!(obj instanceof CalendarDate))
			return false;
		CalendarDate calendardate = (CalendarDate) obj;
		if (isNormalized() != calendardate.isNormalized())
			return false;
		boolean flag = zoneinfo != null;
		boolean flag1 = calendardate.zoneinfo != null;
		if (flag != flag1)
			return false;
		if (flag && !zoneinfo.equals(calendardate.zoneinfo))
			return false;
		else
			return getEra() == calendardate.getEra()
					&& year == calendardate.year && month == calendardate.month
					&& dayOfMonth == calendardate.dayOfMonth
					&& hours == calendardate.hours
					&& minutes == calendardate.minutes
					&& seconds == calendardate.seconds
					&& millis == calendardate.millis
					&& zoneOffset == calendardate.zoneOffset;
	}

//	MrP - JAD casuality
//	public int hashCode() {
//		long l = ((((long) year - 1970L) * 12L + (long) (month - 1)) * 30L + (long) dayOfMonth) * 24L;
//		l = (((l + (long) hours) * 60L + (long) minutes) * 60L + (long) seconds)
//				* 1000L + (long) millis;
//		l -= zoneOffset;
//		boolean flag = isNormalized();
//		int i = 0;
//		Era era1 = getEra();
//		if (era1 != null)
//			i = era1.hashCode();
//		int j = zoneinfo == null ? 0 : zoneinfo.hashCode();
//
//		return (int) l * (int) (l >> 32) ^ i ^ flag ^ j;
//
//	}

	public Object clone() {
		try {
			return super.clone();
		} catch (java.lang.CloneNotSupportedException clonenotsupportedexception) {
			throw new InternalError();
		}
	}

	public String toString() {
		StringBuilder stringbuilder = new StringBuilder();
		CalendarUtils.sprintf0d(stringbuilder, year, 4).append('-');
		CalendarUtils.sprintf0d(stringbuilder, month, 2).append('-');
		CalendarUtils.sprintf0d(stringbuilder, dayOfMonth, 2).append('T');
		CalendarUtils.sprintf0d(stringbuilder, hours, 2).append(':');
		CalendarUtils.sprintf0d(stringbuilder, minutes, 2).append(':');
		CalendarUtils.sprintf0d(stringbuilder, seconds, 2).append('.');
		CalendarUtils.sprintf0d(stringbuilder, millis, 3);
		if (zoneOffset == 0)
			stringbuilder.append('Z');
		else if (zoneOffset != -2147483648) {
			int i;
			char c;
			if (zoneOffset > 0) {
				i = zoneOffset;
				c = '+';
			} else {
				i = -zoneOffset;
				c = '-';
			}
			i /= 60000;
			stringbuilder.append(c);
			CalendarUtils.sprintf0d(stringbuilder, i / 60, 2);
			CalendarUtils.sprintf0d(stringbuilder, i % 60, 2);
		} else {
			stringbuilder.append(" local time");
		}
		return stringbuilder.toString();
	}

	protected void setDayOfWeek(int i) {
		dayOfWeek = i;
	}

	protected void setNormalized(boolean flag) {
		normalized = flag;
	}

	public int getZoneOffset() {
		return zoneOffset;
	}

	protected void setZoneOffset(int i) {
		zoneOffset = i;
	}

	public int getDaylightSaving() {
		return daylightSaving;
	}

	protected void setDaylightSaving(int i) {
		daylightSaving = i;
	}

	public static final int FIELD_UNDEFINED = -2147483648;

	public static final long TIME_UNDEFINED = -9223372036854775808L;

	private Era era;

	private int year;

	private int month;

	private int dayOfMonth;

	private int dayOfWeek;

	private boolean leapYear;

	private int hours;

	private int minutes;

	private int seconds;

	private int millis;

	private long fraction;

	private boolean normalized;

	private TimeZone zoneinfo;

	private int zoneOffset;

	private int daylightSaving;

	private boolean forceStandardTime;

	private Locale locale;
}


/*
	DECOMPILATION REPORT

	Decompiled from: C:\opt\java5-ibm-2007-12-13\jre\lib\core.jar
	Total time: 31 ms
	Jad reported messages/errors:
	Exit status: 0
	Caught exceptions:
*/
