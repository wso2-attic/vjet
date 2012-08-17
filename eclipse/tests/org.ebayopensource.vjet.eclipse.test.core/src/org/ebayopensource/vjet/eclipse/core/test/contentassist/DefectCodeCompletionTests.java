/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.core.test.contentassist;

import java.util.LinkedList;

import org.eclipse.core.runtime.Path;
import org.eclipse.dltk.mod.compiler.env.ISourceModule;
import org.eclipse.dltk.mod.core.CompletionProposal;
import org.eclipse.dltk.mod.core.ModelException;

import org.ebayopensource.vjet.eclipse.codeassist.VjoCompletionEngine;
import org.ebayopensource.vjet.eclipse.core.IJSSourceModule;
import org.ebayopensource.vjet.eclipse.core.search.matching.ICategoryRequestor;
import org.ebayopensource.vjet.eclipse.core.test.FixtureUtils;
import org.ebayopensource.vjet.eclipse.core.test.parser.AbstractVjoModelTests;
import org.ebayopensource.vjet.testframework.fixture.FixtureManager;

public class DefectCodeCompletionTests extends AbstractVjoModelTests {

	public void setUp() throws Exception {
		super.setUp();
//		TypeSpaceMgr mgr = TypeSpaceMgr.getInstance();
//		Collection<IJstType> types = mgr.getAllTypes();
//		for(IJstType type : types) {
//			if(type.getName() == null)
//				continue;
//			RemoveTypeEvent removeEvent = new RemoveTypeEvent(new TypeName(getProjectName(), type.getName()));
//			mgr.processEvent(removeEvent);
//		}
	}
	
	protected int lastPositionInFile(String string, String moduleName)
			throws ModelException {
		String content = ((IJSSourceModule) getSourceModule(
				TestConstants.PROJECT_NAME_VJETPROJECT, "src", new Path(
						moduleName))).getSource();
		if(string == null)
			return content.length();
		int position = content.lastIndexOf(string);
		if (position >= 0) {
			return position + string.length();
		}
		return -1;
	}
	
	/**
	 * VJET - NPE thrown in MethodCompletionHandle during code completion 
	 * @throws ModelException
	 */	
	// bug
	public void testDefect1573() throws ModelException {
		String js = "defect/D1573.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "getState" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js));
			int position = lastPositionInFile("this.state=s;",module);
			containsNames(module, position, names, ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			if (m_fixtureManager != null) {
				m_fixtureManager.tearDown();
			}
		}
	}
	
	/**
	 * VJET - code completion for ctype don't work 
	 * in props block in super type
	 */	
	// bug
	public void testSuperTypeDefect2042() throws ModelException {
		String js = "defect/TypeB.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "printNum", "doIt" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js));
			int position = lastPositionInFile("x.",module);
			containsNames(module, position, names, ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			if (m_fixtureManager != null) {
				m_fixtureManager.tearDown();
			}
		}
	}
	
	/**
	 * VJET - code completion for ctype don't work 
	 * in props block in owner type
	 */	
	// bug
	public void _testOwnerTypeDefect2042() throws ModelException {
		String js = "defect/TypeB.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "fot" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js));
			int position = lastPositionInFile("inst.f",module);
			containsNames(module, position, names, ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			if (m_fixtureManager != null) {
				m_fixtureManager.tearDown();
			}
		}
	}
	
