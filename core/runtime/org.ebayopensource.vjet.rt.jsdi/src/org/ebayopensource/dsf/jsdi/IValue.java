/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsdi;

public interface IValue {
	
	Object UNDEFINED = new Object() {
		public String toString() {
			return "undefined";
		}
	};

	//pre-defined set of ids for Scriptal Object
	long UNKNOWN_ID = -1;
	long SCOPE_ID = -2; //special ID for current scope.
	long THIS_ID = -3; //special ID for pseudo-variable "this"
	long ROOT_ID = -4;

	/**
	 * Returns a unique ID for the object referred to by this variable.
	 * If two variables point to the same underlying object, their
	 * getId() functions will return the same value.
	 * 
	 * This is only meaningful for variables that store an Object. 
	 * For other types of variables (e.g. integers and
	 * strings), this returns <code>UNKNOWN_ID</code>.
	 */
	long getId();
	
	VariableType getType();

	String getTypeName();

	/**
	 * Returns the value of the variable, as an Object.  The return
	 * value will always be one of the following:
	 * 
	 * <ul>
	 * <li> <code>null</code> </li>
	 * <li> <code>Value.UNDEFINED</code> </li>
	 * <li> a <code>Boolean</code> </li>
	 * <li> a <code>Double</code> (careful, it might be <code>Double.NaN</code>) </li>
	 * <li> a <code>String</code> </li>
	 * <li> a <code>Long</code> if this value represents a non-primitive
	 * type, such as an Object.  If it is a Long, then it is the id of
	 * the Value (the same value returned by <code>getId()</code>).
	 * </ul>
	 */
	//Object getValueAsObject(ISession session);

	/**
	 * Returns the value of the variable, converted to a string.  Strings
	 * are returned as the exact value of the string itself, with no
	 * extra quotation marks and no escaping of characters within the
	 * string.
	 */
	String getObjectValueAsString(ISession session);

	/**
	 * Returns all child members of this variable.  Can only be called for
	 * variables of type Object.
	 */
	IVariable[] getMembers(ISession session);

	/**
	 * Returns a specific child member of this variable.  Can only be called for
	 * variables of type <code>Object<code>.
	 * @param name a member name
	 * @return the specified child member, or null if there is no such child.
	 */
	IVariable getMember(String name, ISession session);
	
	IVariable getMember(int childIndex, ISession session);

	/**
	 * Returns the number of child members of this variable.  If called for
	 * a variable which has a simple type such as integer or string,
	 * returns zero.
	 */
	int getMemberCount(ISession session);
}
