/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.rhino.dbgp;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;

import org.ebayopensource.dsf.active.client.AWindow;
import org.ebayopensource.dsf.active.client.WindowFactory;
import org.ebayopensource.dsf.js.dbgp.DBGPDebugger;
import org.ebayopensource.dsf.json.JsonObject;
import org.ebayopensource.dsf.jsrunner.JsRunner;
import org.ebayopensource.dsf.util.JavaSourceLocator;
import org.ebayopensource.vjo.VjBootstrapJsr;
import org.ebayopensource.vjo.loader.VjoConsole;
import org.ebayopensource.vjo.loader.VjoLoader;
import org.mozilla.mod.javascript.Context;
import org.mozilla.mod.javascript.EcmaError;
import org.mozilla.mod.javascript.Scriptable;

public class DefaultRhinoRunner {

	private static final String DEBUG_MODE = "debug";
	private static final String RUN_MODE = "run";

	private static class DebuggingObjects {
		Context _cntx;
		Scriptable _scope;
	}

	public DefaultRhinoRunner() {
	}

	private void enableVjo(Context cx, Scriptable scope) {

		java.net.URL sourceUrl = null;

		try {

			sourceUrl = JavaSourceLocator.getInstance().getSourceUrl(
					VjBootstrapJsr.getSourceUri(), ".js");
			if (sourceUrl == null)
				sourceUrl = VjBootstrapJsr.getJsAsUrl();

			cx.evaluateReader(scope, new InputStreamReader(sourceUrl
					.openStream()), sourceUrl.toExternalForm(), 0, null);

			// setup loader for loading imported js files
			VjoLoader.enable(cx, scope);

			// enable system console to output
			VjoConsole.enable(cx, scope);

		} catch (Throwable t) {
			// TODO: Log this error
			t.printStackTrace();
		}
	}

	private static JsRunner.ProgramInfo initDebuggingObjects(String[] args,
			DebuggingObjects dObj) {

		JsRunner.ProgramInfo pInfo = null;
		try {
			pInfo = JsRunner.getProgramInfo(args);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		if (pInfo.getBrowserType() != null) {

			dObj._scope = (AWindow) WindowFactory.createWindow(pInfo
					.getBrowserType());
			dObj._cntx = ((AWindow) dObj._scope).getContext();
		} else {

			dObj._cntx = Context.enter();
			dObj._scope = dObj._cntx.initStandardObjects();

		}

		return pInfo;
	}

	public void run(String[] args) {

		JsRunner.ProgramInfo pInfo;

		DebuggingObjects dObjs = new DebuggingObjects();

		if (args[0].equalsIgnoreCase(DEBUG_MODE)) {

			String host = args[1];
			String port = args[2];
			String debuggingId = args[3];

			DBGPDebugger debugger;

			String[] pArgs = new String[args.length - 4];
			System.arraycopy(args, 4, pArgs, 0, pArgs.length);

			pInfo = initDebuggingObjects(pArgs, dObjs);

			try {

				final Socket socket = new Socket(host, Integer.parseInt(port));
				debugger = new DBGPDebugger(socket, pInfo.getFileName()
						.toString(), debuggingId, dObjs._cntx);

				debugger.start();
				dObjs._cntx.setDebugger(debugger, null);

				// synchronized (debugger) {
				// try {
				// debugger.isInited = true;
				//
				// debugger.wait();
				//
				// } catch (InterruptedException e) {
				// throw new IllegalStateException();
				// }
				// }
				try {
					// try {
					// Thread.sleep(200);
					// } catch (InterruptedException ie) {
					// ie.printStackTrace();
					// }

					dObjs._cntx.setGeneratingDebug(true);
					dObjs._cntx.setOptimizationLevel(-1);

					evaluate(dObjs, pInfo.getFileName(), makeRunLine(pInfo));

				} catch (FileNotFoundException fnfe) {
					fnfe.printStackTrace();
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
				debugger.notifyEnd();
			} catch (NumberFormatException nfe) {
				nfe.printStackTrace();
			} catch (UnknownHostException uhe) {
				uhe.printStackTrace();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}

		} else if (args[0].equalsIgnoreCase(RUN_MODE)) {

			String[] pArgs = new String[args.length - 1];
			System.arraycopy(args, 1, pArgs, 0, pArgs.length);

			pInfo = initDebuggingObjects(pArgs, dObjs);

			try {

				evaluate(dObjs, pInfo.getFileName(), makeRunLine(pInfo));

			} catch (FileNotFoundException fnfe) {
				fnfe.printStackTrace();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}

		}

	}

	private String makeRunLine(JsRunner.ProgramInfo pInfo) {
		String main = pInfo.getJsClassName() + ".main";
		String[] args = pInfo.getJsArgs();

		StringBuilder mainExec = new StringBuilder();
		mainExec.append("if(").append(main).append(") {\n").append(main)
				.append("(");
		int i = 0;
		if (args != null) {
			for (String arg : args) {
				if (i > 0) {
					mainExec.append(",");
				}
				mainExec.append(JsonObject.quote(arg));
				i++;
			}
		}
		mainExec.append(");\n}");
		return mainExec.toString();
	}

	private void evaluate(DebuggingObjects dObjs, URL scriptUri, String main)
			throws IOException {

		// load vjo library
		enableVjo(dObjs._cntx, dObjs._scope);

		try {

			dObjs._cntx.evaluateReader(dObjs._scope, new InputStreamReader(
					scriptUri.openStream()), scriptUri.toExternalForm(), 1,
					null);

			if (main != null) {
				dObjs._cntx.evaluateString(dObjs._scope, main, "", 1, null);
			}

		} catch (EcmaError ee) {
			// Parse js source failed.
			// TODO: log this error
			ee.printStackTrace();

		} finally {
			Context.exit();
		}

	}
}