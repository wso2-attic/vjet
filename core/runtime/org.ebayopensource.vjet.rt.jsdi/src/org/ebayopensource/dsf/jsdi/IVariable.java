/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsdi;

/**
 * A Variable is any Script variable, such as a String, Number, Object, etc.
 * It encapsulates the concept of a type and a value.
 */
public interface IVariable {
	/**
	 * The name of the variable.
	 */
	public String getName();


	/**
	 * The class in which this member was actually defined.  For example, if class
	 * B extends class A, and class A has member variable V, then for variable
	 * V, the defining class is always "A", even though the parent variable might
	 * be an instance of class B.
	 */
	public String getDefiningClass();


	/**
	 * Returns the value of the variable.
	 */
	public IValue getValue();

	/**
	 * Returns whether the value of the variable has changed since the last
	 * time the program was suspended.  If the previous value of the
	 * variable is unknown, this function will return <code>false</code>.
	 */
	//public boolean hasValueChanged(ISession session);
}
