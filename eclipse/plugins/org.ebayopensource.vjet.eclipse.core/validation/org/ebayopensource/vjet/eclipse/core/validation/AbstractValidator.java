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

import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.IScriptProblem;
import org.ebayopensource.dsf.jst.IScriptUnit;
import org.ebayopensource.vjet.eclipse.core.validation.utils.ProblemUtility;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.mod.compiler.problem.DefaultProblem;

public abstract class AbstractValidator implements IValidator {

	private String validatorId;


	protected String getValidatorId() {
		return validatorId;
	}

	public void setValidatorId(String validatorId) {
		this.validatorId = validatorId;
	}

	public final List<DefaultProblem> validate(IJstType jstType)
			throws CoreException {

		List<IScriptProblem> problems = doValidate(jstType);
		return ProblemUtility.reportProblems(problems);

	}

	public final List<DefaultProblem> validate(IScriptUnit unit)
			throws CoreException {

		List<IScriptProblem> problems = doValidate(unit);
		return ProblemUtility.reportProblems(problems);

	}

}
