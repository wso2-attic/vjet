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
import org.ebayopensource.dsf.jsnative.anno.JsMetatype;
import org.ebayopensource.dsf.jsnative.anno.Property;
import org.mozilla.mod.javascript.IWillBeScriptable;

/**
 * DOMError is an interface that describes an error. 
 *
 */
@DOMSupport(DomLevel.THREE)
@JsMetatype
public interface DOMError extends IWillBeScriptable {
	
	//	 ErrorSeverity
    public static final short SEVERITY_WARNING          = 1;
    public static final short SEVERITY_ERROR            = 2;
    public static final short SEVERITY_FATAL_ERROR      = 3;

    /**
     * The severity of the error, 
     * either SEVERITY_WARNING, SEVERITY_ERROR, or SEVERITY_FATAL_ERROR.
     * @return
     */
    @DOMSupport(DomLevel.THREE) @BrowserSupport({BrowserType.UNDEFINED})
    @Property short getSeverity();

    /**
     * An implementation specific string describing the error that occurred.
     * @return
     */
    @DOMSupport(DomLevel.THREE) @BrowserSupport({BrowserType.UNDEFINED})
    @Property String getMessage();

    /**
     * A String indicating which related data is expected in relatedData. 
     * Users should refer to the specification of the error in order to find 
     * its  type and relatedData  definitions if any. 
     * @return
     */
    @DOMSupport(DomLevel.THREE) @BrowserSupport({BrowserType.UNDEFINED})
    @Property String getType();

    /**
     * The related platform dependent exception if any.
     * @return
     */
    @DOMSupport(DomLevel.THREE) @BrowserSupport({BrowserType.UNDEFINED})
    @Property Object getRelatedException();

    /**
     * The related DOMError.type dependent data if any. 
     * @return
     */
    @DOMSupport(DomLevel.THREE) @BrowserSupport({BrowserType.UNDEFINED})
    @Property Object getRelatedData();

    /**
     * The location of the error.
     * @return
     */
    @DOMSupport(DomLevel.THREE) @BrowserSupport({BrowserType.NONE})
    @Property DOMLocator getLocation();


}
