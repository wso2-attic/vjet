/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.tool.codecompletion;

import java.util.ArrayList;
import java.util.List;

import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.ts.JstTypeSpaceMgr;
import org.ebayopensource.dsf.ts.type.TypeName;

public class VjoCcCtxForTest extends VjoCcCtx {
	
	private IJstMethod selectedMethod;
	private Boolean instatic;
	private Boolean hasNoPrifix;
	
	@Override
	public IJstMethod getSelectedJstMethod() {
		if (selectedMethod != null) {
			return selectedMethod;
		} else {
			return super.getSelectedJstMethod();
		}
	}
	
	@Override
	public List<IJstMethod> getSelectedJstMethods() {
		if (selectedMethod != null) {
			List<IJstMethod> methods = new ArrayList<IJstMethod>();
			methods.add(selectedMethod);
			//Jack: should not show argument in overloaded methods.
//			methods.addAll(selectedMethod.getOverloaded());
			return methods;
		} else {
			return super.getSelectedJstMethods();
		}
	}
	
	public void setSelectedJstMethod(IJstMethod method) {
		this.selectedMethod = method;
	}
	
	public VjoCcCtxForTest(JstTypeSpaceMgr jstTypeSpaceMgr, TypeName typeName) {
		super(jstTypeSpaceMgr, typeName);		
	}
	
	@Override
	public IJstType getActingType() {
		return super.getActingType();
	}
	
	@Override
	public IJstType getCalledType() {
		return super.getCalledType();
	}
	
	@Override
	public boolean isInStatic() {
		if (instatic == null) {
			return super.isInStatic();
		}
		return instatic;
	}
	
	public void setInStatic(boolean inStatic) {
		this.instatic = inStatic;
	}
	
	@Override
	public boolean hasNoPrefix(){
		if (hasNoPrifix == null) {
			return super.hasNoPrefix();
		}
		return hasNoPrifix;
	}
	
	public void setHasNoPrifix(boolean hasNoPrifix) {
		this.hasNoPrifix = hasNoPrifix;
	}

}