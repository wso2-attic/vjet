/*******************************************************************************
 * Copyright (c) 2005-2012 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.testframework.util;

import org.ebayopensource.vjet.testframework.util.DialogMonitor;
import org.eclipse.jface.dialogs.MessageDialog;

import junit.framework.TestCase;

public class TestDialogMonitor extends TestCase {
	
	public void testDialogMonitor() {
		
		DialogMonitor monitor = new DialogMonitor();
		monitor.startMonitoring();
		
		MessageDialog.openConfirm(null, "This is a test", "Please press OK");

		monitor.stopMonitoring();
		
	}

}
