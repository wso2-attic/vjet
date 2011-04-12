/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.eclipse.dltk.mod.core.search.indexing;

import org.ebayopensource.vjet.eclipse.compiler.IJSSourceElementRequestor;

/**
 * No indexing, we use Type Space for search;
 * 
 * 
 * 
 */
public class VjoSourceIndexerRequestor extends SourceIndexerRequestor implements
		IJSSourceElementRequestor {

	public void acceptImport(int declarationStart, int declarationEnd,
			char[][] tokens, boolean onDemand, int modifiers) {
		// TODO Auto-generated method stub

	}

	public void enterInitializer(int declarationStart, int modifiers) {
		// TODO Auto-generated method stub

	}

	public void exitInitializer(int declarationEnd) {
		// TODO Auto-generated method stub

	}

	public void acceptFieldReference(char[] fieldName, int sourcePosition) {
		// TODO Auto-generated method stub

	}

	public void acceptMethodReference(char[] methodName, int argCount,
			int sourcePosition, int sourceEndPosition) {
		// TODO Auto-generated method stub

	}

	public void acceptPackage(int declarationStart, int declarationEnd,
			char[] name) {
		// TODO Auto-generated method stub

	}

	public void acceptTypeReference(char[][] typeName, int sourceStart,
			int sourceEnd) {
		// TODO Auto-generated method stub

	}

	public void acceptTypeReference(char[] typeName, int sourcePosition) {
		// TODO Auto-generated method stub

	}

	public void enterField(FieldInfo info) {
		// TODO Auto-generated method stub

	}

	public boolean enterFieldCheckDuplicates(FieldInfo info) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean enterFieldWithParentType(FieldInfo info, String parentName,
			String delimiter) {
		// TODO Auto-generated method stub
		return false;
	}

	public void enterMethod(MethodInfo info) {
		// TODO Auto-generated method stub

	}

	public void enterMethodRemoveSame(MethodInfo info) {
		// TODO Auto-generated method stub

	}

	public boolean enterMethodWithParentType(MethodInfo info,
			String parentName, String delimiter) {
		// TODO Auto-generated method stub
		return false;
	}

	public void enterModule() {
		// TODO Auto-generated method stub

	}

	public void enterModuleRoot() {
		// TODO Auto-generated method stub

	}

	public void enterType(TypeInfo info) {
		// TODO Auto-generated method stub

	}

	public boolean enterTypeAppend(String fullName, String delimiter) {
		// TODO Auto-generated method stub
		return false;
	}

	public void exitField(int declarationEnd) {
		// TODO Auto-generated method stub

	}

	public void exitMethod(int declarationEnd) {
		// TODO Auto-generated method stub

	}

	public void exitModule(int declarationEnd) {
		// TODO Auto-generated method stub

	}

	public void exitModuleRoot() {
		// TODO Auto-generated method stub

	}

	public void exitType(int declarationEnd) {
		// TODO Auto-generated method stub

	}

}
