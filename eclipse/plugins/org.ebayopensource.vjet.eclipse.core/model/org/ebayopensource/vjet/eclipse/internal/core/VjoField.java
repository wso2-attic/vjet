/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
///**
// * 
// */
//package org.ebayopensource.vjet.eclipse.internal.core;
//
//import org.eclipse.dltk.mod.core.ModelException;
//
//import org.ebayopensource.dsf.jst.IJstProperty;
//import org.ebayopensource.dsf.jst.declaration.JstProperty;
//import org.ebayopensource.vjet.eclipse.core.IJSField;
//
///**
// * 
// * 
// */
//public class VjoField extends VjoMember implements IJSField {
//
//	public VjoField(VjoType parentType, IJstProperty jstProperty) {
//		super(parentType, jstProperty);
//	}
//
//	public VjoField(VjoType parentType, String name) {
//		super(parentType, name);
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see org.eclipse.dltk.mod.core.IField#getFullyQualifiedName(java.lang.String)
//	 */
//	public String getFullyQualifiedName(String enclosingTypeSeparator) {
//		try {
//			return getTypeQualifiedName(enclosingTypeSeparator, false/*    don't
//																		 * show
//																		 * parameters*/);
//		} catch (ModelException e) {
//			// exception thrown only when showing parameters
//			return null;
//		}
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see org.eclipse.dltk.mod.core.IField#getFullyQualifiedName()
//	 */
//	public String getFullyQualifiedName() {
//		return jstProperty().getName().getName();
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see org.eclipse.dltk.mod.core.IField#getTypeQualifiedName(java.lang.String,
//	 *      boolean)
//	 * @see org.eclipse.dltk.mod.internal.core.NamedMember#getTypeQualifiedName
//	 */
//	public String getTypeQualifiedName(String enclosingTypeSeparator,
//			boolean showParameters) throws ModelException {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	private JstProperty jstProperty() {
//		return (JstProperty) m_jstNode;
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see org.eclipse.dltk.mod.core.IModelElement#getElementType()
//	 */
//	public int getElementType() {
//		return FIELD;
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see org.ebayopensource.vjet.eclipse.internal.core.VjoModelElement#getHandleMementoDelimiter()
//	 */
//	protected char getHandleMementoDelimiter() {
//		return JEM_FIELD;
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see org.ebayopensource.vjet.eclipse.core.IJSField#getConstant()
//	 */
//	public Object getConstant() throws ModelException {
//		return jstProperty().getValue().toString();
//	}
//}
