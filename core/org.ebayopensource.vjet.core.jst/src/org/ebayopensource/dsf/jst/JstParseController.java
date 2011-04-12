/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
//package org.ebayopensource.dsf.jst;
//
//import org.ebayopensource.dsf.jst.ts.JstTypeSpaceMgr;
//import org.ebayopensource.dsf.jst.util.JstExpressionBindingResolver;
//
//@Deprecated
//public class JstParseController implements IJstParseController {
//	
//	private IJstParser m_parser;
//	private IJstRefResolver m_resolver;	
//	private JstTypeSpaceMgr m_tsMgr;
//	
//	public JstParseController(IJstParser parser) {
//		m_parser = parser;
//		m_resolver = new JstExpressionBindingResolver(this);
//	}
//
//	public IJstType parse(String groupName, String fileName, String source) {
//		if (m_parser != null) {	
//			return m_parser.parse(groupName, fileName, source);
//		}
//		
//		return null;
//	}
//
//	public IJstType parseAndResolve(String groupName, String fileName,
//			String source) {
//		if (m_parser != null) {			
//			IJstType type = m_parser.parse(groupName, fileName, source);
//			m_resolver.resolve(type);		
//			return type;
//		}
//		
//		return null;
//	}
//
//	public void resolve(IJstType type) {
//		m_resolver.resolve(type);
//	}
//	
//	public void setJstParser(IJstParser parser) {
//		m_parser = parser;
//	}
//	
//	public void setRefResolver(IJstRefResolver resolver) {
//		m_resolver = resolver;
//	}
//	
//	public JstTypeSpaceMgr getJstTypeSpaceMgr() {
//		return m_tsMgr;
//	}
//
//	public void setJstTSMgr(JstTypeSpaceMgr jstTSMgr) {
//		m_tsMgr = jstTSMgr;
//	}
//
//}
