/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
//package org.ebayopensource.vjet.eclipse.internal.core;
//
//import java.util.List;
//
//import org.eclipse.dltk.mod.core.ModelException;
//
//import org.ebayopensource.dsf.jst.IJstMethod;
//import org.ebayopensource.dsf.jst.declaration.JstArg;
//import org.ebayopensource.dsf.jst.declaration.JstConstructor;
//import org.ebayopensource.dsf.jst.declaration.JstMethod;
//import org.ebayopensource.vjet.eclipse.core.IJSMethod;
//
//public class VjoMethod extends VjoMember implements IJSMethod {
//
//	public VjoMethod(VjoType parentType, IJstMethod jstMethod) {
//		super(parentType, jstMethod);
//	}
//
//	public VjoMethod(VjoType parentType, String name) {
//		super(parentType, name);
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see org.eclipse.dltk.mod.core.IMethod#getFullyQualifiedName(java.lang.String)
//	 */
//	public String getFullyQualifiedName(String enclosingTypeSeparator) {
//		try {
//			return getTypeQualifiedName(enclosingTypeSeparator, false/*
//																		 * don't
//																		 * show
//																		 * parameters
//																		 */);
//		} catch (ModelException e) {
//			// exception thrown only when showing parameters
//			return null;
//		}
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see org.eclipse.dltk.mod.core.IMethod#getFullyQualifiedName()
//	 */
//	public String getFullyQualifiedName() {
//		return jstMethod().getName().getName();
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see org.eclipse.dltk.mod.core.IMethod#getParameterInitializers()
//	 */
//	public String[] getParameterInitializers() throws ModelException {
//
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see org.eclipse.dltk.mod.core.IMethod#getParameters()
//	 */
//	public String[] getParameters() throws ModelException {
//		List<JstArg> args = jstMethod().getArgs();
//
//		if (args.size() > 0) {
//			String[] names = new String[args.size()];
//			int idx = 0;
//			for (JstArg arg : args) {
//				names[idx] = arg.getName();
//				idx++;
//			}
//			return names;
//		}
//		return null;
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see org.eclipse.dltk.mod.core.IMethod#getTypeQualifiedName(java.lang.String,
//	 *      boolean)
//	 */
//	public String getTypeQualifiedName(String enclosingTypeSeparator,
//			boolean showParameters) throws ModelException {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see org.eclipse.dltk.mod.core.IMethod#isConstructor()
//	 */
//	public boolean isConstructor() throws ModelException {
//		return m_jstNode instanceof JstConstructor;
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see org.eclipse.dltk.mod.core.IModelElement#getElementType()
//	 */
//	public int getElementType() {
//		return METHOD;
//	}
//
//	private JstMethod jstMethod() {
//		return (JstMethod) m_jstNode;
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see org.ebayopensource.vjet.eclipse.internal.core.VjoModelElement#getHandleMementoDelimiter()
//	 */
//	protected char getHandleMementoDelimiter() {
//		return JEM_METHOD;
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see org.ebayopensource.vjet.eclipse.core.IJSMethod#getNumberOfParameters()
//	 */
//	public int getNumberOfParameters() {
//		return jstMethod().getArgs().size();
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see org.ebayopensource.vjet.eclipse.core.IJSMethod#getParameterTypes()
//	 */
//	public String[] getParameterTypes() {
//		List<JstArg> args = jstMethod().getArgs();
//		if (args.size() > 0) {
//			String[] types = new String[args.size()];
//			int idx = 0;
//			for (JstArg arg : args) {
//				types[idx] = arg.getType().getName();
//				idx++;
//			}
//			return types;
//		}
//		return null;
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see org.ebayopensource.vjet.eclipse.core.IJSMethod#getReturnType()
//	 */
//	public String getReturnType() throws ModelException {
//		return jstMethod().getRtnType().getName();
//	}
//}
