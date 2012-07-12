/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.services;

import java.util.ListIterator;

import org.ebayopensource.dsf.common.enums.BaseEnum;

public class ConnectionProtocolEnum extends BaseEnum {

	public static final ConnectionProtocolEnum GET =
		new ConnectionProtocolEnum("GET", 0);
	public static final ConnectionProtocolEnum POST =
		new ConnectionProtocolEnum("POST", 1);

	//-----------------------------------------------------------------//
	// Template code follows....do not modify other than to replace    //
	// enumeration class name with the name of this class.             //
	//-----------------------------------------------------------------//   
	private ConnectionProtocolEnum(String name, int intValue) {
		super(intValue, name);
	}
	// ------- Type specific interfaces -------------------------------//
	/** Get the enumeration instance for a given value or null */
	public static ConnectionProtocolEnum get(int key) {
		return (ConnectionProtocolEnum) getEnum(
			ConnectionProtocolEnum.class,
			key);
	}
	/** Get the enumeration instance for a given value or return the
	 *  elseEnum default.
	 */
	public static ConnectionProtocolEnum getElseReturn(
		int key,
		ConnectionProtocolEnum elseEnum) {
		return (ConnectionProtocolEnum) getElseReturnEnum(
			ConnectionProtocolEnum.class,
			key,
			elseEnum);
	}
	/** Return an bidirectional iterator that traverses the enumeration
	 *  instances in the order they were defined.
	 */
	public static ListIterator iterator() {
		return getIterator(ConnectionProtocolEnum.class);
	}

}
