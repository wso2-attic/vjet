/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.tests.ts;



import java.util.List;

import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.ts.JstQueryExecutor;
import org.ebayopensource.dsf.ts.TypeSpace;



//@ModuleInfo(value="DsfPrebuild",subModuleId="JavaToJs")
public class QueryTests extends BaseTest {
	
	//@Category( { P1, UNIT })
	//@Description("Test type searches")
	public void testTypeSearch(){
		
		TestDataHelper.clear();
		
		TypeSpace<IJstType,IJstNode> ts = loadTypeSpace();
		assertEquals(TYPE_A.typeName(), ts.getType(TYPE_A).getName());
		assertEquals(1, ts.getType(TYPE_A.typeName()).size());
		assertEquals(TYPE_A.typeName(), ts.getType(TYPE_A.typeName()).get(0).getName());
		assertEquals(0, ts.getType("FOO").size());
	}

	//@Category( { P1, UNIT })
	//@Description("Test finding with JstQueryExecutor")
	public void testFindNeeded(){
		
		TypeSpace<IJstType,IJstNode> ts = loadTypeSpace();
		
		JstQueryExecutor executor = new JstQueryExecutor(ts);
		
		List<IJstType> needed = executor.findNeeded(TYPE_A);
		assertEquals(4, needed.size());
		
		needed = executor.findNeeded(TYPE_B);
		assertEquals(2, needed.size());
		
		needed = executor.findNeeded(TYPE_C);
		assertEquals(2, needed.size());
		
		needed = executor.findNeeded(TYPE_D);
		assertEquals(0, needed.size());
		
		needed = executor.findNeeded(TYPE_Ax);
		assertEquals(0, needed.size());
	}
	
	//@Category( { P1, UNIT })
	//@Description("Test querying of sub types")
	public void testFindSubTypes(){
		
		TypeSpace<IJstType,IJstNode> ts = loadTypeSpace();
		
		JstQueryExecutor executor = new JstQueryExecutor(ts);
		
		List<IJstType> needed = executor.findSubTypes(TYPE_A);
		assertEquals(2, needed.size());
		
		needed = executor.findSubTypes(TYPE_B);
		assertEquals(1, needed.size());
		
		needed = executor.findSubTypes(TYPE_C);
		assertEquals(0, needed.size());
	}
	
	//@Category( { P1, UNIT })
	//@Description("Test querying of satisfiers")
	public void testFindSatisfiers(){
		
		TypeSpace<IJstType,IJstNode> ts = loadTypeSpace();
		
		JstQueryExecutor executor = new JstQueryExecutor(ts);
		
		List<IJstType> needed = executor.findSatisfiers(TYPE_IA);
		assertEquals(1, needed.size());
		
		needed = executor.findSatisfiers(TYPE_A);
		assertEquals(0, needed.size());
		
		needed = executor.findSatisfiers(TYPE_B);
		assertEquals(0, needed.size());
		
		needed = executor.findSatisfiers(TYPE_C);
		assertEquals(0, needed.size());
	}
}
