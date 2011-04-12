/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsgen.shared.jstvalidator;

import java.util.List;

import org.ebayopensource.dsf.jsgen.shared.ids.ScopeIds;
import org.ebayopensource.dsf.jsgen.shared.validation.common.IJstValidationRule;
import org.ebayopensource.dsf.jsgen.shared.validation.common.IJstValidator;
import org.ebayopensource.dsf.jsgen.shared.validation.common.ScopeId;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.JstProblemId;
import org.ebayopensource.dsf.jst.ProblemSeverity;
import org.ebayopensource.dsf.jst.traversal.IJstVisitor;

public class DefaultJstValidationVisitor implements IJstVisitor{

	private final IJstValidator m_validator;
	private final ValidationCtx m_ctx;
	
	public DefaultJstValidationVisitor(ValidationCtx ctx, IJstValidator v) {
		m_validator = v;
		m_ctx = ctx;
	}

	public void endVisit(IJstNode node) {
		// TODO Auto-generated method stub
		
	}

	public void postVisit(IJstNode node) {
		
	}
	

	public void preVisit(IJstNode node) {
		
		
	}
	public void preVisit(IJstType type) {
		
	}

//	private void problem(JstProblemId id, IJstNode node) {
//		JstSource source = node.getSource();
//		int startOffset = source.getStartOffSet();
//		int endOffset = source.getEndOffSet();
//		int line = source.getRow();
//		int col = source.getColumn();
//		IJstProblem prb = getValidator().getProblemFactory().createProblem(getCtx().getFilePath().toCharArray(), id, null, null, null,startOffset,endOffset,line,col);
//		getCtx().getProblems().add(prb);
//		
//	}

	public boolean visit(IJstNode node) {
			
			for(IJstValidationRule r : getValidator().getRules()){
				if(inScope(r.getSpec().getScopeId()) && isAcceptedProblem(r.getSpec().getProblemIds())){
					getCtx().setNode(node);
					r.validate(getCtx());
				}
		}
		return true;
	}

	private boolean isAcceptedProblem(List<JstProblemId> problemIds) {
		for(JstProblemId id:problemIds){
			if(getValidator().getValidationPolicy().getProblemSeverity(id).equals(ProblemSeverity.ignore)){
				return false;
			}
		}
		return true;
	}
	private boolean inScope(List<ScopeId> scopeId) {
		if(getCtx().getScope().equals(ScopeIds.ALL)){
			return true;
		}
		for(ScopeId s:scopeId){
			if(s.equals(getCtx().getScope())){
				return true;
			}
		}
		return false;
	}

	private IJstValidator getValidator() {
		return m_validator;
	}

	private ValidationCtx getCtx() {
		return m_ctx;
	}

}
