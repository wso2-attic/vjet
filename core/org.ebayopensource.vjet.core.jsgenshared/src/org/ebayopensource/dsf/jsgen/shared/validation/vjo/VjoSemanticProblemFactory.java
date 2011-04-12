/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsgen.shared.validation.vjo;

import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.JstProblemId;
import org.ebayopensource.dsf.jst.JstSource;
import org.ebayopensource.dsf.jst.ProblemSeverity;

public class VjoSemanticProblemFactory {

	private static final VjoSemanticProblemFactory s_instance = new VjoSemanticProblemFactory();
	
	public static final VjoSemanticProblemFactory getInstance(){
		return s_instance;
	}
	
	private VjoSemanticProblemFactory(){
		//do nothing
	}
	
	public VjoSemanticProblem createProblem(String[] arguments, String groupId, JstProblemId problemId, String errMsg, IJstNode sourceNode, VjoSemanticRule<?> reporter){
		final JstSource source = lookUpSource(sourceNode);
		
		int startOffset = source != null ? source.getStartOffSet() : -1;
		int endOffset = source != null ? source.getEndOffSet() : -1;
		int line = source != null ? source.getRow() : -1;
		int col = source != null ? source.getColumn() : -1;
		final ProblemSeverity severity = VjoGroupRulesCache.getInstance().getRulePolicy(groupId, reporter).getProblemSeverity(problemId);
		
		final VjoSemanticProblem prb = new VjoSemanticProblem(arguments,problemId,errMsg,null,startOffset,endOffset,line,col,severity);
		return prb;
	}
	
	/**
	 * bugfix, source could sometimes be null, and have to look up in the parent node
	 * @param node
	 * @return
	 */
	private JstSource lookUpSource(IJstNode node){
		if(node == null){
			return null;
		}
		
		final JstSource src = node.getSource();
		if(src != null){
			return src;
		}
		
		return lookUpSource(node.getParentNode());
	}
}
