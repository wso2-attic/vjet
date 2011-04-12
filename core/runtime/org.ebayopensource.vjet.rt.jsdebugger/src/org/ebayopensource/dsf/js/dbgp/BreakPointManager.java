/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.js.dbgp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BreakPointManager {
	public static final String SCRIPT_STR = "#Script#";
	private Map<String, Map<Integer, BreakPoint>> m_fileMap = new HashMap<String, Map<Integer, BreakPoint>>();
	private Map<String, BreakPoint> m_ids = new HashMap<String, BreakPoint>();
	private Map<String, BreakPoint> m_returnNames = new HashMap<String, BreakPoint>();
	private Map<String, List<BreakPoint>> m_watchpoints = new HashMap<String, List<BreakPoint>>();
	private Map<String, BreakPoint> m_callNames = new HashMap<String, BreakPoint>();

	public void removeBreakPoint(String id) {
		BreakPoint object = (BreakPoint) m_ids.get(id);
		if (object != null)
			removeBreakPoint(object);
	}

	public final void addBreakPoint(BreakPoint point) {
		if (point.m_isReturn) {
			m_returnNames.put(point.m_method, point);
		}
		if (point.m_isCall) {
			m_callNames.put(point.m_method, point);
		}

		if (point.m_isWatch) {
			List<BreakPoint> object = m_watchpoints.get(point.m_expression);
			if (object == null) {
				object = new ArrayList<BreakPoint>();
				m_watchpoints.put(point.m_expression, object);
			}
			object.add(point);

		}
		Map<Integer, BreakPoint> object = m_fileMap.get(point.m_file);
		if (object == null) {
			object = new HashMap<Integer, BreakPoint>();
			m_fileMap.put(point.m_file, object);
		}
		object.put(Integer.valueOf(point.m_line), point);
		m_ids.put("p" + point.m_id, point);
	}

	public void removeBreakPoint(BreakPoint point) {
		if (point.m_isReturn) {
			m_returnNames.remove(point.m_method);
		}
		if (point.m_isCall) {
			m_callNames.remove(point.m_method);
		}
		if (point.m_isWatch) {

			m_watchpoints.remove(point.m_expression);
		}
		Map<Integer, BreakPoint> object = m_fileMap.get(point.m_file);
		if (object == null) {
			return;
		}
		object.remove(Integer.valueOf(point.m_line));
		m_ids.remove("p" + point.m_id);
	}

	public BreakPoint hit(String sourcePath, int lineNumber) {
		Map<Integer, BreakPoint> q = m_fileMap.get(sourcePath);
		if (q == null) {
			return null;
		}
		
		Integer lnNumber = Integer.valueOf(lineNumber);
		BreakPoint point = q.get(lnNumber);
		if (point == null) {
			return null;
		}
		point.m_currentHitCount++;
		if (point.m_hitValue > 0) {

			if (point.m_hitCondition == 1) {
				if (point.m_hitValue >= point.m_currentHitCount) {
					return null;
				}
			}
			if (point.m_hitCondition == 2) {
				if (point.m_hitValue != point.m_currentHitCount) {
					return null;
				}
			}
			if (point.m_hitCondition == 3) {
				if (point.m_currentHitCount % point.m_hitValue != 0) {
					return null;
				}
			}
		}
		if (point.m_isTemporary) {
			q.remove(lnNumber);
		}
		return point;
	}

	public void updateBreakpoint(String id, String newState, String newLine,
			String hitValue, String hitCondition, String condexpression) {
		BreakPoint p = (BreakPoint) m_ids.get(id);
		if (p != null) {
			if (newState != null) {
				newState = newState.trim();
				if (newState.equals("enabled")) {
					p.setEnabled(true);
				} else if (newState.equals("disabled")) {
					p.setEnabled(false);
				}
			}
			if (newLine != null) {
				Map<Integer, BreakPoint> map = m_fileMap.get(p.m_file);
				Integer nl = Integer.valueOf(p.m_line);
				BreakPoint po = map.get(nl);
				if (po != p) {
					throw new RuntimeException("Error: mismatch break point at line " + nl);
				} else {
					map.remove(nl);
					map.put(Integer.valueOf(newLine), p);
				}
			}
			if (hitValue != null) {
				p.m_hitValue = Integer.parseInt(hitValue);
			}
			if (hitCondition != null) {
				p.setHitCondition(hitCondition);
			}
			if (!p.m_isWatch) {
				p.m_expression = condexpression;
			}
			else {
				p.m_isModification = condexpression.charAt(condexpression.length() - 1) == '1';
				p.m_isAccess = condexpression.charAt(condexpression.length() - 2) == '1';
				p.m_expression = condexpression.substring(0, condexpression.length() - 2);
			}
		}
	}


	public BreakPoint hitEnter(String sn) {
		return(BreakPoint) m_callNames.get(sn);
	}
	
	public BreakPoint hitExit(String sn) {
		return (BreakPoint) m_returnNames.get(sn);
	}

	public List<BreakPoint> getWatchPoints(String property) {
		return m_watchpoints.get(property);
	}

	public BreakPoint getBreakpoint(String id) {
		return (BreakPoint) m_ids.get(id);
	}

	public void removeBreakPoints()
	{
		m_fileMap.clear();
		m_ids.clear();
		m_returnNames.clear();
		m_watchpoints.clear();
		m_callNames.clear();
	}
}
