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
import org.ebayopensource.dsf.jsnative.anno.BrowserSupport;
import org.ebayopensource.dsf.jsnative.anno.BrowserType;
import org.ebayopensource.dsf.jsnative.anno.DOMSupport;
import org.ebayopensource.dsf.jsnative.anno.DomLevel;
import org.ebayopensource.dsf.jsnative.anno.JsMetatype;
import org.ebayopensource.dsf.jsnative.anno.Property;

/**
 * http://www.w3.org/TR/REC-html40/struct/links.html#edef-LINK
 *
 */
@Alias("HTMLLinkElement")
@DOMSupport(DomLevel.ONE)
@JsMetatype
public interface HtmlLink extends HtmlElement {
	// For target.  Target can accept a user-defined value as well.
	/** "_blank" */
	@AJavaOnly @ARename(name="'_blank'")
	String TARGET_BLANK = "_blank" ;
	/** "_self" */
	@AJavaOnly @ARename(name="'_self'")
	String TARGET_SELF = "_self" ;	
	/** "_parent" */
	@AJavaOnly @ARename(name="'_parent'")
	String TARGET_PARENT = "_parent" ;
	/** "_top" */
	@AJavaOnly @ARename(name="'_top'")
	String TARGET_TOP = "_top" ;

	// For rel.
	/** "Alternate" */
	@AJavaOnly @ARename(name="'Alternate'")
	String REL_ALTERNATE = "Alternate" ;
	/** "Stylesheet" */
	@AJavaOnly @ARename(name="'Stylesheet'")
	String REL_STYLESHEET = "Stylesheet" ;
	/** "Start" */
	@AJavaOnly @ARename(name="'Start'")
	String REL_START = "Start" ;
	/** "Next" */
	@AJavaOnly @ARename(name="'Next'")
	String REL_NEXT = "Next" ;
	/** "Prev" */
	@AJavaOnly @ARename(name="'Prev'")
	String REL_PREV = "Prev" ;
	/** "Contents" */
	@AJavaOnly @ARename(name="'Contents'")
	String REL_CONTENTS = "Contents" ;
	/** "Index" */
	@AJavaOnly @ARename(name="'Index'")
	String REL_INDEX = "Index" ;
	/** "Glossary" */
	@AJavaOnly @ARename(name="'Glossary'")
	String REL_GLOSSARY = "Glossary" ;
	/** "Copyright" */
	@AJavaOnly @ARename(name="'Copyright'")
	String REL_COPYRIGHT = "Copyright" ;
	/** "Chapter" */
	@AJavaOnly @ARename(name="'Chapter'")
	String REL_CHAPTER = "Chapter" ;
	/** "Section" */
	@AJavaOnly @ARename(name="'Section'")
	String REL_SECTION = "Section" ;
	/** "Subsection" */
	@AJavaOnly @ARename(name="'Subsection'")
	String REL_SUBSECTION = "Subsection" ;
	/** "Appendix" */
	@AJavaOnly @ARename(name="'Appendix'")
	String REL_APPENDIX = "Appendix" ;
	/** "Help" */
	@AJavaOnly @ARename(name="'Help'")
	String REL_HELP = "Help" ;
	/** "Bookmark" */
	@AJavaOnly @ARename(name="'Bookmark'")
	String REL_BOOKMARK = "Bookmark" ;
	
	// For media.
	/** "all" */
	@AJavaOnly @ARename(name="'all'")
	String MEDIA_ALL = "all" ;
	/** "aural" */
	@AJavaOnly @ARename(name="'aural'")
	String MEDIA_AURAL = "aural" ;
	/** "braille" */
	@AJavaOnly @ARename(name="'braille'")
	String MEDIA_BRAILLE = "braille" ;
	/** "handheld" */
	@AJavaOnly @ARename(name="'handheld'")
	String MEDIA_HANDHELD = "handheld" ;
	/** "print" */
	@AJavaOnly @ARename(name="'print'")
	String MEDIA_PRINT = "print" ;
	/** "projection" */
	@AJavaOnly @ARename(name="'projection'")
	String MEDIA_PROJECTION = "projection" ;
	/** "screen" */
	@AJavaOnly @ARename(name="'screen'")
	String MEDIA_SCREEN = "screen" ;
	/** "tty" */
	@AJavaOnly @ARename(name="'tty'")
	String MEDIA_TTY = "tty" ;
	/** "tv" */
	@AJavaOnly @ARename(name="'tv'")
	String MEDIA_TV = "tv" ;
	
	@Property boolean getDisabled();
	@Property void setDisabled(boolean disabled);

	@Property String getCharset();
	@Property void setCharset(String charset);

	@Property String getHref();
	@Property void setHref(String href);

	@Property String getHreflang();
	@Property void setHreflang(String hreflang);

	@Property String getMedia();
	@Property void setMedia(String media);

	@Property String getRel();
	@Property void setRel(String rel);

	@Property String getRev();
	@Property void setRev(String rev);

	@Property String getTarget();
	@Property void setTarget(String target);

	@Property String getType();
	@Property void setType(String type);
	
	/**
	 * Retrieves an interface pointer that provides access to the style sheet 
	 * object's properties and methods
	 */
	@BrowserSupport({BrowserType.IE_6P})
	@Property CssStyleSheet getStyleSheet();
	
	
	/**
	 * Retrieves an interface pointer that provides access to the style sheet 
	 * object's properties and methods
	 */
	@BrowserSupport({BrowserType.IE_6P})
	@Property HtmlElementStyle getSheet();
	
	/**
	 * Returns the onload event handler code on the current element. 
	 * @see http://www.w3schools.com/jsref/jsref_onload.asp
	 */
	@DOMSupport(DomLevel.ZERO)
	@Property(name="onload")
	Object getOnLoad();
	
	/**
	 * Sets the onload event handler code on the current element. 
	 * @param functionRef
	 * @see http://www.w3schools.com/jsref/jsref_onload.asp
	 */
	@DOMSupport(DomLevel.ZERO)
	@Property(name="onload")
	void setOnLoad(Object functionRef);
	
	/**
	 * Returns the onunload event handler code on the current element. 
	 * @see http://www.w3schools.com/jsref/jsref_onunload.asp
	 */
	@DOMSupport(DomLevel.ZERO)
	@Property(name="onunload")
	Object getOnUnload();
	
	/**
	 * Sets the onunload event handler code on the current element. 
	 * @param functionRef
	 * @see http://www.w3schools.com/jsref/jsref_onunload.asp
	 */
	@DOMSupport(DomLevel.ZERO)
	@Property(name="onunload")
	void setOnUnload(Object functionRef);

}
