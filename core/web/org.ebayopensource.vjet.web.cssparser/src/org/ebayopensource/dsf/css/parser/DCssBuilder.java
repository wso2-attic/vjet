/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.css.parser;

import java.io.IOException;
import java.io.StringReader;
import java.util.Properties;
import java.util.Stack;

import org.ebayopensource.dsf.css.dom.ICssRule;
import org.ebayopensource.dsf.css.dom.ICssStyleDeclaration;
import org.ebayopensource.dsf.css.dom.ICssStyleSheet;
import org.ebayopensource.dsf.css.dom.impl.DCssCharsetRule;
import org.ebayopensource.dsf.css.dom.impl.DCssFontFaceRule;
import org.ebayopensource.dsf.css.dom.impl.DCssImportRule;
import org.ebayopensource.dsf.css.dom.impl.DCssMediaList;
import org.ebayopensource.dsf.css.dom.impl.DCssMediaRule;
import org.ebayopensource.dsf.css.dom.impl.DCssPageRule;
import org.ebayopensource.dsf.css.dom.impl.DCssProperty;
import org.ebayopensource.dsf.css.dom.impl.DCssRule;
import org.ebayopensource.dsf.css.dom.impl.DCssRuleList;
import org.ebayopensource.dsf.css.dom.impl.DCssStyleDeclaration;
import org.ebayopensource.dsf.css.dom.impl.DCssStyleRule;
import org.ebayopensource.dsf.css.dom.impl.DCssStyleSheet;
import org.ebayopensource.dsf.css.dom.impl.DCssUnknownRule;
import org.ebayopensource.dsf.css.dom.impl.DCssValue;
import org.ebayopensource.dsf.css.sac.CssException;
import org.ebayopensource.dsf.css.sac.IDocumentHandler;
import org.ebayopensource.dsf.css.sac.ILexicalUnit;
import org.ebayopensource.dsf.css.sac.IParser;
import org.ebayopensource.dsf.css.sac.ISacMediaList;
import org.ebayopensource.dsf.css.sac.ISelectorList;
import org.ebayopensource.dsf.css.sac.InputSource;
import org.ebayopensource.dsf.css.sac.helpers.DParserFactory;

public class DCssBuilder {

	private static final String PARSER = SacParser.class.getName();

	private IParser m_parser = null;
	private ICssStyleSheet m_parentStyleSheet = null;
//	private DCssRule m_parentRule = null;

	/** Creates new CSSOMParser */
	public DCssBuilder() {
		try {
			setProperty("org.w3c.css.sac.parser", PARSER);
			DParserFactory factory = new DParserFactory();
			m_parser = factory.makeParser();
		} catch (Exception e) {
		}
	}
	
	public ICssStyleSheet parseStyleSheet(String cssText)
		throws IOException {
		InputSource is = new InputSource(new StringReader(cssText));
		return parseStyleSheet(is);
	}

	public ICssStyleSheet parseStyleSheet(InputSource source)
		throws IOException {
		CSSOMHandler handler = new CSSOMHandler();
		m_parser.setDocumentHandler(handler);
		m_parser.parseStyleSheet(source);
		return (DCssStyleSheet) handler.getRoot();
	}

	public ICssStyleDeclaration parseStyleDeclaration(InputSource source)
		throws IOException {
		DCssStyleDeclaration sd = new DCssStyleDeclaration(null);
		parseStyleDeclaration(sd, source);
		return sd;
	}

	public void parseStyleDeclaration(
		ICssStyleDeclaration sd,
		InputSource source)
		throws IOException {
		Stack<Object> nodeStack = new Stack<Object>();
		nodeStack.push(sd);
		CSSOMHandler handler = new CSSOMHandler(nodeStack);
		m_parser.setDocumentHandler(handler);
		m_parser.parseStyleDeclaration(source);
	}

	public DCssValue parsePropertyValue(InputSource source) throws IOException {
		CSSOMHandler handler = new CSSOMHandler();
		m_parser.setDocumentHandler(handler);
		return new DCssValue(m_parser.parsePropertyValue(source));
	}

	public ICssRule parseRule(InputSource source) throws IOException {
		CSSOMHandler handler = new CSSOMHandler();
		m_parser.setDocumentHandler(handler);
		m_parser.parseRule(source);
		return (DCssRule) handler.getRoot();
	}

	public ISelectorList parseSelectors(InputSource source) throws IOException {
		HandlerBase handler = new HandlerBase();
		m_parser.setDocumentHandler(handler);
		return m_parser.parseSelectors(source);
	}

	public void setParentStyleSheet(ICssStyleSheet parentStyleSheet) {
		m_parentStyleSheet = parentStyleSheet;
	}
	
	public ICssStyleSheet getParentStyleSheet() {
		return m_parentStyleSheet;
	}

	public void setParentRule(ICssRule parentRule) {
//		m_parentRule = parentRule;
	}

	class CSSOMHandler implements IDocumentHandler {

		private Stack<Object> m_nodeStack;
		private Object m_root = null;

		public CSSOMHandler(Stack<Object> nodeStack) {
			m_nodeStack = nodeStack;
		}

		public CSSOMHandler() {
			m_nodeStack = new Stack<Object>();
		}

		public Object getRoot() {
			return m_root;
		}

		public void startDocument(InputSource source) throws CssException {
			if (m_nodeStack.empty()) {
				DCssStyleSheet ss = new DCssStyleSheet();
				m_parentStyleSheet = ss;

				DCssRuleList rules = (DCssRuleList)ss.getCssRules();
				m_nodeStack.push(ss);
				m_nodeStack.push(rules);
			} else {
				// Error
			}
		}

