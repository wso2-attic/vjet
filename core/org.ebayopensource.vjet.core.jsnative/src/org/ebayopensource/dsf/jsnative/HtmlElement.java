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
import org.ebayopensource.dsf.jsnative.anno.Alias;
import org.ebayopensource.dsf.jsnative.anno.BrowserSupport;
import org.ebayopensource.dsf.jsnative.anno.BrowserType;
import org.ebayopensource.dsf.jsnative.anno.DOMSupport;
import org.ebayopensource.dsf.jsnative.anno.DomLevel;
import org.ebayopensource.dsf.jsnative.anno.Dynamic;
import org.ebayopensource.dsf.jsnative.anno.Function;
import org.ebayopensource.dsf.jsnative.anno.JsMetatype;
import org.ebayopensource.dsf.jsnative.anno.JstExclude;
import org.ebayopensource.dsf.jsnative.anno.Property;
import org.ebayopensource.dsf.jsnative.events.EventTarget;

/**
 * All HTML element interfaces derive from this class. Elements that only expose the HTML core attributes 
 * are represented by the base <code>HtmlElement</code> interface. These elements are as follows:
 * <ul>
 * <li>special: SUB, SUP, SPAN, BDO</li> 
 * <li>font: TT, I, B, U, S, STRIKE, BIG, SMALL </li>
 * <li>phrase: EM, STRONG, DFN, CODE, SAMP, KBD, VAR, CITE, ACRONYM, ABBR </li>
 * <li>list: DD, DT </li>
 * <li>NOFRAMES, NOSCRIPT </li>
 * <li>ADDRESS, CENTER </li>
 * </ul>
 *
 */
@Alias("HTMLElement")
@DOMSupport(DomLevel.ONE)
@Dynamic
@JsMetatype
public interface HtmlElement extends Element, EventTarget, ElementView {
	
    @BrowserSupport({BrowserType.NONE})
    @AJavaOnly
    @JstExclude
    @Function Node addBr();
    
    
    /**
     * Set's focus to the HTML element. Not all elements implement this method so there may be no behavior in browser. 
     */
    @Function void focus();
    
    /**
     * Removes focus from the HTML element. Not all elements implement this method so there may be no behavior in browser. 
     */
    @Function void blur();
    
    
	/**
	 * Returns the element's identifier
	 * @return
	 */
	@Property String getId();
	
    /**
     * Sets the element's identifier
     * @param id
     */
	@Property void setId(String id);

    /**
     * Returns the element's advisory title
     * @return
     */
	@Property String getTitle();
    
    /**
     * Set the element's advisory title
     * @param title
     */
	@Property void setTitle(String title);

    /**
     * Returns the language code defined in RFC 1766
     * @return
     */
	@Property String getLang();
    
    /**
     * Sets the language code defined in RFC 1766
     * @param lang
     */
	@Property void setLang(String lang);

    /**
     * Returns element's identifier
     * @return
     */
	@Property String getDir();
    
    /**
     * Sets element's identifier
     * @param dir
     */
	@Property void setDir(String dir);

    /**
     * Returns class attribute of the element
     */
	@Property String getClassName();
    
    /**
     * Set class attribute of the element
     * @param className
     */
	@Property void setClassName(String className);
	
	/**
	 * Gets the markup and content of the element
	 * @return
	 */
	@Property String getInnerHTML();
	
	/**
	 * Sets the markup and content of the element
	 * @param html
	 */
	@Property void setInnerHTML(String html);
	
	/**
	 * Get the style attributes for an element
	 * 
	 * @return HtmlElementStyle
	 */
	@Property HtmlElementStyle getStyle();
	
	/**
	 * Set the style attributes for an element
	 * @param style HtmlElementStyle
	 */
	@Property void setStyle(final HtmlElementStyle style);
	
	/**
	 * Get the style attributes for an element
	 * 
	 * @return HtmlElementStyle
	 */
	@DOMSupport(DomLevel.TWO)
	@BrowserSupport({BrowserType.IE_6P})
	@Property HtmlElementStyle getCurrentStyle();
	
	@DOMSupport(DomLevel.TWO)
	@BrowserSupport({BrowserType.IE_6P})
	@Property HtmlElementStyle getRuntimeStyle();
	
