/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.trace;

import org.ebayopensource.dsf.common.trace.IDsfTracer.ExitStatus;

public class ConsoleTraceWriter implements ITraceWriter {
	
	private static final String ENTER_COLON = "Enter: ";
	private static final String EXIT_COLON = "Exit: ";
	private static final String EXIT = "Exit";
	
	private static final String CALL_COLON = "Call: ";
	private static final String END_CALL_COLON = "End Call: ";
	private static final String END_CALL = "End Call";
	
	private static final String LOOP_COLON = "Loop: ";
	private static final String LOOP_STEP = "Step: ";
	private static final String END_LOOP = "End Loop";
	
	private static final String DOT = ".";
	private static final String COMMA = ",";
	private static final String TAB = "\t";
	
	public ConsoleTraceWriter() {
		// empty on purpose
	}

	public void handleEnterMethod(int depth, String className, String methodName){
		write(getPadding(depth) + ENTER_COLON + className + DOT + methodName);
	}
	public void handleEnterMethod(int depth, String className, String methodName, String msg){
		write(getPadding(depth) + ENTER_COLON + className + DOT + methodName + " (" + msg + ")");
	}
	public void handleExitMethod(int depth, String className, String methodName){
		write(getPadding(depth) + EXIT);
	}
	public void handleExitMethod(int depth, String className, String methodName, ExitStatus status){
		write(getPadding(depth) + EXIT_COLON + status);
	}
	public void handleExitMethod(int depth, String className, String methodName, String msg){
		write(getPadding(depth) + EXIT_COLON + msg);
	}
	public void handleExitMethod(int depth, String className, String methodName, ExitStatus status, String msg){
		write(getPadding(depth) + EXIT_COLON + status + COMMA + msg);
	}
	
	public void handleStartCall(int depth, String className, String methodName){
		write(getPadding(depth) + CALL_COLON + className + DOT + methodName);
	}
	
	public void handleStartCall(int depth, String className, String methodName, String msg){
		write(getPadding(depth) + CALL_COLON + className + DOT + methodName + ", " + msg);
	}
	
	public void handleEndCall(int depth, String className, String methodName, String msg){
		write(getPadding(depth) + END_CALL_COLON + msg);
	}
	
	public void handleEndCall(int depth, String className, String methodName){
		write(getPadding(depth) + END_CALL);
	}
	
	public void handleStartLoop(int depth, String group){
		write(getPadding(depth) + LOOP_COLON + group);
	}
	
	public void handleLoopStep(int depth, String msg){
		write(getPadding(depth+1) + LOOP_STEP + msg);
	}
	
	public void handleEndLoop(int depth, String group){
		write(getPadding(depth) + END_LOOP);
	}
	
	public void handleMsg(int depth, String msg){
		write(getPadding(depth+1) + msg);
	}
	
	public void reset(){
		// No-op
	}
	
	//
	// Private
	//
	private String getPadding(int depth){
		
		StringBuffer padding = new StringBuffer();
		for (int i=0; i<depth;i++){
			padding.append(TAB);
		}
		
		return padding.toString();
	}
	
	private void write(String text){
	}
}
