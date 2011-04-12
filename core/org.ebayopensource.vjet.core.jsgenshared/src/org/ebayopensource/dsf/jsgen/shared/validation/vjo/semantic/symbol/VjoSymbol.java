/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/**
 * 
 */
package org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.symbol;

import org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.VjoPackage;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstType;

public class VjoSymbol implements Comparable<VjoSymbol>, IVjoSymbol{
	/**
	 * vjo symbol's name
	 */
	private String m_name;
	/**
	 * vjo symbol's type, @see EVjoSymbolType
	 * the name & type together identifies the symbol
	 */
	private EVjoSymbolType m_symbolType = EVjoSymbolType.UNKNOWN;
	
	private boolean m_staticReference;
	private IJstType m_declareType;
	private IJstType m_assignedType;
	private IJstNode m_declareNode;
	private boolean m_hasAssigned;
	
	@Deprecated
	private boolean m_readOnly;
	@Deprecated
	private VjoPackage m_package;
	
	private boolean m_visible = true;
	
	/* (non-Javadoc)
	 * @see org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.IVjoSymbol#getName()
	 */
	public String getName() {
		return m_name;
	}
	
	public IVjoSymbol setName(String name) {
		m_name = name;
		return this;
	}
	
	public boolean isStaticReference(){
		return m_staticReference;
	}
	
	public IVjoSymbol setStaticReference(boolean staticReference){
		m_staticReference = staticReference;
		return this;
	}
	
	/* (non-Javadoc)
	 * @see org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.IVjoSymbol#getDeclareType()
	 */
	public IJstType getDeclareType() {
		return m_declareType;
	}
	
	public IVjoSymbol setDeclareType(IJstType type) {
		m_declareType = type;
		return this;
	}
	
	/* (non-Javadoc)
	 * @see org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.IVjoSymbol#getAssignedType()
	 */
	public IJstType getAssignedType(){
		m_assignedType = m_assignedType != null? m_assignedType: m_declareType;
		return m_assignedType;
	}
	
	public boolean isAssigned(){
		return m_hasAssigned;
	}
	
	@Deprecated
	public boolean isReadOnly(){
		return m_readOnly;
	}
	
	@Deprecated
	public IVjoSymbol setReadOnly(boolean ro){
		m_readOnly = ro;
		return this;
	}
	
	public IVjoSymbol setAssignedType(IJstType type){
		m_assignedType = type;
		m_hasAssigned = true;
		return this;
	}
	
	/* (non-Javadoc)
	 * @see org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.IVjoSymbol#getDeclareNode()
	 */
	public IJstNode getDeclareNode(){
		return m_declareNode;
	}
	
	public IVjoSymbol setDeclareNode(IJstNode node){
		m_declareNode = node;
		return this;
	}
	
	/* (non-Javadoc)
	 * @see org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.IVjoSymbol#getPackage()
	 */
	@Deprecated
	public VjoPackage getPackage(){
		return m_package;
	}
	
	@Deprecated
	public IVjoSymbol setPackage(VjoPackage pack){
		m_package = pack;
		return this;
	}
	
	/* (non-Javadoc)
	 * @see org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.IVjoSymbol#getSymbolType()
	 */
	public EVjoSymbolType getSymbolType(){
		return m_symbolType;
	}
	
	public IVjoSymbol setSymbolType(EVjoSymbolType type){
		m_symbolType = type;
		return this;
	}
	
	/* (non-Javadoc)
	 * @see org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.IVjoSymbol#isVisible()
	 */
	public boolean isVisible(){
		return m_visible;
	}
	
	public IVjoSymbol setVisible(boolean visible){
		m_visible = visible;
		return this;
	}
	
	@Override
	public boolean equals(Object other){
		if(other == null){
			return false;
		}
		else if(VjoSymbol.class.isAssignableFrom(other.getClass())){
			if(getName() == null){
				return ((IVjoSymbol)other).getName() == null;
			}
			else{
				return getName().equals(((IVjoSymbol)other).getName()) && getSymbolType().equals(((IVjoSymbol)other).getSymbolType());
			}
		}
		return false;
	}
	
	@Override
	public int hashCode(){
		return getName().hashCode() << 7 + getSymbolType().hashCode();
	}

	public int compareTo(VjoSymbol o) {
		if(equals(o)){
			return 0;
		}
		return 1;
	}
	
	public String toString(){
		final StringBuilder sb = new StringBuilder();
		sb.append("{name: ");
		sb.append(getName());
		sb.append(", type: ");
		sb.append(getAssignedType());
		sb.append("}");
		return sb.toString();
	}
}