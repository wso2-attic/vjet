/*******************************************************************************
 * Copyright (c) 2005-2012 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipsetestutils.view;

import junit.framework.TestCase;

import org.ebayopensource.vjet.testframework.fixture.FixtureDefManager;
import org.ebayopensource.vjet.testframework.fixture.FixtureManager;
import org.ebayopensource.vjet.testframework.fixture.FixtureUtils;
import org.ebayopensource.vjet.testframework.fixture.IFixtureDef;
import org.ebayopensource.vjet.testframework.sandbox.ISandbox;
import org.ebayopensource.vjet.testframework.sandbox.Sandbox;
import org.ebayopensource.vjet.testframework.view.BreakPointUtils;
import org.ebayopensource.vjet.testframework.view.FoldingUtils;
import org.ebayopensource.vjet.testframework.view.MarkerUtils;
import org.ebayopensource.vjet.testframework.view.ViewerUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.internal.ui.javaeditor.CompilationUnitEditor;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.projection.ProjectionAnnotation;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;



/**
 * @author zhhan
 * 
 */
public class JDTEditorTestCase extends TestCase {
	private FixtureManager fm; 
	private CompilationUnitEditor editor;
	private ISourceViewer sourceViewer;
	private IResource resource;
//	private 
	protected void setUp() throws Exception {
		fm = setupFixtures(this,"fixtures_test.xml",new String[]{"com.ebay.tools.testframework.fixture3"});
		openJavaEditor();
	}

	 public static FixtureManager setupFixtures(TestCase testCase,String fixtureFileName,String[] fixtureIDs){
		  
		   ISandbox sandBox = new Sandbox(testCase);
		  sandBox.setUp();
		  FixtureDefManager fixtureDefManager = FixtureUtils.createFixtureDefManagerFromXml(fixtureFileName, testCase, sandBox);
 
		  // Find fixture to test
		  FixtureManager fixtureManager = new FixtureManager(testCase, fixtureDefManager);
		  
		  
		  for (String id : fixtureIDs) {
		   IFixtureDef fixtureToTest = fixtureManager.getFixtures().getFixtureDef(id);
		   
		   // Call fixture.setup() to populate the values in Preferences page
		   if ((fixtureToTest == null)) {
		    throw new RuntimeException("Fixture definition not found");
		   }
		   fixtureManager.setUp(fixtureToTest.getFixtureId()); 
		  }
		  
		  
		  return fixtureManager;
		  
		 }
	protected void tearDown() throws Exception {
		fm.tearDown();
	}

	private void openJavaEditor() {
		try {
			IWorkbench workbench = PlatformUI.getWorkbench();
			IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
			workbench.showPerspective("org.eclipse.jdt.ui.JavaPerspective",
					window);
			IWorkbenchPage page = window.getActivePage();
			IWorkspace workspace = ResourcesPlugin.getWorkspace();
			IProject project = workspace.getRoot().getProject(
					"com.ebay.tools.repositoryeditor.project.artifact");
			assertTrue(project.exists() ); 
			IPath path = new Path("/src/com/ebay/tools/view/TestContent.java");
			IFile file = project.getFile(path);
			IEditorPart part = IDE.openEditor(page, file);
			if(part instanceof CompilationUnitEditor){
				this.editor = (CompilationUnitEditor)part;
				this.resource = file;
				this.sourceViewer = editor.getViewer();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void testMakerUtilsGetAllMarkersInRange() {
		assertEquals(0,MarkerUtils.getAllMarkersInRange(resource, "org.eclipse.jdt.core.problem", 0, 100).size());
	}
	
	public void testFoldingUtilsisCollapsed(){
		ProjectionAnnotation annotation = FoldingUtils.getProjectionAnnotation(sourceViewer, new Position(70,1));
		assertNotNull(annotation);
		assertFalse(FoldingUtils.isCollapsed(sourceViewer, new Position(70,1)));
		annotation.markCollapsed();
		assertTrue(FoldingUtils.isCollapsed(sourceViewer, new Position(70,1)));
	}
	
	public void testBreakPointUtilsgetBreakpoint(){
		assertNull(BreakPointUtils.getBreakpoint(editor,10));
	}
	
	public void testViewerUtilsgetTextAttribute(){
		int length = "package".length();
		TextAttribute ta = ViewerUtils.getTextAttribute(sourceViewer, IDocument.DEFAULT_CONTENT_TYPE, new Position(0,length));
		if(ta == null)
			fail("the position you defined is not correct!");
		ta.getForeground();
		assertTrue(!ta.getForeground().equals(new Color(Display.getDefault(),0,0,0)));
		ta = ViewerUtils.getTextAttribute(sourceViewer, IDocument.DEFAULT_CONTENT_TYPE, new Position(length+1,1));
		if(ta == null)
			fail("the position you defined is not correct!");
		assertTrue(ta.getForeground().equals(new Color(Display.getDefault(),0,0,0)));
		
		
	}
}
