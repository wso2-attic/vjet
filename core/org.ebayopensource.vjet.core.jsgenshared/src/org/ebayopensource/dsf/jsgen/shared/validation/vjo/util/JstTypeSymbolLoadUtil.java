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
import java.util.Set;

import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoValidationCtx;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoValidationRuntimeException;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.VjoConstants;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.symbol.EVjoSymbolType;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.symbol.IVjoSymbol;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.symbol.VjoSymbol;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.symbol.VjoSymbolTable;
import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstProperty;
import org.ebayopensource.dsf.jst.IJstRefType;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.IJstTypeReference;
import org.ebayopensource.dsf.jst.declaration.JstCache;
import org.ebayopensource.dsf.jst.declaration.JstFunctionRefType;
import org.ebayopensource.dsf.jst.declaration.JstMethod;
import org.ebayopensource.dsf.jst.declaration.JstModifiers;
import org.ebayopensource.dsf.jst.util.JstTypeHelper;

public class JstTypeSymbolLoadUtil {
	
	/**
	 * load all private|protected|public properties & methods of the jstType
	 * load all none-private properties & methods of its parent types & its mixin types
	 * @param ctx
	 * @param jstType
	 */
	public static void loadCompleteJstTypeSymbols(final VjoValidationCtx ctx, final IJstType jstType){
		final IJstType typeSpaceType = ctx.getTypeSpaceType(jstType);

		if(hasCircularInheritanceChain(ctx, typeSpaceType, new HashSet<IJstType>())){
			throw new VjoValidationRuntimeException(new IllegalArgumentException("circular inheritance chain detected: " + typeSpaceType.getName()));
		}
		
		recursiveLoadCompleteJstTypesSymbols(ctx, typeSpaceType, typeSpaceType, new HashSet<DuplicateLoadProtectionKey>());
	}
	
	/**
	 * unload all private|protected properties & methods of the jstType
	 * unload all protected properties & methods of its parent types & its mixin types
	 * @param ctx
	 * @param jstType
	 */
	public static void unloadCompleteJstTypeSymbols(final VjoValidationCtx ctx, final IJstType jstType){
		final IJstType typeSpaceType = ctx.getTypeSpaceType(jstType);
		

		if(hasCircularInheritanceChain(ctx, typeSpaceType, new HashSet<IJstType>())){
			throw new VjoValidationRuntimeException(new IllegalArgumentException("circular inheritance chain detected: " + typeSpaceType.getName()));
		}
		
		recursiveUnloadCompleteJstTypesSymbols(ctx, typeSpaceType, typeSpaceType, new HashSet<IJstType>());
	}
	
	/**
	 * recursively load all public properties & methods of the jstType, its parent tyeps and its mixin types
	 * @param ctx
	 * @param jstType
	 * @param modifier
	 */
	public static void loadCompleteJstTypeSymbols(final VjoValidationCtx ctx, final IJstType jstType, int modifier){
		final IJstType typeSpaceType = ctx.getTypeSpaceType(jstType);
		
		if(hasCircularInheritanceChain(ctx, typeSpaceType, new HashSet<IJstType>())){
			throw new VjoValidationRuntimeException(new IllegalArgumentException("circular inheritance chain detected: " + typeSpaceType.getName()));
		}
		
		recursiveLoadCompleteJstTypesSymbols(ctx, typeSpaceType, typeSpaceType, modifier, new HashSet<DuplicateLoadProtectionKey>());
	}
		
	public static void unloadCompleteJstTypeSymbols(final VjoValidationCtx ctx, final IJstType jstType, int modifier){
		final IJstType typeSpaceType = ctx.getTypeSpaceType(jstType);

		if(hasCircularInheritanceChain(ctx, typeSpaceType, new HashSet<IJstType>())){
			throw new VjoValidationRuntimeException(new IllegalArgumentException("circular inheritance chain detected: " + typeSpaceType.getName()));
		}
		
		recursiveLoadCompleteJstTypesSymbols(ctx, typeSpaceType, typeSpaceType, modifier, new HashSet<DuplicateLoadProtectionKey>());
	}
	
	private static boolean hasCircularInheritanceChain(final VjoValidationCtx ctx, final IJstType root, final Set<IJstType> protect){
		final IJstType rootInTypeSpace = ctx.getTypeSpaceType(root);
		
		if(protect.contains(rootInTypeSpace)){
			return true;
		}
		else if(rootInTypeSpace.getExtendsRef().size() == 0){
			return false;
		}
		else{
			protect.add(rootInTypeSpace);
			for(IJstType ext : rootInTypeSpace.getExtends()){
				if(hasCircularInheritanceChain(ctx, ext, protect)){
					return true;
				}
			}
			return false;
		}
	}
	
