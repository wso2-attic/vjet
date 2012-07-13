/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
//import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.xml.sax.InputSource;

import org.ebayopensource.dsf.common.exceptions.DsfRuntimeException;
import org.ebayopensource.dsf.html.dom.DHtmlDocument;
import org.ebayopensource.dsf.html.dom.util.HtmlBuilder;
import org.ebayopensource.dsf.html.dom.util.HtmlBuilderExt;
//import org.ebayopensource.dsf.html.js.Encoding;
//import org.ebayopensource.dsf.html.js.JSHTMLBuilder;
//import org.ebayopensource.dsf.html.js.JSWindow;
//import org.ebayopensource.dsf.html.js.JSWindowFactory;
import org.ebayopensource.dsf.html.sax.HtmlSaxParser;
import org.ebayopensource.dsf.html.sax.Schema;

/**
 * Provides some useful methods that returns a {@link DHtmlDocument} from 
 * the source input String.
 * 
 * @see DHtmlDocument
 */
public class HtmlBuilderHelper {
	
	public static final String REGX_TAG_SKIP = "(<[a-zA-Z_0-9:]+)/([ a-zA-Z_0-9:]+)";
//	public static JSWindow parse(final String src, final URL baseUrl,
//			final JSWindow window) {
//		return parse(src, baseUrl, Encoding.SOURCE_DEFAULT, window);
//	}
//
//	public static JSWindow parse(final String src, final URL baseUrl) {
//		return parse(src, baseUrl, Encoding.SOURCE_DEFAULT);
//	}
//
//	public static JSWindow parse(final String src, final URL baseUrl,
//			final int encoding) {
//		final JSWindow window = JSWindowFactory.createJSWindow();
//		return parse(src, baseUrl, encoding, window);
//	}
//
//	public static JSWindow parse(final String src, final URL baseUrl,
//			final int encoding, JSWindow window) {
//		final StringReader sr = new StringReader(src);
//		try {
//			JSHTMLBuilder.doParse(window, new InputSource(sr), baseUrl,
//					encoding);
//		} catch (Exception e) {
//			throw new DsfRuntimeException(e.getMessage(), e);
//		}
//		return window;
//	}
//
//	public static JSWindow parse(final URL url) {
//		return parse(url, Encoding.SOURCE_DEFAULT);
//	}
//
//	public static JSWindow parse(final URL url, final int encoding) {
//		final JSWindow window = JSWindowFactory.createJSWindow();
//		try {
//			JSHTMLBuilder.doParse(window, url, encoding);
//		} catch (Exception e) {
//			throw new DsfRuntimeException(e.getMessage(), e);
//		}
//		return window;
//	}

	/**
	 * Returns a <code>DHtmlDocument</code> for the given HTML fragment.
	 * <p>
	 * This is the default parsing method. The parser uses <code>HtmlSchema</code> 
	 * and ignores the duplicate ids when parsing the fragment. 
	 * 
	 * @param src 
	 * 		  html fragment string
	 * @return 
	 * 		  the <code>DHtmlDocument</code> object for the given HTML fragment
	 * @see   #parseHtmlFragment(String, boolean)
	 * @see   #parseHtmlFragment(String, HtmlParserOptions)
	 * @see   #parseHtmlFragment(String, boolean, Schema)
	 */
	public static DHtmlDocument parseHtmlFragment(final String src) {
		return parseHtmlFragment(src, false, null);
	}

	/**
	 * Returns a <code>DHtmlDocument</code> for the given HTML fragment.
	 * <p>
	 * This method accepts {@link HtmlParserOptions} to specifiy the behavior of 
	 * the parser. If <code>HtmlParserOptions</code> is passed as
	 * <code>null</code>, the method would call {@link #parseHtmlFragment(String)} 
	 * as default.
	 * 
	 * @param src 
	 *        html fragment string
	 * @param buildOption 
	 *        application html parser options
	 * @return 
	 *        the <code>DHtmlDocument</code> object for the given HTML fragment
	 * @see   HtmlParserOptions
	 * @see   #parseHtmlFragment(String)
	 * @see   #parseHtmlFragment(String, boolean)
	 * @see   #parseHtmlFragment(String, boolean, Schema)
	 */
	public static DHtmlDocument parseHtmlFragment(final String src,
			final HtmlParserOptions buildOption) {
		if (buildOption == null) {
			return parseHtmlFragment(src);
		}
		return parseHtmlFragment(src, buildOption.isFixDuplicateIds(),
				buildOption.getSchema(), buildOption);
	}
	
