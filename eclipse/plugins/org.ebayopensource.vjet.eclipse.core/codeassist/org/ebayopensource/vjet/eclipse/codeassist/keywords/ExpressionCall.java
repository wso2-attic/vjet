/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.codeassist.keywords;

import java.util.HashMap;

import org.ebayopensource.dsf.jsgen.shared.ids.ScopeIds;
import org.ebayopensource.dsf.jstojava.translator.robust.completion.JstCompletion;

/**
 * This class define right call for member of the type in current completion context.
 * 
 * 
 *
 */
public class ExpressionCall {

	private static final String DELIMITER = "-";

	private static final boolean PROPS = true;

	private static final boolean PROTOS = false;

	private static final String THIS_VJO = "THIS_VJO";

	private static final String THIS = "this";

	private static final String VAR = "var";

	private static final boolean STATIC_FIELD = true;

	private static final boolean INSTANCE_FIELD = false;

	private static HashMap<String, Boolean> table = new HashMap<String, Boolean>();

	static {
		populateTable();
	}

	public static boolean isRightCall(JstCompletion completion, boolean isStatic) {

		String context = getContext();

		boolean isPropsBlock = !completion.inScope(ScopeIds.PROTOS);

		return isRightCall(isPropsBlock, context, isStatic);

	}

	private static String getContext() {
		String staticContext = THIS;

		if (CompletionContext.isStaticContext()) {
			staticContext = THIS_VJO;
		}

		if (CompletionContext.isVariableContext()) {			
			staticContext = VAR;
		}
		return staticContext;
	}

	/**
	 * Creates table of the right calls.
	 */
	private static void populateTable() {
		addCall(PROPS, THIS_VJO, STATIC_FIELD, true);
		addCall(PROPS, THIS_VJO, INSTANCE_FIELD, false);
		addCall(PROPS, THIS, STATIC_FIELD, true);
		addCall(PROPS, THIS, INSTANCE_FIELD, false);
		addCall(PROPS, VAR, STATIC_FIELD, true);
		addCall(PROPS, VAR, INSTANCE_FIELD, true);

		addCall(PROTOS, THIS_VJO, STATIC_FIELD, true);
		addCall(PROTOS, THIS_VJO, INSTANCE_FIELD, false);
		addCall(PROTOS, THIS, STATIC_FIELD, true);
		addCall(PROTOS, THIS, INSTANCE_FIELD, true);
		addCall(PROTOS, VAR, STATIC_FIELD, true);
		addCall(PROTOS, VAR, INSTANCE_FIELD, true);
	}

	private static boolean isRightCall(boolean isPropsBlock, String context,
			boolean isStaticMember) {
		String key = createKey(isPropsBlock, context, isStaticMember);
		Boolean isRightCall = false;
		isRightCall = table.get(key);
		if (isRightCall == null) {
			throw new InternalError("Invalid call for member context : " + key);
		}
		return isRightCall;
	}

	public static String createKey(boolean isPropsBlock, String context,
			boolean isStaticMember) {
		StringBuilder builder = new StringBuilder();
		builder.append(isPropsBlock);
		builder.append(DELIMITER);
		builder.append(context);
		builder.append(DELIMITER);
		builder.append(isStaticMember);
		return builder.toString();
	}

	public static void addCall(boolean isPropsBlock, String staticContext,
			boolean isStaticMember, boolean isRightCall) {
		String key = createKey(isPropsBlock, staticContext, isStaticMember);
		table.put(key, isRightCall);
	}
}
