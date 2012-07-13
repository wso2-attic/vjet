/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


import org.ebayopensource.dsf.common.initialization.BaseInitializable;
import org.ebayopensource.dsf.common.initialization.Initializable;
import org.ebayopensource.dsf.common.initialization.InitializationContext;

/**
 * Configure Bean to control the enable/disable of DSF verifier(s)
 * for node creation, naming, parent-child relationship, etc.
 */
public class DsfVerifierConfig implements Serializable  {

	private static final long serialVersionUID = 1L;
	private boolean m_verifyNaming = true;
	private boolean m_verifyRelationship = true;
	private boolean m_verifyInstantiation = true;
	
	
	private static final DsfVerifierConfig s_instance = new DsfVerifierConfig();
	
	public static DsfVerifierConfig getInstance() {
		return s_instance;
	}
	
	private DsfVerifierConfig() {

	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isVerifyNaming() {
		return m_verifyNaming;
	}
	
	/**
	 * if set = true, During node construction and node append operation,
	 * Dsf will invoke IDsfNamingVerifier to verify nodeName, namespace related
	 * prefix and localname.
	 * Default, at construction time, XmlVerifier is invoked
	 * Default, at appendNode operation time, NameChecker is invoked
	 * @param set
	 * @return
	 */
	public void setVerifyNaming(final boolean set) {
		m_verifyNaming = set;
	}

	/**
	 * if true, 
	 * 1. check for cycles  during appendNode operation.
	 * 2. invoke IDNodeRelationshipVerifier
	 * @return
	 */
	public boolean isVerifyRelationship() {
		return m_verifyRelationship;
	}
	
	/**
	 * if set = true, 
	 * 1. check for cycles  during appendNode operation.
	 * 2. invoke IDNodeRelationshipVerifier
	 * @return
	 * @param set
	 * @return
	 */
	public void setVerifyRelationship(final boolean set) {
		m_verifyRelationship = set;
	}
	
	public boolean isVerifyInstantiation() {
		return m_verifyInstantiation;
	}
	
	public void setVerifyInstantiation(final boolean set) {
		m_verifyInstantiation = set;
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
				DsfVerifierConfig config = DsfVerifierConfig.getInstance();
//		        if (!AppBuildConfig.getInstance().isDev()) {
//		            //disable dsf verifier for non-dev env
//		            config.setVerifyNaming(false);
//		            config.setVerifyRelationship(false);
//		        }
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
}
