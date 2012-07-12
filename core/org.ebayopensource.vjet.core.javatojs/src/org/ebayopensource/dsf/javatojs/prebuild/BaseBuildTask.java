/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.prebuild;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import org.apache.tools.ant.Task;

import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstProperty;
import org.ebayopensource.dsf.jst.declaration.JstArg;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.dsf.common.Z;

public class BaseBuildTask extends Task {

	public static final String JAVA_SUFFIX = ".java";
	public static final String JSR_SUFFIX = "Jsr.java";

	protected void getBuildFiles(final File dir, final List<URL> list,
			final String pkgNamesStr, final String excludePkg,
			final boolean debug) {
		if (dir == null) {
			throw new RuntimeException("dir is null");
		}
		if (!dir.exists()) {
			throw new RuntimeException("dir " + dir.getAbsolutePath()
					+ " doesn't exist");
		}

		File[] j2jFiles = dir.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				if (!name.endsWith(JAVA_SUFFIX) || name.endsWith(JSR_SUFFIX)
						|| pkgNamesStr == null) {
					return false;
				}
				String[] pkgNames = pkgNamesStr.split(",");
				for (String pkgName : pkgNames) {
					String subdir = pkgName.replace(".", File.separator);
					if (excludePkg != null) {
						String excludeSubDir = excludePkg.replace(".",
								File.separator);
						if (dir.getPath().contains(excludeSubDir)) {
							return false;
						}
					}
					if ((dir.getPath().contains(
							File.separator + subdir + File.separator) || dir
							.getPath().endsWith(File.separator + subdir))) {
						 if (debug) {
						 System.out.println("Accept: " +
						 dir.toString()+File.separator+name); //KEEPME
						 }
						return true;
					}
				}
				return false;
			}
		});
		if (j2jFiles != null) {
			for (File f : j2jFiles) {
				try {
					list.add(f.toURI().toURL());
				} catch (MalformedURLException e) {
					System.out
							.println("Malformed resource URL for file : " + f);
				}
			}
		}

		File[] subDirs = dir.listFiles(new FileFilter() {
			public boolean accept(File file) {
				return file.isDirectory();
			}
		});
		for (File subDir : subDirs) {
			getBuildFiles(subDir, list, pkgNamesStr, excludePkg, debug);
		}
	}

	protected static File[] parseSourceDirs(String srcDirs, String projRoot) {
		String[] sources = srcDirs.split(File.pathSeparator);
		File[] dirs = new File[sources.length];
		int count = 0;
		for (String source : sources) {
			String sourceFile = source;
			if (source.indexOf(projRoot) == -1) {
				sourceFile = projRoot + File.separatorChar + source;
			}
			dirs[count] = new File(sourceFile);
			count++;
		}
		return dirs;
	}

	protected void dump(List<JstType> jstTypes) {
		for (JstType jstType : jstTypes) {
			Z z = new Z();
			z.format(">> JstType");
			z.format("name", jstType.getName());
			if (!jstType.getAnnotations().isEmpty()) {
				z.format("annotations", jstType.getAnnotations());
			}
			z.format("extends", jstType.getExtends());
			z.format("static props:");
			for (IJstProperty p : jstType.getAllPossibleProperties(true, true)) {
				if (!p.getAnnotations().isEmpty()) {
					z.format("\tprops-annotations", p.getAnnotations());
				}
				z.append("\t" + p.getModifiers().toString());
				z.append(" " + p.getType().getName());
				z.append(" " + p.getName());
				z.format(";");
			}
			z.format("instance props:");
			for (IJstProperty p : jstType.getAllPossibleProperties(false, true)) {
				if (!p.getAnnotations().isEmpty()) {
					z.format("\tprops-annotations", p.getAnnotations());
				}
				z.append("\t" + p.getModifiers().toString());
				z.append(" " + p.getType().getName());
				z.append(" " + p.getName());
				z.format(";");
			}

			IJstMethod cons = jstType.getConstructor();
			if (cons != null) {
				z.format("constructor:");
				z.append("\t" + cons.getModifiers().toString());
				z.append(" " + cons.getName());
				z.append("(");
				Iterator iter = cons.getArgs().iterator();
				while (iter.hasNext()) {
					JstArg arg = (JstArg) iter.next();
					z.append(arg.getType().getName());
					z.append(" " + arg.getName());
					if (iter.hasNext()) {
						z.append(", ");
					}
				}
				z.format(");");
				for (IJstMethod over : cons.getOverloaded()) {
					z.format("overloaded-constructor:");
					z.append("\t" + over.getModifiers().toString());
					z.append(" " + over.getName());
					z.append("(");
					iter = over.getArgs().iterator();
					while (iter.hasNext()) {
						JstArg arg = (JstArg) iter.next();
						z.append(arg.getType().getName());
						z.append(" " + arg.getName());
						if (iter.hasNext()) {
							z.append(", ");
						}
					}
					z.format(");");
				}
			}
			z.format("static methods:");
			for (IJstMethod m : jstType.getMethods(true, true)) {
				if (!m.getAnnotations().isEmpty()) {
					z.format("\tmtd-annotations", m.getAnnotations());
				}
				z.append("\t" + m.getModifiers().toString() + " ");
				if (m.getRtnType() != null) {
					z.append(m.getRtnType().getName());
					z.append(" ");
				}
				z.append(m.getName());
				z.append("(");
				Iterator iter = m.getArgs().iterator();
				while (iter.hasNext()) {
					JstArg arg = (JstArg) iter.next();
					z.append(arg.getType().getName());
					z.append(" " + (arg.isVariable() ? "..." : "")
							+ arg.getName());
					if (iter.hasNext()) {
						z.append(", ");
					}
				}
				z.format(");");
				for (IJstMethod over : m.getOverloaded()) {
					z.format("overloaded-method:");
					z.append("\t" + over.getModifiers().toString());
					if (over.getRtnType() != null) {
						z.append(" ");
						z.append(over.getRtnType().getName());
						z.append(" ");
					}
					z.append(" " + over.getName());
					z.append("(");
					iter = over.getArgs().iterator();
					while (iter.hasNext()) {
						JstArg arg = (JstArg) iter.next();
						z.append(arg.getType().getName());
						z.append(" " + arg.getName());
						if (iter.hasNext()) {
							z.append(", ");
						}
					}
					z.format(");");
				}
			}

			z.format("instance methods:");
			for (IJstMethod m : jstType.getMethods(false, true)) {
				if (!m.getAnnotations().isEmpty()) {
					z.format("\tmtd-annotations", m.getAnnotations());
				}
				z.append("\t" + m.getModifiers().toString() + " ");
				if (m.getRtnType() != null) {
					z.append(m.getRtnType().getName());
					z.append(" ");
				}
				z.append(m.getName());
				z.append("(");
				Iterator iter = m.getArgs().iterator();
				while (iter.hasNext()) {
					JstArg arg = (JstArg) iter.next();
					z.append(arg.getType().getName());
					z.append(" " + (arg.isVariable() ? "..." : "")
							+ arg.getName());
					if (iter.hasNext()) {
						z.append(", ");
					}
				}
				z.format(");");
				for (IJstMethod over : m.getOverloaded()) {
					z.format("overloaded-method:");
					z.append("\t" + over.getModifiers().toString());
					if (over.getRtnType() != null) {
						z.append(" ");
						z.append(over.getRtnType().getName());
						z.append(" ");
					}
					z.append(" " + over.getName());
					z.append("(");
					iter = over.getArgs().iterator();
					while (iter.hasNext()) {
						JstArg arg = (JstArg) iter.next();
						z.append(arg.getType().getName());
						z.append(" " + arg.getName());
						if (iter.hasNext()) {
							z.append(", ");
						}
					}
					z.format(");");
				}
			}
			System.out.println(z);
		}
	}

}
