/*******************************************************************************
 * Copyright (c) 2000-2011 IBM Corporation and others, eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     eBay Inc - modification
 *******************************************************************************/
package org.eclipse.dltk.mod.debug.ui.breakpoints;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.core.model.ILineBreakpoint;
import org.eclipse.dltk.mod.core.DLTKLanguageManager;
import org.eclipse.dltk.mod.core.IDLTKLanguageToolkit;
import org.eclipse.dltk.mod.core.IModelElement;
import org.eclipse.dltk.mod.core.IType;
import org.eclipse.dltk.mod.debug.core.DLTKDebugPlugin;
import org.eclipse.dltk.mod.debug.core.ScriptDebugManager;
import org.eclipse.dltk.mod.debug.core.model.IScriptBreakpoint;
import org.eclipse.dltk.mod.debug.core.model.IScriptMethodEntryBreakpoint;
import org.eclipse.dltk.mod.debug.ui.DLTKDebugUIPlugin;
import org.eclipse.dltk.mod.internal.debug.core.model.AbstractScriptBreakpoint;
import org.eclipse.dltk.mod.internal.debug.core.model.ScriptDebugModel;
import org.eclipse.dltk.mod.internal.debug.ui.ExternalFileEditorInput;
import org.eclipse.dltk.mod.ui.DLTKUILanguageManager;
import org.eclipse.dltk.mod.ui.IDLTKUILanguageToolkit;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IStorageEditorInput;
import org.eclipse.ui.texteditor.ITextEditor;

public class BreakpointUtils {
	public static String getNatureId(IScriptBreakpoint breakpoint) {
		ScriptDebugManager manager = ScriptDebugManager.getInstance();
		return manager.getNatureByDebugModel(breakpoint.getModelIdentifier());
	}

	public static IDLTKLanguageToolkit getLanguageToolkit(
			IScriptBreakpoint breakpoint) {
		return DLTKLanguageManager.getLanguageToolkit(getNatureId(breakpoint));
	}

	public static IDLTKUILanguageToolkit getUILanguageToolkit(
			IScriptBreakpoint breakpoint) {
		return DLTKUILanguageManager
				.getLanguageToolkit(getNatureId(breakpoint));
	}

	public static void addLineBreakpoint(ITextEditor textEditor, int lineNumber)
			throws CoreException {
		IDocument document = textEditor.getDocumentProvider().getDocument(
				textEditor.getEditorInput());

		IResource resource = getBreakpointResource(textEditor);
		try {
			IRegion line = document.getLineInformation(lineNumber - 1);
			int start = line.getOffset();
			int end = start + line.getLength();

			String debugModelId = getDebugModelId(textEditor, resource);
			if (debugModelId == null)
				return;

			IPath location = getBreakpointResourceLocation(textEditor);
			ScriptDebugModel.createLineBreakpoint(debugModelId, resource,
					location, lineNumber, start, end, true, null);
		} catch (BadLocationException e) {
			DLTKDebugPlugin.log(e);
		}
	}

	public static void addSpawnpoint(ITextEditor textEditor, int lineNumber)
			throws CoreException {
		IDocument document = textEditor.getDocumentProvider().getDocument(
				textEditor.getEditorInput());

		IResource resource = getBreakpointResource(textEditor);
		try {
			IRegion line = document.getLineInformation(lineNumber - 1);
			int start = line.getOffset();
			int end = start + line.getLength();

			String debugModelId = getDebugModelId(textEditor, resource);
			if (debugModelId == null)
				return;

			IPath location = getBreakpointResourceLocation(textEditor);
			ScriptDebugModel.createSpawnpoint(debugModelId, resource, location,
					lineNumber, start, end, true, null);
		} catch (BadLocationException e) {
			DLTKDebugPlugin.log(e);
		}
	}

	public static IResource getBreakpointResource(ITextEditor textEditor) {
		IResource resource = (IResource) textEditor.getEditorInput()
				.getAdapter(IResource.class);
		if (resource == null)
			resource = ResourcesPlugin.getWorkspace().getRoot();
		return resource;
	}

	public static IPath getBreakpointResourceLocation(ITextEditor textEditor)
			throws CoreException {
		IResource resource = (IResource) textEditor.getEditorInput()
				.getAdapter(IResource.class);
		if (resource != null)
			return new Path(resource.getLocationURI().getPath());

		// else
		IModelElement element = (IModelElement) textEditor.getEditorInput()
				.getAdapter(IModelElement.class);
		if (element != null) {
			return element.getPath();
		}
		// EBAY MOD START
		IEditorInput editorInput = textEditor.getEditorInput();
		if (editorInput instanceof IStorageEditorInput) {
			IStorage storage = getStorageFromEditorInput(editorInput);
			if (storage != null) {
				return storage.getFullPath();
			}
		} else if (editorInput instanceof ExternalFileEditorInput) {
			return ((ExternalFileEditorInput) editorInput).getPath();
		}
		// EBAY MOD END
		return null;
	}

