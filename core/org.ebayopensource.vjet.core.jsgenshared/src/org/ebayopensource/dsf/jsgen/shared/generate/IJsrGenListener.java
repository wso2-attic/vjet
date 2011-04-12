/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsgen.shared.generate;

import java.io.PrintWriter;
import java.util.Collection;

import org.ebayopensource.dsf.jst.IJstType;

/**
 * Interface for listeners that will be notified before and after 
 * individual sections of JSR are generated.
 * 
 * Please Note:
 * 1. Implementation of IJsrGenListener must be registered into JstGenerator before codegen starts
 * 2. initialize(IJstType) is the place to do any cleanup of previous codegen or initialization for the given jstType
 * 3. Not suitable for nested JSR codegen (no such need/usecase identified so far).
 * 
 * 
 */
public interface IJsrGenListener {
	
	void initialize(IJstType jstType);
	
	void preImports(PrintWriter writer, CodeStyle style);
	void postImports(Collection<String> importedTypeNames, PrintWriter writer, CodeStyle style);
	
	void preInterfaces(PrintWriter writer, CodeStyle style);
	void postInterfaces(PrintWriter writer, CodeStyle style);

	void preConstructors(PrintWriter writer, CodeStyle style);
	void postConstructors(PrintWriter writer, CodeStyle style);
}