	/**
	 * Returns a <code>DHtmlDocument</code> for the given HTML fragment.
	 * <p>
	 * This method accepts a <code>boolean</code> value to indicate whether or
	 * not fixing the duplicate ids when parsing the fragment.
	 * 
	 * @param src
	 *        html fragment string
	 * @param fixDuplicateIds
	 *        <code>true</code> to fix duplicate element ids when parsing the
	 *        fragment
	 * @return 
	 *        the <code>DHtmlDocument</code> object for the given HTML fragment
	 * @see   #parseHtmlFragment(String)
	 * @see   #parseHtmlFragment(String, HtmlParserOptions)
	 * @see   #parseHtmlFragment(String, boolean, Schema)
	 */
	public static DHtmlDocument parseHtmlFragment(final String src,
			final boolean fixDuplicateIds) {
		return parseHtmlFragment(src, fixDuplicateIds, null);
	}

	/**
	 * Returns a <code>DHtmlDocument</code> for a given HTML fragment.
	 * <p>
	 * This method allows user specify a custom schema for the parser. And it also 
	 * accepts a <code>boolean</code> value to indicate whether or not fixing 
	 * the duplicate ids when parsing the fragment. if the parameter 
	 * <code>schema</code> is passed as <code>null</code>, the default 
	 * <code>HtmlSchema</code> would be used. To pass custom schema create 
	 * unique ids in the form of xxx_n where xxx is a duplicate id specified for 
	 * elements in the document and n is number of times id appears - 2. For 
	 * example xxx, xxx_0, xxx_1, etc.
	 * 
	 * @param src
	 *        html fragment string
	 * @param fixDuplicateIds
	 *        <code>true</code> to fix duplicate element ids when parsing the
	 *        fragment
	 * @param schema
	 *        custom schema used by the parser. 
	 * @return 
	 *        the <code>DHtmlDocument</code> object for the given HTML fragment
	 * @see   #parseHtmlFragment(String)
	 * @see   #parseHtmlFragment(String, HtmlParserOptions)
	 * @see   #parseHtmlFragment(String, boolean)
	 */
	public static DHtmlDocument parseHtmlFragment(final String src,
			final boolean fixDuplicateIds, final Schema schema) {
		return parseHtmlFragment(src, fixDuplicateIds, schema, null);
	}

