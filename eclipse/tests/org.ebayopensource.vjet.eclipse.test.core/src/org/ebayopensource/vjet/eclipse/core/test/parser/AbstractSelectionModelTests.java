/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.core.test.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import org.eclipse.dltk.mod.compiler.env.ISourceModule;
import org.eclipse.dltk.mod.core.IModelElement;
import org.eclipse.dltk.mod.core.ModelException;

import org.ebayopensource.vjet.eclipse.core.IJSSourceModule;
import org.ebayopensource.vjet.eclipse.core.test.contentassist.TestConstants;
import org.ebayopensource.vjet.eclipse.internal.codeassist.select.VjoSelectionEngine;

public abstract class AbstractSelectionModelTests extends AbstractVjoModelTests {

	protected void basicTest(IJSSourceModule module, int position,
			String[] compNames, String category) throws ModelException {
		assertNotSame("Invalid file content, cant find position", -1, position);
		ArrayList<IModelElement> results = new ArrayList<IModelElement>();
		VjoSelectionEngine c = new VjoSelectionEngine();
		IModelElement[] arr = c.select((ISourceModule) module, position,
				position);
		assertNotNull("Empty result", arr);

		results.addAll(Arrays.asList(arr));
		compareResults(results, compNames);
	}

	protected void compareResults(ArrayList<IModelElement> results,
			String[] names) {
		assertEquals(names.length, results.size());
		Collections.sort(results, new Comparator() {

			public int compare(Object arg0, Object arg1) {
				IModelElement pr = (IModelElement) arg0;
				IModelElement pr1 = (IModelElement) arg1;
				return new String(pr.getElementName()).compareTo(new String(pr1
						.getElementName()));
			}

		});

		if (names.length > 1) {
			Arrays.sort(names, 0, names.length);
		}

		IModelElement pr;

		for (int i = 0; i < results.size(); i++) {
			pr = results.get(i);
			assertEquals(names[i], new String(pr.getElementName()));
		}
	}

	protected int firstPositionInFile(String string, IJSSourceModule module)
			throws ModelException {
		String content = module.getSource();

		int position = content.indexOf(string);

		if (position >= 0) {
			return position;
		}
		return -1;
	}

	protected int lastPositionInFile(String string, IJSSourceModule module)
			throws ModelException {
		String content = module.getSource();

		if (string == null)
			return content.length();

		int position = content.lastIndexOf(string);

		if (position >= 0) {
			return position;
		}

		return -1;
	}

	protected String getProjectName() {
		return TestConstants.PROJECT_NAME_VJETPROJECT;
	}

	protected String getProjectNameVjo() {
		return TestConstants.PROJECT_NAME_VJOJAVALIB;
	}
}
