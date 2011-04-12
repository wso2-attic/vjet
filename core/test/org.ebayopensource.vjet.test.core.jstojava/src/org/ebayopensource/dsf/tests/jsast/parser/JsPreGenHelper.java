/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.tests.jsast.parser;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import org.ebayopensource.dsf.common.exceptions.DsfRuntimeException;
import org.mozilla.mod.javascript.Kit;

/**
 * Provides helper functions for generating a package specification from a
 * JavaScript file. This class prvides help to the
 * org.ebayopensource.dsf.jstojava.codgen#JsPackageSpecGenerator.
 * 
 * 
 * 
 * 
 */

public class JsPreGenHelper {

//	private static final String VJO3 = "VJO3";
//	private static final String VJO23 = "VJO23";
//	private static final String VJO22 = "VJO22";
	

	/**
	 * Takes a file and converts it to a string.
	 * 
	 * @param f
	 *            the file
	 * @return The string or an IOException indicating the conversion failed..
	 */
	public static String file2String(File f) {
		String s = null;
		try {
			s = url2String(f.toURL());
		} catch (IOException e) {
			throw new DsfRuntimeException("could not convert file to string",
					e);
		}
		return s;
	}

	public static String url2String(URL url) {
		String s = null;
		InputStreamReader input;
		try {
			input = new InputStreamReader(url.openStream());
			s = Kit.readReader(input);
			input.close();
		} catch (IOException e) {
			throw new DsfRuntimeException("could not convert file to string",
					e);
		}
		return s;
	}
	
	/**
	 * Extracts annotations from JavaScript.
	 * 
	 * @param jsContents
	 *            a string containing the JavaScript
	 * @return JavaScript annotations
	 * 
	 * TODO jce look at consolidating getAllAnnotations and
	 * getMetaFromAnnotations
	 */
//	public static List<JsAnnotation> getAllAnnotations(String jsContents) {
//		List<JsAnnotation> annotations = new ArrayList<JsAnnotation>();
//		String[] lines = jsContents.split("\n");
//		for (String line : lines) {
//
//			String[] annotation = parseLine(line);
//
//			if (annotation == null) {
//				JsAnnotation a = null;
//				if (line.indexOf("vjo.ctype(") != -1 ||
//					line.indexOf("vjo.itype(") != -1 ||
//					line.indexOf("vjo.atype(") != -1 ||
//					line.indexOf("vjo.etype(") != -1 ||
//					line.indexOf("vjo.otype(") != -1 ||
//					line.indexOf("vjo.mtype(") != -1) {
//					a = new JsAnnotation(VJO3, "TRUE");
//					annotations.add(a);
//				}
//				
//				if (line.indexOf("vjo.type(") != -1) {
//					a = new JsAnnotation(VJO23, "TRUE");
//					annotations.add(a);
//				}
//				
//				// TODO: Remove check for VJO22 annotations - hsinha
//				if (line.indexOf("vjo.lang.Package(") != -1) {
//					a = new JsAnnotation(VJO22, "TRUE");
//					annotations.add(a);
//				}
//
//				continue;
//			}
//
//			JsAnnotation a = null;
//			if (annotation.length == 2) {
//				a = new JsAnnotation(annotation[0], annotation[1]);
//			} else {
//				a = new JsAnnotation(annotation[0], null);
//			}
//
//			annotations.add(a);
//
//			/*
//			// TODO: Remove this redundant check as vjo.type line should never have "@" symbol and thus
//			// this code will never be executed. - hsinha
//			if (line.indexOf("vjo.type(") != -1) {
//				a = new JsAnnotation(VJO23, "TRUE");
//				annotations.add(a);
//			}
//
//			// TODO: Remove check for VJO22 annotations  - hsinha
//			if (line.indexOf("vjo.lang.Package(") != -1) {
//				a = new JsAnnotation(VJO22, "TRUE");
//				annotations.add(a);
//			}
//			*/
//		}
//
//		return annotations;
//	}

//	/**
//	 * Creates a JsMeta object from a JavaScript file. Sets the package name,
//	 * class name, and the VjO attribute to true.
//	 * 
//	 * @param jsFile
//	 *            a JavaScript file
//	 * @return an instance of JsMeta
//	 */
//	public static JsMeta getMetaFromAnnotations(String jsFile) {
//		//String classN = url.getName().substring(0, url.getName().indexOf("."));
//
//		List<JsAnnotation> annotations = getAllAnnotations(jsFile);
//		JsMeta meta = new JsMeta();
//		for (JsAnnotation note : annotations) {
//			if (note.getName().indexOf("Package") != -1) {
//				meta.setPackage(note.getValue());
//			}
//
//			if (note.getName().indexOf(VJO23) != -1) {
//				meta.setVjo(true);
//			}
//			
//			if (note.getName().indexOf(VJO3) != -1) {
//				meta.setVjo3(true);
//			}
//		}
//		return meta;
//	}

	private static String[] parseLine(String jsDocEntry) {
		int indexOfAt = jsDocEntry.indexOf("@");
		if (indexOfAt != -1) {
			String entryTrimmed = jsDocEntry.substring(indexOfAt);
			String[] entry = entryTrimmed.split(" ");
			return entry;
		}
		return null;
	}

}
