/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsgen.shared.validation.vjo.util;

import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoValidationCtx;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.symbol.EVjoSymbolType;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.symbol.IVjoSymbol;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.symbol.VjoSymbolTable;
import org.ebayopensource.dsf.jst.IJstType;

public class JstTypeToPackageUtil {

	public static IJstType lookUpPackage(VjoValidationCtx ctx, IJstType type){
		final String typeName = type.getName();
		final String[] packageNames = typeName != null ? typeName.split("\\.") : new String[0];
		final VjoSymbolTable symbolTable = ctx.getSymbolTable();
		
		boolean isGlobal = true;
		IJstType parentPackage = ctx.getGlobal();
		for(int i = 0, len = packageNames.length - 1; i < len; i++){
			if(parentPackage == null){
				return null;
			}
			IVjoSymbol childPackageSymbol = symbolTable.getSymbolInScope(parentPackage, packageNames[i], isGlobal ? EVjoSymbolType.LOCAL_VARIABLE : EVjoSymbolType.INSTANCE_VARIABLE);
			if(childPackageSymbol == null){
				return null;
			}
			else{
				parentPackage = childPackageSymbol.getAssignedType();
			}
			isGlobal = false;
		}
		return parentPackage;
	}
}
