/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsgen.shared.validation.vjo;

import org.ebayopensource.dsf.jst.JstProblemId;

public abstract class VjoSemanticRule<T extends IVjoSemanticRuleCtx> implements
        IVjoSemanticRule<T> {

    private String m_name;

    private String m_desc;

    private JstProblemId m_problemId;

    private String m_errMsg;

    private VjoSemanticRulePolicy m_globalPolicy = VjoSemanticRulePolicy.GLOBAL_ERROR_POLICY;
    
    private boolean m_active = true;
    
    public void setProblemId(JstProblemId problemId) {
        m_problemId = problemId;
    }

    public JstProblemId getProblemId() {
        return m_problemId;
    }

    public void setErrMsg(String errMsg) {
        m_errMsg = errMsg;
    }

    public String getErrMsg() {
        return m_errMsg;
    }

    public String getRuleName() {
        return m_name;
    }

    public void setRuleName(String name) {
        m_name = name;
    }

    public String getRuleDescription() {
        return m_desc;
    }

    public void setRuleDescription(String desc) {
        m_desc = desc;
    }

    public VjoSemanticRulePolicy getGlobalRulePolicy() {
        return m_globalPolicy;
    }

    public void setGlobalPolicy(VjoSemanticRulePolicy policy) {
        m_globalPolicy = policy;
    }
    
    public boolean isActive(){
    	return m_active;
    }
    
    public void setActive(final boolean active){
    	m_active = active;
    }
    
    @Override
    public VjoSemanticProblem fire(T ctx){
    	VjoSemanticProblem prob = null;
    	if(preFire(ctx)){
    		prob = doFire(ctx);
    		postFire(ctx);
    	}
    	return prob;
    }
    
    @Override
    public boolean preFire(T ctx){
    	return isActive();
    }
    
    @Override
    public void postFire(T ctx){
    	
    }
}
