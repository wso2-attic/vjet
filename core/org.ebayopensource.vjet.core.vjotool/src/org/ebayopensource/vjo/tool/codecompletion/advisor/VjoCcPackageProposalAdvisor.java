/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.tool.codecompletion.advisor;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstPackage;
import org.ebayopensource.dsf.jst.ts.JstTypeSpaceMgr;
import org.ebayopensource.dsf.ts.ITypeSpace;
import org.ebayopensource.vjo.tool.codecompletion.IVjoCcAdvisor;
import org.ebayopensource.vjo.tool.codecompletion.StringUtils;
import org.ebayopensource.vjo.tool.codecompletion.VjoCcCtx;

public class VjoCcPackageProposalAdvisor extends AbstractVjoCcAdvisor implements
		IVjoCcAdvisor {
	public static final String ID = VjoCcPackageProposalAdvisor.class.getName();

	public void advise(VjoCcCtx ctx) {
		JstTypeSpaceMgr tsMrg = ctx.getJstTypeSpaceMgr();
		String token = ctx.getActingPackageToken();
		ITypeSpace<IJstType, IJstNode> tsds = tsMrg.getTypeSpace();
		List<IJstType> types = tsds.getVisibleTypes(tsds.getGroup(ctx.getGroupName())); 
		Map<String, JstPackage> packages = new HashMap<String, JstPackage>();
		Iterator<IJstType> it = types.iterator();
		while(it.hasNext()){
			IJstType jtype = it.next();
			
			 if(!ctx.isMtypeEabled() && jtype.isMetaType()){
				 continue;
			 }

			
			JstPackage packge = jtype.getPackage();
			if (packge == null || StringUtils.isBlankOrEmpty(packge.getName())) {
				continue;
			}
			if (packages.containsKey(packge.getName())) {
				continue;
			} else {
				packages.put(packge.getName(), packge);
			}
		}
		if (!packages.isEmpty()) {
			Iterator<Entry<String, JstPackage>> it1 = packages.entrySet()
					.iterator();
			while (it1.hasNext()) {
				JstPackage pack = it1.next().getValue();
				if (pack.getName().startsWith(token)) {
					appendData(ctx, pack);
				}
			}
		}
	}

}
