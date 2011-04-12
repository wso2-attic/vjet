/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/* Created on Dec 19, 2005 */
package org.ebayopensource.dsf.html.schemas;

import java.util.Iterator;

import org.ebayopensource.dsf.html.dom.HtmlTypeEnum;

public class ElementInfoAndData implements IElementInfo {
	final private IElementInfo m_elementInfo;
	final private IAttributeInfoMap m_attrMap;
	private Object m_data=null;
	public ElementInfoAndData(
		final IElementInfo elementInfo)
	{
		m_attrMap = new AttributeInfoMapImpl();
		m_elementInfo = elementInfo;
		for (final IAttributeInfo attrInfo:elementInfo){
			final IAttributeInfoAndData attrInfoAndData =
				new IAttributeInfoAndData() {
					// the compiler was naming that m_data in this class
					// was hiding the one in ElementInfoAndData.
					// I renamed it from m_data to m_data2.
					// TODO: investigate further what the intention is.
					private Object m_data2;
					public String getName() {
						return attrInfo.getName();
					}
					public String getDefaultValue() {
						return attrInfo.getDefaultValue();
					}
					public AttributeDefault getAttrDefault(){
						return attrInfo.getAttrDefault();
					}
					public AttributeDataType getDataType() {
						return attrInfo.getDataType();
					}
					public Object getData() {
						return m_data2;
					}
					public void setData(final Object data) {
						m_data2 = data;
					}
				};
			m_attrMap.put(attrInfoAndData);
		}
	}
	public HtmlTypeEnum getType() {
		return m_elementInfo.getType();
	}
	public boolean requireEndTag() {
		return m_elementInfo.requireEndTag();
	}
	public boolean requireStartTag() {
		return m_elementInfo.requireStartTag();
	}

	public IAttributeInfo getAttributeInfo(final String name) {
		return m_elementInfo.getAttributeInfo(name);
	}
	public IAttributeInfoAndData getAttributeInfoAndData(final String name) {
		return (IAttributeInfoAndData)m_attrMap.get(name);
	}
	public IContentModel getContentModel() {
		return m_elementInfo.getContentModel();
	}
	public Iterator<IAttributeInfo> iterator() {
		return m_attrMap.iterator();
	}
	public Object getData() {
		return m_data;
	}
	public void setData(final Object data) {
		m_data = data;
	}
}
