/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.ui.templates;

import org.ebayopensource.vjet.eclipse.internal.ui.text.SimpleVjoSourceViewerConfiguration;
import org.ebayopensource.vjet.eclipse.internal.ui.text.VjoTextTools;
import org.ebayopensource.vjet.eclipse.ui.VjetUIPlugin;
import org.eclipse.dltk.mod.javascript.ui.text.IJavaScriptPartitions;
import org.eclipse.dltk.mod.ui.templates.ScriptTemplateAccess;
import org.eclipse.dltk.mod.ui.templates.ScriptTemplatePreferencePage;
import org.eclipse.dltk.mod.ui.text.ScriptSourceViewerConfiguration;
import org.eclipse.jface.text.IDocument;

/**
 * Javascript templates preference page
 */
public class VjetTemplatePreferencePage extends
		ScriptTemplatePreferencePage {
	/*
	 * @see org.eclipse.dltk.mod.ui.templates.ScriptTemplatePreferencePage#createSourceViewerConfiguration()
	 */
	protected ScriptSourceViewerConfiguration createSourceViewerConfiguration() {
		return new SimpleVjoSourceViewerConfiguration(getTextTools()
				.getColorManager(), getPreferenceStore(), null,
				IJavaScriptPartitions.JS_PARTITIONING, false);
	}

	/*
	 * @see org.eclipse.dltk.mod.ui.templates.ScriptTemplatePreferencePage#getTemplateAccess()
	 */
	protected ScriptTemplateAccess getTemplateAccess() {
		return VjoTemplateAccess.getInstance();
	}

	/*
	 * @see org.eclipse.dltk.mod.ui.templates.ScriptTemplatePreferencePage#setDocumentParticioner(org.eclipse.jface.text.IDocument)
	 */
	protected void setDocumentParticioner(IDocument document) {
		getTextTools().setupDocumentPartitioner(document,
				IJavaScriptPartitions.JS_PARTITIONING);
	}

	/*
	 * @see org.eclipse.dltk.mod.ui.templates.ScriptTemplatePreferencePage#setPreferenceStore()
	 */
	protected void setPreferenceStore() {
		setPreferenceStore(VjetUIPlugin.getDefault().getPreferenceStore());
	}

	private VjoTextTools getTextTools() {
		return VjetUIPlugin.getDefault().getTextTools();
	}
}
