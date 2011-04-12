/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.af.common.error;

/**
 * Error arguments list interface.
 * <br><br>
 * The list shall contain no duplicate arguments names.
*/

public interface ErrorArgsInterface {

	/**
	 * Is the arguments list empty?
	 *  @return <b>true</b> if the list is empty, <b>false</b> otherwise
	 */ 
	boolean isEmpty();
	
	/**
	 * How many error arguments are there?
	 *  @return number of arguments
	 */ 
	int size();
	
//	/**
//	 * Get the arguments names.
//	 * @return array of arguments names
//	 */ 
//	String[] getNames();

//	/**
//	 * Get the arguments values.
//	 * @return array of arguments values
//	 */ 
//	String[] getValues();

 	/**
	 * Get argument name given its index.
	 * @param index argument index
	 * @return argument name
	 * @throws IndexOutOfBoundsException
	 */ 
	String getNameByIndex(int index);

// 	/**
//	 * Get argument value given its index.
//	 * @param index argument index
//	 * @return argument value
//	 * @throws IndexOutOfBoundsException
//	 */ 
//	String getValueByIndex(int index);

	/**
	 * Get argument value given its name.
	 * @param name argument name
	 * @return argument value or <code>null</code> if not found
	 * @throws NullPointerException (if supplied name is <code>null</code>)
	 */ 
	String getValueByName(String name);
	
	/**
	 * Add arguments from another arguments list
	 * @param  args another arguments list
	 * @throws NullPointerException (if name or value is <b>null</b>)
	 * @throws RuntimeException (upon attempt to insert duplicate name)
	 */ 
	void add(ErrorArgsInterface args);

	/**
	 * Compare this list of arguments to the other list.
	 * The lists are equal if and only if:
	 * <ul>
	 * <li>Their sizes are equal</li>
	 * <li>The other list contains all names that this list contains</li>
	 * <li>The values corresponding to the same names in lists are equal</li>
	 * </ul>
	 * @param thatErrorArgs another list of error arguments
	 * @return <b>true</b> if the arguments are equal, <b>false</b> otherwise.
	 */ 
	boolean equals(ErrorArgsInterface thatErrorArgs);
	
	/**
	 * Overrides Object.hashCode().
	 * @return hash code
	 */ 
	int hashCode();
	
	/**
	 * Overrides Object.toString().
	 * @return String representation of the error args list
	 */ 
	String toString();
	
}
