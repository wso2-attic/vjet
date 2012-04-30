/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.translator.robust.completion;

import java.util.Stack;

import org.ebayopensource.dsf.jsgen.shared.validation.common.ScopeId;
import org.ebayopensource.dsf.jst.BaseJstNode;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstType;

public abstract class JstCompletion extends JstSyntaxError implements
		IJstCompletion {

	protected String[] completions;

	public abstract String getIncompletePart();

	protected Stack<ScopeId> blockStack = new Stack<ScopeId>();
	private IJstNode realParent;

	public JstCompletion(BaseJstNode parent, String[] completions) {
		super(parent);
		this.completions = completions;
		this.realParent = parent;
	}

	public void setRealParent(IJstNode realParent) {
		this.realParent = realParent;
	}

	public String[] getCompletion() {
		return completions;
	}

	public Stack<ScopeId> getScopeStack() {
		return blockStack;
	}

	public void setScopeStack(Stack<? extends ScopeId> scopeStack) {
		this.blockStack.clear();
		this.blockStack.addAll(scopeStack);
	}

	public void pushScope(ScopeId identifier) {
		this.blockStack.push(identifier);
	}

	public boolean inScope(ScopeId method) {
		return blockStack.contains(method);
	}

	public boolean isEmptyStack() {
		return blockStack.isEmpty();
	}

	public IJstNode getRealParent() {
		return realParent;
	}
	
	public IJstType getOwnerType(){
		return realParent == null ? null : realParent.getOwnerType();
	}

}
