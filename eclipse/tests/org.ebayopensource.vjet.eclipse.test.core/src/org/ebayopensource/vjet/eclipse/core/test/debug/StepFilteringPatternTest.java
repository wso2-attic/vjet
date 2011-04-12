/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.core.test.debug;

import org.ebayopensource.vjet.eclipse.internal.debug.ui.VjetDebugOptionsManager;

import junit.framework.TestCase;

public class StepFilteringPatternTest extends TestCase {

	public void testPattern(){
		String pattern = "dbgp:///*";
		String[] regExp = VjetDebugOptionsManager.convert2RegExp(new String[]{pattern});
		assertTrue("dbgp:///temp_4.js".matches(regExp[0]));
		assertFalse("jar:dbgp:///temp_4.js".matches(regExp[0]));
		assertFalse("jar:dbgp://localhost/temp_4.js".matches(regExp[0]));
		
		pattern = "*org.ebayopensource.vjo/VjBootstrap*";
		regExp = VjetDebugOptionsManager.convert2RegExp(new String[]{pattern});
		assertTrue("file:/c:/org.ebayopensource.vjo/VjBootstrap_3.js".matches(regExp[0]));
		assertTrue("org.ebayopensource.vjo/VjBootstrap_3.js".matches(regExp[0]));
		
		pattern = "*org.ebayopensource.vjo/VjBootstrap";
		regExp = VjetDebugOptionsManager.convert2RegExp(new String[]{pattern});
		assertTrue("file:/c:/org.ebayopensource.vjo/VjBootstrap".matches(regExp[0]));
		assertFalse("org.ebayopensource.vjo/VjBootstrap_3.js".matches(regExp[0]));
	}
	
	public void testPatternConvertRegExp(){
		String pattern = "dbgp:///*";
		String[] regExp = VjetDebugOptionsManager.convert2RegExp(new String[]{pattern});
		assertEquals("dbgp:///.*", regExp[0]);
		
		pattern = "*org.ebayopensource.vjo/VjBootstrap*";
		regExp = VjetDebugOptionsManager.convert2RegExp(new String[]{pattern});
		assertEquals(".*org\\.ebayopensource\\.vjo/VjBootstrap.*", regExp[0]);
		
		pattern = "*org\\ebayopensource\\vjo\\VjBootstrap*";
		regExp = VjetDebugOptionsManager.convert2RegExp(new String[]{pattern});
		assertEquals(".*org/ebayopensource/vjo/VjBootstrap.*", regExp[0]);

		pattern = "*org\\ebayopensource\\vjo\\VjBootstrap.js*";
		regExp = VjetDebugOptionsManager.convert2RegExp(new String[]{pattern});
		assertEquals(".*org/ebayopensource/vjo/VjBootstrap\\.js.*", regExp[0]);
	}
}
