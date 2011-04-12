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

public class JsUnitFailure {
	
	private final List<StackInfo> m_stacks;
	protected final String NEW_LINE = System.getProperty("line.separator");
	
	public JsUnitFailure(List<StackInfo> stacks) {
		m_stacks = stacks;
	}
	
	public List<StackInfo> getStacks() {
		return m_stacks;
	}
	
	public String getStackTrace() {
		StringBuilder sb = new StringBuilder();
		for (StackInfo info : m_stacks) {
			sb.append(info.toString()).append(NEW_LINE);
		}
		return sb.toString();
	}
}
