/*******************************************************************************
 * Copyright (c) 2005-2012 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.testframework.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import junit.framework.TestCase;

import org.ebayopensource.vjet.testframework.fixture.FixtureDefManager;
import org.ebayopensource.vjet.testframework.fixture.FixtureManager;
import org.ebayopensource.vjet.testframework.fixture.FixtureUtils;
import org.ebayopensource.vjet.testframework.fixture.IFixtureDef;
import org.ebayopensource.vjet.testframework.sandbox.Sandbox;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;


/**
 * 
 * @author ddodd
 *
 */
public class UnitTestHelper {
	
	@SuppressWarnings("unchecked")
	public static void runParentJob(Job job) {
		Class superClass = job.getClass().getSuperclass();
		runJob(superClass, job);
	}

	@SuppressWarnings("unchecked")
	public static void runJob(Job job) {
		Class jobClass = job.getClass();
		runJob(jobClass, job);
	}
	
	@SuppressWarnings("unchecked")
	private static void runJob(Class jobClass, Job job) {
		try {
			jobClass.getName();
			Method scheduleMethod = jobClass.getDeclaredMethod("run", IProgressMonitor.class);
			scheduleMethod.setAccessible(true);
			scheduleMethod.invoke(job, new Object[] { null });
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	
	public static void runEventQueue(int count, int sleepInterval) {
		
		for (int counter=0; counter<count; counter++) {
			runEventQueue();
			sleep(sleepInterval);
		}
	}	
	
	/**
	 * Runs the event queue on the current display until it is empty.
	 */
	public static void runEventQueue() {
		IWorkbenchWindow window= getActiveWorkbenchWindow();
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
			// Do nothing
		}
	}
	
	public static void removeAllProjectFromWorkspace() {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IProject[] projects = workspace.getRoot().getProjects();
		if (projects != null) {
			try {
				for (IProject project : projects) {
					project.close(null);
					project.delete(true, null);
				}
			} catch (CoreException e) {
				// Who cares...
			}
		}
	}
	
	public static void sleep(int intervalTime) {
		try {
			Thread.sleep(intervalTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static Display getActiveDisplay() {
		IWorkbenchWindow window= getActiveWorkbenchWindow();
		return window != null ? window.getShell().getDisplay() : null;
	}
	
	public static IWorkbenchWindow getActiveWorkbenchWindow() {
		return PlatformUI.getWorkbench().getActiveWorkbenchWindow();
	}
	
	// Below is new test helper methods added by chlu
	
	/**
	 * wait waitCount*50 ms, 500ms at least.
	 * 
	 * @param waitCount
	 */
	public static void waitUIOperation(int waitCount) {
		if (waitCount < 10) {
			waitCount = 10;
		}
		int countDown = waitCount;
		try {
			while (countDown-- != 0) {
				UnitTestHelper.runEventQueue();
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} catch (RuntimeException e) {}
	}
	
	/**
	 * help set up testframework fixtures
	 * @param fixtureIDs
	 * @param testCase
	 * @param fixtureFileName
	 */
	public static FixtureManager setupFixtures(TestCase testCase,String fixtureFileName,String... fixtureIDs){
		
		// Create fixturemanager
		Sandbox sandBox = new Sandbox(testCase);
		sandBox.setUp();
		FixtureDefManager fixtureDefManager = 
			FixtureUtils.createFixtureDefManagerFromXml(fixtureFileName, testCase, sandBox);

		// Find fixture to test
		FixtureManager fixtureManager = new FixtureManager(testCase, fixtureDefManager);
		
		for (String id : fixtureIDs) {
			IFixtureDef fixtureToTest = fixtureManager.getFixtures().getFixtureDef(id);

			if ((fixtureToTest == null)) {
				throw new RuntimeException("Fixture definition not found");
			}
			fixtureManager.setUp(fixtureToTest.getFixtureId());	
		}
		return fixtureManager;
	}	
	
	@SuppressWarnings("unchecked")
	public static Object getField(String fieldName, Object instance, Class instanceClass){
		if(instanceClass == null){
			throw new RuntimeException("NoSuchField: " + fieldName);
		}
		try{
			Field field = instanceClass.getDeclaredField(fieldName);
			field.setAccessible(true);
			return field.get(instance);
		}catch (NoSuchFieldException e ) {
			return getField(fieldName, instance, instanceClass.getSuperclass());
		}catch (IllegalAccessException e){
			throw new RuntimeException(e);
		}
	}
	
	public static Object getField(String fieldName, Object instance) {
		return getField(fieldName, instance, instance.getClass());
	}
	
	@SuppressWarnings("unchecked")
	public static void setField(String fieldName, Object instance, Class instanceClass, Object value){
		if(instanceClass == null){
			throw new RuntimeException("NoSuchField: " + fieldName);
		}
		try {
			Field field = instanceClass.getDeclaredField(fieldName);
			field.setAccessible(true);
			field.set(instance, value);
		}catch (NoSuchFieldException e ) {
			setField(fieldName, instance, instanceClass.getSuperclass(),value);
		}catch (IllegalAccessException e){
			throw new RuntimeException(e);
		}
	}

	public static void setField(String fieldName, Object instance, Object value) {
		setField(fieldName,instance, instance.getClass(), value);
	}

	@SuppressWarnings("unchecked")
	public static Object executeMethod(String methodName, Class[] parameterTypes, Object[] args,
			Object instance, Class instanceClass){
		if(instanceClass == null){
			throw new RuntimeException("NoSuchMethod: " + methodName);
		}
		try{
			Method method = instanceClass.getDeclaredMethod(methodName, parameterTypes);
			method.setAccessible(true);
			return method.invoke(instance, args);
		}catch (NoSuchMethodException exception ) {
			return executeMethod(methodName, parameterTypes, args, instance, instanceClass.getSuperclass());
		}catch (IllegalAccessException e){
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public static Object executeMethod(String methodName, Class[] parameterTypes, Object[] args, Object instance) {
		return executeMethod(methodName, parameterTypes, args, instance, instance.getClass());
	}
}
