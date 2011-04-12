/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.ts.event.type;

import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.ts.event.ISourceEvent;
import org.ebayopensource.dsf.ts.event.ISourceEventCallback;
import org.ebayopensource.dsf.ts.event.ISourceEventListener;
import org.ebayopensource.dsf.ts.event.dispatch.IEventListenerHandle;
import org.ebayopensource.dsf.ts.type.TypeName;

/**
 * Note that following changes are currently ignored:
 * 1. Extends
 * 2. Satisfiers
 * 3. Access modifiers 
 */
public final class ModifyTypeEvent extends TypeEvent {
	private String m_src;
	private String m_groupName;
	private String m_fileName;
	private IJstType m_type;

	//
	// Constructors
	//
	public ModifyTypeEvent(TypeName typeName, IJstType jstType){
		super(typeName);
		if (typeName == null){
			throw new AssertionError("typeName cannot be null");
		}
		m_src = null;
		m_groupName = typeName.groupName();
		m_fileName = typeName.typeName();
		m_type = jstType;
	}
		
	public ModifyTypeEvent(String groupName, String fileName, String src){
		super(new TypeName(groupName, fileName));
		m_src = src;
		m_groupName = groupName;
		m_fileName = fileName;
		m_type = null;
	}

	//
	// Satisfy IJstEvent
	//
	/**
	 * @see ISourceEvent#dispatch(ISourceEventListener)
	 */
	public void dispatch(ISourceEventListener listener){
		if (listener == null || !isAppropriateListener(listener)){
			return;
		}
		((ITypeEventListener)listener).onTypeModified(this, null,null);
	}
	
	/**
	 * @see ISourceEvent#dispatch(ISourceEventListener,IEventListenerHandle,ISourceEventCallback)
	 */
	public void dispatch(ISourceEventListener listener, IEventListenerHandle handle, ISourceEventCallback callback){
		if (listener == null || !isAppropriateListener(listener)){
			return;
		}
		((ITypeEventListener)listener).onTypeModified(this, handle, callback);
	}
	
	//
	// API
	//
	public String getGroupName(){
		return m_groupName;
	}
	
	public String getFileName(){
		return m_fileName;
	}
	
	public String getTypeSource(){
		return m_src;
	}
	
	public IJstType getType(){
		return m_type;
	}
	
	public boolean shouldLock() { return true; }
}
