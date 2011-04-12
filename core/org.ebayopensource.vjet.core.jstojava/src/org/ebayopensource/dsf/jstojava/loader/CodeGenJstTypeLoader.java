/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.loader;

import java.io.File;

import org.ebayopensource.dsf.jsgen.shared.util.CodeGenCleaner;


public class CodeGenJstTypeLoader extends DefaultJstTypeLoader {

	public CodeGenJstTypeLoader() {
		super(FileSuffix.js);
	}

	public CodeGenJstTypeLoader(FileSuffix... acceptedSuffixes) {
		super(acceptedSuffixes);
	}

	@Override
	protected boolean isAccepted(File file) {
		// If this is a CodeGen'd file, then it can't be a source for
		// codegen itself.
		if (isCodeGened(file)) {
			return false;
		}
		return super.isAccepted(file);
	}

	private boolean isCodeGened(File file) {
		return CodeGenCleaner.isCodeGened(file, false);
	}


}
