/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/**
 * 
 */
package org.ebayopensource.dsf.jst.validation.vjo;

import org.ebayopensource.dsf.jst.ts.JstTypeSpaceMgr;

/**
 * 
 *
 */
public class VjLibTypeSpaceLoader {
	
	
	private final static JstTypeSpaceMgr testFileSpaceLoader = CodeCompletionUtil.loadJsToTypeSpace(VjoValidationBaseTester.VJLIB_FOLDER);

	/**
	 * @return the testFileSpaceLoader
	 */
	public static JstTypeSpaceMgr getTestFileSpaceLoader() {
		return testFileSpaceLoader;
	}
	
	
}
