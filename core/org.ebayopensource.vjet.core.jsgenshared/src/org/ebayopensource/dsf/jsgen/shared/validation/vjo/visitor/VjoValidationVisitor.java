/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsgen.shared.validation.vjo.visitor;

import java.util.Iterator;
import java.util.List;

import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoValidationCtx;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoValidationRuntimeException;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.validator.VjoSemanticValidatorRepo;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.util.VjoValidationVisitorCtxUpdateUtil;
import org.ebayopensource.dsf.jst.BaseJstNode;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstRefType;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.ISynthesized;
import org.ebayopensource.dsf.jst.declaration.JstAnnotation;
import org.ebayopensource.dsf.jst.declaration.JstArg;
import org.ebayopensource.dsf.jst.declaration.JstArray;
import org.ebayopensource.dsf.jst.declaration.JstBlock;
import org.ebayopensource.dsf.jst.declaration.JstConstructor;
import org.ebayopensource.dsf.jst.declaration.JstDoc;
import org.ebayopensource.dsf.jst.declaration.JstFunctionRefType;
import org.ebayopensource.dsf.jst.declaration.JstGlobalFunc;
import org.ebayopensource.dsf.jst.declaration.JstGlobalProp;
import org.ebayopensource.dsf.jst.declaration.JstGlobalVar;
import org.ebayopensource.dsf.jst.declaration.JstMethod;
import org.ebayopensource.dsf.jst.declaration.JstModifiers;
import org.ebayopensource.dsf.jst.declaration.JstName;
import org.ebayopensource.dsf.jst.declaration.JstObjectLiteralType;
import org.ebayopensource.dsf.jst.declaration.JstPackage;
import org.ebayopensource.dsf.jst.declaration.JstParamType;
import org.ebayopensource.dsf.jst.declaration.JstProperty;
import org.ebayopensource.dsf.jst.declaration.JstProxyMethod;
import org.ebayopensource.dsf.jst.declaration.JstProxyProperty;
import org.ebayopensource.dsf.jst.declaration.JstRawBlock;
import org.ebayopensource.dsf.jst.declaration.JstRefType;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.dsf.jst.declaration.JstTypeReference;
import org.ebayopensource.dsf.jst.declaration.JstTypeWithArgs;
import org.ebayopensource.dsf.jst.declaration.JstVar;
import org.ebayopensource.dsf.jst.declaration.JstVars;
import org.ebayopensource.dsf.jst.declaration.JstVjoProperty;
import org.ebayopensource.dsf.jst.declaration.JstWildcardType;
import org.ebayopensource.dsf.jst.expr.ArrayAccessExpr;
import org.ebayopensource.dsf.jst.expr.ArrayCreationExpr;
import org.ebayopensource.dsf.jst.expr.AssignExpr;
import org.ebayopensource.dsf.jst.expr.BoolExpr;
import org.ebayopensource.dsf.jst.expr.CastExpr;
import org.ebayopensource.dsf.jst.expr.ConditionalExpr;
import org.ebayopensource.dsf.jst.expr.FieldAccessExpr;
import org.ebayopensource.dsf.jst.expr.FuncExpr;
import org.ebayopensource.dsf.jst.expr.InfixExpr;
import org.ebayopensource.dsf.jst.expr.JstArrayInitializer;
import org.ebayopensource.dsf.jst.expr.JstInitializer;
import org.ebayopensource.dsf.jst.expr.ListExpr;
import org.ebayopensource.dsf.jst.expr.MtdInvocationExpr;
import org.ebayopensource.dsf.jst.expr.ObjCreationExpr;
import org.ebayopensource.dsf.jst.expr.ParenthesizedExpr;
import org.ebayopensource.dsf.jst.expr.PostfixExpr;
import org.ebayopensource.dsf.jst.expr.PrefixExpr;
import org.ebayopensource.dsf.jst.expr.PtyGetter;
import org.ebayopensource.dsf.jst.expr.TextExpr;
import org.ebayopensource.dsf.jst.meta.BaseJsCommentMetaNode;
import org.ebayopensource.dsf.jst.stmt.BlockStmt;
import org.ebayopensource.dsf.jst.stmt.BreakStmt;
import org.ebayopensource.dsf.jst.stmt.CatchStmt;
import org.ebayopensource.dsf.jst.stmt.ContinueStmt;
import org.ebayopensource.dsf.jst.stmt.DispatchStmt;
import org.ebayopensource.dsf.jst.stmt.DoStmt;
import org.ebayopensource.dsf.jst.stmt.ExprStmt;
import org.ebayopensource.dsf.jst.stmt.ForInStmt;
import org.ebayopensource.dsf.jst.stmt.ForStmt;
import org.ebayopensource.dsf.jst.stmt.IfStmt;
import org.ebayopensource.dsf.jst.stmt.JstBlockInitializer;
import org.ebayopensource.dsf.jst.stmt.JstStmt;
import org.ebayopensource.dsf.jst.stmt.LabeledStmt;
import org.ebayopensource.dsf.jst.stmt.PtySetter;
import org.ebayopensource.dsf.jst.stmt.RtnStmt;
import org.ebayopensource.dsf.jst.stmt.SwitchStmt;
import org.ebayopensource.dsf.jst.stmt.TextStmt;
import org.ebayopensource.dsf.jst.stmt.ThisStmt;
import org.ebayopensource.dsf.jst.stmt.ThrowStmt;
import org.ebayopensource.dsf.jst.stmt.TryStmt;
import org.ebayopensource.dsf.jst.stmt.TypeDeclStmt;
import org.ebayopensource.dsf.jst.stmt.WhileStmt;
import org.ebayopensource.dsf.jst.stmt.WithStmt;
import org.ebayopensource.dsf.jst.stmt.SwitchStmt.CaseStmt;
import org.ebayopensource.dsf.jst.term.ArrayLiteral;
import org.ebayopensource.dsf.jst.term.JstIdentifier;
import org.ebayopensource.dsf.jst.term.JstLiteral;
import org.ebayopensource.dsf.jst.term.JstProxyIdentifier;
import org.ebayopensource.dsf.jst.term.NV;
import org.ebayopensource.dsf.jst.term.ObjLiteral;
import org.ebayopensource.dsf.jst.term.RegexpLiteral;
import org.ebayopensource.dsf.jst.term.SimpleLiteral;
import org.ebayopensource.dsf.jst.traversal.IJstNodeVisitor;
import org.ebayopensource.dsf.logger.Logger;

