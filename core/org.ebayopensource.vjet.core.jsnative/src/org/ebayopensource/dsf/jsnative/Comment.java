/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsnative;

import org.ebayopensource.dsf.jsnative.anno.DOMSupport;
import org.ebayopensource.dsf.jsnative.anno.DomLevel;
import org.ebayopensource.dsf.jsnative.anno.JsMetatype;

/**
 * This interface inherits from CharacterData and represents the content of a comment, i.e., 
 * all the characters between the starting '<!--' and ending '-->'. 
 * Note that this is the definition of a comment in XML, and, in practice, HTML, 
 * although some HTML tools may implement the full SGML comment structure. 
 *
 */
@DOMSupport(DomLevel.ONE)
@JsMetatype
public interface Comment extends CharacterData {

}