	private static void recursiveLoadCompleteJstTypesSymbols(final VjoValidationCtx ctx, final IJstType scope, final IJstType jstType, Set<DuplicateLoadProtectionKey> dupProtect){
		final DuplicateLoadProtectionKey dupKey = new DuplicateLoadProtectionKey(scope, jstType, JstModifiers.PUBLIC);
		if(dupProtect.contains(dupKey)){
			return;
		}
		else{
			dupProtect.add(dupKey);
		}
		
		final Set<String> dupNameProtect = new HashSet<String>();
		
		for(IJstType extendType : jstType.getExtends()){
			extendType = ctx.getTypeSpaceType(extendType);
			dupNameProtect.add(extendType.getName());
			
			//bugfix, interface type extending from vjo.Object
			if(jstType.isInterface() && !extendType.isInterface()){
				continue;
			}
			
			recursiveLoadCompleteJstTypesSymbols(ctx, scope, extendType, JstModifiers.PROTECTED & ~JstModifiers.STATIC, dupProtect);
			//bugfix, should adjust parent class's symbol visibilities at the same time
			recursiveLoadCompleteJstTypesSymbols(ctx, extendType, extendType, JstModifiers.PROTECTED | JstModifiers.STATIC, dupProtect);
		}
		
		for(IJstType satisfyType : jstType.getSatisfies()){
			satisfyType = ctx.getTypeSpaceType(satisfyType);
			dupNameProtect.add(satisfyType.getName());
			
			if(!satisfyType.isInterface()){
				continue;
			}
			recursiveLoadCompleteJstTypesSymbols(ctx, scope, satisfyType, JstModifiers.PUBLIC & ~JstModifiers.STATIC, dupProtect);
		}
		
		//bugfix, according to Yitao's clarification, mtype's expects interfaces' symbols could be directly used in mtype
		for(IJstType expectType : jstType.getExpects()){
			expectType = ctx.getTypeSpaceType(expectType);
			dupNameProtect.add(expectType.getName());
			
			if(!expectType.isInterface()){
				continue;
			}
			recursiveLoadCompleteJstTypesSymbols(ctx, scope, expectType, JstModifiers.PUBLIC & ~JstModifiers.STATIC, dupProtect);
		}
		
		for(IJstTypeReference mixinTypeRef : jstType.getMixinsRef()){
			IJstType mixinType = mixinTypeRef.getReferencedType();
			
			if(mixinType != null){
				mixinType = ctx.getTypeSpaceType(mixinType);
				dupNameProtect.add(mixinType.getName());
				recursiveLoadCompleteJstTypesSymbols(ctx, scope, mixinType, JstModifiers.PROTECTED | JstModifiers.STATIC, dupProtect);
			}
		}
		

		//update default access modifiers
		for(IJstType importedType: jstType.getImports()){
			importedType = ctx.getTypeSpaceType(importedType);
			if(dupNameProtect.contains(importedType.getName())){
				continue;
			}
			dupNameProtect.add(importedType.getName());
			
			for(IJstProperty instanceProperty: importedType.getAllPossibleProperties(true, false)){
				if(isDefault(instanceProperty.getModifiers(), instanceProperty)){
					final IVjoSymbol instancePropertySymbol = ctx.getSymbolTable().getSymbolInScope(importedType, instanceProperty.getName().getName(), EVjoSymbolType.INSTANCE_VARIABLE);
					if(instancePropertySymbol != null){
						instancePropertySymbol.setVisible(withinSamePackage(jstType.getOwnerType(), importedType.getOwnerType()));
					}
				}
			}
			
			for(IJstProperty staticProperty: importedType.getAllPossibleProperties(false, false)){
				if(isDefault(staticProperty.getModifiers(), staticProperty)){
					final IVjoSymbol instancePropertySymbol = ctx.getSymbolTable().getSymbolInScope(importedType, staticProperty.getName().getName(), EVjoSymbolType.INSTANCE_VARIABLE);
					if(instancePropertySymbol != null){
						instancePropertySymbol.setVisible(withinSamePackage(jstType.getOwnerType(), importedType.getOwnerType()));
					}
				}
			}
			
			for(IJstMethod instanceMethod: importedType.getMethods(true, false)){
				if(isDefault(instanceMethod.getModifiers(), instanceMethod)){
					final IVjoSymbol instancePropertySymbol = ctx.getSymbolTable().getSymbolInScope(importedType, instanceMethod.getName().getName(), EVjoSymbolType.INSTANCE_VARIABLE);
					if(instancePropertySymbol != null){
						instancePropertySymbol.setVisible(withinSamePackage(jstType.getOwnerType(), importedType.getOwnerType()));
					}
				}
			}

			for(IJstMethod staticMethod: importedType.getMethods(false, false)){
				if(isDefault(staticMethod.getModifiers(), staticMethod)){
					final IVjoSymbol instancePropertySymbol = ctx.getSymbolTable().getSymbolInScope(importedType, staticMethod.getName().getName(), EVjoSymbolType.INSTANCE_VARIABLE);
					if(instancePropertySymbol != null){
						instancePropertySymbol.setVisible(withinSamePackage(jstType.getOwnerType(), importedType.getOwnerType()));
					}
				}
			}
		}
		
		loadSingleJstTypeSymbols(ctx, scope, dupProtect, jstType, JstModifiers.PRIVATE, null);
	}
	
	
	private static void recursiveUnloadCompleteJstTypesSymbols(final VjoValidationCtx ctx, final IJstType scope, final IJstType jstType, Set<IJstType> dupProtect){
		if(dupProtect.contains(jstType)){
			return;
		}
		else{
			dupProtect.add(jstType);
		}
		
		for(IJstType extendType : jstType.getExtends()){
			extendType = ctx.getTypeSpaceType(extendType);
			
			recursiveUnloadCompleteJstTypesSymbols(ctx, scope, extendType, JstModifiers.PROTECTED, dupProtect);
			recursiveUnloadCompleteJstTypesSymbols(ctx, extendType, extendType, JstModifiers.PROTECTED, dupProtect);
		}
		
		for(IJstType satisfyType : jstType.getSatisfies()){
			satisfyType = ctx.getTypeSpaceType(satisfyType);
			
			if(!satisfyType.isInterface()){
				continue;
			}
			recursiveUnloadCompleteJstTypesSymbols(ctx, scope, satisfyType, JstModifiers.PROTECTED, dupProtect);
		}
		
		for(IJstTypeReference mixinTypeRef : jstType.getMixinsRef()){
			IJstType mixinType = mixinTypeRef.getReferencedType();
			if(mixinType != null){
				mixinType = ctx.getTypeSpaceType(mixinType);
				recursiveUnloadCompleteJstTypesSymbols(ctx, scope, mixinType, JstModifiers.PROTECTED, dupProtect);
			}
		}
		
		unloadSingleJstTypeSymbols(ctx, scope, jstType, JstModifiers.PROTECTED);
	}
	
