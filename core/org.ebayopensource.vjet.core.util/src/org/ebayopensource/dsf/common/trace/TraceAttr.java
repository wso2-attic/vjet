/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.trace;

import org.ebayopensource.dsf.common.Z;

public class TraceAttr implements ITraceData {
	private String m_name;
	private String m_value;
	
	public TraceAttr(String name, String value){
		m_name = name;
		m_value = value;
	}
	
	public String getName(){
		return m_name;
	}
	
	public String getValue(){
		return m_value;
	}
	
	@Override
	public String toString(){
		Z z = new Z();
		z.format("m_name", m_name);
		z.format("m_value", m_value);
		return z.toString();
	}
}
