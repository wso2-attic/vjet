/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.util.bootstrap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.ebayopensource.dsf.jst.declaration.JstCache;
import org.ebayopensource.dsf.jst.declaration.JstFactory;
import org.ebayopensource.dsf.jst.declaration.JstPackage;
import org.ebayopensource.dsf.jst.declaration.JstType;


public class JsBuilderDef {


	private final JstType m_type;
	private final Map<String, Section> m_sections = new LinkedHashMap<String, Section>();
	private final Map<String, List<Section>> m_anyOrderGrps = new LinkedHashMap<String, List<Section>>();
	private List<List<Section>> m_anyOrderGrp = new ArrayList<List<Section>>();
	
	public class Section{

		private final String m_mtd;
		private final int m_min;
		private final int m_max;
		private boolean m_anyOrder;
		
		public Section(String mtd, int min, int max) {
			m_mtd = mtd;
			m_min = min;
			
			m_max = max;
		}
		
		public Section(String mtd, int min, String max) {
			m_mtd = mtd;
			m_min = min;
			if(max.equals("*")){
				m_max = Integer.MAX_VALUE; 
			}else{
				m_max = new Integer(max);
			}
		}
		

		
		public String getMtd(){
			return m_mtd;
		}
		public int getMin() {
			return m_min;
		}
		public int getMax() {
			return m_max;
		}
		public boolean isAnyOrder() {
			return m_anyOrder;
		}
		

		
	}
	
	public JsBuilderDef() {
		m_type = null;
	}
	
	public JsBuilderDef(String pkg, String type) {	
		m_type = getType(type);
		m_type.setPackage(new JstPackage(pkg));
	}
	
	private final JstType getType(final String type) {
		JstType typeFromCache = JstCache.getInstance().getType(type);
		
		if(typeFromCache!=null){
			return typeFromCache;
		}
		
		return JstFactory.getInstance().createJstType(type,
				true);
	}

	
	public JsBuilderDef mtd(String string, int min, int max) {
		m_sections.put(string, new Section(string, min, max));
		return this;
	}
	public JsBuilderDef mtd(String string, int min, String max) {
		m_sections.put(string, new Section(string, min, max));
		return this;
	}
	public JsBuilderDef anyOrder(JsBuilderDef def) {
		Section firstDef = null;
		List<Section> secs = new ArrayList<Section>();
		m_anyOrderGrp.add(secs);
		
		for(Section s:def.m_sections.values()){
			if(firstDef==null){
				firstDef = s; 
			}
			s.m_anyOrder = true;
			secs.add(s);
			m_anyOrderGrps.put(s.getMtd(), secs);
			
			m_sections.put(s.getMtd(), s);
		}
		return this;
	}

	public JstType getType() {
		return m_type;
	}

	public List<Section> getSections() {
		List<Section> sec = new ArrayList<Section>(m_sections.size());
		sec.addAll(m_sections.values());
		return sec;
	}
	
	public List<Section> getAnyOrderGroup(String name){
		if(m_anyOrderGrps.get(name)==null){
			return Collections.EMPTY_LIST;
		}
		/*
		 * get all sections after finding this section name
		 * that have any order 
		 */
		return m_anyOrderGrps.get(name);
		
	}
	
	
	public List<Section> getAnyOrderGroupMaxOne(String name){
		List<Section> list = new ArrayList<Section>();
		for(Section s: getAnyOrderGroup(name)){
			if(s.m_max==1){
				list.add(s);
			}
		}
		
		return list;
	}

	public List<List<Section>> getAnyOrderGrp() {
		return m_anyOrderGrp;
	}
	
	public List<List<Section>> getAnyOrderMaxOneGrp() {
		List<List<Section>> list = new ArrayList<List<Section>>();
		for(List<Section> sec : m_anyOrderGrp){
			List<Section> sec2 = new ArrayList<Section>();
			for(Section section : sec){
				if(section.getMax()==1){
					sec2.add(section);
				}
			}
			list.add(sec2);
		}
		
		return list;
	}

	public List<Section> getNextOrderSections(String mtd) {
		List<Section> nextOrderSections = new ArrayList<Section>();
		boolean findNextSection = false;
		for(Section sec : m_sections.values()){
			if(sec.getMtd().equals(mtd)){
				findNextSection = true;
				continue;
			}
			if(findNextSection && !sec.getMtd().equals(mtd) ){
				nextOrderSections.add(sec);
			}
			
		}
		return nextOrderSections;
			
	}
	
	
}
