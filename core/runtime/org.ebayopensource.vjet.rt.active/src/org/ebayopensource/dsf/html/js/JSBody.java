/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.js;

import org.ebayopensource.dsf.html.dom.DBody;

public class JSBody extends JSElement {

	private DBody body = null;

	private JSBody() {
	}

	/** Creates new JSDiv */
	public JSBody(JSWindow window, DBody body) {
		super(window, body);

		this.body = body;
	}
}
