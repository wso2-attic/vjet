/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.core.test.debug;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.debug.ui.IDebugModelPresentation;
import org.eclipse.dltk.mod.debug.core.model.IScriptLineBreakpoint;
import org.eclipse.dltk.mod.debug.ui.DLTKDebugUILanguageManager;
import org.eclipse.dltk.mod.internal.debug.core.model.ScriptDebugModel;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

import org.ebayopensource.vjet.eclipse.core.VjoNature;
import org.ebayopensource.vjet.eclipse.core.test.parser.AbstractVjoModelTests;
import org.ebayopensource.vjet.eclipse.internal.ui.editor.VjoEditor;

public class OpenMarkerTest extends AbstractVjoModelTests {

	@SuppressWarnings("unchecked")
	public void testOpenMultipleVjBootstrap() throws IOException, CoreException {
		final String fileName = "VjBootstrap_3.js";
		Enumeration entries = Platform.getBundle("DsfBase").findEntries(
				String.valueOf(IPath.SEPARATOR), fileName, true);
		URL url = (URL) entries.nextElement();
		url = FileLocator.resolve(url);
		Path location = new Path(url.toString());
		IScriptLineBreakpoint bp = ScriptDebugModel.createLineBreakpoint(
				getDebugModelId(), getWorkspaceRoot(), location, 920, -1, -1,
				true, null);

		// open the breakpoint twice
		// 1st
		openBreakpointMarker(bp);
		// 2nd
		openBreakpointMarker(bp);

		// count opened editor
		Display.getDefault().syncExec(new Runnable() {

			@Override
			public void run() {
				IEditorReference[] editorReferences = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getActivePage()
						.getEditorReferences();
				int count = 0;
				for (IEditorReference ref : editorReferences) {
					if (fileName.equals(ref.getTitle())) {
						count++;
					}
				}
				assertEquals("Duplicate editors open!", 1, count);
			}
		});
	}

	private void openBreakpointMarker(final IScriptLineBreakpoint bp) {
		String debugModelId = getDebugModelId();
		IDebugModelPresentation vjetDebugModelPres = getVjetDebugModelPresentation(debugModelId);
		final IEditorInput editorInput = vjetDebugModelPres.getEditorInput(bp);

		Display.getDefault().syncExec(new Runnable() {

			@Override
			public void run() {
				IWorkbenchPage page = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getActivePage();
				try {
					IEditorPart editor = page.openEditor(editorInput,
							VjoEditor.EDITOR_ID);
					editor.setFocus();
					IDE.gotoMarker(editor, bp.getMarker());

				} catch (PartInitException e) {
					fail(e.getMessage());
				}
			}
		});
	}

	public static IDebugModelPresentation getVjetDebugModelPresentation(
			String debugModelId) {
		IDebugModelPresentation vjetDebugModelPres = DebugUITools
				.newDebugModelPresentation(debugModelId);
		return vjetDebugModelPres;
	}

	public static String getDebugModelId() {
		String debugModelId = DLTKDebugUILanguageManager.getLanguageToolkit(
				VjoNature.NATURE_ID).getDebugModelId();
		return debugModelId;
	}
}