/**
 * <p> visitor pattern's visitor implementation for validation
 * meanwhile as a visitor event publisher
 * {@link VjoSemanticValidatorRepo} is the event dispatcher in fact
 * 
 * </p>
 * 
 *
 */
public class VjoValidationVisitor implements IJstNodeVisitor {
	
	/**
	 * @deprecated should be removed when existing code is cleaned up
	 * @see SUPPRESSTYPECHECK
	 */
	private static final String SUPRESSTYPECHECK = "SUPRESSTYPECHECK";

	/**
	 * Use as JS annotation '//< @SUPPRESSTYPECHECK' to suppress validation
	 * errors
	 */
	private static final String SUPPRESSTYPECHECK = "SUPPRESSTYPECHECK";

	private VjoValidationCtx m_ctx;
	
	private static Logger s_logger = null;
	
	private Logger getLogger(){
		if(s_logger==null){
			s_logger = Logger.getInstance(VjoValidationVisitor.class);
		}
		return s_logger;
	}
	
	public VjoValidationVisitor() {
		m_ctx = new VjoValidationCtx();
	}
	
	public VjoValidationCtx getCtx() {
		if(m_ctx == null){
			m_ctx = new VjoValidationCtx();
		}
		return m_ctx;
	}
	
	public VjoValidationVisitor setCtx(VjoValidationCtx ctx){
		m_ctx = ctx;
		return this;
	}
	
