/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.eclipse.dltk.mod.javascript.ui.text;

import org.eclipse.dltk.mod.javascript.core.IJavaScriptConstants;
import org.eclipse.jface.text.IDocument;

public interface IJavaScriptPartitions {

	public final static String JS_PARTITIONING = IJavaScriptConstants.JS_PARTITIONING;

	public final static String JS_SINGLE_COMMENT = "__javascript_single_comment";
	public final static String JS_MULTI_COMMENT = "__javascript_multi_comment";

	public final static String JS_STRING = "__javascript_string";
	public static final String JS_DOC = "__javascript_doc";

	public final static String[] JS_PARTITION_TYPES = new String[] {
			IDocument.DEFAULT_CONTENT_TYPE, IJavaScriptPartitions.JS_STRING,
			IJavaScriptPartitions.JS_SINGLE_COMMENT,
			IJavaScriptPartitions.JS_MULTI_COMMENT,
			IJavaScriptPartitions.JS_DOC };
}
