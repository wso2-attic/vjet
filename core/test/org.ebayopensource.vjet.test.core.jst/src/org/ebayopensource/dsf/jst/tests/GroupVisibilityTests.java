/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.tests;

import junit.framework.Assert;

import org.junit.Test;

import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstFactory;
import org.ebayopensource.dsf.jst.ts.util.JstTypeDependencyCollector;
import org.ebayopensource.dsf.ts.TypeSpace;
import org.ebayopensource.dsf.ts.group.Group;
import org.ebayopensource.dsf.ts.type.TypeName;

public class GroupVisibilityTests {
	
	private static IJstType TYPE_A = JstFactory.getInstance().createJstType("A", false);
	private static IJstType TYPE_B = JstFactory.getInstance().createJstType("B", false);
	private static IJstType TYPE_C = JstFactory.getInstance().createJstType("C", false);
	
	private static JstTypeDependencyCollector DEPENDENCY_COLLECTOR = new JstTypeDependencyCollector();

	@Test
	public void testIsTypeVisible(){
		
		Group<IJstType> groupX = new Group<IJstType>("X", DEPENDENCY_COLLECTOR);
		Group<IJstType> groupY = new Group<IJstType>("Y", DEPENDENCY_COLLECTOR);
		Group<IJstType> groupZ = new Group<IJstType>("Z", DEPENDENCY_COLLECTOR);
		
		groupX.addEntity(TYPE_A.getName(), TYPE_A);
		groupY.addEntity(TYPE_B.getName(), TYPE_B);
		groupZ.addEntity(TYPE_C.getName(), TYPE_C);
		
		TypeSpace<IJstType,IJstNode> ts = new TypeSpace<IJstType,IJstNode>()
			.addGroup(groupX)
			.addGroup(groupY)
			.addGroup(groupZ);
		
		TypeName typeNameA = new TypeName(groupX.getName(), TYPE_A.getName());
		TypeName typeNameB = new TypeName(groupY.getName(), TYPE_B.getName());
		TypeName typeNameC = new TypeName(groupZ.getName(), TYPE_C.getName());
		
		// No group dependency
		
		// - Type A
		Assert.assertFalse(ts.isTypeVisible(TYPE_A.getName(), groupY));
		Assert.assertFalse(ts.isTypeVisible(TYPE_A.getName(), groupZ));
		
		Assert.assertFalse(ts.isTypeVisible(typeNameA, groupY));
		Assert.assertFalse(ts.isTypeVisible(typeNameA, groupZ));
		
		// - Type B
		Assert.assertFalse(ts.isTypeVisible(TYPE_B.getName(), groupX));
		Assert.assertFalse(ts.isTypeVisible(TYPE_B.getName(), groupZ));
		
		Assert.assertFalse(ts.isTypeVisible(typeNameB, groupX));
		Assert.assertFalse(ts.isTypeVisible(typeNameB, groupZ));
		
		// - Type C
		Assert.assertFalse(ts.isTypeVisible(TYPE_C.getName(), groupX));
		Assert.assertFalse(ts.isTypeVisible(TYPE_C.getName(), groupY));
		
		Assert.assertFalse(ts.isTypeVisible(typeNameC, groupX));
		Assert.assertFalse(ts.isTypeVisible(typeNameC, groupY));
		
		// Add dependency: X -> Y
		groupX.addGroupDependency(groupY);
		
		// - Type A
		Assert.assertFalse(ts.isTypeVisible(TYPE_A.getName(), groupY));
		Assert.assertFalse(ts.isTypeVisible(TYPE_A.getName(), groupZ));
		
		Assert.assertFalse(ts.isTypeVisible(typeNameA, groupY));
		Assert.assertFalse(ts.isTypeVisible(typeNameA, groupZ));
		
		// - Type B
		Assert.assertTrue(ts.isTypeVisible(TYPE_B.getName(), groupX));
		Assert.assertFalse(ts.isTypeVisible(TYPE_B.getName(), groupZ));
		
		Assert.assertTrue(ts.isTypeVisible(typeNameB, groupX));
		Assert.assertFalse(ts.isTypeVisible(typeNameB, groupZ));
		
		// - Type C
		Assert.assertFalse(ts.isTypeVisible(TYPE_C.getName(), groupX));
		Assert.assertFalse(ts.isTypeVisible(TYPE_C.getName(), groupY));
		
		Assert.assertFalse(ts.isTypeVisible(typeNameC, groupX));
		Assert.assertFalse(ts.isTypeVisible(typeNameC, groupY));
		
		// Add dependency: Y -> Z
		groupY.addGroupDependency(groupZ);
		
		// - Type A
		Assert.assertFalse(ts.isTypeVisible(TYPE_A.getName(), groupY));
		Assert.assertFalse(ts.isTypeVisible(TYPE_A.getName(), groupZ));
		
		Assert.assertFalse(ts.isTypeVisible(typeNameA, groupY));
		Assert.assertFalse(ts.isTypeVisible(typeNameA, groupZ));
		
		// - Type B
		Assert.assertTrue(ts.isTypeVisible(TYPE_B.getName(), groupX));
		Assert.assertFalse(ts.isTypeVisible(TYPE_B.getName(), groupZ));
		
		Assert.assertTrue(ts.isTypeVisible(typeNameB, groupX));
		Assert.assertFalse(ts.isTypeVisible(typeNameB, groupZ));
		
		// - Type C
		Assert.assertTrue(ts.isTypeVisible(TYPE_C.getName(), groupX));
		Assert.assertTrue(ts.isTypeVisible(TYPE_C.getName(), groupY));
		
		Assert.assertTrue(ts.isTypeVisible(typeNameC, groupX));
		Assert.assertTrue(ts.isTypeVisible(typeNameC, groupY));
		
		// Add dependency: X -> Z
		groupX.addGroupDependency(groupZ);
		
		// - Type A
		Assert.assertFalse(ts.isTypeVisible(TYPE_A.getName(), groupY));
		Assert.assertFalse(ts.isTypeVisible(TYPE_A.getName(), groupZ));
		
		Assert.assertFalse(ts.isTypeVisible(typeNameA, groupY));
		Assert.assertFalse(ts.isTypeVisible(typeNameA, groupZ));
		
		// - Type B
		Assert.assertTrue(ts.isTypeVisible(TYPE_B.getName(), groupX));
		Assert.assertFalse(ts.isTypeVisible(TYPE_B.getName(), groupZ));
		
		Assert.assertTrue(ts.isTypeVisible(typeNameB, groupX));
		Assert.assertFalse(ts.isTypeVisible(typeNameB, groupZ));
		
		// - Type C
		Assert.assertTrue(ts.isTypeVisible(TYPE_C.getName(), groupX));
		Assert.assertTrue(ts.isTypeVisible(TYPE_C.getName(), groupY));
		
		Assert.assertTrue(ts.isTypeVisible(typeNameC, groupX));
		Assert.assertTrue(ts.isTypeVisible(typeNameC, groupY));
		
		// Add dependency: Z -> X
		groupZ.addGroupDependency(groupX);
		
		// - Type A
		Assert.assertTrue(ts.isTypeVisible(TYPE_A.getName(), groupY));
		Assert.assertTrue(ts.isTypeVisible(TYPE_A.getName(), groupZ));
		
		Assert.assertTrue(ts.isTypeVisible(typeNameA, groupY));
		Assert.assertTrue(ts.isTypeVisible(typeNameA, groupZ));
		
		// - Type B
		Assert.assertTrue(ts.isTypeVisible(TYPE_B.getName(), groupX));
		Assert.assertTrue(ts.isTypeVisible(TYPE_B.getName(), groupZ));
		
		Assert.assertTrue(ts.isTypeVisible(typeNameB, groupX));
		Assert.assertTrue(ts.isTypeVisible(typeNameB, groupZ));
		
		// - Type C
		Assert.assertTrue(ts.isTypeVisible(TYPE_C.getName(), groupX));
		Assert.assertTrue(ts.isTypeVisible(TYPE_C.getName(), groupY));
		
		Assert.assertTrue(ts.isTypeVisible(typeNameC, groupX));
		Assert.assertTrue(ts.isTypeVisible(typeNameC, groupY));
		
		// Remove dependency: X -> Y
		groupX.removeGroupDependency(groupY);
		
		// - Type A
		Assert.assertTrue(ts.isTypeVisible(TYPE_A.getName(), groupY));
		Assert.assertTrue(ts.isTypeVisible(TYPE_A.getName(), groupZ));
		
		Assert.assertTrue(ts.isTypeVisible(typeNameA, groupY));
		Assert.assertTrue(ts.isTypeVisible(typeNameA, groupZ));
		
		// - Type B
		Assert.assertFalse(ts.isTypeVisible(TYPE_B.getName(), groupX));
		Assert.assertFalse(ts.isTypeVisible(TYPE_B.getName(), groupZ));
		
		Assert.assertFalse(ts.isTypeVisible(typeNameB, groupX));
		Assert.assertFalse(ts.isTypeVisible(typeNameB, groupZ));
		
		// - Type C
		Assert.assertTrue(ts.isTypeVisible(TYPE_C.getName(), groupX));
		Assert.assertTrue(ts.isTypeVisible(TYPE_C.getName(), groupY));
		
		Assert.assertTrue(ts.isTypeVisible(typeNameC, groupX));
		Assert.assertTrue(ts.isTypeVisible(typeNameC, groupY));
		
		// Remove dependency: X -> Z
		groupX.removeGroupDependency(groupZ);
		
		// - Type A
		Assert.assertTrue(ts.isTypeVisible(TYPE_A.getName(), groupY));
		Assert.assertTrue(ts.isTypeVisible(TYPE_A.getName(), groupZ));
		
		Assert.assertTrue(ts.isTypeVisible(typeNameA, groupY));
		Assert.assertTrue(ts.isTypeVisible(typeNameA, groupZ));
		
		// - Type B
		Assert.assertFalse(ts.isTypeVisible(TYPE_B.getName(), groupX));
		Assert.assertFalse(ts.isTypeVisible(TYPE_B.getName(), groupZ));
		
		Assert.assertFalse(ts.isTypeVisible(typeNameB, groupX));
		Assert.assertFalse(ts.isTypeVisible(typeNameB, groupZ));
		
		// - Type C
		Assert.assertFalse(ts.isTypeVisible(TYPE_C.getName(), groupX));
		Assert.assertTrue(ts.isTypeVisible(TYPE_C.getName(), groupY));
		
		Assert.assertFalse(ts.isTypeVisible(typeNameC, groupX));
		Assert.assertTrue(ts.isTypeVisible(typeNameC, groupY));
		
		// Remove dependency: Y -> Z
		groupY.removeGroupDependency(groupZ);
		
		// - Type A
		Assert.assertFalse(ts.isTypeVisible(TYPE_A.getName(), groupY));
		Assert.assertTrue(ts.isTypeVisible(TYPE_A.getName(), groupZ));
		
		Assert.assertFalse(ts.isTypeVisible(typeNameA, groupY));
		Assert.assertTrue(ts.isTypeVisible(typeNameA, groupZ));
		
		// - Type B
		Assert.assertFalse(ts.isTypeVisible(TYPE_B.getName(), groupX));
		Assert.assertFalse(ts.isTypeVisible(TYPE_B.getName(), groupZ));
		
		Assert.assertFalse(ts.isTypeVisible(typeNameB, groupX));
		Assert.assertFalse(ts.isTypeVisible(typeNameB, groupZ));
		
		// - Type C
		Assert.assertFalse(ts.isTypeVisible(TYPE_C.getName(), groupX));
		Assert.assertFalse(ts.isTypeVisible(TYPE_C.getName(), groupY));
		
		Assert.assertFalse(ts.isTypeVisible(typeNameC, groupX));
		Assert.assertFalse(ts.isTypeVisible(typeNameC, groupY));
		
		// Remove dependency: Z -> X
		groupZ.removeGroupDependency(groupX);
		
		// - Type A
		Assert.assertFalse(ts.isTypeVisible(TYPE_A.getName(), groupY));
		Assert.assertFalse(ts.isTypeVisible(TYPE_A.getName(), groupZ));
		
		Assert.assertFalse(ts.isTypeVisible(typeNameA, groupY));
		Assert.assertFalse(ts.isTypeVisible(typeNameA, groupZ));
		
		// - Type B
		Assert.assertFalse(ts.isTypeVisible(TYPE_B.getName(), groupX));
		Assert.assertFalse(ts.isTypeVisible(TYPE_B.getName(), groupZ));
		
		Assert.assertFalse(ts.isTypeVisible(typeNameB, groupX));
		Assert.assertFalse(ts.isTypeVisible(typeNameB, groupZ));
		
		// - Type C
		Assert.assertFalse(ts.isTypeVisible(TYPE_C.getName(), groupX));
		Assert.assertFalse(ts.isTypeVisible(TYPE_C.getName(), groupY));
		
		Assert.assertFalse(ts.isTypeVisible(typeNameC, groupX));
		Assert.assertFalse(ts.isTypeVisible(typeNameC, groupY));
	}
	
