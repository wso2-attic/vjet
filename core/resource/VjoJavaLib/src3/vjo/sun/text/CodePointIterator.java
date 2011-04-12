package vjo.java.sun.text;

/*jadclipse*/// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
//Jad home page: http://www.kpdus.com/jad.html
//Decompiler options: packimports(3) radix(10) lradix(10) 
//Source File Name:   CodePointIterator.java

import java.text.CharacterIterator;

import vjo.java.lang.* ;

//Referenced classes of package sun.text:
//         CharArrayCodePointIterator, CharSequenceCodePointIterator, CharacterIteratorCodePointIterator

public abstract class CodePointIterator {

	public CodePointIterator() {
	}

	public abstract void setToStart();

	public abstract void setToLimit();

	public abstract int next();

	public abstract int prev();

	public abstract int charIndex();

	public static CodePointIterator create(char ac[]) {
		return new CharArrayCodePointIterator(ac);
	}

	public static CodePointIterator create(char ac[], int i, int j) {
		return new CharArrayCodePointIterator(ac, i, j);
	}

	public static CodePointIterator create(CharSequence charsequence) {
		return new CharSequenceCodePointIterator(charsequence);
	}

	public static CodePointIterator create(CharacterIterator characteriterator) {
		return new CharacterIteratorCodePointIterator(characteriterator);
	}

	public static final int DONE = -1;
}


/*
	DECOMPILATION REPORT

	Decompiled from: C:\opt\java5-ibm-2007-12-13\jre\lib\core.jar
	Total time: 0 ms
	Jad reported messages/errors:
	Exit status: 0
	Caught exceptions:
*/
