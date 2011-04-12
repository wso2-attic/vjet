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
import org.ebayopensource.dsf.jsnative.anno.JsMetatype;
import org.ebayopensource.dsf.jsnative.anno.JsSupport;
import org.ebayopensource.dsf.jsnative.anno.JsVersion;
import org.ebayopensource.dsf.jsnative.anno.Property;
import org.mozilla.mod.javascript.IWillBeScriptable;

/**
 * JavaScript object representing window's screen object.
 */
@JsSupport( JsVersion.MOZILLA_ONE_DOT_TWO)
@JsMetatype
public interface Screen extends IWillBeScriptable {
	
	// Properties
	
	/**
	 * Returns the height of the display screen (excluding the Windows Taskbar)
	 * @return
	 */
	@Property int getAvailHeight();
	
	/**
	 * Returns the width of the display screen (excluding the Windows Taskbar)
	 * @return
	 */
	@Property int getAvailWidth();
	
	/**
	 * Returns the bit depth of the color palette on the destination device or buffer
	 * @return
	 */
	@Property int getColorDepth();
	
	/**
	 * The height of the display screen
	 * @return
	 */
	@Property int getHeight();
	
	/**
	 * Set height of the display screen
	 * @param height
	 */
	@Property void setHeight(int height);

	
	/**
	 * Returns the color resolution (in bits per pixel) of the display screen
	 * @return
	 */
	@BrowserSupport({BrowserType.FIREFOX_1P, BrowserType.OPERA_7P, BrowserType.SAFARI_3P})
	@Property int getPixelDepth();
	
	/**
	 * Returns width of the display screen
	 * @return
	 */
	@Property int getWidth();
	
	/**
	 * Set the width of the display screen
	 * @param width
	 */
	@Property void setWidth(int width);

	
	/**
	 * Returns the bit depth of the color palette in the off-screen bitmap buffer
	 * @return
	 */
	@BrowserSupport({BrowserType.IE_6P})
	@Property int getBufferDepth();
	
	/**
	 * Returns the number of horizontal dots per inch of the display screen
	 * @return
	 */
	@Property int getDeviceXDPI();
	
	/**
	 * Returns the number of vertical dots per inch of the display screen
	 * @return
	 */
	@Property int getDeviceYDPI();
	
	/**
	 * Returns whether the user has enabled font smoothing in the display control panel
	 * @return
	 */
	@Property boolean getFontSmoothingEnabled();
	
	/**
	 * Returns the normal number of horizontal dots per inch of the display screen
	 * @return
	 */
	@Property int getLogicalXDPI();
	
	/**
	 * Returns the normal number of vertical dots per inch of the display screen
	 * @return
	 */
	@Property int getLogicalYDPI();
	
	/**
	 * Returns the update interval for the screen
	 * @return
	 */
	@Property int getUpdateInterval();
	
	/**
	 * Sets the bit depth of the color palette in the off-screen bitmap buffer
	 * @param depth
	 */
	@Property void setBufferDepth(int depth);
	
	/**
	 * Sets the update interval for the screen
	 * @param interval
	 */
	@Property void setUpdateInterval(int interval);

}
