/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/* 
 * $Id: VjetVReporter.java, Dec 21, 2009, 12:35:16 AM, liama. Exp$
 *
 * Copyright (c) 2006-2009 Ebay Technologies. All Rights Reserved.
 * This software program and documentation are copyrighted by Ebay 
 * Technologies.
 */
package org.ebayopensource.dsf.jstojava.cml.vjetv.reporter.impl;

import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoSemanticProblem;

/**
 * Class/Interface description
 * 
 * @author <a href="mailto:liama@ebay.com">liama</a>
 * @since JDK 1.5
 */
public class VjetVReporter extends BaseReporter {
    /*
     * (non-Javadoc)
     * 
     * @see org.ebayopensource.dsf.jstojava.cml.vjetv.reporter.impl.BaseReporter#printProblem(java.lang.StringBuffer,
     *      org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoSemanticProblem,
     *      java.lang.String)
     */
    @Override
    protected void printProblem(StringBuffer message,
            VjoSemanticProblem vjoSemanticProblem, String sources) {
        message.append("Actual problem :" + vjoSemanticProblem + "\n");
        message.append("=====================================\n");
    }
}
