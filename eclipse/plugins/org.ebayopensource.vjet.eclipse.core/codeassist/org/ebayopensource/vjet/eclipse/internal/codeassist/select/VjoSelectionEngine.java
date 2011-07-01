/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.codeassist.select;

import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jstojava.translator.JstUtil;
import org.ebayopensource.vjet.eclipse.codeassist.CodeassistUtils;
import org.ebayopensource.vjet.eclipse.core.IVjoSourceModule;
import org.ebayopensource.vjo.tool.typespace.TypeSpaceMgr;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.dltk.mod.codeassist.IAssistParser;
import org.eclipse.dltk.mod.codeassist.ScriptSelectionEngine;
import org.eclipse.dltk.mod.compiler.env.ISourceModule;
import org.eclipse.dltk.mod.core.IModelElement;
import org.eclipse.dltk.mod.core.ModelException;

/**
 * Engine for processing the selection.
 * 
 * 
 * 
 */
public class VjoSelectionEngine extends ScriptSelectionEngine {

	private SelectionParser	m_parser;

	public VjoSelectionEngine() {
		super();
		this.m_parser = new SelectionParser();
	}

	/**
	 * Convert the given jst node to corresponding DLTK model elements. Or empty
	 * array if no corresponding element.
	 * 
	 * @param node
	 * @return
	 */
	public IModelElement[] convert(IVjoSourceModule module, IJstNode jstNode) {
		return  JstNodeDLTKElementResolver.convert(module, jstNode);
//		return element != null ? new IModelElement[] { element }
//				: new IModelElement[0];
	}

	public IJstNode convertSelection2JstNode(ISourceModule module, int startOffset,
			int endOffset) {
		IVjoSourceModule sourceModule = (IVjoSourceModule) module;
		IJstType jstType;
		jstType = sourceModule.getJstType();
		if (jstType == null) {
			IResource resource = sourceModule.getResource();
			if (resource == null || !resource.exists()
					|| !(resource instanceof IFile)) {
				jstType = CodeassistUtils.findNativeJstType(sourceModule
						.getElementName());
			} else {
				String typeName = CodeassistUtils
						.getClassName((IFile) sourceModule.getResource());
				jstType = TypeSpaceMgr.findType(sourceModule.getScriptProject()
						.getElementName(), typeName);
			}

		}

		// repair offset
		startOffset = this.repairOffset(sourceModule, startOffset);
		endOffset = startOffset;

		IJstNode selection = JstUtil.getLeafNode(jstType, startOffset,
				endOffset, true);
		IJstNode jstBinding = JstNodeDLTKElementResolver
				.lookupBinding(selection);
		return jstBinding;
	}

	@Override
	public IAssistParser getParser() {
		return this.m_parser;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.dltk.mod.codeassist.ISelectionEngine#select(org.eclipse.dltk.mod.compiler.env.ISourceModule,
	 *      int, int)
	 */
	public IModelElement[] select(ISourceModule module, int startOffset,
			int endOffset) {
		IJstNode jstNode = convertSelection2JstNode(module, startOffset, endOffset);
		IVjoSourceModule vjoModule = null;
		if (module instanceof IVjoSourceModule) {
			vjoModule = (IVjoSourceModule)module;
		}
		return convert(vjoModule, jstNode);
	}

	// if select
	private int repairOffset(IVjoSourceModule module, int offset) {
		if (offset == 0)
			return offset;

		try {
			char[] sourceChar = module.getSourceAsCharArray();
			;
			if (sourceChar == null || offset >= sourceChar.length) {
				return offset;
			}
			char selectedChar = sourceChar[offset];
			if (Character.isWhitespace(selectedChar))
				--offset;
		} catch (ModelException e) {
			e.printStackTrace();
		}

		return offset;
	}
}
