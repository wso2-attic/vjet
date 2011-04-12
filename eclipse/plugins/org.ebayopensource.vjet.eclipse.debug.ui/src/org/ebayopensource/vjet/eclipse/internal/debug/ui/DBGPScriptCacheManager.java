/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.debug.ui;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IMarkerDelta;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IBreakpointListener;
import org.eclipse.debug.core.IBreakpointManager;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.dltk.mod.debug.core.DLTKDebugConstants;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchListener;
import org.eclipse.ui.PlatformUI;

import org.ebayopensource.vjet.eclipse.debug.ui.VjetDebugUIPlugin;
import org.ebayopensource.vjet.eclipse.internal.debug.VjetDebugConstants;

/**
 * A manager caches dbgp's virtual file contents for breakpoints only. Then it
 * can view contents by breakpoint when the debugging is end.
 * 
 *  Ouyang
 * 
 */
public final class DBGPScriptCacheManager {

	private static DBGPScriptCacheManager	m_instance;

	private Map<IBreakpoint, IStorage>		m_cache	= new HashMap<IBreakpoint, IStorage>();

	private DBGPScriptCacheManager() {
		// register breakpoint listener
		DebugPlugin.getDefault().getBreakpointManager().addBreakpointListener(
				new IBreakpointListener() {

					@Override
					public void breakpointAdded(IBreakpoint breakpoint) {
						// do nothing
					}

					@Override
					public void breakpointChanged(IBreakpoint breakpoint,
							IMarkerDelta delta) {
						// do nothing
					}

					@Override
					public void breakpointRemoved(IBreakpoint breakpoint,
							IMarkerDelta delta) {
						remove(breakpoint);
					}
				});

		// register workbench listener
		PlatformUI.getWorkbench().addWorkbenchListener(
				new IWorkbenchListener() {

					@Override
					public boolean preShutdown(IWorkbench workbench,
							boolean forced) {
						// remove breakpoints toggled on dbgp virtual files
						IBreakpointManager breakpointManager = DebugPlugin
								.getDefault().getBreakpointManager();
						IBreakpoint[] breakpoints = breakpointManager
								.getBreakpoints(VjetDebugConstants.DEBUG_MODEL_ID);
						try {
							for (IBreakpoint bp : breakpoints) {
								String location = bp.getMarker().getAttribute(
										IMarker.LOCATION, "");
								if (location != null
										&& location
												.startsWith(DLTKDebugConstants.DBGP_SCHEME)) {
									breakpointManager
											.removeBreakpoint(bp, true);
								}
							}
						} catch (CoreException e) {
							VjetDebugUIPlugin.error(e.getLocalizedMessage(), e);
						}
						return true;
					}

					@Override
					public void postShutdown(IWorkbench workbench) {

					}
				});
	}

	public synchronized static DBGPScriptCacheManager getDefault() {
		if (m_instance == null) {
			m_instance = new DBGPScriptCacheManager();
		}
		return m_instance;
	}

	public void add(IBreakpoint key, IStorage value) {
		m_cache.put(key, value);
	}

	public void clear() {
		m_cache.clear();
	}

	public boolean contains(IBreakpoint key) {
		return m_cache.containsKey(key);
	}

	public IStorage get(IBreakpoint breakpoint) {
		return m_cache.get(breakpoint);
	}

	public void remove(IBreakpoint key) {
		m_cache.remove(key);
	}
}
