/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.tool.codecompletion.advisor;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.vjo.tool.codecompletion.IVjoCcAdvisor;
import org.ebayopensource.vjo.tool.codecompletion.IVjoCcProposalData;
import org.ebayopensource.vjo.tool.codecompletion.StringUtils;
import org.ebayopensource.vjo.tool.codecompletion.VjoCcCtx;
import org.ebayopensource.vjo.tool.codecompletion.proposaldata.VjoCcAliasProposalData;

/**
 * 
 * advise all the types which referenced in vjo.needs('xxx', 'yyy') and have
 * alias; example: this.vj$.x<cursor>
 * 
 * need attributes: 1.ctx.actingType 2.ctx.actingToken
 * 
 * 
 * 
 */
public class VjoCcAliasProposalAdvisor extends AbstractVjoCcAdvisor implements
		IVjoCcAdvisor {
	public static final String ID = VjoCcAliasProposalAdvisor.class.getName();

	public void advise(VjoCcCtx ctx) {
		String token = ctx.getActingToken();
		IJstType callingType = ctx.getJstType();
		if (callingType == null) {
			return;
		}
		Map<String, ? extends IJstType> map = callingType.getImportsMap();
		if (map == null || map.isEmpty()) {
			return;
		}
		Iterator<?> it1 = map.entrySet().iterator();
		while (it1.hasNext()) {
			Entry<String, ? extends IJstType> entry = (Entry<String, ? extends IJstType>) it1
					.next();
			String alias = entry.getKey();
			IJstType type = entry.getValue();
			if (!StringUtils.isBlankOrEmpty(alias) && alias.startsWith(token)
					&& type != null && !alias.equals(type.getSimpleName())) {
				IVjoCcProposalData data = new VjoCcAliasProposalData(alias, map
						.get(alias), ctx, ID);
				ctx.getReporter().addPropsal(data);
			}
		}

	}

}
