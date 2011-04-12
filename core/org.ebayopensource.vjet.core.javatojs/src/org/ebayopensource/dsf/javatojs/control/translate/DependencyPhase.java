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
import org.ebayopensource.dsf.javatojs.parse.TypeDependencyVisitor;
import org.ebayopensource.dsf.javatojs.parse.TypeDnDVisitor;
import org.ebayopensource.dsf.javatojs.trace.ITranslateTracer;
import org.ebayopensource.dsf.javatojs.trace.TranslateError;
import org.ebayopensource.dsf.javatojs.trace.TranslateMsgId;
import org.ebayopensource.dsf.javatojs.trace.TranslationTraceId;
import org.ebayopensource.dsf.javatojs.translate.TranslateCtx;
import org.ebayopensource.dsf.javatojs.translate.TranslationMode;
import org.ebayopensource.dsf.javatojs.util.AstBindingHelper;
import org.ebayopensource.dsf.jst.declaration.JstType;

public class DependencyPhase extends BasePhase {
	
	private static final TraceId DEPENDENCY_PHASE = TranslationTraceId.DEPENDENCY_PHASE;

	//
	// Constructors
	//
	public DependencyPhase(JstType jstType, boolean includeDeclaration){
		super(new TranslationMode().addDependency());
		List<JstType> jstTypes = new ArrayList<JstType>();
		jstTypes.add(jstType);
		setStartingTypes(jstTypes);
		if (includeDeclaration){
			getMode().addDeclaration();
		}
	}
	
	public DependencyPhase(List<JstType> jstTypes, boolean includeDeclaration){
		super(new TranslationMode().addDependency());
		setStartingTypes(jstTypes);
		if (includeDeclaration){
			getMode().addDeclaration();
		}
	}
	
	//
	// API
	//
	public List<JstType> translate(){

		ITranslateTracer tracer = getTracer();
		tracer.startGroup(DEPENDENCY_PHASE);

		try {
			CompilationUnit cu;
			BaseTypeVisitor visitor;
			TranslateCtx ctx = getCtx();
			TranslationMode mode = getMode();
			String srcName;
			
			List<JstType> jstTypes = getStartingTypes();
			List<TypeVisitTask> tasks = new ArrayList<TypeVisitTask>(jstTypes.size());
			
			// Create tasks
			for (JstType jstType: jstTypes){
				if (jstType == null){
					addError(new TranslateError(TranslateMsgId.NULL_INPUT, "jstType is null"));
					continue;
				}
				ctx.getTranslateInfo(jstType).addMode(mode);
				cu = AstBindingHelper.getCompilationUnit(jstType);
				srcName = AstBindingHelper.getSourceName(jstType);
				visitor = createVisitor(jstType);
				tasks.add(new TypeVisitTask(srcName, cu, visitor, getTracer(jstType), ctx));
			}
			
			// Execute tasks
			if (tasks.size() == 1 || !ctx.isParallelEnabled()){
				for (TypeVisitTask task: tasks){
					task.execute();
					addDependency(task.getVisitor());
					if (task.getType() == null){
						addError(new TranslateError(TranslateMsgId.STACK_ERROR, task.getId() + ": curType is null"));
						continue;
					}
					setExceptions(task.getType(), task.getExceptions());
				}
			}
			else if (tasks.size() > 0){
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
			tracer.endGroup(DEPENDENCY_PHASE, getErrors());
		}
	}
	
	//
	// Private
	//
	private BaseTypeVisitor createVisitor(final JstType type){
		if (getMode().hasDeclaration()){
			return new TypeDnDVisitor(type);
		}
		else {
			return new TypeDependencyVisitor(type);
		}
	}
}
