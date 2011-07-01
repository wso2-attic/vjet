/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/**
 * 
 */
package org.ebayopensource.vjet.eclipse.core.search;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.ebayopensource.dsf.jst.IJstGlobalVar;
import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstProperty;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstArg;
import org.ebayopensource.dsf.jst.declaration.JstMethod;
import org.ebayopensource.dsf.jst.declaration.JstVar;
import org.ebayopensource.dsf.jst.declaration.JstVars;
import org.ebayopensource.dsf.jst.expr.FieldAccessExpr;
import org.ebayopensource.dsf.jst.term.JstIdentifier;
import org.ebayopensource.dsf.jst.traversal.JstDepthFirstTraversal;
import org.ebayopensource.dsf.ts.property.PropertyName;
import org.ebayopensource.dsf.ts.type.TypeName;
import org.ebayopensource.vjet.eclipse.codeassist.CodeassistUtils;
import org.ebayopensource.vjet.eclipse.core.IVjoSourceModule;
import org.ebayopensource.vjet.eclipse.core.VjetPlugin;
import org.ebayopensource.vjo.tool.typespace.TypeSpaceMgr;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.dltk.mod.core.Flags;
import org.eclipse.dltk.mod.core.IField;
import org.eclipse.dltk.mod.core.IModelElement;
import org.eclipse.dltk.mod.core.ISourceRange;
import org.eclipse.dltk.mod.core.IType;
import org.eclipse.dltk.mod.core.ModelException;
import org.eclipse.dltk.mod.core.search.SearchPattern;
import org.eclipse.dltk.mod.internal.core.NativeVjoSourceModule;
import org.eclipse.dltk.mod.internal.core.search.matching.FieldPattern;

/**
 * Search field/variables declaration and references.
 * 
 * 
 * 
 */
class VjoFieldSearcher extends AbstractVjoElementSearcher {
	public static final int	TYPE_PROP	= 2;
	public static final int	LOCAL_VAR	= 1;
	public static final int	METHOD_ARG	= 0;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ebayopensource.vjet.eclipse.core.search.IVjoElementSearcher#getSearchPatternClass()
	 */
	public Class<? extends SearchPattern> getSearchPatternClass() {
		return FieldPattern.class;
	}

	/**
	 * Request local variable references from methods arguments and identifiers.
	 * 
	 * 
	 * 
	 */
	private class LocalVarRefsRequestor {
		private List<IJstNode>	refs	= new LinkedList<IJstNode>();
		private String			name	= null;

		/**
		 * Accept {@link IJstNode} for search local variable references.
		 * 
		 * @param node
		 */
		public void accept(IJstNode node) {
			if (node == null) {
				return;
			}
			// System.out.println(node.toString());
			if (node instanceof JstIdentifier) {
				JstIdentifier identifier = (JstIdentifier) node;
				if (identifier.getName().equals(name)) {
					refs.add(identifier);
				}
			} else if (node instanceof JstVar) {
				// skip declarations
				return;
			} else if (node instanceof JstMethod) {
				List<JstArg> args = ((JstMethod) node).getArgs();
				for (JstArg jstArg : args) {
					if (name.equals(jstArg.getName())) {
						refs.add(jstArg);
					}
				}
			}
			List<? extends IJstNode> children = node.getChildren();
			if (children != null && children.size() > 0) {
				for (IJstNode jstNode : children) {
					if (jstNode instanceof FieldAccessExpr) {
						// for local var, it would not exist in FieldAccessExpr

						continue;
					}
					accept(jstNode);
				}
			}
		}

		/**
		 * Create instance of this class with name of the local variable.
		 * 
		 * @param name
		 *            name of the local variable.
		 */
		public LocalVarRefsRequestor(String name) {
			super();
			this.name = name;
		}

		/**
		 * Returns list of the found local variable references.
		 * 
		 * @return list of the found local variable references.
		 */
		public List<IJstNode> getRefs() {
			return refs;
		}

		/**
		 * Returns name of the local variable.
		 * 
		 * @return name of the local variable.
		 */
		public String getName() {
			return name;
		}
	}