	private void visitAndValidate(IJstNode jstNode){
		if(jstNode instanceof ISynthesized
				|| getCtx().getVisitedJstNodes().contains(jstNode)){
			return;
		}
		else{
			getCtx().getVisitedJstNodes().add(jstNode);
		}
		
		if(jstNode instanceof JstIdentifier){ // collect JstIdentidier binding to constructor or JstTypeRefType
			JstIdentifier identifier = (JstIdentifier)jstNode;
			IJstNode binding = identifier.getJstBinding();
			IJstType type = identifier.getType();
			
			if (type != null) {
				if (type instanceof IJstRefType) {
					//binding to the type itself indicates a full type reference
					if (binding == type) {
						getCtx().addMustActivelyNeededTypes(
								jstNode.getRootType(), ((IJstRefType) type));
						getCtx().addKnownActivelyNeededTypes(
								jstNode.getRootType(), ((IJstRefType) type));
					}
					//this.vj$ is a known active needed types
					else{
						final IJstNode parent = identifier.getParentNode();
						if(parent != null && parent instanceof FieldAccessExpr){
							final IJstNode qualifier = ((FieldAccessExpr)parent).getExpr();
							if(qualifier != null && qualifier instanceof FieldAccessExpr){
								final IJstNode vj = ((FieldAccessExpr)qualifier).getName();
								if(vj != null 
										&& vj instanceof JstIdentifier
										&& ((JstIdentifier)vj).getJstBinding() != null
										&& ((JstIdentifier)vj).getJstBinding() instanceof JstVjoProperty){
									getCtx().addKnownActivelyNeededTypes(
											jstNode.getRootType(), ((IJstRefType) type));
								}
							}
						}
					}
				} 
			}
		}
		
		visitAndValidate(jstNode, false, false);
	}
	
	/**
	 * <p>
	 * reverse flag indicates children walk through in reversed order given in the parent node's getChildren list
	 * it's required for MtdInvocationExpr, the order of its children is:
	 * MtdInvocationExpr[id:JstIdentifier, arg:IExpr..., qualifier:IExpr)
	 * where we need to visit in the order of:
	 * MtdInvocationExpr[qualifier:IExpr, arg:IExpr..., id:JstIdentifier)
	 * reversed order works in this particular case
	 * 
	 * skipChild flag indicates no children should be visited. In case of fatal error found in the validation, we should
	 * skip the children validations as they won't make any sense then. One example is when circular inheritance chain is
	 * detected.
	 * </p>
	 * @param jstNode
	 * @param reverse
	 * @param skipChildren
	 */
	private void visitAndValidate(IJstNode jstNode, boolean reverse, boolean skipChildren) {
		try{
			getCtx().setJstNode(jstNode);
			final VjoSemanticValidatorRepo dispatcher = VjoSemanticValidatorRepo.getInstance();
			
			dispatcher.dispatch(genEvent(jstNode,null,VjoValidationVisitorState.BEFORE_ALL_CHILDREN));
			if(!reverse){
				for(int i = 0, size = jstNode.getChildren().size(); i < size; i++){
					final IJstNode child = jstNode.getChildren().get(i);
					dispatcher.dispatch(genEvent(jstNode,child,VjoValidationVisitorState.BEFORE_CHILD));
					visitChild(child);
					dispatcher.dispatch(genEvent(jstNode,child,VjoValidationVisitorState.AFTER_CHILD));
				}
			}
			else{
				for(int i = jstNode.getChildren().size() - 1; i >= 0; i--){
					final IJstNode child = jstNode.getChildren().get(i);
					dispatcher.dispatch(genEvent(jstNode,child,VjoValidationVisitorState.BEFORE_CHILD));
					visitChild(child);
					dispatcher.dispatch(genEvent(jstNode,child,VjoValidationVisitorState.AFTER_CHILD));
				}
			}
			dispatcher.dispatch(genEvent(jstNode,null,VjoValidationVisitorState.AFTER_ALL_CHILDREN));
			
			/*the annotation to hide all problems`*/
			if(shouldSuppressTypeCheck(jstNode)){
				getCtx().removeProblems(jstNode, true);
			}
		}
		catch(VjoValidationRuntimeException th){
			throw th;
		}
		catch(Throwable th){
			th.printStackTrace(); //KEEPME
			getLogger().log(th);
//			throw new VjoValidationRuntimeException(th);
		}
	}

	private boolean shouldSuppressTypeCheck(IJstNode jstNode) {
		return jstNode.getAnnotation(SUPPRESSTYPECHECK) != null ||
			   jstNode.getAnnotation(SUPRESSTYPECHECK) != null;
	}
	
	private void visitChild(IJstNode child) {
		child.accept(this);
	}
	
	public void visit(BaseJstNode node) {
		visitAndValidate(node);
	}
	
	public void visit(JstAnnotation node) {
//		visitAndValidate(node);
	}

	public void visit(JstArg node) {
		visitAndValidate(node);
	}

	public void visit(JstArrayInitializer node) {
		visitAndValidate(node);
	}

	public void visit(JstBlock node) {
		visitAndValidate(node);
	}

