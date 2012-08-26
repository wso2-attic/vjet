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
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.TextViewer;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.ITokenScanner;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewer;

/**
 * @author ddodd
 *
 */
public final class ViewerUtils {
	
	private ViewerUtils(){;}
	
	
	/**
	 * Get special text attribute 
	 * <b>the partition is defined by specific editor</b>
	 * vjet already defines the class of IJavaScriptPartitions,It has following partitions:  
	 * <li>__javascript_comment</li>
	 * <li>__javascript_string</li>
	 * <li>__javascript_doc</li>
	 * <li>__dftl_partition_content_type</li>
	 *    
	 * @see TextAttribute
	 * @param sourceViewer
	 * @param partition
	 * @param position 
	 * @return
	 */
	public static TextAttribute getTextAttribute(ISourceViewer sourceViewer,String partition,Position position){
		try {
			java.lang.reflect.Field docField = TextViewer.class.getDeclaredField("fVisibleDocument");
			docField.setAccessible(true);
			IDocument document = (IDocument)docField.get(sourceViewer);
			java.lang.reflect.Field prField = SourceViewer.class.getDeclaredField("fPresentationReconciler");
			prField.setAccessible(true);
			IPresentationReconciler fPresentationReconciler = (IPresentationReconciler)prField.get(sourceViewer);
			DefaultDamagerRepairer dr = (DefaultDamagerRepairer)fPresentationReconciler.getDamager(partition);
			java.lang.reflect.Field fScannerFiled = DefaultDamagerRepairer.class.getDeclaredField("fScanner");
			fScannerFiled.setAccessible(true);
			ITokenScanner fScanner = (ITokenScanner)fScannerFiled.get(dr);
			fScanner.setRange(document, position.getOffset(), position.getLength());
//			fScanner.setRange(document,8, 1);
			IToken token = fScanner.nextToken();
			if(token != null)
				return (TextAttribute)token.getData();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}


}
