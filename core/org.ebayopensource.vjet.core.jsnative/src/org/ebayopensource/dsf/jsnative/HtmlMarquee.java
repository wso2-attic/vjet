/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsnative;

import org.ebayopensource.dsf.javatojs.anno.AJavaOnly;
import org.ebayopensource.dsf.javatojs.anno.ARename;
import org.ebayopensource.dsf.jsnative.anno.Alias;
import org.ebayopensource.dsf.jsnative.anno.DOMSupport;
import org.ebayopensource.dsf.jsnative.anno.DomLevel;
import org.ebayopensource.dsf.jsnative.anno.Function;
import org.ebayopensource.dsf.jsnative.anno.JsMetatype;
import org.ebayopensource.dsf.jsnative.anno.Property;

/**
 * http://developer.mozilla.org/en/docs/HTML:Element:marquee
 *
 */
@Alias("HTMLMarqueeElement")
@DOMSupport(DomLevel.ONE)
@JsMetatype
public interface HtmlMarquee extends HtmlElement {
	@AJavaOnly @ARename(name="'scroll'")
	String BEHAVIOR_SCROLL = "scroll" ; 
	@AJavaOnly @ARename(name="'alternate'")
	String BEHAVIOR_ALTERNATE = "alternate" ; 
	@AJavaOnly @ARename(name="'slide'")
	String BEHAVIOR_SLIDE = "slide" ; 
	
	@AJavaOnly @ARename(name="'text'")
	String DATAFORMATAS_TEXT = "text" ;
	@AJavaOnly @ARename(name="'html'")
	String DATAFORMATAS_HTML = "html" ;  
	
	@AJavaOnly @ARename(name="'left'")
	String DIRECTION_LEFT = "left" ; 
	@AJavaOnly @ARename(name="'right'")
	String DIRECTION_RIGHT = "right" ; 
	@AJavaOnly @ARename(name="'down'")
	String DIRECTION_DOWN = "down" ; 
	@AJavaOnly @ARename(name="'up'")
	String DIRECTION_UP = "up" ; 
	
	@AJavaOnly @ARename(name="'Jscript'")
	String LANGUAGE_JSCRIPT = "Jscript" ;
	@AJavaOnly @ARename(name="'javascript'")
	String LANGUAGE_JAVASCRIPT = "javascript" ; 
	@AJavaOnly @ARename(name="'vbs'")
	String LANGUAGE_VBS = "vbs" ;
	@AJavaOnly @ARename(name="'vbscript'")
	String LANGUAGE_VBSCRIPT = "vbscript" ; 
	@AJavaOnly @ARename(name="'xml'")
	String LANGUAGE_XML = "XML" ;
	
	@AJavaOnly @ARename(name="'off'")
	String UNSELECTABLE_OFF = "off" ; 
	@AJavaOnly @ARename(name="'on'")
	String UNSELECTABLE_ON = "on" ; 
	
	/**
	 * Sets how the text is scrolled within the marquee. 
	 * Possible values are scroll, slide and alternate. 
	 * If no value is specified, the default value is scroll.
	 */
	@Property String getBehavior();
	@Property void setBehavior(String behavior);
	
	/**
	 * Sets the background color through color name or hexadecimal value. 
	 */
	@Property String getBgcolor();
	@Property void setBgcolor(String bgcolor);
	
	/**
	 * Sets the direction of the scrolling within the marquee. 
	 * Possible values are left, right, up and down. 
	 * If no value is specified, the default value is left
	 */
	@Property String getDirection();
	@Property void setDirection(String direction);
	
	/**
	 * Sets the height in pixels or percentage value. 
	 */
	@Property String getHeight();
	@Property void setHeight(String height);
	
	/**
	 * Sets the horizontal margin 
	 */
	@Property String getHspace();
	@Property void setHspace(String hspace);
	
	/**
	 * Sets the number of times the marquee will scroll. 
	 */
	@Property String getLoop();
	@Property void setLoop(String loop);
	
	/**
	 * Sets the amount of scrolling at each interval in pixels. The default value is 6.
	 */
	@Property String getScrollamount();
	@Property void setScrollamount(String scrollamount);
	
	/**
	 * Sets the interval between each scroll movement in milliseconds.
	 */
	@Property String getScrolldelay();
	@Property void setScrolldelay(String scrolldelay); 

	/**
	 * Determines whether scrolldelay values lower than 60 are ignored or not. 
	 * Possible values are true or false. The default value is false 
	 */
	@Property String getTruespeed();
	@Property void setTruespeed(String truespeed);
	
	/**
	 * Sets the vertical margin in pixels or percentage value. 
	 */
	@Property String getVspace();
	@Property void setVspace(String vspace);
	
	/**
	 * Sets the width in pixels or percentage value.
	 */
	@Property String getWidth();
	@Property void setWidth(String width);
	
	/**
	 * Starts scrolling of the marquee
	 */
	@Function void start();
	/**
	 * Stops scrolling of the marquee
	 */
	@Function void stop();

}
