/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.ui.text.completion;

import java.text.MessageFormat;
import java.util.List;

import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.vjet.eclipse.core.IVjoSourceModule;
import org.ebayopensource.vjet.eclipse.internal.core.util.Util;
import org.eclipse.dltk.mod.core.DLTKCore;
import org.eclipse.dltk.mod.core.IBuffer;
import org.eclipse.dltk.mod.core.IType;
import org.eclipse.dltk.mod.core.ModelException;
import org.eclipse.dltk.mod.internal.core.VjoExternalSourceModule;
import org.eclipse.dltk.mod.internal.core.VjoSourceType;
import org.eclipse.text.edits.InsertEdit;
import org.eclipse.text.edits.MultiTextEdit;

/**
 * This class insert "needs('yyy')" string after "xtype('xxxx')" string when
 * completed static class.
 * 
 * 
 * 
 */
public class ImportRewriter {

	private static final String CLOSE_BRACKET = ")";
	private static final String TYPE = "type";
	private static final String VJO_IMPORT = "\r\n.needs(\"{0}\")";

	public MultiTextEdit rewrite(VjoSourceType type, IJstType ownerType) {

		MultiTextEdit edit = new MultiTextEdit();
		IVjoSourceModule module = (IVjoSourceModule) type.getSourceModule();

		String name = module.getTypeName().typeName();
		boolean isNative = module instanceof VjoExternalSourceModule;
		if (!isNative && !existImport(ownerType, name)
				&& !isOwnerType(ownerType, name)) {
			IType itype = Util.toIType(ownerType);
			addImport(itype, edit, name);
		}

		return edit;
	}

	private boolean isOwnerType(IJstType ownerType, String name) {
		return ownerType.getName().equals(name);
	}

	private boolean existImport(IJstType ownerType, String name) {
		boolean exist = false;
		List<? extends IJstType> list = ownerType.getImports();

		for (IJstType jstType : list) {
			if (name.equals(jstType.getName())) {
				exist = true;
				break;
			}
		}

		return exist;
	}

	private void addImport(IType type, MultiTextEdit edit, String name) {

		int index = getTypeEndOffset(type);

		if (index != -1) {
			String s = MessageFormat.format(VJO_IMPORT, name);
			InsertEdit insertEdit;
			insertEdit = new InsertEdit(index + CLOSE_BRACKET.length(), s);
			edit.addChild(insertEdit);
		}
	}

	private int getTypeEndOffset(IType type) {
		int index = -1;
		try {
			IBuffer source = type.getSourceModule().getBuffer();
			String s = String.valueOf(source.getCharacters());
			index = s.indexOf(TYPE);
			if (index != -1) {
				index = s.indexOf(CLOSE_BRACKET, index);
			}
		} catch (ModelException e) {
			DLTKCore.error(e.toString(), e);
		}
		return index;
	}

}
