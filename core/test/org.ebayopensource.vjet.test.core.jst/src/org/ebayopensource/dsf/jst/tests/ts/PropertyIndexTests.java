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
import org.ebayopensource.dsf.jst.ts.util.PropertyDependencyVisitor;
import org.ebayopensource.dsf.ts.TypeSpace;
import org.ebayopensource.dsf.ts.property.PropertyIndex;
import org.ebayopensource.dsf.ts.property.PropertyName;
import org.ebayopensource.dsf.ts.type.TypeName;



//@ModuleInfo(value="DsfPrebuild",subModuleId="JavaToJs")
public class PropertyIndexTests extends BaseTest {

	//@Category( { P1, UNIT })
	//@Description("Test property indexes")
	public void testDependents() {
		
		TestDataHelper.clear();

		TypeSpace<IJstType, IJstNode> ts = createTypeSpace();

		List<IJstType> jstTypes = getJstTypes(getClassList1());

		JstType typeA = (JstType) jstTypes.get(1);
		JstType typeB = (JstType) jstTypes.get(2);
		JstType typeC = (JstType) jstTypes.get(3);
		JstType typeD = (JstType) jstTypes.get(4);

		typeA.getPackage().setGroupName(PROJ_1);
		typeB.getPackage().setGroupName(PROJ_1);
		typeC.getPackage().setGroupName(PROJ_1);
		typeD.getPackage().setGroupName(PROJ_1);

		addPropertyDependency(typeA, ts);
		addPropertyDependency(typeB, ts);
		addPropertyDependency(typeC, ts);
		addPropertyDependency(typeD, ts);

		PropertyIndex<IJstType, IJstNode> ptyIndex1 = ts
				.getPropertyIndex(TYPE_A);
		PropertyIndex<IJstType, IJstNode> ptyIndex2 = ts
				.getPropertyIndex(TYPE_B);
		PropertyIndex<IJstType, IJstNode> ptyIndex3 = ts
				.getPropertyIndex(TYPE_C);
		PropertyIndex<IJstType, IJstNode> ptyIndex4 = ts
				.getPropertyIndex(TYPE_D);

		assertEquals(7, ptyIndex1.size());
		assertEquals(2, ptyIndex2.size());
		assertEquals(1, ptyIndex3.size());
		assertEquals(5, ptyIndex4.size());

		List<IJstNode> dependents = ts.getPropertyDependents(new PropertyName(
				TYPE_A, "COUNTRY"));
		assertEquals(3, dependents.size());
		assertEquals(typeB, dependents.get(0).getOwnerType());
		assertEquals(typeC, dependents.get(1).getOwnerType());

		dependents = ts.getPropertyDependents(new PropertyName(TYPE_A, "CITY"));
		assertEquals(3, dependents.size());
		assertEquals(typeB, dependents.get(0).getOwnerType());
		assertEquals(typeB, dependents.get(1).getOwnerType());
		assertEquals(typeC, dependents.get(2).getOwnerType());

		dependents = ts.getPropertyDependents(new PropertyName(TYPE_A,
				"m_pupulation"));
		assertEquals(2, dependents.size());
		assertEquals(typeA, dependents.get(0).getOwnerType());
		assertEquals(typeB, dependents.get(1).getOwnerType());

		dependents = ts.getPropertyDependents(new PropertyName(TYPE_A,
				"m_company"));
		assertEquals(1, dependents.size());
		assertEquals(typeA, dependents.get(0).getOwnerType());

		// Remove jstMethod
		ptyIndex1.removeEntity("CITY");
		assertEquals(6, ptyIndex1.size());

		// Validation/Errors
	}

	private void addPropertyDependency(final IJstType type,
			TypeSpace<IJstType, IJstNode> ts) {

		Map<PropertyName, List<IJstNode>> map = getDependents(type);

		TypeName tName;
		String ptyName;
		PropertyIndex<IJstType, IJstNode> ptyIndex;
		for (Entry<PropertyName, List<IJstNode>> entry : map.entrySet()) {
			tName = entry.getKey().typeName();
			ptyName = entry.getKey().propertyName();
			ptyIndex = ts.getPropertyIndex(tName);
			if (ptyIndex == null) {
				ptyIndex = new PropertyIndex<IJstType, IJstNode>(type);
				ts.addPropertyIndex(tName, ptyIndex);
			}
			ptyIndex.addDependents(ptyName, entry.getValue());
		}
	}

	private Map<PropertyName, List<IJstNode>> getDependents(final IJstType type) {

		PropertyDependencyVisitor visitor = new PropertyDependencyVisitor();

		JstDepthFirstTraversal.accept(type, visitor);

		return visitor.getPropertyDependencies();
	}
}
