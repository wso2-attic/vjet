/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsnative.events;

import org.ebayopensource.dsf.javatojs.anno.ARename;
import org.ebayopensource.dsf.jsnative.anno.BrowserSupport;
import org.ebayopensource.dsf.jsnative.anno.BrowserType;
import org.ebayopensource.dsf.jsnative.anno.DOMSupport;
import org.ebayopensource.dsf.jsnative.anno.DomLevel;
import org.ebayopensource.dsf.jsnative.anno.Function;
import org.ebayopensource.dsf.jsnative.anno.JstExclude;
import org.ebayopensource.dsf.jsnative.anno.MType;
import org.mozilla.mod.javascript.IWillBeScriptable;

/**
 * http://www.w3.org/TR/DOM-Level-3-Events/java-binding.html
 */
@DOMSupport(DomLevel.THREE)
@MType
public interface EventTarget extends IWillBeScriptable {
	
	@DOMSupport(DomLevel.TWO)
	@BrowserSupport({BrowserType.FIREFOX_1P, BrowserType.OPERA_9P})
	@Function void addEventListener(
			String type, 
			Object listener, 
			boolean useCapture);
	
	/**
	 * @see EventTarget#addEventListener(String, Object, boolean)
	 */
	@BrowserSupport({BrowserType.NONE})
	@ARename(name="addEventListener")
	@JstExclude
	@Function void add(
			String type, 
			Object listener, 
			boolean useCapture);
	

	@DOMSupport(DomLevel.TWO)
	@BrowserSupport({BrowserType.FIREFOX_1P, BrowserType.OPERA_9P})
	@Function void removeEventListener(
			String type, 
			Object listener, 
            boolean useCapture);
	
	@DOMSupport(DomLevel.TWO)
	@BrowserSupport({BrowserType.FIREFOX_1P, BrowserType.OPERA_9P})
	@Function boolean dispatchEvent(Event evt) throws EventException;

	@DOMSupport(DomLevel.TWO)
	@BrowserSupport({BrowserType.IE_6P})
	@Function boolean fireEvent(String evtType);

	@DOMSupport(DomLevel.TWO)
	@BrowserSupport({BrowserType.IE_6P})
	@Function void attachEvent(
			String type, 
			Object listener);
	
	@DOMSupport(DomLevel.TWO)
	@BrowserSupport({BrowserType.IE_6P})
	@Function void detachEvent(
			String type, 
			Object listener);


}
