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
import java.util.List;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.dltk.mod.compiler.env.ISourceModule;
import org.eclipse.dltk.mod.core.CompletionProposal;
import org.eclipse.dltk.mod.core.ModelException;
import org.eclipse.dltk.mod.internal.ui.editor.ScriptEditor;
import org.eclipse.dltk.mod.ui.text.completion.CompletionProposalLabelProvider;
import org.eclipse.dltk.mod.ui.text.completion.ScriptCompletionProposal;
import org.eclipse.dltk.mod.ui.text.completion.ScriptContentAssistInvocationContext;
import org.eclipse.jface.text.source.ISourceViewer;

import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.IJstTypeReference;
import org.ebayopensource.dsf.ts.type.TypeName;
import org.ebayopensource.vjet.eclipse.codeassist.VjoCompletionEngine;
import org.ebayopensource.vjet.eclipse.core.IJSSourceModule;
import org.ebayopensource.vjet.eclipse.core.VjoNature;
import org.ebayopensource.vjet.eclipse.core.search.matching.ICategoryRequestor;
import org.ebayopensource.vjet.eclipse.core.test.FixtureUtils;
import org.ebayopensource.vjet.eclipse.core.test.parser.AbstractVjoModelTests;
import org.ebayopensource.vjet.eclipse.internal.ui.text.completion.VjoTypeCompletionProposalComputer;
import org.ebayopensource.vjet.testframework.fixture.FixtureManager;

public class CodeCompletionTests2 extends AbstractVjoModelTests {

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

//	protected void basicTest(String mname, int position, String[] compNames,
//			String category) throws ModelException {
//		assertNotSame("Invalid file content, cant find position", -1, position);
//
//		LinkedList<CompletionProposal> results = new LinkedList<CompletionProposal>();
//
//		IJSSourceModule module = (IJSSourceModule) getSourceModule(
//				TestConstants.PROJECT_NAME_VJETPROJECT, "src", new Path(mname));
//		VjoCompletionEngine c = createEngine(results, category, module);
//		c.complete((ISourceModule) module, position, 0);
//		compareCompletions(results, compNames, false);
//	}
	
	protected void basicNameTest(IJSSourceModule module, int position, String[] compNames,
			String category) throws ModelException {
		assertNotSame("Invalid file content, cant find position", -1, position);

		LinkedList<CompletionProposal> results = new LinkedList<CompletionProposal>();

		VjoCompletionEngine c = createEngine(results, category, module);
		c.complete((ISourceModule) module, position, 0);
		compareCompletions(results, compNames, true);
	}

