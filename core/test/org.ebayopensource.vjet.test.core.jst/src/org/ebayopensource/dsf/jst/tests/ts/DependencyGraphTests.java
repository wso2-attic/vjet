/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.tests.ts;





import java.util.ArrayList;
import java.util.List;

import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstCache;
import org.ebayopensource.dsf.jst.ts.util.JstTypeDependencyHelper;
import org.ebayopensource.dsf.ts.TypeSpace;
import org.ebayopensource.dsf.ts.graph.DependencyGraph;
import org.ebayopensource.dsf.ts.group.Group;
import org.ebayopensource.dsf.ts.group.Project;



//@ModuleInfo(value="DsfPrebuild",subModuleId="JavaToJs")
public class DependencyGraphTests extends BaseTest {
	
	//@Category( { P1, UNIT })
	//@Description("Test dependency graph for a single group")
	public void testSingleGroup() {
		
		JstCache.getInstance().clear();
		
		DependencyGraph<IJstType> graph = createGraph(getClassList1());
		String typeA = TYPE_A.typeName();
		
		System.out.println(graph.toString());
		assertEquals(5, graph.getEntities().size());
		
		System.out.println("=== Dependencies ===");
		System.out.println("Direct");
		for (IJstType d: graph.getDirectDependencies(typeA, false)){
			System.out.println(d.getName());
		}
		assertEquals(3, graph.getDirectDependencies(typeA, false).size());
		
		System.out.println("Indirect");
		for (IJstType d: graph.getIndirectDependencies(typeA, false)){
			System.out.println(d.getName());
		}
		assertEquals(1, graph.getIndirectDependencies(typeA, false).size());
		
		System.out.println("All");
		for (IJstType d: graph.getAllDependencies(typeA, false)){
			System.out.println(d.getName());
		}
		assertEquals(4, graph.getAllDependencies(typeA, false).size());
		
		System.out.println("=== Dependents ===");
		System.out.println("Direct");
		for (IJstType d: graph.getDirectDependents(typeA, false)){
			System.out.println(d.getName());
		}
		assertEquals(3, graph.getDirectDependents(typeA, false).size());
		
		System.out.println("Indirect");
		for (IJstType d: graph.getIndirectDependents(typeA, false)){
			System.out.println(d.getName());
		}
		assertEquals(2, graph.getIndirectDependents(typeA, false).size());
		
		System.out.println("All");
		for (IJstType d: graph.getAllDependents(typeA, false)){
			System.out.println(d.getName());
		}
		assertEquals(3, graph.getAllDependents(typeA, false).size());
	}

	//@Category( { P1, UNIT })
	//@Description("Test dependency graph for a multiple groups")
	public void testMultiGroups() {
		
		System.out.println("*** Multi-groups ***");
		
		JstCache.getInstance().clear();
		
		DependencyGraph<IJstType> graph1 = createGraph(getClassList1());
		System.out.println("Graph 1");
		System.out.println(graph1.toString());
		assertEquals(5, graph1.getEntities().size());
		//TODO - Following needs to be updated after VjoJavaLib ser file is fixed
		assertEquals(2, graph1.getUnresolvedNodes().size());
		
		DependencyGraph<IJstType> graph2 = createGraph(getClassList2());
		System.out.println("Graph 2");
		System.out.println(graph2.toString());
		assertEquals(1, graph2.getEntities().size());
		assertEquals(1, graph2.getUnresolvedNodes().size());
	}
	
	//@Category( { P3, UNIT })
	//@Description("Test dependency graph for a combination of groups")
	public void testCombinedGroups(){
		
		System.out.println("*** Combined groups ***");
		
		JstCache.getInstance().clear();
		
		TypeSpace<IJstType,IJstNode> ts = new TypeSpace<IJstType,IJstNode>();
		
		Group<IJstType> group1 = new Project<IJstType>(PROJ_1, s_typeBuilder);
		Group<IJstType> group2 = new Project<IJstType>(PROJ_2, s_typeBuilder);
		ts.addGroup(group1).addGroup(group2);
		
		group1.addEntities(JstTypeDependencyHelper.toMap(getJstTypes(getClassList1())));
		group2.addEntities(JstTypeDependencyHelper.toMap(getJstTypes(getClassList2())));

		doAssertions(ts);
	}
	
	//@Category( { P4, UNIT })
	//@Description("Test additions of types to a dependency graph")
	public void testAddType(){
		
		
		System.out.println("*** Update graph ***");
		
		JstCache.getInstance().clear();
		
		TypeSpace<IJstType,IJstNode> ts = new TypeSpace<IJstType,IJstNode>();
		
		Group<IJstType> group1 = new Project<IJstType>(PROJ_1, s_typeBuilder);
		Group<IJstType> group2 = new Project<IJstType>(PROJ_2, s_typeBuilder);
		ts.addGroup(group1).addGroup(group2);
		
		List<IJstType> types1 = getJstTypes(getClassList1());
		List<IJstType> types2 = getJstTypes(getClassList2());
		
		group1.addEntity(TYPE_IA.typeName(), types1.get(0));
		group1.addEntity(TYPE_A.typeName(), types1.get(1));
		group1.addEntity(TYPE_B.typeName(), types1.get(2));
		group1.addEntity(TYPE_C.typeName(), types1.get(3));
		group1.addEntity(TYPE_D.typeName(), types1.get(4));
		group2.addEntity(TYPE_Ax.typeName(), types2.get(0));

		doAssertions(ts);
	}
	
	//@Category( { P1, UNIT })
	//@Description("Test addition of types to a dependency graph")
	public void testAddTypes(){
		
		System.out.println("*** Update graph ***");
		
		JstCache.getInstance().clear();
		
		TypeSpace<IJstType,IJstNode> ts = new TypeSpace<IJstType,IJstNode>();
		
		Group<IJstType> group1 = new Project<IJstType>(PROJ_1, s_typeBuilder);
		Group<IJstType> group2 = new Project<IJstType>(PROJ_2, s_typeBuilder);
		ts.addGroup(group1).addGroup(group2);
		
		List<IJstType> types1 = getJstTypes(getClassList1());
		IJstType typeIA = types1.get(0);
		IJstType typeA = types1.get(1);
		List<IJstType> typesBC = new ArrayList<IJstType>(2);
		typesBC.add(types1.get(2));
		typesBC.add(types1.get(3));
		List<IJstType> types2 = getJstTypes(getClassList2());
		
		group1.addEntity(TYPE_IA.typeName(), typeIA);
		group1.addEntity(TYPE_A.typeName(), typeA);
		group1.addEntities(JstTypeDependencyHelper.toMap(typesBC));
		group1.addEntity(TYPE_D.typeName(), types1.get(4));
		group2.addEntity(TYPE_Ax.typeName(), types2.get(0));

		doAssertions(ts);
	}
}
