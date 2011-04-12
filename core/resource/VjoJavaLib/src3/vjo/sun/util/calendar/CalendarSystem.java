package vjo.sun.util.calendar;

/*jadclipse*/// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
//Jad home page: http://www.kpdus.com/jad.html
//Decompiler options: packimports(3) radix(10) lradix(10) 
//Source File Name:   CalendarSystem.java

import java.util.Map ;

import vjo.lang.* ;
import vjo.lang.StringBuilder ;

import vjo.util.HashMap ;
import vjo.util.TimeZone ;

//Referenced classes of package sun.util.calendar:
//         Gregorian, CalendarDate, Era

public abstract class CalendarSystem {

	public CalendarSystem() {
	}

	private static synchronized void initNames() {
		if (names != null)
			return;
		HashMap hashmap = new HashMap();
		StringBuilder stringbuilder = new StringBuilder();
		for (int i = 0; i < namePairs.length; i += 2) {
			stringbuilder.setLength(0);
			String s = stringbuilder.append("sun.util.calendar.").append(
					namePairs[i + 1]).toString();
			hashmap.put(namePairs[i], s);
			calendars.put(s, stringbuilder.append(".Date").toString());
		}

		names = hashmap;
	}

	public static Gregorian getGregorianCalendar() {
		return GREGORIAN_INSTANCE;
	}

	public static CalendarSystem forName(String s)
 {
     if("gregorian".equals(s))
         return GREGORIAN_INSTANCE;
     CalendarSystem calendarsystem = null;
     Map map = calendars;
     JVM INSTR monitorenter ;
     calendarsystem = (CalendarSystem)calendars.get(s);
     if(calendarsystem != null)
         return calendarsystem;
     String s1;
     if(names == null)
         initNames();
     s1 = (String)names.get(s);
     if(s1 != null) goto _L2; else goto _L1
_L1:
     null;
     map;
     JVM INSTR monitorexit ;
     return;
_L2:
     if(s1.endsWith("LocalGregorianCalendar"))
         break MISSING_BLOCK_LABEL_125;
     Class class1 = Class.forName(s1);
     calendarsystem = (CalendarSystem)class1.newInstance();
     calendars.put(s, calendarsystem);
     calendarsystem;
     map;
     JVM INSTR monitorexit ;
     return;
     Exception exception;
     exception;
     throw new RuntimeException("internal error", exception);
     map;
     JVM INSTR monitorexit ;
       goto _L3
     Exception exception1;
     exception1;
     throw exception1;
_L3:
     return calendarsystem;
 }

	public abstract String getName();

	public abstract CalendarDate getCalendarDate();

	public abstract CalendarDate getCalendarDate(long l);

	public abstract CalendarDate getCalendarDate(long l,
			CalendarDate calendardate);

	public abstract CalendarDate getCalendarDate(long l, TimeZone timezone);

	public abstract CalendarDate newCalendarDate();

	public abstract CalendarDate newCalendarDate(TimeZone timezone);

	public abstract long getTime(CalendarDate calendardate);

	public abstract int getYearLength(CalendarDate calendardate);

	public abstract int getYearLengthInMonths(CalendarDate calendardate);

	public abstract int getMonthLength(CalendarDate calendardate);

	public abstract int getWeekLength();

	public abstract Era getEra(String s);

	public abstract Era[] getEras();

	public abstract void setEra(CalendarDate calendardate, String s);

	public abstract CalendarDate getNthDayOfWeek(int i, int j,
			CalendarDate calendardate);

	public abstract CalendarDate setTimeOfDay(CalendarDate calendardate, int i);

	public abstract boolean validate(CalendarDate calendardate);

	public abstract boolean normalize(CalendarDate calendardate);

	static Map names;

	static Map calendars = new HashMap();

	private static final String pkg = "sun.util.calendar.";

	private static final String namePairs[] = { "gregorian", "Gregorian",
			"julian", "JulianCalendar" };

	private static final Gregorian GREGORIAN_INSTANCE = new Gregorian();

}


/*
	DECOMPILATION REPORT

	Decompiled from: C:\opt\java5-ibm-2007-12-13\jre\lib\core.jar
	Total time: 16 ms
	Jad reported messages/errors:
Overlapped try statements detected. Not all exception handlers will be resolved in the method forName
Couldn't fully decompile method forName
Couldn't resolve all exception handlers in method forName
	Exit status: 0
	Caught exceptions:
*/