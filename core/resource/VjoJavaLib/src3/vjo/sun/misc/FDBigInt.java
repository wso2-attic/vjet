package vjo.java.sun.misc;

/*jadclipse*/// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
//Jad home page: http://www.kpdus.com/jad.html
//Decompiler options: packimports(3) radix(10) lradix(10) 
//Source File Name:   FDBigInt.java
import vjo.java.lang.AssertionError;
import vjo.java.lang.IllegalArgumentException;
import vjo.java.lang.System;

public class FDBigInt {

	public FDBigInt(int i) {
		nWords = 1;
		data = new int[1];
		data[0] = i;
	}

	public FDBigInt(long l) {
		data = new int[2];
		data[0] = (int) l;
		data[1] = (int) (l >>> 32);
		nWords = data[1] != 0 ? 2 : 1;
	}

	public FDBigInt(FDBigInt fdbigint) {
		data = new int[nWords = fdbigint.nWords];
		System.arraycopy(fdbigint.data, 0, data, 0, nWords);
	}

	private FDBigInt(int ai[], int i) {
		data = ai;
		nWords = i;
	}

	public FDBigInt(long l, char ac[], int i, int j) {
		int k = (j + 8) / 9;
		if (k < 2)
			k = 2;
		data = new int[k];
		data[0] = (int) l;
		data[1] = (int) (l >>> 32);
		nWords = data[1] != 0 ? 2 : 1;
		int i1 = i;
		for (int j1 = j - 5; i1 < j1;) {
			int i2 = i1 + 5;
			int k1;
			for (k1 = ac[i1++] - 48; i1 < i2; k1 = (10 * k1 + ac[i1++]) - 48)
				;
			multaddMe(100000, k1);
		}

		int j2 = 1;
		int l1 = 0;
		while (i1 < j) {
			l1 = (10 * l1 + ac[i1++]) - 48;
			j2 *= 10;
		}
		if (j2 != 1)
			multaddMe(j2, l1);
	}

	public void lshiftMe(int i) throws IllegalArgumentException {
		if (i <= 0)
			if (i == 0)
				return;
			else
				throw new IllegalArgumentException("negative shift count");
		int j = i >> 5;
		int k = i & 31;
		int l = 32 - k;
		int ai[] = data;
		int ai1[] = data;
		if (nWords + j + 1 > ai.length)
			ai = new int[nWords + j + 1];
		int i1 = nWords + j;
		int j1 = nWords - 1;
		if (k == 0) {
			System.arraycopy(ai1, 0, ai, j, nWords);
			i1 = j - 1;
		} else {
			for (ai[i1--] = ai1[j1] >>> l; j1 >= 1; ai[i1--] = ai1[j1] << k
					| ai1[--j1] >>> l)
				;
			ai[i1--] = ai1[j1] << k;
		}
		while (i1 >= 0)
			ai[i1--] = 0;
		data = ai;
		for (nWords += j + 1; nWords > 1 && data[nWords - 1] == 0; nWords--)
			;
	}

	public int normalizeMe() throws IllegalArgumentException {
		int j = 0;
		int k = 0;
		int l = 0;
		int i;
		for (i = nWords - 1; i >= 0 && (l = data[i]) == 0; i--)
			j++;

		if (i < 0)
			throw new IllegalArgumentException("zero value");
		nWords -= j;
		if ((l & -268435456) != 0) {
			for (k = 32; (l & -268435456) != 0; k--)
				l >>>= 1;

		} else {
			while (l <= 1048575) {
				l <<= 8;
				k += 8;
			}
			while (l <= 134217727) {
				l <<= 1;
				k++;
			}
		}
		if (k != 0)
			lshiftMe(k);
		return k;
	}

	public FDBigInt mult(int i) {
		long l = i;
		int ai[] = new int[l * ((long) data[nWords - 1] & 4294967295L) <= 268435455L ? nWords
				: nWords + 1];
		long l1 = 0L;
		for (int j = 0; j < nWords; j++) {
			l1 += l * ((long) data[j] & 4294967295L);
			ai[j] = (int) l1;
			l1 >>>= 32;
		}

		if (l1 == 0L) {
			return new FDBigInt(ai, nWords);
		} else {
			ai[nWords] = (int) l1;
			return new FDBigInt(ai, nWords + 1);
		}
	}

	public void multaddMe(int i, int j) {
		long l = i;
		long l1 = l * ((long) data[0] & 4294967295L) + ((long) j & 4294967295L);
		data[0] = (int) l1;
		l1 >>>= 32;
		for (int k = 1; k < nWords; k++) {
			l1 += l * ((long) data[k] & 4294967295L);
			data[k] = (int) l1;
			l1 >>>= 32;
		}

		if (l1 != 0L) {
			data[nWords] = (int) l1;
			nWords++;
		}
	}

	public FDBigInt mult(FDBigInt fdbigint) {
		int ai[] = new int[nWords + fdbigint.nWords];
		for (int i = 0; i < nWords; i++) {
			long l = (long) data[i] & 4294967295L;
			long l1 = 0L;
			int k;
			for (k = 0; k < fdbigint.nWords; k++) {
				l1 += ((long) ai[i + k] & 4294967295L) + l
						* ((long) fdbigint.data[k] & 4294967295L);
				ai[i + k] = (int) l1;
				l1 >>>= 32;
			}

			ai[i + k] = (int) l1;
		}

		int j;
		for (j = ai.length - 1; j > 0 && ai[j] == 0; j--)
			;
		return new FDBigInt(ai, j + 1);
	}

