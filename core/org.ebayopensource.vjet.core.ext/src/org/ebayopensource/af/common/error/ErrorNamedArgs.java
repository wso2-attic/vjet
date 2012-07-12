/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.af.common.error;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Error arguments list based on String/String name/value pairs.
 * <br><br>
 * Names of arguments are unique in the list. 
 * 
 * @see ErrorUnnamedArgs
 *
*/

public	class		ErrorNamedArgs 
		implements 	ErrorArgsInterface, Serializable, Cloneable {

	/* Initial capacities are tuned for short lists
	 * This is the most frequently used category of ErrorArgs
	 */
	private static final int INITIAL_LIST_CAPACITY = 3;
	private static final int INITIAL_MAP_CAPACITY = 7;

	private HashMap   m_map    = new HashMap(INITIAL_MAP_CAPACITY);
	private ArrayList m_names  = new ArrayList(INITIAL_LIST_CAPACITY);
	
	/**
	 * Default Constructor
	 */ 
	public ErrorNamedArgs() {/*nothing to do here*/}
	
	/**
	 * Convenience constructor
	 * @param args array of two-element arrays representing name/value pairs
	 * @throws IndexOutOfBoundsException if a non-pair is supplied
	 */ 
	public ErrorNamedArgs(String[][] args) {
		add(args);
	}

	// ErrorArgsInterface methods

	/**
	 * How many arguments are there?
	 * @return number of arguments
	 */ 
	public int size() {
		return m_names.size();
	}
	
	/**
	 * Is the arguments list empty?
	 *  @return <b>true</b> if the list is empty, <b>false</b> otherwise
	 */ 
	public boolean isEmpty() {
		return (size() == 0);
	}

//	/**
//	 * Get the arguments names 
//	 * @return array of arguments names
//	 */ 
//	public String[] getNames() {
//		return arrayListToStringArray(m_names);
//	}

//	/**
//	 * Get the arguments values
//	 * @return array of arguments values
//	 */ 
//	public String[] getValues() {
//		return arrayListToStringArray(m_values);
//	}
	
 	/**
	 * Get argument name given its index.
	 * @param index argument index
	 * @return argument name
	 * @throws IndexOutOfBoundsException
	 */ 
	public String getNameByIndex(int index) {
		return (String) m_names.get(index);
	}

//	/**
//	 * Get the argument value given the argument index
//	 * @param index argument index
//	 * @return argument value
//	 * @throws IndexOutOfBoundsException
//	 */ 
//	public String getValueByIndex(int index) {
//		return (String) m_values.get(index);
//	}

	/**
	 * Get the argument value given the argument name
	 * @param name argument name
	 * @return argument value or <b>null</b> if argument is not found
	 * @throws NullPointerException (if supplied name is <b>null</b>)
	 */ 
	public String getValueByName(String name) {
		if(name == null) {
			throw new NullPointerException
			("The argument name must be non-null");
		}
		return (String) m_map.get(name);
	}

	/**
	 * Compare error arguments lists
	 * @param thatErrorArgs another arguments list
	 * @return <b>true</b> if the arguments are equal, <b>false</b> otherwise
	 */ 
	public boolean equals(ErrorArgsInterface thatErrorArgs) {
		if (thatErrorArgs == null) {
			return false;
		}

		int size = m_names.size();
		
		if (size != thatErrorArgs.size()) {
			return false;
		}

		for (int i = 0; i < size; i++) {
			/*
			 * Arguments names and values are assumed to be non-null.
			 * This is enforced by the <b>add()</b> methods.
			 */
			String name  = (String) m_names.get(i);
			String value = getValueByName(name);

			if (!value.equals(thatErrorArgs.getValueByName(name))) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Overrides Object.equals()
	 * @param object another object
	 * @return <b>true</b> if the arguments are equal, <b>false</b> otherwise
	 */ 
	public boolean equals(Object object) {
		if (object instanceof ErrorArgsInterface) {
			return equals((ErrorArgsInterface) object);
		}
		return false;
	}
	
	/**
	 * Overrides Object.hashCode()
	 * @return hash code
	 */ 
	public int hashCode() {
		int hash = 0;
		int size = this.size();
		
		/*
		 * We assume that elements of m_names and m_values
		 * are non-null.
		 * If they are null, then the class invariant is violated and 
		 * the NullPointerException will be rightfully thrown.
		 */
		for (int i = 0; i < size; i++) {
			final String name = (String) m_names.get(i);
			hash ^= name.hashCode();
			hash ^= (getValueByName(name)).hashCode();
		}
		
		return hash;
	}
	

	// Class-specific adders
	
	/**
	 * Add an argument
	 * @param name argument name
	 * @param value argument value
	 * @throws NullPointerException (if name or argument is null or empty)
	 * @throws RuntimeException (upon attempt to insert duplicate name)
	 */ 
	public void add(String name,String value) {
		if (name == null) {
			throw new NullPointerException
			("Name of the argument added to NamedErrorArgs can't be null");
		} 
		if (name.length() == 0) {
			throw new NullPointerException
			("Name of the argument added to NamedErrorArgs can't be empty");
		} 
		if (value == null) {
			throw new NullPointerException
			("Argument value added to NamedErrorArgs can't be null");
		} 
		if (m_map.get(name) != null) {
			throw new RuntimeException
			("Duplicate NamedErrorArgs name: " + name);
		}
		m_names.add(name);
		m_map.put(name,value);
	}
	
	/**
	 * Add several arguments
	 * @param  args array of two-element arrays representing name/value pairs
	 * @throws NullPointerException (if name or value is <b>null</b>)
	 * @throws RuntimeException (upon attempt to insert duplicate name)
	 */ 
	public void add(String[][] args) {
		for (int i = 0; i < args.length; i++) {
			String[] pair = args[i];
			if (pair.length != 2) {
				throw new IndexOutOfBoundsException
				("Non-pair in the NamedErrorArgs constructor");
			}
			add(pair[0],pair[1]);
		}	
	}
	
	/**
	 * Add arguments from another arguments list
	 * @param  args another arguments list
	 * @throws NullPointerException (if name or value is <b>null</b>)
	 * @throws RuntimeException (upon attempt to insert duplicate name)
	 */ 
	public void add(ErrorArgsInterface args) {
		int size = args.size();
		
		for (int i = 0; i < size; i++) {
			final String name = args.getNameByIndex(i);
			final String value = args.getValueByName(name);
			add(name, value);
		}	
	}
	
	/**
	 * Get String representation
	 * @return "{" name ":" value { "," name ":" value } "}"
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{ ");
		
		int size = size();
		
		for (int i = 0; i < size; i++) {
			if (i != 0) {
				sb.append(", ");
			}
			final String name = getNameByIndex(i);
			final String value = getValueByName(name);
			sb.append(name);
			sb.append(" : ");
			sb.append(value);
		}
		
		sb.append(" }");

		return sb.toString();
	}

	private static final long serialVersionUID = 5323418970760560460L;

 	
	// Auxillary methods

	/**
	 * Converts ArrayList containing strings into String array
	 * @param arrayList ArrayList of Strings
	 * @return Array of Strings
	 */ 
//	private String[] arrayListToStringArray(ArrayList arrayList) {
//		int size = arrayList.size();
//		
//		String[] result = new String[size];
//
//		for (int i = 0; i < size; i++) {
//			result[i] = (String) arrayList.get(i);	
//		}
//		
//		return result;
//	}
}
