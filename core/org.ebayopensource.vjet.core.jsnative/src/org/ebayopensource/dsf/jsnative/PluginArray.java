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
import org.ebayopensource.dsf.jsnative.anno.DOMSupport;
import org.ebayopensource.dsf.jsnative.anno.DomLevel;
import org.ebayopensource.dsf.jsnative.anno.Function;
import org.ebayopensource.dsf.jsnative.anno.JsMetatype;
import org.ebayopensource.dsf.jsnative.anno.JstExclude;
import org.ebayopensource.dsf.jsnative.anno.Property;
import org.mozilla.mod.javascript.IWillBeScriptable;

/**
 * An <code>PluginArray</code> is an array of Plugins. An individual plugin may be accessed by 
 * either ordinal index or the plugin's name or id attributes.
 */

// TODO -- make this array extend array IE T
// TODO -- make an annotation that can understand JstArray component type on Navigator.getPlugins

@DOMSupport(DomLevel.ONE)
@JsMetatype
public interface PluginArray extends IWillBeScriptable {
	
	/**
	 * Returns the length or size of the array.
	 * @return int
	 */
	@Property int getLength();

    /**
     * This method retrieves a plugin specified by ordinal index. 
     * Plugins are numbered in tree order (depth-first traversal order). 
     * @param index The index of the plugin to be fetched. The index origin is 0
     * @return <Plugin>Node</code> The plugin at the corresponding position upon success. 
     * A value of null is returned if the index is out of range.
     */
	@Function Plugin item(int index);

    /**
     * This method retrieves a Plugin using a name. 
     * @param name The name of the <code>Plugin</code> to be fetched.
     * @return <code>Plugin</code> The plugin with a name or id attribute whose value corresponds 
     * to the specified string. Upon failure (e.g., no plugin with this name exists),
     * returns <code>null</code>.
     */
	@Function Plugin namedItem(String name);
	
	/**
     * Only for Rhino support
     * @param type
     * @return
     */
	@BrowserSupport({BrowserType.RHINO_1P})
	@JstExclude
    @Function Object valueOf(String type);
	
	@BrowserSupport({BrowserType.NONE})
	@JstExclude
	Plugin get(String name);
}

