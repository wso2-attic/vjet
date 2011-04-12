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
import java.util.Iterator;
import java.util.List;

import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstProperty;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.util.JstTypeHelper;
import org.ebayopensource.vjo.tool.codecompletion.CodeCompletionUtils;
import org.ebayopensource.vjo.tool.codecompletion.IVjoCcAdvisor;
import org.ebayopensource.vjo.tool.codecompletion.VjoCcCtx;

/**
 * Advise properties and methods from a called JstType. <br>
 * rule: 1 the constructor should be filter out from result.<br>
 * example1:<br>
 * function() { x<cursor> }<br>
 * example2:<br>
 * function() { x.x<cursor> }<br>
 * 
 * Needs attribute:<br>
 * ctx.actingType,<br>
 * ctx.actingToken,<br>
 * ctx.calledType<br>
 * 
 * ProposalData.data IJstProperty and IjstMethod. <b4>
 * 
 * 
 * 
 */
public class VjoCcPropMethodProposalAdvisor extends AbstractVjoCcAdvisor
		implements IVjoCcAdvisor {
	public static final String ID = VjoCcPropMethodProposalAdvisor.class
			.getName();

	public void advise(VjoCcCtx ctx) {
		String token = ctx.getActingToken();
		IJstType callingType = ctx.getActingType();
		IJstType calledType = ctx.getCalledType();
		if (calledType == null) {
			calledType = callingType;
		}
		boolean isNative = CodeCompletionUtils.isNativeType(calledType);
		IJstType tempCalledType = calledType;
		int[] levels = getCallLevel(callingType, calledType);
		List<String> tempString = new ArrayList<String>();
		List<? extends IJstMethod> methods = JstTypeHelper.getSignatureMethods(calledType, false, true);
		Iterator<? extends IJstMethod> it = methods.iterator();
		while (it.hasNext()) {
			IJstMethod method = it.next();
			if(!isReferenceByDot(method)){
				continue;
			}
			
			if (tempCalledType != method.getOwnerType()) {
				tempCalledType = method.getOwnerType();
				if (tempCalledType == null) {
					tempCalledType = calledType;
				}
				levels = getCallLevel(callingType, tempCalledType);
				isNative = CodeCompletionUtils.isNativeType(tempCalledType);
			}
			boolean exactMatch = exactMatch(method.getName().getName(), token);
			boolean basicMatch = exactMatch;
			if (!exactMatch) {
				basicMatch = basicMatch(method.getName().getName(), token);
			}
			if (exactMatch || basicMatch) {
				addMethod(method, levels, isNative, ctx, tempString, exactMatch);
//				List<IJstMethod> overloads = method.getOverloaded();
//				if (overloads != null) {
//					Iterator<IJstMethod> it1 = overloads.iterator();
//					while (it1.hasNext()) {
//						addMethod(it1.next(), levels, isNative, ctx,
//								tempString, (exactMatch && basicMatch));
//					}
//				}

			}
		}
		List<IJstProperty> properties = calledType.getAllPossibleProperties(false, true);
		Iterator<IJstProperty> it1 = properties.iterator();
		while (it1.hasNext()) {
			
			IJstProperty property = it1.next();
			
			if(!isReferenceByDot(property)){
				continue;
			}
			
			
			if (tempCalledType != property.getOwnerType()) {
				tempCalledType = property.getOwnerType();
				levels = getCallLevel(callingType, tempCalledType);
				isNative = CodeCompletionUtils.isNativeType(tempCalledType);
			}
			boolean exactMatch = exactMatch(property.getName().getName(), token);
			boolean basicMatch = exactMatch;
			if (!exactMatch) {
				basicMatch = basicMatch(property.getName().getName(), token);
			}
			if (levelCheck(property.getModifiers(), levels)
					&& (exactMatch || basicMatch)
					&& (!isNative || !property.getName().getName().startsWith(
							"_"))) {
				appendData(ctx, property, (exactMatch && basicMatch));
			}
		}
		//Inner types
//		List<? extends IJstType> types = calledType.getInstanceEmbededTypes();
//		if (types != null && !types.isEmpty()) {
//			Iterator<? extends IJstType> it2 = types.iterator();
//			while (it2.hasNext()) {
//				IJstType type = it2.next();
//				levels = getCallLevel(callingType, type);
//				boolean exactMatch = exactMatch(type.getSimpleName(), token);
//				boolean basicMatch = exactMatch;
//				if (!exactMatch) {
//					basicMatch = basicMatch(type.getSimpleName(), token);
//				}
//				if (levelCheck(type.getModifiers(), levels)
//						&& (exactMatch || basicMatch)
//						&& (!isNative || !type.getSimpleName().startsWith(
//								"_"))) {
//					appendData(ctx, type, (exactMatch && basicMatch));
//				}
//			}
//		}
		
	}

	private boolean isReferenceByDot(IJstMethod method) {
		if(method.getName()!=null){
			if(method.getName().getName().startsWith("\'" )){
				return false;
			}else if(method.getName().getName().startsWith("\"" )){
				return false;
			}
		}
		
		return true;
	}
	
	private boolean isReferenceByDot(IJstProperty property) {
		if(property.getName()!=null){
			if(property.getName().getName().startsWith("\'" )){
				return false;
			}else if(property.getName().getName().startsWith("\"" )){
				return false;
			}
		}
				
		return true;
	}

	private void addMethod(IJstMethod method, int[] levels, boolean isNative,
			VjoCcCtx ctx, List<String> tempString, boolean exactMatch) {
		if (!method.isConstructor()
				&& levelCheck(method.getModifiers(), levels)
				&& (!isNative || !method.getName().getName().startsWith("_"))) {
			String str = CodeCompletionUtils.getMthodsStr(method);
			if (!(tempString.contains(str))) {
				tempString.add(str);
				appendData(ctx, method, exactMatch);
			}
		}

	}

	/**
	 * This check is used to check native object, such as window, document, it
	 * is inner object, default should not the method and property from the
	 * parent (Global and Object)
	 * 
	 * @param method
	 * @return
	 */
	// protected boolean nativeCheck(VjoCcCtx ctx, IJstType type) {
	// if (type.equals(ctx.getGlobalType())
	// // || type.equals(ctx.getObjectType())
	// ) {
	// return false;
	// } else {
	// return true;
	// }
	// }
	/**
	 * the level should be public, protected, and private. current type (this.),
	 * should show all the method and propertyes. superType: public, and
	 * protected Type in same package: public, default. others: public
	 * 
	 * @param ctx
	 * @return TODO: it should return int[] based on the relationship between
	 *         called type and calling type
	 */
	protected int[] getCallLevel(IJstType callingType, IJstType calledType) {
		return CodeCompletionUtils.getGeneralFieldCallLevel(callingType,
				calledType);
	}

}
