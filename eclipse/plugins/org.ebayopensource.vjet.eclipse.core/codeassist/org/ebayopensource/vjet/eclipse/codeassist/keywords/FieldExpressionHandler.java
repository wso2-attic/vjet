/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.codeassist.keywords;

import java.util.List;

import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.IJstTypeReference;
import org.ebayopensource.dsf.jstojava.translator.robust.completion.JstCompletion;
import org.ebayopensource.vjet.eclipse.codeassist.CodeassistUtils;
import org.ebayopensource.vjet.eclipse.codeassist.compliance.SupportedByAnnotationAcceptor;
import org.ebayopensource.vjet.eclipse.codeassist.keywords.MethodCompletionHandler.TypeMembersRequestor;
import org.ebayopensource.vjet.eclipse.core.IJSMethod;
import org.ebayopensource.vjet.eclipse.internal.core.util.Util;
import org.ebayopensource.vjo.meta.VjoKeywords;
import org.ebayopensource.vjo.tool.typespace.TypeSpaceMgr;
import org.eclipse.core.resources.IProject;
import org.eclipse.dltk.mod.compiler.env.ISourceModule;
import org.eclipse.dltk.mod.core.CompletionProposal;
import org.eclipse.dltk.mod.core.DLTKCore;
import org.eclipse.dltk.mod.core.IField;
import org.eclipse.dltk.mod.core.IMethod;
import org.eclipse.dltk.mod.core.IModelElement;
import org.eclipse.dltk.mod.core.IType;
import org.eclipse.dltk.mod.core.ModelException;
import org.eclipse.dltk.mod.internal.core.JSSourceField;
import org.eclipse.dltk.mod.internal.core.JSSourceFieldElementInfo;
import org.eclipse.dltk.mod.internal.core.JSSourceMethod;
import org.eclipse.dltk.mod.internal.core.JSSourceType;
import org.eclipse.dltk.mod.internal.core.NativeVjoSourceModule;
import org.eclipse.dltk.mod.internal.core.VjoLocalVariable;

public class FieldExpressionHandler {

	private static final String GLOBAL = TypeSpaceMgr.GLOBAL;

	static FieldExpressionHandler getNonForcedHandler() {
		return new FieldExpressionHandler(false);
	}

	private boolean forceInherits;

	private FieldExpressionHandler(boolean forceInherits) {
		this.forceInherits = forceInherits;
	}

	FieldExpressionHandler() {
		this(true);
	}

	private boolean isAccepted(IJstNode jst,
			SupportedByAnnotationAcceptor acceptor) {
		boolean isAccepted = true;
		if (jst != null) {
			isAccepted = acceptor.accept(jst.getAnnotations());
		}
		return isAccepted;
	}

