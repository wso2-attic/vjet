/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.coverage;

import java.util.HashMap;

public class JsCoverageType{
	private String typeName;
	private HashMap <String, JsCoverageFunction> typeFunctionList = new HashMap<String, JsCoverageFunction>();
	
	public JsCoverageType(){
	}
	
	public JsCoverageType(String name){
		typeName = name;
	}
	
	public void addFunction(String funcName){
		typeFunctionList.put(funcName, new JsCoverageFunction(funcName));
	}
	
	public boolean isFunction(String funcName){
		if(getFunction(funcName) != null){
			return true;
		}
		return false;
	}
	
	public JsCoverageFunction getFunction(String funcName){
		return typeFunctionList.get(funcName);
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	public boolean hasCoverage(){
		for (JsCoverageFunction func : typeFunctionList.values()) {
			if(func.isCovered()){
				return true;
			}
		}
		return false;
	}
	
	public int getTypeCoverageLineCount(){
		int count = 0;
		for(JsCoverageFunction func : typeFunctionList.values()){
			count = count + func.getCurLineCovCount();
		}	
		return count;
	}
	
	public int getTypeLineCount(){
		int count = 0;
		for(JsCoverageFunction func : typeFunctionList.values()){
			count = count + func.getTotalLines();
		}	
		return count;
	}
	
	public int getNumberOfCoveredFunctions(){
		int count = 0;
		for(JsCoverageFunction func : typeFunctionList.values()){
			if(func.isCovered()){
				count++;
			}
		}
		return count;
	}
	
	public int getNumberOfTotalFunctions(){
		return typeFunctionList.size();
	}
	
	public HashMap <String, JsCoverageFunction> getTypeFunctionList(){
		return typeFunctionList;
	}
	
	public void setTypeFunctionList(HashMap <String, JsCoverageFunction> funcList){
		typeFunctionList = funcList;
	}
	
}
