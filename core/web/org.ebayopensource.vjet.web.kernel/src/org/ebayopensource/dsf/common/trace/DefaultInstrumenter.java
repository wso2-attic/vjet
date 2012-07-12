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
import java.util.Iterator;
import java.util.List;

import org.w3c.dom.Node;

import org.ebayopensource.dsf.dom.DAttr;
import org.ebayopensource.dsf.dom.DElement;
import org.ebayopensource.dsf.common.xml.IXmlStreamWriter;

/**
 * Default implementation for instrument handler management.
 * Instrumentation handlers can be  called during several phases of
 * Dom construction and rendering. Points you can hook into are:
 *   - start of appendChild to Dom tree
 *   - startElement render (just before element is rendered to Html)
 *   - endElement render (just after element has rendered to Html)
 *   - startSelfRenderElement (just before SelfRender element renders)
 *   - endSelfRenderElement (just after SelfRender element has rendered)
 *   
 *   Spyglass component tracing uses these hooks to augment the generated
 *   HTML extra attributes to be used with client-side JS to view the
 *   generated components in the Html Dom. 
 *   
 *   The runXXXX methods invoke each registered handler in turn with 
 *   the object of interest.
 *
 */
public class DefaultInstrumenter implements IDsfInstrumenter {
	
	private List<IInstrumentDElement> m_instrumentationHandlers;

	public boolean addInstrumenter(final IInstrumentDElement handler) {
		if (m_instrumentationHandlers == null) {
			m_instrumentationHandlers =  new ArrayList<IInstrumentDElement>(1);
			return m_instrumentationHandlers.add(handler);
		}
		else if (!m_instrumentationHandlers.contains(handler)) {
			return m_instrumentationHandlers.add(handler);
		}
		return false;
	}

	public void resetInstrumenter() {
		m_instrumentationHandlers = null;
	}
	
	public boolean hasHandler() {
		return m_instrumentationHandlers != null;
	}

	public void runStartInstrumenters(final DElement component, final IXmlStreamWriter writer) {
		if (!hasHandler()) {
			return;
		}
		for (int i = 0; i < m_instrumentationHandlers.size(); i++) {
			m_instrumentationHandlers.get(i).startElement(component, writer);
		}
	}

	public void runEndInstrumenters(final DElement component, final IXmlStreamWriter writer) {
		if (!hasHandler()) {
			return;
		}
		for (int i = 0; i < m_instrumentationHandlers.size(); i++) {
			m_instrumentationHandlers.get(i).endElement(component, writer);
		}
	}
	
	public void runStartSelfRenderInstrumenters(
		final DElement component, final IXmlStreamWriter writer)
	{
		if (!hasHandler()) {
			return;
		}
		for (int i = 0; i < m_instrumentationHandlers.size(); i++) {
			m_instrumentationHandlers.get(i).startSelfRender(component, writer);
		}
	}

	public void runEndSelfRenderInstrumenters(
		final DElement component, final IXmlStreamWriter writer)
	{
		if (!hasHandler()) {
			return;
		}
		for (int i = 0; i < m_instrumentationHandlers.size(); i++) {
			m_instrumentationHandlers.get(i).endSelfRender(component, writer);
		}
	}
	
	public void runAppendInstrumenters(final Node parent, final Node component) {
		if (!hasHandler()) {
			return;
		}
		for (int i = 0; i < m_instrumentationHandlers.size(); i++) {
			m_instrumentationHandlers.get(i).appendElement(parent, component);
		}
	}
	
	public void runAttributeInstrumenters(DElement component, DAttr attr, String value) {
		if (!hasHandler()) {
			return;
		}
		for (int i = 0; i < m_instrumentationHandlers.size(); i++) {
			m_instrumentationHandlers.get(i).setAttributeValue(component, attr, value);
		}
	}
	public boolean hasInstrumenter(Class clz) {
		if (!hasHandler()) {
			return false;
		}
		Iterator<IInstrumentDElement> i = m_instrumentationHandlers.iterator();
		while(i.hasNext()) {
			IInstrumentDElement instrumenter = i.next();
			if(instrumenter.getClass().equals(clz)) {
				return true;
			}
		}
		return false;
	}
}
	
	
