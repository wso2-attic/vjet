/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.tests.data.structure;

public class Overloadings {

	private int m_size;
	private int m_multipler;
	
	protected Overloadings(){
		this(0);
	}
	
	public Overloadings(int size, int multipler){
		m_size = 0;
		m_multipler = multipler;
	}
	
	public Overloadings(int size, boolean triple){
		m_size = 0;
		if (triple){
			m_multipler = 3;
		}
	}
	
	public Overloadings(int size){
		m_size = 0;
	}
	
	public Overloadings(int[] size){
		m_size = 0;
	}
	
	public Overloadings(String[] size){
		m_size = 0;
	}
	
	protected int getTotal(int multiplier){
		return m_size * multiplier;
	}
	
	private int getTotal(boolean triple){
		return m_size * 3;
	}
	
	final public int testFinal(){
		return 0;
	}
	
	final public <T> int testFinal(T amount){
		return 0;
	}
	
	static private int getTotal(){
		return 0;
	}
	
	static private <T> int getTotal(T amount){
		return 0;
	}
	
	static private int getTotal(String amount){
		return 0;
	}
	
	final static public int testStaticFinal(){
		return 0;
	}
	
	final static public <T> int testStaticFinal(T amount){
		return 0;
	}
}
