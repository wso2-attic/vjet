/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.jsunit;

import java.util.List;

import org.mozilla.mod.javascript.StackInspector.StackInfo;

public class AssertEqualFailure extends JsUnitFailure {
	
	private final Object m_value;
	private final Object m_expected;

	
	public AssertEqualFailure(List<StackInfo> stacks, String expected, String value) {
		super(stacks);
		m_value = value;
		m_expected = expected;

	}
	
	public Object getValue() {
		return m_value;
	}
	
	public Object getExpected() {
		return m_expected;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("assertEqual failed").append(NEW_LINE)
		  .append("current value is: ").append(m_value).append(NEW_LINE)
		  .append("expected value was: ").append(m_expected).append(NEW_LINE)
		  .append(getStackTrace()).append(NEW_LINE);

		return sb.toString();
	}

}
