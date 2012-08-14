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
package org.ebayopensource.vjet.eclipse.internal.ui.wizards;

import java.util.ArrayList;
import java.util.List;

import org.ebayopensource.vjet.eclipse.core.VjetPlugin;
import org.ebayopensource.vjet.eclipse.core.VjoNature;
import org.ebayopensource.vjet.eclipse.internal.ui.preferences.VjetBuildpathBlock;
import org.ebayopensource.vjet.eclipse.ui.VjetUIPlugin;
import org.eclipse.core.internal.resources.Workspace;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.dltk.mod.core.DLTKCore;
import org.eclipse.dltk.mod.core.IBuildpathAttribute;
import org.eclipse.dltk.mod.core.IBuildpathEntry;
import org.eclipse.dltk.mod.core.IModelElement;
import org.eclipse.dltk.mod.core.IProjectFragment;
import org.eclipse.dltk.mod.core.IScriptProject;
import org.eclipse.dltk.mod.core.ModelException;
import org.eclipse.dltk.mod.internal.core.BuildpathEntry;
import org.eclipse.dltk.mod.internal.core.ScriptProject;
import org.eclipse.dltk.mod.javascript.ui.JavaScriptImages;
import org.eclipse.dltk.mod.ui.DLTKUIPlugin;
import org.eclipse.dltk.mod.ui.util.BusyIndicatorRunnableContext;
import org.eclipse.dltk.mod.ui.util.IStatusChangeListener;
import org.eclipse.dltk.mod.ui.wizards.BuildpathsBlock;
import org.eclipse.dltk.mod.ui.wizards.NewElementWizard;
import org.eclipse.dltk.mod.ui.wizards.ProjectWizardFirstPage;
import org.eclipse.dltk.mod.ui.wizards.ProjectWizardSecondPage;
import org.eclipse.dltk.mod.ui.wizards.WorkingSetConfigurationBlock;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkingSet;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.wizards.newresource.BasicNewProjectResourceWizard;

/**
 * VJET wizard for creating a new vjo project.
 * 
 * 
 * 
 */
