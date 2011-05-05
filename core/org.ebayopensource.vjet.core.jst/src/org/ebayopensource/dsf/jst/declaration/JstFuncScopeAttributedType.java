/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.declaration;

/**
 * An attributed type based on function scope at invocation time.
 */
public class JstFuncScopeAttributedType extends JstDeferredType {

	private static final long serialVersionUID = 1L;
	
	public JstFuncScopeAttributedType() {
		super(JstCache.getInstance().getType("Object"));
	}
	
	@Override
	public String getName() {
		return getSimpleName();
	}
	
	@Override
	public String getSimpleName() {
		return "this";
	}
}
