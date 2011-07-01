/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.codeassist.keywords;

import java.util.ArrayList;
import java.util.List;

import org.ebayopensource.dsf.jsgen.shared.ids.ScopeIds;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.IJstTypeReference;
import org.ebayopensource.dsf.jst.expr.FieldAccessExpr;
import org.ebayopensource.dsf.jst.token.IExpr;
import org.ebayopensource.dsf.jstojava.translator.robust.completion.JstCompletion;
import org.ebayopensource.vjet.eclipse.codeassist.CodeassistUtils;
import org.ebayopensource.vjet.eclipse.codeassist.VjoCompletionEngine;
import org.ebayopensource.vjet.eclipse.core.IJSMethod;
import org.ebayopensource.vjet.eclipse.core.ts.ExpressionListCreator;
import org.ebayopensource.vjet.eclipse.internal.core.util.Util;
import org.ebayopensource.vjo.tool.typespace.TypeSpaceMgr;
import org.eclipse.dltk.mod.ast.Modifiers;
import org.eclipse.dltk.mod.compiler.env.ISourceModule;
import org.eclipse.dltk.mod.core.CompletionProposal;
import org.eclipse.dltk.mod.core.DLTKCore;
import org.eclipse.dltk.mod.core.IField;
import org.eclipse.dltk.mod.core.IMember;
import org.eclipse.dltk.mod.core.IMethod;
import org.eclipse.dltk.mod.core.IModelElement;
import org.eclipse.dltk.mod.core.IType;
import org.eclipse.dltk.mod.core.ModelException;
import org.eclipse.dltk.mod.internal.core.JSSourceField;
import org.eclipse.dltk.mod.internal.core.JSSourceFieldElementInfo;
import org.eclipse.dltk.mod.internal.core.JSSourceType;
import org.eclipse.dltk.mod.internal.core.ModelElementRequestor;
import org.eclipse.dltk.mod.internal.core.NativeVjoSourceModule;
import org.eclipse.dltk.mod.internal.core.SourceModule;

/**
 * Creates object members completions
 * 
 */
public class MethodCompletionHandler extends FieldOrMethodCompletionHandler {

	private List<IType> processedTypes = new ArrayList<IType>();

	protected static class TypeMembersRequestor {

		private List<IMethod> methods = new ArrayList<IMethod>();

		private List<IField> fields = new ArrayList<IField>();

		private String namePrefix = "";

		private boolean isExactMatch = false;

		private IType currentType;

		private JstCompletion completion;

		public TypeMembersRequestor(String namePrefix, boolean isExactMatch,
				IType currentType, JstCompletion completion) {

			if (namePrefix != null) {
				this.namePrefix = namePrefix;
				this.isExactMatch = isExactMatch;
			}
			this.currentType = currentType;
			this.completion = completion;
		}

		public void acceptMember(IMember member) {

			int flags = 0;
			try {
				flags = member.getFlags();
			} catch (ModelException e) {
				DLTKCore.error(e.toString(), e);
			}
			boolean accept = accept(member, flags);

			if (accept) {
				switch (member.getElementType()) {
				case IModelElement.METHOD:
					methods.add((IMethod) member);
					break;
				case IModelElement.FIELD:
					fields.add((IField) member);
					break;
				}
			}
		}

		protected boolean accept(IMember member, int flags) {
			boolean isStatic = isStatic(flags);

			final boolean nameMatch = (isExactMatch) ? member.getElementName()
					.equals(namePrefix) : member.getElementName().startsWith(
					namePrefix);

			final boolean allowUnderScore = allowUnderScore(member);

			final boolean isInCurrentType = currentType.equals(member
					.getDeclaringType());
			final boolean acceptPrivate = (isInCurrentType) ? true
					: (flags & Modifiers.AccPrivate) == 0;

			IModelElement sourceModule = member
					.getAncestor(IModelElement.SOURCE_MODULE);
			boolean isNative = sourceModule instanceof NativeVjoSourceModule;

			boolean isWrongCall = !isNative
					&& !ExpressionCall.isRightCall(completion, isStatic);

			boolean accept = !methods.contains(member) && nameMatch
					&& acceptPrivate && !isWrongCall && allowUnderScore;
			return accept;
		}

