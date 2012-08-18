/*******************************************************************************
 * Copyright (c) 2005-2012 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.testframework.util;

import org.eclipse.jface.dialogs.DialogPage;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;

/**
 * 
 * 
 * @author ddodd
 * 
 * This thread is used to monitor the running of tests and try to 
 * detect if any dialogs have popped up during the running of a test.
 *
 */
public class DialogMonitorJob extends Thread {

	boolean m_allDone;

	Display m_display;

	IDialogProcessor m_dialogProcessor;
	
	boolean isSyncMode=true;

	public DialogMonitorJob(Display display, IDialogProcessor processor,boolean isSyncMode) {
		super("Monitoring Dialogs");
		m_display = display;
		m_dialogProcessor = processor;
		this.isSyncMode=isSyncMode;
	}

	
	/**
	 * Recursive method that crawls up the parents and looks for a dialog
	 * 
	 * @param compositeControl
	 * @return
	 */
	private Object getDialog(Control compositeControl) {

		if (compositeControl == null) {
			return null;
		}

		Object data = compositeControl.getData();
		if (data != null && (data instanceof org.eclipse.jface.dialogs.Dialog || data instanceof org.eclipse.swt.widgets.Dialog || data instanceof DialogPage)) {
			return data;
		}

		return getDialog(compositeControl.getParent());
	}

	
	private void processDialog() {
		if(m_display.isDisposed()) {
			return;
		}
		
		
		Control control = m_display.getFocusControl();

		if (control != null && !control.isDisposed()) {

			Object dialogObject = getDialog(control);

			if (dialogObject != null) {
				m_dialogProcessor.processDialog(dialogObject);

				UnitTestHelper.runEventQueue();
			}
		}

	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			if(isSyncMode){
				if(m_display.isDisposed()) {
					break;
				}
				m_display.syncExec(new Runnable() {
					public void run() {
						processDialog();
					}
				});
			}else{				
				m_display.asyncExec(new Runnable() {
					public void run() {
						processDialog();
					}
				});
			}

			if (m_allDone) {
				return;
			}

			/**
			 * Give a little time for the task to run. We do not want to take up
			 * all the CPU by just searching.
			 */
			try {
				Thread.sleep(150);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	public void setAllDone(boolean allDone) {
		m_allDone = allDone;
	}

}