	public void complete(ISourceModule module, int position,
			JstCompletion completion, List<CompletionProposal> list,
			Object jstNode) {

		String token = completion.getToken();
		String typeName = completion.getOwnerType().getName();
		IType currentType = CodeassistUtils.getCurrentType(module, typeName);
		IType type = getType(module, jstNode, typeName);

		if (type != null) {
			IProject typeProject = type.getParent().getScriptProject()
					.getProject();
			SupportedByAnnotationAcceptor acceptor = SupportedByAnnotationAcceptor
					.getAcceptor(typeProject);

			IJstType nativeJstType = null;
			if (type.getSourceModule() instanceof NativeVjoSourceModule) {
				
				List<IJstType> jstTypes = TypeSpaceMgr.getInstance().findType(
						type.getElementName());
				if(jstTypes != null && jstTypes.size() > 0){
					nativeJstType = jstTypes.get(0);
				}
			}

			TypeMembersRequestor requestor;
			requestor = new MethodCompletionHandler.TypeMembersRequestor(token,
					false, currentType, completion);

			if (forceInherits) {
				getMembers(requestor, type);
			} else {
				getOwnMembers(requestor, type);
			}

			if (isNeededGlobal(type)) {
				IType global;
				global = CodeassistUtils.createNativeType(module, GLOBAL);
				getMembers(requestor, global);
			}

			List<IMethod> methods = requestor.getMethods();

			for (IMethod method : methods) {

				if (nativeJstType != null
						&& !isAccepted(nativeJstType.getMethod(method
								.getElementName()), acceptor)) {
					continue;
				}

				char[] name = method.getElementName().toCharArray();

				boolean isConstructor = false;
				try {
					isConstructor = method.isConstructor();
				} catch (ModelException e) {
					DLTKCore.error(e.toString(), e);
				}
				if (isConstructor && type.equals((method.getParent()))) {
					continue;
				} else if (isConstructor) {
					name = VjoKeywords.BASE.toCharArray();
				}

				CompletionProposal data;
				data = CompletionProposal.create(CompletionProposal.METHOD_REF,
						position);
				data.setCompletion(name);
				data.setName(name);
				data.setModelElement(method);
				setParameters(method, data);
				int flags = 0;
				try {
					flags = method.getFlags();
				} catch (ModelException e) {
					DLTKCore.error(e.toString(), e);
				}
				data.setFlags(flags);
				data.setReplaceRange(position - token.length(), position);

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
			List<IField> fields = requestor.getFields();
			for (IField field : fields) {

				if (nativeJstType != null
						&& !isAccepted(nativeJstType.getProperty(field
								.getElementName()), acceptor)) {
					continue;
				}

				int flags = 0;
				try {
					flags = field.getFlags();
				} catch (ModelException e) {
					DLTKCore.error(e.toString(), e);
				}
				CompletionProposal data;
				data = CompletionProposal.create(CompletionProposal.FIELD_REF,
						position);
				data.setCompletion(field.getElementName().toCharArray());
				data.setName(field.getElementName().toCharArray());
				data.setModelElement(field);
				data.setFlags(flags);
				data.setReplaceRange(position - token.length(), position);

				// add a type
				try {
					JSSourceFieldElementInfo info = (JSSourceFieldElementInfo) ((JSSourceField) field)
							.getElementInfo();
					typeName = info.getType();
				} catch (ModelException e) {
					DLTKCore.error(e.toString(), e);
				}
				data.extraInfo = typeName + " - "
						+ field.getParent().getElementName();

				list.add(data);
			}
		}
		// CompletionContext.isInstanceContext = false;

	}

	private boolean isNeededGlobal(IType type) {
		String name = type.getElementName();
		return TypeSpaceMgr.OBJECT.equals(name)
				|| !TypeSpaceMgr.isNativeGlobalObject(name);
	}

	public static IType getType(ISourceModule module, Object jstNode,
			String typeName) {
		IType type;
		IModelElement element = getElement(module, jstNode);

		// if (element instanceof NativeVjoSourceModule) {
		// NativeVjoSourceModule sourceModule = (NativeVjoSourceModule) element;
		// type = sourceModule.getType();
		// } else
		if (element instanceof JSSourceType) {
			type = (IType) element;
		} else {
			try {
				if (element instanceof JSSourceMethod) {
					typeName = ((JSSourceMethod) element).getReturnType();
				} else if (element instanceof JSSourceField) {
					JSSourceFieldElementInfo info = (JSSourceFieldElementInfo) ((JSSourceField) element)
							.getElementInfo();
					typeName = info.getType();
				} else if (element instanceof VjoLocalVariable) {
					typeName = ((VjoLocalVariable) element).getTypeSignature();
				}
			} catch (Exception e) {
				DLTKCore.error(e.toString(), e);
			}
			IModelElement sm = element;
			if (sm == null) {
				return null;
			}
			sm = sm.getAncestor(IModelElement.SOURCE_MODULE);

			type = CodeassistUtils.findType((ISourceModule) sm, typeName);

			if (type == null) {
				type = CodeassistUtils.getType((ISourceModule) sm, typeName);
			}

			if (type == null) {
				// lets try to find native type
				type = CodeassistUtils.findNativeSourceType((ISourceModule) sm,
						typeName);
			}
		}
		return type;
	}

	private static IModelElement getElement(ISourceModule module, Object jstNode) {
		IModelElement element = null;

		if (element == null) {
			IModelElement[] modelElements = CodeassistUtils
					.resolveQualifiedNameReference(jstNode, module);

			if (modelElements != null && modelElements.length > 0) {
				element = modelElements[0];
			}
		}
		return element;
	}

	public Class getCompletionClass() {
		// TODO Auto-generated method stub
		return null;
	}

	protected void getOwnMembers(TypeMembersRequestor requestor, IType type) {

		if (type == null || requestor == null) {
			return;
		}

		try {

			IMethod[] methods = type.getMethods();
			IField[] fields = type.getFields();

			for (IMethod method : methods) {
				requestor.acceptMember(method);
			}
			for (IField field : fields) {
				requestor.acceptMember(field);
			}

		} catch (ModelException me) {
			DLTKCore.error(me.toString(), me);
		}

	}

	protected void getMembers(TypeMembersRequestor requestor, IType type) {
		if (type == null || requestor == null) {
			return;
		}
		try {

			getOwnMembers(requestor, type);

			String[] superClassNames = type.getSuperClasses();
			if (superClassNames != null && superClassNames.length > 0) {
				IType superType = CodeassistUtils.findResourceType(
						(ISourceModule) type.getParent(), superClassNames[0]);
				if (superType != null) {
					getMembers(requestor, superType);
				}
			}

			IJstType jstType = Util.toJstType(type);
			if (jstType != null) {
				List<? extends IJstTypeReference> mixinTypes = Util.toJstType(
						type).getMixinsRef();
				for (IJstTypeReference mixinType : mixinTypes) {
					getMembers(requestor, Util.toIType(mixinType
							.getReferencedType()));
				}
			}
		} catch (ModelException e) {
			DLTKCore.error(e.toString(), e);
		}
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

	protected char[][] createParameters(String[] paramNames) {
		char[][] parameters = new char[paramNames.length][];
		for (int i = 0; i < paramNames.length; i++) {
			parameters[i] = paramNames[i].toCharArray();
		}
		return parameters;
	}

}