	public void visit(JstBlockInitializer node) {
		visitAndValidate(node);
	}

	public void visit(JstRawBlock node) {
		visitAndValidate(node);
	}

	public void visit(JstDoc node) {
		visitAndValidate(node);
	}

	public void visit(JstIdentifier node) {
		visitAndValidate(node);
	}

	public void visit(JstInitializer node) {
		visitAndValidate(node);
	}

	public void visit(JstLiteral node) {

	}

	public void visit(ArrayLiteral node) {
		visitAndValidate(node);
	}

	public void visit(ObjLiteral node) {
		final VjoValidationCtx ctx = getCtx();
		VjoValidationVisitorCtxUpdateUtil.updateCtxBeforeObjLiteral(ctx, node);
		visitAndValidate(node);
		VjoValidationVisitorCtxUpdateUtil.updateCtxAfterObjLiteral(ctx, node);
	}

	public void visit(JstMethod node) {
		final VjoValidationCtx ctx = getCtx();
		VjoValidationVisitorCtxUpdateUtil.updateCtxBeforeMethod(ctx, node);
		visitAndValidate(node);
		VjoValidationVisitorCtxUpdateUtil.updateCtxAfterMethod(ctx, node);
	}

	public void visit(RegexpLiteral node) {

	}

	public void visit(SimpleLiteral node) {
		//bugfix, typespace loaded in some environment caused jstType mess with java lang types
	}

	public void visit(JstConstructor node) {
		//skip anonymous overload constructor
		if(node.getParentNode() instanceof IJstType){
			final IJstType jstType = (IJstType)node.getParentNode();
			if(jstType.getConstructor() != node){
				return;
			}
		}
		final VjoValidationCtx ctx = getCtx();
		VjoValidationVisitorCtxUpdateUtil.updateCtxBeforeMethod(ctx, node);
		visitAndValidate(node);
		VjoValidationVisitorCtxUpdateUtil.updateCtxAfterMethod(ctx, node);
	}

	public void visit(JstModifiers node) {
		visitAndValidate(node);
	}

	public void visit(JstName node) {
		visitAndValidate(node);
	}

	public void visit(JstPackage node) {
		visitAndValidate(node);
	}

	public void visit(JstProperty node) {
		final VjoValidationCtx ctx = getCtx();
		VjoValidationVisitorCtxUpdateUtil.updateCtxBeforeProperty(ctx, node);
		visitAndValidate(node);
		VjoValidationVisitorCtxUpdateUtil.updateCtxAfterProperty(ctx, node);
	}

	public void visit(JstType node) {
		boolean runtimeException = false;
		final VjoValidationCtx ctx = getCtx();
		
		try{
			VjoValidationVisitorCtxUpdateUtil.updateCtxBeforeType(ctx, node);
		}
		catch(VjoValidationRuntimeException ex){
			runtimeException = true;
			//should log this to the driver
		}
		
		if(!runtimeException){
			visitAndValidate(node);
			//bugfix 4405, inner types must be validated, but they're not children of the containing type
			final List<JstType> innerTypes = node.getEmbededTypes();
			if(innerTypes != null && innerTypes.size() > 0){
				for(Iterator<JstType> it = innerTypes.iterator(); it.hasNext();){
					visit(it.next());
				}
			}
		}
		else{
			visitAndValidate(node, false, true);
		}
		
		try{
			VjoValidationVisitorCtxUpdateUtil.updateCtxAfterType(ctx, node);
		}
		catch(VjoValidationRuntimeException ex){
			//do nothing
			runtimeException = true;
		}
	}

	public void visit(JstArray node) {
		visitAndValidate(node);
	}

	public void visit(JstFunctionRefType node) {
		visitAndValidate(node);
	}

	public void visit(JstObjectLiteralType node) {
		getCtx().getScope().addTypeNode(node);
		visitAndValidate(node);
		getCtx().getScope().removeTypeNode(node);
	}

	public void visit(JstRefType node) {
		visitAndValidate(node);
	}

	public void visit(IJstRefType node) {
		visitAndValidate(node);
	}

	public void visit(JstTypeReference node) {
//		visitAndValidate(node);
	}

	public void visit(JstVar node) {
		visitAndValidate(node);
	}

	public void visit(JstVars node) {
		visitAndValidate(node);
	}

	public void visit(NV node) {
		visitAndValidate(node);
	}

