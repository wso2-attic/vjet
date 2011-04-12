package vjo.sun.util.calendar;

/*jadclipse*/// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
//Jad home page: http://www.kpdus.com/jad.html
//Decompiler options: packimports(3) radix(10) lradix(10) 
//Source File Name:   BaseCalendar.java

import java.lang.AssertionError;
import java.lang.IllegalArgumentException;

import vjo.lang.* ;
import vjo.lang.StringBuilder;

import vjo.util.TimeZone;

//Referenced classes of package sun.util.calendar:
//         AbstractCalendar, CalendarDate, CalendarUtils

public abstract class BaseCalendar extends AbstractCalendar {
	public static abstract class Date extends CalendarDate {

		public Date setNormalizedDate(int i, int j, int k) {
			setNormalizedYear(i);
			setMonth(j).setDayOfMonth(k);
			return this;
		}

		public abstract int getNormalizedYear();

		public abstract void setNormalizedYear(int i);

		protected final boolean hit(int i) {
			return i == cachedYear;
		}

		protected final boolean hit(long l) {
			return l >= cachedFixedDateJan1 && l < cachedFixedDateNextJan1;
		}

		protected int getCachedYear() {
			return cachedYear;
		}

		protected long getCachedJan1() {
			return cachedFixedDateJan1;
		}

		protected void setCache(int i, long l, int j) {
			cachedYear = i;
			cachedFixedDateJan1 = l;
			cachedFixedDateNextJan1 = l + (long) j;
		}

		int cachedYear;

		long cachedFixedDateJan1;

		long cachedFixedDateNextJan1;

		protected Date() {
			cachedYear = 2004;
			cachedFixedDateJan1 = 731581L;
			cachedFixedDateNextJan1 = cachedFixedDateJan1 + 366L;
		}

		protected Date(TimeZone timezone) {
			super(timezone);
			cachedYear = 2004;
			cachedFixedDateJan1 = 731581L;
			cachedFixedDateNextJan1 = cachedFixedDateJan1 + 366L;
		}
	}

	public BaseCalendar() {
	}

	public boolean validate(CalendarDate calendardate) {
		Date date = (Date) calendardate;
		if (date.isNormalized())
			return true;
		int i = date.getMonth();
		if (i < 1 || i > 12)
			return false;
		int j = date.getDayOfMonth();
		if (j <= 0 || j > getMonthLength(date.getNormalizedYear(), i))
			return false;
		int k = date.getDayOfWeek();
		Date _tmp = date;
		if (k != -2147483648 && k != getDayOfWeek(date))
			return false;
		if (!validateTime(calendardate)) {
			return false;
		} else {
			date.setNormalized(true);
			return true;
		}
	}

	public boolean normalize(CalendarDate calendardate) {
		if (calendardate.isNormalized())
			return true;
		Date date = (Date) calendardate;
		TimeZone timezone = date.getZone();
		if (timezone != null) {
			getTime(calendardate);
			return true;
		}
		int i = normalizeTime(date);
		normalizeMonth(date);
		long l = (long) date.getDayOfMonth() + (long) i;
		int j = date.getMonth();
		int k = date.getNormalizedYear();
		int i1 = getMonthLength(k, j);
		if (l <= 0L || l > (long) i1) {
			if (l <= 0L && l > -28L) {
				i1 = getMonthLength(k, --j);
				l += i1;
				date.setDayOfMonth((int) l);
				if (j == 0) {
					j = 12;
					date.setNormalizedYear(k - 1);
				}
				date.setMonth(j);
			} else if (l > (long) i1 && l < (long) (i1 + 28)) {
				l -= i1;
				j++;
				date.setDayOfMonth((int) l);
				if (j > 12) {
					date.setNormalizedYear(k + 1);
					j = 1;
				}
				date.setMonth(j);
			} else {
				long l1 = (l + getFixedDate(k, j, 1, date)) - 1L;
				getCalendarDateFromFixedDate(date, l1);
			}
		} else {
			date.setDayOfWeek(getDayOfWeek(date));
		}
		calendardate.setLeapYear(isLeapYear(date.getNormalizedYear()));
		calendardate.setZoneOffset(0);
		calendardate.setDaylightSaving(0);
		date.setNormalized(true);
		return true;
	}

