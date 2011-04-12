/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.codeassist.keywords;

import java.util.List;

import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.JstSource;
import org.ebayopensource.dsf.jst.declaration.JstBlock;
import org.ebayopensource.dsf.jst.stmt.BlockStmt;
import org.ebayopensource.dsf.jst.token.IStmt;
import org.ebayopensource.dsf.jstojava.translator.JstUtil;

/**
 * This class contains additional utilities methods for completion acceptor.
 * 
 * 
 * 
 */
public abstract class AbstractCompletionAcceptor extends CompletionAcceptor {

	public AbstractCompletionAcceptor() {
		super();
	}

	protected BlockStmt getBlock(int position, IJstMethod jstMethod) {

		JstBlock block = jstMethod.getBlock();
		List<IStmt> list = block.getStmts();
		BlockStmt result = getBlock(position, null, list);

		return result;
	}

	/**
	 * Returns {@link BlockStmt} object by for current completion position in
	 * list of the {@link IStmt} objects.
	 * 
	 * @param position
	 * @param block
	 *            parent {@link BlockStmt} block.
	 * @param list
	 *            list if the {@link IStmt} object.
	 * @return {@link BlockStmt} object.
	 */
	protected BlockStmt getBlock(int position, BlockStmt block, List<IStmt> list) {

		BlockStmt result = block;

		if (list != null) {

			for (IStmt stmt : list) {

				if (stmt instanceof BlockStmt) {

					BlockStmt blockStmt = (BlockStmt) stmt;
					if (!JstUtil.include(blockStmt.getSource(), position)) {
						break;
					} else {
						result = getBlock(position, blockStmt);
					}
				}
			}
		}

		return result;
	}

	private BlockStmt getBlock(int position, BlockStmt blockStmt) {
		BlockStmt result;
		result = blockStmt;
		List<IStmt> list2 = blockStmt.getBody().getStmts();
		if (list2 != null) {
			result = getBlock(position, result, list2);
		}
		return result;
	}

	protected boolean inSource(JstSource source, int position) {
		return source.getStartOffSet() <= position
				&& source.getEndOffSet() >= position;
	}

}