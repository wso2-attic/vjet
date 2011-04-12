package vjo.java.sun.security.action;

/*jadclipse*/// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
//Jad home page: http://www.kpdus.com/jad.html
//Decompiler options: packimports(3) radix(10) lradix(10) 
//Source File Name:   PutAllAction.java

import java.util.Map;

import vjo.java.lang.* ;

import vjo.java.security.PrivilegedAction;
import vjo.java.security.Provider;

public class PutAllAction implements PrivilegedAction {

	public PutAllAction(Provider provider1, Map map1) {
		provider = provider1;
		map = map1;
	}

	public Object run() {
		provider.putAll(map);
		return null;
	}

	private final Provider provider;

	private final Map map;
}


/*
	DECOMPILATION REPORT

	Decompiled from: C:\opt\java5-ibm-2007-12-13\jre\lib\core.jar
	Total time: 16 ms
	Jad reported messages/errors:
	Exit status: 0
	Caught exceptions:
*/
