/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.debug.ui;

import org.eclipse.dltk.mod.debug.ui.ScriptDebugModelPresentation;
import org.eclipse.dltk.mod.internal.debug.ui.ScriptDebugHover;
import org.eclipse.jface.preference.IPreferenceStore;

public class VjetDebugHover extends ScriptDebugHover{

	@Override
	protected ScriptDebugModelPresentation getModelPresentation() {
		return new VjetDebugModelPresentation();
	}

	public void setPreferenceStore(IPreferenceStore store) {
		// TODO Auto-generated method stub
		
	}

}
