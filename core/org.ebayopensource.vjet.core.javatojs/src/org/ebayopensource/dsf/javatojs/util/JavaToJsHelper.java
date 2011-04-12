/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.JarURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

import org.ebayopensource.dsf.javatojs.control.IBuildResourceFilter;
import org.ebayopensource.dsf.javatojs.translate.TranslateCtx;
import org.ebayopensource.dsf.javatojs.translate.UnitTranslator;
import org.ebayopensource.dsf.jsgen.shared.generate.CodeStyle;
import org.ebayopensource.dsf.jsgen.shared.vjo.GeneratorCtx;
import org.ebayopensource.dsf.jsgen.shared.vjo.VjoGenerator;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.dsf.util.JavaSourceLocator;

/**
 * Util class for Java to JS (vjo) translation
 */
public class JavaToJsHelper {

	private static final String JAR_FILE_SEPARATOR = "!";

	public static void getDirectFiles(final URL dir, final List<URL> list,
			final IBuildResourceFilter fileFilter) throws IOException,
			URISyntaxException {
		getFiles(dir, list, fileFilter, false);

	}

	public static void getFiles( URL dir, final List<URL> list,
			final IBuildResourceFilter fileFilter, boolean isRecursive)
			throws IOException, URISyntaxException {
		if (dir == null) {
			return;
		}
		JarURLConnection conn = null;
		// check if it is filesystem or zipfile entry
		if (dir.getProtocol().equalsIgnoreCase("jar")) {

			final String[] splitOutput = dir.toExternalForm().split(
					JAR_FILE_SEPARATOR);
			try {
				conn = (JarURLConnection) (new URL(splitOutput[0]
						+ JAR_FILE_SEPARATOR + "/")).openConnection();

				final JarFile jarFile = conn.getJarFile();
				final String jarFileUrlPrefix = splitOutput[0]
						+ JAR_FILE_SEPARATOR;
				if (splitOutput != null && splitOutput.length > 0) {

					String relativeDirPath = splitOutput[1];
					if (!relativeDirPath.endsWith("/")) {
						relativeDirPath = relativeDirPath + "/";
					}

					if (relativeDirPath.length() > 1
							&& relativeDirPath.startsWith("/")) {
						relativeDirPath = relativeDirPath.substring(1);
					}
					int count = count(relativeDirPath, '/');
					final Enumeration<JarEntry> jarEntries = jarFile.entries();
					while (jarEntries.hasMoreElements()) {
						final JarEntry currentEntry = jarEntries.nextElement();
						if (currentEntry.getName().startsWith(relativeDirPath)
								&& !currentEntry.isDirectory()
								&& !relativeDirPath.equals(currentEntry
										.getName())) {

							if (isRecursive) {
								list.add(new URL(jarFileUrlPrefix + "/"
										+ currentEntry.getName()));
							} else {
								if (count == count(currentEntry.getName(), '/')) {
									list.add(new URL(jarFileUrlPrefix + "/"
											+ currentEntry.getName()));
								}
							}

						}

					}

				}
			}finally{
				if(conn != null){
					conn.getOutputStream().close();
				}
			}
		} else if (dir.getProtocol().equalsIgnoreCase("file")) {

			// File[] files = dir.listFiles(s_fileFilter);
			final File rootFile = new File(dir.toURI());
			final File[] files = rootFile.listFiles();

			if (files != null) {
				for (File currentFile : files) {
					URL url = currentFile.toURI().toURL();
					if (currentFile.isDirectory()) {
						if (isRecursive) {
							getFiles(url, list, fileFilter, isRecursive);
						}
					} else {
						if (fileFilter.accept(url)) {
							list.add(url);
						}
					}

				}
			}

		}

	}

	private static int count(String sourceString, char lookFor) {
		if (sourceString == null) {
			return -1;
		}
		int count = 0;
		for (int i = 0; i < sourceString.length(); i++) {
			final char c = sourceString.charAt(i);
			if (c == lookFor) {
				count++;
			}
		}
		return count;
	}

	public static String genVjo(Class<?> clz) throws IOException {
		return genVjo(clz, null, null);
	}

	public static String genVjo(Class<?> clz, TranslateCtx tCtx,
			GeneratorCtx gCtx) throws IOException {
		if (tCtx == null) {
			tCtx = TranslateCtx.ctx();
		}
		if (gCtx == null) {
			gCtx = new GeneratorCtx(CodeStyle.PRETTY);
		}
		JstType jst = translate(clz, tCtx);
		return toVjo(jst, gCtx);
	}