	private static void recursiveLoadCompleteJstTypesSymbols(final VjoValidationCtx ctx, final IJstType scope, final IJstType jstType, int modifier, final Set<DuplicateLoadProtectionKey> dupProtect){
		final DuplicateLoadProtectionKey dupKey = new DuplicateLoadProtectionKey(scope, jstType, JstModifiers.PUBLIC);
		if(dupProtect.contains(dupKey)){
			return;
		}
		else{
			dupProtect.add(dupKey);
		}
		
		final Set<String> dupNameProtect = new HashSet<String>();
		
		for(IJstType extendType : jstType.getExtends()){
			extendType = ctx.getTypeSpaceType(extendType);
			dupNameProtect.add(extendType.getName());

			//bugfix, interface type extending from vjo.Object
			if(jstType.isInterface() && !extendType.isInterface()){
				continue;
			}
			
			recursiveLoadCompleteJstTypesSymbols(ctx, scope, extendType, modifier & ~JstModifiers.STATIC, dupProtect);
			recursiveLoadCompleteJstTypesSymbols(ctx, extendType, extendType, modifier | JstModifiers.STATIC, dupProtect);
		}
		
		for(IJstType satisfyType : jstType.getSatisfies()){
			satisfyType = ctx.getTypeSpaceType(satisfyType);
			dupNameProtect.add(satisfyType.getName());
			
			if(!satisfyType.isInterface()){
				continue;
			}
			recursiveLoadCompleteJstTypesSymbols(ctx, scope, satisfyType, modifier & ~JstModifiers.STATIC, dupProtect);
		}
		
		//bugfix, according to Yitao's clarification, mtype's expects interfaces' symbols could be directly used in mtype
		for(IJstType expectType : jstType.getExpects()){
			expectType = ctx.getTypeSpaceType(expectType);
			dupNameProtect.add(expectType.getName());
			
			if(!expectType.isInterface()){
				continue;
			}
			recursiveLoadCompleteJstTypesSymbols(ctx, scope, expectType, modifier & ~JstModifiers.STATIC, dupProtect);
		}
		
		for(IJstTypeReference mixinTypeRef : jstType.getMixinsRef()){
			IJstType mixinType = mixinTypeRef.getReferencedType();
			if(mixinType != null){
				mixinType = ctx.getTypeSpaceType(mixinType);
				dupNameProtect.add(mixinType.getName());
				recursiveLoadCompleteJstTypesSymbols(ctx, scope, mixinType, modifier | JstModifiers.STATIC, dupProtect);
			}
		}
		
		//update default access modifiers
		for(IJstType importedType: jstType.getImports()){
			importedType = ctx.getTypeSpaceType(importedType);
			if(dupNameProtect.contains(importedType.getName())){
				continue;
			}
			dupNameProtect.add(importedType.getName());
			
			for(IJstProperty instanceProperty: importedType.getAllPossibleProperties(true, false)){
				if(isDefault(instanceProperty.getModifiers(), instanceProperty)){
					final IVjoSymbol instancePropertySymbol = ctx.getSymbolTable().getSymbolInScope(importedType, instanceProperty.getName().getName(), EVjoSymbolType.INSTANCE_VARIABLE);
					if(instancePropertySymbol != null){
						instancePropertySymbol.setVisible(withinSamePackage(jstType.getOwnerType(), importedType.getOwnerType()));
					}
				}
			}
			
			for(IJstProperty staticProperty: importedType.getAllPossibleProperties(false, false)){
				if(isDefault(staticProperty.getModifiers(), staticProperty)){
					final IVjoSymbol instancePropertySymbol = ctx.getSymbolTable().getSymbolInScope(importedType, staticProperty.getName().getName(), EVjoSymbolType.INSTANCE_VARIABLE);
					if(instancePropertySymbol != null){
						instancePropertySymbol.setVisible(withinSamePackage(jstType.getOwnerType(), importedType.getOwnerType()));
					}
				}
			}
			
			for(IJstMethod instanceMethod: importedType.getMethods(true, false)){
				if(isDefault(instanceMethod.getModifiers(), instanceMethod)){
					final IVjoSymbol instancePropertySymbol = ctx.getSymbolTable().getSymbolInScope(importedType, instanceMethod.getName().getName(), EVjoSymbolType.INSTANCE_VARIABLE);
					if(instancePropertySymbol != null){
						instancePropertySymbol.setVisible(withinSamePackage(jstType.getOwnerType(), importedType.getOwnerType()));
					}
				}
			}

			for(IJstMethod staticMethod: importedType.getMethods(false, false)){
				if(isDefault(staticMethod.getModifiers(), staticMethod)){
					final IVjoSymbol instancePropertySymbol = ctx.getSymbolTable().getSymbolInScope(importedType, staticMethod.getName().getName(), EVjoSymbolType.INSTANCE_VARIABLE);
					if(instancePropertySymbol != null){
						instancePropertySymbol.setVisible(withinSamePackage(jstType.getOwnerType(), importedType.getOwnerType()));
					}
				}
			}
			
		}
		
		loadSingleJstTypeSymbols(ctx, scope, dupProtect, jstType, modifier, null);
	}
	
