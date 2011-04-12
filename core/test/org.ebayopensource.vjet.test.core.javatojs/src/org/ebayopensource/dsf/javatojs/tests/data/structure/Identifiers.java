/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.tests.data.structure;

import java.util.Date;

import org.ebayopensource.dsf.javatojs.tests.data.Person;

public class Identifiers extends Person {
	
	private String m_name;
	private static int s_count;
	private int age;

	public void qualifiedNames(){
		Status enumConst = Person.Status.MARRIED;
		String innerConst = Person.Type.HUMAN;
		String constConst = Person.s_type.HUMAN;
		
		Person person = new Person();
		String field = person.m_name;
		String fieldConst = person.m_subType.HUMAN;
	}
	
	public void fieldAccess(String name){
		String localName= name;
		String memberName = m_name;
		String thisName = this.m_name;
		String superName = super.m_name;
		Date superDate = super.m_dob;
		int total = s_count;
		int thisTotal = this.s_count;
		Type type = super.s_type;
		
		String field = new Person().m_name;
	}
	
	public void setAge(int age) {
		this.age = age;
	}
}