	public void visit(JstStmt node) {
		visitAndValidate(node);
	}

	public void visit(BoolExpr node) {
		visitAndValidate(node);
	}

	public void visit(InfixExpr node) {
		visitAndValidate(node);
	}

	public void visit(ParenthesizedExpr node) {
		visitAndValidate(node);
	}

	public void visit(PostfixExpr node) {
		visitAndValidate(node);
	}

	public void visit(PrefixExpr node) {
		visitAndValidate(node);
	}

	public void visit(ArrayAccessExpr node) {
		visitAndValidate(node);
	}

	public void visit(ArrayCreationExpr node) {
		visitAndValidate(node);
	}

	public void visit(AssignExpr node) {
		visitAndValidate(node);
	}

	public void visit(CastExpr node) {
		visitAndValidate(node);
	}

	public void visit(ConditionalExpr node) {
		visitAndValidate(node);
	}

	public void visit(FieldAccessExpr node) {
		visitAndValidate(node);
	}

	public void visit(FuncExpr node) {
		visitAndValidate(node);
	}

	public void visit(ObjCreationExpr node) {
		visitAndValidate(node);
	}

	public void visit(MtdInvocationExpr node) {		
		visitAndValidate(node, true, false);
	}

	public void visit(ExprStmt node) {
		visitAndValidate(node);
	}

	public void visit(CaseStmt node) {
		visitAndValidate(node);
	}

	public void visit(BlockStmt node) {
		visitAndValidate(node);
	}

	public void visit(CatchStmt node) {
		visitAndValidate(node);
	}

	public void visit(ForInStmt node) {
		visitAndValidate(node);
	}

	public void visit(ForStmt node) {
		visitAndValidate(node);
	}

	public void visit(IfStmt node) {
		visitAndValidate(node);
	}

	public void visit(DispatchStmt node) {
		visitAndValidate(node);
	}

	public void visit(SwitchStmt node) {
		visitAndValidate(node);
	}

	public void visit(TryStmt node) {
		visitAndValidate(node);
	}

	public void visit(WhileStmt node) {
		visitAndValidate(node);
	}

	public void visit(DoStmt node) {
		visitAndValidate(node);
	}

	public void visit(WithStmt node) {
		//look up with expr scoped members as local variables
		visitAndValidate(node);
	}

	public void visit(BreakStmt node) {
		visitAndValidate(node);
	}

	public void visit(ContinueStmt node) {
		visitAndValidate(node);
	}

	public void visit(LabeledStmt node) {
		visitAndValidate(node);
	}

	public void visit(RtnStmt node) {
		visitAndValidate(node);
	}

	public void visit(TypeDeclStmt node) {
		visitAndValidate(node);
	}

	public void visit(ListExpr node) {
		visitAndValidate(node);
	}

	public void visit(ThisStmt node) {
		visitAndValidate(node);
	}

	public void visit(TextExpr node) {
		visitAndValidate(node);
	}

	public void visit(TextStmt node) {
		visitAndValidate(node);
	}

	public void visit(ThrowStmt node) {
		visitAndValidate(node);
	}

	public void visit(PtyGetter node) {
		visitAndValidate(node);
	}

	public void visit(PtySetter node) {
		visitAndValidate(node);
	}

	public void visit(JstProxyMethod node) {
		visitAndValidate(node);
	}

	public void visit(JstProxyProperty node) {
		visitAndValidate(node);
	}

	public void visit(JstParamType node) {
		visitAndValidate(node);
	}

	public void visit(JstWildcardType node) {
		visitAndValidate(node);
	}

	public void visit(JstTypeWithArgs node) {
		visitAndValidate(node);
	}

	private VjoValidationVisitorEvent genEvent(final IJstNode node, final IJstNode child, final VjoValidationVisitorState state){
		return new VjoValidationVisitorEvent(getCtx(), node, child, state);
	}

	@Override
	public void visit(JstGlobalVar node) {
		visitAndValidate(node);
		
	}

	@Override
	public void visit(JstGlobalFunc node) {
		visitAndValidate(node);
		
	}

	@Override
	public void visit(JstGlobalProp node) {
		visitAndValidate(node);
		
	}

	@Override
	public void visit(JstProxyIdentifier node) {
		visitAndValidate(node);
	}

	@Override
	public void visit(BaseJsCommentMetaNode<?> node) {
		visitAndValidate(node);
	}
}
