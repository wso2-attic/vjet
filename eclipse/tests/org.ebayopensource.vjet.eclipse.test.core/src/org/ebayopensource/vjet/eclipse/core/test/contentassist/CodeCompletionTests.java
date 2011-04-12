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

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.dltk.mod.compiler.env.ISourceModule;
import org.eclipse.dltk.mod.core.CompletionProposal;
import org.eclipse.dltk.mod.core.ModelException;

import org.ebayopensource.vjet.eclipse.codeassist.VjoCompletionEngine;
import org.ebayopensource.vjet.eclipse.core.IJSSourceModule;
import org.ebayopensource.vjet.eclipse.core.search.matching.ICategoryRequestor;
import org.ebayopensource.vjet.eclipse.core.test.parser.AbstractVjoModelTests;

public class CodeCompletionTests extends AbstractVjoModelTests {

	private static boolean isFirstRun = true;

	// @Before
	public void setUp() {
		setWorkspaceSufix("1");
		IProject project = getWorkspaceRoot().getProject(getTestProjectName());

		if (isFirstRun) {
			try {
				super.deleteResource(project);
			} catch (CoreException e) {
				e.printStackTrace();
			}
			super.setUpSuite();
			isFirstRun = false;
		}
	}

	/**
	 * completion on keyword(empty file)
	 * 
	 * @throws ModelException
	 */
	// @Test
	public void test0() throws ModelException {
		String[] names = new String[] { "vjo" };
		IJSSourceModule module = (IJSSourceModule) getSourceModule(
				getTestProjectName(), "src", new Path("test/test0.js"));
		basicTest(module, 0, names, ICategoryRequestor.TYPE_CATEGORY);
	}

	/**
	 * completion on keyword(import)
	 * 
	 * @throws ModelException
	 */
	// @Test
	public void test1() throws ModelException {
		String[] names = new String[] { "needs(\'\')" };// ,
		// "vjo.needsLib(\"\");"
		// };
		IJSSourceModule module = (IJSSourceModule) getSourceModule(
				getTestProjectName(), "src", new Path("test/test1.js"));
		int position = lastPositionInFile(".n", module);
		basicTest(module, position, names, ICategoryRequestor.TYPE_CATEGORY);
	}

	/**
	 * completion on keyword(type)
	 * 
	 * @throws ModelException
	 */
	// @Test
	public void test2() throws ModelException {
		String[] names = new String[] { "ctype('test.test2')" };
		IJSSourceModule module = (IJSSourceModule) getSourceModule(
				getTestProjectName(), "src", new Path("test/test2.js"));
		int position = lastPositionInFile("vjo.c", module);
		basicTest(module, position, names, ICategoryRequestor.TYPE_CATEGORY);
	}

	/**
	 * completion on type(imports)
	 * 
	 * @throws ModelException
	 */
	// @Test
	public void test3() throws ModelException {
		String[] names = new String[0];
		IJSSourceModule module = (IJSSourceModule) getSourceModule(
				getTestProjectName(), "src", new Path("test/test3.js"));
		int position = lastPositionInFile("vjo.needs(\"", module);
		basicTest(module, position, names, ICategoryRequestor.TEXT_CATEGORY);
	}

	// @Test

	public void testKeywordsCompletion() throws ModelException {
		IJSSourceModule source = (IJSSourceModule) getSourceModule(
				getTestProjectName(), "src", new Path("test/test.js"));

		String content = source.getSource();

		LinkedList<CompletionProposal> proposals = new LinkedList<CompletionProposal>();

		VjoCompletionEngine engine = createEngine(proposals, "src", source);

		int position = 0; // at the start of editor page

		engine.complete((ISourceModule) source, position, 0);
		compareCompletions(proposals, new String[] { "vjo" }, false);

		position = firstPositionInFile(".needs('vjo.", source) - 10;
		//cursor at .needs() 
		proposals.clear();

		engine.complete((ISourceModule) source, position, 0);
		compareCompletions(proposals, new String[] { "needs(\'\')" }, false);

		proposals.clear();
		String text = "vjo.type('test.test')";
		position = content.indexOf(text);

		if (position > 0) {

			position += text.length();

			while (Character.isWhitespace(content.charAt(position++)))
				;

			engine.complete((ISourceModule) source, position, 0);
			
			compareCompletions(proposals, new String[] { "inherits('')",
					"inits(function(){})", "mixin('')",
					"mixinProps('')", "needs(\'\')",
					"satisfies('')", "endType()" }, false);
		}

		proposals.clear();
		text = "function(svcId, handler) {han";
		position = content.indexOf(text, position);

		if (position > 0) {

			position += text.length();

			engine.complete((ISourceModule) source, position, 0);
			
			for(CompletionProposal pr : proposals){
				System.out.println(pr);
			}
			
			compareCompletions(proposals, new String[] { "handleRequest", "handler"}, false);
		}
	}

