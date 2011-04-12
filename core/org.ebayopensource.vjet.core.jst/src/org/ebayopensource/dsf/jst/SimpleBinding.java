/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst;

import org.ebayopensource.dsf.jst.JstSource.IBinding;


public class SimpleBinding implements IBinding {
	
	private String m_name;
	private String m_text;
	
	//
	// Constructor
	//
	public SimpleBinding(final String text){
		this(null, text);
	}

	public SimpleBinding(final String name, final String text){
		m_name = name;
		m_text = text;
	}

	//
	// Satisfy IBinding
	//
	public String getName(){
		return m_name;
	}
	
	public String toText(){
		return m_text;
	}
	
	@Override
	public String toString(){
		return m_text;
	}
}
