/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.translate.policy;

public class TranslationPolicy implements ITranslationPolicy {	
	
	private ExclusionPolicyModel m_exclusionPolicy = new ExclusionPolicyModel();
	
	//
	// Satisfy ITranslationPolicy
	//
	public boolean isClassExcluded(Class<?> className){	
		if(className == null) {
			return false;
		}
		if(m_exclusionPolicy.isClassExcluded(className.getName())){
			return true;
		} 
		return false;
	}
	
	public boolean isClassExcluded(String classNameString){		
		if(m_exclusionPolicy.isClassExcluded(classNameString)){
			return true;
		} 
		return false;
	}
	
	//
	// API
	//
	/**
	 * Add {@link Pkg} to excluded list
	 * @param pkg
	 */
	public void addExcludePackage(Pkg pkg){
		m_exclusionPolicy.add(pkg);
	}
	
	/**
	 * Add the package name to excluded list
	 * @param pkgString
	 */
	public void addExcludePackage(String pkgString){
		Pkg pkg = new Pkg(pkgString);
		addExcludePackage(pkg);
	}
	
	/**
	 * Add the class name to excluded list
	 * @param fullyQualifiedClassName
	 * TODO:  validate the class name?
	 * ex., org.ebayopensource.dsf.javatojs.translate.policy.TranslationPolicy
	 */
	public void addExcludeClass(String fullyQualifiedClassName){
		m_exclusionPolicy.add(fullyQualifiedClassName);
	}
	
	/**
	 * Add the class to the excluded class 
	 * @param classValue
	 */
	public void addExcludeClass(Class<?> classValue){
		m_exclusionPolicy.add(classValue.getName());
	}	
}
