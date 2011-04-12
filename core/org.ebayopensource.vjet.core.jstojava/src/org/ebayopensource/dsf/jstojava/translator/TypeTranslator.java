/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.translator;

import java.util.List;

import org.ebayopensource.dsf.jst.FileBinding;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.IJstTypeReference;
import org.ebayopensource.dsf.jst.JstSource;
import org.ebayopensource.dsf.jst.JstSource.IBinding;
import org.ebayopensource.dsf.jst.declaration.JstModifiers;
import org.ebayopensource.dsf.jst.declaration.JstPackage;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.dsf.jst.declaration.JstTypeReference;
import org.ebayopensource.dsf.jst.declaration.JstType.Category;
import org.ebayopensource.dsf.jst.token.IStmt;
import org.ebayopensource.dsf.jstojava.parser.comments.CommentCollector.InactiveNeedsWrapper;
import org.ebayopensource.dsf.jstojava.translator.robust.ast2jst.BaseAst2JstTranslator;
import org.ebayopensource.dsf.jstojava.translator.robust.ast2jst.TranslatorFactory;
import org.ebayopensource.vjo.meta.VjoKeywords;
import org.eclipse.mod.wst.jsdt.core.ast.IExpression;
import org.eclipse.mod.wst.jsdt.core.ast.IProgramElement;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.ArrayInitializer;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.CharLiteral;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.Expression;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.FunctionExpression;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.Literal;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.MessageSend;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.SingleNameReference;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.StringLiteral;

public class TypeTranslator extends BaseTranslator {
	//private static final JstArg[] EMPTY_ARGS = new JstArg[0];
//	private static final String OBJ = "obj";
//	private static final String OBJECT = "Object";
	//private static final String INSTANCE_OF = "instanceOf";

	//
	// Constructor
	//
	public TypeTranslator(final TranslateCtx ctx) {
		super(ctx);
	}

