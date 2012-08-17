/*******************************************************************************
 * Copyright (c) 2005-2012 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipsetestutils;

import java.lang.reflect.Method;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.WorkbenchException;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;

/**
 * 
 * @author ddodd
 * 
 * This class contain a set of methods to help facilitate running tests.
 *
 */
public class UnitTestHelper {

	public static final String INTRO_VIEW_ID = "org.eclipse.ui.internal.introview";

	public static void runParentJob(Job job) {
		Class superClass = job.getClass().getSuperclass();
		runJob(superClass, job);
	}

	public static void runJob(Job job) {
		Class jobClass = job.getClass();
		runJob(jobClass, job);
	}

	private static void runJob(Class jobClass, Job job) {
		try {
			jobClass.getName();
			Method scheduleMethod = jobClass.getDeclaredMethod("run",
					IProgressMonitor.class);
			scheduleMethod.setAccessible(true);

			scheduleMethod.invoke(job, new Object[] { null });

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static IWorkbenchWindow getActiveWorkbenchWindow() {
		return PlatformUI.getWorkbench().getActiveWorkbenchWindow();
	}

	/**
	 * Runs the event queue on the current display until it is empty.
	 */
	public static void runEventQueue() {
		IWorkbenchWindow window = getActiveWorkbenchWindow();
		if (window != null)
			runEventQueue(window.getShell());
	}

	public static void runEventQueue(IWorkbenchPart part) {
		if (part == null) {
			return;
		}
		runEventQueue(part.getSite().getShell());
	}

	public static void runEventQueue(Shell shell) {
		runEventQueue(shell.getDisplay());
	}

	public static void runEventQueue(Display display) {
		while (display.readAndDispatch()) {
			// do nothing
		}
	}

	/**
	 * Used to let the Display thread process messages. The time is time*100ms
	 * 
	 * @param time
	 * @throws InterruptedException
	 */
	public static void runDisplay(int time) throws InterruptedException {
		Display current = Display.getCurrent();
		for (int count = time; count > 0; count--) {
			if (current != null) {
				current.syncExec(new Runnable() {
					public void run() {

						Display display = Display.getCurrent();
						if (!display.readAndDispatch()) {
						}

					}
				});
			}
			Thread.sleep(100);
		}
	}

	public static void sleep(int intervalTime) {
		try {
			Thread.sleep(intervalTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Process UI input but do not return for the specified time interval.
	 * 
	 * @param waitTimeMillis
	 *            the number of milliseconds
	 */
	public void delay(long waitTimeMillis) {
		Display display = Display.getCurrent();

		// If this is the UI thread,
		// then process input.

		if (display != null) {
			long endTimeMillis = System.currentTimeMillis() + waitTimeMillis;
			while (System.currentTimeMillis() < endTimeMillis) {
				if (!display.readAndDispatch())
					display.sleep();
			}
			display.update();
		}
		// Otherwise, perform a simple sleep.
		else {
			try {
				Thread.sleep(waitTimeMillis);
			} catch (InterruptedException e) {
				// Ignored.
			}
		}
	}

	/**
	 * Wait until all background tasks are complete.
	 */
	public void waitForJobs() {

		// Cancel the Data Usage Jobs
		Job[] currentJobs = Job.getJobManager().find(null);
		for(Job job : currentJobs) {
			if (job.getName().contains("Usage")) {
				job.cancel();
			}
		}

		while (!Job.getJobManager().isIdle()) {
			delay(50);
		}
	}

	public void openJavaPerspective() {
		System.out.println("-- open Java perspective --");
		try {
			PlatformUI.getWorkbench().showPerspective(
					"org.eclipse.jdt.ui.JavaPerspective", getWorkbenchWindow());
			PlatformUI
					.getWorkbench()
					.getPerspectiveRegistry()
					.setDefaultPerspective("org.eclipse.jdt.ui.JavaPerspective");
		} catch (WorkbenchException e) {
			e.printStackTrace();
		}
	}

	private IWorkbenchWindow getWorkbenchWindow() {
		IWorkbench workbench = PlatformUI.getWorkbench();
		return workbench.getActiveWorkbenchWindow();
	}

	public void closeAllPerspectives() {
		IWorkbenchAction action = ActionFactory.CLOSE_ALL_PERSPECTIVES
				.create(getWorkbenchWindow());
		action.run();
	}

	public static boolean isViewShown(String viewId) {
		return getActivePage().findViewReference(viewId) != null;
	}

	public static boolean showView(String viewId, boolean show)
			throws PartInitException {
		IWorkbenchPage activePage = getActivePage();
		IViewReference view = activePage.findViewReference(viewId);
		boolean shown = view != null;
		if (shown != show)
			if (show)
				activePage.showView(viewId);
			else
				activePage.hideView(view);
		return shown;
	}

	public static IWorkbenchPage getActivePage() {
		IWorkbenchWindow window = getActiveWorkbenchWindow();
		return window != null ? window.getActivePage() : null;
	}

	public static Display getActiveDisplay() {
		IWorkbenchWindow window = getActiveWorkbenchWindow();
		return window != null ? window.getShell().getDisplay() : null;
	}

}
