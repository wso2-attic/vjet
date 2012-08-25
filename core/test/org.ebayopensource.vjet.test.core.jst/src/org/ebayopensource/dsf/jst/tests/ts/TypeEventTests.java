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

import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstCache;
import org.ebayopensource.dsf.jst.declaration.JstProperty;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.dsf.jst.term.JstIdentifier;
import org.ebayopensource.dsf.jst.ts.JstQueryExecutor;
import org.ebayopensource.dsf.jst.ts.JstTypeSpaceMgr;
import org.ebayopensource.dsf.ts.event.type.AddTypeEvent;
import org.ebayopensource.dsf.ts.event.type.ModifyTypeEvent;
import org.ebayopensource.dsf.ts.event.type.RemoveTypeEvent;
import org.ebayopensource.dsf.ts.event.type.RenameTypeEvent;
import org.ebayopensource.dsf.ts.method.MethodIndex;
import org.ebayopensource.dsf.ts.property.PropertyIndex;
import org.ebayopensource.dsf.ts.property.PropertyName;
import org.ebayopensource.dsf.ts.type.TypeName;
import org.ebayopensource.vjo.lib.IResourceResolver;
import org.ebayopensource.vjo.lib.LibManager;
import org.junit.Before;




//@ModuleInfo(value="DsfPrebuild",subModuleId="JavaToJs")
public class TypeEventTests extends BaseTest {
	
	@Before
	public void clearCache() {
		JstCache.getInstance().clear();
		
	}
	
	
	//@Category( { P1, UNIT })
	//@Description("Test addition of event types")
	public void testAddTypeEvents(){
		
		TestDataHelper.clear();

		final JstTypeSpaceMgr tsMgr = getTypeSpaceManager();

		JstQueryExecutor queryExecutor = tsMgr.getQueryExecutor();

		// Needed
		List<IJstType> needed = queryExecutor.findNeeded(TYPE_A);
		assertEquals(4, needed.size());

		needed = queryExecutor.findNeeded(TYPE_B);
		assertEquals(2, needed.size());

		needed = queryExecutor.findNeeded(TYPE_C);
		assertEquals(2, needed.size());

		needed = queryExecutor.findNeeded(TYPE_Ax);
		assertEquals(0, needed.size());

		// Properties
		assertEquals(7, tsMgr.getTypeSpace().getPropertyIndex(TYPE_A).size());
		PropertyIndex<IJstType,IJstNode> ptyIndex = tsMgr.getTypeSpace().getPropertyIndex(TYPE_A);
		assertEquals(3, ptyIndex.getDependents("COUNTRY").size());
		assertEquals(2, ptyIndex.getDependents("m_pupulation").size());

		// Methods
		assertEquals(3, tsMgr.getTypeSpace().getMethodIndex(TYPE_A).size());
		MethodIndex<IJstType,IJstNode> mtdIndex = tsMgr.getTypeSpace().getMethodIndex(TYPE_A);
		assertEquals(1, mtdIndex.getDependents("getName").size());
		assertEquals(4, mtdIndex.getDependents("getC").size());

		MethodIndex index = tsMgr.getTypeSpace().getMethodIndex(TYPE_B);

		List dependents = index.getDependents("getName");

		assertEquals(7, dependents.size());
	}

