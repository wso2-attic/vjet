/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.css.parser;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.ebayopensource.dsf.common.FileUtils;

/**
 * Generates the SacParser CSS parser files from the css3-modified.jj JavaCC grammar file
 * 
 * To generate the parser, do the following steps:
 * - check out the following files:
 *     CharStream.java
 *     ParseException.java
 *     SacParser.java
 *     SacParserConstants.java
 *     SacParserTokenManager.java
 *     Token.java
 *     TokenMgrError.java
 * - run this class as a "Java application" (no parser generation warnings or errors
 *   should be displayed on the console during this step)
 * - open SacParser.java and do all the items listed in the first "todo" section
 * - run JUnit on AllCssTests to make sure all unit tests for the CSS parser pass
 * 
 * JavaCC version 3.1 MUST be used to generate SacParser.  Newer versions of JavaCC
 * cause performance problems when running in debug mode because of a line generated
 * by JavaCC in SacParser that reads:
 *   if (jj_la == 0 && jj_scanpos == jj_lastpos) throw jj_ls;
 * The "throw" in this line causes the debugger to run up to 1,000 times slower than
 * a regular run.  JavaCC v3.1 does not use this "throw" statement and is thus fast
 * even in debug mode. 
 */
public class GenerateSacParser {

	/**
	 * To run this main, please add javacc.jar (version 5 or above) into the
	 * classpath of eclipse launcher for this java application (from Run Configurations).
	 * 
	 * Sample location: \externalv3\javacc\5.0\javacc.jar
	 */
	public static void main(String[] args) {
		String grammarFilePath = getPath() ;
		
		// Use JavaCC to generate the parser
		GenerateSacParser.generate(grammarFilePath) ;
		
		// Do the text fixes to the generated parser
		GenerateSacParser.postGenFixes(grammarFilePath) ;
	}
	
	public static void generate(String grammarFilePath) {
		String grammarFileName = grammarFilePath + "css3-modified.jj";
		try {
			Class<?> javaccMain = Class.forName("org.javacc.parser.Main");
			Method mainProgram = javaccMain.getMethod("mainProgram", String[].class);
			int retCode = (Integer) mainProgram.invoke(null,
				new Object[] {new String[]{"-OUTPUT_DIRECTORY=" + grammarFilePath, grammarFileName}});
			System.out.println("javacc return code: " + retCode) ;	//KEEPME
		}
		catch(Exception e) {
			throw new RuntimeException(e) ;
		}
	}
	
	/*
	 * We have to work on the physical file path and NOT a resource-as-stream
	 * approach since the SacParser.java is just source and is not compiled
	 * at this point and thus not available via an anchor class lookup.
	 */
	private static void postGenFixes(String path) {
		LineNumberReader lineReader = null;
		FileReader reader = null;
		try {
			String sacParserPath = path + "/SacParser.java" ;
			
			reader = new FileReader(sacParserPath) ;
			lineReader = new LineNumberReader(reader) ;
			String line = lineReader.readLine();
			StringBuffer buffer = new StringBuffer(5000) ;
			while(line != null) {	
				buffer.append(line) ;
				buffer.append("\n") ;
				line = lineReader.readLine() ;
			}
			
			String sacParserText = buffer.toString() ;
			String fixedParserText = fixGennedCode(sacParserText) ;
			
			// We now write out the "fixed" SacParser.java
			File f = new File(sacParserPath) ;
			if (!f.delete() && f.exists()) {
				System.out.println("unable to delete file "+f.getAbsolutePath()) ;	//KEEPME
			}
			if(!f.createNewFile()) {
				System.out.println("file " +f.getAbsolutePath()+ " already exists") ;	//KEEPME
			}
			f.setWritable(true) ;
	
			FileUtils.writeFile(sacParserPath, fixedParserText, "utf-8") ;
			
			System.out.println(fixedParserText) ;	//KEEPME
		}
		catch(Exception e) {
			throw new RuntimeException(e) ;
		} finally {
			if (lineReader != null) {
				try {
					lineReader.close();
				} catch (IOException e) {
					// NOPMD - ignore
				}
			}
		}
	}
	
