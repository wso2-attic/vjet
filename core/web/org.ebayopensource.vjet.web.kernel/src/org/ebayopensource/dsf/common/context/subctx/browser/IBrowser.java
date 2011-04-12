/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/**
 * 
 */
package org.ebayopensource.dsf.common.context.subctx.browser;



/**
 * Represents a browser. An instance of this interface can be obtained by <code>BrowserCtx</code> in runtime, and
 * which is thread-local<br>
 * Consumers can get various information about the current browser (which is being used by end user) and know if the
 * given feature identified by the feature id is supported by the current browser.<br>
 * 
 * 
 */
public interface IBrowser
{
    public String getBrowserName();

    public String getBrowserMajorVersion();
    
    public String getBrowserMinorVersion();

    public String getOSName();
    
    public boolean isMozilla(); 

    public boolean isOsType(OsEnum os);
    
    public boolean isBrowserType(BrowserEnum browser);

    /**
     * Checks whether the given feature is supported by this browser
     * @param feature
     * @return
     */
    public Capability supportFeature(String feature);
}
