/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.declaration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.ebayopensource.dsf.jst.IJstOType;
import org.ebayopensource.dsf.jst.IJstProperty;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.traversal.IJstNodeVisitor;

public class JstObjectLiteralType extends JstType implements IJstOType{

	private static final long serialVersionUID = 1L;
	private List<IJstProperty> m_optionalFields = null;
	private JstMethod m_constructor = null;

	public JstObjectLiteralType (String name) {
		super(name);
		m_optionalFields = new ArrayList<IJstProperty>();
	}
	
	@Override
	public void accept(IJstNodeVisitor visitor){
		visitor.visit(this);
	}

	public List<IJstProperty> getOptionalFields() {
		return Collections.unmodifiableList(m_optionalFields);
	}

	public void addOptionalField(IJstProperty optionalField) {
		synchronized (this) {
			m_optionalFields.add(optionalField);
		}
	}

	public boolean hasOptionalFields() {
		return !m_optionalFields.isEmpty();
	}
	
	public boolean isOptionalField(final IJstProperty pty){
		return m_optionalFields.contains(pty);
	}
	
	/**
	 * @see IJstType#getConstructor()
	 */
	public JstMethod getConstructor() {
		if (m_constructor == null) {
			JstModifiers modifiers = new JstModifiers();
			modifiers.setPublic();
			List<IJstProperty> fields = this.getProperties(false);
			JstArg[] argsArray = getContructorArgs(fields.size()-1, fields);
			m_constructor = new JstConstructor(modifiers, argsArray);
			if (hasOptionalFields()) {
				//Create overloaded...
				for (int i = 0; i < fields.size(); i++) {
					if (m_optionalFields.contains(fields.get(i))) {
						for (int j = i-1; j < fields.size() - 1; j++) {
							JstConstructor overloaded = new JstConstructor(getContructorArgs(j, fields));
							overloaded.setParent(m_constructor, false);
							m_constructor.addOverloaded(overloaded);
							
						}
						break;
					}
				}
			} 
			addChild(m_constructor);
		}
		return m_constructor;
	}
	
	private JstArg[] getContructorArgs(int endIndex, List<IJstProperty> fields) {
		int size = endIndex+1;
		JstArg[] argsArray = new JstArg[size];
		for (int i = 0; i < size; i++) {
			IJstProperty field = fields.get(i);
			argsArray[i] = new JstArg(field.getType(), field.getName().getName(), false);
		}
		return argsArray;
	}
}
