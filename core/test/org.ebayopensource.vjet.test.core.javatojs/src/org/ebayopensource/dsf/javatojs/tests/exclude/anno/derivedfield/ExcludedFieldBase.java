/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.tests.exclude.anno.derivedfield;

import org.ebayopensource.dsf.javatojs.anno.AExclude;

public class ExcludedFieldBase {

	
	@AExclude
	public String m_excludedFieldInBase1;	
	
	@AExclude
	public String m_excludedFieldInBase2;
	
	@AExclude
	public static String s_excludedFieldInBase3;
	
	@AExclude
	public static String s_excludedFieldInBase4;
	
}
