package org.ebayopensource.eclipse.vjet.javalaunch;

import org.ebayopensource.eclipse.vjet.javalaunch.utils.LaunchUtilPlugin;
import org.eclipse.jface.preference.*;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.IWorkbench;

/**
 * This class represents a preference page that
 * is contributed to the Preferences dialog. By 
 * subclassing <samp>FieldEditorPreferencePage</samp>, we
 * can use the field support built into JFace that allows
 * us to create a page that is small and knows how to 
 * save, restore and apply itself.
 * <p>
 * This page is used to modify preferences only. They
 * are stored in the preference store that belongs to
 * the main plug-in class. That way, preferences can
 * be accessed directly via the preference store.
 */

public class JavaSourcePreferencePage
	extends FieldEditorPreferencePage
	implements IWorkbenchPreferencePage {

	public JavaSourcePreferencePage() {
		super(GRID);
		setPreferenceStore(LaunchUtilPlugin.getDefault().getPreferenceStore());
		setDescription("This will set up the VM Launch Parameters to add the parameter -java.source.path with the calculated source directories");
	}
	
	/**
	 * Creates the field editors. Field editors are abstractions of
	 * the common GUI blocks needed to manipulate various types
	 * of preferences. Each field editor knows how to save and
	 * restore itself.
	 */
	public void createFieldEditors() {
		
//
		 addField(new BooleanFieldEditor(LaunchUtilPlugin.CONFIGURE_SOURCE_PATH_ENABLED, 
				 "Set vm parameter java.source.path", getFieldEditorParent()));
	        
		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}
	
}