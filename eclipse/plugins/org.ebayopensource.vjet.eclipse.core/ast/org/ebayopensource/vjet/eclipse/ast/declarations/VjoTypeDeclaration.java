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
import org.eclipse.dltk.mod.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.mod.ast.declarations.TypeDeclaration;

/**
 * 
 * 
 */
public class VjoTypeDeclaration extends TypeDeclaration {

	public VjoTypeDeclaration(String name, int nameStart, int nameEnd,
			int start, int end) {
		super(name, nameStart, nameEnd, start, end);
	}

	@Override
	public void traverse(ASTVisitor visitor) throws Exception {
		if (visitor.visit(this)) {
			if (this.getSuperClasses() != null) {
				this.getSuperClasses().traverse(visitor);
			}
			if (this.fBody != null) {
				fBody.traverse(visitor);
			}
			FieldDeclaration[] fields = getVariables();
			for (FieldDeclaration fieldDeclaration : fields) {
				fieldDeclaration.traverse(visitor);
			}
			MethodDeclaration[] methods = getMethods();
			for (MethodDeclaration method : methods) {
				method.traverse(visitor);
			}
			visitor.endvisit(this);
		}
	}
}
