/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.service;

import java.util.ListIterator;

import org.ebayopensource.dsf.common.enums.BaseEnum;

/**
 * 
 */
public class TransportTypeEnum extends BaseEnum {

	private static final long serialVersionUID = 1L;

	public static final TransportTypeEnum IN_PROCESS =
		new TransportTypeEnum("InProc", 0);
	
	@Deprecated
	/**
	 * replaced with a set of specific remote transport types, such as XHR, JSONP and FLASH.
	 */
	public static final TransportTypeEnum REMOTE =
		new TransportTypeEnum("Remote", 1);
	/**
	 * The XMLHttpRequest Object specification defines an API that provides scripted client functionality for transferring data between a client and a server.
	 * <p>
	 * http://www.w3.org/TR/XMLHttpRequest/
	 */
	public static final TransportTypeEnum XHR =
		new TransportTypeEnum("XHR", 2);
	/**
	 * JSON with Padding, a JSON extension wherein a prefix is specified as an input argument of the call itself.
	 * <p>
	 * http://en.wikipedia.org/wiki/JSONP#JSONP
	 */
	public static final TransportTypeEnum JSONP =
		new TransportTypeEnum("JSONP", 3);
	/**
	 * Proprietary remote transport based on Flash. (Recommended only for cross-domain post of large amount of data when there are no other means)
	 */
	public static final TransportTypeEnum FLASH =
		new TransportTypeEnum("FLASH", 4);
	//-----------------------------------------------------------------//
	// Template code follows....do not modify other than to replace    //
	// enumeration class name with the name of this class.             //
	//-----------------------------------------------------------------//   
	private TransportTypeEnum(String name, int intValue) {
		super(intValue, name);
	}
	// ------- Type specific interfaces -------------------------------//
	/** Get the enumeration instance for a given value or null */
	public static TransportTypeEnum get(int key) {
		return (TransportTypeEnum) getEnum(TransportTypeEnum.class, key);
	}
	/** Get the enumeration instance for a given value or return the
	 *  elseEnum default.
	 */
	public static TransportTypeEnum getElseReturn(
		int key,
		TransportTypeEnum elseEnum) {
		return (TransportTypeEnum) getElseReturnEnum(
			TransportTypeEnum.class,
			key,
			elseEnum);
	}
	/** Return an bidirectional iterator that traverses the enumeration
	 *  instances in the order they were defined.
	 */
	public static ListIterator iterator() {
		return getIterator(TransportTypeEnum.class);
	}

}
