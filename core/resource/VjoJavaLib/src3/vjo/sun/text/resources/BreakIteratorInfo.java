package vjo.java.sun.text.resources;

/*jadclipse*/// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
//Jad home page: http://www.kpdus.com/jad.html
//Decompiler options: packimports(3) radix(10) lradix(10) 
//Source File Name:   BreakIteratorInfo.java

import vjo.java.lang.* ;

import vjo.java.util.ListResourceBundle;

public class BreakIteratorInfo extends ListResourceBundle {

	public BreakIteratorInfo() {
	}

	public Object[][] getContents() {
		return contents;
	}

	static final Object contents[][] = {
			{
					"BreakIteratorClasses",
					new String[] { "RuleBasedBreakIterator",
							"RuleBasedBreakIterator", "RuleBasedBreakIterator",
							"RuleBasedBreakIterator" } },
			{ "CharacterData", "CharacterBreakIteratorData" },
			{ "WordData", "WordBreakIteratorData" },
			{ "LineData", "LineBreakIteratorData" },
			{ "SentenceData", "SentenceBreakIteratorData" } };

}


/*
	DECOMPILATION REPORT

	Decompiled from: C:\opt\java5-ibm-2007-12-13\jre\lib\core.jar
	Total time: 0 ms
	Jad reported messages/errors:
	Exit status: 0
	Caught exceptions:
*/