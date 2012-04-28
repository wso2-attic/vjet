/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo;

import java.util.ArrayList;
import java.util.List;

import org.ebayopensource.dsf.jst.util.bootstrap.JsBuilderDef;

public class VjBootStrapDef {

	private static final String DEFS = "defs";
	private static final String END_TYPE = "endType";
	private static final String EXPECTS = "expects";
	private static final String GLOBALS = "globals";
	private static final String INHERITS = "inherits";
	private static final String INITS = "inits";
	private static final String MIXIN = "mixin";
	private static final String NEEDS = "needs";
	private static final String NEEDSLIB = "needslib";
	private static final String OPTIONS = "options";
	private static final String PROTOS = "protos";
	private static final String PROPS = "props";
	private static final String SATISFIES = "satisfies";
	private static final String VALUES = "values";
	public static JsBuilderDef CTYPE;
	public static JsBuilderDef LTYPE;
	public static JsBuilderDef ITYPE;
	public static JsBuilderDef ETYPE;
	public static List<JsBuilderDef> SCHEMA = new ArrayList<JsBuilderDef>();
	public static JsBuilderDef MTYPE;
	public static JsBuilderDef OTYPE;
	public static JsBuilderDef FTYPE;

	static {

		CTYPE = new JsBuilderDef("vjo", "ctype")
				
				.anyOrder(
						new JsBuilderDef()
						.mtd(NEEDS, 0, "*")
						.mtd(INHERITS, 0, 1)
						.mtd(MIXIN, 0, "*")
						.mtd(SATISFIES, 0,"*"))
						.mtd(NEEDSLIB, 0, "*")
				.anyOrder(new JsBuilderDef()
					.mtd(GLOBALS, 0, 1))
				.anyOrder(
						new JsBuilderDef()
						.mtd(PROPS, 0, 1)
						.mtd(PROTOS, 0, 1))
				.mtd(INITS, 0, 1)
				.mtd(OPTIONS, 0, 1)
				.mtd(END_TYPE, 1, 1);
		
		LTYPE = new JsBuilderDef("vjo", "ltype")
					.anyOrder(new JsBuilderDef()
					.mtd(NEEDS, 0, "*")
					.mtd(NEEDSLIB, 0, "*")
					)
				.mtd(END_TYPE, 1, 1);

		ITYPE = new JsBuilderDef("vjo","itype")
				.anyOrder(new JsBuilderDef()
					.mtd(NEEDS, 0, "*")
					.mtd(INHERITS, 0, "*")
					.mtd(NEEDSLIB, 0, "*")
				)
				.anyOrder(new JsBuilderDef()
					.mtd(PROPS, 0, 1)
					.mtd(PROTOS, 0, 1))
				.mtd(INITS, 0, 1)
				.mtd(OPTIONS, 0, 1)
				.mtd(END_TYPE, 1, 1);

		ETYPE = new JsBuilderDef("vjo","etype")
				.anyOrder(new JsBuilderDef()
					.mtd(NEEDS, 0, "*")
					.mtd(NEEDSLIB, 0, "*")
					.mtd(SATISFIES, 0, "*")
					.mtd(VALUES, 0, 1)
				)
				.anyOrder(new JsBuilderDef()
					.mtd(PROPS, 0, 1)
					.mtd(PROTOS, 0, 1))
				.mtd(INITS, 0, 1)
				.mtd(OPTIONS, 0, 1)
				.mtd(END_TYPE, 1, 1);

			MTYPE = new JsBuilderDef("vjo","mtype")
			.anyOrder(new JsBuilderDef()
				.mtd(NEEDS, 0, "*")
				.mtd(NEEDSLIB, 0, "*")
				.mtd(SATISFIES, 0, "*")
			)			
			.mtd(EXPECTS, 0, "*")
			.anyOrder(
					new JsBuilderDef()
					.mtd(PROPS, 0, 1)
					.mtd(PROTOS, 0, 1))
			.mtd(OPTIONS, 0, 1)
			.mtd(END_TYPE, 1, 1);

		OTYPE = new JsBuilderDef("vjo","otype")
				.mtd(GLOBALS, 0, 1)
				.mtd(DEFS, 0, 1)
				.mtd(OPTIONS, 0, 1)
				.mtd(END_TYPE,1, 1);
		
		FTYPE = new JsBuilderDef("vjo","ftype")
				.anyOrder(new JsBuilderDef()
					.mtd(NEEDS, 0, "*")
					.mtd(NEEDSLIB, 0, "*"))
				.anyOrder(new JsBuilderDef()
					.mtd(GLOBALS, 0, 1))
				.mtd(PROPS, 1, 1)
				.mtd(INITS, 0, 1)
				.mtd(OPTIONS, 0, 1)
				.mtd(END_TYPE,1, 1);

		SCHEMA.add(CTYPE);
		SCHEMA.add(LTYPE);
		SCHEMA.add(ITYPE);
		SCHEMA.add(ETYPE);
		SCHEMA.add(MTYPE);
		SCHEMA.add(OTYPE);
		SCHEMA.add(FTYPE);

	}

}
