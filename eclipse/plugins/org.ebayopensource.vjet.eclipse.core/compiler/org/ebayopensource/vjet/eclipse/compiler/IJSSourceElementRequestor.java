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
package org.ebayopensource.vjet.eclipse.compiler;

import org.eclipse.core.resources.IResource;
import org.eclipse.dltk.mod.compiler.ISourceElementRequestor;

/**
 * 
 * 
 */
public interface IJSSourceElementRequestor extends ISourceElementRequestor {

	public static class JSFieldInfo extends FieldInfo {
		public long[] m_annotationPositions;
		public String m_initializationSource;
		public String m_type;
		
		public IResource resource;
	}

	public static class JSMethodInfo extends MethodInfo {
		public boolean m_isConstructor;
		public String[] m_parameterTypes;
		public String m_returnType;
		public JSTypeParameterInfo[] m_typeParameters;
		
		public IResource resource;
		public boolean[] b_isVariables;
	}

	public static class JSTypeInfo extends TypeInfo {
		public String[] superinterfaces;
	}

	public static class JSTypeParameterInfo extends ElementInfo {
		/*
		 * The bounds names of this type parameter.
		 */
		public char[][] m_bounds;
	}

	/**
	 * @param declarationStart
	 *            This is the position of the first character of the import
	 *            keyword.
	 * @param declarationEnd
	 *            This is the position of the ';' ending the import statement or
	 *            the end of the comment following the import.
	 * @param tokens
	 *            This are the tokens of the import like specified in the
	 *            source.
	 * @param onDemand
	 *            set to true if the import is an import on demand (e.g. import
	 *            java.io.*). False otherwise.
	 * @param modifiers
	 *            can be set to static from 1.5 on.
	 */
	void acceptImport(int declarationStart, int declarationEnd,
			char[][] tokens, boolean onDemand, int modifiers);

	void enterInitializer(int declarationStart, int modifiers);

	void exitInitializer(int declarationEnd);
}
