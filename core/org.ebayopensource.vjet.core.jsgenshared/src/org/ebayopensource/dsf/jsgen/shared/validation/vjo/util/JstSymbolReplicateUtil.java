/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsgen.shared.validation.vjo.util;

import java.util.List;

import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoValidationCtx;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.symbol.EVjoSymbolType;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.symbol.IVjoSymbol;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.symbol.VjoSymbol;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.symbol.VjoSymbolTable;
import org.ebayopensource.dsf.jst.IJstType;

public class JstSymbolReplicateUtil {

	/**
	 * <p>
	 * 	replicate all symbols associated with one scope to another
	 * </p>
	 * @param ctx
	 * @param fromType
	 * @param toType
	 */
	public static void replicate(final VjoValidationCtx ctx,
			final IJstType fromType,
			final IJstType toType){
		
		replicate(ctx, fromType, toType, EVjoSymbolType.LOCAL_VARIABLE);
		replicate(ctx, fromType, toType, EVjoSymbolType.INSTANCE_VARIABLE);
		replicate(ctx, fromType, toType, EVjoSymbolType.STATIC_VARIABLE);
	}
	
	/**
	 * <p>
	 * replicate symbols associated with one scope to another
	 * only the specified symbol types will be replicated
	 * </p>
	 * @param ctx
	 * @param fromType
	 * @param toType
	 * @param symbolType
	 */
	public static void replicate(final VjoValidationCtx ctx,
			final IJstType fromType,
			final IJstType toType,
			final EVjoSymbolType symbolType){
		
		final VjoSymbolTable symbolTable = ctx.getSymbolTable();
		final List<IVjoSymbol> symbols2Replicate = symbolTable.getSymbolsInScope(fromType, symbolType);
		
		for(IVjoSymbol symbol2Replicate: symbols2Replicate){
			if(symbols2Replicate == null){
				continue;
			}
			//replicate the symbol only when it doesn't exist already
			IVjoSymbol symbolReplicated = symbolTable.getSymbolInScope(toType, symbol2Replicate.getName(), symbol2Replicate.getSymbolType());
			if(symbolReplicated == null){
				symbolReplicated = new VjoSymbol();
				symbolReplicated.setName(symbol2Replicate.getName());
				symbolReplicated.setDeclareType(symbol2Replicate.getDeclareType());
				symbolReplicated.setSymbolType(symbol2Replicate.getSymbolType());
				symbolReplicated.setDeclareNode(symbol2Replicate.getDeclareNode());
				symbolReplicated.setAssignedType(symbol2Replicate.getAssignedType());
				symbolReplicated.setStaticReference(symbol2Replicate.isStaticReference());
				symbolReplicated.setVisible(symbol2Replicate.isVisible());
			
				symbolTable.addSymbolInScope(toType, symbolReplicated);
			}
		}
	}
	
	/**
	 * 
	 * @param ctx
	 * @param fromType
	 * @param toType
	 * @param symbolType
	 * @param modifiedSymbolType
	 * <p>
	 * replicate symbols associated with one scope to another scope and have its symbol type modified
	 * </p>
	 */
	public static void replicateAndModify(final VjoValidationCtx ctx,
			final IJstType fromType,
			final IJstType toType,
			final EVjoSymbolType symbolType,
			final EVjoSymbolType modifiedSymbolType){
		
		final VjoSymbolTable symbolTable = ctx.getSymbolTable();
		final List<IVjoSymbol> symbols2Replicate = symbolTable.getSymbolsInScope(fromType, symbolType);
		
		for(IVjoSymbol symbol2Replicate: symbols2Replicate){
			if(symbols2Replicate == null){
				continue;
			}
			IVjoSymbol symbolReplicated = symbolTable.getSymbolInScope(toType, symbol2Replicate.getName(), modifiedSymbolType);
			if(symbolReplicated == null){
				symbolReplicated = new VjoSymbol();
				symbolReplicated.setName(symbol2Replicate.getName());
				symbolReplicated.setDeclareType(symbol2Replicate.getDeclareType());
				symbolReplicated.setSymbolType(modifiedSymbolType);
				symbolReplicated.setDeclareNode(symbol2Replicate.getDeclareNode());
				symbolReplicated.setAssignedType(symbol2Replicate.getAssignedType());
				symbolReplicated.setStaticReference(symbol2Replicate.isStaticReference());
				symbolReplicated.setVisible(symbol2Replicate.isVisible());
			
				symbolTable.addSymbolInScope(toType, symbolReplicated);
			}
		}
	}
}
