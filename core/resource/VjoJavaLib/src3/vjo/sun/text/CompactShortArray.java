package vjo.java.sun.text;

import java.lang.ArrayIndexOutOfBoundsException;
import java.lang.IllegalArgumentException;
import java.lang.InternalError;

import vjo.java.lang.*;
import vjo.java.lang.Character;
import vjo.java.lang.Math; 
import vjo.java.lang.System ;

/*jadclipse*/// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.//Jad home page: http://www.kpdus.com/jad.html
//Decompiler options: packimports(3) radix(10) lradix(10) 
//Source File Name:   CompactShortArray.java

public final class CompactShortArray implements java.lang.Cloneable {
	// ------------------------------------------------------------------------
	/**
	 * An iterator over the indices and values in this compact array, The next()
	 * method returns each successive index that was used to store a value in
	 * the array, and the shortValue() method returns each corresponding value.
	 */
	public class Iterator {
		Iterator() {
			compact();
		}

		// Find the next character in the array
		public boolean hasNext() {
			nextIndex = index;
			boolean done = true;

			if (index != nextIndex) {
				// hasNext has already been called, and there's a new
				// index waiting to be returned
				return true;
			}

			while (++nextIndex < Character.MAX_VALUE) {
				//
				// See if we're at the start of a new block. If so, there are
				// some optimizations we can try
				//
				if ((nextIndex & BLOCKMASK) == 0) {
					int blockIndex = nextIndex >> BLOCKSHIFT;

					if (indices[blockIndex] == iUntouched) {
						// This block wasn't touched; we can skip it and go
						// to the beginning of the next one. The -1 is to
						// compensate for the ++nextIndex in the loop condition
						// System.out.println("skipping block " + blockIndex);
						nextIndex += BLOCKCOUNT - 1;
						continue;
					} else if (iUntouched == -1 && !touched) {
						// Remember the index of the first untouched block we
						// find, so we can skip any others with the same index
						iUntouched = indices[blockIndex - 1];

						// System.out.println("Block " + (blockIndex-1) + " was
						// untouched");
					} else {
						// Keep track of whether the next block was touched at
						// all
						touched = false;
					}
				}
				nextValue = elementAt((char) nextIndex);

				if (nextValue != defValue) {
					touched = true; // Remember this block was touched
					break; // Return all non-default values
				}
			}

			return nextIndex < Character.MAX_VALUE;
		}

		public char next() {
			if (index == nextIndex && !hasNext()) {
				throw new ArrayIndexOutOfBoundsException();
			}
			index = nextIndex;
			value = nextValue;

			return (char) index;
		}

		public short shortValue() {
			return value;
		}

		// Privates....
		int nextIndex = -1;

		int index = -1;

		short nextValue;

		short value;

		short iUntouched = -1;

		boolean touched = true;

		short defValue = defaultValue;
	}

	public CompactShortArray() {
		this((short) 0);
	}

	public CompactShortArray(short word0) {
		values = new short[65536];
		indices = new short[512];
		hashes = new int[512];
		for (int i = 0; i < 65536; i++)
			values[i] = word0;

		for (int j = 0; j < 512; j++) {
			indices[j] = (short) (j << 7);
			hashes[j] = 0;
		}

		isCompact = false;
		defaultValue = word0;
	}

	public CompactShortArray(short aword0[], short aword1[], short word0) {
		if (aword0.length != 512)
			throw new IllegalArgumentException(
					"Index out of bounds.");
		for (int i = 0; i < 512; i++) {
			short word1 = aword0[i];
			if (word1 < 0 || word1 >= aword1.length + 128)
				throw new IllegalArgumentException(
						"Index out of bounds.");
		}

		indices = aword0;
		values = aword1;
		isCompact = true;
		defaultValue = word0;
	}

	public short elementAt(char c) {
		return values[(indices[c >> 7] & 65535) + (c & 127)];
	}

	public void setElementAt(char c, short word0) {
		if (isCompact)
			expand();
		values[c] = word0;
		touchBlock(c >> 7, word0);
	}

	public void setElementAt(char c, char c1, short word0) {
		if (isCompact)
			expand();
		for (int i = c; i <= c1; i++) {
			values[i] = word0;
			touchBlock(i >> 7, word0);
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
					for (j1 = 0; j1 < i;) {
						if (hashes[k] == hashes[j1]
								&& arrayRegionMatches(values, j, values, i1,
										128))
							indices[k] = (short) i1;
						j1++;
						i1 += 128;
					}

					if (indices[k] == -1) {
						System
								.arraycopy(values, j, values, i1, 128);
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
			short aword0[] = new short[l];
			System.arraycopy(values, 0, aword0, 0, l);
			values = aword0;
			isCompact = true;
			hashes = null;
		}
	}

	public static final boolean arrayRegionMatches(short aword0[], int i,
			short aword1[], int j, int k) {
		int l = i + k;
		int i1 = j - i;
		for (int j1 = i; j1 < l; j1++)
			if (aword0[j1] != aword1[j1 + i1])
				return false;

		return true;
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

	public short[] getStringArray() {
		return values;
	}

	public Object clone() {
		try {
			CompactShortArray compactshortarray = (CompactShortArray) super
					.clone();
			compactshortarray.values = (short[]) (short[]) values.clone();
			compactshortarray.indices = (short[]) (short[]) indices.clone();
			return compactshortarray;
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
		CompactShortArray compactshortarray = (CompactShortArray) obj;
		for (int i = 0; i < 65536; i++)
			if (elementAt((char) i) != compactshortarray.elementAt((char) i))
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

	public Iterator getIterator() {
		return new Iterator();
	}

	private void expand() {
		if (isCompact) {
			short aword0[] = new short[65536];
			for (int i = 0; i < 65536; i++)
				aword0[i] = elementAt((char) i);

			for (int j = 0; j < 512; j++)
				indices[j] = (short) (j << 7);

			values = null;
			values = aword0;
			isCompact = false;
		}
	}

	public static final int UNICODECOUNT = 65536;

	static final int BLOCKSHIFT = 7;

	static final int BLOCKCOUNT = 128;

	static final int INDEXSHIFT = 9;

	static final int INDEXCOUNT = 512;

	static final int BLOCKMASK = 127;

	private short values[];

	private short indices[];

	private int hashes[];

	private boolean isCompact;

	short defaultValue;

}

/*
 * DECOMPILATION REPORT
 * 
 * Decompiled from: C:\opt\java5-ibm-2007-12-13\jre\lib\core.jar Total time: 15
 * ms Jad reported messages/errors: Exit status: 0 Caught exceptions:
 */