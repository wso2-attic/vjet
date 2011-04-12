/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.liveconnect;

import java.util.ArrayList;
import java.util.List;

public class DLCPageHistory {
	
	public static String NEW = "NEW";
	public static String FORWARD = "FORWARD";
	public static String BACK = "BACK";
	public static String RELOAD = "RELOAD";
	
	private static int s_pageId = 0;
	
	List<String> m_locations = new ArrayList<String>();
	int m_currentIndex = -1;
	
	public synchronized String add(String location) {
		if (m_currentIndex == -1) {
			m_locations.add(location);
			m_currentIndex++;
			return NEW;
		}
		if (m_currentIndex >= 0 && 
			m_locations.get(m_currentIndex).equals(location)) {
			return RELOAD;
		}
		if (m_currentIndex > 0 && 
			m_locations.get(m_currentIndex - 1).equals(location)) {
			m_currentIndex--;
			return BACK;
		}
		if (m_currentIndex >= 0 && 
			m_currentIndex < (m_locations.size() - 1) &&
			m_locations.get(m_currentIndex + 1).equals(location)) {
			m_currentIndex++;
			return FORWARD;
		}
		if (m_currentIndex < (m_locations.size() - 1)) {
			for (int i = m_locations.size() - 1; i > m_currentIndex; i--) {
				m_locations.remove(i);
			}
		}
		m_locations.add(location);
		m_currentIndex++;
		return NEW;
	}
	
	public synchronized String getCurrentLocation() {
		return m_locations.get(m_currentIndex);
	}
	
	public static synchronized int getNextPageId() {
		return s_pageId ++;
	}
}
