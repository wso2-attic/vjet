/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.context.subctx.browser.internal;

import java.util.HashMap;
import java.util.Map;

import org.ebayopensource.dsf.common.context.subctx.browser.BrowserCapabilityRegistry;
import org.ebayopensource.dsf.common.context.subctx.browser.BrowserEnum;
import org.ebayopensource.dsf.common.context.subctx.browser.Capability;
import org.ebayopensource.dsf.common.context.subctx.browser.IBrowser;
import org.ebayopensource.dsf.common.context.subctx.browser.OsEnum;

public class Browser implements IBrowser
{
    BrowserSniffingUtils            _sniffingUtils;
    private Map<String, Capability> _featureCapabilities;

    public Browser(String userAgentName)
    {
        _sniffingUtils = new BrowserSniffingUtils(userAgentName);
        _featureCapabilities = new HashMap<String, Capability>();
    }

    public String getBrowserName()
    {
        return _sniffingUtils.getBrowserVendor();
    }

    public String getBrowserMajorVersion()
    {
        return _sniffingUtils.getBrowserMajorVersion();
    }

    public String getBrowserMinorVersion()
    {
        return _sniffingUtils.getBrowserMinorVersion();
    }

    public String getOSName()
    {
        return _sniffingUtils.getBrowserOS();
    }

    public boolean isOsType(OsEnum os)
    {
        return _sniffingUtils.m_osNameMap.containsKey(os);
    }

    public Capability supportFeature(String feature)
    {
        if (feature == null || feature.trim().length() == 0)
        {
            return Capability.UNKNOWN_FEATURE;
        }
        Capability c = _featureCapabilities.get(feature);
        if (c != null)
        {
            return c;
        }
        c = BrowserCapabilityRegistry.getInstance().capableOf(feature, this);
        
        // We cache the capability here to save time 'coz in a specific thread, the browser capabilities wont change
        _featureCapabilities.put(feature, c);
        return c;
    }

    public boolean isBrowserType(BrowserEnum browser)
    {
        return _sniffingUtils.m_browserMap.containsKey(browser);
    }

    public boolean isMozilla()
    {
         return _sniffingUtils.isMozillaBrowser();
    }
}
