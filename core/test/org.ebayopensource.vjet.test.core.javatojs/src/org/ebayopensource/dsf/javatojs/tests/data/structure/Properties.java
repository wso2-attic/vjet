/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.tests.data.structure;

public class Properties {
	
	// =============== Static ===============
	public static String s_Sa;
	protected static String s_Sb = "static";
	static String s_Sc = null;
	
	public static Boolean s_Ba;
	public static boolean s_bb = true;
	
	public static Integer s_Ia;
	public static Integer s_Ib = 3;
	
	public static int s_ia = 5, s_ib = s_ia, S_ic;
	public static float s_fa = s_ia = s_ib = 7; 
	
	// init expr
	public static long s_la = 3 + 9; 
	public static long s_lb = s_ia + 9; // TODO
	
	// =============== Instance ===============
	// - Supported
	public String m_Sa;
	public String m_Sb = "instance";
	
	public Boolean m_Ba;
	public boolean m_bb = true;
	
	public Integer m_Ia;
	public Integer m_Ib = 3;
	
	public int m_ia = 5, m_ib = m_ia = m_Ia + m_Ib, m_ic;
	public float m_fa = m_ib = 7; 
	
	// - Instance field init --> constructs
	public long m_la = m_ia + 9; 
	
	// =============== Others ===============
	public Long m_L = 123L;	// TODO: more other formats ...
	public Boolean m_B = Boolean.TRUE;	// TODO: type specific value/constants, ...
}
