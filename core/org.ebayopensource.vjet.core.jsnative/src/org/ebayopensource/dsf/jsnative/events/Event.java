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
import org.ebayopensource.dsf.jsnative.anno.Dynamic;
import org.ebayopensource.dsf.jsnative.anno.Function;
import org.ebayopensource.dsf.jsnative.anno.Property;
import org.mozilla.mod.javascript.IJsJavaConvertible;
import org.mozilla.mod.javascript.IWillBeScriptable;

/**
 * http://www.w3.org/TR/DOM-Level-3-Events/java-binding.html
 */
@DOMSupport(DomLevel.THREE)
@Dynamic
public interface Event extends IWillBeScriptable,IJsJavaConvertible{
	
	//	 PhaseType
    public static final short CAPTURING_PHASE           = 1;
    public static final short AT_TARGET                 = 2;
    public static final short BUBBLING_PHASE            = 3;

    @DOMSupport(DomLevel.TWO)
	@BrowserSupport({BrowserType.IE_6P, BrowserType.FIREFOX_1P, BrowserType.OPERA_9P})
    @Property String getType();

    @DOMSupport(DomLevel.TWO)
	@BrowserSupport({BrowserType.FIREFOX_1P, BrowserType.OPERA_9P})
	@ARename(name="target || window.event.srcElement")
    @Property EventTarget getTarget();
	
    @DOMSupport(DomLevel.TWO)
	@BrowserSupport({BrowserType.IE_6P})
    @Property EventTarget getSrcElement();

//    @Property EventTarget getCurrentTarget();

//    @Property short getEventPhase();

    @DOMSupport(DomLevel.TWO)
	@BrowserSupport({BrowserType.FIREFOX_1P, BrowserType.OPERA_9P})
	@ARename(name = "bubbles || !(window.event.cancelBubble)")
    @Property boolean getBubbles();
	
	@BrowserSupport({BrowserType.IE_6P})
    @Property boolean getCancelBubble();

	@DOMSupport(DomLevel.TWO)
	@BrowserSupport({BrowserType.FIREFOX_1P, BrowserType.OPERA_9P})
	@Property boolean getCancelable();

	@DOMSupport(DomLevel.TWO)
	@BrowserSupport({BrowserType.FIREFOX_1P, BrowserType.OPERA_9P})
	@Property long getTimeStamp();

	@DOMSupport(DomLevel.TWO)
	@BrowserSupport({BrowserType.FIREFOX_1P, BrowserType.OPERA_9P})
	@Function void stopPropagation();

	@DOMSupport(DomLevel.TWO)
	@BrowserSupport({BrowserType.FIREFOX_1P, BrowserType.OPERA_9P})
	@Function void preventDefault();

//  @Function void initEvent(String eventTypeArg, 
//                          boolean canBubbleArg, 
//                          boolean cancelableArg);

    // Level-3
//    public String getNamespaceURI();
//
    @DOMSupport(DomLevel.THREE)
    @Function void stopImmediatePropagation();

    @DOMSupport(DomLevel.THREE)
    @Property boolean getDefaultPrevented();
    
    @DOMSupport(DomLevel.TWO)
    @BrowserSupport({BrowserType.IE_6P})
    @Property boolean getReturnValue();

//  @Function void initEventNS(String namespaceURIArg, 
//                            String eventTypeArg, 
//                            boolean canBubbleArg, 
//                            boolean cancelableArg);
    
}
