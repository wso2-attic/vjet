package vjo.java.sun.text;

/*jadclipse*/// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
//Jad home page: http://www.kpdus.com/jad.html
//Decompiler options: packimports(3) radix(10) lradix(10) 
//Source File Name:   ComposedCharIter.java

import vjo.java.lang.* ;

//Referenced classes of package sun.text:
//         NormalizerImpl

public final class ComposedCharIter {

	public ComposedCharIter() {
		curChar = -1;
	}

	public int next() {
		if (curChar == decompNum - 1)
			return -1;
		else
			return chars[++curChar];
	}

	public String decomposition() {
		return decomps[curChar];
	}

	public static final int DONE = -1;

	private static int chars[];

	private static String decomps[];

	private static int decompNum;

	private int curChar;

	static {
		char c = '\u07D0';
		chars = new int[c];
		decomps = new String[c];
		decompNum = NormalizerImpl.getDecompose(chars, decomps);
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
