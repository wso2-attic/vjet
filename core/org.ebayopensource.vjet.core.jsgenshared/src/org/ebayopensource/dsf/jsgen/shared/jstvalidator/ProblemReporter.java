/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsgen.shared.jstvalidator;

import java.io.PrintStream;
import java.util.List;

import org.ebayopensource.dsf.jsgen.shared.validation.common.IJstValidationPolicy;
import org.ebayopensource.dsf.jst.IScriptProblem;
import org.ebayopensource.dsf.jst.ProblemSeverity;

public class ProblemReporter {

	public static final char NEWLINE = System.getProperty("line.separator").charAt(0);
	
	private IJstValidationPolicy m_policy;
	private char[] m_fileSource;

	
	public void printToConsole(List<IScriptProblem> prbs, char[] fileSource){
		m_fileSource = fileSource;
		for(IScriptProblem p:prbs){
			printProblem(System.out,p);
		}
	}

	private void printProblem(PrintStream out, IScriptProblem p) {
		if(p.type()==ProblemSeverity.error){
			out.print("ERROR:");
		}
		if(p.type()==ProblemSeverity.warning){
			out.print("WARNING:");
		}
		out.println("Problem Type:" + p.getID().getName());
		if(p.getMessage()!=null){
			out.println(p.getMessage());
		}
		
		out.println("in file " + new String(p.getOriginatingFileName()));
		printLocationInfo(out,p);
		out.println("\n-------------------------");
		
		
	}

	private void printLocationInfo(PrintStream out,IScriptProblem p) {
		out.println("line : " + p.getSourceLineNumber() + " col: " + p.getColumn());
		
		int lineCount = 1;
		int startPrinting = 0;
		for(int i=0;i<p.getSourceEnd()&&i<m_fileSource.length;i++){
			if(m_fileSource[i]==NEWLINE){
				lineCount++;
			}
			if(lineCount==p.getSourceLineNumber()){
				if(startPrinting==0){
					startPrinting = i;
				}
				out.print(m_fileSource[i]);
			}
		}
		out.println();
		for(int j=0;j<=p.getColumn();j++){
			if(j==p.getColumn()){
				out.println("^");
			}else{
				if(startPrinting+j < m_fileSource.length 
						&& m_fileSource[startPrinting+j] != '\t'){
					out.print(" ");	
				}else{
					out.print("\t");
				}
			}
			
		}
		
	}


	
}
