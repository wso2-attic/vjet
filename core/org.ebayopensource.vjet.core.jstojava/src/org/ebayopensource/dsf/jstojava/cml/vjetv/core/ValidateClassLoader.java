/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.cml.vjetv.core;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import org.ebayopensource.dsf.jstojava.cml.vjetv.util.ClassLoaderUtil;
import org.ebayopensource.dsf.jstojava.cml.vjetv.util.FileOperator;

/**
 * Custom class loader. Wapper the current thread class loader, and the output
 * folder and dependence jar in this class loader. And current thread class
 * loader will be replaced by this class loader.
 * 
 * @author <a href="mailto:liama@ebay.com">liama</a>
 * @since JDK 1.5
 */
public class ValidateClassLoader {

    /**
     * URL class loader to be extended.
     */
    private final URLClassLoader m_loader = null;

    /**
     * constructor.
     * 
     * @param bins
     *            String[] Absolute path of test project output folder and
     *            dependence jars package.
     * @param ep
     *            String[] the exclude pattern for test case class.
     */
    public ValidateClassLoader(String[] bins, String[] ep) {
        URL[] urls = createURL(bins);
        for (int i = 0; i < urls.length; i++) {
            // ClassLoaderUtil.addExtClassPath(bins[i]);
            ClassLoaderUtil.addClassPath2ClassLoader(ValidateClassLoader.class
                    .getClassLoader(), urls[i]);
        }
    }

    /**
     * Add path to source path
     * 
     * @param bins
     *            String[]
     */
    public void addSourcePath(String[] bins) {
        StringBuffer sb = new StringBuffer();
        // if (null != System.getProperty(FileOperator.JAVASOURCEPATH)) {
        // sb.append(System.getProperty(FileOperator.JAVASOURCEPATH));
        // }
        for (int i = 0; i < bins.length; i++) {
            sb.append(FileOperator.SEPARATOR + bins[i]);
        }
        System.setProperty(FileOperator.JAVASOURCEPATH, sb.toString());
    }

    /**
     * collect url from string.
     * 
     * @param bins
     *            Absolute path of test project output folder and dependence
     *            jars package.
     * @return URL[]
     */
    private URL[] createURL(String[] bins) {
        if (null == bins) {
            return new URL[] {};
        }
        URL[] urls = new URL[bins.length];
        for (int i = 0; i < bins.length; i++) {
            File file = new File(bins[i]);
            try {
                urls[i] = file.toURL();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        return urls;
    }

}
