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
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.IJstTypeReference;
import org.ebayopensource.dsf.jst.JstSource;
import org.ebayopensource.dsf.jst.declaration.JstTypeReference;
import org.ebayopensource.dsf.jst.declaration.JstVars;
import org.ebayopensource.dsf.jst.term.JstIdentifier;
import org.ebayopensource.dsf.jst.traversal.IJstVisitor;
import org.ebayopensource.vjet.eclipse.codeassist.CodeassistUtils;
import org.ebayopensource.vjet.eclipse.internal.codeassist.select.JstNodeDLTKElementResolver;
import org.eclipse.dltk.mod.core.IModelElement;
import org.eclipse.dltk.mod.core.IType;
import org.eclipse.dltk.mod.internal.core.ScriptProject;

/**
 * Re-writen by kevin based on JstBiding
 * 
 * 
 * 
 */
public class VjoTypeReferencesVisitor implements IJstVisitor {
	private IType type;
	private List<VjoMatch> result;
	private IJstType dependentType;

	/**
	 * Create instance of this class with specified type, dependent type and
	 * list of the {@link VjoMatch} objects.
	 * 
	 * @param type
	 *            type for which perform search references in dependent type.
	 * @param dependentType
	 *            type in which perform search.
	 * @param result
	 *            list of the {@link VjoMatch} objects.
	 */
	public VjoTypeReferencesVisitor(IType type, IJstType dependentType, List<VjoMatch> result) {
		this.type = type;
		this.result = result;
		this.dependentType = dependentType;
	}

	/*
	 * (non-Javadoc)
	 * @see org.ebayopensource.dsf.jst.traversal.IJstVisitor#endVisit(org.ebayopensource.dsf.jst.IJstNode)
	 */
	public void endVisit(IJstNode node) {
	}

	/*
	 * (non-Javadoc)
	 * @see org.ebayopensource.dsf.jst.traversal.IJstVisitor#postVisit(org.ebayopensource.dsf.jst.IJstNode)
	 */
	public void postVisit(IJstNode node) {
	}

	/*
	 * (non-Javadoc)
	 * @see org.ebayopensource.dsf.jst.traversal.IJstVisitor#preVisit(org.ebayopensource.dsf.jst.IJstNode)
	 */
	public void preVisit(IJstNode node) {
	}

	/*
	 * visit leaf node to collect type references.
	 * 
	 * @see org.ebayopensource.dsf.jst.traversal.IJstVisitor#visit(org.ebayopensource.dsf.jst.IJstNode)
	 */
	public boolean visit(IJstNode node) {
		if (node instanceof JstTypeReference)
			this.processJstTypeReference((JstTypeReference)node);
		
		if (node instanceof JstIdentifier)
			this.processJstIdentifier((JstIdentifier)node);
		
        if (node instanceof JstVars)
            this.processJstTypeReference(((JstVars)node).getTypeRef());
        
        return true;
	}
	
	/**
	 * process jst identifier
	 * 
	 * @param identifier
	 * @return
	 */
	private void processJstIdentifier(JstIdentifier identifier) {
		//filter 'this' keyword
		if (identifier.getSource() == null || identifier.getJstBinding() == null)
			return;
		
		//filter identifier name
		if (!identifier.getName().equals(this.type.getElementName()))
			return;
		
		if (CodeassistUtils.isNativeType(this.dependentType)) 
			return;
		
		/**
		 * for inner source type, just search outer type is OK
		 */
		IType sourceType = CodeassistUtils.findType(this.dependentType);
		if (sourceType == null)
			return;
		
		//directly resolve the identifier node to IType
		int offset = identifier.getSource().getStartOffSet();
		IModelElement[] elements = JstNodeDLTKElementResolver.lookupAndConvert(identifier);
		if(elements!=null && elements.length>0){
		IModelElement modelElement = elements[0];
			if (modelElement instanceof IType) {
				IType resolvedType = (IType)modelElement;
				if (resolvedType.getFullyQualifiedName().equals(this.type.getFullyQualifiedName()))
					this.createMatch(identifier);
			}
		}
	}
	
	/**
	 * process IJstTypeReference: including declaration, argument type, local var type
	 * 
	 * @param typeReference
	 * @return
	 */
	private void processJstTypeReference(IJstTypeReference typeReference) {
		if (typeReference.getSource() == null)
			return;
		
		IJstType jstType = typeReference.getReferencedType();
		this.processTypeMatch(jstType, typeReference);
	}
	
	/**
	 * compare type simple name, if pass, create the corresponding vjo match.
	 * TODO: maybe later should compare package qualified name
	 * 
	 * @param targetType
	 * @param node
	 */
	private void processTypeMatch(IJstType opitionType, IJstNode node) {
		//param check
		if (opitionType == null || opitionType.getName() == null)
			return;
		
		String targetTypeName = this.type.getElementName();
		boolean isTypeMatch = opitionType.getSimpleName().equals(targetTypeName);
		if (isTypeMatch)
			this.createMatch(node);
	}
	
	/**
	 * create vjo match for the jst node with JstSource
	 * 
	 * @param jstNode
	 */
	private void createMatch(IJstNode jstNode) {
		IType dltkType = CodeassistUtils.findType((ScriptProject)type.getScriptProject(), this.dependentType.getRootType());
		if (dltkType == null)
			return;
		
		JstSource jstSource = jstNode.getSource();
		VjoMatch match = VjoMatchFactory.createTypeMatch(dltkType, jstSource.getStartOffSet(), jstSource.getLength());
		this.result.add(match);
	}
}
