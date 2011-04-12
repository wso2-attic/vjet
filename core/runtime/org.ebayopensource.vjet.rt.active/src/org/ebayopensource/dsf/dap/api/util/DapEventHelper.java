/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dap.api.util;

import org.ebayopensource.dsf.active.dom.html.AHtmlElement;
import org.ebayopensource.dsf.active.dom.html.AHtmlType;
import org.ebayopensource.dsf.active.dom.html.ANode;
import org.ebayopensource.dsf.dap.rt.JsBase;
import org.ebayopensource.dsf.jsnative.HtmlElement;
import org.ebayopensource.dsf.jsnative.HtmlInput;
import org.ebayopensource.dsf.jsnative.HtmlSelect;
import org.ebayopensource.dsf.jsnative.HtmlTextArea;
import org.ebayopensource.dsf.jsnative.Node;
import org.ebayopensource.dsf.jsnative.events.Event;
import org.ebayopensource.dsf.jsnative.events.EventTarget;
import org.ebayopensource.dsf.jsnative.events.KeyboardEvent;

public class DapEventHelper extends JsBase {

	//
	// Singleton
	//
	private static final DapEventHelper s_instance = new DapEventHelper();
	private DapEventHelper(){}
	public static DapEventHelper getInstance(){
		return s_instance;
	}
	
	//
	// API
	//
	public String getSrcId(Event event){
		EventTarget target = event.getTarget(); 
		if (target instanceof AHtmlElement){
			return ((AHtmlElement)target).getId();
		}
		return null;
	}

	public Node getSrcNode(Event event){
		EventTarget target = event.getTarget(); 
		ANode aNode = null;
		if (target instanceof AHtmlElement){
			aNode = ((AHtmlElement)target);
		}
		return aNode;
	}
	
	public HtmlElement getSrcElement(Event event){
		EventTarget target = event.getTarget(); 
		HtmlElement e = null;
		if (target instanceof AHtmlElement){
			e = ((HtmlElement)target);
		}
		return e;
	}
	
	public <T extends HtmlElement> T getSrcElement(Event event, AHtmlType<T> elementType){
		EventTarget target = event.getTarget(); 
		HtmlElement e = null;
		if (target instanceof HtmlElement){
			return (T)target;
		}
		return null;
	}
	
	public String getValue(Event event){
		if (event == null){
			return null;
		}
		EventTarget src = event.getTarget();
		if (src instanceof HtmlInput){
			return ((HtmlInput)src).getValue();
		}
		else if (src instanceof HtmlTextArea){
			return ((HtmlTextArea)src).getValue();
		}
		else if (src instanceof HtmlSelect){
			return ((HtmlSelect)src).getValue();
		}
		return null;
	}
	
	public int getKeyCode(Event event){
		if (event == null){
			return -1;
		}
		if (event instanceof KeyboardEvent){
			return ((KeyboardEvent)event).getKeyCode();
		}
		else {
			return -1;
		}
	}
}
