/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsgen.shared.validation.vjo.util;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoValidationCtx;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.symbol.EVjoSymbolType;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.symbol.IVjoSymbol;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.symbol.VjoSymbol;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.symbol.VjoSymbolTable;
import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstProperty;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstFunctionRefType;
import org.ebayopensource.dsf.jst.declaration.JstObjectLiteralType;
import org.ebayopensource.dsf.jst.ts.JstTypeSpaceMgr;
import org.ebayopensource.dsf.jst.util.JstTypeHelper;
import org.ebayopensource.dsf.ts.group.IGroup;
import org.ebayopensource.dsf.ts.group.GroupSymbolMapTable.GlobalSymbolMapEntry;

public class JstGlobalScopeUtil {

	public static final IJstType getGlobal(final VjoValidationCtx ctx, final JstTypeSpaceMgr typeSpaceMgr){
		final IJstType global = new JstObjectLiteralType(GLOBAL_SCOPE);
		ctx.setGlobal(global);
		
		//load global types into symbol table
//		if(typeSpaceMgr != null && typeSpaceMgr.isInitialized()){
//			final VjoSymbolTable symbolTable = ctx.getSymbolTable();
//			IJstType vjoNativeType = typeSpaceMgr.getQueryExecutor().findType(new TypeName(LibManager.VJO_BASE_LIB_NAME, VJO_TYPE));
//			if(vjoNativeType == null){
//				return global;
//			}
//			else{
////				vjoNativeType = new JstTypeRefType(vjoNativeType);
//			}
//
//			updateGlobalWithGroup(ctx, global, vjoNativeType, LibManager.JAVA_PRIMITIVE_LIB_NAME, typeSpaceMgr);
//			updateGlobalWithGroup(ctx, global, vjoNativeType, LibManager.VJO_BASE_LIB_NAME, typeSpaceMgr);
//			updateGlobalWithGroup(ctx, global, vjoNativeType, LibManager.VJO_JAVA_LIB_NAME, typeSpaceMgr);
//			updateGlobalWithGroup(ctx, global, vjoNativeType, LibManager.JS_NATIVE_GLOBAL_LIB_NAME, typeSpaceMgr);
//			updateGlobalWithGroup(ctx, global, vjoNativeType, LibManager.JS_NATIVE_LIB_NAME, typeSpaceMgr);
//
//			// Add methods and props from global scope
//			addMethodsToScope(global, symbolTable, typeSpaceMgr.getQueryExecutor().getAllGlobalMethods());
//			addPropsToScope(global, symbolTable, typeSpaceMgr.getQueryExecutor().getAllGlobalProperties());
//			addTypesToScope(global, symbolTable, typeSpaceMgr.getQueryExecutor().getAllGlobalTypes(), ctx);
//		}
		return global;
	}
	
