/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.js;

import org.ebayopensource.dsf.html.dom.DDiv;

public class JSDiv extends JSElement {

	private DDiv div = null;

	private JSDiv() {
	}

	/** Creates new JSDiv */
	public JSDiv(JSWindow window, DDiv div) {
		super(window, div);

		this.div = div;
	}
}
