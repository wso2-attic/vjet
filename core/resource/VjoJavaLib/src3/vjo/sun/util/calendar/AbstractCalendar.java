package vjo.sun.util.calendar;

/*jadclipse*/// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
//Jad home page: http://www.kpdus.com/jad.html
//Decompiler options: packimports(3) radix(10) lradix(10) 
//Source File Name:   AbstractCalendar.java

import java.lang.IllegalArgumentException;

import vjo.lang.* ;
import vjo.lang.StringBuilder;
import vjo.lang.System ;

import vjo.util.TimeZone;

//Referenced classes of package sun.util.calendar:
//         CalendarSystem, Era, ZoneInfo, CalendarDate, 
//         CalendarUtils

public abstract class AbstractCalendar extends CalendarSystem {

	protected AbstractCalendar() {
	}

	public Era getEra(String s) {
		if (eras != null) {
			for (int i = 0; i < eras.length; i++)
				if (eras[i].equals(s))
					return eras[i];

		}
		return null;
	}

	public Era[] getEras() {
		Era aera[] = null;
		if (eras != null) {
			aera = new Era[eras.length];
			System.arraycopy(eras, 0, aera, 0, eras.length);
		}
		return aera;
	}

	public void setEra(CalendarDate calendardate, String s) {
		if (eras == null)
			return;
		for (int i = 0; i < eras.length; i++) {
			Era era = eras[i];
			if (era != null && era.getName().equals(s)) {
				calendardate.setEra(era);
				return;
			}
		}

		throw new IllegalArgumentException((new StringBuilder()).append(
				"unknown era name: ").append(s).toString());
	}

	protected void setEras(Era aera[]) {
		eras = aera;
	}

	public CalendarDate getCalendarDate() {
		return getCalendarDate(System.currentTimeMillis(), newCalendarDate());
	}

	public CalendarDate getCalendarDate(long l) {
		return getCalendarDate(l, newCalendarDate());
	}

	public CalendarDate getCalendarDate(long l, TimeZone timezone) {
		CalendarDate calendardate = newCalendarDate(timezone);
		return getCalendarDate(l, calendardate);
	}

	public CalendarDate getCalendarDate(long l, CalendarDate calendardate) {
		int i = 0;
		int j = 0;
		int k = 0;
		long l1 = 0L;
		TimeZone timezone = calendardate.getZone();
		if (timezone != null) {
			int ai[] = new int[2];
			if (timezone instanceof ZoneInfo) {
				j = ((ZoneInfo) timezone).getOffsets(l, ai);
			} else {
				j = timezone.getOffset(l);
				ai[0] = timezone.getRawOffset();
				ai[1] = j - ai[0];
			}
			l1 = j / 86400000;
			i = j % 86400000;
			k = ai[1];
		}
		calendardate.setZoneOffset(j);
		calendardate.setDaylightSaving(k);
		l1 += l / 86400000L;
		i += (int) (l % 86400000L);
		if (i >= 86400000) {
			i -= 86400000;
			l1++;
		} else {
			while (i < 0) {
				i += 86400000;
				l1--;
			}
		}
		l1 += 719163L;
		getCalendarDateFromFixedDate(calendardate, l1);
		setTimeOfDay(calendardate, i);
		calendardate.setLeapYear(isLeapYear(calendardate));
		calendardate.setNormalized(true);
		return calendardate;
	}

	public long getTime(CalendarDate calendardate) {
		long l = getFixedDate(calendardate);
		long l1 = (l - 719163L) * 86400000L + getTimeOfDay(calendardate);
		int i = 0;
		TimeZone timezone = calendardate.getZone();
		if (timezone != null) {
			if (calendardate.isNormalized())
				return l1 - (long) calendardate.getZoneOffset();
			int ai[] = new int[2];
			if (calendardate.isStandardTime()) {
				if (timezone instanceof ZoneInfo) {
					((ZoneInfo) timezone).getOffsetsByStandard(l1, ai);
					i = ai[0];
				} else {
					i = timezone.getOffset(l1 - (long) timezone.getRawOffset());
				}
			} else if (timezone instanceof ZoneInfo)
				i = ((ZoneInfo) timezone).getOffsetsByWall(l1, ai);
			else
				i = timezone.getOffset(l1 - (long) timezone.getRawOffset());
		}
		l1 -= i;
		getCalendarDate(l1, calendardate);
		return l1;
	}