	// @Test
	public void testCompletionAfterHtmlDocument() throws ModelException {

		IJSSourceModule source = (IJSSourceModule) getSourceModule(
				getTestProjectName(), "src", new Path("test/MType.js"));

		String content = source.getSource();

		LinkedList<CompletionProposal> proposals = new LinkedList<CompletionProposal>();

		VjoCompletionEngine engine = createEngine(proposals, "src", source);
		int position = content.indexOf("body.aLink =");
		engine.complete((ISourceModule) source, position, 0);
		assertTrue(proposals.size() > 0);
	}

	/**
	 * completion on endType
	 * 
	 * @throws ModelException
	 */
	public void testExclusionEndType() throws ModelException {
		String[] names = new String[] { "inits(function(){})",
				"mixinProps('')", "mixin('')", "protos({})" };
		IJSSourceModule module = (IJSSourceModule) getSourceModule(
				getTestProjectName(), "src", new Path("test/MType.js"));
		
		int position = lastPositionInFile(".end", module) - 3; // test on
		// .<cursor>endType()
		containsNames(module, position, names, ICategoryRequestor.TEXT_CATEGORY);
	}

	/**
	 * VJET - makeFinal() can be removed from proposals
	 * 
	 * @throws ModelException
	 */
	public void testExclusionMakeFinal() throws ModelException {
		String[] names = new String[] { "makeFinal()" };
		IJSSourceModule module = (IJSSourceModule) getSourceModule(
				getTestProjectName(), "src", new Path("test/test.js"));
		
		int position = lastPositionInFile("vjo.type('test.test')", module) +1 ; // test on vjo.T.<cursor>
		excludesNames(module, position, names, ICategoryRequestor.TEXT_CATEGORY);
	}
	
	public void testEndType() throws ModelException {
		String[] names = new String[] { "endType()" };
		IJSSourceModule module = (IJSSourceModule) getSourceModule(
				getTestProjectName(), "src", new Path("test/test8.js"));

		int position = lastPositionInFile(null, module) - 1; // test on
		// .<cursor>
		containsNames(module, position, names, ICategoryRequestor.TEXT_CATEGORY);
	}

	/**
	 * completion on non-repeatable proposals
	 * 
	 * @throws ModelException
	 */
	public void testExclusion() throws ModelException {
		String[] names = new String[] { "inherits('')", "satisfies('')" };
		IJSSourceModule module = (IJSSourceModule) getSourceModule(
				getTestProjectName(), "src", new Path("test/keywords.js"));
		int position = lastPositionInFile(".props", module) - 5; // test on
		// .<cursor>props
		containsNames(module, position, names, ICategoryRequestor.TEXT_CATEGORY);
	}
	
	/**
	 * completion on non-repeatable proposals
	 * 
	 * @throws ModelException
	 */
	public void testRepeatProtosExclusion() throws ModelException {
		String[] names = new String[] { "protos({})" };
		IJSSourceModule module = (IJSSourceModule) getSourceModule(
				getTestProjectName(), "src", new Path("test/repeatExclusion.js"));
		int position = lastPositionInFile(".protos", module) - 6; // test on
		// .<cursor>props
		excludesNames(module, position, names, ICategoryRequestor.TEXT_CATEGORY);
	}
	
	/**
	 * completion on non-repeatable proposals
	 * 
	 * @throws ModelException
	 */
	public void testRepeatPropsExclusion() throws ModelException {
		String[] names = new String[] { "props({})" };
		IJSSourceModule module = (IJSSourceModule) getSourceModule(
				getTestProjectName(), "src", new Path("test/repeatExclusion2.js"));
		int position = lastPositionInFile(".props", module) - 5; // test on
		// .<cursor>props
		excludesNames(module, position, names, ICategoryRequestor.TEXT_CATEGORY);
	}

