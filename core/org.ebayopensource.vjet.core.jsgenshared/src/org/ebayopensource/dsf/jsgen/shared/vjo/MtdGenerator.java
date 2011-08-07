/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsgen.shared.vjo;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.List;

import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.declaration.JstArg;
import org.ebayopensource.dsf.jst.declaration.JstBlock;
import org.ebayopensource.dsf.jst.declaration.JstMethod;
import org.ebayopensource.dsf.jst.declaration.JstRawBlock;
import org.ebayopensource.dsf.jst.reserved.JsCoreKeywords;
import org.ebayopensource.dsf.jst.token.IStmt;
import org.ebayopensource.vjo.meta.VjoKeywords;

public class MtdGenerator extends BaseGenerator {
	
	
	//
	// Constructor
	//
	public MtdGenerator(final GeneratorCtx ctx){
		super(ctx);
	}
	
	//
	// API
	//
	public void writeMtd(final IJstMethod mtd){
		
		PrintWriter writer = getWriter();
		
		// JsDoc
//		writeJsDoc(mtd);

		// Declaration
		writeNewline();
		writeIndent();
	
		
		String mtdName = mtd.getName().getName();
		if (mtdName != null){
			writer.append(mtdName).append(COLON);
		}
		
		if((mtd.getOwnerType() !=null && mtd.getOwnerType().isMetaType()) || mtd.getAnnotation("metaMethod")!=null){
			writer.append("vjo." +VjoKeywords.NEEDS_IMPL);
			return;
		}

			writer.append(JsCoreKeywords.FUNCTION);
			List<JstArg> args = mtd.getArgs();
			if (args.size() > 0){
				JstArg varArg = null;
				int fixArgCount = args.size();
				writer.append("(");
				for (int i=0; i<args.size(); i++){
					if (args.get(i).isVariable()){
						varArg = args.get(i);
						fixArgCount = i;
						break;
					}
					if (i > 0){
						writer.append(",");
					}
					writer.append(args.get(i).getName());
				}
				writer.append("){");
				if (varArg != null){
					writeVarArgs(fixArgCount, varArg);
				}
			}
			else {
				writer.append("(){");
			}
			
			indent();
			
			// Body
			writeBlock(mtd.getBlock());
		//	getWriter().append("}");
		}
	
		
		// Close
//		endWriteNameFunc(hasMore);
	
	
	//
	// Private
	//
	private void writeVarArgs(int fixArgCount, JstArg varArg){
		String fixArgs = String.valueOf(fixArgCount);
		String argName = varArg.getName();
		PrintWriter writer = getWriter();
		indent();
		writeNewline();
		writeIndent();
		writer.append("var ").append(argName).append(";");
		writeNewline();
		writeIndent();
		writer.append("if (arguments.length == ").append(String.valueOf(fixArgCount + 1)).append(" && arguments[").append(fixArgs).append("]  instanceof Array){");
		indent();
		writeNewline();
		writeIndent();
		writer.append(argName).append("=arguments[").append(fixArgs).append("];");
		outdent();
		writeNewline();
		writeIndent();
		writer.append("}");
		
		writeNewline();
		writeIndent();
		writer.append("else {");
		
		indent();
		writeNewline();
		writeIndent();
		writer.append(argName).append("=[];");
		
		writeNewline();
		writeIndent();
		writer.append("for (var i=").append(fixArgs).append("; i<arguments.length; i++){");
		indent();
		writeNewline();
		writeIndent();
		writer.append(argName).append("[i-").append(fixArgs).append("]=arguments[i];");
		outdent();
		writeNewline();
		writeIndent();
		writer.append("}");
		outdent();
		writeNewline();
		writeIndent();
		writer.append("}");
		outdent();
	}
	
	private void writeBlock(final JstBlock block){
		if (block == null){
			return;
		} else if (block.getStmts().isEmpty()) {
			if (block instanceof JstRawBlock) {//currently only used by VjotoVjo3 converter.
				int cols = 0;
				String offset = "";
				if (block.getParentNode() instanceof JstMethod) {
					JstMethod meth = (JstMethod)block.getParentNode();
					cols = meth.getSource().getColumn();
				}
				for (int i=0; i<cols; i++) {
					offset += " ";
				}
				//raw blocks shouldn't have any statements
				String blockTxt = block.toBlockText();
				LineNumberReader reader = new LineNumberReader(new StringReader(blockTxt));
				try {//make sure tabbing is correct
					String line = reader.readLine();
					if (line!=null) {
						getWriter().append("   "+line);
					}
					while ((line=reader.readLine())!=null) {
						getWriter().append("\n   "+line);
					}
				} catch (IOException e) {
					//TODO log error?
				}
			}
			return;
		}
		for (IStmt s : block.getStmts()){
			getStmtGenerator().writeStmt(s);
		}
	}
}
