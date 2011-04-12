/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.ui.wizards;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.dltk.mod.internal.ui.dialogs.StatusInfo;

public class VjoNameValidator {

	static final char DOT = '.';

	/**
	 * If the name start or end with dot, return the error status.
	 * 
	 * @param name
	 * @return
	 */
	public static IStatus startOrEndWithDot(String name) {
		if (name.charAt(0) == DOT || name.charAt(name.length() - 1) == DOT) {
			return new StatusInfo(IStatus.ERROR,
					VjetWizardMessages.convention_package_dotName);
		}
		return null;
	}

	/**
	 * If the name has consecutive dot in the name,return the error status.
	 * 
	 * @param name
	 * @return
	 */
	public static IStatus consecutiveDotsName(String name) {
		int dot = 0;
		int length = name.length();
		while (dot != -1 && dot < length - 1) {
			if ((dot = name.indexOf(DOT, dot + 1)) != -1 && dot < length - 1
					&& name.charAt(dot + 1) == DOT) {
				return new StatusInfo(
						IStatus.ERROR,
						VjetWizardMessages.convention_package_consecutiveDotsName);
			}
		}
		return null;
	}

}
