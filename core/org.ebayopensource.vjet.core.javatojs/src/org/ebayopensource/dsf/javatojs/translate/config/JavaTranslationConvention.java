/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.translate.config;

public class JavaTranslationConvention {
	private static final String TEMP_INDEX = "_$i";
	private static final String TEMP_ARRAY = "_$arr";
	private static final String ARRAY_LENGTH = "length";	
	private static final String TEMP_ITERATOR = "_$itr";
	private static final String ITERATOR_METHOD = "iterator";
	private static final String ITERATOR_HAS_NEXT = "hasNext";
	private static final String ITERATOR_NEXT = "next";
	
	private String m_tempIndex = TEMP_INDEX;
	private String m_tempArray = TEMP_ARRAY;	
	private String m_tempIterator = TEMP_ITERATOR;
	
	public String getTempIndex() {
		return m_tempIndex;
	}
	
	public void setTempIndex(String tempIndex) {
		m_tempIndex = tempIndex;
	}
	
	public String getTempArray() {
		return m_tempArray;
	}
	
	public void setTempArray(String tempArray) {
		m_tempArray = tempArray;
	}
	
	public String getTempIterator() {
		return m_tempIterator;
	}
	
	public void setTempIterator(String tempIterator) {
		m_tempIterator = tempIterator;
	}
	
	public String getArrayLength() {
		return ARRAY_LENGTH;
	}
	
	public String getIteratorMethod() {
		return ITERATOR_METHOD;
	}
	
	public String getIteratorHasNext() {
		return ITERATOR_HAS_NEXT;
	}
	
	public String getIteratorNext() {
		return ITERATOR_NEXT;
	}
}
