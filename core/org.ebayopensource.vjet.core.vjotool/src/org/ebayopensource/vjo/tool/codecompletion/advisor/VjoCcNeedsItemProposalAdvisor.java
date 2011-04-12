/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.tool.codecompletion.advisor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.vjo.tool.codecompletion.VjoCcCtx;

/**
 * advise all the types which referenced in vjo.needs('xxx') clearly; Also items
 * in inherits('xxx'), satisfies('xxx')
 * 
 * example: this.vj$.x<cursor>
 * 
 * need attributes: 1.ctx.actingType 2.ctx.actingToken
 * 
 * 
 * 
 */
public class VjoCcNeedsItemProposalAdvisor extends AbstractVjoCcAdvisor {
	public static final String ID = VjoCcNeedsItemProposalAdvisor.class
			.getName();

	public void advise(VjoCcCtx ctx) {
		IJstType type = ctx.getActingType();
		if (type == null) {
			return;
		}
		String token = ctx.getActingToken();
		List<IJstType> result = new ArrayList<IJstType>();
		List<String> sresult = new ArrayList<String>();
		// Add itself
		appendTempType(type, result, sresult);
		// Add parent type if having
		IJstNode node = type.getParentNode();
		while (node != null && (node instanceof IJstType)) {
			appendTempType((IJstType) node, result, sresult);
			node = node.getParentNode();
		}
		// Add child type if having
		// Inner type should not be proposed after "this.vj$."
//		List<? extends IJstType> list = type.getEmbededTypes();
//		if (list != null && !list.isEmpty()) {
//			Iterator<? extends IJstType> it = list.iterator();
//			while (it.hasNext()) {
//				IJstType temp = it.next();
//				appendTempType(temp, result, sresult);
//			}
//		}
		// Add needs
		List<? extends IJstType> etypes = type.getImports();
		Iterator<? extends IJstType> it = etypes.iterator();
		while (it.hasNext()) {
			IJstType temp = it.next();
			appendTempType(temp, result, sresult);
		}
		// Add inherites
		IJstType inheritType = type.getExtend();
		if (inheritType != null) {
			appendTempType(inheritType, result, sresult);
		}
		// Add satifies
		List<? extends IJstType> stypes = type.getSatisfies();
		it = stypes.iterator();
		while (it.hasNext()) {
			IJstType temp = it.next();
			appendTempType(temp, result, sresult);
		}
		Iterator<IJstType> it1 = result.iterator();
		while (it1.hasNext()) {
			IJstType temp = it1.next();
			String simpleName = temp.getSimpleName();
			if (exactMatch(simpleName, token)) {
				appendData(ctx, temp);
			} else if (basicMatch(simpleName, token)) {
				appendData(ctx, temp, false);
			}
		}
	}

	private void appendTempType(IJstType type, List<IJstType> result,
			List<String> sresult) {
		if (type == null || type.isFakeType()) {
			return;
		}
		String simpleName = type.getSimpleName();
		if (!sresult.contains(simpleName)) {
			sresult.add(simpleName);
			result.add(type);
		}
	}

}
