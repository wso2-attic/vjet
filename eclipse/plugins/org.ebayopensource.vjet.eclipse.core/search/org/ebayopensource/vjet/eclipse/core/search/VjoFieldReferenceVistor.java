/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/**
 * 
 */
package org.ebayopensource.vjet.eclipse.core.search;

import java.util.List;

import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.JstSource;
import org.ebayopensource.dsf.jst.declaration.JstArg;
import org.ebayopensource.dsf.jst.declaration.JstVars;
import org.ebayopensource.dsf.jst.term.JstIdentifier;
import org.ebayopensource.dsf.jst.traversal.IJstVisitor;
import org.ebayopensource.vjet.eclipse.codeassist.CodeassistUtils;
import org.eclipse.dltk.mod.core.Flags;
import org.eclipse.dltk.mod.core.IField;
import org.eclipse.dltk.mod.core.IMethod;
import org.eclipse.dltk.mod.core.IModelElement;
import org.eclipse.dltk.mod.core.IType;
import org.eclipse.dltk.mod.core.ModelException;
import org.eclipse.dltk.mod.internal.core.ScriptProject;

/**
 * vjo field reference visitor(local var/ method argument/ type property)
 * 
 * 
 *
 */
public class VjoFieldReferenceVistor implements IJstVisitor {
	private IField field;
	private IJstNode searchNode;
	private List<VjoMatch> result;
	
	private boolean isMethodArgument;
	private boolean isLocalVar;
	private boolean isTypeProperty;
	
	/**
	 * @param field         js source field
	 * @param dependentType search scope
	 * @param result        vjo match result
	 */
	public VjoFieldReferenceVistor(IField field, IJstNode searchNode, List<VjoMatch> result) {
		this.field = field;
		this.searchNode = searchNode;
		this.result = result;
		
		//reference search type
		this.isMethodArgument = this.isMethodArgument();
		this.isLocalVar = this.isLocalVar();
		this.isTypeProperty = this.isTypeProperty();
	}
	

	/* (non-Javadoc)
	 * @see org.ebayopensource.dsf.jst.traversal.IJstVisitor#endVisit(org.ebayopensource.dsf.jst.IJstNode)
	 */
	@Override
	public void endVisit(IJstNode node) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.ebayopensource.dsf.jst.traversal.IJstVisitor#postVisit(org.ebayopensource.dsf.jst.IJstNode)
	 */
	@Override
	public void postVisit(IJstNode node) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.ebayopensource.dsf.jst.traversal.IJstVisitor#preVisit(org.ebayopensource.dsf.jst.IJstNode)
	 */
	@Override
	public void preVisit(IJstNode node) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.ebayopensource.dsf.jst.traversal.IJstVisitor#visit(org.ebayopensource.dsf.jst.IJstNode)
	 */
	@Override
	public boolean visit(IJstNode node) {
		if (this.isMethodArgument && node instanceof JstIdentifier)
			this.processMethodArgumentReference((JstIdentifier)node);
		
		if (this.isLocalVar && node instanceof JstIdentifier)
			this.processLocalVarReference((JstIdentifier)node);
		
		if (this.isTypeProperty && node instanceof JstIdentifier)
			this.processTypePropertyReference((JstIdentifier)node);
		
		return true;
	}
	
	private boolean isMethodArgument() {
		if (!(this.field.getParent() instanceof IMethod))
			return false;
		
		try {
			IMethod method = (IMethod)field.getParent();
			String[] parameters = method.getParameters();
			for (int i = 0; i < parameters.length; i++) {
				if (this.field.getElementName().equals(parameters[i]))
					return true;
			}
			return false;
		} catch (ModelException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private boolean isLocalVar() {
		if (this.field.getParent().getElementType() == IModelElement.METHOD)
			return true;
		else
			return false;
	}
	
	private boolean isTypeProperty() {
		if (this.field.getParent().getElementType() == IModelElement.TYPE)
			return true;
		else
			return false;
	}
	
	private void processMethodArgumentReference(JstIdentifier jstIdentifier) {
		if (jstIdentifier.getJstBinding() instanceof JstArg) {
			String argName = jstIdentifier.getName();
			if (argName.equals(this.field.getElementName()))
				this.createMatch(jstIdentifier);
		}
	}
	
	private void processLocalVarReference(JstIdentifier jstIdentifier) {
		if (jstIdentifier.getJstBinding() instanceof JstVars) {
			String varName = jstIdentifier.getName();
			if (varName.equals(this.field.getElementName()))
				this.createMatch(jstIdentifier);
		}
	}
	
	//directly crate corresponding vjo match, the jstidenfier is from TypeSpaceMgr.getPropertyDependents
	private void processTypePropertyReference(JstIdentifier jstIdentifier) {
		this.createMatch(jstIdentifier);
	}
	
	/**
	 * create vjo match for the jst node with JstSource
	 * 
	 * @param jstNode
	 */
	private void createMatch(IJstNode jstNode) {
		IType dltkType = CodeassistUtils.findType(this.searchNode.getRootType());
		if (dltkType == null)
			return;
		
		JstSource jstSource = jstNode.getSource();
		//Check if the file is visible in the result project's build path
		IType declareType = CodeassistUtils.findType((ScriptProject)dltkType.getScriptProject(), field.getDeclaringType().getFullyQualifiedName());
		if (declareType == null) {
			return;
		}
		VjoMatch match = VjoMatchFactory.createTypeMatch(dltkType, jstSource.getStartOffSet(), jstSource.getLength());
		try {
			match.setIsPublic(Flags.isPublic(field.getFlags()));
			match.setIsStatic(Flags.isStatic(field.getFlags()));
		} catch (ModelException e) {
			e.printStackTrace();
		} finally {
			this.result.add(match);
		}
	}

}
