package vjo.sun.util.calendar;

/*jadclipse*/// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
//Jad home page: http://www.kpdus.com/jad.html
//Decompiler options: packimports(3) radix(10) lradix(10) 
//Source File Name:   ZoneInfo.java

import java.lang.ClassNotFoundException ;
import java.lang.IllegalArgumentException;
import java.lang.NullPointerException;

import java.util.Collection ;
import java.util.List; 
import java.util.Map ;

import java.io.IOException;

import java.util.Iterator ;

import vjo.lang.* ;
import vjo.lang.StringBuilder ;
import vjo.lang.System ;

import vjo.util.ArrayList ;
import vjo.util.Date ;
import vjo.util.SimpleTimeZone;
import vjo.util.TimeZone;

import vjo.io.ObjectInputStream;
import vjo.lang.ref.SoftReference;


//Referenced classes of package sun.util.calendar:
//         CalendarSystem, CalendarDate, ZoneInfoFile

public class ZoneInfo extends TimeZone {

	public ZoneInfo() {
		rawOffsetDiff = 0;
		willGMTOffsetChange = false;
		dirty = false;
	}

	public ZoneInfo(String s, int i) {
		this(s, i, 0, 0, null, null, null, false);
	}

	ZoneInfo(String s, int i, int j, int k, long al[], int ai[], int ai1[],
			boolean flag) {
		rawOffsetDiff = 0;
		willGMTOffsetChange = false;
		dirty = false;
		setID(s);
		rawOffset = i;
		dstSavings = j;
		checksum = k;
		transitions = al;
		offsets = ai;
		simpleTimeZoneParams = ai1;
		willGMTOffsetChange = flag;
	}

	public int getOffset(long l) {
		return getOffsets(l, null, 0);
	}

	public int getOffsets(long l, int ai[]) {
		return getOffsets(l, ai, 0);
	}

	public int getOffsetsByStandard(long l, int ai[]) {
		return getOffsets(l, ai, 1);
	}

	public int getOffsetsByWall(long l, int ai[]) {
		return getOffsets(l, ai, 2);
	}

	private int getOffsets(long l, int ai[], int i) {
		if (transitions == null) {
			int j = getLastRawOffset();
			if (ai != null) {
				ai[0] = j;
				ai[1] = 0;
			}
			return j;
		}
		l -= rawOffsetDiff;
		int k = getTransitionIndex(l, i);
		if (k < 0) {
			int i1 = getLastRawOffset();
			if (ai != null) {
				ai[0] = i1;
				ai[1] = 0;
			}
			return i1;
		}
		if (k < transitions.length) {
			long l1 = transitions[k];
			int i2 = offsets[(int) (l1 & 15L)] + rawOffsetDiff;
			if (ai != null) {
				int j2 = (int) (l1 >>> 4 & 15L);
				int k2 = j2 != 0 ? offsets[j2] : 0;
				ai[0] = i2 - k2;
				ai[1] = k2;
			}
			return i2;
		}
		SimpleTimeZone simpletimezone = getLastRule();
		if (simpletimezone != null) {
			int j1 = simpletimezone.getRawOffset();
			long l2 = l;
			if (i != 0)
				l2 -= rawOffset;
			int i3 = simpletimezone.inDaylightTime(new Date(l2)) ? simpletimezone
					.getDSTSavings()
					: 0;
			if (ai != null) {
				ai[0] = j1;
				ai[1] = i3;
			}
			return j1 + i3;
		}
		int k1 = getLastRawOffset();
		if (ai != null) {
			ai[0] = k1;
			ai[1] = 0;
		}
		return k1;
	}

	private final int getTransitionIndex(long l, int i) {
		int j = 0;
		for (int k = transitions.length - 1; j <= k;) {
			int i1 = (j + k) / 2;
			long l1 = transitions[i1];
			long l2 = l1 >> 12;
			if (i != 0)
				l2 += offsets[(int) (l1 & 15L)];
			if (i == 1) {
				int j1 = (int) (l1 >>> 4 & 15L);
				if (j1 != 0)
					l2 -= offsets[j1];
			}
			if (l2 < l)
				j = i1 + 1;
			else if (l2 > l)
				k = i1 - 1;
			else
				return i1;
		}

		if (j >= transitions.length)
			return j;
		else
			return j - 1;
	}

