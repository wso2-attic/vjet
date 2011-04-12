/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.control;

import java.io.File;
import java.net.URL;

import org.ebayopensource.dsf.javatojs.translate.policy.ITranslationPolicy;
import org.ebayopensource.dsf.javatojs.translate.policy.WildcardList;
import org.ebayopensource.dsf.jsgen.shared.util.CodeGenCleaner;

public class BuildFileFilter implements IBuildResourceFilter {
	// commented var out as it is being set, but not used by anything.
	// private ITranslationPolicy policy;

	private static WildcardList s_excludeList = new WildcardList();

	private static WildcardList s_includeList = new WildcardList();

	static {
		s_excludeList.addWildcard("*Jsr.java");
		s_includeList.addWildcard("*.java");
	}

	public BuildFileFilter(ITranslationPolicy policy) {
//		this.policy = policy;
	}

	/**
	 * Return true if the file/directory is not in excluded list
	 */
	public boolean accept(URL file) {
		// dont need to check because next method call will anyway check that
		// if (!file.isFile()) {
		// return false;
		// }
		// exclude if this is codegen's java file
		if (CodeGenCleaner.isCodeGened(file, false)) {
			return false;
		}
		String fileName = getFileName(file);
		return !isFileExcluded(fileName);

	}

	/**
	 * Get the file name from the {@link File} object
	 * 
	 * @param file,
	 *            the {@link File}
	 * @return fileName
	 */
	private String getFileName(URL file) {
		String fileName = null;
		try {
			fileName=	file.getPath().substring(file.getPath().lastIndexOf('/')+1);
		} catch (Exception e) {
			System.err.println(e.toString());
		}
		return fileName;
	}
	
	
	/**
	 * Convert the file name with full path into java style and check for the
	 * exclution with the policy and wild card excluded list.
	 * 
	 * @param fileName
	 * @return
	 */

	private boolean isFileExcluded(String fileName) {
		if (!s_includeList.contains(fileName)) {
			return true;
		}
		if (s_excludeList.contains(fileName)) {
			return true;
		}
		// Commented By kpatil@shopping.com for Fixing NPE in test
		// org.ebayopensource.dsf.javatojs.tests.custom.exclude.PolicyExcludeTest.test_1.

		// System.out.println("..................."+fileName);
		// String clzName = JavaToJsHelper.getClassName(fileName);
		// if(policy.isClassExcluded(clzName)){
		// return true;
		// }
		else {
			return false;
		}

	}

}
