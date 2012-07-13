/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.coverage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.ebayopensource.dsf.common.FileUtils;

public class VjBootStrapInstrumentor {
	public static void main(String[] args) throws UnsupportedEncodingException,
			FileNotFoundException, IOException {
		String fileName = "D:/ccviews/d_sjc_alpatel1_gort589_2/v4darwin/DSFVjoDef/src/org.ebayopensource.vjo/VjBootstrap_3.js";
		String bootstrap = FileUtils.readFile(fileName, "cp1252");
		bootstrap = bootstrap.replaceAll("\r\n", "\n");
		String[] bootSplit = bootstrap.split("\n");

		int count = 0;
		String newBootStrap = "";
		boolean inFunc = false;
		boolean returnBefore = false;
		for (int i = 0; i < bootSplit.length; i++) {
			System.out.println(bootSplit[i]);
			if ((bootSplit[i].endsWith(";") || bootSplit[i].endsWith("){") || bootSplit[i]
					.endsWith(") {"))
					&& (!bootSplit[i].contains("function")
							&& !bootSplit[i].endsWith("};")
							&& !bootSplit[i].contains("else") && !bootSplit[i]
							.contains("});"))) {

				String line = bootSplit[i-1].replace("\t", "");
				line = line.trim();
				if (line.startsWith("if") || line.startsWith("else")) {
					if (line.endsWith(")")) {
						newBootStrap = newBootStrap
								+ "{\nalap.cov.jsCoverage(\"bootstrap_3\", \"n/a\",\""
								+ count + "\");\n" + bootSplit[i] + "\n}";
					} else {
						newBootStrap = newBootStrap
								+ "\nalap.cov.jsCoverage(\"bootstrap_3\", \"n/a\",\""
								+ count + "\");\n" + bootSplit[i];
					}
				} else {

					newBootStrap = newBootStrap
							+ "\nalap.cov.jsCoverage(\"bootstrap_3\", \"n/a\",\""
							+ count + "\");\n" + bootSplit[i];
				}
				count++;
			}
			// if (bootSplit[i].contains("function")&& i > 2) {
			// inFunc = true;
			// }
			// if (bootSplit[i].contains("},")) {
			// inFunc = false;
			// }
			// if (!bootSplit[i].contains("if(") && !bootSplit[i].contains("if
			// (")
			// && inFunc && !bootSplit[i].endsWith(",")
			// && !bootSplit[i].endsWith(", ")
			// && returnBefore == false
			// && !bootSplit[i].contains("else")
			// && !bootSplit[i].contains(": function")) {
			// newBootStrap = newBootStrap
			// + "\nalap.cov.jsCoverage(\"bootstrap_3\", \"n/a\",\""
			// + count + "\");\n" + bootSplit[i];
			// count++;
			// if(bootSplit[i].contains("return")){
			// returnBefore = true;
			// }else{
			// returnBefore = false;
			// }
			else {
				System.out.println(bootSplit[i]);
				newBootStrap = newBootStrap + "\n" + bootSplit[i];
				returnBefore = false;
			}

		}

		System.out.println(newBootStrap);
		FileUtils.writeFile(fileName, newBootStrap, "cp1252");
	}
}
