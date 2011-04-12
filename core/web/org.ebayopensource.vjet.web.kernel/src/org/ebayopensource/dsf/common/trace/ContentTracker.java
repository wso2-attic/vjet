/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.trace;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ContentTracker {
	
	private Map<String, Integer> m_traceback = new HashMap<String, Integer>();
	
	private static final String BEGIN_INS = "<@#";
	private static final String END_INS = "#@>";
	
	public ContentTracker(Map<String, Integer> contentMap) {
		m_traceback = contentMap;
	}
	
	public Map<String, Integer> getTraceBackMap() {
		return m_traceback;
	}
	
	private Integer addToTrackbackMap(String text, String id, String instrument) {
		
		// replace " and | in text as they are reserved in the content table
		text = replaceDblQuoteAndPipe(text);
		
		if (text.length() > 20) { // truncate strings longer than 20 characters
			text = text.substring(0, 20) + "...";			
		}			
		
		String key = text + "|" + id + "|" + instrument;
		
		if (m_traceback.containsKey(key)) {
			return m_traceback.get(key);
		} else {
			m_traceback.put(key,
				Integer.valueOf(m_traceback.size()));
			return m_traceback.get(key);
		}
	}
	
	public static class InstrumentedText {
		private String m_text;
		private Integer m_traceIdx;
		
		private InstrumentedText(String text, Integer idx) {
			m_text = text;
			m_traceIdx = idx;
		}
		
		public String getText() {
			return m_text;
		}
		
		public Integer getIdx() {
			return m_traceIdx;
		}
	}
	
	// return an instrumented string with idx to cotent id and content unit class name
	// appended
	public String instrumentPlainText(String text, String id, Object contentUnit) {
		
		StringBuilder builder = new StringBuilder();
		
		Integer idx = addToTrackbackMap(text, id, contentUnit.getClass().getName() + "." + id);
		
		builder.append(text).append(BEGIN_INS).append(idx).append(END_INS);
		
		return builder.toString();		
	}
	
	// instrument DContent by storing the instruments in the hash map
	//
	public void instrumentDContent(IContentTrace dcontent, String text, String id, Object contentUnit) {
		
		Integer idx = addToTrackbackMap(text, id, contentUnit.getClass().getName() + "." + id);
		
		dcontent.setContentInstrumentation(idx.toString() + " ");	
	}
	
	private static String replaceDblQuoteAndPipe(String text) {
		String newtxt = text.replaceAll("\"", "\'");
		
		return newtxt.replace("|", "/"); // pipe '|' is used as field seperator
	}
	
	// get all instrumented elements indexes to the traceback table
	//
	public static InstrumentedText[] getAllTracebacks(final String value) {
		ArrayList<Integer> startIndexs = new ArrayList<Integer>();
		ArrayList<Integer> endIndexs = new ArrayList<Integer>();
		
		int startIdx = 0;
		int endIdx = 0;
		
		int current = 0;
		while (startIdx >= 0 && endIdx >= 0){
			startIdx = value.indexOf(BEGIN_INS, endIdx);
			
			if (startIdx >= 0) {
				startIndexs.add(Integer.valueOf(startIdx));
			
				endIdx = value.indexOf(END_INS, startIdx);
				if (endIdx >=0) {
					endIndexs.add(Integer.valueOf(endIdx));
					current ++;
				}
				else {
					startIndexs.remove(current);
				}
			}
		}
		
		if (startIndexs.size() == 0) {
			return new InstrumentedText[0]; // return a 0 dimention array for non-instrumentation
		}
			
		InstrumentedText[] instrumentedTexts = new InstrumentedText[startIndexs.size()+1]; // add last chunk with no instrument
		
		int begin = 0;
		int beginoffset = BEGIN_INS.length();
		int endoffset = END_INS.length();
		
		int i = 0;
		for (i = 0; i < startIndexs.size(); i++) {
			String text = value.substring(begin, startIndexs.get(i));
			String idxStr = value.substring(startIndexs.get(i) + beginoffset, endIndexs.get(i));
			idxStr = removeNonDigitChars(idxStr);
			int idx = Integer.parseInt(idxStr);
			instrumentedTexts[i] = new InstrumentedText(text, idx);
			begin = endIndexs.get(i) + endoffset;			
		}
		
		// add last chunk of text if there is no instrumentation
		instrumentedTexts[i] = new InstrumentedText(value.substring(begin), -1);
		
		return instrumentedTexts;		
	}
	
	private static String removeNonDigitChars(String str) {
		char[] charArray = str.toCharArray();
				
		int newidx = 0;
		
		for (int idx = 0; idx < charArray.length; idx ++) {
			char ch = charArray[idx];
			
			if (ch >= '0' && ch <= '9') {
				
				if (newidx < idx) {
					charArray[newidx] = charArray[idx];
				}
				newidx ++;
			}			
		}
		
		if (newidx == charArray.length)
			return str;
		else {
			char[] newArray = new char[newidx];
			System.arraycopy(charArray, 0, newArray, 0, newidx);
			return new String(newArray);
		}
	}
	
}
