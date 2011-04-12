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
import org.ebayopensource.dsf.jst.IJstDoc;
import org.ebayopensource.dsf.jst.traversal.IJstNodeVisitor;

public class JstDoc extends BaseJstNode implements IJstDoc {
	
	private static final long serialVersionUID = 8559195226449734501L;
	
	private String m_comment;
	
	//
	// Constructor
	//
	public JstDoc(final String comment){
		assert comment != null : "comment cannot be null";
		m_comment = comment;
	}

	public String getComment() {
		return m_comment;
	}
	
	@Override
	public void accept(IJstNodeVisitor visitor){
		visitor.visit(this);
	}
	
	@Override
	public String toString(){
		return m_comment;
	}
	
	@Override
	public boolean equals(Object obj){
		if (obj == null || obj.getClass() != getClass()){
			return false;
		}
		return m_comment.equals(((JstDoc)obj).m_comment);
	}
	
	@Override
	public int hashCode(){
		return m_comment.hashCode();
	}

}
