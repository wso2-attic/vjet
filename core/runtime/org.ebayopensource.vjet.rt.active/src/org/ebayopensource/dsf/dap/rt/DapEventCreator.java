/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dap.rt;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.ebayopensource.dsf.active.dom.html.AHtmlElement;
import org.ebayopensource.dsf.active.dom.html.AHtmlHelper;
import org.ebayopensource.dsf.active.dom.html.ANode;
import org.ebayopensource.dsf.common.exceptions.DsfRuntimeException;
import org.ebayopensource.dsf.dap.event.AKeyEvent;
import org.ebayopensource.dsf.dap.event.AMouseEvent;
import org.ebayopensource.dsf.dap.event.DapEvent;
import org.ebayopensource.dsf.html.events.EventType;
import org.ebayopensource.dsf.jsnative.Node;
import org.ebayopensource.dsf.jsnative.events.EventTarget;
import org.ebayopensource.dsf.liveconnect.client.DLCEvent;
import org.ebayopensource.dsf.liveconnect.client.DLCEvent.KeyInfo;
import org.ebayopensource.dsf.liveconnect.client.DLCEvent.Position;

public final class DapEventCreator {
	
	private Map<String,EventType> s_eventTypes = new HashMap<String,EventType>(10);
	//
	// Singleton
	//
	private static DapEventCreator s_instance = new DapEventCreator();
	private DapEventCreator(){};
	static DapEventCreator getInstance(){
		return s_instance;
	}
	
	//
	// Package protected
	//
	DapEvent createEvent(DLCEvent dlcEvent){ 
		String srcId = dlcEvent.getSrcId();
		Node jsElem = null;
		String srcByPath = dlcEvent.getSrcPath();
		if (srcId == null && srcByPath == null){
			throw new DsfRuntimeException("both srcId and srcPath are null");
		}
		jsElem = AHtmlHelper.getElementReference(srcByPath, srcId);
		if (jsElem == null || !(jsElem instanceof EventTarget)){
			// TODO uncomment the exception after fix test failures
			throw new DsfRuntimeException("eventTarget is null at: " + srcByPath);
//			jsElem = DapCtx.ctx().getWindow().getDocument().getBody();
		}
		DapEventInfo evtInfo = new DapEventInfo();
		Position position = dlcEvent.getPosition();
		if (position != null){
			evtInfo.setClientX(position.getClientX());
			evtInfo.setClientY(position.getClientY());
			evtInfo.setScreenX(position.getScreenX());
			evtInfo.setScreenY(position.getScreenY());
			evtInfo.setPageX(position.getPageX());
			evtInfo.setPageY(position.getPageY());
		}
		
		KeyInfo keyInfo = dlcEvent.getKeyInfo();
		if (keyInfo != null){
			evtInfo.setAltKey(keyInfo.isAltKey());
			evtInfo.setCtrlKey(keyInfo.isCtrlKey());
			evtInfo.setShiftKey(keyInfo.isShiftKey());
			evtInfo.setMetaKey(keyInfo.isMetaKey());
			evtInfo.setKeyCode(keyInfo.getKeyCode());
			evtInfo.setCharCode(keyInfo.getCharCode());
			evtInfo.setKeyIdentifier(keyInfo.getKeyIdentifier());
			evtInfo.setKeyLocation(keyInfo.getKeyLocation());
		}
		
		evtInfo.setButton(dlcEvent.getButton());
		evtInfo.setModifierState(dlcEvent.isModifierState());
		evtInfo.setCancelable(dlcEvent.isCancelable());
		evtInfo.setCancelBubble(dlcEvent.isCancelBubble());
		evtInfo.setTimeStamp(dlcEvent.getTimeStamp());
		evtInfo.setDetail(dlcEvent.getDetail());
		evtInfo.setWhich(dlcEvent.getWhich());
		
		ANode relTarget = AHtmlHelper.getElementReference(dlcEvent.getRelatedTarget(), dlcEvent.getRelatedTargetId());
		if(relTarget!=null && (relTarget instanceof AHtmlElement)){
			evtInfo.setRelatedTarget((AHtmlElement)relTarget);
		}
		return createEvt((EventTarget)jsElem, dlcEvent.getType(), evtInfo);
	}

