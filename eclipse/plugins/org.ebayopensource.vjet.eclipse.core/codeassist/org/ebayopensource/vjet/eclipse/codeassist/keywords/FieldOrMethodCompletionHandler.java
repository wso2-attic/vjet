/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.codeassist.keywords;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.ebayopensource.dsf.jsgen.shared.ids.ScopeIds;
import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstArg;
import org.ebayopensource.dsf.jstojava.translator.robust.completion.JstCompletion;
import org.ebayopensource.dsf.jstojava.translator.robust.completion.JstFieldOrMethodCompletion;
import org.ebayopensource.vjet.eclipse.codeassist.CodeassistUtils;
import org.ebayopensource.vjet.eclipse.core.IJSMethod;
import org.ebayopensource.vjet.eclipse.core.IJSType;
import org.eclipse.dltk.mod.ast.Modifiers;
import org.eclipse.dltk.mod.compiler.env.ISourceModule;
import org.eclipse.dltk.mod.core.CompletionProposal;
import org.eclipse.dltk.mod.core.DLTKCore;
import org.eclipse.dltk.mod.core.IMethod;
import org.eclipse.dltk.mod.core.IType;
import org.eclipse.dltk.mod.core.ModelException;

/**
 * Provides "new method stub" and "method override" completion proposals
 * 
 * 
 */
public class FieldOrMethodCompletionHandler extends BaseCompletionHandler implements ICompletionHandler {

	private static final String COMMA = ",";
	private static final String TWO_DOT = " : ";
	private static final String OPEN_BRACKET = "[";
	private static final String CLOSE_BRACKET = "]";
	private static final String NEW_ENUM_VALUE = " - new enum value";
	private static final char TAB = '\t';
	private static final char NEWLINE_1 = '\r';
	private static final char NEWLINE_2 = '\n';
	private static final String RN = "\r\n";
	
	public static final String MAIN_METHOD  = "main";

	/* (non-Javadoc)
	 * @see org.ebayopensource.vjet.eclipse.codeassist.keywords.ICompletionHandler#complete(org.eclipse.dltk.mod.compiler.env.ISourceModule, int, org.ebayopensource.dsf.jstojava.translator.robust.completion.JstCompletion, java.util.List)
	 */
	public void complete(ISourceModule module, int position,
			JstCompletion completion, List<CompletionProposal> list) {

//		JstFieldOrMethodCompletion fieldOrMethodCompletion;
//		fieldOrMethodCompletion = (JstFieldOrMethodCompletion) completion;
//		boolean isStaticBlock = fieldOrMethodCompletion.isStatic();
//		String token = completion.getToken();
//		String[] tokens = trimTokens(token.split(":"));
//
//		if (completion.inScope(ScopeIds.VALUES)) {
//			if (tokens.length == 1) {
//				String tokenStr = tokens.length > 0 ? tokens[0] : "";
//				if (tokenStr.length() > 0
//						&& Character.isJavaIdentifierStart(tokenStr.charAt(0))) {
//					proposeNewEnumValue(token, position, list,
//							fieldOrMethodCompletion);
//				}
//			}
//		} else if (inPropsOrProtosBlock(completion)){
//			complete(module, position, list, isStaticBlock, tokens);
//		}
		super.complete(module, position, completion, list);
	}

	private boolean inPropsOrProtosBlock(JstCompletion completion) {
		return completion.inScope(ScopeIds.PROPS) || completion.inScope(ScopeIds.PROTOS);
	}

	private void complete(ISourceModule module, int position,
			List<CompletionProposal> list, boolean isStaticBlock,
			String[] tokens) {
		String token;
		MethodRequestor requestor = null;
		if (tokens.length < 2) {
			String tokenStr = tokens.length > 0 ? tokens[0] : "";
			requestor = new MethodRequestor(tokenStr, false);
			if (tokenStr.length() > 0
					&& Character.isJavaIdentifierStart(tokenStr.charAt(0))) {
				proposeNewFunction(tokenStr, position, list, isStaticBlock);
			}
		} else {
			token = tokens[0];
			if ("function".startsWith(tokens[1])) {
				requestor = new MethodRequestor(token, true);
			}
		}

		if (requestor != null && !isStaticBlock) {
			getSuperMethodsToOverwrite(requestor, module);

			for (IMethod method : requestor.getMethods()) {
				createMethodCompletion(position, list, method);
			}
		}						
		
	}

