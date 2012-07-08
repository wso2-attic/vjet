/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.translator;

import org.ebayopensource.dsf.jsgen.shared.ids.ScopeIds;
import org.ebayopensource.dsf.jst.declaration.JstCache;
import org.ebayopensource.dsf.jst.declaration.JstObjectLiteralType;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.eclipse.mod.wst.jsdt.core.ast.IObjectLiteralField;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.Expression;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.ObjectLiteral;

public class OptionsTranslator extends BasePropsProtosTranslator {
	public OptionsTranslator(TranslateCtx ctx) {
		super(ctx);
		type = ScopeIds.OPTIONS;
	}
	
	@Override
	public void process(Expression expr, JstType jstType) {
		
		if(expr instanceof ObjectLiteral){
			ObjectLiteral objLit = (ObjectLiteral)expr;
			// process NV which start with alias::
			if(objLit.getFields()==null){
				return;
			}
			for (IObjectLiteralField field : objLit.getFields()) {
				String fieldName = field.getFieldName().toString();
				fieldName = fieldName.replace("\"", "");
				fieldName = fieldName.replace("\'", "");
				
				if(fieldName.equals("singleton")){
					jstType.setSingleton(Boolean.valueOf(field.getInitializer().toString()));
				}
				
				if(fieldName.equals("alias")){
					String fieldValue  = field.getInitializer().toString();
					fieldValue = fieldValue.replace("\"", "");
					fieldValue = fieldValue.replace("\'", "");
					fieldValue = fieldValue.replace("::", ".");
					
					JstObjectLiteralType jstObjectLiteralType = JstCache.getInstance().getAliasType(fieldValue, false);
					
					jstObjectLiteralType = (JstObjectLiteralType)jstType.getOType("cfg");
					if(jstObjectLiteralType!=null){
						jstObjectLiteralType.setAliasTypeName(fieldValue);
						JstCache.getInstance().addAliasType(fieldValue, jstObjectLiteralType);
					}
					
				}
				
//				String fieldName = field.getFieldName().toString();
//				fieldName = fieldName.replace("\"", "");
//				fieldName = fieldName.replace("\'", "");
//				if(fieldName.startsWith("alias")){
//					String fieldValue  = field.getInitializer().toString();
//					fieldValue = fieldValue.replace("\"", "");
//					fieldValue = fieldValue.replace("\'", "");
//					fieldValue = fieldValue.replace("::", ".");
//					String alias = fieldName.substring(fieldName.indexOf("alias::")+7);
//					JstType otype = JstCache.getInstance().getType(fieldValue);
//					
//				
//					if(otype != null && otype instanceof JstObjectLiteralType){
//						JstObjectLiteralType aliasType = JstCache.getInstance().getAliasType(fieldName, false);
//						if(aliasType!=null){
//							aliasType = (JstObjectLiteralType)otype;
//						}else{
//							
//							JstCache.getInstance().addAliasType(alias, (JstObjectLiteralType)otype);
//						}
//					}else{
//						
//						 JstCache.getInstance().getAliasType(alias, true);
//						
//						JstCache.getInstance().createAliasPlaceHolder(alias, fieldValue);
//						
//					}
//				}
//				
			}
		}
		
		super.process(expr, jstType);
	}
}