		private boolean allowUnderScore(IMember member) {
			return !(member.getElementName().startsWith("_") && !CompletionContext
					.isVariableContext());
		}

		protected boolean isStatic(int flags) {
			return (flags & Modifiers.AccStatic) != 0;
		}

		public List<IMethod> getMethods() {
			return methods;
		}

		public List<IField> getFields() {
			return fields;
		}

		public IType getType() {
			return currentType;
		}
	}

	/* (non-Javadoc)
	 * @see org.ebayopensource.vjet.eclipse.codeassist.keywords.FieldOrMethodCompletionHandler#complete(org.eclipse.dltk.mod.compiler.env.ISourceModule, int, org.ebayopensource.dsf.jstojava.translator.robust.completion.JstCompletion, java.util.List)
	 */
	public void complete(ISourceModule module, int position,
			JstCompletion completion, List<CompletionProposal> list) {
		
		CompletionContext.setStaticContext(completion
				.inScope(ScopeIds.PROPS));
		
		CompletionContext.setInstanceContext(false);
		IModelElement currentElement = getCurrentElement(module, position);
		IMethod currentMethod = getCurrentMethod(currentElement);
		String token = getToken(completion);

		TypeMembersRequestor requestor;
		IType type = getType(module);
		requestor = createTypeMemberRequestor(completion, token, type);
		requestMembers(requestor, module);

		// set completion context

		// Filed bug 1997 29 jun 2009
		IJstNode realParent = completion.getRealParent();
		CompletionContext
				.thisWithinStaticContext(realParent instanceof FieldAccessExpr
						&& ((FieldAccessExpr) realParent).getExpr().toString()
								.equals(VjoKeywordFactory.KWD_THIS.getName()));

		
		CompletionContext.setCompletedContext(isCompletedContext(completion));

		createMethodCompletions(position, list, currentMethod, requestor);
		createFieldCompletions(position, list, requestor);

		// process local vars
		if (currentMethod != null) {
			addLocalVarProposals(currentMethod, token, list, position);
		}

		//Jack: comment these two line, because type proposal can be get from super method
		if (!token.equals("") && completion.getCompositeToken() == null)
			createTypeProposals(module, position, completion, list);
		completePackages(module, position, completion, list);
//		super.complete(module, position, completion, list);
	}

	protected TypeMembersRequestor createTypeMemberRequestor(
			JstCompletion completion, String token, IType type) {
		return new TypeMembersRequestor(token, false, type, completion);
	}

	private void createFieldCompletions(int position,
			List<CompletionProposal> list, TypeMembersRequestor requestor) {
		List<IField> fields = requestor.getFields();

		for (IField field : fields) {
			CompletionProposal data;
			int flags = 0;
			try {
				flags = field.getFlags();
			} catch (ModelException e) {
				DLTKCore.error(e.toString(), e);
			}
			data = createFieldProposal(position, field, flags);
			list.add(data);
		}
	}

	protected CompletionProposal createFieldProposal(int position,
			IField field, int flags) {
		CompletionProposal data;
		String typeName = null;

		data = CompletionProposal
				.create(CompletionProposal.FIELD_REF, position);
		char[] token = field.getElementName().toCharArray();
		data.setCompletion(token);
		data.setName(field.getElementName().toCharArray());
		data.setModelElement(field);
		data.setFlags(flags);

		// add a type
		try {
			JSSourceFieldElementInfo info = (JSSourceFieldElementInfo) ((JSSourceField) field)
					.getElementInfo();
			typeName = info.getType();
		} catch (ModelException e) {
			DLTKCore.error(e.toString(), e);
		}
		data.extraInfo = typeName + " - " + field.getParent().getElementName();

		return data;
	}

	private void createMethodCompletions(int position,
			List<CompletionProposal> list, IMethod currentMethod,
			TypeMembersRequestor requestor) {
		List<IMethod> methods = requestor.getMethods();
		createMethodsProposal(position, list, currentMethod, methods);
	}

