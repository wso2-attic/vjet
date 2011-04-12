/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.tests.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/*
 * Class for generating Input to the Translator
 * for all primitive wrapper classes
 * 
 */
public class SrcGenerator {

	private static final String S_EQUAL = " = ";

	private static final String S_NEWLINE = "\n";

	private static final String S_SEMICOLON = ";";

	public static String a() {
		System.out.println("test");
		return null;
	}

	// ^////[A-Z, 0-9,_]*
	public static void main(String[] args) {

		try {

			BufferedWriter out = null;
			// Boolean.class ,String.class,Integer.class
			Class[] clazzArr = { Byte.class, Short.class, Integer.class,
					Long.class, Float.class, Double.class, Character.class,
					Boolean.class, String.class };
			for (Class clazz : clazzArr) {
				String clzzName = clazz.getSimpleName().toLowerCase();
				FileWriter fstream = new FileWriter(clzzName + "_out.txt");
				out = new BufferedWriter(fstream);
				outConstructor(clazz, out);
				outFields(clazz, out);
				outMethod(clazz, out);

				// Close the output stream
				out.close();
			}

		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	public static void outFields(Class clazz, BufferedWriter out)
			throws Exception {
		log(clazz.getSimpleName().toString() + " : Started........ outFields()");
		String clzzName = clazz.getSimpleName().toLowerCase();
		out.write("public void " + clzzName + "WrapperFields(){" + S_NEWLINE);
		Field[] fieldArr = clazz.getFields();
		int fCount = 0;
		for (Field field : fieldArr) {
			out.write("// --" + field.getName() + S_NEWLINE);
			out.write(field.getGenericType() + " v_" + clzzName + fCount
					+ S_EQUAL + field.toGenericString() + S_SEMICOLON
					+ S_NEWLINE);
			fCount++;
		}
		out.write("}" + S_NEWLINE);
		log(clazz.getSimpleName().toString()
				+ " : Completed........ outFields()");

	}

	public static void outMethod(Class clazz, BufferedWriter out)
			throws Exception {

		log(clazz.getSimpleName().toString() + " : Started........ outMethod()");
		String clzzName = clazz.getSimpleName().toLowerCase();
		out.write("public void " + clzzName + "WrapperMthd(){\n ");
		Method[] mthdArr = clazz.getMethods();
		for (Method mthd : mthdArr) {

			out.write("// --" + mthd.getName());
			if ((Modifier.toString(mthd.getModifiers()).indexOf("static")) != -1)
				out.write("  (static)  ");

			out.write(S_NEWLINE);

			if (mthd.getGenericReturnType().toString().equalsIgnoreCase("void"))
				out.write(mthd + S_SEMICOLON + S_NEWLINE);

			else {
				out.write(mthd.getReturnType() + " v_" + mthd.getName()
						+ S_EQUAL + mthd.toGenericString() + S_SEMICOLON
						+ S_NEWLINE);

			}
		}
		out.write("}" + S_NEWLINE);
		log(clazz.getSimpleName().toString()
				+ " : Completed........ outMethod()");

	}

	public static void outConstructor(Class clazz, BufferedWriter out)
			throws Exception

	{
		log(clazz.getSimpleName().toString()
				+ " : Started............ outConstructor()");
		String clzzName = clazz.getSimpleName().toLowerCase();
		out.write("public void " + clzzName + "Constructors(){ " + S_NEWLINE);
		Constructor[] constructArr = clazz.getConstructors();
		int count = 0;
		out.write("// --" + "Unitialised" + S_NEWLINE);
		out.write(clazz.getSimpleName() + "  v_" + clzzName + "Obj"
				+ S_SEMICOLON + S_NEWLINE);
		out.write("// --" + "NULL" + S_NEWLINE);
		out.write(clazz.getSimpleName() + "  v_" + clzzName + "Obj" + count
				+ " =  null" + S_SEMICOLON + S_NEWLINE);

		for (Constructor construct : constructArr) {

			count++;
			out.write("// --" + construct.toGenericString() + S_NEWLINE);
			out.write(clazz.getSimpleName() + "  v_" + clzzName + "Obj" + count
					+ " =  new " + removeModifier(construct.toGenericString())
					+ S_SEMICOLON + S_NEWLINE);

		}
		out.write("}" + S_NEWLINE);
		log(clazz.getSimpleName().toString()
				+ " : Complted....... outConstructor()");

	}

	public static void log(String str) {

		System.out.println(str);

	}
	
	/**
	 * @TODO 
	 */
	public static String removeModifier(String str) {

		String[] tokenArr = str.trim().split(" ");
		String result = "";
		for (String token : tokenArr) {
			if (token.equalsIgnoreCase("public"))
				token = "";
			result += token;

		}

		return result;

	}

}
