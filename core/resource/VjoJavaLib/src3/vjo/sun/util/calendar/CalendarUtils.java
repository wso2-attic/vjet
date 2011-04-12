package vjo.sun.util.calendar;

/*jadclipse*/// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
//Jad home page: http://www.kpdus.com/jad.html
//Decompiler options: packimports(3) radix(10) lradix(10) 
//Source File Name:   CalendarUtils.java

import vjo.lang.* ;
import vjo.lang.StringBuffer ;
import vjo.lang.StringBuilder ;

public class CalendarUtils {

	public CalendarUtils() {
	}

	public static final boolean isGregorianLeapYear(int i) {
		return i % 4 == 0 && (i % 100 != 0 || i % 400 == 0);
	}

	public static final boolean isJulianLeapYear(int i) {
		return i % 4 == 0;
	}

	public static final long floorDivide(long l, long l1) {
		return l < 0L ? (l + 1L) / l1 - 1L : l / l1;
	}

	public static final int floorDivide(int i, int j) {
		return i < 0 ? (i + 1) / j - 1 : i / j;
	}

	public static final int floorDivide(int i, int j, int ai[]) {
		if (i >= 0) {
			ai[0] = i % j;
			return i / j;
		} else {
			int k = (i + 1) / j - 1;
			ai[0] = i - k * j;
			return k;
		}
	}

	public static final int floorDivide(long l, int i, int ai[]) {
		if (l >= 0L) {
			ai[0] = (int) (l % (long) i);
			return (int) (l / (long) i);
		} else {
			int j = (int) ((l + 1L) / (long) i - 1L);
			ai[0] = (int) (l - (long) (j * i));
			return j;
		}
	}

	public static final long mod(long l, long l1) {
		return l - l1 * floorDivide(l, l1);
	}

	public static final int mod(int i, int j) {
		return i - j * floorDivide(i, j);
	}

	public static final int amod(int i, int j) {
		int k = mod(i, j);
		return k != 0 ? k : j;
	}

	public static final long amod(long l, long l1) {
		long l2 = mod(l, l1);
		return l2 != 0L ? l2 : l1;
	}

	public static final StringBuilder sprintf0d(StringBuilder stringbuilder,
			int i, int j) {
		long l = i;
		if (l < 0L) {
			stringbuilder.append('-');
			l = -l;
			j--;
		}
		int k = 10;
		for (int i1 = 2; i1 < j; i1++)
			k *= 10;

		for (int j1 = 1; j1 < j && l < (long) k; j1++) {
			stringbuilder.append('0');
			k /= 10;
		}

		stringbuilder.append(l);
		return stringbuilder;
	}

	public static final StringBuffer sprintf0d(StringBuffer stringbuffer,
			int i, int j) {
		long l = i;
		if (l < 0L) {
			stringbuffer.append('-');
			l = -l;
			j--;
		}
		int k = 10;
		for (int i1 = 2; i1 < j; i1++)
			k *= 10;

		for (int j1 = 1; j1 < j && l < (long) k; j1++) {
			stringbuffer.append('0');
			k /= 10;
		}

		stringbuffer.append(l);
		return stringbuffer;
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
