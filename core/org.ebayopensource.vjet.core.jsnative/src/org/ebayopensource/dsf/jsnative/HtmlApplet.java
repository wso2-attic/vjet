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
import org.ebayopensource.dsf.jsnative.anno.JsMetatype;
import org.ebayopensource.dsf.jsnative.anno.Property;

/**
 * An embedded Java applet. See the APPLET element definition in HTML 4.01. 
 * This element is deprecated in HTML 4.01.
 * http://www.w3.org/TR/1999/REC-html401-19991224/struct/objects.html#edef-APPLET
 *
 */
@Alias("HTMLAppletElement")
@DOMSupport(DomLevel.ONE)
@JsMetatype
public interface HtmlApplet extends HtmlElement {
	/** "bottom" */
	@AJavaOnly @ARename(name="'bottom'")
	public static final String ALIGN_BOTTOM = "bottom" ;
	/** "middle" */
	@AJavaOnly @ARename(name="'middle'")
	public static final String ALIGN_MIDDLE = "middle" ;
	/** "top" */
	@AJavaOnly @ARename(name="'top'")
	public static final String ALIGN_TOP = "top" ;
	/** "left" */
	@AJavaOnly @ARename(name="'left'")
	public static final String ALIGN_LEFT = "left" ;
	/** "right" */
	@AJavaOnly @ARename(name="'right'")
	public static final String ALIGN_RIGHT = "right" ;
	
	@Property String getAlign();
	@Property void setAlign(String align);

	@Property String getAlt();
	@Property void setAlt(String alt);

	@Property String getArchive();
	@Property void setArchive(String archive);

	@Property String getCode();
	@Property void setCode(String code);

	@Property String getCodeBase();
	@Property void setCodeBase(String codeBase);

	@Property String getHeight();
	@Property void setHeight(String height);

	@Property int getHspace();
	@Property void setHspace(int hspace);

	@Property String getName();
	@Property void setName(String name);

	@Property String getObject();
	@Property void setObject(String object);

	@Property int getVspace();
	@Property void setVspace(int vspace);

	@Property String getWidth();
	@Property void setWidth(String width);

	

}
