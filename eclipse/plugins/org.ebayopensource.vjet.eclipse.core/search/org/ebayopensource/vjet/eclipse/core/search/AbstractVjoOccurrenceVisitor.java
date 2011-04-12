/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.core.search;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.JstSource;
import org.ebayopensource.vjet.eclipse.core.ts.GenericVisitor;

public abstract class AbstractVjoOccurrenceVisitor extends GenericVisitor {

	private Set<IJstNode>		m_foundNodes;
	private ArrayList<VjoMatch>	m_matches;
	private String				m_matchName;
	private IJstNode			m_matchNode;

	public AbstractVjoOccurrenceVisitor(IJstNode matchNode) {
		super();

		this.m_matchNode = matchNode;

		m_matches = new ArrayList<VjoMatch>();
		m_foundNodes = new HashSet<IJstNode>();
	}

	/**
	 * matched occurrences
	 * 
	 * @return {@link List}
	 */
	public List<VjoMatch> getMatches() {
		return m_matches;
	}

	/**
	 * if node added before, the operation would return directly.
	 * 
	 * @param matchedNode
	 */
	protected void addMatch(IJstNode matchedNode) {
		// check duplicate nodes
		if (m_foundNodes.contains(matchedNode)) {
			return;
		}
		m_foundNodes.add(matchedNode);
		JstSource source = matchedNode.getSource();
		if (source == null) {
			return;
		}
		VjoMatch match = VjoMatchFactory.createOccurrenceMatch(matchedNode,
				source.getStartOffSet(), source.getLength());
		m_matches.add(match);
	}

	/**
	 * @return the matchNode
	 */
	protected IJstNode getMatchNode() {
		return m_matchNode;
	}

	protected boolean matchName(String name) {
		return m_matchName.equals(name);
	}

	protected boolean matchNode(IJstNode node) {
		return m_matchNode.equals(node);
	}

	protected void setMatchName(String name) {
		this.m_matchName = name;
	}

}