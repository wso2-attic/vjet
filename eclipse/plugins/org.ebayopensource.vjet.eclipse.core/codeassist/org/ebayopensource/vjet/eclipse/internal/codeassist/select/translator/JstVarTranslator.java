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
package org.ebayopensource.vjet.eclipse.internal.codeassist.select.translator;

import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstVar;
import org.ebayopensource.vjet.eclipse.codeassist.CodeassistUtils;
import org.ebayopensource.vjet.eclipse.core.IVjoSourceModule;
import org.ebayopensource.vjet.eclipse.internal.codeassist.select.JstNodeDLTKElementResolver;
import org.eclipse.dltk.mod.core.IModelElement;
import org.eclipse.dltk.mod.core.IType;

/**
 * 
 *
 */
public class JstVarTranslator extends DefaultNodeTranslator {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ebayopensource.vjet.eclipse.internal.codeassist.select.translator.DefaultNodeTranslator#convert(org.ebayopensource.dsf.jst.IJstNode)
	 */
	@Override
	public IModelElement[] convert(IVjoSourceModule module, IJstNode node) {
		JstVar jstVar = (JstVar) node;
		IJstType rootType = node.getRootType();
		IModelElement element = JstNodeDLTKElementResolver.convert(module, rootType)[0];
		if (IModelElement.TYPE != element.getElementType()) {
			return null;
		}
		IType dltkType = (IType) element;

		IJstType jstType = jstVar.getType();
		String typeName = jstType != null ? jstType.getName() : "Object";

		IModelElement[] localVar = CodeassistUtils.getLocalVar(
				(org.eclipse.dltk.mod.compiler.env.ISourceModule) dltkType
						.getSourceModule(), jstVar.getName(), typeName, jstVar
						.getSource());
		
		return localVar != null ?  localVar 
		: new IModelElement[0];
		
	}

}
