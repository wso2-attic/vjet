/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.translate;

public final class TranslationMode {
	
	private static final int DEPENDENCY = 1;	
	private static final int DECLARATION = 2;
	private static final int IMPLEMENTATION = 4;
	
	private int m_mode = 0;
	
	public TranslationMode setDependency(){
		m_mode = DEPENDENCY;
		return this;
	}
	
	public TranslationMode addDependency(){
		m_mode |= DEPENDENCY;
		return this;
	}
	
	public TranslationMode setDeclaration(){
		m_mode = DECLARATION;
		return this;
	}
	
	public TranslationMode addDeclaration(){
		m_mode |= DECLARATION;
		return this;
	}
	
	public TranslationMode setImplementation(){
		m_mode = IMPLEMENTATION;
		return this;
	}
	
	public TranslationMode addImplementation(){
		m_mode |= IMPLEMENTATION;
		return this;
	}
	
	public boolean hasDependency(){
		return (m_mode & DEPENDENCY) == DEPENDENCY;
	}
	
	public boolean hasDeclaration(){
		return (m_mode & DECLARATION) == DECLARATION;
	}
	
	public boolean hasImplementation(){
		return (m_mode & IMPLEMENTATION) == IMPLEMENTATION;
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		boolean isEmpty = true;
		if (hasDependency()){
			sb.append("Dependency");
			isEmpty = false;
		}
		if (hasDeclaration()){
			if (!isEmpty){
				sb.append(",");
			}
			sb.append("Declaration");
		}
		if (hasImplementation()){
			if (!isEmpty){
				sb.append(",");
			}
			sb.append("Impl");
		}
		
		return sb.toString();
	}
	
	void setMode(int mode){
		m_mode = mode;
	}
	
	int getMode(){
		return m_mode;
	}
}
