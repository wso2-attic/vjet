package vjo.java.sun.text;

/*jadclipse*/// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
//Jad home page: http://www.kpdus.com/jad.html
//Decompiler options: packimports(3) radix(10) lradix(10) 
//Source File Name:   SupplementaryCharacterData.java
import java.lang.AssertionError;

import vjo.java.lang.* ;
import vjo.java.lang.Integer ;
import vjo.java.lang.StringBuilder;

public final class SupplementaryCharacterData implements java.lang.Cloneable {

	public SupplementaryCharacterData(int ai[]) {
		dataTable = ai;
	}

	public int getValue(int i) {
		if (!assertionsDisabled && (i < 65536 || i > 1114111))
			throw new AssertionError((new StringBuilder()).append(
					"Invalid code point:").append(Integer.toHexString(i))
					.toString());
		int j = 0;
		int k = dataTable.length - 1;
		do {
			int l = (j + k) / 2;
			int i1 = dataTable[l] >> 8;
			int j1 = dataTable[l + 1] >> 8;
			if (i < i1)
				k = l;
			else if (i > j1 - 1)
				j = l;
			else
				return dataTable[l] & 255;
		} while (true);
	}

	public int[] getArray() {
		return dataTable;
	}

	private int dataTable[];

//MrP - JAD casuality
	static final boolean assertionsDisabled = true ; //!sun / text
//			/ SupplementaryCharacterData.desiredAssertionStatus();

}


/*
	DECOMPILATION REPORT

	Decompiled from: C:\opt\java5-ibm-2007-12-13\jre\lib\core.jar
	Total time: 16 ms
	Jad reported messages/errors:
	Exit status: 0
	Caught exceptions:
*/