/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.af.common.error;

// Java imports
import java.io.Serializable;


/**
 * Error Identifier. Category,sub-category, and name together constitute
 * the "primary key" identifying the error type.
 * ErrorId can also have an error reporting context such as a field or page in
 * the presentation tier. In that case the first token of error id string will
 * represent the id of the context in the form of 
 * 'context.category.subcategory.name'.
 * 
 *  Lopatin
 */

public	class 	 	ErrorId 
		implements	Serializable {

	/**
	 * The character separating the Id fields in the String representation
	 */
	public static final char SEPARATOR = '.';
	
	private String m_context;
	private String m_category;
	private String m_subcategory;
	private String m_name;
	
	/**
	 * Constructor
	 * This constructor takes an error ID as a single string
	 * instead of as three separate strings.
	 * @param errorID error String of the form "category.subcategory.name"
	 * or "context.category.subcategory.name".
	 * @throws IllegalArgumentException
	 */
	public ErrorId(String errorID) {
		if (errorID == null) {
			throw new IllegalArgumentException(
				"Error: null error ID supplied.");
		}

		final String [] tokens = errorID.split("\\.");

		if (tokens.length < 3) {
			throw new IllegalArgumentException(
				"Error: incomplete error ID supplied."
					+ " Error ID must be of the form of 'category.subcategory.name'.");
		}
		
		if (tokens.length > 4) {
			throw new IllegalArgumentException(
				"Error:  too many periods in error ID."
					+ " Error ID must be of the form of 'category.subcategory.name'"
					+ " or 'context.category.subcategory.name'.");
		}
		
		int index = tokens.length - 1;
		m_name = tokens[index--];
		m_subcategory = tokens[index--];
		m_category = tokens[index--];
		if (index >= 0) {
			m_context = tokens[index];
		}
		else {
			m_context = null;
		}
	}
	
	
	/**
	 * Constructor
	 * @param category error category
	 * @param subcategory error sub-category
	 * @param name error name
	 */ 
	public ErrorId(String category, String subcategory, String name) {
		m_context = null;
		setCategory(category);
		setSubCategory(subcategory);
		setName(name);	
	}
	
	/**
	 * Constructor
	 *  @param context error context
	 * @param category error category
	 * @param subcategory error sub-category
	 * @param name error name
	 */ 
	public ErrorId(String context, String category, 
		String subcategory, String name) {
		if (context == null || context.length() == 0) {
			throw new IllegalArgumentException(
				"context must not be null or empty");
		}
		m_context = context;
		setCategory(category);
		setSubCategory(subcategory);
		setName(name);	
	}
	
	/**
	 * Set the error category
	 * @param category error category
	 * @throws NullPointerException (if the category is null or empty)
	 */ 
	public void setCategory(String category) {
		if (category == null) {
			throw new NullPointerException
			("Error category must be supplied");
		}
 		if (category.length() == 0) {
			throw new NullPointerException
			("Error category must be a non-empty String");
		}
		m_category = category;
	}
	
	/**
	 * Set the error sub-category
	 * @param subcategory error sub-category
	 * @throws NullPointerException (if the sub-category is null or empty)
	 */ 
	public void setSubCategory(String subcategory) {
		if (subcategory == null) {
			throw new NullPointerException
			("Error sub-category must be supplied");
		}
 		if (subcategory.length() == 0) {
			throw new NullPointerException
			("Error sub-category must be a non-empty String");
		}
		m_subcategory = subcategory;
	}
	
	/**
	 * Set the error name
	 * @param name error name
	 * @throws NullPointerException (if the name is null or empty)
	 */ 
	public void setName(String name) {
		if (name == null) {
			throw new NullPointerException
			("Error name must be supplied");
		}
 		if (name.length() == 0) {
			throw new NullPointerException
			("Error name must be a non-empty String");
		}
		m_name = name;
	}
	
	/**
	 * Get the error context
	 * @return error context or null
	 */ 
	public String getContext() {
		return m_context;
	}
	
	/**
	 * Get the error category
	 * @return error category
	 */ 
	public String getCategory() {
		return m_category;
	}
	
	/**
	 * Get the error sub-category
	 * @return error sub-category
	 */ 
	public String getSubCategory() {
		return m_subcategory;
	}
	
	/**
	 * Get the error name
	 * @return error name
	 */ 
	public String getName() {
		return m_name;
	}
	
	/**
	 * Check whether this error id equals the other error id.
	 * The ids are equal if all their respective fields are equal.
	 * @param id another error id
	 * @return <b>true</b> if the ids are equal, <b>false</b> otherwise
	 */ 
	public boolean equals(ErrorId id) {

		String context = id.getContext();
		String category = id.getCategory();
		String subcategory = id.getSubCategory();
		String name = id.getName();

		if (m_context != context) {
			if ((m_context == null) && (context != null)) {
				return false;
			}	
			if (m_context != null &&
				!m_context.equals(context)) {
				return false;
			}
		}		
		if (m_category != category) {
			if ((m_category == null) || (category == null)) {
				return false;
			}	
			if (!m_category.equals(category)) {
				return false;
			}
		}
		if (m_subcategory != subcategory) {
			if ((m_subcategory == null) || (subcategory == null)) {
				return false;
			}	
			if (!m_subcategory.equals(subcategory)) {
				return false;
			}
		}
		if (m_name != name) {
			if ((m_name == null) || (name == null)) {
				return false;
			}
			if (!m_name.equals(name)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Overrides Object.equals()
	 * @param object another object
	 * @return <b>true</b> if the ids are equal, <b>false</b> otherwise
	 */
	@Override
	public boolean equals(Object object) {
		if (object instanceof ErrorId) {
			return equals((ErrorId)object);
		}
		return false;
	}
	
	/**
	 * Overrides Object.hashCode()
	 * @return hash code
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		if (m_context != null) {
			hash ^= m_context.hashCode();
		}
		/*
		 * We assume that category, subcategory, and name
		 * are non-null.
		 * If they are null, then the class invariant is violated and 
		 * the NullPointerException will be rightfully thrown.
		 */
		hash ^= m_category.hashCode();
		hash ^= m_subcategory.hashCode();
		hash ^= m_name.hashCode();
		
		return hash;
	}

	/**
	 * Get String representation
	 * @return String of the format category.subcategory.name or
	 * context.category.subcategory.name
	 */
	@Override
	public String toString() {
		return getFullName();
	}
	
	/**
	 * Get the full name
	 * @return String of the format category.subcategory.name or
	 * context.category.subcategory.name
	 */
	public String getFullName() {
		StringBuilder rb = new StringBuilder();
		if (m_context != null) {
			rb.append(m_context).append(SEPARATOR);
		}
		rb.append(m_category).append(SEPARATOR);
		rb.append(m_subcategory).append(SEPARATOR);
		rb.append(m_name);
		return rb.toString();
	}

	private static final long serialVersionUID = -6541136898302759215L;
}
