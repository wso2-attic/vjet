/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *

 *******************************************************************************/
package org.eclipse.dltk.mod.internal.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Stack;

import org.ebayopensource.vjet.eclipse.compiler.IJSSourceElementRequestor;
import org.ebayopensource.vjet.eclipse.core.IJSSourceModule;
import org.ebayopensource.vjet.eclipse.core.IJSTypeParameter;
import org.eclipse.core.runtime.Assert;
import org.eclipse.dltk.mod.compiler.CharOperation;
import org.eclipse.dltk.mod.core.DLTKCore;
import org.eclipse.dltk.mod.core.IModelElement;
import org.eclipse.dltk.mod.core.ISourceModule;
import org.eclipse.dltk.mod.core.ModelException;

public class VjoSourceModuleStructureRequestor implements
		IJSSourceElementRequestor {

	private final static String[] EMPTY = new String[0];

	/*
	 * Map from ModelElementInfo to of ArrayList of IModelElement representing
	 * the children of the given info.
	 */
	protected HashMap m_children;

	/**
	 * Stack of parent handles, corresponding to the info stack. We keep both,
	 * since info objects do not have back pointers to handles.
	 */
	private Stack m_handleStack;

	protected boolean m_hasSyntaxErrors = false;

	/**
	 * The import container info - null until created
	 */
	protected JSModelElementInfo m_importContainerInfo;

	/**
	 * Stack of parent scope info objects. The info on the top of the stack is
	 * the parent of the next element found. For example, when we locate a
	 * method, the parent info object will be the type the method is contained
	 * in.
	 */
	private Stack m_infoStack;

	/**
	 * The handle to the source module being parsed
	 */
	private ISourceModule m_module;

	/**
	 * The info object for the module being parsed
	 */
	private JSSourceModuleElementInfo m_moduleInfo;

	/**
	 * Hashtable of children elements of the source module. Children are added
	 * to the table as they are found by the parser. Keys are handles, values
	 * are corresponding info objects.
	 */
	private Map m_newElements;

	public VjoSourceModuleStructureRequestor(ISourceModule module,
			JSSourceModuleElementInfo moduleInfo, Map newElements) {
		this.m_module = module;
		this.m_moduleInfo = moduleInfo;
		this.m_newElements = newElements;
	}

	public void acceptFieldReference(char[] fieldName, int sourcePosition) {
	
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ebayopensource.vjet.eclipse.compiler.IJSSourceElementRequestor#acceptImport(int,
	 *      int, char[][], boolean, int)
	 */
	public void acceptImport(int declarationStart, int declarationEnd,
			char[][] tokens, boolean onDemand, int modifiers) {
		ModelElement parentHandle = (ModelElement) this.m_handleStack.peek();
		if (!(parentHandle.getElementType() == IModelElement.SOURCE_MODULE)) {
			Assert.isTrue(false); // Should not happen
		}

		IJSSourceModule parentCU = (IJSSourceModule) parentHandle;
		// create the import container and its info
		ImportContainer importContainer = (ImportContainer) parentCU
				.getImportContainer();
		if (this.m_importContainerInfo == null) {
			this.m_importContainerInfo = new JSModelElementInfo();
			IJSModelElementInfo parentInfo = (IJSModelElementInfo) this.m_infoStack
					.peek();
			// TODO is it the same? addToChildren(parentInfo, importContainer);
			parentInfo.addChild(importContainer);
			this.m_newElements.put(importContainer, this.m_importContainerInfo);
		}

		String elementName = ModelManager.getModelManager().intern(
				new String(CharOperation.concatWith(tokens, '.')));
		ImportDeclaration handle = new ImportDeclaration(importContainer,
				elementName, onDemand);
		resolveDuplicates(handle);

		ImportDeclarationElementInfo info = new ImportDeclarationElementInfo();
		info.setSourceRangeStart(declarationStart);
		info.setSourceRangeEnd(declarationEnd);
		info.setFlags(modifiers);

		// TODO is it the same? addToChildren(this.importContainerInfo, handle);
		m_importContainerInfo.addChild(handle);
		this.m_newElements.put(handle, info);
	}

	public void acceptMethodReference(char[] methodName, int argCount,
			int sourcePosition, int sourceEndPosition) {
	}

	public void acceptPackage(int declarationStart, int declarationEnd,
			char[] name) {
		IJSModelElementInfo parentInfo = (IJSModelElementInfo) this.m_infoStack
				.peek();
		ModelElement parentHandle = (ModelElement) this.m_handleStack.peek();
		PackageDeclaration handle = null;

		// if (parentHandle.getElementType() == IModelElement.SOURCE_MODULE) {
		handle = new JSPackageDeclaration(parentHandle, new String(name));

		this.resolveDuplicates(handle);

		JSSourceRefElementInfo info = new JSSourceRefElementInfo();
		info.setSourceRangeStart(declarationStart);
		info.setSourceRangeEnd(declarationEnd);

		// TODO is it the same? addToChildren(parentInfo, handle);
		parentInfo.addChild(handle);
		this.m_newElements.put(handle, info);
	}

	public void acceptTypeReference(char[] typeName, int sourcePosition) {
		
		
	}

	// public boolean enterFieldCheckDuplicates(FieldInfo fieldInfo,
	// IJSModelElementInfo parentInfo, ModelElement parentHandle) {
	// IModelElement[] childrens = parentInfo.getChildren();
	// for (int i = 0; i < childrens.length; ++i) {
	// if (childrens[i] instanceof SourceField
	// && childrens[i].getElementName().equals(fieldInfo.name)) {
	// // we should go inside existent element
	// SourceField handle = (SourceField) childrens[i];
	// JSSourceFieldElementInfo info = (JSSourceFieldElementInfo)
	// this.newElements
	// .get(handle);
	// this.infoStack.push(info);
	// this.handleStack.push(handle);
	// return true;
	// }
	// }
	// if (parentInfo instanceof JSSourceMethodElementInfo) {
	// JSSourceMethodElementInfo method = (JSSourceMethodElementInfo)
	// parentInfo;
	// String[] args = method.getArgumentNames();
	// for (int i = 0; i < args.length; ++i) {
	// if (args[i].equals(fieldInfo.name)) {
	// return false;
	// }
	// }
	// }
	// this.createField(fieldInfo, parentInfo, parentHandle);
	// return true;
	// }

	public void acceptTypeReference(char[][] typeName, int sourceStart,
			int sourceEnd) {
	}

	private void addToChildren(ModelElementInfo parentInfo, ModelElement handle) {
		ArrayList childrenList = (ArrayList) this.m_children.get(parentInfo);
		if (childrenList == null)
			this.m_children.put(parentInfo, childrenList = new ArrayList());
		childrenList.add(handle);
	}

	private void createField(FieldInfo fieldInfo, MemberElementInfo parentInfo,
			ModelElement parentHandle) {			
		ModelManager manager = ModelManager.getModelManager();
		JSFieldInfo jsFieldInfo = (JSFieldInfo) fieldInfo;		
		SourceField handle = new JSSourceField(parentHandle, manager
				.intern(fieldInfo.name));
		this.resolveDuplicates(handle);
		
		//add by xingzhu, set actual resource
		handle.setResource(jsFieldInfo.resource);

		JSSourceFieldElementInfo info = new JSSourceFieldElementInfo();
		info.setNameSourceStart(fieldInfo.nameSourceStart);
		info.setNameSourceEnd(fieldInfo.nameSourceEnd);
		info.setSourceRangeStart(fieldInfo.declarationStart);
		info.setFlags(fieldInfo.modifiers);
		info.setType(jsFieldInfo.m_type);
		info.setInitializationSource(jsFieldInfo.m_initializationSource);

		parentInfo.addChild(handle);
		this.m_newElements.put(handle, info);

		this.m_infoStack.push(info);
		this.m_handleStack.push(handle);
	}

	public void enterField(FieldInfo fieldInfo) {

		MemberElementInfo parentInfo = (MemberElementInfo) this.m_infoStack
				.peek();
		ModelElement parentHandle = (ModelElement) this.m_handleStack.peek();

		this.createField(fieldInfo, parentInfo, parentHandle);
	}

	public boolean enterFieldCheckDuplicates(FieldInfo fieldInfo) {
		// IJSModelElementInfo parentInfo = (IJSModelElementInfo) this.infoStack
		// .peek();
		// ModelElement parentHandle = (ModelElement) this.handleStack.peek();
		// return this.enterFieldCheckDuplicates(fieldInfo, parentInfo,
		// parentHandle);
		return false;
	}

	public boolean enterFieldWithParentType(FieldInfo info, String parentName,
			String delimiter) {
		// try {
		// ModelElement element = this.getExistentType(parentName, delimiter);
		// if (element == null) {
		// return false;
		// }
		// IJSModelElementInfo typeInfo = (IJSModelElementInfo) element
		// .getElementInfo();
		// this.enterFieldCheckDuplicates(info, typeInfo, element);
		// return true;
		// } catch (ModelException e) {
		// if (DLTKCore.DEBUG) {
		// e.printStackTrace();
		// }
		// }
		return false;
	}

	/**
	 * @see IJSSourceElementRequestor
	 */
	public void enterInitializer(int declarationSourceStart, int modifiers) {
		MemberElementInfo parentInfo = (MemberElementInfo) this.m_infoStack
				.peek();
		ModelElement parentHandle = (ModelElement) this.m_handleStack.peek();
		JSInitializer handle = null;

		if (parentHandle.getElementType() == IModelElement.TYPE) {
			handle = new JSInitializer(parentHandle, 1);
		} else {
			Assert.isTrue(false); // Should not happen
		}
		resolveDuplicates(handle);

		JSInitializerElementInfo info = new JSInitializerElementInfo();
		info.setSourceRangeStart(declarationSourceStart);
		info.setFlags(modifiers);

		parentInfo.addChild(handle);
		// addToChildren(parentInfo, handle);
		this.m_newElements.put(handle, info);

		this.m_infoStack.push(info);
		this.m_handleStack.push(handle);
	}

	public void enterMethod(MethodInfo methodInfo) {
		IJSModelElementInfo parentInfo = (IJSModelElementInfo) this.m_infoStack
				.peek();
		ModelElement parentHandle = (ModelElement) this.m_handleStack.peek();

		this.processMethod(methodInfo, parentInfo, parentHandle);
	}

	public void enterMethodRemoveSame(MethodInfo methodInfo) {
		IJSModelElementInfo parentInfo = (IJSModelElementInfo) this.m_infoStack
				.peek();
		IModelElement[] childrens = parentInfo.getChildren();
		for (int i = 0; i < childrens.length; ++i) {
			if (childrens[i].getElementName().equals(methodInfo.name)) {
				parentInfo.removeChild(childrens[i]);
			}
		}
		this.enterMethod(methodInfo);
	}

	public boolean enterMethodWithParentType(MethodInfo info,
			String parentName, String delimiter) {
		try {
			ModelElement element = this.getExistentType(parentName, delimiter);
			if (element == null) {
				return false;
			}
			IJSModelElementInfo typeInfo = (IJSModelElementInfo) element
					.getElementInfo();

			IModelElement[] childrens = typeInfo.getChildren();
			for (int i = 0; i < childrens.length; ++i) {
				if (childrens[i].getElementName().equals(info.name)) {
					typeInfo.removeChild(childrens[i]);
				}
			}

			this.processMethod(info, typeInfo, element);
			return true;
		} catch (ModelException e) {
			DLTKCore.error(e.toString(), e);
		}
		return false;
	}

	public void enterModule() {
		this.m_infoStack = new Stack();
		this.m_handleStack = new Stack();
		this.m_children = new HashMap();
		this.enterModuleRoot();
	}

	public void enterModuleRoot() {
		this.m_infoStack.push(this.m_moduleInfo);
		this.m_handleStack.push(this.m_module);
	}

	public void enterType(TypeInfo typeInfo) {
		IJSModelElementInfo parentInfo = (IJSModelElementInfo) this.m_infoStack
				.peek();
		ModelElement parentHandle = (ModelElement) this.m_handleStack.peek();
		this.processType(typeInfo, parentInfo, parentHandle);
	}

	public boolean enterTypeAppend(String fullName, String delimiter) {
		try {
			ModelElement element = this.getExistentType(fullName, delimiter);
			if (element == null) {
				return false;
			} else {
				IJSModelElementInfo info = (IJSModelElementInfo) element
						.getElementInfo();
				this.m_infoStack.push(info);
				this.m_handleStack.push(element);
				return true;
			}
		} catch (ModelException e) {
			DLTKCore.error(e.toString(), e);
		}
		return false;
	}

	public void enterTypeParameter(JSTypeParameterInfo typeParameterInfo) {
		IJSModelElementInfo parentInfo = (IJSModelElementInfo) this.m_infoStack
				.peek();
		ModelElement parentHandle = (ModelElement) this.m_handleStack.peek();
		String nameString = new String(typeParameterInfo.name);
		JSTypeParameter handle = new JSTypeParameter(parentHandle, nameString); // NB:
		// occurenceCount
		// is
		// computed
		// in
		// resolveDuplicates
		resolveDuplicates(handle);

		JSTypeParameterElementInfo info = new JSTypeParameterElementInfo();
		info.setSourceRangeStart(typeParameterInfo.declarationStart);
		info.m_nameStart = typeParameterInfo.nameSourceStart;
		info.m_nameEnd = typeParameterInfo.nameSourceEnd;
		info.m_bounds = typeParameterInfo.m_bounds;
		if (parentInfo instanceof JSSourceTypeElementInfo) {
			JSSourceTypeElementInfo elementInfo = (JSSourceTypeElementInfo) parentInfo;
			IJSTypeParameter[] typeParameters = elementInfo.m_typeParameters;
			int length = typeParameters.length;
			System.arraycopy(typeParameters, 0,
					typeParameters = new IJSTypeParameter[length + 1], 0,
					length);
			typeParameters[length] = handle;
			elementInfo.m_typeParameters = typeParameters;
		} else {
			JSSourceMethodElementInfo elementInfo = (JSSourceMethodElementInfo) parentInfo;
			IJSTypeParameter[] typeParameters = elementInfo.typeParameters;
			int length = typeParameters.length;
			System.arraycopy(typeParameters, 0,
					typeParameters = new IJSTypeParameter[length + 1], 0,
					length);
			typeParameters[length] = handle;
			elementInfo.typeParameters = typeParameters;
		}

		this.m_newElements.put(handle, info);
		this.m_infoStack.push(info);
		this.m_handleStack.push(handle);
	}

	public void exitField(int declarationEnd) {
		this.exitMember(declarationEnd);
	}

	/**
	 * @see IJSSourceElementRequestor
	 */
	public void exitInitializer(int declarationEnd) {
		exitMember(declarationEnd);
	}

	protected void exitMember(int declarationEnd) {
		Object object = this.m_infoStack.pop();
		IJSSourceRefModelElementInfo info = (IJSSourceRefModelElementInfo) object;
		info.setSourceRangeEnd(declarationEnd);
		this.m_handleStack.pop();
	}

	public void exitMethod(int declarationEnd) {
		this.exitMember(declarationEnd);
	}

	public void exitModule(int declarationEnd) {
		// set import container children TODO check if necessary
		// if (this.importContainerInfo != null) {
		// setChildren(this.importContainerInfo);
		// }

		// TODO check if this is necessery set children
		// setChildren(this.moduleInfo);

		this.m_moduleInfo.setSourceLength(declarationEnd + 1);

		// determine if there were any parsing errors
		this.m_moduleInfo.setIsStructureKnown(!this.m_hasSyntaxErrors);
	}

	public void exitModuleRoot() {
		this.m_infoStack.pop();
		this.m_handleStack.pop();
	}

	public void exitType(int declarationEnd) {
		this.exitMember(declarationEnd);
	}

	private SourceType findTypeFrom(IModelElement[] children, String name,
			String parentName, String delimiter) {
		try {
			for (int i = 0; i < children.length; ++i) {
				if (children[i] instanceof SourceType) {
					SourceType type = (SourceType) children[i];
					String qname = name + delimiter + type.getElementName();
					if (qname.equals(parentName)) {
						return type;
					}
					SourceType val = this.findTypeFrom(type.getChildren(),
							qname, parentName, delimiter);
					if (val != null) {
						return val;
					}
				}
			}
		} catch (ModelException e) {
			DLTKCore.error(e.toString(), e);
		}
		return null;
	}

	/**
	 * Returns type in which we currently are. If we are not in type, returns
	 * null.
	 * 
	 * @return
	 */
	private SourceType getCurrentType() {
		SourceType t = null;
		for (Iterator iter = this.m_handleStack.iterator(); iter.hasNext();) {
			Object o = iter.next();
			if (o instanceof SourceType) {
				t = (SourceType) o;
			}
		}
		return t;
	}

	/**
	 * Searches for a type already in the model. If founds, returns it. If
	 * <code>parentName</code> starts with a delimeter, searches starting from
	 * current source module (i.e. in global), else from the current level.
	 * 
	 * @param parentName
	 * @param delimiter
	 * @return null if type not found
	 */
	private SourceType getExistentType(String parentName, String delimiter) {
		try {
			SourceType element = null;
			if (parentName.startsWith(delimiter)) {
				element = this.findTypeFrom(this.m_module.getChildren(), "",
						parentName, delimiter);
				return element;
			} else {
				parentName = delimiter + parentName;
				SourceType enc = this.getCurrentType();
				if (enc == null) {
					element = this.findTypeFrom(this.m_module.getChildren(),
							"", parentName, delimiter);
				} else {
					element = this.findTypeFrom(enc.getChildren(), "",
							parentName, delimiter);
				}
				return element;
			}

		} catch (ModelException e) {
			DLTKCore.error(e.toString(), e);
		}
		return null;
	}

	private void processMethod(MethodInfo methodInfo,
			IJSModelElementInfo parentInfo, ModelElement parentHandle) {
		JSMethodInfo jsMethodInfo = (JSMethodInfo) methodInfo;

		// translate nulls to empty arrays
		if (jsMethodInfo.m_parameterTypes == null) {
			jsMethodInfo.m_parameterTypes = CharOperation.NO_STRINGS;
		}
		if (methodInfo.parameterNames == null) {
			methodInfo.parameterNames = CharOperation.NO_STRINGS;
		}
		if (methodInfo.exceptionTypes == null) {
			methodInfo.exceptionTypes = CharOperation.NO_STRINGS;
		}

		String nameString = methodInfo.name;
		ModelManager manager = ModelManager.getModelManager();
		// TODO add param types here
		JSSourceMethod handle = null;
		

		if (parentHandle.getElementType() == IModelElement.TYPE || parentHandle.getElementType() == IJSInitializer.ELEMENT_TYPE) {
			if(parentHandle.getElementType()==11){
				parentHandle = (ModelElement)parentHandle.getParent();
			}
			handle = new JSSourceMethod(parentHandle, manager
					.intern(nameString), jsMethodInfo.m_parameterTypes);
		} else {
			Assert.isTrue(false); // Should not happen
		}
		this.resolveDuplicates(handle);

		//add by xingzhu, set the actual resource
		handle.setResource(jsMethodInfo.resource);
		
		JSSourceMethodElementInfo info = new JSSourceMethodElementInfo();
		info.setConstructor(jsMethodInfo.m_isConstructor);
		info.setSourceRangeStart(methodInfo.nameSourceStart);
		info.setFlags(methodInfo.modifiers);
		info.setNameSourceStart(methodInfo.nameSourceStart);
		info.setNameSourceEnd(methodInfo.nameSourceEnd);

		String[] parameterNames = methodInfo.parameterNames == null ? EMPTY
				: methodInfo.parameterNames;

		String[] parameterInitializers = methodInfo.parameterInitializers == null ? EMPTY
				: methodInfo.parameterInitializers;

		if (parameterNames.length != parameterInitializers.length) {
			parameterInitializers = new String[parameterNames.length];
		}

		for (int i = 0, length = parameterNames.length; i < length; i++) {
			if (parameterNames[i] == null) {
				parameterNames[i] = "";
			}
			//String name = manager.intern(parameterNames[i]); fix bug 2206, findbugs warning
			if (parameterInitializers[i] != null) {
				parameterInitializers[i] = manager
						.intern(parameterInitializers[i]);
			}

		}
		info.setArgumentNames(parameterNames);
		info.setArgumentInializers(parameterInitializers);
		info.setReturnType(manager.intern(jsMethodInfo.m_returnType)
				.toCharArray());
		info.setIsVariables(jsMethodInfo.b_isVariables);
		
		parentInfo.addChild(handle);
		this.m_newElements.put(handle, info);
		this.m_infoStack.push(info);
		this.m_handleStack.push(handle);

		// TODO check if this should be here
		if (jsMethodInfo.m_typeParameters != null) {
			for (int i = 0, length = jsMethodInfo.m_typeParameters.length; i < length; i++) {
				JSTypeParameterInfo typeParameterInfo = jsMethodInfo.m_typeParameters[i];
				enterTypeParameter(typeParameterInfo);
				// TODO check this exitMember(typeParameterInfo.declarationEnd);
			}
		}
	}

	private void processType(TypeInfo typeInfo, IJSModelElementInfo parentInfo,
			ModelElement parentHandle) {
		String nameString = typeInfo.name;
		JSTypeInfo jsTypeInfo = (JSTypeInfo) typeInfo;
		JSSourceType handle = new VjoSourceType(parentHandle, nameString); // NB:
		// occurenceCount
		// is
		// computed
		// in
		// resolveDuplicates
		this.resolveDuplicates(handle);

		JSSourceTypeElementInfo info = new JSSourceTypeElementInfo();
		info.setHandle(handle);
		info.setSourceRangeStart(typeInfo.declarationStart);
		info.setFlags(typeInfo.modifiers);
		info.setNameSourceStart(typeInfo.nameSourceStart);
		info.setNameSourceEnd(typeInfo.nameSourceEnd);
		ModelManager manager = ModelManager.getModelManager();
		String[] superclasses = typeInfo.superclasses;
		for (int i = 0, length = superclasses == null ? 0 : superclasses.length; i < length; i++) {
			superclasses[i] = manager.intern(superclasses[i]);
		}
		info.setSuperclassNames(superclasses);
		String[] superinterfaceses = jsTypeInfo.superinterfaces;
		for (int i = 0, length = superinterfaceses == null ? 0
				: superinterfaceses.length; i < length; i++) {
			superinterfaceses[i] = manager.intern(superinterfaceses[i]);
		}
		info.setSuperInterfaceNames(superinterfaceses);
		parentInfo.addChild(handle);
		this.m_newElements.put(handle, info);
		this.m_infoStack.push(info);
		this.m_handleStack.push(handle);
	}

	/**
	 * Resolves duplicate handles by incrementing the occurrence count of the
	 * handle being created until there is no conflict.
	 */
	protected void resolveDuplicates(SourceRefElement handle) {
		while (this.m_newElements.containsKey(handle)) {
			handle.occurrenceCount++;
		}
	}

	private void setChildren(IJSModelElementInfo info) {
		ArrayList childrenList = (ArrayList) this.m_children.get(info);
		if (childrenList != null) {
			int length = childrenList.size();
			IModelElement[] elements = new IModelElement[length];
			childrenList.toArray(elements);
			info.setChildren(elements);
		}
	}
}
