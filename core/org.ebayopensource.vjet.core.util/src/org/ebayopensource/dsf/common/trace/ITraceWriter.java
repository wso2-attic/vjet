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

public interface ITraceWriter {

	void handleEnterMethod(int depth, String className, String methodName);
	void handleEnterMethod(int depth, String className, String methodName, String msg);
	void handleExitMethod(int depth, String className, String methodName);
	void handleExitMethod(int depth, String className, String methodName, ExitStatus status);
	void handleExitMethod(int depth, String className, String methodName, String msg);
	void handleExitMethod(int depth, String className, String methodName, ExitStatus status, String msg);
	
	void handleStartCall(int depth, String className, String methodName);
	void handleStartCall(int depth, String className, String methodName, String msg);
	void handleEndCall(int depth, String className, String methodName, String msg);
	void handleEndCall(int depth, String className, String methodName);
	
	void handleStartLoop(int depth, String group);
	void handleLoopStep(int depth, String msg);
	void handleEndLoop(int depth, String group);
	
	void handleMsg(int depth, String msg);
	
	void reset();
}
