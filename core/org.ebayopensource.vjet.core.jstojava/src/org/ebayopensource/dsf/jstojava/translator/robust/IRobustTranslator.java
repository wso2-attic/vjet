/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.translator.robust;

import java.util.Stack;

import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.dsf.jstojava.translator.BaseTranslator;

import org.eclipse.mod.wst.jsdt.core.ast.IProgramElement;

public interface IRobustTranslator {
	
	public static final String END_TYPE = "endType";
	void configure(Stack<IProgramElement> astElements, JstType jst,
			BaseTranslator weakTranslator, IntegerHolder completionPos);
	
	IErrorCollector getErrorCollector();
	
	boolean transform();
	
	IRobustTranslator getSubTranslator(IProgramElement element);
	
}
