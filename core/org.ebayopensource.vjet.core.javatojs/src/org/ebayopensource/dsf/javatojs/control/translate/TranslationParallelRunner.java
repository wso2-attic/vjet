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

import com.ebay.kernel.bean.configuration.BeanConfigCategoryInfo;
import com.ebay.kernel.bean.configuration.ConfigCategoryCreateException;
import com.ebay.kernel.cmdrunner.CommandRunner;
import com.ebay.kernel.cmdrunner.CommandRunnerFactory;
import com.ebay.kernel.cmdrunner.CommandRunnerPropertyBean;
import com.ebay.kernel.cmdrunner.CrCalTransaction;
import com.ebay.kernel.cmdrunner.ExecutionPolicy;
import com.ebay.kernel.cmdrunner.ResponseCommandTracker;
import com.ebay.kernel.initialization.BaseInitializationContext;
import com.ebay.kernel.initialization.InitializationException;

public class TranslationParallelRunner {
	
	private static volatile boolean s_initialized = false;
	
	//
	// Singleton
	//
	private static volatile TranslationParallelRunner s_instance;
	private TranslationParallelRunner(){
		if (s_initialized){
			return;
		}
		
		synchronized(TranslationParallelRunner.class){
			CommandRunnerFactory.getInitializable()
				.doInitialize(new BaseInitializationContext());
			s_initialized = true;
		}
	};
	
	public static TranslationParallelRunner getInstance(){
		if (s_instance != null){
			return s_instance;
		}
		
		synchronized(TranslationParallelRunner.class){
			if (s_instance == null){
				s_instance = new TranslationParallelRunner();
				s_instance.setRunner(CommandRunnerFactory.createCommandRunner(getDefaultPropertyBean()));
			}
		}
		
		return s_instance;
	}
	
	private CommandRunner m_cmdRunner = null;
	public int m_batchRunTimeout = 300000;
	
	//
	// Satisfy IParallelRunner
	//
	public ResponseCommandTracker execute(List<? extends BaseTask> tasks) {
		
		ResponseCommandTracker responseCmdTracker = new ResponseCommandTracker();
		
		CrCalTransaction parentCrCalTx = m_cmdRunner.createGroupingTransaction();
		parentCrCalTx.start();

		for (BaseTask task: tasks){
			responseCmdTracker.add(task);
			
			try {
				m_cmdRunner.add(task, task.getId(), ExecutionPolicy.getDefaultPolicy(), parentCrCalTx);
			} 
			catch (Exception ipEx) {
				// TODO
				ipEx.printStackTrace();
			}
		}	
			
		synchronized (responseCmdTracker) {
			if (!responseCmdTracker.isAllDone()) {
				//wait for the command completion or timeout
				responseCmdTracker.waitForCommandsToComplete(m_batchRunTimeout);
			}				
		}
		
		if (!responseCmdTracker.isAllDone()) {
			// TODO: something about commands not being completed
		}
		
		for (BaseTask task: tasks){
			task.logParentToChildReference(); 
		}

		parentCrCalTx.completed("0");
		
		return responseCmdTracker;
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
	private static final String CMD_RUNNER_ID = "org.ebayopensource.dsf.javatojs.Translate";
	private static final String CMD_RUNNER_ALIAS = "TranslationParallelRunner";
	private static final String CMD_RUNNER_GROUP = "ebay.dsf.javatojs";
	private static final int MIN_THREADS = 4;
	private static final int MAX_THREADS = 15;
	private static final int MAX_COMMAND_QUEUE_SIZE = 4000;
	private static CommandRunnerPropertyBean getDefaultPropertyBean() {		

		BeanConfigCategoryInfo configCategory;
		
		try {
			configCategory = BeanConfigCategoryInfo.createBeanConfigCategoryInfo(
					CMD_RUNNER_ID,
					CMD_RUNNER_ALIAS,
					CMD_RUNNER_GROUP,
					true,
					true,
					null,
					"V4 Java to JS Parallerl Runner Configuration",
					true);

		} 
		catch (ConfigCategoryCreateException e) {
			throw new InitializationException(e);
		}

		CommandRunnerPropertyBean commandRunnerProperties = 
			new CommandRunnerPropertyBean(CMD_RUNNER_ID, configCategory);
		commandRunnerProperties.setMaxCommandQueueSize(MAX_COMMAND_QUEUE_SIZE);
		commandRunnerProperties.setMaxThreads(MAX_THREADS);
		commandRunnerProperties.setMinThreads(MIN_THREADS);		

		return commandRunnerProperties;
	}
	
	private void setRunner(CommandRunner cmdRunner){
		m_cmdRunner = cmdRunner;
	}
}
