package vjo.java.sun.text;

/*jadclipse*/// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
//Jad home page: http://www.kpdus.com/jad.html
//Decompiler options: packimports(3) radix(10) lradix(10) 
//Source File Name:   CodePointIterator.java

import vjo.java.lang.* ;

//Referenced classes of package sun.text:
//         CodePointIterator

final class CharArrayCodePointIterator extends CodePointIterator {

	public CharArrayCodePointIterator(char ac[]) {
		text = ac;
		limit = ac.length;
	}

	public CharArrayCodePointIterator(char ac[], int i, int j) {
		if (i < 0 || j < i || j > ac.length) {
			throw new vjo.lang.IllegalArgumentException();
		} else {
			text = ac;
			start = index = i;
			limit = j;
			return;
		}
	}

	public void setToStart() {
		index = start;
	}

	public void setToLimit() {
		index = limit;
	}

	public int next() {
		if (index < limit) {
			char c = text[index++];
			if (vjo.lang.Character.isHighSurrogate(c) && index < limit) {
				char c1 = text[index];
				if (vjo.lang.Character.isLowSurrogate(c1)) {
					index++;
					return vjo.lang.Character.toCodePoint(c, c1);
				}
			}
			return c;
		} else {
			return -1;
		}
	}

	public int prev() {
		if (index > start) {
			char c = text[--index];
			if (vjo.lang.Character.isLowSurrogate(c) && index > start) {
				char c1 = text[index - 1];
				if (vjo.lang.Character.isHighSurrogate(c1)) {
					index--;
					return vjo.lang.Character.toCodePoint(c1, c);
				}
			}
			return c;
		} else {
			return -1;
		}
	}

	public int charIndex() {
		return index;
	}

	private char text[];

	private int start;

	private int limit;

	private int index;
}


/*
	DECOMPILATION REPORT

	Decompiled from: C:\opt\java5-ibm-2007-12-13\jre\lib\core.jar
	Total time: 15 ms
	Jad reported messages/errors:
	Exit status: 0
	Caught exceptions:
*/
