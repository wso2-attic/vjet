/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.controller;

import java.io.File;

import org.ebayopensource.dsf.common.exceptions.DsfRuntimeException;

public class GenerationConfig {

	
	private boolean m_genJsr = true;
	private boolean m_genNJP = true;
	private File m_outputDir;
	
	public void setOutputDir(File outputDir) {
		if(outputDir!=null && !outputDir.isDirectory()){
			throw new DsfRuntimeException("please input directory this is a file :" +outputDir);
		}
		m_outputDir = outputDir;
	}
	
	public File getOutputDir(){
		return m_outputDir;
	}

	public boolean isGenJsr() {
		return m_genJsr;
	}

	public void setGenJsr(boolean genJsr) {
		m_genJsr = genJsr;
	}

	public boolean isGenNJP() {
		return m_genNJP;
	}

	public void setGenNJP(boolean genNJP) {
		m_genNJP = genNJP;
	}
	
}
