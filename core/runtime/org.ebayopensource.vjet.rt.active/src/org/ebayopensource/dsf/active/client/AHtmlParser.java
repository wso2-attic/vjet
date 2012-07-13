/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.active.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.ext.LexicalHandler;

import org.ebayopensource.dsf.active.dom.html.AHtmlBuilder;
import org.ebayopensource.dsf.active.dom.html.AHtmlDocument;
import org.ebayopensource.dsf.active.dom.html.AHtmlFrame;
import org.ebayopensource.dsf.active.dom.html.AHtmlHelper;
import org.ebayopensource.dsf.active.dom.html.AHtmlIFrame;
import org.ebayopensource.dsf.active.dom.html.AHtmlScript;
import org.ebayopensource.dsf.active.dom.html.IDocListener;
import org.ebayopensource.dsf.common.exceptions.DsfRuntimeException;
import org.ebayopensource.dsf.dap.rt.DapCtx;
import org.ebayopensource.dsf.html.dom.EHtmlAttr;
import org.ebayopensource.dsf.html.dom.HtmlTypeEnum;
import org.ebayopensource.dsf.html.js.ActiveJsExecutionControlCtx;
import org.ebayopensource.dsf.html.js.Encoding;
import org.ebayopensource.dsf.html.js.JSDebug;
import org.ebayopensource.dsf.html.js.URLUtil;
import org.ebayopensource.dsf.html.sax.AHtmlSchema;
import org.ebayopensource.dsf.html.sax.HtmlSaxParser;
import org.ebayopensource.dsf.jsnative.HtmlElement;
import org.ebayopensource.dsf.jsnative.Window;
import org.ebayopensource.dsf.liveconnect.client.IDLCClient;
import org.ebayopensource.dsf.common.StringUtils;

