/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dom;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.ebayopensource.dsf.dom.DText;
import org.ebayopensource.dsf.common.enums.BaseEnum;

public final class DOMTypeEnum extends BaseEnum {
	private static final long serialVersionUID = 1L;
	
	private static int s_currentOrdinal=0;
	private static final Map<String, DOMTypeEnum> s_tagNameToEnum 
		= new HashMap<String, DOMTypeEnum>();
	private static final Map<Class, DOMTypeEnum> s_classToEnum 
		= new HashMap<Class, DOMTypeEnum>();

	// The following was code gened with the following script:
	// grep = HtmlType.java|grep -v HTML_DOCUMENT|awk
	//   '{print "\tpublic static final DOMTypeEnum "$2" = new DOMTypeEnum(HtmlType."$2",\""tolower($2)"\", D"substr($2,1,1)tolower(substr($2,2))".class);"}'

	public static final DOMTypeEnum TEXT=new DOMTypeEnum("text",DText.class);
	
	private final Class m_typeClass;
	private final char [] m_nameChars;
	private DOMTypeEnum(String name, final Class typeClass) {
		super(s_currentOrdinal, name);
		s_currentOrdinal++;
		m_typeClass = typeClass;
		if (name != null) {
			s_tagNameToEnum.put(name, this);
		}
		if (typeClass != null) {
			s_classToEnum.put(typeClass, this);
		}
		if (name == null){
			m_nameChars = null;
		} else {
			m_nameChars = name.toCharArray();
		}
	}

	/** internal method, only for use within this library.  Any use
	 * by code above the library will result in a P1 bug being filed.  By
	 * using this method one agrees to the terms.
	 * @return char [] - char array for the name
	 */
	char [] getNameChars(){
		return m_nameChars;
	}
	
	//
	// API
	//
	public Class getTypeClass() {
		return m_typeClass;
	}
	
	public static int size() {
		return s_currentOrdinal;
	}
	
	public static DOMTypeEnum getEnum(final String name) {
		return s_tagNameToEnum.get(name);
	}
	
	public static DOMTypeEnum getEnum(final Class typeClass) {
		return s_classToEnum.get(typeClass);
	}
	
	public static Iterator<DOMTypeEnum> valueIterator(){
		final Iterator<DOMTypeEnum> iterator
			= BaseEnum.getIterator(DOMTypeEnum.class);
		return iterator;
	}

	public static Iterable<DOMTypeEnum> valueIterable(){
		return s_iterable;
	}
	
	//
	// Private
	//
	private static Iterable<DOMTypeEnum> s_iterable = new Iterable<DOMTypeEnum>(){
		// has no state, so it is multi-thread safe.
		public Iterator<DOMTypeEnum> iterator() {
			return valueIterator();
		}		
	};
}

