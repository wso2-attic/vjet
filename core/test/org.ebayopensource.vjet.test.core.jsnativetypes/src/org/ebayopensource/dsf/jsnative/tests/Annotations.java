/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsnative.tests;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.ebayopensource.dsf.active.dom.html.AHtmlHelper;
import org.ebayopensource.dsf.jsnative.anno.Alias;
import org.ebayopensource.dsf.jsnative.anno.BrowserSupport;
import org.ebayopensource.dsf.jsnative.anno.Constructor;
import org.ebayopensource.dsf.jsnative.anno.DOMSupport;
import org.ebayopensource.dsf.jsnative.anno.Function;
import org.ebayopensource.dsf.jsnative.anno.JsSupport;
import org.ebayopensource.dsf.jsnative.anno.JstExclude;
import org.ebayopensource.dsf.jsnative.anno.Property;

/**
 * Returns lists of strings of JsNative, Function, Property and SupportedBy
 * annotations.
 * 
 * 
 * 
 */
public class Annotations {

	// return Functions
	public static List<String> getFunctions(Class type) {
		List<String> functions = new ArrayList<String>();

		// for all methods
		Method[] methods = type.getMethods();
		for (Method m : methods) {
			String methodName = m.getName();

			// skip this method due to Rhino, see JsNativeCustomTranslator
			if (methodName.equals("valueOf")) {
				continue;
			}

			boolean isFunction = false;
			// process annotations
			Annotation[] annotations = m.getAnnotations();
			for (Annotation annotate : annotations) {

				// is annotated with @Function
				if (annotate.annotationType().equals(JstExclude.class)) {
					isFunction = false;
					break;
				}
				if (annotate.annotationType().equals(Function.class)) {
					isFunction = true;
					break;
				}
				
			}

			// if Function and not already there, add it
			if (isFunction && !functions.contains(methodName)) {
				// System.out.println("AddF: " + methodName);
				if ("_void".equals(methodName)) {
					methodName = "void";
				}
				functions.add(methodName);
			}
		}

		return functions;
	}

	// return Constructors
	public static List<String> getConstructors(Class type) {
		List<String> constructors = new ArrayList<String>();

		// for all methods
		Method[] methods = type.getDeclaredMethods();
		for (Method m : methods) {
			String methodName = m.getName();

			boolean isConstructor = false;
			// process annotations
			Annotation[] annotations = m.getAnnotations();
			for (Annotation annotate : annotations) {

				// is annotated with @Function
				if (annotate.annotationType().equals(Constructor.class)) {
					isConstructor = true;
					break;
				}
			}

			if (isConstructor) {
				constructors.add(methodName);
			}
		}

		return constructors;
	}

	// return Propertys
	public static List<String> getProperties(Class type) {
		List<String> properties = new ArrayList<String>();

		// for all methods
		Method[] methods = type.getMethods();
		for (Method m : methods) {
			String methodName = m.getName();

			Property prop = m.getAnnotation(Property.class);

			// if property and it's a getter
			// note that every property has one getter and may or may not have a
			// setter
			if (prop != null && methodName.startsWith("get")) {
				String truncatedMethodName;
				// If Alias annotation was provided, get the property name
				// from it.
				Alias alias = m.getAnnotation(Alias.class);
				if (alias != null) {
					truncatedMethodName = alias.value();
				} else if (prop.name() != null && prop.name().length() > 0) {
					truncatedMethodName = prop.name();
				} else {

					// truncate prefix "get" and lowercase first letter (unless
					// all caps)
					// and add to list
					// e.g., "getClosed" is "closed", "getTagName" is "tagName",
					// "getURL" is "URL", not "uRL"
					truncatedMethodName = AHtmlHelper
							.getOriginalPropertyName(methodName.substring(3));
				}

				// if Property and not already there, add it
				if (!properties.contains(truncatedMethodName)) {
					// System.out.println("AddP: " + truncatedMethodName);
					properties.add(truncatedMethodName);
				}
			}
		} // for m

		return properties;
	}

	// TODO: get values too
	// return SupportedBy
	public static List<String> getSupportedBy(Class type) {
		List<String> supportedBys = new ArrayList<String>();

		// for all methods
		Method[] methods = type.getDeclaredMethods();
		for (Method m : methods) {
			String methodName = m.getName();

			boolean isSupportedBy = false;
			boolean isProperty = false;
			boolean isFunction = false;
			// process annotations
			Annotation[] annotations = m.getAnnotations();
			for (Annotation annotate : annotations) {

				// is annotated with SupportedBy
				if (annotate.annotationType().equals(BrowserSupport.class)
						|| annotate.annotationType().equals(
								DOMSupport.class)
						|| annotate.annotationType().equals(
								JsSupport.class)) {
					isSupportedBy = true;

					// is also annotated with Property?
					for (Annotation a : annotations) {
						if (a.annotationType().equals(Property.class)) {
							isProperty = true;
							Property prop = (Property) a;
							// If Alias annotation was provided, get the
							// property name
							// from it.
							Alias alias = m.getAnnotation(Alias.class);
							if (alias != null) {
								methodName = alias.value();
							} else if (prop.name() != null
									&& prop.name().length() > 0) {
								methodName = prop.name();
							} else {
								methodName = AHtmlHelper
										.getOriginalPropertyName(methodName
												.substring(3));
							}
							break;
						}
					}
					// is also annotated with Function?
					for (Annotation a : annotations) {
						if (a.annotationType().equals(JstExclude.class)) {
							isFunction = false;
							break;
						}
						
						
						if (a.annotationType().equals(Function.class)) {
							isFunction = true;
							break;
						}
					}
					break;
				}
			} // for annotate

			if (isSupportedBy && (isProperty || isFunction)) {
				// if SupportedBy and not already there, add it
				if (!supportedBys.contains(methodName)) {
					// System.out.println("AddSB2: " + methodName);
					if ("_void".equals(methodName)) {
						methodName = "void";
					}
					supportedBys.add(methodName);
				}
			}
		} // for m

		return supportedBys;
	}

	private static String getTruncatedMethodName(String methodName) {
		// TODO Auto-generated method stub
		return null;
	}

	// from ADomMeta.java
	// private static boolean isNameAllCapitalized(String name) {
	// if ("getNaN".equals(name)) {
	// return true;
	// }
	// char [] chars = name.toCharArray();
	// for (char ch : chars) {
	// if (Character.isLetter(ch) && !Character.isUpperCase(ch)) {
	// return false;
	// }
	// }
	// return true;
	// }

}
