/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.traversal;

import org.ebayopensource.dsf.jst.IJstNode;

public class JstVisitorAdapter implements IJstVisitor {

	public void preVisit(IJstNode node){}
	public boolean visit(IJstNode node){return true;}
	public void endVisit(IJstNode node){}
	public void postVisit(IJstNode node){}
}
