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
import org.ebayopensource.dsf.jsnative.anno.Property;
import org.mozilla.mod.javascript.IWillBeScriptable;

/**
 * The TypeInfo interface represents a type referenced from Element or Attr nodes, 
 * specified in the schemas associated with the document. 
 * The type is a pair of a namespace URI and name properties, 
 * and depends on the document's schema. 
 *
 */
@DOMSupport(DomLevel.THREE)
@JsMetatype
public interface TypeInfo extends IWillBeScriptable {
	
	@DOMSupport(DomLevel.THREE) @BrowserSupport({BrowserType.UNDEFINED})
	@Property String getTypeName();

	@DOMSupport(DomLevel.THREE) @BrowserSupport({BrowserType.UNDEFINED})
	@Property String getTypeNamespace();

    // DerivationMethods
    public static final int DERIVATION_RESTRICTION    = 0x00000001;
    public static final int DERIVATION_EXTENSION      = 0x00000002;
    public static final int DERIVATION_UNION          = 0x00000004;
    public static final int DERIVATION_LIST           = 0x00000008;

    @DOMSupport(DomLevel.THREE) @BrowserSupport({BrowserType.UNDEFINED})
    @Function boolean isDerivedFrom(String typeNamespaceArg, 
                                 String typeNameArg, 
                                 int derivationMethod);


}
