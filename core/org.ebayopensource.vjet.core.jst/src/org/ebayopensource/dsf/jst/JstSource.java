/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst;

import java.io.Serializable;

import org.ebayopensource.dsf.common.Z;

public class JstSource implements Serializable {

	private static final long serialVersionUID = 4870713462710132633L;
	
	public static final int JAVA = 0;
	public static final int VJO = 1;
	public static final int JS = 2;
	public static final String ARG = "args";
	
	public interface IBinding {
		String getName();
		String toText();
	}
	
	private int m_type;
	private String m_name;
	transient private IBinding m_binding;
	private int m_row;
	private int m_col;
	private int m_length;
//	private int m_offset;
	private int m_endOffset;
	private int m_startOffset;
	
	//
	// Constructor
	//
	public JstSource(final String name){
		this((IBinding)null);
		m_name = name;
	}
	
	public JstSource(final IBinding binding){
		this(JAVA, binding, -1, -1);
	}
	
	public JstSource(int row, int col){
		this(JAVA, null, row, col);
	}
	
	public JstSource(final IBinding binding, int row, int col){
		this(JAVA, binding, row, col);
	}

	public JstSource(int type, final IBinding binding, int row, int col){
		this(type,binding,row,col,0,0,0);
	}

	
	public JstSource(int type, int row, int col, int length, int startOffset, int endOffset){
		this(type,null,row,col,length,startOffset,endOffset);
	}
	
	
	public JstSource(int type, final IBinding binding, int row, int col, int length, int startOffset, int endOffset){
		m_type = type;
		m_binding = binding;
		m_row = row;
		m_col = col;
		m_length = length;
		m_startOffset = startOffset;
		m_endOffset = endOffset;
		if (binding != null){
			m_name = binding.getName();
		}
	}
	
	//
	// API
	//
	/**
	 * Returns the type of source JS, VJO, Java
	 */
	
	public int getType(){
		return m_type;
	}
	
	public String getName(){
		return m_name;
	}
	
	/**
	 * Returns the number of characters of the source code for this element,
	 * relative to the source buffer in which this element is contained.
	 * 
	 * @return the number of characters of the source code for this element,
	 * relative to the source buffer in which this element is contained
	 */
	public int getLength() {
		return m_length;
		
	}
	/**
	 * Returns the 0-based index of the first character of the source code for this element,
	 * relative to the source buffer in which this element is contained.
	 * 
	 * @return the 0-based index of the first character of the source code for this element,
	 * relative to the source buffer in which this element is contained
	 */
	public int getStartOffSet() {
		return m_startOffset;
		
	}
	
	public int getEndOffSet(){
		return m_endOffset;
	}
	
	public IBinding getBinding(){
		return m_binding;
	}
	
	public void setBinding(IBinding binding){
		m_binding = binding;
	}
	
	public int getRow(){
		return m_row;
	}
	
	public int getColumn(){
		return m_col;
	}
	
	@Override
	public String toString(){
		Z z = new Z();
		z.format("m_type", m_type);
		z.format("m_name", m_name);
		if (m_binding != null){
			z.format("m_binding", m_binding.getClass().getName());
		}
		else {
			z.format("m_binding", null);
		}
		z.format("m_row", m_row);
		z.format("m_col", m_col);
		z.format("m_startOffset", m_startOffset);
		z.format("m_endOffset", m_endOffset);
		return z.toString();
	}

}
