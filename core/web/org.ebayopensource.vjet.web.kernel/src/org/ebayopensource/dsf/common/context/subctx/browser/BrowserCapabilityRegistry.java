/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.context.subctx.browser;

import java.util.HashMap;
import java.util.Map;

public class BrowserCapabilityRegistry
{
    private static BrowserCapabilityRegistry    _instance;
    private Map<String, IFeatureCapableChecker> _capabilities;

    private BrowserCapabilityRegistry()
    {
        _capabilities = new HashMap<String, IFeatureCapableChecker>();
    }

    public synchronized static BrowserCapabilityRegistry getInstance()
    {
        if (_instance == null)
        {
            _instance = new BrowserCapabilityRegistry();
        }
        return _instance;
    }

    public void register(String featureId, IFeatureCapableChecker capable)
    {
        if (featureId == null || featureId.trim().length() == 0 || capable == null)
        {
            return;
        }
        if (_capabilities.containsKey(featureId))
        {
            // Already registered with same name
            return;
        }
        _capabilities.put(featureId, capable);
    }

    public String[] getKnownFeatures()
    {
        return (String[]) _capabilities.keySet().toArray(new String[0]);
    }

    public Capability capableOf(String featureId, IBrowser browser)
    {
        if (featureId == null || featureId.trim().length() == 0)
        {
            return Capability.UNKNOWN_FEATURE;
        }
        IFeatureCapableChecker capable = _capabilities.get(featureId);
        if (capable == null)
        {
            return Capability.UNKNOWN_FEATURE;
        }
        boolean capableOf = true;
        try
        {
            // Exception may occur from extension code
            capableOf = capable.supportFeature(browser);
        }
        catch (Exception e)
        {
            return Capability.UNKNOWN_FEATURE;
        }

        if (capableOf)
        {
            return Capability.IS_CAPABLE;
        }
        else
        {
            return Capability.NOT_CAPABLE;
        }
    }
}
