/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.test.utils.vjo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import junit.framework.AssertionFailedError;

import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.junit.Assert;

import org.ebayopensource.af.common.error.ErrorList;
import org.ebayopensource.af.common.error.ErrorObject;
import org.ebayopensource.dsf.common.trace.TraceAttr;
import org.ebayopensource.dsf.common.trace.event.TraceId;
import org.ebayopensource.dsf.javatojs.control.BaseTranslationInitializer;
import org.ebayopensource.dsf.javatojs.control.ITranslationInitializer;
import org.ebayopensource.dsf.javatojs.control.TranslationController;
import org.ebayopensource.dsf.javatojs.trace.ITranslateTracer;
import org.ebayopensource.dsf.javatojs.trace.TranslateError;
import org.ebayopensource.dsf.javatojs.trace.TranslationTraceId;
import org.ebayopensource.dsf.javatojs.translate.TranslateCtx;
import org.ebayopensource.dsf.javatojs.translate.TranslateInfo;
import org.ebayopensource.dsf.javatojs.util.AstParserHelper;
import org.ebayopensource.dsf.jsgen.shared.generate.CodeStyle;
import org.ebayopensource.dsf.jsgen.shared.generate.JsrGenerator;
import org.ebayopensource.dsf.jsgen.shared.generate.NativeJsProxyGenerator;
import org.ebayopensource.dsf.jsgen.shared.vjo.GeneratorCtx;
import org.ebayopensource.dsf.jsgen.shared.vjo.VjoGenerator;
import org.ebayopensource.dsf.jst.declaration.JstCache;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.dsf.util.JavaSourceLocator;
import org.ebayopensource.dsf.logger.LogLevel;

public class TestHelper {

	long m_total = 0;

	long m_start = 0;

	boolean m_logTime = false;

	boolean m_logCode = false;

	private Class<?> m_javaCls;

	private TranslateCtx m_translatorCtx;
	private static String vjoFileExt = ".vjo";

	public static final File CODEGEN_DIR = new File("CODEGENDIR");
	static {
		CODEGEN_DIR.mkdirs();
		final String currentSourcePth = System.getProperty("java.source.path");
		System.setProperty("java.source.path", CODEGEN_DIR.getAbsolutePath()
				+ java.io.File.pathSeparator + currentSourcePth);

	}

	// 
	// Constructor
	//
	public TestHelper(Class<?> javaClass, final TranslateCtx ctx) {
		m_translatorCtx = ctx;
		m_javaCls = javaClass;

	}

	public JstType translate() {

		TranslationController controller = getTranslationController(null);
		JstType type = controller.targetedTranslation(m_javaCls);

		logTime("Translate");
		m_translatorCtx.getErrorReporter().reportAll();
		return type;
	}

	public static TranslationController getTranslationController(
			ITranslationInitializer initializer) {
		return new TranslationController(initializer);
	}

	public static String toVjo(final JstType type, CodeStyle style) {

		if (type == null) {
			return null;
		}
		GeneratorCtx genCtx = new GeneratorCtx(CodeStyle.PRETTY);
		genCtx.setStyle(style);
		VjoGenerator writer = genCtx.getProvider().getTypeGenerator();
		writer.writeVjo(type);
		return writer.getGeneratedText();
	}

	public static String toJsr(final JstType type, CodeStyle style) {

		if (type == null) {
			return null;
		}
		StringWriter buffer = new StringWriter();
		JsrGenerator writer = new JsrGenerator(new PrintWriter(buffer), style);
		writer.writeJsr(type);
		return buffer.toString();
	}

	public String toNativeProxy(final JstType type, CodeStyle style) {

		StringWriter buffer = genNativeProxy(type, style);
		logCode("\n============================== NP ===============================\n"
				+ buffer
				+ "\n------------------------------------------------------------------");

		return buffer.toString();
	}

	public String getExpectedVjo() throws MalformedURLException {
		return readFromFile(getSourceFileName(m_javaCls, ".vjo"));
	}

	public String getExpectedJsr() throws MalformedURLException {
		return readFromFile(getSourceFileName(m_javaCls, ".jsr"));
	}

	public void writeVjo(String vjo) throws MalformedURLException,
			URISyntaxException {
		if (vjo == null) {
			return;
		}
		writeToFile(getOutputFileName(m_javaCls, vjoFileExt), vjo);
	}

