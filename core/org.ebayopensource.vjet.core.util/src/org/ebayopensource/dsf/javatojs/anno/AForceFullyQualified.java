/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.METHOD})
/**
 * Use this annotation to force all static references to be fully qualified
 * inside a static method body
 * Please note:
 * <li>It's not applied to method signature</li>
 * <li>It has no impact on imports/needs</li>
 * <li>It should only be used with STATIC method. Otherwise it's ignored
 */
public @interface AForceFullyQualified {

}
