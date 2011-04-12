/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.tests.data.structure;

public class Cast {

	private long m_lValue = Long.MAX_VALUE;
	private int m_iValue = (int)m_lValue;
	
	public int getIntValue(long lValue){
		return (int)lValue;
	}
	
	public long getLongValue(int lValue){
		return (long)(lValue * 10);
	}
	
	public String getStringValue(Object oValue){
		return (String)oValue;
	}
}