	private static void recursiveUnloadCompleteJstTypesSymbols(final VjoValidationCtx ctx, final IJstType scope, final IJstType jstType, int modifier, Set<IJstType> dupProtect){
		if(dupProtect.contains(jstType)){
			return;
		}
		else{
			dupProtect.add(jstType);
		}
		
		for(IJstType extendType : jstType.getExtends()){
			extendType = ctx.getTypeSpaceType(extendType);
			recursiveUnloadCompleteJstTypesSymbols(ctx, scope, extendType, modifier, dupProtect);
			recursiveUnloadCompleteJstTypesSymbols(ctx, extendType, extendType, modifier, dupProtect);
		}
		
		for(IJstType satisfyType : jstType.getSatisfies()){
			satisfyType = ctx.getTypeSpaceType(satisfyType);
			recursiveUnloadCompleteJstTypesSymbols(ctx, scope, satisfyType, modifier, dupProtect);
		}
		
		for(IJstTypeReference mixinTypeRef : jstType.getMixinsRef()){
			IJstType mixinType = mixinTypeRef.getReferencedType();
			if(mixinType != null){
				mixinType = ctx.getTypeSpaceType(mixinType);
				recursiveUnloadCompleteJstTypesSymbols(ctx, scope, mixinType, modifier, dupProtect);
			}
		}
		
		unloadSingleJstTypeSymbols(ctx, scope, jstType, modifier);
	}
	
