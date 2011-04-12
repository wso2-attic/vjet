/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.context.subctx.browser;

public class Capability
{
    private String _literalStr;

    private Capability(String literalStr)
    {
        _literalStr = literalStr;
    }

    public static final Capability NOT_CAPABLE     = new Capability("Not Capable");
    public static final Capability IS_CAPABLE      = new Capability("Is Capable");
    public static final Capability UNKNOWN_FEATURE = new Capability("Unknown");

    @Override
    public String toString()
    {
        return _literalStr;
    }
}
