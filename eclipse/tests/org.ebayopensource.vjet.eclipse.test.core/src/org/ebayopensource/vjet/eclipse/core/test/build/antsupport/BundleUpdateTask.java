/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.core.test.build.antsupport;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

/**
 * Note: the steps are given at:
 * https://wiki2.arch.ebay.com/confluence/display/QE/Installation
 * 
 * do below 3 things: Dsfbase depend on jsnativeresource correct bundle versions
 * if not correct correct src paths in build properties if not exist
 * 
 * 
 * 
 */
public class BundleUpdateTask extends Task {

	private static final String BUNDLE_VERSION = "Bundle-Version:";

	private static final String MANIFEST_MF_PATH = "META-INF/MANIFEST.MF";

	private static final String REQUIRE_BUNDLE = "Require-Bundle:";

	public static final String PROPERTY_SOURCE_PREFIX = "source.."; //$NON-NLS-1$

	static String[] projects = new String[] { "DsfBase", "DsfPrebuild",
			"JsNativeResource", "uKernel", "uKernelCore" };

	String projectDirectory;

	/**
	 * set the location where to find the generated projects.
	 * 
	 * @param dir
	 */
	public void setProjectDirectory(String dir) {
		this.projectDirectory = dir;
	}

	@Override
	public void execute() throws BuildException {

		if (projectDirectory == null) {
			throw new BuildException("Dir must be specified");
		}

		for (String prj : projects) {
			File projectFile = new File(projectDirectory, prj);
			if (!projectFile.exists()) {
				throw new BuildException("cannot find project: " + prj
						+ " under " + projectDirectory);
			}

			try {
				addDependency(projectFile);
				updateBundleVersions(projectFile);
				// updateBuildProperties(projectFile);
			} catch (Exception e) {
				throw new BuildException("unknown exception occurred ", e);
			}
		}
		super.execute();
	}

	private void addDependency(File projFile) throws FileNotFoundException,
			IOException {
		// Modify bundle.DsfBase's MANIFEST.MF (not bundle.DsfPrebuild's)

		// 1. Double-click PackageExplorer->bundle.DsfBase\META-INF\MANIFEST.MF
		// (Note that you don't have to check it out.)
		// 2. Click on MANIFEST.MF tab on bottom.
		// 3. Add as non-last line in Require-Bundle: (don't forget the trailing
		// comma):
		// JsNativeResource;visibility:=reexport,
		// 4. Save

		if (projFile.getName().equals("DsfBase")) {
			File manifest = new File(projFile, MANIFEST_MF_PATH);
			if (!manifest.exists()) {
				throw new BuildException("cannot find Manifest.mf for project "
						+ projFile.getName());
			}

			List lines = readLines(new FileInputStream(manifest));

			int startLine = 0;
			for (Object object : lines) {
				String line = (String) object;
				if (line.startsWith(REQUIRE_BUNDLE)) {
					break;
				}
				startLine++;

			}
			String line = (String) lines.get(startLine);
			if (line.startsWith(REQUIRE_BUNDLE)) {

				int endLine = startLine + 1;
				// go to next line
				while (endLine <= lines.size() - 1) {

					line = (String) lines.get(endLine);
					if (line.indexOf(": ") != -1) {
						// have a space is the gramar of Header
						endLine = endLine - 1;
						break;// has : means meet another header
					}

					endLine++;

				}

				line = (String) lines.get(endLine);
				if (!line.contains("JsNativeResource;visibility:=reexport")) {
					
					//yes! leave 2 spaces after \n
					lines.set(endLine, line
							+ ",\n  JsNativeResource;visibility:=reexport");

					saveFile(manifest, lines);
				}

			}

		}

	}

