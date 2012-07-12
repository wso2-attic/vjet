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

public class PayloadTypeEnum extends BaseEnum {

	public static final PayloadTypeEnum RAW = new PayloadTypeEnum("RAW", 0);
	public static final PayloadTypeEnum XML = new PayloadTypeEnum("XML", 1);
	public static final PayloadTypeEnum NVPAIR = new PayloadTypeEnum("NVPAIR", 2);
	public static final PayloadTypeEnum JSON = new PayloadTypeEnum("JSON", 3);
	public static final PayloadTypeEnum JSCALLBACK = new PayloadTypeEnum("JSCALLBACK", 4);
	public static final PayloadTypeEnum SOAP = new PayloadTypeEnum("SOAP", 5);

	//-----------------------------------------------------------------//
	// Template code follows....do not modify other than to replace    //
	// enumeration class name with the name of this class.             //
	//-----------------------------------------------------------------//   
	private PayloadTypeEnum(String name, int intValue) {
		super(intValue, name);
	}
	// ------- Type specific interfaces -------------------------------//
	/** Get the enumeration instance for a given value or null */
	public static PayloadTypeEnum get(int key) {
		return (PayloadTypeEnum) getEnum(PayloadTypeEnum.class, key);
	}
	/** Get the enumeration instance for a given name or null */
	public static PayloadTypeEnum get(String name) {
		return (PayloadTypeEnum) getEnum(PayloadTypeEnum.class, name);
	}
	/** Get the enumeration instance for a given value or return the
	 *  elseEnum default.
	 */
	public static PayloadTypeEnum getElseReturn(int key, PayloadTypeEnum elseEnum) {
		return (PayloadTypeEnum) getElseReturnEnum(
			PayloadTypeEnum.class,
			key,
			elseEnum);
	}
	/** Return an bidirectional iterator that traverses the enumeration
	 *  instances in the order they were defined.
	 */
	public static ListIterator iterator() {
		return getIterator(PayloadTypeEnum.class);
	}

}
