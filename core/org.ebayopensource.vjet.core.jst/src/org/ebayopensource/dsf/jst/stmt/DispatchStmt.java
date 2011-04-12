/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.stmt;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.ebayopensource.dsf.jst.declaration.JstArg;
import org.ebayopensource.dsf.jst.declaration.JstMethod;
import org.ebayopensource.dsf.jst.traversal.IJstNodeVisitor;

public class DispatchStmt extends IfStmt {
	
	private static final long serialVersionUID = 1L;
	Map<Integer,List<JstMethod>> m_mtds = new LinkedHashMap<Integer,List<JstMethod>>();
	
	//
	// Constructor
	//
	public DispatchStmt(){
	}
	
	//
	// API
	//
	public void addMethod(final JstMethod mtd){
		if (mtd == null){
			return;
		}
		List<JstArg> args = mtd.getArgs();
		Integer key = new Integer(args.size());
		List<JstMethod> list = m_mtds.get(key);
		if (list == null){
			list = new ArrayList<JstMethod>();
			m_mtds.put(key, list);
		}
		list.add(mtd);
	}
	
	public Map<Integer,List<JstMethod>> getMethods() {
		return m_mtds;
	}

	@Override
	public void accept(IJstNodeVisitor visitor){
		visitor.visit(this);
	}
	
}
