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
//import org.eclipse.core.runtime.IProgressMonitor;
//import org.eclipse.dltk.mod.core.IModelElement;
//import org.eclipse.dltk.mod.core.IModelStatusConstants;
//import org.eclipse.dltk.mod.core.ISourceManipulation;
//import org.eclipse.dltk.mod.core.ISourceModule;
//import org.eclipse.dltk.mod.core.ISourceRange;
//import org.eclipse.dltk.mod.core.ModelException;
//import org.eclipse.dltk.mod.internal.core.IJSInitializer;
//import org.eclipse.dltk.mod.internal.core.ModelStatus;
//import org.eclipse.dltk.mod.internal.core.util.Util;
//
//import org.ebayopensource.vjet.eclipse.core.IJSType;
//
///**
// * 
// * 
// */
//public class VjoInitializer extends VjoMember implements IJSInitializer {
//
//	public static final char JEM_INITIALIZER = '|';
//
//	public VjoInitializer(VjoModelElement parent, int count) {
//		super(parent, (String) null);
//		// 0 is not valid: this first occurrence is occurrence 1.
//		if (count <= 0)
//			throw new IllegalArgumentException();
//		this.occurrenceCount = count;
//	}
//
//	@Override
//	public boolean equals(Object o) {
//		if (!(o instanceof VjoInitializer))
//			return false;
//		return super.equals(o);
//	}
//
//	/*
//	 * \ (non-Javadoc)
//	 * 
//	 * @see org.eclipse.dltk.mod.core.IModelElement#getElementType()
//	 */
//	public int getElementType() {
//		return ELEMENT_TYPE;
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see org.ebayopensource.vjet.eclipse.internal.core.VjoModelElement#getHandleMemento(java.lang.StringBuffer)
//	 */
//	@Override
//	protected void getHandleMemento(StringBuffer buff) {
//		((VjoModelElement) getParent()).getHandleMemento(buff);
//		buff.append(getHandleMementoDelimiter());
//		buff.append(this.occurrenceCount);
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see org.ebayopensource.vjet.eclipse.internal.core.VjoModelElement#getHandleMementoDelimiter()
//	 */
//	@Override
//	protected char getHandleMementoDelimiter() {
//		return JEM_INITIALIZER;
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see org.ebayopensource.vjet.eclipse.internal.core.VjoMember#getNameRange()
//	 */
//	@Override
//	public ISourceRange getNameRange() {
//		return null;
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see org.ebayopensource.vjet.eclipse.internal.core.VjoModelElement#getPrimaryElement(boolean)
//	 */
//	@Override
//	public IModelElement getPrimaryElement(boolean checkOwner) {
//		if (checkOwner) {
//			ISourceModule cu = (ISourceModule) getAncestor(SOURCE_MODULE);
//			if (cu == null || cu.isPrimary())
//				return this;
//		}
//		IModelElement primaryParent = this.m_parent.getPrimaryElement(false);
//		return ((IJSType) primaryParent).getInitializer(this.occurrenceCount);
//	}
//
//	@Override
//	public int hashCode() {
//		return Util.combineHashCodes(this.m_parent.hashCode(),
//				this.occurrenceCount);
//	}
//
//	/**
//	 */
//	public String readableName() {
//		return ((VjoModelElement) getDeclaringType()).getElementName();
//	}
//
//	/**
//	 * @see ISourceManipulation
//	 */
//	public void rename(String newName, boolean force, IProgressMonitor monitor)
//			throws ModelException {
//		throw new ModelException(new ModelStatus(
//				IModelStatusConstants.INVALID_ELEMENT_TYPES, this));
//	}
//}
