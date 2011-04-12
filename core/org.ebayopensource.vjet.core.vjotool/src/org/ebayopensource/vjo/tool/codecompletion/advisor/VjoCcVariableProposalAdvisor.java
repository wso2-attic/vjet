/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.tool.codecompletion.advisor;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstProperty;
import org.ebayopensource.dsf.jst.declaration.JstArg;
import org.ebayopensource.dsf.jst.declaration.JstVars;
import org.ebayopensource.dsf.jst.declaration.VarTable;
import org.ebayopensource.dsf.jst.expr.AssignExpr;
import org.ebayopensource.dsf.jst.token.ILHS;
import org.ebayopensource.dsf.jstojava.controller.JstExpressionTypeLinkerHelper;
import org.ebayopensource.dsf.jstojava.translator.TranslateHelper.RenameableSynthJstProxyMethod;
import org.ebayopensource.dsf.jstojava.translator.TranslateHelper.RenameableSynthJstProxyProp;
import org.ebayopensource.vjo.tool.codecompletion.IVjoCcAdvisor;
import org.ebayopensource.vjo.tool.codecompletion.VjoCcCtx;

/**
 * advise the variable proposal, argument from the JstMethod where the cursor
 * in.<br>
 * Example:<br>
 * fun : function() {<br>
 * var v; //>String xx<cursor> }
 * 
 * Need attributes: 1. ctx.actingToken 2. ctx.selectedMethod
 * 
 * ProposalData.data: JstIdentifier
 * 
 * 
 * 
 */
public class VjoCcVariableProposalAdvisor extends AbstractVjoCcAdvisor
		implements IVjoCcAdvisor {
	public static final String ID = VjoCcVariableProposalAdvisor.class
			.getName();

	public void advise(VjoCcCtx ctx) {
		final List<IJstNode> selectedNodes = ctx.getSelectedJstNodes();
		if(selectedNodes.size() == 0){
			IJstMethod method = ctx.getSelectedJstMethod();
			if (method == null) {
				return;
			}
			List<JstVars> vars = ctx.getJstVars(method);
			if (vars.isEmpty()) {
				return;
			}
			Iterator<JstVars> it = vars.iterator();
			List<String> argList = VjoCcCtx.getArgStringList(method);
			while (it.hasNext()) {
				JstVars var = it.next();
				List<AssignExpr> ass = var.getInitializer().getAssignments();
				if (ass != null) {
					for (AssignExpr assign : ass) {
						ILHS lhs = assign.getLHS();
						if (lhs.toLHSText().startsWith(ctx.getActingToken())
								&& !argList.contains(lhs.toLHSText())) {
							appendData(ctx, lhs);
						}
					}
				}
			}
			return;
		}
		
		IJstNode innerMostSelectedNode = null;
		do{
			innerMostSelectedNode = selectedNodes.remove(selectedNodes.size() - 1);
		}
		while(innerMostSelectedNode == null && selectedNodes.size() > 0);
		
		if(innerMostSelectedNode == null){
			return;
		}
		
		final List<VarTable> varTables = JstExpressionTypeLinkerHelper.getVarTablesBottomUp(innerMostSelectedNode);
		final Set<String> proposedVarNames = new HashSet<String>();
		for(VarTable varTable: varTables){
			for(Map.Entry<String, IJstNode> varEntry : varTable.getVarNodes().entrySet()){
				final String varName = varEntry.getKey();
				if(varName.equals(ctx.getActingToken())){
					continue;
				}
				if(!varName.startsWith(ctx.getActingToken())){
					continue;
				}
				if(proposedVarNames.contains(varName)){
					continue;
				}
				else{
					proposedVarNames.add(varName);
				}
				
				IJstNode varBinding = varEntry.getValue();
				/**
				 * @see VjoParameterProposalAdvisor
				 */
				//added by huzhou@ebay.com as JstArg is proposed by other Advisor instead
				if(varBinding != null && !(varBinding instanceof JstArg)){
					varBinding = renameBindings(varName, varBinding);
					appendData(ctx, varBinding);
				}
			}
		}
	}

	private IJstNode renameBindings(final String varName,
			final IJstNode varBinding) {
		if(varBinding instanceof IJstProperty){
			final IJstProperty varBindingPty = (IJstProperty)varBinding;
			if(!varName.equals(varBindingPty.getName().getName())){
				return new RenameableSynthJstProxyProp(varBindingPty, varName);
			}
		}
		else if(varBinding instanceof IJstMethod){
			final IJstMethod varBindingMtd = (IJstMethod)varBinding;
			if(!varName.equals(varBindingMtd.getName().getName())){
				return new RenameableSynthJstProxyMethod(varBindingMtd, varName);
			}
		}
		
		return varBinding;
	}
}
