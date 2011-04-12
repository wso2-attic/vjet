/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.reserved;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.ebayopensource.dsf.jsnative.anno.JsVersion;

@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface ImplementedBy {
	JsVersion[] jsVersions() default {JsVersion.NONE};
}
