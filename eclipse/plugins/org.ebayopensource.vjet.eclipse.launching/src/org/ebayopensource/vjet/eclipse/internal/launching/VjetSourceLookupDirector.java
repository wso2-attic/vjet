/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.launching;

import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.debug.core.sourcelookup.AbstractSourceLookupDirector;
import org.eclipse.debug.core.sourcelookup.ISourceLookupParticipant;

public class VjetSourceLookupDirector extends AbstractSourceLookupDirector {

	public Object getSourceElement(IStackFrame stackFrame) {
		return getSourceElement((Object) stackFrame);
	}

	@Override
	public void initializeParticipants() {
		addParticipants(new ISourceLookupParticipant[] {
				new VjetDBGPSourceLookupParticipant(),
				new VjetSourceLookupParticipant() });
	}

}