	/**
	 * populate list with "new method stub" completion proposals
	 * 
	 * @param position - completion position
	 * @param list - {@link List} of completion proposals
	 * @param method - 
	 */
	private void createMethodCompletion(int position,
			List<CompletionProposal> list, IMethod method) {
		String funcName = method.getElementName();
		boolean isConstructor = false;
		int flags = 0;
		
		try {
			flags = method.getFlags();
			isConstructor = method.isConstructor();
		} catch (ModelException e2) {
			DLTKCore.error(e2.toString(), e2);
		}
		if (isConstructor) {
			funcName = "constructs";
		}
		StringBuffer completionText = new StringBuffer(funcName);
		completionText.append(" : function(");
		String[] paramNames = null;
		String[] paramTypes = null;
		String returnType = null;
		String inType = null;
		try {
			paramNames = method.getParameters();
			if (method instanceof IJSMethod) {
				paramTypes = ((IJSMethod) method).getParameterTypes();
				returnType = ((IJSMethod) method).getReturnType();
				inType = ((IJSMethod) method).getDeclaringType()
						.getElementName();
			}
			if (paramNames != null) {
				for (int i = 0; i < paramNames.length; i++) {
					if (i > 0) {
						completionText.append(", ");
					}
					completionText.append(paramNames[i]);
				}
			}
			method.getFlags();
		} catch (ModelException e) {
			DLTKCore.error(e.toString(), e);
		}
		completionText.append(") {");
		completionText.append(NEWLINE_1);
		completionText.append(NEWLINE_2);
		completionText.append(TAB);
		
		completionText.append(getSuperFunctionCall(funcName,
				returnType, paramNames));
		
		completionText.append(NEWLINE_1);
		completionText.append(NEWLINE_2);
		completionText.append("}");

		if(isConstructor){
			returnType = "";
		}
		String comment = generateMethodComment(funcName, returnType,
				paramNames, paramTypes, flags);
		completionText.insert(0, comment);

		CompletionProposal data = CompletionProposal.create(
				CompletionProposal.METHOD_DECLARATION, position);
		data.setName(funcName.toCharArray());
		// TODO getRelevance();
		data.setRelevance(2);
		data.setCompletion(completionText.toString().toCharArray());
		data.setIsConstructor(isConstructor);
		if (paramNames != null) {
			char[][] parameters = createParameters(paramNames);
			data.setParameterNames(parameters);
		}
		data.setFlags(flags);
		data.setModelElement(method);
		data.extraInfo = new MethodCompletionExtraInfo(paramTypes,
				returnType, inType);
		list.add(data);
	}

	protected char[][] createParameters(String[] paramNames) {
		char[][] parameters = new char[paramNames.length][];
		for (int i = 0; i < paramNames.length; i++) {
			parameters[i] = paramNames[i].toCharArray();
		}
		return parameters;
	}

	private void proposeNewFunction(String token, int position,
			List<CompletionProposal> list, boolean isStaticBlock) {

		CompletionProposal data;
		data = createNewFunctionCompletion(token, position, isStaticBlock);
		list.add(data);
		if (MAIN_METHOD.startsWith(token) && isStaticBlock){
			data = createNewFunctionCompletion(MAIN_METHOD, position, true);
			list.add(data);
		}
		
	}

	private CompletionProposal createNewFunctionCompletion(String token,
			int position, boolean isStaticBlock) {
		CompletionProposal data = CompletionProposal.create(
				CompletionProposal.POTENTIAL_METHOD_DECLARATION, position);
		data.setName(token.toCharArray());
		// TODO getRelevance();
		data.setRelevance(2);

		StringBuffer completionText = new StringBuffer(token);
		completionText.append("() {").append(NEWLINE_1).append(NEWLINE_2)
				.append(TAB).append(NEWLINE_1).append(NEWLINE_2).append("}");
		data.setCompletion(completionText.toString().toCharArray());
		data.extraInfo = isStaticBlock;
		return data;
	}

