package vjo.java.sun.text;

/*jadclipse*/// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
//Jad home page: http://www.kpdus.com/jad.html
//Decompiler options: packimports(3) radix(10) lradix(10) 
//Source File Name:   CodePointIterator.java

import vjo.java.lang.* ;
import vjo.java.lang.Character; ;

//Referenced classes of package sun.text:
//         CodePointIterator

final class CharSequenceCodePointIterator extends CodePointIterator {

	public CharSequenceCodePointIterator(CharSequence charsequence) {
		text = charsequence;
	}

	public void setToStart() {
		index = 0;
	}

	public void setToLimit() {
		index = text.length();
	}

	public int next() {
		if (index < text.length()) {
			char c = text.charAt(index++);
			if (Character.isHighSurrogate(c) && index < text.length()) {
				char c1 = text.charAt(index + 1);
				if (Character.isLowSurrogate(c1)) {
					index++;
					return Character.toCodePoint(c, c1);
				}
			}
			return c;
		} else {
			return -1;
		}
	}

	public int prev() {
		if (index > 0) {
			char c = text.charAt(--index);
			if (Character.isLowSurrogate(c) && index > 0) {
				char c1 = text.charAt(index - 1);
				if (Character.isHighSurrogate(c1)) {
					index--;
					return Character.toCodePoint(c1, c);
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

	private CharSequence text;

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
