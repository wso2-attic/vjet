/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/**
 * 
 */
package org.ebayopensource.vjet.eclipse.internal.debug.ui.html;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.core.model.ILineBreakpoint;
import org.eclipse.dltk.mod.debug.core.DLTKDebugPlugin;
import org.eclipse.dltk.mod.debug.core.ScriptDebugManager;
import org.eclipse.dltk.mod.debug.ui.DLTKDebugUIPlugin;
import org.eclipse.dltk.mod.debug.ui.breakpoints.BreakpointUtils;
import org.eclipse.dltk.mod.internal.debug.core.model.ScriptDebugModel;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.ui.texteditor.ITextEditor;

import org.ebayopensource.vjet.eclipse.core.VjoNature;

/**
 * 
 * 
 */
public class HtmlBreakpointUtils {

	/**
	 * utility class
	 */
	private HtmlBreakpointUtils() {
		// TODO Auto-generated constructor stub
	}

	public static void addLineBreakpoint(ITextEditor textEditor, int lineNumber)
			throws CoreException {
		IDocument document = textEditor.getDocumentProvider().getDocument(
				textEditor.getEditorInput());

		IResource resource = BreakpointUtils.getBreakpointResource(textEditor);
		try {
			IRegion line = document.getLineInformation(lineNumber - 1);
			int start = line.getOffset();
			int end = start + line.getLength();

			String debugModelId = ScriptDebugManager.getInstance().getDebugModelByNature(VjoNature.NATURE_ID);

			IPath location = BreakpointUtils.getBreakpointResourceLocation(textEditor);
			ScriptDebugModel.createLineBreakpoint(debugModelId, resource,
					location, lineNumber, start, end, true, null);
		} catch (BadLocationException e) {
			DLTKDebugPlugin.log(e);
		}
	}
	
	public static ILineBreakpoint findLineBreakpoint(ITextEditor editor,
			int lineNumber) throws CoreException {
		IResource resource = BreakpointUtils.getBreakpointResource(editor);
		String debugModelId = ScriptDebugManager.getInstance().getDebugModelByNature(VjoNature.NATURE_ID);
		IBreakpoint[] breakpoints = DebugPlugin.getDefault()
				.getBreakpointManager().getBreakpoints(debugModelId);
		IPath breakPointResourceLocation = BreakpointUtils.getBreakpointResourceLocation(editor);
		if (breakPointResourceLocation == null) {
			return null;
		}
		String location = breakPointResourceLocation.toPortableString();

		for (int i = 0; i < breakpoints.length; i++) {
			IBreakpoint breakpoint = breakpoints[i];
			IResource bpResource = breakpoint.getMarker().getResource();
			String bpLocation = (String) breakpoint.getMarker().getAttribute(
					IMarker.LOCATION);

			if (resource.equals(bpResource) && location.equals(bpLocation)) {
				ILineBreakpoint lineBreakpoint = (ILineBreakpoint) breakpoint;
				try {
					if (lineBreakpoint.getLineNumber() == lineNumber) {
						return lineBreakpoint;
					}
				} catch (CoreException e) {
					DLTKDebugUIPlugin.log(e);
				}
			}
		}

		return null;
	}
}
