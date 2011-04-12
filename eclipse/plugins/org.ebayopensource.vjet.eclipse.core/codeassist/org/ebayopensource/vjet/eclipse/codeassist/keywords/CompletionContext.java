/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.codeassist.keywords;

/**
 * This class keep context information about current completion.
 * 
 * 
 *
 */
public class CompletionContext {

	private static boolean isStaticContext;
	private static boolean isInstanceContext;
	private static boolean isCompletedContext;
	private static boolean thisWithinStaticContext;
	private static boolean isVariableContext;

	public static void setStaticContext(boolean isStatic) {
		isStaticContext = isStatic;
		if (isStatic) {
			isInstanceContext = false;
		}
	}

	public static void thisWithinStaticContext(boolean thisWithinStaticContext1) {
		thisWithinStaticContext = thisWithinStaticContext1;
	}

	public static boolean isThisWithinStaticContext() {
		return thisWithinStaticContext;
	}

	public static void setInstanceContext(boolean isInstance) {
		isInstanceContext = isInstance;
		if (isInstance) {
			isStaticContext = false;
		}
	}

	public static void setCompletedContext(boolean isCompleted) {
		isCompletedContext = isCompleted;
	}

	/**
	 * @return the isStaticContext
	 */
	public static boolean isStaticContext() {
		return isStaticContext;
	}

	/**
	 * @return the isInstanceContext
	 */
	public static boolean isInstanceContext() {
		return isInstanceContext;
	}

	/**
	 * @return the isCompletedContext
	 */
	public static boolean isCompletedContext() {
		return isCompletedContext;
	}

	/**
	 * @return the isVariableContext
	 */
	public static boolean isVariableContext() {
		return isVariableContext;
	}

	/**
	 * @param isVariableContext
	 *            the isVariableContext to set
	 */
	public static void setVariableContext(boolean isVariableContext) {
		CompletionContext.isVariableContext = isVariableContext;
	}

}
