package vjo.java.sun.text;

/*jadclipse*/// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
//Jad home page: http://www.kpdus.com/jad.html
//Decompiler options: packimports(3) radix(10) lradix(10) 
//Source File Name:   IntHashtable.java

import java.lang.IllegalArgumentException;

import vjo.java.lang.* ;
import vjo.java.lang.System ;

import vjo.java.io.PrintStream;

public final class IntHashtable {

	public IntHashtable() {
		defaultValue = 0;
		initialize(3);
	}

	public IntHashtable(int i) {
		defaultValue = 0;
		initialize(leastGreaterPrimeIndex((int) ((float) i / 0.4F)));
	}

	public int size() {
		return count;
	}

	public boolean isEmpty() {
		return count == 0;
	}

	public void put(int i, int j) {
		if (count > highWaterMark)
			rehash();
		int k = find(i);
		if (keyList[k] <= -2147483647) {
			keyList[k] = i;
			count++;
		}
		values[k] = j;
	}

	public int get(int i) {
		return values[find(i)];
	}

	public void remove(int i) {
		int j = find(i);
		if (keyList[j] > -2147483647) {
			keyList[j] = -2147483647;
			values[j] = defaultValue;
			count--;
			if (count < lowWaterMark)
				rehash();
		}
	}

	public int getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(int i) {
		defaultValue = i;
		rehash();
	}

	public boolean equals(Object obj) {
		if (obj.getClass() != getClass())
			return false;
		IntHashtable inthashtable = (IntHashtable) obj;
		if (inthashtable.size() != count
				|| inthashtable.defaultValue != defaultValue)
			return false;
		for (int i = 0; i < keyList.length; i++) {
			int j = keyList[i];
			if (j > -2147483647 && inthashtable.get(j) != values[i])
				return false;
		}

		return true;
	}

	public int hashCode() {
		int i = 465;
		int j = 1362796821;
		for (int k = 0; k < keyList.length; k++) {
			i = i * j + 1;
			i += keyList[k];
		}

		for (int l = 0; l < values.length; l++) {
			i = i * j + 1;
			i += values[l];
		}

		return i;
	}

	public Object clone() throws java.lang.CloneNotSupportedException {
		IntHashtable inthashtable = (IntHashtable) super.clone();
		values = (int[]) (int[]) values.clone();
		keyList = (int[]) (int[]) keyList.clone();
		return inthashtable;
	}

	private void initialize(int i) {
		if (i < 0)
			i = 0;
		else if (i >= PRIMES.length) {
			System.out.println("TOO BIG");
			i = PRIMES.length - 1;
		}
		primeIndex = i;
		int j = PRIMES[i];
		values = new int[j];
		keyList = new int[j];
		for (int k = 0; k < j; k++) {
			keyList[k] = -2147483648;
			values[k] = defaultValue;
		}

		count = 0;
		lowWaterMark = (int) ((float) j * 0.0F);
		highWaterMark = (int) ((float) j * 0.4F);
	}

	private void rehash() {
		int ai[] = values;
		int ai1[] = keyList;
		int i = primeIndex;
		if (count > highWaterMark)
			i++;
		else if (count < lowWaterMark)
			i -= 2;
		initialize(i);
		for (int j = ai.length - 1; j >= 0; j--) {
			int k = ai1[j];
			if (k > -2147483647)
				putInternal(k, ai[j]);
		}

	}

	public void putInternal(int i, int j) {
		int k = find(i);
		if (keyList[k] < -2147483647) {
			keyList[k] = i;
			count++;
		}
		values[k] = j;
	}

	private int find(int i) {
		if (i <= -2147483647)
			throw new IllegalArgumentException(
					"key can't be less than 0xFFFFFFFE");
		int j = -1;
		int k = (i ^ 67108864) % keyList.length;
		if (k < 0)
			k = -k;
		int l = 0;
		do {
			int i1 = keyList[k];
			if (i1 == i)
				return k;
			if (i1 <= -2147483647) {
				if (i1 == -2147483648) {
					if (j >= 0)
						k = j;
					return k;
				}
				if (j < 0)
					j = k;
			}
			if (l == 0) {
				l = i % (keyList.length - 1);
				if (l < 0)
					l = -l;
				l++;
			}
			k = (k + l) % keyList.length;
		} while (k != j);
		return k;
	}

	private static int leastGreaterPrimeIndex(int i) {
		int j;
		for (j = 0; j < PRIMES.length && i >= PRIMES[j]; j++)
			;
		return j != 0 ? j - 1 : 0;
	}

	private int defaultValue;

	private int primeIndex;

	private static final float HIGH_WATER_FACTOR = 0.4F;

	private int highWaterMark;

	private static final float LOW_WATER_FACTOR = 0F;

	private int lowWaterMark;

	private int count;

	private int values[];

	private int keyList[];

	private static final int EMPTY = -2147483648;

	private static final int DELETED = -2147483647;

	private static final int MAX_UNUSED = -2147483647;

	private static final int PRIMES[] = { 17, 37, 67, 131, 257, 521, 1031,
			2053, 4099, 8209, 16411, 32771, 65537, 131101, 262147, 524309,
			1048583, 2097169, 4194319, 8388617, 16777259, 33554467, 67108879,
			134217757, 268435459, 536870923, 1073741827, 2147483647 };

}


/*
	DECOMPILATION REPORT

	Decompiled from: C:\opt\java5-ibm-2007-12-13\jre\lib\core.jar
	Total time: 15 ms
	Jad reported messages/errors:
	Exit status: 0
	Caught exceptions:
*/
