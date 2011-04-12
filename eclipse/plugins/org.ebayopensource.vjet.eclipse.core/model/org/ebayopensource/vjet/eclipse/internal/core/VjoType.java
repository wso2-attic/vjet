/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
///**
// * 
// */
//package org.ebayopensource.vjet.eclipse.internal.core;
//
//import java.io.InputStream;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.eclipse.core.runtime.IProgressMonitor;
//import org.eclipse.dltk.mod.core.CompletionRequestor;
//import org.eclipse.dltk.mod.core.IField;
//import org.eclipse.dltk.mod.core.IMethod;
//import org.eclipse.dltk.mod.core.IModelElement;
//import org.eclipse.dltk.mod.core.IModelElementVisitor;
//import org.eclipse.dltk.mod.core.IProjectFragment;
//import org.eclipse.dltk.mod.core.IScriptFolder;
//import org.eclipse.dltk.mod.core.IScriptProject;
//import org.eclipse.dltk.mod.core.ISourceModule;
//import org.eclipse.dltk.mod.core.IType;
//import org.eclipse.dltk.mod.core.ITypeHierarchy;
//import org.eclipse.dltk.mod.core.ModelException;
//import org.eclipse.dltk.mod.core.WorkingCopyOwner;
//import org.eclipse.dltk.mod.internal.core.IJSInitializer;
//
//import org.ebayopensource.dsf.jst.IJstMethod;
//import org.ebayopensource.dsf.jst.IJstNode;
//import org.ebayopensource.dsf.jst.IJstProperty;
//import org.ebayopensource.dsf.jst.IJstType;
//import org.ebayopensource.dsf.jst.declaration.JstArg;
//import org.ebayopensource.dsf.jst.declaration.JstMethod;
//import org.ebayopensource.dsf.jst.declaration.JstProperty;
//import org.ebayopensource.dsf.jst.token.IStmt;
//import org.ebayopensource.vjet.eclipse.core.IJSMethod;
//import org.ebayopensource.vjet.eclipse.core.IJSType;
//
///**
// * 
// * 
// */
//public class VjoType extends VjoMember implements IJSType {
//
//	public VjoType(VjoModelElement parent, IJstType jstType) {
//		super(parent, jstType);
//	}
//
//	public VjoType(VjoModelElement parent, String name) {
//		super(parent, name);
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see org.eclipse.dltk.mod.core.IType#codeComplete(char[], int, int, char[][],
//	 *      char[][], int[], boolean, org.eclipse.dltk.mod.core.CompletionRequestor)
//	 */
//	public void codeComplete(char[] snippet, int insertion, int position,
//			char[][] localVariableTypeNames, char[][] localVariableNames,
//			int[] localVariableModifiers, boolean isStatic,
//			CompletionRequestor requestor) throws ModelException {
//		// TODO Auto-generated method stub
//
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see org.eclipse.dltk.mod.core.IType#codeComplete(char[], int, int, char[][],
//	 *      char[][], int[], boolean, org.eclipse.dltk.mod.core.CompletionRequestor,
//	 *      org.eclipse.dltk.mod.core.WorkingCopyOwner)
//	 */
//	public void codeComplete(char[] snippet, int insertion, int position,
//			char[][] localVariableTypeNames, char[][] localVariableNames,
//			int[] localVariableModifiers, boolean isStatic,
//			CompletionRequestor requestor, WorkingCopyOwner owner)
//			throws ModelException {
//		// TODO Auto-generated method stub
//
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see org.eclipse.dltk.mod.core.IType#findMethods(org.eclipse.dltk.mod.core.IMethod)
//	 */
//	public IMethod[] findMethods(IMethod method) {
//		List<? extends IJstMethod> jstMethods = jstType().getMethods();
//
//		String elementName = method.getElementName();
//		String[] parameters;
//		try {
//			parameters = method.getParameters();
//		} catch (ModelException e) {
//			parameters = new String[0];
//			e.printStackTrace();
//		}
//		ArrayList<IMethod> list = new ArrayList<IMethod>();
//		for (IJstMethod jstMethod : jstMethods) {
//			if (elementName.equals(jstMethod.getName().getName())) {
//				List<JstArg> args = jstMethod.getArgs();
//				if (parameters.length == args.size()) {
//					boolean isArgNamesEquals = false;
//					for (int i = 0; i < parameters.length; i++) {
//						if (parameters[i].equals(args.get(0).getName())) {
//							isArgNamesEquals = true;
//						}
//					}
//					if (isArgNamesEquals) {
//						list.add(new VjoMethod(this, jstMethod));
//					}
//				}
//			}
//		}
//		return list.toArray(new IMethod[list.size()]);
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see org.eclipse.dltk.mod.core.IType#getField(java.lang.String)
//	 */
//	public IField getField(String name) {
//		IJstProperty jstProperty = jstType().getProperty(name);
//
//		IField field;
//		if (jstProperty != null) {
//			field = new VjoField(this, jstProperty);
//		} else {
//			field = new VjoField(this, name);
//		}
//		return field;
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see org.eclipse.dltk.mod.core.IType#getFields()
//	 */
//	public IField[] getFields() throws ModelException {
//		List<IJstProperty> jstProperties = jstType().getProperties();
//
//		IField[] fields = new IField[jstProperties.size()];
//		int idx = 0;
//		for (IJstProperty jstProprty : jstProperties) {
//			fields[idx] = new VjoField(this, jstProprty);
//			idx++;
//		}
//
//		return fields;
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see org.eclipse.dltk.mod.core.IType#getFullyQualifiedName(java.lang.String)
//	 */
//	public String getFullyQualifiedName(String enclosingTypeSeparator) {
//		return jstType().getName();
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see org.eclipse.dltk.mod.core.IType#getFullyQualifiedName()
//	 */
//	public String getFullyQualifiedName() {
//		return getFullyQualifiedName("$");
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see org.eclipse.dltk.mod.core.IType#getMethod(java.lang.String)
//	 */
//	public IMethod getMethod(String name) {
//		IJstMethod jstMethod = jstType().getMethod(name);
//
//		IMethod method;
//		if (jstMethod != null) {
//			method = new VjoMethod(this, jstMethod);
//		} else {
//			method = new VjoMethod(this, name);
//		}
//		return method;
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see org.eclipse.dltk.mod.core.IType#getMethods()
//	 */
//	public IMethod[] getMethods() throws ModelException {
//		List<? extends IJstMethod> jstMethods = jstType().getMethods();
//
//		IMethod[] methods = new IMethod[jstMethods.size()];
//		int idx = 0;
//		for (IJstMethod jstMethod : jstMethods) {
//			methods[idx] = new VjoMethod(this, jstMethod);
//			idx++;
//		}
//
//		return methods;
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see org.eclipse.dltk.mod.core.IType#getScriptFolder()
//	 */
//	public IScriptFolder getScriptFolder() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see org.eclipse.dltk.mod.core.IType#getSuperClasses()
//	 */
//	public String[] getSuperClasses() throws ModelException {
//		List<? extends IJstType> extendedTypes = jstType().getExtends();
//
//		String[] superClasses = new String[extendedTypes.size()];
//		int idx = 0;
//		for (IJstType type : extendedTypes) {
//			superClasses[idx] = type.getName();
//			idx++;
//		}
//		return superClasses;
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see org.eclipse.dltk.mod.core.IType#getType(java.lang.String)
//	 */
//	public IType getType(String name) {
//		// TODO use typespace here to find existing type???
//		return new VjoType(this, name);
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see org.eclipse.dltk.mod.core.IType#getTypeQualifiedName()
//	 */
//	public String getTypeQualifiedName() {
//		return getTypeQualifiedName("$");
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see org.eclipse.dltk.mod.core.IType#getTypeQualifiedName(java.lang.String)
//	 */
//	public String getTypeQualifiedName(String enclosingTypeSeparator) {
//		return getElementName();
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see org.eclipse.dltk.mod.core.IType#getTypes()
//	 */
//	public IType[] getTypes() throws ModelException {
//		// add internal types if necessary
//		return new IType[0];
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see org.eclipse.dltk.mod.core.IType#loadTypeHierachy(java.io.InputStream,
//	 *      org.eclipse.core.runtime.IProgressMonitor)
//	 */
//	public ITypeHierarchy loadTypeHierachy(InputStream input,
//			IProgressMonitor monitor) throws ModelException {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see org.eclipse.dltk.mod.core.IType#newSupertypeHierarchy(org.eclipse.core.runtime.IProgressMonitor)
//	 */
//	public ITypeHierarchy newSupertypeHierarchy(IProgressMonitor monitor)
//			throws ModelException {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see org.eclipse.dltk.mod.core.IType#newSupertypeHierarchy(org.eclipse.dltk.mod.core.ISourceModule[],
//	 *      org.eclipse.core.runtime.IProgressMonitor)
//	 */
//	public ITypeHierarchy newSupertypeHierarchy(ISourceModule[] workingCopies,
//			IProgressMonitor monitor) throws ModelException {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see org.eclipse.dltk.mod.core.IType#newSupertypeHierarchy(org.eclipse.dltk.mod.core.WorkingCopyOwner,
//	 *      org.eclipse.core.runtime.IProgressMonitor)
//	 */
//	public ITypeHierarchy newSupertypeHierarchy(WorkingCopyOwner owner,
//			IProgressMonitor monitor) throws ModelException {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see org.eclipse.dltk.mod.core.IType#newTypeHierarchy(org.eclipse.dltk.mod.core.IScriptProject,
//	 *      org.eclipse.core.runtime.IProgressMonitor)
//	 */
//	public ITypeHierarchy newTypeHierarchy(IScriptProject project,
//			IProgressMonitor monitor) throws ModelException {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see org.eclipse.dltk.mod.core.IType#newTypeHierarchy(org.eclipse.dltk.mod.core.IScriptProject,
//	 *      org.eclipse.dltk.mod.core.WorkingCopyOwner,
//	 *      org.eclipse.core.runtime.IProgressMonitor)
//	 */
//	public ITypeHierarchy newTypeHierarchy(IScriptProject project,
//			WorkingCopyOwner owner, IProgressMonitor monitor)
//			throws ModelException {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see org.eclipse.dltk.mod.core.IType#newTypeHierarchy(org.eclipse.core.runtime.IProgressMonitor)
//	 */
//	public ITypeHierarchy newTypeHierarchy(IProgressMonitor monitor)
//			throws ModelException {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see org.eclipse.dltk.mod.core.IType#newTypeHierarchy(org.eclipse.dltk.mod.core.ISourceModule[],
//	 *      org.eclipse.core.runtime.IProgressMonitor)
//	 */
//	public ITypeHierarchy newTypeHierarchy(ISourceModule[] workingCopies,
//			IProgressMonitor monitor) throws ModelException {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see org.eclipse.dltk.mod.core.IType#newTypeHierarchy(org.eclipse.dltk.mod.core.WorkingCopyOwner,
//	 *      org.eclipse.core.runtime.IProgressMonitor)
//	 */
//	public ITypeHierarchy newTypeHierarchy(WorkingCopyOwner owner,
//			IProgressMonitor monitor) throws ModelException {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see org.eclipse.dltk.mod.core.IModelElement#accept(org.eclipse.dltk.mod.core.IModelElementVisitor)
//	 */
//	public void accept(IModelElementVisitor visitor) throws ModelException {
//		if (visitor.visit(this)) {
//			IModelElement[] elements = getChildren();
//			for (int i = 0; i < elements.length; ++i) {
//				elements[i].accept(visitor);
//			}
//		}
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see org.eclipse.dltk.mod.core.IModelElement#getElementType()
//	 */
//	public int getElementType() {
//		return TYPE;
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see org.eclipse.dltk.mod.core.IParent#getChildren()
//	 */
//	public IModelElement[] getChildren() throws ModelException {
//		List<? extends IJstNode> jstChildren = jstType().getChildren();
//		List<IModelElement> children = new ArrayList<IModelElement>();
//		for (IJstNode child : jstChildren) {
//			if (child instanceof JstMethod) {
//				children.add(new VjoMethod(this, (JstMethod) child));
//			} else if (child instanceof JstProperty) {
//				children.add(new VjoField(this, (JstProperty) child));
//			}
//			// TODO what about initializers???
//		}
//		return children.toArray(new IModelElement[children.size()]);
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see org.eclipse.dltk.mod.core.IParent#hasChildren()
//	 */
//	public boolean hasChildren() throws ModelException {
//		return jstType().getMethods().size() > 0
//				|| jstType().getProperties().size() > 0;
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see org.ebayopensource.vjet.eclipse.internal.core.VjoModelElement#getHandleMementoDelimiter()
//	 */
//	protected char getHandleMementoDelimiter() {
//		return JEM_TYPE;
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see org.ebayopensource.vjet.eclipse.core.IJSType#getInitializer(int)
//	 */
//	public IJSInitializer getInitializer(int occurrenceCount) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see org.ebayopensource.vjet.eclipse.core.IJSType#getInitializers()
//	 */
//	public IJSInitializer[] getInitializers() throws ModelException {
//		List<IStmt> initializers = jstType().getInstanceInitializers();
//		initializers.addAll(jstType().getStaticInitializers());
//
//		// TODO implement this
//		for (IStmt initializer : initializers) {
//
//		}
//		return null;
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see org.ebayopensource.vjet.eclipse.core.IJSType#getMethod(java.lang.String,
//	 *      java.lang.String[])
//	 */
//	public IJSMethod getMethod(String name, String[] parameterTypeSignatures) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see org.ebayopensource.vjet.eclipse.core.IJSType#getProjectFragment()
//	 */
//	public IProjectFragment getProjectFragment() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see org.ebayopensource.vjet.eclipse.core.IJSType#getSuperInterfaceNames()
//	 */
//	public String[] getSuperInterfaceNames() throws ModelException {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see org.ebayopensource.vjet.eclipse.core.IJSType#isClass()
//	 */
//	public boolean isClass() throws ModelException {
//		return !jstType().isInterface() && !isEnum();
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see org.ebayopensource.vjet.eclipse.core.IJSType#isEnum()
//	 */
//	public boolean isEnum() throws ModelException {
//		return jstType().isEnum();
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see org.ebayopensource.vjet.eclipse.core.IJSType#isInterface()
//	 */
//	public boolean isInterface() throws ModelException {
//		return jstType().isInterface();
//	}
//
//	private IJstType jstType() {
//		return (IJstType) m_jstNode;
//	}
//}