	public void processType(MessageSend el, JstType jstType) {
		assert el != null;
		IExpression receiver = el;
		
		do {
			if (receiver instanceof MessageSend) {
				MessageSend message = (MessageSend) receiver;
				String method = String.valueOf(message.selector);
				if (VjoKeywords.PROTOS.equals(method)) {
					getProvider().getProtosTranslator().process(
							message.arguments[0], jstType);
				} else if (VjoKeywords.PROPS.equals(method)) {
					getProvider().getPropsTranslator().process(
							message.arguments[0], jstType);
				} else if (VjoKeywords.GLOBALS.equals(method)) {
					getProvider().getGlobalsTranslator().process(
							message.arguments[0], jstType);
				} else if (VjoKeywords.INHERITS.equals(method)) {
					processInherits(message, jstType);
				} else if (VjoKeywords.SATISFIES.equals(method)) {
					processSatisfies(message, jstType);
				} else if (VjoKeywords.EXPECTS.equals(method)) {
					processExpects(message, jstType);
				} else if (VjoKeywords.MIXIN.equals(method)) {
					processMixins(message, jstType);
				} else if (VjoKeywords.MAKE_FINAL.equals(method)) {
					jstType.getModifiers().merge(JstModifiers.FINAL);
				} else if (VjoKeywords.INITS.equals(method)) {
					processInits(message.arguments[0], jstType);
				} else if (VjoKeywords.OPTIONS.equals(method)) {
					getProvider().getGlobalsTranslator().process(
						message.arguments[0], jstType);	
				} else if (VjoKeywords.ENDTYPE.equals(method)) {
//					m_ctx.setPreviousNodeSourceEnd(message.sourceEnd);
				} else if (JstUtil.isType(method)) {
					String fullName = getName(message);
					
					
					String comment = getCtx().getCommentCollector().getCommentNonMeta2(message.sourceStart);
					JsDocHelper.addJsDoc(comment, jstType);
										
					int idx = 0;
					if (!jstType.isEmbededType()) {
						fullName = fullName.trim();
						idx = fullName.lastIndexOf('.');
						boolean hasGenericParams = true;
						int end = fullName.indexOf("<");
						if (end < 0) {
							hasGenericParams = false;
							end = fullName.length();
						}
						String clzName = fullName.substring(idx + 1,end);
						if (idx > 0) {
							String pkgName = fullName.substring(0, idx);
							JstSource source = getPackageSource(
									message.arguments[0], idx);
							JstPackage pkg = new JstPackage(pkgName);
							pkg.setGroupName(this.m_ctx.getGroup());
							pkg.setSource(source);
							jstType.setPackage(pkg);
						}
						jstType.setSimpleName(clzName);
						if (hasGenericParams && fullName.charAt(fullName.length()-1)=='>') {
							TranslateHelper.addParamsToType(getCtx(), jstType, fullName.substring(end+1,fullName.length()-1));
						}
						//add inactive needs
						addInactiveNeeds(jstType, m_ctx);						
					}

					JstSource source = getTypeNameSource(message, idx, fullName
							.length());
					
					if (jstType.getSource() != null) { // old source exists
					
						IBinding oldBinding = jstType.getSource().getBinding();
						
						if (oldBinding != null && oldBinding instanceof FileBinding) {
							// copy the initial file binding to the new source
							source.setBinding(oldBinding);
						}
					}
					
					jstType.setSource(source);

//					if (VjoKeywords.ATYPE.equals(method)) {
//						// set abstract
//						jstType.getModifiers().merge(JstModifiers.ABSTRACT);
//					} else 
					
					if (VjoKeywords.ETYPE.equals(method)) {
						// set etype
						jstType.setCategory(Category.ENUM);
						jstType.getModifiers().setFinal();
					} else if (VjoKeywords.ITYPE.equals(method)) {
						jstType.setCategory(Category.INTERFACE);
						
					} else if (VjoKeywords.MTYPE.equals(method)) {
						// set mtype
						jstType.setCategory(Category.MODULE);
					} else if (VjoKeywords.OTYPE.equals(method)) {
						jstType.setCategory(Category.OTYPE);
					} else if (VjoKeywords.FTYPE.equals(method)) {
						jstType.setCategory(Category.FTYPE);
					} 
//					else if (VjoKeywords.LTYPE.equals(method)) {
//						jstType.setCategory(Category.LTYPE);
//					}
				} else {
					// error?
				}
				receiver = message.getReceiver();
			} else if (receiver instanceof SingleNameReference) {
				receiver = null;
//				receiver = message.getReceiver();
			} else {
				System.err
						.println("Unprocessed type in processType TypeTranslator"
								+ receiver);
				// FIXME Field access expr.
				receiver = null;
			}
		} while (receiver != null);

	}

	public static void addInactiveNeeds(JstType jstType, TranslateCtx ctx) {
		List<InactiveNeedsWrapper>ineeds = ctx.getCommentCollector().getInactiveNeeds();
		for (InactiveNeedsWrapper ineed : ineeds) {//TODO fix source
			final String ineedsTypeName = ineed.getNeedsTypeName();
			JstSource source = new JstSource(JstSource.JS, -1, -1, ineedsTypeName.length(), ineed.getBeginOffset(), ineed.getEndOffset()
					+ ineedsTypeName.length());
			IJstTypeReference importType = TranslateHelper.getType(ctx, ineedsTypeName, source);
			if (jstType.getInactiveImport(importType.getReferencedType().getSimpleName()) != null) {
				//If one already exists with this simple name then use full name
				jstType.addInactiveImport("", importType);
			} else {
				jstType.addInactiveImport(importType);
			}
		}
	}


	private static String getName(MessageSend message) {
		String fullName = "";
		if (message.arguments != null && message.arguments.length > 0) {
			fullName = message.arguments[0].toString();
			fullName = JstUtil.getCorrectName(fullName);
		}
		return fullName;
	}
	
