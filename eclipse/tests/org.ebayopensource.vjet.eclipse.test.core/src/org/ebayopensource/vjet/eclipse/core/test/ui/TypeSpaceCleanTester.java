/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/* 
 * $Id: ss.java, Jun 22, 2009, 7:33:28 PM, liama. Exp$
 *
 * Copyright (c) 2006-2009 Ebay Technologies. All Rights Reserved.
 * This software program and documentation are copyrighted by Ebay 
 * Technologies.
 */
package org.ebayopensource.vjet.eclipse.core.test.ui;

import org.ebayopensource.dsf.jst.ts.JstTypeSpaceMgr;
import org.ebayopensource.dsf.jstojava.controller.JstParseController;
import org.ebayopensource.vjet.eclipse.core.parser.VjoParserToJstAndIType;
import org.ebayopensource.vjet.eclipse.core.parser.VjoSourceElementResolver;
import org.ebayopensource.vjet.eclipse.core.ts.VjoJstTypeLoader;
import org.ebayopensource.vjo.tool.typespace.TypeSpaceMgr;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 *Add test cases for test type space clean function
 * 
 * @author <a href="mailto:liama@ebay.com">liama</a>
 * @since JDK 1.5
 */
public class TypeSpaceCleanTester extends TestCase {

    /**
     * Add test cases for test type space clean function
     * Purpose: After clean. The default lib should be reloaded.
     */
    public void testTypeSpaceClean() {
        
    	JstParseController controller = VjoParserToJstAndIType.getJstParseController();
        controller.setRefResolver(new VjoSourceElementResolver(controller));
        new JstTypeSpaceMgr(controller, new VjoJstTypeLoader());
        TypeSpaceMgr.getInstance().init(controller);
        TypeSpaceMgr.getInstance().clean();
        Assert.assertEquals(TypeSpaceMgr.getInstance().getTypeSpace()
                .getGroups().keySet().size(), 4);
    }
}
