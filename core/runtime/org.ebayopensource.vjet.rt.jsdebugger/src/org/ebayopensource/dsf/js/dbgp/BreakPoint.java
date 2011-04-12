/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.js.dbgp;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class BreakPoint {

	static int s_last_id = 0;

	protected final String m_file;
	protected final int m_line;
	protected final int m_id;
	protected boolean m_enabled = true;
	protected boolean m_isTemporary = false;
	protected int m_hitValue = 0;
	protected int m_hitCondition = 0;
	protected int m_currentHitCount = 0;
	protected String m_expression;
	protected boolean m_isExitBreakpoint;
	protected String m_method;
	protected boolean m_isReturn;
	protected boolean m_isWatch;
	protected boolean m_isCall;
	protected boolean m_isModification;
	protected boolean m_isAccess;
	private String m_type;

	protected BreakPoint(Map<String, String> options) {

		m_type = options.get("-t");
		if (m_type.equals("call") || m_type.equals("return")) {
			m_method = options.get("-m");
			m_isReturn = m_type.equals("return");
			m_isCall = m_type.equals("call");
		}
		if (m_type.equals("watch")) {
			m_isWatch = true;
		}

		String uri = options.get("-f");
		if (uri != null) {
			// modify by patrick
			if (uri.startsWith("dbgp")) {
				m_file = uri;
			} else {
				try {
					m_file = new URL(uri).toExternalForm();
				} catch (MalformedURLException mue) {
					throw new RuntimeException(mue);
				}
			}
			// end modify
		} else {
			m_file = "";
		}

		String line = options.get("-n");
		if (line != null) {
			m_line = Integer.parseInt(line);
		} else {
			m_line = -1;
		}

		String tm = options.get("-r");
		if (tm != null) {
			m_isTemporary = tm.equals("1");
		}
		String hitValue = options.get("-h");
		if (hitValue != null) {
			m_hitValue = Integer.parseInt(hitValue);
		}
		String hitCondition = options.get("-o");
		setHitCondition(hitCondition);
		String exp = options.get("--");

		String disable = options.get("-s");
		if (disable.equals("disabled")) {
			setEnabled(false);
		}
		if (exp != null) {
			m_expression = Base64Helper.decodeString(exp);
			if (m_expression != null)
				m_expression = m_expression.trim();
		}
		if (m_isWatch) {
			m_isModification = m_expression.charAt(m_expression.length() - 1) == '1';
			m_isAccess = m_expression.charAt(m_expression.length() - 2) == '1';
			m_expression = m_expression.substring(0, m_expression.length() - 2);
		}

		m_id = s_last_id++;
	}

	protected void setHitCondition(String hitCondition) {
		if (hitCondition != null) {
			if (hitCondition.equals(">=")) {
				m_hitCondition = 1;
			}
			if (hitCondition.equals("==")) {
				m_hitCondition = 2;
			}
			if (hitCondition.equals("%")) {
				m_hitCondition = 3;
			}
		}
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((m_file == null) ? 0 : m_file.hashCode());
		result = prime * result + m_line;
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final BreakPoint other = (BreakPoint) obj;
		if (m_file == null) {
			if (other.m_file != null) {
				return false;
			}
		} else if (!m_file.equals(other.m_file)) {
			return false;
		}
		if (m_line != other.m_line) {
			return false;
		}
		return true;
	}

	protected boolean isEnabled() {
		return m_enabled;
	}

	protected void setEnabled(boolean enabled) {
		m_enabled = enabled;
	}

	public String getType() {
		return m_type;
	}

	public String getState() {
		return "";
	}

	public String getHitCondition() {
		if (m_hitCondition == 1) {
			return ">=";
		}
		if (m_hitCondition == 2) {
			return "==";
		}
		if (m_hitCondition == 3) {
			return "%";
		}
		return "==";
	}
}
