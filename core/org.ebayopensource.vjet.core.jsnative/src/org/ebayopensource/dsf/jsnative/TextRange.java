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
import org.ebayopensource.dsf.jsnative.anno.Property;
import org.mozilla.mod.javascript.IWillBeScriptable;

/**
 * JavaScript object representing document.selection.TextRange object. (IE specific)
 */
@JsMetatype
public interface TextRange extends IWillBeScriptable {
	/**
	 * 
	 * Returns the HTML fragment for the selected text range.
	 */
	@BrowserSupport({BrowserType.IE_6P})
	@Property String getHtmlText();

	/**
	 * 
	 * Retrieves the text contained within the range
	 */
	@BrowserSupport({BrowserType.IE_6P})
	@Property String getText();

	/**
	 * 
	 * Retrieves the width of the rectangle that bounds the text range.
	 */
	@BrowserSupport({BrowserType.IE_6P})
	@Property int getBoundingWidth();

	/**
	 * 
	 * Retrieves the height of the rectangle that bounds the text range.
	 */
	@BrowserSupport({BrowserType.IE_6P})
	@Property int getBoundingHeight();

	
	/**
	 * 
	 * Retrieves the left of the rectangle that bounds the text range.
	 */
	@BrowserSupport({BrowserType.IE_6P})
	@Property int getBoundingLeft();

	
	/**
	 * 
	 * Retrieves the top of the rectangle that bounds the text range.
	 */
	@BrowserSupport({BrowserType.IE_6P})
	@Property int getBoundingTop();

	
	/**
	 * 
	 * Retrieves the left coordinate of the rectangle that bounds the text range.
	 */
	@BrowserSupport({BrowserType.IE_6P})
	@Property int getOffsetLeft();

	/**
	 * 
	 * Retrieves the top coordinate of the rectangle that bounds the text range.
	 */
	@BrowserSupport({BrowserType.IE_6P})
	@Property int getOffsetTop();
	
	/**
     * Only for Rhino support
     * @param type
     * @return
     */
	@BrowserSupport({BrowserType.RHINO_1P})
    @Function Object valueOf(String type);

}
