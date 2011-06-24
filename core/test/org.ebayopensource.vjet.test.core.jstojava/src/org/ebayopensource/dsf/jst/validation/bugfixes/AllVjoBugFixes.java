/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.validation.bugfixes;

import org.ebayopensource.dsf.jst.declaration.JstCache;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	VjoBugFixes.class
})

public class AllVjoBugFixes {

    @BeforeClass
    public static void beforeClass(){
    	JstCache.getInstance().clear();
    }
	
}
