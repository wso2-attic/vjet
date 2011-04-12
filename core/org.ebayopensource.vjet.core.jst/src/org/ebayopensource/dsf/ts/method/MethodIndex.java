/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.ts.method;

import org.ebayopensource.dsf.ts.index.DependencyIndexMap;
import org.ebayopensource.dsf.ts.index.IDependencyIndexMap;

/**
 * FIXME: what is the difference in this class responsibilities to PropertyIndex and why to keep this completely generic?
 *
 * @param <T>
 * @param <D>
 */
public class MethodIndex<T,D> extends DependencyIndexMap<D> implements IDependencyIndexMap<D> {

	private T m_type;
	
	//
	// Constructor
	//
	/**
	 * Constructor
	 * @param type T
	 */
	public MethodIndex(final T type){
		m_type = type;
	}
	
	//
	// API
	//
	public T getType(){
		return m_type;
	}
}
