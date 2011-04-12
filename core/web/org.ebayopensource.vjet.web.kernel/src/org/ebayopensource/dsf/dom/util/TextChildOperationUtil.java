/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dom.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.ebayopensource.dsf.dom.DNode;
import org.ebayopensource.dsf.dom.DText;

/**
* There are many elements that rely on Dtext to make them useful.  For example, 
* Anchor and LI are good examples.  
* 
* We want the ability set/get the “text” value part of such an element without 
* having to create the Dtext node or look it up explicitly.  
* 
* Since this functionality is needed across a number of DhtmlElements, 
* it should be modeled as an internal utility class that is delegated to from 
* the specific DhtmlElement public methods.
* 
* The naming convention is in effect for these and thus we use the “ext” name.
* <pre>
* getHtmlExtTextValue() : String
* setHtmlExtTextValue(String s) : Specific-Element-Type
* </pre>
* During the set operation, if the value of the passed in String is null, we 
* treat this as a remove all Dtext children of this element.
* <pre>
* DLi li = new DLi() ;
* </pre>
* Doing a li.getHtmlExtTextValue() would return null since there are no Dtext 
* nodes in the newly created DLi instance.
* <pre>
* li.setHtmlExtTextValue(“First”) ;
* </pre>
* This has the following effects.  
* <li>If there are no Dtext children, we create one with the passed in text value, 
* parent it to the DLi instance and return it.
* <li>There is exactly 1 Dtext child, we simply update its value with the passed in value.
* <li>There is more than 1 Dtext child, we remove them and do #1.  Note that the 
* Dtext nodes are replaced regardless on being contiguous or not, this is just 
* the semantic we are defining to make things simple/precise.
* 
* We have cases where we need to support:
* <pre>
* DLi li = new DLi() ;
* Dtext t1 = new Dtext(“one”) ;
* Dtext t2 = new Dtext(“two”);
* li.domAppendChild(t1).domAppendChild(t2) ;
* </pre>
* In these cases if we ask for the text “value” of such an arrangement, we should 
* return “onetwo” as a single concatenated value.
* 
* If we do a li.setHtmlExtTextValue(null), it will remove the Dtext nodes from 
* the li instance and li.getHtmlExtTextValue() will return null.  The other 
* consequence of this is that t1 and t2 will now no longer be parented by the 
* li instance.
*/
public final class TextChildOperationUtil {
	public static DText setTextValue(
		final DNode element, final String newTextValue)
	{
		final int childCount = element.getLength() ;

		// If we have no children and newTextValue is null, we're done and
		// simply return null.  If no children and newTextValue is not null, then
		// we create the new DText with the newTextValue, add it to the children
		// and return the new DText instance.		
		if (childCount == 0) {
			if (newTextValue == null) {
				return null ;
			}
			final DText newText = new DText(newTextValue) ;
			element.add(newText) ;
			return newText ;
		}
		
		// At this point we have 1 or more children.  There may be 0 to n of
		// these children that are DText nodes.  Find out how many DText children
		// we have.
		final List<Node> textNodeChildren = new ArrayList<Node>(2) ; // usually 0 or 1
		final NodeList children = element.getChildNodes() ;
		for(int i = 0; i < childCount; i++) {
			Node candidate = children.item(i) ; 
			if (candidate instanceof DText)  {
				textNodeChildren.add(candidate) ;
			}
		}

		if (textNodeChildren.size() == 1) {
			DText onlyTextNode = (DText)textNodeChildren.get(0) ;
			onlyTextNode.setData(newTextValue) ;
			return onlyTextNode ;	
		}
		
		// We may have the condition where we have more than 1 DText node.  At
		// this point we are going to delete all those DText nodes regardless of
		// what the passed in value was.  If the passed in value was null, we're
		// done and return null.  If not, we create a new DText node with the non-null
		// value, add it to the end of the remaining child list and return that
		// new node.
//		element.getDomChildNodes().removeAll(textNodeChildren) ;
		final Iterator<Node> iter = textNodeChildren.iterator();
		while (iter.hasNext()) {
			element.removeChild(iter.next());
		}
		if (newTextValue == null) {
			return null ;
		}			
		final DText newText = new DText(newTextValue) ;
		element.add(newText) ;
		return newText ;		
	}
	
	public static String getTextValue(final DNode element) {
// MrPperf - don't create children unless needed.
		final int childCount = element.getLength() ;
		if (childCount == 0) { // No children, then no data means null
			return null ;
		}
		
		final NodeList children = element.getChildNodes() ;
		
		// At this point we have one or more children and we need to determine if
		// there are any DText nodes.  If none, then we return null.  If 1 we 
		// just return its DOM data value, if more than 1, we concatenate their
		// DOM data values and return that.
		String answer = null ;
		for(int i = 0; i < childCount; i++) {
			Node candidate = children.item(i) ; 
			if (candidate instanceof DText)  {
				DText dtextNode = (DText)candidate ;
				if (answer == null) {
					answer = dtextNode.getData() ;
				}
				else {
					answer += dtextNode.getData() ;
				}	
			}
		}
		
		return answer ;
	}
}
