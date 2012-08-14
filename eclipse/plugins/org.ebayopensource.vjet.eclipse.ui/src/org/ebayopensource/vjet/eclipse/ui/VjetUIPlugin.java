/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.ui;

import java.io.IOException;

//import org.ebayopensource.vjet.eclipse.internal.ui.editor.NativeElementFileAdvisor;
import org.ebayopensource.vjet.eclipse.internal.ui.editor.VjoEditor;
import org.ebayopensource.vjet.eclipse.internal.ui.preferences.codestyle.CodeTemplateContextType;
import org.ebayopensource.vjet.eclipse.internal.ui.preferences.codestyle.CodeTemplates;
import org.ebayopensource.vjet.eclipse.internal.ui.text.VjoTextTools;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.content.IContentType;
//import org.eclipse.dltk.mod.internal.ui.editor.ExternalFileEditorInput;
import org.eclipse.dltk.mod.ui.DLTKUIPlugin;
import org.eclipse.dltk.mod.ui.viewsupport.ImageDescriptorRegistry;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.FontRegistry;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.text.templates.ContextTypeRegistry;
import org.eclipse.jface.text.templates.persistence.TemplateStore;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorRegistry;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IPerspectiveListener;
import org.eclipse.ui.IStartup;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.editors.text.templates.ContributionContextTypeRegistry;
import org.eclipse.ui.editors.text.templates.ContributionTemplateStore;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.ui.progress.WorkbenchJob;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class VjetUIPlugin extends AbstractUIPlugin implements IStartup {
	private IPartListener partListener = new VjetPartListener();
	private IPerspectiveListener perspectiveListener = new VjetPerspectiveListener();

	// The shared instance
	private static VjetUIPlugin s_plugin;

	// The plug-in ID
	public static final String PLUGIN_ID = "org.ebayopensource.vjet.eclipse.ui";

	// for code generation templates
	private TemplateStore fCodeTemplateStore;

	/**
	 * The key to store customized code templates.
	 */
	private static final String CODE_TEMPLATES_KEY = "org.eclipse.vjet.ui.text.custom_code_templates"; //$NON-NLS-1$

	/**
	 * The key to store whether the legacy code templates have been migrated
	 */
	private static final String CODE_TEMPLATES_MIGRATION_KEY = "org.eclipse.vjet.ui.text.code_templates_migrated"; //$NON-NLS-1$

	/**
	 * The code template context type registry for the java editor.
	 */
	private ContextTypeRegistry fCodeTemplateContextTypeRegistry;

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static VjetUIPlugin getDefault() {
		return s_plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given plug-in
	 * relative path.
	 * 
	 * @param path
	 *            the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return AbstractUIPlugin.imageDescriptorFromPlugin(PLUGIN_ID, path);
	}

	private VjoTextTools fJavascriptTextTools;

	private ImageDescriptorRegistry m_imageDescriptorRegistry;

	/**
	 * The constructor
	 */
	public VjetUIPlugin() {
	}

	public synchronized VjoTextTools getTextTools() {
		if (fJavascriptTextTools == null)
			fJavascriptTextTools = new VjoTextTools(true);
		return fJavascriptTextTools;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		
		//Added by Eric.Ma to fix <VJET JS> view name in view list
		IPerspectiveDescriptor[] allSets = PlatformUI.getWorkbench().getPerspectiveRegistry().getPerspectives();
        for (IPerspectiveDescriptor iPerspectiveDescriptor : allSets) {
            if(iPerspectiveDescriptor.getId().contains("VJET_JS")){
                PlatformUI.getWorkbench().getPerspectiveRegistry().deletePerspective(iPerspectiveDescriptor);
            }
        }
        //End of added
		setPluginInstance(this);

		new WorkbenchJob("Starting VJET UI plugin") {
			
			@Override
			public IStatus runInUIThread(IProgressMonitor arg0) {
				IWorkbenchWindow window = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow();
				window.getPartService().addPartListener(partListener);
				registryAdvisor();
				reloadFont();
				return new Status(IStatus.OK, PLUGIN_ID, IStatus.OK, "Finished creating part", null);
	
			}
		}.schedule();
		
//		Display.getDefault().asyncExec(new Runnable() {
//			public void run() {
//				IWorkbenchWindow window = PlatformUI.getWorkbench()
//						.getActiveWorkbenchWindow();
//				window.getPartService().addPartListener(partListener);
//				// window.addPerspectiveListener(perspectiveListener);
//				registryAdvisor();
//				reloadFont();
//			}
//		});
		
        
        // add by patrick
        // make the vjet editor default to js file
        IEditorRegistry editorRegistry = VjetUIPlugin.getDefault().getWorkbench().getEditorRegistry();
        editorRegistry.setDefaultEditor(getExtentionPattern(), VjoEditor.EDITOR_ID);
	}

	private String getExtentionPattern() {
		return "*."
				+ VjoEditor.getDefaultContentType().getFileSpecs(
						IContentType.FILE_EXTENSION_SPEC)[0];
	}

	/**
	 * Because clearcase cq tool cause a wrong font loaded for
	 * "JFaceResources.TEXT_FONT", So here if the font is not correct, reload
	 * it.
	 */
	private void reloadFont() {
		FontRegistry registry = JFaceResources.getFontRegistry();
		if (registry.defaultFont().equals(
				registry.get(JFaceResources.TEXT_FONT))) {
			FontData[] datas = registry.getFontData(JFaceResources.TEXT_FONT);
			registry.put(JFaceResources.TEXT_FONT, registry.defaultFont()
					.getFontData());
			registry.put(JFaceResources.TEXT_FONT, datas);
		}
	}

	private void registryAdvisor() {
//		ExternalFileEditorInput.registryAdvisor(new NativeElementFileAdvisor());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(BundleContext context) throws Exception {
		setPluginInstance(null);
		IWorkbenchWindow window = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow();
		if (window != null) {
			window.getPartService().removePartListener(partListener);
		}
		super.stop(context);
	}

	// Modify by Oliver, 2009-12-01, fix findbugs bug.
	private static void setPluginInstance(VjetUIPlugin pluginPar) {
		s_plugin = pluginPar;
	}

	/**
	 * Returns the template store for the code generation templates.
	 * 
	 * @return the template store for the code generation templates
	 */
	public TemplateStore getCodeTemplateStore() {
		if (fCodeTemplateStore == null) {
			IPreferenceStore store = getPreferenceStore();
			boolean alreadyMigrated = store
					.getBoolean(CODE_TEMPLATES_MIGRATION_KEY);
			if (alreadyMigrated)
				fCodeTemplateStore = new ContributionTemplateStore(
						getCodeTemplateContextRegistry(), store,
						CODE_TEMPLATES_KEY);
			else {
				fCodeTemplateStore = new org.ebayopensource.vjet.eclipse.internal.ui.preferences.codestyle.CompatibilityTemplateStore(
						getCodeTemplateContextRegistry(), store,
						CODE_TEMPLATES_KEY, getOldCodeTemplateStoreInstance());
				store.setValue(CODE_TEMPLATES_MIGRATION_KEY, true);
			}

			try {
				fCodeTemplateStore.load();
			} catch (IOException e) {
				DLTKUIPlugin.log(e);
			}

			fCodeTemplateStore.startListeningForPreferenceChanges();

			// compatibility / bug fixing code for duplicated templates
			// TODO remove for 3.0
			org.ebayopensource.vjet.eclipse.internal.ui.preferences.codestyle.CompatibilityTemplateStore
					.pruneDuplicates(fCodeTemplateStore, true);

		}

		return fCodeTemplateStore;
	}

	/**
	 * Returns the template context type registry for the code generation
	 * templates.
	 * 
	 * @return the template context type registry for the code generation
	 *         templates
	 */
	public ContextTypeRegistry getCodeTemplateContextRegistry() {
		if (fCodeTemplateContextTypeRegistry == null) {
			fCodeTemplateContextTypeRegistry = new ContributionContextTypeRegistry();

			CodeTemplateContextType
					.registerContextTypes(fCodeTemplateContextTypeRegistry);
		}

		return fCodeTemplateContextTypeRegistry;
	}

	private CodeTemplates getOldCodeTemplateStoreInstance() {
		return CodeTemplates.getInstance();
	}

	/**
	 * Returns the currently active vjo editor, or <code>null</code> if it
	 * cannot be determined.
	 * 
	 * @return the currently active vjo editor, or <code>null</code>
	 */
	public static VjoEditor getVjoEditor() {
		IEditorPart part = getActivePage().getActiveEditor();
		if (part instanceof VjoEditor)
			return (VjoEditor) part;
		else
			return null;
	}

	public static IWorkbenchPage getActivePage() {
		return PlatformUI.getWorkbench().getActiveWorkbenchWindow()
				.getActivePage();
	}

	public static void log(int severity, String message) {
		log(new Status(severity, PLUGIN_ID, severity, message, null));
	}
	public static void log(Exception e) {
		log(new Status(IStatus.ERROR, PLUGIN_ID, IStatus.ERROR, null, e));
	}

	public static void log(IStatus status) {
		getDefault().getLog().log(status);
	}

	//add by patrick
	@Override
	public void earlyStartup() {
		System.out.println("Vjet UI early startup.");
	}
	
	

}
