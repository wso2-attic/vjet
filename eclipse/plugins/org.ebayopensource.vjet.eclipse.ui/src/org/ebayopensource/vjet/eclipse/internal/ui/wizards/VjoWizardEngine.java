/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.ui.wizards;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.mod.core.IModelElement;
import org.eclipse.dltk.mod.core.IProjectFragment;
import org.eclipse.dltk.mod.core.IScriptFolder;


/**
 * Engine for emulation of a work of VJET wizards. 
 *
 */
public class VjoWizardEngine {
	
	/**
	 * Creates a new vjo package.
	 * 
	 * @param scriptFolder {@link IScriptFolder}
	 * @param packageName 
	 * 
	 * @return the name of created vjo package.
	 * 
	 * @throws CoreException {@link CoreException}
	 * @throws InterruptedException {@link InterruptedException}
	 */
	public Object createPackage(IScriptFolder scriptFolder, String packageName) throws CoreException, InterruptedException {
		VjoNewPackageWizardPage wizard = new VjoNewPackageWizardPage();
		
		// set preferences
//		wizard.setScriptFolder(scriptFolder, true);
		 IProjectFragment fragment = (IProjectFragment) scriptFolder
		 .getAncestor(IModelElement.PROJECT_FRAGMENT);
		 wizard.setProjectFragmentRoot(fragment, false);
		wizard.setPackageText(packageName, true);
		wizard.createPackage(null);
		
		return wizard.getNewScriptFolder();
	}
	
	/**
	 * Creates a new vjo type.
	 * 
	 * @param scriptFolder {@link IScriptFolder}
	 * @param name
	 * @param superclass the name of parent class
	 * @param isAbstract
	 * @param isMethodStubGeneration
	 * 
	 * @throws InterruptedException {@link InterruptedException}
	 * @throws CoreException {@link CoreException}
	 */
	public void createType(IScriptFolder scriptFolder, String name, String superclass, boolean isAbstract, boolean isMethodStubGeneration,boolean isConstructorGeneration) throws InterruptedException, CoreException {
		VjoClassCreationWizard wizard = new VjoClassCreationWizard();
		wizard.addPages();
		VjoClassCreationPage page = (VjoClassCreationPage) wizard.getPages()[0];
		
		// set preferences
		 IProjectFragment fragment = (IProjectFragment) scriptFolder
		 .getAncestor(IModelElement.PROJECT_FRAGMENT);
		page.setProjectFragmentRoot(fragment, false);
//		page.setScriptFolder(scriptFolder, false);
		page.setFileText(name);
		page.initializeSuperClassField();
		if(superclass != null)
			page.setSuperclassFieldText(superclass);
		page.setAbstractButton(isAbstract);
		page.setMethodStubGeneration(isMethodStubGeneration);
		page.setConstructorStubGeneration(isConstructorGeneration);
		
		wizard.finishPage(null);
	}
	
	public void createType(IScriptFolder scriptFolder, String name, String superclass, boolean isAbstract, boolean isMethodStubGeneration) throws InterruptedException, CoreException {
		VjoClassCreationWizard wizard = new VjoClassCreationWizard();
		wizard.addPages();
		VjoClassCreationPage page = (VjoClassCreationPage) wizard.getPages()[0];
		
		// set preferences
		 IProjectFragment fragment = (IProjectFragment) scriptFolder
		 .getAncestor(IModelElement.PROJECT_FRAGMENT);
		page.setProjectFragmentRoot(fragment, false);
//		page.setScriptFolder(scriptFolder, false);
		page.setFileText(name);
		page.initializeSuperClassField();
		if(superclass != null)
			page.setSuperclassFieldText(superclass);
		page.setAbstractButton(isAbstract);
		page.setMethodStubGeneration(isMethodStubGeneration);
		
		wizard.finishPage(null);
	}
	/**
	 * Creates a new vjo interface.
	 * 
	 * @param scriptFolder {@link IScriptFolder}
	 * @param name
	 * 
	 * @throws CoreException {@link CoreException}
	 * @throws InterruptedException {@link InterruptedException}
	 */
	public void createInterface(IScriptFolder scriptFolder, String name) throws CoreException, InterruptedException {
		VjoInterfaceCreationWizard wizard = new VjoInterfaceCreationWizard();
		wizard.addPages();
		wizard.initializeData();
		VjoSourceModulePage page = (VjoSourceModulePage) wizard.getPages()[0];
						
		// set preferences
		 IProjectFragment fragment = (IProjectFragment) scriptFolder
		 .getAncestor(IModelElement.PROJECT_FRAGMENT);
		page.setProjectFragmentRoot(fragment, true);
		page.setFileText(name);

		wizard.finishPage(null);
	}
	
	/**
	 * Creates a new vjo enumeration type.
	 * 
	 * @param scriptFolder {@link IScriptFolder}
	 * @param name
	 * 
	 * @throws CoreException {@link CoreException}
	 * @throws InterruptedException {@link InterruptedException}
	 */
	public void createEnumeration(IScriptFolder scriptFolder, String name) throws CoreException, InterruptedException {
		VjoEnumCreationWizard wizard = new VjoEnumCreationWizard();
		wizard.addPages();
		wizard.initializeData();
		VjoSourceModulePage page = (VjoSourceModulePage) wizard.getPages()[0];
						
		// set preferences
//		page.setScriptFolder(scriptFolder, true);
		// set preferences
		 IProjectFragment fragment = (IProjectFragment) scriptFolder
		 .getAncestor(IModelElement.PROJECT_FRAGMENT);
		page.setProjectFragmentRoot(fragment, true);		
		page.setFileText(name);

		wizard.finishPage(null);
	}
	
	/**
	 * Creates a new vjo mixin type.
	 * 
	 * @param scriptFolder {@link IScriptFolder}
	 * @param name
	 * 
	 * @throws CoreException {@link CoreException}
	 * @throws InterruptedException {@link InterruptedException}
	 */
	public void createMixin(IScriptFolder scriptFolder, String name) throws CoreException, InterruptedException {
		VjoMixinCreationWizard wizard = new VjoMixinCreationWizard();
		wizard.addPages();
		wizard.initializeData();
		VjoSourceModulePage page = (VjoSourceModulePage) wizard.getPages()[0];
						
		// set preferences
//		page.setScriptFolder(scriptFolder, true);
		// set preferences
		 IProjectFragment fragment = (IProjectFragment) scriptFolder
		 .getAncestor(IModelElement.PROJECT_FRAGMENT);
		page.setProjectFragmentRoot(fragment, true);
		page.setFileText(name);

		wizard.finishPage(null);
	}
}
