/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.eclipse.dltk.mod.javascript.scriptdoc;

import java.io.Reader;

import org.eclipse.dltk.mod.core.IMember;
import org.eclipse.dltk.mod.core.ModelException;
import org.eclipse.dltk.mod.internal.javascript.reference.resolvers.IResolvableMember;
import org.eclipse.dltk.mod.ui.documentation.IScriptDocumentationProvider;

public class ScriptDocumentationProvider implements
		IScriptDocumentationProvider {

	public Reader getInfo(IMember element, boolean lookIntoParents,
			boolean lookIntoExternal) {
		if (element instanceof IResolvableMember) {
			IResolvableMember mn = (IResolvableMember) element;
			return mn.getInfo(lookIntoParents, lookIntoExternal);
		} else {
			try {
				return ScriptdocContentAccess.getHTMLContentReader(element,
						lookIntoParents, lookIntoExternal);
			} catch (ModelException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
	}

	public Reader getInfo(String content) {
		return null;
	}

	public void clear() {
		// TODO Auto-generated method stub

	}

}
