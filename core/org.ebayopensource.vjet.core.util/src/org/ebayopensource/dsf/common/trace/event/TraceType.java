/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.trace.event;

import org.ebayopensource.dsf.common.exceptions.DsfRuntimeException;

public class TraceType {
	
	public static TraceType ENTER_METHOD = new TraceType("ENTER_METHOD");
	public static TraceType EXIT_METHOD = new TraceType("EXIT_METHOD");
	
//	ENTER_BRANCH,
//	EXIT_BRANCH,
	
	public static TraceType OBJECT_TYPE = new TraceType("OBJECT_TYPE");
	public static TraceType OBJECT_STATE = new TraceType("OBJECT_STATE");
	public static TraceType DATAMODEL = new TraceType("DATAMODEL");
	
	public static TraceType NV = new TraceType("NV");
	public static TraceType MSG = new TraceType("MSG");
	
	public static TraceType START_CALL = new TraceType("START_CALL");
	public static TraceType END_CALL = new TraceType("END_CALL");
	
	public static TraceType START_LOOP = new TraceType("START_LOOP");
	public static TraceType END_LOOP = new TraceType("END_LOOP");
	
	private String m_name;
	protected TraceType(String name){
		if (name == null){
			throw new DsfRuntimeException("name cannot be null");
		}
		m_name = name;
	}
	
	public String name(){
		return m_name;
	}
	
	public static TraceType parse(String name){
		if (name == null){
			return null;
		}
		else if (name.equals(ENTER_METHOD.name())){
			return ENTER_METHOD;
		}
		else if (name.equals(EXIT_METHOD.name())){
			return EXIT_METHOD;
		}
//		else if (name.equals(ENTER_BRANCH.name())){
//			return ENTER_BRANCH;
//		}
//		else if (name.equals(EXIT_BRANCH.name())){
//			return EXIT_BRANCH;
//		}
		else if (name.equals(OBJECT_TYPE.name())){
			return OBJECT_TYPE;
		}
		else if (name.equals(OBJECT_STATE.name())){
			return OBJECT_STATE;
		}else if (name.equals(DATAMODEL.name())){
			return DATAMODEL;
		}
		else if (name.equals(MSG.name())){
			return MSG;
		}
		else if (name.equals(NV.name())){
			return NV;
		}
		else if (name.equals(START_CALL.name())){
			return START_CALL;
		}
		else if (name.equals(END_CALL.name())){
			return END_CALL;
		}
		else if (name.equals(START_LOOP.name())){
			return START_LOOP;
		}
		else if (name.equals(END_LOOP.name())){
			return END_LOOP;
		}
		
		return null;
	}
}