	/**
	 * VJET - Invalid code completion for object static members
	 */	
	// bug
	public void testDefect2107() throws ModelException {
		String js = "defect/TypeB.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "printNum"};
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js));
			int position = lastPositionInFile("x.",module);
			containsNames(module, position, names, ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			if (m_fixtureManager != null) {
				m_fixtureManager.tearDown();
			}
		}
	}
	
	/**
	 * VJET - Code completion - Instance methods from type in needs shouldn't be shown
	 */	
	// bug
	public void testDefect2335() throws ModelException {
		String js = "defect/BugTestA.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "fooFunc"};
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js));
			int position = lastPositionInFile("fooF",module);
			excludesNames(module, position, names, ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			if (m_fixtureManager != null) {
				m_fixtureManager.tearDown();
			}
		}
	}

	/**
	 * VJET - proposals with double-underscores should not be listed as proposals
	 */	
	// bug
	public void testDefect2189Inclusion() throws ModelException {
		String js = "defect/D2189.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "__noSuchMethod__", "__parent__", 
					"__proto__", "__defineGetter__",	"__defineSetter__", 
					"__lookupGetter__",	"__lookupSetter__" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js));
			int position = lastPositionInFile("x.",module);
			containsNames(module, position, names, ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			if (m_fixtureManager != null) {
				m_fixtureManager.tearDown();
			}
		}
	}
	
	/**
	 * VJET - proposals with double-underscores should not be listed as proposals
	 */	
	// bug
	public void testDefect2189Exclusion() throws ModelException {
		String js = "defect/D2189.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "__noSuchMethod__", "__parent__", 
					"__proto__", "__defineGetter__",	"__defineSetter__", 
					"__lookupGetter__",	"__lookupSetter__" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js));
			int position = lastPositionInFile("var", module) - "var".length();
			excludesNames(module, position, names, ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			if (m_fixtureManager != null) {
				m_fixtureManager.tearDown();
			}
		}
	}
	
	/**
	 *  VJET - Code completion/suggestions does not work after vjo.ctype() and new line
	 */	
	// bug
	public void testDefect2034() throws ModelException {
		String js = "defect/D2034.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "props({})", "protos({})" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js));
			int position = lastPositionInFile(".",module);
			containsNames(module, position, names, ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			if (m_fixtureManager != null) {
				m_fixtureManager.tearDown();
			}
		}
	}
	
	/**
	 *  VJET - code completion - Instance methods do not work in static block
	 */	
	// bug
	public void testDefect2040() throws ModelException {
		String js = "defect/D2040.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "e1", "s1", "s2", "s3", "testMe" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js));
			int position = lastPositionInFile("x.",module);
			containsNames(module, position, names, ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			if (m_fixtureManager != null) {
				m_fixtureManager.tearDown();
			}
		}
	}
	
	/**
	 * VJET - Invalid static method call completion
	 * 
	 * @throws ModelException
	 */	
	// bug
	public void testDefect2078() throws ModelException {
		String js = "defect/C.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] {};
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js));
			int position = lastPositionInFile("gg", module);
			basicTest(module, position, names, ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			if (m_fixtureManager != null) {
				m_fixtureManager.tearDown();
			}
		}
	}

	/**
	 * VJET - Completion engine propose incorrect completions after ObjCreationExp
	 * 
	 * @throws ModelException
	 */	
	// bug
	public void testDefect2446Inclusion() throws ModelException {
		String js = "defect/D2446.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "ggg"};
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js));
			int position = lastPositionInFile("(new this.vj$.A()).", module);
			containsNames(module, position, names, ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			if (m_fixtureManager != null) {
				m_fixtureManager.tearDown();
			}
		}
	}
	
	/**
	 * VJET - Completion engine propose incorrect completions after ObjCreationExp
	 * 
	 * @throws ModelException
	 */	
	// bug
	public void testDefect2446Exclusion() throws ModelException {
		String js = "defect/D2446.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "foo"};
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js));
			int position = lastPositionInFile("(new this.vj$.A()).", module);
			excludesNames(module, position, names, ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			if (m_fixtureManager != null) {
				m_fixtureManager.tearDown();
			}
		}
	}
	
	/**
	 * VJET - When declaring a property value ie x : // control space here there are no values proposed.
	 * 
	 * @throws ModelException
	 */	
	// bug
	public void testDefect2422OnStatic() throws ModelException {
		String js = "defect/D2422OnStatic.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "closed", "name", "length", "parent", "top"};
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js));
			int position = lastPositionInFile("x1 : window.", module);
			containsNames(module, position, names, ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			if (m_fixtureManager != null) {
				m_fixtureManager.tearDown();
			}
		}
	}
	
	/**
	 * VJET - When declaring a property value ie x : // control space here there are no values proposed.
	 * 
	 * @throws ModelException
	 */	
	// bug
	public void testDefect2422OnStatic2() throws ModelException {
		String js = "defect/D2422OnStatic.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "document", "frames", "history", "Infinity", "NaN",
						"location", "prototype", "window"};
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js));
			int position = lastPositionInFile("x2 : ", module);
			containsNames(module, position, names, ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			if (m_fixtureManager != null) {
				m_fixtureManager.tearDown();
			}
		}
	}
	
	/**
	 * VJET - When declaring a property value ie x : // control space here there are no values proposed.
	 * 
	 * @throws ModelException
	 */	
	// bug
	public void testDefect2422OnNonStatic() throws ModelException {
		String js = "defect/D2422OnNonStatic.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "closed", "name", "length", "parent", "top"};
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js));
			
			int position = lastPositionInFile("x1 : window.", module);
			containsNames(module, position, names, ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			if (m_fixtureManager != null) {
				m_fixtureManager.tearDown();
			}
		}
	}
	
	/**
	 * VJET - When declaring a property value ie x : // control space here there are no values proposed.
	 * 
	 * @throws ModelException
	 */	
	// bug
	public void testDefect2422OnNonStatic2() throws ModelException {
		String js = "defect/D2422OnNonStatic.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "document", "frames", "history", "Infinity", "NaN",
					"location", "prototype", "window"};
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js));
			
			int position = lastPositionInFile("x2 : ", module);
			containsNames(module, position, names, ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			if (m_fixtureManager != null) {
				m_fixtureManager.tearDown();
			}
		}
	}
	
