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
import org.ebayopensource.dsf.jst.declaration.JstAttributedType;
import org.ebayopensource.dsf.jst.declaration.JstModifiers;
import org.ebayopensource.dsf.jst.declaration.JstTypeReference;
import org.ebayopensource.dsf.jst.util.JstTypeHelper;
import org.ebayopensource.vjo.tool.codecompletion.IVjoCcAdvisor;
import org.ebayopensource.vjo.tool.codecompletion.VjoCcCtx;
import org.ebayopensource.vjo.tool.codecompletion.proposaldata.VjoCcProposalData;

/**
 * Have effect inside of method
 * example:
 * xxx function() {
 *    ab<cursor>
 * }
 * when the token is empty or simple name string (without dot), advise the Global method.
 * Global methods and property come from JstTypeSpaceMgr().getQueryExecutor()
				.getAllGlobalProperties() and JstTypeSpaceMgr().getQueryExecutor()
				.getAllGlobalMethods()	
 * but now, it come from global object.
 * 
 * need attributes:
 * ctx.actingToken
 * 
 * 
 *
 */
public class VjoCcGlobalAdvisor extends AbstractVjoCcAdvisor implements
		IVjoCcAdvisor {
	public static final String ID = VjoCcGlobalAdvisor.class.getName();

	public void adviseOld(VjoCcCtx ctx) {

		String token = ctx.getActingToken();
		IJstType calledType = ctx.getGlobalType();
		if (calledType == null) {
			return;
		}
		List<? extends IJstMethod> methods = calledType.getMethods();
		Iterator<? extends IJstMethod> it = methods.iterator();
		while (it.hasNext()) {
			IJstMethod method = it.next();
			if (method.getName().getName().startsWith(token)) {
				appendData(ctx, method);
			}
		}
		List<IJstProperty> properties = calledType.getProperties();
		Iterator<IJstProperty> it1 = properties.iterator();
		while (it1.hasNext()) {
			IJstProperty property = it1.next();
			if (property.getName().getName().startsWith(token)) {
				appendData(ctx, property);
			}
		}

	}

	/**
	 * This is the right way to get Global method, but it still can not work
	 * TODO, need use this after the back end API is ok
	 * @param ctx
	 */
	public void advise(VjoCcCtx ctx) {
		String token = ctx.getToken();
		
		
		List<IJstNode> vars = ctx.getJstTypeSpaceMgr().getQueryExecutor()
				.getAllGroupScopedGlobalVars(ctx.getGroupName());		
		if(vars==null){
			return;
		}
		Iterator<IJstNode> it = vars.iterator();
		while (it.hasNext()) {
			IJstNode node = it.next();
			if (node instanceof IJstMethod) {
				IJstMethod method = (IJstMethod) node;
				if (method.getName().getName().startsWith(token)) {
					appendMethod(ctx, method, method.getName().getName());
				}				
			}
			
			if (node instanceof IJstProperty) {
				IJstProperty property = (IJstProperty) node;
				if (property.getName().getName().startsWith(token)) {
					appendData(ctx, property);
				}
			}
			
			if (node instanceof IJstGlobalVar) {
				IJstGlobalVar jsvar = (IJstGlobalVar) node;
				if (jsvar.getScopeForGlobal() == null) {
					String name = jsvar.getName().getName();
					if (name.startsWith(token)) {
						
						if(jsvar.getTypeRef() instanceof JstTypeReference){
							JstTypeReference tref = (JstTypeReference)jsvar.getTypeRef();
							if(tref.getReferencedType().isFType()){
								IJstMethod m = tref.getReferencedType().getMethod("_invoke_");
								appendMethod(ctx, m, name);
							}
							tref.getOwnerType();
						}
						
						
						if(jsvar.getType() instanceof JstAttributedType){
							JstAttributedType attrType = (JstAttributedType)jsvar.getType();
							if(attrType.getJstBinding() instanceof IJstMethod){
								IJstMethod method = (IJstMethod)attrType.getJstBinding();
								appendMethod(ctx, method, name);
							}
						}
						
						
						if(jsvar.isFunc()){
							appendData(ctx, jsvar.getFunction());
						} else {
							appendData(ctx, jsvar.getProperty());
						}
					}
				}
			}			
		}		
	}

	private void appendMethod(VjoCcCtx ctx, IJstMethod method, final String name) {
		List<? extends IJstMethod> smethods = JstTypeHelper.getSignatureMethods(method);
		Iterator<? extends IJstMethod> it = smethods.iterator();
		while (it.hasNext()) {
			ctx.getReporter().addPropsal(new VjoCcProposalData(it.next(), ctx, getId()){
				@Override
				public String getName() {
					return name;
				}
			});
		}
	}

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
		return new int[] { JstModifiers.PUBLIC, JstModifiers.PROTECTED,
				JstModifiers.NONE, JstModifiers.PRIVATE };
	}

}