	@Test
	public void testGetVisibleType(){
		
		Group<IJstType> groupX = new Group<IJstType>("X", DEPENDENCY_COLLECTOR);
		Group<IJstType> groupY = new Group<IJstType>("Y", DEPENDENCY_COLLECTOR);
		Group<IJstType> groupZ = new Group<IJstType>("Z", DEPENDENCY_COLLECTOR);
		
		groupX.addEntity(TYPE_A.getName(), TYPE_A);
		groupY.addEntity(TYPE_B.getName(), TYPE_B);
		groupZ.addEntity(TYPE_C.getName(), TYPE_C);
		
		TypeSpace<IJstType,IJstNode> ts = new TypeSpace<IJstType,IJstNode>()
			.addGroup(groupX)
			.addGroup(groupY)
			.addGroup(groupZ);
		
		TypeName typeNameA = new TypeName(groupX.getName(), TYPE_A.getName());
		TypeName typeNameB = new TypeName(groupY.getName(), TYPE_B.getName());
		TypeName typeNameC = new TypeName(groupZ.getName(), TYPE_C.getName());
		
		// No group dependency
		
		// - Type A
		Assert.assertEquals(0, ts.getVisibleType(TYPE_A.getName(), groupY).size());
		Assert.assertEquals(0, ts.getVisibleType(TYPE_A.getName(), groupZ).size());
		
		Assert.assertEquals(null, ts.getVisibleType(typeNameA, groupY));
		Assert.assertEquals(null, ts.getVisibleType(typeNameA, groupZ));
		
		// - Type B
		Assert.assertEquals(0, ts.getVisibleType(TYPE_B.getName(), groupX).size());
		Assert.assertEquals(0, ts.getVisibleType(TYPE_B.getName(), groupZ).size());
		
		Assert.assertEquals(null, ts.getVisibleType(typeNameB, groupX));
		Assert.assertEquals(null, ts.getVisibleType(typeNameB, groupZ));
		
		// - Type C
		Assert.assertEquals(0, ts.getVisibleType(TYPE_C.getName(), groupX).size());
		Assert.assertEquals(0, ts.getVisibleType(TYPE_C.getName(), groupY).size());
		
		Assert.assertEquals(null, ts.getVisibleType(typeNameC, groupX));
		Assert.assertEquals(null, ts.getVisibleType(typeNameC, groupY));
		
		// Add dependency: X -> Y
		groupX.addGroupDependency(groupY);
		
		// - Type A
		Assert.assertEquals(0, ts.getVisibleType(TYPE_A.getName(), groupY).size());
		Assert.assertEquals(0, ts.getVisibleType(TYPE_A.getName(), groupZ).size());
		
		Assert.assertEquals(null, ts.getVisibleType(typeNameA, groupY));
		Assert.assertEquals(null, ts.getVisibleType(typeNameA, groupZ));
		
		// - Type B
		Assert.assertEquals(TYPE_B, ts.getVisibleType(TYPE_B.getName(), groupX).get(0));
		Assert.assertEquals(0, ts.getVisibleType(TYPE_B.getName(), groupZ).size());
		
		Assert.assertEquals(TYPE_B, ts.getVisibleType(typeNameB, groupX));
		Assert.assertEquals(null, ts.getVisibleType(typeNameB, groupZ));
		
		// - Type C
		Assert.assertEquals(0, ts.getVisibleType(TYPE_C.getName(), groupX).size());
		Assert.assertEquals(0, ts.getVisibleType(TYPE_C.getName(), groupY).size());
		
		Assert.assertEquals(null, ts.getVisibleType(typeNameC, groupX));
		Assert.assertEquals(null, ts.getVisibleType(typeNameC, groupY));
		
		// Add dependency: Y -> Z
		groupY.addGroupDependency(groupZ);
		
		// - Type A
		Assert.assertEquals(0, ts.getVisibleType(TYPE_A.getName(), groupY).size());
		Assert.assertEquals(0, ts.getVisibleType(TYPE_A.getName(), groupZ).size());
		
		Assert.assertEquals(null, ts.getVisibleType(typeNameA, groupY));
		Assert.assertEquals(null, ts.getVisibleType(typeNameA, groupZ));
		
		// - Type B
		Assert.assertEquals(TYPE_B, ts.getVisibleType(TYPE_B.getName(), groupX).get(0));
		Assert.assertEquals(0, ts.getVisibleType(TYPE_B.getName(), groupZ).size());
		
		Assert.assertEquals(TYPE_B, ts.getVisibleType(typeNameB, groupX));
		Assert.assertEquals(null, ts.getVisibleType(typeNameB, groupZ));
		
		// - Type C
		Assert.assertEquals(TYPE_C, ts.getVisibleType(TYPE_C.getName(), groupX).get(0));
		Assert.assertEquals(TYPE_C, ts.getVisibleType(TYPE_C.getName(), groupY).get(0));
		
		Assert.assertEquals(TYPE_C, ts.getVisibleType(typeNameC, groupX));
		Assert.assertEquals(TYPE_C, ts.getVisibleType(typeNameC, groupY));
		
		// Add dependency: X -> Z
		groupX.addGroupDependency(groupZ);
		
		// - Type A
		Assert.assertEquals(0, ts.getVisibleType(TYPE_A.getName(), groupY).size());
		Assert.assertEquals(0, ts.getVisibleType(TYPE_A.getName(), groupZ).size());
		
		Assert.assertEquals(null, ts.getVisibleType(typeNameA, groupY));
		Assert.assertEquals(null, ts.getVisibleType(typeNameA, groupZ));
		
		// - Type B
		Assert.assertEquals(TYPE_B, ts.getVisibleType(TYPE_B.getName(), groupX).get(0));
		Assert.assertEquals(0, ts.getVisibleType(TYPE_B.getName(), groupZ).size());
		
		Assert.assertEquals(TYPE_B, ts.getVisibleType(typeNameB, groupX));
		Assert.assertEquals(null, ts.getVisibleType(typeNameB, groupZ));
		
		// - Type C
		Assert.assertEquals(TYPE_C, ts.getVisibleType(TYPE_C.getName(), groupX).get(0));
		Assert.assertEquals(TYPE_C, ts.getVisibleType(TYPE_C.getName(), groupY).get(0));
		
		Assert.assertEquals(TYPE_C, ts.getVisibleType(typeNameC, groupX));
		Assert.assertEquals(TYPE_C, ts.getVisibleType(typeNameC, groupY));
		
		// Add dependency: Z -> X
		groupZ.addGroupDependency(groupX);
		
		// - Type A
		Assert.assertEquals(TYPE_A, ts.getVisibleType(TYPE_A.getName(), groupY).get(0));
		Assert.assertEquals(TYPE_A, ts.getVisibleType(TYPE_A.getName(), groupZ).get(0));
		
		Assert.assertEquals(TYPE_A, ts.getVisibleType(typeNameA, groupY));
		Assert.assertEquals(TYPE_A, ts.getVisibleType(typeNameA, groupZ));
		
		// - Type B
		Assert.assertEquals(TYPE_B, ts.getVisibleType(TYPE_B.getName(), groupX).get(0));
		Assert.assertEquals(TYPE_B, ts.getVisibleType(TYPE_B.getName(), groupZ).get(0));
		
		Assert.assertEquals(TYPE_B, ts.getVisibleType(typeNameB, groupX));
		Assert.assertEquals(TYPE_B, ts.getVisibleType(typeNameB, groupZ));
		
		// - Type C
		Assert.assertEquals(TYPE_C, ts.getVisibleType(TYPE_C.getName(), groupX).get(0));
		Assert.assertEquals(TYPE_C, ts.getVisibleType(TYPE_C.getName(), groupY).get(0));
		
		Assert.assertEquals(TYPE_C, ts.getVisibleType(typeNameC, groupX));
		Assert.assertEquals(TYPE_C, ts.getVisibleType(typeNameC, groupY));
		
		// Remove dependency: X -> Y
		groupX.removeGroupDependency(groupY);
		
		// - Type A
		Assert.assertEquals(TYPE_A, ts.getVisibleType(TYPE_A.getName(), groupY).get(0));
		Assert.assertEquals(TYPE_A, ts.getVisibleType(TYPE_A.getName(), groupZ).get(0));
		
		Assert.assertEquals(TYPE_A, ts.getVisibleType(typeNameA, groupY));
		Assert.assertEquals(TYPE_A, ts.getVisibleType(typeNameA, groupZ));
		
		// - Type B
		Assert.assertEquals(0, ts.getVisibleType(TYPE_B.getName(), groupX).size());
		Assert.assertEquals(0, ts.getVisibleType(TYPE_B.getName(), groupZ).size());
		
		Assert.assertEquals(null, ts.getVisibleType(typeNameB, groupX));
		Assert.assertEquals(null, ts.getVisibleType(typeNameB, groupZ));
		
		// - Type C
		Assert.assertEquals(TYPE_C, ts.getVisibleType(TYPE_C.getName(), groupX).get(0));
		Assert.assertEquals(TYPE_C, ts.getVisibleType(TYPE_C.getName(), groupY).get(0));
		
		Assert.assertEquals(TYPE_C, ts.getVisibleType(typeNameC, groupX));
		Assert.assertEquals(TYPE_C, ts.getVisibleType(typeNameC, groupY));
		
		// Remove dependency: X -> Z
		groupX.removeGroupDependency(groupZ);
		
		// - Type A
		Assert.assertEquals(TYPE_A, ts.getVisibleType(TYPE_A.getName(), groupY).get(0));
		Assert.assertEquals(TYPE_A, ts.getVisibleType(TYPE_A.getName(), groupZ).get(0));
		
		Assert.assertEquals(TYPE_A, ts.getVisibleType(typeNameA, groupY));
		Assert.assertEquals(TYPE_A, ts.getVisibleType(typeNameA, groupZ));
		
		// - Type B
		Assert.assertEquals(0, ts.getVisibleType(TYPE_B.getName(), groupX).size());
		Assert.assertEquals(0, ts.getVisibleType(TYPE_B.getName(), groupZ).size());
		
		Assert.assertEquals(null, ts.getVisibleType(typeNameB, groupX));
		Assert.assertEquals(null, ts.getVisibleType(typeNameB, groupZ));
		
		// - Type C
		Assert.assertEquals(0, ts.getVisibleType(TYPE_C.getName(), groupX).size());
		Assert.assertEquals(TYPE_C, ts.getVisibleType(TYPE_C.getName(), groupY).get(0));
		
		Assert.assertEquals(null, ts.getVisibleType(typeNameC, groupX));
		Assert.assertEquals(TYPE_C, ts.getVisibleType(typeNameC, groupY));
		
		// Remove dependency: Y -> Z
		groupY.removeGroupDependency(groupZ);
		
		// - Type A
		Assert.assertEquals(0, ts.getVisibleType(TYPE_A.getName(), groupY).size());
		Assert.assertEquals(TYPE_A, ts.getVisibleType(TYPE_A.getName(), groupZ).get(0));
		
		Assert.assertEquals(null, ts.getVisibleType(typeNameA, groupY));
		Assert.assertEquals(TYPE_A, ts.getVisibleType(typeNameA, groupZ));
		
		// - Type B
		Assert.assertEquals(0, ts.getVisibleType(TYPE_B.getName(), groupX).size());
		Assert.assertEquals(0, ts.getVisibleType(TYPE_B.getName(), groupZ).size());
		
		Assert.assertEquals(null, ts.getVisibleType(typeNameB, groupX));
		Assert.assertEquals(null, ts.getVisibleType(typeNameB, groupZ));
		
		// - Type C
		Assert.assertEquals(0, ts.getVisibleType(TYPE_C.getName(), groupX).size());
		Assert.assertEquals(0, ts.getVisibleType(TYPE_C.getName(), groupY).size());
		
		Assert.assertEquals(null, ts.getVisibleType(typeNameC, groupX));
		Assert.assertEquals(null, ts.getVisibleType(typeNameC, groupY));
		
		// Remove dependency: Z -> X
		groupZ.removeGroupDependency(groupX);
		
		// - Type A
		Assert.assertEquals(0, ts.getVisibleType(TYPE_A.getName(), groupY).size());
		Assert.assertEquals(0, ts.getVisibleType(TYPE_A.getName(), groupZ).size());
		
		Assert.assertEquals(null, ts.getVisibleType(typeNameA, groupY));
		Assert.assertEquals(null, ts.getVisibleType(typeNameA, groupZ));
		
		// - Type B
		Assert.assertEquals(0, ts.getVisibleType(TYPE_B.getName(), groupX).size());
		Assert.assertEquals(0, ts.getVisibleType(TYPE_B.getName(), groupZ).size());
		
		Assert.assertEquals(null, ts.getVisibleType(typeNameB, groupX));
		Assert.assertEquals(null, ts.getVisibleType(typeNameB, groupZ));
		
		// - Type C
		Assert.assertEquals(0, ts.getVisibleType(TYPE_C.getName(), groupX).size());
		Assert.assertEquals(0, ts.getVisibleType(TYPE_C.getName(), groupY).size());
		
		Assert.assertEquals(null, ts.getVisibleType(typeNameC, groupX));
		Assert.assertEquals(null, ts.getVisibleType(typeNameC, groupY));
	}
}