	public static void loadSingleJstTypeSymbols(final VjoValidationCtx ctx, final IJstType jstType){
		final IJstType typeSpaceType = ctx.getTypeSpaceType(jstType);
		loadSingleJstTypeSymbols(ctx, typeSpaceType, new HashSet<DuplicateLoadProtectionKey>(), typeSpaceType, JstModifiers.PUBLIC, null);
	}
	
	public static void unloadSingleJstTypeSymbols(final VjoValidationCtx ctx, final IJstType jstType){
		final IJstType typeSpaceType = ctx.getTypeSpaceType(jstType);
		unloadSingleJstTypeSymbols(ctx, typeSpaceType, typeSpaceType, JstModifiers.PUBLIC);
	}
	
	public static void loadSingleJstTypeSymbols(final VjoValidationCtx ctx, final IJstType jstType, int modifier){
		final IJstType typeSpaceType = ctx.getTypeSpaceType(jstType);
		loadSingleJstTypeSymbols(ctx, typeSpaceType, new HashSet<DuplicateLoadProtectionKey>(), typeSpaceType, modifier, null);
	}
	
	public static void unloadSingleJstTypeSymbols(final VjoValidationCtx ctx, final IJstType jstType, int modifier){
		final IJstType typeSpaceType = ctx.getTypeSpaceType(jstType);
		unloadSingleJstTypeSymbols(ctx, typeSpaceType, typeSpaceType, modifier);
	}
	
	private static IJstMethod s_valuesMtd;
	
	public static void loadSingleJstTypeSymbols(final VjoValidationCtx ctx, final IJstNode scope, final Set<DuplicateLoadProtectionKey> dupProtect, final IJstType jstType, int modifier, EVjoSymbolType symbolType){
		final IJstType typeSpaceType = ctx.getTypeSpaceType(jstType);
		
		final JstModifiers jstModifier = new JstModifiers(modifier);
		final VjoSymbolTable symbolTable =  ctx.getSymbolTable();

		//bugfix, enum symbols
		if(typeSpaceType.isEnum()){
			for(IJstProperty enumProperty: typeSpaceType.getEnumValues()){
				symbolizeProperty(scope, typeSpaceType, symbolType, jstModifier,
						ctx, enumProperty);
			}
			if(s_valuesMtd == null){
				final IJstType vjoEnumType = JstCache.getInstance().getType("vjo.Enum");
				if(vjoEnumType != null){
					final IJstMethod valuesMtd = vjoEnumType.getMethod("values", true);
					s_valuesMtd = valuesMtd;
				}
			}
			if(s_valuesMtd != null){
//				s_valuesMtd.getModifiers().setStatic(true);
				symbolizeMethod(typeSpaceType, symbolType, jstModifier, symbolTable, s_valuesMtd);
//				s_valuesMtd.getModifiers().setStatic(false);
			}
		}
		
		//bugfix, loading embedding classes
		for(IJstType innerType : typeSpaceType.getEmbededTypes()){
			recursiveLoadCompleteJstTypesSymbols(ctx, innerType, innerType, JstModifiers.PRIVATE, dupProtect);
			symbolizeInnerClass(scope, typeSpaceType, innerType, jstModifier, symbolTable);
		}
		
		for(IJstProperty jstProperty: typeSpaceType.getProperties()){
			symbolizeProperty(scope, typeSpaceType, symbolType, jstModifier,
					ctx, jstProperty);
		}
		
		for(IJstMethod jstMethod: typeSpaceType.getMethods()){
			symbolizeMethod(scope, symbolType, jstModifier, symbolTable,
					jstMethod);
		}
		
		if(typeSpaceType.getConstructor() != null){
			symbolizeMethod(scope, symbolType, jstModifier, symbolTable,
					typeSpaceType.getConstructor());
		}
		
		symbolizeClazzProperty(ctx, scope, symbolTable);
	}

