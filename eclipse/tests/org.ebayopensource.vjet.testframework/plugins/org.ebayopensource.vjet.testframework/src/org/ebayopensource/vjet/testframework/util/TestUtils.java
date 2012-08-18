/*******************************************************************************
 * Copyright (c) 2005-2012 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.testframework.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.eclipse.core.internal.dtree.TestHelper;

/**
 * 
 * @author ddodd
 * 
 * Set of utilities to help with running and setting up tests.
 *
 */
public class TestUtils {

	
	private static final int DEFAULT_BUFFER = 4096;
	
	public static String fullName(Object testSource, String suffix) {
		if (null == testSource) {
			return suffix;
		}
		if (null == suffix) {
			suffix = "";
		} else {
			suffix = "(" + suffix + ")";
		}
		
		String fullName  = className(testSource);
		fullName += ".";
		if (testSource instanceof TestCase) {
			fullName += ((TestCase)testSource).getName() ;
		} else if (testSource instanceof TestSuite) {
			fullName += ((TestSuite)testSource).getName();
		} else {
			fullName += testSource.getClass().getSimpleName();
		}
		fullName += suffix;
		
		return fullName;
	}

	/**
	 * Return the File which represents the sandbox directory.
	 * @param testSource
	 * @return
	 */
	public static File getSandboxDir(Object testSource) {
		final String name = (null == testSource ? "test-"
				+ System.currentTimeMillis() : fullName(testSource, null));
		
		File sandBoxBaseDir = null;
		if (null != name) {
			sandBoxBaseDir = getTempDir();
			if (isDirectoryWritable(sandBoxBaseDir)) {
				sandBoxBaseDir = new File(sandBoxBaseDir, name);
			}
		}
		return sandBoxBaseDir;
	}

	/**
	 * @return readable temporary directory (do not delete)
	 */
	public static File getTempDir() {
		String tmpDirPath = "/tmp/";// System.getProperty("java.io.tmpdir");
		if (null != tmpDirPath) {
			File result = new File(tmpDirPath);
			result.mkdirs();
			if (readableDirectory(result)) {
				return result;
			}
		}
		return null;
	}

	public static boolean isDirectoryWritable(File file) {
		return (null != file) && file.canWrite() && file.isDirectory();
	}

	public static boolean isDirectoryWritable(File dir, boolean force) {
		if (isDirectoryWritable(dir)) {
			return true;
		}
		if (!force) {
			return false;
		}
		dir.mkdirs(); // result too variable to rely on
		return isDirectoryWritable(dir);
	}

	public static boolean isFileReadable(File file) {
		return (null != file) && file.canRead() && file.isFile();
	}
	
	
	/**
	 * Create a new sandbox directory with the specified name.
	 * 
	 * @see TestHelper
	 * 
	 * @param name
	 *            ignored if null
	 * @return null if unable to create, writable File directory with name
	 *         otherwise
	 */
	public static File makeSandboxDir(File sandboxDir) {
		if (null != sandboxDir) {
			if (isDirectoryWritable(sandboxDir, true)) {
				return sandboxDir;
			} else {
				throw new Error("Sandbox dir is not writable: " + sandboxDir);
			}
		}
		return null;
	}

	/**
	 * Create a sandbox directory with full name of input test.
	 * 
	 * @return null if unable to create, writable File directory otherwise
	 */
	public static File makeSandboxDir(Object test) {
		File sandboxDir = getSandboxDir(test);
		return makeSandboxDir(sandboxDir);
	}

	public static boolean readableDirectory(File dir) {
		return (null != dir) && dir.canRead() && dir.isDirectory();
	}

	/**
	 * Readable class name (i.e., not unqualified)
	 * 
	 * @param o
	 *            may be null
	 * @return readable String class name
	 */
	public static String className(Object o) {
		if (null == o) {
			return "NOCLASS";
		}
		if (o instanceof Class) {
			return ((Class<?>) o).getSimpleName();
		}
		// implemetn short/long name policy for those who don't care
		return o.getClass().getSimpleName();
	
	}
	

	/**
	 * @param zipFile
	 *            the File to open as a ZipFile
	 * @param destDir
	 *            the File to copy contents of zipFile to (need not exist)
	 * @return String error if detected or null otherwise
	 * @throws IOException
	 */
	public static String unzip(File zipFile, File destDir) throws IOException {
		if (!isDirectoryWritable(destDir, true)) {
			return "unable to create writable directory: " + destDir;
		}

		ZipFile zip = null;
		try {
			zip = new ZipFile(zipFile);
			Enumeration<? extends ZipEntry> entries = zip.entries();
			while (entries.hasMoreElements()) {
				ZipEntry entry = entries.nextElement();
				if (!entry.isDirectory()) {
					File destFile = new File(destDir, entry.getName());
					InputStream src = null;
					try {
						src = zip.getInputStream(entry);
						String result = write(src, destFile);
						if (null != result) {
							return result;
						}
					} finally {
						if (null != src) {
							src.close();
						}
					}
				}
			}
		} finally {
			if (null != zip) {
				zip.close();
			}
		}
		return null;
	}
	
	public static String write(InputStream source, File destFile)
			throws IOException {

		if (!isDirectoryWritable(destFile.getParentFile(), true)) {
			return "unable to create parent dir for " + destFile;
		}
		OutputStream out = null;
		try {
			out = new FileOutputStream(destFile);
			copy(source, out);
			return null;
		} finally {
			if (null != out) {
				out.close();
			}
		}
	}
	
	
	private static final int MAX_STREAM_COPY_SIZE = 1024 * 1024 * 50;

	public static String copy(InputStream inputStream) throws IOException {
		return copy(inputStream, MAX_STREAM_COPY_SIZE);
	}

	public static String copy(InputStream inputStream, int maxSize)
			throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		copy(inputStream, out, maxSize);
		return out.toString(); // TODO P3 assumes default encoding
	}

	public static void copy(InputStream inputStream, OutputStream outputStream)
			throws IOException {
		copy(inputStream, outputStream, MAX_STREAM_COPY_SIZE);
	}
	
	
	public static void copy(InputStream inputStream, OutputStream outputStream,
			int maxSize) throws IOException {
		if (null != inputStream) {
			byte[] buffer = new byte[DEFAULT_BUFFER];
			int total = 0;
			int read;
			while ((read = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, read);
				total += read;
			}
		}
	}
	

}
