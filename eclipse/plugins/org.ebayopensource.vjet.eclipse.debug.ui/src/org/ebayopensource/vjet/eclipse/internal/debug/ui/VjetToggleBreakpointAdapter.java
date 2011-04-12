/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.debug.ui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.core.model.ILineBreakpoint;
import org.eclipse.dltk.mod.compiler.env.ISourceModule;
import org.eclipse.dltk.mod.core.Flags;
import org.eclipse.dltk.mod.core.ICodeAssist;
import org.eclipse.dltk.mod.core.IMember;
import org.eclipse.dltk.mod.core.IMethod;
import org.eclipse.dltk.mod.core.IModelElement;
import org.eclipse.dltk.mod.core.IType;
import org.eclipse.dltk.mod.core.ModelException;
import org.eclipse.dltk.mod.debug.core.DLTKDebugPlugin;
import org.eclipse.dltk.mod.debug.core.model.IScriptLineBreakpoint;
import org.eclipse.dltk.mod.debug.core.model.IScriptMethodEntryBreakpoint;
import org.eclipse.dltk.mod.debug.ui.breakpoints.BreakpointUtils;
import org.eclipse.dltk.mod.debug.ui.breakpoints.ScriptToggleBreakpointAdapter;
import org.eclipse.dltk.mod.internal.debug.core.model.AbstractScriptBreakpoint;
import org.eclipse.dltk.mod.internal.debug.core.model.ScriptDebugModel;
import org.eclipse.dltk.mod.internal.ui.editor.ExternalStorageEditorInput;
import org.eclipse.dltk.mod.internal.ui.editor.ScriptEditor;
import org.eclipse.dltk.mod.internal.ui.editor.WorkingCopyManager;
import org.eclipse.dltk.mod.ui.DLTKUIPlugin;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditor;

import org.ebayopensource.vjet.eclipse.debug.ui.VjetDebugUIPlugin;
import org.ebayopensource.vjet.eclipse.internal.debug.VjetDebugConstants;
import org.ebayopensource.vjet.eclipse.internal.launching.VjoDBGPSourceModule;

public class VjetToggleBreakpointAdapter extends ScriptToggleBreakpointAdapter {

	private DBGPScriptCacheManager	m_cacheManger	= DBGPScriptCacheManager
															.getDefault();

	@Override
	protected String getDebugModelId() {
		return VjetDebugConstants.DEBUG_MODEL_ID;
	}

	public boolean canToggleBreakpoints(IWorkbenchPart part,
			ISelection selection) {
		return true;
	}

	public void toggleBreakpoints(IWorkbenchPart part, ISelection selection)
			throws CoreException {

		if (selection instanceof ITextSelection) {
			ITextEditor textEditor = getTextEditor(part);
			ITextSelection textSelection = (ITextSelection) selection;
			int lineNumber = textSelection.getStartLine() + 1; // one based

			ILineBreakpoint breakpoint = BreakpointUtils.findLineBreakpoint(
					textEditor, lineNumber);
			if (breakpoint != null) {
				breakpoint.delete();
				return;
			}

			if (textEditor instanceof ScriptEditor) {
				ScriptEditor scriptEditor = (ScriptEditor) textEditor;
				try {
					IDocument doc = scriptEditor.getScriptSourceViewer()
							.getDocument();

					IRegion region = doc.getLineInformation(lineNumber - 1);
					String string = doc.get(region.getOffset(), region
							.getLength());
					int index = string.indexOf("function");
					if (index != -1) {
						string = string.substring(index + "function".length())
								.trim();
						int apos = string.indexOf('(');
						if (apos >= 0) {
							string = string.substring(0, apos).trim();
						}

						// createMethodEntryBreakpoint(textEditor, lineNumber,
						// string);
						// return;
					}
				} catch (BadLocationException e) {
					DLTKDebugPlugin.log(e);
					return;
				}
			}
			// else
			createLineBreakpoint(textEditor, lineNumber);
		}
	}

