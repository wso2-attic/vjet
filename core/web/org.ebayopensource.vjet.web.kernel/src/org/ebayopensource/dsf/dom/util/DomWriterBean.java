/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dom.util;

import java.util.HashSet;
import java.util.Set;

import org.ebayopensource.dsf.common.exceptions.DsfExceptionHelper;
import com.ebay.kernel.bean.configuration.BaseConfigBean;
import com.ebay.kernel.bean.configuration.BeanConfigCategoryInfo;
import com.ebay.kernel.bean.configuration.DynamicConfigBean;
import com.ebay.kernel.initialization.BaseInitializable;
import com.ebay.kernel.initialization.Initializable;
import com.ebay.kernel.initialization.InitializationContext;

public class DomWriterBean extends BaseConfigBean {
		
	private static final long serialVersionUID = 770642500133798058L;
	
	public static final String CONFIG_CATEGORY_ID = "org.ebayopensource.dsf.dom.util.DomWriter";
	public static final String CONFIG_CATEGORY_ALIAS = "DomWriter";
	public static final String CONFIG_CATEGORY_GROUP = "DSF";
	public static final String DESC = "DSF DomWriter configuration";
	
	public static final String CAL_LOG_ENABLE = "CalLogEnabled";
	
	private DynamicConfigBean m_configBean;
	
	//
	// Singleton
	//
	private static DomWriterBean s_instance = new DomWriterBean();
	private DomWriterBean() {
	}
	public static DomWriterBean getInstance() {
		return s_instance;
	}
	
	//
	// API
	//
	public DomWriterBean setCalLogEnabled(boolean enable) {
		try {
			if (m_configBean.hasProperty(CAL_LOG_ENABLE)) {
				m_configBean.setPropertyValue(CAL_LOG_ENABLE, Boolean.valueOf(enable));
			}
			else {
				m_configBean.addProperty(CAL_LOG_ENABLE, Boolean.valueOf(enable));
			}
		}
		catch(Throwable t){}
		
		return this;
	}
	
	public boolean isCalLogEnabled() {
		try {
			return Boolean.valueOf(m_configBean.getPropertyValue(CAL_LOG_ENABLE).toString())
				.booleanValue();
		}
		catch(Throwable t){
			return false;
		}
	}
	
	//
	// Init
	//
	private static Initializable s_initializable;
	static public synchronized Initializable getInitializable() {
		if ( s_initializable != null ) {
			return s_initializable;
		}

		s_initializable = new BaseInitializable() {
			private Set<CharacteristicEnum> m_characteristics = null;
			protected void initialize(final InitializationContext context) {
				DomWriterBean.getInstance().initConfigBean();
			}
			protected void shutdown(final InitializationContext context) {
				s_initializable = null;
			}
			public synchronized Set<CharacteristicEnum> getCharacteristics() {
				if(m_characteristics == null) {
					m_characteristics = new HashSet<CharacteristicEnum>(1);
					m_characteristics.add(Initializable.CharacteristicEnum.NO_LAZY_INIT);
				}
				return m_characteristics;
			}
		};

		return s_initializable;
	}
	
	//
	// Private
	//
	private void initConfigBean() {

		BeanConfigCategoryInfo category = null;
		try {
			category = BeanConfigCategoryInfo.createBeanConfigCategoryInfo(
					CONFIG_CATEGORY_ID, 
					CONFIG_CATEGORY_ALIAS, 
					CONFIG_CATEGORY_GROUP, 
					true, 
					true, 
					null, 
					DESC, 
					true);
			
			m_configBean = new DynamicConfigBean(category);
			m_configBean.setExternalMutable();
			setCalLogEnabled(false);
		} 
		catch (Exception e) {
			DsfExceptionHelper.chuck("Failed to create config category for " + CONFIG_CATEGORY_ID);
		}
	}
}