	/**
	 * completion on type(imports)
	 * 
	 * @throws ModelException
	 */
	// @Test
	public void test4() throws ModelException {
		String[] names = new String[] { "typeCompletion.*",
				"typeCompletion.tata.*", "typeCompletion.test1.*",
				"typeCompletion.test1.test1_1.*", "typeCompletion.test2.*", };
		IJSSourceModule module = (IJSSourceModule) getSourceModule(
				getTestProjectName(), "src", new Path("test/test4.js"));
		int position = lastPositionInFile(".needs(\"type", module);
		basicTest(module, position, names, ICategoryRequestor.TEXT_CATEGORY);
	}

	/**
	 * completion on type(imports)
	 * 
	 * @throws ModelException
	 */
	// @Test
	public void test5() throws ModelException {
		String[] names = new String[] { "TypeA", "TypeB",
				"typeCompletion.test1.test1_1.*" };
		IJSSourceModule module = (IJSSourceModule) getSourceModule(
				getTestProjectName(), "src", new Path("test/test5.js"));
		int position = lastPositionInFile(".needs(\"typeCompletion.test1.",
				module);
		basicTest(module, position, names, ICategoryRequestor.TEXT_CATEGORY);
	}

	/**
	 * completion on type(imports)
	 * 
	 * @throws ModelException
	 */
	// @Test
	public void test6() throws ModelException {
		String[] names = new String[] { "TypeC" };
		IJSSourceModule module = (IJSSourceModule) getSourceModule(
				getTestProjectName(), "src", new Path("test/test6.js"));
		int position = lastPositionInFile(
				".needs(\"typeCompletion.test1.test1_1.", module);
		basicTest(module, position, names, ICategoryRequestor.TEXT_CATEGORY);
	}

	/**
	 * completion on type(inherits)
	 * 
	 * @throws ModelException
	 */
	// @Test
	public void test7() throws ModelException {
		String[] names = new String[] { "typeCompletion.tata",
				"typeCompletion.test1", "typeCompletion.test1.test1_1",
				"typeCompletion.test2" };
		IJSSourceModule module = (IJSSourceModule) getSourceModule(
				getTestProjectName(), "src", new Path("test/test7.js"));
		int position = lastPositionInFile(".inherits (\"typeCompletion.t",
				module);
		basicTest(module, position, names, ICategoryRequestor.TEXT_CATEGORY);
	}

	/**
	 * completion on enumeration type(etype)
	 * 
	 * @throws ModelException
	 */
	// @Test vjo.etype("test.test8a").
	public void test8() throws ModelException {
		String[] names = new String[] { "protos({})", "values()" };
		IJSSourceModule module = (IJSSourceModule) getSourceModule(
				getTestProjectName(), "src", new Path("test/test8a.js"));
		int position = lastPositionInFile("vjo.etype('test.test8a').", module);
		basicTest(module, position, names, ICategoryRequestor.TEXT_CATEGORY);
	}

	/**
	 * completion on values in etype
	 * 
	 * @throws ModelException
	 */
	// @Test
	public void test8a() throws ModelException {
		IJSSourceModule module = (IJSSourceModule) getSourceModule(
				getTestProjectName(), "src", new Path("test/test8.js"));
		String[] names = new String[] { "MON : [val,displayName]" };
		int position = firstPositionInFile("MON", module);
		basicTest(module, position, names, ICategoryRequestor.TEXT_CATEGORY);
	}

	public void testKeywords() throws ModelException {
		IJSSourceModule module = (IJSSourceModule) getSourceModule(
				getTestProjectName(), "src", new Path("test/keywords.js"));
		String[] names = new String[] { "vjo" };
		int position = firstPositionInFile("vjo;", module) - 1;
		containsNames(module, position, names, ICategoryRequestor.TEXT_CATEGORY);

		names = new String[] { "instanceof" };
		position = firstPositionInFile("ins;", module) - 1;
		containsNames(module, position, names, ICategoryRequestor.TEXT_CATEGORY);

		names = new String[] { "switch" };
		position = firstPositionInFile("swi;", module) - 1;
		containsNames(module, position, names, ICategoryRequestor.TEXT_CATEGORY);

		names = new String[] { "typeof()" };
		position = firstPositionInFile("type;", module) - 1;
		containsNames(module, position, names, ICategoryRequestor.TEXT_CATEGORY);
	}

}
