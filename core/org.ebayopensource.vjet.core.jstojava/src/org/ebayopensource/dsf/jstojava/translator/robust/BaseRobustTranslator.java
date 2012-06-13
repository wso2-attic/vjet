/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.translator.robust;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.ebayopensource.dsf.jst.JstSource;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.dsf.jst.meta.IJsCommentMeta;
import org.ebayopensource.dsf.jstojava.translator.BaseTranslator;
import org.ebayopensource.dsf.jstojava.translator.TranslateCtx;
import org.ebayopensource.dsf.jstojava.translator.TranslateHelper;
import org.ebayopensource.dsf.jstojava.translator.robust.completion.JstCommentUtil;
import org.ebayopensource.dsf.jstojava.translator.robust.completion.JstCompletion;
import org.ebayopensource.dsf.jstojava.translator.robust.completion.JstKeywordCompletion;
import org.ebayopensource.dsf.jstojava.translator.robust.completion.JstSyntaxError;
import org.ebayopensource.dsf.jstojava.translator.robust.completion.JstTypeCompletion;
import org.eclipse.mod.wst.jsdt.core.ast.IProgramElement;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.ASTNode;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.AllocationExpression;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.Argument;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.Expression;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.Literal;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.MessageSend;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.SingleNameReference;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.StringLiteral;

class IntegerHolder {

	private int value = -1;

	IntegerHolder(int value) {
		wrap(value);
	}

	int value() {
		return value;
	}

	void wrap(int value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}
	
	

}

abstract class BaseRobustTranslator implements IRobustTranslator {

	private static final char DOT = '.';
	
	private static final String LT = "<";

	private static final String GT = ">";

	private static final String DOUBLE_SLASH = "//";

	protected static final String EMPTY = "";

	protected Stack<IProgramElement> astElements;
	protected JstType jst;

	protected IProgramElement current;
	protected BaseTranslator weakTranslator;

	protected IntegerHolder completionPos;

	protected final TranslateCtx m_ctx;
	public static final List<String> ALLOWED_KEYWORDS = new ArrayList<String>();

	BaseRobustTranslator(TranslateCtx ctx) {
		m_ctx = ctx;
	}

	protected JstSource createSource(JstSourceUtil util, int start, int end, String decl) {
		return TranslateHelper.createJstSource(util, decl.length(), start, end);
	}

//	protected abstract Map<String, Class> getTranslatorMap();

	protected JstTypeCompletion createOnTypeCompletion() {
		return new JstTypeCompletion(jst);
	}

	protected final boolean checkOnTypeCompletion() {

		boolean onTypeCompletion = false;

		if (current instanceof MessageSend
				&& ((MessageSend) current).arguments != null) {

			Expression[] expArgs = ((MessageSend) current).arguments;
			
			for (Expression expression : expArgs) {
				onTypeCompletion = checkOnTypeCompletion(onTypeCompletion, expression);	
			}
			

		} // if (current instanceof MessageSend) {

		checkOnNameCompletion();

		return onTypeCompletion;

	}

	private boolean checkOnTypeCompletion(boolean onTypeCompletion,
			Expression expArg) {
		if (expArg instanceof StringLiteral) {

			StringLiteral arg = (StringLiteral) expArg;

			int startPos = arg.sourceStart() + 1;

			onTypeCompletion = completionPos.value() >= startPos
					&& completionPos.value() <= arg.sourceEnd();

			if (onTypeCompletion) {

				String composite = new String(arg.source());
				int prefix = completionPos.value() - startPos;

				// completion on type has been detected

				setOnTypeCompletion(startPos, arg.sourceEnd(), composite,
						composite.substring(0, prefix));

			} // end if(onTypeCompletion)

		} // end if (expArg instanceof StringLiteral) {
		return onTypeCompletion;
	}