	DapEvent createEvent(EventTarget jsElem, String eventType, DapEventInfo evtInfo){
		return createEvt(jsElem, eventType, evtInfo);
	}
	
	//
	// Private
	//
	private DapEvent createEvt(EventTarget jsElem, String eventType, DapEventInfo evtInfo){
		final String ON = "on";
		int onInd = eventType.indexOf(ON);
		if(onInd!=-1){
			eventType = eventType.substring(onInd+ON.length());
		}
		if (EventType.FOCUS.getName().equals(eventType)){
			DapEvent event = new DapEvent(jsElem,EventType.FOCUS);
			setDapEventInfo(event,evtInfo);
			return event;
		}
		else if (EventType.BLUR.getName().equals(eventType)) {
			DapEvent event = new DapEvent(jsElem,EventType.BLUR);
			setDapEventInfo(event,evtInfo);
		return event;
		}
		else if (EventType.CLICK.getName().equals(eventType)) {
			AMouseEvent event = new AMouseEvent(jsElem,EventType.CLICK); 
			setMouseEventInfo(event,evtInfo);
			return event;
		}
		else if (EventType.DBLCLICK.getName().equals(eventType)) {
			AMouseEvent event = new AMouseEvent(jsElem,EventType.DBLCLICK); 
			setMouseEventInfo(event,evtInfo);
			return event;
		}
		else if (EventType.MOUSEDOWN.getName().equals(eventType)) {
			AMouseEvent event = new AMouseEvent(jsElem,EventType.MOUSEDOWN);
			setMouseEventInfo(event,evtInfo);			
			return event;
		}
		else if (EventType.MOUSEOVER.getName().equals(eventType)) {
			AMouseEvent event = new AMouseEvent(jsElem,EventType.MOUSEOVER); 
			setMouseEventInfo(event,evtInfo);
			return event;
		}
		else if (EventType.MOUSEOUT.getName().equals(eventType)) {
			AMouseEvent event = new AMouseEvent(jsElem,EventType.MOUSEOUT); 
			setMouseEventInfo(event,evtInfo);
			return event;
		}
		else if (EventType.MOUSEUP.getName().equals(eventType)) {
			AMouseEvent event = new AMouseEvent(jsElem,EventType.MOUSEUP); 
			setMouseEventInfo(event,evtInfo);
			return event;
		}
		else if (EventType.MOUSEMOVE.getName().equals(eventType)) {
			AMouseEvent event = new AMouseEvent(jsElem,EventType.MOUSEMOVE); 
			setMouseEventInfo(event,evtInfo);
			return event;
		}
		else if (EventType.KEYUP.getName().equals(eventType)){
			AKeyEvent event = new AKeyEvent(jsElem,EventType.KEYUP); 
			setKeyEventInfo(event,evtInfo);
			return event;
		}
		else if (EventType.KEYDOWN.getName().equals(eventType)){
			AKeyEvent event = new AKeyEvent(jsElem,EventType.KEYDOWN);
			setKeyEventInfo(event,evtInfo);
			return event;
		}
		else if (EventType.KEYPRESS.getName().equals(eventType)){
			AKeyEvent event = new AKeyEvent(jsElem,EventType.KEYPRESS);
			setKeyEventInfo(event,evtInfo);
			return event;
		}
		else if (EventType.LOAD.getName().equals(eventType)){
			DapEvent event = new DapEvent(jsElem,EventType.LOAD);
			setDapEventInfo(event,evtInfo);
			return event;
		}
		else if (EventType.UNLOAD.getName().equals(eventType)){
			DapEvent event = new DapEvent(jsElem,EventType.UNLOAD);
			setDapEventInfo(event,evtInfo);
			return event;
		}
		else if (EventType.RESIZE.getName().equals(eventType)){
			DapEvent event = new DapEvent(jsElem,EventType.RESIZE);
			setDapEventInfo(event,evtInfo);
			return event;
		}
		else if (EventType.SCROLL.getName().equals(eventType)){
			DapEvent event = new DapEvent(jsElem,EventType.SCROLL);
			setDapEventInfo(event,evtInfo);
			return event;		
		}
		else if (EventType.SELECT.getName().equals(eventType)){
			DapEvent event = new DapEvent(jsElem,EventType.SELECT);
			setDapEventInfo(event,evtInfo);
			return event;
		}
		else if (EventType.SUBMIT.getName().equals(eventType)){
			DapEvent event = new DapEvent(jsElem,EventType.SUBMIT);
			setDapEventInfo(event,evtInfo);
			return event;
		}
		else if (EventType.RESET.getName().equals(eventType)){
			DapEvent event = new DapEvent(jsElem,EventType.RESET);
			setDapEventInfo(event,evtInfo);
			return event;
		}
		else if (EventType.ERROR.getName().equals(eventType)){
			DapEvent event = new DapEvent(jsElem,EventType.ERROR);
			setDapEventInfo(event,evtInfo);
			return event;
		}
		else if (EventType.READYSTATECHANGE.getName().equals(eventType)){
			DapEvent event = new DapEvent(jsElem,EventType.READYSTATECHANGE);
			setDapEventInfo(event,evtInfo);
			return event;
		}
		else {
			DapEvent event = new DapEvent(jsElem,getEventType(eventType));
			setDapEventInfo(event,evtInfo);
			return event;
		}
	}
	@SuppressWarnings("unchecked")
	private EventType getEventType(final String eventName){
		EventType type = getET(eventName);
		if (type != null){
			return type;
		}
		
		Iterator<EventType> itr = EventType.getIterator(EventType.class);
		while (itr.hasNext()){
			type = itr.next();
			if (type.getName().equals(eventName)){
				setET(eventName, type);
				return type;
			}
		}
		
		return null;
	}
	
