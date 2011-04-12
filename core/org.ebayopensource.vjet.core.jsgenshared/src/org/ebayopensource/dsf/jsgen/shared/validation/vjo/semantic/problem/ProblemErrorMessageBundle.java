/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.problem;

import java.util.ResourceBundle;

public class ProblemErrorMessageBundle {

	private static final ProblemErrorMessageBundle s_instance = new ProblemErrorMessageBundle();
	
	public static final ProblemErrorMessageBundle getInstance(){
		return s_instance;
	}
	
	private boolean m_initialized;
	private ResourceBundle m_bundle;
	private ResourceBundle m_bundleVerbose;
	
	private ProblemErrorMessageBundle(){
		try{
			m_bundle = ResourceBundle.getBundle("org.ebayopensource.dsf.jsgen.VjoSemanticProblemErrorMessages");
			m_bundleVerbose = ResourceBundle.getBundle("org.ebayopensource.dsf.jsgen.VjoSemanticProblemErrorMessagesVerbose");
			m_initialized = true;
		}
		catch(Throwable t){
			m_initialized = false;
		}
	}
	
	public String getErrorMessage(String key){
		return getErrorMessage(key, false);
	}
	
	public String getErrorMessage(String key, boolean verbose){
		if(!m_initialized){
			return "";
		}
		
		try{
			if(verbose){
				return m_bundleVerbose.getString(key);
			}
			else{
				return m_bundle.getString(key);
			}
		}
		catch(Exception ex){
			return "";
		}
	}
}
