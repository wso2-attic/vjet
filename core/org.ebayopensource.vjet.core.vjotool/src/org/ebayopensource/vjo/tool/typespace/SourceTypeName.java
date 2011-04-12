/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.tool.typespace;

import org.ebayopensource.dsf.ts.type.TypeName;

public class SourceTypeName extends TypeName {

	private String source;

	public static final int ADDED = 0x1;

	public static final int REMOVED = 0x2;

	public static final int CHANGED = 0x4;

	private int action = ADDED;

	public static final byte[] EMPTY_CONTENT = new byte[0];

	public SourceTypeName(String groupName, String typeName, String source) {
		super(groupName, typeName);
		this.source = source;
	}

	public SourceTypeName(String groupName, String typeName) {
		super(groupName, typeName);
	}

	public String source() {
		return source;
	}

	public int getAction() {
		return action;
	}

	public void setAction(int action) {
		this.action = action;
	}

	/**
	 * 
	 * @return
	 * @author
	 */
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("SourceTypeName[");
		buffer.append("groupName = ").append(groupName());
		buffer.append(" typeName = ").append(typeName());
		buffer.append(" action = ").append(action);
		buffer.append("]");
		return buffer.toString();
	}

	public void setSource(String source) {
		this.source = source;
	}

	
}
