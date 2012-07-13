/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf;

import java.util.HashSet;
import java.util.Set;

import org.ebayopensource.dsf.dom.util.DomWriterBean;
import org.ebayopensource.dsf.common.initialization.BaseInitializable;
import org.ebayopensource.dsf.common.initialization.BaseInitializationManager;
import org.ebayopensource.dsf.common.initialization.BaseModule;
import org.ebayopensource.dsf.common.initialization.Initializable;
import org.ebayopensource.dsf.common.initialization.InitializationContext;
import org.ebayopensource.dsf.common.initialization.ModuleInterface;

public class Module extends BaseModule {

	private Module() {
		super(new InitializationManager());
	}

	private static Module s_module = new Module();

	public static ModuleInterface getInstance() {
		return s_module;
	}

	public static class InitializationManager extends BaseInitializationManager {

		final static ModuleInterface [] DEPENDENT_MODULES = {};

		InitializationManager() {
			super(DEPENDENT_MODULES);
			getInitializationHelper().add(DomWriterBean.getInitializable());
			getInitializationHelper().add(
				new BaseInitializable(){
					protected void initialize(final InitializationContext context) {
						// TODO add any init if needed
					}
					
					protected void shutdown(final InitializationContext context) {
						// TODO add any cleanup if needed
					}
					
					private Set<CharacteristicEnum> m_characteristics = null;
					public synchronized Set<?> getCharacteristics() {
						if(m_characteristics == null) {
							m_characteristics = new HashSet<CharacteristicEnum>(1);
							m_characteristics.add(Initializable.CharacteristicEnum.NO_LAZY_INIT);
						}
						return m_characteristics;
					}
				});
		}
	}
}
