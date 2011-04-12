/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.ts;

import java.io.File;
import java.util.List;

import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.ts.event.group.AddGroupEvent;

public interface IJstTypeLoader {
	
	public static class SourceType {
		private String m_groupName;
		private String m_fileName;
		private String m_source;
		private File m_file;
		private IJstType m_jstType;
		
		public SourceType(String groupName, String fileName) {
			m_groupName = groupName;
			m_fileName = fileName;
		}
		
		public SourceType(String groupName, String fileName, String source) {
			m_groupName = groupName;
			m_fileName = fileName;
			m_source = source;
		}
		
		public SourceType(String groupName, String fileName, String source, File file) {
			m_groupName = groupName;
			m_fileName = fileName;
			m_source = source;
			m_file = file;
		}
		
		public SourceType(String groupName, IJstType jstType) {
			m_groupName = groupName;
			m_fileName = jstType.getName();
			m_jstType = jstType;
		}
				
		public void setGroupName(String groupName) {
			m_groupName = groupName;
		}
		
		public void setFileName(String fileName) {
			m_fileName = fileName;
		}
		
		public void setSource(String source) {
			m_source = source;
		}
		
		public void setJstType(IJstType jstType) {
			m_jstType = jstType;
		}
				
		public String getGroupName() {
			return m_groupName;
		}
		
		public String getFileName() {
			return m_fileName;
		}
				
		public String getSource() {
			return m_source;
		}
		
		public File getFile() {
			return m_file;
		}
		
		public IJstType getJstType() {
			return m_jstType;
		}
		
	}
	
	public List<SourceType> loadJstTypes(List<AddGroupEvent> groupList);

}
