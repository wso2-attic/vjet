/*******************************************************************************
 * Copyright (c) 2005-2012 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.testframework.view;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.source.ISourceViewer;

/**
 * utility class regarding for range indicator.
 * 
 * @author ddodd
 *
 */
public class RangeIndicatorUtil {

	/**
	 * utility class
	 */
	private RangeIndicatorUtil() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * get range indicator for the source viewer
	 * 
	 * @param sourceViewer
	 * @return
	 */
	public static IRegion getRangeIndication(ISourceViewer sourceViewer) {
		if (sourceViewer == null)
			return null;
		
		return sourceViewer.getRangeIndication();
	}
	
	/**
	 * determine whether a range indication exists start from certain line to certain line 
	 * 
	 * @param sourceViewer
	 * @param startLine     start line number is 0 not 1
	 * @param endLine       start line number is 0 not 1
	 * @return
	 */
	public static boolean hasRangeIndication(ISourceViewer sourceViewer, int startLine, int endLine) {
		if (sourceViewer == null)
			return false;
		
		IRegion rangeIndication = sourceViewer.getRangeIndication();
		if (rangeIndication == null)
			return false;
		
		try {
			IDocument document = sourceViewer.getDocument();
			int realStartLine = document.getLineOfOffset(rangeIndication.getOffset());
			System.out.println("real start line === " + realStartLine);
			int realEndLine = document.getLineOfOffset(rangeIndication.getOffset() + rangeIndication.getLength());
			System.out.println("real end line === " + realEndLine);
			
			return (realStartLine == startLine) && (realEndLine == endLine);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
}
