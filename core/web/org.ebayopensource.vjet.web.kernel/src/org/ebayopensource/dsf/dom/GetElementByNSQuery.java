/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dom;

import org.w3c.dom.Node;

import org.ebayopensource.dsf.common.node.visitor.AbortDNodeTraversalException;
import org.ebayopensource.dsf.common.node.visitor.DNodeVisitStatus;
import org.ebayopensource.dsf.common.node.visitor.DefaultDNodeVisitor;

/**
 * Visitor to help traveral DOM tree to get Elements that match 
 * the specified uri and local name
 *
 */
public class GetElementByNSQuery extends DefaultDNodeVisitor {
	private DNodeList m_answer = null;
	private String m_uri = null;
	private String m_localName = null;
	private boolean m_enableNS = false;
	//public int m_count = 0;
	public GetElementByNSQuery(final DNodeList nodeList, final String uri, final String localName){
		m_answer = nodeList;
		m_uri = uri;
		m_localName = localName;
		m_enableNS = uri !=null;
	}
	//public int m_count = 0 ;
	public DNodeVisitStatus visit(DNode node) throws AbortDNodeTraversalException {
		if (node.getNodeType() !=  Node.ELEMENT_NODE) {
			return DNodeVisitStatus.CONTINUE ;	
		}
		//System.out.println(node.getNodeName());
		if (!m_enableNS) {
		    if (m_localName.equals("*") || (node.getNodeName() != null && node.getNodeName().equals(m_localName))) {
		    	m_answer.privateAdd(node);
		    }
		} else {
		    // DOM2: Namespace logic. 
		    if (m_localName.equals("*")) {
				if (m_uri != null && m_uri.equals("*")) {
					m_answer.privateAdd(node);
				} else {					  
				    if ((m_uri == null && node.getNamespaceURI() == null)
					|| (m_uri != null && m_uri.equals(node.getNamespaceURI())))
				    {
				    	m_answer.privateAdd(node);
				    }
				}
		    } else {			    	
				if (node.getLocalName() != null && node.getLocalName().equals(m_localName)) {
				    if (m_uri != null && m_uri.equals("*")) {
				    	m_answer.privateAdd(node);
				    } else {
						if ((m_uri == null && node.getNamespaceURI() == null)
						    || (m_uri != null && m_uri.equals(node.getNamespaceURI())))
						{
							m_answer.privateAdd(node);
						}
				    }
				}
		    }
		}
		//m_count++ ;
		
		//System.out.println(m_count + ": " + node.getNodeName()) ;
		return DNodeVisitStatus.CONTINUE ;			
	}
}

