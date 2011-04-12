/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.js;

import java.util.Vector;

public class JSAnalysisListener implements JSListener {

	public JSAnalysisListener() {
	}

	private Vector m_actions = new Vector();

	public void reset() {
		m_actions.removeAllElements();
	}

	public String doAction(
		int actionType,
		String name,
		String value,
		String targetId) {

		String localValue = value;
		if (actionType == JSAction.GET_COOKIE) {
			localValue = cookieAction(localValue);
		} else if (actionType == JSAction.GET_INPUT_VALUE) {
			//to see if there is value be set
			localValue = inputAction(name, targetId, localValue);
		} else if (actionType == JSAction.GET_INPUT_CHECKED) {
			//to see if there is value be set
			localValue = inputCheckedAction(name, targetId, localValue);
		} else if (actionType == JSAction.GET_SELECTED_INDEX) {
			localValue = selectedIndexAction(name, targetId, localValue);
		} else if (actionType == JSAction.GET_TEXTAREA_VALUE) {
			//to see if there is value be set
			localValue = textareaAction(name, targetId, localValue);

		}

		//get cookie has no side effect, so don't keep it in the list
		if (actionType != JSAction.GET_COOKIE) {
			m_actions.add(new JSAction(actionType, name, localValue, targetId));
		}

		return value; //return the same value passed in
	}

	private String textareaAction(String name, String targetId, String value) {
		String localValue = value;
		int numActions = m_actions.size();
		for (int i = numActions - 1; i >= 0; i--) {
			JSAction action = (JSAction) m_actions.get(i);
			if (action.m_actionType == JSAction.SET_TEXTAREA_VALUE
				&& action.m_actor.equals(name)
				&& action.m_targetId.equals(targetId)) {
				localValue = action.m_value;
			}
		}
		//most likely no user enters any input yet, 
		//provide some default value for more logic be tested
		if (localValue == null || localValue.length() == 0) {
			localValue = "_dsf_" + name;
		}
		return localValue;
	}

	private String selectedIndexAction(
		String name,
		String targetId,
		String value) {
		String localValue = value;
		//to see if there is value be set
		int numActions = m_actions.size();
		for (int i = numActions - 1; i >= 0; i--) {
			JSAction action = (JSAction) m_actions.get(i);
			if (action.m_actionType == JSAction.SET_SELECTED_INDEX
				&& action.m_actor.equals(name)
				&& action.m_targetId.equals(targetId)) {
				localValue = action.m_value;
			}
		}
		return localValue;
	}

	private String inputCheckedAction(
		String name,
		String targetId,
		String value) {
		String localValue = value;
		int numActions = m_actions.size();
		for (int i = numActions - 1; i >= 0; i--) {
			JSAction action = (JSAction) m_actions.get(i);
			if (action.m_actionType == JSAction.SET_INPUT_CHECKED
				&& action.m_actor.equals(name)
				&& action.m_targetId.equals(targetId)) {
				localValue = action.m_value;
			}
		}
		//most likely no user enters any input yet, 
		//provide some default value for more logic be tested
		if (localValue == null || localValue.length() == 0) {
			localValue = "false";
		}
		return localValue;
	}

	private String inputAction(String name, String targetId, String value) {
		String localValue = value;
		int numActions = m_actions.size();
		for (int i = numActions - 1; i >= 0; i--) {
			JSAction action = (JSAction) m_actions.get(i);
			if (action.m_actionType == JSAction.SET_INPUT_VALUE
				&& action.m_actor.equals(name)
				&& action.m_targetId.equals(targetId)) {
				localValue = action.m_value;
			}
		}
		//most likely no user enters any input yet, 
		//provide some default value for more logic be tested
		if (localValue == null || localValue.length() == 0) {
			localValue = "_dsf_" + name;
		}
		return localValue;
	}

	private String cookieAction(String value) {
		String localValue = value;
		//to see if there is cookie be set
		int numActions = m_actions.size();
		for (int i = numActions - 1; i >= 0; i--) { //in reverse order
			JSAction action = (JSAction) m_actions.get(i);
			if (action.m_actionType == JSAction.SET_COOKIE) {
				localValue = action.m_value;
			}
		}
		return localValue;
	}

	public int getLength() {
		return m_actions.size();
	}

	public JSAction get(int index) {
		if (index < 0 || index >= m_actions.size()) {
			return null;
		}
		return (JSAction) m_actions.get(index);
	}
}
