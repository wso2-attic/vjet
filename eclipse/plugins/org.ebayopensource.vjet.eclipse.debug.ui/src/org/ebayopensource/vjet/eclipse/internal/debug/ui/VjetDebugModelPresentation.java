/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.debug.ui;

import java.net.URI;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.ILineBreakpoint;
import org.eclipse.debug.core.model.ISourceLocator;
import org.eclipse.debug.core.model.IThread;
import org.eclipse.debug.core.sourcelookup.ISourceLookupDirector;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.dltk.mod.core.environment.EnvironmentPathUtils;
import org.eclipse.dltk.mod.core.environment.IFileHandle;
import org.eclipse.dltk.mod.dbgp.DbgpURIUtil;
import org.eclipse.dltk.mod.debug.core.DLTKDebugConstants;
import org.eclipse.dltk.mod.debug.core.DLTKDebugPlugin;
import org.eclipse.dltk.mod.debug.core.model.IScriptBreakpoint;
import org.eclipse.dltk.mod.debug.core.model.IScriptMethodEntryBreakpoint;
import org.eclipse.dltk.mod.debug.core.model.IScriptStackFrame;
import org.eclipse.dltk.mod.debug.core.model.IScriptThread;
import org.eclipse.dltk.mod.debug.core.model.IScriptValue;
import org.eclipse.dltk.mod.debug.core.model.IScriptVariable;
import org.eclipse.dltk.mod.debug.core.model.IScriptWatchpoint;
import org.eclipse.dltk.mod.debug.ui.Messages;
import org.eclipse.dltk.mod.debug.ui.ScriptDebugImageDescriptor;
import org.eclipse.dltk.mod.debug.ui.ScriptDebugImages;
import org.eclipse.dltk.mod.debug.ui.ScriptDebugModelPresentation;
import org.eclipse.dltk.mod.internal.debug.ui.ExternalFileEditorInput;
import org.eclipse.dltk.mod.internal.ui.editor.ExternalStorageEditorInput;
import org.eclipse.dltk.mod.ui.DLTKPluginImages;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.FileEditorInput;

import org.ebayopensource.vjet.eclipse.codeassist.CodeassistUtils;
import org.ebayopensource.vjet.eclipse.debug.ui.VjetDebugUIPlugin;
import org.ebayopensource.vjet.eclipse.internal.debug.VjetDebugConstants;
import org.ebayopensource.vjet.eclipse.internal.debug.VjetDebugPlugin;
import org.ebayopensource.vjet.eclipse.internal.launching.LauncherUtil;
import org.ebayopensource.vjet.eclipse.internal.launching.VjetSourceLookupParticipant;
import org.ebayopensource.vjet.eclipse.internal.launching.VjoDBGPSourceModule;

public class VjetDebugModelPresentation extends ScriptDebugModelPresentation {

	private static final String		VJET_EDITOR_ID	= "org.ebayopensource.vjet.ui.VjetJsEditor";
	static ImageRegistry			registry		= new ImageRegistry(Display
															.getDefault());
	private DBGPScriptCacheManager	m_cacheManager	= DBGPScriptCacheManager
															.getDefault();

	static {
		Display.getDefault().asyncExec(new Runnable() {

			public void run() {

				DLTKPluginImages
						.get(ScriptDebugImages.IMG_OBJS_CONTENDED_MONITOR);
			}

		});
	}

	protected final String getStackFrameText(IScriptStackFrame stackFrame) {
		URI sourceURI = stackFrame.getSourceURI();
		if (DLTKDebugConstants.DBGP_SCHEME.equals(sourceURI.getScheme())) {
			try {
				return NLS.bind(
						Messages.ScriptDebugModelPresentation_stackFrameText3,
						toString(sourceURI), new Integer(stackFrame
								.getLineNumber()));
			} catch (DebugException e) {
				VjetDebugUIPlugin.error(e.getLocalizedMessage(), e);
			}
		}
		return super.getStackFrameText(stackFrame);

	}