	/**
	 * Returns a <code>DHtmlDocument</code> for a given HTML fragment.
	 * <p>
	 * This is the basic implementation of <code>parseHtmlFramment()</code> 
	 * series methods, and it is not public. If the parameter 
	 * <code>fixDuplicatedIds</code> is setting as true, the relative attribute 
	 * <code>fixDuplicatedIds</code> would be ignored at all. Both 
	 * <code>schema</code> and <code>buildOption</code> could be 
	 * <code>null</code>, and the default object would be passed at then.
	 * 
	 * @param src
	 *        html fragment string
	 * @param fixDuplicateIds
	 *        <code>true</code> to fix duplicate element ids when parsing the
	 *        fragment
	 * @param schema
	 *        custom schema. 
	 * @param buildOption 
	 *        application html parser options       
	 * @return 
	 *        the <code>DHtmlDocument</code> object for the given HTML fragment
	 * @see   HtmlParserOptions
	 * @see   #parseHtmlFragment(String)
	 * @see   #parseHtmlFragment(String, HtmlParserOptions)
	 * @see   #parseHtmlFragment(String, boolean)
	 */
	private static DHtmlDocument parseHtmlFragment(final String src,
			final boolean fixDuplicateIds, final Schema schema,
			final HtmlParserOptions buildOption) 
	{		
		
		final HtmlBuilder builder;
		String newSrc = src;
		if (buildOption == null) {
			builder = new HtmlBuilder();
		} else {
			if (schema != null && schema != buildOption.getSchema()){
				//the schema passed in must be the same as in buildOption				
				throw new DsfRuntimeException("Schema corruption.");
			}
			newSrc = PreProcessHelper.preProcess(src, 
					buildOption.isPreDecode(),
					buildOption.isPreProcessTagName(), 
					buildOption.isPreProcessUnkownTag(), 
					buildOption.getSchema());
			
			builder = new HtmlBuilderExt(buildOption);
		}
		if (fixDuplicateIds) {
			builder.setFixDuplicateIds(true);
		}
		final HtmlSaxParser parser = new HtmlSaxParser();
		parser.setContentHandler(builder);
		//parser.setLexicalHandler(builder);		

		final InputSource is = new InputSource(new StringReader(newSrc));
	
		try {			
			if (schema != null) {
				// custom Html schema
				parser.setProperty(HtmlSaxParser.schemaProperty, schema); // Set
			}
			//if parser doesn't add defaultAttr, 
			//then org.ebayopensource.dsf.active.dom.html.AllADomTests will fail
			//parser.setFeature(HtmlSaxParser.defaultAttributesFeature, false);					
			parser.setFeature(HtmlSaxParser.defaultAttributesFeature, 
					(buildOption==null || !buildOption.isNoDefaultAttr())?true:false);	

			if (buildOption != null && buildOption.getWellformTags().size() > 0) {
				parser.parse(is, buildOption.getWellformTags());				
			} else {
				parser.parse(is);
			}			
		} catch (Exception e) {
			throw new DsfRuntimeException(e);
		}

		return builder.getHTMLDocument();
	}	
	
	
	
	//buildOption.isPreProcessTagName(), buildOption.isPreProcessUnkownTag(), buildOption.getSchema()
	/*
	 * Helper to pro-process the input html fragment per parseOption
	 */
	static class PreProcessHelper{
		//fix bug BUGDB00616728  <test/> should not be replaced by <test >
		//<test / >, <test     /   >
		private static final String REGX_TAG_SKIP1 = "(<[a-zA-Z_0-9:]+)([ ]*/[ ]+>)";
		private static String preProcess(String input, boolean bDecode, boolean bHandleTagName, boolean bHandleUnkown1stTag, Schema schema){
			String newSrc = input;
			if (bDecode){//decode the input string
				//URLDecoder replace "+" by " "
				//to avoid lost "+", replace + by %2B, pre-decode					
				try {
					newSrc = URLDecoder.decode(input.replaceAll("\\+", "%2B"), "utf-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//wait for bug fix in URLDecoder
				//newSrc = URLDecoder.decode(src.replaceAll("\\+", "%2B"), "UTF-8");
			}
			if (bHandleTagName){	
				//newSrc = newSrc.replaceAll("<<", "<");
				newSrc = newSrc.replaceAll(REGX_TAG_SKIP1, "$1/>");
				newSrc = newSrc.replaceAll(REGX_TAG_SKIP, "$1 $2");
				if (bHandleUnkown1stTag){
					String tag = getFirstTag(newSrc);
					//if first tag is unknown, prefix with html and body tags
					//prefix unknown tag with <html><body> and remove extra <body>
					newSrc = (tag == null || schema.getElementType(tag)!=null)
						? newSrc : "<html><body>" + newSrc.replaceAll("<body>", "");
				}
			}
			return newSrc;
		}
		
		private static final Pattern p = Pattern.compile("^[ ]*<([a-zA-Z_0-9:]+)[ |/|>]");
		private static String getFirstTag(String s){
			Matcher m = p.matcher(s);
			if (m.find()){
				return m.group(1);
			}
			return null;		
		}
	}
}