package vjo.sun.util.calendar;

/*jadclipse*/// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
//Jad home page: http://www.kpdus.com/jad.html
//Decompiler options: packimports(3) radix(10) lradix(10) 
//Source File Name:   ZoneInfoFile.java
import java.lang.ArrayIndexOutOfBoundsException;

import java.util.List; 
import java.util.Map ;

import java.io.FileNotFoundException;
import java.io.IOException ;

import vjo.lang.* ;
import vjo.lang.StringBuilder;
import vjo.lang.System ;

import vjo.util.ArrayList; 
import vjo.util.HashMap; 

import vjo.io.File ;
import vjo.io.InputStream ;
import vjo.io.FileInputStream ;

import vjo.lang.ref.SoftReference;

import vjo.security.*;


//Referenced classes of package sun.util.calendar:
//         ZoneInfo

public class ZoneInfoFile {

	public ZoneInfoFile() {
	}

	public static String getFileName(String s) {
		if (File.separatorChar == '/')
			return s;
		else
			return s.replace('/', File.separatorChar);
	}

	public static ZoneInfo getCustomTimeZone(String s, int i) {
		String s1 = toCustomID(i);
		ZoneInfo zoneinfo = getFromCache(s1);
		if (zoneinfo == null) {
			zoneinfo = new ZoneInfo(s1, i);
			zoneinfo = addToCache(s1, zoneinfo);
			if (!s1.equals(s))
				zoneinfo = addToCache(s, zoneinfo);
		}
		return (ZoneInfo) zoneinfo.clone();
	}

	public static String toCustomID(int i) {
		int j = i / 60000;
		char c;
		if (j >= 0) {
			c = '+';
		} else {
			c = '-';
			j = -j;
		}
		int k = j / 60;
		int l = j % 60;
		char ac[] = { 'G', 'M', 'T', c, '0', '0', ':', '0', '0' };
		if (k >= 10)
			ac[4] += k / 10;
		ac[5] += k % 10;
		if (l != 0) {
			ac[7] += l / 10;
			ac[8] += l % 10;
		}
		return new String(ac);
	}

	public static ZoneInfo getZoneInfo(String s) {
		ZoneInfo zoneinfo = getFromCache(s);
		if (zoneinfo == null) {
			zoneinfo = createZoneInfo(s);
			if (zoneinfo == null)
				return null;
			zoneinfo = addToCache(s, zoneinfo);
		}
		return (ZoneInfo) zoneinfo.clone();
	}

	static synchronized ZoneInfo getFromCache(String s) {
		if (zoneInfoObjects == null)
			return null;
		else
			return (ZoneInfo) zoneInfoObjects.get(s);
	}

	static synchronized ZoneInfo addToCache(String s, ZoneInfo zoneinfo) {
		if (zoneInfoObjects == null) {
			zoneInfoObjects = new HashMap();
		} else {
			ZoneInfo zoneinfo1 = (ZoneInfo) zoneInfoObjects.get(s);
			if (zoneinfo1 != null)
				return zoneinfo1;
		}
		zoneInfoObjects.put(s, zoneinfo);
		return zoneinfo;
	}

