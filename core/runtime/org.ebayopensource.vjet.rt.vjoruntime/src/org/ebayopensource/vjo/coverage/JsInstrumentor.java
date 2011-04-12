/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.coverage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public abstract class JsInstrumentor {
	
	public HashMap<String, Integer> markers = new HashMap<String,Integer>();
	public HashMap<String, Integer> types = new HashMap<String,Integer>();
	private ArrayList <JsCoverageType> m_types = new ArrayList<JsCoverageType>();
	
	public abstract String instrument(String source);
	
	protected abstract String instrumentStatements(String constrSource, String type, String method);
	
	public boolean isTypeExists(String type){
		Iterator iter = m_types.iterator();
		while(iter.hasNext()){
			JsCoverageType testType = (JsCoverageType) iter.next();
			if(type.equalsIgnoreCase(testType.getTypeName())){
				return true;
			}
		}
		return false;
	}
	
	public JsCoverageType getType(String type){
		Iterator iter = m_types.iterator();
		while(iter.hasNext()){
			JsCoverageType testType = (JsCoverageType) iter.next();
			if(type.equalsIgnoreCase(testType.getTypeName())){
				return testType;
			}
		}
		return null;
	}
	
	public ArrayList <JsCoverageType> getTypes(){
		return m_types;
	}
	
	public void addType(JsCoverageType type){
		m_types.add(type);
	}

}
