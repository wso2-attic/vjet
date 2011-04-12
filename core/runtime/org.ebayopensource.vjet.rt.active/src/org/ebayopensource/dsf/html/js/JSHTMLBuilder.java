/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.js;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.ext.LexicalHandler;

import org.ebayopensource.dsf.html.dom.DStyle;
import org.ebayopensource.dsf.html.dom.HtmlTypeEnum;
import org.ebayopensource.dsf.html.dom.util.HtmlBuilder;
import org.ebayopensource.dsf.html.dom.util.HtmlDomHelper;
import org.ebayopensource.dsf.html.sax.HtmlSaxParser;

public class JSHTMLBuilder
	implements ContentHandler, LexicalHandler, JSBuilderListener {

	static final String BODY = HtmlTypeEnum.BODY.getName();
	
	private JSWindow browserWindow = null;
	private HtmlBuilder baseBuilder = null;
	private URL urlContext = null;

	private boolean isRoot = true;
	private boolean isInsideBody = false;
	private boolean isJavascript = false;
	private String javascriptSrc = null;
	private String theScript = null;
	private String bodyOnLoadScript = null;
	private int numOfNestedBodyTag = 0;
	private int encoding;
	private StringBuffer generatedContents = new StringBuffer();

	public JSHTMLBuilder(
		JSWindow browserWindow,
		boolean isRoot,
		int encoding) {
		this.isRoot = isRoot;
		this.browserWindow = browserWindow;
		this.baseBuilder = browserWindow.getBaseBuilder();
		this.encoding = encoding;
	}

	public static void doParse(
		JSWindow jsWindow,
		InputSource inputSource,
		URL urlContext,
		int encoding)
		throws SAXException, IOException {
		if (jsWindow.getURL() == null) {
			jsWindow.setURL(urlContext);
		}

		HtmlSaxParser parser = new HtmlSaxParser();
		JSHTMLBuilder builder =
			new JSHTMLBuilder(jsWindow, jsWindow.isRoot, encoding);
		builder.setURLContext(urlContext);
		jsWindow.isRoot = false;
		parser.setContentHandler(builder);

		try {
			parser.parse(inputSource);
		} catch (Exception e) {
			JSDebug.println(e.getMessage());
			e.printStackTrace();
		}
	}

	/** Parse a web page specified by the link.
	 *
	 * @param s The web page address link.
	 * @throws SAXException
	 * @throws IOException
	 */
	public static void doParse(JSWindow jsWindow, URL url, int encoding)
		throws SAXException, IOException {
		if (jsWindow.getURL() == null) {
			jsWindow.setURL(url);
		}
		HtmlSaxParser parser = new HtmlSaxParser();
		JSHTMLBuilder builder =
			new JSHTMLBuilder(jsWindow, jsWindow.isRoot, encoding);
		builder.setURLContext(url);
		jsWindow.isRoot = false;
		parser.setContentHandler(builder);

		// Make IE request headers
		URLConnection urlConn = url.openConnection();
		urlConn.setUseCaches(false);
		urlConn.setAllowUserInteraction(false);
		urlConn.setDoInput(true);
		urlConn.setRequestProperty(
			"User-Agent",
			"Mozilla/4.0 (compatible; MSIE 5.5; Windows NT 4.0)");
		urlConn.setRequestProperty(
			"Accept",
			"text/html, image/gif, image/jpeg, */*");
		urlConn.setRequestProperty("Accept-Language", "en-us");
		urlConn.connect();
		InputStream is = urlConn.getInputStream();
		parser.parse(new InputSource(is));
	}

	public void setURLContext(URL u) {
		urlContext = u;
	}

	public void startDocument() throws SAXException {
		//JSDebug.print("***JSHTMLBuilder:startDocument():");
		if (isRoot == true) {
			//JSDebug.println("");
			baseBuilder.startDocument();
		} else {
			//JSDebug.println(" (ignored)");
		}
	}

	public void endDocument() throws SAXException {
		//JSDebug.print("***JSHTMLBuilder:endDocument():");
		if (isRoot == true) {
			//JSDebug.println("");
			baseBuilder.endDocument();
		} else {
			//JSDebug.println(" (ignored)");
		}
	}

	public void startElement(
		String namespaceURI,
		String localName,
		String qName,
		Attributes atts)
		throws SAXException {
		//JSDebug.print("***JSHTMLBuilder:startElement(): " + s);
		String s1 = localName.trim();

		if (!isRoot) {
			if (s1.equalsIgnoreCase(BODY)&&!isInsideBody)
			{
				isInsideBody = true;
				//JSDebug.println(" (ignored)");
				return;
			}
			if (!isInsideBody) {
				//JSDebug.println(" (ignored)");
				return;
			}
			if (s1.equalsIgnoreCase(BODY)) {
				numOfNestedBodyTag++;
			}
		}

		if (s1.equalsIgnoreCase(HtmlTypeEnum.SCRIPT.getName())) {
			// Javascript is the default scripting language in browser
			isJavascript = true;
			javascriptSrc = null;
			theScript = null;
		}

		boolean isBodyElement = false;
		//boolean isFrameSetElement = false;
		if (s1.equalsIgnoreCase(BODY)) {
			isBodyElement = true;
		}
		//else if (s1.equalsIgnoreCase("frameset")) {
		//    isFrameSetElement = true;
		//}

		if (isJavascript || isBodyElement) {
			for (int i = 0; i < atts.getLength(); i++) {
				String name = atts.getLocalName(i).trim();
				String value = atts.getValue(i).trim();
				//JSDebug.print(" " + name + "=" + value);

				if (isJavascript) {
					if (name.equalsIgnoreCase("language")
						&& value.toLowerCase().startsWith("javascript")
							== false) {
						isJavascript = false;
					}
					// Assumption: src only for JavaScript
					else if (
						name.equalsIgnoreCase("src") && value.length() > 0) {
						//javascriptSrc = value;
					}
				} else if (isBodyElement) { // || isFrameSetElement) {
					if (name.equalsIgnoreCase("onload")) {
						if (value.length() > 0) {
							//remove the return statement from javascript
							int index = value.indexOf("; return ");
							if (index == -1)
								index = value.indexOf(";return ");
							if (index != -1)
								value = value.substring(0, index);
							if (isBodyElement)
								bodyOnLoadScript = value;
							//else
							//    frameSetOnLoadScript = value;
						}
					}
				}
			}
		}

		if ((isRoot == true)
			|| ((isRoot == false) && (isJavascript == false))) {
			baseBuilder.startElement(namespaceURI, localName, qName, atts);
			if (s1.equalsIgnoreCase(HtmlTypeEnum.FORM.getName())) {
				//baseBuilder.startElement(HtmlTypeEnum.DIV.getName(), null);
			}
		}
	}

	public void endElement(String namespaceURI, String localName, String qName)
		throws SAXException {
		//JSDebug.print("***JSHTMLBuilder:endElement(): " + s);

		localName = localName.trim();
		if (!isRoot) {
			if (localName.equalsIgnoreCase(BODY)) {

				if (numOfNestedBodyTag > 0) {
					numOfNestedBodyTag--;
				} else {
					isInsideBody = false;
					//JSDebug.println(" (ignored)");
					return;
				}
			}
			if (!isInsideBody) {
				//JSDebug.println(" (ignored)");
				return;
			}
		}

		if ((isRoot == true)
			|| ((isRoot == false) && (isJavascript == false))) {
			baseBuilder.endElement(namespaceURI, localName, qName);
		}

		if (localName.equalsIgnoreCase(HtmlTypeEnum.STYLE.getName())) {
			try {
				browserWindow.setCssStyleSheet(
					HtmlDomHelper.getStyleSheet(
						(DStyle) baseBuilder.getCurrentElement().getLastChild()));
			} catch (Exception e) {
			}

		}

		if (isJavascript && ActiveJsExecutionControlCtx.ctx().needExecuteJavaScript()) {
			if (javascriptSrc != null && javascriptSrc.trim().length() > 0) {
				String pageUrl = urlContext.toString();
				String url = URLUtil.getAbsoluteURL(javascriptSrc, pageUrl);
				try {
					InputStreamReader reader = null;
					if (encoding != Encoding.SOURCE_UNKNOWN) {
						reader =
							new InputStreamReader(
								new URL(url).openStream(),
								Encoding.JAVA_ENCODING[encoding]);
					} else {
						reader =
							new InputStreamReader(new URL(url).openStream());
					}

					//replace document.all(...) to document.all[]
					StringBuffer sb = new StringBuffer();
					int c = 0;
					while ((c = reader.read()) != -1) {
						sb.append((char) c);
					}
					String scriptStr = sb.toString();
					boolean noReplace = true;
					int index = scriptStr.indexOf("document.all(");
					while (index > 0) {
						index += 12;
						sb.setCharAt(index, '[');
						int endIndex = scriptStr.indexOf(")", index);
						sb.setCharAt(endIndex, ']');
						index = scriptStr.indexOf("document.all(", endIndex);
						noReplace = false;
					}

					JSBuilderListener builderListener =
						browserWindow.getJsBuilderListener();
					browserWindow.setJsBuilderListener(this);
					try {
						//browserWindow.executeScript(reader);
						if (noReplace)
							browserWindow.executeScript(scriptStr);
						else
							browserWindow.executeScript(sb.toString());
					} catch (Exception ex) {
						JSDebug.printJavaScriptException(ex, javascriptSrc);
					}
					//reset the builderListener
					browserWindow.setJsBuilderListener(builderListener);
				} catch (Exception ex) {
					JSDebug.printJavaScriptException(ex, javascriptSrc);
				}
			} else if (theScript != null && theScript.trim().length() > 0) {
				JSBuilderListener builderListener =
					browserWindow.getJsBuilderListener();
				browserWindow.setJsBuilderListener(this);
				try {
					browserWindow.executeScript(theScript);
				} catch (Exception ex) {
					JSDebug.printJavaScriptException(ex, theScript);
				}
				//reset the builderListener
				browserWindow.setJsBuilderListener(builderListener);
			}
			isJavascript = false;
			javascriptSrc = null;
			theScript = null;
		}

		if ((localName.equalsIgnoreCase(BODY) && bodyOnLoadScript != null)) {
			String replacement = browserWindow.getJSLocation().getReplacement();
			if (replacement != null && replacement.trim().length() > 0) {
				//do nothing
			} else if (isRoot) {
				String script = bodyOnLoadScript;
				JSBuilderListener builderListener =
					browserWindow.getJsBuilderListener();
				browserWindow.setJsBuilderListener(this);
				try {
					browserWindow.executeScript(script);
				} catch (Exception ex) {
					JSDebug.printJavaScriptException(ex, bodyOnLoadScript);
				}
				//reset the builderListener
				browserWindow.setJsBuilderListener(builderListener);
				bodyOnLoadScript = null;
			}
		}
	}

	public void characters(char[] ac, int i, int j) throws SAXException {
		String script = new String(ac, i, j);
		//JSDebug.println("***JSHTMLBuilder:characters(): " + script);

		if (isJavascript == true) {
			if (theScript == null) {
				theScript = script;
			}
			else {
				theScript += script;
			}			
		}

		if ((isRoot == true)
			|| ((isRoot == false) && (isJavascript == false))) {
			if (script.trim().startsWith("<![") == false) {
				baseBuilder.characters(ac, i, j);
			}
		}
	}

	public void ignorableWhitespace(char[] ac, int i, int j)
		throws SAXException {
		baseBuilder.ignorableWhitespace(ac, i, j);
	}

	public void processingInstruction(String s, String s1)
		throws SAXException {
		baseBuilder.processingInstruction(s, s1);
	}

	public void setDocumentLocator(org.xml.sax.Locator locator) {
		baseBuilder.setDocumentLocator(locator);
	}

	public void doneDocumentWrite() {
		String newContent = browserWindow.getGeneratedContentFromScript();
		if (newContent.trim().length() == 0)
			return;
		boolean parseIt = (generatedContents.length() == 0) ? true : false;
		generatedContents.append(newContent);
		//newContent.trim();
		if (newContent.endsWith(">") || newContent.endsWith("\n")) {
			parseIt = true;
		} else if (newContent.startsWith("<") && !newContent.endsWith(">")) {
			parseIt = false; //need to wait
		}

		if (parseIt) {
			String content = generatedContents.toString();
			String noLeadingSpaceContent = content.trim();
			if (noLeadingSpaceContent.startsWith("<style")
				|| noLeadingSpaceContent.startsWith("<STYLE")
				|| noLeadingSpaceContent.startsWith("<script")
				|| noLeadingSpaceContent.startsWith("<SCRIPT")) {
				content = "<body> " + content + " </body>";
			}
			try {
				if (ActiveJsExecutionControlCtx.ctx().needParseGeneratedContent()) {
					doParse(
						browserWindow,
						new InputSource(new StringReader(content)),
						urlContext,
						encoding);
				}
			} catch (Exception ex) {
				JSDebug.println(ex.getMessage());
			}
			generatedContents.delete(0, generatedContents.length());
		}

	}

	////////////////////////////////////////////////////////////////////
	// Additional implementation for ContentHandler interface
	////////////////////////////////////////////////////////////////////
	public void startPrefixMapping(String prefix, String uri)
		throws SAXException {
		baseBuilder.startPrefixMapping(prefix, uri);
	}

	public void endPrefixMapping(String prefix) throws SAXException {
		baseBuilder.endPrefixMapping(prefix);
	}

	public void skippedEntity(String name) throws SAXException {
		baseBuilder.skippedEntity(name);
	}

	////////////////////////////////////////////////////////////////////
	// LexicalHandler required implementation
	////////////////////////////////////////////////////////////////////
	public void comment(char[] text, int start, int length)
		throws SAXException {
		baseBuilder.comment(text, start, length);
	}

	public void endCDATA() throws SAXException {
		baseBuilder.endCDATA();
	}
	public void endDTD() throws SAXException {
		baseBuilder.endDTD();
	}
	public void endEntity(String name) throws SAXException {
		baseBuilder.endEntity(name);
	}
	public void startCDATA() throws SAXException {
		baseBuilder.startCDATA();
	}
	public void startDTD(String name, String publicId, String systemId)
		throws SAXException {
		baseBuilder.startDTD(name, publicId, systemId);
	}
	public void startEntity(String name) throws SAXException {
		baseBuilder.startEntity(name);
	}

}