	/**
	 * //currently seems do not need this call if using BundleGenerator task.
	 * but if we generate by ECLIPSE build plugin we need update
	 * buildProperties.
	 * 
	 * @param projFile
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	// private void updateBuildProperties(File projFile)
	// throws FileNotFoundException, IOException {
	//
	// File file = new File(projFile, "build.properties");
	// // boolean searchBuild = file.exists();
	// if (file.exists()) {
	// if (!file.canWrite())
	// file.setWritable(true);
	//
	// ExternalBuildModel bModel = new ExternalBuildModel(projFile
	// .getAbsolutePath()) {
	//
	// @Override
	// public boolean isEditable() {
	// return true;
	// }
	//
	// };
	// bModel.load();
	//
	// IBuild build = bModel.getBuild();
	// IBuildEntry entry = build.getEntry(PROPERTY_SOURCE_PREFIX); //$NON-NLS-1$
	//
	// try {
	// if (entry != null) {
	// String[] tokens = entry.getTokens();
	// for (String token : tokens) {
	// if (!new File(token).exists()) {
	// entry.removeToken(token);
	// }
	// }
	// if (entry.getTokens().length == 0)
	// build.remove(entry);
	// }
	//
	// PrintWriter printWriter=new PrintWriter(file.getAbsolutePath());
	// build.write("", printWriter);
	// printWriter.flush();
	// printWriter.close();
	//
	// } catch (CoreException e) {
	// throw new BuildException(e);
	// }
	// }
	//
	// }
	private void updateBundleVersions(File projFile)
			throws FileNotFoundException, IOException {

		File manifest = new File(projFile, MANIFEST_MF_PATH);
		if (!manifest.exists()) {
			throw new BuildException("cannot find Manifest.mf for project "
					+ projFile.getName());
		}

		List lines = readLines(new FileInputStream(manifest));

		int startLine = 0;
		for (Object object : lines) {
			String line = (String) object;
			if (line.startsWith(BUNDLE_VERSION)) {
				String version = line.substring(BUNDLE_VERSION.length()).trim();
				String[] parts = version.split("\\.");
				if (parts.length > 4) {
					version = parts[0] + "." + parts[1] + "." + parts[2] + "."
							+ parts[parts.length - 1];
					lines.set(startLine, BUNDLE_VERSION + " " + version);
					saveFile(manifest, lines);
				}

				break;
			}
			startLine++;

		}

	}

	private void saveFile(File manifest, List lines)
			throws UnsupportedEncodingException, FileNotFoundException,
			IOException {
		StringBuffer sbuf = new StringBuffer(512);
		for (Object object2 : lines) {
			sbuf.append((String) object2).append("\n");

		}
		// save lines

		writeFile(manifest.getAbsolutePath(), sbuf.toString(), "UTF-8");
	}

	public static void writeFile(String fileName, String text,
			String characterEncoding) throws UnsupportedEncodingException,
			FileNotFoundException, IOException {
		FileOutputStream fos;
		byte[] buf = text.getBytes(characterEncoding);
		int numWritten;
		fos = new FileOutputStream(fileName);
		numWritten = 0;
		fos.write(buf, numWritten, buf.length);
		fos.close();
	}

	/**
	 * This is a convenience method thread reads an input stream into a List<String>
	 * 
	 * @param inputStream
	 * @return
	 * @throws IOException
	 */
	public static List readLines(final InputStream inputStream)
			throws IOException {
		final String text = readStream(inputStream);
		StringTokenizer tokenizer = new StringTokenizer(text, "\n\r");
		Vector list = new Vector(100);
		while (tokenizer.hasMoreElements()) {
			final String line = tokenizer.nextToken();
			list.add(line);
		}
		return list;
	}

	/**
	 * This is a convienence method to read text from an input stream into a
	 * string. It will use the default encoding of the OS. It call the
	 * underlying readString(InputStreamReader)
	 * 
	 * @param inputStream -
	 *            InputStream
	 * @return String - the text that was read in
	 * @throws IOException
	 */
	public static String readStream(final InputStream inputStream)
			throws IOException {
		final InputStreamReader isr = new InputStreamReader(inputStream);
		try {
			return readStream(isr);
		} finally {
			isr.close();
		}
	}

	/**
	 * This is a convienence method to read text from a stream into a string.
	 * The transfer buffer is 4k and the initial string buffer is 75k. If this
	 * causes a problem, write your own routine.
	 * 
	 * @param isr -
	 *            InputStreamReader
	 * @return String - the text that was read in
	 * @throws IOException
	 */
	public static String readStream(final InputStreamReader isr)
			throws IOException {
		StringBuffer sb = new StringBuffer(75000);
		char[] buf = new char[4096];
		int numRead;
		do {
			numRead = isr.read(buf, 0, buf.length);
			if (numRead > 0) {
				sb.append(buf, 0, numRead);
			}
		} while (numRead >= 0);
		final String result = sb.toString();
		return result;
	}

	public static void main(String[] args) {
		BundleUpdateTask update = new BundleUpdateTask();
		update.setProjectDirectory("C:/Eric/workspaces3/ReleaseVjet");
		update.execute();
	}

}
