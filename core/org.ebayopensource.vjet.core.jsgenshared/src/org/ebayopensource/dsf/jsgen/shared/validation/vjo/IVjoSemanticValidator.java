/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsgen.shared.validation.vjo;

import java.util.List;

import org.ebayopensource.dsf.jst.IJstNode;

@Deprecated
public interface IVjoSemanticValidator {

	void preChildrenValidation(final VjoValidationCtx ctx) 
		throws VjoValidationRuntimeException;
	
	void preChildValidation(final VjoValidationCtx ctx, 
			final IJstNode child) 
		throws VjoValidationRuntimeException;
	
	void postChildValidation(final VjoValidationCtx ctx, 
			final IJstNode child) 
		throws VjoValidationRuntimeException;

	void postChildrenValidation(final VjoValidationCtx ctx) 
		throws VjoValidationRuntimeException;
		
	List<Class<? extends IJstNode>> getTargetNodeTypes();
}