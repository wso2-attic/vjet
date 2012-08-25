/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.tests.generics;



import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import org.ebayopensource.dsf.javatojs.control.TranslationController;
import org.ebayopensource.dsf.javatojs.tests.util.MainTranslator;
import org.ebayopensource.dsf.jsgen.shared.generate.CodeStyle;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.vjet.test.util.TestHelper;


//@ModuleInfo(value="DsfPrebuild",subModuleId="JavaToJs")
public class GenericsTest{
	@Test
	//@Category( { P1, FUNCTIONAL })
	//@Description("Test Generics support")
	public void genericComments(){
		TranslationController controller = TestHelper.getTranslationController(MainTranslator.getInitializer());
		List <JstType> jstTypes = controller.onDemandTranslation(Generics2.class);
		TestHelper helper = new TestHelper(null, null);
		String vjoActual = helper.toVjo(jstTypes.get(0), CodeStyle.PRETTY);
		assertTrue(vjoActual.indexOf("Generics<T>") > -1);
		assertTrue(vjoActual.indexOf("Generics2<T>") > -1);
		assertTrue(vjoActual.indexOf("//> constructs(T o)") > -1);
	}
}
