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
 * http://www.w3.org/TR/REC-html40/struct/links.html#edef-BASE
 */
@Alias("HTMLBaseElement")
@DOMSupport(DomLevel.ONE)
@JsMetatype
public interface HtmlBase extends HtmlElement {
	/** "_blank" */
	@AJavaOnly @ARename(name="'_blank'")
	public static final String TARGET_BLANK = "_blank" ;
	/** "_self" */
	@AJavaOnly @ARename(name="'_self'")
	public static final String TARGET_SELF = "_self" ;
	/** "_parent" */
	@AJavaOnly @ARename(name="'_parent'")
	public static final String TARGET_PARENT = "_parent" ;
	/** "_top" */
	@AJavaOnly @ARename(name="'_top'")
	public static final String TARGET_TOP = "_top" ;
	
	@Property String getHref();
	@Property void setHref(String href);

	@Property String getTarget();
	@Property void setTarget(String target);


}
