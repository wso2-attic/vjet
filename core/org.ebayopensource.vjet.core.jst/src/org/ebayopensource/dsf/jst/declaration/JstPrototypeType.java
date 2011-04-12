/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.declaration;

import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.traversal.IJstNodeVisitor;


public class JstPrototypeType extends JstProxyType{
	private static final long serialVersionUID = 1L;
	
	public static final String PROTOTYPE = "Prototype::";
	
	public JstPrototypeType(final IJstType type) {
		super(type);
	}
	
	public String getSimpleName(){
		return PROTOTYPE + getType().getSimpleName();
	}
	
	public String getName(){
		return PROTOTYPE + getType().getName();
	}
	
	public void accept(IJstNodeVisitor visitor){
//TODO restore logic in V1.5	visitor.visit(this);
	}
}
