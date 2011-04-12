/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.ui.html.codeassist;

import java.util.List;

import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.wst.sse.core.internal.provisional.IndexedRegion;
import org.eclipse.wst.sse.ui.internal.contentassist.ContentAssistUtils;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMText;
import org.eclipse.wst.xml.ui.internal.contentassist.AbstractContentAssistProcessor;
import org.w3c.dom.Node;

import org.ebayopensource.vjet.eclipse.internal.ui.text.completion.VjoProposalEclipsePresenter;
import org.ebayopensource.vjo.tool.codecompletion.CodeCompletionUtils;
import org.ebayopensource.vjo.tool.codecompletion.IVjoCcEngine;
import org.ebayopensource.vjo.tool.codecompletion.IVjoCcProposalData;
import org.ebayopensource.vjo.tool.codecompletion.engine.VjoCcEngine;
import org.ebayopensource.vjo.tool.typespace.TypeSpaceMgr;

public class EsfVjoContentAssistProcessor extends
		AbstractContentAssistProcessor {
	
	public static final String JS_SCRIPT_ID = "com.ebay.tools.v4.esfjet.script";
	
	public EsfVjoContentAssistProcessor() {
		super();
	}
	
	@Override
	public ICompletionProposal[] computeCompletionProposals(
			ITextViewer textViewer, int documentPosition) {
		setErrorMessage(null);

		fTextViewer = textViewer;

		IndexedRegion treeNode = ContentAssistUtils.getNodeAt(textViewer, documentPosition);

		Node node = (Node) treeNode;
		if (node.getNodeType() != Node.TEXT_NODE) {
			//Return default proposal
			return new ICompletionProposal[0];
		} else {
			IDOMText tNode = (IDOMText)node;
			String content = tNode.getNodeValue();
			int relativeOffset = documentPosition - tNode.getStartStructuredDocumentRegion().getStart();
			IVjoCcEngine engine = new VjoCcEngine(TypeSpaceMgr.parser());

			List<IVjoCcProposalData> list = engine.complete(JS_SCRIPT_ID, JS_SCRIPT_ID, content, relativeOffset);
			// printProposal(list);
			if (list.isEmpty()) {
				return new ICompletionProposal[0];
			}
			CodeCompletionUtils.printProposal(list);
			VjoProposalEclipsePresenter presenter = new VjoProposalEclipsePresenter(
					engine.getContext(), documentPosition, textViewer
					.getSelectedRange(), tNode.getStructuredDocument());
			List<ICompletionProposal> proposalList = presenter.doPresenter(list);
			ICompletionProposal[] result = new ICompletionProposal[proposalList.size()];
			proposalList.toArray(result);
			return result;
		}
//		IDOMNode xmlnode = (IDOMNode) node;
//
//		ContentAssistRequest contentAssistRequest = null;
//
//		IStructuredDocumentRegion sdRegion = getStructuredDocumentRegion(documentPosition);
//		ITextRegion completionRegion = getCompletionRegion(documentPosition, node);
//
//		String matchString = getMatchString(sdRegion, completionRegion, documentPosition);
	}


}