	void normalizeMonth(CalendarDate calendardate) {
		Date date = (Date) calendardate;
		int i = date.getNormalizedYear();
		long l = date.getMonth();
		if (l <= 0L) {
			long l1 = 1L - l;
			i -= (int) (l1 / 12L + 1L);
			l = 13L - l1 % 12L;
			date.setNormalizedYear(i);
			date.setMonth((int) l);
		} else if (l > 12L) {
			i += (int) ((l - 1L) / 12L);
			l = (l - 1L) % 12L + 1L;
			date.setNormalizedYear(i);
			date.setMonth((int) l);
		}
	}

	public int getYearLength(CalendarDate calendardate) {
		return isLeapYear(((Date) calendardate).getNormalizedYear()) ? 366
				: '\u016D';
	}

	public int getYearLengthInMonths(CalendarDate calendardate) {
		return 12;
	}

	public int getMonthLength(CalendarDate calendardate) {
		Date date = (Date) calendardate;
		int i = date.getMonth();
		if (i < 1 || i > 12)
			throw new IllegalArgumentException((new StringBuilder()).append(
					"Illegal month value: ").append(i).toString());
		else
			return getMonthLength(date.getNormalizedYear(), i);
	}

	private final int getMonthLength(int i, int j) {
		int k = DAYS_IN_MONTH[j];
		if (j == 2 && isLeapYear(i))
			k++;
		return k;
	}

	public long getDayOfYear(CalendarDate calendardate) {
		return getDayOfYear(((Date) calendardate).getNormalizedYear(),
				calendardate.getMonth(), calendardate.getDayOfMonth());
	}

	final long getDayOfYear(int i, int j, int k) {
		return (long) k
				+ (long) (isLeapYear(i) ? ACCUMULATED_DAYS_IN_MONTH_LEAP[j]
						: ACCUMULATED_DAYS_IN_MONTH[j]);
	}

	public long getFixedDate(CalendarDate calendardate) {
		if (!calendardate.isNormalized())
			normalizeMonth(calendardate);
		return getFixedDate(((Date) calendardate).getNormalizedYear(),
				calendardate.getMonth(), calendardate.getDayOfMonth(),
				(Date) calendardate);
	}

	public long getFixedDate(int i, int j, int k, Date date) {
		boolean flag = j == 1 && k == 1;
		if (date != null && date.hit(i))
			if (flag)
				return date.getCachedJan1();
			else
				return (date.getCachedJan1() + getDayOfYear(i, j, k)) - 1L;
		int l = i - 1970;
		if (l >= 0 && l < FIXED_DATES.length) {
			long l1 = FIXED_DATES[l];
			if (date != null)
				date.setCache(i, l1, isLeapYear(i) ? 366 : 365);
			return flag ? l1 : (l1 + getDayOfYear(i, j, k)) - 1L;
		}
		long l2 = (long) i - 1L;
		long l3 = k;
		if (l2 >= 0L)
			l3 += ((365L * l2 + l2 / 4L) - l2 / 100L) + l2 / 400L
					+ (long) ((367 * j - 362) / 12);
		else
			l3 += ((365L * l2 + CalendarUtils.floorDivide(l2, 4L)) - CalendarUtils
					.floorDivide(l2, 100L))
					+ CalendarUtils.floorDivide(l2, 400L)
					+ (long) CalendarUtils.floorDivide(367 * j - 362, 12);
		if (j > 2)
			l3 -= isLeapYear(i) ? 1L : 2L;
		if (date != null && flag)
			date.setCache(i, l3, isLeapYear(i) ? 366 : 365);
		return l3;
	}

