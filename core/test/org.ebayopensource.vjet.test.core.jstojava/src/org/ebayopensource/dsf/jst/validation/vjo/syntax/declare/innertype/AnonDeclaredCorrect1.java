/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/* 
 * $Id: AnonDeclaredCorrect1.java, May 25, 2009, 10:58:12 PM, liama. Exp$
 *
 * Copyright (c) 2006-2009 Ebay Technologies. All Rights Reserved.
 * This software program and documentation are copyrighted by Ebay 
 * Technologies.
 */
package org.ebayopensource.dsf.jst.validation.vjo.syntax.declare.innertype;




import java.util.List;

import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoSemanticProblem;
import org.ebayopensource.dsf.jst.validation.vjo.VjoValidationBaseTester;
import org.junit.Before;
import org.junit.Test;




/**
 * Anon declared correct1
 *
 * @author <a href="mailto:liama@ebay.com">liama</a>
 * @since JDK 1.5
 */
//@ModuleInfo(value="DsfPrebuild",subModuleId="JsToJava")
//@Category( { P1, FAST, UNIT })
public class AnonDeclaredCorrect1 extends VjoValidationBaseTester {

    @Before
    public void setUp(){
        expectProblems.clear();
        //bugfix by roy, we don't validate vjo.make now
//        expectProblems.add(createNewProblem(MethodProbIds.UndefinedFunction, 26, 0));
    }

    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test create anon ctype with vjo.make")
    public void testNestedTypes() {
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "syntax.declare.innerType.","AnonExample1.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
}
