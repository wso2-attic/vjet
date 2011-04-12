/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.js;

import org.ebayopensource.dsf.html.dom.DButton;
import org.ebayopensource.dsf.html.dom.EHtmlAttr;

public class JSButton extends JSElement {

	private DButton button = null;

	private JSButton() {
	}

	/** Creates new JSDiv */
	public JSButton(JSWindow window, DButton button) {
		super(window, button);

		this.button = button;
	}
	
	public void setDisabled(boolean disabled){
		button.setHtmlDisabled(disabled);
		getListener().onAttrChange(button, EHtmlAttr.disabled, disabled);
	}
}
