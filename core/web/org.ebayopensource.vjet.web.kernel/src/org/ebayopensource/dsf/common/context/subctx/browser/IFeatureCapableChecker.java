/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.context.subctx.browser;

/**
 * Checker to check whether the given browser has the specific capability (feature), for example, subclass
 * <code>PngAlphaFeatureCapableChecker</code> might be employed to check whether the given browser can display png
 * image with alpha transparency or not.<br>
 * Consumers may implment this interface to check various capabilities of browser and register the checker on
 * <code>BrowserCapabilityRegistry</code> thus can be reused by other consumers.<br>
 * 
 * @see BrowserCapabilityRegistry
 * @see IBrowser
 */
public interface IFeatureCapableChecker
{
    /**
     * Whether the feature cared by this checker is supported by the given browser or not
     * 
     * @param browser
     * @return
     */
    public boolean supportFeature(IBrowser browser);
}
