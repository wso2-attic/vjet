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
package org.ebayopensource.vjet.eclipse.internal.ui.view.ast;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.mod.wst.jsdt.core.ast.IASTNode;
import org.eclipse.mod.wst.jsdt.core.compiler.IProblem;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.CompilationUnitDeclaration;

/**
 * 
 *
 */
class ASTTreeLabelProvider extends LabelProvider {
	private boolean isRecoveryUnit;
	
	public ASTTreeLabelProvider(boolean isRecoveryUnit) {
		this.isRecoveryUnit = isRecoveryUnit;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.LabelProvider#getText(java.lang.Object)
	 */
	public String getText(Object element) {
		String simpleClassName = element.getClass().getSimpleName();
		
		if (element instanceof IASTNode) {
			IASTNode node = (IASTNode)element;
			String offset = "[" + node.sourceStart() + "," + node.sourceEnd() + "]";
			
			if (element instanceof CompilationUnitDeclaration) {
				if (this.isRecoveryUnit)
					return simpleClassName + offset + " (Recovery)"; 
				else
					return simpleClassName + offset + " (Original)"; 
			}
			
			return simpleClassName + offset;
		}
		
		if (element instanceof IProblem) {
			IProblem problem = (IProblem)element;
			
			return simpleClassName + "[id=" + problem.getID() + "]"; 
		}
			
		return simpleClassName;
	}
}
