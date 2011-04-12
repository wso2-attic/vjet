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
package org.eclipse.dltk.mod.ui.text.completion;

import org.eclipse.dltk.mod.ui.templates.ScriptTemplateProposal;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.contentassist.ICompletionProposalExtension4;
import org.eclipse.jface.text.templates.Template;
import org.eclipse.jface.text.templates.TemplateContext;
import org.eclipse.swt.graphics.Image;

/**
 * 
 * 
 */
public class VjoTemplateProposal extends ScriptTemplateProposal implements
		ICompletionProposalExtension4 {

	/**
	 * @param template
	 * @param context
	 * @param region
	 * @param image
	 * @param relevance
	 */
	public VjoTemplateProposal(Template template, TemplateContext context,
			IRegion region, Image image, int relevance) {
		super(template, context, region, image, relevance);
	}

	public boolean isAutoInsertable() {
		return false;
	}
}
