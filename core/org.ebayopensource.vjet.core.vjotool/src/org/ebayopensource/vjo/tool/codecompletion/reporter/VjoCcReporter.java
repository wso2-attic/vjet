/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.tool.codecompletion.reporter;

import java.util.ArrayList;
import java.util.List;

import org.ebayopensource.vjo.tool.codecompletion.IVjoCcProposalData;
import org.ebayopensource.vjo.tool.codecompletion.IVjoCcReporter;

public class VjoCcReporter implements IVjoCcReporter {
	private List<IVjoCcProposalData> m_result = new ArrayList<IVjoCcProposalData>();

	public void addPropsal(IVjoCcProposalData data) {
		m_result.add(data);
	}

	public List<IVjoCcProposalData> getProposalData() {
		return m_result;
	}

}
