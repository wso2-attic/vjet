/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.tests.data.custom.meta;

import org.ebayopensource.dsf.javatojs.translate.config.MethodKey;
import org.ebayopensource.dsf.javatojs.translate.custom.CustomAttr;
import org.ebayopensource.dsf.javatojs.translate.custom.meta.CustomMethod;
import org.ebayopensource.dsf.javatojs.translate.custom.meta.CustomType;

public class TestMeta {

	public static CustomType getCustomType(){
		Class<?> type = A.class;
		String mtdName = "foo";
		String[] mtd2Params = {"int"};
		String[] mtd3Params = {"String"};
		String[] mtd4Params = {"int", "String"};
		String[] mtd5Params = {"int", "String"};
		return new CustomType(type)
			.addCustomMethod(new CustomMethod(new MethodKey(mtdName, false, false)).setAttr(CustomAttr.EXCLUDED))
			.addCustomMethod(new CustomMethod(new MethodKey(mtdName, true, false, mtd2Params)).setAttr(CustomAttr.EXCLUDED))
			.addCustomMethod(new CustomMethod(new MethodKey(mtdName, true, false, mtd3Params)).setAttr(CustomAttr.EXCLUDED))
			.addCustomMethod(new CustomMethod(new MethodKey(mtdName, false, false, mtd4Params)).setAttr(CustomAttr.EXCLUDED))
			.addCustomMethod(new CustomMethod(new MethodKey(mtdName, false, true, mtd5Params)).setAttr(CustomAttr.EXCLUDED));
	}
}
