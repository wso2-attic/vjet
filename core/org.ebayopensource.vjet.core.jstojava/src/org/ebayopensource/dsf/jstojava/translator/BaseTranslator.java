/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.translator;

import org.ebayopensource.dsf.jstojava.report.ErrorReporter;
import org.eclipse.mod.wst.jsdt.core.ast.IExpression;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.ArrayInitializer;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.CharLiteral;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.StringLiteral;

public abstract class BaseTranslator {

	protected final TranslateCtx m_ctx;

	//
	// Constructor
	//
	public BaseTranslator(final TranslateCtx ctx) {
		assert ctx != null : "ctx cannot be null";
		m_ctx = ctx;
	}

	//
	// Public
	//
	public TranslateCtx getCtx() {
		return m_ctx;
	}

	public TranslatorProvider getProvider() {
		return m_ctx.getProvider();
	}

	protected ErrorReporter getErrorReporter() {
		return m_ctx.getErrorReporter();
	}
	

	protected boolean isArray(IExpression param) {
		return param instanceof ArrayInitializer;
	}

	protected boolean isString(IExpression param) {
		return (param instanceof StringLiteral || param instanceof CharLiteral);
	}
}
