/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsgen.shared.generate;

public class CodeStyle {
	public static final CodeStyle PRETTY = new CodeStyle("Pretty", 0);
	public static final CodeStyle COMPACT = new CodeStyle("Compact", 1);
	public static final CodeStyle OBFUSCATED = new CodeStyle("Obfuscated", 2);
	
	private String m_name;
	private int m_value;
	private CodeStyle (final String name, int value){
		m_name = name;
		m_value = value;
	}
	
	@Override
	public String toString(){
		return m_name + ":" + m_value;
	}
}
