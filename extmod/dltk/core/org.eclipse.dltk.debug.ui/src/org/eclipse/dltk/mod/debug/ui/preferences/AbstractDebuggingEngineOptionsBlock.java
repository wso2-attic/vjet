package org.eclipse.dltk.mod.debug.ui.preferences;

import org.eclipse.core.resources.IProject;
import org.eclipse.dltk.mod.core.DLTKContributionExtensionManager;
import org.eclipse.dltk.mod.launching.debug.DebuggingEngineManager;
import org.eclipse.dltk.mod.ui.preferences.ContributedExtensionOptionsBlock;
import org.eclipse.dltk.mod.ui.preferences.PreferenceKey;
import org.eclipse.dltk.mod.ui.util.IStatusChangeListener;
import org.eclipse.ui.preferences.IWorkbenchPreferenceContainer;

public abstract class AbstractDebuggingEngineOptionsBlock extends
		ContributedExtensionOptionsBlock {

	public AbstractDebuggingEngineOptionsBlock(IStatusChangeListener context,
			IProject project, PreferenceKey[] allKeys,
			IWorkbenchPreferenceContainer container) {
		super(context, project, allKeys, container);
	}
	
	/*
	 * @see org.eclipse.dltk.mod.ui.preferences.DLTKContributedExtensionOptionsBlock#getExtensionManager()
	 */
	protected DLTKContributionExtensionManager getExtensionManager() {
		return DebuggingEngineManager.getInstance();
	}

	/*
	 * @see org.eclipse.dltk.mod.ui.preferences.DLTKContributedExtensionOptionsBlock#getSelectorGroupLabel()
	 */
	protected String getSelectorGroupLabel() {
		return ScriptDebugPreferencesMessages.DebuggingEngine;
	}

	/*
	 * @see org.eclipse.dltk.mod.ui.preferences.DLTKContributedExtensionOptionsBlock#getSelectorNameLabel()
	 */
	protected String getSelectorNameLabel() {
		return ScriptDebugPreferencesMessages.NameLabel;
	}

	/*
	 * @see org.eclipse.dltk.mod.ui.preferences.ContributedExtensionOptionsBlock#getPreferenceLinkMessage()
	 */
	protected String getPreferenceLinkMessage() {
		return ScriptDebugPreferencesMessages.LinkToDebuggingEnginePreferences;
	}

}
