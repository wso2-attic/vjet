/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.translator.robust.completion;

import org.ebayopensource.dsf.jst.BaseJstNode;

public class JstCompletionOnQualifiedNameReference extends JstCompletion {

	private static final long serialVersionUID = 1L;

	public JstCompletionOnQualifiedNameReference(BaseJstNode parent) {
		super(parent, new String[0]);
	}

	@Override
	public String getIncompletePart() {
		return getToken();
	}

}
