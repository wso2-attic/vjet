/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.declaration;

import org.ebayopensource.dsf.jst.BaseJstNode;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.IJstTypeReference;
import org.ebayopensource.dsf.jst.traversal.IJstNodeVisitor;
import org.ebayopensource.dsf.common.Z;

/**
 * the wrapper node that is used for import/extend/satisfies.
 */
public final class JstTypeReference extends BaseJstNode implements IJstTypeReference {
	private static final long serialVersionUID = -5219677533086422894L;
	private IJstType m_wrapped_type;
	private static final IJstType ERROR_UNDEFINED_TYPE = new JstType("ERROR_UNDEFINED_TYPE");

	public JstTypeReference(final IJstType type) {		
		if (type != null) {
			m_wrapped_type = type;
		} else {
			//to mark the error condition instead of null value
			m_wrapped_type = ERROR_UNDEFINED_TYPE;
		}
	}

	// for identity purposes, there could be multiple reference to the same type
	// use source info to uniquely identify the reference
	
	@Override
	public boolean equals (Object o) {
		
		if (!(o instanceof JstTypeReference)) {
			return false;
		}
		JstTypeReference other = (JstTypeReference) o;
		// equal if wrapped type is equal and source is equal
		return getReferencedType() == other.getReferencedType() &
				getSource() == other.getSource();
	}
	
	@Override
	public int hashCode() {
		if (getSource() != null) {
			return  getSource().hashCode() & m_wrapped_type.hashCode();
		} else {
			return m_wrapped_type.hashCode();
		}
	}

	@Override
	public void accept(IJstNodeVisitor visitor){
		visitor.visit(this);
	}
	
	@Override
	public String toString() {
		Z z = new Z();
		z.format("m_wrapped_type", m_wrapped_type.toString());
		if (getSource() != null){
			z.format(getSource().toString());
		}
		return z.toString();
	}

	public IJstType getReferencedType() {
		
		return m_wrapped_type;
	}
	
	public void setReferencedType(IJstType type){
		m_wrapped_type = type;
	}
}