	public int getOffset(int i, int j, int k, int l, int i1, int j1) {
		if (j1 < 0 || j1 >= 86400000)
			throw new IllegalArgumentException();
		if (i == 0)
			j = 1 - j;
		else if (i != 1)
			throw new IllegalArgumentException();
		CalendarDate calendardate = gcal.newCalendarDate(null);
		calendardate.setDate(j, k + 1, l);
		if (!gcal.validate(calendardate))
			throw new IllegalArgumentException();
		if (i1 < 1 || i1 > 7)
			throw new IllegalArgumentException();
		if (transitions == null) {
			return getLastRawOffset();
		} else {
			long l1 = gcal.getTime(calendardate) + (long) j1;
			l1 -= rawOffset;
			return getOffsets(l1, null, 0);
		}
	}

	public synchronized void setRawOffset(int i) {
		if (i == rawOffset + rawOffsetDiff)
			return;
		rawOffsetDiff = i - rawOffset;
		if (lastRule != null)
			lastRule.setRawOffset(i);
		dirty = true;
	}

	public int getRawOffset() {
		if (!willGMTOffsetChange) {
			return rawOffset + rawOffsetDiff;
		} else {
			int ai[] = new int[2];
			getOffsets(System.currentTimeMillis(), ai, 0);
			return ai[0];
		}
	}

	public boolean isDirty() {
		return dirty;
	}

	private int getLastRawOffset() {
		return rawOffset + rawOffsetDiff;
	}

	public boolean useDaylightTime() {
		return simpleTimeZoneParams != null;
	}

	public boolean inDaylightTime(Date date) {
		if (date == null)
			throw new NullPointerException();
		if (transitions == null)
			return false;
		long l = date.getTime() - (long) rawOffsetDiff;
		int i = getTransitionIndex(l, 0);
		if (i < 0)
			return false;
		if (i < transitions.length)
			return (transitions[i] & 240L) != 0L;
		SimpleTimeZone simpletimezone = getLastRule();
		if (simpletimezone != null)
			return simpletimezone.inDaylightTime(date);
		else
			return false;
	}

	public int getDSTSavings() {
		return dstSavings;
	}

	public String toString() {
		return (new StringBuilder()).append(getClass().getName()).append(
				"[id=\"").append(getID()).append("\"").append(",offset=")
				.append(getLastRawOffset()).append(",dstSavings=").append(
						dstSavings).append(",useDaylight=").append(
						useDaylightTime()).append(",transitions=").append(
						transitions == null ? 0 : transitions.length).append(
						",lastRule=").append(
						lastRule != null ? ((Object) (lastRule))
								: ((Object) (getLastRuleInstance()))).append(
						"]").toString();
	}

	public static String[] getAvailableIDs() {
		Object obj = ZoneInfoFile.getZoneIDs();
		List list = ZoneInfoFile.getExcludedZones();
		if (list != null) {
			ArrayList arraylist = new ArrayList(((List) (obj)).size()
					+ list.size());
			arraylist.addAll(((Collection) (obj)));
			arraylist.addAll(list);
			obj = arraylist;
		}
		String as[] = new String[((List) (obj)).size()];
		return (String[]) ((List) (obj)).toArray(as);
	}

	public static String[] getAvailableIDs(int i) {
		ArrayList arraylist = new ArrayList();
		List list = ZoneInfoFile.getZoneIDs();
		int ai[] = ZoneInfoFile.getRawOffsets();
		label0: for (int j = 0; j < ai.length; j++) {
			if (ai[j] != i)
				continue;
			byte abyte0[] = ZoneInfoFile.getRawOffsetIndices();
			int k = 0;
			do {
				if (k >= abyte0.length)
					continue label0;
				if (abyte0[k] == j) {
					arraylist.add(list.get(k++));
					while (k < abyte0.length && abyte0[k] == j)
						arraylist.add(list.get(k++));
					break label0;
				}
				k++;
			} while (true);
		}

		List list1 = ZoneInfoFile.getExcludedZones();
		if (list1 != null) {
			Iterator iterator = list1.iterator();
			do {
				if (!iterator.hasNext())
					break;
				String s = (String) iterator.next();
				TimeZone timezone = getTimeZone(s);
				if (timezone != null && timezone.getRawOffset() == i)
					arraylist.add(s);
			} while (true);
		}
		String as[] = new String[arraylist.size()];
		arraylist.toArray(as);
		return as;
	}