	private static IJstType s_vjoClazzType;
	protected static void symbolizeClazzProperty(
			final VjoValidationCtx ctx,
			final IJstNode scope,
			final VjoSymbolTable symbolTable) {
		if(s_vjoClazzType == null){
			s_vjoClazzType = JstCache.getInstance().getType("vjo.Class");
			if(s_vjoClazzType instanceof IJstRefType){
				s_vjoClazzType = ((IJstRefType)s_vjoClazzType).getReferencedNode();
			}
		}
		
		//support clazz properties
		IVjoSymbol clazzSymbol = symbolTable.getSymbolInScope(scope, "clazz", EVjoSymbolType.STATIC_VARIABLE);
		if(clazzSymbol == null){
			clazzSymbol = new VjoSymbol();
			clazzSymbol.setDeclareType(s_vjoClazzType);
			clazzSymbol.setSymbolType(EVjoSymbolType.STATIC_VARIABLE);
			clazzSymbol.setName("clazz");
			symbolTable.addSymbolInScope(scope, clazzSymbol);
		}
	}

	private static void symbolizeInnerClass(final IJstNode scope, 
			final IJstType jstType,
			final IJstType innerClzType,
			final JstModifiers jstModifier,
			final VjoSymbolTable symbolTable){
		//bugfix, static inner type should not be inherited
//		if(innerClzType.getModifiers().isStatic() && innerClzType.getOwnerType() != scope){
//			return;
//		}
		
		IVjoSymbol symbol4InnerClass = symbolTable.getSymbolInScope(scope, innerClzType.getName(), (innerClzType.getModifiers().isStatic() ? EVjoSymbolType.STATIC_VARIABLE: EVjoSymbolType.INSTANCE_VARIABLE));
		if(symbol4InnerClass == null){
			boolean isStaticInnerType = innerClzType.getModifiers().isStatic();
			symbol4InnerClass = new VjoSymbol()
				.setName(innerClzType.getSimpleName())
				.setSymbolType((isStaticInnerType ? EVjoSymbolType.STATIC_VARIABLE
						: EVjoSymbolType.INSTANCE_VARIABLE))
				.setDeclareType(JstTypeHelper.getJstTypeRefType(innerClzType));
			symbolTable.addSymbolInScope(scope, symbol4InnerClass);
		}
		//update the visibility
		if(innerClzType.getModifiers().isNone()){
			symbol4InnerClass.setVisible(true);
		}
		else if(jstType.isInterface()){
			symbol4InnerClass.setVisible(true);
		}
		else if(jstModifier.isPublic() && (innerClzType.getModifiers().isProtected() || innerClzType.getModifiers().isPrivate())
				|| jstModifier.isProtected() && innerClzType.getModifiers().isPrivate()
				|| isDefault(innerClzType.getModifiers()) && !withinSamePackage(scope.getOwnerType(), innerClzType.getOwnerType())){
			symbol4InnerClass.setVisible(false);
		}
		else{
			symbol4InnerClass.setVisible(true);
		}
		
		symbol4InnerClass.setDeclareNode(innerClzType);
		symbol4InnerClass.setStaticReference(true);
	}
	
	private static void symbolizeProperty(final IJstNode scope,
			final IJstType jstType, EVjoSymbolType symbolType,
			final JstModifiers jstModifier,
			final VjoValidationCtx ctx,
			IJstProperty jstProperty) {
		final VjoSymbolTable symbolTable = ctx.getSymbolTable();
		//bugfix, static methods should not be inherited
		if(jstProperty.isStatic() 
				&& jstProperty.getOwnerType() != scope
				&& !jstModifier.isStatic()){
			return;
		}
		
		IVjoSymbol symbol4Property = symbolTable.getSymbolInScope(scope, jstProperty.getName().getName(), symbolType != null ? symbolType : (jstProperty.isStatic() ? EVjoSymbolType.STATIC_VARIABLE: EVjoSymbolType.INSTANCE_VARIABLE)); 
		if(symbol4Property == null){
			symbol4Property = new VjoSymbol()
				.setName(jstProperty.getName().getName())
				.setSymbolType(symbolType != null ? symbolType : (jstProperty.isStatic() ? EVjoSymbolType.STATIC_VARIABLE
						: EVjoSymbolType.INSTANCE_VARIABLE));
			symbolTable.addSymbolInScope(scope, symbol4Property);
		}
		//update the visibility
		if(jstProperty.getModifiers().isNone() || jstType.isInterface()){
			symbol4Property.setVisible(true);
		}
		else if(jstModifier.isPublic() && (jstProperty.getModifiers().isProtected() || jstProperty.getModifiers().isPrivate())
				|| jstModifier.isProtected() && jstProperty.getModifiers().isPrivate()
				|| isDefault(jstProperty.getModifiers(), jstProperty) && !withinSamePackage(scope.getOwnerType(), jstProperty.getOwnerType())){
			symbol4Property.setVisible(false);
		}
		else{
			symbol4Property.setVisible(true);
		}
		
		symbol4Property.setDeclareNode(jstProperty);
		final IJstType propertyType = jstProperty.getType() == null 
			|| ("Object".equals(jstProperty.getType().getSimpleName())
					&& !"vjo.Object".equals(jstProperty.getType().getName()))
			? VjoConstants.ARBITARY
			: jstProperty.getType();
		symbol4Property.setDeclareType(propertyType);
		symbol4Property.setStaticReference(false);
		
//		if(jstProperty instanceof JstVjoProperty && "vj$".equals(jstProperty.getName().getName())){
//			symbolizeVj$(ctx, (JstVjoProperty)jstProperty);
//		}
	}
	
//	private static void symbolizeVj$(final VjoValidationCtx ctx,
//			final JstVjoProperty vj$Property){
//		final IJstType referenceType = vj$Property.getType();
//		loadSingleJstTypeSymbols(ctx, referenceType);
//	}

