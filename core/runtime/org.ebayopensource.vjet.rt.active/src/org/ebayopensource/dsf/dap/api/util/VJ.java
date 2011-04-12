/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dap.api.util;

import org.ebayopensource.dsf.dap.proxy.ScriptEngineCtx;
import org.ebayopensource.dsf.dap.rt.DapCtx;
import org.ebayopensource.dsf.jsnative.HtmlDocument;
import org.ebayopensource.dsf.jsnative.Window;


public class VJ {
	
	public static Window win() {
		return DapCtx.window();
	}
	
	public static HtmlDocument doc() {
		return DapCtx.document();
	}
	
	/**
	 * This should only be called inside "non-scoped" methods
	 * to get different "this" object
	 */
	public static Object this_() {
		return ScriptEngineCtx.ctx().peekScopeFormStack();
	}
	
	//Following helper classes should not have any state, as they are only
	//initialized once
	
	/**
	 * provides convenient methods for Java programmers to use more typed
	 * and simple APIs for element creation via document
	 */
	public static DapDocumentHelper docx = DapDocumentHelper.getInstance();
	
	/**
	 * provides convenient methods for Java programmers to use more typed
	 * and simple APIs for using event methods
	 */
	public static DapEventHelper evtx = DapEventHelper.getInstance();
	
	/**
	 * provides typed API to global native JS functions
	 */
	public static DapJsHelper js = DapJsHelper.getInstance();
	
	public static VJJsxHelper jsx = VJJsxHelper.getInstance() ;
	
	/**
	 * should only be used to convert native "variant" JS object
	 * which could represent different primitive JS types: string, boolean and number.
	 */
	public static DapVariantTypeHelper as = DapVariantTypeHelper.getInstance();
}