	private void createMethodsProposal(int position,
			List<CompletionProposal> list, IMethod currentMethod,
			List<IMethod> methods) {
		for (IMethod method : methods) {
			boolean isConstructor = false;
			try {
				isConstructor = method.isConstructor();
			} catch (ModelException e) {
				DLTKCore.error(e.toString(), e);
			}
			if (/* method.equals(currentMethod) || */isConstructor) {
				continue;
			}

			CompletionProposal data = createMethodProposal(position, method);
			int flags = 0;
			try {
				flags = method.getFlags();
			} catch (ModelException e) {
				DLTKCore.error(e.toString(), e);
			}
			data.setFlags(flags);

			String returnType = null;

			try {
				if (method instanceof IJSMethod) {
					returnType = ((IJSMethod) method).getReturnType();
					data.extraInfo = returnType + " - "
							+ method.getParent().getElementName();
				}
			} catch (ModelException e) {
				DLTKCore.error(e.toString(), e);
			}
			list.add(data);
		}
	}

	protected CompletionProposal createMethodProposal(int position,
			IMethod method) {
		CompletionProposal data;
		data = CompletionProposal.create(CompletionProposal.METHOD_REF,
				position);
		data.setCompletion(method.getElementName().toCharArray());
		data.setName(method.getElementName().toCharArray());
		data.setModelElement(method);
		setParameters(method, data);
		return data;
	}

	protected CompletionProposal createTypeProposal(int position, IType type,
			JstCompletion completion) {

		CompletionProposal data;
		data = CompletionProposal.create(CompletionProposal.TYPE_REF, position);
		data.setCompletion(CodeassistUtils.getTypeCompletionToken(type));
		String name = type.getElementName();
		String packageName = CodeassistUtils.getPackageName(type);
		if (packageName != null && packageName.trim().length() > 0) {
			name = name + " - " + packageName;
		}
		data.setName(name.toCharArray());
		data.setModelElement(type);
		data.extraInfo = completion.getOwnerType();

		return data;
	}

	private String getToken(JstCompletion completion) {
		String token = completion.getToken();
		if (token == null) {
			token = "";
		}
		return token;
	}

	private IModelElement getCurrentElement(ISourceModule module, int position) {
		IModelElement currentElement = null;
		try {
			currentElement = ((org.eclipse.dltk.mod.core.ISourceModule) module)
					.getElementAt(position);
		} catch (ModelException e1) {
			DLTKCore.error(e1.toString(), e1);
		}
		return currentElement;
	}

	private IMethod getCurrentMethod(IModelElement currentElement) {
		IMethod currentMethod = null;
		if (currentElement != null
				&& currentElement.getElementType() == IModelElement.METHOD) {
			currentMethod = (IMethod) currentElement;
		}
		return currentMethod;
	}

	protected void addLocalVarProposals(IMethod method, String token,
			List<CompletionProposal> list, int position) {
		try {
			IModelElement[] children = method.getChildren();
			for (IModelElement child : children) {
				if (child.getElementType() == IModelElement.FIELD
						&& child.getElementName().startsWith(token)) {
					CompletionProposal data;
					data = CompletionProposal.create(
							CompletionProposal.LOCAL_VARIABLE_REF, position);
					data.setCompletion(child.getElementName().toCharArray());
					data.setName(child.getElementName().toCharArray());
					data.setModelElement(child);
					// add the type
					try {
						if (child instanceof JSSourceField) {
							JSSourceFieldElementInfo info = (JSSourceFieldElementInfo) ((JSSourceField) child)
									.getElementInfo();
							String typeName = info.getType();
							data.extraInfo = typeName;
						}
					} catch (ModelException e) {
						DLTKCore.error(e.toString(), e);
					}

					list.add(data);
				}
			}
		} catch (ModelException e) {
			DLTKCore.error(e.toString(), e);
		}
	}

	// private boolean isExtractMatch(String token) {
	// return token!=null && token.length() > 0;
	// }

	public Class getCompletionClass() {
		return null;
	}

