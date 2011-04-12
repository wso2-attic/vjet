/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.tests.data;

import java.util.Date;

public class Person {

	public String m_name = "Foo", m_lname;
	protected Date m_dob;
	public Type m_subType = new Type(Type.HUMAN);
	public static Type s_type = new Type(Type.HUMAN);
	
	public Person(){};
	
	public Person(final String name){
		setName(name);
	}

	public final String getName() {
		return m_name;
	}

	public final void setName(String name) {
		m_name = name;
	}	
	
	static public enum Status {
		SINGLE,
		MARRIED,
		DIVORCED
	}
	
	static public class Type {
		public static final String HUMAN = "human";
		public String m_type;
		
		public Type(String type){
			m_type = type;
		}
	}
}
