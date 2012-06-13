/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.translator;

import org.ebayopensource.dsf.jst.declaration.JstDoc;
import org.ebayopensource.dsf.jst.declaration.JstMethod;
import org.ebayopensource.dsf.jst.declaration.JstProperty;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.dsf.jst.meta.IJsCommentMeta;

public class JsDocHelper {

	public static void addJsDoc(IJsCommentMeta meta, JstMethod jstMethod) {
		if (meta !=null && meta.getCommentSrc() != null) {
			String jsdoc = getJsDocFromStructuredComment(meta);
			if (jsdoc != "" ) {
				jstMethod.setDoc(createJsDocNode(jsdoc));
			}
		}
	}
	
	public static void addJsDoc(IJsCommentMeta meta, JstProperty property) {
		if (meta !=null &&meta.getCommentSrc() != null) {
			String jsdoc = getJsDocFromStructuredComment(meta);
			property.setDoc(createJsDocNode(jsdoc));

		}
	}

	public static void addJsDoc(String jsdoc, JstProperty property) {
		if (jsdoc != null) {
			if(!jsdoc.trim().startsWith("/**")){
				return;
			}
			property.setDoc(createJsDocNode(jsdoc));

		}
	}

	private static String getJsDocFromStructuredComment(IJsCommentMeta meta) {
		String[] split = meta.getCommentSrc().split(";");
		String jsdoc = "";
		if (split.length >= 2) {
			for (int i = 1; i < split.length; i++) {
				jsdoc = jsdoc + split[i];
			}
		}
		return jsdoc.trim();
	}

	public static void addJsDoc(String jsdoc, JstType property) {
		if (jsdoc != null) {
			if(!jsdoc.trim().startsWith("/**")){
				return;
			}
			property.setDoc(createJsDocNode(jsdoc));

		}
	}

	public static void addJsDoc(IJsCommentMeta meta, JstType type) {

		if (meta !=null && meta.getCommentSrc() != null) {

			String jsdoc = getJsDocFromStructuredComment(meta);
			if (jsdoc != "") {
				type.setDoc(createJsDocNode(jsdoc));
			}
		}

	}

	private static JstDoc createJsDocNode(String jsdoc) {
		jsdoc = jsdoc.trim();
		jsdoc = jsdoc.replaceAll("^/\\*\\*", "");
		jsdoc = jsdoc.replaceAll("^\\*", "");
		jsdoc = jsdoc.replaceAll("\\*/$", "");
		// commentSrc = commentSrc.replaceAll("^(\t)*(\\*)*", "");
		jsdoc = jsdoc.replaceAll("\\s+\\*", "\n");
		return new JstDoc(jsdoc);
	}
	
	
	
	public static void addJsDoc(String jsdoc, JstMethod method) {
		if (jsdoc != null) {
			if(!jsdoc.trim().startsWith(("/**"))){
				return;
			}
		
			method.setDoc(createJsDocNode(jsdoc));
			

		}
	}
	
	
	
}
