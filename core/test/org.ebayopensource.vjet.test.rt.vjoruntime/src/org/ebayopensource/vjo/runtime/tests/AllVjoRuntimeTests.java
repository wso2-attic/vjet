/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.runtime.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import org.ebayopensource.vjo.runtime.tests.metatype.BootstrappingTests;
import org.ebayopensource.vjo.runtime.tests.metatype.ConstructorTests;
import org.ebayopensource.vjo.runtime.tests.metatype.FieldsTests;
import org.ebayopensource.vjo.runtime.tests.metatype.MethodsTests;
import org.ebayopensource.vjo.runtime.tests.metatype.NativeTypesTests;
import org.ebayopensource.vjo.runtime.tests.section.globals.GlobalsTests;

@RunWith(Suite.class)
@Suite.SuiteClasses({
		ConstructorTests.class, MethodsTests.class, FieldsTests.class,
		NativeTypesTests.class, BootstrappingTests.class, GlobalsTests.class })
public class AllVjoRuntimeTests {

}

