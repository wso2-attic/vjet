/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.ts.group;

import org.ebayopensource.dsf.ts.graph.IDependencyCollector;

public class Library<E> extends Group<E> {
	
	private String m_path;
	
	//
	// Constructor
	//
	public Library(final String name, final IDependencyCollector<E> builder){
		this(name, null, builder);
	}
	
	public Library(final String name, final String path, final IDependencyCollector<E> builder){
		super(name, builder);
		m_path = path;
	}
	
	//
	// API
	//	
	public String getPath(){
		return m_path;
	}
	
	public boolean isReadOnly() {
		return true;
	}
}
