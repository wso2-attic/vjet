/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dap.proxy;

import org.ebayopensource.dsf.javatojs.anno.AJavaOnly;
import org.mozilla.mod.javascript.Function;
import org.mozilla.mod.javascript.IJsJavaProxy;

/**
 * proxy for native JS function, with a generic "call" and "apply" method
 */
@AJavaOnly
public interface INativeJsFuncProxy<T> extends IJsJavaProxy {
	Object call(T thisObj, Object ...args);
	Object apply(T thisObj, Object[] args);
	Function getJsNative();
}
