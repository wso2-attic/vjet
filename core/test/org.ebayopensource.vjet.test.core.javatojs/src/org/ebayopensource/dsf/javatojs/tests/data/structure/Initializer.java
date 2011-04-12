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

public class Initializer {

	private static Person s_person = new Person();
	private int m_count;
	static private int s_total;
	
	static {
		Date date = new Date();
		s_person.m_name = date.toString();
	}
	
	{
		m_count = s_total + 10;
	}
	
	private static int s_age = 10 + s_person.hashCode();
	
	static {
		Date date = new Date();
		s_person.m_name = date.toString();
		s_age += 10;
	}
}
