/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * This util class is for retrieving the java or other type source files based
 * on the search path specified by "-Djava.source.path" or optimized paths
 * listed below:
 * 
 * -Djava.source.projectRoot=d:\rootforprojects\
 * -Djava.source.projectList=vobdir\ProjectA,vobdir\ProjectB,\vobdir\ProjectC
 * 
 * -Djava.source.jarRoot=d:\rootforbinaries\
 * -Djava.source.jarList=a.jar,b.jar,c.jar,d.jar
 * 
 * yields the same path as
 * 
 * d:\rootforprojects\vobdir\ProjectA d:\rootforprojects\vobdir\ProjectB
 * d:\rootforprojects\vobdir\ProjectC d:\rootforbinaries\a.jar
 * d:\rootforbinaries\b.jar d:\rootforbinaries\c.jar d:\rootforbinaries\d.jar
 * 
 * with less characters we are only supporting one root directory for projects
 * and one root directory for binaries.
 * 
 * 
 * If "java.source.path" is not specified, it will try to using classpath to
 * derive the source search path (not necessarily correct) by changing /bin to
 * /src.
 */
public class JavaSourceLocator {

	private static JavaSourceLocator s_instance = new JavaSourceLocator();

	private URLClassLoader m_loader;

	private String m_sourcePath = null;
	
	private static boolean s_isUnix = ":".equals(File.pathSeparator);

	private JavaSourceLocator() {
		reset();
	}

	public static JavaSourceLocator getInstance() {
		if (s_instance.m_sourcePath != null) {
			if (!s_instance.m_sourcePath.equals(System
					.getProperty("java.source.path"))) {
				s_instance.reset();
			}
		}
		return s_instance;
	}

	public void reset() {
		try {
			m_sourcePath = System.getProperty("java.source.path");

			m_sourcePath = determineSourcePath(m_sourcePath);

			if (m_sourcePath != null && m_sourcePath.length() != 0) {
				m_loader = new URLClassLoader(getSourcePathUrls(m_sourcePath,
						false));
			} else {
				String classPath = System.getProperty("java.class.path");
				ClassLoader clzLoader = this.getClass().getClassLoader();
				if (clzLoader instanceof URLClassLoader) {
					// extract additional classpath from URLClassLoader
					StringBuilder sb = new StringBuilder();
					URL[] urls = ((URLClassLoader) clzLoader).getURLs();
					for (URL url : urls) {
						String path = url.getPath();
						if (path.endsWith("/bin/")) {
							sb.append(File.pathSeparator);
							sb.append(path.substring(0, path.length() - 1));
						}
					}
					classPath += sb.toString();
				}

				m_loader = new URLClassLoader(
						getSourcePathUrls(classPath, true));
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public URLClassLoader getClassLoader() {
		return m_loader;
	}

	private String determineSourcePath(String sourcePath) {
		String projectRoot = System.getProperty("java.source.projectRoot");
		String projectList = System.getProperty("java.source.projectList");
		String jarRoot = System.getProperty("java.source.jarRoot");
		String jarList = System.getProperty("java.source.jarList");

		StringBuilder sb = new StringBuilder();
		if (sourcePath != null && sourcePath.length() != 0) {
			sb.append(sourcePath);
			sb.append(File.pathSeparator);

		}

		if (projectRoot != null && !projectRoot.equals("")
				&& projectList != null && projectList.equals("")) {

			String[] projList = projectList.split(",");
			for (String project : projList) {
				sb.append(projectRoot + File.separatorChar + project);
				sb.append(File.pathSeparator);
			}
		}

		if (jarRoot != null && jarRoot.length() != 0 && jarList != null
				&& jarList.length() != 0) {
			String[] libList = jarList.split(",");
			for (String lib : libList) {
				sb.append(jarRoot + lib);
				sb.append(File.pathSeparator);
			}
		}

		if (sb.toString().equalsIgnoreCase("")) {
			return null;
		}
		return sb.toString();

	}

	/**
	 * get java source file URL
	 */
	public URL getSourceUrl(Class clz) {
		return getSourceUrl(clz.getName());
	}

	/**
	 * get java source file URL
	 */
	public URL getSourceUrl(String clzName) {
		return getSourceUrl(clzName, ".java");
	}

	/**
	 * get source file URL based on sourceName (a.b.c), and its suffix such as
	 * ".js", ".java", etc.
	 */
	public URL getSourceUrl(String sourceName, String suffix) {
		String baseUri = sourceName.replace(".", "/");
		String fileName = baseUri + suffix;
		return m_loader.findResource(fileName);
	}

	/**
	 * get java source file content
	 */
	public String getSource(Class clz) {
		return getSource(getSourceUrl(clz));
	}

	/**
	 * get java source file content
	 */
	public String getSource(String clzName) {
		return getSource(getSourceUrl(clzName));
	}

	/**
	 * get source file content based on sourceName (a.b.c), and its suffix such
	 * as ".js", ".java", etc.
	 */
	public String getSource(String clzName, String suffix) {
		return getSource(getSourceUrl(clzName, suffix));
	}

	public String getSource(URL url) {
		if (url == null) {
			return null;
		}
		try {
			byte[] buffer = new byte[1024];
			StringWriter sw = new StringWriter();
			InputStream is = url.openStream();
			int i = 0;
			while ((i = is.read(buffer)) > 0) {
				sw.write(new String(buffer, 0, i));
			}
			return sw.toString();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static final String WINDOW_URL_PROTO = "file:/";

	private static final String UNIX_URL_PROTO = "file://";


    private static URL[] getSourcePathUrls(String path, boolean isClzPath)
            throws MalformedURLException {
        String fileUrlProto = s_isUnix ? UNIX_URL_PROTO : WINDOW_URL_PROTO;        
        path = path.replace("\\", "/");
        path = path.replace("//", "/");
        ArrayList<URL> urlList = new ArrayList<URL>();
        StringTokenizer st = new StringTokenizer(path, File.pathSeparator);
        Set<String> uris = new HashSet<String>();
        while (st.hasMoreElements()) {
            String value = st.nextToken();
            if (!s_isUnix && value.startsWith("/")) {
            	value = value.substring(1); //remove incorrect leading '/' for windows
            }
            if (uris.contains(value)) {
            	continue;
            }
            uris.add(value);
            if (value.endsWith(".jar") || value.endsWith(".zip")) {
                if (!isClzPath) {
                    // urlList.add(new URL("jar:file:/" + value + "!/"));
                    urlList.add(new URL("jar:" + fileUrlProto + value + "!/"));
                }
            } else if (!isClzPath || value.endsWith("/src")) {
                if (!value.endsWith("/")) {
                    value += "/";
                }
                urlList.add(new URL(fileUrlProto + value));
            } else if (isClzPath && value.endsWith("/bin")) {
            	String srcFile = value.substring(0, value.length() - 4) + "/src/";           	
            	try {
            		if (new File(srcFile).exists()) {
	            		URL srcUrl = new URL(fileUrlProto + srcFile);
	            		urlList.add(srcUrl);
            		}
            	}
            	catch (Exception e) {
					//source directory is not there
				}
            }
        }
        URL[] urls = new URL[urlList.size()];
        return urlList.toArray(urls);
    }
}
