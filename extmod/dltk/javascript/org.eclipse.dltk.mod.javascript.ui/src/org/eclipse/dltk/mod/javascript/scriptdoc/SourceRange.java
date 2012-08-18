/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.eclipse.dltk.mod.javascript.scriptdoc;

import org.eclipse.dltk.mod.core.ISourceRange;

public class SourceRange implements ISourceRange {

	int offset;
	int length;
	public SourceRange(int i, int j) {
		this.offset=i;
		this.length=j;
	}

	public int getLength() {
		return length;
	}

	public int getOffset() {
		return offset;
	}

}
