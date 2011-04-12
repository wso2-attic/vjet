package vjo.java.sun.security.action;

/*jadclipse*/// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
//Jad home page: http://www.kpdus.com/jad.html
//Decompiler options: packimports(3) radix(10) lradix(10) 
//Source File Name:   GetBooleanAction.java

import vjo.java.lang.* ;
import vjo.java.lang.Boolean ;

import vjo.java.security.PrivilegedAction;

public class GetBooleanAction implements PrivilegedAction {

	public GetBooleanAction(String s) {
		theProp = s;
	}

	public Object run() {
		return new Boolean(Boolean.getBoolean(theProp));
	}

	private String theProp;
}


/*
	DECOMPILATION REPORT

	Decompiled from: C:\opt\java5-ibm-2007-12-13\jre\lib\core.jar
	Total time: 0 ms
	Jad reported messages/errors:
	Exit status: 0
	Caught exceptions:
*/
