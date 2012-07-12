/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.events;

import java.util.ListIterator;

import org.ebayopensource.dsf.common.enums.BaseEnum;

public class EventType extends BaseEnum implements IDomEventType {

	private static final long serialVersionUID = 1L;
	private static int value = 0 ;
	
	private static int id() {
		return value++ ;
	}
	
	// Only valid in DBody and DFrameset elements                                    		                                		                                		                                		                               		                             
	public static final EventType LOAD = new EventType("load");	
	public static final EventType UNLOAD = new EventType("unload");

	// Only valid in DForm elements	
	public static final EventType CHANGE = new EventType("change"); 
	public static final EventType SUBMIT = new EventType("submit");
	public static final EventType RESET = new EventType("reset");
	public static final EventType SELECT = new EventType("select"); 	
	public static final EventType BLUR = new EventType("blur"); 	                               		
	public static final EventType FOCUS = new EventType("focus"); 
	
	// Keyboard events.  NOT valid in base, bdo, br, frame, frameset, head
	// html, iframe, meta, param, script, style and title elements
	public static final EventType KEYDOWN = new EventType("keydown");   
	public static final EventType KEYPRESS = new EventType("keypress");   
	public static final EventType KEYUP = new EventType("keyup");
	
	// Mouse events.  NOT valid in base, bdo, br, frame, frameset, head
	// html, iframe, meta, param, script, style and title elements                               		
	public static final EventType CLICK = new EventType("click");                                    		
	public static final EventType DBLCLICK = new EventType("dblclick"); 
	// ALIAS
	public static final EventType DOUBLECLICK = DBLCLICK; 
	
	public static final EventType MOUSEDOWN = new EventType("mousedown"); 
	public static final EventType MOUSEMOVE = new EventType("mousemove"); 
	public static final EventType MOUSEOUT = new EventType("mouseout"); 
	public static final EventType MOUSEOVER = new EventType("mouseover"); 
	public static final EventType MOUSEUP = new EventType("mouseup");
	
	public static final EventType RESIZE = new EventType("resize");
	public static final EventType SCROLL = new EventType("scroll") ;
	
	// NON standard event but used for timed events
	// TODO move to custom eventtypes enum
	public static final EventType TICK = new EventType("tick");
	
	// IE EVENTS - These were added to support the IE tags:
	// DBgSound, DCustom, DEmbed, DMarquee, DNoBr, DRt, DRuby, DWbr, DXml, DXmp
	//
	// This list includes those that are not defined above, as some of them
	// have already been defined. (Eg. CLICK, DOUBLECLICK, etc.)  
	public static final EventType ACTIVATE = new EventType("activate") ; 
	public static final EventType AFTERUPDATE = new EventType("afterupdate") ;
	public static final EventType BEFOREACTIVATE = new EventType("beforeactivate") ;
	public static final EventType BEFORECOPY = new EventType("beforecopy") ;
	public static final EventType BEFORECUT = new EventType("beforecut") ;
	public static final EventType BEFOREDEACTIVATE = new EventType("beforedeactivate") ;
	public static final EventType BEFOREEDITFOCUS = new EventType("beforeeditfocus") ;
	public static final EventType BEFOREPASTE = new EventType("beforepaste") ;
	public static final EventType BEFOREUPDATE = new EventType("beforeupdate") ;
	public static final EventType CONTEXTMENU = new EventType("contextmenu") ;
	public static final EventType CONTROLSELECT = new EventType("controlselect") ;
	public static final EventType COPY = new EventType("copy") ;
	public static final EventType CUT = new EventType("cut") ;
	public static final EventType DEACTIVATE = new EventType("deactivate") ;
	public static final EventType DRAG = new EventType("drag") ;
	public static final EventType DRAGEND = new EventType("dragend") ;
	public static final EventType DRAGENTER = new EventType("dragenter") ;
	public static final EventType DRAGLEAVE = new EventType("dragleave") ;
	public static final EventType DRAGOVER = new EventType("dragover") ;
	public static final EventType DRAGSTART = new EventType("dragstart") ;
	public static final EventType DROP = new EventType("drop") ;
	public static final EventType ERRORUPDATE = new EventType("errorupdate") ;
	public static final EventType FILTERCHANGE = new EventType("filterchange") ;
	public static final EventType FINISH = new EventType("finish") ;
	public static final EventType FOCUSIN = new EventType("focusin") ;
	public static final EventType FOCUSOUT = new EventType("focusout") ;
	public static final EventType HELP = new EventType("help") ;
	public static final EventType LAYOUTCOMPLETE = new EventType("layoutcomplete") ;
	public static final EventType LOSECAPTURE = new EventType("losecapture") ;
	public static final EventType MOUSEENTER = new EventType("mouseenter") ;
	public static final EventType MOUSELEAVE = new EventType("mouseleave") ;
	public static final EventType MOUSEWHEEL = new EventType("mousewheel") ;
	public static final EventType MOVE = new EventType("move") ;
	public static final EventType MOVEEND = new EventType("moveend") ;
	public static final EventType MOVESTART = new EventType("movestart") ;
	public static final EventType PASTE = new EventType("paste") ;
	public static final EventType PROPERTYCHANGE = new EventType("propertychange") ;
	public static final EventType READYSTATECHANGE = new EventType("readystatechange") ;
	public static final EventType RESIZEEND = new EventType("resizeend") ;
	public static final EventType RESIZESTART = new EventType("resizestart") ;
	public static final EventType SELECTSTART = new EventType("selectstart") ;
	public static final EventType START = new EventType("start") ;
	public static final EventType TIMEERROR = new EventType("timeerror") ;
	public static final EventType BOUNCE = new EventType("bounce") ;
	public static final EventType DOMCONTENTLOADED = new EventType("DOMContentLoaded") ;
	public static final EventType ERROR =  new EventType("error");
//	public static final EventType READYSTATECHANGE = new EventType("readystatechange") ;
	
