/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.tests.ts;



import org.ebayopensource.dsf.jst.IJstProperty;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstProperty;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.dsf.jst.ts.JstQueryExecutor;
import org.ebayopensource.dsf.jst.ts.JstTypeSpaceMgr;
import org.ebayopensource.dsf.ts.event.property.RemovePropertyEvent;
import org.ebayopensource.dsf.ts.event.property.RenamePropertyEvent;
import org.ebayopensource.dsf.ts.method.MethodName;
import org.ebayopensource.dsf.ts.property.PropertyName;




//@ModuleInfo(value="DsfPrebuild",subModuleId="JavaToJs")
public class PropertyEventTests extends BaseTest {
	
	//@Category( { P1, UNIT })
	//@Description("Test renaming of property events")
	public void testRenamePropertyEvents(){

		JstTypeSpaceMgr tsMgr = getTypeSpaceManager();
		JstQueryExecutor queryExecutor = tsMgr.getQueryExecutor();
		IJstType typeA = queryExecutor.findType(TYPE_A);
		IJstType typeB = queryExecutor.findType(TYPE_B);
		IJstType typeC = queryExecutor.findType(TYPE_C);
		IJstProperty ptyCountry = typeA.getProperty("COUNTRY", true);
		
		assertEquals(3, queryExecutor.findPropertyDependentNodes(new PropertyName(TYPE_A, "COUNTRY")).size());
		assertEquals(2, queryExecutor.findPropertyDependentTypes(new PropertyName(TYPE_A, "COUNTRY")).size());
		assertEquals(typeB, queryExecutor.findPropertyDependentTypes(new PropertyName(TYPE_A, "COUNTRY")).get(0));
		assertEquals(typeC, queryExecutor.findPropertyDependentTypes(new PropertyName(TYPE_A, "COUNTRY")).get(1));
		
		// Rename "COUNTRY" to "CNTY"
		((JstProperty)ptyCountry).setName("CNTY");
		tsMgr.processEvent(new RenamePropertyEvent(new PropertyName(TYPE_A, "COUNTRY"), "CNTY", false));
		
		assertEquals(0, queryExecutor.findPropertyDependentNodes(new PropertyName(TYPE_A, "COUNTRY")).size());
		assertEquals(0, queryExecutor.findPropertyDependentTypes(new PropertyName(TYPE_A, "COUNTRY")).size());
		
		assertEquals(3, queryExecutor.findPropertyDependentNodes(new PropertyName(TYPE_A, "CNTY")).size());
		assertEquals(2, queryExecutor.findPropertyDependentTypes(new PropertyName(TYPE_A, "CNTY")).size());
		assertEquals(typeB, queryExecutor.findPropertyDependentTypes(new PropertyName(TYPE_A, "CNTY")).get(0));
		assertEquals(typeC, queryExecutor.findPropertyDependentTypes(new PropertyName(TYPE_A, "CNTY")).get(1));
		
		// Rename back
		((JstProperty)ptyCountry).setName("COUNTRY");
	}

