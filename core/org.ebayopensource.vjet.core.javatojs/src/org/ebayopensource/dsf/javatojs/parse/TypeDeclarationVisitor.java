/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.parse;

import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import org.ebayopensource.dsf.javatojs.translate.TranslationMode;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstType;

/**
 * A shollow visitor for finding non-private declarations 
 * and direct dependency.
 * It's not for full-translation
 */
public class TypeDeclarationVisitor extends BaseTypeVisitor {
	
	//
	// Constructors
	//
	public TypeDeclarationVisitor(final JstType type) {
		super(type, new TranslationMode().addDeclaration());
	}
	
	//
	// Override ASTVisitor
	//
	@Override
	public boolean visit(TypeDeclaration node) {
		
		if (Modifier.isPrivate(node.getModifiers())){
			return false;
		}
		
		if (!super.visit(node)){
			return false;
		}
		
		// Extends
		IJstType baseType = getCtx().getTranslateInfo(getCurType()).getBaseType();
        if (baseType != null){
	        addDependency(baseType, node.getSuperclassType());
       }
	
		return true;
	}
	
	@Override
	public boolean visit(EnumDeclaration node) {

		if (Modifier.isPrivate(node.getModifiers())){
			return false;
		}
	
		return super.visit(node);
	}
	
	@Override
	public boolean visit(FieldDeclaration node) {
		
		if (Modifier.isPrivate(node.getModifiers())){
			return false;
		}
	
		return super.visit(node);
	}
	
	@Override
	public boolean visit(MethodDeclaration node) {
		if (Modifier.isPrivate(node.getModifiers())){
			return false;
		}
	
		return super.visit(node);
	}
}
