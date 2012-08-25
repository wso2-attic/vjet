/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.tests;




import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.JarURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.tools.ant.BuildException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.ebayopensource.dsf.javatojs.prebuild.J2JsBuildTask;
import org.ebayopensource.dsf.javatojs.tests.data.build.Dependent;
import org.ebayopensource.dsf.javatojs.tests.data.build.j2j.N;
import org.ebayopensource.dsf.javatojs.tests.data.build.j2j.subdir2.J;
import org.ebayopensource.dsf.javatojs.tests.data.build.subdir.Fields;
import org.ebayopensource.dsf.javatojs.tests.data.build.subdir.subdir.subdir.I;
import org.ebayopensource.dsf.javatojs.tests.data.build.subdir.subdir2.Base;
import org.ebayopensource.dsf.javatojs.tests.data.build.subdir.subdir2.subdir3.C;
import org.ebayopensource.dsf.javatojs.translate.config.CodeGenConfig;
import org.ebayopensource.dsf.javatojs.util.JavaToJsHelper;
import org.ebayopensource.dsf.util.JavaSourceLocator;



//@ModuleInfo(value = "DsfPrebuild", subModuleId = "JavaToJs")
public class PreBuildTests {

	private static final String SRC_DIR_NAME = "src";
	public File traceFile;
	private File workingDir;
	private File sourceDir;


	public PreBuildTests() throws IOException, URISyntaxException {
		prepareSourceDir(this.getClass());
		traceFile = new File("v4trace.xml");
		System.out.println("Expected Trace File : "+traceFile.getCanonicalPath());
	}

	private void prepareSourceDir(Class<?> anchor) throws IOException,
			URISyntaxException {
		URL sourceUrl = JavaSourceLocator.getInstance().getSourceUrl(anchor);
		if (sourceUrl.getProtocol().equalsIgnoreCase("jar")) {
			final JarURLConnection conn = (JarURLConnection) sourceUrl
					.openConnection();
			final JarFile jarFile = conn.getJarFile();
			workingDir = new File(jarFile.getName() + "Extract");
			sourceDir = new File(workingDir, SRC_DIR_NAME);
			sourceDir.mkdirs();

			final Enumeration<JarEntry> entries = jarFile.entries();
			while (entries.hasMoreElements()) {
				final JarEntry currentEntry = entries.nextElement();
				if (currentEntry.isDirectory()) {
					new File(sourceDir, currentEntry.getName()).mkdirs();
				} else {
					final File fileToBeWritten = new File(sourceDir,
							currentEntry.getName());

					if (!fileToBeWritten.getParentFile().exists()) {
						fileToBeWritten.getParentFile().mkdirs();
					}
					fileToBeWritten.createNewFile();
					final FileWriter fileWriter = new FileWriter(
							fileToBeWritten);
					fileWriter.write(JavaToJsHelper
							.readFromInputReader(new InputStreamReader(jarFile
									.getInputStream(currentEntry))));
					fileWriter.flush();
					fileWriter.close();

				}
			}

		} else {
			String packagePath = anchor.getPackage().getName()
					.replace('.', '/');
			String url = sourceUrl.toExternalForm();
			sourceDir = new File(new URL(url.substring(0, url
					.indexOf(packagePath))).toURI());
			workingDir = sourceDir.getParentFile();
		}
	}

	private File getDirectDir(Class clz) {
		return new File(sourceDir, clz.getPackage().getName().replace('.', '/'));
	}

	@Before
	@After
	public void removeJsJsrFiles() throws URISyntaxException {

		// delete all .js and jsr files before and after running each test.
		// Otherwise if test
		// codegens js files
		// the tests that check none are created in certain situations will
		// fail.
		
		deleteFiles(getFilesInDir(getDirectDir(Fields.class), ".js"));
		deleteFiles(getFilesInDir(getDirectDir(Fields.class), "Jsr.java"));

		deleteFiles(getFilesInDir(getDirectDir(Base.class), ".js"));
		deleteFiles(getFilesInDir(getDirectDir(Base.class), "Jsr.java"));

		deleteFiles(getFilesInDir(getDirectDir(C.class), ".js"));
		deleteFiles(getFilesInDir(getDirectDir(C.class), "Jsr.java"));

//		deleteFiles(getFilesInDir(getDirectDir(H.class), ".js"));
//		deleteFiles(getFilesInDir(getDirectDir(H.class), "Jsr.java"));

		deleteFiles(getFilesInDir(getDirectDir(I.class), ".js"));
		deleteFiles(getFilesInDir(getDirectDir(I.class), "Jsr.java"));

		deleteFiles(getFilesInDir(getDirectDir(N.class), ".js"));
		deleteFiles(getFilesInDir(getDirectDir(N.class), "Jsr.java"));

		deleteFiles(getFilesInDir(getDirectDir(J.class), ".js"));
		deleteFiles(getFilesInDir(getDirectDir(J.class), "Jsr.java"));

		deleteFiles(getFilesInDir(getDirectDir(Dependent.class), ".js"));
		deleteFiles(getFilesInDir(getDirectDir(Dependent.class), "Jsr.java"));

		traceFile.delete();
	}

