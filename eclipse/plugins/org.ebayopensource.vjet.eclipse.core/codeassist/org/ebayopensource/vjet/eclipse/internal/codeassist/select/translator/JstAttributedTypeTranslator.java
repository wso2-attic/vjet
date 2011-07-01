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

import java.lang.reflect.Field;

import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstAttributedType;
import org.ebayopensource.dsf.jst.declaration.JstProxyMethod;
import org.ebayopensource.dsf.jst.declaration.JstProxyProperty;
import org.ebayopensource.dsf.jst.declaration.JstProxyType;
import org.ebayopensource.dsf.jstojava.controller.JstExpressionBindingResolver;
import org.ebayopensource.dsf.jstojava.controller.JstExpressionTypeLinkerHelper;
import org.ebayopensource.dsf.jstojava.controller.JstParseController;
import org.ebayopensource.vjet.eclipse.codeassist.CodeassistUtils;
import org.ebayopensource.vjet.eclipse.core.IVjoSourceModule;
import org.ebayopensource.vjet.eclipse.core.VjetPlugin;
import org.ebayopensource.vjet.eclipse.core.parser.VjoParserToJstAndIType;
import org.eclipse.dltk.mod.core.IModelElement;
import org.eclipse.dltk.mod.core.IScriptProject;
import org.eclipse.dltk.mod.core.IType;
import org.eclipse.dltk.mod.core.ModelException;
import org.eclipse.dltk.mod.internal.core.ScriptProject;

/**
 * 
 * 
 */
public class JstAttributedTypeTranslator extends DefaultNodeTranslator {

	public IModelElement[] convert(IJstNode jstNode) {
		if (jstNode instanceof JstAttributedType){
			final JstAttributedType attributed  = (JstAttributedType)jstNode;
			final JstParseController ctrl = VjoParserToJstAndIType.getJstParseController();
			final JstExpressionBindingResolver resolver = getResolver(ctrl);
			if(resolver != null){
				final IJstNode attributedBinding = JstExpressionTypeLinkerHelper.look4ActualBinding(resolver, attributed);
				if(attributedBinding != null && attributedBinding != jstNode){
					return JstToDLTKNodeTranslator.getNodeTranslator(attributedBinding).convert(attributedBinding);
				}
			}
		}
		return null;
	}
	
	public static JstExpressionBindingResolver getResolver(final JstParseController ctrl){
		Field f;
		try {
			f = JstParseController.class.getDeclaredField("m_resolver");
			f.setAccessible(true);
			final Object r = f.get(ctrl);
			if(r instanceof JstExpressionBindingResolver){
				return (JstExpressionBindingResolver)r;
			}
		} catch(Throwable e) {
			return null;
		}
		return null;
	}
	
	@Override
	public IJstNode lookupBinding(IJstNode jstNode) {
		// default implementation
		if (jstNode instanceof JstAttributedType){
			final JstAttributedType attributed  = (JstAttributedType)jstNode;
			final JstParseController ctrl = VjoParserToJstAndIType.getJstParseController();
			final JstExpressionBindingResolver resolver = getResolver(ctrl);
			if(resolver != null){
				final IJstNode attributedBinding = JstExpressionTypeLinkerHelper.look4ActualBinding(resolver, attributed);
				if(attributedBinding != null && attributedBinding != jstNode){
					if(attributedBinding instanceof JstProxyProperty){
						return ((JstProxyProperty)attributedBinding).getTargetProperty();
					}
					else if(attributedBinding instanceof JstProxyMethod){
						return ((JstProxyMethod)attributedBinding).getTargetMethod();
					}
					else if(attributedBinding instanceof JstProxyType){
						return ((JstProxyType)attributedBinding).getType();
					}
					
					return attributedBinding;
				}
			}
		}
		return null;
	}

	@Override
	public IModelElement[] convert(IVjoSourceModule module, IJstNode jstNode) {
		IJstType jstType = (IJstType) jstNode;
		IScriptProject sProject = null;
		if (module != null) {
			sProject = module.getScriptProject();
		}
		IModelElement mElement = null;
		if (sProject != null && (!CodeassistUtils.isNativeType(jstType) // type
																		// in
																		// workspace
				|| CodeassistUtils // type in external source type
						.isBinaryType(jstType))) {
			mElement = CodeassistUtils.findType((ScriptProject) sProject,
					jstType);
		}
		if (mElement == null) {
			mElement = convert(jstNode)[0];
		}
		
		return mElement != null ? new IModelElement[] { mElement }
		: new IModelElement[0];
	}

	/**
	 * get corresponding type, including inner type
	 * 
	 * @param rootType
	 * @param dltkTypeName
	 * @return
	 */
	private IType getType(IType rootType, String dltkTypeName) {
		try {
			if (rootType.getFullyQualifiedName(".").equals(dltkTypeName))
				return rootType;
			else {
				IType[] types = rootType.getTypes();
				for (int i = 0; i < types.length; i++) {
					IType type = this.getType(types[i], dltkTypeName);
					if (type != null)
						return type;
				}
			}
		} catch (ModelException e) {
			VjetPlugin.error(e.getLocalizedMessage(), e);
		}
		return null;
	}
}