	public void getCalendarDateFromFixedDate(CalendarDate calendardate, long l) {
		Date date = (Date) calendardate;
		int i;
		long l1;
		boolean flag;
		if (date.hit(l)) {
			i = date.getCachedYear();
			l1 = date.getCachedJan1();
			flag = isLeapYear(i);
		} else {
			i = getGregorianYearFromFixedDate(l);
			l1 = getFixedDate(i, 1, 1, null);
			flag = isLeapYear(i);
			date.setCache(i, l1, flag ? 366 : 365);
		}
		int j = (int) (l - l1);
		long l2 = l1 + 31L + 28L;
		if (flag)
			l2++;
		if (l >= l2)
			j += flag ? 1 : 2;
		int k = 12 * j + 373;
		if (k > 0)
			k /= 367;
		else
			k = CalendarUtils.floorDivide(k, 367);
		long l3 = l1 + (long) ACCUMULATED_DAYS_IN_MONTH[k];
		if (flag && k >= 3)
			l3++;
		int i1 = (int) (l - l3) + 1;
		int j1 = getDayOfWeekFromFixedDate(l);
		if (assertionDisabled && j1 <= 0) {
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

	public int getDayOfWeek(CalendarDate calendardate) {
		long l = getFixedDate(calendardate);
		return getDayOfWeekFromFixedDate(l);
	}

	public static final int getDayOfWeekFromFixedDate(long l) {
		if (l >= 0L)
			return (int) (l % 7L) + 1;
		else
			return (int) CalendarUtils.mod(l, 7L) + 1;
	}

	public int getYearFromFixedDate(long l) {
		return getGregorianYearFromFixedDate(l);
	}

	final int getGregorianYearFromFixedDate(long l) {
		int k2;
		int i3;
		int j3;
		int k3;
		if (l > 0L) {
			long l1 = l - 1L;
			k2 = (int) (l1 / 146097L);
			int i = (int) (l1 % 146097L);
			i3 = i / 36524;
			int k = i % 36524;
			j3 = k / 1461;
			int j1 = k % 1461;
			k3 = j1 / 365;
			int i2 = j1 % 365 + 1;
		} else {
			long l2 = l - 1L;
			k2 = (int) CalendarUtils.floorDivide(l2, 146097L);
			int j = (int) CalendarUtils.mod(l2, 146097L);
			i3 = CalendarUtils.floorDivide(j, 36524);
			int i1 = CalendarUtils.mod(j, 36524);
			j3 = CalendarUtils.floorDivide(i1, 1461);
			int k1 = CalendarUtils.mod(i1, 1461);
			k3 = CalendarUtils.floorDivide(k1, 365);
			int j2 = CalendarUtils.mod(k1, 365) + 1;
		}
		int l3 = 400 * k2 + 100 * i3 + 4 * j3 + k3;
		if (i3 != 4 && k3 != 4)
			l3++;
		return l3;
	}

	protected boolean isLeapYear(CalendarDate calendardate) {
		return isLeapYear(((Date) calendardate).getNormalizedYear());
	}

	boolean isLeapYear(int i) {
		return CalendarUtils.isGregorianLeapYear(i);
	}

	public static final int JANUARY = 1;

	public static final int FEBRUARY = 2;

	public static final int MARCH = 3;

	public static final int APRIL = 4;

	public static final int MAY = 5;

	public static final int JUNE = 6;

	public static final int JULY = 7;

	public static final int AUGUST = 8;

	public static final int SEPTEMBER = 9;

	public static final int OCTOBER = 10;

	public static final int NOVEMBER = 11;

	public static final int DECEMBER = 12;

	public static final int SUNDAY = 1;

	public static final int MONDAY = 2;

	public static final int TUESDAY = 3;

	public static final int WEDNESDAY = 4;

	public static final int THURSDAY = 5;

	public static final int FRIDAY = 6;

	public static final int SATURDAY = 7;

	private static final int BASE_YEAR = 1970;

	private static final int FIXED_DATES[] = { 719163, 719528, 719893, 720259,
			720624, 720989, 721354, 721720, 722085, 722450, 722815, 723181,
			723546, 723911, 724276, 724642, 725007, 725372, 725737, 726103,
			726468, 726833, 727198, 727564, 727929, 728294, 728659, 729025,
			729390, 729755, 730120, 730486, 730851, 731216, 731581, 731947,
			732312, 732677, 733042, 733408, 733773, 734138, 734503, 734869,
			735234, 735599, 735964, 736330, 736695, 737060, 737425, 737791,
			738156, 738521, 738886, 739252, 739617, 739982, 740347, 740713,
			741078, 741443, 741808, 742174, 742539, 742904, 743269, 743635,
			744000, 744365 };

	static final int DAYS_IN_MONTH[] = { 31, 31, 28, 31, 30, 31, 30, 31, 31,
			30, 31, 30, 31 };

	static final int ACCUMULATED_DAYS_IN_MONTH[] = { -30, 0, 31, 59, 90, 120,
			151, 181, 212, 243, 273, 304, 334 };

	static final int ACCUMULATED_DAYS_IN_MONTH_LEAP[] = { -30, 0, 31, 60, 91,
			121, 152, 182, 213, 244, 274, 305, 335 };

//MrP - JAD casuality
//	static final boolean $assertionsDisabled = !sun / util / calendar
//			/ BaseCalendar.desiredAssertionStatus();
//MrP what I think...
	static final boolean assertionDisabled = true ;

}


/*
	DECOMPILATION REPORT

	Decompiled from: C:\opt\java5-ibm-2007-12-13\jre\lib\core.jar
	Total time: 32 ms
	Jad reported messages/errors:
	Exit status: 0
	Caught exceptions:
*/
