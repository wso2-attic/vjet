/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.js;

import org.w3c.dom.NodeList;

import org.ebayopensource.dsf.dom.DText;
import org.ebayopensource.dsf.html.dom.DSpan;
import org.mozilla.mod.javascript.ScriptableObject;

public class JSSpan extends JSElement{

	private DSpan span = null;
	
	public void jsConstructor() {};
	public String getClassName() { return "JSSpan"; }

	public JSSpan() {
	}

	/** Creates new JSDiv */
	public JSSpan(JSWindow window, DSpan span) {
		super(window, span);

		this.span = span;
		
		defineProperty(
				"innerHTML",
				JSSpan.class,
				ScriptableObject.DONTENUM);
	}
	
	public void setText(String text){
		DText textNode = getTextNode();
		if (text == null || text.length() == 0){
			if (textNode == null){
				return;
			}
			else {
				span.removeChild(textNode);
				this.getChildren().remove(textNode);
			}
		}
		else {
			if (textNode == null){
				textNode = new DText();
				span.add(textNode);
				this.getChildren().add(textNode);
			}
			textNode.setTextContent(text);
		}
		getListener().onElementChange(span);
	}
	
	public void setInnerHTML(String text){
		setText(text);
	}
	
	public String getInnerHTML(){
		String text = null;
		DText textNode = getTextNode();
		if (textNode != null){
			text = textNode.getTextContent();
		}
		return text == null ? "" : text;
	}
	
	//
	// Private
	//
	private DText getTextNode(){
		NodeList children = span.getChildNodes();
		if (children.getLength() > 0){
			for (int i=0; i<children.getLength(); i++){
				if (children.item(i) instanceof DText){
					return (DText)children.item(i);
				}
			}
		} 
		return null;
	}
}
