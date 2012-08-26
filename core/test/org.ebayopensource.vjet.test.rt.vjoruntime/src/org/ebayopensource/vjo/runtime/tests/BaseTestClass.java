/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.runtime.tests;

import java.io.IOException;
import java.net.URL;

import org.ebayopensource.dsf.jsnative.anno.BrowserType;
import org.ebayopensource.dsf.jsrunner.JsRunner;
import org.ebayopensource.dsf.util.JavaSourceLocator;
import org.ebayopensource.vjo.jsunit.VjoJsUnit;
import org.ebayopensource.vjo.runner.VjoRunner;
import org.eclipse.core.runtime.FileLocator;
import org.junit.Assert;
import org.mozilla.mod.javascript.JavaScriptException;

public class BaseTestClass {
	protected final void runJsTest(final String fullyQualifiedVjoName,
			BrowserType browserType) throws IOException {
		
		if(runJsTestInternal(fullyQualifiedVjoName, browserType)!=null){
			Assert.fail("See \"Standard Error Console\" for details.");
		}
		
	}
	
	
	protected final RuntimeException runJsTestNoAssert(final String fullyQualifiedVjoName,
			BrowserType browserType) throws IOException {
		return runJsTestInternal(fullyQualifiedVjoName, browserType);
	}
	
	private final RuntimeException runJsTestInternal(final String fullyQualifiedVjoName,
			final BrowserType browserType) throws IOException {
	
	URL fileUrl = JavaSourceLocator.getInstance().getSourceUrl(
			fullyQualifiedVjoName, ".js");
	if(fileUrl.getProtocol().contains("bundleresource")){
		fileUrl = FileLocator.resolve(fileUrl);
	}
	VjoRunner runner = new VjoRunner(new JsRunner.ProgramInfo() {
		public BrowserType getBrowserType() {
			return browserType;
		}
	}, false);
	VjoJsUnit jsUnit = new VjoJsUnit();
	runner.enableJsUnit(jsUnit);
	
	try {
		runner
				.exec(fileUrl, fullyQualifiedVjoName,
						new String[0]);
	} catch (RuntimeException rte) {

		final StackTraceElement[] traceElements = Thread.currentThread()
				.getStackTrace();
		int counter = 0;
		for (final StackTraceElement traceElement : traceElements) {
			if (traceElement.getMethodName().equals("runJsTest")) {
				final StackTraceElement trace = traceElements[counter + 1];
				System.err.println("TestCase \"" + trace.getMethodName()
						+ "\" failed at line # " + trace.getLineNumber()
						+ " of class [" + trace.getClassName() + "]");
			}
			counter++;
		}

		if (rte.getCause() != null
				&& (rte.getCause() instanceof JavaScriptException)) {
			final JavaScriptException javaScriptException = (JavaScriptException) rte
					.getCause();

			System.err.println("Message (JS)      : "
					+ javaScriptException.details());
			System.err.print("Stacktrace(JS)    : \n"
					+ javaScriptException.getScriptStackTrace());
			
			return javaScriptException;
		} else {
			
			System.err.println(rte.getMessage());
			return rte;
		}
		
		
	}
	return null;
	}

}
