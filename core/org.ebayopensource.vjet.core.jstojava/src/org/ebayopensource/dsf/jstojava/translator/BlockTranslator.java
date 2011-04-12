/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.translator;

import org.ebayopensource.dsf.jst.declaration.JstBlock;
import org.ebayopensource.dsf.jstojava.translator.robust.ast2jst.FieldReferenceTranslator;
import org.ebayopensource.dsf.jstojava.translator.robust.ast2jst.MessageSendTranslator;
import org.ebayopensource.dsf.jstojava.translator.robust.ast2jst.TranslatorFactory;
import org.eclipse.mod.wst.jsdt.core.ast.IASTNode;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.FieldReference;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.MessageSend;

public class BlockTranslator {

	public static JstBlock createJstBlock(final TranslateCtx ctx, IASTNode node) {

//		StringBuffer buf = new StringBuffer();
//		if (node instanceof ASTNode) {
//			((ASTNode) node).print(0, buf);
//		}

		// System.out.println(buf.toString());
		// ASTVisitor visitor = new ASTPrinterUtil().new PrintVisitor();
		// node.traverse(visitor);
//		TranslateCtx ctx2 = ctx;
//		ctx.setSkiptImplementation(true);
//		ctx.setSkipJsExtSyntaxArgs(true);
//		ctx2.setAST(ctx.getAST());
		
//		MessageSend b = null;
		JstBlock block = new JstBlock();
		block.setSource(TranslateHelper.getSource(node, ctx.getSourceUtil()));
		if (node instanceof MessageSend) {
			MessageSend b = (MessageSend) node;
			MessageSendTranslator bt = (MessageSendTranslator) TranslatorFactory
					.getTranslator(node, ctx);
			block.addChild(bt.translate(b));
		} else if (node instanceof FieldReference) {
			FieldReference fr = (FieldReference) node;
			FieldReferenceTranslator ft  = 
				(FieldReferenceTranslator) TranslatorFactory.getTranslator(node, ctx);
			block.addChild(ft.translate(fr));
		}

		return block;
	}

}
