/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.tests;



import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

import org.ebayopensource.af.common.error.ErrorList;
import org.ebayopensource.af.common.error.ErrorObject;
import org.ebayopensource.dsf.javatojs.report.DefaultErrorReporter;
import org.ebayopensource.dsf.javatojs.report.ErrorReporter;



//@ModuleInfo(value="DsfPrebuild",subModuleId="JavaToJs")
public class ErrorReporterTests {
	
	@Test
	//@Category( { P4, FUNCTIONAL })
	//@Description("Test ErrorReporter functionality")
	public void testError() {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		ErrorReporter reporter = new DefaultErrorReporter(new PrintStream(
				stream));
		reporter.error("test error", null, 1, 2);
		assertTrue(reporter.hasErrors());

		reporter.reportErrors();
		assertTrue(stream.toString().indexOf("test error") != -1);

		ErrorList errors = reporter.getErrors();
		assertEquals(1, errors.size());

		ErrorObject eo = errors.get(0);
		assertTrue(eo.isError());
		assertTrue(eo.getParameters().getValueByName("message").indexOf("test error") != -1);
		assertEquals("1", eo.getParameters().getValueByName("line"));

		reporter.error("here is another error", null, 2, 9);
		assertTrue(reporter.hasErrors());

		reporter.reportErrors();
		assertTrue(stream.toString().indexOf("here is another error") != -1);

		errors = reporter.getErrors();
		assertEquals(2, errors.size());
	}

	@Test
	//@Category( { P4, FUNCTIONAL })
	//@Description("Test the error reporting capabilities of ErrorReporter can be turned off")
	public void testErrorDisabled() {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		ErrorReporter reporter = new DefaultErrorReporter(new PrintStream(
				stream));
		reporter.setReportErrors(false);
		reporter.error("test error", null, 1, 2);
		assertFalse(reporter.hasErrors());

		reporter.reportErrors();
		assertEquals(0, stream.size());
	}

	@Test
	//@Category( { P4, FUNCTIONAL })
	//@Description("Test the warning reporting capabilities of ErrorReporter")
	public void testWarning() {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		ErrorReporter reporter = new DefaultErrorReporter(new PrintStream(
				stream));
		reporter.warning("test warning", null, 1, 2);
		assertTrue(reporter.hasWarnings());

		reporter.reportWarnings();
		assertTrue(stream.toString().indexOf("test warning") != -1);

		ErrorList warnings = reporter.getWarnings();
		assertEquals(1, warnings.size());

		ErrorObject eo = warnings.get(0);
		assertTrue(eo.isWarning());
		assertTrue(eo.getParameters().getValueByName("message").indexOf("test warning") != -1);
		assertEquals("1", eo.getParameters().getValueByName("line"));

		reporter.warning("here is another warning", null, 3, 10);
		assertTrue(reporter.hasWarnings());

		reporter.reportWarnings();
		assertTrue(stream.toString().indexOf("here is another warning") != -1);

		warnings = reporter.getWarnings();
		assertEquals(2, warnings.size());
	}

	@Test
	//@Category( { P4, FUNCTIONAL })
	//@Description("Test the warning reporting capabilities of ErrorReporter can be turned off")
	public void testWarningDisabled() {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		ErrorReporter reporter = new DefaultErrorReporter(new PrintStream(
				stream));
		reporter.setReportWarnings(false);
		reporter.error("test warning", null, 1, 1);
		assertFalse(reporter.hasWarnings());

		reporter.reportWarnings();
		assertEquals(0, stream.size());
	}

}
