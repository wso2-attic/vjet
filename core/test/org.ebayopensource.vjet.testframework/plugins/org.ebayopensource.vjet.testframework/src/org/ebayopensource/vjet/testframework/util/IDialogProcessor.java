/*******************************************************************************
 * Copyright (c) 2005-2012 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.testframework.util;


public interface IDialogProcessor {
	
	// This method is called to process the found dialog.
	// The dialog is passed in as an Object because eclipse does not have
	// one root dialog.
	public void processDialog(Object dialog);
}
