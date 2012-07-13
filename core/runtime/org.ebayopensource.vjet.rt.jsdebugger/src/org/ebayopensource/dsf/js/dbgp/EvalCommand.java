/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.js.dbgp;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Map;

import org.ebayopensource.dsf.util.JavaSourceLocator;
import org.mozilla.mod.javascript.Function;
import org.mozilla.mod.javascript.NativeArray;
import org.mozilla.mod.javascript.NativeFunction;
import org.mozilla.mod.javascript.NativeJavaArray;
import org.mozilla.mod.javascript.NativeJavaClass;
import org.mozilla.mod.javascript.NativeJavaObject;
import org.mozilla.mod.javascript.NativeObject;
import org.ebayopensource.dsf.common.resource.ResourceUtil;

final class EvalCommand extends DBGPDebugger.Command {

	private final DBGPDebugger m_debugger;
	public static final String DETAIL_EVAL_TYPE = "com.ebay.vjo.runner.DescriptUtils";
	public static final String DETAIL_EVAL_FUN = "com.ebay.vjo.runner.DescriptUtils.detail";

	EvalCommand(DBGPDebugger debugger) {
		m_debugger = debugger;
	}

	void parseAndExecute(String command, Map<String, String> options) {
		String value = Base64Helper.decodeString(options.get("--"));
		if (value.length() == 0) {
			value = "this";
		}
		StringBuffer valueBuffer = new StringBuffer();
		int depth = 1;
		if (options.containsKey("-d")) {
			depth = Integer.parseInt(options.get("-d"));
		}
		if (m_debugger.m_stackmanager.getStackDepth() == 0 || value == null) {
			// modify by patrick
			m_debugger.printProperty("", "", "", valueBuffer, 0, true);
			// end modify
			m_debugger.printResponse("<response command=\"eval\"\r\n"
					+ " transaction_id=\"" + options.get("-i") + "\" depth=\""
					+ depth + "\" success=\"1\" " + ">\r\n" + valueBuffer
					+ "</response>\r\n" + "");
			return;
		}
		DBGPDebugFrame fr = m_debugger.m_stackmanager.getStackFrame(depth);
		Object evaluated = fr.eval(value);
		// Execute js method "props" to get detail info
		// "props" method comes from DSFVjoDef\src\com\ebay\vjo\VjBootstrap_3.js
		boolean needReevaluate = false;
		
		if ((evaluated instanceof Function)
				|| (evaluated instanceof NativeObject)
				|| (evaluated instanceof NativeArray)
				|| (evaluated instanceof NativeFunction)
				|| (evaluated instanceof NativeJavaClass)
				|| (evaluated instanceof NativeJavaClass)
				|| (evaluated instanceof NativeJavaArray)) {
			needReevaluate = true;
		} else if (evaluated instanceof NativeJavaObject) {
			if (((NativeJavaObject)evaluated).unwrap() instanceof String ){
				evaluated = ((NativeJavaObject)evaluated).getDefaultValue(String.class);
			} else {
				needReevaluate = true;
			}
			
		}
		
		if (needReevaluate) {
			if (existDescripUtils(fr)) {
				Object tempEvaluated = fr.eval(DETAIL_EVAL_FUN + "(" + value
						+ ")");
				if (!"".equals(tempEvaluated)) {
					evaluated = tempEvaluated;
				}
			}
		}
			
//		String shName = value;
//		int k = shName.lastIndexOf('.');
//		if (k != -1) {
//			shName = shName.substring(k + 1);
//		}
		// modify by patrick
		m_debugger.printProperty("", "", evaluated, valueBuffer, 0, true);
		// end modify
		m_debugger.printResponse("<response command=\"eval\"\r\n"
				+ " transaction_id=\"" + options.get("-i") + "\" depth=\""
				+ depth + "\" success=\"1\" " + ">\r\n" + valueBuffer
				+ "</response>\r\n" + "");
	}

	private boolean existDescripUtils(DBGPDebugFrame fr) {
		Object desc = fr.eval(DETAIL_EVAL_FUN);
		if (desc instanceof Function) {
			return true;
		} else {
			return loadDescriptUtil(fr);
		}
	}

	private boolean loadDescriptUtil(DBGPDebugFrame fr) {
		URL sourceUrl = null;
		try {
			// load from source if available
			sourceUrl = JavaSourceLocator.getInstance().getSourceUrl(
					DETAIL_EVAL_TYPE, ".js");
		} catch (Throwable e) {
			 e.printStackTrace();
		}
		if (sourceUrl == null) {
			String path = DETAIL_EVAL_TYPE.replace(".", "/") + ".js";
			int index = path.lastIndexOf("/");
			String resourceName = path;
			String relDir = "";
			if (index > 0) {
				relDir = path.substring(0, index);
				resourceName = path.substring(index + 1);
			}
			try {
				sourceUrl = ResourceUtil.getResource(relDir, resourceName);
			} catch (IOException e) {
				// Do nothing
				// e.printStackTrace();
			}
		}

		if (sourceUrl != null) {
			try {
				fr.eval(new InputStreamReader(sourceUrl.openStream(), "UTF-8"));
				return true;
			} catch (Exception e) {
				return false;
			}
		} else {
			return false;
		}

	}
}