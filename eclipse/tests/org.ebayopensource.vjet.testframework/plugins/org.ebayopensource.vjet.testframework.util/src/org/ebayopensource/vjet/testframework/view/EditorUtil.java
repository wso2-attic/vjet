/*******************************************************************************
 * Copyright (c) 2005-2012 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.testframework.view;

import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

/**
 * editor utility class, regarding for editor part/document...
 * 
 * @author ddodd
 *
 */
public class EditorUtil {

	/**
	 * utility class
	 */
	private EditorUtil() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * get the active editor part of the current eclipse instance.
	 * whether in UI thread or non UI thread, it does work.
	 * 
	 * Note:
	 * 1. user can cast the return value from 'IEditorPart' to corresponding real type, for example 'VjoEditor' 
	 * 
	 * @return
	 */
	public static IEditorPart getActiveEditor() {
		return getActiveWorkbenchWindow().getActivePage().getActiveEditor();
	}
	
	/**
	 * get the active workbench window of the current eclipse instance.
	 * whether in UI thread or non UI thread, it does work.
	 * 
	 * Note:
	 * 1. when running windowtester, the context is in non UI thread
	 * 
	 * @return
	 */
	public static IWorkbenchWindow getActiveWorkbenchWindow() {
		if (Display.getCurrent() != null)
			return PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		
		ActiveWorkbenchWindowFetchJob windowFetchJob = new ActiveWorkbenchWindowFetchJob();
		Display.getDefault().syncExec(windowFetchJob);
		return windowFetchJob.getActiveWorkbenchWindow();
	}
	
	/**
	 * this helper job is help to get ruturn value, becase a runnbale instance
	 * can not return value
	 * 
	 * @author xingzhu
	 */
	private static class ActiveWorkbenchWindowFetchJob implements Runnable {
		private IWorkbenchWindow window;
		
		public void run() {
			this.window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		}
		
		public IWorkbenchWindow getActiveWorkbenchWindow() {
			return this.window;
		}
	}
}
