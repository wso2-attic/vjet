/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.jsunit;

import java.util.ArrayList;
import java.util.List;

import org.ebayopensource.dsf.active.client.ScriptExecutor;
import org.ebayopensource.dsf.dap.rt.BaseScriptable;
import org.mozilla.mod.javascript.Context;
import org.mozilla.mod.javascript.ScriptRuntime;
import org.mozilla.mod.javascript.Scriptable;
import org.mozilla.mod.javascript.ScriptableObject;
import org.mozilla.mod.javascript.StackInspector;

/**
 * An adapter class in server side for vjo.jsunit
 */
public class VjoJsUnit extends BaseScriptable {

	private static final long serialVersionUID = 1L;

	private static final String[] MTD_NAMES = { "assertEquals", "assertTrue",
			"assertFalse", "assertNotNull", "fail" };

	private static final String VJO_INSTANCE = "vjo";

	private static final String VJO_JSUNIT_PROP = "jsunit";

	private static final String[] JS_MTDS_PROXIES = {
			"function assertTrue (arg0) { return vjo.jsunit.assertTrue(arg0); } ",
			"function assertFalse (arg0) { return vjo.jsunit.assertFalse(arg0); } ",
			"function assertEquals (arg0,arg1,arg2) { return vjo.jsunit.assertEquals(arg0,arg1); } ",
			"function assertNotNull (arg0) { return vjo.jsunit.assertNotNull(arg0); } ",
			"function fail (arg0) { return vjo.jsunit.fail(arg0); } " };

	private List<JsUnitFailure> m_failures = new ArrayList<JsUnitFailure>();

	public VjoJsUnit() {
		defineFunctionProperties(MTD_NAMES);
	}

	public void attach(Context cx, Scriptable scope) {
		Object scriptal = Context.javaToJS(this, scope);
		Scriptable vjo = (Scriptable) ScriptableObject.getProperty(scope,
				VJO_INSTANCE);
		ScriptableObject.putProperty(vjo, VJO_JSUNIT_PROP, scriptal);
		for (String script : JS_MTDS_PROXIES) {
			ScriptExecutor.executeScript(script, scope, cx);
		}
	}

	public void assertEquals(Object expected, Object value) {
		if (expected != null && value != null) {
			if (expected instanceof Number && value instanceof Number) {
				expected = ScriptRuntime.toString(expected);
				value = ScriptRuntime.toString(value);

			}
		}

		if (expected != value && !(expected != null && expected.equals(value))) {
			m_failures.add(new AssertEqualFailure(StackInspector.getStack(),
					ScriptRuntime.toString(expected), ScriptRuntime
							.toString(value)));
		}
	}

	public void assertTrue(boolean value) {

		if (!value) {
			m_failures.add(new AssertTrueFailure(StackInspector.getStack()));
		}
	}

	public void assertFalse(boolean value) {
		if (value) {
			m_failures.add(new AssertFalseFailure(StackInspector.getStack()));
		}
	}

	public void assertNotNull(Object actual) {

		if (actual == null) {
			m_failures.add(new AssertTrueFailure(StackInspector.getStack()));
		}
	}
	
	public void fail(String msg) {
		m_failures.add(new Failure(StackInspector.getStack()));
	}

	public List<JsUnitFailure> getFailures() {
		return m_failures;
	}

	public String getFailureMsgs() {
		String failMsg = "";
		for (JsUnitFailure failure : m_failures) {
			failMsg = failMsg + failure.getStackTrace() + failure.NEW_LINE;
		}
		return failMsg;
	}

	public void resetFailures() {
		m_failures.clear();
	}
}