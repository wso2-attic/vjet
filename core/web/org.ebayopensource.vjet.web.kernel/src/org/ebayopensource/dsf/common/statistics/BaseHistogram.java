/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.statistics;


import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.ebayopensource.dsf.common.statistics.DarwinStatisticsCtx.DarwinStatisticsCount;
import org.ebayopensource.dsf.dom.DCDATASection;
import org.ebayopensource.dsf.dom.DElement;
import org.ebayopensource.dsf.dom.util.XmlWriterHelper;

public abstract class BaseHistogram implements IV4Statistics {
	private Map<String, DarwinStatisticsPageView> m_histo;
		
	public BaseHistogram(){
		m_histo = new ConcurrentHashMap<String, DarwinStatisticsPageView>();
	}
	
	public void reset() {
		m_histo.clear();
	}
	
	public String asString(){
		return XmlWriterHelper.asString(toXml());
	}
	
	public void count(String pageViewId, String cmpId, long usage, long ref){
		DarwinStatisticsPageView pv = m_histo.get(pageViewId);
		if(pv == null){
			pv = new DarwinStatisticsPageView(pageViewId);
			m_histo.put(pageViewId, pv);
		}
		
		pv.count(cmpId, usage, ref);
	}
	
	public abstract String getName();
	abstract String getHistogramName();
	abstract String getSubObjName();
	
	public DElement toXml() {
		final DElement cmpHisto = new DElement(getHistogramName());	
		Map<String, DarwinStatisticsPageView> sortedMap = new TreeMap<String, DarwinStatisticsPageView>(m_histo);
		for( Entry<String, DarwinStatisticsPageView>	entry : sortedMap.entrySet()){
			final DarwinStatisticsPageView pvObj = entry.getValue();
			final DElement pageViewTag = new DElement(V4StatisticsUtil.PAGE_VIEW);				
			final DElement pageViewGuidTag = new DElement(V4StatisticsUtil.GUID);
			pageViewGuidTag.add(new DCDATASection(pvObj.getPageViewName()));			
			pageViewTag.add(pageViewGuidTag);
			
			final Map<String, DarwinStatisticsUnit> objMap = pvObj.getDarwinStatisticsUnitMap();
			Map<String, DarwinStatisticsUnit> sortedObjMap = new TreeMap<String, DarwinStatisticsUnit>(objMap);
			for( Entry<String, DarwinStatisticsUnit> objEntry : sortedObjMap.entrySet()){
				final DarwinStatisticsUnit obj = objEntry.getValue();
				final DElement cmpTag = new DElement(getSubObjName());
				
				final DElement cmpGuidTag = new DElement(V4StatisticsUtil.GUID);
				cmpGuidTag.add(new DCDATASection(obj.getGuid()));		
				cmpTag.add(cmpGuidTag);
				
				final DElement cmpCountTag = new DElement(V4StatisticsUtil.COUNT);
				cmpTag.add(cmpCountTag);
								
				final DElement cmpUsageTag = new DElement(V4StatisticsUtil.USAGE);
				cmpUsageTag.add(new DCDATASection(String.valueOf(obj.getCount().getUsage())));
				cmpCountTag.add(cmpUsageTag);
				
				final DElement cmpRefTag = new DElement(V4StatisticsUtil.REF);
				cmpRefTag.add(new DCDATASection(String.valueOf(obj.getCount().getRef())));
				cmpCountTag.add(cmpRefTag);

				pageViewTag.add(cmpTag);
			}
			cmpHisto.add(pageViewTag);
		}
		
		return cmpHisto;
	}


	/* inner clz*/
	public static class DarwinStatisticsPageView{
		private String m_pvId;
		private Map<String, DarwinStatisticsUnit> m_darwinStatisticsUnitMap;
						
		public DarwinStatisticsPageView(String pvId){
			m_pvId = pvId;
			m_darwinStatisticsUnitMap = new ConcurrentHashMap<String, DarwinStatisticsUnit>();
		}
		
		public DarwinStatisticsPageView(String pvId, String guid){
			this(pvId);
			m_darwinStatisticsUnitMap.put(guid, new DarwinStatisticsUnit(guid));
		}

		public void count(String cmpId, long usage, long ref){
			DarwinStatisticsUnit v4Obj = m_darwinStatisticsUnitMap.get(cmpId);
			if(v4Obj == null){
				v4Obj = new DarwinStatisticsUnit(cmpId);
				m_darwinStatisticsUnitMap.put(cmpId, v4Obj);
			}
			
			final DarwinStatisticsCount count = v4Obj.getCount();
			count.incrementUsage(usage);
			count.incrementRef(ref);
		}
		
		public final Map<String, DarwinStatisticsUnit> getDarwinStatisticsUnitMap(){
			return m_darwinStatisticsUnitMap;
		}

		String getPageViewName() {
			return m_pvId;
		}

		void setPageViewName(String pvId) {
			m_pvId = pvId;
		}
	}

	public static class DarwinStatisticsUnit{
		private String m_guid;
		private DarwinStatisticsCount m_count;
		
		DarwinStatisticsUnit(String guid){
			this(guid, 1L);
		}
		
		DarwinStatisticsUnit(String guid, long x){
			m_guid = guid;
			m_count = new DarwinStatisticsCount();
		}
		
		public String getGuid() {
			return m_guid;
		}

		public void setGuid(String guid) {
			m_guid = guid;
		}

		public DarwinStatisticsCount getCount(){
			return m_count;
		}
	}
}
