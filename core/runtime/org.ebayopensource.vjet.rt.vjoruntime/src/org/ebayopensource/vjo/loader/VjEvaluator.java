/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.loader;

import org.mozilla.mod.javascript.Context;
import org.mozilla.mod.javascript.Scriptable;

public class VjEvaluator {

	private static final long serialVersionUID = 1L;
	
	private static final String DEFAULT_NS_TOKEN = "Vj";
	
	private static boolean s_verbose = false;

	private final Context m_cx;
	
	//
	// Constructor
	//
	public VjEvaluator(final Context cx) {
		m_cx = cx;
	}
	
	public Object evaluate(final String name, final String source, final Scriptable scope){
		
		if(s_verbose){
			System.out.println("Evaluating: " + name); //KEEPME
		}
		
		try { 
			Object obj = m_cx.evaluateString(scope, source, name, 1, null);
			
			if(s_verbose){
				System.out.println("Evaluated: " + name); //KEEPME
			}
			
			return obj;
//			if (DapCtx.ctx().isActiveMode() && isJ2JType(clzName)) {
//				m_cx.evaluateString(m_scope, clzName + "=Packages." + clzName + ";", clzName, 1, null);
//			}
		} 
		catch (Exception e) {
			if (s_verbose){
				e.printStackTrace();
			}
			throw new RuntimeException(e);
		}
	}
}
