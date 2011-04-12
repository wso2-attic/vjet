/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsnative;

import org.ebayopensource.dsf.jsnative.anno.Alias;
import org.ebayopensource.dsf.jsnative.anno.DOMSupport;
import org.ebayopensource.dsf.jsnative.anno.DomLevel;
import org.ebayopensource.dsf.jsnative.anno.JsMetatype;
import org.ebayopensource.dsf.jsnative.anno.Property;

/**
 * 
 * http://developer.mozilla.org/en/docs/HTML:Element:bgsound
 *
 */
@Alias("HTMLBgsoundElement")
@DOMSupport(DomLevel.ONE)
@JsMetatype
public interface HtmlBgSound extends HtmlElement {
	
	/**
	 * This attribute defines a number between -10,000 and +10,000 
	 * that determines how the volume will be divided between the speakers
	 */
	@Property String getBalance();
	@Property void setBalance(String balance);
	
	/**
	 * This attribute specifies the URL of the sound file to be played, 
	 * which must be one of the following types: .wav, .au, or .mid. 
	 */
	@Property String getSrc();
	@Property void setSrc(String src);

	/**
	 * This attribute indicates the number of times a sound is to be played 
	 * and either has a numeric value or the keyword infinite. 
	 */
	@Property String getLoop();
	@Property void setLoop(String loop);

	/**
	 * This attribute defines a number between -10,000 and 0 that determines 
	 * the loudness of a page's background sound. 
	 */
	@Property String getVolume();
	@Property void setVolume(String volume);
}
