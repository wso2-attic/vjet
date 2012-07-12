/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dom.util;

import java.io.IOException;
import java.io.Writer;

import org.ebayopensource.dsf.common.xml.IIndenter;
/**
 * Indenter used by HtmlLineLocator to locate line number of a given DOM tree.
 */

public interface ILineLocateIndenter extends IIndenter{
	static String NEW_LINE = "\n";	
	static String NEW_LINE_2 = System.getProperty("line.separator");
		
	/**
	 * Gets the current line number that is hold by the locator. It will be used 
	 * by HtmlWriter to addLineNumber for each node.
	 * 
	 * @return
	 *         the value of the current line number
	 */
	String getLineNumber();
	
	/**
	 * Counts line numbers for the given text.
	 *  
	 * @return
	 *         the original text, or a text that contain lineNumber at the 
	 *         beginning of each line
	 */
	String lineNumberText(String text);	
	
	/**
	 * Default LineLocateIndenter uses line break for indent.
	 */
	public static class DefaultLineLocateIndenter implements ILineLocateIndenter {
		protected int m_numWrites = 0;
			
		public void indent(final Writer writer, final int level)
			throws IOException
		{
			if (m_numWrites++ > 0) {
				writer.write(NEW_LINE);
			}			
		}
		
		public String getLineNumber(){
			return String.valueOf(m_numWrites + 1);
		}	
		
		public String lineNumberText(String text){
			if (text == null){
				return null;
			}			
			String modTxt = text.replaceAll(NEW_LINE_2, NEW_LINE);			
			int idx = modTxt.indexOf(ILineLocateIndenter.NEW_LINE);
			while (idx != -1){
				m_numWrites ++;
				idx = modTxt.indexOf(NEW_LINE, idx + 1);
			}	
			return text;
		}
	};
	
	/**
	 * Indenter that will add line number to the beginning of each line.
	 */
	public static class LineNumberIndenter extends DefaultLineLocateIndenter {		
		@Override
		public void indent(final Writer writer, final int level)
			throws IOException
		{
			super.indent(writer, level);
			writer.write(String.valueOf(m_numWrites));
		}
		@Override
		public String lineNumberText(String text){
			if (text == null){
				return null;
			}			
			
			String modTxt = text.replaceAll(NEW_LINE_2, NEW_LINE);			
			
			int idx = modTxt.indexOf(ILineLocateIndenter.NEW_LINE);
			if (idx == -1){
				return text;
			}
			StringBuffer tmp = new StringBuffer();
			int preIdx = 0;
			while (idx != -1){				
				if (preIdx != 0) {
					tmp.append(++m_numWrites);
				}				
				tmp.append(modTxt.substring(preIdx, idx + 1));				
				preIdx = idx + 1;
				idx = modTxt.indexOf(ILineLocateIndenter.NEW_LINE, preIdx);
			}	
			if (preIdx != 0) {
				tmp.append(++m_numWrites);
				tmp.append(modTxt.substring(preIdx));
			}			
			return tmp.toString();			
		}		
	};
}
