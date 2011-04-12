/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/* 
 * $Id: DefaultValueTester.java, Nov 26, 2009, 5:27:59 AM, liama. Exp$
 *
 * Copyright (c) 2006-2009 Ebay Technologies. All Rights Reserved.
 * This software program and documentation are copyrighted by Ebay 
 * Technologies.
 */
package org.ebayopensource.vjet.eclipse.core.test.validation;

import org.eclipse.core.runtime.Preferences;

import org.ebayopensource.vjet.eclipse.core.VjetPlugin;
import org.ebayopensource.vjet.eclipse.core.test.parser.AbstractVjoModelTests;

/**
 * Class/Interface description
 *
 * @author <a href="mailto:liama@ebay.com">liama</a>
 * @since JDK 1.5
 */
public class DefaultValueTester extends AbstractVjoModelTests {
    public void testDefaultValidation() {
        Preferences store = VjetPlugin.getDefault().getPluginPreferences();
        assertTrue(store
                .isDefault(VjetPlugin.VJETVALIDATION));
        assertTrue(store
                .getBoolean(VjetPlugin.VJETVALIDATION));
    }
}
