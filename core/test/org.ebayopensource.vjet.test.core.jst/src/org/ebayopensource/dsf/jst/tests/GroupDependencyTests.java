/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.tests;

import org.junit.Assert;
import org.junit.Test;

import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstFactory;
import org.ebayopensource.dsf.jst.ts.util.JstTypeDependencyCollector;
import org.ebayopensource.dsf.ts.group.Group;

public class GroupDependencyTests {
	
	private static JstTypeDependencyCollector DEPENDENCY_COLLECTOR = new JstTypeDependencyCollector();
	
	private static IJstType TYPE_A = JstFactory.getInstance().createJstType("A", false);
	private static IJstType TYPE_B = JstFactory.getInstance().createJstType("B", false);
	private static IJstType TYPE_C = JstFactory.getInstance().createJstType("C", false);
	private static IJstType TYPE_D = JstFactory.getInstance().createJstType("D", false);
	private static IJstType TYPE_E = JstFactory.getInstance().createJstType("E", false);
	private static IJstType TYPE_F = JstFactory.getInstance().createJstType("F", false);
	private static IJstType TYPE_G = JstFactory.getInstance().createJstType("G", false);
	private static IJstType TYPE_H = JstFactory.getInstance().createJstType("H", false);
	private static IJstType TYPE_I = JstFactory.getInstance().createJstType("I", false);

	@Test
	public void testGroupDependency(){
		
		Group<IJstType> groupX = new Group<IJstType>("X", DEPENDENCY_COLLECTOR);
		Group<IJstType> groupY = new Group<IJstType>("Y", DEPENDENCY_COLLECTOR);
		Group<IJstType> groupZ = new Group<IJstType>("Z", DEPENDENCY_COLLECTOR);
		
		groupX.addEntity(TYPE_A.getName(), TYPE_A);
		groupY.addEntity(TYPE_B.getName(), TYPE_B);
		groupZ.addEntity(TYPE_C.getName(), TYPE_C);
		
		// No dependency
		Assert.assertFalse(groupX.isDependOn(groupY));
		Assert.assertFalse(groupX.isDependOn(groupZ));
		
		Assert.assertFalse(groupY.isDependOn(groupX));
		Assert.assertFalse(groupY.isDependOn(groupZ));
		
		Assert.assertFalse(groupZ.isDependOn(groupX));
		Assert.assertFalse(groupZ.isDependOn(groupY));
		
		// Add dependency: X->Y, Y->Z
		groupX.addGroupDependency(groupY);
		groupY.addGroupDependency(groupZ);
		
		Assert.assertTrue(groupX.isDependOn(groupY));
		Assert.assertTrue(groupX.isDependOn(groupZ));
		
		Assert.assertFalse(groupY.isDependOn(groupX));
		Assert.assertTrue(groupY.isDependOn(groupZ));
		
		Assert.assertFalse(groupZ.isDependOn(groupX));
		Assert.assertFalse(groupZ.isDependOn(groupY));
		
		// Add dependency: Z->X (no circular validation at group level)
		groupZ.addGroupDependency(groupX);
		
		Assert.assertTrue(groupX.isDependOn(groupY));
		Assert.assertTrue(groupX.isDependOn(groupZ));
		
		Assert.assertTrue(groupY.isDependOn(groupX));
		Assert.assertTrue(groupY.isDependOn(groupZ));
		
		Assert.assertTrue(groupZ.isDependOn(groupX));
		Assert.assertTrue(groupZ.isDependOn(groupY));
		
		// Add dependency: X->Z (no circular validation at group level)
		groupX.addGroupDependency(groupZ);
		
		Assert.assertTrue(groupX.isDependOn(groupY));
		Assert.assertTrue(groupX.isDependOn(groupZ));
		
		Assert.assertTrue(groupY.isDependOn(groupX));
		Assert.assertTrue(groupY.isDependOn(groupZ));
		
		Assert.assertTrue(groupZ.isDependOn(groupX));
		Assert.assertTrue(groupZ.isDependOn(groupY));
		
		// Remove dependency: X->Y
		groupX.removeGroupDependency(groupY);
		
		Assert.assertFalse(groupX.isDependOn(groupY));
		Assert.assertTrue(groupX.isDependOn(groupZ));
		
		Assert.assertTrue(groupY.isDependOn(groupX));
		Assert.assertTrue(groupY.isDependOn(groupZ));
		
		Assert.assertTrue(groupZ.isDependOn(groupX));
		Assert.assertFalse(groupZ.isDependOn(groupY));
		
		// Remove dependency: Y->Z
		groupY.removeGroupDependency(groupZ);
		
		Assert.assertFalse(groupX.isDependOn(groupY));
		Assert.assertTrue(groupX.isDependOn(groupZ));
		
		Assert.assertFalse(groupY.isDependOn(groupX));
		Assert.assertFalse(groupY.isDependOn(groupZ));
		
		Assert.assertTrue(groupZ.isDependOn(groupX));
		Assert.assertFalse(groupZ.isDependOn(groupY));
		
		// Remove dependency: X->Z
		groupX.removeGroupDependency(groupZ);
		
		Assert.assertFalse(groupX.isDependOn(groupY));
		Assert.assertFalse(groupX.isDependOn(groupZ));
		
		Assert.assertFalse(groupY.isDependOn(groupX));
		Assert.assertFalse(groupY.isDependOn(groupZ));
		
		Assert.assertTrue(groupZ.isDependOn(groupX));
		Assert.assertFalse(groupZ.isDependOn(groupY));
		
		// Remove dependency: Z->X
		groupZ.removeGroupDependency(groupX);
		
		Assert.assertFalse(groupX.isDependOn(groupY));
		Assert.assertFalse(groupX.isDependOn(groupZ));
		
		Assert.assertFalse(groupY.isDependOn(groupX));
		Assert.assertFalse(groupY.isDependOn(groupZ));
		
		Assert.assertFalse(groupZ.isDependOn(groupX));
		Assert.assertFalse(groupZ.isDependOn(groupY));
	}
	
