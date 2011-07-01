/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.translator;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ 
	Ast2JstTest.class,
	BlockTranslatorTest.class,
	CommentParsingTest.class,
	ExpectsTranslatorTest.class,
	ExpressionTranslatorTest.class,
	FieldTranslatorTest.class,
	MethodTranslatorTest.class,
	MixinTranslatorTest.class,
	NeedsTranslatorTest.class,
	//OtherTranslatorTests.class,
	ProgramTranslatorTest.class,
	PropsTranslatorTest.class,
	ProtosTranslatorTest.class,
	StatementTranslatorTest.class, 
	TypeTranslatorTest.class,
	GlobalsTranslatorTest.class,
	ETypeTests.class
})
public class AllTranslatorsTests {

	public static Test suite() {
	    return new JUnit4TestAdapter(AllTranslatorsTests.class);
	}
}
