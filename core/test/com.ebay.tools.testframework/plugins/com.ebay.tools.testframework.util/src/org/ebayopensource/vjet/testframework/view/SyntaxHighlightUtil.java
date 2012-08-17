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
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.ITokenScanner;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.swt.graphics.Color;

/**
 * utility class to process Syntax Highlight or Syntax Color
 * 
 * @author ddodd
 *
 */
public class SyntaxHighlightUtil {

	/**
	 * utility class
	 */
	private SyntaxHighlightUtil() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * similar to 'getBackgroundColor'
	 * 
	 * By the way, you can get foreground color and background color from 'TextAttribute'
	 * 
	 * @param sourceViewer
	 * @param offset
	 * @param length
	 * @return
	 */
	public static TextAttribute getTextAttribute(ISourceViewer sourceViewer, int offset, int length) {
		if (offset < 0 || length <= 0)
			return null;
		try {
			//content type
			IDocument document = sourceViewer.getDocument();
			String contentType = document.getContentType(offset);
			
			//presentation reconciler
			java.lang.reflect.Field prField = SourceViewer.class.getDeclaredField("fPresentationReconciler");
			prField.setAccessible(true);
			IPresentationReconciler fPresentationReconciler = (IPresentationReconciler)prField.get(sourceViewer);
			
			//token scanner
			DefaultDamagerRepairer dr = (DefaultDamagerRepairer)fPresentationReconciler.getDamager(contentType);
			java.lang.reflect.Field fScannerFiled = DefaultDamagerRepairer.class.getDeclaredField("fScanner");
			fScannerFiled.setAccessible(true);
			ITokenScanner fScanner = (ITokenScanner)fScannerFiled.get(dr);
			fScanner.setRange(document, offset, length);
			
			//token
			IToken token = fScanner.nextToken();
			if (token != null)
				return (TextAttribute)token.getData();
			
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * fetch the syntax background color for the region (offset and length)
	 * 
	 * @param sourceViewer
	 * @param offset
	 * @param length
	 * @return
	 */
	public static Color getBackgroundColor(ISourceViewer sourceViewer, int offset, int length) {
		TextAttribute textAttribute = getTextAttribute(sourceViewer, offset, length);
		if (textAttribute == null)
			return null;
		
		return textAttribute.getBackground();
	}
	
	/**
	 * fetch the syntax foreground color for the region (offset and length)
	 * 
	 * @param sourceViewer
	 * @param offset
	 * @param length
	 * @return
	 */
	public static Color getForegroundColor(ISourceViewer sourceViewer, int offset, int length) {
		TextAttribute textAttribute = getTextAttribute(sourceViewer, offset, length);
		if (textAttribute == null)
			return null;
		
		return textAttribute.getForeground();
	}
	
}