	private static String getName(Expression expr) {
		
		String fullName = "";
		
		if (expr instanceof MessageSend) {
			MessageSend message = (MessageSend)expr;
			
			if (message.arguments != null && message.arguments.length > 0) {
				fullName = message.arguments[0].toString();
				fullName = JstUtil.getCorrectName(fullName);
			}
		}
		else {
			fullName = expr.toString();
			fullName = JstUtil.getCorrectName(fullName);
		}		
		
		return fullName;
	}

	public void processIType(MessageSend el, JstType jstType) {
		assert el != null;
		jstType.setCategory(Category.INTERFACE);
		processType(el, jstType);
	}

	public void processMType(MessageSend el, JstType jstType) {
		assert el != null;
		jstType.setCategory(Category.MODULE);
		processType(el, jstType);

	}
	
	public void processOType(MessageSend el, JstType jstType) {
		assert el != null;
		jstType.setCategory(Category.OTYPE);
		processType(el, jstType);
	}

	public void processEType(MessageSend el, JstType jstType) {
		assert el != null;
		jstType.setCategory(Category.ENUM);
		processType(el, jstType);
	}

	public void processAType(MessageSend el, JstType jstType) {
		assert el != null;
		jstType.getModifiers().setAbstract();
		processCType(el, jstType);
	}

	
	public void processCType(MessageSend el, JstType jstType) {
		jstType.setCategory(Category.CLASS);
		processType(el, jstType);
	}
	
	//by huzhou@ebay.com for ftype feature
	public void processFType(MessageSend el, JstType jstType) {
		assert el != null;
		jstType.setCategory(Category.FTYPE);
		processType(el, jstType);
	}
	
//	public void processTopLevel(MessageSend e1, JstType jstType){
//		List<IJsCommentMeta> comments = m_ctx.getCommentCollector().getCommentMeta(
//				jstType.getSource().getStartOffSet(), //sourceStart is incorrect when there is js doc in front of it
//				m_ctx.getPreviousNodeSourceEnd(),
//				m_ctx.getNextNodeSourceStart());
//		
//		if(comments!=null){
//			JstModifiers modifiers = jstType.getModifiers();
//			for(IJsCommentMeta c:comments){
//				if(c.getAccess().equals()){
//					
//				}
//				modifiers.setPublic();
//				modifiers.setStatic(false);
//				modifiers.setFinal(false);
//				modifiers.setAbstract(false);
//			}
//		}
//		
//	}

	public void processInits(Expression expr, JstType jstType) {
		if (m_ctx.isSkiptImplementation()) {	
			return;
		}
//		boolean isSkipImpl = m_ctx.isSkiptImplementation();
		if (expr instanceof FunctionExpression) {
			FunctionExpression funcExpr = (FunctionExpression) expr;
			IProgramElement[] statements = funcExpr.getMethodDeclaration()
					.getStatements();

			// TODO add a statement to inits block
			if (statements != null) {
				for (IProgramElement st : statements) {
					@SuppressWarnings("unchecked")
					BaseAst2JstTranslator translator = TranslatorFactory
						.getTranslator(st, m_ctx);
					if (translator != null) {
						@SuppressWarnings("unchecked")
						Object result = translator.translate(st);
						if (result instanceof IStmt) {
							jstType.addInit((IStmt) result, true);
						}
					}
				}
			}
		} else {
			System.err.println("Unprocessed type: " + expr.getClass()
					+ " in processInits TypeTranslator");
		}

	}

	public void processSatisfies(MessageSend expr, JstType jstType) {
		processInheritsOrImplements(expr, jstType, Category.INTERFACE);
	}

	public void processInherits(MessageSend expr, JstType jstType) {
		processInheritsOrImplements(expr, jstType, Category.CLASS);
	}

	public void processExpects(MessageSend expr, JstType jstType) {
		processInheritsOrImplements(expr, jstType, Category.MODULE);
	}
	