	@Override
	protected void searchDeclarations(SearchQueryParameters params,
			List<VjoMatch> result) {
		IField element = (IField) params.getElement();
		IVjoSourceModule module = (IVjoSourceModule) element.getSourceModule();
		IField field = findFieldDeclaration(element, module);
		TypeName typeName = module.getTypeName();

		IType type = CodeassistUtils.findResourceType(module, typeName
				.typeName());
		if (isInScope(type) && field != null) {
			try {
				ISourceRange nameRange = field.getNameRange();
				VjoMatch match = VjoMatchFactory.createMethodMatch(type,
						nameRange.getOffset(), nameRange.getLength());
				result.add(match);

				// Add by Oliver.2009-06-25.
				try {
					match.setIsPublic(Flags.isPublic(field.getFlags()));
					match.setIsStatic(Flags.isStatic(field.getFlags()));
				} catch (ModelException e) {
				}

				// searchCommentDeclarition(typeName, nameRange);
			} catch (ModelException e) {
				VjetPlugin.getDefault().getLog().log(
						new Status(IStatus.ERROR, VjetPlugin.PLUGIN_ID,
								IStatus.ERROR, "Wrong name range", e));
			}
		}
	}

	/**
	 * Find field declaration for the field reference element
	 * 
	 * @param element
	 *            field reference element
	 * @param module
	 *            source module
	 * @return field declaration object.
	 */
	private IField findFieldDeclaration(IField element, IVjoSourceModule module) {
		IField field = null;

		try {
			int offset = element.getNameRange().getOffset();
			IModelElement parent = module.getElementAt(offset);
			if (parent == null) {
				return null;
			}
			if (parent instanceof IField) {
				field = (IField) parent;
			} else {
				String name = element.getElementName();
				field = (IField) CodeassistUtils.findChild(name, parent);
			}

		} catch (ModelException e1) {
			// DLTKCore.error(e1.toString(), e1);
		}
		return field;
	}

	@Override
	protected void searchReferences(SearchQueryParameters params,
			List<VjoMatch> result) {
		IField field = (IField) params.getElement();
		if (this.isLocal(field)) {
			IJstMethod jstMethod = this.getJstMethod(field);
			this.findRefs(field, jstMethod, result);
		} else {
			if (field.getSourceModule() instanceof NativeVjoSourceModule)
				this.processNativeTypeField(field, result);
			else
				this.processSourceTypeField(field, result);
		}
	}

