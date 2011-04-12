/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package vjo.ebay.dsf.javatojs.tests.data.structure;

import org.ebayopensource.dsf.javatojs.tests.data.IHandler;
import org.ebayopensource.dsf.javatojs.tests.data.Person;

public final class Employee extends Person implements IHandler {
	
	private float m_salary;
	private int m_age;
	
	public Employee(){
		this(null);
	}
	private Employee(final String name){
		super(name);
	}
	private Employee(final String name, final int age){
		super(name);
		m_age = age;
	}
	private Employee(final String name, final float salary){
		this(name);
		m_salary = salary;
	}
	private Employee(final String name, final int age, final float salary){
		this(name, age);
		m_salary = salary;
	}

	protected void setSalary(final float salary){
		m_salary = salary;
	}
	
	float getSalary(){
		return m_salary;
	}
	
	public boolean handle(boolean debug){
		return false;
	}
	
	public String toString(){
		return getName();
	}
}