	protected void checkOnNameCompletion() {

		if (current != null ) {

			String token = getToken(current);
			if(token!=null){
			
				int startPos = getStartPos(current, token);
				int endPos = startPos + token.length();
				boolean onNameCompletion = completionPos.value() >= startPos
					&& completionPos.value() <= endPos;

				if (onNameCompletion) {
					String composite = token;
					int prefix = completionPos.value() - startPos;

					setOnNameCompletion(startPos, endPos, composite, composite
						.substring(0, prefix));
				}
			}
		}
	}

	private String getToken(IProgramElement element) {

		String token = null;

		if (element instanceof MessageSend) {
			MessageSend send = (MessageSend) element;
			token = String.valueOf(send.selector);
		}

		if (element instanceof SingleNameReference) {
			SingleNameReference reference = (SingleNameReference) element;
			token = String.valueOf(reference.token);
		}

		return token;
	}

	private int getStartPos(IProgramElement send, String token) {
		char[] cs = weakTranslator.getCtx().getOriginalSource();
		String s = String.valueOf(cs, send.sourceStart(), send.sourceEnd()
				- send.sourceStart() + 1);
		return s.indexOf(token) + send.sourceStart();
	}

	private void setOnNameCompletion(int start, int end, String composite,
			String token) {

		JstTypeCompletion onTypeCompletion = createOnNameCompletion();
		onTypeCompletion.setSource(createSource(m_ctx.getSourceUtil(),start, end,
				"JstOnTypeCompletion"));
		onTypeCompletion.setToken(token);
		onTypeCompletion.setCompositeToken(composite);

		onTypeCompletion.setParent(jst);

		completionPos.wrap(-1); // zeroize completion position

	}

	protected JstTypeCompletion createOnNameCompletion() {
		return new JstTypeCompletion(jst);
	}

	protected void analyzeErrorChunk(ErrorChunk errorChunk) {

		final String composite = errorChunk.toString();

		// Examples
		// 1) vjo.type("com.a.B").inhe<CUR> -- possible completion
		// 2) vjo.type("com.a.B").sat.inf<CUR> -- only errors
		// 3) vj<CUR> -- possible completion

		int errorIndex = 0;
		for (IProgramElement error : errorChunk.getErrorChunk()) {

			errorIndex++;

			String errorToken = TranslateHelper.getStringToken(error);

			int start = getStartOffset(error, errorToken);
			int end = error.sourceEnd() + 1;
			int prefix = completionPos.value() - start;

			// ignore all errors except the first one
			if (errorIndex == 1 && completionPos.value() >= start
					&& completionPos.value() <= end &&
					prefix <= errorToken.length()) {

//				int prefix = completionPos.value() - start;
				String sub = errorToken.substring(0, prefix);

				List<String> completions = new ArrayList<String>();
				for (String allowed : getAllowedTokens()) {
					if (allowed.startsWith(sub)) {
						completions.add(allowed);
					}
				}

				setKeywordCompletion(start, error.sourceEnd() + 1, composite,
						sub, (String[]) completions.toArray(new String[] {}));

			} else {

				setSyntaxError(start, error.sourceEnd(), composite, errorToken);

			}

		} // end cycle

	}

	private int getStartOffset(IProgramElement error, String errorToken) {
		int offset = error.sourceEnd() - errorToken.length() + 1;
		if (error instanceof MessageSend) {
			char[] source = weakTranslator.getCtx().getOriginalSource();
			String content = String.valueOf(source);
			int pos = this.completionPos.value();
			if (pos != -1 && pos < content.length()) {
				int position = content.lastIndexOf(errorToken, pos);
				if (position != -1) {
					offset = position;
				}
			}
		}
		return offset;
	}

	protected final void setOnTypeCompletion(int start, int end,
			String composite, String token) {

		JstTypeCompletion onTypeCompletion = createOnTypeCompletion();
		onTypeCompletion.setSource(createSource(m_ctx.getSourceUtil(),start, end,
				"JstOnTypeCompletion"));
		onTypeCompletion.setToken(token);
		onTypeCompletion.setCompositeToken(composite);
		m_ctx.addSyntaxError(onTypeCompletion);

		onTypeCompletion.setParent(jst);

		completionPos.wrap(-1); // zeroize completion position

	}

