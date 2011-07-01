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

import java.util.List;

import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.IJstTypeReference;
import org.ebayopensource.dsf.jst.JstSource;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.dsf.jst.traversal.IJstVisitor;
import org.ebayopensource.vjet.eclipse.codeassist.CodeassistUtils;
import org.eclipse.dltk.mod.core.IScriptFolder;
import org.eclipse.dltk.mod.core.IType;
import org.eclipse.dltk.mod.internal.core.ScriptProject;

/**
 * This class perform search type references in dependent type.
 * 
 */
public class VjoPackageReferencesVisitor implements IJstVisitor {

	private IScriptFolder pkg;

	private List<VjoMatch> result;

	private IJstType dependentType;

	/**
	 * Create instance of this class with specified type, dependent type and
	 * list of the {@link VjoMatch} objects.
	 * 
	 * @param pkg
	 *            type for which perform search references in dependent type.
	 * @param dependentType
	 *            type in which perform search.
	 * @param result
	 *            list of the {@link VjoMatch} objects.
	 */
	public VjoPackageReferencesVisitor(IScriptFolder pkg, IJstType dependentType, List<VjoMatch> result) {
		this.pkg = pkg;
		this.result = result;
		this.dependentType = dependentType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ebayopensource.dsf.jst.traversal.IJstVisitor#endVisit(org.ebayopensource.dsf.jst.IJstNode)
	 */
	public void endVisit(IJstNode node) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ebayopensource.dsf.jst.traversal.IJstVisitor#postVisit(org.ebayopensource.dsf.jst.IJstNode)
	 */
	public void postVisit(IJstNode node) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ebayopensource.dsf.jst.traversal.IJstVisitor#preVisit(org.ebayopensource.dsf.jst.IJstNode)
	 */
	public void preVisit(IJstNode node) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ebayopensource.dsf.jst.traversal.IJstVisitor#visit(org.ebayopensource.dsf.jst.IJstNode)
	 */
	public boolean visit(IJstNode node) {

		if (node instanceof IJstType) {
			JstType reference = (JstType) node;
			visit(reference);
		}

		if (node instanceof IJstTypeReference) {
			IJstTypeReference reference = (IJstTypeReference) node;
			visit(reference);
		}

		return true;
	}

	private void visit(IJstTypeReference reference) {
		IJstType type = reference.getReferencedType();
		if (type.getPackage() == null)
			return;
		if (this.pkg.getElementName().equals(type.getPackage().getName())) {
			String typeName = type.getName();
			if (containInTypeTop(dependentType, typeName)) {
				createMatch(reference);
			}

		}

	}

	private void visit(IJstType type) {
		// String pkgName = this.pkg.getElementName();
		createMatch(type);
	}

	private boolean containInTypeTop(IJstType jstType, String typeName) {
		List<? extends IJstType> al = jstType.getImports();
		for (IJstType jstType2 : al) {
			if (jstType2.getName().equals(typeName)) {
				return true;
			}
		}

		al = jstType.getExtends();
		for (IJstType jstType2 : al) {
			if (jstType2.getName().equals(typeName)) {
				return true;
			}
		}

		al = jstType.getExpects();
		for (IJstType jstType2 : al) {
			if (jstType2.getName().equals(typeName)) {
				return true;
			}
		}

		al = jstType.getSatisfies();
		for (IJstType jstType2 : al) {
			if (jstType2.getName().equals(typeName)) {
				return true;
			}
		}

		return false;
	}

	private IType getType(IJstType jstType) {
		// TODO seems not work for inter-project dependency
		ScriptProject project = (ScriptProject) pkg.getScriptProject();
		IType type = CodeassistUtils.findType(project, jstType.getName());
		return type;

	}

	private void createMatch(IJstType type) {
		String pkg = type.getPackage().getName();
		if (pkg.equals(this.pkg.getElementName())) {
			int len = pkg.length();
			JstSource source = type.getPackage().getSource();
			if (source != null) {
				VjoMatch match = VjoMatchFactory.createPackageMatch(getType(dependentType), source.getStartOffSet(), len);
				result.add(match);
			}
		}

	}

	/**
	 * Create {@link VjoMatch} object from {@link IJstTypeReference} object and
	 * add to result field.
	 * 
	 * @param reference
	 *            {@link IJstTypeReference} object.
	 */
	private void createMatch(IJstTypeReference reference) {
		String pkg = reference.getReferencedType().getPackage().getName();
		int len = pkg.length();
		JstSource source = reference.getSource();
		if (source != null) {
			VjoMatch match = null;
			if (source.getLength() == reference.getReferencedType().getName().length()) {
				match = VjoMatchFactory.createPackageMatch(getType(dependentType), source.getStartOffSet(), len);
			} else {
				// small range, match "vjo.samples.foundations.Employee12",but
				// returning offset start from "Employee12"
				// minus 1 because there is a dot
				match = VjoMatchFactory.createPackageMatch(getType(dependentType), source.getStartOffSet() - len - 1, len);
			}

			if (match != null && !result.contains(match))
				result.add(match);
		}
	}
}
