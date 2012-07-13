/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dom.util;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.ebayopensource.dsf.common.initialization.BaseInitializable;
import org.ebayopensource.dsf.common.initialization.Initializable;
import org.ebayopensource.dsf.common.initialization.InitializationContext;

public class DomWriterBean implements Serializable {
		
	private static final long serialVersionUID = 770642500133798058L;
	
	
	
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

	}
	
}

