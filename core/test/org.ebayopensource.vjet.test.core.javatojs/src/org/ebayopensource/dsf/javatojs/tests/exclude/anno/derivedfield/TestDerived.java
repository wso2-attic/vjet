/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.tests.exclude.anno.derivedfield;

public class TestDerived  extends ExcludedFieldBase{
	
	public String getExcludedFieldInBase1(){
		return m_excludedFieldInBase1;
	}
	
	public static String getExcludedFieldInBase3(){
		return s_excludedFieldInBase3;
	}
	

}
