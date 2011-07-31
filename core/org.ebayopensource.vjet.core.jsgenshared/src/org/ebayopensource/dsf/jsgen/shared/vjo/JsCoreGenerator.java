/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsgen.shared.vjo;

import org.ebayopensource.dsf.jst.reserved.JsCoreKeywords;
import org.ebayopensource.dsf.jst.token.IExpr;

/**
 * TODO:
 * 1. add assertion
 * 2. Method overloading to support other value types
 * 3. Null handling
 */
public class JsCoreGenerator extends BaseGenerator {
	
	//
	// Constructor
	//
	public JsCoreGenerator(final GeneratorCtx ctx){
		super(ctx);
	}
	
	//
	// API
	//
	public JsCoreGenerator writeNameValue(final String name, final String value, boolean hasMore){
		return writeNameValue(name, value, hasMore, true);
	}
	
	public JsCoreGenerator writeNameValue(final String name, final String value, boolean hasMore, boolean addNewLine){
		if (addNewLine) {
			writeIndent();
		}
		getWriter().append(name).append(COLON).append(value);
		if (hasMore){
			getWriter().append(COMMA);
		}
		if (addNewLine) {
			writeNewline();
		}
		return this;
	}
	
	public JsCoreGenerator writeNameValue(final String name, final boolean value, boolean hasMore){
		return writeNameValue(name, String.valueOf(value), hasMore);
	}

	public JsCoreGenerator writeNameValue(final String name, final long value, boolean hasMore){
		return writeNameValue(name, String.valueOf(value), hasMore);
	}
	
	public JsCoreGenerator writeNameValue(final String name, final double value, boolean hasMore){
		return writeNameValue(name, String.valueOf(value), hasMore);
	}
	
//	public JsCoreGenerator writeNV(final INV nv, boolean hasMore){
//		writeIndent();
//		getWriter().append(nv.toNVText());
//		if (hasMore){
//			getWriter().append(COMMA);
//		}
//		writeNewline();
//		return this;
//	}
	
	public JsCoreGenerator startWriteFunc(final Object... args){
		getWriter().append(JsCoreKeywords.FUNCTION);
		if (args != null && args.length > 0){
			getWriter().append("(");
			for (int i=0; i<args.length; i++){
				if(args[i]!=null){
					if (i > 0){
						getWriter().append(",");
					}
					getWriter().append(args[i].toString());
				}
			}
			getWriter().append("){");
		}
		else {
			getWriter().append("(){");
		}
		indent();
		return this;
	}
	
	public JsCoreGenerator endWriteFunc(boolean hasMore, boolean isMetaType){
		outdent();
		writeNewline();
		writeIndent();
		if(!isMetaType){
			getWriter().append("}");
		}
		if (hasMore){
			getWriter().append(COMMA);
		}
		return this;
	}
	
	public JsCoreGenerator writeInstanceVarAssignment(final String varName, final IExpr expr){
		writeIndent();
		getWriter().append(JsCoreKeywords.THIS).append(".").append(varName).append("=").append(expr.toExprText()).append(SEMI_COLON);
		writeNewline();
		return this;
	}
	
	public JsCoreGenerator writeInstanceFuncCall(final String funcName, final Object... args){
		writeIndent();
		getWriter().append(JsCoreKeywords.THIS).append(".").append(funcName).append("(");
		if (args != null && args.length > 0){
			for (int i=0; i<args.length; i++){
				if (i > 0){
					getWriter().append(",");
				}
				getWriter().append(args[i].toString());
			}
		}
		getWriter().append(");");
		writeNewline();
		return this;
	}
	
	public JsCoreGenerator writeReturn(final String value){
		writeIndent();
		getWriter().append(JsCoreKeywords.RETURN).append(SPACE).append(value).append(SEMI_COLON);
		writeNewline();
		return this;
	}
	
	public JsCoreGenerator writeReturn(final boolean value){
		return writeReturn(String.valueOf(value));
	}
	
	public JsCoreGenerator writeReturn(final long value){
		return writeReturn(String.valueOf(value));
	}
	
	public JsCoreGenerator writeReturn(final double value){
		return writeReturn(String.valueOf(value));
	}
	
	public JsCoreGenerator writeReturn(final Object value){
		return writeReturn(String.valueOf(value));
	}
}

