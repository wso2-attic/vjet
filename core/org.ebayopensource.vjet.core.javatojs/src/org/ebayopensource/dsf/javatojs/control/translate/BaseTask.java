/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.control.translate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.jdt.core.dom.CompilationUnit;

import org.ebayopensource.dsf.common.trace.TraceAttr;
import org.ebayopensource.dsf.common.trace.event.TraceId;
import org.ebayopensource.dsf.javatojs.trace.ITranslateTracer;
import org.ebayopensource.dsf.javatojs.trace.TraceTime;
import org.ebayopensource.dsf.javatojs.trace.TranslateError;
import org.ebayopensource.dsf.javatojs.trace.TranslationTraceId;
import org.ebayopensource.dsf.javatojs.translate.TranslateCtx;
import org.ebayopensource.dsf.javatojs.translate.TranslationMode;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.dsf.javatojs.control.translate.ResponseCommand;

public abstract class BaseTask extends ResponseCommand {
	
	public static final TraceId TASK = TranslationTraceId.TASK;

	private String m_id;
	private CompilationUnit m_cu;
	private TranslateCtx m_parentCtx;
	private ITranslateTracer m_tracer;
	private List<Throwable> m_exceptions;
	
	BaseTask(String id, CompilationUnit cu, ITranslateTracer tracer, TranslateCtx parentCtx){
		m_id = id;
		m_cu = cu;
		m_tracer = tracer;
		m_parentCtx = parentCtx;
	}
	
	protected abstract TranslationMode getMode();
	protected abstract JstType getType();
	abstract void doIt();
	
	public void execute(){
		
//		System.out.println("Task: " + getId() + ", mode=" + getMode().toString());
		
		TraceTime timer = m_parentCtx.getTraceManager().getTimer();
		if (m_tracer.isEnabled()){
			m_tracer.startGroup(TASK, timer,
					new TraceAttr("type", getId()),
					new TraceAttr("mode", getMode().toString()),
					new TraceAttr("thread", String.valueOf(Thread.currentThread().getId())));
		}
		
		try {
			setupCtx();
			doIt();
		}
		catch(Throwable t){
			if (m_exceptions == null){
				m_exceptions = new ArrayList<Throwable>();
			}
			m_exceptions.add(t);
			t.printStackTrace();
		}
		finally {
			if (m_tracer.isEnabled()){
				m_tracer.endGroup(TASK, getErrors(), timer);
			}
			restoreCtx();
		}
	}
	
	public String getId(){
		return m_id;
	}
	
	@Override
	public String toString(){
		return getId();
	}
	
	CompilationUnit getCompilationUnit(){
		return m_cu;
	}
	
	protected ITranslateTracer getTracer(){
		return m_tracer;
	}
	
	public List<TranslateError> getErrors(){
		if (getType() == null){
			return Collections.emptyList();
		}
		JstType jstType = getType();
		if (jstType.getEmbededTypes().isEmpty()){
			return m_parentCtx.getTranslateInfo(jstType).getStatus().getErrors();
		}
		List<TranslateError> list = new ArrayList<TranslateError>();
		list.addAll(m_parentCtx.getTranslateInfo(jstType).getStatus().getErrors());
		for (JstType embeded: jstType.getEmbededTypes()){
			list.addAll(m_parentCtx.getTranslateInfo(embeded).getStatus().getErrors());
		}
		return list;
	}
	
	public List<Throwable> getExceptions(){
		if (m_exceptions == null){
			return Collections.emptyList();
		}
		return Collections.unmodifiableList(m_exceptions);
	}
	
	//
	// Private
	//
	private void setupCtx(){
		TranslateCtx.createChildCtx(m_parentCtx)
			.getTraceManager().setTracer(m_tracer);
	}
	
	private void restoreCtx(){
		TranslateCtx.setCtx(m_parentCtx);
	}
}
