/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.codeassist.keywords;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.dltk.mod.compiler.env.ISourceModule;

public class SimpleKeywordCompletionEngine implements KeywordCompletionEngine {

	private static final char TRIGGER_SPACE = ' ';
	private static final char TRIGGER_TAB = '\t';
	private static final char TRIGGER_DOT = '.';
	private static final char TRIGGER_SEMICOLON = ';';
	private static final char TRIGGER_COLON = ':';
	private static final char TRIGGER_LPARENTHESIS = '(';
	private static final char TRIGGER_RPARENTHESIS = ')';
	private static final char TRIGGER_LBRACE = '{';
	private static final char TRIGGER_RBRACE = '}';
	private static final char TRIGGER_NEWLINE_1 = '\r';
	private static final char TRIGGER_NEWLINE_2 = '\n';

	private static final String TRIGGER_LINE = " \t.;:{}()\r\n";

	private static final String EMPTY = "";

	private int cursor;

	private char[] cntx;
	private int initPos;
	
	private int startPosToComplete;

	private static VjoKeywordCompletionResult prevResult = null;

	private List<IVjoCompletionData> keywords = new ArrayList<IVjoCompletionData>();
	
	public SimpleKeywordCompletionEngine(ISourceModule module, int initPos) {
		this.cntx = module.getSourceContents().toCharArray();
		this.initPos = initPos - 1;
	}

	private char[] getInvertedBackwardBlock(int size) {

		int amount = cursor < size ? cursor + 1 : size;
		int limit = cursor - amount;
		char[] block = new char[amount];

		for (int iter = cursor; iter > limit; iter--) {
			block[cursor - iter] = cntx[iter];
		}

		return block;
	}

	private void processingContext() {

		boolean stopSearching = false;
		
		cursor = initPos;

		int bCounter = 0; // braces counter {
		int pCounter = 0; // parenthesis counter (

		int unclosedBFlags = 0; // right braces counter
		int unclosedPFlags = 0; // right braces counter

		while (cursor != -1 && !stopSearching) {

			switch (cntx[cursor]) {

			case TRIGGER_RPARENTHESIS: // )

				// maybe this is end of top-level keywords such as protos,
				// props, ect.
				// this can be also end of function parameter declaration
				
				if (pCounter == 0) {

					IVjoCompletionData nearestKeyword = new VjoKeywordPatternHelper(
							getInvertedBackwardBlock(100)).lookupNearestKeyword();

					if (nearestKeyword != null) { // if null still searching

						if (nearestKeyword.isTopLevelKeyword()
								&& !nearestKeyword.isComposableKeyword()) {
							
							keywords.addAll(((ITopLevel) nearestKeyword).getPeerKeywords());
							stopSearching = true;

						} else if (nearestKeyword.isComposableKeyword()) {

							keywords.addAll(((IComposable) nearestKeyword).getAllowedCompositeKeywords());
							stopSearching = true;

						}
					}

				}

				unclosedPFlags++;
				pCounter++; // increase parenthesis counter
				break;

			case TRIGGER_LPARENTHESIS: // (

				// maybe this is start of top-level keywords such as protos,
				// props, ect.
				// this can be also start of function parameter declaration
				pCounter--; // decrease parenthesis counter
				break;

			case TRIGGER_RBRACE: // }

				unclosedBFlags++;
				bCounter++; // increase braces counter
				break;

			case TRIGGER_LBRACE: // {

				if (bCounter == 0) {

					// we are inside some block
					// lookup for the nearest keyword
					IVjoCompletionData nearestKeyword = new VjoKeywordPatternHelper(
							getInvertedBackwardBlock(100)).lookupNearestKeyword();
					
					stopSearching = true;
					if(nearestKeyword != null){
						keywords.addAll( ((IEnclosable) nearestKeyword).getAllowedEnclosedKeywords());
					}
					// else specified keyword isn't implemented yet 

				} 
				else if (bCounter == 1) {

					if (pCounter == 1) {

						// we are outside of top-level keyword such as
						// protos, props, ect...
						IVjoCompletionData nearestKeyword = new VjoKeywordPatternHelper(
								getInvertedBackwardBlock(100)).lookupNearestKeyword();

						keywords.addAll(((IComposable) nearestKeyword).getAllowedCompositeKeywords());
						stopSearching = true;

					} else if (unclosedBFlags == 1 && unclosedPFlags == 0) {

						// maybe we are after unclosed keyword like 'do'
						// we need to get complemented parts for it

						IVjoCompletionData nearestKeyword = new VjoKeywordPatternHelper(
								getInvertedBackwardBlock(100)).lookupNearestKeyword();

						if (nearestKeyword != null && nearestKeyword.isUnclosed()) {

							keywords = ((IUnclosed) nearestKeyword).getComplementedKeywords();

							// store last position
							int cur = cursor;

							// filter obtained complemented keywords
							filter();
							
							stopSearching = !((IUnclosed)nearestKeyword).isSelfSufficient();

							// reset cursor to the last saved position
							cursor = cur;

						}
						// else still searching...

					}
				}// end bCounter == 1

				bCounter--; // decrease breaces counter
				// ---- end case TRIGGER_LBRACE

			}// end switch

			cursor--;

		}// end while

		if (cursor == -1 && !stopSearching) {

			// we are at the start of editor page
			// get all top-level keywords

			keywords.addAll(VjoKeywordFactory.getTopLevelKeywords());

		}

	}

	private void filter() {

		List<IVjoCompletionData> filteredKeywords = new ArrayList<IVjoCompletionData>();

		// return to the initial position
		cursor = initPos;

		char[] invertedBlock = getInvertedBackwardBlock(100);

		Iterator<IVjoCompletionData> iterator = keywords.iterator();
		while (iterator.hasNext()) {

			IVjoCompletionData keyword = iterator.next();

			if (new VjoKeywordPatternHelper(invertedBlock)
					.isSuitableForCompleteion(keyword)) {

				cursor = initPos;
				StringBuffer buf = new StringBuffer();

				// search word to complete
				while (cursor > -1 && (TRIGGER_LINE.indexOf(cntx[cursor]) == -1 ||
						keyword.isAllowedTrigger(cntx[cursor]))) {
					buf.insert(0, cntx[cursor--]);
				}

				String word = buf.toString();
				startPosToComplete = cursor + 1;

				if (keyword.canComplete(word)) {
					filteredKeywords.add(keyword);
				}

			}

			keywords = filteredKeywords;

		}

	}

	/* (non-Javadoc)
	 * @see org.ebayopensource.vjet.eclipse.codeassist.keywords.KeywordCompletionEngine#getCompletionResult()
	 */
	public VjoKeywordCompletionResult getCompletionResult() {

		// parsing context
		processingContext();

		// filter obtained keywords
		filter();

		// return filtered keywords
		return getCompletionResultForVjoKeyword(keywords, startPosToComplete);

	}

	private static VjoKeywordCompletionResult getCompletionResultForVjoKeyword(
			List<IVjoCompletionData> keywords, int startPosToComplete) {
		prevResult = new VjoKeywordCompletionResult(keywords,
				startPosToComplete);
		return prevResult;
	}
	

	public static VjoKeywordCompletionResult getJustObtainedResult() {
		return prevResult;
	}

}