public class VjetProjectCreationWizard extends NewElementWizard implements
		INewWizard, IExecutableExtension {

	private static final Path[] EMPTY_PATH = new Path[0];
	private static final String SOURCE_FOLDER_NAME = "src";
	private static final String ROOT_STRING = "/";
	private IConfigurationElement fConfigElement;
	private ProjectWizardFirstPage fFirstPage = new VJOProjectWizardFirstPage();
	private ProjectWizardSecondPage fSecondPage;

	public VjetProjectCreationWizard() {
		setDefaultPageImageDescriptor(JavaScriptImages.DESC_WIZBAN_PROJECT_CREATION);
		setDialogSettings(DLTKUIPlugin.getDefault().getDialogSettings());
		setWindowTitle(VjetWizardMessages.ProjectCreationWizard_title);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#addPages()
	 */
	public void addPages() {
		super.addPages();
		fFirstPage.setTitle(VjetWizardMessages.ProjectCreationWizardFirstPage_title);
		fFirstPage.setDescription(VjetWizardMessages.ProjectCreationWizardFirstPage_description);
		addPage(fFirstPage);
		
		fSecondPage = new ProjectWizardSecondPage(fFirstPage) {
			@Override
			protected BuildpathsBlock createBuildpathBlock(
					IStatusChangeListener listener) {
				return new VjetBuildpathBlock(
						new BusyIndicatorRunnableContext(), listener, 0,
						useNewSourcePage(), null);
			}

			@Override
			protected IPreferenceStore getPreferenceStore() {
				//modify by zhuxing: change to vjet ui plugin related preference store
				return VjetUIPlugin.getDefault().getPreferenceStore();
			}

			@Override
			protected String getScriptNature() {
				return VjoNature.NATURE_ID;
			}
			
			@Override
			public void createControl(Composite parent) {
				super.createControl(parent);
				PlatformUI.getWorkbench().getHelpSystem().setHelp(parent,
						IHelpContextIds.NEW_PROJECT);
			}
	
		
			@Override
			public void init(IScriptProject jproject,
					IBuildpathEntry[] defaultEntries,
					boolean defaultsOverrideExistingBuildpath) {
				//add sdk container
				List<IBuildpathEntry> entriesList = new ArrayList<IBuildpathEntry>();
				if(defaultEntries!=null && defaultEntries.length>0){
					for(IBuildpathEntry e: defaultEntries){
						entriesList.add(e);
					}
				}
				entriesList.add(DLTKCore.newContainerEntry(getJsSDKPath()));
				entriesList.add(DLTKCore.newContainerEntry(getBrowserSDKPath()));
				entriesList.add(DLTKCore.newContainerEntry(getVjoPath()));
				IBuildpathEntry[] ary = new IBuildpathEntry[]{};
				super.init(jproject, entriesList.toArray(ary), defaultsOverrideExistingBuildpath);
			}
		};
		addPage(fSecondPage);
	}

	
	private IPath getSdkPath() {
		IPath path = new Path(VjetPlugin.SDK_CONTAINER);
		path = path.append(VjetPlugin.ID_DEFAULT_SDK);
		return path;
	}
	
	private IPath getJsSDKPath() {
		IPath path = new Path(VjetPlugin.JSNATIVESDK_ID);
		path = path.append(VjetPlugin.JS_DEFAULT_SDK_LABEL);
		return path;
	}
	

	
	
	private IPath getBrowserSDKPath() {
		IPath path = new Path(VjetPlugin.BROWSERSDK_ID);
		path = path.append(VjetPlugin.BROWSERSDK_LABEL);
		return path;
	}
	
	private IPath getVjoPath() {
		IPath path = new Path(VjetPlugin.VJOLIB_ID);
		path = path.append(VjetPlugin.VJOLIB_LABEL);
		return path;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.dltk.mod.ui.wizards.NewElementWizard#finishPage(org.eclipse.core.runtime.IProgressMonitor)
	 */
	protected void finishPage(IProgressMonitor monitor)
			throws InterruptedException, CoreException {
		fSecondPage.performFinish(monitor); // use the full progress monitor
	
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.dltk.mod.ui.wizards.NewElementWizard#getCreatedElement()
	 */
	public IModelElement getCreatedElement() {
		return DLTKCore.create(fFirstPage.getProjectHandle());
	}

	@Override
	public boolean performCancel() {
		fSecondPage.performCancel();
		return super.performCancel();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.dltk.mod.ui.wizards.NewElementWizard#performFinish()
	 */
	public boolean performFinish() {
		boolean res = super.performFinish();
		if (res) {
			BasicNewProjectResourceWizard.updatePerspective(fConfigElement);
			ScriptProject project = (ScriptProject) fSecondPage
					.getScriptProject();
			
			// Add by Oliver. Make VJO project to be organized by work set.
			IWorkingSet[] workingSets= fFirstPage.getWorkingSets();
			WorkingSetConfigurationBlock.addToWorkingSets(project, workingSets);
			
			//delete by Kevin, to fix bug 2517, don't create source folder automatically
//			addDefaultSourceFolder(project);
			selectAndReveal(project.getProject());
		}
		return res;
	}

	/**
	 * Adds a default source folder to created project.
	 * 
	 * @param project
	 *            {@link ScriptProject}
	 */
	private void addDefaultSourceFolder(ScriptProject project) {
		Workspace workspace = (Workspace) project.getProject().getWorkspace();
		IPath path = new Path(ROOT_STRING);
		path = path.append(project.getElementName());
		path = path.append(SOURCE_FOLDER_NAME);
		IFolder folder = (IFolder) workspace
				.newResource(path, IResource.FOLDER);
		try {
			folder.create(false, true, new NullProgressMonitor());
			IBuildpathEntry[] newEntries = getBuildpathEntries(project, path);
			project.saveBuildpath(newEntries);
		} catch (CoreException e) {
			DLTKCore.error(e.toString(), e);
		}
	}

	/**
	 * Gets a list of buildpath entries for specified project.
	 * 
	 * @param project
	 *            {@link IScriptProject}
	 * @param path
	 *            {@link IPath}
	 * 
	 * @return the list of buildpath entries {@link IBuildpathEntries}
	 * 
	 * @throws ModelException
	 *             {@link ModelException}
	 */
	private IBuildpathEntry[] getBuildpathEntries(IScriptProject project,
			IPath path) throws ModelException {
		IBuildpathEntry[] entries = project.getRawBuildpath();
		IBuildpathEntry[] newEntries = new BuildpathEntry[entries.length];
		System.arraycopy(entries, 0, newEntries, 0, entries.length);
		newEntries[0] = new BuildpathEntry(IProjectFragment.K_SOURCE,
				BuildpathEntry.BPE_SOURCE, path, false, EMPTY_PATH, EMPTY_PATH,
				null, false, new IBuildpathAttribute[0], false);
		return newEntries;
	}

	/*
	 * Stores the configuration element for the wizard. The config element will
	 * be used in <code>performFinish</code> to set the result perspective.
	 */
	public void setInitializationData(IConfigurationElement cfig,
			String propertyName, Object data) {
		fConfigElement = cfig;
	}
}