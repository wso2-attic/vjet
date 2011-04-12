/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.active.client;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

import org.ebayopensource.dsf.active.util.DapDebugConsole;
import org.ebayopensource.dsf.common.exceptions.DsfRuntimeException;
import org.ebayopensource.dsf.html.js.ActiveJsExecutionControlCtx;
import org.mozilla.mod.javascript.Context;
import org.mozilla.mod.javascript.JavaScriptException;
import org.mozilla.mod.javascript.Scriptable;

public class ScriptExecutor {
	
	public static Object executeScript(String script, AWindow window)
		throws JavaScriptException {
		synchronized (window.getJsExcutionLock()) {
			return executeScript(script, window.getScope(), window.getContext(), null);
		}		
	}
	
	public static Object executeScript(String script, String srcId, AWindow window)
		throws JavaScriptException {
		synchronized (window.getJsExcutionLock()) {
			return executeScript(script, window.getScope(), window.getContext(), srcId);
		}
	}
	
	public static Object executeScript(String script, Scriptable scope, Context context)
		throws JavaScriptException {
		return executeScript(script, scope, context, null);
	}
		
	public static Object executeScript(String script, Scriptable scope, Context context, String srcId)
		throws JavaScriptException {
		
		DapDebugConsole.enable(context, scope);

		String s = script.trim();
		Object ret = null;
		if (ActiveJsExecutionControlCtx.ctx().needDebug()) {
			Map<String, String> fragments = getSourceFragments(s, srcId);
			for (Map.Entry<String, String> entry : fragments.entrySet()) {
				ret = context.evaluateString(scope, entry.getValue(), entry.getKey(), 1, null);
			}
		}
		else {
			String id = (srcId == null) ? createKey(s) : srcId;
			ret = context.evaluateString(scope, s, id, 1, null);
		}

		return ret;
	}
	
	private static Map<String, String> getSourceFragments(String data, String srcId) {
		Map<String, String> fragments = new LinkedHashMap<String, String>();
		int currentIndex = 0;
		int tokenIndex = data.indexOf(ActiveJsExecutionControlCtx.ACTIVE_JS_SRC);
		if (tokenIndex == -1) {
			String id = (srcId == null) ? createKey(data) : srcId;
			fragments.put(id, data);
		}
		else {
			while (tokenIndex != -1) {
				if (tokenIndex > currentIndex) {
					String fragment = data.substring(currentIndex, tokenIndex).trim();
					if (fragment.length() > 0) {
						fragments.put(createKey(fragment), fragment);
					}
				}
				currentIndex = processTokenValue(data, tokenIndex, fragments);
				tokenIndex = -1;
				if (currentIndex < data.length()) {
					tokenIndex = data.indexOf(ActiveJsExecutionControlCtx.ACTIVE_JS_SRC, currentIndex);
				}
			}
			if (currentIndex < data.length()) {
				String fragment = data.substring(currentIndex).trim();
				fragments.put(createKey(fragment), fragment);
			}
		}
		return fragments;
	}
	
	private static String createKey(String data) {
		String id = data;
		if (data.length() > 50) {
			id = data.substring(0, 50);
		}
		return "[" + id + " ...]_" + data.length() + "_" + data.hashCode();
	}
	
	private static int processTokenValue(String data, int tokenIndex, Map<String, String> fragments) {
		int start = tokenIndex + ActiveJsExecutionControlCtx.ACTIVE_JS_SRC.length() + 1;
		int end = data.indexOf("]", start);
		String url = data.substring(start, end);
		String fileName = url;
		if (url.startsWith("file:")) {
			//tmp logic to get source JS under /src instead of the copy under /bin
			try {
				int index = url.indexOf("/bin/");
				String srcUrl = url;
				if (index > 0) {
					srcUrl = url.substring(0, index) + "/src/" + url.substring(index + 5);
				}
				File file = new File(new URL(srcUrl).getFile());
				if (file.exists()) {
					fileName = file.toURL().toExternalForm();
					url = srcUrl;
				}
			} catch (MalformedURLException e) {
				throw new DsfRuntimeException(url, e);
			}
		}
		String text = getJsContent(url);
		fragments.put(fileName, text);
		end++;
		return end;
	}
	
	public static String getJsContent(final String url) {
		final InputStream inputStream;
		try {
			inputStream = new URL(url).openStream();
		} catch (IOException e) {
			throw new DsfRuntimeException(url, e);
		}
		final ByteArrayOutputStream os = new ByteArrayOutputStream(4096);
		final byte [] buffer = new byte[4096];
		try {
			try {
				do {
					final int numBytesXferred = inputStream.read(buffer);
					if (numBytesXferred == -1 ) {
						break ; // EOF
					}
					os.write(buffer, 0, numBytesXferred);
				} while (true);
			} finally {
				inputStream.close();
			}
			final String scriptText = os.toString("utf-8");
			return scriptText;
		} catch (IOException e) {
			throw new DsfRuntimeException("can not load '" + url + "'", e);
		}
	}

}