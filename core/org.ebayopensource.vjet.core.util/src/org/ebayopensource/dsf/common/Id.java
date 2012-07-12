/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common;
import java.io.Serializable;

import org.ebayopensource.dsf.common.exceptions.DsfRuntimeException;
import org.ebayopensource.dsf.common.enums.BaseEnum;

public abstract class Id extends BaseEnum implements Serializable, Cloneable {	
	private static long s_sequence = 0 ;	// We use for name generation
	private static int s_enumSequence = 0 ; // Enums are have int
	
	//
	// Constructor(s)
	//
	/**
	 * Create a new Id instance with a default package name
	 * of "_" and using the String value of nextSequence().
	 * 
	 * The Id returned is NOT registered with the EnumManager
	 * and thus does not support any of the normal Enum characteristics
	 * such as next(), previous(), identity protection, reconstitution after
	 * Serialization, iterators or other static lookup methods
	 */
	public Id() {
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
	public Id(final String name) {
		this(getNextEnumSequence(), name, false) ;
	}
	
	public Id(final int enumId, final String name) {
		this(enumId, name, true) ;
	}
	
	public Id(final int enumId, final String name, final boolean register) {
		super(enumId, name, register) ;

		if (name == null) {
			throw new DsfRuntimeException(
				"Id must not be null") ;
		}
		
		if (name.trim().length() == 0){
			throw new DsfRuntimeException(
				"Id must not be empty or spaces") ;
		}
	}
	
	//
	// API
	//
	
	/**
	 * Answers the next sequence value.  The sequence values String
	 * value is used in default Id construction.  This value is exposed
	 * in case other code wants/needs to also use a sequence id for 
	 * other objects or for testing.
	 */
	public static synchronized long getNextSequence() {
		return s_sequence++ ;
	}
	
	public static synchronized int getNextEnumSequence() {
		if (s_enumSequence == Integer.MAX_VALUE) {
			s_enumSequence = 0 ;
		}
		return s_enumSequence++ ;
	}
	
	//
	// Overrides from Object
	//
	@Override
	public boolean equals(final Object other) {
		if (other == null) {
			return false ;
		}
		
		if (other instanceof Id == false) {
			return false ;
		}
	
		final Id otherId = (Id)other ;
		
		return getValue() == otherId.getValue()
			&& getName().equals(otherId.getName()) ;
	}
	@Override
	public Object clone() {
		try {
			return super.clone() ;
		}
		catch(CloneNotSupportedException e) {
			throw new DsfRuntimeException(
				"Unexepected failure during clone") ;
		}
	}
	@Override
	public String toString() {
		return "(" + super.getId() + "," + super.getName() + ")";
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return super.hashCode();
	}
	
	//
	// Framework
	//
	protected static synchronized String nextDefaultName() {
		return "Id" + getNextSequence() ;
	}
}
