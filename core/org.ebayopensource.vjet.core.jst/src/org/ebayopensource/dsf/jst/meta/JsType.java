/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.meta;

import java.util.ArrayList;
import java.util.List;

public class JsType extends JsTypingMeta {
	
	private final String m_name;
	private boolean m_isTypeRef = false;
	private boolean m_aliasRef = false;
	private List<ArgType> m_args = new ArrayList<ArgType>();
	
	public JsType(Token type) {
		super(type);
		m_name = type.image;
	}
	
	//refactored by huzhou to identify array type with dimension information only
	public String getType() {
//		int dimensions = getDimension();
//		StringBuffer postFix = null;
//		if (dimensions > 0) {
//			postFix = new StringBuffer();
//			for (int i = 0; i < dimensions; i++) {
//				postFix.append("[]");
//			}
//		}
		return //((isTypeRef()) ? JstTypeRefType.TYPE : "") + 
			m_name; 
//			+ dimensions > 0 ? postFix.toString() : "");
	}
	
	public boolean isGeneric() {
		return (m_args.size()>0);
	}
	
	public boolean isTypeRef() {
		return m_isTypeRef;
	}
	
	public boolean isAliasRef() {
		return m_aliasRef;
	}


	public JsType setTypeRef(boolean typeRef) {
		m_isTypeRef = typeRef;
		return this;
	}
	
	public JsType setAliasRef(boolean alias) {
		m_aliasRef= alias;
		return this;
	}
	
	public void addArg(ArgType arg) {
		m_args.add(arg);
	}
	
	public List<ArgType> getArgs() {
		return m_args;
	}
	

}
