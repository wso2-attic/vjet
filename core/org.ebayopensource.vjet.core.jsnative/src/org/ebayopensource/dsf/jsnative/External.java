/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsnative;

import org.ebayopensource.dsf.jsnative.anno.BrowserSupport;
import org.ebayopensource.dsf.jsnative.anno.BrowserType;
import org.ebayopensource.dsf.jsnative.anno.Function;
import org.ebayopensource.dsf.jsnative.anno.JsMetatype;
import org.mozilla.mod.javascript.IWillBeScriptable;

/**
 * JavaScript object representing window.external object. (IE specific)
 */
@JsMetatype
public interface External extends IWillBeScriptable {
	// Methods
	/**
	 * Clears the contents of the current selection.
	 */
	@BrowserSupport({BrowserType.IE_6P})
	@Function void AutoCompleteSaveForm(HtmlForm elem);

	/**
     * Only for Rhino support
     * @param type
     * @return
     */
	@BrowserSupport({BrowserType.RHINO_1P})
    @Function Object valueOf(String type);

}
