/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.core.validation;

import java.util.List;
import java.util.Set;

import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.IScriptProblem;
import org.ebayopensource.dsf.jst.IScriptUnit;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.mod.compiler.problem.DefaultProblem;

public interface IValidator {

	List<DefaultProblem> validate( IJstType jstType)
			throws CoreException;

	List<DefaultProblem> validate( IScriptUnit jstType)
	throws CoreException;

	Set<IResource> deriveResources(Object object);
	
	List<IScriptProblem> doValidate( IJstType jstType);

	List<IScriptProblem> doValidate( IScriptUnit unit);
	
}
