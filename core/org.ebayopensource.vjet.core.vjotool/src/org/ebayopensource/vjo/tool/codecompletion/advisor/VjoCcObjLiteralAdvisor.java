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
import java.util.List;

import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstProperty;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstArg;
import org.ebayopensource.dsf.jst.declaration.JstFuncType;
import org.ebayopensource.dsf.jst.declaration.JstFunctionRefType;
import org.ebayopensource.dsf.jst.declaration.JstName;
import org.ebayopensource.dsf.jst.declaration.JstObjectLiteralType;
import org.ebayopensource.dsf.jst.declaration.SynthOlType;
import org.ebayopensource.dsf.jst.term.NV;
import org.ebayopensource.dsf.jst.term.ObjLiteral;
import org.ebayopensource.dsf.jstojava.translator.TranslateHelper.RenameableSynthJstProxyMethod;
import org.ebayopensource.dsf.jstojava.translator.TranslateHelper.RenameableSynthJstProxyProp;
import org.ebayopensource.dsf.jstojava.translator.robust.completion.JstCompletion;
import org.ebayopensource.vjo.tool.codecompletion.IVjoCcAdvisor;
import org.ebayopensource.vjo.tool.codecompletion.VjoCcCtx;

public class VjoCcObjLiteralAdvisor extends AbstractVjoCcAdvisor implements
		IVjoCcAdvisor {

	public static final String ID = VjoCcObjLiteralAdvisor.class.getName();

		/**
		 * ObjLiteral advisor has 2 possible kinds of proposals
		 * <ol>
		 * <li>To propose valid object literal names if it complies with some OType</li>
		 * <li>To propose valid function signatures if it complies with some OType
		 * methods</li>
		 * </ol>
		 */
		public void advise(VjoCcCtx ctx) {
	
			final JstCompletion completion = ctx.getCompletion();
			final IJstNode realParent = completion.getRealParent();
			final String compositeToken = completion.getCompositeToken() != null ? completion
					.getCompositeToken() : "";
			ObjLiteral enclosingObjLiteral = null;
			String name = "";
	
			// decides whether the context requires proposals of obj literal's names
			// or function values
			boolean proposingName = false;
			if (realParent instanceof ObjLiteral) {
				enclosingObjLiteral = (ObjLiteral) realParent;
				proposingName = true;
			} else if (realParent instanceof NV) {
				name = ((NV) realParent).getName();
				enclosingObjLiteral = (ObjLiteral) ((NV) realParent)
						.getParentNode();
				proposingName = compositeToken.indexOf(':') > 0 ? false : true;
			}
	
			// look for bound otype, if available, start proposals
			final IJstType olExprType = enclosingObjLiteral.getResultType();
			if (olExprType != null && olExprType instanceof SynthOlType) {
				final SynthOlType enclosingObjLiteralType = (SynthOlType) olExprType;
				List<IJstType> olResolvedTypes = enclosingObjLiteralType
						.getResolvedOTypes();
				if (olResolvedTypes==null && enclosingObjLiteralType !=null) {
					olResolvedTypes = new ArrayList<IJstType>();
					olResolvedTypes.add(enclosingObjLiteralType);
				}
				//olResolvedTypes.add(enclosingObjLiteralType);
	//			if(olResolvedTypes==null){
	//				return;
	//			}
				for (IJstType olResolvedType : olResolvedTypes) {
					if (olResolvedType != null
							&& (olResolvedType instanceof JstObjectLiteralType || olResolvedType instanceof SynthOlType)) {
						final IJstType otype =  olResolvedType;
						// names proposals, filtered with the partial name if
						// non-empty
						if (proposingName) {
							String nvOp = (enclosingObjLiteral != null) ? ": " : "";
							int count= 0;
							// otype.addOptionalField(optionalField)
							for (IJstProperty otypePty : otype.getProperties(false)) {
								
								if(count==0 && (otypePty.getValue() instanceof ObjLiteral || otypePty.getType() instanceof JstObjectLiteralType)){
									nvOp += "{}";
								}
								final JstName ptyName = otypePty.getName();
								
								final IJstType parameterType = otypePty.getType();
								// support functions which are part of object literal type
								// letter case tolerated
								if(parameterType instanceof JstFuncType){
									appendData(ctx, getParamNamedMethodProposal(otypePty, ((JstFuncType)parameterType).getFunction()), true);
								}
								else if(parameterType instanceof JstFunctionRefType){
									appendData(ctx, getParamNamedMethodProposal(otypePty, ((JstFunctionRefType)parameterType).getMethodRef()), true);
								}
								else{
								
									if( ptyName.getName() != null && ptyName.getName().toLowerCase()
													.startsWith(name.toLowerCase())) {
										appendData(
												ctx,
												proposeObjLiteralProperty(otypePty,
														nvOp), true);
									}
								}
								count++;
							}
						}
						// function signature proposals, matching name required
						else {
							if (name.length() > 0) {
								final IJstProperty otypePty = otype.getProperty(
										name, false);
								if (otypePty != null
										&& otypePty.getType() != null
										&& otypePty.getType() instanceof JstFuncType) {
									final IJstMethod otypeMtd = ((JstFuncType) otypePty
											.getType()).getFunction();
									if (otypeMtd != null) {
										// handles none-overloading
										if (!otypeMtd.isDispatcher()) {
											appendData(ctx, otypeMtd, true);
										}
										// handles overloading apis
										else {
											for (IJstMethod otypeMtdOverload : otypeMtd
													.getOverloaded()) {
												appendData(ctx, otypeMtdOverload,
														true);
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}

		private IJstMethod getParamNamedMethodProposal(
				final IJstProperty pty, final IJstMethod method) {
	
			return new RenameableSynthJstProxyMethod(method, pty.getName().getName());
		}
		
	private IJstProperty proposeObjLiteralProperty(final IJstProperty pty,
			final String nvOp) {
		return new RenameableSynthJstProxyProp(pty, pty.getName().getName()
				+ nvOp);
	}
}