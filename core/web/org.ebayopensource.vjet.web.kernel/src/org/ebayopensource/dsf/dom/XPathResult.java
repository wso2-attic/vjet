/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dom;

import javax.xml.namespace.QName;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.NodeList;

import org.ebayopensource.dsf.common.exceptions.DsfRuntimeException;

public final class XPathResult {
	final DNode m_node ;
	final XPathExpression m_xpathExpr ;
	final String m_xpathExprString ;
	
	//
	// Constructor(s)
	//
	XPathResult(final DNode node, final String xpathExpressionString) {
		m_node = node ;
		m_xpathExpr = null ;
		m_xpathExprString = xpathExpressionString ;
	}
	
	XPathResult(final DNode node, final XPathExpression xpathExpression) {
		m_node = node ;
		m_xpathExpr = xpathExpression ;
		m_xpathExprString = null ;
	}
	
	//
	// API
	//

	public Object getValue(final QName retType) {
		return (Object)evalXPath(retType) ;
	}
	
	public DNode getNode() {
		Object o = evalXPath(XPathConstants.NODE) ;
		DNode node = (DNode)o ;
		return node ;
	}
	
	public NodeList getNodes() {
		Object o = evalXPath(XPathConstants.NODESET) ;
		NodeList list = (NodeList)o ;
		return list ;
	}
	
	public Boolean getBool() {
		return (Boolean)evalXPath(XPathConstants.BOOLEAN) ;
	}

	public String getStr() {
		return (String)evalXPath(XPathConstants.STRING) ;
	}

	public Number getNum() {
		return (Number)evalXPath(XPathConstants.NUMBER) ;
	}
	
	//
	// Private
	//
	private Object evalXPath(final QName retType) {
		return m_xpathExprString == null
			? evalXPath(m_xpathExpr, retType) 
			: evalXPath(m_xpathExprString, retType) ;
	}
	
	private Object evalXPath(
			final XPathExpression xpathExpression, final QName retType)
		{
			assertXPath(xpathExpression, retType);	
			try {
				return xpathExpression.evaluate(m_node, retType) ;
			}
			catch(XPathExpressionException e) {
				throw new DsfRuntimeException(e) ;
			}
		}

	private Object evalXPath(final String xpathExpression, final QName retType) {
		assertXPath(xpathExpression, retType);	
		
		XPathFactory factory = XPathFactory.newInstance() ;
		XPath xpath = factory.newXPath();
		try {
			XPathExpression expr = xpath.compile(xpathExpression) ;
			return expr.evaluate(m_node, retType) ;
		}
		catch(XPathExpressionException e) {
			throw new DsfRuntimeException(e) ;
		}
	}

	private void assertXPath(
		final Object xpathExpression,
		final QName retType)
	{
		if (xpathExpression == null) {
			throw new DsfRuntimeException("XPath expression must not be null") ;
		}
		if (retType == null) {
			throw new DsfRuntimeException("XPath expressions return type must not be null") ;
		}
		if (retType != XPathConstants.BOOLEAN
			&& retType != XPathConstants.NODE
			&& retType != XPathConstants.NODESET
			&& retType != XPathConstants.NUMBER
			&& retType != XPathConstants.STRING)
		{
			throw new DsfRuntimeException(
				"XPaths expression return type should be one of XPathConstants: BOOLEAN | NODE | NODESET | NUMBER | STRING") ;
		}
	}
}
