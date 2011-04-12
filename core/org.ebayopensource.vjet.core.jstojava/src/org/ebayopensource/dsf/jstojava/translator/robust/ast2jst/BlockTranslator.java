/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.translator.robust.ast2jst;

import org.ebayopensource.dsf.jst.declaration.JstBlock;
import org.ebayopensource.dsf.jst.stmt.BlockStmt;
import org.ebayopensource.dsf.jstojava.translator.TranslateHelper;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.Block;

public class BlockTranslator extends BaseAst2JstTranslator<Block, JstBlock> {

	@Override
	protected JstBlock doTranslate(Block block) {
		assert block != null && !(m_parent instanceof JstBlock);
		JstBlock jstBlock = null;
		if(m_parent!=null && m_parent instanceof JstBlock && m_parent.getParentNode() instanceof BlockStmt ){
			jstBlock = (JstBlock) m_parent;
		}
		if (jstBlock == null) {
			jstBlock = new JstBlock();
		
		}
		jstBlock.setSource(TranslateHelper.getSource(block, m_ctx.getSourceUtil()));
		
		if (block.statements != null) {
			m_ctx.setPreviousNodeSourceEnd(block.sourceStart());
//			Jack: Add the following method call, and comment the left part
//			See bug: 5705 and 5688
			TranslateHelper.addStatementsToJstBlock(block.statements,
					jstBlock, block.sourceEnd(), m_ctx);
//			int len = block.statements.length;
//			for (int i=0; i<len; i++) {
//				if (i+1<len) {
//					m_ctx.setNextNodeSourceStart(block.statements[i+1].sourceEnd());
//				} else if (len==1) {
//					m_ctx.setNextNodeSourceStart(block.sourceEnd());
//				}
//				Statement stmt = block.statements[i];
//				
//				BaseAst2JstTranslator translator = getTranslator(stmt);
//				translator.setParent(jstBlock);
//				Object node = translator.translate(stmt);
//				if (node instanceof IStmt) {
//					jstBlock.addStmt((IStmt)node);
//				}
//				m_ctx.setPreviousNodeSourceEnd(stmt.sourceEnd());
//			}
			
		}
		return jstBlock;
	}

}
