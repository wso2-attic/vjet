/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.declaration;

import org.ebayopensource.dsf.jst.IJstRefType;
import org.ebayopensource.dsf.jst.IJstType;

public class JstInferredRefType extends JstInferredType implements IJstRefType {

	private static final long serialVersionUID = 1L;

	public JstInferredRefType(IJstRefType targetType) {
		super(targetType);
	}

	@Override
	public IJstType getReferencedNode() {
		return ((IJstRefType)getType()).getReferencedNode();
	}
//
//	@Override
//	public boolean isFullyQualifiedReference() {
//		return ((IJstRefType)getType()).isFullyQualifiedReference();
//	}
}
