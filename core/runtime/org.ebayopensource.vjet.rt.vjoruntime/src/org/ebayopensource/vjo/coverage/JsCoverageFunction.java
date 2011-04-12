/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.coverage;


public class JsCoverageFunction {

	private String functionName;
	private int [] trackLineCoverage;
	private int curLineCovCount;
	private int totalLines;
	private String js;
	private boolean covered = false;
	
	//empty constructor so deserialization can happen.
	public JsCoverageFunction(){
	}
	
	public JsCoverageFunction(String name) {
		functionName = name;
//		try {
//			js = FileUtils.readFile("D:/ccviews/d_sjc_alpatel1_gort589_2/v4darwin/DSFVjoDef/src/org.ebayopensource.vjo/VjBootStrap_3.js", "cp1252");
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	public String getFunctionName() {
		return functionName;
	}

	public int[] getTrackLineCoverage() {
		return trackLineCoverage;
	}
	
	public void setTrackLineCoverage(int [] trkLineCov) {
		trackLineCoverage = trkLineCov;
	}
	
	public void lineCovered(int lineNumber) {
		if(this.trackLineCoverage[lineNumber] <= 0){
			curLineCovCount = curLineCovCount + 1;
			covered = true;
		}
		this.trackLineCoverage[lineNumber] = this.trackLineCoverage[lineNumber] + 1;
	}
	
	public int getCurLineCovCount() {
		return curLineCovCount;
	}
	
	public void setCurLineCovCount(int curLineCt) {
		curLineCovCount = curLineCt;
	}

	public int getTotalLines() {
		return totalLines;
	}

	public void setTotalLines(int lines) {
		trackLineCoverage = new int [lines];
		totalLines = lines;
	}
	
	public String getJs() {
		return js;
	}

	public void setJs(String rawJs) {
		js = rawJs;
	}
	
	public boolean isCovered(){
		return covered;
	}
}
