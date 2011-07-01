/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/

package org.ebayopensource.vjet.eclipse.internal.ui.editor.semantic.highlighting;

import org.ebayopensource.dsf.jst.declaration.JstMethod;
import org.ebayopensource.dsf.jst.declaration.JstName;
import org.eclipse.dltk.mod.ast.expressions.Expression;
import org.eclipse.dltk.mod.internal.ui.editor.semantic.highlighting.SemanticHighlightingReconciler;
import org.eclipse.jdt.core.dom.SimpleName;

/**
 * Semantic token
 */
public final class SemanticToken {

	/** AST node */
	private JstName fName;
	private Expression fLiteral;
	private JstMethod fMethod;//our method do not have a child as JstName which refers to the method name part.

	/** Binding */
	// private IBinding fBinding;
	/** Is the binding resolved? */
	// private boolean fIsBindingResolved = false;
	/** AST root */
	// private CompilationUnit fRoot;
	// private boolean fIsRootResolved = false;
	/**
	 * @return Returns the binding, can be <code>null</code>.
	 */
	// public IBinding getBinding() {
	// if (!fIsBindingResolved) {
	// fIsBindingResolved= true;
	// if (fNode != null)
	// fBinding= fNode.resolveBinding();
	// }
	//		
	// return fBinding;
	// }
	/**
	 * @return the AST node (a {@link SimpleName})
	 */
	public JstName getNameNode() {
		return fName;
	}

	/**
	 * @return the AST node (a
	 *         <code>Boolean-, Character- or NumberLiteral</code>)
	 */
	public Expression getLiteral() {
		return fLiteral;
	}

	
	public JstMethod getMethod(){
		return fMethod;
	}
	
	/**
	 * @return the AST root
	 */
	// public CompilationUnit getRoot() {
	// if (!fIsRootResolved) {
	// fIsRootResolved= true;
	// fRoot= (CompilationUnit) (fNode != null ? fNode : fLiteral).getRoot();
	// }
	//
	// return fRoot;
	// }
	/**
	 * Update this token with the given AST node.
	 * <p>
	 * NOTE: Allowed to be used by {@link SemanticHighlightingReconciler} only.
	 * </p>
	 * 
	 * @param node
	 *            the AST simple name
	 */
	void update(JstName node) {
		clear();
		fName = node;
	}

	/**
	 * Update this token with the given AST node.
	 * <p>
	 * NOTE: Allowed to be used by {@link SemanticHighlightingReconciler} only.
	 * </p>
	 * 
	 * @param literal
	 *            the AST literal
	 */
	void update(Expression literal) {
		clear();
		fLiteral = literal;
	}
	
	void update(JstMethod method) {
		clear();
		fMethod= method;
	}
	

	/**
	 * Clears this token.
	 * <p>
	 * NOTE: Allowed to be used by {@link SemanticHighlightingReconciler} only.
	 * </p>
	 */
	void clear() {
		fName = null;
		fLiteral = null;
		fMethod=null;
		// fBinding= null;
		// fIsBindingResolved = false;
		// fRoot= null;
		// fIsRootResolved = false;
	}
}
