/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.ui.editor;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;

public class VjoROEditor extends VjoEditor {

	private StackLayout fStackLayout;
	private Composite fParent;

	private Composite fViewerComposite;

	public VjoROEditor() {

	}

	@Override
	public void doSave(IProgressMonitor monitor) {

	}

	@Override
	public void doSaveAs() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isEditable() {
		return false;
	}

	@Override
	public boolean isEditorInputReadOnly() {
		return true;
	}

	@Override
	protected boolean isInInsertMode() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		this.setSite(site);
		this.setInput(input);
	}

	@Override
	public boolean isDirty() {
		return false;
	}

	@Override
	public boolean isSaveAsAllowed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void createPartControl(Composite parent) {
		fParent = new Composite(parent, SWT.NONE);
		fStackLayout = new StackLayout();
		fParent.setLayout(fStackLayout);

		fViewerComposite = new Composite(fParent, SWT.NONE);
		fViewerComposite.setLayout(new FillLayout());

		super.createPartControl(fViewerComposite);
		fStackLayout.topControl = fViewerComposite;
		fParent.layout();
		// super.createPartControl(parent);
		// parent.setEnabled(false);
	}
}
