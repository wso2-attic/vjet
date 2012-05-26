/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/* 
 * $Id: BuildReporter.java, Jan 7, 2010, 11:24:42 PM, liama. Exp$
 *
 * Copyright (c) 2006-2009 Ebay Technologies. All Rights Reserved.
 * This software program and documentation are copyrighted by Ebay 
 * Technologies.
 */
package org.ebayopensource.dsf.jstojava.cml.vjetv.reporter.impl;

import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoSemanticProblem;
import org.ebayopensource.dsf.jstojava.cml.vjetv.util.FileOperator;

/**
 * This reporter used by build system.
 * 
 * @author <a href="mailto:liama@ebay.com">liama</a>
 * @since JDK 1.5
 */
public class BuildReporter extends BaseReporter {

    /*
     * (non-Javadoc)
     * 
     * @see org.ebayopensource.dsf.jstojava.cml.vjetv.reporter.impl.BaseReporter#printProblem(java.lang.StringBuffer,
     *      org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoSemanticProblem)
     */
    @Override
    protected void printProblem(StringBuffer message,
            VjoSemanticProblem vjoSemanticProblem, String sources) {
        StringBuffer sb = new StringBuffer(vjoSemanticProblem.type() + ":Line:"
                + vjoSemanticProblem.getSourceLineNumber() + ": "
                + vjoSemanticProblem.getMessage() + "; Code: ");
        message.append(sb.toString());
        final int point = sb.toString().length();
        int start = vjoSemanticProblem.getSourceStart();
        int end = vjoSemanticProblem.getSourceEnd();
        int newStart = FileOperator.getNewStringPosition(sources, start);
        String line = FileOperator.getSourceLineFromFile(sources, start,
                end + 1);
        String trimLine = line.trim();
        message.append(trimLine + "\n");
        for (int i = 0; i < point + start - newStart - (line.length() - trimLine.length()) + 1; i++) {
            message.append(" ");
        }
        message.append("^\n");
//        message.append("=====================================" +
//        		"============================================" +
//        		"============================================\n");
    }
}
