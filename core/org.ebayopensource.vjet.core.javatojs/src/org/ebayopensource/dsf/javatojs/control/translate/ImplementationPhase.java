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
import java.util.List;

import org.eclipse.jdt.core.dom.CompilationUnit;

import org.ebayopensource.dsf.common.trace.event.TraceId;
import org.ebayopensource.dsf.javatojs.trace.ITranslateTracer;
import org.ebayopensource.dsf.javatojs.trace.TranslateError;
import org.ebayopensource.dsf.javatojs.trace.TranslateMsgId;
import org.ebayopensource.dsf.javatojs.trace.TranslationTraceId;
import org.ebayopensource.dsf.javatojs.translate.TranslateCtx;
import org.ebayopensource.dsf.javatojs.translate.TranslationMode;
import org.ebayopensource.dsf.javatojs.util.AstBindingHelper;
import org.ebayopensource.dsf.jst.declaration.JstType;

public class ImplementationPhase extends BasePhase {
	
	private static final TraceId IMPLEMENTATION_PHASE = TranslationTraceId.IMPLEMENTATION_PHASE;
	
	//
	// Constructors
	//
	public ImplementationPhase(JstType type){
		super(new TranslationMode().addImplementation());
		addStartingType(type);
	}
	
	public ImplementationPhase(List<JstType> types){
		
		super(new TranslationMode().addImplementation());
		setStartingTypes(types);
	}
	
	//
	// API
	//
	public List<JstType> translate(){
		
		ITranslateTracer tracer = getTracer();
		tracer.startGroup(IMPLEMENTATION_PHASE);

		try {
			TranslateCtx ctx = getCtx();
			TranslationMode mode = getMode();
			
			String srcName;
			CompilationUnit cu;
			List<TypeTranslateTask> tasks = new ArrayList<TypeTranslateTask>();
			
			// Create tasks
			for (JstType jstType: getStartingTypes()){
				if (jstType == null){
					addError(new TranslateError(TranslateMsgId.NULL_INPUT, "jstType is null"));
					continue;
				}
				if (ctx.isJavaOnly(jstType)
						|| ctx.isJSProxy(jstType)
						|| ctx.isExcluded(jstType)){
					continue;
				}
				cu = AstBindingHelper.getCompilationUnit(jstType);
				srcName = AstBindingHelper.getSourceName(jstType);
				ctx.getTranslateInfo(jstType).addMode(mode);
				setModeForInnerTypes(jstType, mode, ctx);
				tasks.add(new TypeTranslateTask(srcName, cu, jstType, ctx, getTracer(jstType), ctx));
			}
			
			// Execute tasks
			if (tasks.size() == 1 || !ctx.isParallelEnabled()){
				for (TypeTranslateTask task: tasks){
					task.execute();
//					postProcess(task.getType());
					setExceptions(task.getType(), task.getExceptions());
				}
			}
			else if (tasks.size() > 0) {
				TranslationParallelRunner.getInstance().execute(tasks);
				for (TypeTranslateTask task: tasks){
//					postProcess(task.getType());
					setExceptions(task.getType(), task.getExceptions());
				}
			}
			
			mergeTraces();

			return getStartingTypes();
		}
		finally {
			tracer.endGroup(IMPLEMENTATION_PHASE);
		}
	}
	
	private void setModeForInnerTypes(JstType jstType, TranslationMode mode, TranslateCtx ctx){
		List<JstType> inners = jstType.getEmbededTypes();
		for (JstType t: inners){
			ctx.getTranslateInfo(t).addMode(mode);
			setModeForInnerTypes(t, mode, ctx);
		}
	}
}
