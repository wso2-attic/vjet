/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.trace;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Node;

import org.ebayopensource.dsf.DSFRootAnchor;
import org.ebayopensource.dsf.dom.DAttr;
import org.ebayopensource.dsf.dom.DElement;
import org.ebayopensource.dsf.common.xml.IXmlStreamWriter;

/**
 * HtmlTraceInstrumenter to provide the instrumentation for each node
 * 
 *
 */
public class HtmlTraceInstrumenter implements IInstrumentDElement{
		
	private Map<String, Integer> m_traceback = new HashMap<String, Integer>();
		
	public HtmlTraceInstrumenter (Map htmlTraceMap) {
		m_traceback = htmlTraceMap;
	}
	
	/*
	 * For each add/appendChild this method is called. Construct the TraceBack table info 
	 * by obtaining the Stack Trace path.
	 */
	public void appendElement(Node parent, Node node) {
		if (node.getClass().getName().startsWith(
				DSFRootAnchor.class.getPackage().getName())) {

			// Omit node objects that are other than Element node which are not
			// "interesting".
			if (node.getNodeType() != Node.ELEMENT_NODE) {
				return;
			}

			// Omit DContent objects as they happen to self-render but are not
			// "interesting".
			if (node.getClass().getName().equals(
					"org.ebayopensource.dsf.resource.content.runtime.DContent")) {
				return;
			}

			StackTraceElement trace[] = Thread.currentThread().getStackTrace();

			int caller = trace.length - 1;
			while (caller >= 0) {
				if (!trace[caller].getMethodName().startsWith("appendChild")
						&& !trace[caller].getMethodName().startsWith("add")) {
					caller--;
					continue; // still appendChild/add, keep backing up
				} else {
					StringBuffer spyTr = new StringBuffer();
					String key = null;
					int traceCount = 0;
					
					//Add the index info to TraceBack table
					for (int i = caller + 1; i < trace.length; i++) {

						traceCount++;
						if (traceCount > 15) {
							break;
						}

						key = trace[i].getClassName() + ":"
								+ trace[i].getMethodName() + ":"
								+ trace[i].getLineNumber();
						if (m_traceback.containsKey(key)) {
							spyTr.append(m_traceback.get(key).toString());
						} else {
							m_traceback.put(key,
									new Integer(m_traceback.size()));
							spyTr.append(m_traceback.get(key).toString());
						}
						if (i < trace.length - 1) {
							spyTr.append(" ");
						}
					}
					
					//Dump the spyTr index for each node
					DElement de = (DElement) node;
					de.setAttribute("spyTr", spyTr.toString());
					return;
				}
			}
		}

	}
	
	public void setAttributeValue(DElement node, DAttr attr, String value) {
		// no-op
	}

	public void endElement(DElement node, IXmlStreamWriter writer) {
		// TODO Auto-generated method stub
		
	}

	public void endSelfRender(DElement node, IXmlStreamWriter writer) {
		// TODO Auto-generated method stub
		
	}

	public void startElement(DElement node, IXmlStreamWriter writer) {
		// TODO Auto-generated method stub
		
	}

	public void startSelfRender(DElement node, IXmlStreamWriter writer) {
		// TODO Auto-generated method stub
		
	}

}
