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
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import org.ebayopensource.dsf.javatojs.tests.data.build.j2j.N;
import org.ebayopensource.dsf.javatojs.tests.data.build.j2j.subdir2.J;
import org.ebayopensource.dsf.javatojs.tests.data.build.j2jma.P;
import org.ebayopensource.dsf.util.JavaSourceLocator;

import org.ebayopensource.dsf.common.resource.ResourceUtil;

//@ModuleInfo(value="DsfPrebuild",subModuleId="JavaToJs")
public class VjoPrebuildAntTest {
	
	@BeforeClass
	public static void init(){
	}
	
	@Ignore("Required, unless this test can be fixed to run using Ant")
	@Test
	//@Category( { P5, INTEGRATION })
	//@Description("Test translation operation using Ant")
	public void runAnt() throws IOException,
			URISyntaxException, InterruptedException {
		String path = new File(ResourceUtil.getResource(VjoPrebuildAntTest.class,
				"VjoPrebuildAntTest.class").toURI()).getPath();
		path = path.substring(0, path.indexOf("v4darwin"));
		String antHomePath = "set ANT_HOME=\"" + path
				+ "builddeployv3/Build2/ant\"";
		
		String antFilePath = path + "builddeployv3/Build2/ant-scripts/JsPrebuild.xml";
		
		//ANT_HOME=D:\ccviews\d_sjc_alpatel1_thor575\builddeployv3\Build2\ant
		//System.out.println(antFilePath);
		
		// Run the ant task
		Process p = null;
		if (System.getProperty("os.name").startsWith("Win")) {
			p = Runtime.getRuntime().exec(
					"cmd /C \"" + antHomePath + "&ant -Dglobal.view.root.dir=" + path + " -Dglobal.build.dir=" + path + "BuildOutput/build50/prebuildTest -DProjectName=DSFJavaToJsTests -DSourceDirectories=src -Dbasedir=" + path + "v4darwin/DSFJavaToJsTests -DoutputDir=" + path + "BuildOutput/build50/jsprebuild/DSFJavaToJsTests -buildfile " + antFilePath + "\"");

		} else {
			p = Runtime.getRuntime().exec("ant -buildfile " + antFilePath);
		}
		ReadProcStream reader = new ReadProcStream();
		reader.readStreams(p);
		p.waitFor();
		p.destroy();
		
		String output = reader.getOutput();
		String errors = reader.getErrors();
		System.out.println("OUTPUT:\n" + output);
		System.out.println("ERRORS:\n" + errors);
		assertEquals("", errors);
		//assertTrue(output.indexOf("BUILD SUCCESSFUL") > -1);
		
		File dir = new File(JavaSourceLocator.getInstance().getSourceUrl(J.class).toURI());
		File[] files = getFilesInDir(dir, ".js");
		assertTrue(files.length == 4);
		assertTrue(files[0].getName().equalsIgnoreCase("J.js"));
		assertTrue(files[1].getName().equalsIgnoreCase("K.js"));
		assertTrue(files[2].getName().equalsIgnoreCase("L.js"));
		assertTrue(files[3].getName().equalsIgnoreCase("M.js"));
		
		dir = new File(JavaSourceLocator.getInstance().getSourceUrl(J.class).toURI());
		files = getFilesInDir(dir, "Jsr.java");
		assertTrue(files.length == 4);
		assertTrue(files[0].getName().equalsIgnoreCase("JJsr.java"));
		assertTrue(files[1].getName().equalsIgnoreCase("KJsr.java"));
		assertTrue(files[2].getName().equalsIgnoreCase("LJsr.java"));
		assertTrue(files[3].getName().equalsIgnoreCase("MJsr.java"));
		
		dir = new File(JavaSourceLocator.getInstance().getSourceUrl(N.class).toURI());
		files = getFilesInDir(dir, ".js");
		assertTrue(files.length == 2);
		assertTrue(files[0].getName().equalsIgnoreCase("N.js"));
		assertTrue(files[1].getName().equalsIgnoreCase("O.js"));
		
		dir = new File(JavaSourceLocator.getInstance().getSourceUrl(N.class).toURI());
		files = getFilesInDir(dir, "Jsr.java");
		assertTrue(files.length == 2);
		assertTrue(files[0].getName().equalsIgnoreCase("NJsr.java"));
		assertTrue(files[1].getName().equalsIgnoreCase("OJsr.java"));
		
		dir = new File(JavaSourceLocator.getInstance().getSourceUrl(P.class).toURI());
		files = getFilesInDir(dir, ".js");
		assertTrue(files.length == 0);
		
		dir = new File(JavaSourceLocator.getInstance().getSourceUrl(P.class).toURI());
		files = getFilesInDir(dir, "Jsr.java");
		assertTrue(files.length == 0);
	}
	
	private static File [] getFilesInDir(File dir, String fileType){
		final String fType = fileType;
		File[] files = dir.getParentFile().listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.endsWith(fType);
			}			
		});
		
		return files;
	}

}