	public static TimeZone getTimeZone(String s) {
		ZoneInfo zoneinfo;
		label0: {
			zoneinfo = null;
			zoneinfo = ZoneInfoFile.getZoneInfo(s);
			if (zoneinfo != null)
				break label0;
			try {
				Map map = getAliasTable();
				String s1 = s;
				do {
					if ((s1 = (String) map.get(s1)) == null)
						break label0;
					zoneinfo = ZoneInfoFile.getZoneInfo(s1);
				} while (zoneinfo == null);
				zoneinfo.setID(s);
				zoneinfo = ZoneInfoFile.addToCache(s, zoneinfo);
				zoneinfo = (ZoneInfo) zoneinfo.clone();
			} catch (Exception exception) {
			}
		}
		return zoneinfo;
	}

	private synchronized SimpleTimeZone getLastRule() {
		if (lastRule == null)
			lastRule = getLastRuleInstance();
		return lastRule;
	}

	public SimpleTimeZone getLastRuleInstance() {
		if (simpleTimeZoneParams == null)
			return null;
		if (simpleTimeZoneParams.length == 10)
			return new SimpleTimeZone(getLastRawOffset(), getID(),
					simpleTimeZoneParams[0], simpleTimeZoneParams[1],
					simpleTimeZoneParams[2], simpleTimeZoneParams[3],
					simpleTimeZoneParams[4], simpleTimeZoneParams[5],
					simpleTimeZoneParams[6], simpleTimeZoneParams[7],
					simpleTimeZoneParams[8], simpleTimeZoneParams[9],
					dstSavings);
		else
			return new SimpleTimeZone(getLastRawOffset(), getID(),
					simpleTimeZoneParams[0], simpleTimeZoneParams[1],
					simpleTimeZoneParams[2], simpleTimeZoneParams[3],
					simpleTimeZoneParams[4], simpleTimeZoneParams[5],
					simpleTimeZoneParams[6], simpleTimeZoneParams[7],
					dstSavings);
	}

	public Object clone() {
		ZoneInfo zoneinfo = (ZoneInfo) super.clone();
		zoneinfo.lastRule = null;
		return zoneinfo;
	}

	public int hashCode() {
		return getLastRawOffset() ^ checksum;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof ZoneInfo)) {
			return false;
		} else {
			ZoneInfo zoneinfo = (ZoneInfo) obj;
			return getID().equals(zoneinfo.getID())
					&& getLastRawOffset() == zoneinfo.getLastRawOffset()
					&& checksum == zoneinfo.checksum;
		}
	}

	public boolean hasSameRules(TimeZone timezone) {
		if (this == timezone)
			return true;
		if (timezone == null)
			return false;
		if (!(timezone instanceof ZoneInfo)) {
			if (getRawOffset() != timezone.getRawOffset())
				return false;
			return transitions == null && !useDaylightTime()
					&& !timezone.useDaylightTime();
		}
		if (getLastRawOffset() != ((ZoneInfo) timezone).getLastRawOffset())
			return false;
		else
			return checksum == ((ZoneInfo) timezone).checksum;
	}

	private static synchronized Map getAliasTable() {
		Map map = null;
		SoftReference softreference = aliasTable;
		if (softreference != null) {
			map = (Map) softreference.get();
			if (map != null)
				return map;
		}
		map = ZoneInfoFile.getZoneAliases();
		if (map != null)
			aliasTable = new SoftReference(map);
		return map;
	}

	private void readObject(ObjectInputStream objectinputstream)
			throws IOException, ClassNotFoundException {
		objectinputstream.defaultReadObject();
		dirty = true;
	}

	private static final int UTC_TIME = 0;

	private static final int STANDARD_TIME = 1;

	private static final int WALL_TIME = 2;

	private static final long OFFSET_MASK = 15L;

	private static final long DST_MASK = 240L;

	private static final int DST_NSHIFT = 4;

	private static final long ABBR_MASK = 3840L;

	private static final int TRANSITION_NSHIFT = 12;

	private static final CalendarSystem gcal = CalendarSystem
			.getGregorianCalendar();

	private int rawOffset;

	private int rawOffsetDiff;

	private int checksum;

	private int dstSavings;

	private long transitions[];

	private int offsets[];

	private int simpleTimeZoneParams[];

	private boolean willGMTOffsetChange;

	private transient boolean dirty;

	private static final long serialVersionUID = 2653134537216586139L;

	private transient SimpleTimeZone lastRule;

	private static SoftReference aliasTable;

}


/*
	DECOMPILATION REPORT

	Decompiled from: C:\opt\java5-ibm-2007-12-13\jre\lib\core.jar
	Total time: 63 ms
	Jad reported messages/errors:
	Exit status: 0
	Caught exceptions:
*/