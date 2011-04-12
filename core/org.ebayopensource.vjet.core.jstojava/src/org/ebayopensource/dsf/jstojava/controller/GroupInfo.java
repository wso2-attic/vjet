/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.controller;

import java.util.List;

public class GroupInfo {
	private final String m_groupName;
	private final List<String> m_dependentGroups;
	
	public GroupInfo(String groupName, List<String> dependentGroups) {
		m_groupName = groupName;
		m_dependentGroups = dependentGroups;
	}
	
	public String getGroupName() {
		return m_groupName;
	}
	
	public List<String> getDependentGroups() {
		return m_dependentGroups;
	}
}