	private synchronized EventType getET(String name){
		return s_eventTypes.get(name);
	}
	
	private synchronized void setET(String name, EventType type){
		s_eventTypes.put(name, type);
	}

	private void setDapEventInfo(DapEvent event, DapEventInfo evtInfo){
		if(evtInfo!=null){
			event.setCanBubble(!evtInfo.isCancelBubble());
			event.setCancelable(evtInfo.isCancelable());
			event.setTimestamp(evtInfo.getTimeStamp());
		}			
	}
	
	private void setMouseEventInfo(AMouseEvent event, DapEventInfo evtInfo){
		if(evtInfo!=null){
			event.setClientX(evtInfo.getClientX());
			event.setClientY(evtInfo.getClientY());
			event.setScreenX(evtInfo.getScreenX());
			event.setScreenY(evtInfo.getScreenY());
			event.setAltKey(evtInfo.isAltKey());
			event.setShiftKey(evtInfo.isShiftKey());
			event.setCtrlKey(evtInfo.isCtrlKey());
			event.setCanBubble(!evtInfo.isCancelBubble());
			event.setButton(evtInfo.getButton());
			event.setMetaKey(evtInfo.isMetaKey());
			event.setCancelable(evtInfo.isCancelable());
			event.setTimestamp(evtInfo.getTimeStamp());
			event.setDetail(evtInfo.getDetail());
			event.setRelatedTarget(evtInfo.getRelatedTarget());
			event.setPageX(evtInfo.getPageX());
			event.setPageY(evtInfo.getPageY());
		}			
	}

	private void setKeyEventInfo(AKeyEvent event, DapEventInfo evtInfo){
		if(evtInfo!=null){
			event.setCanBubble(!evtInfo.isCancelBubble());
			event.setMetaKey(evtInfo.isMetaKey());
			event.setKeyCode(evtInfo.getKeyCode());
			event.setWhich(evtInfo.getWhich());
			event.setCharCode(evtInfo.getCharCode());
			event.setKeyIdentifier(evtInfo.getKeyIdentifier());
			event.setKeyLocation(evtInfo.getKeyLocation());
			event.setModifierState(evtInfo.isModifierState());
			event.setCancelable(evtInfo.isCancelable());
			event.setTimestamp(evtInfo.getTimeStamp());
			event.setDetail(evtInfo.getDetail());
		}			
	}

}
