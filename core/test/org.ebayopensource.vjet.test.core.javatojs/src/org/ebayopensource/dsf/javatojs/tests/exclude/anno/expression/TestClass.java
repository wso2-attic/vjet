/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.tests.exclude.anno.expression;

import org.ebayopensource.dsf.javatojs.tests.exclude.TestTargetTranslator;
import org.ebayopensource.dsf.javatojs.tests.exclude.TestTargetTranslator.ClassList;


/*
 * Type Error is thrown by Class as when accessing static setFlag() method
 * 
 */
public class TestClass {
	
	public static void main(String[] args) {
		ClassList sourceList = TestTargetTranslator.getSourceList();
		sourceList
		
		.addToList(SimpleExpression.class)	
		.addToList(ForExpression.class)
		
		;
		
		TestTargetTranslator.translate(sourceList);
		TestTargetTranslator.deleteGenerated(sourceList);
	}
}


