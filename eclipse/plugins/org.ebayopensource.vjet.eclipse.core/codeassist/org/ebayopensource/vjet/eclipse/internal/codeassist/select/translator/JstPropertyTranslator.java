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
import org.ebayopensource.dsf.jst.IJstProperty;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.SynthOlType;
import org.ebayopensource.vjet.eclipse.codeassist.CodeassistUtils;
import org.ebayopensource.vjet.eclipse.core.IVjoSourceModule;
import org.ebayopensource.vjet.eclipse.internal.codeassist.select.JstNodeDLTKElementResolver;
import org.eclipse.dltk.mod.core.IField;
import org.eclipse.dltk.mod.core.IModelElement;
import org.eclipse.dltk.mod.core.IType;

/**
 * 
 * 
 */
public class JstPropertyTranslator extends DefaultNodeTranslator {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ebayopensource.vjet.eclipse.internal.codeassist.select.translator.DefaultNodeTranslator#convert(org.ebayopensource.dsf.jst.IJstNode)
	 */
	@Override
	public IModelElement[] convert(IVjoSourceModule module, IJstNode node) {
		IJstProperty jstProperty = (IJstProperty) node;
		IJstType ownerType = jstProperty.getOwnerType();
		IModelElement[] elements = JstNodeDLTKElementResolver.convert(module, ownerType);
		
		if(elements.length==0 & ownerType instanceof SynthOlType){
			
			CodeassistUtils.findDeclaringBlock(node);
			
			elements = JstNodeDLTKElementResolver.convert(module, module.getJstType());
			IType dltkType = (IType)elements[0];
			String name = jstProperty.getName().getName();
			IField field = dltkType.getField(name);
//			IField field = 	return new JSSourceField(dltkType, name);
			return field != null ? new IModelElement[] { field }
			: new IModelElement[0];
		}
		
		if(elements.length==0 || !(elements[0] instanceof IType)){
			return new IModelElement[0];
		}
		IType dltkType = (IType)elements[0];
		String name = jstProperty.getName().getName();
		IField field = dltkType.getField(name);
			
		return field != null ? new IModelElement[] { field }
		: new IModelElement[0];
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ebayopensource.vjet.eclipse.internal.codeassist.select.translator.DefaultNodeTranslator#resolveBinding(org.ebayopensource.dsf.jst.IJstNode)
	 */
	@Override
	public IJstNode lookupBinding(IJstNode jstNode) {
		IJstProperty property = (IJstProperty) jstNode;
		// "this.vj$.[Type]" handling
		if (CodeassistUtils.isVjDollarProp(property)) {
			return JstNodeDLTKElementResolver.lookupBinding(property.getType());
		}
		return jstNode;
	}

}
