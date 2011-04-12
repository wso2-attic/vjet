/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/*******************************************************************
 *
 * Licensed Materials - Property of IBM
 * 
 * AJAX Toolkit Framework 6-28-496-8128
 * 
 * (c) Copyright IBM Corp. 2006 All Rights Reserved.
 * 
 * U.S. Government Users Restricted Rights - Use, duplication or 
 * disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 *
 *******************************************************************/
package org.ebayopensource.dsf.jstojava.jslint;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.ebayopensource.dsf.jstojava.report.ErrorReporter;
import org.mozilla.mod.javascript.Context;
import org.mozilla.mod.javascript.Function;
import org.mozilla.mod.javascript.RhinoException;
import org.mozilla.mod.javascript.Script;
import org.mozilla.mod.javascript.Scriptable;
import org.mozilla.mod.javascript.ScriptableObject;

/**
 * Used to locate potentially suspicious language in JavaScript files.
 * This is used as part of the validation process in JavaScript prior
 * to codegen.
 * 
 * 
 *
 */
public class JsLintValidator  {

	private static final String JS_DO_NOT_LINT_VALIDATE = "@JsDoNotLintValidate";

	private Scriptable m_scope, m_options;

	private Function m_lintFunction;

//	private static Logger s_logger = null;
	
	private ErrorReporter m_errorReport = null;

//	private static Logger getLogger() {
//		if (s_logger == null) {
//			s_logger = Logger.getInstance(JsLintValidator.class);
//		}
//		return s_logger;
//	}

	// // hijack logger for bootstrap only until tool uprev is possible
	// static class Logger{
	//		
	// static Logger s_instance;
	// static Logger getInstance(Class c){
	// if(s_instance==null){
	// s_instance = new Logger();
	// }
	//			
	// return s_instance;
	//			
	// }
	//		
	// static void log(int level, String message){
	// if(level == LogLevel.ERROR){
	// System.err.println(message);
	// }else{
	// System.out.println(message);
	// }
	//		
	// }
	//		
	//		
	// }

	
	/**
	 * Runs lint on the existing JavaScript and reports status.
	 */
	
