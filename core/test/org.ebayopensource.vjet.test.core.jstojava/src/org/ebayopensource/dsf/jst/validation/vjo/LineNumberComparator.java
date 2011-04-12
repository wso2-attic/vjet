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
package org.ebayopensource.dsf.jst.validation.vjo;

import java.util.Comparator;

import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoSemanticProblem;

/**
 * Class/Interface description
 *
 * @author <a href="mailto:liama@ebay.com">liama</a>
 * @since JDK 1.5
 */
public class LineNumberComparator implements Comparator<VjoSemanticProblem>{
    
    int line1 = 0;
    int line2 = 0;
    
    public int compare(VjoSemanticProblem object1, VjoSemanticProblem object2) {
        line1 = object1.getSourceLineNumber();
        line2 = object2.getSourceLineNumber();
        if(line1>line2){
            return 2;
        }else if(line1<line2){
            return -2;
        }
        return 0;
    }
}
