/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.trace;

import java.util.ArrayList;

public class ContentTraceInfo {
	private String m_nameSpace;
	private ArrayList<String> m_attributes;
	private ArrayList<String> m_texts;
	
	public ContentTraceInfo() {
		m_nameSpace = "";
		m_attributes = new ArrayList<String>();
		m_texts = new ArrayList<String>();
		
	}
	
	public void setNameSpace(String ns) {
		m_nameSpace = ns;
	}
	
	public String getNameSpace() {
		return m_nameSpace;
	}
	
	public void setAttributeInstrument(String attr, String instrument) {
		if (instrument != null && instrument.length() > 0) {
			String attrInfo = attr + ":" + instrument.trim();
			m_attributes.add(attrInfo);
		}
	}
	
	public void setTextInstrument(String instrument) {
		if (instrument != null && instrument.length() > 0) {
			m_texts.add(instrument.trim());
		}
	}
	
	public String getAttrInsList() {
		StringBuilder list = new StringBuilder();
		
		for (int i = 0; i < m_attributes.size(); i++) {
			list.append(m_attributes.get(i));
			if (i < m_attributes.size() -1)
				list.append(",");
		}
		
		return list.toString();
	}
	
	public String getTextInsList() {
		StringBuilder list = new StringBuilder();
		
		for (int i = 0; i < m_texts.size(); i++) {
			list.append(m_texts.get(i));
			if (i < m_texts.size() -1)
				list.append(",");
		}
		
		return list.toString();
	}
}