	private static void updateGlobalWithGroup(final VjoValidationCtx ctx,
			final IJstType global,
			final IJstType vjoNativeType,
			final String groupName, 
			final JstTypeSpaceMgr typeSpaceMgr){

		final Set<IJstType> dupProtect = new HashSet<IJstType>();
		final IGroup<IJstType> group = typeSpaceMgr.getTypeSpace().getGroup(groupName);
		if(group == null){
			return;
		}
		
		final VjoSymbolTable symbolTable = ctx.getSymbolTable();
		for(Iterator<Map.Entry<String, IJstType>> it = group.getEntities().entrySet().iterator(); it.hasNext();){
			final Map.Entry<String, IJstType> entry = it.next();
			final String nativeSymbolName = entry.getKey();
			IJstType nativeType = entry.getValue();
		
			if(nativeType != null && !dupProtect.contains(nativeType)){
				dupProtect.add(nativeType);
				
				IVjoSymbol globalTypeSymbol = new VjoSymbol();
				globalTypeSymbol.setName(nativeSymbolName);
				globalTypeSymbol.setDeclareType(JstTypeHelper.getJstTypeRefType(nativeType));
				globalTypeSymbol.setStaticReference(true);
				globalTypeSymbol.setSymbolType(EVjoSymbolType.LOCAL_VARIABLE);
				symbolTable.addSymbolInScope(global, globalTypeSymbol);
				
				JstTypeSymbolLoadUtil.loadCompleteJstTypeSymbols(ctx, nativeType);
				
				//load global functions & properties from window
				if(WINDOW_TYPE.equals(nativeSymbolName)  || GLOBAL_TYPE.equals(nativeSymbolName)){
					//add window symbol
					IVjoSymbol windowSymbol = new VjoSymbol();
					windowSymbol.setName(WINDOW_SCOPE);
					windowSymbol.setDeclareType(nativeType);
					windowSymbol.setSymbolType(EVjoSymbolType.LOCAL_VARIABLE);
					windowSymbol.setStaticReference(false);
					if(symbolTable.getSymbolInScope(global, WINDOW_SCOPE, EVjoSymbolType.LOCAL_VARIABLE) == null) {
						symbolTable.addSymbolInScope(global, windowSymbol);
					}
				}
//				else if(FUNCTION_TYPE.equals(nativeSymbolName)){
//					TypeCheckUtil.setFunctionNativeType(nativeType);
//				}
//				else if(OBJECT_TYPE.equals(nativeSymbolName)){
//					TypeCheckUtil.setObjectNativeType(nativeType);
//				}
				else if(HTMLIMAGE_ELEMENT_TYPE.equals(nativeSymbolName)){
					IVjoSymbol imageSymbol = new VjoSymbol();
					imageSymbol.setName(IMAGE_SCOPE);
					imageSymbol.setDeclareType(JstTypeHelper.getJstTypeRefType(nativeType));
					imageSymbol.setSymbolType(EVjoSymbolType.LOCAL_VARIABLE);
					imageSymbol.setStaticReference(true);
					symbolTable.addSymbolInScope(global, imageSymbol);
				}
				else if(HTMLOPTION_ELEMENT_TYPE.equals(nativeSymbolName)){
					IVjoSymbol optionSymbol = new VjoSymbol();
					optionSymbol.setName(OPTION_SCOPE);
					optionSymbol.setDeclareType(JstTypeHelper.getJstTypeRefType(nativeType));
					optionSymbol.setSymbolType(EVjoSymbolType.LOCAL_VARIABLE);
					optionSymbol.setStaticReference(true);
					if(symbolTable.getSymbolInScope(global, OPTION_SCOPE, EVjoSymbolType.LOCAL_VARIABLE) == null) {
						symbolTable.addSymbolInScope(global, optionSymbol);
					}
				}
				
				if(WINDOW_TYPE.equals(nativeSymbolName) || GLOBAL_TYPE.equals(nativeSymbolName)){
					for(IJstMethod nativeMtd : nativeType.getMethods()){
						IVjoSymbol methodSymbol = new VjoSymbol();
						methodSymbol.setName(((IJstMethod)nativeMtd).getName().getName());
						methodSymbol.setDeclareType(new JstFunctionRefType((IJstMethod)nativeMtd));
						methodSymbol.setStaticReference(false);
						methodSymbol.setSymbolType(EVjoSymbolType.LOCAL_FUNCTION);
						symbolTable.addSymbolInScope(global, methodSymbol);
					}
					
					for(IJstProperty nativePty : nativeType.getProperties()){
						IVjoSymbol propertySymbol = new VjoSymbol();
						propertySymbol.setName(((IJstProperty)nativePty).getName().getName());
						propertySymbol.setDeclareType(((IJstProperty)nativePty).getType());
						propertySymbol.setStaticReference(false);
						propertySymbol.setSymbolType(EVjoSymbolType.LOCAL_VARIABLE);
						symbolTable.addSymbolInScope(global, propertySymbol);
					}
				}
				
				if(VJO_TYPE.equals(nativeSymbolName)){
					nativeType = vjoNativeType;
					for(IJstType importedType : nativeType.getImports()){
						JstTypeSymbolLoadUtil.loadCompleteJstTypeSymbols(ctx, importedType);
					}
					//should have been fixed in the symbol loading inner type logic, @see JstTypeSymbolLoadUtil#symbolizeInnerClass
					//load sysout, syserr
				}
				else if(VJO_OBJECT_TYPE.equals(nativeSymbolName)){
					IVjoSymbol vjoObjectSymbol = new VjoSymbol();
					vjoObjectSymbol.setName(OBJECT_TYPE);
					vjoObjectSymbol.setDeclareType(JstTypeHelper.getJstTypeRefType(nativeType));
					vjoObjectSymbol.setStaticReference(true);
					vjoObjectSymbol.setSymbolType(EVjoSymbolType.STATIC_VARIABLE);
					symbolTable.addSymbolInScope(vjoNativeType, vjoObjectSymbol);
				}
				else if(VJO_ENUM_TYPE.equals(nativeSymbolName)){
					IVjoSymbol vjoEnumSymbol = new VjoSymbol();
					vjoEnumSymbol.setName(ENUM_SCOPE);
					vjoEnumSymbol.setDeclareType(JstTypeHelper.getJstTypeRefType(nativeType));
					vjoEnumSymbol.setStaticReference(true);
					vjoEnumSymbol.setSymbolType(EVjoSymbolType.STATIC_VARIABLE);
					symbolTable.addSymbolInScope(vjoNativeType, vjoEnumSymbol);
				}
				else if(VJO_CLASS_TYPE.equals(nativeSymbolName)){
					IVjoSymbol vjoClassSymbol = new VjoSymbol();
					vjoClassSymbol.setName(CLASS_SCOPE);
					vjoClassSymbol.setDeclareType(JstTypeHelper.getJstTypeRefType(nativeType));
					vjoClassSymbol.setStaticReference(true);
					vjoClassSymbol.setSymbolType(EVjoSymbolType.STATIC_VARIABLE);
					symbolTable.addSymbolInScope(vjoNativeType, vjoClassSymbol);
				}
			}
		}
	}

