/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsgen.shared.generate;

import org.ebayopensource.dsf.jsgen.shared.generate.custom.DapJsrMeta;



public class GeneratorConfig {
	
	private JsrGenConfig m_jsrGenConfig;
	private IJsrFilters m_filters;
	private boolean m_addCodeGenAnnotation = true;
	
	public GeneratorConfig(IJsrFilters filters) {
		m_jsrGenConfig = new JsrGenConfig();
		m_filters = filters;
	}

	public JsrGenConfig getJsrGenConfig() {
		return m_jsrGenConfig;
	}

	public void setJsrGenConfig(JsrGenConfig genConfig) {
		m_jsrGenConfig = genConfig;
	}
	
	public static class JsrGenConfig {
		
		private boolean m_needsProxyEventhandlers = true;
		private ICustomJsrProvider m_customJsrProvider = 
			DapJsrMeta.getInstance();
		private boolean m_genResourceSpec = true;

		public boolean needsProxyEventhandlers() {
			return m_needsProxyEventhandlers;
		}

		public void setNeedsProxyEventhandlers(boolean proxyEventhandlers) {
			m_needsProxyEventhandlers = proxyEventhandlers;
		}

		ICustomJsrProvider getCustomJsrProvider() {
			return m_customJsrProvider;
		}

		void setCustomJsrProvider(ICustomJsrProvider jsrProvider) {
			m_customJsrProvider = jsrProvider;
		}

		public boolean isGenResouceSpec(){
			return m_genResourceSpec;
		}
		
		public void setGenResouceSpec(boolean genResourceSpec){
			m_genResourceSpec = genResourceSpec;
		}
		
	}

	public IJsrFilters getFilters() {
		return m_filters;
	}

	public boolean shouldAddCodeGenAnnotation() {
		return m_addCodeGenAnnotation;
	}

	public void setAddCodeGenAnnotation(boolean value) {
		m_addCodeGenAnnotation = value;
	}

}
