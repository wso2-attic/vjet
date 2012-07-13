/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.tests.ts;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.ts.util.JstTypeSerializer;
import org.ebayopensource.dsf.common.resource.ResourceUtil;

public class TestDataHelper {
	
	public static final String TEST_DATA_JAR = "TestData.ser";
	
	private static Map<String,IJstType> s_jstTypes = getJstTypes();

	public static synchronized Map<String,IJstType> getJstTypes() {
		
		if (s_jstTypes != null){
			return s_jstTypes;
		}

		Map<String,IJstType> jstTypes = new HashMap<String,IJstType>();
		try {
			Class<?> anchorClass = TestDataHelper.class;
			String jstFilename = TEST_DATA_JAR;
			InputStream fio = ResourceUtil.getMandatoryResourceAsStream(anchorClass, jstFilename);
			List<IJstType> deserialize = JstTypeSerializer.getInstance().deserialize(fio);
			for(IJstType t: deserialize){
				jstTypes.put(t.getName(), t);
			}
			s_jstTypes = jstTypes;
			return jstTypes;
		} catch (RuntimeException e) {
			e.printStackTrace();	//KEEPME
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return jstTypes;
	}
	
	public static synchronized void clear(){
		s_jstTypes = null;
	}
}