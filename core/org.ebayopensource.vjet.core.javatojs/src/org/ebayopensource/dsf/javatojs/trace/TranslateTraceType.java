/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.trace;

import org.ebayopensource.dsf.common.trace.event.TraceType;

public class TranslateTraceType extends TraceType {

	public static TranslateTraceType START_GROUP = new TranslateTraceType("StartGroup");
	public static TranslateTraceType END_GROUP = new TranslateTraceType("EndGroup");
	
	public static TranslateTraceType TIME = new TranslateTraceType("Time");
	public static TranslateTraceType ERRORS = new TranslateTraceType("Errors");
	
	private TranslateTraceType(String name){
		super(name);
	}
	
	public static TraceType parse(String name){
		if (name == null){
			return null;
		}
		else if (name.equals(START_GROUP.name())){
			return START_GROUP;
		}
		else if (name.equals(END_GROUP.name())){
			return END_GROUP;
		}
		else {
			return TraceType.parse(name);
		}
	}
}
