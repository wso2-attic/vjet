package vjo.java.sun.security.action;

/*jadclipse*/// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
//Jad home page: http://www.kpdus.com/jad.html
//Decompiler options: packimports(3) radix(10) lradix(10) 
//Source File Name:   GetLongAction.java

import vjo.java.lang.* ;
import vjo.java.lang.Long ;

import vjo.java.security.PrivilegedAction;

public class GetLongAction implements PrivilegedAction {

	public GetLongAction(String s) {
		defaultSet = false;
		theProp = s;
	}

	public GetLongAction(String s, long l) {
		defaultSet = false;
		theProp = s;
		defaultVal = l;
		defaultSet = true;
	}

	public Object run() {
		Long long1 = Long.getLong(theProp);
		if (long1 == null && defaultSet)
			return new Long(defaultVal);
		else
			return long1;
	}

	private String theProp;

	private long defaultVal;

	private boolean defaultSet;
}


/*
	DECOMPILATION REPORT

	Decompiled from: C:\opt\java5-ibm-2007-12-13\jre\lib\core.jar
	Total time: 0 ms
	Jad reported messages/errors:
	Exit status: 0
	Caught exceptions:
*/
