/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.ui.actions;

import org.ebayopensource.vjet.eclipse.internal.ui.editor.VjoEditor;
import org.eclipse.jface.action.Action;

/**
 * Remove occurrence annotations action.
 * 
 * @since 3.0
 */
public class RemoveOccurrenceAnnotations extends Action {

	/** The Javascript editor to which this actions belongs. */
	private final VjoEditor fEditor;

	/**
	 * Creates this action.
	 * 
	 * @param editor
	 *            the Javascript editor for which to remove the occurrence annotations
	 */
	public RemoveOccurrenceAnnotations(VjoEditor editor) {
		fEditor = editor;
	}

	/*
	 * @see org.eclipse.jface.action.Action#run()
	 */
	public void run() {
		fEditor.removeOccurrenceAnnotations();
	}
}