	// Supported by <img> tag
	public static final EventType ABORT = new EventType("abort");
	
	// Fillout remain HTML 5.0 events - we did some of them like ondragXXX awhile
	// ago.  We're just finishing up here...
//	public static final EventType ABORT = new EventType("abort");
	public static final EventType AFTERPRINT = new EventType("fterprint");
	public static final EventType BEFOREPRINT = new EventType("beforeprint");
//	public static final EventType BLUR = new EventType("blur");
	public static final EventType CANPLAY = new EventType("canplay");
	public static final EventType CANPLAYTHROUGH = new EventType("canplaythrough");
//	public static final EventType CHANGE = new EventType("change");
//	public static final EventType CLICK = new EventType("click");
//	public static final EventType DBLCLICK = new EventType("dblclick");
	public static final EventType DURATIONCHANGE = new EventType("durationchange");
	public static final EventType EMPTIED = new EventType("emptied");
	public static final EventType ENDED = new EventType("ended");
//	public static final EventType FOCUS = new EventType("focus");
	public static final EventType FORMCHANGE = new EventType("formchange");
	public static final EventType HASCHANGE = new EventType("haschange");
	public static final EventType INPUT = new EventType("input");
	public static final EventType FORMINPUT = new EventType("forminput");
	public static final EventType INVALID = new EventType("invalid");
//	public static final EventType LOAD = new EventType("load");
	public static final EventType LOADEDDATA = new EventType("loadeddata");
	public static final EventType LOADEDMETADATA = new EventType("loadedmetadata");
	public static final EventType LOADSTART = new EventType("loadstart");
	public static final EventType OFFLINE = new EventType("offline");
	public static final EventType ONLINE = new EventType("online");
	public static final EventType PAUSE = new EventType("pause");
	public static final EventType PLAY = new EventType("play");
	public static final EventType PLAYING = new EventType("playing");
	public static final EventType POPSTATE = new EventType("popstate");
	public static final EventType PROGRESS = new EventType("progress");
	public static final EventType RATECHANGE = new EventType("ratechange");
	public static final EventType REDO = new EventType("redo");
	public static final EventType SEEKED = new EventType("seeked");
	public static final EventType SEEKING = new EventType("seeking");
//	public static final EventType SELECT = new EventType("select");
	public static final EventType SHOW = new EventType("show");
	public static final EventType STALLED = new EventType("stalled");
	public static final EventType STORAGE = new EventType("storage");
//	public static final EventType SUBMIT = new EventType("submit");
	public static final EventType SUSPEND = new EventType("suspend");
	public static final EventType WAITING = new EventType("waiting");
	public static final EventType TIMEUPDATE = new EventType("timeupdate");
	
	public static final EventType BEFOREONLOAD = new EventType("beforeonload");
	public static final EventType BEFOREUNLOAD = new EventType("beforeunload");
//	public static final EventType CONTEXTMENU = new EventType("contextmenu", 72);
//	public static final EventType DRAG = new EventType("drag", 73);
//	public static final EventType DRAGEND = new EventType("dragend", 74);
//	public static final EventType DRAGENTER = new EventType("dragenter", 75);
//	public static final EventType DRAGLEAVE = new EventType("dragleave", 76);
//	public static final EventType DRAGOVER = new EventType("dragover", 77);
//	public static final EventType DRAGSTART = new EventType("dragstart", 78);
//	public static final EventType DRAGDROP = new EventType("dragdrop", 72);
//	public static final EventType ERROR = new EventType("error");
	public static final EventType MESSAGE = new EventType("message");
//	public static final EventType RESIZE = new EventType("resize");
//	public static final EventType SCROLL = new EventType("scroll");
	public static final EventType UNDO = new EventType("undo");
//	public static final EventType UNLOAD = new EventType("unload");	
	public static final EventType VOLUMECHANGE = new EventType("volumechange");	
	
	//-----------------------------------------------------------------//
	// Template code follows....do not modify other than to replace    //
	// enumeration class name with the name of this class.             //
	//-----------------------------------------------------------------//   
	protected EventType(String name) {
		super(id(), name);
	}   
	
	// ------- Type specific interfaces -------------------------------//
	/** Get the enumeration instance for a given value or null */
	public static EventType get(final int key) {
		return (EventType)getEnum(EventType.class, key);
	}   
	/** Get the enumeration instance for a given value or null */
	public static EventType get(final String name) {
		return (EventType)getEnum(EventType.class, name);
	}   
	/** Get the enumeration instance for a given value or return the
	 *  elseEnum default.
	 */
	public static EventType getElseReturn(final int key, final EventType elseEnum) {  
		return (EventType)getElseReturnEnum(EventType.class, key, elseEnum);
	}   
	/** Return an bidirectional iterator that traverses the enumeration
	 *  instances in the order they were defined.
	 */
	public static ListIterator<EventType> iterator() {
		return getIterator(EventType.class);
	} 
}
