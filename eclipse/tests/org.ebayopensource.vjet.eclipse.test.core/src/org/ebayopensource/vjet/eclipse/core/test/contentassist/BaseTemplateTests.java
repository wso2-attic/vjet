/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.core.test.contentassist;

import org.ebayopensource.vjet.eclipse.core.test.parser.AbstractVjoModelTests;

public class BaseTemplateTests extends AbstractVjoModelTests {
	public void testForTemplate() throws Exception {
		String template = "for";
		String [] names = {"foreach - iterate over array", "for - iterate over array"};
		String project = TestConstants.PROJECT_NAME_VJETPROJECT;
		new TemplateTestUtil().templateBasedTest(
				template, names, project, "partials/CTypeTemplateTest.js");
	}
	
	public void testWithTemplate() throws Exception {
		String template = "with";
		String [] names = {"with - With expression"};
		String project = TestConstants.PROJECT_NAME_VJETPROJECT;
		new TemplateTestUtil().templateBasedTest(
				template, names, project, "partials/CTypeTemplateTest.js");
	}
	
	public void testWhileTemplate() throws Exception {
		String template = "while";
		String [] names = {"while - while loop"};
		String project = TestConstants.PROJECT_NAME_VJETPROJECT;
		new TemplateTestUtil().templateBasedTest(
				template, names, project, "partials/CTypeTemplateTest.js");
	}
	
	public void testDoWhileTemplate() throws Exception {
		String template = "do";
		String [] names = {"do - do-while"};
		String project = TestConstants.PROJECT_NAME_VJETPROJECT;
		new TemplateTestUtil().templateBasedTest(
				template, names, project, "partials/CTypeTemplateTest.js");
	}
	
	public void testIfTemplate() throws Exception {
		String template = "if";
		String [] names = {"if - if statement", "ifelse - if - else statement"};
		String project = TestConstants.PROJECT_NAME_VJETPROJECT;
		new TemplateTestUtil().templateBasedTest(
				template, names, project, "partials/CTypeTemplateTest.js");
	}
	
	public void testTryTemplate() throws Exception {
		String template = "try";
		String [] names = {"try - try catch block", "try - try finally block", 
				"try - try catch finally block"};
		String project = TestConstants.PROJECT_NAME_VJETPROJECT;
		new TemplateTestUtil().templateBasedTest(
				template, names, project, "partials/CTypeTemplateTest.js");
	}
}
