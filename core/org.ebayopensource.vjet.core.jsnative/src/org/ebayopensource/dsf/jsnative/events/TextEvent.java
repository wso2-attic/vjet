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
import org.w3c.dom.views.AbstractView;

/**
 * http://www.w3.org/TR/DOM-Level-3-Events/java-binding.html
 */
@DOMSupport(DomLevel.THREE)
public interface TextEvent extends UIEvent {
	
	@DOMSupport(DomLevel.THREE)
	@BrowserSupport({BrowserType.FIREFOX_1P, BrowserType.OPERA_9P})
	@Property String getData();

	@DOMSupport(DomLevel.THREE)
	@BrowserSupport({BrowserType.FIREFOX_1P, BrowserType.OPERA_9P})
	@Function void initTextEvent(String typeArg, 
                              boolean canBubbleArg, 
                              boolean cancelableArg, 
                              AbstractView viewArg, 
                              String dataArg);

//	 DOM Level 3
//	@DOMSupport(DomLevel.THREE)
//	@BrowserSupport({ BrowserType.FIREFOX_1, BrowserType.OPERA_9})
//	@Function void initTextEventNS(String namespaceURIArg, 
//                                String typeArg, 
//                                boolean canBubbleArg, 
//                                boolean cancelableArg, 
//                                AbstractView viewArg, 
//                                String dataArg);

}

