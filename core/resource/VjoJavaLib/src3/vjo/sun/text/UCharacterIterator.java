package vjo.java.sun.text;

/*jadclipse*/// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
//Jad home page: http://www.kpdus.com/jad.html
//Decompiler options: packimports(3) radix(10) lradix(10) 
//Source File Name:   UCharacterIterator.java
import java.lang.Cloneable ;
import java.lang.IllegalArgumentException;
import java.lang.NullPointerException;

import java.text.CharacterIterator;

import vjo.java.lang.* ;
import vjo.java.lang.Character;
import vjo.java.lang.Math; 

public class UCharacterIterator implements Cloneable {
	static class CharacterIteratorWrapper extends UCharacterIterator {

		public int current() {
			char c = iterator.current();
			if (c == '\uFFFF')
				return -1;
			else
				return c;
		}

		public int getLength() {
			return iterator.getEndIndex() - iterator.getBeginIndex();
		}

		public int getIndex() {
			return iterator.getIndex();
		}

		public int next() {
			char c = iterator.current();
			iterator.next();
			if (c == '\uFFFF')
				return -1;
			else
				return c;
		}

		public int previous() {
			char c = iterator.previous();
			if (c == '\uFFFF')
				return -1;
			else
				return c;
		}

		public void setIndex(int i) {
			iterator.setIndex(i);
		}

		public int getBeginIndex() {
			return iterator.getBeginIndex();
		}

		private CharacterIterator iterator;

		CharacterIteratorWrapper(CharacterIterator characteriterator) {
			if (characteriterator == null) {
				throw new NullPointerException();
			} else {
				iterator = characteriterator;
				return;
			}
		}
	}

	private UCharacterIterator(String s) {
		if (s == null) {
			throw new NullPointerException();
		} else {
			text = s;
			currentIndex = 0;
			return;
		}
	}

	protected UCharacterIterator() {
	}

	public static final UCharacterIterator getInstance(String s) {
		return new UCharacterIterator(s);
	}

	public static final UCharacterIterator getInstance(
			CharacterIterator characteriterator) {
		return new CharacterIteratorWrapper(characteriterator);
	}

	public int current() {
		if (currentIndex < text.length())
			return text.charAt(currentIndex);
		else
			return -1;
	}

	public int getLength() {
		return text.length();
	}

	public int getIndex() {
		return currentIndex;
	}

	public int next() {
		if (currentIndex < text.length())
			return text.charAt(currentIndex++);
		else
			return -1;
	}

	public int nextCodePoint() {
		int i = next();
		if (Character.isHighSurrogate((char) i)) {
			int j = next();
			if (Character.isLowSurrogate((char) j))
				return Character.toCodePoint((char) i, (char) j);
			if (j != -1)
				previous();
		}
		return i;
	}

	public int previous() {
		if (currentIndex > 0)
			return text.charAt(--currentIndex);
		else
			return -1;
	}

	public int previousCodePoint() {
		int i = previous();
		if (Character.isLowSurrogate((char) i)) {
			int j = previous();
			if (Character.isHighSurrogate((char) j))
				return Character.toCodePoint((char) j, (char) i);
			if (j != -1)
				next();
		}
		return i;
	}

	public void setIndex(int i) {
		if (i < 0 || i > text.length()) {
			throw new IllegalArgumentException("Invalid index");
		} else {
			currentIndex = i;
			return;
		}
	}

	public int getBeginIndex() {
		return 0;
	}

	public void setToLimit() {
		setIndex(getLength());
	}

	public int moveIndex(int i) {
		int j = Math.max(0, Math.min(getIndex() + i, getLength()));
		setIndex(j);
		return j;
	}

	public Object clone() throws java.lang.CloneNotSupportedException {
		return super.clone();
	}

	public static final int DONE = -1;

	private String text;

	private int currentIndex;
}


/*
	DECOMPILATION REPORT

	Decompiled from: C:\opt\java5-ibm-2007-12-13\jre\lib\core.jar
	Total time: 16 ms
	Jad reported messages/errors:
	Exit status: 0
	Caught exceptions:
*/