	//@Category( { P1, UNIT })
	//@Description("Test modifications of type events")
//	@Ignore("problem with library manager java 2 js renaming")
	public void testModifyTypeEvents(){

		JstTypeSpaceMgr tsMgr = getTypeSpaceManager();
		JstQueryExecutor queryExecutor = tsMgr.getQueryExecutor();
		IJstType typeA = queryExecutor.findType(TYPE_A);
		IJstType typeB = queryExecutor.findType(TYPE_B);
		IJstType typeC = queryExecutor.findType(TYPE_C);
		IJstType typeD = queryExecutor.findType(TYPE_D);

		List<IJstType> needed = queryExecutor.findNeeded(TYPE_A);
		assertEquals(4, needed.size());

		// direct dependency of type B
		assertEquals(1, tsMgr.getTypeSpace().getDirectDependencies(TYPE_B).size());

		PropertyIndex ptyIdx = tsMgr.getTypeSpace().getPropertyIndex(TYPE_B);

		assertEquals(2, ptyIdx.size());

		assertEquals(2, ptyIdx.getDependents("ADDRESS").size());

		MethodIndex mtdIdx = tsMgr.getTypeSpace().getMethodIndex(TYPE_B);

		assertEquals(1, mtdIdx.size());

		assertEquals(7, mtdIdx.getDependents("getName").size());

		// Modify B - remove import
		((JstType)typeB).removeImport(((JstType)typeA));
		((JstType)typeB).clearExtends();
		ModifyTypeEvent event = new ModifyTypeEvent(TYPE_B, typeB);

		tsMgr.processEvent(event);

		ptyIdx = tsMgr.getTypeSpace().getPropertyIndex(TYPE_B);

		assertEquals(2, ptyIdx.size());

		assertEquals(2, ptyIdx.getDependents("ADDRESS").size());

		mtdIdx = tsMgr.getTypeSpace().getMethodIndex(TYPE_B);

		assertEquals(1, mtdIdx.size());

		assertEquals(7, mtdIdx.getDependents("getName").size());

		// direct dependency of type B is removed
		 
		assertEquals(0, tsMgr.getTypeSpace().getDirectDependencies(TYPE_B).size());

		// - Missing types
		//TODO - Following needs to be updated after VjoJavaLib ser file is fixed
//		assertTrue(" actual MissingTypes : "+queryExecutor.findMissingTypes(), queryExecutor.findMissingTypes().size() == 1);
 

		// - Unresolved dependents
		//TODO - Following needs to be updated after VjoJavaLib ser file is fixed
//		assertTrue(" actual UnresolvedDependents : "+queryExecutor.findUnresolvedDependents(), queryExecutor.findUnresolvedDependents().size() == 1);

		// - needed
		needed = queryExecutor.findNeeded(TYPE_A);
		assertEquals(3, needed.size());

		needed = queryExecutor.findNeeded(TYPE_B);
		assertEquals(2, needed.size());

		needed = queryExecutor.findNeeded(TYPE_C);
		assertEquals(2, needed.size());

		needed = queryExecutor.findNeeded(TYPE_Ax);
		assertEquals(0, needed.size());

		// - Properties
		assertEquals(7, tsMgr.getTypeSpace().getPropertyIndex(TYPE_A).size());

		assertEquals(2, tsMgr.getTypeSpace().getPropertyIndex(TYPE_B).size());

		PropertyName ptyName = new PropertyName(TYPE_D, "m_d_instance_var");

		List<IJstNode> list =  queryExecutor.findPropertyDependentNodes(ptyName);

		assertEquals(1, list.size());

		// - Methods
		assertEquals(3, tsMgr.getTypeSpace().getMethodIndex(TYPE_A).size());

		assertEquals(1, tsMgr.getTypeSpace().getMethodIndex(TYPE_B).size());

		MethodIndex index = tsMgr.getTypeSpace().getMethodIndex(TYPE_B);

		List dependents = index.getDependents("getName");

		assertEquals(7, dependents.size());

		// Modify B - add import
		((JstType)typeB).addImport(((JstType)typeA));
		JstIdentifier id = new JstIdentifier("m_d");
		id.setJstBinding(typeD.getProperty("m_d_instance_var"));
		((JstType)typeB).addProperty(new JstProperty(typeD, "testD", id));
		event = new ModifyTypeEvent(TYPE_B, typeB);

		tsMgr.processEvent(event);

		// - Missing types
		//TODO - Following needs to be updated after VjoJavaLib ser file is fixed
//		assertEquals(1, queryExecutor.findMissingTypes().size());

		// - Unresolved dependents
		//TODO - Following needs to be updated after VjoJavaLib ser file is fixed
//		assertEquals(1, queryExecutor.findUnresolvedDependents().size());

		// direct dependency of type B is added
		assertEquals(1, tsMgr.getTypeSpace().getDirectDependencies(TYPE_B).size());

		// - needed
		needed = queryExecutor.findNeeded(TYPE_A);
		assertEquals(4, needed.size());

		needed = queryExecutor.findNeeded(TYPE_B);
		assertEquals(2, needed.size());

		needed = queryExecutor.findNeeded(TYPE_C);
		assertEquals(2, needed.size());

		needed = queryExecutor.findNeeded(TYPE_Ax);
		assertEquals(0, needed.size());

		// - Properties
		assertEquals(7, tsMgr.getTypeSpace().getPropertyIndex(TYPE_A).size());

		assertEquals(2, tsMgr.getTypeSpace().getPropertyIndex(TYPE_B).size());

		list =  queryExecutor.findPropertyDependentNodes(ptyName);

		assertEquals(2, list.size());

		// - Methods
		assertEquals(3, tsMgr.getTypeSpace().getMethodIndex(TYPE_A).size());

		assertEquals(1, tsMgr.getTypeSpace().getMethodIndex(TYPE_B).size());

		index = tsMgr.getTypeSpace().getMethodIndex(TYPE_B);

		dependents = index.getDependents("getName");

		assertEquals(7, dependents.size());
	}

