/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.ts.event.group;

import org.ebayopensource.dsf.ts.event.ISourceEvent;
import org.ebayopensource.dsf.ts.event.ISourceEventListener;
import org.ebayopensource.dsf.ts.event.dispatch.IEventListenerHandle;
import org.ebayopensource.dsf.common.Z;

/**
 * Base for all group related event types.
 * 
 * 
 */
public abstract class GroupEvent  implements ISourceEvent<IEventListenerHandle> {
	
	private String m_groupName;
	private String m_groupPath;
	
	//
	// Constructors
	//
	/**
	 * Constructor
	 * @param groupName String
	 */
	GroupEvent(String groupName){
		this(groupName, null);
	}

	/**
	 * Constructor
	 * @param groupName String
	 * @param groupPath String
	 */
	GroupEvent(String groupName, String groupPath){
		assert groupName != null : "groupName cannot be null";
		m_groupName = groupName;
		m_groupPath = groupPath;
	}
	
	//
	// Satisfy IJstEvent
	//
	public boolean isAppropriateListener(ISourceEventListener listener){
		return listener instanceof IGroupEventListener;
	}

	//
	// API
	//
	/**
	 * Answer the name of the group
	 * @return String
	 */
	public String getGroupName(){
		return m_groupName;
	}
	
	/**
	 * Answer the path of the group
	 * @return String
	 */
	public String getGroupPath(){
		return m_groupPath;
	}
	
	@Override
	public String toString(){
		Z z = new Z();
		z.format("eventType", getClass().getSimpleName());
		z.format("m_groupName", m_groupName);
		z.format("m_groupPath", m_groupPath);
		return z.toString();
	}
}