	private static void addPropsToScope(final IJstType global,
			final VjoSymbolTable symbolTable, List<? extends IJstNode> props) {
		for(IJstNode nativePty : props){
			if(nativePty instanceof IJstProperty){
				IVjoSymbol propertySymbol = new VjoSymbol();
				propertySymbol.setName(((IJstProperty)nativePty).getName().getName());
				propertySymbol.setDeclareType(((IJstProperty)nativePty).getType());
				propertySymbol.setStaticReference(false);
				propertySymbol.setSymbolType(EVjoSymbolType.STATIC_VARIABLE);
				symbolTable.addSymbolInScope(global, propertySymbol);
			}
		}
	}
	
	private static void addTypesToScope(final IJstType global,
			final VjoSymbolTable symbolTable, List<GlobalSymbolMapEntry> props, VjoValidationCtx ctx) {
		for(GlobalSymbolMapEntry nativePty : props){
			IVjoSymbol typeSymbol = new VjoSymbol();
			typeSymbol.setName(nativePty.getName());
			IJstType node = (IJstType)nativePty.getNode();
			typeSymbol.setDeclareType(node);
			typeSymbol.setStaticReference(false);
			typeSymbol.setSymbolType(EVjoSymbolType.LOCAL_VARIABLE);
			symbolTable.addSymbolInScope(global, typeSymbol);
			
			JstTypeSymbolLoadUtil.loadCompleteJstTypeSymbols(ctx, node);
		}
	}

	private static void addMethodsToScope(final IJstType global,
			final VjoSymbolTable symbolTable, List<? extends IJstNode> methods) {
		for(IJstNode nativeMtd : methods){
			if(nativeMtd instanceof IJstMethod){
				IVjoSymbol methodSymbol = new VjoSymbol();
				IJstMethod method = (IJstMethod)nativeMtd;
				methodSymbol.setName(((IJstMethod)nativeMtd).getName().getName());
				methodSymbol.setDeclareType(new JstFunctionRefType((IJstMethod)nativeMtd));
				methodSymbol.setStaticReference(method.isStatic());
				methodSymbol.setSymbolType(EVjoSymbolType.LOCAL_FUNCTION);
				symbolTable.addSymbolInScope(global, methodSymbol);
			}
		}
	}


	private static final String CLASS_SCOPE = "Class";
	private static final String ENUM_SCOPE = "Enum";
	private static final String VJO_CLASS_TYPE = "vjo.Class";
	private static final String VJO_ENUM_TYPE = "vjo.Enum";
	private static final String VJO_OBJECT_TYPE = "vjo.Object";
//	private static final String SYSERR_TYPE = "syserr";
//	private static final String SYSOUT_TYPE = "sysout";
	private static final String VJO_TYPE = "vjo";
	private static final String OPTION_SCOPE = "Option";
	private static final String HTMLOPTION_ELEMENT_TYPE = "HTMLOptionElement";
	private static final String IMAGE_SCOPE = "Image";
	private static final String HTMLIMAGE_ELEMENT_TYPE = "HTMLImageElement";
	private static final String OBJECT_TYPE = "Object";
	private static final String FUNCTION_TYPE = "Function";
	private static final String WINDOW_SCOPE = "window";
	private static final String GLOBAL_TYPE = "Global";
	private static final String WINDOW_TYPE = "Window";
	private static final String GLOBAL_SCOPE = "global";
}
