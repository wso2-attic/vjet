/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/* 
 * $Id: ClassLoaderUtil.java, Dec 22, 2009, 8:22:21 PM, liama. Exp$
 *
 * Copyright (c) 2006-2009 Ebay Technologies. All Rights Reserved.
 * This software program and documentation are copyrighted by Ebay 
 * Technologies.
 */
package org.ebayopensource.dsf.jstojava.cml.vjetv.util;

import java.io.File;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;

/**
 * Class/Interface description
 * 
 * @author <a href="mailto:liama@ebay.com">liama</a>
 * @since JDK 1.5
 */
public class ClassLoaderUtil {
    /**
     * The value is used for get parent loader.
     */
    private static Field classes;

    /**
     * The add url method loader.
     */
    private static Method addURL;
    static {
        try {
            classes = ClassLoader.class.getDeclaredField("parent");
            addURL = URLClassLoader.class.getDeclaredMethod("addURL",
                    new Class[] { URL.class });
        } catch (Exception e) {
            e.printStackTrace();
        }
        classes.setAccessible(true);
        addURL.setAccessible(true);
    }

    /**
     * The value is used for system loader
     */
    private static URLClassLoader system = (URLClassLoader) getSystemClassLoader();

    /**
     * The value is used for external loader
     */
    private static URLClassLoader ext = (URLClassLoader) getExtClassLoader();

    /**
     * Get system loader
     * 
     * @return {@link ClassLoader}
     */
    public static ClassLoader getSystemClassLoader() {
        return ClassLoader.getSystemClassLoader();
    }

    /**
     * Get external loader
     * 
     * @return {@link ClassLoader}
     */
    public static ClassLoader getExtClassLoader() {
        return getSystemClassLoader().getParent();
    }

    /**
     * Get sub class loader from system class loader
     * 
     * @return {@link List}
     */
    public static List getClassesLoadedBySystemClassLoader() {
        return getClassesLoadedByClassLoader(getSystemClassLoader());
    }

    /**
     * Get sub class loader from external class loader
     * 
     * @return {@link List}
     */
    public static List getClassesLoadedByExtClassLoader() {
        return getClassesLoadedByClassLoader(getExtClassLoader());
    }

    /**
     * Get sub class loader from  class loader
     * 
     * @param cl {@link ClassLoader}
     * @return {@link List}
     */
    public static List getClassesLoadedByClassLoader(ClassLoader cl) {
        try {
            return (List) classes.get(cl);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get system urls
     * 
     * @return {@link URL}
     */
    public static URL[] getSystemURLs() {
        return system.getURLs();
    }

    /**
     * Get external urls
     * 
     * @return {@link URL}
     */
    public static URL[] getExtURLs() {
        return ext.getURLs();
    }

    /**
     * Print class path to specify stream
     * 
     * @param ps {@link PrintStream}
     * @param classPath {@link URL}
     */
    private static void list(PrintStream ps, URL[] classPath) {
        for (int i = 0; i < classPath.length; i++) {
            ps.println(classPath[i]);
        }
    }

    /**
     * list system class path
     */
    public static void listSystemClassPath() {
        listSystemClassPath(System.out);
    }

    /**
     * Print system class path
     * 
     * @param ps {@link PrintStream}
     */
    public static void listSystemClassPath(PrintStream ps) {
        ps.println("SystemClassPath:");
        list(ps, getSystemClassPath());
    }

    /**
     * List external class path
     */
    public static void listExtClassPath() {
        listExtClassPath(System.out);
    }

    /**
     * Print external class path
     * 
     * @param ps {@link PrintStream}
     */
    public static void listExtClassPath(PrintStream ps) {
        ps.println("ExtClassPath:");
        list(ps, getExtClassPath());
    }

    /**
     * Get system class path
     * 
     * @return {@link URL}
     */
    public static URL[] getSystemClassPath() {
        return getSystemURLs();
    }

    /**
     * Get external class path
     * 
     * @return {@link URL}
     */
    public static URL[] getExtClassPath() {
        return getExtURLs();
    }

    /**
     * Add user specify URL to system class loader
     * 
     * @param url {@link URL}
     */
    public static void addURL2SystemClassLoader(URL url) {
        try {
            addURL.invoke(system, new Object[] { url });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Add user specify URL to external class loader
     * 
     * @param url {@link URL}
     */
    public static void addURL2ExtClassLoader(URL url) {
        try {
            addURL.invoke(ext, new Object[] { url });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Add user specify String to class loader
     * 
     * @param path {@link String}
     */
    public static void addClassPath(String path) {
        addClassPath(new File(path));
    }

    /**
     * Add user specify String to external class loader
     * 
     * @param path {@link String}
     */
    public static void addExtClassPath(String path) {
        addExtClassPath(new File(path));
    }

    /**
     * Add user specify File to  class loader
     * 
     * @param dirOrJar {@link File}
     */
    public static void addClassPath(File dirOrJar) {
        try {
            addURL2SystemClassLoader(dirOrJar.toURL());
        } catch (MalformedURLException e) {
            //throw new RootException(e);
            e.printStackTrace();
        }
    }

    /**
     * Add user specify File to external class loader
     * 
     * @param dirOrJar {@link File}
     */
    public static void addExtClassPath(File dirOrJar) {
        try {
            addURL2ExtClassLoader(dirOrJar.toURL());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add specify path to specify loader
     * 
     * @param cl {@link ClassLoader}
     * @param path String
     */
    public static void addClassPath2ClassLoader(ClassLoader cl, String path) {
        try {
            addURL.invoke(cl, new File(path).toURL());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add specify path to specify loader
     * 
     * @param cl {@link ClassLoader}
     * @param path String
     */
    public static void addClassPath2ClassLoader(ClassLoader cl, URL path) {
        try {
            addURL.invoke(cl, path);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}