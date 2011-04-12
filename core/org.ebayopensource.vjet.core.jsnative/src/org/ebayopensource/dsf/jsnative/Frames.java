/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsnative;

import org.ebayopensource.dsf.jsnative.anno.DOMSupport;
import org.ebayopensource.dsf.jsnative.anno.DomLevel;
import org.ebayopensource.dsf.jsnative.anno.Function;
import org.ebayopensource.dsf.jsnative.anno.JsMetatype;
import org.mozilla.mod.javascript.IWillBeScriptable;


/**
 * Represents an array of Window objects, one for each frame of iframe contained
 * within this window.
 *
 */
@DOMSupport(DomLevel.ZERO)
@JsMetatype
public interface Frames extends IWillBeScriptable {

	@Function void addChildWindow(Window window);

	@Function int size();

	@Function Window at(int i);

}