	public void testVjoDot() throws ModelException {
		String js = "Test1.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "ctype('Test1')", "etype('Test1')", 
					"itype('Test1')", "mtype('Test1')", "needs(\'\')", "otype('Test1')" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js));
			int position = lastPositionInFile("vjo.", module);
			containsNames(module, position, names, ICategoryRequestor.TEXT_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}
	
	public void testCompletionAfterInherits() throws ModelException {
		String js = "Test.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "protos({})" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js));
			int position = lastPositionInFile("inherits('Test4').", module);
			containsNames(module, position, names, ICategoryRequestor.TEXT_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}
	
	/**
	 * instance members shouldn't appear in protos block after "this." 
	 * 
	 * Test case for bug: http://quickbugstage.arch.ebay.com/show_bug.cgi?id=1996
	 * 
	 * @throws ModelException
	 */
	public void test5() throws ModelException {
		String js = "Test5.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "a" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js));
			int position = lastPositionInFile("   this.", module);
			containsNames(module, position, names, ICategoryRequestor.TEXT_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}
	
	public void test1() throws ModelException {
		String js = "Test1.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "ctype('Test1')" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js));
			int position = lastPositionInFile("vjo.c", module);
			basicTest(module, position, names, ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	public void test2() throws ModelException {
		String js = "Test2.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "props({})", "protos({})", };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js));
			int position = lastPositionInFile(".p", module);
			basicTest(module, position, names, ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	/**
	 * There should not be any proposal for protos if it's already present
	 * 
	 * @throws ModelException
	 */
	public void test3() throws ModelException {
		String js = "Test3.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "protos({})" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js));
			int position = lastPositionInFile("}).p", module);
			basicTest(module, position, names, ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	/**
	 * needs should appear below type statement
	 * 
	 * @throws ModelException
	 */

	public void test4() throws ModelException {
		String js = "Test4.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "needs(\'\')" };// ,
			// "vjo.needsLib(\"\");"
			System.out.println("WORKSPACE ROOT"
					+ ResourcesPlugin.getWorkspace().getRoot().toString()); // };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js));
			int position = lastPositionInFile(".n", module);
			basicTest(module, position, names, ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			if (m_fixtureManager != null) {
				m_fixtureManager.tearDown();
			}
		}
	}

	/**
	 * Tests on this.b<cursor> in inherited class
	 * 
	 * @throws ModelException
	 */
	public void testInheritance1() throws ModelException {
		String js = "inheritance/B.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "base" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js));
			int position = lastPositionInFile("this.b", module);
			basicTest(module, position, names, ICategoryRequestor.TYPE_CATEGORY);

		} finally {
			if (m_fixtureManager != null) {
				m_fixtureManager.tearDown();
			}

		}
	}

	/**
	 * Tests on this.base.s<cursor> in inherited class
	 * 
	 * @throws ModelException
	 */
	// bug
	public void testInheritance2() throws ModelException {
		String js = "inheritance/B.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "setState", "state" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js));
			int position = lastPositionInFile("this.base.s", module);
			containsNames(module, position, names, ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			if (m_fixtureManager != null) {
				m_fixtureManager.tearDown();
			}

		}
	}

	/**
	 * Test for inheritance using abstract type
	 */
	public void testInheritance3() throws ModelException {
		String js = "inheritance/TypeB.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "//> public abstract void fooA()\r\nfooA : function() {\r\n\tthis.base.fooA();\r\n}" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js));
			int position = lastPositionInFile(".protos({", module);
			basicTest(module, position, names, ICategoryRequestor.TYPE_CATEGORY);
			position = lastPositionInFile("fooA:function(){}", module);
			basicTest(module, position, names, ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			if (m_fixtureManager != null) {
				m_fixtureManager.tearDown();
			}

		}
	}

	// /**
	// * Test on method self reference
	// * @throws ModelException
	// */
	public void testStatic1() throws ModelException {
		String js = "Static1.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "stA", "stB", "stC", "add" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js));
			int position = lastPositionInFile("add:function(){", module);
			containsNames(module, position, names, ICategoryRequestor.TEXT_CATEGORY);
		} finally {
			if (m_fixtureManager != null) {
				m_fixtureManager.tearDown();
			}

		}
	}

	/**
	 * In the static Initialization block type name .ctrl space should show
	 * proposal for static properties and static methods
	 * 
	 * @throws ModelException
	 */
	// BUG
	public void testStatic2() throws ModelException {
		String js = "Static2.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "s_init", "helloWorld" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js));
			int position = lastPositionInFile("Static2.", module);
			containsNames(module, position, names, ICategoryRequestor.TEXT_CATEGORY);
		} finally {
			if (m_fixtureManager != null) {
				m_fixtureManager.tearDown();
			}
		}
	}

	// /**
	// *Static Initialization block should show proposal for static fields
	// *
	// * @throws ModelException
	// */
	// BUG
	public void testStatic2x() throws ModelException {
		String js = "Static2.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "s_init", "helloWorld" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js));
			int position = lastPositionInFile("inits(function(){", module);
			containsNames(module, position, names,
					ICategoryRequestor.TEXT_CATEGORY);
		} finally {
			if (m_fixtureManager != null) {
				m_fixtureManager.tearDown();
			}

		}
	}

	// 	 	  
	// /**
	// *Static fields of one class can be called from any static method of
	// another class
	// *
	// * @throws ModelException
	// */
	// //BUG This functionality works if .needs is declared above the type
	// declaration
	public void testStatic3() throws ModelException {
		String js = "Static3.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "stA", "stB", "add", "instanceof"};
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js));
			int position = lastPositionInFile("Static1.", module);
			basicTest(module, position, names, ICategoryRequestor.TEXT_CATEGORY);
		} finally {
			if (m_fixtureManager != null) {
				m_fixtureManager.tearDown();
			}

		}
	}

	/**
	 * Test on accessing static fields from within instance function block // *
	 * 
	 * @throws ModelException //
	 */

	// bug
	public void testStatic4() throws ModelException {
		String js = "Static4.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "var1", "var2", "var3" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js));
			int position = lastPositionInFile("return this.vj$.Static4.",
					module);
			containsNames(module, position, names, ICategoryRequestor.TEXT_CATEGORY);
		} finally {
			if (m_fixtureManager != null) {
				m_fixtureManager.tearDown();
			}

		}
	}
	
	/**
	 * Test case for BUG 2149(VJET - Incorrect completion for static members)
	 * 
	 * @throws ModelException
	 */
	public void testStatic5() throws ModelException {
		String js = "static/Ab.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "staticDo" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js));
			int position = lastPositionInFile("x.doIt().st", module);
			basicTest(module, position, names, ICategoryRequestor.TEXT_CATEGORY);
		} finally {
			if (m_fixtureManager != null) {
				m_fixtureManager.tearDown();
			}

		}
	}
	
	private void doTypeCodeCompleate(IJSSourceModule module, int position, String completionName) throws ModelException {
		assertNotSame("Invalid file content, cant find position", -1, position);

		VjoTypeCompletionProposalComputer computer = new VjoTypeCompletionProposalComputer();
		ScriptEditor vEditor = (ScriptEditor) getEditor(module);
		ISourceViewer viewer = vEditor.getViewer();
		
		ScriptContentAssistInvocationContext context = new ScriptContentAssistInvocationContext(viewer, position, vEditor, VjoNature.NATURE_ID){
			protected CompletionProposalLabelProvider createLabelProvider() {
				return null;
			}
		};
			
		List<ScriptCompletionProposal> completions = computer.computeCompletionProposals(context, null);
		
		ScriptCompletionProposal proposal = null;
		
		for (ScriptCompletionProposal vjoCompletionProposal : completions) {
			if (completionName.equals(vjoCompletionProposal.getDisplayString())) {
				proposal = vjoCompletionProposal;
				break;
			}
		}
		
		if (proposal != null) {
			proposal.apply(viewer.getDocument());
		}
		
		vEditor.doSave(null);
	}
	
	/**
	 * Test add import after type completion in props block
	 * 
	 * @throws ModelException
	 */
	public void testAddImport() throws ModelException {
		String js = "static/Bb.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String dispalyName = new String("Ab - static");
			String name = "Ab";
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js));
			int position = lastPositionInFile("A", module);
			doTypeCodeCompleate(module, position, dispalyName);
			
			IJstType type = mgr.findType(new TypeName(TestConstants.PROJECT_NAME_VJETPROJECT, "static.Bb"));
			assertTrue(type.getImports().size() > 0);
			IJstTypeReference ref = type.getImportRef(name);
			assertNotNull(ref);
			assertTrue(ref.getSource().getStartOffSet() > type.getSource().getStartOffSet());
			
		} finally {
			if (m_fixtureManager != null) {
				m_fixtureManager.tearDown();
			}

		}
	}
	
	/**
	 * VJET - Invalid code completion for static members in non-static block
	 * 
	 * @throws ModelException
	 */

	// bug
	public void testStaticInProtos() throws ModelException {
		String js = "defect/TestStaticInProtosDefect.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "stA" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js));
			int position = lastPositionInFile("st", module);
			containsNames(module, position, names, ICategoryRequestor.TEXT_CATEGORY);
		} finally {
			if (m_fixtureManager != null) {
				m_fixtureManager.tearDown();
			}

		}
	}
	
	/**
	 * VJET - Invalid code completion for static object instance
	 * 
	 * @throws ModelException
	 */

	// bug
	public void testStaticInstance() throws ModelException {
		String js = "defect/TestStaticInProtosDefect.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "ggg" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js));
			int position = lastPositionInFile("x.", module);
			containsNames(module, position, names, ICategoryRequestor.TEXT_CATEGORY);
		} finally {
			if (m_fixtureManager != null) {
				m_fixtureManager.tearDown();
			}

		}
	}

	/**
	 * Tests on vjo main for instance method
	 * 
	 * @throws ModelException
	 */
	// bug
	public void _testMain1() throws ModelException {
		String js = "main/Test1.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "foo()" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js));
			int position = lastPositionInFile("test1.", module);
			basicTest(module, position, names, ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			if (m_fixtureManager != null) {
				m_fixtureManager.tearDown();
			}

		}
	}

	/**
	 * Tests on vjo main for static method
	 * 
	 * @throws ModelException
	 */
	public void _testMain2() throws ModelException {
		String js = "main/Test2.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "stFfoo()" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js));
			int position = lastPositionInFile("Test1.", module);
			basicTest(module, position, names, ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			if (m_fixtureManager != null) {
				m_fixtureManager.tearDown();
			}

		}
	}

	/**
	 * Tests on vjo main for overloaded constructor
	 * 
	 * @throws ModelException
	 */
	public void _testMain3() throws ModelException {
		String js = "main/Test3.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "foo()" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js));
			int position = lastPositionInFile("test3.", module);
			basicTest(module, position, names, ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			if (m_fixtureManager != null) {
				m_fixtureManager.tearDown();
			}

		}
	}

	// }
	/**
	 * Tests on vjo main-show required arg stubs for instance method
	 * 
	 * @throws ModelException
	 */
	public void _testMain4() throws ModelException {
		String js = "main/Test4.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "doIt(x,y,isAdd)" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js));
			int position = lastPositionInFile("testObj.", module);
			basicTest(module, position, names, ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			if (m_fixtureManager != null) {
				m_fixtureManager.tearDown();
			}

		}
	}

	/**
	 * Tests for showing required arg stubs for static method
	 * 
	 * @throws ModelException
	 */
	// bug
	public void _testMain5() throws ModelException {
		String js = "main/Test4.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "doStatic1(Z)" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js));
			int position = lastPositionInFile("doStatic2:function(){this.",
					module);
			basicTest(module, position, names, ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			if (m_fixtureManager != null) {
				m_fixtureManager.tearDown();
			}

		}
	}
		
	//test case for http://quickbugstage.arch.ebay.com/show_bug.cgi?id=674
	public void testLocalVarCompletion() throws ModelException {
		String js = "VjoVarTest.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "x", "y", "zfr" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js));
			int position = lastPositionInFile("var x, y, zfr; ", module);
			containsNames(module, position, names, ICategoryRequestor.TEXT_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}
	
	//test function arguments completion inside function body
	public void testLocalVarCompletion2() throws ModelException {
		String js = "VjoVarTest.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "ctr", "strr" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js));
			int position = lastPositionInFile("(ctr, strr) { ", module);
			containsNames(module, position, names, ICategoryRequestor.TEXT_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}
	
	//test case for http://quickbugstage.arch.ebay.com/show_bug.cgi?id=1599
	public void testLocalVarCompletion3() throws ModelException {
		String js = "VjoVarTest.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "zfr" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js));
			int position = lastPositionInFile("       	z", module);
			containsNames(module, position, names, ICategoryRequestor.TEXT_CATEGORY);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	//test new method stub completion after comma
	public void testNewMethodStubCompletion() throws ModelException {
		String js = "VjoVarTest.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "hello" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js));
			int position = lastPositionInFile("hello", module);
			assertNotSame("Invalid file content, cant find position", -1, position);

			LinkedList<CompletionProposal> results = new LinkedList<CompletionProposal>();

			VjoCompletionEngine c = createEngine(results, ICategoryRequestor.TEXT_CATEGORY, module);
			c.complete((ISourceModule) module, position, 0);
			assertTrue("New method stub completion should be provided", results.size() > 0);
			CompletionProposal cp = results.getFirst();
			assertTrue("Invalid completion proposal kind", cp.getKind() == CompletionProposal.POTENTIAL_METHOD_DECLARATION);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}
	
	//test new method stub completion inside method (fix for )
	public void testNewMethodStubCompletion1() throws ModelException {
		String js = "TryTest.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js));
			int position = lastPositionInFile("try {} ry", module);
			assertNotSame("Invalid file content, cant find position", -1, position);

			LinkedList<CompletionProposal> results = new LinkedList<CompletionProposal>();

			VjoCompletionEngine c = createEngine(results, ICategoryRequestor.TEXT_CATEGORY, module);
			c.complete((ISourceModule) module, position, 0);
			assertTrue("New method stub completion should be provided", results.size() == 0);
			/*CompletionProposal cp = results.getFirst();
			assertFalse("Invalid completion proposal kind", cp.getKind() == CompletionProposal.POTENTIAL_METHOD_DECLARATION);*/
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}
	
	//test method override completion
	public void testMethodOverride() throws ModelException {
		String js = "inheritance/codeassist/B.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			String[] names = new String[] { "foo" };
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js));
			int position = lastPositionInFile("protos({ ", module);
			basicNameTest(module, position, names, ICategoryRequestor.TYPE_CATEGORY);
			position = lastPositionInFile("}, ", module);
			basicNameTest(module, position, names, ICategoryRequestor.TYPE_CATEGORY);
		} finally {
			if (m_fixtureManager != null) {
				m_fixtureManager.tearDown();
			}
		}
	}
	
	@Override
	protected String getProjectName() {	
		return TestConstants.PROJECT_NAME_VJETPROJECT;
	}
}