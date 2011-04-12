/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.trace;

/**
 * Defines common interface for tracing.
 */
public interface IDsfTracer {
	
	/**
	 * Turn trace on or off
	 * @param enable boolean
	 */
	void enableTrace(boolean enable);
	
	/**
	 * Answer whether trace is on.
	 * @return boolean
	 */
	boolean isEnabled();
	
	/**
	 * Add trace writer.
	 * @param writer ITraceWriter
	 * @return IDsfTracer
	 */
	IDsfTracer addWriter(ITraceWriter writer);
	
	/**
	 * Trace method entering. Should be called at the begining of
	 * each method to be traced. Must be paired with one of the
	 * exit method.
	 * @param caller Object
	 */
	void enterMethod(Object caller);
	
	/**
	 * Trace method entering. Should be called at the begining of
	 * each method to be traced. Must be paired with one of the
	 * exitMethod.
	 * @param caller Object
	 * @param msg String
	 */
	void enterMethod(Object caller, String msg);
	
	/**
	 * Trace method exiting. Should be called at the end of
	 * each method to be traced, as well as before each return
	 * statement. Must be paired with one of the enterMethod.
	 * @param caller Object
	 */
	void exitMethod(Object caller);
	
	/**
	 * Trace method exiting. Should be called at the end of
	 * each method to be traced, as well as before each return
	 * statement. Must be paired with one of the enterMethod.
	 * @param caller Object
	 * @param status ExitStatus
	 */
	void exitMethod(Object caller, ExitStatus status);
	
	/**
	 * Trace method exiting. Should be called at the end of
	 * each method to be traced, as well as before each return
	 * statement. Must be paired with one of the enterMethod.
	 * @param caller Object
	 * @param msg String
	 */
	void exitMethod(Object caller, String msg);
	
	/**
	 * Trace method exiting. Should be called at the end of
	 * each method to be traced, as well as before each return
	 * statement. Must be paired with one of the enterMethod.
	 * @param caller Object
	 * @param status ExitStatus
	 * @param msg String
	 */
	void exitMethod(Object caller, ExitStatus status, String msg);
	
	/**
	 * Trace starting of method invocation. Should be called just before a method
	 * invocation. Must be paired with one of the endCall.
	 * @param callee Object instance to be called on
	 * @param method String name of the method invoked.
	 */
	void startCall(Object callee, String method);
	
	/**
	 * Trace starting of method invocation. Should be called just before a method
	 * invocation. Must be paired with one of the endCall.
	 * @param callee Object instance to be called on
	 * @param method String name of the method invoked.
	 * @param msg String
	 */
	void startCall(Object callee, String method, String msg);
	
	/**
	 * Trace ending of method invocation. Should be called just after a method
	 * invocation. Must be paired with one of the startCall.
	 * @param callee Object instance to be called on
	 * @param method String name of the method invoked.
	 */
	void endCall(Object callee, String method);
	
	/**
	 * Trace ending of method invocation. Should be called just after a method
	 * invocation. Must be paired with one of the startCall.
	 * @param callee Object instance to be called on
	 * @param method String name of the method invoked.
	 * @param msg String
	 */
	void endCall(Object callee, String method, String msg);

	/**
	 * Trace starting of a loop, for loop or while loop etc. Should be called just 
	 * before the loop starts. Must be paired with endLoop.
	 * @param group String name for the loop
	 */
	void startLoop(String group);
	
	/**
	 * Trace individual step of a loop. Should be called inside the loop. 
	 * Should be used together with startLoop and endLoop.
	 * @param msg String
	 */
	void loopStep(String msg);
	
	/**
	 * Trace ending of a loop, for loop or while loop etc. Should be called just 
	 * after the loop ends. Must be paired with startLoop.
	 * @param group String name for the loop
	 */
	void endLoop(String group);
	
	/**
	 * Trace any intermmediate step inside a method. Should be used between enterMethod
	 * and exitMethod. 
	 * @param msg String
	 */
	void msg(String msg);
	
	/**
	 * Clean up. For example, turn of trace flag, remove all writers etc.
	 */
	void reset();
	
	public enum ExitStatus {
//		NORMAL,
		ERROR,
		EXCEPTION,
		NO_OP
	}
}
