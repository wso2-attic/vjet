/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.core.search;

import org.eclipse.dltk.mod.core.IModelElement;
import org.eclipse.dltk.mod.core.search.IDLTKSearchScope;
import org.eclipse.dltk.mod.core.search.SearchPattern;

/**
 * Class contains search query parameters : scope, search pattern, model element
 * and others.
 * 
 * 
 * 
 * 
 */
public class SearchQueryParameters {

	SearchPattern pattern;

	String stringPattern;

	boolean isElementSpecification = false;

	Object element;

	int limitTo;

	IDLTKSearchScope scope;

	/**
	 * Create instance of this class with search pattern object and string
	 * pattern.
	 * 
	 * @param pattern
	 *            search patterns
	 * @param stringPattern
	 *            string pattern.
	 */
	public SearchQueryParameters(SearchPattern pattern, String stringPattern) {
		this.pattern = pattern;
		this.stringPattern = stringPattern;
	}

	/**
	 * Create instance of this class
	 */
	public SearchQueryParameters() {
		super();
	}

	/**
	 * Returns the value of the search patterns field
	 * 
	 * @return the new value for the search pattern field.
	 */
	public SearchPattern getPattern() {
		return pattern;
	}

	/**
	 * Sets the new value for the search pattern field.
	 * 
	 * @param pattern
	 *            the new value for the search pattern field.
	 */
	public void setPattern(SearchPattern pattern) {
		this.pattern = pattern;
	}

	/**
	 * Returns the value of the string patterns field
	 * 
	 * @return the new value for the string pattern field.
	 */
	public String getStringPattern() {
		return stringPattern;
	}

	/**
	 * Sets the new value for the string pattern field.
	 * 
	 * @param stringPattern
	 *            the new value for the string pattern field.
	 */
	public void setStringPattern(String stringPattern) {
		this.stringPattern = stringPattern;
	}

	/**
	 * Returns true if this query contains {@link IModelElement} object
	 * 
	 * @return true if this query contains {@link IModelElement} object
	 */
	public boolean isElementSpecification() {
		return isElementSpecification;
	}

	/**
	 * Sets true if this query contains {@link IModelElement} object.
	 * 
	 * @param isElementSpecification true if this query contains {@link IModelElement} object.
	 */
	public void setElementSpecification(boolean isElementSpecification) {
		this.isElementSpecification = isElementSpecification;
	}

	/**
	 * Returns the value of the scope field
	 * 
	 * @return the new value for the scope field.
	 */
	public IDLTKSearchScope getScope() {
		return scope;
	}

	/**
	 * Returns the value of the limitTo field
	 * 
	 * @return the new value for the limitTo field.
	 */
	public int getLimitTo() {
		return limitTo;
	}

	/**
	 * Returns the value of the limitTo field
	 * 
	 * @return the new value for the limitTo field.
	 */	
	public Object getElement() {
		return element;
	}

	/**
	 * Sets the new value for the element field.
	 * 
	 * @param element the new value for the element field.
	 */
	public void setElement(Object element) {
		this.element = element;
	}

	/**
	 * Sets the new value for the scope field.
	 * 
	 * @param scope the new value for the scope field.
	 */
	public void setScope(IDLTKSearchScope scope) {
		this.scope = scope;
	}

	/**
	 * Sets the new value for the limitTo field.
	 * 
	 * @param limitTo the new value for the limitTo field.
	 */
	public void setLimitTo(int limitTo) {
		this.limitTo = limitTo;
	}

}