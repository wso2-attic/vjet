/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.declaration;

import java.util.List;

import org.ebayopensource.dsf.jst.BaseJstNode;
import org.ebayopensource.dsf.jst.IJstAnnotation;
import org.ebayopensource.dsf.jst.IJstDoc;
import org.ebayopensource.dsf.jst.IJstGlobalFunc;
import org.ebayopensource.dsf.jst.IJstGlobalProp;
import org.ebayopensource.dsf.jst.IJstGlobalVar;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.IJstTypeReference;
import org.ebayopensource.dsf.jst.JstSource;
import org.ebayopensource.dsf.jst.token.IExpr;
import org.ebayopensource.dsf.jst.token.ISimpleTerm;
import org.ebayopensource.dsf.jst.traversal.IJstNodeVisitor;
import org.ebayopensource.dsf.jst.util.DataTypeHelper;

public class JstGlobalVar extends BaseJstNode implements IJstGlobalVar {

	private static final long serialVersionUID = 1L;
	
	private JstTypeReference m_type;
	private JstName m_name;
	private ISimpleTerm m_value;
	private IExpr m_initializer;
	private IJstDoc m_doc;
	private IJstGlobalFunc m_globalFunction;
	private IJstGlobalProp m_globalProp;
	private boolean m_memberPromotion;
	private String m_scopeForGlobal;

	public void setMemberPromotion(boolean memberPromotion) {
		m_memberPromotion = memberPromotion;
	}	
	
	/**
	 * Constructor
	 * @param type JstType type of the property
	 * @param name String
	 */
	public JstGlobalVar(IJstGlobalFunc globalFunc) {
		m_name = globalFunc.getName();
		m_doc = globalFunc.getDoc();
		m_type = new JstTypeReference(globalFunc.getRtnType());
		m_globalFunction = globalFunc;
		addChild(globalFunc);
	}
	
	/**
	 * Constructor
	 * @param type JstType type of the property
	 * @param name String
	 */
	public JstGlobalVar(IJstGlobalProp globalProp) {
		m_name = globalProp.getName();
		m_doc = globalProp.getDoc();
		m_type = new JstTypeReference(globalProp.getType());
		m_globalProp = globalProp;
		addChild(globalProp);
	}
	
	@Override
	public void accept(IJstNodeVisitor visitor){
		visitor.visit(this);
	}
	
	
	@Override
	public IJstDoc getDoc() {
		return m_doc;
	}

	@Override
	public IExpr getInitializer() {
		return m_initializer;
	}

	@Override
	public JstName getName() {
		return m_name;
	}

	@Override
	public IJstType getType() {
		return (m_type == null)? null: m_type.getReferencedType();
	}

	@Override
	public IJstTypeReference getTypeRef() {
		return m_type;
	}

	/**
	 * Set name for the global
	 * @param name String
	 */
	public void setName(String name){
		setName(new JstName(name));
	}

	/**
	 * Set name for the global
	 * @param name String
	 */
	public void setName(JstName name){
		assert name != null : "name cannot be null";
		// FIXME: need to replace the child?
		m_name = name;
	}
	
	/**
	 * Set type for the property
	 * @param type IJstType
	 */
	public void setType(IJstType type){
		m_type.setReferencedType(type);
	}

	/**
	 * Set value for the property
	 * @param value ISimpleTerm
	 */
	public void setValue(final ISimpleTerm value) {
		m_value = value;
		addChild(value);
	}
	
	/**
	 * Set the initializer for this global. 
	 * @param initializer IExpr
	 */
	public void setInitializer(IExpr initializer){
		m_initializer = initializer;
	}
		
	@Override
	public ISimpleTerm getValue() {
		return m_value;
	}

	@Override
	public List<IJstAnnotation> getAnnotations() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Answer formatted property name:value
	 * @return String
	 */
	public String toNVText() {
		if (m_value == null) {
			String defaultValue = null;
			IJstType elementType = getType();
			defaultValue = DataTypeHelper.getDefaultValue(elementType.getName());
			return m_name + ":" + defaultValue;
		}
		return m_name + ":" + m_value.toSimpleTermText();
	}
	
	
	/**
	 * Set JstDoc for this property
	 * @param jstDoc IJstDoc
	 */
	public void setDoc(IJstDoc jstDoc) {
		m_doc = jstDoc;
	}

	@Override
	public IJstGlobalFunc getFunction() {
		return m_globalFunction;
	}

	@Override
	public boolean isFunc() {
		return m_globalFunction!=null;
	}

	@Override
	public IJstGlobalProp getProperty() {
		return m_globalProp;
	}
	
	@Override
	public JstSource getSource() {
		if(m_globalProp!=null){
			return m_globalProp.getSource();
		}
		return m_globalFunction.getSource();
	}

	public void setScopeForGlobal(String scopeForGlobal) {
		m_scopeForGlobal = scopeForGlobal;
	}

	public String getScopeForGlobal() {
		return m_scopeForGlobal;
	}	
}
