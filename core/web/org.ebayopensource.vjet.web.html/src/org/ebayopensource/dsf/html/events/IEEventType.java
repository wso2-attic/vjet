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

public class IEEventType extends BaseEnum implements IDomEventType {

	public static final IEEventType ERROR = new IEEventType("error", 0);
	public static final IEEventType PASTE = new IEEventType("paste", 1);
	public static final IEEventType COPY = new IEEventType("copy", 2);
	
	
	protected IEEventType(String name, int id) {
		super(id, name);
		// TODO Auto-generated constructor stub
	}


	private static final long serialVersionUID = 5025470935660044136L;

	// ------- Type specific interfaces -------------------------------//
	/** Get the enumeration instance for a given value or null */
	public static IEEventType get(int key) {
		return (IEEventType)getEnum(IEEventType.class, key);
	}   
	/** Get the enumeration instance for a given value or return the
	 *  elseEnum default.
	 */
	public static IEEventType getElseReturn(int key, EventType elseEnum) {  
		return (IEEventType)getElseReturnEnum(IEEventType.class, key, elseEnum);
	}   
	/** Return an bidirectional iterator that traverses the enumeration
	 *  instances in the order they were defined.
	 */
	public static ListIterator iterator() {
		return getIterator(IEEventType.class);
	} 
	
}