	protected final void setSyntaxError(int start, int end, String composite,
			String token) {

		JstSyntaxError error = new JstSyntaxError(jst);
		error.setSource(createSource(m_ctx.getSourceUtil(),start, end, "JstSyntaxError"));
		error.setCompositeToken(composite);
		error.setToken(token);

		error.setParent(jst);
	}

	protected final void setKeywordCompletion(int start, int end,
			String composite, String token, String[] completions) {

		JstCompletion completion = new JstKeywordCompletion(jst, completions);
		completion.setSource(createSource(m_ctx.getSourceUtil(),start, end, "JstKeywordCompletion"));
		completion.setCompositeToken(composite);
		completion.setToken(token);

		completion.setParent(jst);
		completion.setScopeStack(m_ctx.getScopeStack());
		m_ctx.addSyntaxError(completion);

		completionPos.wrap(-1); // zeroize completion position
	}

	protected final void lookupEmptyCompletion() {
		// Check for comment completion first
		if (current instanceof MessageSend) {
			checkForCommentCompletion((ASTNode) current);
			// If it was picked up as comment completion, then it cannot be a 
			// an empty keyword completion
			if (m_ctx.isCreatedCompletion()) {
				return;
			}
		}

		char[] originSrc = weakTranslator.getCtx().getOriginalSource();

		int start, end;

		if (current != null && astElements.empty()) {

			// Examples
			// 1) vjo.<CUR> -- possible completion
			// 2) vjo.type("com.a.B").<CUR> -- possible completion
			// 3) vjo.type("absdafsr")<CUR> -- error only

			if (originSrc.length > (current.sourceEnd() + 1)
					&& hasCompletionDot(originSrc)) {

				start = current.sourceEnd() + 2;
				end = originSrc.length;

				if (completionPos.value() >= start
						&& completionPos.value() <= end) {

					setKeywordCompletion(start, end, EMPTY, EMPTY,
							getAllowedTokens());

				}

			}

		} else if (current != null) {

			// Examples
			// 1) vjo.type("com.a.B").<CUR> .props({}) -- possible completion
			// 2) vjo.type(). \r\n <CUR> \r\n abc -- possible completion

			IProgramElement next = astElements.peek();

			start = current.sourceEnd() + 2;
			String origin = new String(originSrc);
			String token = TranslateHelper.getStringToken(next);
			end = origin.indexOf(token, start);

			if (completionPos.value() >= start && completionPos.value() <= end) {

				setKeywordCompletion(start, end, EMPTY, EMPTY,
						getAllowedTokens());

			}
		}
	}

	private boolean hasCompletionDot(char[] originSrc) {						
		boolean hasDot = false;
		
		int end = completionPos.value();
		int start = current.sourceEnd() + 1;
		
		if (end==-1 || start> end){
			return hasDot;
		}

		for (int i = start; i < end; i++) {
			char c = originSrc[i];

			if (c == DOT) {
				hasDot = true;
				break;
			}

			if (!Character.isWhitespace(c)) {
				break;
			}
		}

		return hasDot;
	}

	protected String[] getAllowedTokens() {
		
		return m_ctx.getSections();
	}

	protected final IRobustTranslator instantiateTranslator(
			IProgramElement element) {

		String token = TranslateHelper.getStringToken(element);

		IRobustTranslator translator = RobustTranslatorFactory
				.createTranslator(m_ctx, m_ctx.getSectionTranslator(token));
		if (translator != null) {
			translator.configure(astElements, jst, weakTranslator,
					completionPos);
		}

		return translator;
	}

	public final void configure(Stack<IProgramElement> astElements,
			JstType jst, BaseTranslator weakTranslator,
			IntegerHolder completionPos) {

		this.astElements = astElements;
		this.jst = jst;
		this.weakTranslator = weakTranslator;
		this.completionPos = completionPos;

	}

