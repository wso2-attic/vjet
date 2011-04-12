/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsgen.shared.validation.vjo.visitor;

import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoValidationCtx;
import org.ebayopensource.dsf.jst.IJstNode;

public interface IVjoValidationVisitorEvent {

	public IJstNode getVisitNode();
	public IJstNode getVisitChildNode();
	public VjoValidationCtx getValidationCtx();
	public VjoValidationVisitorState getVisitState();
}