	public static JstType translate(Class<?> clz, TranslateCtx ctx) throws IOException {
		CompilationUnit cu = parse(clz);
		UnitTranslator translator = new UnitTranslator();
		JstType type = translator.processUnit(cu);
		ctx.getErrorReporter().reportAll();
		return type;
	}

	public static String toVjo(JstType jst, GeneratorCtx ctx) {
		VjoGenerator writer = ctx.getProvider().getTypeGenerator();
		writer.writeVjo(jst);
		return writer.getGeneratedText();
	}

	public static CompilationUnit parse(String clzName) throws IOException {
		// String javaSrc = JavaSourceLocator.getInstance().getSource(clzName);
		// if (javaSrc == null){
		// return null;
		// }
		// return parse(javaSrc.toCharArray());
		String inSource = readFromInputReader(new InputStreamReader(JavaSourceLocator.getInstance().getSourceUrl(clzName).openStream()));
		if (inSource == null) {
			return null;
		}
        ASTParser astParser = AstParserHelper.newParser();
        astParser.setSource(inSource.toCharArray());

        CompilationUnit cu = (CompilationUnit)astParser.createAST(null);
        
        return cu;
	}
	
	public static CompilationUnit toAst(String source){

        ASTParser astParser = AstParserHelper.newParser();
        astParser.setSource(source.toCharArray());

        CompilationUnit cu = (CompilationUnit)astParser.createAST(null);
        
        return cu;
	}
	
//	public static CompilationUnit parse(Class clz){
//		String javaSrc = JavaSourceLocator.getInstance().getSource(clz);
//		if (javaSrc == null){
//			return null;
//		}
//        return parse(javaSrc.toCharArray());
//	}
	
	private static CompilationUnit parse(char[] source){
		ASTParser astParser = AstParserHelper.newParser();
        astParser.setSource(source);
        return (CompilationUnit)astParser.createAST(null);
	}
	
	//
	// Temp
	//
	public static CompilationUnit parse(Class<?> cls) throws IOException {
		String inSource = readFromInputReader(new InputStreamReader(
				JavaSourceLocator.getInstance().getSourceUrl(cls).openStream()));
		ASTParser astParser = AstParserHelper.newParser();
		astParser.setSource(inSource.toCharArray());

		CompilationUnit cu = (CompilationUnit) astParser.createAST(null);

		return cu;
	}
	
	public static String readFromFile(String fileName) {
		FileInputStream  fis = null;
		InputStreamReader isr = null;
		try {
			String characterEncoding = "utf-8";
			fis = new FileInputStream(fileName);
			isr = new InputStreamReader(fis, characterEncoding);
			String code = readFromInputReader(isr);
			return code;
		} catch (Exception e) {
			// e.printStackTrace() ;
			return null;
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) { /* ignore */
				} // NOPMD
			}
			if (isr != null) {
				try {
					isr.close();
				} catch (IOException e) { /* ignore */
				} // NOPMD
			}
		}
	}

	public static String getClassName(final String pkgPath) {
		String clzNameWithExtension = getPackageName(pkgPath);
		return clzNameWithExtension.substring(0, clzNameWithExtension
				.lastIndexOf("."));

	}


	public static String getPkgNameFromSrc(final String src) {
		if (src == null || src.length() == 0) {
			return null;
		}
		ASTParser astParser = AstParserHelper.newParser(false);
		astParser.setSource(src.toCharArray());
		CompilationUnit cu = (CompilationUnit) astParser.createAST(null);
		if (cu == null || cu.getPackage() == null) {
			return null;
		}
		String pkgName = cu.getPackage().getName().getFullyQualifiedName();
		return pkgName;

	}

	private static String getPackageName(final String pkgPath) {
		int index = pkgPath.indexOf("src");
		if (index < 0) {
			return null;
		}
		String pkgName = pkgPath.substring(index + 4);
		pkgName = pkgName.replace("\\", ".");
		pkgName = pkgName.replace("/", ".");

		return pkgName;
	}

	public static String readFromInputReader(InputStreamReader reader) {

		StringBuffer sb = new StringBuffer();
		char buf[] = new char[4096];
		int numRead;

		try {
			do {
				numRead = reader.read(buf, 0, buf.length);
				if (numRead > 0)
					sb.append(buf, 0, numRead);
			} while (numRead >= 0);
		} catch (Exception e) {
			// TODO
			// e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) { /* ignore */
			} // NOPMD
		}

		return sb.toString();
	}

}
