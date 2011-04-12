/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.tests.exclude.aexclude;


public class B4  {	
	A10 o= new A10();
	
	public void m1(){
		A10.EmbededClass eo = o.new EmbededClass();
		eo.m1();
	}
}
