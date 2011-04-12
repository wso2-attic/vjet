/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XpathHelper {
   private XPath m_xpath;

   private Map<String, XPathExpression> m_cacheMap = new HashMap<String, XPathExpression>();

   public XpathHelper() {
      m_xpath = XPathFactory.newInstance().newXPath();
   }

   public Boolean getBooleanAttrValue(Node from, String attributeName) {
      return Boolean.valueOf(getStringAttrValue(from, attributeName));
   }

   public Double getDoubleAttrValue(Node from, String attributeName) {
      return Double.valueOf(getStringAttrValue(from, attributeName));
   }

   public String getStringAttrValue(Node from, String attributeName) {
      NamedNodeMap attributes = from.getAttributes();

      if (attributes != null) {
         Node attribute = attributes.getNamedItem(attributeName);

         if (attribute != null) {
            return attribute.getNodeValue();
         }
      }

      return null;
   }

   private Object select(Object from, String expression, QName returnType) throws XPathExpressionException {
      XPathExpression xpe = m_cacheMap.get(expression);

      if (xpe == null) {
         xpe = m_xpath.compile(expression);
         m_cacheMap.put(expression, xpe);
      }

      return xpe.evaluate(from, returnType);
   }

   public Boolean selectBoolean(Node from, String expression) throws XPathExpressionException {
      return (Boolean) select(from, expression, XPathConstants.BOOLEAN);
   }

   public Node selectNode(Node from, String expression) throws XPathExpressionException {
      return (Node) select(from, expression, XPathConstants.NODE);
   }

   public NodeList selectNodeList(Node from, String expression) throws XPathExpressionException {
      return (NodeList) select(from, expression, XPathConstants.NODESET);
   }

   public Number selectNumber(Node from, String expression) throws XPathExpressionException {
      return (Number) select(from, expression, XPathConstants.NUMBER);
   }

   public String selectString(Node from, String expression) throws XPathExpressionException {
      return (String) select(from, expression, XPathConstants.STRING);
   }

   public static Document getDocument(InputStream source) throws SAXException, IOException,
         ParserConfigurationException {
      DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
      domFactory.setNamespaceAware(true); // never forget this!

      Document doc = domFactory.newDocumentBuilder().parse(source);
      return doc;
   }

   public static Document newDocument() throws ParserConfigurationException {
      DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
      domFactory.setNamespaceAware(true); // never forget this!

      Document doc = domFactory.newDocumentBuilder().newDocument();
      return doc;
   }

   public static String buildXml(Node node) throws TransformerFactoryConfigurationError, TransformerException {
      StringWriter writer = new StringWriter();

      buildXml(node, writer);
      return writer.toString();
   }

   public static void buildXml(Node node, Writer writer) throws TransformerFactoryConfigurationError,
         TransformerException {
      Transformer transformer = TransformerFactory.newInstance().newTransformer();
      transformer.setOutputProperty(javax.xml.transform.OutputKeys.ENCODING, "utf-8");
      transformer.setOutputProperty(javax.xml.transform.OutputKeys.INDENT, "yes");
      transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

      transformer.transform(new DOMSource(node), new StreamResult(writer));
   }
}
