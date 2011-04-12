/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.tests.exclude.meta;

import org.ebayopensource.dsf.javatojs.translate.custom.CustomAttr;
import org.ebayopensource.dsf.javatojs.translate.custom.meta.BaseCustomMetaProvider;
import org.ebayopensource.dsf.javatojs.translate.custom.meta.CustomMethod;
import org.ebayopensource.dsf.javatojs.translate.custom.meta.CustomType;
import org.ebayopensource.dsf.javatojs.translate.custom.meta.ICustomMetaProvider;

public class TestMeta extends BaseCustomMetaProvider implements
		ICustomMetaProvider {

	private static TestMeta s_instance = new TestMeta();

	private static boolean m_initialized = false;

	private TestMeta() {
	}

	public static TestMeta getInstance() {
		if (!m_initialized) {
			synchronized (s_instance) {
				if (!m_initialized) {
					s_instance.init();
					m_initialized = true;
				}
			}
		}
		return s_instance;
	}

	private void init() {
		{
			final Class type = MyCustomData.class;
			final CustomType customType = new CustomType(type);	
			//customType.setAttr(CustomAttr.EXCLUDED);
			
			customType.addCustomMethod(new CustomMethod("m_m1")
			.setAttr(CustomAttr.EXCLUDED));
			
			addCustomType(type.getName(), customType);
			addCustomType(type.getSimpleName(), customType);
		}
	

	}
}
