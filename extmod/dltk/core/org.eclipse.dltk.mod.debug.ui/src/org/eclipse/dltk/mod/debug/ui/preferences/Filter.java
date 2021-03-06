/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.dltk.mod.debug.ui.preferences;

 
/**
 * Model object that represents a single entry in a filter table.
 */
public class Filter {

	private String fName;
	private boolean fChecked;
	// Element modifiers, used for correct icon display.
	int modifiers;

	public Filter(String name, boolean checked, int modifiers) {
		setName(name);
		setChecked(checked);
		setModifiers(modifiers);
	}

	public int getModifiers() {
		return modifiers;
	}

	public void setModifiers(int modifiers) {
		this.modifiers = modifiers;
	}

	public String getName() {
		return fName;
	}

	public void setName(String name) {
		fName = name;
	}

	public boolean isChecked() {
		return fChecked;
	}

	public void setChecked(boolean checked) {
		fChecked = checked;
	}

	public boolean equals(Object o) {
		if (o instanceof Filter) {
			Filter other = (Filter) o;
			if (getName().equals(other.getName())) {
				return true;
			}
		}
		return false;
	}

	public int hashCode() {
		return getName().hashCode();
	}
}
