/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsgen.shared.validation.vjo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ebayopensource.dsf.jst.IJstType;

/**
 * 
 * 
 *
 * <p>
 * 	validation result is a JstType to Problem list mapping
 *  all problems could be returned or problems against specific JstType could be queried
 *  
 *  result is the return type of driver's api
 * </p>
 * 
 * @see VjoValidationDriver#validateComplete(List, String)
 */
public class VjoValidationResult {
	private Map<IJstType, List<VjoSemanticProblem>> m_problems;

	public void addResult(IJstType type, List<VjoSemanticProblem> problems) {
		if(m_problems == null){
			m_problems = new HashMap<IJstType, List<VjoSemanticProblem>>();
		}
		
		List<VjoSemanticProblem> probList = m_problems.get(type);
		if(probList == null){
			probList = new ArrayList<VjoSemanticProblem>();
			m_problems.put(type, probList);
		}
		
		probList.addAll(problems);
	}
	
	public List<VjoSemanticProblem> getProblemsByType(IJstType type){
		if(m_problems == null){
			return Collections.emptyList();
		}
		
		List<VjoSemanticProblem> toReturn = m_problems.get(type);
		if(toReturn == null){
			return Collections.emptyList();
		}
		else{
			return Collections.unmodifiableList(toReturn);
		}
	}
	
	public List<VjoSemanticProblem> getAllProblems(){
		return getAllProblems(-1);
	}
	
	public List<VjoSemanticProblem> getAllProblems(int limit){
		if(m_problems == null){
			return Collections.emptyList();
		}
		
		List<VjoSemanticProblem> toReturn = new ArrayList<VjoSemanticProblem>();
		for(List<VjoSemanticProblem> typeProblems : m_problems.values()){
			//too many problems could cause oom
			final int actualLimit = limit >= 0 ? Math.min(typeProblems.size(), limit) : typeProblems.size();
			for(int i = 0; i < actualLimit; i++){
				toReturn.add(typeProblems.get(i));
			}
		}
		
		return toReturn;
	}
}
