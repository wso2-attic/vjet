/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/**
 * 
 */
package org.ebayopensource.vjet.eclipse.internal.debug.ui.html;

import org.eclipse.debug.ui.AbstractLaunchConfigurationTabGroup;
import org.eclipse.debug.ui.CommonTab;
import org.eclipse.debug.ui.EnvironmentTab;
import org.eclipse.debug.ui.ILaunchConfigurationDialog;
import org.eclipse.debug.ui.ILaunchConfigurationTab;


/**
 * 
 *
 */
public class HtmlTabGroup extends AbstractLaunchConfigurationTabGroup {
	private HtmlMainTab htmlMainTab;
	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTabGroup#createTabs(org.eclipse.debug.ui.ILaunchConfigurationDialog, java.lang.String)
	 */
	public void createTabs(ILaunchConfigurationDialog dialog, String mode) {
		htmlMainTab = new HtmlMainTab(mode);
		
		ILaunchConfigurationTab[] tabs = new ILaunchConfigurationTab[] {
				htmlMainTab,
//				new JavaMainTab(),
				new HtmlArgumentsTab(htmlMainTab), 
//				new JavaJRETab(),
//				new JavaClasspathTab(),
				new EnvironmentTab(),
				new CommonTab()
			};
			setTabs(tabs);
	}

}
