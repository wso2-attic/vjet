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
import org.ebayopensource.dsf.jst.declaration.JstObjectLiteralType;
import org.ebayopensource.vjet.eclipse.codeassist.CodeassistUtils;
import org.ebayopensource.vjet.eclipse.core.IVjoSourceModule;
import org.ebayopensource.vjet.eclipse.core.VjetPlugin;
import org.eclipse.dltk.mod.core.IModelElement;
import org.eclipse.dltk.mod.core.IScriptProject;
import org.eclipse.dltk.mod.core.IType;
import org.eclipse.dltk.mod.core.ModelException;
import org.eclipse.dltk.mod.internal.core.ScriptProject;

/**
 * 
 * 
 */
public class JstObjectLiteralTypeTranslator extends DefaultNodeTranslator {

	public IModelElement[] convert(IJstNode jstNode) {
		if(!(jstNode instanceof JstObjectLiteralType)){
			return null;
		}
		
		IJstType jstType = (IJstType)jstNode.getParentNode();
		if (CodeassistUtils.isNativeType(jstType)) {
			IType type = CodeassistUtils.findNativeSourceType(jstType);
			return type != null ? new IModelElement[] { type }
			: new IModelElement[0];
		} else {
			IType rootDLTKType = CodeassistUtils
					.findType(jstType.getRootType());
			if (rootDLTKType == null)
				return null;

			IModelElement converted = this.getType(rootDLTKType, jstType.getName());
			final JstObjectLiteralType jstObjectLiteralType = (JstObjectLiteralType)jstNode;
			if(converted instanceof IType
					&& jstObjectLiteralType.getSimpleName() != null){
				IModelElement field = ((IType)converted).getField(jstObjectLiteralType.getSimpleName());
				converted = field != null ? field : converted;
			}
			return converted != null ? new IModelElement[] { converted }
			: new IModelElement[0];
			
		}
	}

	@Override
	public IModelElement[] convert(IVjoSourceModule module, IJstNode jstNode) {
		if(!(jstNode instanceof JstObjectLiteralType)){
			return null;
		}
		
		IJstType jstType = (IJstType)jstNode.getParentNode();
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
		
		final JstObjectLiteralType jstObjectLiteralType = (JstObjectLiteralType)jstNode;
		if(mElement instanceof IType
				&& jstObjectLiteralType.getSimpleName() != null){
			IModelElement field = ((IType)mElement).getField(jstObjectLiteralType.getSimpleName());
			mElement = field != null ? field : mElement;
		}
		
		return mElement != null ? new IModelElement[] { mElement }
		: new IModelElement[0];
//		return mElement;
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
