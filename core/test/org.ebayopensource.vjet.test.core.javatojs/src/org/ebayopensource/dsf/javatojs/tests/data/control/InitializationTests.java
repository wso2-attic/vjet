/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.tests.data.control;


import java.util.ArrayList;
import java.util.List;

import org.junit.Test;


import static org.junit.Assert.*;

import org.ebayopensource.dsf.javatojs.control.TranslationController;
import org.ebayopensource.dsf.javatojs.tests.data.control.initializers.InitializerA;


//@ModuleInfo(value="DsfPrebuild",subModuleId="JavaToJs")
public class InitializationTests {

	@Test
	//@Category( { P1, FUNCTIONAL })
	//@Description("Test Initialization ordering")
	public void testInitialization(){
		
		InitializerA initializer = new InitializerA();
		TranslationController c = new TranslationController(initializer);
		c.targetedTranslation(new ArrayList<Class<?>>());
		
		assertEquals(5, initializer.m_list.size());
		assertEquals("C", initializer.m_list.get(0));
		assertEquals("E", initializer.m_list.get(1));
		assertEquals("D", initializer.m_list.get(2));
		assertEquals("B", initializer.m_list.get(3));
		assertEquals("A", initializer.m_list.get(4));
	}
	
	public static class BaseInitializer {
		protected static List<String> m_list = new ArrayList<String>();
	}
}
