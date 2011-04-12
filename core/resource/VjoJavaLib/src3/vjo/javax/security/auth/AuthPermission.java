package vjo.javax.security.auth;

/*jadclipse*/// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
//Jad home page: http://www.kpdus.com/jad.html
//Decompiler options: packimports(3) radix(10) lradix(10) 
//Source File Name:   AuthPermission.java


import vjo.security.BasicPermission;

public final class AuthPermission extends BasicPermission {

	public AuthPermission(String s) {
		super("createLoginContext".equals(s) ? "createLoginContext.*" : s);
	}

	public AuthPermission(String s, String s1) {
		super("createLoginContext".equals(s) ? "createLoginContext.*" : s, s1);
	}

	private static final long serialVersionUID = 5806031445061587174L;
}


/*
	DECOMPILATION REPORT

	Decompiled from: C:\opt\java5-ibm-2007-12-13\jre\lib\security.jar
	Total time: 15 ms
	Jad reported messages/errors:
	Exit status: 0
	Caught exceptions:
*/
