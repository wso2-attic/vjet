/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.core.validation.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.ebayopensource.af.common.error.ErrorList;
import org.ebayopensource.af.common.error.ErrorObject;
import org.ebayopensource.dsf.jst.IScriptProblem;
import org.ebayopensource.dsf.jst.ProblemSeverity;
import org.ebayopensource.dsf.jstojava.report.DefaultErrorReporter;
import org.eclipse.dltk.mod.compiler.problem.DefaultProblem;
import org.eclipse.dltk.mod.compiler.problem.ProblemSeverities;

public class ProblemUtility {

	public static List<DefaultProblem> reportErrors(String location,
			 ErrorList errorList) {
		List<DefaultProblem> probs = new ArrayList<DefaultProblem>();
		if (errorList == null) {
			return probs;
		}

		for (Iterator i = errorList.listIterator(); i.hasNext();) {
			ErrorObject eo = (ErrorObject) i.next();
			probs.add(reportProblem(location, eo,
					ProblemSeverities.Error));
		}
		return probs;
	}

	public static List<DefaultProblem> reportWarnings(String location,
			 ErrorList warnings) {
		List<DefaultProblem> probs = new ArrayList<DefaultProblem>();
		if (warnings == null) {
			return probs;
		}

		for (Iterator i = warnings.listIterator(); i.hasNext();) {
			ErrorObject eo = (ErrorObject) i.next();
			probs.add(reportProblem(location, eo,
					ProblemSeverities.Warning));
		}
		return probs;

	}

	private static DefaultProblem reportProblem(String location,
			ErrorObject errorObject, int problemServerity) {
		int begin = getIntValue(errorObject, DefaultErrorReporter.BEGIN);
		int end = getIntValue(errorObject, DefaultErrorReporter.END);
		int line = getIntValue(errorObject, "line");
		int column = getIntValue(errorObject, "column");
		String message = errorObject.getParameters().getValueByName("message");

		return reportProblem(location, begin, end, line, column, message,
				problemServerity);
	}

	public static DefaultProblem reportProblem(IScriptProblem problem,
			int problemServerity) {
		String message = problem.getMessage();
		if (message == null) {
			message = problem.getID().toString();
		}

		return reportProblem("", problem.getSourceStart(), problem
				.getSourceEnd() + 1, problem.getSourceLineNumber(), problem
				.getColumn(), message, problemServerity);
	}

	public static List<DefaultProblem> reportProblems(
			List<IScriptProblem> problems) {

		List<DefaultProblem> probs = new ArrayList<DefaultProblem>();
		for (IScriptProblem problem : problems) {
			if (!problem.type().equals(ProblemSeverity.ignore)) {
				probs.add(reportProblem(problem, getProblemServerity(problem
						.type())));
			}
		}
		return probs;
	}

	private static int getProblemServerity(ProblemSeverity serverity) {
		switch (serverity) {
		case error:
			return ProblemSeverities.Error;
		case warning:
			return ProblemSeverities.Warning;
		case ignore:
			return ProblemSeverities.Ignore;
		}
		return ProblemSeverities.Error;
	}

	private static int getIntValue(ErrorObject errorObject, String name) {
		String value = errorObject.getParameters().getValueByName(name);
		return value == null ? 0 : Integer.parseInt(value);
	}

	/*
	 * private static void reportProblem(IResource resource, String probType,
	 * int start, int end, int line, int col, String msg, int problemSeverity) {
	 * 
	 * try { IMarker marker = resource
	 * .createMarker(DefaultProblem.MARKER_TYPE_PROBLEM);
	 * 
	 * Map map = new HashMap(6);
	 * 
	 * map.put(IMarker.LINE_NUMBER, line); map.put(IMarker.CHAR_START, start);
	 * map.put(IMarker.CHAR_END, end + 1); map.put(IMarker.SEVERITY,
	 * problemSeverity); map.put(IMarker.MESSAGE, msg);
	 * map.put(IMarker.PRIORITY, IMarker.PRIORITY_HIGH);
	 * map.put(IMarker.LOCATION, line);
	 * 
	 * marker.setAttributes(map);
	 * 
	 *  } catch (CoreException e) { DLTKCore.error(e.toString(), e); } }
	 */

	private static DefaultProblem reportProblem(String location,
			int start, int end, int line, int col, String msg, int problemSeverity) {

		return new DefaultProblem(location, msg, 0, null, problemSeverity,
				start, end, line, col);

	}

}