	//@Category( { P1, UNIT })
	//@Description("Test renaming of type events")
	public void testRenameTypeEvents(){

		final JstTypeSpaceMgr tsMgr = getTypeSpaceManager();
		final JstQueryExecutor queryExecutor = tsMgr.getQueryExecutor();
		final IJstType typeA = queryExecutor.findType(TYPE_A);

		// Rename A
		((JstType)typeA).setSimpleName("A1");
		tsMgr.processEvent(new RenameTypeEvent(TYPE_A, TYPE_A1.typeName()));

//			public void onComplete(EventListenerStatus<IJstType> status){
				IJstType typeA1 = queryExecutor.findType(TYPE_A1);
				assertEquals(TYPE_A1.typeName(), typeA.getName());
				assertEquals(typeA, typeA1);

				// - Missing types
//				assertEquals(2, queryExecutor.findMissingTypes().size());

				// - Unresolved dependents
//				assertEquals(2, queryExecutor.findUnresolvedDependents().size());

				// - needed
				List<IJstType> needed = queryExecutor.findNeeded(TYPE_A);
				assertEquals(0, needed.size());

				needed = queryExecutor.findNeeded(TYPE_A1);
				assertEquals(4, needed.size());

				needed = queryExecutor.findNeeded(TYPE_B);
				assertEquals(2, needed.size());

				needed = queryExecutor.findNeeded(TYPE_C);
				assertEquals(2, needed.size());

				needed = queryExecutor.findNeeded(TYPE_Ax);
				assertEquals(0, needed.size());

				// - Properties
				assertEquals(null, tsMgr.getTypeSpace().getPropertyIndex(TYPE_A));
				assertEquals(7, tsMgr.getTypeSpace().getPropertyIndex(TYPE_A1).size());

				// - Methods
				assertEquals(null, tsMgr.getTypeSpace().getMethodIndex(TYPE_A));
				assertEquals(3, tsMgr.getTypeSpace().getMethodIndex(TYPE_A1).size());

				System.out.println("=== Aynsc RenameTypeEvent Done ===");
//			}
//		});
				
				tsMgr.processEvent(new RenameTypeEvent(TYPE_A1, TYPE_A.typeName()));
				
				// Rename back
				((JstType)typeA).setSimpleName("A");
	}