	public IErrorCollector getErrorCollector() {

		return new DefaultErrorCollector(current, astElements) {

			public boolean isValidToken(String token) {

				for (String allowedToken : getAllowedTokens()) {
					if (allowedToken.equals(token)) {
						return true;
					}
				}

				for (String allowedToken : ALLOWED_KEYWORDS) {
					if (allowedToken.equals(token)) {
						return true;
					}
				}

				return false;
			}

			public void collect() {

				super.collect();

				analyzeErrorChunk(toErrorChunk());
			}

		};
	}

	public boolean transform() {

		boolean skipTransformation = false;
		
		// if (current != null && current instanceof MessageSend) {
		// Ast2Jst ast2Jst = new Ast2Jst();
		// ast2Jst.transform(current, jst, weakTranslator.getCtx());
		// }

		if (!checkOnTypeCompletion()) {
			processCompletion();
		}

		// lookup possible empty completions
		lookupEmptyCompletion();

		while (!astElements.empty() && !skipTransformation) {

			// get top element from the stack
			IProgramElement nextElement = astElements.peek();
			
			// get sub translator depends of detected element
			IRobustTranslator subTranslator = getSubTranslator(nextElement);

			if (subTranslator != null) {
				skipTransformation = subTranslator.transform();
			} else {
				getErrorCollector().collect();
			}

		}

		return skipTransformation;
	}

	protected void processCompletion() {
		// nothing
	}
	
	protected void checkForCommentCompletion(ASTNode astNode) {
		if (m_ctx.isCreatedCompletion()) {
			return;
		}
		int completionPos = m_ctx.getCompletionPos();
		if (completionPos == -1) {
			return;
		}
		if (astNode instanceof Argument || astNode instanceof SingleNameReference 
			|| astNode instanceof AllocationExpression || astNode instanceof Literal) {
			return; 
		}
		List<IJsCommentMeta> metas = getCommentMeta(astNode);
		for (IJsCommentMeta meta : metas) {
			if (JstCommentUtil.isWithInComment(meta.getBeginOffset(), meta.getEndOffset(), completionPos)) {
				JstCommentUtil.createComentCompletion((ASTNode) astNode, meta, m_ctx);
			}
		}
		
		List<String> comments = 
			JstCommentUtil.getNonMetaComments((ASTNode) astNode, m_ctx);
		for (String comment : comments) {
			if (isCommentMeta(comment)) {
				int beginOffset = m_ctx.getCommentCollector().getCommentNonMetaBeginOffset(comment);
				if (beginOffset == -1) {
					continue;
				}
				if (JstCommentUtil.isWithInComment(beginOffset, beginOffset+comment.length(), completionPos)) {
					JstCommentUtil.createComentCompletion(
							(ASTNode) astNode, comment, beginOffset, beginOffset+comment.length(), m_ctx);
				}
			}
		}
	}
	
	private boolean isCommentMeta(String comment) {
		return comment.contains(DOUBLE_SLASH) && (comment.contains(GT) || comment.contains(LT));
	}

	private List<IJsCommentMeta> getCommentMeta(ASTNode astNode) {
		return m_ctx.getCommentCollector().getCommentMeta(astNode.sourceStart, astNode.sourceEnd,
				m_ctx.getPreviousNodeSourceEnd(), getNextNodeSourceStart(astNode), true);
	}
	
	private int getNextNodeSourceStart(ASTNode astNode) {
		int next = astNode.sourceEnd; 
		if (astElements.size()>0) {
			
			IProgramElement ne = astElements.peek();
			if (ne instanceof MessageSend) {
				char[] selector = ((MessageSend)ne).selector;
				String src = m_ctx.getOriginalSourceAsString();
				if (selector !=null && src !=null) {
					next = src.indexOf(String.valueOf(selector),next);
				}
			}
		}
		return next;
	}

	public IRobustTranslator getSubTranslator(IProgramElement element) {

		IRobustTranslator subTranslator = null;

		if (element instanceof MessageSend) {
			subTranslator = instantiateTranslator(element);
		} else {

		}

		return subTranslator;
	}

}
