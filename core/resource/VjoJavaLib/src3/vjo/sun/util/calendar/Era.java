package vjo.sun.util.calendar;

/*jadclipse*/// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
//Jad home page: http://www.kpdus.com/jad.html
//Decompiler options: packimports(3) radix(10) lradix(10) 
//Source File Name:   Era.java

import vjo.lang.* ;
import vjo.lang.StringBuilder ;

import vjo.util.Locale;
import vjo.util.TimeZone;

//Referenced classes of package sun.util.calendar:
//         CalendarDate, CalendarSystem, Gregorian

public class Era {

	protected Era(String s, String s1, long l, boolean flag) {
		hash = 0;
		name = s;
		abbr = s1;
		since = l;
		localTime = flag;
		Gregorian gregorian = CalendarSystem.getGregorianCalendar();
		sinceDate = gregorian.newCalendarDate(null);
		gregorian.getCalendarDate(l, sinceDate);
	}

	public String getName() {
		return name;
	}

	public String getDisplayName(Locale locale) {
		return name;
	}

	public String getAbbreviation() {
		return abbr;
	}

	public String getDiaplayAbbreviation(Locale locale) {
		return abbr;
	}

	public long getSince(TimeZone timezone) {
		if (timezone == null || !localTime) {
			return since;
		} else {
			int i = timezone.getOffset(since);
			return since + (long) i;
		}
	}

	public CalendarDate getSinceDate() {
		return (CalendarDate) sinceDate.clone();
	}

	public boolean isLocalTime() {
		return localTime;
	}

	public boolean equals(Object obj) {
		if (!(obj instanceof Era)) {
			return false;
		} else {
			Era era = (Era) obj;
			return name.equals(era.name) && abbr.equals(era.abbr)
					&& since == era.since && localTime == era.localTime;
		}
	}

	public int hashCode() {
		if (hash == 0)
			hash = name.hashCode() ^ abbr.hashCode() ^ (int) since
					^ (int) (since >> 32) ^ (localTime ? 1 : 0);
		return hash;
	}

	public String toString() {
		StringBuilder stringbuilder = new StringBuilder();
		stringbuilder.append('[');
		stringbuilder.append(getName()).append(" (");
		stringbuilder.append(getAbbreviation()).append(')');
		stringbuilder.append(" since ").append(getSinceDate());
		if (localTime) {
			stringbuilder.setLength(stringbuilder.length() - 1);
			stringbuilder.append(" local time");
		}
		stringbuilder.append(']');
		return stringbuilder.toString();
	}

	private final String name;

	private final String abbr;

	private final long since;

	private final CalendarDate sinceDate;

	private final boolean localTime;

	private int hash;
}


/*
	DECOMPILATION REPORT

	Decompiled from: C:\opt\java5-ibm-2007-12-13\jre\lib\core.jar
	Total time: 15 ms
	Jad reported messages/errors:
	Exit status: 0
	Caught exceptions:
*/
