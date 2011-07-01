/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.core.search;

import org.ebayopensource.dsf.jst.IJstNode;
import org.eclipse.dltk.mod.core.IScriptFolder;
import org.eclipse.dltk.mod.core.IType;
import org.eclipse.dltk.mod.core.search.SearchPattern;

/**
 * Factory for creation matches.
 * 
 * 
 *
 */
public class VjoMatchFactory {

	private static int DEFAULT_RULE = SearchPattern.R_FULL_MATCH
			| SearchPattern.R_EQUIVALENT_MATCH | SearchPattern.R_ERASURE_MATCH;

	/**
	 * Create method match for element in type {@link IType} with specified position on source.
	 * 
	 * @param type {@link IType} type.
	 * @param offset element offset on source.
	 * @param length element length in source.
	 * @return method match.
	 */
	public static VjoMatch createMethodMatch(IType type, int offset, int length) {
		return new VjoMatch(type, DEFAULT_RULE, offset, length, 0,
				false, false, false);
	}

	/**
	 * Create field match for element in type {@link IType} with specified position on source.
	 * 
	 * @param type {@link IType} type.
	 * @param offset element offset on source.
	 * @param length element length in source.
	 * @return field match.
	 */
	public static VjoMatch createFieldMatch(IType type, int offset, int length) {
		return new VjoMatch(type, DEFAULT_RULE, offset, length, 0,
				false, false, false);
	}

	
	/**
	 * Create type match for element in type {@link IType} with specified position on source.
	 * 
	 * @param type {@link IType} type.
	 * @param offset element offset on source.
	 * @param length element length in source.
	 * @return type match.
	 */
	public static VjoMatch createTypeMatch(IType type, int offset, int length) {
		return new VjoMatch(type, DEFAULT_RULE, offset, length, 0,
				false, false, false);
	}

	/**
	 * Create package match for element in type {@link IScriptFolder} with specified position on source.
	 * 
	 * @param scriptFolder {@link IScriptFolder} scriptFolder.
	 * @param offset element offset on source.
	 * @param length element length in source.
	 * @return type match.
	 */
	
	public static VjoMatch createPackageMatch(IType type, int offset, int length) {
		return new VjoMatch(type, DEFAULT_RULE, offset, length, 0,
				false, false, false);
	}
	
	/**
	 * Create occurrence match for matched jst node with specified position on source.
	 * 
	 * @param matchedNode {@link IJstNode}.
	 * @param offset element offset on source.
	 * @param length element length in source.
	 * @return field match.
	 */
	public static VjoMatch createOccurrenceMatch(IJstNode matchedNode, int offset, int length) {
		return new VjoMatch(matchedNode, DEFAULT_RULE, offset, length, 0,
				false, false, false);
	}
}
