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
package org.ebayopensource.dsf.common.context.subctx;

import org.ebayopensource.dsf.common.context.BaseSubCtx;
import org.ebayopensource.dsf.common.context.subctx.browser.IBrowser;
import org.ebayopensource.dsf.common.context.subctx.browser.internal.Browser;

/**
 * 
 * 
 */
public class BrowserCtx extends BaseSubCtx
{
    public static final String BROWSER_SUB_CTX = "BROWSER_SUB_CTX";
    private String             m_userAgent;
    private IBrowser           m_browser;

    public BrowserCtx(String userAgent)
    {
        m_userAgent = userAgent;
        m_browser = new Browser(userAgent);
    }

    public IBrowser getBrowser()
    {
        return m_browser;
    }

    public String getUserAgent()
    {
        return m_userAgent;
    }

    public static BrowserCtx getBrowserCtx()
    {
        return (BrowserCtx) BrowserCtxHelper.getSubCtx(BROWSER_SUB_CTX);
    }
}
