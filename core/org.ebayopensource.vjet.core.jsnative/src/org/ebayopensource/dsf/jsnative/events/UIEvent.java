/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsnative.events;

import org.ebayopensource.dsf.jsnative.anno.BrowserSupport;
import org.ebayopensource.dsf.jsnative.anno.BrowserType;
import org.ebayopensource.dsf.jsnative.anno.DOMSupport;
import org.ebayopensource.dsf.jsnative.anno.DomLevel;
import org.ebayopensource.dsf.jsnative.anno.Function;
import org.ebayopensource.dsf.jsnative.anno.Property;
import org.mozilla.mod.javascript.IJsJavaConvertible;

/**
 * http://www.w3.org/TR/DOM-Level-3-Events/java-binding.html
 */
@DOMSupport(DomLevel.THREE)
public interface UIEvent extends Event, IJsJavaConvertible {
	
	@DOMSupport(DomLevel.TWO)
	@BrowserSupport({BrowserType.IE_6P,BrowserType.FIREFOX_1P, BrowserType.OPERA_9P})
	@Property Object getView();

	@DOMSupport(DomLevel.TWO)
	@BrowserSupport({BrowserType.IE_6P,BrowserType.FIREFOX_1P, BrowserType.OPERA_9P})
	@Property int getDetail();

	@DOMSupport(DomLevel.TWO)
	@BrowserSupport({BrowserType.IE_6P,BrowserType.FIREFOX_1P, BrowserType.OPERA_9P})
	@Function void initUIEvent(String typeArg, 
                            boolean canBubbleArg, 
                            boolean cancelableArg, 
                            Object viewArg, 
                            int detailArg);

	// DOM Level 3
//	@Function void initUIEventNS(String namespaceURIArg, 
//                              String typeArg, 
//                              boolean canBubbleArg, 
//                              boolean cancelableArg, 
//                              AbstractView viewArg, 
//                              int detailArg);

}

