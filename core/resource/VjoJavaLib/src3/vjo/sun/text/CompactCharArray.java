package vjo.java.sun.text;

/*jadclipse*/// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
//Jad home page: http://www.kpdus.com/jad.html
//Decompiler options: packimports(3) radix(10) lradix(10) 
//Source File Name:   CompactCharArray.java

import java.lang.Cloneable ;
import java.lang.IllegalArgumentException;
import java.lang.InternalError;

import vjo.java.lang.* ;
import vjo.java.lang.Math ;
import vjo.java.lang.System ;

//Referenced classes of package sun.text:
//         Utility

public final class CompactCharArray implements Cloneable {

	public CompactCharArray() {
		this('\0');
	}

	public CompactCharArray(char c) {
		values = new char[65536];
		indices = new char[2048];
		hashes = new int[2048];
		for (int i = 0; i < 65536; i++)
			values[i] = c;

		for (int j = 0; j < 2048; j++) {
			indices[j] = (char) (j << 5);
			hashes[j] = 0;
		}

		isCompact = false;
		defaultValue = c;
	}

	public CompactCharArray(String s, String s1) {
		this(Utility.RLEStringToCharArray(s), Utility.RLEStringToCharArray(s1));
	}

	public CompactCharArray(char ac[], char ac1[]) {
		if (ac.length != 2048)
			throw new IllegalArgumentException("Index out of bounds.");
		for (int i = 0; i < 2048; i++) {
			char c = ac[i];
			if (c < 0 || c >= ac1.length + 32)
				throw new IllegalArgumentException("Index out of bounds.");
		}

		indices = ac;
		values = ac1;
		isCompact = true;
	}

	public char elementAt(char c) {
		return values[(indices[c >> 5] & 65535) + (c & 31)];
	}

	public void setElementAt(char c, char c1) {
		if (isCompact)
			expand();
		values[c] = c1;
		touchBlock(c >> 5, c1);
	}

	public void setElementAt(char c, char c1, char c2) {
		if (isCompact)
			expand();
		for (int i = c; i <= c1; i++) {
			values[i] = c2;
			touchBlock(i >> 5, c2);
		}

	}

	public void compact() {
		if (!isCompact) {
			int i = 0;
			int j = 0;
			char c = '\uFFFF';
			for (int k = 0; k < indices.length;) {
				indices[k] = '\uFFFF';
				boolean flag = blockTouched(k);
				if (!flag && c != 65535) {
					indices[k] = c;
				} else {
					int i1 = 0;
					int j1 = 0;
					for (j1 = 0; j1 < i;) {
						if (hashes[k] == hashes[j1]
								&& arrayRegionMatches(values, j, values, i1, 32))
							indices[k] = (char) i1;
						j1++;
						i1 += 32;
					}

					if (indices[k] == '\uFFFF') {
						System.arraycopy(values, j, values, i1, 32);
						indices[k] = (char) i1;
						hashes[j1] = hashes[k];
						i++;
						if (!flag)
							c = (char) i1;
					}
				}
				k++;
				j += 32;
			}

			int l = i * 32;
			char ac[] = new char[l];
			System.arraycopy(values, 0, ac, 0, l);
			values = ac;
			isCompact = true;
			hashes = null;
		}
	}

	static final boolean arrayRegionMatches(char ac[], int i, char ac1[],
			int j, int k) {
		int l = i + k;
		int i1 = j - i;
		for (int j1 = i; j1 < l; j1++)
			if (ac[j1] != ac1[j1 + i1])
				return false;

		return true;
	}

	private final void touchBlock(int i, int j) {
		hashes[i] = hashes[i] + (j << 1) | 1;
	}

	private final boolean blockTouched(int i) {
		return hashes[i] != 0;
	}

	public char[] getIndexArray() {
		return indices;
	}

	public char[] getStringArray() {
		return values;
	}

	public Object clone() {
		try {
			CompactCharArray compactchararray = (CompactCharArray) super
					.clone();
			compactchararray.values = (char[]) (char[]) values.clone();
			compactchararray.indices = (char[]) (char[]) indices.clone();
			if (hashes != null)
				compactchararray.hashes = (int[]) (int[]) hashes.clone();
			return compactchararray;
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
		CompactCharArray compactchararray = (CompactCharArray) obj;
		for (int i = 0; i < 65536; i++)
			if (elementAt((char) i) != compactchararray.elementAt((char) i))
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
			char ac[] = new char[65536];
			hashes = new int[2048];
			for (int i = 0; i < 65536; i++) {
				char c = elementAt((char) i);
				ac[i] = c;
				touchBlock(i >> 5, c);
			}

			for (int j = 0; j < 2048; j++)
				indices[j] = (char) (j << 5);

			values = null;
			values = ac;
			isCompact = false;
		}
	}

	private char getArrayValue(int i) {
		return values[i];
	}

	private char getIndexArrayValue(int i) {
		return indices[i];
	}

	public static final int UNICODECOUNT = 65536;

	private static final int BLOCKSHIFT = 5;

	private static final int BLOCKCOUNT = 32;

	private static final int INDEXSHIFT = 11;

	private static final int INDEXCOUNT = 2048;

	private static final int BLOCKMASK = 31;

	private char values[];

	private char indices[];

	private int hashes[];

	private boolean isCompact;

	private char defaultValue;
}


/*
	DECOMPILATION REPORT

	Decompiled from: C:\opt\java5-ibm-2007-12-13\jre\lib\core.jar
	Total time: 16 ms
	Jad reported messages/errors:
	Exit status: 0
	Caught exceptions:
*/
