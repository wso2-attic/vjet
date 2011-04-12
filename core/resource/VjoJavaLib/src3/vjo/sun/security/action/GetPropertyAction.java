package vjo.java.sun.security.action;

/*jadclipse*/// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
//Jad home page: http://www.kpdus.com/jad.html
//Decompiler options: packimports(3) radix(10) lradix(10) 
//Source File Name:   GetPropertyAction.java

import vjo.java.lang.* ;

import vjo.java.security.PrivilegedAction;

public class GetPropertyAction implements PrivilegedAction {

	public GetPropertyAction(String s) {
		theProp = s;
	}

	public GetPropertyAction(String s, String s1) {
		theProp = s;
		defaultVal = s1;
	}

	public Object run() {
		String s = vjo.lang.System.getProperty(theProp);
		return s != null ? s : defaultVal;
	}

	private String theProp;

	private String defaultVal;
}


/*
	DECOMPILATION REPORT

	Decompiled from: C:\opt\java5-ibm-2007-12-13\jre\lib\core.jar
	Total time: 15 ms
	Jad reported messages/errors:
	Exit status: 0
	Caught exceptions:
*/
