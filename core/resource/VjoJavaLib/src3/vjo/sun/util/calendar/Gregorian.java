package vjo.sun.util.calendar;

/*jadclipse*/// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
//Jad home page: http://www.kpdus.com/jad.html
//Decompiler options: packimports(3) radix(10) lradix(10) 
//Source File Name:   Gregorian.java

import vjo.lang.* ;

import vjo.util.TimeZone;

//Referenced classes of package sun.util.calendar:
//         BaseCalendar, CalendarDate

public class Gregorian extends BaseCalendar {
	static class Date extends BaseCalendar.Date {

		public int getNormalizedYear() {
			return getYear();
		}

		public void setNormalizedYear(int i) {
			setYear(i);
		}

		protected Date() {
		}

		protected Date(TimeZone timezone) {
			super(timezone);
		}
	}

	Gregorian() {
	}

	public String getName() {
		return "gregorian";
	}

	public CalendarDate newCalendarDate() {
		return new Date();
	}

	public CalendarDate newCalendarDate(TimeZone timezone) {
		return new Date(timezone);
	}
}


/*
	DECOMPILATION REPORT

	Decompiled from: C:\opt\java5-ibm-2007-12-13\jre\lib\core.jar
	Total time: 16 ms
	Jad reported messages/errors:
	Exit status: 0
	Caught exceptions:
*/
