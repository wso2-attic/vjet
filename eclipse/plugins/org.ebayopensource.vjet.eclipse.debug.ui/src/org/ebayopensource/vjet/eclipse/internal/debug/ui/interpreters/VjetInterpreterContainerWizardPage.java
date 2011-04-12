/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.debug.ui.interpreters;

import org.eclipse.dltk.mod.internal.debug.ui.interpreters.AbstractInterpreterComboBlock;
import org.eclipse.dltk.mod.internal.debug.ui.interpreters.AbstractInterpreterContainerWizardPage;

public class VjetInterpreterContainerWizardPage extends AbstractInterpreterContainerWizardPage{

	@Override
	protected AbstractInterpreterComboBlock getInterpreterBlock() {
		return new VjetInterpreterComboBlock();
	}

}
