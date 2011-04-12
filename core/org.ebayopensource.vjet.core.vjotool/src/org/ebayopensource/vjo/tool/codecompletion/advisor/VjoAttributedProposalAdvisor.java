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
import java.util.List;

import org.ebayopensource.dsf.jst.IJstGlobalVar;
import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstProperty;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstVjoBaseProperty;
import org.ebayopensource.dsf.jst.declaration.JstVjoProperty;
import org.ebayopensource.dsf.jst.ts.JstTypeSpaceMgr;
import org.ebayopensource.dsf.ts.ITypeSpace;
import org.ebayopensource.dsf.ts.type.TypeName;
import org.ebayopensource.vjo.tool.codecompletion.IVjoCcAdvisor;
import org.ebayopensource.vjo.tool.codecompletion.VjoCcCtx;

/**
 * Advising attributes after the <ATTRIBUTOR>?<COLON><COLON>?
 * 
 * 
 * 
 */
public class VjoAttributedProposalAdvisor extends AbstractVjoCcAdvisor
		implements IVjoCcAdvisor {
	public static final String ID = VjoAttributedProposalAdvisor.class
			.getName();

	public void advise(VjoCcCtx ctx) {
		final String token = ctx.getActingPackageToken();
		final String attributorTypeName = getAttributorTypeName(token);
		
		if (attributorTypeName == null) {// error case
//			propose nothing
		} else if (attributorTypeName.length() == 0) {// global case
			proposeGlobalVars(ctx, isStaticAccess(token));
		} else {
			proposePropertiesAndMethodsUnderAttributor(ctx, token, attributorTypeName);
		}
	}

	private void proposeGlobalVars(VjoCcCtx ctx, final boolean staticAccess) {
		final List<IJstNode> vars = ctx.getJstTypeSpaceMgr().getQueryExecutor()
				.getAllGlobalVars(ctx.getGroupName());
		for (Iterator<IJstNode> it = vars.iterator();it.hasNext();) {
			IJstNode node = it.next();
			if (node instanceof IJstMethod) {
				appendData(ctx, node, staticAccess);
			}

			if (node instanceof IJstProperty) {
				appendData(ctx, node, staticAccess);
			}
			if (node instanceof IJstGlobalVar) {
				IJstGlobalVar jsvar = (IJstGlobalVar) node;
				if (jsvar.getScopeForGlobal() == null) {
					if (jsvar.isFunc()) {
						appendData(ctx, jsvar.getFunction(), staticAccess);
					} 
					else {
						appendData(ctx, jsvar.getProperty(), staticAccess);
					}
				}
			}
		}
	}

	private void proposePropertiesAndMethodsUnderAttributor(
			final VjoCcCtx ctx,
			final String token, 
			final String attributorTypeName) {
		final ITypeSpace<IJstType, IJstNode> typeSpace = ctx
				.getJstTypeSpaceMgr().getTypeSpace();
		IJstType actingType = ctx
				.getActingType();
		
		if(actingType==null){
			return;
		}
		
		final List<IJstType> availableDependencies = typeSpace
				.getAllDependencies(new TypeName(ctx.getGroupName(), actingType.getName()));

		final String attributeName = getAttributeName(token);
		final boolean staticAccess = isStaticAccess(token);
		boolean found = false;
		for (IJstType available : availableDependencies) {
			if (available.getName().equals(attributorTypeName)
					|| available.getSimpleName().equals(attributorTypeName)) {
				proposePropertiesAndMethodsOfType(ctx, staticAccess, available,
						attributeName);
				found = true;
			}
		}
		
		if(!found){
			//check if it's a global type
			IJstType globalType = ctx.getJstTypeSpaceMgr().getQueryExecutor().findType(new TypeName(JstTypeSpaceMgr.JS_NATIVE_GRP, attributorTypeName));
			if(globalType == null){
				globalType = ctx.getJstTypeSpaceMgr().getQueryExecutor().findType(new TypeName(JstTypeSpaceMgr.JS_BROWSER_GRP, attributorTypeName));
			}
			if(globalType == null){
				globalType = ctx.getJstTypeSpaceMgr().getQueryExecutor().findGlobalType(null, attributorTypeName);
			}
			if(globalType != null){
				proposePropertiesAndMethodsOfType(ctx, staticAccess, (IJstType)globalType, attributeName);
			}
		}
	}

	private void proposePropertiesAndMethodsOfType(final VjoCcCtx ctx,
			final boolean staticAccess, IJstType available,
			final String attributeName) {
		for (IJstProperty pty : available.getAllPossibleProperties(
				staticAccess, false)) {
			if (pty instanceof JstVjoProperty
					|| pty instanceof JstVjoBaseProperty) {
				continue;
			} else if (pty.getName().getName()
					.startsWith(attributeName)) {
				appendData(ctx, pty);
			}
		}
		for (IJstProperty pty : available.getAllPossibleProperties(
				!staticAccess, false)) {
			if (pty instanceof JstVjoProperty
					|| pty instanceof JstVjoBaseProperty) {
				continue;
			} else if (pty.getName().getName()
					.startsWith(attributeName)) {
				appendData(ctx, pty);
			}
		}
		for (IJstMethod mtd : available.getMethods(staticAccess, false)) {
			if (mtd.getName().getName().startsWith(attributeName)) {
				if (!mtd.isDispatcher()) {
					appendData(ctx, mtd);
				} else {
					for (IJstMethod overload : mtd.getOverloaded()) {
						appendData(ctx, overload);
					}
				}
			}
		}
		for (IJstMethod mtd : available.getMethods(!staticAccess, false)) {
			if (mtd.getName().getName().startsWith(attributeName)) {
				if (!mtd.isDispatcher()) {
					appendData(ctx, mtd);
				} else {
					for (IJstMethod overload : mtd.getOverloaded()) {
						appendData(ctx, overload);
					}
				}
			}
		}
	}

	private String getAttributorTypeName(final String token) {
		if (token == null) {
			return null;
		}

		final int colonIndex = token.indexOf(':');
		if (colonIndex >= 0) {
			return token.substring(0, colonIndex);
		}
		return token;
	}

	private String getAttributeName(final String token) {
		if (token == null) {
			return null;
		}

		final int colonIndex = token.lastIndexOf(':');
		if (colonIndex >= 0) {
			if (colonIndex + 1 == token.length()) {
				return "";
			}
			return token.substring(colonIndex + 1);
		}
		return token;
	}
	
	private boolean isStaticAccess(final String token) {
		if (token == null) {
			return false;
		}

		return token.indexOf("::") >= 0;
	}

}
