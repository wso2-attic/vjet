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
//import org.eclipse.dltk.mod.core.IMember;
//import org.eclipse.dltk.mod.core.IModelElement;
//import org.eclipse.dltk.mod.core.ISourceRange;
//import org.eclipse.dltk.mod.core.IType;
//import org.eclipse.dltk.mod.core.ModelException;
//import org.eclipse.dltk.mod.internal.core.SourceRange;
//
//import org.ebayopensource.dsf.jst.IJstNode;
//import org.ebayopensource.dsf.jst.IJstType;
//import org.ebayopensource.dsf.jst.JstSource;
//import org.ebayopensource.dsf.jst.declaration.JstMethod;
//import org.ebayopensource.dsf.jst.declaration.JstModifiers;
//import org.ebayopensource.dsf.jst.declaration.JstProperty;
//
///**
// * 
// * 
// */
//abstract class VjoMember extends VjoSourceRefElement implements IMember {
//
//	VjoMember(VjoModelElement parent, IJstNode jstNode) {
//		super(parent, jstNode);
//	}
//
//	VjoMember(VjoModelElement parent, String name) {
//		super(parent, name);
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see org.eclipse.dltk.mod.core.IMember#getDeclaringType()
//	 */
//	public IType getDeclaringType() {
//		IModelElement parentElement = getParent();
//		if (parentElement.getElementType() == TYPE) {
//			return (IType) parentElement;
//		}
//		return null;
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see org.eclipse.dltk.mod.core.IMember#getFlags()
//	 */
//	public int getFlags() throws ModelException {
//		JstModifiers jstModifiers = null;
//		if (m_jstNode instanceof IJstType) {
//			jstModifiers = ((IJstType) m_jstNode).getModifiers();
//		} else if (m_jstNode instanceof JstProperty) {
//			jstModifiers = ((JstProperty) m_jstNode).getModifiers();
//		} else if (m_jstNode instanceof JstMethod) {
//			jstModifiers = ((JstMethod) m_jstNode).getModifiers();
//		}
//		// TODO convert modifiers to flags
//		int flags = 0;
//		return flags;
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see org.eclipse.dltk.mod.core.IMember#getNameRange()
//	 */
//	public ISourceRange getNameRange() throws ModelException {
//		JstSource nameSource = null;
//		if (m_jstNode instanceof IJstType) {
//			nameSource = ((IJstType) m_jstNode).getSource();
//		} else if (m_jstNode instanceof JstProperty) {
//			nameSource = ((JstProperty) m_jstNode).getName().getSource();
//		} else if (m_jstNode instanceof JstMethod) {
//			nameSource = ((JstMethod) m_jstNode).getName().getSource();
//		}
//		if (nameSource != null) {
//			return new SourceRange(nameSource.getStartOffSet(), nameSource
//					.getLength());
//		}
//		return null;
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see org.eclipse.dltk.mod.core.IMember#getType(java.lang.String, int)
//	 */
//	public IType getType(String name, int occurrenceCount) {
//		if (false) {// isBinary()) {
//			throw new IllegalArgumentException("Not a source member " /*
//																		 * +
//																		 * toStringWithAncestors()
//																		 */); //$NON-NLS-1$
//		} else {
//			// TODO do we need TS to search for existing type?
//			VjoType type = new VjoType(this, name);
//			type.occurrenceCount = occurrenceCount;
//			return type;
//		}
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see org.eclipse.dltk.mod.core.IParent#getChildren()
//	 */
//	public IModelElement[] getChildren() throws ModelException {
//		return NO_ELEMENTS;
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see org.eclipse.dltk.mod.core.IParent#hasChildren()
//	 */
//	public boolean hasChildren() throws ModelException {
//		return false;
//	}
//}