	// The following properties correspond to the HTML 'on' event attributes.
	
//	/**
//	 * Returns the oncopy event handler code on the current element. 
//	 */
//	@DOMSupport(DomLevel.ZERO)
//	@BrowserSupport({BrowserType.FIREFOX_3P})
//	@Property Object getOncopy();
//	
//	/**
//	 * Sets the oncopy event handler code on the current element. 
//	 * @param functionRef
//	 */
//	@DOMSupport(DomLevel.ZERO)
//	@BrowserSupport({BrowserType.FIREFOX_3P})
//	@Property void setOncopy(Object functionRef);
//	
//	/**
//	 * Returns the oncut event handler code on the current element. 
//	 */
//	@DOMSupport(DomLevel.ZERO)
//	@BrowserSupport({BrowserType.FIREFOX_3P})
//	@Property Object getOncut();
//	
//	/**
//	 * Sets the oncut event handler code on the current element. 
//	 * @param functionRef
//	 */
//	@DOMSupport(DomLevel.ZERO)
//	@BrowserSupport({BrowserType.FIREFOX_3P})
//	@Property void setOncut(Object functionRef);
//	
//	/**
//	 * Returns the onpaste event handler code on the current element. 
//	 */
//	@DOMSupport(DomLevel.ZERO)
//	@BrowserSupport({BrowserType.FIREFOX_3P})
//	@Property Object getOnpaste();
//	
//	/**
//	 * Sets the onpaste event handler code on the current element. 
//	 * @param functionRef
//	 */
//	@DOMSupport(DomLevel.ZERO)
//	@BrowserSupport({BrowserType.FIREFOX_3P})
//	@Property void setOnpaste(Object functionRef);
//	
//	
//	/**
//	 * Returns the onbeforeunload event handler code on the current element. 
//	 */
//	@DOMSupport(DomLevel.ZERO)
//	@Property Object getOnbeforeunload();
//	
//	/**
//	 * Sets the onbeforeunload event handler code on the current element.
//	 * @param functionRef
//	 */
//	@DOMSupport(DomLevel.ZERO)
//	@Property void setOnbeforeunload(Object functionRef);
	
//	/**
//	 * Returns the oncontextmenu event handler code on the current element. 
//	 */
//	@DOMSupport(DomLevel.ZERO)
//	@Property Object getOncontextmenu();
//	
//	/**
//	 * Sets the oncontextmenu event handler code on the current element. 
//	 * @param functionRef
//	 */
//	@DOMSupport(DomLevel.ZERO)
//	@Property void setOncontextmenu(Object functionRef);
//	
	
	/**
	 * Returns the onreadystatechange event handler code on the current element. 
	 */
	@DOMSupport(DomLevel.ZERO)
	@BrowserSupport({BrowserType.IE_6P} )
	@Property(name="onreadystatechange")
	Object getOnReadyStateChange();
	
	/**
	 * Sets the onreadystatechange event handler code on the current element. 
	 * @param functionRef
	 */
	@DOMSupport(DomLevel.ZERO)
	@BrowserSupport({BrowserType.IE_6P} )
	@Property(name="onreadystatechange")
	void setOnReadyStateChange(Object functionRef);
	
	/**
	 * Returns the onscroll event handler code on the current element. 
	 * @see http://msdn.microsoft.com/en-us/library/ms536966(VS.85).aspx
	 * @see https://developer.mozilla.org/En/DOM:element.onscroll
	 */
	@DOMSupport(DomLevel.ZERO)
	@BrowserSupport({BrowserType.IE_6P, BrowserType.FIREFOX_1P})
	@Property(name="onscroll")
	Object getOnScroll();
	
	/**
	 * Sets the onscroll event handler code on the current element. 
	 * @param functionRef
	 * @see http://msdn.microsoft.com/en-us/library/ms536966(VS.85).aspx
	 * @see https://developer.mozilla.org/En/DOM:element.onscroll
	 */
	@DOMSupport(DomLevel.ZERO)
	@BrowserSupport({BrowserType.IE_6P, BrowserType.FIREFOX_1P})
	@Property(name="onscroll")
	void setOnScroll(Object functionRef);
	
}