	public boolean canToggleMethodBreakpoints(IWorkbenchPart part,
			ISelection selection) {
		if (isRemote(part, selection)) {
			return false;
		}
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection ss = (IStructuredSelection) selection;
			return getMethods(ss).length > 0;
		}
		return (selection instanceof ITextSelection)
				&& isMethod((ITextSelection) selection, part);
	}

	/**
	 * Returns the methods from the selection, or an empty array
	 * 
	 * @param selection
	 *            the selection to get the methods from
	 * @return an array of the methods from the selection or an empty array
	 */
	protected IMethod[] getMethods(IStructuredSelection selection) {
		if (selection.isEmpty()) {
			return new IMethod[0];
		}
		List methods = new ArrayList(selection.size());
		Iterator iterator = selection.iterator();
		while (iterator.hasNext()) {
			Object thing = iterator.next();
			try {
				if (thing instanceof IMethod) {
					IMethod method = (IMethod) thing;
					if (!Flags.isAbstract(method.getFlags())) {
						methods.add(method);
					}
				}
			} catch (ModelException e) {
			}
		}
		return (IMethod[]) methods.toArray(new IMethod[methods.size()]);
	}

	/**
	 * Returns if the text selection is a valid method or not
	 * 
	 * @param selection
	 *            the text selection
	 * @param part
	 *            the associated workbench part
	 * @return true if the selection is a valid method, false otherwise
	 */
	private boolean isMethod(ITextSelection selection, IWorkbenchPart part) {
		ITextEditor editor = getTextEditor(part);
		if (editor != null) {
			IModelElement element = getJavaElement(editor.getEditorInput());
			if (element != null) {
				try {
					if (element instanceof ISourceModule) {
						IModelElement[] elements = ((ICodeAssist) ((ISourceModule) element))
								.codeSelect(selection.getOffset(), selection
										.getLength());
						if(elements!=null){
							element = elements.length > 0 ? elements[0] : null;
						}
					}
					// else if(element instanceof IClassFile) {
					// element = ((IClassFile)
					// element).getElementAt(selection.getOffset());
					// }
					return element != null
							&& element.getElementType() == IModelElement.METHOD;
				} catch (ModelException e) {
					return false;
				}
			}
		}
		return false;
	}

	/**
	 * gets the <code>IModelElement</code> from the editor input
	 * 
	 * @param input
	 *            the current editor input
	 * @return the corresponding <code>IModelElement</code>
	 * @since 3.3
	 */
	private IModelElement getJavaElement(IEditorInput input) {

		IModelElement je = DLTKUIPlugin.getEditorInputModelElement(input);
		if (je != null) {
			return je;
		}
		// try to get from the working copy manager
		return ((WorkingCopyManager) DLTKUIPlugin.getDefault()
				.getWorkingCopyManager()).getWorkingCopy(input, false);
		// return DebugWorkingCopyManager.getWorkingCopy(input, false);
	}

	public boolean canToggleWatchpoints(IWorkbenchPart part,
			ISelection selection) {

		if (selection instanceof ITextSelection) {
			final ITextSelection textSelection = (ITextSelection) selection;
			final String text = textSelection.getText();
			if (part instanceof ScriptEditor) {
				ScriptEditor scriptEditor = (ScriptEditor) part;
				try {
					IDocument doc = scriptEditor.getScriptSourceViewer()
							.getDocument();
					IRegion region = doc.getLineInformation(textSelection
							.getStartLine());
					String string = doc.get(region.getOffset(), region
							.getLength());

					return string.indexOf('=') != -1
							|| string.trim().startsWith("var ");
				} catch (BadLocationException e) {
					DLTKUIPlugin.log(e);
				}
			}

			return text.indexOf("=") != -1;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.ui.actions.IToggleBreakpointsTarget#toggleMethodBreakpoints
	 * (org.eclipse.ui.IWorkbenchPart, org.eclipse.jface.viewers.ISelection)
	 */
	public void toggleMethodBreakpoints(final IWorkbenchPart part,
			final ISelection finalSelection) throws CoreException {
		if (!this.canToggleMethodBreakpoints(part, finalSelection))
			return;

		IMethod[] methods = getMethods((IStructuredSelection) finalSelection);
		for (int i = 0; i < methods.length; i++) {
			try {
				ITextEditor textEditor = this.getTextEditor(methods[i]);

				/**
				 * work out the script line number. To JFace, the first line has
				 * line number 0; while to Script, the first line has line
				 * number 1.
				 */
				IDocumentProvider documentProvider = (textEditor)
						.getDocumentProvider();
				IDocument document = documentProvider.getDocument(textEditor
						.getEditorInput());
				int methodSourceOffset = methods[i].getSourceRange()
						.getOffset();
				int documentLineNumber = document
						.getLineOfOffset(methodSourceOffset);
				int scriptLineNumber = documentLineNumber + 1;

				// if already have line breakpoints, skip
				ILineBreakpoint breakpoint = BreakpointUtils
						.findLineBreakpoint(textEditor, scriptLineNumber);

				if (breakpoint != null) {
					breakpoint.delete();
					continue;
				} else {
					createMethodEntryBreakpoint(textEditor, scriptLineNumber,
							"");
				}
			} catch (Exception e) {
				VjetDebugUIPlugin.error(e.getLocalizedMessage(), e);
			}
		}
	}

	/**
	 * fetch the corresponding text editor by the given method.
	 * 
	 * @param method
	 * @return
	 */
	private ITextEditor getTextEditor(IMethod method) {
		try {
			IResource resource = method.getResource();
			IWorkbenchPage workbenchPage = PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getActivePage();
			return (ITextEditor) IDE
					.openEditor(workbenchPage, (IFile) resource);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Returns if the structured selection is itself or is part of an interface
	 * 
	 * @param selection
	 *            the current selection
	 * @return true if the selection is part of an interface, false otherwise
	 * @since 3.2
	 */
	private boolean isInterface(ISelection selection, IWorkbenchPart part) {
		try {
			ISelection sel = selection;
			if (!(sel instanceof IStructuredSelection)) {
				sel = translateToMembers(part, selection);
			}
			if (sel instanceof IStructuredSelection) {
				Object obj = ((IStructuredSelection) sel).getFirstElement();
				if (obj instanceof IMember) {
					IMember member = (IMember) ((IStructuredSelection) sel)
							.getFirstElement();
					if (member.getElementType() == IModelElement.TYPE) {
						return Flags.isInterface(((IType) member).getFlags());
					}
					return Flags.isInterface(member.getDeclaringType()
							.getFlags());
				}
				// else if(obj instanceof i) {
				// IJavaFieldVariable var = (ijavafieldvariable) obj;
				// IType type =
				// JavaDebugUtils.resolveType(var.getDeclaringType());
				// return type != null && type.isInterface();
				// }
			}
		} catch (CoreException e1) {
		}
		return false;
	}

	public void toggleWatchpoints(IWorkbenchPart part, ISelection selection)
			throws CoreException {
		toggleLineBreakpoints(part, selection);

	}

	private void createMethodEntryBreakpoint(ITextEditor textEditor,
			int lineNumber, String methodName) throws CoreException {
		IEditorInput editorInput = textEditor.getEditorInput();
		IDocument document = textEditor.getDocumentProvider().getDocument(
				editorInput);

		IResource resource = BreakpointUtils.getBreakpointResource(textEditor);
		if (resource != null) {
			try {
				IRegion line = document.getLineInformation(lineNumber - 1);
				int start = line.getOffset();
				int end = start + line.getLength() - 1;
				IPath path = resource.getLocation();
				IScriptMethodEntryBreakpoint methodEntryBreakpoint = ScriptDebugModel
						.createMethodEntryBreakpoint(getDebugModelId(),
								resource, path, lineNumber, start, end, false,
								null, methodName);
				methodEntryBreakpoint.setBreakOnEntry(true);
				((AbstractScriptBreakpoint) methodEntryBreakpoint)
						.register(true);
				if (needCache(editorInput)) {
					add2Cache(editorInput, methodEntryBreakpoint);
				}
			} catch (BadLocationException e) {
				VjetDebugUIPlugin.error(e.getLocalizedMessage(), e);
			}
		}
	}

	private void createLineBreakpoint(ITextEditor textEditor, int lineNumber)
			throws CoreException {
		IEditorInput editorInput = textEditor.getEditorInput();
		IDocument document = textEditor.getDocumentProvider().getDocument(
				editorInput);

		IResource resource = BreakpointUtils.getBreakpointResource(textEditor);
		try {
			IRegion line = document.getLineInformation(lineNumber - 1);
			int start = line.getOffset();
			int end = start + line.getLength();

			IPath location = BreakpointUtils
					.getBreakpointResourceLocation(textEditor);
			IScriptLineBreakpoint breakpoint = ScriptDebugModel
					.createLineBreakpoint(getDebugModelId(), resource,
							location, lineNumber, start, end, true, null);

			// if editor input's storage is VjoDBGPSourceModule, add to cache.
			if (needCache(editorInput)) {
				add2Cache(editorInput, breakpoint);
			}
		} catch (BadLocationException e) {
			VjetDebugUIPlugin.error(e.getLocalizedMessage(), e);
		}
	}

	private void add2Cache(IEditorInput editorInput, IBreakpoint breakpoint)
			throws CoreException {
		m_cacheManger.add(breakpoint, getVjoDBGPSourceModule(editorInput));
	}

	private boolean needCache(IEditorInput editorInput) {
		return getVjoDBGPSourceModule(editorInput) != null;
	}

	private VjoDBGPSourceModule getVjoDBGPSourceModule(IEditorInput editorInput) {
		if (editorInput instanceof ExternalStorageEditorInput) {
			IStorage storage = ((ExternalStorageEditorInput) editorInput)
					.getStorage();
			if (storage instanceof VjoDBGPSourceModule) {
				return (VjoDBGPSourceModule) storage;
			}
		}
		return null;
	}
}
