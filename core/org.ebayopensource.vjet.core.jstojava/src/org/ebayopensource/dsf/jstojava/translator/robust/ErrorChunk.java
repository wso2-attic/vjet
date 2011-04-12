/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.translator.robust;

import java.util.ArrayList;
import java.util.List;

import org.ebayopensource.dsf.jstojava.translator.TranslateHelper;
import org.eclipse.mod.wst.jsdt.core.ast.IProgramElement;

class ErrorChunk {

	private IProgramElement noErrorElement;
	private List<IProgramElement> errorChunk;

	ErrorChunk(IProgramElement noErrorElement) {
		this.noErrorElement = noErrorElement;
		errorChunk = new ArrayList<IProgramElement>();
	}

	void append(IProgramElement error) {
		errorChunk.add(error);
	}

	IProgramElement getNoErrorElement() {
		return noErrorElement;
	}

	List<IProgramElement> getErrorChunk() {
		return errorChunk;
	}

	public String toString() {

		StringBuffer buffer = new StringBuffer();
		if (noErrorElement != null) {
			buffer.append(noErrorElement);
			buffer.append(" . ");
		}

		for (int iter = 0; iter < errorChunk.size(); iter++) {

			buffer.append(TranslateHelper.getStringToken(errorChunk.get(iter)));
			if(iter != errorChunk.size()  - 1){
				buffer.append(" . ");
			}
			
		}

		return buffer.toString();

	}

}