	//@Category( { P1, UNIT })
	//@Description("Test removal/addition of property events")
	public void testRemoveAddPropertyEvents(){
		
		TestDataHelper.clear();

		JstTypeSpaceMgr tsMgr = getTypeSpaceManager();
		JstQueryExecutor queryExecutor = tsMgr.getQueryExecutor();
		IJstType typeA = queryExecutor.findType(TYPE_A);
		IJstType typeB = queryExecutor.findType(TYPE_B);
		IJstType typeC = queryExecutor.findType(TYPE_C);
		
		assertEquals(3, queryExecutor.findPropertyDependentNodes(new PropertyName(TYPE_A, "COUNTRY")).size());
		assertEquals(2, queryExecutor.findPropertyDependentTypes(new PropertyName(TYPE_A, "COUNTRY")).size());
		assertEquals(typeB, queryExecutor.findPropertyDependentTypes(new PropertyName(TYPE_A, "COUNTRY")).get(0));
		assertEquals(typeC, queryExecutor.findPropertyDependentTypes(new PropertyName(TYPE_A, "COUNTRY")).get(1));
		
		assertEquals(3, queryExecutor.findPropertyDependentNodes(new PropertyName(TYPE_A, "CITY")).size());
		assertEquals(2, queryExecutor.findPropertyDependentTypes(new PropertyName(TYPE_A, "CITY")).size());
		
		assertEquals(2, queryExecutor.findPropertyDependentNodes(new PropertyName(TYPE_B, "ADDRESS")).size());
		assertEquals(2, queryExecutor.findPropertyDependentTypes(new PropertyName(TYPE_B, "ADDRESS")).size());
		assertEquals(5, queryExecutor.findPropertyUsagesWithinNode(typeC).size());
		assertEquals(0, queryExecutor.findUnresolvedProperties(TYPE_C).size());
		
		// Remove "ADDRESS" in B
		tsMgr.processEvent(new RemovePropertyEvent(new PropertyName(TYPE_B, "ADDRESS"), true));
		((JstType)typeB).removeProperty("ADDRESS", false);
		
		assertEquals(0, queryExecutor.findPropertyDependentNodes(new PropertyName(TYPE_B, "ADDRESS")).size());
		assertEquals(0, queryExecutor.findPropertyDependentTypes(new PropertyName(TYPE_B, "ADDRESS")).size());
		assertEquals(5, queryExecutor.findPropertyUsagesWithinNode(typeC).size());
		assertEquals(1, queryExecutor.findUnresolvedProperties(TYPE_C).size());
		
		assertEquals(TYPE_B, queryExecutor.findUnresolvedProperties(TYPE_C).get(0).typeName());
		assertEquals("ADDRESS", queryExecutor.findUnresolvedProperties(TYPE_C).get(0).propertyName());
		assertEquals(2, queryExecutor.findPropertyDependentNodes(new PropertyName(TYPE_A, "COUNTRY")).size());
		assertEquals(2, queryExecutor.findPropertyDependentNodes(new PropertyName(TYPE_A, "CITY")).size());
		
		assertEquals(4, queryExecutor.findMethodDependentNodes(new MethodName(TYPE_A, "getSomething")).size());
		assertEquals(2, queryExecutor.findMethodDependentTypes(new MethodName(TYPE_A, "getSomething")).size());
		
		assertEquals(2, queryExecutor.findPropertyDependentNodes(new PropertyName(TYPE_B, "m_something")).size());
		assertEquals(2, queryExecutor.findPropertyDependentTypes(new PropertyName(TYPE_B, "m_something")).size());
		
		// Remove "m_something" in B
		tsMgr.processEvent(new RemovePropertyEvent(new PropertyName(TYPE_B, "m_something"), false));
		IJstProperty jstProperty = ((JstType)typeB).removeProperty("m_something", false);
		
		assertEquals(0, queryExecutor.findPropertyDependentNodes(new PropertyName(TYPE_B, "m_something")).size());
		assertEquals(0, queryExecutor.findPropertyDependentTypes(new PropertyName(TYPE_B, "m_something")).size());

		assertEquals(3, queryExecutor.findMethodDependentNodes(new MethodName(TYPE_A, "getSomething")).size());
		assertEquals(2, queryExecutor.findMethodDependentTypes(new MethodName(TYPE_A, "getSomething")).size());
		
		// Add "m_something" back B
		// TODO: uncomment after resolve impl of IJstParseController & IJstTypeLoader
//		((JstType)typeB).addProperty(jstProperty);
//		tsMgr.processEvent(new AddPropertyEvent(new PropertyName(TYPE_B, "m_something"), jstProperty));
//		
//		assertEquals(4, queryExecutor.findMethodDependentNodes(new MethodName(TYPE_A, "getSomething")).size());
//		assertEquals(2, queryExecutor.findMethodDependentTypes(new MethodName(TYPE_A, "getSomething")).size());
//		
//		assertEquals(2, queryExecutor.findPropertyDependentNodes(new PropertyName(TYPE_B, "m_something")).size());
//		assertEquals(2, queryExecutor.findPropertyDependentTypes(new PropertyName(TYPE_B, "m_something")).size());
	
	}
}
