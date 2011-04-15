/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/* 
 * $Id: VjoValidationExport.java, Mar 16, 2010, 10:49:53 PM, liama. Exp$
 *
 * Copyright (c) 2006-2009 Ebay Technologies. All Rights Reserved.
 * This software program and documentation are copyrighted by Ebay 
 * Technologies.
 */
/* 
 * $Id: VjoValidationExport.java, Mar 16, 2010, 10:49:53 PM, liama. Exp$
 *
 * Copyright (c) 2006-2009 Ebay Technologies. All Rights Reserved.
 * This software program and documentation are copyrighted by Ebay 
 * Technologies.
 */
package org.ebayopensource.vjet.eclipse.core.test.validation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import junit.framework.Assert;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.PlatformUI;

import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoGroupRulesCache;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoSemanticRulePolicy;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.rules.VjoSemanticRuleRepo;
import org.ebayopensource.vjet.eclipse.core.test.parser.AbstractVjoModelTests;
import org.ebayopensource.vjet.eclipse.internal.ui.preferences.VjetProblemSeveritiesConfigurationBlock;

/**
 * Class/Interface description
 *
 * @author <a href="mailto:liama@ebay.com">liama</a>
 * @since JDK 1.5
 */
public class VjoValidationPolicy extends AbstractVjoModelTests {

    private static final String PROJECT_NAME = "BugVerifyProject";
    
    @Override
    protected void setUp() throws Exception {
        mgr.setAllowChanges(false);
        try {
            //close welcome view
            IViewReference[] references = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getViewReferences();
            for (int i = 0; i < references.length; i++) {
                if ("org.eclipse.ui.internal.introview".equals(references[i].getId()))
                    PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().hideView(references[i]);
            }
            
            // ensure autobuilding is turned off
            IWorkspaceDescription description = getWorkspace().getDescription();
            if (description.isAutoBuilding()) {
                description.setAutoBuilding(false);
                getWorkspace().setDescription(description);
            }
            
            //set up project
            setUpScriptProjectTo(PROJECT_NAME, PROJECT_NAME);                   
            
            //refresh typespace
            mgr.reload(this);   
            waitTypeSpaceLoaded();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    public void oldtestExportProjectFunction(){
        IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(PROJECT_NAME);
        String path = project.getLocation()+File.separator+"test.pref";
        VjetProblemSeveritiesConfigurationBlock.exportPolicy2File(Display.getCurrent().getActiveShell(), project, path);
        Assert.assertEquals(true, new File(path).exists());
    }
    
   
    public void oldtestModifyProjectValue(){
        IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(PROJECT_NAME);
        VjoGroupRulesCache.getInstance().addGroupPolicy(PROJECT_NAME,
                VjoSemanticRuleRepo.getInstance().PROPERTY_SHOULD_BE_DEFINED,
                VjoSemanticRulePolicy.GLOBAL_IGNORE_POLICY);
        String path = project.getLocation()+File.separator+"test.pref";
        VjetProblemSeveritiesConfigurationBlock.exportPolicy2File(Display.getCurrent().getActiveShell(), project, path);
        File file = new File(path);
        Assert.assertEquals(true,file.exists());
        Properties p = new Properties();
        try {
			p.load(new FileInputStream(file));
			String value = p.getProperty("Misc.Property_Should_Be_Defined");
			Assert.assertEquals("ignore", value);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public void oldtestExportGlobalFunction(){
        IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(PROJECT_NAME);
        String path = project.getLocation()+File.separator+"test.pref";
        VjetProblemSeveritiesConfigurationBlock.exportPolicy2File(Display.getCurrent().getActiveShell(), null, path);
        Assert.assertEquals(true, new File(path).exists());
    }
    
    public void oldtestImportFunction(){
        IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(PROJECT_NAME);
        VjetProblemSeveritiesConfigurationBlock block = new VjetProblemSeveritiesConfigurationBlock(null,project,null);
        String path = project.getLocation()+File.separator+"test.pref";
        block.importPre2Repo(Display.getCurrent().getActiveShell(), path);
    }
}
