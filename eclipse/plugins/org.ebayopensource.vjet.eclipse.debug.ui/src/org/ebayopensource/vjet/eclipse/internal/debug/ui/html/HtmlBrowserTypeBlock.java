/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.debug.ui.html;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

public class HtmlBrowserTypeBlock extends AbstractLaunchConfigurationTab {
	
	protected Combo vjoBrowserType ;
	
	public void createControl(Composite parent) {
		Font font= parent.getFont();
		Group group= new Group(parent, SWT.NONE);
		group.setText("Browser:"); 
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		group.setLayoutData(gd);
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		group.setLayout(layout);
		group.setFont(font);
		
		vjoBrowserType = new Combo(group, SWT.READ_ONLY) ;

		vjoBrowserType.add("IE") ;  // IE_6P
		vjoBrowserType.add("FIREFOX") ; // Firefox_1P
		vjoBrowserType.select(0) ;
        
		gd = new GridData(GridData.FILL_HORIZONTAL);
		vjoBrowserType.setLayoutData(gd);
		vjoBrowserType.setFont(font);	
		

	    SelectionListener listener = new SelectionAdapter() {
	        public void widgetSelected(SelectionEvent e) {
	            updateLaunchConfigurationDialog();
	        }
	    };
	    vjoBrowserType.addSelectionListener(listener);
		
	}

	public String getName() {
		return "VjoBrowserType";
	}

	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute(IHtmlLaunchConstants.HTML_BROWSER_TYPE, getAttributeValueFrom(vjoBrowserType)) ;
	}

	// Set default for new configuration
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute(IHtmlLaunchConstants.HTML_BROWSER_TYPE, "");	
	}
	
	public void initializeFrom(ILaunchConfiguration configuration) {
		try { 
			vjoBrowserType.setText(configuration.getAttribute(IHtmlLaunchConstants.HTML_BROWSER_TYPE, "")) ;
		} catch (CoreException e) { 
			e.printStackTrace() ; 
		}
	}
	
	protected String getVjoBrowserType() { 
		return vjoBrowserType.getText() ;
	}
	
	
	protected String getAttributeValueFrom(Combo combo) { 
		String content = combo.getText() ; 
		if (content.length() > 0) 
			return content ;
		return null ; 
	}

}
