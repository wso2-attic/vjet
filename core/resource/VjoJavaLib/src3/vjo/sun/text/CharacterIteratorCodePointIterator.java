package vjo.java.sun.text;

/*jadclipse*/// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
//Jad home page: http://www.kpdus.com/jad.html
//Decompiler options: packimports(3) radix(10) lradix(10) 
//Source File Name:   CodePointIterator.java

import java.text.CharacterIterator;

import vjo.java.lang.* ;
import vjo.java.lang.Character ;

//Referenced classes of package sun.text:
//         CodePointIterator

final class CharacterIteratorCodePointIterator extends CodePointIterator {

	public CharacterIteratorCodePointIterator(
			CharacterIterator characteriterator) {
		iter = characteriterator;
	}

	public void setToStart() {
		iter.setIndex(iter.getBeginIndex());
	}

	public void setToLimit() {
		iter.setIndex(iter.getEndIndex());
	}

	public int next() {
		char c = iter.current();
		if (c != '\uFFFF') {
			char c1 = iter.next();
			if (Character.isHighSurrogate(c) && c1 != '\uFFFF'
					&& Character.isLowSurrogate(c1)) {
				iter.next();
				return Character.toCodePoint(c, c1);
			} else {
				return c;
			}
		} else {
			return -1;
		}
	}

	public int prev() {
		char c = iter.previous();
		if (c != '\uFFFF') {
			if (Character.isLowSurrogate(c)) {
				char c1 = iter.previous();
				if (Character.isHighSurrogate(c1))
					return Character.toCodePoint(c1, c);
				iter.next();
			}
			return c;
		} else {
			return -1;
		}
	}

	public int charIndex() {
		return iter.getIndex();
	}

	private CharacterIterator iter;
}


/*
	DECOMPILATION REPORT

	Decompiled from: C:\opt\java5-ibm-2007-12-13\jre\lib\core.jar
	Total time: 0 ms
	Jad reported messages/errors:
	Exit status: 0
	Caught exceptions:
*/