	protected Image getBreakpointImage(IScriptBreakpoint breakpoint) {
		if (breakpoint instanceof IScriptWatchpoint) {
			IScriptWatchpoint w = (IScriptWatchpoint) breakpoint;
			try {
				if (w.isEnabled()) {
					return DebugUITools
							.getImage(IDebugUIConstants.IMG_OBJS_WATCHPOINT);
				}
			} catch (CoreException e) {
				DLTKDebugPlugin.log(e);
			}
			return DebugUITools
					.getImage(IDebugUIConstants.IMG_OBJS_WATCHPOINT_DISABLED);
		}
		if (breakpoint instanceof IScriptMethodEntryBreakpoint) {
			IScriptMethodEntryBreakpoint ll = (IScriptMethodEntryBreakpoint) breakpoint;
			int flags = 0;

			try {
				if (ll.breakOnEntry())
					flags |= ScriptDebugImageDescriptor.ENTRY;
				if (ll.breakOnExit())
					flags |= ScriptDebugImageDescriptor.EXIT;

				if (flags == 0)
					return DebugUITools
							.getImage(IDebugUIConstants.IMG_OBJS_BREAKPOINT_DISABLED);
				if (ll.isEnabled()) {
					String key = flags + "enabled";
					Image image = registry.get(key);
					if (image == null) {
						registry
								.put(
										key,
										new ScriptDebugImageDescriptor(
												DebugUITools
														.getImageDescriptor(IDebugUIConstants.IMG_OBJS_BREAKPOINT),
												flags));
						return registry.get(key);
					}
					return image;
				} else {
					String key = flags + "disabled";
					Image image = registry.get(key);
					if (image == null) {
						registry
								.put(
										key,
										new ScriptDebugImageDescriptor(
												DebugUITools
														.getImageDescriptor(IDebugUIConstants.IMG_OBJS_BREAKPOINT_DISABLED),
												flags));
						return registry.get(key);
					}
					return image;
				}
			} catch (CoreException e) {
				DLTKDebugPlugin.log(e);

			}
		}

		return null;
	}

	protected Image getVariableImage(IScriptVariable variable) {
		IScriptVariable v = variable;
		IScriptValue scriptValue;
		try {
			scriptValue = (IScriptValue) v.getValue();
		} catch (DebugException e) {
			return ScriptDebugImages
					.get(ScriptDebugImages.IMG_OBJS_LOCAL_VARIABLE);
		}
		String typeString = (scriptValue).getType().getName();
		if (typeString.equals("function"))
			return DLTKPluginImages.get(DLTKPluginImages.IMG_METHOD_PRIVATE);
		if (typeString.equals("javaclass"))
			return DLTKPluginImages.get(DLTKPluginImages.IMG_OBJS_CLASS);
		if (typeString.equals("javaobject"))
			return DLTKPluginImages.get(DLTKPluginImages.IMG_METHOD_PROTECTED);
		if (typeString.equals("javaarray"))
			return DLTKPluginImages.get(DLTKPluginImages.IMG_METHOD_DEFAULT);
		String fullName = scriptValue.getEvalName();
		if (fullName != null) {
			if (fullName.indexOf('.') >= 0 || (fullName.equals("this"))) {
				return DLTKPluginImages.get(DLTKPluginImages.IMG_METHOD_PUBLIC);
			}
		}
		return ScriptDebugImages.get(ScriptDebugImages.IMG_OBJS_LOCAL_VARIABLE);
	}

	@Override
	public String getEditorId(IEditorInput input, Object element) {
		if (CodeassistUtils.isVjetFileName(input.getName())) {
			return VJET_EDITOR_ID;
		} else {
			if (input instanceof IFileEditorInput)
				return IDE.getDefaultEditor(
						((IFileEditorInput) input).getFile()).getId();
		}
		return VJET_EDITOR_ID;
	}

	// add b patrick
	@Override
	public IEditorInput getEditorInput(Object element) {
		IEditorInput editorInput = super.getEditorInput(element);
		if (editorInput != null) {
			return editorInput;
		}
		if (element instanceof ILineBreakpoint) {
			editorInput = getLineBreakpointEditorInput((ILineBreakpoint) element);
		}
		return editorInput;
	}