	private static void symbolizeMethod(final IJstNode scope,
			final EVjoSymbolType symbolType, 
			final JstModifiers jstModifier,
			final VjoSymbolTable symbolTable, 
			final IJstMethod jstMethod) {
		//bugfix, static methods should not be inherited
		if(jstMethod.isStatic() 
				&& jstMethod.getOwnerType() != scope
				&& !jstModifier.isStatic()){
			return;
		}
				
		//bugfix, could be a parent overridable method
		//need to add as an overload method of the parent's method
		//example Number#toString(num:Number) vs. Object.toString()
		IVjoSymbol symbol4Method = symbolTable.getSymbolInScope(scope, jstMethod.getName().getName(), symbolType != null ? symbolType : (jstMethod.isStatic() ? EVjoSymbolType.STATIC_FUNCTION: EVjoSymbolType.INSTANCE_FUNCTION), true);
		if(symbol4Method == null){
			symbol4Method = new VjoSymbol()
				.setName(jstMethod.getName().getName())
				.setSymbolType(symbolType != null ? symbolType : (jstMethod.isStatic() ? EVjoSymbolType.STATIC_FUNCTION
						: EVjoSymbolType.INSTANCE_FUNCTION));
			symbolTable.addSymbolInScope(scope, symbol4Method);
		}
		else{
			final IJstType methodType = symbol4Method.getAssignedType();
			if(methodType != null && methodType instanceof JstFunctionRefType){
				IJstMethod mtd = ((JstFunctionRefType)methodType).getMethodRef();

				if(mtd != null 
						&& mtd instanceof JstMethod
						&& jstMethod instanceof JstMethod){
					if(!jstMethod.equals(mtd) &&
							mtd.getParentNode() != jstMethod.getParentNode()){
						symbol4Method = new VjoSymbol()
							.setName(jstMethod.getName().getName())
							.setSymbolType(symbolType != null ? symbolType : (jstMethod.isStatic() ? EVjoSymbolType.STATIC_FUNCTION
									: EVjoSymbolType.INSTANCE_FUNCTION));
						
						symbolTable.addSymbolInScope(scope, symbol4Method);
		/*	BUG 8632 Yubin 11/04/09
		 * The following code will add overloaded method toString() of native Object to vjo.Object, then add toString() of vjo.Object
		 * to the overloaded methods of toString() of the native Object, causing circular relationship
		 * 
		 * DON't modify JstType directly
		 * 		
						if(!((JstMethod)jstMethod).getOverloaded().contains((JstMethod)mtd) 
								&& !(mtd.isAbstract() || mtd.getOwnerType() != null && mtd.getOwnerType().isInterface())
								&& !mtd.getModifiers().isStatic()
								&& !jstMethod.getModifiers().isStatic()){
							((JstMethod)jstMethod).addOverloaded((JstMethod)mtd);
						}
		*/
					}
				}
			}
		}
		
		//update the visibility
		if(jstModifier.isPublic() && (jstMethod.getModifiers().isProtected() || jstMethod.getModifiers().isPrivate())
				|| jstModifier.isProtected() && jstMethod.getModifiers().isPrivate()
				|| isDefault(jstMethod.getModifiers(), jstMethod) && !withinSamePackage(scope.getOwnerType(), jstMethod.getOwnerType())){
			symbol4Method.setVisible(false);
		}
		else{
			symbol4Method.setVisible(true);
		}
		
		symbol4Method.setDeclareNode(jstMethod);
		symbol4Method.setDeclareType(new JstFunctionRefType(jstMethod));
	}
	
