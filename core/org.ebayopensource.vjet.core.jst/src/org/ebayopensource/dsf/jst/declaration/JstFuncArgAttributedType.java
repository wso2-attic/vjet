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
 * An attributed type based on specific function argument at function invocation time.
 */
public class JstFuncArgAttributedType extends JstDeferredType {

	private static final long serialVersionUID = 1L;
	private final int m_argPosition; //one-based indexing
	
	/**
	 * @param argPosition - one-based indexing of argument
	 */
	public JstFuncArgAttributedType(int argPosition) {
		super(JstCache.getInstance().getType("Object"));
		m_argPosition = argPosition;
	}
	
	public int getArgPosition() {
		return m_argPosition;
	}
	
	@Override
	public String getName() {
		return getSimpleName();
	}
	
	@Override
	public String getSimpleName() {
		return "%" + m_argPosition;
	}
}
