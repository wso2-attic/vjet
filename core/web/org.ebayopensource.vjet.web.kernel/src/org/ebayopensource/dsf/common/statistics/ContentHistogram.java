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


public class ContentHistogram implements IV4Statistics {
	private Map<String, DarwinStatisticsCount> m_histo;
	
	ContentHistogram(){
		m_histo = new ConcurrentHashMap<String, DarwinStatisticsCount>();
	}
	
	public String getName() {
		return V4StatisticsUtil.CONTENT_HISTOGRAM_NAME;
	}

	public void reset() {
		m_histo.clear();
	}
	
	public Map<String, DarwinStatisticsCount> getHisto() {
		return m_histo;
	}

//	public void setHisto(Map<String, DarwinStatisticsCount> histo) {
//		m_histo = histo;
//	}

	public DElement toXml() {
		DElement content_tag = new DElement(V4StatisticsUtil.CONTENT_HISTOGRAM_NAME);
		
		Map<String, DarwinStatisticsCount> sortedMap = new TreeMap<String, DarwinStatisticsCount>(m_histo);
		for( Entry<String, DarwinStatisticsCount>	entry : sortedMap.entrySet()){
			final String key = entry.getKey();
			final DarwinStatisticsCount count = entry.getValue();
			DElement unit_tag = new DElement(V4StatisticsUtil.CONTENT_UNIT);			
			DElement guid_tag = new DElement(V4StatisticsUtil.GUID);
			guid_tag.add(new DCDATASection(String.valueOf(key)));
			
			DElement count_tag = new DElement(V4StatisticsUtil.COUNT);
			DElement usage_tag = new DElement(V4StatisticsUtil.USAGE);
			DElement ref_tag = new DElement(V4StatisticsUtil.REF);
			usage_tag.add(new DCDATASection(String.valueOf(count.getUsage())));
			ref_tag.add(new DCDATASection(String.valueOf(count.getRef())));
			
			count_tag.add(usage_tag);
			count_tag.add(ref_tag);
			unit_tag.add(guid_tag);
			unit_tag.add(count_tag);
			content_tag.add(unit_tag);
		}
		
		return content_tag;
	}
	
	public String asString(){
		return XmlWriterHelper.asString(toXml());
	}
	
	public void count(String cmpId, long usage, long ref){
		if( m_histo == null){
			m_histo =  new ConcurrentHashMap<String, DarwinStatisticsCount>();
		}
		
		DarwinStatisticsCount count = m_histo.get(cmpId);
		if( count == null){
			count = new DarwinStatisticsCount();
			m_histo.put(cmpId, count);
		}
		
		count.setUsage((count.getUsage() + usage));
		count.setRef(count.getRef() + ref);
	}
}
