/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
//package org.ebayopensource.dsf.jstojava.validator;
//
//import java.util.Collection;
//
//import org.ebayopensource.dsf.jstojava.jsdoc.JsDocValidator;
//import org.ebayopensource.dsf.jstojava.jsparser.JsFunctionBlock;
//import org.ebayopensource.dsf.jstojava.report.ErrorReporter;
//
///**
// * Validates blocks of JavaScript code and returns errors and warnings for all 
// * the blocks.
// * 
// * 
// *
// */
//public class JsFunctionBlockValidator {
//
//	private ErrorReporter m_errorReport;
//
//	private final Collection<JsFunctionBlock> m_sourceBlocks; // add warnings to this
//
//	/**
//	 *
//	 * @param statusCollection validation statuses
//	 * @param sourceBlocks source code
//	 */
//	public JsFunctionBlockValidator(ErrorReporter errorReport,
//			Collection<JsFunctionBlock> sourceBlocks) {
//		m_errorReport = errorReport;
//		m_sourceBlocks = sourceBlocks;
//	}
//
//	public final void validate() {
//		JsDocValidator jsdocv = new JsDocValidator(m_errorReport,m_sourceBlocks);
//		jsdocv.validate();
//		
//		for(JsFunctionBlock block:m_sourceBlocks){
//			String source = block.getRawSource();
//			source = removeWrapper(source);
//			source = addWrapper(source);
//			JsCodeValidator validator = new JsCodeValidator(m_errorReport,source,block.getSourceURI(),block.getStartingNumber()+1);
//			validator.validate();
//		}
//	}
//
//	private String addWrapper(final String source) {
//		return "function x()" + source;
//	}
//
//	private String removeWrapper(final String source) {
//		int firstCurly = source.indexOf("{");
//		int lastCurly = source.indexOf("}");
//		if(firstCurly!=-1 && lastCurly!=-1){
//			return source.substring(firstCurly, lastCurly+1);
//		}
//		return source;
//	}
//
//}
