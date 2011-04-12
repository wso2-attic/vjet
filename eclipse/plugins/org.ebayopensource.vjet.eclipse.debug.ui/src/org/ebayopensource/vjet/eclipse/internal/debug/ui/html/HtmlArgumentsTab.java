/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.debug.ui.html;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.debug.ui.ILaunchConfigurationDialog;
import org.eclipse.debug.ui.StringVariableSelectionDialog;
import org.eclipse.dltk.mod.debug.ui.ScriptDebugImages;
import org.eclipse.dltk.mod.debug.ui.actions.ControlAccessibleListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;



public class HtmlArgumentsTab extends AbstractLaunchConfigurationTab {
	
	private static String tabName = "VJO Arguments" ; 
	
	protected Label fPrgmArgumentsLabel ; 
	protected Text fPrgmArgumentsText ; 
	
	private HtmlMainTab htmlMainTab;
	
	protected HtmlLocationBlock vjoHtmlLocationBlock ; 
	protected HtmlMainClassBlock vjoMainClassBlock ; 
	protected HtmlBrowserTypeBlock vjoBrowserTypeBlock ; 

	
	public HtmlArgumentsTab(HtmlMainTab htmlMainTab) { 
		this.htmlMainTab = htmlMainTab;
		
		vjoHtmlLocationBlock = new HtmlLocationBlock(this) ; 
		vjoMainClassBlock = new HtmlMainClassBlock()  ; 
		vjoBrowserTypeBlock = new HtmlBrowserTypeBlock() ; 
	}
	
	public void createControl(Composite parent) {
		
		Font font = parent.getFont();
		Composite comp = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(1, true);
		comp.setLayout(layout);
		comp.setFont(font);
		
		GridData gd = new GridData(GridData.FILL_BOTH);
		comp.setLayoutData(gd);
		setControl(comp);
//		setHelpContextId();		
		
		// Add the Html Resource Name control
		vjoHtmlLocationBlock.createControl(comp) ;
		createVerticalSpacer(comp, 4) ; 
		
		// Add the Vjo Main Class control
		vjoMainClassBlock.createControl(comp) ; 
		createVerticalSpacer(comp, 4);
		
		Group group = new Group(comp, SWT.NONE);
		group.setFont(font);
		layout = new GridLayout();
		group.setLayout(layout);
		group.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		String controlName = ("Program arguments"); 
		group.setText(controlName);
		
		fPrgmArgumentsText = new Text(group, SWT.MULTI | SWT.WRAP | SWT.BORDER | SWT.V_SCROLL);
		gd = new GridData(GridData.FILL_BOTH);
		gd.heightHint = 40; 
		gd.widthHint = 100;
		fPrgmArgumentsText.setLayoutData(gd);
		fPrgmArgumentsText.setFont(font);
		fPrgmArgumentsText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent evt) {
				updateLaunchConfigurationDialog();
			}
		});
		ControlAccessibleListener.addListener(fPrgmArgumentsText, group.getText());
		
		String buttonLabel = "Variables";  
		Button pgrmArgVariableButton = createPushButton(group, buttonLabel, null); 
		pgrmArgVariableButton.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
		pgrmArgVariableButton.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				StringVariableSelectionDialog dialog = new StringVariableSelectionDialog(getShell());
				dialog.open();
				String variable = dialog.getVariableExpression();
				if (variable != null) {
                    fPrgmArgumentsText.insert(variable);
				}
				updateLaunchConfigurationDialog();
			}
			public void widgetDefaultSelected(SelectionEvent e) {
			}	
		});
		createVerticalSpacer(comp, 4);
		// Add the Browser Type control

		vjoBrowserTypeBlock.createControl(comp) ; 
		createVerticalSpacer(comp, 4) ; 
	}

	public IProject getProject() {
		return this.htmlMainTab.getSelectedProject();
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.AbstractLaunchConfigurationTab#getImage()
	 */
	public Image getImage() {
		return ScriptDebugImages.get(ScriptDebugImages.IMG_VIEW_ARGUMENTS_TAB);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#getName()
	 */
	public String getName() {
		return tabName ;
	}

	// Save, ie, when user selects Apply button on the VjO Arguments Tab
	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#performApply(org.eclipse.debug.core.ILaunchConfigurationWorkingCopy)
	 */
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute(IHtmlLaunchConstants.ATTR_PROGRAM_ARGUMENTS, getAttributeValueFrom(fPrgmArgumentsText)) ;
		vjoHtmlLocationBlock.performApply(configuration) ;
		vjoMainClassBlock.performApply(configuration) ; 
		vjoBrowserTypeBlock.performApply(configuration) ;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#setDefaults(org.eclipse.debug.core.ILaunchConfigurationWorkingCopy)
	 */
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute(IHtmlLaunchConstants.ATTR_MAIN_TYPE_NAME, IHtmlLaunchConstants.HTML_VJO_RUNNER_CLASS) ;
		vjoHtmlLocationBlock.setDefaults(configuration) ;
		vjoMainClassBlock.setDefaults(configuration) ;
		vjoBrowserTypeBlock.setDefaults(configuration) ;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#initializeFrom(org.eclipse.debug.core.ILaunchConfiguration)
	 */
	public void initializeFrom(ILaunchConfiguration configuration) {
		try { 
			fPrgmArgumentsText.setText(configuration.getAttribute(IHtmlLaunchConstants.ATTR_PROGRAM_ARGUMENTS, "")) ;
			vjoHtmlLocationBlock.initializeFrom(configuration) ;
			vjoMainClassBlock.initializeFrom(configuration) ;
			vjoBrowserTypeBlock.initializeFrom(configuration) ;
		} catch (CoreException e) { 
			e.printStackTrace() ;
		}
	}

	protected String getAttributeValueFrom(Text text) {
		String content = text.getText().trim(); 
		if (content.length() > 0)  
			return content ; 
		return null ; 
	}
	
	public void setLaunchConfigurationDialog(ILaunchConfigurationDialog dialog) { 
		super.setLaunchConfigurationDialog(dialog) ;
		vjoHtmlLocationBlock.setLaunchConfigurationDialog(dialog) ;
		vjoMainClassBlock.setLaunchConfigurationDialog(dialog) ; 
		vjoBrowserTypeBlock.setLaunchConfigurationDialog(dialog) ; 
	}
}
