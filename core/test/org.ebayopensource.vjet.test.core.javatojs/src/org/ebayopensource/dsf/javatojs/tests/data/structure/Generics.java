/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.tests.data.structure;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Generics<Ctx,Ctx2 extends Date> extends ArrayList<Ctx>{
	private List<? extends Ctx> m_list = null;
	private List<Ctx2> m_list2 = null;
	private Map<Ctx,Generics<Ctx,Ctx2>> m_map = new HashMap<Ctx,Generics<Ctx,Ctx2>>();
	private Ctx[] m_ctxArray;
	
	public List<? extends Ctx> convert(List<? extends Ctx> value){
		return null;
	}
	
	public List<? extends Ctx2> convert2(Object value){
		return null;
	}
	
	public Map<Ctx,Generics<Ctx,Ctx2>> getMap(){
		return m_map;
	}
	
	public Ctx[] getArray(){
		return m_ctxArray;
	}
	
	public <R,S extends List<Date>> List<? extends R> convertAny(Ctx ctx, List<? extends R> converter, Object value){
		return null;
	}
	
	public <T,S,M,K> java.util.Set<T> testMethod9(S p1,M p2,K p3){
		return null;
	}
	
	public Map<String,String> getStringMap(){
		return null;
	}
	
	public List<Integer> testMethod10(List<? extends Ctx> myList) {
		List<Integer> out = new ArrayList<Integer>();
		for (int i = 0; i < myList.size(); i++) {
			out.add(myList.get(i).toString().length());
		}
		return out;
	}
}
