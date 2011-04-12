/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.js;

import org.mozilla.mod.javascript.Context;
import org.mozilla.mod.javascript.Scriptable;

public class JSScreen { //extends ScriptableObject {

	private JSWindow window = null;
	private Context cx = null;
	private Scriptable scope = null;

	public JSScreen(JSWindow window) {
		this.window = window;
		this.cx = window.getContext();
		this.scope = window.getScope();
	}

	// Property: availHeight, R/O -------------------------------------------------
	private int availHeight = 600;
	public int getAvailHeight() {
		return (availHeight);
	}

	// Property: availLeft, R/O -------------------------------------------------
	private int availLeft = 0;
	public int getAvailLeft() {
		return (availLeft);
	}

	// Property: availTop, R/O -------------------------------------------------
	private int availTop = 0;
	public int getAvailTop() {
		return (availTop);
	}

	// Property: availWidth, R/O -------------------------------------------------
	private int availWidth = 800;
	public int getAvailWidth() {
		return (availWidth);
	}

	// Property: colorDepth, R/O -------------------------------------------------
	private int colorDepth = 8;
	public int getColorDepth() {
		return (colorDepth);
	}

	// Property: height, R/O -------------------------------------------------
	private int height = 600;
	public int getHeight() {
		return (height);
	}

	// Property: pixelDepth, R/O -------------------------------------------------
	private int pixelDepth = 8;
	public int getPixelDepth() {
		return (pixelDepth);
	}

	// Property: width, R/O -------------------------------------------------
	private int width = 800;
	public int getWidth() {
		return (width);
	}

}
