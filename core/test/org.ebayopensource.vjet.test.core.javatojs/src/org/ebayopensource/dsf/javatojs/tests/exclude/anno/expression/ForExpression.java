/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.tests.exclude.anno.expression;

public class ForExpression {	
	public void process(){
		for(int i=0; i< ExcludedClass.value; i++){
			new ExcludedClass2().getClass();
			//Object o = (i>2?null:new ExcludedClass3());
		}		
		ExcludedClass3 obj3 = new ExcludedClass3();
	}

}