	private void processInheritsOrImplements(MessageSend expr, JstType jstType,
			JstType.Category category) {
		
		Expression[] params = expr.arguments;
		
		if (params != null && params.length > 0) {
			if (isString(params[0])) {
				processInheritsOrSatisfies(params[0], jstType, category);
			}
			else if (isArray(params[0])) {
				ArrayInitializer param = (ArrayInitializer) params[0];
				
				if (param.expressions != null && param.expressions.length > 0) {
					if (JstType.Category.INTERFACE == category) {
						for (Expression ast : param.expressions) {
							processInheritsOrSatisfies(ast, jstType, category);
						}
					}
					else {
						Expression ast = param.expressions[0];
						processInheritsOrSatisfies(ast, jstType, category);
					}
				}
			}
		}
		else {
			processInheritsOrSatisfies(expr, jstType, category);
		}		
	}

	private void processInheritsOrSatisfies(Expression expr, JstType jstType,
			JstType.Category category) {
		String extendedTypeName = getName(expr);
		
		IJstType extendedType = TranslateHelper.findType(m_ctx, extendedTypeName);
//NOTE	refactored by huzhou, all generic logic in type string is now moved to TranslateHelper#findType
//		
//		if(extendedType == null){
//			String fullTypeName = extendedTypeName;
//			String params = null;
//			String name = extendedTypeName;
//			int start = name.indexOf("<");
//			if (start>1 && name.charAt(name.length()-1)=='>') {
//				params = name.substring(start+1,name.length()-1);
//				name = name.substring(0, start);
//			}
//			if (extendedTypeName.indexOf('.') == -1) {	
//				// no package
//				IJstType importedType = m_ctx.getCurrentType().getImport(
//						name);
//				if (importedType != null) {
//					fullTypeName = importedType.getName();
//					if (params!=null) {
//						fullTypeName += "<" + params + ">";
//					}
//				}
//			}
//			extendedType = TranslateHelper.findType(m_ctx, fullTypeName);
//		}
		
		
/* The returned extendedType is already JstTypeWithArgs with param
 * 6-30-09 Yubin commented out the following code
		if (params!=null) {
			JstTypeWithArgs typeWithArgs = new JstTypeWithArgs(extendedType);
			String[] args = params.split(",");
			for (String arg : args) {
				IJstType pType = jstType.getParamType(arg);
				if (pType==null) {
					pType = TranslateHelper.findType(m_ctx, arg);
				}
				typeWithArgs.addArgType(pType);
			}
			extendedType = typeWithArgs;
		}
*/
		JstSource source = getTypeNameSource(expr, extendedTypeName
				.lastIndexOf("."), extendedTypeName.length());

		JstTypeReference reference;

		switch (category) {
		case CLASS:
			reference = TranslateHelper.createRef(extendedType, source);
			jstType.addExtend(reference);
			break;
		case INTERFACE:
			//extendedType.setCategory(Category.INTERFACE);
			reference = TranslateHelper.createRef(extendedType, source);
			jstType.addSatisfy(reference);
			//Add it to needs if not there already
			jstType.addImport(extendedType);
			break;
		case MODULE:
			//extendedType.setCategory(Category.INTERFACE);
			reference = TranslateHelper.createRef(extendedType, source);
			jstType.addExpects(reference);
			break;

		}
	}
	
	public void processMixins(MessageSend expr, JstType jstType) {
		assert (expr != null): "Error parsing mixin";
		boolean isError = false;
		if (expr.getArguments() != null) {
			IExpression argExpr = expr.getArguments()[0];
			if (isString(argExpr)) {
				IJstTypeReference mixinType = getType(m_ctx, (Literal) argExpr);
				if(mixinType!=null){
//					((JstType)mixinType.getReferencedType()).setCategory(Category.MODULE);
					jstType.addMixin(mixinType);
				}
			} else if (isArray(argExpr)) {
				ArrayInitializer mixinExpr = (ArrayInitializer) argExpr;
				for (Expression e : mixinExpr.expressions) {
					if (isString(e)) {
						IJstTypeReference mixinType = getType(m_ctx, (Literal) e);
						jstType.addMixin(mixinType);
					} else {
						isError = true;
					}
				}
			} else {
				isError = true;
			}
			
		} else {
			isError = true;
		}
		
		if (isError) {
			System.err.println("Error while proccesing mixin statement: " + expr.toString());
			//throw new RuntimeException("Unable to process mixin statement: " + expr.toString());
		}
	}
	
