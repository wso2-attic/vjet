/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.trace;

import org.ebayopensource.dsf.common.trace.event.TraceId;

public interface TranslationTraceId {
	
	TraceId TEST = new TraceId("Test");
	TraceId PKG = new TraceId("Package");
	TraceId FILE = new TraceId("File");
	
	TraceId CONTROLLER = new TraceId("Controller");
	TraceId DEPENDENCY_PHASE = new TraceId("DependencyPhase");
	TraceId DECLARATION_PHASE = new TraceId("DeclarationPhase");
	TraceId IMPLEMENTATION_PHASE = new TraceId("ImplementationPhase");
	TraceId TASK = new TraceId("Task");
	TraceId VISITOR = new TraceId("Visitor");
	TraceId TRANSLATOR = new TraceId("Translator");
	
	TraceId TYPE = new TraceId("Type");
	TraceId DEPENDENTS = new TraceId("Dependents");
}
