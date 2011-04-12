/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsnative.anno;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;



@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface SupportedBy {
	BrowserType[] browsers() default {BrowserType.IE_6P, BrowserType.FIREFOX_1P, BrowserType.OPERA_7P, BrowserType.SAFARI_3P};
	DomLevel domLevel() default DomLevel.UNDEFINED;
	JsVersion[] jsVersions() default {JsVersion.UNDEFINED};
}
