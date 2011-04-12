/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.translate.post;

import org.ebayopensource.dsf.javatojs.translate.TranslateCtx;
import org.ebayopensource.dsf.javatojs.translate.config.MethodKey;
import org.ebayopensource.dsf.javatojs.translate.custom.CustomInfo;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.declaration.JstMethod;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.dsf.jst.traversal.JstVisitorAdapter;

public class MethodPostTranslationVisitor extends JstVisitorAdapter {

	public boolean visit(IJstNode node){
		
		if (!(node instanceof JstMethod) || !(node.getOwnerType() instanceof JstType)){
			return true;
		}
		
		TranslateCtx ctx = TranslateCtx.ctx();
		JstType type = (JstType)node.getOwnerType();
		JstMethod mtd = (JstMethod)node;
		String name = mtd.getName().getName();
		MethodKey mtdKey = MethodKey.genMethodKey(mtd);
		CustomInfo cInfo = ctx.getTranslateInfo(type).getMethodCustomInfo(mtdKey);
		if (!cInfo.isNone()){
			type.removeMethod(name, mtd.isStatic());
		}
		else if (cInfo.getName() != null && !cInfo.isMappedToJS() && !cInfo.isMappedToVJO()){
			mtd.getName().setName(cInfo.getName());
		}
		
		return false;
	}
}
