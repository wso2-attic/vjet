/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dap.rt;

public interface IDapConsoleProxy extends Runnable{
	void setBrowserEmulator(DapBrowserEmulator emulator);
	void onLoad(DapSession session);
	void onUnload(DapSession session);
	
	
}
