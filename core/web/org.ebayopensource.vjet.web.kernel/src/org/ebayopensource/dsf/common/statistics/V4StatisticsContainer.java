/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.statistics;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.ebayopensource.dsf.dom.DElement;
import org.ebayopensource.dsf.dom.util.XmlWriterHelper;

public class V4StatisticsContainer implements IV4Statistics {
	private static final V4StatisticsContainer s_instance = new V4StatisticsContainer();
	
	public static final V4StatisticsContainer getInstance(){
		return s_instance;
	}
	
	private ConcurrentMap<String, IV4Statistics> m_stat;
	private ContentHistogram m_contentHisto;
	private ComponentHistogram m_componentHisto;
	private JavascriptHistogram m_jsHisto;
	private CssHistogram m_cssHisto;
	private ImageHistogram m_imgHisto;
	private LinkHistogram m_lnkHisto;
	private EsfTemplateHistogram m_esfTemplateHisto;
	private EsfTemplateFailureHistogram m_esfTemplateFailureHisto;
	private BizMoHistogram m_bizMoHisto;
	private BizOpHistogram m_bizOpHisto;
	
	private UnspecdCmpStatistics m_unspecdInfo;
	
	//private static final int THRESHOLD = 8; //do statistics every 8 visits to solve the perf impact
	private static final int THRESHOLD = 500; //changed sampling rate to 500 as per finding domain request.
	 private final AtomicInteger m_monitor = new AtomicInteger(0);
	
	private V4StatisticsContainer(){
		m_stat = new ConcurrentHashMap<String, IV4Statistics>();
		
		m_contentHisto = new ContentHistogram();
		add(V4StatisticsUtil.CONTENT_HISTOGRAM_NAME, m_contentHisto);
		
		m_componentHisto = new ComponentHistogram();
		add(V4StatisticsUtil.COMPONENT_HISTOGRAM_NAME, m_componentHisto);
		
		m_jsHisto = new JavascriptHistogram();
		add(V4StatisticsUtil.JS_HISTOGRAM_NAME, m_jsHisto);
		
		m_cssHisto = new CssHistogram();
		add(V4StatisticsUtil.CSS_HISTOGRAM_NAME, m_cssHisto);
		
		m_imgHisto = new ImageHistogram();
		add(V4StatisticsUtil.IMAGE_HISTOGRAM_NAME, m_imgHisto);
		
		m_lnkHisto = new LinkHistogram();
		add(V4StatisticsUtil.LINK_HISTOGRAM_NAME, m_lnkHisto);
      
		m_esfTemplateHisto = new EsfTemplateHistogram();
		add(V4StatisticsUtil.ESF_TEMPLATE_HISTOGRAM_NAME, m_esfTemplateHisto);
      
		m_esfTemplateFailureHisto = new EsfTemplateFailureHistogram();
		add(V4StatisticsUtil.ESF_TEMPLATE_FAILURE_HISTOGRAM_NAME, m_esfTemplateFailureHisto);
		
		m_bizMoHisto = new BizMoHistogram();
		add(V4StatisticsUtil.BIZMO_HISTOGRAM_NAME, m_bizMoHisto);
		
		m_bizOpHisto = new BizOpHistogram();
		add(V4StatisticsUtil.BIZOP_HISTOGRAM_NAME, m_bizOpHisto);
		
		m_unspecdInfo = new UnspecdCmpStatistics();
		add(UnspecdCmpStatistics.NAME, m_unspecdInfo);
	}
	
	public int getIncrement(){
		return THRESHOLD;
	}
	
	public int getMonitor(){
		return m_monitor.get();
	}
	
	public void incrementMonitor(){
		m_monitor.incrementAndGet();
	}
	
	public boolean doStatistics(){
		return m_monitor.get()%THRESHOLD == 0;
	}
	
	public ContentHistogram getContentHistogram(){
		return m_contentHisto;
	}
	
	public ComponentHistogram getComponentHistogram(){
		return m_componentHisto;
	}
	
	public JavascriptHistogram getJavascriptHistogram(){
		return m_jsHisto;
	}
	
	public CssHistogram getCssHistogram(){
		return m_cssHisto;
	}
	
	public ImageHistogram getImageHistogram(){
		return m_imgHisto;
	}
	
	public LinkHistogram getLinkHistogram(){
		return m_lnkHisto;
	}
   
	public EsfTemplateHistogram getEsfTemplateHistogram(){
	   return m_esfTemplateHisto;
	}

	public EsfTemplateFailureHistogram getEsfTemplateFailureHistogram(){
		return m_esfTemplateFailureHisto;
	}

	public BizMoHistogram getBizMoHistogram(){
		return m_bizMoHisto;
	}
	
	public BizOpHistogram getBizOpHistogram(){
		return m_bizOpHisto;
	}

	public void add(String name, IV4Statistics stat){
		m_stat.put(name, stat);
	}
	
	public IV4Statistics get(String name){
		return m_stat.get(name);
	}

	public String getName() {
		return V4StatisticsUtil.V4_STATISTICS_NAME;
	}

	public void reset() {
		// values() would be slightly better in this case

        for (IV4Statistics value : m_stat.values()) {

            value.reset();

        }

        m_stat.clear();


	}

	public DElement toXml() {
		final DElement stat = new DElement(V4StatisticsUtil.STATISTICS_NODE_NAME);

        // valueSet() would be slightly better in this case

        for (IV4Statistics value : m_stat.values()) {

            stat.add(value.toXml());

        }

        return stat;
	}
	
	public String asString(){
		return XmlWriterHelper.asString(toXml());
	}

	public UnspecdCmpStatistics getUnspecdInfo() {
		return m_unspecdInfo;
	}

}
