/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.core.test.ui;

import java.lang.reflect.Field;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.dltk.mod.ui.CodeFormatterConstants;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

import org.ebayopensource.vjet.eclipse.core.IJSSourceModule;
import org.ebayopensource.vjet.eclipse.core.test.FixtureUtils;
import org.ebayopensource.vjet.eclipse.internal.ui.editor.VjoEditor;
import org.ebayopensource.vjet.eclipse.ui.VjetUIPlugin;
import org.ebayopensource.vjet.testframework.fixture.FixtureManager;
import org.ebayopensource.vjet.testframework.view.RangeIndicatorUtil;

public class VjoAutoEditorStrategyTester extends
		AbstractVjoAutoEditorStrategyTester {

	// @Test
	public void testIndent1() throws Exception {
		String js = "autoEdit/indentTest1";
		String goldenJs = js + "Golden";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js
				+ ".js");
		try {
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js + ".js"));
			IJSSourceModule goldenModule = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(goldenJs + ".js"));

			basicTest(module, 58, goldenModule);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}

	}

	// @Test
	public void testIndent2() throws Exception {
		String js = "autoEdit/indentTest2";
		String goldenJs = js + "Golden";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js
				+ ".js");
		try {
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js + ".js"));
			IJSSourceModule goldenModule = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(goldenJs + ".js"));

			basicTest(module, 130, goldenModule);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}

	}

	public void testIndent3() throws Exception {
		String js = "autoEdit/indentTest3";
		String goldenJs = js + "Golden";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js
				+ ".js");
		try {
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js + ".js"));
			IJSSourceModule goldenModule = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(goldenJs + ".js"));

			basicTest(module, 130, goldenModule);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}

	}

	public void testBug4579a() throws Exception {
		String js = "autoEdit/indentBug4579a";

		String goldenJs = js + "Golden";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js
				+ ".js");
		try {
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js + ".js"));
			IJSSourceModule goldenModule = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(goldenJs + ".js"));

			String text = "myProp: 234,\r\n\tmyProp2: \"hello world\",";
			basicTest(module, 89, text, goldenModule);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}

	}

	public void testBug7553() throws Exception {
		String js = "autoEdit/indentBug7553";

		String goldenJs = js + "Golden";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js
				+ ".js");
		try {
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js + ".js"));
			IJSSourceModule goldenModule = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(goldenJs + ".js"));

			String text = "\t//>public constructs()\r\n\tconstructs : function(){\r\n        this.ia = 10;\r\n\t}";
			basicTest(module, 82, 77, text, goldenModule);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}

	}

	public void testBug7694() throws Exception {
		String js = "autoEdit/indentTest7694";
		String goldenJs = js + "Golden";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js
				+ ".js");
		try {
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js + ".js"));
			IJSSourceModule goldenModule = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(goldenJs + ".js"));

			basicTest(module, 130, goldenModule);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}

	}

	public void testBug7698a() throws Exception {
		String js = "autoEdit/indentTest7698a";
		String goldenJs = js + "Golden";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js
				+ ".js");
		try {
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js + ".js"));
			IJSSourceModule goldenModule = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(goldenJs + ".js"));

			basicTest(module, 138, goldenModule);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}

	}

	public void testBug7698b() throws Exception {
		String js = "autoEdit/indentTest7698b";
		String goldenJs = js + "Golden";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js
				+ ".js");
		try {
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js + ".js"));
			IJSSourceModule goldenModule = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(goldenJs + ".js"));

			basicTest(module, 137, goldenModule);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}

	}

	public void testBug6012() throws Exception {
		String js = "autoEdit/indentTest6012";
		String goldenJs = js + "Golden";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js
				+ ".js");
		try {
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js + ".js"));
			IJSSourceModule goldenModule = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(goldenJs + ".js"));

			basicTest(module, 49, goldenModule);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}

	}

	public void testBug7679() throws Exception {
		String js = "autoEdit/indentTest7679";

		String goldenJs = js + "Golden";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js
				+ ".js");
		try {
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js + ".js"));
			IJSSourceModule goldenModule = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(goldenJs + ".js"));

			String text = "\r\n\tx : function(){\r\n\t}";
			basicTest(module, 111, 10, text, goldenModule);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}

	}

	public void testBug6479() throws Exception {
		String js = "autoEdit/indentTest6479";

		String goldenJs = js + "Golden";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js
				+ ".js");
		try {
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js + ".js"));
			IJSSourceModule goldenModule = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(goldenJs + ".js"));

			basicTest(module, 165, goldenModule);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}

	}

	private void testTabSize(int tabSize, VjoEditor editor) {
		try {
			Class clazz = editor.getClass().getClassLoader().loadClass(
					"org.eclipse.ui.texteditor.AbstractTextEditor");
			Field method = clazz.getDeclaredField("fSourceViewer");
			method.setAccessible(true);
			ISourceViewer sourceViewer = (ISourceViewer) method.get(editor);
			int size = sourceViewer.getTextWidget().getTabs();
			assertEquals(size, tabSize);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
	}

	public void testBug4552() throws Exception {
		String js = "autoEdit/indentTest4552";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js
				+ ".js");
		IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(
				new Path(getProjectName() + "/src/" + js + ".js"));

		VjoEditor editor = (VjoEditor) IDE.openEditor(PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage(), file);

		try {
			IPreferenceStore store = VjetUIPlugin.getDefault()
					.getPreferenceStore();
			store.setValue(CodeFormatterConstants.FORMATTER_TAB_SIZE, 10);
			store.setValue(CodeFormatterConstants.FORMATTER_TAB_CHAR,
					"Tabs only");
			store.setValue(CodeFormatterConstants.FORMATTER_TAB_SIZE, 4);
			testTabSize(4, editor);

		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	public void testBug2512() throws Exception {
		String js = "autoEdit/indentTest2512";

		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js
				+ ".js");
		IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(
				new Path(getProjectName() + "/src/" + js + ".js"));

		VjoEditor editor = (VjoEditor) IDE.openEditor(PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage(), file);

		try {
			IPreferenceStore store = VjetUIPlugin.getDefault()
					.getPreferenceStore();
			testTabSize(
					store.getInt(CodeFormatterConstants.FORMATTER_TAB_SIZE),
					editor);
			store.setValue(CodeFormatterConstants.FORMATTER_TAB_SIZE, 10);
			testTabSize(10, editor);

		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}

	}

	public void testBug3970() throws Exception {
		String js = "autoEdit/indentTest3970";

		String goldenJs = js + "Golden";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js
				+ ".js");
		try {
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js + ".js"));
			IJSSourceModule goldenModule = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(goldenJs + ".js"));

			basicTest(module, 59, goldenModule);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}

	}

	public void testBug7986a() throws Exception {
		String js = "autoEdit/indentTest7986a";

		String goldenJs = js + "Golden";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js
				+ ".js");
		try {
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js + ".js"));
			IJSSourceModule goldenModule = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(goldenJs + ".js"));

			basicTest(module, 103, goldenModule);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}

	}

	public void testBug7986b() throws Exception {
		String js = "autoEdit/indentTest7986b";

		String goldenJs = js + "Golden";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js
				+ ".js");
		try {
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js + ".js"));
			IJSSourceModule goldenModule = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(goldenJs + ".js"));

			indentTest(module, 123, goldenModule);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}

	}

	public void testBug8748a() throws Exception {
		String js = "autoEdit/indentTest8748a";

		String goldenJs = js + "Golden";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js
				+ ".js");
		try {
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js + ".js"));
			IJSSourceModule goldenModule = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(goldenJs + ".js"));

			basicTest(module, 107, goldenModule);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}

	}

	public void testBug8748b() throws Exception {
		String js = "autoEdit/indentTest8748b";

		String goldenJs = js + "Golden";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js
				+ ".js");
		try {
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js + ".js"));
			IJSSourceModule goldenModule = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(goldenJs + ".js"));

			basicTest(module, 108, goldenModule);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}

	}

	/**
	 * Test the source viewer of Vjet editor and if the start and end line equal
	 * the given value, it passes.
	 * 
	 * Add by Oliver.
	 * 
	 * @throws Exception
	 */
	public void testBug3811() throws Exception {
		String js = "autoEdit/test3811";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js
				+ ".js");
		IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(
				new Path(getProjectName() + "/src/" + js + ".js"));

		VjoEditor editor = (VjoEditor) IDE.openEditor(PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage(), file);

		try {
			try {
				Class clazz = editor.getClass().getClassLoader().loadClass(
						"org.ebayopensource.vjet.eclipse.internal.ui.editor.VjoEditor");
				Field fSourceViewer = clazz.getDeclaredField("fSourceViewer");
				fSourceViewer.setAccessible(true);
				ISourceViewer sourceViewer = (ISourceViewer) fSourceViewer.get(editor);
				boolean hasIcon = RangeIndicatorUtil.hasRangeIndication(
						sourceViewer, 0, 3);
				assertEquals(hasIcon, true);
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			}

		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}
}
