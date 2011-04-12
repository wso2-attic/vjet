/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.validator;

//import org.ebayopensource.dsf.jstojava.jsdoc.JsDocValidator2;
import org.ebayopensource.dsf.jstojava.jslint.JsLintValidator;
import org.ebayopensource.dsf.jstojava.report.ErrorReporter;

/**
 * Validates raw source code.
 * 
 *
 */
public class JsCodeValidator {

	private ErrorReporter m_errorReport;
	private final String m_rawSource;
	private final String m_sourceURI;
	private final int m_startingLine;

/**
 * Validates raw source code and gather status results. This runs the code through the JavaScript 
 * optimizer.  Reconciles raw soruce code validation with optmized validation resultss.
 * 
 * @param statusCollection collection of validation statuses
 * @param source JavaScript source code
 * @param sourceURI  uniform resource identifier
 * @param startingLine first line in file
 */
	
	public JsCodeValidator(ErrorReporter errorReport, String source, String sourceURI, int startingLine) {
		m_errorReport = errorReport;
		m_rawSource = source;
		m_sourceURI = sourceURI;
		m_startingLine = startingLine;
	}
	
	private final void lintValidation(){
		JsLintValidator validator = new JsLintValidator(m_errorReport);
		validator.validateString(m_rawSource, m_startingLine, m_sourceURI);
		
		
	}
	
//	private final void docValidation(){
//		JsDocValidator2 v = new JsDocValidator2(m_errorReport);
//		v.validate();	
//	}
	
	
	/**
	 * Validates the source files using lint.
	 *
	 */
	public final void validate(){
		lintValidation();
//		docValidation();
		
	}

	
	
}
