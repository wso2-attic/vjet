/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.state;

import org.ebayopensource.dsf.common.Id;

public class StateId extends Id {
	
	private static final long serialVersionUID = 1L;
	public static StateId TERMINAL = new StateId(Integer.MAX_VALUE, "Terminal");

	/**
	 * Create a new Id instance with a default package name
	 * of "_" and using the String value of nextSequence().
	 * 
	 * The Id returned is NOT registered with the EnumManager
	 * and thus does not support any of the normal Enum characteristics
	 * such as next(), previous(), identity protection, reconstitution after
	 * Serialization, iterators or other static lookup methods
	 */
	public StateId() {
		this(nextDefaultName()) ;	
	}

	/**
	 * Create a new Id instance with a default package name
	 * of "_" using the specified name.  The name must not
	 * be null else we throw a DarwinRuntimeException
	 * exception
	 * 
	 * The Id returned is NOT registered with the EnumManager
	 * and thus does not support any of the normal Enum characteristics
	 * such as next(), previous(), identity protection, reconstitution after
	 * Serialization, iterators or other static lookup methods
	 */	
	public StateId(final String name) {
		this(getNextEnumSequence(), name, false) ;
	}
	
	public StateId(final int enumId, final String name) {
		this(enumId, name, true) ;
	}
	
	public StateId(final int enumId, final String name, final boolean register) {
		super(enumId, name, register) ;
	}

}
