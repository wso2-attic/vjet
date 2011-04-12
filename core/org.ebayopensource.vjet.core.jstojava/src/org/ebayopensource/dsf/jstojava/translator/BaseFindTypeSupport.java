/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.translator;

import java.util.HashMap;
import java.util.Map;

import org.ebayopensource.af.common.error.ErrorList;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstCache;
import org.ebayopensource.dsf.jstojava.parser.comments.JsCommentMeta;
import org.ebayopensource.dsf.jstojava.report.ErrorReporter;
import org.ebayopensource.dsf.jstojava.report.ErrorReportPolicy.ReportLevel;
import org.ebayopensource.dsf.jstojava.translator.robust.JstSourceUtil;

/**
 * for linker or other users to create find type support
 * @see IFindTypeSupport
 * @see TranslateHelper#findType(IFindTypeSupport, org.ebayopensource.dsf.jstojava.parser.comments.JsTypingMeta, org.ebayopensource.dsf.jstojava.parser.comments.IJsCommentMeta)
 * @see TranslateHelper#findType(IFindTypeSupport, String)
 * 
 * the linker doesn't have all the {@link TranslateCtx} information but still should be able to find the type based on the {@link JsCommentMeta}
 * 
 *
 */
public class BaseFindTypeSupport implements IFindTypeSupport {

	public static final ErrorReporter NO_ERROR_REPORTER = new ErrorReporter(){

		@Override
		public void error(String message, String resource, int line, int column) {}

		@Override
		public void error(String message, String resource, int start, int end,
				int line, int column) {}

		@Override
		public ErrorList getErrors() {
			return new ErrorList(0);
		}

		@Override
		public ErrorList getWarnings() {
			return new ErrorList(0);
		}

		@Override
		public boolean hasErrors() {
			return false;
		}

		@Override
		public boolean hasWarnings() {
			return false;
		}

		@Override
		public void report(ReportLevel level, String message, String resource,
				int line, int column) {}

		@Override
		public void reportAll() {}

		@Override
		public void reportErrors() {}

		@Override
		public void reportWarnings() {}

		@Override
		public void setReportErrors(boolean value) {}

		@Override
		public void setReportWarnings(boolean value) {}

		@Override
		public void warning(String message, String resource, int line,
				int column) {}

		@Override
		public void warning(String message, String resource, int start,
				int end, int line, int column) {}
		
	};
	
	public static final ILineInfoProvider ZERO_LINE_INFO_PROVIDER = 
		new ILineInfoProvider() {
			@Override
			public int line(int beginOffset) {
				return 0;
			}
			
			@Override
			public int col(int beginOffset) {
				return 0;
			}
		};
	
	private IJstType m_currentType;
	private ErrorReporter m_errorReporter = NO_ERROR_REPORTER;
	private ILineInfoProvider m_lineInfoProvider = ZERO_LINE_INFO_PROVIDER;
	private char[] m_originalSource = new char[0];
	private Map<String, IJstType> m_symbol2TypeMap = new HashMap<String, IJstType>(0);
	
	@Override
	public IJstType getCurrentType() {
		return m_currentType;
	}
	
	public BaseFindTypeSupport setCurrentType(final IJstType currentType) {
		m_currentType = currentType;
		return this;
	}
	
	@Override
	public ErrorReporter getErrorReporter() {
		return m_errorReporter;
	}
	
	public BaseFindTypeSupport setErrorReporter(final ErrorReporter ep){
		m_errorReporter = ep;
		return this;
	}
	
	@Override
	public ILineInfoProvider getLineInfoProvider() {
		if(m_lineInfoProvider == null
				&& getOriginalSource() != null){
			m_lineInfoProvider = new JstSourceUtil(getOriginalSource());
		}
		return m_lineInfoProvider;
	}
	
	public BaseFindTypeSupport setLineInfoProvider(final ILineInfoProvider li){
		m_lineInfoProvider = li;
		return this;
	}
	
	@Override
	public char[] getOriginalSource() {
		return m_originalSource;
	}
	
	public BaseFindTypeSupport setOriginalSource(char[] source){
		m_originalSource = source;
		return this;
	}
	
//	@Override
//	public Map<String, IJstType> getSymbol2TypeMap() {
//		return Collections.unmodifiableMap(m_symbol2TypeMap);
//	}

	public BaseFindTypeSupport setSymbol2TypeMap(final Map<String, IJstType> symbol2TypeMap){
		if(symbol2TypeMap != null){
			m_symbol2TypeMap.clear();
			m_symbol2TypeMap.putAll(symbol2TypeMap);
		}
		return this;
	}

	@Override
	public IJstType findTypeByName(String name) {
		final IJstType foundFromOwningMap = m_symbol2TypeMap.get(name);
		if(foundFromOwningMap == null){
			return JstCache.getInstance().getType(name);
			//NOTE, in order to reflect the latest type in JstCache
			//no caching is being done here at all
		}
		return foundFromOwningMap;
	}
}