	// process type field reference in source type, even in inner source type
	private void processSourceTypeField(IField field, List<VjoMatch> result) {
		IJstProperty jstProperty = this.getJstProperty(field);
		if (jstProperty == null)
			return;

		// work out PropertyName and find referenced nodes from TypeSpaceMgr
		String grouName = jstProperty.getOwnerType().getPackage()
				.getGroupName();
		String typeName = jstProperty.getOwnerType().getName();
		PropertyName propertyName = new PropertyName(new TypeName(grouName,
				typeName), field.getElementName());

		List<IJstNode> list = mgr.getPropertyDependents(propertyName);
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			IJstNode jstNode = (IJstNode) iterator.next();
			this.findRefs(field, jstNode, result);
		}
	}

	// process type field reference in native type
	private void processNativeTypeField(IField field, List<VjoMatch> result) {
		NativeVjoSourceModule nativeVjoSourceModule = (NativeVjoSourceModule) field
				.getSourceModule();

		String groupName = nativeVjoSourceModule.getTypeName().groupName();
		String typeName = nativeVjoSourceModule.getTypeName().typeName();
		PropertyName propertyName = new PropertyName(new TypeName(groupName,
				typeName), field.getElementName());

		List<IJstNode> list = mgr.getPropertyDependents(propertyName);
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			IJstNode jstNode = (IJstNode) iterator.next();
			this.findRefs(field, jstNode, result);
		}
	}

	// pass request to VjoFieldReferenceVistor
	private void findRefs(IField field, IJstNode searchNode,
			List<VjoMatch> result) {
		if (searchNode == null)
			return;

		VjoFieldReferenceVistor fieldReferenceVistor = new VjoFieldReferenceVistor(
				field, searchNode, result);
		JstDepthFirstTraversal.accept(searchNode, fieldReferenceVistor);
	}

	/**
	 * determine whether is local (including var and arg)
	 * 
	 * @param field
	 * @return
	 */
	private boolean isLocal(IField field) {
		if (field.getParent().getElementType() == IModelElement.METHOD)
			return true;
		else
			return false;
	}

	/**
	 * get correct corresponding jst method (iterating inner type)
	 * 
	 * @param field
	 * @return
	 */
	private IJstMethod getJstMethod(IField field) {
		// first, work out root jst type (outer type)
		IVjoSourceModule module = (IVjoSourceModule) field.getSourceModule();
		TypeName jstTypeName = module.getTypeName();
		IJstType jstType = TypeSpaceMgr.findType(jstTypeName.groupName(),
				jstTypeName.typeName());

		// second, work out dltk type name
		IType type = (IType) field.getParent().getParent();
		String dltkTypeName = type.getFullyQualifiedName(".");

		// fetch the jst method (maybe a sub method of inner type)
		return this.getJstMethod(jstType, dltkTypeName, field.getParent()
				.getElementName());
	}

	// currently, not support anoymous inner type
	private IJstMethod getJstMethod(IJstType type, String typeName,
			String methodName) {
		if (type.getAlias().equals(typeName)) {
			IJstMethod jstMethod = type.getMethod(methodName);
			if (jstMethod == null && "constructs".equals(methodName))
				jstMethod = type.getConstructor();
			return jstMethod;
		} else {
			// iterate inner types...
			for (Iterator iterator = type.getEmbededTypes().iterator(); iterator
					.hasNext();) {
				IJstType innerType = (IJstType) iterator.next();
				IJstMethod jstMethod = this.getJstMethod(innerType, typeName,
						methodName);
				if (jstMethod != null)
					return jstMethod;
			}
		}
		return null;
	}

	/**
	 * get correct corresponding jst property
	 * 
	 * @param field
	 * @return
	 */
	private IJstProperty getJstProperty(IField field) {
		// first, work out root jst type (outer type)
		IVjoSourceModule module = (IVjoSourceModule) field.getSourceModule();
		TypeName jstTypeName = module.getTypeName();
		IJstType jstType = TypeSpaceMgr.findType(jstTypeName.groupName(),
				jstTypeName.typeName());

		// second, work out dltk type name
		if(!(field.getParent() instanceof IType) && field instanceof IField){
			field = (IField) field.getParent();
		}
		String dltkTypeName = ((IType) field.getParent())
				.getFullyQualifiedName(".");

		return this.getJstProperty(jstType, dltkTypeName, field
				.getElementName());
	}

	// currently, not support anoymous inner type
	private IJstProperty getJstProperty(IJstType type, String typeName,
			String propertyName) {
		if (type.getAlias().equals(typeName)) {
			IJstProperty jstProperty = type.getProperty(propertyName);
			IJstGlobalVar globalVar = type.getGlobalVar(propertyName);
			if(jstProperty==null && globalVar!=null){
				return globalVar.getProperty();
			}
			
			return jstProperty;
		} else {
			// iterate inner types...
			for (Iterator iterator = type.getEmbededTypes().iterator(); iterator
					.hasNext();) {
				IJstType innerType = (IJstType) iterator.next();
				IJstProperty jstProperty = this.getJstProperty(innerType,
						typeName, propertyName);
				if (jstProperty != null)
					return jstProperty;
			}
		}
		return null;
	}

	// add by patrick
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ebayopensource.vjet.eclipse.core.search.AbstractVjoElementSearcher#findOccurrence(org.ebayopensource.dsf.jst.IJstNode,
	 *      org.ebayopensource.dsf.jst.IJstNode)
	 */
	@Override
	public List<VjoMatch> findOccurrence(IJstNode jstNode, IJstNode scope) {
		int fieldType = -1;
		if (jstNode instanceof JstArg) {
			fieldType = METHOD_ARG;
		} else if (jstNode instanceof JstVars) {
			fieldType = LOCAL_VAR;
		} else if (jstNode instanceof IJstProperty) {
			fieldType = TYPE_PROP;
		} else {
			VjetPlugin.error("Unhandled field node type in occurrence marking:"
					+ jstNode.getClass().getName());
			return Collections.emptyList();
		}

		IJstNode searchedTree = scope;
		// search from method/constructor tree only if not type property
		if (isLocal(fieldType)) {
			searchedTree = CodeassistUtils.findDeclaringMethod(jstNode);
		}
		
		if (searchedTree == null) {
			return Collections.emptyList();
		}

		return findOccurrence(fieldType, jstNode, searchedTree);
	}

	private boolean isLocal(int fieldType) {
		return TYPE_PROP != fieldType;
	}

	private List<VjoMatch> findOccurrence(int fieldType, IJstNode matchNode,
			IJstNode jstTree) {
		AbstractVjoOccurrenceVisitor visitor = new VjoFieldOccurrenceVisitor(fieldType,
				matchNode);
		jstTree.accept(visitor);

		return visitor.getMatches();
	}
	// end add
}