	@Test(expected = BuildException.class)
	//@Category( { P4, FUNCTIONAL })
	//@Description("Test that J2JsBuildTask throws BuildException if src dir is set to null")
	public void srcDirNull() {
		J2JsBuildTask task = new J2JsBuildTask();
		task.execute();
	}

	@Test(expected = BuildException.class)
	//@Category( { P4, FUNCTIONAL })
	//@Description("Test that J2JsBuildTask throws BuildException if project dir is set to null")
	public void projectDirNull() {
		J2JsBuildTask task = new J2JsBuildTask();
		task.setSourceDirs("src");
		task.execute();
	}

	@Test
	//@Category( { P4, FUNCTIONAL })
	//@Description("Test J2JsBuildTask where no vjo codegen is requested")
	public void noVjoCodegen() throws URISyntaxException, IOException {
		J2JsBuildTask task = new J2JsBuildTask();

		task.setSourceDirs(SRC_DIR_NAME);
		task.setProjectDir(workingDir.getAbsolutePath());
		task.execute();

		assertNull(task.getJ2jPkgName());

		{
			final File dir = new File(sourceDir, Fields.class.getPackage()
					.getName().replace('.', '/'));
			final File[] files = getFilesInDir(dir, ".js");
			assertTrue(files.length == 0);
		}

		{
			final File[] files = getFilesInDir(new File(sourceDir, Base.class
					.getPackage().getName().replace('.', '/')), ".js");
			assertTrue(files.length == 0);
		}

		{
			final File[] files = getFilesInDir(new File(sourceDir, C.class
					.getPackage().getName().replace('.', '/')), ".js");
			assertTrue(files.length == 0);
		}
//		{
//			final File[] files = getFilesInDir(new File(sourceDir, H.class
//					.getPackage().getName().replace('.', '/')), ".js");
//			assertTrue(files.length == 0);
//		}
		{
			final File[] files = getFilesInDir(new File(sourceDir, I.class
					.getPackage().getName().replace('.', '/')), ".js");
			assertTrue(files.length == 0);
		}

	}

	@Test
	//@Category( { P1, FUNCTIONAL })
	//@Description("Test J2JsBuildTask where vjo codegen is requested")
	public void hasVjoCodegen() throws URISyntaxException {
		J2JsBuildTask task = new J2JsBuildTask();

		task.setSourceDirs("src");
		task.setProjectDir(workingDir.getAbsolutePath());
		
		task.setGenJsr(true);
		task.setEnableParallel(false);
		task.setEnableTrace(false);
		task.setJ2jPkgName("j2j,subdir");
		task.execute();
		// run validation sets
		validate();
	}

	@Test
	//@Category( { P4, FUNCTIONAL })
	//@Description("Test J2JsBuildTask where Java2Js code generator properties are read from a property file")
	public void testJava2JsProperties() throws URISyntaxException {
		String codeGenPropPath = 
		new File (getDirectDir(this.getClass()),"java2js.properties").getAbsolutePath();
		String temp = System.getProperty(CodeGenConfig.GLOBAL_CODE_GEN_PROP);
		System.setProperty(CodeGenConfig.GLOBAL_CODE_GEN_PROP, codeGenPropPath);
		J2JsBuildTask task = new J2JsBuildTask();

		task.setSourceDirs("src");
		task.setProjectDir(workingDir.getAbsolutePath());
		try {
			task.execute();
		} finally {
			System.setProperty(CodeGenConfig.GLOBAL_CODE_GEN_PROP,
					temp == null ? "" : temp);
		}
		// run validation sets
		validate();
	}

	@Test
	//@Category( { P4, FUNCTIONAL })
	//@Description("Test J2JsBuildTask's parallel option")
	public void testParallel() throws URISyntaxException {
		J2JsBuildTask task = new J2JsBuildTask();

		task.setSourceDirs(SRC_DIR_NAME);
		task.setProjectDir(workingDir.getAbsolutePath());
		task.setEnableParallel(true);
		task.setEnableTrace(false);
		task.setJ2jPkgName("j2j,subdir");
		task.execute();
		// run validation sets
		validate();
	}

	@Test
	//@Category( { P4, FUNCTIONAL })
	//@Description("Test J2JsBuildTask's trace option")
	public void testTrace() throws URISyntaxException,
			UnsupportedEncodingException, FileNotFoundException, IOException {
		J2JsBuildTask task = new J2JsBuildTask();

		task.setSourceDirs(SRC_DIR_NAME);
		task.setProjectDir(workingDir.getAbsolutePath());
		task.setEnableParallel(false);
		task.setEnableTrace(true);
		task.setJ2jPkgName("j2j,subdir");
		task.execute();
		// run validation sets
		validate();

		assertTrue(traceFile.length() > 600);
	}

