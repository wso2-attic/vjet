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

import java.util.Collection;
import java.util.SortedMap;
import java.util.TreeMap;

public class GroupPkgDirectoryItem extends GroupItem {
	SortedMap children = new TreeMap();

	protected GroupPkgDirectoryItem(String name, GroupPkgDirectoryItem parent) {
		super(name, parent);
	}

	void addChild(GroupItem item) {
		children.put(item.getName(), item);
	}

	public Collection getChildren() {
		return children.values();
	}

	public GroupItem getItem(String name) {
		return (GroupItem) children.get(name);
	}
	public String toString() {
		return super.toString() + children.toString();
	}
}
