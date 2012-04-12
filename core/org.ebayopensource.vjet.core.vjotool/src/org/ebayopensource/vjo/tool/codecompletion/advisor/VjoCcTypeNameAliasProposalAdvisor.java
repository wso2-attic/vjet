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
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstRefType;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstName;
import org.ebayopensource.dsf.jst.declaration.JstPackage;
import org.ebayopensource.dsf.jst.ts.JstTypeSpaceMgr;
import org.ebayopensource.dsf.jstojava.translator.robust.completion.JstCommentCompletion;
import org.ebayopensource.dsf.ts.ITypeSpace;
import org.ebayopensource.vjo.tool.codecompletion.IVjoCcAdvisor;
import org.ebayopensource.vjo.tool.codecompletion.StringUtils;
import org.ebayopensource.vjo.tool.codecompletion.VjoCcCtx;
import org.ebayopensource.vjo.tool.codecompletion.proposaldata.VjoCcStringProposalData;

/**
 * advise all JstTypes in type space example1: needs('<cursor>') example2:
 * function() { xxx<cursor> }
 * 
 * need attributes: 1. ctx.actingToken
 * 
 * 
 * 
 */
public class VjoCcTypeNameAliasProposalAdvisor extends AbstractVjoCcAdvisor implements
		IVjoCcAdvisor {
	public static final String ID = VjoCcTypeNameAliasProposalAdvisor.class.getName();

	private boolean fullNameCheck = true;

	public void advise(VjoCcCtx ctx) {
		JstTypeSpaceMgr tsMrg = ctx.getJstTypeSpaceMgr();
		ITypeSpace<IJstType, IJstNode> tsds = tsMrg.getTypeSpace();
		Map<String,IJstType> aliasMap = tsds.getAllVisibleAliasNames(tsds.getGroup(ctx.getGroupName()));
		Collection<IJstType> types =aliasMap.values();
		IJstType otype = ctx.getActingType();
		boolean fullNameCheck = ctx.ifContainsFullNameCheck();
		String token = ctx.getActingPackageToken();
		Object info = ctx.getInfo(VjoCcCtx.INFO_KEY_IN_TYPE_SCOPE);
		boolean hideSelf = false;
		if (info != null && (info instanceof Boolean)) {
			hideSelf = true;
		}
		List<String> nameList = new ArrayList<String>();
		Iterator<IJstType> it = types.iterator();
		while(it.hasNext()){
			IJstType type = it.next();
			if(type.isFakeType() && !type.isMetaType()){
				continue;
			}
			
			if(!(ctx.getCompletion() instanceof JstCommentCompletion) && type.isMetaType()){
				continue;
			}
			if (nameList.contains(type.getName())) {
				continue;
			}

			// use the current edited content instead of the one in type space
			if (isSameType(type, otype)) {
				if (hideSelf) {
					continue;
				} else {
					type = otype;
				}
			} else {
				// check if hide inner type
				if (ctx.containsFieldAdvisors()) {
					IJstType calledType = ctx.getCalledType();
					if (calledType != null) {
						String cTypeName = calledType.getName();
						IJstType tempType = calledType;
						if (calledType instanceof IJstRefType) {
							tempType = ((IJstRefType) calledType).getReferencedNode();
							cTypeName = tempType
									.getName();
						}
						if (type.getName() == null
								|| (type.getName().contains(cTypeName) && type.getParentNode() == tempType)) {
							continue;
						}
					}
				}
			}
			if (!mtypeCheck(ctx, type)) {
				continue;
			}
			String sTypeName = type.getAliasTypeName();
			if (VjoCcAdvisorConstances.UNEXIST_TYPES.contains(sTypeName)) {
				continue;
			}
			if (StringUtils.isBlankOrEmpty(sTypeName)) {
				continue;
			}
			boolean exactMatch = exactMatch(sTypeName, token);
			boolean basicMatch = exactMatch;
			if (!exactMatch) {
				basicMatch = basicMatch(sTypeName, token);
			}
			if (exactMatch || basicMatch) {
				VjoCcStringProposalData data = new VjoCcStringProposalData(type.getAliasTypeName(),ctx,VjoCcTypeNameAliasProposalAdvisor.ID);
				data.setAccurateMatch(false);
				ctx.getReporter().addPropsal(data);
				nameList.add(type.getAliasTypeName());
			} else {
				if (fullNameCheck && isInPackage(type.getName(), token)) {
					VjoCcStringProposalData data = new VjoCcStringProposalData(type.getAliasTypeName(),ctx,VjoCcTypeNameAliasProposalAdvisor.ID);
					data.setAccurateMatch(false);
					ctx.getReporter().addPropsal(data);
					nameList.add(type.getAliasTypeName());
				}
			}
		}

	}

	private boolean isInPackage(String typeName, String packageName) {
		if (!typeName.startsWith(packageName)) {
			return false;
		}
		String s = typeName.substring(packageName.length());
		return s != null && s.indexOf(".") == -1;
	}

	private boolean mtypeCheck(VjoCcCtx ctx, IJstType type) {
		return ctx.isMtypeEabled() || !type.isMixin();
	}

	private boolean isSameType(IJstType type, IJstType otype) {
		if (type == null || otype == null) {
			return false;
		}
		String oname = otype.getName();
		String name = type.getName();
		if (!StringUtils.compare(oname, name)) {
			return false;
		}

		String ogroupName = null;
		JstPackage pck = otype.getPackage();
		if (pck != null) {
			ogroupName = pck.getGroupName();
		}
		String groupName = type.getPackage().getGroupName();
		return StringUtils.compare(ogroupName, groupName);
	}
	

	/**
	 * if false, when adivse, only compare the token and the simple typename if
	 * true, when adivse, need compare the token with simple typename and full
	 * type name(package name + class simple name). true has the similar feature
	 * with the package advisor.
	 * 
	 * @return
	 */
	protected boolean ifContainsFullNameCheck() {
		return fullNameCheck;
	}

}
