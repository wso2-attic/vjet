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
package org.ebayopensource.vjet.eclipse.internal.ui;

import org.ebayopensource.vjet.eclipse.core.VjoLanguageToolkit;
import org.ebayopensource.vjet.eclipse.internal.ui.text.SimpleVjoSourceViewerConfiguration;
import org.ebayopensource.vjet.eclipse.ui.VjetUIPlugin;
import org.ebayopensource.vjet.eclipse.ui.VjoElementLabels;
import org.eclipse.dltk.mod.core.IDLTKLanguageToolkit;
import org.eclipse.dltk.mod.ui.AbstractDLTKUILanguageToolkit;
import org.eclipse.dltk.mod.ui.ScriptElementLabels;
import org.eclipse.dltk.mod.ui.text.ScriptSourceViewerConfiguration;
import org.eclipse.dltk.mod.ui.text.ScriptTextTools;
import org.eclipse.ui.plugin.AbstractUIPlugin;

/**
 * 
 * 
 */
public class VjetUILanguageToolkit extends AbstractDLTKUILanguageToolkit {
	private static ScriptElementLabels s_instance = new VjoElementLabels();

	public IDLTKLanguageToolkit getCoreToolkit() {
		return VjoLanguageToolkit.getDefault();
	}

	@Override
	public String getEditorId(Object inputElement) {
		return "org.ebayopensource.vjet.ui.VjetJsEditor";
	}

	@Override
	public ScriptElementLabels getScriptElementLabels() {
		return s_instance;
	}

	@Override
	public ScriptTextTools getTextTools() {
		return super.getTextTools();
	}

	@Override
	protected AbstractUIPlugin getUIPLugin() {
		return VjetUIPlugin.getDefault();
	}

	@Override
	public ScriptSourceViewerConfiguration createSourceViewerConfiguration() {
		return new SimpleVjoSourceViewerConfiguration(getTextTools()
				.getColorManager(), getPreferenceStore(), null,
				getPartitioningId(), false);
	}
}
