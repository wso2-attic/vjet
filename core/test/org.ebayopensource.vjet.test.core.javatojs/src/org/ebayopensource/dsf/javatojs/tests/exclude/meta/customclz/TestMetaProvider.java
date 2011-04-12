/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.tests.exclude.meta.customclz;

import org.ebayopensource.dsf.javatojs.tests.exclude.meta.A;
import org.ebayopensource.dsf.javatojs.tests.exclude.meta.B;
import org.ebayopensource.dsf.javatojs.tests.exclude.meta.C;
import org.ebayopensource.dsf.javatojs.translate.config.MethodKey;
import org.ebayopensource.dsf.javatojs.translate.custom.CustomAttr;
import org.ebayopensource.dsf.javatojs.translate.custom.meta.BaseCustomMetaProvider;
import org.ebayopensource.dsf.javatojs.translate.custom.meta.CustomMethod;
import org.ebayopensource.dsf.javatojs.translate.custom.meta.CustomType;
import org.ebayopensource.dsf.javatojs.translate.custom.meta.ICustomMetaProvider;

public class TestMetaProvider extends BaseCustomMetaProvider implements
		ICustomMetaProvider {

	private static TestMetaProvider s_instance = new TestMetaProvider();

	private static boolean m_initialized = false;

	private TestMetaProvider() {
	}

	public static TestMetaProvider getInstance() {
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
			// exclude class
			final Class type = A.class;
			final CustomType customType = new CustomType(type);	
			
			customType.setAttr(CustomAttr.EXCLUDED);	
			
			addCustomType(type.getName(), customType);
			addCustomType(type.getSimpleName(), customType);
		}
		
		{
			final Class type = B.class;
			final CustomType customType = new CustomType(type);	
			
			customType.addCustomMethod(
					new CustomMethod(MethodKey.genMethodKey(B.class, "m_m1"))
					.setAttr(CustomAttr.EXCLUDED));
			customType.addCustomMethod(
					new CustomMethod(MethodKey.genMethodKey(B.class, "m_m2"))
					.setAttr(CustomAttr.EXCLUDED));
			customType.addCustomMethod(
					new CustomMethod(MethodKey.genMethodKey(B.class, "m_m3"))
					.setAttr(CustomAttr.EXCLUDED));
			customType.addCustomMethod(
					new CustomMethod(MethodKey.genMethodKey(B.class, "m_m4"))
					.setAttr(CustomAttr.EXCLUDED));
			customType.addCustomMethod(
					new CustomMethod(MethodKey.genMethodKey(B.class, "s_m1"))
					.setAttr(CustomAttr.EXCLUDED));
			customType.addCustomMethod(
					new CustomMethod(MethodKey.genMethodKey(B.class, "s_m2"))
					.setAttr(CustomAttr.EXCLUDED));
			customType.addCustomMethod(
					new CustomMethod(MethodKey.genMethodKey(B.class, "s_m3"))
					.setAttr(CustomAttr.EXCLUDED));
			customType.addCustomMethod(
					new CustomMethod(MethodKey.genMethodKey(B.class, "s_m4"))
					.setAttr(CustomAttr.EXCLUDED));
			
			addCustomType(type.getName(), customType);
			addCustomType(type.getSimpleName(), customType);
			
		}
		{
			final Class type = C.class;
			final CustomType customType = new CustomType(type);	
			
			customType.addCustomMethod(new CustomMethod("s_m1")
			.setAttr(CustomAttr.EXCLUDED));
			customType.addCustomMethod(new CustomMethod("s_m2")
			.setAttr(CustomAttr.EXCLUDED));
			
			addCustomType(type.getName(), customType);
			addCustomType(type.getSimpleName(), customType);
			
		}
	

	}
}
