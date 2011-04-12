/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dap.event;

import org.ebayopensource.dsf.active.client.ActiveObject;
import org.ebayopensource.dsf.dap.rt.DapCtx;
import org.ebayopensource.dsf.html.events.EventType;
import org.ebayopensource.dsf.jsnative.events.Event;
import org.ebayopensource.dsf.jsnative.events.EventTarget;

@SuppressWarnings("serial")
public class DapEvent extends ActiveObject implements Event {
	
	private EventTarget m_target;
	private EventType m_eventType; 
	
	private boolean m_canBubble = false;
	private boolean m_cancelable = false;
	private long m_timestamp = 0;
	private boolean m_stopPropagation = false;
	private boolean m_stopImmediatePropagation = false;
	private boolean m_preventDefault = false;
	private boolean m_returnValue = true;
	private Object m_view;
	
	//
	// Constructor(s)
	//
	public DapEvent(final EventTarget target, final EventType eventType) {
		m_target = target;
		m_eventType = eventType;
		popScriptable();
	}

	private void popScriptable(){
		populateScriptable(DapEvent.class,DapCtx.ctx().getWindow().getBrowserType());
	}
	
	public DapEvent() {
		popScriptable();
	}

	//
	// Satisfy Event
	//
	public EventTarget getTarget() {
		return m_target;
	}
	
	public EventTarget getSrcElement() {
		return m_target;
	}
	
	public String getType(){
		return m_eventType == null ? null : m_eventType.getName();
	}
	
	protected void setType(String typeName) {
		EventType type = EventType.get(typeName);
		if (type != null) {
			m_eventType = type;
		}
	}
	
	public boolean getBubbles(){
		return m_canBubble;
	}
	
	public boolean getCancelBubble() {
		return !m_canBubble;
	}
	
	public boolean getCancelable(){
		return m_cancelable;
	}
	
    public long getTimeStamp(){
		return m_timestamp;
	}

    public void stopPropagation(){
    	m_stopPropagation = true;
	}
    
    public void preventDefault(){
    	m_preventDefault = true;
	}
    
    public void stopImmediatePropagation(){
    	m_stopImmediatePropagation = true;
	}
    
    public boolean getDefaultPrevented(){
    	return m_preventDefault;
    }
    
	public boolean getReturnValue() {
		return m_returnValue;
	}
	
	//
	// API
	//
    public void setTarget(EventTarget target){
    	m_target = target;
    }
    
	public void setSrcElement(EventTarget target){
    	m_target = target;
    }
   
	public void setCanBubble(boolean bubble) {
		m_canBubble = bubble;
	}
	
	public void setCancelBubble(boolean cancelBubble) {
		m_canBubble = !cancelBubble;
	}

	public void setCancelable(boolean cancelable) {
		this.m_cancelable = cancelable;
	}

	public Object getView() {
		return m_view;
	}

	public void setView(Object view) {
		m_view = view;
	}

	public void setTimestamp(long m_timestamp) {
		this.m_timestamp = m_timestamp;
	}

	public void setEventType(EventType type) {
		m_eventType = type;
	}

	public boolean isStopPropagation() {
		return m_stopPropagation;
	}

	public boolean isStopImmediatePropagation() {
		return m_stopImmediatePropagation;
	}

	public void setReturnValue(boolean value) {
		m_returnValue = value;
	}
}