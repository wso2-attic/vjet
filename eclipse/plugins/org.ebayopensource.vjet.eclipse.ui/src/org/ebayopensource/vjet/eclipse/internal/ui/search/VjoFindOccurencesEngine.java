/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.ui.search;

import java.util.Collections;
import java.util.List;

import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.vjet.eclipse.codeassist.CodeassistUtils;
import org.ebayopensource.vjet.eclipse.core.IJSSourceModule;
import org.ebayopensource.vjet.eclipse.core.search.VjoMatch;
import org.ebayopensource.vjet.eclipse.core.search.VjoSearchOccurrenceEngine;
import org.ebayopensource.vjet.eclipse.internal.codeassist.select.VjoSelectionEngine;
import org.eclipse.dltk.mod.codeassist.ISelectionEngine;
import org.eclipse.dltk.mod.core.DLTKLanguageManager;
import org.eclipse.dltk.mod.core.IDLTKLanguageToolkit;
import org.eclipse.dltk.mod.core.ModelException;
import org.eclipse.dltk.mod.internal.core.VjoSourceModule;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Region;

/**
 * 
 * Used only for Test cases, keep update with VjoEditor
 * Engine for searching mark occurences.
 * 
 */
public class VjoFindOccurencesEngine {

	/**
	 * Finds mark occurences by specified parameters.
	 * 
	 * @param module
	 * @param region
	 * 
	 * @return a list of matches {@link VjoMatch}
	 * 
	 * @throws ModelException
	 */
	public List<VjoMatch> findOccurences(IJSSourceModule module, IRegion region)
			throws ModelException {
		if (!(module instanceof VjoSourceModule)) {
			return Collections.emptyList();
		}
		VjoSourceModule vjoSourceModule = ((VjoSourceModule) module);

		int offset = region.getOffset();
		offset = CodeassistUtils.findWordOffset(module.getSourceAsCharArray(), offset);
		region = findWord(module.getSource(), offset);

		IDLTKLanguageToolkit toolKit = DLTKLanguageManager.getLanguageToolkit(vjoSourceModule);
		ISelectionEngine selectionEngine = DLTKLanguageManager.getSelectionEngine(toolKit.getNatureId());
		if(!(selectionEngine instanceof VjoSelectionEngine)){
			return Collections.emptyList();
		}
		VjoSelectionEngine vjoSelectionEngine = (VjoSelectionEngine) selectionEngine;
		IJstNode jstNode = vjoSelectionEngine.convertSelection2JstNode(vjoSourceModule, region.getOffset(), region.getLength());
		IJstType scopeTree = CodeassistUtils.getJstType(vjoSourceModule);

		return VjoSearchOccurrenceEngine.findOccurrence(jstNode, scopeTree);
	}

	/**
	 * Finds a word by specified offset and returns the region of word.
	 * 
	 * @param source
	 * @param offset
	 * 
	 * @return the region of found word
	 */
	public static IRegion findWord(String source, int offset) {

		int start = -2;
		int end = -1;

		int pos = offset;
		char c;

		while (pos >= 0) {
			c = source.charAt(pos);
			if (!Character.isJavaIdentifierPart(c))
				break;
			--pos;
		}
		start = pos;

		pos = offset;
		int length = source.length();

		while (pos < length) {
			c = source.charAt(pos);
			if (!Character.isJavaIdentifierPart(c))
				break;
			++pos;
		}
		end = pos;

		if (start >= -1 && end > -1) {
			if (start == offset && end == offset)
				return new Region(offset, 0);
			else if (start == offset)
				return new Region(start, end - start);
			else
				return new Region(start + 1, end - start - 1);
		}

		return null;
	}

}
