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

import org.eclipse.debug.core.DebugException;
import org.eclipse.dltk.mod.debug.core.ISmartStepEvaluator;
import org.eclipse.dltk.mod.debug.core.model.IScriptStackFrame;
import org.eclipse.dltk.mod.debug.core.model.IScriptThread;

import org.ebayopensource.vjet.eclipse.debug.ui.VjetDebugUIPlugin;

/**
 * 
 * 
 *  Ouyang
 * 
 */
public class VjetSmartStepEvaluator implements ISmartStepEvaluator {

	public static final String	MODIFIER_SPLITTER	= "::";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.dltk.mod.debug.core.ISmartStepEvaluator#skipSuspend(java.lang
	 * .String[], org.eclipse.dltk.mod.debug.core.model.IScriptThread)
	 */
	@Override
	public boolean skipSuspend(String[] filters, IScriptThread thread) {
		try {
			if (!thread.hasStackFrames()) {
				return false;
			}
			IScriptStackFrame stackFrame = (IScriptStackFrame) thread
					.getTopStackFrame();
			URI sourceURI = stackFrame.getSourceURI();
			String sourceLocation = sourceURI.toString();
			for (String filter : filters) {
				if (match(sourceLocation, filter)) {
					return true;
				}
			}
		} catch (DebugException e) {
			VjetDebugUIPlugin.error(e.getLocalizedMessage(), e);
		}

		return false;
	}

	private boolean match(String sourceLocation, String filter) {
		return sourceLocation.matches(filter.split(MODIFIER_SPLITTER)[0]);
	}

}
