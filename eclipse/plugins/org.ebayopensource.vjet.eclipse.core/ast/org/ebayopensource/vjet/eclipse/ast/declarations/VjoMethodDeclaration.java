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

import java.util.Iterator;
import java.util.List;

import org.eclipse.dltk.mod.ast.ASTVisitor;
import org.eclipse.dltk.mod.ast.declarations.Argument;
import org.eclipse.dltk.mod.ast.declarations.Decorator;
import org.eclipse.dltk.mod.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.mod.ast.references.TypeReference;
import org.eclipse.dltk.mod.ast.statements.Block;

/**
 * 
 * 
 */
public class VjoMethodDeclaration extends MethodDeclaration {
	public TypeReference type;

	public VjoMethodDeclaration(String name, int nameStart, int nameEnd,
			int declStart, int declEnd) {
		super(name, nameStart, nameEnd, declStart, declEnd);
	}

	@Override
	public void traverse(ASTVisitor visitor) throws Exception {
		if (visitor.visit(this)) {
			// Decorators
			List decorators = getDecorators();
			if (decorators != null) {
				if (decorators != null) {
					Iterator it = decorators.iterator();
					while (it.hasNext()) {
						Decorator dec = (Decorator) it.next();
						dec.traverse(visitor);
					}
				}
			}

			// Arguments
			List arguments = getArguments();
			if (arguments != null) {
				Iterator it = arguments.iterator();
				while (it.hasNext()) {
					Argument arg = (Argument) it.next();
					arg.traverse(visitor);
				}
			}

			// Body
			Block body = getBody();
			if (body != null) {
				body.traverse(visitor);
			}

			// retrun type
			if (type != null) {
				type.traverse(visitor);
			}
			visitor.endvisit(this);
		}
	}
}
