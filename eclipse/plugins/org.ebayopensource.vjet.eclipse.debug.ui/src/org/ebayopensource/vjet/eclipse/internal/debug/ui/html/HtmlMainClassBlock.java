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
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;

public class HtmlMainClassBlock extends AbstractLaunchConfigurationTab {
	
	protected Text vjoMainClassText;

	public void createControl(Composite parent) {
		
		Font font= parent.getFont();
		Group group= new Group(parent, SWT.NONE);
		group.setText("VjO main class:"); 
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		group.setLayoutData(gd);
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		group.setLayout(layout);
		group.setFont(font);
		vjoMainClassText = new Text(group, SWT.SINGLE | SWT.BORDER);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		vjoMainClassText.setLayoutData(gd);
		vjoMainClassText.setFont(font);
		
		vjoMainClassText.addModifyListener(new ModifyListener() { 
			public void modifyText(ModifyEvent evt) { 
				updateLaunchConfigurationDialog() ;
			}
		}) ; 
		
	}

	public String getName() {
		return "VjO Main Class" ;
	}

	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute(IHtmlLaunchConstants.HTML_MAIN_TYPE, getAttributeValueFrom(vjoMainClassText)) ;
	}

	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute(IHtmlLaunchConstants.HTML_MAIN_TYPE, "");		
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#initializeFrom(org.eclipse.debug.core.ILaunchConfiguration)
	 */
	public void initializeFrom(ILaunchConfiguration configuration) {
		try { 
			vjoMainClassText.setText(configuration.getAttribute(IHtmlLaunchConstants.HTML_MAIN_TYPE, "")) ;
		} catch (CoreException e) { 
			e.printStackTrace() ; 
		}
	}

	public String getVjoMainClass() { 
		return  vjoMainClassText.getText().trim();
	}
	
	protected String getAttributeValueFrom(Text text) { 
		String content = text.getText().trim() ;
		if (content.length() > 0) 
			return content ;
		return null ; 
	}
}
