/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.tests.data.build.j2j;


public class N {
	
	// =============== Static ===============
	public static String s_Sa;
	protected static String s_Sb = "static";
	static String s_Sc = null;
	
	private static Boolean s_Ba;
	private static boolean s_bb = true;
	
	private static Integer s_Ia;
	private static Integer s_Ib = 3;
	
	private static int s_ia = 5, s_ib = s_ia, S_ic;
	private static float s_fa = s_ia = s_ib = 7; 
	
	// init expr
	private static long s_la = 3 + 9; 
	private static long s_lb = s_ia + 9; // TODO
	
	// =============== Instance ===============
	// - Supported
	private String m_Sa;
	private String m_Sb = "instance";
	
	private Boolean m_Ba;
	private boolean m_bb = true;
	
	private Integer m_Ia;
	private Integer m_Ib = 3;
	
	private int m_ia = 5, m_ib = m_ia = m_Ia + m_Ib, m_ic;
	private float m_fa = m_ib = 7; 
	
	// - Instance field init --> constructs
	private long m_la = m_ia + 9; 
	
	// =============== Others ===============
	private Long m_L = 123L;	// TODO: more other formats ...
	private Boolean m_B = Boolean.TRUE;	// TODO: type specific value/constants, ...
}
