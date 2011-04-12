/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.translator.robust;

import java.util.Stack;

import org.ebayopensource.dsf.jstojava.translator.TranslateHelper;
import org.eclipse.mod.wst.jsdt.core.ast.IProgramElement;

abstract class DefaultErrorCollector implements IErrorCollector{
	
	private Stack<IProgramElement> astElements;
	private ErrorChunk errorChunk;
	
	DefaultErrorCollector(IProgramElement noErrorElement, Stack<IProgramElement> astElements){
		this.astElements = astElements;
		errorChunk = new ErrorChunk(noErrorElement);
	}
	
	public ErrorChunk toErrorChunk(){
		return errorChunk;
	}
	
	public void collect() {
		
		// the top element is error exactly 
		IProgramElement errorElement = astElements.pop();
		errorChunk.append(errorElement);
		
		while(!astElements.empty()){
			
			// check the next token
			errorElement = astElements.peek();

			if(isValidToken(TranslateHelper.getStringToken(errorElement))){
				break;
			}
			else{
				errorChunk.append(astElements.pop());
			}
			
		}
		
	}
	
}