	public void processNeeds(IExpression[] params, JstType jstType) {
		assert params != null;
		if (params != null && params.length > 0) {
			if (isString(params[0])) {
				Literal literal = (Literal) params[0];
				JstTypeReference type = getType(m_ctx, literal);
				if (type != null) {
					if (params.length > 1 && isString(params[1])) {
						literal = (Literal) params[1];
						String alias = JstUtil.getCorrectName(literal);
						jstType.addImport(alias, type);
					} else {
						jstType.addImport(type);
					}
				}
			} else if (isArray(params[0])) {
				ArrayInitializer param = (ArrayInitializer) params[0];
				//When syntax error: needs([]''), param.expressions == null
				//See:SquareBracketCompletionTest.testSbInNeedsFromParser()
				if (param.expressions != null) {
					for (IExpression ast : param.expressions) {
						if (ast instanceof StringLiteral || ast instanceof CharLiteral) {
							JstTypeReference type = getType(m_ctx, (Literal) ast);
							if (type != null) {
								jstType.addImport(type);
							}
						}
					}
				} else {
					System.err.println("Error while proccesing needs statement");
				}
			}
		} else {
			System.err.println("Error while proccesing needs statement");
		}

	}


	private JstTypeReference getType(TranslateCtx m_ctx, Literal literal) {
		assert literal != null;
		

		
		String typeName = JstUtil.getCorrectName(literal);
		
		if(typeName.equals("")){
			return null;
		}
		
		// no beginLine and beginColumn in literal object => they are hardcoded
		// to -1
//		JstSource source = TranslateHelper.createJstSource(m_ctx.getSourceUtil(), typeName
//				.length(), literal.sourceStart + 1, literal.sourceStart
//				+ typeName.length());
		JstSource source = getTypeNameSource(literal, typeName
				.lastIndexOf("."), typeName.length());
		IJstType type = TranslateHelper.getJstType(TranslateHelper.findType(m_ctx, typeName));
		JstTypeReference reference = TranslateHelper.createRef(type, source);

		return reference;
	}
	
	private JstSource getTypeNameSource(Expression expression, 
			int lastDotIdx, int length) {
		
		JstSource source;
		
		if (expression instanceof MessageSend) {
			MessageSend messageSendExpression = (MessageSend)expression;
			
			if (messageSendExpression.arguments != null
					&& messageSendExpression.arguments.length > 0) {
				expression = messageSendExpression.arguments[0];
			}
			else {
				source =TranslateHelper.createJstSource(m_ctx.getSourceUtil(), 0,
						messageSendExpression.sourceEnd,
						messageSendExpression.sourceEnd);
				return source;
			}
		}
		
		if (lastDotIdx != -1) {
			length = length - lastDotIdx - 1;
		}
		int beginOffset = (expression.sourceStart() + 1) + lastDotIdx + 1;
		source = TranslateHelper.createJstSource(m_ctx.getSourceUtil(),  length, beginOffset,
				beginOffset + length - 1);
		return source;
	}

	private JstSource getPackageSource(Expression expression, int lastDotIdx) {
		int len;
		if (lastDotIdx > 0) {
			len = lastDotIdx;
		} else {
			len = 0;
		}
		int endOffset = len > 0 ? expression.sourceStart + len
				: expression.sourceStart + 1;
		return TranslateHelper.createJstSource(m_ctx.getSourceUtil(), len,
				expression.sourceStart + 1, endOffset);
	
	}

//	class FullImage extends EcmaScriptVisitorAdapter {
//		StringBuilder fullImage = new StringBuilder();
//
//		@Override
//		protected void pre(SimpleNode node, Object data) {
//			if (node instanceof ASTCompositeReference) {
//				super.pre(node, data);
//			} else if (node instanceof ASTIdentifier) {
//				super.pre(node, data);
//			} else {
//				fullImage.append(node.getBeginToken().image);
//				super.pre(node, data);
//			}
//		}
//	}
}
