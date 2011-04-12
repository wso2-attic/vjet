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
import java.util.Map.Entry;

import org.ebayopensource.dsf.common.statistics.DarwinStatisticsCtx.DarwinStatisticsCount;


public class V4StatisticsUtil {

	public static final String V4_STATISTICS_NAME = "V4_Statistics";
	
	public static final String CONTENT_HISTOGRAM_NAME = "v4-content";
	public static final String COMPONENT_HISTOGRAM_NAME = "v4-component";
	public static final String JS_HISTOGRAM_NAME = "v4-js";
	public static final String ESF_TEMPLATE_HISTOGRAM_NAME = "esf-template";
	public static final String ESF_TEMPLATE_FAILURE_HISTOGRAM_NAME = "esf-template-failure";
	public static final String BIZMO_HISTOGRAM_NAME = "bizmo";
	public static final String BIZOP_HISTOGRAM_NAME = "bizop";
	public static final String CSS_HISTOGRAM_NAME = "v4-css";
	public static final String IMAGE_HISTOGRAM_NAME = "v4-image";
	public static final String LINK_HISTOGRAM_NAME = "v4-link";

	public static final String STATISTICS_NODE_NAME = "statistics";
	public static final String DATA_NODE_NAME = "data";
	public static final String NAME_ATTR = "name";
	
	public static final String GUID = "guid";
	public static final String COUNT = "count";
	public static final String USAGE = "usage";
	public static final String REF = "ref";
	
	// Content part
	public static final String CONTENT_UNIT = "content-unit";
	
	// Component part
	public static final String PAGE_VIEW = "page-view";
	public static final String COMPONENT = "component";
	
	// Esf template part
	public static final String ESF_TEMPLATE = "esf_template";
   
	public static final String ESF_TEMPLATE_FAILURE = "esf_template_failure";

	// Bizmo template part
	public static final String BIZMO = "bizmo";
	
	public static final String BIZOP = "bizop";
	
	// Js part
	public static final String JS = "js";
	
	// Css part
	public static final String CSS = "css";
	
	// Image part
	public static final String IMAGE = "image";
	
	// Link part
	public static final String LINK = "_link";
	
	public static void countStatistics(String pageViewName, BaseHistogram histo, Map<String, DarwinStatisticsCount> stat){
		for(Entry<String, DarwinStatisticsCount> entry : stat.entrySet()){
			final String key = entry.getKey();
			final DarwinStatisticsCount count = entry.getValue();
			histo.count(pageViewName, key, count.getUsage(), count.getRef());
		}
	}
	
	public static void countContentStatistics(ContentHistogram histo, Map<String, DarwinStatisticsCount> stat){
		for(Entry<String, DarwinStatisticsCount> entry : stat.entrySet()){
			final String key = entry.getKey();
			final DarwinStatisticsCount count = entry.getValue();
			histo.count(key, count.getUsage(), count.getRef());
		}
	}
}
