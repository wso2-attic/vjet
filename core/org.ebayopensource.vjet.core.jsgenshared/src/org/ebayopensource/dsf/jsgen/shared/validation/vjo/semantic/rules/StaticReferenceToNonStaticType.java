/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/* 
 * $Id: StaticReferenceToNonStaticType.java, Jul 1, 2010, 7:52:10 PM, liama. Exp$
 *
 * Copyright (c) 2006-2007 Wipro Technologies. All Rights Reserved.
 * This software program and documentation are copyrighted by Wipro 
 * Technologies.
 */
package org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.rules;

import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoSemanticProblem;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoSemanticProblemFactory;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoSemanticRule;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.rules.rulectx.BaseVjoSemanticRuleCtx;
import org.ebayopensource.dsf.jst.declaration.JstArg;
import org.ebayopensource.dsf.jst.declaration.JstMethod;

/**
 * Class/Interface description
 *
 * @author <a href="mailto:liang.ma@wipro.com">liama</a>
 * @since 0.1
 * @since JDK 1.4
 */
public class StaticReferenceToNonStaticType extends VjoSemanticRule<BaseVjoSemanticRuleCtx> {

    @Override
    public VjoSemanticProblem doFire(BaseVjoSemanticRuleCtx ctx) {
        if(ctx.getNode() instanceof JstArg){
            JstArg arg = (JstArg) ctx.getNode();
            JstMethod jstMethod = (JstMethod) arg.getParentNode();
            if(jstMethod.getParamNames().size()>0 && jstMethod.getParamNames().contains(arg.getType().getName())){
                return null;
            }
            final VjoSemanticProblem problem = VjoSemanticProblemFactory.getInstance().createProblem(ctx.getArguments(), ctx.getGroupId(), getProblemId(), getErrMsg(), ctx.getNode(), this);
            return problem;
        }
        return null;
    }
}
