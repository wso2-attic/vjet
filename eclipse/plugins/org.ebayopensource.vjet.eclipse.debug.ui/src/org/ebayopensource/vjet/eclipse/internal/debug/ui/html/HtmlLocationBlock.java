/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.debug.ui.html;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.dltk.mod.debug.ui.messages.DLTKLaunchConfigurationsMessages;
import org.eclipse.dltk.mod.ui.DLTKUIPlugin;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;

public class HtmlLocationBlock extends AbstractLaunchConfigurationTab {
	
	private HtmlArgumentsTab htmlArgumentsTab;
	
	protected Text htmlName;
	protected Button searchButton ;
	protected static final String EMPTY_STRING = "" ;

	public HtmlLocationBlock(HtmlArgumentsTab htmlArgumentsTab) {
		this.htmlArgumentsTab = htmlArgumentsTab;
	}
	
	public void createControl(Composite parent) {
		
		Font font= parent.getFont();
		final Group group= new Group(parent, SWT.NONE);
		group.setText("Html File:"); 
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		group.setLayoutData(gd);
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		group.setLayout(layout);
		group.setFont(font);
		setControl(parent) ;
		htmlName = new Text(group, SWT.SINGLE | SWT.BORDER);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		htmlName.setLayoutData(gd);
		htmlName.setFont(font);
		htmlName.setText("${resource_loc}");
		htmlName.addModifyListener(new ModifyListener() { 
			public void modifyText(ModifyEvent evt) { 
				updateLaunchConfigurationDialog() ;
			}
		}) ; 
		
		searchButton = createPushButton(group, " Search  " , null);
		// gd = new GridData(GridData.HORIZONTAL_ALIGN_END) ;
		searchButton.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
		htmlName.setText("dummy") ; 
		searchButton.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
			}
			public void widgetSelected(SelectionEvent e) {
				handleSearchButtonSelected();
			}
		}) ;
	}

	/**
	 * The select button pressed handler
	 */
	protected void handleSearchButtonSelected() {
		ElementTreeSelectionDialog dialog = new ElementTreeSelectionDialog(
				getShell(), new WorkbenchLabelProvider(),
				new WorkbenchContentProvider());
		dialog
				.setTitle(DLTKLaunchConfigurationsMessages.mainTab_searchButton_title);
		dialog
				.setMessage(DLTKLaunchConfigurationsMessages.mainTab_searchButton_message);
		dialog.setInput(getProject());
		dialog.addFilter(new ViewerFilter() {
			@Override
			public boolean select(Viewer viewer, Object parentElement,
					Object element) {
				if (element instanceof IFile) {
					String extensionName = ((IFile)element).getFileExtension();
					if ("html".equalsIgnoreCase(extensionName)
							|| "htm".equalsIgnoreCase(extensionName))
						return true;
					else
						return false;
				}
				return true;
			}
		});
		if (dialog.open() == IDialogConstants.OK_ID) {
			IResource resource = (IResource) dialog.getFirstResult();
			String arg = resource.getProjectRelativePath().toPortableString();
			htmlName.setText(arg);
		}
	}
	
	private IProject getProject() {
		return this.htmlArgumentsTab.getProject();
	}
	
	public String getName() {
		return "Html File" ;
	}

	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		String file = getAttributeValueFrom(htmlName) ; 
		if (file != null) { 
			if (!(file.endsWith(".htm") || file.endsWith(".html"))) { 
				configuration.setAttribute(IHtmlLaunchConstants.HTML_RESOURCE_LOCATION, EMPTY_STRING) ;
			} else { 
				configuration.setAttribute(IHtmlLaunchConstants.HTML_RESOURCE_LOCATION, getAttributeValueFrom(htmlName)) ;
			}
		}
	}
	
	// Default for a new configuration
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute(IHtmlLaunchConstants.HTML_RESOURCE_LOCATION, HtmlLaunchUtils.getSelectedResource());
	}
	
	public void initializeFrom(ILaunchConfiguration configuration) {
		try { 
			htmlName.setText(configuration.getAttribute(IHtmlLaunchConstants.HTML_RESOURCE_LOCATION, EMPTY_STRING)) ;
		} catch (CoreException e) { 
			DLTKUIPlugin.log(e);
		}
	}

	public String getResourceLocation() { 
		return  htmlName.getText().trim();
	}
	
	protected String getAttributeValueFrom(Text text) { 
		String content = text.getText().trim() ;
		if (content.length() > 0) 
			return content ;
		return null ; 
	}
}
