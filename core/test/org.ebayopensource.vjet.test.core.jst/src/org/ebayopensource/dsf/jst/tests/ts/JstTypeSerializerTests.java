/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.tests.ts;



import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.ebayopensource.dsf.jst.IJstLib;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.ts.util.JstTypeSerializer;


import org.ebayopensource.vjo.lib.LibManager;

//@ModuleInfo(value="DsfPrebuild",subModuleId="JavaToJs")
public class JstTypeSerializerTests extends BaseTest {
	
	//@Category( { P1, UNIT })
	//@Description("Test Jst Type Serialization and Deserialization")
	public void testSerializeDeserialize() {
		
		IJstLib jstLib = LibManager.getInstance().getJavaPrimitiveLib();
		List<IJstType> jstList= new ArrayList<IJstType>(jstLib.getAllTypes(true));
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		JstTypeSerializer.getInstance().serialize(jstList, os);
		
		ByteArrayInputStream is = new ByteArrayInputStream(os.toByteArray());
		List<IJstType> result = JstTypeSerializer.getInstance().deserialize(is);
		
		for (IJstType type : jstList) {
			assertTrue(listContains(type.getName(), result));
		}
	}
	
	private boolean listContains(String name, List<IJstType> jstList) {
		for (IJstType type : jstList) {
			if (type.getName().equals(name)) {
				return true;
			}
		}
		return false;
	}

}
