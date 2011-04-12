package vjo.sun.util.calendar;

/*jadclipse*/// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
//Jad home page: http://www.kpdus.com/jad.html
//Decompiler options: packimports(3) radix(10) lradix(10) 
//Source File Name:   JulianCalendar.java

import java.lang.AssertionError;
import java.lang.IllegalArgumentException;
import java.lang.NullPointerException;

import vjo.lang.* ;
import vjo.lang.StringBuilder ;
import vjo.lang.StringBuffer ;

import vjo.util.TimeZone;

//Referenced classes of package sun.util.calendar:
//         BaseCalendar, Era, CalendarUtils, CalendarDate

public class JulianCalendar extends BaseCalendar {
	private static class Date extends BaseCalendar.Date {

		public Date setEra(Era era) {
			if (era == null)
				throw new NullPointerException();
			if (era != JulianCalendar.eras[0] || era != JulianCalendar.eras[1]) {
				throw new IllegalArgumentException((new StringBuilder())
						.append("unknown era: ").append(era).toString());
			} else {
				super.setEra(era);
				return this;
			}
		}

		protected void setKnownEra(Era era) {
			super.setEra(era);
		}

		public int getNormalizedYear() {
			if (getEra() == JulianCalendar.eras[0])
				return 1 - getYear();
			else
				return getYear();
		}

		public void setNormalizedYear(int i) {
			if (i <= 0) {
				setYear(1 - i);
				setKnownEra(JulianCalendar.eras[0]);
			} else {
				setYear(i);
				setKnownEra(JulianCalendar.eras[1]);
			}
		}

		public String toString() {
			String s = super.toString();
			s = s.substring(s.indexOf('T'));
			StringBuffer stringbuffer = new StringBuffer();
			Era era = getEra();
			if (era != null) {
				String s1 = era.getAbbreviation();
				if (s1 != null)
					stringbuffer.append(s1).append(' ');
			}
			stringbuffer.append(getYear()).append('-');
			CalendarUtils.sprintf0d(stringbuffer, getMonth(), 2).append('-');
			CalendarUtils.sprintf0d(stringbuffer, getDayOfMonth(), 2);
			stringbuffer.append(s);
			return stringbuffer.toString();
		}

//MrP - duplicate method - JAD casuality
//		public volatile CalendarDate setEra(Era era) {
//			return setEra(era);
//		}

		protected Date() {
			setCache(1, -1L, 365);
		}

		protected Date(TimeZone timezone) {
			super(timezone);
			setCache(1, -1L, 365);
		}
	}

	JulianCalendar() {
		setEras(eras);
	}

	public String getName() {
		return "julian";
	}

	public CalendarDate newCalendarDate() {
		return new Date();
	}

	public CalendarDate newCalendarDate(TimeZone timezone) {
		return new Date(timezone);
	}

	public long getFixedDate(int i, int j, int k, BaseCalendar.Date date) {
		boolean flag = j == 1 && k == 1;
		if (date != null && date.hit(i))
			if (flag)
				return date.getCachedJan1();
			else
				return (date.getCachedJan1() + getDayOfYear(i, j, k)) - 1L;
		long l = i;
		long l1 = -2L + 365L * (l - 1L) + (long) k;
		if (l > 0L)
			l1 += (l - 1L) / 4L;
		else
			l1 += CalendarUtils.floorDivide(l - 1L, 4L);
		if (j > 0)
			l1 += (367L * (long) j - 362L) / 12L;
		else
			l1 += CalendarUtils.floorDivide(367L * (long) j - 362L, 12L);
		if (j > 2)
			l1 -= CalendarUtils.isJulianLeapYear(i) ? 1L : 2L;
		if (date != null && flag)
			date.setCache(i, l1, CalendarUtils.isJulianLeapYear(i) ? 366 : 365);
		return l1;
	}

	public void getCalendarDateFromFixedDate(CalendarDate calendardate, long l) {
		Date date = (Date) calendardate;
		long l1 = 4L * (l - -1L) + 1464L;
		int i;
		if (l1 >= 0L)
			i = (int) (l1 / 1461L);
		else
			i = (int) CalendarUtils.floorDivide(l1, 1461L);
		int j = (int) (l - getFixedDate(i, 1, 1, date));
		boolean flag = CalendarUtils.isJulianLeapYear(i);
		if (l >= getFixedDate(i, 3, 1, date))
			j += flag ? 1 : 2;
		int k = 12 * j + 373;
		if (k > 0)
			k /= 367;
		else
			k = CalendarUtils.floorDivide(k, 367);
		int i1 = (int) (l - getFixedDate(i, k, 1, date)) + 1;
		int j1 = getDayOfWeekFromFixedDate(l);
		if (assertionsDisabled && j1 <= 0) {
			throw new AssertionError((new StringBuilder()).append(
					"negative day of week ").append(j1).toString());
		} else {
			date.setNormalizedYear(i);
			date.setMonth(k);
			date.setDayOfMonth(i1);
			date.setDayOfWeek(j1);
			date.setLeapYear(flag);
			date.setNormalized(true);
			return;
		}
	}

	public int getYearFromFixedDate(long l) {
		int i = (int) CalendarUtils.floorDivide(4L * (l - -1L) + 1464L, 1461L);
		return i;
	}

	public int getDayOfWeek(CalendarDate calendardate) {
		long l = getFixedDate(calendardate);
		return getDayOfWeekFromFixedDate(l);
	}

	boolean isLeapYear(int i) {
		return CalendarUtils.isJulianLeapYear(i);
	}

	private static final int BCE = 0;

	private static final int CE = 1;

	private static final Era eras[] = {
			new Era("BeforeCommonEra", "B.C.E.", -9223372036854775808L, false),
			new Era("CommonEra", "C.E.", -62135709175808L, true) };

	private static final int JULIAN_EPOCH = -1;

//MrP - JAD casuality
	static final boolean assertionsDisabled = true ; //!sun / util / calendar
//			/ JulianCalendar.desiredAssertionStatus();

}


/*
	DECOMPILATION REPORT

	Decompiled from: C:\opt\java5-ibm-2007-12-13\jre\lib\core.jar
	Total time: 32 ms
	Jad reported messages/errors:
	Exit status: 0
	Caught exceptions:
*/
