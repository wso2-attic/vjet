/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.context.subctx;

import org.ebayopensource.dsf.common.context.ContextHelper;
import org.ebayopensource.dsf.common.context.DsfCtx;
import org.ebayopensource.dsf.common.context.ISubCtx;

public class BrowserCtxHelper extends ContextHelper
{
    public static void registerSubCtx(String ctxName, ISubCtx subCtx)
    {
        setSubCtx(DsfCtx.ctx(), ctxName, subCtx);
    }

    public static ISubCtx getSubCtx(String ctxName)
    {
        return (ISubCtx) getSubCtx(DsfCtx.ctx(), ctxName);
    }
}
