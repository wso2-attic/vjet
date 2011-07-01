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
public class JstTypeTranslator extends DefaultNodeTranslator {

	public IModelElement[] convert(IJstNode jstNode) {
		IJstType jstType = (IJstType) jstNode;
		if (CodeassistUtils.isNativeType(jstType)) {
			IType type= CodeassistUtils.findNativeSourceType(jstType);
			return type != null ? new IModelElement[] { type }
			: new IModelElement[0];
		} else {
			IType rootDLTKType = CodeassistUtils
					.findType(jstType.getRootType());
			if (rootDLTKType == null)
				return null;

			IType type = this.getType(rootDLTKType, jstType.getName());
			return type != null ? new IModelElement[] { type }
			: new IModelElement[0];
		}
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
			IModelElement[] elements = convert(jstNode);
			if(elements!=null){
				mElement = elements[0];
			}
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
