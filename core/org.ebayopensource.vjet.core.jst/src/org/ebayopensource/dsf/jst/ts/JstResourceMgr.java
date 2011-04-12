/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.ts;

import java.io.InputStream;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.ebayopensource.dsf.jst.IJstLib;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.dsf.jst.ts.util.JstTypeDependencyCollector;
import org.ebayopensource.dsf.jst.ts.util.JstTypeDependencyHelper;
import org.ebayopensource.dsf.jst.ts.util.JstTypeSerializer;
import org.ebayopensource.dsf.ts.TypeSpace;
import org.ebayopensource.dsf.ts.group.Group;


class JstResourceMgr {
	
	private boolean m_initialized = false;
	private JstTypeSpaceMgr m_tsMgr;
	private TypeSpace<IJstType,IJstNode> m_ts;
	
	//
	// Constructor
	//
	JstResourceMgr(JstTypeSpaceMgr tsMgr){
		assert tsMgr != null : "tsMgr cannot be null";
		m_tsMgr = tsMgr;
		m_ts = m_tsMgr.getTypeSpaceImpl();
	}
	
	public boolean initialize(){
		if (!m_initialized){
			m_initialized = true;
		}
		return m_initialized;
	}
	
	/**
	 * Load JST types library from an input stream
	 * @param is InputStream for a serialized JstType resource library
	 * @param grpName type space group name for the library
	 */
	public void loadLibrary(InputStream is, String grpName) {
		if (m_ts == null) {
			return;
		}
		Group<IJstType> group = 
			new Group<IJstType>(grpName, new JstTypeDependencyCollector());
		m_ts.addGroup(group);
		group.addEntities(JstTypeDependencyHelper.toMap(JstTypeSerializer.getInstance().deserialize(is)));
	}
	
	public void loadLibrary(IJstLib lib, String grpName) {
		if (m_ts == null) {
			return;
		}
		Group<IJstType> group = 
			new Group<IJstType>(grpName, new JstTypeDependencyCollector());
		m_ts.addGroup(group);
		Map<String,IJstType> map = new LinkedHashMap<String,IJstType>();
		for (JstType t: lib.getAllTypes(true)){
			group.setGroupNameInJstType(t);
			map.put(t.getName(), t);
			
			// put inner types in map as well
			//
			List<? extends IJstType> innerTypes = t.getEmbededTypes();
			
			if (innerTypes != null) {
				for (IJstType type : innerTypes) {
					map.put(type.getName(), type);
				}
			}
		}
		group.addEntities(map);
	}
	
	public void persistTypeSpace() {
		if (!m_tsMgr.getConfig().shouldPersistTypeSpace()) {
			return;
		}
		if (m_ts == null) {
			return;
		}
		persistGroups(m_ts.getGroups().values());
	}

	private void persistGroups(Collection<Group<IJstType>> groups) {
		//TODO complete implementation
		
	}
}
