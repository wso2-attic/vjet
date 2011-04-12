/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.test.utils.vjo;

import java.net.URL;
import java.util.Iterator;
import java.util.List;

import org.ebayopensource.dsf.jst.declaration.JstType;

public class Java2JstInfo {

	private JstType javaJstType;
	private String vjoExpected;
	private String jsrExpected;
	private String vjoActual;
	private String jsrActual;
	private JstType jsJstJsr;
	private URL jsrFilename;
	private URL vjoFilename;
	private boolean hasJsrCompareFailure = false;
	private boolean hasJsrCompileFailure = false;
	private boolean hasVjoFailure = false;
	private List<?> jsrCompileFailures;
	
	public Java2JstInfo(JstType jstType){
		this.javaJstType = jstType;
	}

	public JstType getJstType() {
		return javaJstType;
	}

	public String getExpectedVjo() {
		return vjoExpected;
	}
	
	public String getExpectedJsr() {
		return jsrExpected;
	}
	
	public String getActualVjo() {
		return vjoActual;
	}
	
	public String getActualJsr() {
		return jsrActual;
	}
	
	public URL getJsrFilename() {
		return jsrFilename;
	}
	
	public URL getVjoFilename() {
		return vjoFilename;
	}
	
	public void setExpectedVjo(String vjo) {
		this.vjoExpected =  vjo;
	}
	
	public void setExpectedJsr(String jsr) {
		this.jsrExpected = jsr;
	}
	
	public void setActualVjo(String vjo) {
		this.vjoActual =  vjo;
	}
	
	public void setActualJsr(String jsr) {
		this.jsrActual = jsr;
	}
	
	public void setHasVjoFailure(boolean flag) {
		this.hasVjoFailure = flag;
	}
	
	public void setHasJsrCompareFailure(boolean flag) {
		this.hasJsrCompareFailure = flag;
	}
	
	public boolean hasJsrCompareFailure() {
		return hasJsrCompareFailure;
	}
	
	public void setHasJsrCompileFailure(boolean flag) {
		this.hasJsrCompileFailure = flag;
	}
	
	public void setCompileFailList(List compileErrors) {
		this.jsrCompileFailures = compileErrors;
	}
	
	public boolean hasJsrCompileFailure() {
		return hasJsrCompileFailure;
	}
	
	public boolean hasVjoFailure() {
		return hasVjoFailure;
	}
	
	public boolean hasFailures(){
		if(hasJsrCompareFailure || hasVjoFailure || hasJsrCompileFailure){
			return true;
		}
		return false;
	}
	
	public void setJsrFilename(URL jsrFilename) {
		this.jsrFilename = jsrFilename;
	}
	
	public void setVjoFilename(URL vjoFilename) {
		this.vjoFilename = vjoFilename;
	}
	
	public void setJsJstJsr(JstType jst) {
		this.jsJstJsr = jst;
	}
	
	public JstType getJsJstJsr() {
		return jsJstJsr;
	}
	
	public boolean jsrJstsSame(){
		
		return true;
	}

	public String getJsrCompileFailures(){
		String errors = "";
		Iterator itr = jsrCompileFailures.iterator();
		while(itr.hasNext()){
			errors += itr.next() + "\n";
		}
		return errors;
	}
}