	private static ZoneInfo createZoneInfo(String ID)
 {
		byte[] buf = readZoneInfoFile(getFileName(ID));
		if (buf == null) {
			return null;
		}

		int index;
		for (index = 0; index < JAVAZI_LABEL.length; index++) {
			if (buf[index] != JAVAZI_LABEL[index]) {
				System.err.println("ZoneInfo: wrong magic number: " + ID);
				return null;
			}
		}

		if (buf[index++] > JAVAZI_VERSION) {
			System.err.println("ZoneInfo: incompatible version ("
					+ buf[index - 1] + "): " + ID);
			return null;
		}

		int filesize = buf.length;
		int rawOffset = 0;
		int dstSavings = 0;
		int checksum = 0;
		boolean willGMTOffsetChange = false;
		long[] transitions = null;
		int[] offsets = null;
		int[] simpleTimeZoneParams = null;

		try {
			while (index < filesize) {
				byte tag = buf[index++];
				int len = ((buf[index++] & 0xFF) << 8) + (buf[index++] & 0xFF);

				if (filesize < index + len) {
					break;
				}

				switch (tag) {
				case TAG_CRC32: {
					int val = buf[index++] & 0xff;
					val = (val << 8) + (buf[index++] & 0xff);
					val = (val << 8) + (buf[index++] & 0xff);
					val = (val << 8) + (buf[index++] & 0xff);
					checksum = val;
				}
					break;

				case TAG_LastDSTSaving: {
					short val = (short) (buf[index++] & 0xff);
					val = (short) ((val << 8) + (buf[index++] & 0xff));
					dstSavings = val * 1000;
				}
					break;

				case TAG_RawOffset: {
					int val = buf[index++] & 0xff;
					val = (val << 8) + (buf[index++] & 0xff);
					val = (val << 8) + (buf[index++] & 0xff);
					val = (val << 8) + (buf[index++] & 0xff);
					rawOffset = val;
				}
					break;

				case TAG_Transition: {
					int n = len / 8;
					transitions = new long[n];
					for (int i = 0; i < n; i++) {
						long val = buf[index++] & 0xff;
						val = (val << 8) + (buf[index++] & 0xff);
						val = (val << 8) + (buf[index++] & 0xff);
						val = (val << 8) + (buf[index++] & 0xff);
						val = (val << 8) + (buf[index++] & 0xff);
						val = (val << 8) + (buf[index++] & 0xff);
						val = (val << 8) + (buf[index++] & 0xff);
						val = (val << 8) + (buf[index++] & 0xff);
						transitions[i] = val;
					}
				}
					break;

				case TAG_Offset: {
					int n = len / 4;
					offsets = new int[n];
					for (int i = 0; i < n; i++) {
						int val = buf[index++] & 0xff;
						val = (val << 8) + (buf[index++] & 0xff);
						val = (val << 8) + (buf[index++] & 0xff);
						val = (val << 8) + (buf[index++] & 0xff);
						offsets[i] = val;
					}
				}
					break;

				case TAG_SimpleTimeZone: {
					if (len != 32 && len != 40) {
						System.err
								.println("ZoneInfo: wrong SimpleTimeZone parameter size");
						return null;
					}
					int n = len / 4;
					simpleTimeZoneParams = new int[n];
					for (int i = 0; i < n; i++) {
						int val = buf[index++] & 0xff;
						val = (val << 8) + (buf[index++] & 0xff);
						val = (val << 8) + (buf[index++] & 0xff);
						val = (val << 8) + (buf[index++] & 0xff);
						simpleTimeZoneParams[i] = val;
					}
				}
					break;

				case TAG_GMTOffsetWillChange: {
					if (len != 1) {
						System.err
								.println("ZoneInfo: wrong byte length for TAG_GMTOffsetWillChange");
					}
					willGMTOffsetChange = buf[index++] == 1;
				}
					break;

				default:
					System.err.println("ZoneInfo: unknown tag < " + tag
							+ ">. ignored.");
					index += len;
					break;
				}
			}
		} catch (Exception e) {
			System.err.println("ZoneInfo: corrupted zoneinfo file: " + ID);
			return null;
		}

		if (index != filesize) {
			System.err.println("ZoneInfo: wrong file size: " + ID);
			return null;
		}

		return new ZoneInfo(ID, rawOffset, dstSavings, checksum, transitions,
				offsets, simpleTimeZoneParams, willGMTOffsetChange);
 }

	static List getZoneIDs() {
		Object obj = null;
		SoftReference softreference = zoneIDs;
		if (softreference != null) {
			obj = (List) softreference.get();
			if (obj != null)
				return ((List) (obj));
		}
		byte abyte0[] = null;
		abyte0 = getZoneInfoMappings();
		int i = JAVAZM_LABEL_LENGTH + 1;
		int j = abyte0.length;
		try {
			label0: do {
				if (i >= j)
					break;
				byte byte0 = abyte0[i++];
				int k = ((abyte0[i++] & 255) << 8) + (abyte0[i++] & 255);
				switch (byte0) {
				case 64: // '@'
					int l = (abyte0[i++] << 8) + (abyte0[i++] & 255);
					obj = new ArrayList(l);
					for (int i1 = 0; i1 < l; i1++) {
						byte byte1 = abyte0[i++];
						((List) (obj)).add(new String(abyte0, i, byte1,
								"US-ASCII"));
						i += byte1;
					}

					break label0;

				default:
					i += k;
					break;
				}
			} while (true);
		} catch (Exception exception) {
			System.err.println("ZoneInfo: corrupted ZoneInfoMappings");
		}
		zoneIDs = new SoftReference(obj);
		return ((List) (obj));
	}