	private void proposeNewEnumValue(String token, int position,
			List<CompletionProposal> list, JstFieldOrMethodCompletion completion) {

		CompletionProposal data = CompletionProposal.create(
				CompletionProposal.KEYWORD, position);
		String value = token + NEW_ENUM_VALUE;
		data.setName(value.toCharArray());
		data.setRelevance(2);
		StringBuffer completionText = new StringBuffer(token);
		if (!completion.getToken().trim().endsWith(":")) {
			completionText.append(TWO_DOT);
		}
		completionText.append(OPEN_BRACKET);
		completionText.append(getParameters(completion));
		completionText.append(CLOSE_BRACKET);
		data.setCompletion(completionText.toString().toCharArray());
		data.extraInfo = completion.isStatic();
		list.add(data);
	}

	private String getParameters(JstFieldOrMethodCompletion completion) {
		IJstType type = completion.getOwnerType();
		IJstMethod constructor = type.getConstructor();
		StringBuffer buffer = new StringBuffer();
		if (constructor != null && constructor.getParamNames() != null) {
			List<JstArg> strings = constructor.getArgs();
			for (int i = 0; i < strings.size(); i++) {
				JstArg s = strings.get(i);
				buffer.append(s.getName());
				if (i < strings.size() - 1) {
					buffer.append(COMMA);
				}
			}

		}
		return buffer.toString();
	}

	private String getSuperFunctionCall(String funcName, String returnType,
			String[] paramNames) {
		StringWriter superCall = new StringWriter();
		if (!"void".equals(returnType)) {
			superCall.append("return ");
		}
		superCall.append("this.base.");
		superCall.append(funcName);
		superCall.append("(");
		for (int i = 0; i < paramNames.length; i++) {
			if (i > 0) {
				superCall.append(", ");
			}
			superCall.append(paramNames[i]);
		}
		superCall.append(");");
		return superCall.toString();
	}

	/**
	 * @param funcName
	 * @param returnType
	 * @param paramNames
	 * @param paramTypes
	 * @param flags
	 * @return {@link String} comment. Example: //> public String bar(int i)
	 */
	private String generateMethodComment(String funcName, String returnType,
			String[] paramNames, String[] paramTypes, int flags) {
		StringBuffer comment = new StringBuffer("//>");
		if ((flags & Modifiers.AccPublic) != 0) {
			comment.append(" public");
		} else if ((flags & Modifiers.AccProtected) != 0) {
			comment.append(" protected");
		}
		if ((flags & Modifiers.AccFinal) != 0) {
			comment.append(" final");
		}
		if ((flags & Modifiers.AccAbstract) != 0) {
			comment.append(" abstract");
		}
		comment.append(" ");
		comment.append(returnType);
		comment.append(" ");
		comment.append(funcName);
		comment.append("(");
		if (paramTypes != null && paramNames != null
				&& paramNames.length == paramTypes.length) {
			appendParameterSignature(comment, paramTypes, paramNames);
		}
		comment.append(')');
		comment.append(NEWLINE_1);
		comment.append(NEWLINE_2);
		return comment.toString();
	}

	public Class getCompletionClass() {
		return JstFieldOrMethodCompletion.class;
	}

	protected class MethodRequestor {
		private List<IMethod> methods = new ArrayList<IMethod>();
		private String namePrefix = "";
		private boolean isExactMatch = false;

		public MethodRequestor(String namePrefix, boolean isExactMatch) {
			if (namePrefix != null) {
				this.namePrefix = namePrefix;
				this.isExactMatch = isExactMatch;
			}
		}