	private static boolean isDefault(final JstModifiers jstModifiers){
		return !(jstModifiers.isPublic() || jstModifiers.isProtected() || jstModifiers.isPrivate());
	}
	
	private static boolean isDefault(final JstModifiers jstModifiers, final IJstMethod mtd){
		return !(jstModifiers.isPublic() || jstModifiers.isProtected() || jstModifiers.isPrivate())
			&& mtd.getRtnType() != null;
	}
	
	private static boolean isDefault(final JstModifiers jstModifiers, final IJstProperty pty){
		return !(jstModifiers.isPublic() || jstModifiers.isProtected() || jstModifiers.isPrivate())
			&& pty.getType() != null;
	}
	
	private static boolean withinSamePackage(final IJstType type1, final IJstType type2){
		final String type1Name = type1.getName();
		final String type2Name = type2.getName();
		if(type1Name == null || type2Name == null){
			return false;
		}
		
		final int type1LastDotIndex = type1Name.lastIndexOf('.');
		final int type2LastDotIndex = type2Name.lastIndexOf('.');
		final String type1PackageName = type1LastDotIndex >= 0 ? type1Name.substring(0, type1LastDotIndex) : type1Name;
		final String type2PackageName = type2LastDotIndex >= 0 ? type2Name.substring(0, type2LastDotIndex) : type2Name;
		return type1PackageName.equals(type2PackageName);
	}
	
	private static void unloadSingleJstTypeSymbols(final VjoValidationCtx ctx, final IJstType scope, final IJstType jstType, int modifier){
		final JstModifiers jstModifier = new JstModifiers(modifier);
		final VjoSymbolTable symbolTable = ctx.getSymbolTable();
		
		if(!jstType.isInterface()){
			for(IJstProperty jstProperty: jstType.getProperties()){
				IVjoSymbol symbol4Property = symbolTable.getSymbolInScope(scope, jstProperty.getName().getName(), jstProperty.isStatic() ? EVjoSymbolType.STATIC_VARIABLE: EVjoSymbolType.INSTANCE_VARIABLE); 
				if(symbol4Property != null){
					if(jstModifier.isPrivate() && jstProperty.getModifiers().isPrivate()
							|| jstModifier.isProtected() && (jstProperty.getModifiers().isProtected() || jstProperty.getModifiers().isPrivate())
							|| jstModifier.isPublic()){
						symbol4Property.setVisible(false);
					}
				}
			}
		}
		
		for(IJstMethod jstMethod: jstType.getMethods()){
			IVjoSymbol symbol4Method = symbolTable.getSymbolInScope(scope, jstMethod.getName().getName(), jstMethod.isStatic() ? EVjoSymbolType.STATIC_FUNCTION: EVjoSymbolType.INSTANCE_FUNCTION);
			if(symbol4Method != null){
				if(jstModifier.isPrivate() && jstMethod.getModifiers().isPrivate()
						|| jstModifier.isProtected() && (jstMethod.getModifiers().isProtected() || jstMethod.getModifiers().isPrivate())
						|| jstModifier.isPublic()){
					symbol4Method.setVisible(false);
				}
			}
		}
	}

	private static final class DuplicateLoadProtectionKey{
		private IJstType scope;
		private IJstType source;
		private int modifiers = JstModifiers.NONE;
		
		public DuplicateLoadProtectionKey(){
			
		}
		
		public DuplicateLoadProtectionKey(final IJstType scope,
				final IJstType source,
				final int modifiers){
			setScope(scope);
			setSource(source);
			setModifiers(modifiers);
		}
		
		public IJstType getScope() {
			return scope;
		}
		public void setScope(IJstType scope) {
			this.scope = scope;
		}
		public IJstType getSource() {
			return source;
		}
		public void setSource(IJstType source) {
			this.source = source;
		}
		public int getModifiers() {
			return modifiers;
		}
		public void setModifiers(int modifiers) {
			this.modifiers = modifiers;
		}
		
		@Override
		public boolean equals(Object target){
			if(target == null || !(target instanceof DuplicateLoadProtectionKey)){
				return false;
			}
			final DuplicateLoadProtectionKey key = (DuplicateLoadProtectionKey)target;
			return key.scope == this.scope
				&& key.source == this.source
				&& key.modifiers == this.modifiers;
		}
		
		@Override
		public int hashCode(){
			int hashCode = 0;
			if(scope != null){
				hashCode += scope.hashCode() << 6;
			}
			if(source != null){
				hashCode += scope.hashCode() << 4;
			}
			hashCode += modifiers << 2;
			return hashCode;
		}
	}
}
