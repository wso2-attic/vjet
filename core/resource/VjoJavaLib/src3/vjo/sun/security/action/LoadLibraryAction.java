package vjo.java.sun.security.action;

/*jadclipse*/// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
//Jad home page: http://www.kpdus.com/jad.html
//Decompiler options: packimports(3) radix(10) lradix(10) 
//Source File Name:   LoadLibraryAction.java

import vjo.java.lang.* ;
import vjo.java.lang.System ;

import vjo.java.security.PrivilegedAction;

public class LoadLibraryAction implements PrivilegedAction {

	public LoadLibraryAction(String s) {
		theLib = s;
	}

	public Object run() {
		System.loadLibrary(theLib);
		return null;
	}

	private String theLib;
}


/*
	DECOMPILATION REPORT

	Decompiled from: C:\opt\java5-ibm-2007-12-13\jre\lib\core.jar
	Total time: 16 ms
	Jad reported messages/errors:
	Exit status: 0
	Caught exceptions:
*/