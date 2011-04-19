/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsgen.shared.vjo;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.ebayopensource.dsf.jsgen.shared.generate.CodeStyle;
import org.ebayopensource.dsf.jsgen.shared.generate.DefaultJsrFilters;
import org.ebayopensource.dsf.jsgen.shared.generate.GeneratorConfig;
import org.ebayopensource.dsf.jsgen.shared.generate.Indenter;


public class GeneratorCtx {

	private CodeStyle m_style;
	private StringWriter m_buffer;
	private PrintWriter m_printer;
	private Indenter m_indenter;
	private GeneratorProvider m_provider;
	private GeneratorConfig	m_config;
	
	//
	// Constructor
	//
	public GeneratorCtx(final CodeStyle style){
		m_buffer = new StringWriter();
		m_printer = new PrintWriter(m_buffer);
		m_style = style;
		m_indenter = new Indenter(m_printer, style);
		m_provider = new GeneratorProvider(this);
		
		m_config = new GeneratorConfig(new DefaultJsrFilters());
	}
	
	//
	// API
	//
	public Indenter getIndenter() {
		return m_indenter;
	}
	public void setIndenter(Indenter indenter) {
		m_indenter = indenter;
	}
	public CodeStyle getStyle() {
		return m_style;
	}
	public void setStyle(CodeStyle style) {
		m_style = style;
		if (m_provider != null){
			m_provider.setStyle(style);
		}
	}
	public void setNewline(String newline) {
		m_provider.setNewline(newline);
	}
	public PrintWriter getWriter() {
		return m_printer;
	}
	
	public GeneratorProvider getProvider(){
		return m_provider;
	}
	
	public GeneratorConfig getConfig() {
		return m_config;
	}

	public void setConfig(GeneratorConfig m_config) {
		this.m_config = m_config;
	}
	
//	public TranslateConfig getTranslateConfig(){
//		return m_tCtx.getConfig();
//	}
	
	//
	// Package Protected
	//
	StringWriter getStringWriter(){
		return m_buffer;
	}
}
