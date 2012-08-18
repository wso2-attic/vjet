/*******************************************************************************
 * Copyright (c) 2005-2012 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.testframework.view;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.texteditor.ITextEditor;

/**
 * @author ddodd
 * 
 */
public class BreakPointUtils {

	private BreakPointUtils() {
		;
	}

	/**
	 * @param editor
	 * @param lineNumber
	 * @return
	 */
	public static IBreakpoint getBreakpoint(ITextEditor editor,
			int lineNumber) {
		IEditorInput editorInput = editor.getEditorInput();
		IResource resource = (IResource) editorInput.getAdapter(IResource.class);
		if(resource == null)
			return null;
		IBreakpoint[] breakpoints = DebugPlugin.getDefault()
				.getBreakpointManager().getBreakpoints();
		for (IBreakpoint bp : breakpoints) {
			IMarker marker = bp.getMarker();
			try {
				if (marker != null
						&& (lineNumber + "").equals(marker
								.getAttribute(IMarker.LINE_NUMBER)) 
								&& resource.equals(marker.getResource())) {
					return bp;
				}
			} catch (Exception e) {

			}
		}
		return null;
	}

}
