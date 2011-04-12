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
//import org.eclipse.core.resources.IResource;
//import org.eclipse.core.runtime.IPath;
//import org.eclipse.dltk.mod.core.IModelElement;
//import org.eclipse.dltk.mod.core.IOpenable;
//import org.eclipse.dltk.mod.core.ISourceModule;
//import org.eclipse.dltk.mod.core.ISourceRange;
//import org.eclipse.dltk.mod.core.ISourceReference;
//import org.eclipse.dltk.mod.core.ModelException;
//import org.eclipse.dltk.mod.internal.core.SourceRange;
//
//import org.ebayopensource.dsf.jst.IJstNode;
//import org.ebayopensource.dsf.jst.JstSource;
//
///**
// * 
// * 
// */
//abstract class VjoSourceRefElement extends VjoModelElement implements
//		ISourceReference {
//
//	/*
//	 * A count to uniquely identify this element in the case that a duplicate
//	 * named element exists. For example, if there are two fields in a
//	 * compilation unit with the same name, the occurrence count is used to
//	 * distinguish them. The occurrence count starts at 1 (thus the first
//	 * occurrence is occurrence 1, not occurrence 0).
//	 */
//	public int occurrenceCount = 1;
//
//	/**
//	 * @param parent
//	 * @param jstNode
//	 */
//	public VjoSourceRefElement(VjoModelElement parent, IJstNode jstNode) {
//		super(parent, jstNode);
//	}
//
//	/**
//	 * @param parent
//	 * @param name
//	 */
//	public VjoSourceRefElement(VjoModelElement parent, String name) {
//		super(parent, name);
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see org.eclipse.dltk.mod.core.ISourceReference#getSource()
//	 */
//	public String getSource() throws ModelException {
//		// TODO Auto-generated method stub
//		return "";
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see org.eclipse.dltk.mod.core.ISourceReference#getSourceRange()
//	 */
//	public ISourceRange getSourceRange() throws ModelException {
//		JstSource jstSource = m_jstNode.getSource();
//		if (jstSource != null) {
//			return new SourceRange(jstSource.getStartOffSet(), jstSource
//					.getLength());
//		}
//		return null;
//	}
//
//	/**
//	 * Return the first instance of IOpenable in the hierarchy of this type
//	 * (going up the hierarchy from this type);
//	 */
//	public IOpenable getOpenableParent() {
//		IModelElement current = getParent();
//		while (current != null) {
//			if (current instanceof IOpenable) {
//				return (IOpenable) current;
//			}
//			current = current.getParent();
//		}
//		return null;
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see org.eclipse.dltk.mod.core.IModelElement#getResource()
//	 */
//	public IResource getResource() {
//		return getParent().getResource();
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see org.eclipse.dltk.mod.core.IModelElement#getPath()
//	 */
//	public IPath getPath() {
//		return getParent().getPath();
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see org.eclipse.dltk.mod.core.IModelElement#getUnderlyingResource()
//	 */
//	public IResource getUnderlyingResource() throws ModelException {
//		if (!exists())
//			throw newNotPresentException();
//		return getParent().getUnderlyingResource();
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see org.eclipse.dltk.mod.core.IModelElement#isStructureKnown()
//	 */
//	public boolean isStructureKnown() throws ModelException {
//		// structure is always known inside an openable
//		return true;
//	}
//
//	/*
//	 * (non-Javadoc) Elements within compilation units and class files have no
//	 * corresponding resource.
//	 * 
//	 * @see org.eclipse.dltk.mod.core.IModelElement#getCorrespondingResource()
//	 */
//	public IResource getCorrespondingResource() throws ModelException {
//		if (!exists())
//			throw newNotPresentException();
//		return null;
//	}
//
//	public ISourceModule getSourceModule() {
//		return ((VjoModelElement) getParent()).getSourceModule();
//	}
//}
