/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.tool.codecompletion.advisor;

import org.ebayopensource.dsf.jst.IJstType;

/**
 * advise actual class (except interface) found from type space.
 * example1:
 * ctype().extends('<cursor>')
 * 
 *  need attributes:
 *  1.ctx.actingToken 
 * 
 *
 */
public class VjoCcCTypeProposalAdvisor extends VjoCcTypeProposalAdvisor {
	public static final String ID = VjoCcCTypeProposalAdvisor.class.getName();

	protected boolean typeTypeCheck(IJstType jstType) {
		if (jstType.isInterface() || jstType.isOType()) {
			return false;
		}
		return true;
	}
}
