/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsnative.events;

import org.ebayopensource.dsf.jsnative.anno.Constructor;
import org.ebayopensource.dsf.jsnative.anno.DOMSupport;
import org.ebayopensource.dsf.jsnative.anno.DomLevel;

/**
 *http://www.w3.org/TR/DOM-Level-3-Events/#java-binding-java-binding
 */
@DOMSupport(DomLevel.THREE)
public class EventException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public EventException(short code, String message) {
       super(message);
       this.code = code;
    }
	
    public short   code;
    
    // EventExceptionCode
    public static final short UNSPECIFIED_EVENT_TYPE_ERR = 0;
    public static final short DISPATCH_REQUEST_ERR      = 1;
    
    @Constructor public void EventException(){
    	
    }
    
	@Constructor public void EventException(int errorCode){
		
	}
}