	protected void requestMembers(TypeMembersRequestor requestor,
			ISourceModule module) {
		processedTypes.clear();
		IType type = getType(module);
		getMembers(requestor, type);
		type = CodeassistUtils.createNativeType(module, TypeSpaceMgr.WINDOW);
		getMembers(requestor, type);
		type = CodeassistUtils.createNativeType(module, TypeSpaceMgr.GLOBAL);
		getMembers(requestor, type);
		type = CodeassistUtils.createNativeType(module, TypeSpaceMgr.OBJECT);
		getMembers(requestor, type);
		/*
		 * type = CodeassistUtils.createNativeType(module);
		 * getMembers(requestor, type);
		 */
	}

	private void getMembers(TypeMembersRequestor requestor, IType type) {
		getMembers(requestor, type, true);
	}

	protected IType getType(ISourceModule module) {
		return CodeassistUtils.getType(module, null);
	}

	protected void getMembers(TypeMembersRequestor requestor, IType type,
			boolean processDepends) {
		if (type == null || requestor == null) {
			return;
		}
		processedTypes.add(type);
		try {
			IJstType jstType = Util.toJstType(type);

			// TODO why not use jst type instead
			IMethod[] methods = type.getMethods();
			IField[] fields = type.getFields();

			for (IMethod method : methods) {
				requestor.acceptMember(method);
			}
			for (IField field : fields) {
				requestor.acceptMember(field);
			}

			String[] superClassNames = type.getSuperClasses();
			if (superClassNames != null && superClassNames.length > 0) {
				IType superType = CodeassistUtils.findResourceType(
						(ISourceModule) type.getParent(), superClassNames[0]);
				if (superType != null) {
					getDependsMembers(requestor, superType);
				}
			}

			if (processDepends) {

				List<? extends IJstType> imports = jstType.getImports();
				for (IJstType importType : imports) {
					if (importType.getPackage() != null
							&& importType.getPackage().getGroupName() != null
							&& CompletionContext.isStaticContext()) {
						getDependsMembers(requestor, Util.toIType(importType));
					}
				}
				List<? extends IJstTypeReference> mixinTypes = jstType
						.getMixinsRef();
				for (IJstTypeReference mixinType : mixinTypes) {
					getDependsMembers(requestor, Util.toIType(mixinType
							.getReferencedType()));
				}
//				List<? extends IJstTypeReference> staticMixinTypes = jstType
//						.getMixins();
//				for (IJstTypeReference staticMixinType : staticMixinTypes) {
//					getDependsMembers(requestor, Util.toIType(staticMixinType
//							.getReferencedType()));
//				}
				if (type instanceof JSSourceType) {
					String[] interfaces = ((JSSourceType) type)
							.getSuperInterfaceNames();
					for (String string : interfaces) {
						IType superInterface = CodeassistUtils
								.findResourceType((ISourceModule) type
										.getParent(), string);
						if (superInterface != null) {
							getDependsMembers(requestor, superInterface);
						}
					}
				}
			}
		} catch (ModelException e) {
			DLTKCore.error(e.toString(), e);
		}
	}

	private void getDependsMembers(TypeMembersRequestor requestor, IType type) {
		if (!processedTypes.contains(type)) {
			getMembers(requestor, type, false);
		}
	}

	protected void createTypeProposals(ISourceModule module, int position,
			JstCompletion completion, List<CompletionProposal> list) {

		ModelElementRequestor requestor = new ModelElementRequestor();
		CodeassistUtils.findTypes((SourceModule) module, completion.getToken(),
				requestor);

		if (requestor.getTypes() != null && requestor.getTypes().length > 0) {
			for (IType type : requestor.getTypes()) {
				if (VjoCompletionEngine.UNEXIST_TYPES.contains(type.getElementName())) {
					continue;
				}
				list.add(createTypeProposal(position, type, completion));
			}
		}
	}

	private boolean isCompletedContext(JstCompletion completion) {

		boolean isCompletedContext = false;
		IJstNode node = completion.getRealParent();
		List<IExpr> list = ExpressionListCreator.create(node);

		if (list.size() > 1)
			isCompletedContext = list.get(1).toExprText().equals(
					CompletionConstants.THIS_VJO);

		return isCompletedContext;
	}
}
