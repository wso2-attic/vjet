/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.test.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.junit.Assert;

import org.ebayopensource.af.common.error.ErrorList;
import org.ebayopensource.af.common.error.ErrorObject;
import org.ebayopensource.dsf.common.trace.TraceAttr;
import org.ebayopensource.dsf.common.trace.event.TraceId;
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

	private Class m_javaCls;

	private ITranslationInitializer m_initializer;

	private GeneratorCtx m_generatorCtx;
	
	private static String vjoFileExt = ".vjo";
	private static String jsrFileExt = ".jsr";
	public static final String NEWLINE = "\r\n";
	
	private static final File CODEGENDIR = new File("CODEGEN");
	static{
		CODEGENDIR.mkdirs();
		try {
			System.out.println("CodeGen Dir : "+CODEGENDIR.getCanonicalPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	// 
	// Constructor
	//
	public TestHelper(Class javaClass, final ITranslationInitializer initializer) {
		m_initializer = initializer;
		m_javaCls = javaClass;
		m_generatorCtx = new GeneratorCtx(CodeStyle.PRETTY);
		m_generatorCtx.setNewline(NEWLINE);
	}
	
	public static void main(String[] args) {
		try {
			Class c = Class.forName(args[0]);
			TestHelper th = new TestHelper(c,null);
			JstType type = th.translate();
			System.out.println(th.toVjo(type, CodeStyle.PRETTY));
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

	//
	// API
	//
	public TestHelper logTime(boolean logTime) {
		m_logTime = logTime;
		if (m_logTime) {
			m_start = System.currentTimeMillis();
		}
		return this;
	}

	public TestHelper logCode(boolean logCode) {
		m_logCode = logCode;
		return this;
	}

	public JstType translate() {

		TranslationController controller = getTranslationController(m_initializer);
		JstType type = controller.targetedTranslation(m_javaCls);

		logTime("Translate");
		getCtx().getErrorReporter().reportAll();
		return type;
	}

	public static TranslationController getTranslationController(ITranslationInitializer configInitializer) {
		return new TranslationController(configInitializer);
	}

	public String toVjo(final JstType type, CodeStyle style) {

		if (type == null) {
			return null;
		}

		m_generatorCtx.setStyle(style);

		String buffer = gentype(type);
		logCode("\n============================== type ===============================\n"
				+ buffer
				+ "\n------------------------------------------------------------------");

		return buffer.toString();
	}

	public String toJsr(final JstType type, CodeStyle style) {

		StringWriter buffer = genJsr(type, style);
		logCode("\n============================== Jsr ===============================\n"
				+ buffer
				+ "\n------------------------------------------------------------------");

		return buffer.toString();
	}

	public String getExpectedVjo() {
		return JavaSourceLocator.getInstance().getSource(JavaSourceLocator.getInstance().getSourceUrl(m_javaCls.getName(), ".vjo"));
//		return JavaSourceLocator.getInstance().getVjoSource(m_javaCls);
//		return readFromFile(getFileName(m_javaCls, ".vjo"));
	}

	public String getExpectedJsr() {
		return JavaSourceLocator.getInstance().getSource(JavaSourceLocator.getInstance().getSourceUrl(m_javaCls.getName(), ".jsr"));
//		return JavaSourceLocator.getInstance().getJsrSource(m_javaCls);
//		return readFromFile(getFileName(m_javaCls, ".jsr"));
	}

	public void writeVjo(String vjo) {
		if (vjo == null) {
			return;
		}
		writeToFile(getFileName(m_javaCls, vjoFileExt), vjo);
	}
	
	public static void writeVjo(List <JstType> jstTypes) throws ClassNotFoundException {
		
		String vjoActual = null;
		String text = "";
		for(JstType type : jstTypes){
			
			System.out.println(type.getName());
			text = text + "\n" + type.getName();
			TestHelper helper = new TestHelper(null, null);
			vjoActual = helper.toVjo(type, CodeStyle.PRETTY);
			writeToFile(getFileName(Class.forName(type.getName()), vjoFileExt), vjoActual);
		}

	}

	public void writeJsr(String type) {
		writeToFile(getFileName(m_javaCls, jsrFileExt), type);
	}

	public static void writeVjo(String srcName, String vjo) {
		try {
			writeToFile(getFileName(Class.forName(srcName), ".vjo"), vjo);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String getExpectedVjo(String srcName) {
		return JavaSourceLocator.getInstance().getSource(JavaSourceLocator.getInstance().getSourceUrl(srcName, ".vjo"));
	}

	public static void testType(Class srcType, ITranslationInitializer initializer) {
		TranslateCtx.ctx().reset();
		JstCache.getInstance().clear();
		testType(srcType, getTranslationController(initializer), false, false, true);
	}

	public static void testType(Class srcType, ITranslationInitializer initializer,
			boolean persist, boolean validate) {
		JstCache.getInstance().clear();
		testType(srcType, getTranslationController(initializer), persist, persist, validate);
	}
	
	public static void testType(Class srcType, ITranslationInitializer initializer,
			boolean persistVjo, boolean persistJsr, boolean validate) {
		JstCache.getInstance().clear();
		testType(srcType, getTranslationController(initializer), persistVjo, persistJsr, validate);
	}

//	public static void testType(Class srcType, TranslationController controller) {
//		testType(srcType, controller, false, false, true);
//	}

	public static final TraceId ID = TranslationTraceId.TEST;

	public static void testType(Class srcType,
			TranslationController controller, boolean persistVjo,
			boolean persistJsr, boolean validate) {

		// Reset
		// LibManager.getInstance().clear();
		// JstCache.getInstance().clear();
		// TranslateCtx.createCtx();
		getCtx().setTranslateInfo(new HashMap<JstType, TranslateInfo>());

		// Start
		getTracer().startGroup(ID,
				new TraceAttr("name", srcType.getSimpleName()));
		// getLogger().log(Level.INFO, "Started testing type " +
		// srcType.getSimpleName());

		TestHelper helper = new TestHelper(srcType, controller.getInitializer());
		String vjoActual = "";

		JstType jstType = controller.targetedTranslation(srcType);
		
		vjoActual = helper.toVjo(jstType, CodeStyle.PRETTY);

		String jsrActual = "";
		jsrActual = helper.toJsr(jstType, CodeStyle.PRETTY);
//		getCtx().getTraceManager().close();
		printErrors(controller.getErrors());
		printExceptions(controller.getExceptions());

		if (persistVjo) {
			helper.writeVjo(vjoActual);
		}
		
		if(persistJsr){
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
	
	public static void vjoOnDemand(Class srcType, TranslationController controller){
		List <JstType> jstTypes = controller.onDemandTranslation(srcType);
		try{
			writeVjo(jstTypes);
		}catch (ClassNotFoundException e) {
			System.err.println("CLASS TO TRANSLATE WAS NOT FOUND");
			e.printStackTrace();
		}
	}

	public static void printErrors(List<TranslateError> errors) {
		for (TranslateError e : errors) {
			if (e.getLevel() == LogLevel.ERROR) {
				System.out.println(e.toString());
			}
		}
	}
	
	public static void printExceptions(Map<JstType,List<Throwable>> exceptions) {
		for (Entry<JstType,List<Throwable>> entry: exceptions.entrySet()) {
			for (Throwable t: entry.getValue()){
				System.out.println("Exception when translating " + entry.getKey().getName() + ": ");
				t.printStackTrace();
			}
		}
	}

	public static TranslateCtx getCtx() {
		return TranslateCtx.ctx();
	}

	protected static ITranslateTracer getTracer() {
		return getCtx().getTraceManager().getTracer();
	}

	// private static Logger getLogger() {
	// return getCtx().getTraceManager().getLogger();
	// }

	//
	// Protected
	//
	protected CompilationUnit parse(Class cls) {
		String inSource = readFromFile(getFileName(cls, ".java"));
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

	protected String gentype(JstType type) {

		VjoGenerator writer = m_generatorCtx.getProvider().getTypeGenerator();
		writer.writeVjo(type);
		logTime("Gen type");

		return writer.getGeneratedText();
	}

	protected StringWriter genJsr(JstType type, CodeStyle style) {

		StringWriter buffer = new StringWriter();
		getJsrGenerator(buffer, style).writeJsr(type, true);
		logTime("Gen Jsr");

		return buffer;
	}
	
	protected JsrGenerator getJsrGenerator(final StringWriter buffer, final CodeStyle style) {

		JsrGenerator writer = new JsrGenerator(new PrintWriter(buffer), style);
		writer.setNewline(NEWLINE);
//		writer.addListener(new DapJsrGenListener());
//		writer.writeJsr(type, true);
//		logTime("Gen Jsr");

		return writer;
	}

	//
	// Temp
	//
	protected static String getFileName(Class cls, String ext) {
		return new File(CODEGENDIR,cls.getSimpleName()
				+ ext).getAbsolutePath();

	}

	protected static String readFromFile(String fileName) {

		try {
			String characterEncoding = "utf-8";

			FileInputStream fis;
			InputStreamReader isr;
			StringBuffer sb = new StringBuffer();
			char buf[] = new char[4096];
			int numRead;

			fis = new FileInputStream(fileName);
			isr = new InputStreamReader(fis, characterEncoding);

			do {
				numRead = isr.read(buf, 0, buf.length);
				if (numRead > 0)
					sb.append(buf, 0, numRead);
			} while (numRead >= 0);

			isr.close();
			fis.close();

			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	static protected void writeToFile(String fileName, String code) {

		try {
			if(fileName.contains("StreamHandler")){
				System.out.println("Booyah");
			}
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
		return getCtx().getErrorReporter().hasErrors();
	}

	public String getErrorsReported() {
		final ErrorList errors = (ErrorList) getCtx().getErrorReporter()
				.getErrors();
		final StringBuilder errorBuilder = new StringBuilder();
		ListIterator iter = errors.listIterator();
		while (iter.hasNext()) {
			ErrorObject eo = (ErrorObject) iter.next();
			errorBuilder.append(eo.getParameters().getValueByName("message"));
			errorBuilder.append(", on line : ");
			errorBuilder.append(eo.getParameters().getValueByName("line"));
			errorBuilder.append("\n");
		}
		return errorBuilder.toString();
	}
	
	public static void setVjoFileExt(String ext){
		vjoFileExt = ext;
	}
	
	public static void setJsrFileExt(String ext){
		jsrFileExt = ext;
	}
}
