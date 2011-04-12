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
 * JavaScript object representing document.selection object. (IE specific)
 */
@JsMetatype
public interface Selection extends IWillBeScriptable {
	
	// Properties
	/**
	 * Retrieves the type of selection. 
	 */
	@BrowserSupport({BrowserType.IE_6P})
	@Property String getType();

	/**
	 * Retrieves the type of selection. 
	 */
	@BrowserSupport({BrowserType.IE_6P})
	@Property String getTypeDetail();
	
	// Methods
	/**
	 * Clears the contents of the current selection.
	 */
	@BrowserSupport({BrowserType.IE_6P})
	@Function void clear();
	
	/**
	 * Deselects the current selection.
	 */
	@BrowserSupport({BrowserType.IE_6P})
	@Function void empty();
	
	/**
	 * Creates a TextRange object for the selection.
	 */
	@BrowserSupport({BrowserType.IE_6P})
	@Function TextRange createRange();
	
	/**
	 * Creates a TextRange object collection for the selection.
	 */
	@BrowserSupport({BrowserType.IE_6P})
	@Function TextRange[] createRangeCollection();

	/**
     * Only for Rhino support
     * @param type
     * @return
     */
	@BrowserSupport({BrowserType.RHINO_1P})
    @Function Object valueOf(String type);
}
