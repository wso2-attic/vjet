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
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstConstructor;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.dsf.jst.declaration.JstTypeReference;
import org.ebayopensource.dsf.jst.term.JstIdentifier;
import org.ebayopensource.vjet.eclipse.codeassist.CodeassistUtils;
import org.ebayopensource.vjet.eclipse.internal.codeassist.select.JstNodeDLTKElementResolver;

/**
 * Visitor for finding type's occurrence
 * 
 *  Ouyang
 * 
 */
public class VjoTypeOccurrenceVisitor extends AbstractVjoOccurrenceVisitor {

	public VjoTypeOccurrenceVisitor(IJstType jstType) {
		super(jstType);

		setMatchName(jstType.getSimpleName());
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
		IJstNode checkNode = null;
		if (jstBinding instanceof IJstType) {
			checkNode = CodeassistUtils.lookupJstType((IJstType) jstBinding);
		} else {
			return;
		}
		if (matchNode(checkNode)) {
			addMatch(node);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ebayopensource.vjet.eclipse.core.ts.GenericVisitor#visit(org.ebayopensource.dsf.jst.declaration.JstType)
	 */
	@Override
	public void visit(JstType node) {
		super.visit(node);
		if (matchNode(node)) {
			addMatch(node);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ebayopensource.vjet.eclipse.core.ts.GenericVisitor#visit(org.ebayopensource.dsf.jst.declaration.JstTypeReference)
	 */
	@Override
	public void visit(JstTypeReference node) {
		if (matchNode(node.getReferencedType())) {
			addMatch(node);
		}
	}

	/* (non-Javadoc)
	 * @see org.ebayopensource.vjet.eclipse.core.ts.GenericVisitor#visit(org.ebayopensource.dsf.jst.declaration.JstConstructor)
	 */
	@Override
	public void visit(JstConstructor node) {
		if(matchNode(node.getOwnerType())){
			addMatch(node.getName());
		}
		super.visit(node);
	}

}