		public void endDocument(InputSource source) throws CssException {

			// Pop the rule list and style sheet nodes
			m_nodeStack.pop();
			m_root = m_nodeStack.pop();
		}

		public void comment(String text) throws CssException {
		}

		public void ignorableAtRule(String atRule) throws CssException {

			// Create the unknown rule and add it to the rule list
			DCssUnknownRule ir =
				new DCssUnknownRule(m_parentStyleSheet, null, atRule);
			if (!m_nodeStack.empty()) {
				((DCssRuleList) m_nodeStack.peek()).add(ir);
			} else {
				m_root = ir;
			}
		}

		public void namespaceDeclaration(String prefix, String uri)
			throws CssException {
		}

		public void importStyle(
			String uri,
			ISacMediaList media,
			String defaultNamespaceURI)
			throws CssException {

			// Create the import rule and add it to the rule list
			DCssImportRule ir =
				new DCssImportRule(
					m_parentStyleSheet,
					null,
					uri,
					new DCssMediaList(media));
			if (!m_nodeStack.empty()) {
				((DCssRuleList) m_nodeStack.peek()).add(ir);
			} else {
				m_root = ir;
			}
		}

		public void startMedia(ISacMediaList media) throws CssException {

			// Create the media rule and add it to the rule list
			DCssMediaRule mr =
				new DCssMediaRule(
					m_parentStyleSheet,
					null,
					new DCssMediaList(media));
			if (!m_nodeStack.empty()) {
				((DCssRuleList) m_nodeStack.peek()).add(mr);
			}

			// Create the rule list
			DCssRuleList rules = new DCssRuleList();
			mr.setRuleList(rules);
			m_nodeStack.push(mr);
			m_nodeStack.push(rules);
		}

		public void endMedia(ISacMediaList media) throws CssException {

			// Pop the rule list and media rule nodes
			m_nodeStack.pop();
			m_root = m_nodeStack.pop();
		}

		public void startCharset(String name) throws CssException {
			// Create the page rule and add it to the rule list
			DCssCharsetRule rule = new DCssCharsetRule(m_parentStyleSheet,null,name);
			if (!m_nodeStack.empty()) {
				((DCssRuleList) m_nodeStack.peek()).add(rule);
			}
	
			// Create the style declaration
			DCssStyleDeclaration decl = new DCssStyleDeclaration(rule);
//			rule.setStyle(decl);
			m_nodeStack.push(rule);
			m_nodeStack.push(decl);
		}
	
		public void endCharset(String name) throws CssException {
			// Pop both the style declaration and the page rule nodes
			m_nodeStack.pop();
			m_root = m_nodeStack.pop();
		}
	
		public void startPage(String name, String pseudo_page)
			throws CssException {

			// Create the page rule and add it to the rule list
			DCssPageRule pr =
				new DCssPageRule(
					m_parentStyleSheet,
					null,
					name,
					pseudo_page);
			if (!m_nodeStack.empty()) {
				((DCssRuleList) m_nodeStack.peek()).add(pr);
			}

			// Create the style declaration
			DCssStyleDeclaration decl = new DCssStyleDeclaration(pr);
			pr.setStyle(decl);
			m_nodeStack.push(pr);
			m_nodeStack.push(decl);
		}

		public void endPage(String name, String pseudo_page)
			throws CssException {

			// Pop both the style declaration and the page rule nodes
			m_nodeStack.pop();
			m_root = m_nodeStack.pop();
		}

		public void startFontFace() throws CssException {

			// Create the font face rule and add it to the rule list
			DCssFontFaceRule ffr =
				new DCssFontFaceRule(m_parentStyleSheet, null);
			if (!m_nodeStack.empty()) {
				((DCssRuleList) m_nodeStack.peek()).add(ffr);
			}

			// Create the style declaration
			DCssStyleDeclaration decl = new DCssStyleDeclaration(ffr);
			ffr.setStyle(decl);
			m_nodeStack.push(ffr);
			m_nodeStack.push(decl);
		}

		public void endFontFace() throws CssException {

			// Pop both the style declaration and the font face rule nodes
			m_nodeStack.pop();
			m_root = m_nodeStack.pop();
		}

		public void startSelector(ISelectorList selectors) throws CssException {

			// Create the style rule and add it to the rule list
			DCssStyleRule sr =
				new DCssStyleRule(m_parentStyleSheet, null, selectors);
			if (!m_nodeStack.empty()) {
				((DCssRuleList) m_nodeStack.peek()).add(sr);
			}

			// Create the style declaration
			DCssStyleDeclaration decl = new DCssStyleDeclaration(sr);
			sr.setStyle(decl);
			m_nodeStack.push(sr);
			m_nodeStack.push(decl);
		}

		public void endSelector(ISelectorList selectors) throws CssException {

			// Pop both the style declaration and the style rule nodes
			m_nodeStack.pop();
			m_root = m_nodeStack.pop();
		}

		public void property(String name, ILexicalUnit value, boolean important)
			throws CssException {
			ICssStyleDeclaration decl =
				(ICssStyleDeclaration) m_nodeStack.peek();
			decl.addProperty(
				new DCssProperty(name, new DCssValue(value), important));
		}
	}

	public static void setProperty(String key, String val) {
		Properties props = System.getProperties();
		props.put(key, val);
		System.setProperties(props);
	}
}