	private IEditorInput getLineBreakpointEditorInput(
			ILineBreakpoint lineBreakpoint) {
		// check cache
		if (m_cacheManager.contains(lineBreakpoint)) {
			return new ExternalStorageEditorInput(m_cacheManager
					.get(lineBreakpoint));
		}

		// check marker location
		IMarker marker = lineBreakpoint.getMarker();
		if (marker == null) {
			return null;
		}

		String location = null;
		try {
			location = (String) marker.getAttribute(IMarker.LOCATION);
		} catch (CoreException e) {
			VjetDebugPlugin.error(e.getLocalizedMessage(), e);
		}
		if (location == null) {
			return null;
		}
		IPath path = Path.fromPortableString(location);
		if (EnvironmentPathUtils.isFull(path)) {
			path = EnvironmentPathUtils.getLocalPath(path);
		}
		IStorage storage = null;
		if (isZipFile(path)) {
			try {
				storage = LauncherUtil.createZipEntryFile(new URI(path
						.toString()));
			} catch (Exception e) {
				VjetDebugPlugin.error(e.getLocalizedMessage(), e);
			}
		} else if (DbgpURIUtil.isDBGPPath(path)) {
			// try to find the dbgp virtual file by existing launches.
			ILaunch[] launches = getLaunches();
			for (ILaunch launch : launches) {
				ISourceLookupDirector sourceLookupDirector = getSourceLookupDirector(launch);
				if (sourceLookupDirector == null) {
					continue;
				}
				try {
					Object sourceElement = sourceLookupDirector
							.getSourceElement(DbgpURIUtil
									.convert2DBGPURIFromPath(path));
					VjoDBGPSourceModule vjoDBGPSourceModule = null;
					if (sourceElement instanceof IAdaptable) {
						vjoDBGPSourceModule = (VjoDBGPSourceModule) ((IAdaptable) sourceElement)
								.getAdapter(VjoDBGPSourceModule.class);
					}
					if (vjoDBGPSourceModule == null) {
						continue;
					}
					IScriptThread scriptThread = getFirstScriptThread(launch);
					if (scriptThread == null || scriptThread.isTerminated()) {
						continue;
					}
					vjoDBGPSourceModule.setDBGPSession(scriptThread
							.getDbgpSession());
					m_cacheManager.add(lineBreakpoint, vjoDBGPSourceModule);
					storage = vjoDBGPSourceModule;
					break;
				} catch (Exception e) {
					VjetDebugPlugin.error(e.getLocalizedMessage(), e);
				}
			}
			if (storage == null) {
				throw new UnsupportedOperationException(
						"No cached contents for vjo debugger's virtual file: "
								+ path.toString());
			}
		} else if (isLocalFile(path)) {
			Object element = VjetSourceLookupParticipant.findFileElement(URI
					.create(path.toString()).getPath().substring(1));
			if (element instanceof IFile) {
				return new FileEditorInput((IFile) element);
			} else if (element instanceof IFileHandle) {
				return new ExternalFileEditorInput((IFileHandle) element);
			}
		}

		if (storage != null) {
			return new ExternalStorageEditorInput(storage);
		}
		return null;
	}

	private boolean isLocalFile(IPath path) {
		return path.toString().startsWith(DLTKDebugConstants.FILE_SCHEME);
	}

	private ISourceLookupDirector getSourceLookupDirector(ILaunch launch) {
		ISourceLookupDirector sourceLookupDirector = null;
		ISourceLocator sourceLocator = launch.getSourceLocator();
		if (sourceLocator != null
				&& sourceLocator instanceof ISourceLookupDirector) {
			sourceLookupDirector = (ISourceLookupDirector) sourceLocator;
		}
		return sourceLookupDirector;
	}

	private ILaunch[] getLaunches() {
		return DebugPlugin.getDefault().getLaunchManager().getLaunches();
	}

	private IScriptThread getFirstScriptThread(ILaunch launch)
			throws DebugException {
		IScriptThread scriptThread = null;
		for (IDebugTarget debugTarget : launch.getDebugTargets()) {
			for (IThread thread : debugTarget.getThreads()) {
				if (thread instanceof IScriptThread) {
					scriptThread = (IScriptThread) thread;
				}
			}
		}
		return scriptThread;
	}

	private boolean isZipFile(IPath path) {
		String fileLocation = path.toString();
		return fileLocation.startsWith(VjetDebugConstants.JAR_SCHEME)
				|| fileLocation.startsWith(VjetDebugConstants.ZIP_SCHEME);
	}

}
