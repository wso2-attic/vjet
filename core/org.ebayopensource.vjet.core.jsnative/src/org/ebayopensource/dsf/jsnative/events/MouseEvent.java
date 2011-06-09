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
public interface MouseEvent extends UIEvent, IJsJavaConvertible {
	
	@DOMSupport(DomLevel.TWO)
	@BrowserSupport({BrowserType.IE_6P, BrowserType.FIREFOX_1P, BrowserType.OPERA_9P})
	@Property int getScreenX();

	@DOMSupport(DomLevel.TWO)
	@BrowserSupport({BrowserType.IE_6P, BrowserType.FIREFOX_1P, BrowserType.OPERA_9P})
	@Property int getScreenY();

	@DOMSupport(DomLevel.TWO)
	@BrowserSupport({BrowserType.IE_6P, BrowserType.FIREFOX_1P, BrowserType.OPERA_9P})
	@Property int getClientX();

	@DOMSupport(DomLevel.TWO)
	@BrowserSupport({BrowserType.IE_6P, BrowserType.FIREFOX_1P, BrowserType.OPERA_9P})
	@Property int getClientY();

	@DOMSupport(DomLevel.TWO)
	@BrowserSupport({BrowserType.IE_6P, BrowserType.FIREFOX_1P, BrowserType.OPERA_9P})
	@Property boolean getCtrlKey();

	@DOMSupport(DomLevel.TWO)
	@BrowserSupport({BrowserType.IE_6P, BrowserType.FIREFOX_1P, BrowserType.OPERA_9P})
	@Property boolean getShiftKey();

	@DOMSupport(DomLevel.TWO)
	@BrowserSupport({BrowserType.IE_6P, BrowserType.FIREFOX_1P, BrowserType.OPERA_9P})
	@Property boolean getAltKey();

	@DOMSupport(DomLevel.TWO)
	@BrowserSupport({BrowserType.IE_6P, BrowserType.FIREFOX_1P, BrowserType.OPERA_9P})
	@Property boolean getMetaKey();

	@DOMSupport(DomLevel.TWO)
	@BrowserSupport({BrowserType.IE_6P, BrowserType.FIREFOX_1P, BrowserType.OPERA_9P})
	@Property int getButton();
	
	@DOMSupport(DomLevel.THREE)
	@Property int getButtons();

	@DOMSupport(DomLevel.TWO)
	@BrowserSupport({BrowserType.FIREFOX_1P, BrowserType.OPERA_9P})
	@Property EventTarget getRelatedTarget();

	@DOMSupport(DomLevel.TWO)
	@BrowserSupport({BrowserType.IE_6P})
	@Property EventTarget getFromElement();

	@DOMSupport(DomLevel.TWO)
	@BrowserSupport({BrowserType.IE_6P})
	@Property EventTarget getToElement();
	
	@DOMSupport(DomLevel.TWO)
	@BrowserSupport({BrowserType.IE_6P})
    @Property int getOffsetX();
    
	@DOMSupport(DomLevel.TWO)
	@BrowserSupport({BrowserType.IE_6P})
    @Property int getOffsetY();
	
	@DOMSupport(DomLevel.TWO)
	@BrowserSupport({BrowserType.IE_6P})
    @Property int getX();
    
	@DOMSupport(DomLevel.TWO)
	@BrowserSupport({BrowserType.IE_6P})
    @Property int getY();

	@DOMSupport(DomLevel.TWO)
	@BrowserSupport({BrowserType.FIREFOX_1P,BrowserType.OPERA_7P,BrowserType.SAFARI_3P})
    @Property int getPageX();
	
	@DOMSupport(DomLevel.TWO)
	@BrowserSupport({BrowserType.FIREFOX_1P,BrowserType.OPERA_7P,BrowserType.SAFARI_3P})
    @Property int getPageY();

	@DOMSupport(DomLevel.TWO)
	@BrowserSupport({BrowserType.IE_6P, BrowserType.FIREFOX_1P, BrowserType.OPERA_9P})
	@Function void initMouseEvent(String typeArg, 
                               boolean canBubbleArg, 
                               boolean cancelableArg, 
                               Object viewArg, 
                               int detailArg, 
                               int screenXArg, 
                               int screenYArg, 
                               int clientXArg, 
                               int clientYArg, 
                               boolean ctrlKeyArg, 
                               boolean altKeyArg, 
                               boolean shiftKeyArg, 
                               boolean metaKeyArg, 
                               int buttonArg, 
                               EventTarget relatedTargetArg);

	// DOM Level 3
	@DOMSupport(DomLevel.THREE)
    @Function boolean getModifierState(String keyIdentifierArg);
//
//	@DOMSupport(DomLevel.THREE)
//    @Function void initMouseEventNS(String namespaceURIArg, 
//                                 String typeArg, 
//                                 boolean canBubbleArg, 
//                                 boolean cancelableArg, 
//                                 AbstractView viewArg, 
//                                 int detailArg, 
//                                 int screenXArg, 
//                                 int screenYArg, 
//                                 int clientXArg, 
//                                 int clientYArg, 
//                                 short buttonArg, 
//                                 EventTarget relatedTargetArg, 
//                                 String modifiersListArg);

}
