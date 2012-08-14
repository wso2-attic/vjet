/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.core.ts;

import org.ebayopensource.vjo.tool.typespace.TypeLoadMonitor;
import org.eclipse.core.runtime.IProgressMonitor;

/**
 * Eclipse implementation of the type load monitor.
 * 
 * 
 *
 */
public class EclipseTypeLoadMonitor extends TypeLoadMonitor {

	private static final String LOADING_TYPE = "Loading type";

	private static final String PREPARATION_LIST_OF_TYPES = "Preparation of the list of types";

	private IProgressMonitor m_monitor;

	private int m_count;
	
//	private int typesCount;
	
	private float lastPercent;

	/**
	 * Create instance of this class with specified {@link IProgressMonitor} object. 
	 * 
	 * @param monitor {@link IProgressMonitor} object.
	 */
	public EclipseTypeLoadMonitor(IProgressMonitor monitor) {
		this.m_monitor = monitor;
	}

	@Override
	public void loadTypeFinished() {
		m_monitor.worked(100);
		m_monitor.done();
		System.out.println("percent = " + 100);
	}

	@Override
	public void loadTypeListFinished() {
		m_monitor.worked(100);
		m_monitor.done();
		System.out.println("percent = " + 100);
	}

	@Override
	public void loadTypeListStarted(int typesCount) {
		m_monitor.beginTask(LOADING_TYPE, 100);					
	}

	@Override
	public void loadTypeStarted(String group, String file) {
		m_count++;
		m_monitor.subTask(file);
		m_monitor.worked(m_count);				
		setCanceled(m_monitor.isCanceled());			
	}
	
	@Override
	public void loadTypeStarted(float percent) {
		if (lastPercent != percent) {
			m_monitor.worked((int)(percent-lastPercent));
			lastPercent = percent;
//			System.out.println("percent = " + percent);
		}
	}

	@Override
	public void preparationTypeListFinished() {
		m_monitor.worked(5);
	}

	@Override
	public void preparationTypeListStarted() {
		m_monitor.setTaskName(PREPARATION_LIST_OF_TYPES);		
	}

}
