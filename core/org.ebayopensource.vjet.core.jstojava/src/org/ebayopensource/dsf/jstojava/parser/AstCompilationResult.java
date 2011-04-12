/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.parser;

import org.eclipse.mod.wst.jsdt.internal.compiler.ast.CompilationUnitDeclaration;

public class AstCompilationResult {
	private final CompilationUnitDeclaration m_compilationUnitDeclaration;
	private final boolean m_hasNonFakeTokenInsertionError;
	
	public AstCompilationResult(
		CompilationUnitDeclaration compilationUnitDeclaration,
		boolean hasNonFakeTokenInsertionError) {
		m_compilationUnitDeclaration = compilationUnitDeclaration;
		m_hasNonFakeTokenInsertionError = hasNonFakeTokenInsertionError;
	}
	
	public boolean hasNonFakeTokenInsertionError() {
		return m_hasNonFakeTokenInsertionError;
	}

	public CompilationUnitDeclaration getCompilationUnitDeclaration() {
		return m_compilationUnitDeclaration;
	}
}
