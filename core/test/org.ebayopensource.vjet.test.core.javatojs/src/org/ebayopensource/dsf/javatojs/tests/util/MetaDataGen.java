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
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;

public class MetaDataGen {

	public static void main(String[] args) {

		try {
			FileWriter fstream = new FileWriter("string_out.txt");
			BufferedWriter out = new BufferedWriter(fstream);
			//Boolean.class ,String.class,Integer.class
			Class[] clazzArr = { String.class };
			for (Class clazz : clazzArr) {
				// Get Fields 
				out.write("FIELDFIELD\tFIELDFIELD\n");
				Field[] fieldArr = clazz.getFields();
				for (Field field : fieldArr)
					out.write(clazz.getSimpleName() + "\t" + field + "\n");

				//Get Methods
				out.write("METHODMETHOD\tMETHODMETHOD\n");
				Method[] mthdArr = clazz.getMethods();
				String[] strArr = new String[mthdArr.length];
                int i =0;
				for (Method mthd : mthdArr)
				{	
					out.write(clazz.getSimpleName() +"\t"+ mthd.getName() +"\t" + mthd + "\n");
                    strArr[i]= mthd.getName()+"\t" + clazz.getSimpleName() +"\t" + mthd + "\n";
                    i++;
				}
				
				Arrays.sort(strArr, new StringComparator("ba"));
				System.out.println(Arrays.deepToString(strArr)); //
				
			}
			//		Close the output stream
			out.close();
		} catch (SecurityException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	

	static class StringComparator implements Comparator<String>{
		String str;

		public StringComparator(String subs){
		str = subs;
		}

		public int compare(String o1, String o2){

		return o1.indexOf(str) - o2.indexOf(str);
		}
		}
	
	
	
}
