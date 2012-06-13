/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.parser.comments;

import java.util.ArrayList;
import java.util.List;

import org.ebayopensource.dsf.jst.meta.JsTypingMeta;

public class JsParam {
	private String m_name;
	private List<JsTypingMeta> m_types = new ArrayList<JsTypingMeta>(4);
	private boolean m_isOptional = false;
	private boolean m_isVariable = false;
	private boolean m_isFinal = false;
	
	public JsParam(String name) {
		m_name = name;
	}
	
	public JsParam(String name, JsTypingMeta type) {
		m_name = name;
		addTyping(type);
	}
	
	public void addTyping(JsTypingMeta type) {
		m_types.add(type);
	}
	
	public String getName() {
		return m_name;
	}

	public String getType() {
		return m_types.get(0).getType();
	}
	
	public List<JsTypingMeta> getTypes() {
		return m_types;
	}
	
	//TODO: update offset logic to account for var/multiple args
	public int getBeginOffset() {
		if (m_types.isEmpty()) return 0;
		return m_types.get(0).getBeginOffset();
	}

	public int getEndOffset() {
		if (m_types.isEmpty()) return 0;
		return m_types.get(m_types.size()-1).getEndOffset();
	}
	
	public int getBeginColumn() {
		if (m_types.isEmpty()) return 0;
		return m_types.get(0).getBeginColumn();
	}

	public int getEndColumn() {
		if (m_types.isEmpty()) return 0;
		return m_types.get(m_types.size()-1).getEndColumn();
	}

	public boolean isOptional() {
		return m_isOptional;
	}

	public void setOptional(boolean isOptional) {
		m_isOptional = isOptional;
	}
	
	public boolean isFinal() {
		return m_isFinal;
	}

	public void setFinal(boolean isFinal) {
		m_isFinal = isFinal;
	}
	
	public boolean isVariable() {
		return m_isVariable;
	}

	public void setVariable(boolean isVariable) {
		m_isVariable = isVariable;
	}
}
