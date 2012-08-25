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
import java.util.Map;
import java.util.Map.Entry;

import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.dsf.jst.traversal.JstDepthFirstTraversal;
import org.ebayopensource.dsf.jst.ts.util.MethodDependencyVisitor;
import org.ebayopensource.dsf.ts.TypeSpace;
import org.ebayopensource.dsf.ts.method.MethodIndex;
import org.ebayopensource.dsf.ts.method.MethodName;
import org.ebayopensource.dsf.ts.type.TypeName;



//@ModuleInfo(value="DsfPrebuild",subModuleId="JavaToJs")
public class MethodIndexTests extends BaseTest {
	//@Category( { P1, UNIT })
	//@Description("Test method indexes")
	public void testDependents(){
		
		TestDataHelper.clear();
			
		TypeSpace<IJstType,IJstNode> ts = createTypeSpace();
		
		List<IJstType> jstTypes = getJstTypes(getClassList1());
		
		JstType typeA = (JstType)jstTypes.get(1);
		JstType typeB = (JstType)jstTypes.get(2);
		JstType typeC = (JstType)jstTypes.get(3);
		JstType typeD = (JstType)jstTypes.get(4);
		
		typeA.getPackage().setGroupName(PROJ_1);
		typeB.getPackage().setGroupName(PROJ_1);
		typeC.getPackage().setGroupName(PROJ_1);
		typeD.getPackage().setGroupName(PROJ_1);
		
		addMethodDependency(typeA,ts);
		addMethodDependency(typeB,ts);
		addMethodDependency(typeC,ts);
		addMethodDependency(typeD,ts);
		
		MethodIndex<IJstType,IJstNode> mtdIndex1 = ts.getMethodIndex(TYPE_A);
		MethodIndex<IJstType,IJstNode> mtdIndex2 = ts.getMethodIndex(TYPE_B);
		MethodIndex<IJstType,IJstNode> mtdIndex3 = ts.getMethodIndex(TYPE_C);
		MethodIndex<IJstType,IJstNode> mtdIndex4 = ts.getMethodIndex(TYPE_D);
		
		assertEquals(3, mtdIndex1.size());
		assertEquals(1, mtdIndex2.size());
		assertEquals(1, mtdIndex3.size());
		assertEquals(1, mtdIndex4.size());
		
		List<IJstNode> dependents = ts.getMethodDependents(new MethodName(TYPE_A, "getC"));
		assertEquals(4, dependents.size());
		assertEquals(typeB, dependents.get(0).getOwnerType());
		
		dependents = ts.getMethodDependents(new MethodName(TYPE_A, "getName"));
		assertEquals(1, dependents.size());
		assertEquals(typeB, dependents.get(0).getOwnerType());
		
		dependents = ts.getMethodDependents(new MethodName(TYPE_B, "getName"));
		assertEquals(7, dependents.size());
		assertEquals(typeB, dependents.get(0).getOwnerType());
		assertEquals(typeC, dependents.get(1).getOwnerType());
		
		dependents = ts.getMethodDependents(new MethodName(TYPE_C, "getFirstName"));
		assertEquals(1, dependents.size());
		assertEquals(typeB, dependents.get(0).getOwnerType());
		
		// Remove jstMethod
		mtdIndex1.removeEntity("getName");
		assertEquals(2, mtdIndex1.size());
		
		// Validation/Errors
	}
	
	private void addMethodDependency(final IJstType type, TypeSpace<IJstType,IJstNode> ts){
		
		Map<MethodName,List<IJstNode>> map = getDependents(type);

		String mtdName;
		MethodIndex<IJstType,IJstNode> mtdIndex;
		TypeName tName;
		for(Entry<MethodName,List<IJstNode>> entry: map.entrySet()){
			tName = entry.getKey().typeName();
			mtdName = entry.getKey().methodName();
			mtdIndex = ts.getMethodIndex(tName);
			if (mtdIndex == null){
				mtdIndex = new MethodIndex<IJstType,IJstNode>(type);
				ts.addMethodIndex(tName, mtdIndex);
			}
			mtdIndex.addDependents(mtdName, entry.getValue());
		}
	}
	
	private Map<MethodName,List<IJstNode>> getDependents(final IJstType type){
		
		MethodDependencyVisitor visitor = new MethodDependencyVisitor();
		
		JstDepthFirstTraversal.accept(type, visitor);
		
		return visitor.getMethodDependencies();
	}
}
