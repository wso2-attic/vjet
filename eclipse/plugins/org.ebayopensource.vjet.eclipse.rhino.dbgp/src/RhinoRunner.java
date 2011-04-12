/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
import org.ebayopensource.vjet.eclipse.rhino.dbgp.DefaultRhinoRunner;

public class RhinoRunner {

	private static final String DEBUG_MODE = "debug";
	private static final String RUN_MODE = "run";

	public static void main(String[] args) {

		if (args[0].equalsIgnoreCase(DEBUG_MODE)) {

			String host = args[1];
			String port = args[2];
			String debuggingId = args[3];

		}
		String[] pArgs = new String[args.length - 1];
		System.arraycopy(args, 1, pArgs, 0, pArgs.length);

		new DefaultRhinoRunner().run(args);
	}
}
