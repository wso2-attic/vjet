/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.eclipse.dltk.mod.internal.core;

import org.eclipse.core.runtime.Assert;
import org.eclipse.dltk.mod.compiler.CharOperation;
import org.eclipse.dltk.mod.core.IMethod;
import org.eclipse.dltk.mod.core.IModelElement;
import org.eclipse.dltk.mod.core.ModelException;

import org.ebayopensource.vjet.eclipse.core.IJSMethod;
import org.ebayopensource.vjet.eclipse.core.IJSType;

public class JSSourceMethod extends SourceMethod implements IJSMethod {

	/**
	 * The parameter type signatures of the method - stored locally to perform
	 * equality test. <code>null</code> indicates no parameters.
	 */
	protected String[] m_parameterTypes;

	public JSSourceMethod(ModelElement parent, String name) {
		super(parent, name);
	}

	public JSSourceMethod(ModelElement parent, String name,
			String[] parameterTypes) {
		super(parent, name);
		Assert.isTrue(name.indexOf('.') == -1);
		if (parameterTypes == null) {
			this.m_parameterTypes = CharOperation.NO_STRINGS;
		} else {
			this.m_parameterTypes = parameterTypes;
		}
	}

//	@Override
//	public boolean equals(Object o) {
//		if (!(o instanceof SourceMethod))
//			return false;
//		//TODO we need to compare parameter types, not only number of params
//		if (this.m_parameterTypes == null || ((JSSourceMethod) o).m_parameterTypes == null) {
//			return false;
//		}
//		return super.equals(o)
//				&& (this.m_parameterTypes.length == ((JSSourceMethod) o).m_parameterTypes.length);
////				&& Util.equalArraysOrNull(this.m_parameterTypes,
////						((JSSourceMethod) o).m_parameterTypes);
//	}

	/**
	 * @see ModelElement#getHandleMemento(StringBuffer)
	 */
	@Override
	protected void getHandleMemento(StringBuffer buff) {
		((ModelElement) getParent()).getHandleMemento(buff);
		char delimiter = getHandleMementoDelimiter();
		buff.append(delimiter);
		escapeMementoName(buff, getElementName());
		for (int i = 0; i < this.m_parameterTypes.length; i++) {
			buff.append(delimiter);
			escapeMementoName(buff, this.m_parameterTypes[i]);
		}
		if (this.occurrenceCount > 1) {
			buff.append(JEM_COUNT);
			buff.append(this.occurrenceCount);
		}
	}

	/**
	 * @see IJSMethod
	 */
	public int getNumberOfParameters() {
		return this.m_parameterTypes == null ? 0 : this.m_parameterTypes.length;
	}

	/**
	 * @see IJSMethod
	 */
	public String[] getParameterTypes() {
		return this.m_parameterTypes;
	}

	/*
	 * @see JavaElement#getPrimaryElement(boolean)
	 */
	@Override
	public IModelElement getPrimaryElement(boolean checkOwner) {
		if (checkOwner) {
			SourceModule cu = (SourceModule) getAncestor(SOURCE_MODULE);
			if (cu.isPrimary())
				return this;
		}
		IModelElement primaryParent = this.parent.getPrimaryElement(false);
		return ((IJSType) primaryParent).getMethod(this.name,
				this.m_parameterTypes);
	}

	/**
	 * @see IJSMethod
	 */
	public String getReturnType() throws ModelException {
		SourceMethodElementInfo info = (SourceMethodElementInfo) getElementInfo();
		return new String(info.getReturnTypeName());
		// TODO return Signature.createTypeSignature(info.getReturnTypeName(),
		// false);
	}

	/**
	 * @see IMethod
	 */
	@Override
	public boolean isConstructor() throws ModelException {
		//if (!this.getElementName().equals(this.parent.getElementName())) {
		// use "constructs" instead of type name
		if (!this.getElementName().equals("constructs")) {
			// faster than reaching the info
			return false;
		}
		JSSourceMethodElementInfo info = (JSSourceMethodElementInfo) getElementInfo();
		return info.isConstructor();
	}
	
	public SourceField getLocalDeclaration(String name, String type) {
		return new VjoLocalVariable(this, name, 0, 0, 0, 0, type);
	}
}
