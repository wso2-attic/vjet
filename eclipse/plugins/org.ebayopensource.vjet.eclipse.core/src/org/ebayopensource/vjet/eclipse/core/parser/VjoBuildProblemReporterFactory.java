/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.core.parser;

import org.eclipse.core.resources.IResource;
import org.eclipse.dltk.mod.core.IBuildProblemReporterFactory;
import org.eclipse.dltk.mod.internal.core.builder.BuildProblemReporter;

public class VjoBuildProblemReporterFactory implements
		IBuildProblemReporterFactory {

	// private class VjoBuildProblemReporter extends BuildProblemReporter{
	//		
	// private VjoBuildProblemReporter(IResource resource){
	//			
	// super(resource);
	// problems.
	//			
	// }
	//		
	// }

	public BuildProblemReporter createReporter(IResource resource) {
		return new BuildProblemReporter(resource); 
		//{
		//	public void flush() {
		//	}
		//};
	}

}
