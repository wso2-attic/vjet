/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.core;

import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstProperty;
import org.ebayopensource.dsf.jst.IJstType;
import org.eclipse.core.runtime.PlatformObject;
import org.eclipse.dltk.mod.core.IModelElement;
import org.eclipse.dltk.mod.core.IModelElementVisitor;
import org.eclipse.dltk.mod.core.IModelStatusConstants;
import org.eclipse.dltk.mod.core.IOpenable;
import org.eclipse.dltk.mod.core.IScriptModel;
import org.eclipse.dltk.mod.core.IScriptProject;
import org.eclipse.dltk.mod.core.ISourceModule;
import org.eclipse.dltk.mod.core.ModelException;
import org.eclipse.dltk.mod.internal.core.ModelStatus;

public abstract class VjoModelElement extends PlatformObject implements
		IModelElement {
	public static final char JEM_ESCAPE = '\\';

	public static final char JEM_SCRIPTPROJECT = '=';

	public static final char JEM_PROJECTFRAGMENT = '/';

	public static final char JEM_SCRIPTFOLDER = '<';

	public static final char JEM_FIELD = '^';

	public static final char JEM_METHOD = '~';

	public static final char JEM_SOURCEMODULE = '{';

	public static final char JEM_TYPE = '[';

	public static final char JEM_IMPORTDECLARATION = '#';

	public static final char JEM_COUNT = '!';

	public static final char JEM_LOCALVARIABLE = '@';

	public static final char JEM_TYPE_PARAMETER = ']';

	public static final char JEM_PACKAGEDECLARATION = '%';

	// Used to replace path / or \\ symbols in external package names and
	// archives.
	public static final char JEM_SKIP_DELIMETER = '>';

	protected static final IModelElement[] NO_ELEMENTS = new IModelElement[0];

	protected IJstNode m_jstNode;

	protected VjoModelElement m_parent;

	protected String m_name;

	public VjoModelElement(VjoModelElement parent, IJstNode jstNode) {
		this.m_parent = parent;
		this.m_jstNode = jstNode;
	}

	VjoModelElement(VjoModelElement parent, String name) {
		this.m_parent = parent;
		this.m_name = name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.dltk.mod.core.IModelElement#exists()
	 */
	public boolean exists() {
		return m_jstNode != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.dltk.mod.core.IModelElement#accept(org.eclipse.dltk.mod.core.IModelElementVisitor)
	 */
	public void accept(IModelElementVisitor visitor) throws ModelException {
		// TODO Auto-generated method stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.dltk.mod.core.IModelElement#getAncestor(int)
	 */
	public IModelElement getAncestor(int ancestorType) {
		IModelElement element = this;
		while (element != null) {
			if (element.getElementType() == ancestorType)
				return element;
			element = element.getParent();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.dltk.mod.core.IModelElement#getElementName()
	 */
	public String getElementName() {
		String name;
		if (m_jstNode instanceof IJstType) {
			name = ((IJstType) m_jstNode).getName();
		} else if (m_jstNode instanceof IJstProperty) {
			name = ((IJstProperty) m_jstNode).getName().getName();
		} else if (m_jstNode instanceof IJstMethod) {
			name = ((IJstMethod) m_jstNode).getName().getName();
		} else {
			name = m_name;
		}
		return name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.dltk.mod.core.IModelElement#getHandleIdentifier()
	 */
	public String getHandleIdentifier() {
		return getHandleMemento();
	}

	public String getHandleMemento() {
		StringBuffer buff = new StringBuffer();
		getHandleMemento(buff);
		return buff.toString();
	}

	protected void getHandleMemento(StringBuffer buff) {
		((VjoModelElement) getParent()).getHandleMemento(buff);
		buff.append(getHandleMementoDelimiter());
		escapeMementoName(buff, getElementName());
	}

	protected abstract char getHandleMementoDelimiter();

	protected void escapeMementoName(StringBuffer buffer, String mementoName) {
		for (int i = 0, length = mementoName.length(); i < length; i++) {
			char character = mementoName.charAt(i);
			switch (character) {
			case JEM_ESCAPE:
			case JEM_COUNT:
			case JEM_SCRIPTPROJECT:
			case JEM_PROJECTFRAGMENT:
			case JEM_SCRIPTFOLDER:
			case JEM_FIELD:
			case JEM_METHOD:
			case JEM_SOURCEMODULE:
			case JEM_TYPE:
			case JEM_IMPORTDECLARATION:
			case JEM_LOCALVARIABLE:
			case JEM_TYPE_PARAMETER:
				buffer.append(JEM_ESCAPE);
			}
			buffer.append(character);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.dltk.mod.core.IModelElement#getModel()
	 */
	public IScriptModel getModel() {
		IModelElement current = this;
		do {
			if (current instanceof IScriptModel)
				return (IScriptModel) current;
		} while ((current = current.getParent()) != null);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.dltk.mod.core.IModelElement#getOpenable()
	 */
	public IOpenable getOpenable() {
		return this.getOpenableParent();
	}

	/**
	 * Return the first instance of IOpenable in the parent hierarchy of this
	 * element.
	 * 
	 * <p>
	 * Subclasses that are not IOpenable's must override this method.
	 */
	public IOpenable getOpenableParent() {
		return (IOpenable) this.m_parent;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.dltk.mod.core.IModelElement#getParent()
	 */
	public IModelElement getParent() {
		return m_parent;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.dltk.mod.core.IModelElement#getPrimaryElement()
	 */
	public IModelElement getPrimaryElement() {
		return getPrimaryElement(true);
	}

	/*
	 * Returns the primary element. If checkOwner, and the cu owner is primary,
	 * return this element.
	 */
	public IModelElement getPrimaryElement(boolean checkOwner) {
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.dltk.mod.core.IModelElement#getScriptProject()
	 */
	public IScriptProject getScriptProject() {
		IModelElement current = this;
		do {
			if (current instanceof IScriptProject)
				return (IScriptProject) current;
		} while ((current = current.getParent()) != null);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.dltk.mod.core.IModelElement#isReadOnly()
	 */
	public boolean isReadOnly() {
		return false;
	}

	/**
	 * Creates and returns a new not present exception for this element.
	 */
	public ModelException newNotPresentException() {
		return new ModelException(new ModelStatus(
				IModelStatusConstants.ELEMENT_DOES_NOT_EXIST, this));
	}

	public ISourceModule getSourceModule() {
		return null;
	}

	public IJstNode getJstNode() {
		return m_jstNode;
	}	

}