		public void acceptMethod(IMethod method) {
			int flags = 0;
			boolean isConstructor = false;
			try {
				flags = method.getFlags();
				isConstructor = method.isConstructor();
			} catch (ModelException e) {
				DLTKCore.error(e.toString(), e);
			}
			final boolean nameMatch = (isExactMatch) ? method.getElementName()
					.equals(namePrefix) : method.getElementName().startsWith(
					namePrefix);
			if (!methods.contains(method) && nameMatch
					&& (flags & Modifiers.AccPrivate) == 0
					&& acceptStatic(flags) && (flags & Modifiers.AccFinal) == 0
					&& !isConstructor) {
				methods.add(method);
			}
		}

		protected boolean acceptStatic(int flags) {
			return (flags & Modifiers.AccStatic) == 0;
		}

		public List<IMethod> getMethods() {
			return methods;
		}
	}

	protected void getMethods(MethodRequestor requestor, IType type) {
		if (type == null || requestor == null) {
			return;
		}
		try {
			IMethod[] methods;
			methods = type.getMethods();
			for (IMethod method : methods) {
				requestor.acceptMethod(method);
			}
			String[] superClassNames = type.getSuperClasses();
			if (superClassNames != null && superClassNames.length > 0) {
				IType superType = CodeassistUtils.findResourceType(
						(ISourceModule) type.getParent(), superClassNames[0]);
				if (superType != null) {
					getMethods(requestor, superType);
				}
			}
		} catch (ModelException e) {
			DLTKCore.error(e.toString(), e);
		}
	}

	public class MethodCompletionExtraInfo {
		public String[] parameterTypes;
		public String returnType = "$";
		public String inType = "$";

		public MethodCompletionExtraInfo(String[] parameterTypes,
				String returnType, String inType) {
			this.parameterTypes = parameterTypes;
			this.returnType = returnType;
			this.inType = inType;
		}
	}

	protected StringBuffer appendParameterSignature(StringBuffer buffer,
			String[] parameterTypes, String[] parameterNames) {
		if (parameterNames != null) {
			for (int i = 0; i < parameterNames.length; i++) {
				if (i > 0) {
					buffer.append(',');
					buffer.append(' ');
				}
				buffer.append(parameterTypes[i]).append(' ').append(
						parameterNames[i]);
			}
		}
		return buffer;
	}

	protected void getSuperMethodsToOverwrite(MethodRequestor requestor,
			ISourceModule module) {
		IType type = CodeassistUtils.getType(module, null);
		String[] superClassNames;
		String[] superInterfaceNames;

		try {
			superClassNames = type.getSuperClasses();
			if (superClassNames != null && superClassNames.length > 0) {
				type = CodeassistUtils.findResourceType((ISourceModule) type
						.getParent(), superClassNames[0]);
				getMethods(requestor, type);
			}
			if (type instanceof IJSType) {
				superInterfaceNames = ((IJSType) type).getSuperInterfaceNames();
				if (superInterfaceNames != null) {
					for (String name : superInterfaceNames) {
						IType interfaceType = CodeassistUtils.findResourceType(
								(ISourceModule) type.getParent(), name);
						if (interfaceType != null) {
							type = interfaceType;
							getMethods(requestor, type);
						}
					}
				}
			}

		} catch (ModelException e1) {
			DLTKCore.error(e1.toString(), e1);
		}
	}

	protected void requestMethods(MethodRequestor requestor,
			ISourceModule module) {
		IType type = CodeassistUtils.getType(module, null);
		getMethods(requestor, type);
	}

	protected void setParameters(IMethod method, CompletionProposal data) {

		try {

			String[] paramNames = method.getParameters();
			char[][] params = createParameters(paramNames);
			data.setParameterNames(params);

		} catch (ModelException e) {
			DLTKCore.error(e.toString(), e);
		}
	}

	private String[] trimTokens(String[] tokens) {
		List<String> newTokens = new ArrayList<String>();
		for (String string : tokens) {
			string = string.trim();
			if (string.length() > 0) {
				newTokens.add(string);
			}
		}
		return newTokens.toArray(new String[newTokens.size()]);
	}

}
