/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/**
 * 
 */
package org.ebayopensource.vjet.eclipse.ast.declarations;

import org.eclipse.dltk.mod.ast.ASTVisitor;
import org.eclipse.dltk.mod.ast.declarations.FieldDeclaration;
import org.eclipse.dltk.mod.ast.references.TypeReference;

/**
 * 
 * 
 */
public class VjoFieldDeclaration extends FieldDeclaration {
	public TypeReference type;

	public VjoFieldDeclaration(String name, int nameStart, int nameEnd,
			int declStart, int declEnd, TypeReference type) {
		super(name, nameStart, nameEnd, declStart, declEnd);
		this.type = type;
	}

	@Override
	public void traverse(ASTVisitor visitor) throws Exception {
		if (visitor.visit(this)) {
			getRef().traverse(visitor);
			if (type != null) {
				type.traverse(visitor);
			}
			visitor.endvisit(this);
		}
	}
}
