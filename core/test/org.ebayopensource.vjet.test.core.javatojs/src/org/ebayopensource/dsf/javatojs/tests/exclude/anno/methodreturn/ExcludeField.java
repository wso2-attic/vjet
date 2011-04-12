/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.tests.exclude.anno.methodreturn;

import org.ebayopensource.dsf.javatojs.anno.AExclude;


public class ExcludeField {
	@AExclude
	public String m_excludedField1;	
	
	@AExclude
	public String m_excludedFieldUsedInsideFunction2;
	
	@AExclude
	public static String s_excludedField3;
	
	@AExclude
	public static String s_excludedFieldUsedInsideFunction4;
	
	
	public String getExcludedField2(){
		return m_excludedFieldUsedInsideFunction2;
	}
	
	public static String getExcludedField4(){
		return s_excludedFieldUsedInsideFunction4;
	}
	
	
}