	static Map getZoneAliases() {
		byte abyte0[] = getZoneInfoMappings();
		int i = JAVAZM_LABEL_LENGTH + 1;
		int j = abyte0.length;
		HashMap hashmap = null;
		try {
			label0: do {
				if (i >= j)
					break;
				byte byte0 = abyte0[i++];
				int k = ((abyte0[i++] & 255) << 8) + (abyte0[i++] & 255);
				switch (byte0) {
				case 67: // 'C'
					int l = (abyte0[i++] << 8) + (abyte0[i++] & 255);
					hashmap = new HashMap(l);
					for (int i1 = 0; i1 < l; i1++) {
						byte byte1 = abyte0[i++];
						String s = new String(abyte0, i, byte1, "US-ASCII");
						i += byte1;
						byte1 = abyte0[i++];
						String s1 = new String(abyte0, i, byte1, "US-ASCII");
						i += byte1;
						hashmap.put(s, s1);
					}

					break label0;

				default:
					i += k;
					break;
				}
			} while (true);
		} catch (Exception exception) {
			System.err.println("ZoneInfo: corrupted ZoneInfoMappings");
			return null;
		}
		return hashmap;
	}

	static List getExcludedZones() {
		if (hasNoExcludeList)
			return null;
		Object obj = null;
		SoftReference softreference = excludedIDs;
		if (softreference != null) {
			obj = (List) softreference.get();
			if (obj != null)
				return ((List) (obj));
		}
		byte abyte0[] = getZoneInfoMappings();
		int i = JAVAZM_LABEL_LENGTH + 1;
		int j = abyte0.length;
		try {
			label0: do {
				if (i >= j)
					break;
				byte byte0 = abyte0[i++];
				int k = ((abyte0[i++] & 255) << 8) + (abyte0[i++] & 255);
				switch (byte0) {
				case 69: // 'E'
					int l = (abyte0[i++] << 8) + (abyte0[i++] & 255);
					obj = new ArrayList();
					for (int i1 = 0; i1 < l; i1++) {
						byte byte1 = abyte0[i++];
						String s = new String(abyte0, i, byte1, "US-ASCII");
						i += byte1;
						((List) (obj)).add(s);
					}

					break label0;

				default:
					i += k;
					break;
				}
			} while (true);
		} catch (Exception exception) {
			System.err.println("ZoneInfo: corrupted ZoneInfoMappings");
			return null;
		}
		if (obj != null)
			excludedIDs = new SoftReference(obj);
		else
			hasNoExcludeList = true;
		return ((List) (obj));
	}

	static byte[] getRawOffsetIndices() {
		byte abyte0[] = null;
		SoftReference softreference = rawOffsetIndices;
		if (softreference != null) {
			abyte0 = (byte[]) softreference.get();
			if (abyte0 != null)
				return abyte0;
		}
		byte abyte1[] = getZoneInfoMappings();
		int i = JAVAZM_LABEL_LENGTH + 1;
		int j = abyte1.length;
		try {
			label0: do {
				if (i >= j)
					break;
				byte byte0 = abyte1[i++];
				int k = ((abyte1[i++] & 255) << 8) + (abyte1[i++] & 255);
				switch (byte0) {
				case 66: // 'B'
					abyte0 = new byte[k];
					for (int l = 0; l < k; l++)
						abyte0[l] = abyte1[i++];

					break label0;

				default:
					i += k;
					break;
				}
			} while (true);
		} catch (ArrayIndexOutOfBoundsException arrayindexoutofboundsexception) {
			System.err.println("ZoneInfo: corrupted ZoneInfoMappings");
		}
		rawOffsetIndices = new SoftReference(abyte0);
		return abyte0;
	}

	static int[] getRawOffsets() {
		int ai[] = null;
		SoftReference softreference = rawOffsets;
		if (softreference != null) {
			ai = (int[]) softreference.get();
			if (ai != null)
				return ai;
		}
		byte abyte0[] = getZoneInfoMappings();
		int i = JAVAZM_LABEL_LENGTH + 1;
		int j = abyte0.length;
		try {
			label0: do {
				if (i >= j)
					break;
				byte byte0 = abyte0[i++];
				int k = ((abyte0[i++] & 255) << 8) + (abyte0[i++] & 255);
				switch (byte0) {
				case 65: // 'A'
					int l = k / 4;
					ai = new int[l];
					for (int i1 = 0; i1 < l; i1++) {
						int j1 = abyte0[i++] & 255;
						j1 = (j1 << 8) + (abyte0[i++] & 255);
						j1 = (j1 << 8) + (abyte0[i++] & 255);
						j1 = (j1 << 8) + (abyte0[i++] & 255);
						ai[i1] = j1;
					}

					break label0;

				default:
					i += k;
					break;
				}
			} while (true);
		} catch (ArrayIndexOutOfBoundsException arrayindexoutofboundsexception) {
			System.err.println("ZoneInfo: corrupted ZoneInfoMappings");
		}
		rawOffsets = new SoftReference(ai);
		return ai;
	}

