/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.tests.ts;



import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstMethod;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.dsf.jst.ts.JstQueryExecutor;
import org.ebayopensource.dsf.jst.ts.JstTypeSpaceMgr;
import org.ebayopensource.dsf.ts.event.method.RemoveMethodEvent;
import org.ebayopensource.dsf.ts.event.method.RenameMethodEvent;
import org.ebayopensource.dsf.ts.method.MethodName;
import org.ebayopensource.dsf.ts.property.PropertyName;




//@ModuleInfo(value="DsfPrebuild",subModuleId="JavaToJs")
public class MethodEventTests extends BaseTest {
	
	//@Category( { P1, UNIT })
	//@Description("Test rename of method events")
	public void testRenameMethodEvents(){

		JstTypeSpaceMgr tsMgr = getTypeSpaceManager();
		JstQueryExecutor queryExecutor = tsMgr.getQueryExecutor();
		IJstType typeA = queryExecutor.findType(TYPE_A);
		IJstType typeB = queryExecutor.findType(TYPE_B);
		IJstMethod mtdGetName = typeA.getMethod("getName",false);
		
		assertEquals(1, queryExecutor.findMethodDependentNodes(new MethodName(TYPE_A, "getName")).size());
		assertEquals(1, queryExecutor.findMethodDependentTypes(new MethodName(TYPE_A, "getName")).size());
		assertEquals(typeB, queryExecutor.findMethodDependentTypes(new MethodName(TYPE_A, "getName")).get(0));
		
		// Rename "getName" to "getFullName"
		((JstMethod)mtdGetName).setName("getFullName");
		tsMgr.processEvent(new RenameMethodEvent(new MethodName(TYPE_A, "getName"), "getFullName", false));
		
		assertEquals(0, queryExecutor.findMethodDependentNodes(new MethodName(TYPE_A, "getName")).size());
		assertEquals(0, queryExecutor.findMethodDependentTypes(new MethodName(TYPE_A, "getName")).size());
		
		assertEquals(1, queryExecutor.findMethodDependentNodes(new MethodName(TYPE_A, "getFullName")).size());
		assertEquals(1, queryExecutor.findMethodDependentTypes(new MethodName(TYPE_A, "getFullName")).size());
		assertEquals(typeB, queryExecutor.findMethodDependentTypes(new MethodName(TYPE_A, "getFullName")).get(0));
		
		// Rename back
		((JstMethod)mtdGetName).setName("getName");
	}

	//@Category( { P1, UNIT })
	//@Description("Test removal of method events")
	public void testRemoveAddMethodEvents(){

		JstTypeSpaceMgr tsMgr = getTypeSpaceManager();
		JstQueryExecutor queryExecutor = tsMgr.getQueryExecutor();
		IJstType typeB = queryExecutor.findType(TYPE_B);
		IJstType typeC = queryExecutor.findType(TYPE_C);
		
		assertEquals(1, queryExecutor.findMethodDependentNodes(new MethodName(TYPE_A, "getName")).size());
		assertEquals(1, queryExecutor.findMethodDependentTypes(new MethodName(TYPE_A, "getName")).size());
		assertEquals(typeB, queryExecutor.findMethodDependentTypes(new MethodName(TYPE_A, "getName")).get(0));
		
		assertEquals(5, queryExecutor.findMethodUsagesWithinNode(typeB).size());
		assertEquals(7, queryExecutor.findMethodDependentNodes(new MethodName(TYPE_B, "getName")).size());
		assertEquals(3, queryExecutor.findMethodDependentTypes(new MethodName(TYPE_B, "getName")).size());
		
		assertEquals(2, queryExecutor.findMethodUsagesWithinNode(typeC).size());
		assertEquals(0, queryExecutor.findUnresolvedMethods(TYPE_C).size());
		
		assertEquals(3, queryExecutor.findPropertyDependentNodes(new PropertyName(TYPE_A, "CITY")).size());
		assertEquals(2, queryExecutor.findPropertyDependentTypes(new PropertyName(TYPE_A, "CITY")).size());
		
		// Remove "getName" in TYPE_B
		tsMgr.processEvent(new RemoveMethodEvent(new MethodName(TYPE_B, "getName"), false));
		IJstMethod jstMethod = ((JstType)typeB).removeMethod("getName",false);
		
		assertEquals(2, queryExecutor.findPropertyDependentNodes(new PropertyName(TYPE_A, "CITY")).size());
		assertEquals(2, queryExecutor.findPropertyDependentTypes(new PropertyName(TYPE_A, "CITY")).size());

		assertEquals(0, queryExecutor.findMethodDependentNodes(new MethodName(TYPE_B, "getName")).size());
		assertEquals(0, queryExecutor.findMethodDependentTypes(new MethodName(TYPE_B, "getName")).size());
		
		assertEquals(1, queryExecutor.findMethodUsagesWithinNode(typeC).size());
		assertEquals(1, queryExecutor.findUnresolvedMethods(TYPE_C).size());
		assertEquals(TYPE_B, queryExecutor.findUnresolvedMethods(TYPE_C).get(0).typeName());
		assertEquals("getName", queryExecutor.findUnresolvedMethods(TYPE_C).get(0).methodName());
		
		assertEquals(0, queryExecutor.findMethodDependentNodes(new MethodName(TYPE_A, "getName")).size());
		assertEquals(0, queryExecutor.findMethodDependentTypes(new MethodName(TYPE_A, "getName")).size());
		
		// Add back "getName" in TYPE_B after it's removed
		// TODO: uncomment after resolve impl of IJstParseController & IJstTypeLoader
//		((JstType)typeB).addMethod(jstMethod);
//		tsMgr.processEvent(new AddMethodEvent(new MethodName(TYPE_B, "getName"), jstMethod));
//		assertEquals(3, queryExecutor.findPropertyDependentNodes(new PropertyName(TYPE_A, "CITY")).size());
//		assertEquals(2, queryExecutor.findPropertyDependentTypes(new PropertyName(TYPE_A, "CITY")).size());
//		
//		assertEquals(7, queryExecutor.findMethodDependentNodes(new MethodName(TYPE_B, "getName")).size());
//		assertEquals(3, queryExecutor.findMethodDependentTypes(new MethodName(TYPE_B, "getName")).size());
//		
//		assertEquals(1, queryExecutor.findMethodDependentNodes(new MethodName(TYPE_A, "getName")).size());
//		assertEquals(1, queryExecutor.findMethodDependentTypes(new MethodName(TYPE_A, "getName")).size());	
	}
}
