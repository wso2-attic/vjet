/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.resolver;

/**
 * Utility class for loading bootstrap JS and evaluating resolvers in same thread
 * due to Rhino engine requirement.
 */
public class SingleThreadExecutor {
	
	private static final SingleThreadExecutor s_instance = new SingleThreadExecutor();
	
	private Runnable m_target = null;
	private final Runner m_runner;
	
	private SingleThreadExecutor() {
		m_runner = new Runner();
		Thread td = new Thread(m_runner);
		td.setDaemon(true);
		td.start();
	}
	
	public static SingleThreadExecutor getInstance() {
		return s_instance;
	}
	
	public synchronized void execute(Runnable r) {
		m_target = r;		
		synchronized(m_runner) {
			m_runner.notifyAll();
			try {
				m_runner.wait();
			} catch (InterruptedException e) {
			}
		}		
	}
	
	private class Runner implements Runnable {
		@Override
		public void run() {
			while(true) {
				synchronized(this) {
					if (m_target != null) {
						m_target.run();
						m_target = null;
						notifyAll();
					}
					try {
						wait();
					} catch (InterruptedException e) {
					}
				}
			}
		}
	}
}