	private static String getDebugModelId(ITextEditor textEditor,
			IResource resource) throws CoreException {
		String debugModelId = ScriptDebugModel.getDebugModelId(resource);
		if (debugModelId != null) {
			return debugModelId;
		}

		// else
		// EBAY MOD START
		IDLTKLanguageToolkit toolkit = null;
		IEditorInput editorInput = textEditor.getEditorInput();
		IModelElement element = (IModelElement) editorInput
				.getAdapter(IModelElement.class);
		if (element != null) {
			toolkit = DLTKLanguageManager.getLanguageToolkit(element);
		}

		IStorage storage = getStorageFromEditorInput(editorInput);
		if (toolkit == null && storage != null) {
			String name = storage.getName();
			IDLTKLanguageToolkit[] languageToolkits = DLTKLanguageManager
					.getLanguageToolkits();
			for (int i = 0; i < languageToolkits.length; i++) {
				toolkit = languageToolkits[i];
				IContentType contentType = Platform.getContentTypeManager()
						.getContentType(toolkit.getLanguageContentType());
				if (contentType.isAssociatedWith(name)) {
					break;
				}
			}
		}

		if (toolkit != null) {
			return ScriptDebugManager.getInstance().getDebugModelByNature(
					toolkit.getNatureId());
		}
		return null;
		// EBAY MOD END
	}

	public static ILineBreakpoint findLineBreakpoint(ITextEditor editor,
			int lineNumber) throws CoreException {
		IResource resource = getBreakpointResource(editor);
		String debugModelId = getDebugModelId(editor, resource);
		IBreakpoint[] breakpoints = DebugPlugin.getDefault()
				.getBreakpointManager().getBreakpoints(debugModelId);
		IPath breakPointResourceLocation = getBreakpointResourceLocation(editor);
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

	public static void addMethodEntryBreakpoint(ITextEditor textEditor,
			int lineNumber, String methodName) throws CoreException {
		IDocument document = textEditor.getDocumentProvider().getDocument(
				textEditor.getEditorInput());

		// EBAY MOD START
		IResource resource = getBreakpointResource(textEditor);
		// EBAY MOD END
		if (resource != null) {
			try {
				IRegion line = document.getLineInformation(lineNumber - 1);
				int start = line.getOffset();
				int end = start + line.getLength() - 1;
				// TODO
				IPath path = resource.getLocation();
				// EBAY MOD START
				IScriptMethodEntryBreakpoint methodEntryBreakpoint = ScriptDebugModel
						.createMethodEntryBreakpoint(
								getDebugModelId(textEditor, resource),
								resource, path, lineNumber, start, end, false,
								null, methodName);
				// EBAY MOD END
				methodEntryBreakpoint.setBreakOnEntry(true);
				((AbstractScriptBreakpoint) methodEntryBreakpoint)
						.register(true);
			} catch (BadLocationException e) {
				DebugPlugin.log(e);
			}
		}
	}

	public static void addWatchPoint(ITextEditor textEditor, int lineNumber,
			String fieldName) throws CoreException {
		IDocument document = textEditor.getDocumentProvider().getDocument(
				textEditor.getEditorInput());

		IResource resource = (IResource) textEditor.getEditorInput()
				.getAdapter(IResource.class);
		if (resource != null) {
			try {
				IRegion line = document.getLineInformation(lineNumber - 1);
				int start = line.getOffset();
				int end = start + line.getLength() - 1;
				// TODO
				IPath path = resource.getLocation();

				/* ILineBreakpoint b = */ScriptDebugModel.createWatchPoint(
						resource, path, lineNumber, start, end, fieldName);
			} catch (BadLocationException e) {
				DebugPlugin.log(e);
			}
		}
	}

	public static void addExceptionBreakpoint(String debugModelId,
			boolean caught, final boolean uncaught, final IType type)
			throws CoreException {
		// TODO: Resource should refer to valid script type, so debug model id
		// can be calculated from it
		IResource resource = type.getResource();
		if (resource == null || !resource.getProject().exists()) {
			resource = ResourcesPlugin.getWorkspace().getRoot();
		}
		if (resource != null) {
			ScriptDebugModel.createExceptionBreakpoint(debugModelId, resource,
					type.getTypeQualifiedName(), caught, uncaught, true, null);
		}
	}

	// EBAY MOD START
	private static IStorage getStorageFromEditorInput(IEditorInput editorInput)
			throws CoreException {
		IStorage storage = null;
		IStorageEditorInput storageEditInput = null;
		if (editorInput instanceof IStorageEditorInput) {
			storageEditInput = (IStorageEditorInput) editorInput;
		}
		if (storageEditInput != null) {
			storage = storageEditInput.getStorage();
		}
		return storage;
	}
	// EBAY MOD END
}