	public static void writeVjo(Collection<Java2JstInfo> jav2JstInfoColl,
			boolean writeVjo, String fileExt) throws ClassNotFoundException {

		for (Java2JstInfo infoObj : jav2JstInfoColl) {
			final JstType type = infoObj.getJstType();
			try {
				System.out.println("TYPE NAME : " + type.getName());
				if (writeVjo) {
					final File vjoFile = getOutputFileName(Class.forName(type
							.getName()), fileExt);
					writeToFile(vjoFile, infoObj.getExpectedVjo());
					infoObj.setVjoFilename(vjoFile.toURI().toURL());
				}

			} catch (AssertionFailedError e) {
				throw e;
			} catch (Throwable e) {
				System.err.println("Error During Vjo Writing: "
						+ type.getName() + "\n" + e.getMessage());
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
	}

	public static void writeJsr(Collection<Java2JstInfo> jstTypeInfos,
			boolean writeJsr, String fileExt) throws ClassNotFoundException {

		for (Java2JstInfo infoObj : jstTypeInfos) {
			final JstType type = infoObj.getJstType();
			try {
				System.out.println("TYPE NAME : " + type.getName());
				if (writeJsr) {
					final File tempJsrFile = getOutputFileName(Class
							.forName(type.getName()), fileExt);
					final File jsrFile = new File(tempJsrFile.getParentFile(),
							tempJsrFile.getName().replace(".java", "Jsr.java"));
					writeToFile(jsrFile, infoObj.getExpectedJsr());
					infoObj.setJsrFilename(jsrFile.toURI().toURL());
				}

			} catch (AssertionFailedError e) {
				throw e;
			} catch (Throwable e) {
				System.err.println("Error During Jsr Writing: "
						+ type.getName() + "\n" + e.getMessage());
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}

	}

	public void writeJsr(String type) throws MalformedURLException,
			URISyntaxException {
		writeToFile(getOutputFileName(m_javaCls, ".jsr"), type);
	}

	public void writeJsrDotJava(String type) throws MalformedURLException,
			URISyntaxException {
		writeToFile(getOutputFileName(m_javaCls, "Jsr.java"), type);
	}

	public void writeDotJs(String vjo) throws MalformedURLException,
			URISyntaxException {
		if (vjo == null) {
			return;
		}
		writeToFile(getOutputFileName(m_javaCls, ".js"), vjo);
	}

	public static void writeVjo(String srcName, String vjo)
			throws MalformedURLException, URISyntaxException {
		try {
			writeToFile(getOutputFileName(Class.forName(srcName), ".vjo"), vjo);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String getExpectedVjo(String srcName) {
		try {
			return readFromFile(getSourceFileName(Class.forName(srcName),
					".vjo"));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static void testType(Class<?> srcType,
			ITranslationInitializer initializer) {
		try {
			testType(srcType, getTranslationController(initializer), false,
					false, true);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void testType(Class<?> srcType,
			ITranslationInitializer initializer, boolean persist,
			boolean validate) {
		try {
			testType(srcType, getTranslationController(initializer), persist,
					persist, validate);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void testType(Class<?> srcType,
			ITranslationInitializer initializer, boolean persistVjo,
			boolean persistJsr, boolean validate) {
		try {
			testType(srcType, getTranslationController(initializer),
					persistVjo, persistJsr, validate);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void testType(Class<?> srcType,
			TranslationController controller) {
		try {
			testType(srcType, controller, false, false, true);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static final TraceId ID = TranslationTraceId.TEST;

	public static void testType(Class<?> srcType,
			TranslationController controller, boolean persistVjo,
			boolean persistJsr, boolean validate) throws MalformedURLException,
			URISyntaxException {

		// Reset
		// LibManager.getInstance().clear();
		JstCache.getInstance().clear();
		// TranslateCtx.createCtx();
		getCtx().setTranslateInfo(new HashMap<JstType, TranslateInfo>());

		// Start
		getTracer().startGroup(ID,
				new TraceAttr("name", srcType.getSimpleName()));
		// getLogger().log(Level.INFO, "Started testing type " +
		// srcType.getSimpleName());

		TestHelper helper = new TestHelper(srcType, getCtx());
		String vjoActual = "";

		JstType jstType = controller.targetedTranslation(srcType);

		vjoActual = TestHelper.toVjo(jstType, CodeStyle.PRETTY);

		String jsrActual = "";
		jsrActual = TestHelper.toJsr(jstType, CodeStyle.PRETTY);

		printErrors(controller.getErrors());
		printExceptions(controller.getExceptions());
		getTracer().endGroup(ID);

		if (persistVjo) {
			helper.writeVjo(vjoActual);
		}

		if (persistJsr) {
			helper.writeJsr(jsrActual);
		}

		// Assert
		if (validate) {
			String jsrExpectedResult = helper.getExpectedJsr();
			String vjoExpectedResult = helper.getExpectedVjo();
			Assert.assertEquals("Expected Result : \n" + vjoExpectedResult
					+ "\nActual Result : \n" + vjoActual, vjoExpectedResult,
					vjoActual);

			Assert.assertEquals("Expected Result : \n" + jsrExpectedResult
					+ "\nActual Result : \n" + jsrActual, jsrExpectedResult,
					jsrActual);
		}
		// getLogger().log(Level.INFO, "Ended testing type " +
		// srcType.getSimpleName());
	}

	public static List<JstType> vjoOnDemand(Class<?> srcType,
			TranslationController controller, boolean isOnDemandTrans) {

		List<JstType> jstTypes = new ArrayList<JstType>();

		if (isOnDemandTrans)
			jstTypes = controller.onDemandTranslation(srcType);
		else {
			jstTypes.add(controller.targetedTranslation(srcType));

		}
		return jstTypes;
	}

	public static void printErrors(List<TranslateError> errors) {
		for (TranslateError e : errors) {
			if (e.getLevel() == LogLevel.ERROR) {
				System.out.println(e.toString());
			}
		}
	}

	public static void printExceptions(Map<JstType, List<Throwable>> exceptions) {
		for (Entry<JstType, List<Throwable>> entry : exceptions.entrySet()) {
			for (Throwable t : entry.getValue()) {
				System.out.println("Exception when translating "
						+ entry.getKey().getName() + ": ");
				t.printStackTrace();
			}
		}
	}

	private static ITranslationInitializer s_initializer;

	public static ITranslationInitializer getInitializer() {

		if (s_initializer == null) {
			s_initializer = new BaseTranslationInitializer() {
				public void initialize() {
					TranslateCtx.ctx().enableParallel(true).enableTrace(true);
				}
			};

		}
		return s_initializer;
	}

	public static TranslateCtx getCtx() {
		return TranslateCtx.ctx();
	}

	private static ITranslateTracer getTracer() {
		return getCtx().getTraceManager().getTracer();
	}

	// private static Logger getLogger() {
	// return getCtx().getTraceManager().getLogger();
	// }

	//
	// Protected
	//
	protected CompilationUnit parse(Class<?> cls) throws MalformedURLException {
		String inSource = readFromFile(getSourceFileName(cls, ".java"));
		logTime("Read File");

		logCode("\n============================== Java ==============================\n"
				+ inSource
				+ "------------------------------------------------------------------");

		ASTParser astParser = AstParserHelper.newParser();
		astParser.setSource(inSource.toCharArray());

		CompilationUnit cu = (CompilationUnit) astParser.createAST(null);
		logTime("Parse");

		return cu;
	}

	protected StringWriter genNativeProxy(JstType type, CodeStyle style) {

		StringWriter buffer = new StringWriter();
		NativeJsProxyGenerator writer = new NativeJsProxyGenerator(
				new PrintWriter(buffer), style);
		writer.writeProxy(type);
		logTime("Gen Native Proxy");

		return buffer;
	}

	//
	// Temp
	//
	public static File getOutputFileName(Class<?> cls, String ext)
			throws MalformedURLException, URISyntaxException {

		final URL sourceFile = JavaSourceLocator.getInstance()
				.getSourceUrl(cls);

		if (sourceFile.getProtocol().equalsIgnoreCase("jar")) {

			return new File(CODEGEN_DIR, cls.getName().replace('.',
					File.separatorChar)
					+ ext);

		} else if (sourceFile.getProtocol().equalsIgnoreCase("file")) {
			return new File(new URL(sourceFile.toExternalForm().replace(
					".java", ext)).toURI());
		} else {
			return null;
		}
	}

	public static URL getSourceFileName(Class<?> cls, String ext)
			throws MalformedURLException {

		final URL sourceFile = JavaSourceLocator.getInstance()
				.getSourceUrl(cls);
		return new URL(sourceFile.toExternalForm().replace(".java", ext));
	}

	public static String readFromFile(URL fileName) {

		try {
			final StringBuffer sb = new StringBuffer();
			final char buf[] = new char[4096];
			int numRead;
			final InputStreamReader isr = new InputStreamReader(fileName
					.openStream(), "utf-8");

			do {
				numRead = isr.read(buf, 0, buf.length);
				if (numRead > 0)
					sb.append(buf, 0, numRead);
			} while (numRead >= 0);

			isr.close();

			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	static protected void writeToFile(File fileName, String code) {

		if (!fileName.getParentFile().exists()) {
			fileName.getParentFile().mkdirs();
		}
		try {
			String characterEncoding = "utf-8";

			FileOutputStream fis;
			OutputStreamWriter isr;
			char buf[] = code.toCharArray(); // new char[4096];

			fis = new FileOutputStream(fileName);
			isr = new OutputStreamWriter(fis, characterEncoding);

			isr.write(buf, 0, buf.length);
			;

			isr.close();
			fis.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void logCode(Object msg) {
		if (m_logCode) {
			System.out.println(msg.toString());
		}
	}

	protected void logTime(String label) {
		if (m_logTime) {
			long cur = System.currentTimeMillis();
			long elapse = cur - m_start;
			m_start = cur;
			m_total += elapse;
			System.out.println("--> " + label + " = " + elapse
					+ " (acummulated: " + m_total + ")");
		}
	}

	public boolean hasErrorsReported() {
		return m_translatorCtx.getErrorReporter().hasErrors();
	}

	public String getErrorsReported() {
		final ErrorList errors = (ErrorList) m_translatorCtx.getErrorReporter()
				.getErrors();
		final StringBuilder errorBuilder = new StringBuilder();
		ListIterator<?> iter = errors.listIterator();
		while (iter.hasNext()) {
			ErrorObject eo = (ErrorObject) iter.next();
			errorBuilder.append(eo.getParameters().getValueByName("message"));
			errorBuilder.append(", on line : ");
			errorBuilder.append(eo.getParameters().getValueByName("line"));
			errorBuilder.append("\n");
		}
		return errorBuilder.toString();
	}

	public static void validateAsserts(String javaSrc, String jsSrc) {
		int count1 = 0;
		int count2 = 0;

		int start = javaSrc.indexOf("class");
		if (start == -1) {
			return;
		}
		javaSrc = javaSrc.substring(javaSrc.indexOf("class"), javaSrc.length());

		int index = javaSrc.indexOf("@AVjoExclude");
		if (index > -1) {
			String filtered = javaSrc.substring(0, index);
			javaSrc = javaSrc.substring(index, javaSrc.length());
			filtered += javaSrc.substring(javaSrc.indexOf("}"), javaSrc
					.length());
			javaSrc = filtered;
		}

		String[] regexs = new String[] { "\n.*assertEquals", "\n.*assertTrue",
				"\n.*assertFalse", }; // "\n.*fail" };

		for (int i = 0; i < regexs.length; i++) {
			Pattern p = Pattern.compile(regexs[i]);
			Matcher m = p.matcher(javaSrc); // get a matcher object
			while (m.find()) {
				int assertIndex = m.group().indexOf("assert");
				int commentTest1 = m.group().indexOf("//");
				int commentTest2 = m.group().indexOf("/\\*");
				int commentTest3 = m.group().indexOf("\\*/");

				if (commentTest1 == -1 || commentTest1 > assertIndex) {
					if (commentTest2 == -1 || commentTest2 > assertIndex) {
						if (commentTest3 == -1 || commentTest3 < assertIndex) {
							count1++;
						}
					}
				}

				// need to add when fail is passed.
			}

			Pattern p2 = Pattern.compile(regexs[i]);
			Matcher m2 = p2.matcher(jsSrc); // get a matcher object
			while (m2.find()) {
				count2++;
			}

		}
		if (count1 == count2) {
			return;
		}
		throw new AssertionFailedError("The number of asserts in Java file ["
				+ count1 + "] are not the same as translated Js file[" + count2
				+ "]!");
	}

	public static void setVjoFileExt(String ext) {
		vjoFileExt = ext;
	}

}