	public JsLintValidator(ErrorReporter errorReport) {
		super();
		m_errorReport = errorReport;
		Context cx = Context.enter();
		Reader reader = null;
		try {
			// TODO: try to create once and cache as much of this as possible

			reader = getJSLintReader();
			Script lintScript = cx.compileReader(reader, "jslint", 0, null);
			m_scope = cx.initStandardObjects(null);
			lintScript.exec(cx, m_scope);
			Object jslint = m_scope.get("jslint", m_scope);
			if (!(jslint instanceof Function)) {
				m_errorReport.error("lint is undefined or not a function.", null, 0, 0);		
				// log
				// this
				// instead
				return; // throw?
			}
			m_lintFunction = (Function) jslint;

			m_options = new ScriptableObject(m_scope, null) {
				
				private static final long serialVersionUID = -8900927704771222704L;

				public String getClassName() {
					return "options";
				};
			};
			m_options.put("laxLineEnd", m_options, Boolean.TRUE);
			m_options.put("debug", m_options, Boolean.TRUE); // allow the
			// "debugger"
			// keyword
			m_options.put("eval", m_options, Boolean.TRUE); // allow use of
			// eval()

		} catch (IOException ioe) {
		} finally {
			Context.exit();
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException ioe) {
				}
			}
		}
	}

	/**
	 * Cleans up resources.
	 *
	 */
	public void cleanup() {

		// TODO cleanup resources?
		m_scope = null;
		m_lintFunction = null;
		m_options = null;
	}

	/**
	 * Currently, this method does nothing.
	 * @param script
	 * @param lineno
	 * @param uri
	 */
	public void validateString(String script, int lineno, String uri) {
		// System.err.println("JSLintValidator: lineno="+lineno+"
		// script="+script);

//		long start = System.currentTimeMillis();
		lint(script, uri, lineno);
//		long end = System.currentTimeMillis();
		// Logger.getInstance(JsLintValidator.class).log(LogLevel.INFO,
		// "Javascript Lint time elapsed=" + (end - start));
//		getLogger().log(LogLevel.INFO,
//				"Javascript Lint time elapsed=" + (end - start));
	}

	/**
	 * Use Rhino to parse the script, flagging errors and warnings along the way
	 * via the ErrorReporter interface
	 * 
	 * @param script script to be parsed
	 * @param uri uri of the script, for annotation only
	 * @param lineno beginning line number reference, for annotation only
	 *            
	 */
	private void lint(String source, String uri, int lineno) {
		
		if(source.indexOf(JS_DO_NOT_LINT_VALIDATE)!=-1){
			m_errorReport.warning("Not validating this file", null, 0, 0);
			return;
		}
		
		if (m_lintFunction != null) {
			Context cx = Context.enter();
			try {
				Object functionArgs[] = { source, m_options };
				Object result = m_lintFunction.call(cx, m_scope, m_scope,
						functionArgs);
				// TODO: need to catch "error" objects and extract message,
				// line, character
				boolean lintFree = Context.toBoolean(result);
				// System.out.println("lint(source) = " + lintFree);

				processNotLintFree(uri, lineno, lintFree);

				// else {
				// // TODO use jslint.report()?
				// // Scriptable report =
				// // (Scriptable)_lintFunction.get("report", _lintFunction);
				// // Object output = ((Function)report).call(cx, _scope,
				// // _scope, functionArgs);
				// // System.out.println("jslint.report() = "+output);
				// }
			} catch (RhinoException rhinoe) {
				String error = "Error running jslint script, file: " + uri
						+ " line: " + rhinoe.lineNumber();
				// System.err.println("\t" + rhinoe.lineSource());
				// getLogger().log(LogLevel.ERROR, rhinoe.details());
				m_errorReport.error(error, null, rhinoe.lineNumber(), 0);
//				CodeGenStatus status = new CodeGenStatus(StatusCode.FatalError,error, rhinoe.lineNumber(), new DsfJsValidationException(error));
//				m_errorList.add(status);
				// throw new ValidationException(new
				// LocalizedMessage(IMessage.NORMAL_SEVERITY, "JSLint internal
				// error: "+rhinoe.details()));
				// } catch(RuntimeException re) {
				// re.printStackTrace();
				// throw re;
			} finally {
				Context.exit();
			}
		}
	}

	private void processNotLintFree(String uri, int lineno, boolean lintFree) {
		if (!lintFree) {
			Scriptable lint = (Scriptable) m_lintFunction.get("errors",
					m_lintFunction);
			Object lengthObject = lint.get("length", lint);
			if (!(lengthObject instanceof Double)) {
				return;
			}
			int length = ((Double) lengthObject).intValue();

			processLintErrors(lineno, lint, length);

//			if (hasError) {
//				throw new DsfRuntimeException(
//						"JS lint reported errors in file: " + uri);
//			}
		}
	}

	private boolean processLintErrors(int lineno, Scriptable lint, int length) {
		boolean xHasError = false;
		for (int i = 0; i < length; i++) {
			Scriptable error = (Scriptable) lint.get(i, lint);
			if (error == null) {
				//System.out.println("JSLint has detected a fatal error."); // TODO:
				// log?
				// marker?
				break;
			}

			int line = ((Double) error.get("line", error)).intValue() + lineno;
//			int character = ((Double) error.get("character", error)).intValue();
			String reason = (String) error.get("reason", error);
			String evidence = (String) error.get("evidence", error);

			// TODO: replace with CodeGenStatus
			//CodeGenStatus status = new CodeGenStatus(StatusCode.Error,"JSLint error: " + reason + "evidence: " + evidence ,line, character);
			m_errorReport.warning("JSLint error: " + reason + "evidence: " + evidence , null, line, 0);
//			CodeGenStatus status = new CodeGenStatus(StatusCode.Warning,"JSLint error: " + reason + "evidence: " + evidence ,line);
//			m_errorList.add(status);
			//System.out.println("LINT line=" + line + " character=" + character
				//	+ " reason=" + reason + " evidence=" + evidence);

			// IMessage messageObject = new
			// LocalizedMessage(IMessage.NORMAL_SEVERITY, reason);
			// messageObject.setLineNo(line);
			// messageObject.setOffset(getLineOffset(line));
			// messageObject.setLength(character);
			// messageObject.setTargetObject(uri);
			// reporter.addMessage(this, messageObject);
			xHasError = true;
		}
		return xHasError;
	}
	
	
	private Reader getJSLintReader() throws IOException {

		InputStream jslint = JsLintValidator.class.getResourceAsStream("jslint.js");
		
		return new InputStreamReader(jslint);
		// should
		// match
		// whatever
		// is
		// used
		// inz
		// jslint.js
	}
}