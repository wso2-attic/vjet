/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsgen.shared.validation.vjo.visitor;

import org.ebayopensource.dsf.jst.IJstNode;

public interface IVjoValidationVisitorEventDispatcher {

	void registerListener(IVjoValidationListener listener);
	void registerListener(IJstNode node, IVjoValidationListener listener);
	void appendListener(IJstNode node, IVjoValidationListener listener);
	
	void registerPreAllChildrenListener(IVjoValidationListener listener);
	void registerPreAllChildrenListener(IJstNode node, IVjoValidationListener listener);
	void appendPreAllChildrenListener(IJstNode node, IVjoValidationListener listener);
	
	void registerPreChildListener(IVjoValidationListener listener);
	void registerPreChildListener(IJstNode node, IVjoValidationListener listener);
	void appendPreChildListener(IJstNode node, IVjoValidationListener listener);
	
	void registerPostChildListener(IVjoValidationListener listener);
	void registerPostChildListener(IJstNode node, IVjoValidationListener listener);
	void appendPostChildListener(IJstNode node, IVjoValidationListener listener);
	
	void registerPostAllChildrenListener(IVjoValidationListener listener);
	void registerPostAllChildrenListener(IJstNode node, IVjoValidationListener listener);
	void appendPostAllChildrenListener(IJstNode node, IVjoValidationListener listener);
	
	void dispatch(final IVjoValidationVisitorEvent event);
}
