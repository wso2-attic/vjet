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

import org.eclipse.dltk.mod.ast.ASTNode;
import org.eclipse.dltk.mod.ast.ASTVisitor;
import org.eclipse.dltk.mod.ast.declarations.Argument;
import org.eclipse.dltk.mod.ast.references.SimpleReference;
import org.eclipse.dltk.mod.ast.references.TypeReference;

/**
 * 
 * 
 */
public class VjoArgument extends Argument {
	public TypeReference type;

	public VjoArgument(SimpleReference name, int start, ASTNode init, int mods) {
		super(name, start, init, mods);
	}

	@Override
	public void traverse(ASTVisitor visitor) throws Exception {
		if (visitor.visit(this)) {
			if (initialization != null) {
				initialization.traverse(visitor);
			}
			if (type != null) {
				type.traverse(visitor);
			}
			visitor.endvisit(this);
		}
	}
}
