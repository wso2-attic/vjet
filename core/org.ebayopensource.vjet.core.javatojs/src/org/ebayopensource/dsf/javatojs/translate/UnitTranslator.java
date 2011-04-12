/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.translate;

import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;

import org.ebayopensource.dsf.jst.declaration.JstType;

/**
 * Provide translation functionality from AST (Java) to JST (JS/Vjo)
 */
public final class UnitTranslator extends BaseTranslator {
	
	//
	// API
	//
	public JstType processUnit(CompilationUnit cu){
		JstType jstType = TranslateHelper.Factory
			.createJstType(cu, null);
		processUnit(cu, jstType);
		return jstType;
	}
	/**
	 * Entry method for translation from Ast to Jst
	 * @param cu CompilationUnit
	 * @return JstVjo
	 */
	public JstType processUnit(CompilationUnit cu, JstType type){
		
		PackageTranslator pkgTranslator = getPackageTranslator();
		TypeTranslator typeTranslator = getTypeTranslator();
//		DataTypeTranslator dataTypeTranslator = getDataTypeTranslator();
		
        // Imports
//		List<ImportDeclaration> imports = cu.imports();
//		for(ImportDeclaration dec: imports){
//			dataTypeTranslator.processImport(dec, type);
//		}  
	
		// Package
        type.setPackage(pkgTranslator.getPackage(cu.getPackage().getName().toString()));
        
        if (getCtx().getTranslateInfo(type).getMode().hasImplementation()){
        	// Type
            List types = cu.types();
            AbstractTypeDeclaration astType;
            JstType sibling;
            for (int i=0; i<types.size(); i++){
            	if (types.get(i) instanceof AbstractTypeDeclaration) {
            		astType = (AbstractTypeDeclaration)types.get(i);
            		sibling = type.getSiblingType(astType.getName().toString());
            		if (sibling == null){
            			sibling = type;
            		}
            		else {
            			getCtx().getTranslateInfo(sibling).getMode().setImplementation();
            		}
            		typeTranslator.processType(astType, sibling);
            	} 
            	else {
            		getLogger().logUnhandledNode(this, (ASTNode)types.get(i), type);
            	}
            }
        }
        
        return type;
	}
}
