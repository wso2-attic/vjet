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
import org.mozilla.mod.javascript.IWillBeScriptable;

/**
 * When associating an object to a key on a node using Node.setUserData() 
 * the application can provide a handler that gets called when the node the object 
 * is associated to is being cloned, imported, or renamed. This can be used by the 
 * application to implement various behaviors regarding the data it associates to 
 * the DOM nodes. This interface defines that handler. 
 *
 */
@DOMSupport(DomLevel.THREE)
@JsMetatype
public interface UserDataHandler extends IWillBeScriptable {
	
//	 OperationType
    public static final short NODE_CLONED               = 1;
    public static final short NODE_IMPORTED             = 2;
    public static final short NODE_DELETED              = 3;
    public static final short NODE_RENAMED              = 4;
    public static final short NODE_ADOPTED              = 5;

    @DOMSupport(DomLevel.THREE) @BrowserSupport({BrowserType.UNDEFINED})
    @Function void handle(short operation, 
                       String key, 
                       Object data, 
                       Node src, 
                       Node dst);


}
