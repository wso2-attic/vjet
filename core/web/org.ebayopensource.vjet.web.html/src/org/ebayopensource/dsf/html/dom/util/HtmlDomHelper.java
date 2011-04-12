/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.dom.util;

import java.io.IOException;
import java.io.StringReader;

import org.ebayopensource.dsf.css.dom.ICssRuleList;
import org.ebayopensource.dsf.css.dom.ICssStyleRule;
import org.ebayopensource.dsf.css.dom.ICssStyleSheet;
import org.ebayopensource.dsf.css.parser.DCssBuilder;
import org.ebayopensource.dsf.css.parser.DSelectorList;
import org.ebayopensource.dsf.css.parser.selectors.DDescendantSelector;
import org.ebayopensource.dsf.css.parser.selectors.DElementSelector;
import org.ebayopensource.dsf.css.sac.IConditionalSelector;
import org.ebayopensource.dsf.css.sac.InputSource;
import org.ebayopensource.dsf.css.sac.ISelector;
import org.ebayopensource.dsf.css.sac.ISimpleSelector;
import org.ebayopensource.dsf.dom.DText;
import org.ebayopensource.dsf.html.dom.BaseCoreHtmlElement;
import org.ebayopensource.dsf.html.dom.DHtmlDocument;
import org.ebayopensource.dsf.html.dom.DStyle;
import org.ebayopensource.dsf.html.dom.HtmlTypeEnum;

public class HtmlDomHelper {

	public static ICssStyleSheet getStyleSheet(final DHtmlDocument document) {
		final DStyle style =
			(DStyle) document.getElementsByTagName(HtmlTypeEnum.STYLE).item(0);
		if (style == null) {
			return null;
		}
		return getStyleSheet(style);
	}

	public static ICssStyleSheet getStyleSheet(final DStyle style) {
		final DText valueNode = (DText) style.getChildNodes().item(0);
		String styleValue = valueNode.getData();
		
		if (styleValue == null || styleValue.length() == 0) {
			return null;
		}
		
		if (styleValue.startsWith("<!--")) {
			styleValue = styleValue.substring(4);
		}
		
		int index = styleValue.indexOf("//-->");
		if (index > 0) {
			styleValue = styleValue.substring(0, index);
		}

		final InputSource is = new InputSource(new StringReader(styleValue));
		final DCssBuilder parser = new DCssBuilder();
		ICssStyleSheet ss = null;
		try {
			ss = parser.parseStyleSheet(is);
		} 
		catch (IOException e) {
			// TODO?
		}
		return ss;
	}

	public static void setStyleSheet(
		final ICssStyleSheet styleSheet,
		final DHtmlDocument document)
	{
		final DStyle style =
			(DStyle) document.getElementsByTagName(HtmlTypeEnum.STYLE).item(0);
		if (style == null) {
			return;
		}
		
		final DText valueNode = (DText) style.getChildNodes().item(0);
		final String updatedStyleValue = "<!--\n" + styleSheet.toString() + "//-->";
		valueNode.setData(updatedStyleValue);
	}
	
	public static DStyle createDStyle(final ICssStyleSheet styleSheet) {
		final DStyle style = new DStyle();
		style.add("<!--\n" + styleSheet.toString() + "//-->");
		return style;
	}
	
	public static void addConstraintToCssSelectors(
		final BaseCoreHtmlElement element, final ICssStyleSheet styleSheet)
	{		
		final String id = element.getHtmlId();
		if (id != null && id.length() > 0) {
			addConstraint(element.getTagName(),
				(element.getTagName()+ "#" + id).toLowerCase(), styleSheet, id);
		}
//		else {
//			String className = element.getHtmlClassName();
//			if (className != null) {
//				addConstraint(element.getDomTagName(),
//					(element.getDomTagName() + "." + className).toLowerCase(),
//					styleSheet, null);
//			}
//		}
	}
	
	
	public static void addConstraint(
		final String tagName, 
		final String selectStr, 
		final ICssStyleSheet styleSheet, 
		final String id) 
	{
		final ICssRuleList rules = styleSheet.getCssRules();
		for (int i = 0; i < rules.getLength(); i++) {
			ICssStyleRule rule = (ICssStyleRule)rules.item(i);
			DSelectorList selectors = (DSelectorList)rule.getSelectors();
			for (int j = 0; j < selectors.getLength(); j++) {
				ISelector selector = selectors.item(j);
				if (id != null && selector.toString().startsWith("*#" + id)) {
					continue;
				}

				//if it is DescendantSelector, find most top Ancestor
				DDescendantSelector descendantSelector = null;
				while (selector instanceof DDescendantSelector) {
					descendantSelector = (DDescendantSelector)selector;
					selector = descendantSelector.getAncestorSelector();
				}

				if (selector instanceof DElementSelector) {
					DElementSelector elemSelector = 
						(DElementSelector)selector;
					String localName = elemSelector.getLocalName();
					if (localName == null) {
						continue;
					}
					if (localName.equalsIgnoreCase(tagName)) {
						elemSelector.setLocalName(selectStr);
						continue;
					}
				}				
				
				if (selector instanceof IConditionalSelector) {
					if (selector.toString().toLowerCase().startsWith(selectStr)){
						continue;
					}
				}
				
				if (selector instanceof ISimpleSelector) {
					DDescendantSelector newSelector = 
						new DDescendantSelector(
							new DElementSelector(selectStr), 
								(ISimpleSelector)selector);
					if (descendantSelector != null) {
						descendantSelector.setAncestorSelector(newSelector);
					}
					else {
						selectors.set(j, newSelector);
					}
				}
			}
		}
	}
}
