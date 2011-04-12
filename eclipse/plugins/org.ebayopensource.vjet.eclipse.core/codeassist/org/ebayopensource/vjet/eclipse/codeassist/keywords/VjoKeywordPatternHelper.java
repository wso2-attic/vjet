/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.codeassist.keywords;

import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VjoKeywordPatternHelper {
	
	private String cntx = null; 
	
	/**
	 * Constructs pattern helper instance.
	 * @param lookupContext inverted lookup context.
	 * @param initPos initial position
	 */
	public VjoKeywordPatternHelper(char[] lookupContext){
		cntx = new String(lookupContext);
	}
	
	/**
	 * Whether the keyword is suitable for completion for the current context. 
	 * @param keyword unfiltered keyword
	 * @return true if keyword is suitable for completion otherwise false.
	 */
	public boolean isSuitableForCompleteion(IVjoCompletionData keyword){
		
		//Pattern pattern = keyword.getCompletionPattern();
		//Matcher matcher = pattern.matcher(cntx); 

		return false;//matcher.find() && matcher.start() == 0;
		
	}
	
	public boolean containWordOrWhitespacesOnly(){

		Pattern pattern = Pattern.compile("[\\w\\t\\s]*");
		
		return !pattern.matcher(cntx).find();
	}
	
	/**
	 * Finds the nearest keyword in current inverted context.
	 * @return the nearest keyword or null if there isn't.
	 */
	public IVjoCompletionData lookupNearestKeyword(){
		
		//TODO this should be using infrastructure keywords not duplicated vjo keyword factory
		Iterator<IVjoCompletionData> keywords = VjoKeywordFactory.
			getInstalledKeywords().iterator();
		
		IVjoCompletionData nearestKeyword = null;
		
		while(keywords.hasNext()){
			
			IVjoCompletionData keyword = keywords.next();
			
			if(keyword.isEnclosableKeyword() || keyword.isComposableKeyword() ||
					keyword.isTopLevelKeyword()){
				
				Pattern pattern = Pattern.compile("");
				Matcher matcher = pattern.matcher(cntx); 
				
				if(matcher.find() && matcher.start() == 0){
						
					nearestKeyword = keyword;
					break;
					
				}
				
			}
			
		}
		
		return nearestKeyword;
	}
	
}