//	//BUG 2103 (VJET - Constructor access modifier not generated)
//	public void testMethodOverrideAccessModifier() throws ModelException {
//		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
//		try {
//			String name = new String("foo");
//			String module = "inheritance/codeassist/B.js";
//			int position = lastPositionInFile("protos({ ", module);
//			
//			assertNotSame("Invalid file content, cant find position", -1, position);
//
//			LinkedList<CompletionProposal> results = new LinkedList<CompletionProposal>();
//			IJSSourceModule mmodule = (IJSSourceModule) getSourceModule(
//					getTestProjectName(), "src", new Path(module));
//
//			VjoCompletionEngine c = createEngine(results, ICategoryRequestor.TYPE_CATEGORY, mmodule);
//			c.complete((ISourceModule) mmodule, position, 0);
//			CompletionProposal foo = null;
//			Iterator it = results.iterator();
//			while (it.hasNext()) {
//				CompletionProposal pr = (CompletionProposal) it.next();
//				if (pr.getName().equals(name)) {
//					foo = pr;
//					break;
//				}
//			}
//			assertNotNull("Can't get foo() method override completion", foo);
//			final String completionText = new String(foo.getCompletion());
//			assertTrue("", completionText.contains("foo"));
//		} finally {
//			if (m_fixtureManager != null) {
//				m_fixtureManager.tearDown();
//			}
//		}
//	}
	
	public void testDefectOnBlockSection() throws ModelException {
		String js = "defect/TestOnBlockSection.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "protos({})" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js));
			int position = lastPositionInFile(".pro", module);
			containsNames(module, position, names, ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}
	
	/**
	 * VJET - satisfies(['a.b.c.X', 'x.y.z.R']) code completion for array style doesn't work. Also applied to mixin.
	 * @throws ModelException
	 */
	public void testDefect2420OnType() throws ModelException {
		String js = "defect/D2420.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "I1", "I2" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js));
			int position = lastPositionInFile(",\"defect.", module);
			containsNames(module, position, names, ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}
	
	/**
	 * VJET - satisfies(['a.b.c.X', 'x.y.z.R']) code completion for array style doesn't work. Also applied to mixin.
	 * @throws ModelException
	 */
	public void testDefect2420OnMixin() throws ModelException {
		String js = "defect/D2420Mixin.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "I1", "I2" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js));
			int position = lastPositionInFile(",\"defect.", module);
			containsNames(module, position, names, ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}
	
	/**
	 * VJET - NPE in JstUtil during code completion
	 * 
	 */	
	// bug
	public void testSuperTypeDefect2545() throws ModelException {
		String js = "defect/D2545.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "testMethod" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js));
			int position = lastPositionInFile("this.vj$.B().",module);
			containsNames(module, position, names, ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			if (m_fixtureManager != null) {
				m_fixtureManager.tearDown();
			}
		}
	}
	
	public void testDefect2150() throws ModelException {
		String js = "defect/D2150.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "sysout", "syserr" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js));
			int position = lastPositionInFile("vjo.",module);
			containsNames(module, position, names, ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			if (m_fixtureManager != null) {
				m_fixtureManager.tearDown();
			}
		}
	}
	
	public void testDefect1801() throws ModelException {
		String js = "defect/D1801.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "sysout", "syserr" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js));
			int position = lastPositionInFile("vjo.",module);
			containsNames(module, position, names, ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			if (m_fixtureManager != null) {
				m_fixtureManager.tearDown();
			}
		}
	}
	
	public void testBug2336() throws ModelException {
		String js = "partials/Bug2336.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					TestConstants.PROJECT_NAME_VJETPROJECT, "src", new Path(js));
			int position = lastPositionInFile("TypeC().", module);
			assertNotSame("Invalid file content, cant find position", -1, position);

			LinkedList<CompletionProposal> results = new LinkedList<CompletionProposal>();

			VjoCompletionEngine c = createEngine(results, ICategoryRequestor.TYPE_CATEGORY, module);
			c.complete((ISourceModule) module, position, 0);
			assertTrue("New method stub completion should be provided", results != null);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}
	
	public void testBug2042() throws ModelException {
		String js = "partials/Bug2042.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] containNames = new String[] { "s_init", "helloWorld", 
					"helloWorld1", "helloWorld2", "helloWorld3", "setName" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					TestConstants.PROJECT_NAME_VJETPROJECT, "src", new Path(js));
			int position = lastPositionInFile("x.", module);
			containsNames(module, position, containNames, ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}
	
	public void testBug2078() throws ModelException {
		String js = "partials/Bug2078.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] containNames = new String[] { "s_init", "helloWorld", 
					"helloWorld1", "helloWorld2", "helloWorld3", "setName" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					TestConstants.PROJECT_NAME_VJETPROJECT, "src", new Path(js));
			int position = lastPositionInFile("hello", module);
			excludesNames(module, position, containNames, ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}
	
	public void testBug1995() throws ModelException {
		String js = "partials/Bug1995.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] containNames = new String[] { "protos({})" };
			String[] excludeNames = new String[] { "props({})" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					TestConstants.PROJECT_NAME_VJETPROJECT, "src", new Path(js));
			int position = firstPositionInFile(".p", module);
			excludesNames(module, position, excludeNames, ICategoryRequestor.TYPE_CATEGORY);
			containsNames(module, position, containNames, ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}
	
	public void testBug2149() throws ModelException {
		String js = "partials/Bug2149TypeA.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "staticDo()" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					TestConstants.PROJECT_NAME_VJETPROJECT, "src", new Path(js));
			int position = lastPositionInFile("x.doIt().st", module);
			new PartialCodeCompletionTests().doTypeCodeCompleate(module, position, names[0]);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}
	
	public void testBug643() throws ModelException {
		String js = "partials/Bug643.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "fr", "sel", "changeFrame", "createAnchorOnClick",
					"init", "oElem"};
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					TestConstants.PROJECT_NAME_VJETPROJECT, "src", new Path(js));
			int position = lastPositionInFile("value;", module);
			containsNames(module, position, names, ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}
	
	public void testBug2335() throws ModelException {
		String js = "partials/Bug2335.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "func1"};
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					TestConstants.PROJECT_NAME_VJETPROJECT, "src", new Path(js));
			int position = lastPositionInFile("func", module);
			excludesNames(module, position, names, ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}
	
	public void testBug1994() throws ModelException {
		String js = "partials/Bug1994.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "gh" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					TestConstants.PROJECT_NAME_VJETPROJECT, "src", new Path(js));
			int position = lastPositionInFile("this.", module);
			excludesNames(module, position, names, ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}
	
	public void testBug1244() throws ModelException {
		String js = "partials/Bug1994.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "gh" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					TestConstants.PROJECT_NAME_VJETPROJECT, "src", new Path(js));
			int position = lastPositionInFile("this.", module);
			excludesNames(module, position, names, ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}
	
	@Override
	protected String getProjectName() {	
		return TestConstants.PROJECT_NAME_VJETPROJECT;
	}
}