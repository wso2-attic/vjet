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
import org.ebayopensource.dsf.javatojs.parse.BaseTypeVisitor;
import org.ebayopensource.dsf.javatojs.parse.TypeDeclarationVisitor;
import org.ebayopensource.dsf.javatojs.parse.TypeDnDVisitor;
import org.ebayopensource.dsf.javatojs.trace.ITranslateTracer;
import org.ebayopensource.dsf.javatojs.trace.TranslateError;
import org.ebayopensource.dsf.javatojs.trace.TranslateMsgId;
import org.ebayopensource.dsf.javatojs.trace.TranslationTraceId;
import org.ebayopensource.dsf.javatojs.translate.TranslateCtx;
import org.ebayopensource.dsf.javatojs.translate.TranslateInfo;
import org.ebayopensource.dsf.javatojs.translate.TranslationMode;
import org.ebayopensource.dsf.javatojs.util.AstBindingHelper;
import org.ebayopensource.dsf.jst.declaration.JstType;

public class DeclarationPhase extends BasePhase {
	
	private static final TraceId DECLARATION_PHASE = TranslationTraceId.DECLARATION_PHASE;
	
	//
	// Constructors
	//
	public DeclarationPhase(List<JstType> types){
		this(types, false);
	}
	
	public DeclarationPhase(List<JstType> types, boolean includeDependency){
		super(new TranslationMode().addDeclaration());
		setStartingTypes(types);
		if (includeDependency){
			getMode().addDependency();
		}
	}
	
	//
	// API
	//
	public List<JstType> translate(){
		
		ITranslateTracer tracer = getTracer();
		tracer.startGroup(DECLARATION_PHASE);
		
		try {
			TranslateCtx ctx = getCtx();
			TranslationMode mode = getMode();

			String srcName;
			CompilationUnit cu;
			BaseTypeVisitor visitor;
			TranslateInfo tInfo;
		
			List<TypeVisitTask> tasks = new ArrayList<TypeVisitTask>();
			
			// Create tasks
			for (JstType jstType: getStartingTypes()){
				if (jstType == null){
					addError(new TranslateError(TranslateMsgId.NULL_INPUT, "jstType is null"));
					continue;
				}
				// Exclude visited types
				tInfo = ctx.getTranslateInfo(jstType);
				if (mode.hasDeclaration() && tInfo.getMode().hasDeclaration()
						|| mode.hasDependency() && tInfo.getMode().hasDependency()){
					continue;
				}
				cu = AstBindingHelper.getCompilationUnit(jstType);
				srcName = AstBindingHelper.getSourceName(jstType);
				ctx.getTranslateInfo(jstType).addMode(mode);
				visitor = createVisitor(jstType);
				tasks.add(new TypeVisitTask(srcName, cu, visitor, getTracer(jstType), ctx));
			}
			
			// Execute tasks
			if (tasks.size() == 1 || !ctx.isParallelEnabled()){
				for (TypeVisitTask task: tasks){
					task.execute();
					addDependency(task.getVisitor());
					setExceptions(task.getType(), task.getExceptions());
				}
			}
			else if (tasks.size() > 0) {
				TranslationParallelRunner.getInstance().execute(tasks);
				for (TypeVisitTask task: tasks){
					addDependency(task.getVisitor());
					setExceptions(task.getType(), task.getExceptions());
				}
			}
			
			mergeTraces();
			
			return getDependentTypes();
		}
		finally {
			tracer.endGroup(DECLARATION_PHASE, getErrors());
		}
	}
	
	//
	// Private
	//
	private BaseTypeVisitor createVisitor(final JstType type){
		if (getMode().hasDependency() && !getCtx().isJavaOnly(type)){
			return new TypeDnDVisitor(type);
		}
		else {
			return new TypeDeclarationVisitor(type);
		}
	}
}