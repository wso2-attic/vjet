/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.control.translate;

import org.eclipse.jdt.core.dom.CompilationUnit;

import org.ebayopensource.dsf.javatojs.parse.BaseTypeVisitor;
import org.ebayopensource.dsf.javatojs.trace.ITranslateTracer;
import org.ebayopensource.dsf.javatojs.trace.TranslationTraceId;
import org.ebayopensource.dsf.javatojs.translate.TranslateCtx;
import org.ebayopensource.dsf.javatojs.translate.TranslationMode;
import org.ebayopensource.dsf.javatojs.translate.config.PackageMapping;
import org.ebayopensource.dsf.jst.declaration.JstType;

public final class TypeVisitTask extends BaseTask {

	private BaseTypeVisitor m_visitor;
	
	//
	// Constructor
	//
	TypeVisitTask(String id, CompilationUnit cu, 
			BaseTypeVisitor visitor, ITranslateTracer tracer, TranslateCtx parentCtx){
		super(id, cu, tracer, parentCtx);
		m_visitor = visitor;
	}
	
	//
	// Satisfying BaseTask
	//
	void doIt(){
		getCompilationUnit().accept(m_visitor);
		
		if (getTracer().isEnabled() && !m_visitor.getDependency().isEmpty()){
			PackageMapping pkgMapping = TranslateCtx.ctx().getConfig().getPackageMapping();
			ITranslateTracer tracer = getTracer();
			tracer.startGroup(TranslationTraceId.DEPENDENTS);
			for (JstType type: m_visitor.getDependency()){
				tracer.traceNV(TranslationTraceId.TYPE, this, "Type", pkgMapping.mapFrom(type.getName()));
			}
			tracer.endGroup(TranslationTraceId.DEPENDENTS);
		}
	}

	//
	// API
	//
	public BaseTypeVisitor getVisitor(){
		return m_visitor;
	}
	
	public  TranslationMode getMode(){
		return m_visitor.getMode();
	}
	
	public JstType getType(){
		return m_visitor.getType();
	}
}
