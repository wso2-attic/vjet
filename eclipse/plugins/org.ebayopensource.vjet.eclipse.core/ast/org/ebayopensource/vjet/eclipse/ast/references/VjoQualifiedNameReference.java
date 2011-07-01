/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.ast.references;

import org.ebayopensource.dsf.jst.token.IExpr;
import org.eclipse.dltk.mod.ast.references.SimpleReference;

public class VjoQualifiedNameReference extends SimpleReference {

	private String type;
	private IExpr expr;
	
	public VjoQualifiedNameReference(int start, int end, String name, String typeName, IExpr expression) {
		super(start, end, name);
		this.type = typeName;
		this.expr = expression;
	}

	public String getType() {
		return type;
	}
	
	public IExpr getExpr() {
		return expr;
	}
}
