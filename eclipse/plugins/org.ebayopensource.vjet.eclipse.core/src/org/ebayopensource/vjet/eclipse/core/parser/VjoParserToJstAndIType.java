/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.core.parser;

import java.util.List;

import org.ebayopensource.dsf.jst.IScriptProblem;
import org.ebayopensource.dsf.jst.IScriptUnit;
import org.ebayopensource.dsf.jst.ts.JstTypeSpaceMgr;
import org.ebayopensource.dsf.jstojava.controller.JstParseController;
import org.ebayopensource.dsf.jstojava.parser.VjoParser;
import org.ebayopensource.vjet.eclipse.core.VjetPlugin;
import org.ebayopensource.vjet.eclipse.core.ts.VjoJstTypeLoader;
import org.ebayopensource.vjet.eclipse.core.validation.ValidationEntry;
import org.ebayopensource.vjet.eclipse.core.validation.utils.ProblemUtility;
import org.ebayopensource.vjo.tool.typespace.TypeSpaceMgr;
import org.eclipse.dltk.mod.compiler.problem.DefaultProblem;
import org.eclipse.dltk.mod.compiler.problem.IProblem;
import org.eclipse.dltk.mod.compiler.problem.IProblemReporter;

/**
 * Changed by Jack at 2009.10.14: Because ScriptUnit has handle all the problems
 * from parser. so no need cache them in this class.
 * 
 */
public class VjoParserToJstAndIType {

	private static JstParseController m_controller;
	// private ErrorList m_parse_errors;
	// private ErrorList m_parse_warnings;

	// private JstGroupCache m_cache = JstGroupCache.getInstance();

	private IProblemReporter reporter;
	// if the report in constructor is not null, the validation will be enabled
	// automatically
	private boolean validatable = false;

	private int completionPos;

	public VjoParserToJstAndIType(IProblemReporter reporter) {
		this.reporter = reporter;
		this.validatable = reporter != null;
	}

	public VjoParserToJstAndIType() {
		this(null);
	}

	public IScriptUnit parse(String groupName, String typeName, String source,
			int completionPos) {
		if(VjetPlugin.TRACE_PARSER){
			System.out.println("%%%PARSING: typename:" + typeName + ", group:" + groupName);
		}
		this.completionPos = completionPos;
		// parse
		IScriptUnit unit = innerParse(groupName, typeName, source);

		if (unit != null) {
			//if disable all the validations (syntax and semantic)
			if  (!ValidationEntry.isEnableVjetValidation()) {
				return unit;
			}
			// deal with problems
			List<DefaultProblem> dproblems = null;
			List<IScriptProblem> problems = unit.getProblems();
			if (problems.isEmpty() && validatable && TypeSpaceMgr.getInstance().isLoaded()) {
				dproblems = ValidationEntry.validator(unit);
			} else {
				dproblems = ProblemUtility.reportProblems(problems);
			}
			if (dproblems != null && reporter != null) {
				reportProblems(dproblems, reporter);
			}
			return unit;
		}
		return VjoParser.UNKNOWNUNIT;
	}

	private static void initJstParserController() {
		VjoParser vjoParser = new VjoParser();
		m_controller = new JstParseController(vjoParser);
		m_controller.setRefResolver(new VjoSourceElementResolver(m_controller));
		new JstTypeSpaceMgr(m_controller, new VjoJstTypeLoader());
	}

	public static JstParseController getJstParseController() {
		if (m_controller == null) {
			initJstParserController();
		}
		return m_controller;
	}

	public IScriptUnit parse(String groupName, String typeName, String source) {
		return parse(groupName, typeName, source, -1);
	}

	private IScriptUnit innerParse(String groupName, String typeName,
			String source) {

		JstParseController m_controller = getJstParseController();
		m_controller.setRefResolver(new VjoSourceElementResolver(m_controller));
		m_controller.setJstTSMgr(TypeSpaceMgr.getInstance().getController()
				.getJstTypeSpaceMgr());
		try {

			IScriptUnit unit = m_controller.parseAndResolve(groupName,
					typeName, source);
			return unit;
		} catch (Exception e) {
			e.printStackTrace();
			VjetPlugin.error("problem parsing: groupname=" + groupName + " typename ="+ typeName, e);
		}

		return null;

	}

	private void reportProblems(List<DefaultProblem> problems,
			IProblemReporter reporter) {
		if (problems == null) {
			return;
		}
		for (IProblem problem : problems) {
			reporter.reportProblem(problem);
		}

	}

	/**
	 * Clear open validation feature.
	 */
	public void enableValidation() {
		this.validatable = true;
	}

	/**
	 * Only parse vjo, skip validation
	 */
	public void disableValidation() {
		this.validatable = false;
	}

}
