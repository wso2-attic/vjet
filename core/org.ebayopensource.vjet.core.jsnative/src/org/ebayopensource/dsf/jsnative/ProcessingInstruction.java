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

/**
 * The ProcessingInstruction interface represents a "processing instruction", 
 * used in XML as a way to keep processor-specific information in the text of the document.
 *
 */
@DOMSupport(DomLevel.ONE)
@JsMetatype
public interface ProcessingInstruction extends Node {
	
	/**
	 * The target of this processing instruction. 
	 * XML defines this as being the first token following the markup that 
	 * begins the processing instruction.
	 * @return
	 */
	@DOMSupport(DomLevel.ONE)
	@BrowserSupport({BrowserType.FIREFOX_2P, BrowserType.OPERA_9P, BrowserType.SAFARI_3P})
	@Property String getTarget();

	/**
	 * The content of this processing instruction. 
	 * This is from the first non white space character after the target to 
	 * the character immediately preceding the ?>.
	 * @return
	 */
	@DOMSupport(DomLevel.ONE)
	@BrowserSupport({BrowserType.FIREFOX_2P, BrowserType.OPERA_9P, BrowserType.SAFARI_3P})
	@Property String getData();
	
	@DOMSupport(DomLevel.ONE)
	@BrowserSupport({BrowserType.FIREFOX_2P, BrowserType.OPERA_9P, BrowserType.SAFARI_3P})
	@Property void setData(String data);

}
