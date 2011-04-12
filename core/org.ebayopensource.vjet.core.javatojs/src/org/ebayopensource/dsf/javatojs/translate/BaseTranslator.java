/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.translate;

import org.ebayopensource.dsf.javatojs.report.ErrorReporter;
import org.ebayopensource.dsf.javatojs.translate.custom.CustomTranslateDelegator;

public abstract class BaseTranslator {

	private TranslateCtx m_ctx;
	
	//
	// Protected
	//
	protected TranslateCtx getCtx(){
		if (m_ctx == null){
			m_ctx = TranslateCtx.ctx();
		}
		return m_ctx;
	}
	
	protected TranslatorProvider getProvider(){
		return getCtx().getProvider();
	}
	
	protected TranslateLogger getLogger(){
		return getCtx().getLogger();
	}
	
	protected PackageTranslator getPackageTranslator(){
		return getProvider().getPackageTranslator();
	}
	
	protected TypeTranslator getTypeTranslator(){
		return getProvider().getTypeTranslator();
	}
	
	protected LiteralTranslator getLiteralTranslator(){
		return getProvider().getLiteralTranslator();
	}
	
	protected NameTranslator getNameTranslator(){
		return getProvider().getNameTranslator();
	}
	
	protected FieldTranslator getFieldTranslator(){
		return getProvider().getFieldTranslator();
	}
	
	protected MethodTranslator getMtdTranslator(){
		return getProvider().getMethodTranslator();
	}
	
	protected ExpressionTranslator getExprTranslator(){
		return getProvider().getExprTranslator();
	}
	
	protected StatementTranslator getStmtTranslator(){
		return getProvider().getStmtTranslator();
	}
	
	protected DataTypeTranslator getDataTypeTranslator(){
		return getProvider().getDataTypeTranslator();
	}
	
	protected CustomTranslateDelegator getCustomTranslator(){
		return getProvider().getCustomTranslator();
	}
	
	protected OtherTranslator getOtherTranslator(){
		return getProvider().getOtherTranslator();
	}
	
	protected ErrorReporter getErrorReporter(){
		return getCtx().getErrorReporter();
	}
}
