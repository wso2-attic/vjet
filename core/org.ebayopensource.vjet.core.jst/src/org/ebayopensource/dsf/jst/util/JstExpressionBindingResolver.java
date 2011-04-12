/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
//package org.ebayopensource.dsf.jst.util;
//
//import org.ebayopensource.dsf.jst.IJstRefResolver;
//import org.ebayopensource.dsf.jst.IJstType;
//import org.ebayopensource.dsf.jst.JstParseController;
//import org.ebayopensource.dsf.jst.traversal.JstDepthFirstTraversal;
//
//@Deprecated
//public class JstExpressionBindingResolver implements IJstRefResolver {
//	
//	private final JstParseController m_controller;
//	
//	private final JstExpressionTypeLinker m_typeLinkerVisitor;
//	
//	public JstExpressionBindingResolver(JstParseController controller) {
//		m_controller = controller;
//		m_typeLinkerVisitor = new JstExpressionTypeLinker(this);
//	}
//
//	public void resolve(IJstType type) {
//		m_typeLinkerVisitor.setType(type);		
//		JstDepthFirstTraversal.accept(type, m_typeLinkerVisitor);
//	}
//	
//	public JstParseController getController() {
//		return m_controller;
//	}
//
//}
