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
 * Announces new type. Fired by IDE (our container) on course of loading or processing new source file.
 *
 * @param <T>
 */
public final class AddTypeEvent<T> extends TypeEvent {
	
	private String m_src;
	private String m_groupName;
	private String m_fileName;
	private IJstType m_type;
	private Object m_userObj;

	//
	// Constructors
	//
	public AddTypeEvent(String fileName, String src){
		this(null, fileName, src, null);
	}
	
	public AddTypeEvent(String fileName, String src, Object userObj){
		this(null, fileName, src, userObj);
	}
	
	public AddTypeEvent(TypeName typeName, IJstType jstType){
		this(typeName, jstType, null);
	}
	
	public AddTypeEvent(TypeName typeName, IJstType jstType, Object userObj){
		super(typeName);
		if (typeName == null){
			throw new AssertionError("typeName cannot be null");
		}
		m_src = null;
		m_groupName = typeName.groupName();
		m_fileName = typeName.typeName();
		m_type = jstType;
		m_userObj = userObj;
	}
	
	public AddTypeEvent(String groupName, String fileName, String src){
		this(groupName, fileName, src, null);
	}
	
	public AddTypeEvent(String groupName, String fileName, String src, Object userObj){
		super(new TypeName(groupName, fileName));
		m_src = src;
		m_groupName = groupName;
		m_fileName = fileName;
		m_userObj = userObj;
	}

	//
	// Satisfy ISourceEvent
	//
	/**
	 * @see ISourceEvent#dispatch(ISourceEventListener)
	 */
	public void dispatch(ISourceEventListener listener){
		if (listener == null){
			return;
		}
		((ITypeEventListener)listener).onTypeAdded(this, null, null);
	}
	
	/**
	 * @see ISourceEvent#dispatch(ISourceEventListener,IEventListenerHandle,ISourceEventCallback)
	 */
	public void dispatch(ISourceEventListener listener, IEventListenerHandle handle, ISourceEventCallback callback){
		if (listener == null){
			return;
		}
		((ITypeEventListener)listener).onTypeAdded(this, handle, callback);
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
	
	public Object getUserObject(){
		return m_userObj;
	}
	
	public boolean shouldLock() { return true; }
}