	protected long getTimeOfDay(CalendarDate calendardate) {
		long l = calendardate.getTimeOfDay();
		if (l != -9223372036854775808L) {
			return l;
		} else {
			long l1 = getTimeOfDayValue(calendardate);
			calendardate.setTimeOfDay(l1);
			return l1;
		}
	}

	public long getTimeOfDayValue(CalendarDate calendardate) {
		long l = calendardate.getHours();
		l *= 60L;
		l += calendardate.getMinutes();
		l *= 60L;
		l += calendardate.getSeconds();
		l *= 1000L;
		l += calendardate.getMillis();
		return l;
	}

	public CalendarDate setTimeOfDay(CalendarDate calendardate, int i) {
		if (i < 0)
			throw new IllegalArgumentException();
		boolean flag = calendardate.isNormalized();
		int j = i;
		int k = j / 3600000;
		j %= 3600000;
		int l = j / 60000;
		j %= 60000;
		int i1 = j / 1000;
		j %= 1000;
		calendardate.setHours(k);
		calendardate.setMinutes(l);
		calendardate.setSeconds(i1);
		calendardate.setMillis(j);
		calendardate.setTimeOfDay(i);
		if (k < 24 && flag)
			calendardate.setNormalized(flag);
		return calendardate;
	}

	public int getWeekLength() {
		return 7;
	}

	protected abstract boolean isLeapYear(CalendarDate calendardate);

	public CalendarDate getNthDayOfWeek(int i, int j, CalendarDate calendardate) {
		CalendarDate calendardate1 = (CalendarDate) calendardate.clone();
		normalize(calendardate1);
		long l = getFixedDate(calendardate1);
		long l1;
		if (i > 0)
			l1 = (long) (7 * i) + getDayOfWeekDateBefore(l, j);
		else
			l1 = (long) (7 * i) + getDayOfWeekDateAfter(l, j);
		getCalendarDateFromFixedDate(calendardate1, l1);
		return calendardate1;
	}

	static long getDayOfWeekDateBefore(long l, int i) {
		return getDayOfWeekDateOnOrBefore(l - 1L, i);
	}

	static long getDayOfWeekDateAfter(long l, int i) {
		return getDayOfWeekDateOnOrBefore(l + 7L, i);
	}

	public static long getDayOfWeekDateOnOrBefore(long l, int i) {
		long l1 = l - (long) (i - 1);
		if (l1 >= 0L)
			return l - l1 % 7L;
		else
			return l - CalendarUtils.mod(l1, 7L);
	}

	protected abstract long getFixedDate(CalendarDate calendardate);

	protected abstract void getCalendarDateFromFixedDate(
			CalendarDate calendardate, long l);

	public boolean validateTime(CalendarDate calendardate) {
		int i = calendardate.getHours();
		if (i < 0 || i >= 24)
			return false;
		i = calendardate.getMinutes();
		if (i < 0 || i >= 60)
			return false;
		i = calendardate.getSeconds();
		if (i < 0 || i >= 60)
			return false;
		i = calendardate.getMillis();
		return i >= 0 && i < 1000;
	}

	int normalizeTime(CalendarDate calendardate) {
		long l = getTimeOfDay(calendardate);
		long l1 = 0L;
		if (l >= 86400000L) {
			l1 = l / 86400000L;
			l %= 86400000L;
		} else if (l < 0L) {
			l1 = CalendarUtils.floorDivide(l, 86400000L);
			if (l1 != 0L)
				l -= 86400000L * l1;
		}
		if (l1 != 0L)
			calendardate.setTimeOfDay(l);
		calendardate.setMillis((int) (l % 1000L));
		l /= 1000L;
		calendardate.setSeconds((int) (l % 60L));
		l /= 60L;
		calendardate.setMinutes((int) (l % 60L));
		calendardate.setHours((int) (l / 60L));
		return (int) l1;
	}

	static final int SECOND_IN_MILLIS = 1000;

	static final int MINUTE_IN_MILLIS = 60000;

	static final int HOUR_IN_MILLIS = 3600000;

	static final int DAY_IN_MILLIS = 86400000;

	static final int EPOCH_OFFSET = 719163;

	private Era eras[];
}


/*
	DECOMPILATION REPORT

	Decompiled from: C:\opt\java5-ibm-2007-12-13\jre\lib\core.jar
	Total time: 16 ms
	Jad reported messages/errors:
	Exit status: 0
	Caught exceptions:
*/