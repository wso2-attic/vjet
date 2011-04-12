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
import org.ebayopensource.dsf.javatojs.translate.TranslateInfo;
import org.ebayopensource.dsf.javatojs.translate.config.MethodKey;
import org.ebayopensource.dsf.javatojs.translate.custom.CustomInfo;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.declaration.JstMethod;
import org.ebayopensource.dsf.jst.declaration.JstProperty;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.dsf.jst.term.JstIdentifier;
import org.ebayopensource.dsf.jst.traversal.JstVisitorAdapter;

public class JavaOnlyVisitor extends JstVisitorAdapter {
	
	//
	// Satisfy IJstVisitor
	//
	public boolean visit(IJstNode node){
		
		TranslateCtx ctx = TranslateCtx.ctx();
		
		if (node instanceof JstIdentifier){
			JstIdentifier jstIdentifier = (JstIdentifier)node;
			IJstNode jstBinding = jstIdentifier.getJstBinding();
			if (jstBinding instanceof JstType){
				JstType jstType = (JstType)jstBinding;
				if (jstType != null){
					if(ctx.isJavaOnly(jstType)){
						jstIdentifier.setName(null);
						jstIdentifier.setQualifier(null);
					}
				}
			}
			else if (jstBinding instanceof JstProperty){
				JstProperty jstPty = (JstProperty)jstBinding;
				if (jstPty != null && jstPty.getOwnerType() instanceof JstType){
					TranslateInfo tI = ctx.getTranslateInfo((JstType)jstPty.getOwnerType());
					CustomInfo cI = tI.getFieldCustomInfo(jstPty.getName().getName());
					if(cI.isJavaOnly()){
						jstIdentifier.setName(null);
					}
				}
			}
			else if (jstBinding instanceof JstMethod){
				JstMethod jstMtd = (JstMethod)jstBinding;
				if (jstMtd != null && jstMtd.getOwnerType() instanceof JstType){
					TranslateInfo tI = ctx.getTranslateInfo((JstType)jstMtd.getOwnerType());
					CustomInfo cI = tI.getMethodCustomInfo(MethodKey.genMethodKey(jstMtd));
					if(cI.isJavaOnly()){
						jstIdentifier.setName(null);
					}
				}
			}
		}
		
		return true;
	}
}