	public FDBigInt add(FDBigInt fdbigint) {
		long l = 0L;
		int ai[];
		int ai1[];
		int j;
		int k;
		if (nWords >= fdbigint.nWords) {
			ai = data;
			j = nWords;
			ai1 = fdbigint.data;
			k = fdbigint.nWords;
		} else {
			ai = fdbigint.data;
			j = fdbigint.nWords;
			ai1 = data;
			k = nWords;
		}
		int ai2[] = new int[j];
		int i;
		for (i = 0; i < j; i++) {
			l += (long) ai[i] & 4294967295L;
			if (i < k)
				l += (long) ai1[i] & 4294967295L;
			ai2[i] = (int) l;
			l >>= 32;
		}

		if (l != 0L) {
			int ai3[] = new int[ai2.length + 1];
			System.arraycopy(ai2, 0, ai3, 0, ai2.length);
			ai3[i++] = (int) l;
			return new FDBigInt(ai3, i);
		} else {
			return new FDBigInt(ai2, i);
		}
	}

	public FDBigInt sub(FDBigInt fdbigint) {
		int ai[] = new int[nWords];
		int j = nWords;
		int k = fdbigint.nWords;
		int l = 0;
		long l1 = 0L;
		int i;
		for (i = 0; i < j; i++) {
			l1 += (long) data[i] & 4294967295L;
			if (i < k)
				l1 -= (long) fdbigint.data[i] & 4294967295L;
			if ((ai[i] = (int) l1) == 0)
				l++;
			else
				l = 0;
			l1 >>= 32;
		}

		if (!$assertionsDisabled && l1 != 0L)
			throw new AssertionError(l1);
		if (!$assertionsDisabled && !dataInRangeIsZero(i, k, fdbigint))
			throw new AssertionError();
		else
			return new FDBigInt(ai, j - l);
	}

	private static boolean dataInRangeIsZero(int i, int j, FDBigInt fdbigint) {
		while (i < j)
			if (fdbigint.data[i++] != 0)
				return false;
		return true;
	}

	public int cmp(FDBigInt fdbigint) {
		int i;
		if (nWords > fdbigint.nWords) {
			int j = fdbigint.nWords - 1;
			for (i = nWords - 1; i > j; i--)
				if (data[i] != 0)
					return 1;

		} else if (nWords < fdbigint.nWords) {
			int k = nWords - 1;
			for (i = fdbigint.nWords - 1; i > k; i--)
				if (fdbigint.data[i] != 0)
					return -1;

		} else {
			i = nWords - 1;
		}
		for (; i > 0 && data[i] == fdbigint.data[i]; i--)
			;
		int l = data[i];
		int i1 = fdbigint.data[i];
		if (l < 0)
			if (i1 < 0)
				return l - i1;
			else
				return 1;
		if (i1 < 0)
			return -1;
		else
			return l - i1;
	}

	public int quoRemIteration(FDBigInt fdbigint)
			throws IllegalArgumentException {
		if (nWords != fdbigint.nWords)
			throw new IllegalArgumentException("disparate values");
		int i = nWords - 1;
		long l = ((long) data[i] & 4294967295L) / (long) fdbigint.data[i];
		long l1 = 0L;
		for (int j = 0; j <= i; j++) {
			l1 += ((long) data[j] & 4294967295L) - l
					* ((long) fdbigint.data[j] & 4294967295L);
			data[j] = (int) l1;
			l1 >>= 32;
		}

		if (l1 != 0L) {
			for (long l2 = 0L; l2 == 0L;) {
				l2 = 0L;
				for (int k = 0; k <= i; k++) {
					l2 += ((long) data[k] & 4294967295L)
							+ ((long) fdbigint.data[k] & 4294967295L);
					data[k] = (int) l2;
					l2 >>= 32;
				}

				if (!$assertionsDisabled && l2 != 0L && l2 != 1L)
					throw new AssertionError(l2);
				l--;
			}

		}
		long l3 = 0L;
		for (int i1 = 0; i1 <= i; i1++) {
			l3 += 10L * ((long) data[i1] & 4294967295L);
			data[i1] = (int) l3;
			l3 >>= 32;
		}

		if (!$assertionsDisabled && l3 != 0L)
			throw new AssertionError(l3);
		else
			return (int) l;
	}

	public long longValue() {
		if (!$assertionsDisabled && nWords <= 0)
			throw new AssertionError(nWords);
		if (nWords == 1)
			return (long) data[0] & 4294967295L;
		if (!$assertionsDisabled && !dataInRangeIsZero(2, nWords, this))
			throw new AssertionError();
		if (!$assertionsDisabled && data[1] < 0)
			throw new AssertionError();
		else
			return (long) data[1] << 32 | (long) data[0] & 4294967295L;
	}

	public String toString() {
//		StringBuffer stringbuffer = new StringBuffer(30);
//		stringbuffer.append('[');
//		int i = Math.min(nWords - 1, data.length - 1);
//		if (nWords > data.length)
//			stringbuffer.append((new StringBuilder()).append("(").append(
//					data.length).append("<").append(nWords).append("!)")
//					.toString());
//		for (; i > 0; i--) {
//			stringbuffer.append(Integer.toHexString(data[i]));
//			stringbuffer.append(' ');
//		}
//
//		stringbuffer.append(Integer.toHexString(data[0]));
//		stringbuffer.append(']');
//		return stringbuffer.toString();
		return "";
	}

	int nWords;

	int data[];

	static final boolean $assertionsDisabled = true ; //MrP - JAD casuality - !sun.misc.FDBigInt.desiredAssertionStatus();

}


/*
	DECOMPILATION REPORT

	Decompiled from: C:\opt\java5-ibm-2007-12-13\jre\lib\core.jar
	Total time: 15 ms
	Jad reported messages/errors:
	Exit status: 0
	Caught exceptions:
*/
