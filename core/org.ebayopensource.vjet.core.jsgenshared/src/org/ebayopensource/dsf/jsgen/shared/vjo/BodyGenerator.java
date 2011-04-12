/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsgen.shared.vjo;

import java.io.PrintWriter;

import org.ebayopensource.dsf.jst.declaration.JstBlock;
import org.ebayopensource.dsf.jst.token.IStmt;

public class BodyGenerator extends BaseGenerator {
	
	
	//
	// Constructor
	//
	public BodyGenerator(final GeneratorCtx ctx){
		super(ctx);
	}
	
	//
	// API
	//
	public void writeBody(final JstBlock body){
		if (body == null){
			return;
		}
		
		PrintWriter writer = getWriter();
		writer.append("{");
		indent();
		for (IStmt stmt: body.getStmts()){
			getStmtGenerator().writeStmt(stmt);
		}
		outdent();
		writeNewline();
		writeIndent();
		writer.append("}");
	}
}
