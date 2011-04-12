/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.core.search;

import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstProperty;
import org.ebayopensource.dsf.jst.declaration.JstArg;
import org.ebayopensource.dsf.jst.declaration.JstName;
import org.ebayopensource.dsf.jst.declaration.JstVars;
import org.ebayopensource.dsf.jst.term.JstIdentifier;
import org.ebayopensource.vjet.eclipse.codeassist.CodeassistUtils;
import org.ebayopensource.vjet.eclipse.internal.codeassist.select.JstNodeDLTKElementResolver;

/**
 * this visitor is for finding field's occurrences.
 * 
 *  Ouyang
 * 
 */
public class VjoFieldOccurrenceVisitor extends AbstractVjoOccurrenceVisitor {

	private boolean	m_isLocalVar;
	private boolean	m_isMethodArg;
	private boolean	m_isTypeProp;

	/**
	 * <li>field type is method arg, then match node should be JstArg;</li>
	 * <li>field type is local var, then match node should be AssignExp;</li>
	 * <li> field type is type prop, then match node should be JstProperty.</li>
	 * 
	 * @param fieldType
	 * @param matchNode
	 */
	public VjoFieldOccurrenceVisitor(int fieldType, IJstNode matchNode) {
		super(matchNode);

		m_isMethodArg = fieldType == VjoFieldSearcher.METHOD_ARG;
		m_isLocalVar = fieldType == VjoFieldSearcher.LOCAL_VAR;
		m_isTypeProp = fieldType == VjoFieldSearcher.TYPE_PROP;

		setMatchName(getFieldName());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ebayopensource.vjet.eclipse.core.ts.GenericVisitor#visit(org.ebayopensource.dsf.jst.declaration.JstArg)
	 */
	@Override
	public void visit(JstArg node) {
		if (!m_isMethodArg) {
			return;
		}
		if (!matchName(node.getName())) {
			return;
		}
		if (matchNode(node)) {
			addMatch(node);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ebayopensource.vjet.eclipse.core.ts.GenericVisitor#visit(org.ebayopensource.dsf.jst.term.JstIdentifier)
	 */
	@Override
	public void visit(JstIdentifier node) {
		if (!matchName(node.getName())) {
			return;
		}
		IJstNode jstBinding = JstNodeDLTKElementResolver.lookupBinding(node);

		boolean doMatch = false;
		if (m_isLocalVar && (jstBinding instanceof JstVars)) {
			doMatch = true;
		} else if (m_isMethodArg && (jstBinding instanceof JstArg)) {
			doMatch = true;
		} else if (m_isTypeProp && (jstBinding instanceof IJstProperty)) {
			doMatch = true;
		}

		boolean add = doMatch ? matchNode(jstBinding) : false;
		if (add) {
			addMatch(node);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ebayopensource.vjet.eclipse.core.ts.GenericVisitor#visit(org.ebayopensource.dsf.jst.declaration.JstName)
	 */
	@Override
	public void visit(JstName node) {
		if (!m_isTypeProp) {
			return;
		}
		if (!matchName(node.getName())) {
			return;
		}
		IJstNode parentNode = node.getParentNode();
		if (parentNode instanceof IJstProperty) {
			if (matchNode(parentNode)) {
				addMatch(node);
			}
		}
	}

	private String getFieldName() {
		String tempName = null;
		if (m_isLocalVar) {
			tempName = getLocalVariableName();
		} else if (m_isMethodArg) {
			tempName = getMethodArgName();
		} else {
			tempName = getTypePropName();
		}
		return tempName;
	}

	private String getLocalVariableName() {
		return CodeassistUtils.getFirstVariableName((JstVars) getMatchNode());
	}

	private String getMethodArgName() {
		return ((JstArg) getMatchNode()).getName();
	}

	private String getTypePropName() {
		return ((IJstProperty) getMatchNode()).getName().getName();
	}

}
