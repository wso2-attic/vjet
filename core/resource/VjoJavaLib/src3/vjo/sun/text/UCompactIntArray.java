package vjo.java.sun.text;

/*jadclipse*/// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
//Jad home page: http://www.kpdus.com/jad.html
//Decompiler options: packimports(3) radix(10) lradix(10) 
//Source File Name:   UCompactIntArray.java

import vjo.java.lang.* ;
import vjo.java.lang.System ;

public final class UCompactIntArray implements java.lang.Cloneable {

	public UCompactIntArray() {
		values = new int[16][];
		indices = new short[16][];
		blockTouched = new boolean[16][];
		planeTouched = new boolean[16];
	}

	public UCompactIntArray(int i) {
		this();
		defaultValue = i;
	}

	public int elementAt(int i) {
		int j = (i & 196608) >> 16;
		if (!planeTouched[j]) {
			return defaultValue;
		} else {
			i &= 65535;
			return values[j][(indices[j][i >> 7] & 65535) + (i & 127)];
		}
	}

	public void setElementAt(int i, int j) {
		if (isCompact)
			expand();
		int k = (i & 196608) >> 16;
		if (!planeTouched[k])
			initPlane(k);
		i &= 65535;
		values[k][i] = j;
		blockTouched[k][i >> 7] = true;
	}

	public void compact() {
		if (isCompact)
			return;
		for (int i = 0; i < 16; i++) {
			if (!planeTouched[i])
				continue;
			int j = 0;
			int k = 0;
			short word0 = -1;
			for (int l = 0; l < indices[i].length;) {
				indices[i][l] = -1;
				if (!blockTouched[i][l] && word0 != -1) {
					indices[i][l] = word0;
				} else {
					int j1 = j * 128;
					if (l > j)
						System.arraycopy(values[i], k, values[i], j1, 128);
					if (!blockTouched[i][l])
						word0 = (short) j1;
					indices[i][l] = (short) j1;
					j++;
				}
				l++;
				k += 128;
			}

			int i1 = j * 128;
			int ai[] = new int[i1];
			System.arraycopy(values[i], 0, ai, 0, i1);
			values[i] = ai;
			blockTouched[i] = null;
		}

		isCompact = true;
	}

	private void expand() {
		if (isCompact) {
			for (int k = 0; k < 16; k++) {
				if (!planeTouched[k])
					continue;
				blockTouched[k] = new boolean[512];
				int ai[] = new int[65536];
				for (int i = 0; i < 65536; i++) {
					ai[i] = values[k][indices[k][i >> 7] & 65535 + (i & 127)];
					blockTouched[k][i >> 7] = true;
				}

				for (int j = 0; j < 512; j++)
					indices[k][j] = (short) (j << 7);

				values[k] = ai;
			}

			isCompact = false;
		}
	}

	private void initPlane(int i) {
		values[i] = new int[65536];
		indices[i] = new short[512];
		blockTouched[i] = new boolean[512];
		planeTouched[i] = true;
		if (planeTouched[0] && i != 0) {
			System.arraycopy(indices[0], 0, indices[i], 0, 512);
		} else {
			for (int j = 0; j < 512; j++)
				indices[i][j] = (short) (j << 7);

		}
		for (int k = 0; k < 65536; k++)
			values[i][k] = defaultValue;

	}

	public int getKSize() {
		int i = 0;
		for (int j = 0; j < 16; j++)
			if (planeTouched[j])
				i += values[j].length * 4 + indices[j].length * 2;

		return i / 1024;
	}

	private static final int PLANEMASK = 196608;

	private static final int PLANESHIFT = 16;

	private static final int PLANECOUNT = 16;

	private static final int CODEPOINTMASK = 65535;

	private static final int UNICODECOUNT = 65536;

	private static final int BLOCKSHIFT = 7;

	private static final int BLOCKCOUNT = 128;

	private static final int INDEXSHIFT = 9;

	private static final int INDEXCOUNT = 512;

	private static final int BLOCKMASK = 127;

	private int defaultValue;

	private int values[][];

	private short indices[][];

	private boolean isCompact;

	private boolean blockTouched[][];

	private boolean planeTouched[];
}


/*
	DECOMPILATION REPORT

	Decompiled from: C:\opt\java5-ibm-2007-12-13\jre\lib\core.jar
	Total time: 15 ms
	Jad reported messages/errors:
	Exit status: 0
	Caught exceptions:
*/