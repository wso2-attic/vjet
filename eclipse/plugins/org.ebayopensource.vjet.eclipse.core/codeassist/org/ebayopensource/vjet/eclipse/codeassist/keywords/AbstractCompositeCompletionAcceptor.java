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

import org.ebayopensource.dsf.jst.BaseJstNode;
import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.JstSource;
import org.ebayopensource.dsf.jst.declaration.JstMethod;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.dsf.jst.stmt.BlockStmt;
import org.ebayopensource.dsf.jst.token.IStmt;
import org.ebayopensource.dsf.jstojava.translator.JstUtil;
import org.ebayopensource.dsf.jstojava.translator.robust.completion.JstCompletion;

/**
 * This class contains additional functionality for composite completions such
 * try/catch, switch/case, if/else.
 * 
 * 
 * 
 * @param <T>
 *            parent {@link BlockStmt} object
 */
public abstract class AbstractCompositeCompletionAcceptor<T> extends
		AbstractCompletionAcceptor {

	public AbstractCompositeCompletionAcceptor() {
		super();
	}

	public boolean accept(int position, JstCompletion completion) {

		IJstType jstType = completion.getOwnerType();

		int startIndex = completion.getSource().getStartOffSet();
		// TODO convert this to IJstMethod
		IJstMethod jstMethod = (JstMethod) JstUtil.getMethod(startIndex,
				(JstType) jstType);

		boolean accept = false;

		if (jstMethod != null) {
			accept = accept(position, jstMethod);
		}

		return accept;
	}

	protected boolean accept(int position, IJstMethod jstMethod) {
		BlockStmt blockStmt = getBlock(position, jstMethod);

		boolean accept = false;

		if (blockStmt == null) {
			List<IStmt> list = jstMethod.getBlock().getStmts();
			accept = belowBlock(position, list);
		} else {
			accept = belowBlock(blockStmt, position);
		}
		return accept;
	}

	/**
	 * Returns true if current keyword below parent keyword in {@link BlockStmt}
	 * object.
	 * 
	 * @param t
	 *            {@link BlockStmt} object.
	 * @param position
	 *            current comletion position.
	 * @return true if current keyword below parent keyword in {@link BlockStmt}
	 *         object.
	 */
	protected boolean belowBlock(BlockStmt t, int position) {

		List<IStmt> list = t.getBody().getStmts();
		boolean belowBlock = false;

		if (list != null) {
			belowBlock = belowBlock(position, list);
		}

		// check that completion position below statement and statement is
		// parent of the composite statements
		if (belowBlock && getStatementClass().equals(t.getClass())) {
			List<BaseJstNode> dependsStmts = getDependsStatements((T) t);
			// check that completion position not in depends statements
			belowBlock = notInDependsStatements(position, dependsStmts);
		}

		return belowBlock;
	}

	/**
	 * Return true if completion position below then list of the {@link IStmt}
	 * objects.
	 * 
	 * @param position
	 *            current completion position.
	 * @param list
	 *            list of the {@link IStmt} objects.
	 * @return true if completion position below then list of the {@link IStmt}
	 *         objects.
	 */
	protected boolean belowBlock(int position, List<IStmt> list) {

		boolean belowBlock = false;

		if (list != null) {

			for (IStmt stmt : list) {

				if (getStatementClass().equals(stmt.getClass())) {

					BlockStmt tryStmt = (BlockStmt) stmt;
					belowBlock = belowSource(tryStmt, position);

					if (belowBlock) {
						break;
					}
				}
			}
		}

		return belowBlock;
	}

	/**
	 * Returns true if position below then source of the block statement.
	 * 
	 * @param stmt
	 *            block statement.
	 * @param position
	 *            current completion position.
	 * @return true if position below then source of the block statement.
	 */
	protected boolean belowSource(BlockStmt stmt, int position) {

		List<BaseJstNode> dependsStmts = getDependsStatements((T) stmt);
		boolean below = belowSource(stmt.getSource(), position);

		if (below) {
			below = notInDependsStatements(position, dependsStmts);
		}

		return below;
	}

	/**
	 * Return true if completion not in depends statements.
	 * 
	 * @param position
	 *            current completion position.
	 * @param dependsStmts
	 *            list of the depends statements.
	 * @return true if completion not in depends statements.
	 */
	private boolean notInDependsStatements(int position,
			List<BaseJstNode> dependsStmts) {

		boolean notInSource = true;

		for (BaseJstNode stmt : dependsStmts) {

			if (stmt != null && stmt.getSource() != null) {
				notInSource = !inSource(stmt.getSource(), position);
				if (!notInSource) {
					break;
				}
			}
		}
		return notInSource;
	}

	/**
	 * Returns list of the {@link BaseJstNode} depends statements for parent
	 * statement.
	 * 
	 * @param expr
	 *            parent statement.
	 * @return list of the {@link BaseJstNode} depends statements.
	 */
	protected abstract List<BaseJstNode> getDependsStatements(T expr);

	/**
	 * Returns parent statement class object.
	 * 
	 * @return parent statement class object.
	 */
	protected abstract Object getStatementClass();

	protected boolean belowSource(JstSource source, int position) {
		return source.getEndOffSet() < position;
	}

}