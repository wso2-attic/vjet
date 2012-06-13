/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.meta;

public class JsAnnotation {
	public enum JsAnnotationType {
		SUPRESSTYPECHECK, DEPRECATED, SUPPRESSTYPECHECK
	};

	private boolean m_supressTypeCheck;
	private boolean m_deprecated;
	private boolean m_isNone;
	public static final String AT = "@";

	public JsAnnotation() {

	}

	public JsAnnotation(String annotation) {
		setAnnotation(annotation);
	}

	public void setAnnotation(String annotation) {
		if ((AT + JsAnnotationType.SUPRESSTYPECHECK.toString())
				.equals(annotation) ||
			(AT + JsAnnotationType.SUPPRESSTYPECHECK.toString())
				.equals(annotation)) {
			m_supressTypeCheck = true;
		} else if ((AT + JsAnnotationType.DEPRECATED.toString())
				.equals(annotation)) {
			m_deprecated = true;
		} else {
			m_isNone = true;
		}
	}

	public boolean isSupressTypeCheck() {
		return m_supressTypeCheck;
	}

	public boolean isDeprecated() {
		return m_deprecated;
	}

	public boolean isNone() {
		return m_isNone;
	}

	public static void main(String[] args) {
		JsAnnotation none = new JsAnnotation();
		System.out.println("SuppressTypeCheck="+none.isSupressTypeCheck());

		none = new JsAnnotation("ANNOTATION.SUPRESSTYPECHECK");
		System.out.println("SuppressTypeCheck="+none.isSupressTypeCheck());

		none = new JsAnnotation("@SUPRESSTYPECHECK");
		System.out.println("SuppressTypeCheck="+none.isSupressTypeCheck());
		
		none = new JsAnnotation("@SUPPRESSTYPECHECK");
		System.out.println("SuppressTypeCheck="+none.isSupressTypeCheck());

		none = new JsAnnotation("A");
		System.out.println("SuppressTypeCheck="+none.isSupressTypeCheck());
		
		none = new JsAnnotation("@DEPRECATED");
		System.out.println("Deprecated="+none.isDeprecated());
	}

}
