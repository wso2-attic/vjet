/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.naming;

import java.io.Serializable;

/** 
 * The short name is an ID that is used on the client side.
 * The long name is more readable and debuggable for the server side.
 */
public interface IDsfId extends Serializable, Cloneable {
	public static final Mode SINGLE = new Mode("single");
	public static final Mode MULTIPLE = new Mode("multiple");

	static final class Mode implements Serializable, Cloneable {
		private static final long serialVersionUID = 1L;
		final String m_type;
		private Mode(final String type) {
			m_type = type;
		}		
	}

	/**
	 * @return
	 */
	public abstract String getLongName();
	/**
	 * @return
	 */
	public abstract String getShortName();
	public abstract String toString();
	/**
	 * @return
	 */
	public abstract Mode getIdMode();
	/**
	 * @param string
	 */
	public abstract void setIdMode(Mode mode);
	/**
	 * @param string
	 */
	public abstract void setLongName(String string);
	/**
	 * @param string
	 */
	public abstract void setShortName(String string);
	
	public abstract void assertStandardId(String s);
}