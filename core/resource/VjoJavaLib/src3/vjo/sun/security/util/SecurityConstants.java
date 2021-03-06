package vjo.java.sun.security.util;

/*jadclipse*/// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
//Jad home page: http://www.kpdus.com/jad.html
//Decompiler options: packimports(3) radix(10) lradix(10) 
//Source File Name:   SecurityConstants.java


import vjo.java.awt.AWTPermission;

import vjo.java.net.NetPermission;
import vjo.java.net.SocketPermission;

import vjo.java.security.AllPermission;
import vjo.java.security.SecurityPermission;

import vjo.java.javax.security.auth.AuthPermission;

public final class SecurityConstants {

	private SecurityConstants() {
	}

	public static final String FILE_DELETE_ACTION = "delete";

	public static final String FILE_EXECUTE_ACTION = "execute";

	public static final String FILE_READ_ACTION = "read";

	public static final String FILE_WRITE_ACTION = "write";

	public static final String SOCKET_RESOLVE_ACTION = "resolve";

	public static final String SOCKET_CONNECT_ACTION = "connect";

	public static final String SOCKET_LISTEN_ACTION = "listen";

	public static final String SOCKET_ACCEPT_ACTION = "accept";

	public static final String SOCKET_CONNECT_ACCEPT_ACTION = "connect,accept";

	public static final String PROPERTY_RW_ACTION = "read,write";

	public static final String PROPERTY_READ_ACTION = "read";

	public static final String PROPERTY_WRITE_ACTION = "write";

	public static final AllPermission ALL_PERMISSION = new AllPermission();

	public static final AWTPermission TOPLEVEL_WINDOW_PERMISSION = new AWTPermission(
			"showWindowWithoutWarningBanner");

	public static final AWTPermission ACCESS_CLIPBOARD_PERMISSION = new AWTPermission(
			"accessClipboard");

	public static final AWTPermission CHECK_AWT_EVENTQUEUE_PERMISSION = new AWTPermission(
			"accessEventQueue");

	public static final AWTPermission READ_DISPLAY_PIXELS_PERMISSION = new AWTPermission(
			"readDisplayPixels");

	public static final AWTPermission CREATE_ROBOT_PERMISSION = new AWTPermission(
			"createRobot");

	public static final AWTPermission WATCH_MOUSE_PERMISSION = new AWTPermission(
			"watchMousePointer");

	public static final AWTPermission SET_WINDOW_ALWAYS_ON_TOP_PERMISSION = new AWTPermission(
			"setWindowAlwaysOnTop");

	public static final AWTPermission ALL_AWT_EVENTS_PERMISSION = new AWTPermission(
			"listenToAllAWTEvents");

	public static final NetPermission SPECIFY_HANDLER_PERMISSION = new NetPermission(
			"specifyStreamHandler");

	public static final NetPermission SET_PROXYSELECTOR_PERMISSION = new NetPermission(
			"setProxySelector");

	public static final NetPermission GET_PROXYSELECTOR_PERMISSION = new NetPermission(
			"getProxySelector");

	public static final NetPermission SET_COOKIEHANDLER_PERMISSION = new NetPermission(
			"setCookieHandler");

	public static final NetPermission GET_COOKIEHANDLER_PERMISSION = new NetPermission(
			"getCookieHandler");

	public static final NetPermission SET_RESPONSECACHE_PERMISSION = new NetPermission(
			"setResponseCache");

	public static final NetPermission GET_RESPONSECACHE_PERMISSION = new NetPermission(
			"getResponseCache");

	public static final RuntimePermission CREATE_CLASSLOADER_PERMISSION = new RuntimePermission(
			"createClassLoader");

	public static final RuntimePermission CHECK_MEMBER_ACCESS_PERMISSION = new RuntimePermission(
			"accessDeclaredMembers");

	public static final RuntimePermission MODIFY_THREAD_PERMISSION = new RuntimePermission(
			"modifyThread");

	public static final RuntimePermission MODIFY_THREADGROUP_PERMISSION = new RuntimePermission(
			"modifyThreadGroup");

	public static final RuntimePermission GET_PD_PERMISSION = new RuntimePermission(
			"getProtectionDomain");

	public static final RuntimePermission GET_CLASSLOADER_PERMISSION = new RuntimePermission(
			"getClassLoader");

	public static final RuntimePermission STOP_THREAD_PERMISSION = new RuntimePermission(
			"stopThread");

	public static final RuntimePermission GET_STACK_TRACE_PERMISSION = new RuntimePermission(
			"getStackTrace");

	public static final SecurityPermission CREATE_ACC_PERMISSION = new SecurityPermission(
			"createAccessControlContext");

	public static final SecurityPermission GET_COMBINER_PERMISSION = new SecurityPermission(
			"getDomainCombiner");

	public static final SecurityPermission GET_POLICY_PERMISSION = new SecurityPermission(
			"getPolicy");

	public static final SocketPermission LOCAL_LISTEN_PERMISSION = new SocketPermission(
			"localhost:1024-", "listen");

	public static final AuthPermission DO_AS_PERMISSION = new AuthPermission(
			"doAs");

	public static final AuthPermission DO_AS_PRIVILEGED_PERMISSION = new AuthPermission(
			"doAsPrivileged");

}


/*
	DECOMPILATION REPORT

	Decompiled from: C:\opt\java5-ibm-2007-12-13\jre\lib\core.jar
	Total time: 31 ms
	Jad reported messages/errors:
	Exit status: 0
	Caught exceptions:
*/