public class AHtmlParser
	implements ContentHandler, LexicalHandler, IDocListener {

	static final String BODY = HtmlTypeEnum.BODY.getName();
	static final AHtmlSchema m_schema = new AHtmlSchema();
	
	private AWindow m_browserWindow = null;
	private AHtmlBuilder m_baseBuilder = null;
	private URL m_urlContext = null;

	private boolean m_isRoot = true;
	private boolean m_isInsideBody = false;
	private boolean m_isJavascript = false;
	private String m_javascriptSrc = null;
	private String m_theScript = null;
	private boolean m_isFrame = false;
	private String m_frameSrc = null;
//	private String m_bodyOnLoadScript = null;
	private int m_numOfNestedBodyTag = 0;
	private int m_encoding;
	private StringBuilder m_generatedContents = new StringBuilder();
	private int m_scriptIndex = 0;

	// Constructor 
	public AHtmlParser(
		AWindow browserWindow,
		boolean isRoot,
		int encoding) {
		if (browserWindow == null) {
			throw new IllegalArgumentException("browserWindow is null");
		}
		m_isRoot = isRoot;
		m_browserWindow = browserWindow;
		m_baseBuilder = browserWindow.getHtmlBuilder();
		m_encoding = encoding;
	}
	
	/**
	 * Parse given html string
	 * @param src html string
	 * @param baseUrl base URL or <code>null</code>
	 * @return instance of Window
	 */
	public static Window parse(final String src, final URL baseUrl) {
		return parse(src, baseUrl, Encoding.SOURCE_DEFAULT);
	}
	
	/**
	 * Parse given html string
	 * @param src html string
	 * @param baseUrl base URL or <code>null</code>
	 * @param window instance of AWindow
	 * @return instance of Window
	 */
	public static Window parse(final String src, final URL baseUrl, final Window window) {
		return parse(src, baseUrl, Encoding.SOURCE_DEFAULT, window);
	}
	
	/**
	 * Parse given html string
	 * @param src html string
	 * @param baseUrl base URL or <code>null</code>
	 * @param encoding int defined in <code>org.ebayopensource.dsf.html.js.Encoding</code>
	 * @return instance of Window
	 */
	public static Window parse(
			final String src, final URL baseUrl, final int encoding)
	{
		final Window window = WindowFactory.createWindow();
		return parse(src, baseUrl, encoding, window);
	}
	
	public static Window parse(final URL url) {
		return parse(url, (AWindow)WindowFactory.createWindow());
	}
	
	public static Window parse(final URL url, AWindow window) {
		if (url == null) {
			throw new IllegalArgumentException("url is null");
		}
		try {
			return parse(window, new InputSource(url.openStream()), url, Encoding.SOURCE_DEFAULT);
		} catch (Exception e) {
			throw new DsfRuntimeException(e.getMessage(), e);
		} 
	}
			
	/**
	 * Parse given html string
	 * @param src html string
	 * @param baseUrl base URL or <code>null</code>
	 * @param encoding int defined in <code>org.ebayopensource.dsf.html.js.Encoding</code>
	 * @param window instance of Window
	 * @return instance of Window
	 */
	public static Window parse(
		final String src, final URL baseUrl, final int encoding, Window window)
	{
		if (src == null) {
			throw new IllegalArgumentException("html string is null");
		}
		final StringReader sr = new StringReader(src);
		try {
			parse((AWindow)window, new InputSource(sr), baseUrl, encoding);
		}
		catch (Exception e) {
//			e.printStackTrace();
			throw new DsfRuntimeException(e.getMessage(), e);
		} 						
		return window;
	}

	/**
	 * Parse html supplied by InputSource
	 * @param window instance of AWindow
	 * @param inputSource for html 
	 * @param urlContext 
	 * @param encoding int defined in <code>org.ebayopensource.dsf.html.js.Encoding</code>
	 * @throws SAXException
	 * @throws IOException
	 */
	static AWindow parse(
		AWindow window,
		InputSource inputSource,
		URL urlContext,
		int encoding)
		throws SAXException, IOException {
		if (window.getURL() == null) {
			window.setURL(urlContext);
		}

//		Parser parser = new Parser();
		HtmlSaxParser parser = new HtmlSaxParser();
//		parser.setFeature(HtmlSaxParser.CDATAElementsFeature, false);
		parser.setProperty(HtmlSaxParser.schemaProperty, m_schema); // Set custom Html schema
		AHtmlParser builder =
			new AHtmlParser(window, window.isRoot(), encoding);
		builder.setURLContext(urlContext);
		window.setRoot(false);
		parser.setContentHandler(builder);

		try {
			if (encoding != Encoding.SOURCE_UNKNOWN) {
				inputSource.setEncoding(Encoding.JAVA_ENCODING[encoding]);
			}
			parser.parse(inputSource);
		} catch (Exception e) {
			throw new DsfRuntimeException(e.getMessage(), e);
		}
		return window;
	}

	/**
	 * Parse the web page given the supplied URL
	 * @param window instance of AWindow
	 * @param url web page URL 
	 * @param encoding int defined in <code>org.ebayopensource.dsf.html.js.Encoding</code>
	 * @throws SAXException
	 * @throws IOException
	 */
	static AWindow parse(AWindow window, URL url, int encoding)
		throws SAXException, IOException {
		if (window.getURL() == null) {
			window.setURL(url);
		}
		HtmlSaxParser parser = new HtmlSaxParser();
		AHtmlParser builder =
			new AHtmlParser(window, window.isRoot(), encoding);
		builder.setURLContext(url);
		window.setRoot(false);
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
		return window;
	}

	public void setURLContext(URL u) {
		m_urlContext = u;
	}

	public void startDocument() throws SAXException {
		if (m_isRoot) {
			m_baseBuilder.startDocument();
		} 
	}

	public void endDocument() throws SAXException {
		if (m_isRoot) {
			m_baseBuilder.endDocument();
		} 
	}

	public void startElement(
		String namespaceURI,
		String localName,
		String qName,
		Attributes atts)
		throws SAXException {

		String s1 = localName.trim();
		if (!m_isRoot) {
			if (s1.equalsIgnoreCase(BODY)&&!m_isInsideBody)
			{
				m_isInsideBody = true;
				return;
			}
			if (!m_isInsideBody) {
				return;
			}
			if (s1.equalsIgnoreCase(BODY)) {
				m_numOfNestedBodyTag++;
			}
		}
		m_baseBuilder.startElement(namespaceURI, localName, qName, atts);
		
		
	}

	public void endElement(String namespaceURI, String localName, String qName)
		throws SAXException {
		localName = localName.trim();
		if (!m_isRoot) {
			if (localName.equalsIgnoreCase(BODY)) {

				if (m_numOfNestedBodyTag > 0) {
					m_numOfNestedBodyTag--;
				} else {
					m_isInsideBody = false;
					return;
				}
			}
			if (!m_isInsideBody) {
				return;
			}
		}
		
		// Get current element 
		HtmlElement curElem = m_baseBuilder.getCurrentElement();
		
		// Check if current element is <script> tag
		if (HtmlTypeEnum.SCRIPT.getName().equalsIgnoreCase(curElem.getTagName())) {
			AHtmlScript htmlScript = (AHtmlScript) curElem;
			boolean dlcScriptToken = 
				(htmlScript.getAttribute(IDLCClient.DLC_TOKEN) != null &&
				 htmlScript.getAttribute(IDLCClient.DLC_TOKEN).length() > 0);
			if (!dlcScriptToken) {
				m_isJavascript = true;
				if (htmlScript.getSrc() != null && htmlScript.getSrc().length() > 0) {
					m_javascriptSrc = htmlScript.getSrc().trim();
					
				} else {
					m_theScript = htmlScript.getText();
				}
			}
			m_scriptIndex++;
		}
		
		else if (HtmlTypeEnum.FRAME.getName().equalsIgnoreCase(curElem.getTagName()) ||
				HtmlTypeEnum.IFRAME.getName().equalsIgnoreCase(curElem.getTagName())) {
			m_isFrame = true;
			String src = null;
			if (HtmlTypeEnum.FRAME.getName().equalsIgnoreCase(curElem.getTagName())) {
				src = ((AHtmlFrame) curElem).getSrc();
			} else {
				src = ((AHtmlIFrame) curElem).getSrc();
			}
			if (src != null && src.length() > 0) {
				m_frameSrc = src.trim();
			}
		}

		if (m_isRoot || (!m_isRoot && !m_isJavascript)) {
			m_baseBuilder.endElement(namespaceURI, localName, qName);
		}
		
		if (m_isFrame && DapCtx.ctx().isWebMode()) {
			AWindow frameWindow = (AWindow) WindowFactory.createWindow(m_browserWindow);
			frameWindow.setName(curElem.getAttribute(EHtmlAttr.name.name()));
			if (m_frameSrc != null) {
				String pageUrl = null;
				if (m_urlContext != null) {
					pageUrl = URLUtil.getBaseURL(m_urlContext.toString());
				}
				URL url;
				try {
					url = new URL(URLUtil.getAbsoluteURL(m_frameSrc, URLUtil.getBaseURL(pageUrl)));
				} catch (MalformedURLException e) {
					e.printStackTrace();	//KEEPME
					throw new SAXException(e);
				} finally {
					m_isFrame = false;
					m_frameSrc = null;
				}
				try {
					parse(frameWindow, new InputSource(url.openStream()), 
							url, Encoding.SOURCE_DEFAULT);
				} catch (IOException e) {
					e.printStackTrace(); //KEEPME
					throw new SAXException(e);
				}  finally {
					m_isFrame = false;
					m_frameSrc = null;
				}
			}
			m_isFrame = false;
			m_frameSrc = null;
			
			AHtmlDocument frameDoc = (AHtmlDocument) frameWindow.getDocument();
			if (HtmlTypeEnum.FRAME.getName().equalsIgnoreCase(curElem.getTagName())) {
				AHtmlFrame htmlFrame = (AHtmlFrame) curElem;
				AHtmlHelper.setContentDocument(htmlFrame, frameDoc);
			}
			if (HtmlTypeEnum.IFRAME.getName().equalsIgnoreCase(curElem.getTagName())) {
				AHtmlIFrame htmlFrame = (AHtmlIFrame) curElem;	
				AHtmlHelper.setContentDocument(htmlFrame, frameDoc);
			}
		}

		if (m_isJavascript && ActiveJsExecutionControlCtx.ctx().needExecuteJavaScript()) {
			if (m_javascriptSrc != null && m_javascriptSrc.length() > 0) {
				String pageUrl = null;
				if (m_urlContext != null) {
					pageUrl = URLUtil.getBaseURL(m_urlContext.toString());
				}
				String url = URLUtil.getAbsoluteURL(m_javascriptSrc,  URLUtil.getBaseURL(pageUrl));
				try {
					InputStreamReader reader = null;
					if (m_encoding != Encoding.SOURCE_UNKNOWN) {
						reader =
							new InputStreamReader(
								new URL(url).openStream(),
								Encoding.JAVA_ENCODING[m_encoding]);
					} else {
						reader =
							new InputStreamReader(new URL(url).openStream());
					}

					StringBuffer sb = new StringBuffer();
					int c = 0;
					while ((c = reader.read()) != -1) {
						sb.append((char) c);
					}
					String scriptStr = sb.toString();
					executeScript(scriptStr, url);
				} catch (Exception ex) {
					ex.printStackTrace(); //KEEPME
					throw new SAXException(ex);
				} finally {
					m_isJavascript = false;
					m_javascriptSrc = null;
					m_theScript = null;
				}
			} else if (m_theScript != null && m_theScript.trim().length() > 0) {
				executeScript(m_theScript,
					getScriptId(m_urlContext == null ? null : m_urlContext.toExternalForm(), m_theScript));
			}
			m_isJavascript = false;
			m_javascriptSrc = null;
			m_theScript = null;
		}

//		if ((localName.equalsIgnoreCase(BODY) && m_bodyOnLoadScript != null)) {
//			if (m_isRoot) {
//				executeScript(m_bodyOnLoadScript);
//				m_bodyOnLoadScript = null;
//			}
//		}
	}
	
	private String getScriptId(String pageUrl, String script) {
		String id = (pageUrl == null) ? "Annonymous" : pageUrl;
		return id + "#Script#" + m_scriptIndex +"#" +  getLineNumber() + "#"+ innerGetEmptyLineCount(script);
		
	}
	
	private int innerGetEmptyLineCount(String script) {
		List<String> ss = StringUtils.splitStr(script, '\n');
		Iterator<String> it = ss.iterator();
		int i = 0;
		while(it.hasNext()) {
			String s = it.next();
			if (s.trim().length() == 0) {
				i++;
			} else {
				break;
			}
		}
		return i;
	}

	private int getLineNumber() {
		if (m_baseBuilder.getDocumentLocator() == null) {
			return m_scriptIndex;
		} else {
			return m_baseBuilder.getDocumentLocator().getLineNumber();
		}
	}

	private void executeScript(String scriptStr, String srcId) {
		IDocListener builderListener =
			m_browserWindow.getDocListener();		
		boolean domChangeListenerEnabled = 
			m_browserWindow.isDomChangeListenerEnabled();
		try {
			m_browserWindow.setDocListener(this);
			m_browserWindow.enableDomChangeListener(true);
			ScriptExecutor.executeScript(scriptStr, srcId, m_browserWindow);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
		finally {
			// reset doc listener
			m_browserWindow.setDocListener(builderListener);
			// reset DomChangeListener control
			m_browserWindow.enableDomChangeListener(domChangeListenerEnabled);
		}
	}

	public void characters(char[] ac, int i, int j) throws SAXException {
		m_baseBuilder.characters(ac, i, j);
	}

	public void ignorableWhitespace(char[] ac, int i, int j)
		throws SAXException {
		m_baseBuilder.ignorableWhitespace(ac, i, j);
	}

	public void processingInstruction(String s, String s1)
		throws SAXException {
		m_baseBuilder.processingInstruction(s, s1);
	}

	public void setDocumentLocator(org.xml.sax.Locator locator) {
		m_baseBuilder.setDocumentLocator(locator);
	}

	public void doneDocumentWrite() {
		String newContent = m_browserWindow.getGeneratedContentFromScript();
		if (newContent.trim().length() == 0)
			return;
		boolean parseIt = (m_generatedContents.length() == 0) ? true : false;
		m_generatedContents.append(newContent.trim());
		if (newContent.endsWith(">") || newContent.endsWith("\n")) {
			parseIt = true;
		} else if (newContent.startsWith("<") && !newContent.endsWith(">")) {
			parseIt = false; //need to wait
		}

		if (parseIt) {
			String content = m_generatedContents.toString();
			// BUGFIX: http://quickbugstage.arch.ebay.com/show_bug.cgi?id=308
			// Not sure why needed to add <body> tag to the document.write content. The is
			// causing document to have multiple <body> as well as for <script> tag to get
			// </body> as the text of <script> element.
//			String noLeadingSpaceContent = content.trim();
//			if (noLeadingSpaceContent.length() >= 7) {
//				String leadingChars = noLeadingSpaceContent.substring(0, 7).toLowerCase();
//				if (leadingChars.startsWith("<style")
//					|| leadingChars.startsWith("<script")) {
//					content = "<body> " + content + " </body>";
//				}
//			}
			
			// Check if we have a complete <script> element
			// If not, then we have to wait for complete data before parsing it.
			String lowercaseContent = content.toLowerCase();
			if (lowercaseContent.startsWith("<script") &&
					!lowercaseContent.endsWith("</script")) {
				return; // need to wait
			}
			
			try {
				if (ActiveJsExecutionControlCtx.ctx().needParseGeneratedContent()) {
					parse(
						m_browserWindow,
						new InputSource(new StringReader(content)),
						m_urlContext,
						m_encoding);
				}
			} catch (Exception ex) {
				JSDebug.println(ex.getMessage());
			}
			m_generatedContents.delete(0, m_generatedContents.length());
		}

	}

	////////////////////////////////////////////////////////////////////
	// Additional implementation for ContentHandler interface
	////////////////////////////////////////////////////////////////////
	public void startPrefixMapping(String prefix, String uri)
		throws SAXException {
		m_baseBuilder.startPrefixMapping(prefix, uri);
	}

	public void endPrefixMapping(String prefix) throws SAXException {
		m_baseBuilder.endPrefixMapping(prefix);
	}

	public void skippedEntity(String name) throws SAXException {
		m_baseBuilder.skippedEntity(name);
	}

	////////////////////////////////////////////////////////////////////
	// LexicalHandler required implementation
	////////////////////////////////////////////////////////////////////
	public void comment(char[] text, int start, int length)
		throws SAXException {
		m_baseBuilder.comment(text, start, length);
	}

	public void endCDATA() throws SAXException {
		m_baseBuilder.endCDATA();
	}
	public void endDTD() throws SAXException {
		m_baseBuilder.endDTD();
	}
	public void endEntity(String name) throws SAXException {
		m_baseBuilder.endEntity(name);
	}
	public void startCDATA() throws SAXException {
		m_baseBuilder.startCDATA();
	}
	public void startDTD(String name, String publicId, String systemId)
		throws SAXException {
		m_baseBuilder.startDTD(name, publicId, systemId);
	}
	public void startEntity(String name) throws SAXException {
		m_baseBuilder.startEntity(name);
	}

}
