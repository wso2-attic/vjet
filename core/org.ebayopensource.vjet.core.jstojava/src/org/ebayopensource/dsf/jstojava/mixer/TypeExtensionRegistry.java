/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.mixer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Registry of type extensions at Lib/Group level from meta bootstrap.
 */
public class TypeExtensionRegistry {
	
	private static final TypeExtensionRegistry s_instance = new TypeExtensionRegistry();
	
	private Map<String, ExtensionInfo> m_extensionInfos = new LinkedHashMap<String, ExtensionInfo>();
	private Set<String> m_groupsWithMixer = new HashSet<String>();
	
	public static TypeExtensionRegistry getInstance() {
		return s_instance;
	}
	
	public boolean isNonExtendedType(String targetType, String groupId) {
		ExtensionInfo currentGroupInfo = m_extensionInfos.get(groupId);
		if (currentGroupInfo != null) {
			if (currentGroupInfo.isNonExtentedType(targetType)) {
				return true;
			}
		}
		return false;
	}
	
	public List<String> getExtension(
		String targetType, List<String> baseTypes, String groupId, List<String> dependentGroupIds) {

		List<String> extensions = null;
		ExtensionInfo currentGroupInfo = m_extensionInfos.get(groupId);
		if (currentGroupInfo != null) {
			if (currentGroupInfo.isNonExtentedType(targetType)) {
				return null;
			}
			extensions = currentGroupInfo.getCombinedExtensions(targetType);
			if (extensions != null) {
				return extensions;
			}
		}
		
		extensions = new ArrayList<String>();
		if (currentGroupInfo != null) {
			collectExtensions(extensions, currentGroupInfo, targetType, baseTypes);
		} else {
			currentGroupInfo = new ExtensionInfo(null);
			m_extensionInfos.put(groupId, currentGroupInfo);
		}
		
		if (dependentGroupIds != null) {
			for (int i = 0; i < dependentGroupIds.size(); i++) {
				String depGrpId = dependentGroupIds.get(i);
				if (m_groupsWithMixer.contains(depGrpId)) {
					ExtensionInfo info = m_extensionInfos.get(depGrpId);
					collectExtensions(extensions, info, targetType, baseTypes);
				}
			}
		}
		if (extensions.isEmpty()) {
			currentGroupInfo.addNonExtentedType(targetType);
			return null;
		} else {
			currentGroupInfo.addCombinedExtensions(targetType, extensions);
			return extensions;
		}
	}
	
	public void addMixer(TypeMixer mixer) {
		m_extensionInfos.put(mixer.getGroupId(), new ExtensionInfo(mixer));
		update();
	}
	
	public void clear(String groupId) {
		m_extensionInfos.remove(groupId);
		update();
	}
	
	public void clearAll() {
		m_extensionInfos.clear();
		m_groupsWithMixer.clear();
	}	
	
	public void update() {
		m_groupsWithMixer.clear();
		for (Map.Entry<String, ExtensionInfo> entry: m_extensionInfos.entrySet()) {
			ExtensionInfo info = entry.getValue();
			info.clear();
			if (info.m_mixer != null) {
				m_groupsWithMixer.add(entry.getKey());
			}
		}
	}
	
	private void collectExtensions(List<String> extensions,
		ExtensionInfo info, String targetType, List<String> baseTypes) {
		if (info == null) {
			return;
		}
		collect(extensions, info, targetType);
		if (baseTypes != null) {
			for (String baseType : baseTypes) {
				collect(extensions, info, baseType);
			}
		}
	}
	
	private void collect(List<String> extensions, ExtensionInfo info, String targetType) {
		List<String> ext = info.getMixInTypes(targetType);
		if (ext != null) {
			extensions.addAll(ext);
		}
	}
	
	private static class ExtensionInfo {	
		private final TypeMixer m_mixer;
		private Set<String> m_nonExtendedTypes = new HashSet<String>();
		private Map<String, List<String>> m_combinedExtensions = new HashMap<String, List<String>>();
		
		private ExtensionInfo(TypeMixer mixer) {
			m_mixer = mixer;
		}
		
		private List<String> getMixInTypes(String targetType) {
			return m_mixer == null ? null : m_mixer.getMixInTypes(targetType);
		}
		
		private void addNonExtentedType(String targetType) {
			m_nonExtendedTypes.add(targetType);
		}
		
		private boolean isNonExtentedType(String targetType) {
			return m_nonExtendedTypes.contains(targetType);
		}
		
		private List<String> getCombinedExtensions(String targetType) {
			return m_combinedExtensions.get(targetType);
		}
		
		private void addCombinedExtensions(String targetType, List<String> extensions) {
			m_combinedExtensions.put(targetType, extensions);
		}
		
		private void clear() {
			m_nonExtendedTypes.clear();
			m_combinedExtensions.clear();
		}
	}
}