	@Test
	//@Category( { P4, FUNCTIONAL })
	//@Description("Test J2JsBuildTask's getters and setters")
	public void testGetSet() {
		J2JsBuildTask task = new J2JsBuildTask();
		task.setEnableParallel(true);
		task.setEnableTrace(true);
		assertTrue(task.getEnableParallel());
		assertTrue(task.getEnableTrace());

		assertNull(task.getExclusion());
		task.setExclusion("myexclude: !@#$%^&*I(O)");
		assertEquals("myexclude: !@#$%^&*I(O)", task.getExclusion());

		task.setEnableParallel(false);
		task.setEnableTrace(false);
		assertFalse(task.getEnableParallel());
		assertFalse(task.getEnableTrace());

	}

	private File[] getFilesInDir(File dir, String fileType) {
		final String fType = fileType;
		File[] files = dir.listFiles(new FileFilter() {
			public boolean accept(File name) {
				return name.getName().endsWith(fType);
			}
		});

		return files;
	}

	private void deleteFiles(File[] files) {
		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				System.out.println("Deleting File : "+files[i]);
				files[i].delete();
			}
		}
	}

	private static class NameComparator implements Comparator<File> {

		public static final NameComparator INSTANCE = new NameComparator();

		public int compare(File o1, File o2) {
			return o1.getName().compareTo(o2.getName());
		}

	}

	private void validate() throws URISyntaxException {

		{
			File[] files = getFilesInDir(getDirectDir(Fields.class), ".js");
			Arrays.sort(files, NameComparator.INSTANCE);
			assertTrue(files.length == 1);

			// Modifying this condition as the Fields class contains codegen
			// annotation and needs to be excluded
			assertFalse("No match for \"" + files[0].getName() + "\"", files[0]
					.getName().equalsIgnoreCase("Fields.js"));
			assertTrue("No match for \"" + files[0].getName() + "\"", files[0]
					.getName().equalsIgnoreCase("Methods.js"));

		}
		{
			File[] files = getFilesInDir(getDirectDir(Base.class), ".js");
			Arrays.sort(files, NameComparator.INSTANCE);
			assertTrue(files.length == 5);
			assertTrue("No match for \"" + files[0].getName() + "\"", files[0]
					.getName().equalsIgnoreCase("Base.js"));
			assertTrue("No match for \"" + files[1].getName() + "\"", files[1]
					.getName().equalsIgnoreCase("D.js"));
			assertTrue("No match for \"" + files[2].getName() + "\"", files[2]
					.getName().equalsIgnoreCase("E.js"));
			assertTrue("No match for \"" + files[3].getName() + "\"", files[3]
					.getName().equalsIgnoreCase("F.js"));
			assertTrue("No match for \"" + files[4].getName() + "\"", files[4]
					.getName().equalsIgnoreCase("G.js"));
		}

		{
			File[] files = getFilesInDir(getDirectDir(C.class), ".js");
			Arrays.sort(files, NameComparator.INSTANCE);
			assertTrue(files.length == 3);
			assertTrue("No match for \"" + files[0].getName() + "\"", files[0]
					.getName().equalsIgnoreCase("A.js"));
			assertTrue("No match for \"" + files[1].getName() + "\"", files[1]
					.getName().equalsIgnoreCase("B.js"));
			assertTrue("No match for \"" + files[2].getName() + "\"", files[2]
					.getName().equalsIgnoreCase("C.js"));
		}

		{
			File[] files = getFilesInDir(getDirectDir(I.class), ".js");
			Arrays.sort(files, NameComparator.INSTANCE);
			assertTrue(files.length == 1);
			assertTrue("No match for \"" + files[0].getName() + "\"", files[0]
					.getName().equalsIgnoreCase("I.js"));
		}

//		{
//			File[] files = getFilesInDir(getDirectDir(H.class), ".js");
//			Arrays.sort(files, NameComparator.INSTANCE);
//			Arrays.sort(files, NameComparator.INSTANCE);
//			assertTrue(files.length == 0);
//		}

		{
			File[] files = getFilesInDir(getDirectDir(N.class), ".js");
			Arrays.sort(files, NameComparator.INSTANCE);
			assertTrue(files.length == 2);
			assertTrue("No match for \"" + files[0].getName() + "\"", files[0]
					.getName().equalsIgnoreCase("N.js"));
			assertTrue("No match for \"" + files[1].getName() + "\"", files[1]
					.getName().equalsIgnoreCase("O.js"));

		}

		{
			File[] files = getFilesInDir(getDirectDir(J.class), ".js");
			Arrays.sort(files, NameComparator.INSTANCE);
			Arrays.sort(files, NameComparator.INSTANCE);
			assertTrue(files.length == 4);
			assertTrue("No match for \"" + files[0].getName() + "\"", files[0]
					.getName().equalsIgnoreCase("J.js"));
			assertTrue("No match for \"" + files[1].getName() + "\"", files[1]
					.getName().equalsIgnoreCase("K.js"));
			assertTrue("No match for \"" + files[2].getName() + "\"", files[2]
					.getName().equalsIgnoreCase("L.js"));
			assertTrue("No match for \"" + files[3].getName() + "\"", files[3]
					.getName().equalsIgnoreCase("M.js"));

		}

	}
}