	//@Category( { P1, UNIT })
	//@Description("Test removal of type events")
	public void testRemoveTypeEvents() throws Exception{

		final JstTypeSpaceMgr tsMgr = getTypeSpaceManager();
		final JstQueryExecutor queryExecutor = tsMgr.getQueryExecutor();
		final IJstType typeA = queryExecutor.findType(TYPE_A);
		final IJstType typeB = queryExecutor.findType(TYPE_B);
		final IJstType typeC = queryExecutor.findType(TYPE_C);
		final IJstType typeD = queryExecutor.findType(TYPE_D);

		MethodIndex<IJstType,IJstNode> mtdIndex = tsMgr.getTypeSpace().getMethodIndex(TYPE_A);

		assertEquals(1, mtdIndex.getDependents("getName").size());
		assertEquals(4, mtdIndex.getDependents("getC").size());
		assertEquals(4, mtdIndex.getDependents("getSomething").size());

		// Remove B
		tsMgr.processEvent(new RemoveTypeEvent(TYPE_B));



//				 - Missing types
			IJstType typeB2 = queryExecutor.findType(TYPE_B);
			assertNull(typeB2);
//			assertEquals(3, queryExecutor.findMissingTypes().size());
//			assertEquals(TYPE_B, queryExecutor.findMissingTypes().get(1));

			// - Unresolved dependents
			Map<TypeName,List<IJstType>> unsolved = queryExecutor.findUnresolvedDependents();
//			assertEquals(3, unsolved.size());

			// skip first element in the unsolved node collection as it's vjo.Object
			Object[] keyArray = unsolved.keySet().toArray();
			Object[] valueArray = unsolved.values().toArray();

//			assertEquals(TYPE_B, keyArray[1]);
//			assertEquals(2, ((List)valueArray[1]).size());
//			assertEquals(typeC, ((List)valueArray[1]).get(0));
//			assertEquals(typeD, ((List)valueArray[1]).get(1));

			// - needed
			List<IJstType> needed = queryExecutor.findNeeded(TYPE_A);
			assertEquals(3, needed.size());

			needed = queryExecutor.findNeeded(TYPE_B);
			assertEquals(2, needed.size());

			needed = queryExecutor.findNeeded(TYPE_C);
			assertEquals(2, needed.size());

			needed = queryExecutor.findNeeded(TYPE_Ax);
			assertEquals(0, needed.size());

			// - Properties
			assertEquals(null, tsMgr.getTypeSpace().getPropertyIndex(TYPE_B));
			PropertyIndex<IJstType,IJstNode> ptyIndex = tsMgr.getTypeSpace().getPropertyIndex(TYPE_A);
			assertEquals(2, ptyIndex.getDependents("COUNTRY").size());
			assertEquals(1, ptyIndex.getDependents("m_pupulation").size());

			// - Methods
			assertEquals(null, tsMgr.getTypeSpace().getMethodIndex(TYPE_B));
			mtdIndex = tsMgr.getTypeSpace().getMethodIndex(TYPE_A);
			assertEquals(0, mtdIndex.getDependents("getName").size());
			assertEquals(3, mtdIndex.getDependents("getC").size());
			assertEquals(2, mtdIndex.getDependents("getSomething").size());

			System.out.println("=== Aynsc RemoveTypeEvent Done ===");




		// Add B back
//		String source = FileUtils.getResourceAsString(B.class, "B.vjo");
		tsMgr.processEvent(new AddTypeEvent(new TypeName(TYPE_B.groupName(), TYPE_B.typeName()), typeB));
		
//				assertEquals(EventListenerStatus.Code.Successful,status.getCode());
//				 - Missing types
				assertEquals(typeB, queryExecutor.findType(TYPE_B));
//				assertEquals(2, queryExecutor.findMissingTypes().size());

				// - Unresolved dependents
				unsolved = queryExecutor.findUnresolvedDependents();
//				assertEquals(2, unsolved.size());

				// - needed
				needed = queryExecutor.findNeeded(TYPE_A);
				assertEquals(4, needed.size());

				needed = queryExecutor.findNeeded(TYPE_B);
				assertEquals(2, needed.size());

				needed = queryExecutor.findNeeded(TYPE_C);
				assertEquals(2, needed.size());

				needed = queryExecutor.findNeeded(TYPE_Ax);
				assertEquals(0, needed.size());

				// - Properties
				assertEquals(7, tsMgr.getTypeSpace().getPropertyIndex(TYPE_A).size());
				ptyIndex = tsMgr.getTypeSpace().getPropertyIndex(TYPE_A);
				assertEquals(3, ptyIndex.getDependents("COUNTRY").size());
				assertEquals(2, ptyIndex.getDependents("m_pupulation").size());

				// - Methods
				assertEquals(3, tsMgr.getTypeSpace().getMethodIndex(TYPE_A).size());
				mtdIndex = tsMgr.getTypeSpace().getMethodIndex(TYPE_A);
				assertEquals(1, mtdIndex.getDependents("getName").size());
				assertEquals(4, mtdIndex.getDependents("getC").size());

				System.out.println("=== Aynsc AddTypeEvent Done ===");
			}

}
