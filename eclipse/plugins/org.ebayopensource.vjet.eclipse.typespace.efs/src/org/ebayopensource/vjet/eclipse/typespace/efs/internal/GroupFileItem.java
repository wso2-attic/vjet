/*******************************************************************************
 * Copyright (c) 2006 Alex Blewitt and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Alex Blewitt - initial API and implementation
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.typespace.efs.internal;


public class GroupFileItem extends GroupItem {

	private String m_name;

	public GroupFileItem(String name, GroupPkgDirectoryItem parent) {
		super(name, parent);
		m_name = name;
	}

	public String getFullName() {
		return m_name;
	}




	
//	public InputStream openInputStream() throws IOException {
////		return getRoot().getInputStream(entry);
//	}
}
