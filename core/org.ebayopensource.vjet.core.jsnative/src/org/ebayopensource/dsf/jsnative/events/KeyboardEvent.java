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
public interface KeyboardEvent extends UIEvent, IJsJavaConvertible {
    // KeyLocationCode
    public static final int DOM_KEY_LOCATION_STANDARD = 0x00;
    public static final int DOM_KEY_LOCATION_LEFT     = 0x01;
    public static final int DOM_KEY_LOCATION_RIGHT    = 0x02;
    public static final int DOM_KEY_LOCATION_NUMPAD   = 0x03;

    @DOMSupport(DomLevel.THREE)
    @BrowserSupport({BrowserType.IE_6P, BrowserType.FIREFOX_1P, BrowserType.OPERA_9P})
    @Property String getKeyIdentifier();

    @DOMSupport(DomLevel.THREE)
    @BrowserSupport({BrowserType.IE_6P, BrowserType.FIREFOX_1P, BrowserType.OPERA_9P})
    @Property int getKeyLocation();

    @DOMSupport(DomLevel.THREE)
    @BrowserSupport({BrowserType.IE_6P, BrowserType.FIREFOX_1P, BrowserType.OPERA_9P})
    @Property boolean getCtrlKey();

    @DOMSupport(DomLevel.THREE)
    @BrowserSupport({BrowserType.IE_6P, BrowserType.FIREFOX_1P, BrowserType.OPERA_9P})
    @Property boolean getShiftKey();

    @DOMSupport(DomLevel.THREE)
    @BrowserSupport({BrowserType.IE_6P, BrowserType.FIREFOX_1P, BrowserType.OPERA_9P})
    @Property boolean getAltKey();

    @DOMSupport(DomLevel.THREE)
    @BrowserSupport({BrowserType.IE_6P, BrowserType.FIREFOX_1P, BrowserType.OPERA_9P})
    @Property boolean getMetaKey();

    @DOMSupport(DomLevel.THREE)
    @BrowserSupport({BrowserType.IE_6P, BrowserType.FIREFOX_1P, BrowserType.OPERA_9P})
    @Property int getKeyCode();

    
    @DOMSupport(DomLevel.THREE)
    @BrowserSupport({BrowserType.FIREFOX_2P})
    @Property int getWhich();

    @DOMSupport(DomLevel.THREE)
    @BrowserSupport({BrowserType.FIREFOX_1P, BrowserType.OPERA_9P})
    @Property int getCharCode();

    @DOMSupport(DomLevel.THREE)
    @BrowserSupport({BrowserType.IE_6P, BrowserType.FIREFOX_1P, BrowserType.OPERA_9P})
    @Function boolean getModifierState(String keyIdentifierArg);

    @DOMSupport(DomLevel.THREE)
    @BrowserSupport({BrowserType.IE_6P, BrowserType.FIREFOX_1P, BrowserType.OPERA_9P})
    @Function void initKeyboardEvent(String typeArg, 
                                  boolean canBubbleArg, 
                                  boolean cancelableArg, 
                                  Object viewArg, 
                                  String keyIdentifierArg, 
                                  int keyLocationArg, 
                                  String modifiersListArg);

    @DOMSupport(DomLevel.THREE)
    @BrowserSupport({BrowserType.IE_6P, BrowserType.FIREFOX_1P, BrowserType.OPERA_9P})
    @Function void initKeyboardEventNS(String namespaceURIArg, 
                                    String typeArg, 
                                    boolean canBubbleArg, 
                                    boolean cancelableArg, 
                                    Object viewArg, 
                                    String keyIdentifierArg, 
                                    int keyLocationArg, 
                                    String modifiersListArg);

}

