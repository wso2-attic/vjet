/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.ui.wizards.dialogfields;

import org.eclipse.dltk.mod.internal.ui.util.SWTUtil;
import org.eclipse.dltk.mod.internal.ui.wizards.dialogfields.SelectionButtonDialogField;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

/**
 * This class is representation of the check box field
 * 
 * 
 *
 */
public class CheckBoxDialogField extends SelectionButtonDialogField {

	private int fButtonStyle;
	private String checkBoxText;

	public CheckBoxDialogField(int buttonStyle) {
		super(buttonStyle);
		this.fButtonStyle = buttonStyle;
	}

	public Control[] doFillIntoGrid(Composite parent, int nColumns) {
		assertEnoughColumns(nColumns);

		Label label = getLabelControl(parent);
		label.setLayoutData(gridDataForLabel(1));

		Button button = getSelectionButton(parent);		
		GridData gd = gridDataForCheckBox(nColumns - 1);
		if (fButtonStyle == SWT.PUSH) {
			gd.widthHint = SWTUtil.getButtonWidthHint(button);
		}

		button.setLayoutData(gd);

		return new Control[] { button };
	}
	
	@Override
	public Button getSelectionButton(Composite group) {
		Button button = super.getSelectionButton(group);
		button.setText(checkBoxText);
		return button;
	}

	protected static GridData gridDataForCheckBox(int span) {
		GridData gd = new GridData();
		gd.horizontalAlignment = GridData.FILL;
		gd.grabExcessHorizontalSpace = false;
		gd.horizontalSpan = span;
		return gd;
	}

	@Override
	public void setLabelText(String labeltext) {
		this.fLabelText = labeltext;
	}

	public String getCheckBoxText() {
		return checkBoxText;
	}

	public void setCheckBoxText(String checkBoxText) {
		this.checkBoxText = checkBoxText;
	}

}