	@Test
	public void testGroupDependencyAll(){
		
		Group<IJstType> groupA = new Group<IJstType>("A", DEPENDENCY_COLLECTOR);
		Group<IJstType> groupB = new Group<IJstType>("B", DEPENDENCY_COLLECTOR);
		Group<IJstType> groupC = new Group<IJstType>("C", DEPENDENCY_COLLECTOR);
		Group<IJstType> groupD = new Group<IJstType>("D", DEPENDENCY_COLLECTOR);
		Group<IJstType> groupE = new Group<IJstType>("E", DEPENDENCY_COLLECTOR);
		Group<IJstType> groupF = new Group<IJstType>("F", DEPENDENCY_COLLECTOR);
		Group<IJstType> groupG = new Group<IJstType>("G", DEPENDENCY_COLLECTOR);
		Group<IJstType> groupH = new Group<IJstType>("H", DEPENDENCY_COLLECTOR);
		Group<IJstType> groupI = new Group<IJstType>("I", DEPENDENCY_COLLECTOR);
		
		groupA.addEntity(TYPE_A.getName(), TYPE_A);
		groupB.addEntity(TYPE_B.getName(), TYPE_B);
		groupC.addEntity(TYPE_C.getName(), TYPE_C);
		groupD.addEntity(TYPE_D.getName(), TYPE_D);
		groupE.addEntity(TYPE_E.getName(), TYPE_E);
		groupF.addEntity(TYPE_F.getName(), TYPE_F);
		groupG.addEntity(TYPE_G.getName(), TYPE_G);
		groupH.addEntity(TYPE_H.getName(), TYPE_H);
		groupI.addEntity(TYPE_I.getName(), TYPE_I);
		
		// Add dependencies
		groupA.addGroupDependency(groupB);
		groupA.addGroupDependency(groupC);
		
		groupB.addGroupDependency(groupD);
		groupB.addGroupDependency(groupE);
		
		groupC.addGroupDependency(groupF);
		groupC.addGroupDependency(groupG);
		groupC.addGroupDependency(groupH);
		
		groupE.addGroupDependency(groupH);
		groupE.addGroupDependency(groupI);
		
		groupF.addGroupDependency(groupI);
		
		groupG.addGroupDependency(groupI);
		
		groupH.addGroupDependency(groupB);
		
		groupI.addGroupDependency(groupD);
		
		// Assertions
		Assert.assertEquals(8, groupA.getGroupDependency().size());
		Assert.assertEquals(4, groupB.getGroupDependency().size());
		Assert.assertEquals(7, groupC.getGroupDependency().size());
		Assert.assertEquals(0, groupD.getGroupDependency().size());
		Assert.assertEquals(4, groupE.getGroupDependency().size());
		Assert.assertEquals(2, groupF.getGroupDependency().size());
		Assert.assertEquals(2, groupG.getGroupDependency().size());
		Assert.assertEquals(4, groupH.getGroupDependency().size());
		Assert.assertEquals(1, groupI.getGroupDependency().size());
	}
}
