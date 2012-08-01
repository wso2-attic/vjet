/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.control.translate;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.ebayopensource.dsf.common.initialization.BaseInitializable;
import org.ebayopensource.dsf.common.initialization.BaseInitializationContext;
import org.ebayopensource.dsf.common.initialization.Initializable;
import org.ebayopensource.dsf.common.initialization.InitializationContext;



public class TranslationParallelRunner {
	
	private static volatile boolean s_initialized = false;
	
	
	//
	// Singleton
	//
	private static volatile TranslationParallelRunner s_instance;
	private static BaseInitializable s_initializable;
	private TranslationParallelRunner(){
		if (s_initialized){
			return;
		}
		
		 synchronized(TranslationParallelRunner.class){
		 	
			
			  getInitializable().doInitialize(new BaseInitializationContext());
			        s_initialized = true;
			 
			      }
		
		synchronized(TranslationParallelRunner.class){
			s_initialized = true;
		}
	};
	
    public static synchronized Initializable getInitializable() {
        if (s_initializable != null) {
            return s_initializable;
        }
        BaseInitializable initializable = new BaseInitializable() {
            protected void initialize(final InitializationContext context) {
//                CommandRunner.initialize();
                // Bean to enable/disable ThreadLocals being flushed at the end
                // of the task
//                ThreadLocalFlushBean.getBean();
            }
            protected void shutdown(final InitializationContext context) {
//                if (m_defaultCommandRunner != null) {
//                    m_defaultCommandRunner.shutdown();
//                    m_defaultCommandRunner = null;
//                }
            }
        };
        s_initializable = initializable;
        return s_initializable;
    }
	
	
	public static TranslationParallelRunner getInstance(){
		if (s_instance != null){
			return s_instance;
		}
		
		synchronized(TranslationParallelRunner.class){
			if (s_instance == null){
				s_instance = new TranslationParallelRunner();
				
				int cores = Runtime.getRuntime().availableProcessors();
				ExecutorService s = Executors.newScheduledThreadPool(cores + 1);
				
				s_instance.setRunner(s);
			}
		}
		
		return s_instance;
	}
	
	private ExecutorService m_cmdRunner = null;
	public int m_batchRunTimeout = 300000;
	
	//
	// Satisfy IParallelRunner
	//
	public Object execute(List<? extends BaseTask> tasks) {
		
		

		for (Callable task: tasks){
			
			try {
				m_cmdRunner.submit(task);
			} 
			catch (Exception ipEx) {
				// TODO
				ipEx.printStackTrace();
			}
		}	


		
		return null;
	}
	
	//
	// API
	// 
	public void setBatchRunTimeout(int batchRunTimeout){
		m_batchRunTimeout = batchRunTimeout;
	}
	
	//
	// Private
	//
	// Default Command runner properties

	
	private void setRunner(ExecutorService cmdRunner){
		m_cmdRunner = cmdRunner;
	}
}
