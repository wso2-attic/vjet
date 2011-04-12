/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/* 
 * $Id: LineNumberComparator.java, Oct 26, 2009, 6:52:02 PM, liama. Exp$
 *
 * Copyright (c) 2006-2009 Ebay Technologies. All Rights Reserved.
 * This software program and documentation are copyrighted by Ebay 
 * Technologies.
 */
package org.ebayopensource.dsf.jstojava.cml.vjetv.reporter;

import java.util.Comparator;

import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoSemanticProblem;

/**
 * Class/Interface description
 *
 * @author <a href="mailto:liama@ebay.com">liama</a>
 * @since JDK 1.5
 */
public class LineNumberComparator implements Comparator<VjoSemanticProblem>{
    
    private int m_line1 = 0;
    private int m_line2 = 0;
    
    /* (non-Javadoc)
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    public int compare(VjoSemanticProblem object1, VjoSemanticProblem object2) {
        m_line1 = object1.getSourceLineNumber();
        m_line2 = object2.getSourceLineNumber();
        if(m_line1>m_line2){
            return 2;
        }else if(m_line1<m_line2){
            return -2;
        }
        return 0;
    }
}
