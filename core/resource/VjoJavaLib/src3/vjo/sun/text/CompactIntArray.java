package vjo.java.sun.text;

/*jadclipse*/// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
//Jad home page: http://www.kpdus.com/jad.html
//Decompiler options: packimports(3) radix(10) lradix(10) 
//Source File Name:   CompactIntArray.java
import java.lang.IllegalArgumentException;
import java.lang.InternalError;

import vjo.java.lang.* ;
import vjo.java.lang.Math ;
import vjo.java.lang.System ;

//Referenced classes of package sun.text:
//         Utility

public final class CompactIntArray implements java.lang.Cloneable {

	public CompactIntArray() {
		this(0);
	}

	public CompactIntArray(int i) {
		values = new int[65536];
		indices = new short[512];
		hashes = new int[512];
		for (int j = 0; j < 65536; j++)
			values[j] = i;

		for (int k = 0; k < 512; k++) {
			indices[k] = (short) (k << 7);
			hashes[k] = 0;
		}

		isCompact = false;
	}

	public CompactIntArray(short aword0[], int ai[]) {
		if (aword0.length != 512)
			throw new IllegalArgumentException("Index out of bounds.");
		for (int i = 0; i < 512; i++) {
			short word0 = aword0[i];
			if (word0 < 0 || word0 >= ai.length + 128)
				throw new IllegalArgumentException("Index out of bounds.");
		}

		indices = aword0;
		values = ai;
		isCompact = true;
	}

	public int elementAt(char c) {
		return values[(indices[c >> 7] & 65535) + (c & 127)];
	}

	public void setElementAt(char c, int i) {
		if (isCompact)
			expand();
		values[c] = i;
		touchBlock(c >> 7, i);
	}

	public void setElementAt(char c, char c1, int i) {
		if (isCompact)
			expand();
		for (int j = c; j <= c1; j++) {
			values[j] = i;
			touchBlock(j >> 7, i);
		}

	}

	public void compact() {
		if (!isCompact) {
			int i = 0;
			int j = 0;
			short word0 = -1;
			for (int k = 0; k < indices.length;) {
				indices[k] = -1;
				boolean flag = blockTouched(k);
				if (!flag && word0 != -1) {
					indices[k] = word0;
				} else {
					int i1 = 0;
					int j1 = 0;
					j1 = 0;
					do {
						if (j1 >= i)
							break;
						if (hashes[k] == hashes[j1]
								&& Utility.arrayRegionMatches(values, j,
										values, i1, 128)) {
							indices[k] = (short) i1;
							break;
						}
						j1++;
						i1 += 128;
					} while (true);
					if (indices[k] == -1) {
						System.arraycopy(values, j, values, i1, 128);
						indices[k] = (short) i1;
						hashes[j1] = hashes[k];
						i++;
						if (!flag)
							word0 = (short) i1;
					}
				}
				k++;
				j += 128;
			}

			int l = i * 128;
			int ai[] = new int[l];
			System.arraycopy(values, 0, ai, 0, l);
			values = ai;
			isCompact = true;
			hashes = null;
		}
	}

	private final void touchBlock(int i, int j) {
		hashes[i] = hashes[i] + (j << 1) | 1;
	}

	private final boolean blockTouched(int i) {
		return hashes[i] != 0;
	}

	public short[] getIndexArray() {
		return indices;
	}

	public int[] getStringArray() {
		return values;
	}

	public Object clone() {
		try {
			CompactIntArray compactintarray = (CompactIntArray) super.clone();
			compactintarray.values = (int[]) (int[]) values.clone();
			compactintarray.indices = (short[]) (short[]) indices.clone();
			if (hashes != null)
				compactintarray.hashes = (int[]) (int[]) hashes.clone();
			return compactintarray;
		} catch (java.lang.CloneNotSupportedException clonenotsupportedexception) {
			throw new InternalError();
		}
	}

	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		CompactIntArray compactintarray = (CompactIntArray) obj;
		for (int i = 0; i < 65536; i++)
			if (elementAt((char) i) != compactintarray.elementAt((char) i))
				return false;

		return true;
	}

	public int hashCode() {
		int i = 0;
		int j = Math.min(3, values.length / 16);
		for (int k = 0; k < values.length; k += j)
			i = i * 37 + values[k];

		return i;
	}

	private void expand() {
		if (isCompact) {
			hashes = new int[512];
			int ai[] = new int[65536];
			for (int i = 0; i < 65536; i++) {
				int k = elementAt((char) i);
				ai[i] = k;
				touchBlock(i >> 7, k);
			}

			for (int j = 0; j < 512; j++)
				indices[j] = (short) (j << 7);

			values = ai;
			isCompact = false;
		}
	}

	public static final int UNICODECOUNT = 65536;

	private static final int BLOCKSHIFT = 7;

	private static final int BLOCKCOUNT = 128;

	private static final int INDEXSHIFT = 9;

	private static final int INDEXCOUNT = 512;

	private static final int BLOCKMASK = 127;

	private int values[];

	private short indices[];

	private boolean isCompact;

	private int hashes[];
}


/*
	DECOMPILATION REPORT

	Decompiled from: C:\opt\java5-ibm-2007-12-13\jre\lib\core.jar
	Total time: 16 ms
	Jad reported messages/errors:
	Exit status: 0
	Caught exceptions:
*/
