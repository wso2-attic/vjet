/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html;

import org.ebayopensource.dsf.dom.DNode;
import org.ebayopensource.dsf.dom.util.ILineLocateIndenter;

/**
 * Helper class to locate line number for a give html tree
 *
 */
public class HtmlLineLocator {
	/**
	 * Add line number for nodes inside the give htmlTree based on the given indenter.
	 * Add line number operation will Skip empty DText.
	 * After addLineNumber to the htmlTree, application 
	 * can retrive the line number from any nodes within this htmlTree by calling: 
	 * node.getUserData(HtmlWriter.START_LINE
	 * @param htmlTree
	 * @param indenter
	 */
	public static void addLineNumber(DNode htmlTree, ILineLocateIndenter indenter){
		HtmlWriterCtx opt = new HtmlWriterCtx(indenter)
			.setAddLineNumber(true)
			.setTrimDText(true);
		HtmlWriterHelper.write(htmlTree, opt);
		
	}
	
	/**
	 * Get string text for the given htmlTree and add line number to the htmlTree.
	 * If the given htmlTree has empty DText, the return string text will ignore those empty DText.  
	 * Based on the given indenter, returned string text may have the line number at the beginning of each line, such as
	 * 1<html>
	 * 2<body>
	 * 3<br clear="none">This
	 * 4text
	 * 5has
	 * 6many
	 * 7newlines
	 * 8
	 * 9</body>
	 * 10</html>
	 * 
	 * @param htmlTree
	 * @param indenter
	 * @return
	 */
	//<br></br>This\ntext\nhas\nmany\nnewlines\n
	public static String asString(DNode htmlTree, ILineLocateIndenter indenter){
		HtmlWriterCtx opt = new HtmlWriterCtx(indenter)
			.setAddLineNumber(true)
			.setTrimDText(true);
		HtmlWriterHelper.write(htmlTree, opt);
		return opt.getWriter().toString();
	}
}