	private static String fixGennedCode(String sacParserText) {
		List<FixPair> pairs = getFixPairs() ;
		
		// 0. Our starting point replacement index is 0
		int pos = 0 ;
		
		// 1. get initial starting point for fix pairs to start working from
		String s = "private boolean jj_semLA;";
		pos = sacParserText.indexOf(s) + s.length() ;
		
		// 2. loop over pairs replacing as we go and updating next starting pos
		ReplaceResponse rr ;
		for(FixPair pair: pairs) {
			String findText = pair.getToBeFixed() ;
			String replacementText = pair.getReplacement() ;
			rr = replace(sacParserText, pos, findText, replacementText);
			
			// update pos and text for our next replacement
			pos = rr.m_pos ;
			sacParserText = rr.m_text ;
		}
		
		return sacParserText ;
	}
	
	private static ReplaceResponse replace(
		String s, int pos, String findText, String replacementText)
	{
		// Look for findText from starting pos
		int startOfFindText = s.indexOf(findText, pos) ;
		if (startOfFindText == -1) { // error
			throw new RuntimeException(
				"From position: " + pos + " we did not see: " + findText) ;
		}
		
		String firstPart = s.substring(0, startOfFindText) ;
		int startOfRemainder = startOfFindText + findText.length() ;
		String remainder = s.substring(startOfRemainder) ;
		
		ReplaceResponse rr = new ReplaceResponse() ;
		rr.m_text = firstPart + replacementText + remainder ;
		// Our position is where we started + the size of the replace text
		rr.m_pos = startOfFindText + replacementText.length() ; 
		return rr ;
	}
	
	private static class ReplaceResponse {
		public int m_pos ;
		public String m_text ;
	}
	
	private static List<FixPair> getFixPairs() {
		List<FixPair> fixPairs = new ArrayList<FixPair>() ;
		
		fixPairs.add(new FixPair(
			"private boolean jj_semLA;", 
			"// private boolean jj_semLA;")) ;
		
		fixPairs.add(new FixPair(
			"private java.util.Vector jj_expentries = new java.util.Vector();", 
			"private List<int[]> jj_expentries = new ArrayList<int[]>();")); 

//   for(java.util.Enumeration enum = jj_expentries.elements(); enum.hasMoreElements();) {
//	     int[] oldentry = (int[])(enum.nextElement());
		
// We need 2 fixPairs to describe the above 2 lines
// 1.Find the for statement and just replace it with a ""
// 2.The next find will catch the second int[] oldentry... and replace it
		fixPairs.add(new FixPair(
			"for (java.util.Enumeration enum = jj_expentries.elements(); enum.hasMoreElements();) {",
			"")) ;
		fixPairs.add(new FixPair(
			"int[] oldentry = (int[])(enum.nextElement());",
			"for(int[] oldentry : jj_expentries) {")) ;
		
		fixPairs.add(new FixPair(
			"jj_expentries.addElement(jj_expentry);",
			"jj_expentries.add(jj_expentry);")) ;
			
		// We do this just to position us to the right location for the
		// next round of fixes
		fixPairs.add(new FixPair(
			"public ParseException generateParseException() {",
			"public ParseException generateParseException() {")) ;
				
		fixPairs.add(new FixPair(
			"jj_expentries.removeAllElements();",
			"jj_expentries.clear();")) ;
			
		fixPairs.add(new FixPair(
			"jj_expentries.addElement(jj_expentry);",
			"jj_expentries.add(jj_expentry);")) ;
			
		fixPairs.add(new FixPair(
			"exptokseq[i] = (int[])jj_expentries.elementAt(i);",
			"exptokseq[i] = jj_expentries.get(i);")) ;
			
		return fixPairs ;
	}
	
	private static class FixPair {
		private String m_toBeFixed ;  // exact text to be fixed
		private String m_replacement ;// exact text to replace the text to be fixed
		
		FixPair(String toBeFixed, String replacement) {
			m_toBeFixed = toBeFixed ;
			m_replacement = replacement ;
		}
		
		public String getToBeFixed() {
			return m_toBeFixed ;
		}
		public String getReplacement() {
			return m_replacement ;
		}
	}
	
	private static String getPath() {
		File root = new File(".");
		char c = File.separatorChar;
		String clzName = DCssBuilder.class.getName() ;
		try {
			String path = root.getCanonicalPath() + c + "src" + c 
				+ clzName.substring(0, clzName.lastIndexOf('.')).replace('.', c) + c;
			return path ;
		}
		catch(Exception e) {
			throw new RuntimeException(e) ;
		}
	}
}