	private static byte[] getZoneInfoMappings() {
		SoftReference softreference = zoneInfoMappings;
		if (softreference != null) {
			byte abyte0[] = (byte[]) softreference.get();
			if (abyte0 != null)
				return abyte0;
		}
		byte abyte1[] = readZoneInfoFile("ZoneInfoMappings");
		if (abyte1 == null)
			return null;
		int i;
		for (i = 0; i < JAVAZM_LABEL.length; i++)
			if (abyte1[i] != JAVAZM_LABEL[i]) {
				System.err
						.println("ZoneInfo: wrong magic number: ZoneInfoMappings");
				return null;
			}

		if (abyte1[i++] > 1) {
			System.err.println((new StringBuilder()).append(
					"ZoneInfo: incompatible version (").append(abyte1[i - 1])
					.append("): ").append("ZoneInfoMappings").toString());
			return null;
		} else {
			zoneInfoMappings = new SoftReference(abyte1);
			return abyte1;
		}
	}

	private static byte[] readZoneInfoFile(String fileName)
 {
		byte[] buffer = null;

		try {
			String homeDir = (String) AccessController
					.doPrivileged(new vjo.sun.security.action.GetPropertyAction(
							"java.home"));
			final String fname = homeDir + File.separator + "lib"
					+ File.separator + "zi" + File.separator + fileName;
			buffer = (byte[]) AccessController
					.doPrivileged(new PrivilegedExceptionAction() {
						public Object run() throws IOException {
							File file = new File(fname);
							int filesize = (int) file.length();
							byte[] buf = new byte[filesize];

							FileInputStream fis = new FileInputStream(file);

							if (fis.read(buf) != filesize) {
								fis.close();
								throw new IOException("read error on " + fname);
							}
							fis.close();
							return buf;
						}
					});
		} catch (PrivilegedActionException e) {
			Exception ex = e.getException();
			if (!(ex instanceof FileNotFoundException)
					|| JAVAZM_FILE_NAME.equals(fileName)) {
				System.err.println("ZoneInfo: " + ex.getMessage());
			}
		}
		return buffer;
 }

	public static final byte JAVAZI_LABEL[] = { 106, 97, 118, 97, 122, 105, 0 };

	private static final int JAVAZI_LABEL_LENGTH = JAVAZI_LABEL.length;

	public static final byte JAVAZI_VERSION = 1;

	public static final byte TAG_RawOffset = 1;

	public static final byte TAG_LastDSTSaving = 2;

	public static final byte TAG_CRC32 = 3;

	public static final byte TAG_Transition = 4;

	public static final byte TAG_Offset = 5;

	public static final byte TAG_SimpleTimeZone = 6;

	public static final byte TAG_GMTOffsetWillChange = 7;

	public static final String JAVAZM_FILE_NAME = "ZoneInfoMappings";

	public static final byte JAVAZM_LABEL[] = { 106, 97, 118, 97, 122, 109, 0 };

	private static final int JAVAZM_LABEL_LENGTH = JAVAZM_LABEL.length;

	public static final byte JAVAZM_VERSION = 1;

	public static final byte TAG_ZoneIDs = 64;

	public static final byte TAG_RawOffsets = 65;

	public static final byte TAG_RawOffsetIndices = 66;

	public static final byte TAG_ZoneAliases = 67;

	public static final byte TAG_TZDataVersion = 68;

	public static final byte TAG_ExcludedZones = 69;

	private static Map zoneInfoObjects = null;

	private static volatile SoftReference zoneIDs = null;

	private static volatile SoftReference excludedIDs = null;

	private static volatile boolean hasNoExcludeList = false;

	private static volatile SoftReference rawOffsetIndices = null;

	private static volatile SoftReference rawOffsets = null;

	private static volatile SoftReference zoneInfoMappings = null;

}


/*
	DECOMPILATION REPORT

	Decompiled from: C:\opt\java5-ibm-2007-12-13\jre\lib\core.jar
	Total time: 47 ms
	Jad reported messages/errors:
Couldn't fully decompile method createZoneInfo
Couldn't resolve all exception handlers in method createZoneInfo
	Exit status: 0
	Caught exceptions:
*/