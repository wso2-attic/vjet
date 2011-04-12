package vjo.net;

/*
 * @(#)src/classes/sov/java/net/URLClassLoader.java, net, asdev, 20070119 1.40
 * ===========================================================================
 * Licensed Materials - Property of IBM
 * "Restricted Materials of IBM"
 *
 * IBM SDK, Java(tm) 2 Technology Edition, v5.0
 * (C) Copyright IBM Corp. 1998, 2005. All Rights Reserved
 * ===========================================================================
 */

/*
 * ===========================================================================
 (C) Copyright Sun Microsystems Inc, 1992, 2004. All rights reserved.
 * ===========================================================================
 */




/*
 *
 * Change activity:
 *
 * Reason  Date   Origin  Description
 * ------  ----   ------  ----------------------------------------------------
 * 003683  051199 hdrl    Sun 1.3 rollup
 * 006740  280100 hdmaw   Sun 1.3R
 * 005654  160600 hdpgr   ScJVM: Loading checked extensions
 * 005654  160600 hdpgr   ScJVM: Loading checked extensions
 * 022900  160800 hdmaw   Replace SCJVM_NO_EXTENSION_LOADER with InternalError
 * 025066  131000 hdejs   ScJVM,PERF: Add ibmJVMGetExcludedFields
 * 025627  241000 hdejs:  ScJVM: Add parameter to SetJVMUnresettable...
 * 026352  221100 hdmaw   ScJVM: Use manifest entry to mark compatible extensions
 * 026494  271100 hdejs   ScJVM: Cross heap reference checking
 * 055138  190902 kennard Improve diagnostics for class loading
 * 055302  250902 kennard Change showloading property to verbose
 * 056111  301002 stalleyj Merge 1.4.1 changes 
 * 055792.1240203 cwhite   Allow EXTENSION_COMPATIBLE in any part of the MANIFEST.MF
 * 060643  130603 nichanir ScJVM: Cache URLClassPath & create lazily.
 * 062522  020703 ansriniv JCK:api/java_net/URLClassLoader/NewInst1Tests.java failed
 * 064539  220903 shankar  Fix in loadClass() for Sun Security Bulletin #00115
 * 064147  211003 riclau   1.4.2 merge (without SecurityConstants dependency)
 * 064147  212003 riclau   1.4.2 merge (SecurityConstants part)
 * 079049  261004 stalleyj Merge to 5.0
 * 082370  260105 stalleyj Merge to build 8  
 * 082771  070205 cwhite   Remove Shiraz code
 * 87929   030505 shankar  Java 5.0 Shared classes related changes
 * 80916.1 090905 riclau   Fix ClassFinder thread safety issue 
 * 094142  230905 riclau   Rework Shared Classes support to handle jars with 
 *                         "Class-Path" attribute (Extension Mechanism)
 * 097113  251005 jury     Temporarily disable trace to fix performance regression
 *
 * ===========================================================================
 * Module Information:
 *
 * DESCRIPTION: URLClassLoader           
 * ===========================================================================
 */

import java.lang.ClassNotFoundException ;
import java.lang.IllegalArgumentException;
import java.lang.SecurityException ;

import java.util.Enumeration;
import java.util.NoSuchElementException;
import java.util.regex.PatternSyntaxException;

import java.io.IOException;

import vjo.lang.* ;
import vjo.lang.SecurityManager;
import vjo.lang.System;

import vjo.lang.reflect.Method;
import vjo.lang.reflect.Modifier;

import vjo.io.File;
import vjo.io.FilePermission;
import vjo.io.InputStream;

import vjo.net.URL;
import vjo.net.URLConnection;
import vjo.net.URLStreamHandlerFactory;

import vjo.util.StringTokenizer;

import vjo.util.Vector;                                            /*ibm@5654*/
import vjo.util.regex.Pattern;                                     /*ibm@55138*/
import vjo.util.regex.Matcher;                                     /*ibm@55138*/
import vjo.util.regex.PatternSyntaxException;                      /*ibm@55138*/

//import java.util.jar.Manifest;
//import java.util.jar.Attributes;
//import java.util.jar.Attributes.Name;

import vjo.security.CodeSigner;
import vjo.security.PrivilegedAction;
import vjo.security.PrivilegedExceptionAction;
import vjo.security.AccessController;
import vjo.security.AccessControlContext;
import vjo.security.SecureClassLoader;
import vjo.security.CodeSource;
import vjo.security.Permission;
import vjo.security.PermissionCollection;

//import sun.misc.Resource;
//import sun.misc.URLClassPath;
//import sun.net.www.ParseUtil;
//import sun.security.util.SecurityConstants;
//import com.ibm.jvm.ClassLoaderDiagnosticsHelper;                    /*ibm@55138*/

//import com.ibm.oti.shared.Shared;                                   /*ibm@87929*/
//import com.ibm.oti.shared.SharedClassHelperFactory;                 /*ibm@87929*/
//import com.ibm.oti.shared.SharedClassURLClasspathHelper;            /*ibm@87929*/
//import com.ibm.oti.shared.HelperAlreadyDefinedException;            /*ibm@87929*/

/**
 * This class loader is used to load classes and resources from a search
 * path of URLs referring to both JAR files and directories. Any URL that
 * ends with a '/' is assumed to refer to a directory. Otherwise, the URL
 * is assumed to refer to a JAR file which will be opened as needed.
 * <p>
 * The AccessControlContext of the thread that created the instance of
 * URLClassLoader will be used when subsequently loading classes and
 * resources.
 * <p>
 * The classes that are loaded are by default granted permission only to
 * access the URLs specified when the URLClassLoader was created.
 *
 * @author  David Connelly
 * @version 1.85, 08/02/04
 * @since   1.2
 */
public class URLClassLoader extends SecureClassLoader {
    /* The search path for classes and resources */
    private URLClassPath ucp;

    /* The context to be used when loading classes and resources */
    private AccessControlContext acc;
    
    /* Private member fields used for Shared classes*/
    private SharedClassURLClasspathHelper sharedClassURLClasspathHelper; /*ibm@87929*/
    private SharedClassMetaDataCache sharedClassMetaDataCache;     /*ibm@94142*/

    /*ibm@87929 
     * Wrapper class for maintaining the index of where the metadata (codesource and manifest)
     * is found - used only in Shared classes context.
     */
    private class SharedClassIndexHolder implements SharedClassURLClasspathHelper.IndexHolder {
        int index;

        public void setIndex(int index) {
            this.index = index;
        }
    }

    /*ibm@94142
     * Wrapper class for internal storage of metadata (codesource and manifest) associated with 
     * shared class - used only in Shared classes context.
     */
    private class SharedClassMetaData {
        private CodeSource codeSource;
        private Manifest manifest;

        SharedClassMetaData(CodeSource codeSource, Manifest manifest) {
            this.codeSource = codeSource;
            this.manifest = manifest;
        }

        public CodeSource getCodeSource() { return codeSource; }
        public Manifest getManifest() { return manifest; }
    }

    /*ibm@94142
     * Represents a collection of SharedClassMetaData objects retrievable by
     * index. 
     */
    private class SharedClassMetaDataCache {
        private final static int BLOCKSIZE = 10;
        private SharedClassMetaData[] store;
        
        public SharedClassMetaDataCache(int initialSize) {
            /* Allocate space for an initial amount of metadata entries */
            store = new SharedClassMetaData[initialSize];
        }

        /** 
         * Retrieves the SharedClassMetaData stored at the given index, or null
         * if no SharedClassMetaData was previously stored at the given index 
         * or the index is out of range.
         */
        public synchronized SharedClassMetaData getSharedClassMetaData(int index) {
            if (index < 0 || store.length < (index+1)) {
                return null;
            }
            return store[index];
        }

        /** 
         * Stores the supplied SharedClassMetaData at the given index in the
         * store. The store will be grown to contain the index if necessary.
         */
        public synchronized void setSharedClassMetaData(int index, 
                                                     SharedClassMetaData data) {
            ensureSize(index);
            store[index] = data;
        }

        /* Ensure that the store can hold at least index number of entries */
        private synchronized void ensureSize(int index) {
            if (store.length < (index+1)) {
                int newSize = (index+BLOCKSIZE);
                SharedClassMetaData[] newSCMDS = new SharedClassMetaData[newSize];
                System.arraycopy(store, 0, newSCMDS, 0, store.length);
                store = newSCMDS;
            }
        }
    }

    /*ibm@94142 
     * Return true if shared classes support is active, otherwise false.
     */
    private boolean usingSharedClasses() {
        return (sharedClassURLClasspathHelper != null);
    }

    /*ibm@94142
     * Initialize support for shared classes.
     */
    private void initializeSharedClassesSupport(URL[] initialClassPath) {
        /* get the Shared class helper and initialize the metadata store if we are sharing */
        SharedClassHelperFactory sharedClassHelperFactory = Shared.getSharedClassHelperFactory();

        if (sharedClassHelperFactory != null) {
            try {
                this.sharedClassURLClasspathHelper = sharedClassHelperFactory.getURLClasspathHelper(this, initialClassPath);
            } catch (HelperAlreadyDefinedException ex) { // thrown if we get 2 types of helper for the same classloader
                ex.printStackTrace();
            }
            /* Only need to create a meta data cache if using shared classes */
            if (usingSharedClasses()) {
                /* Create a metadata cache */
                this.sharedClassMetaDataCache = new SharedClassMetaDataCache(initialClassPath.length);
            }
        } 
    }

    /**
     * Constructs a new URLClassLoader for the given URLs. The URLs will be
     * searched in the order specified for classes and resources after first
     * searching in the specified parent class loader. Any URL that ends with
     * a '/' is assumed to refer to a directory. Otherwise, the URL is assumed
     * to refer to a JAR file which will be downloaded and opened as needed.
     *
     * <p>If there is a security manager, this method first
     * calls the security manager's <code>checkCreateClassLoader</code> method
     * to ensure creation of a class loader is allowed.
     * 
     * @param urls the URLs from which to load classes and resources
     * @param parent the parent class loader for delegation
     * @exception  SecurityException  if a security manager exists and its  
     *             <code>checkCreateClassLoader</code> method doesn't allow 
     *             creation of a class loader.
     * @see SecurityManager#checkCreateClassLoader
     */
    public URLClassLoader(URL[] urls, ClassLoader parent) {
	super(parent);
	// this is to make the stack depth consistent with 1.1
	SecurityManager security = System.getSecurityManager();
	if (security != null) {
	    security.checkCreateClassLoader();
	}
	initializeSharedClassesSupport(urls);                      /*ibm@94142*/
	ucp = new URLClassPath(urls, null, sharedClassURLClasspathHelper);/*ibm@94142*/
	acc = AccessController.getContext();
    }

    /**
     * Constructs a new URLClassLoader for the specified URLs using the
     * default delegation parent <code>ClassLoader</code>. The URLs will
     * be searched in the order specified for classes and resources after
     * first searching in the parent class loader. Any URL that ends with
     * a '/' is assumed to refer to a directory. Otherwise, the URL is
     * assumed to refer to a JAR file which will be downloaded and opened
     * as needed.
     *
     * <p>If there is a security manager, this method first
     * calls the security manager's <code>checkCreateClassLoader</code> method
     * to ensure creation of a class loader is allowed.
     * 
     * @param urls the URLs from which to load classes and resources
     *
     * @exception  SecurityException  if a security manager exists and its  
     *             <code>checkCreateClassLoader</code> method doesn't allow 
     *             creation of a class loader.
     * @see SecurityManager#checkCreateClassLoader
     */
    public URLClassLoader(URL[] urls) {
	super();
	// this is to make the stack depth consistent with 1.1
	SecurityManager security = System.getSecurityManager();
	if (security != null) {
	    security.checkCreateClassLoader();
	}
	initializeSharedClassesSupport(urls);                      /*ibm@94142*/
	ucp = new URLClassPath(urls, null, sharedClassURLClasspathHelper);/*ibm@94142*/
	acc = AccessController.getContext();
    }

    /**
     * Constructs a new URLClassLoader for the specified URLs, parent
     * class loader, and URLStreamHandlerFactory. The parent argument
     * will be used as the parent class loader for delegation. The
     * factory argument will be used as the stream handler factory to
     * obtain protocol handlers when creating new URLs.
     *
     * <p>If there is a security manager, this method first
     * calls the security manager's <code>checkCreateClassLoader</code> method
     * to ensure creation of a class loader is allowed.
     *
     * @param urls the URLs from which to load classes and resources
     * @param parent the parent class loader for delegation
     * @param factory the URLStreamHandlerFactory to use when creating URLs
     *
     * @exception  SecurityException  if a security manager exists and its  
     *             <code>checkCreateClassLoader</code> method doesn't allow 
     *             creation of a class loader.
     * @see SecurityManager#checkCreateClassLoader
     */
    public URLClassLoader(URL[] urls, ClassLoader parent,
			  URLStreamHandlerFactory factory) {
	super(parent);
	// this is to make the stack depth consistent with 1.1
	SecurityManager security = System.getSecurityManager();
	if (security != null) {
	    security.checkCreateClassLoader();
	}
	initializeSharedClassesSupport(urls);                      /*ibm@94142*/
	ucp = new URLClassPath(urls, factory, sharedClassURLClasspathHelper);/*ibm@94142*/
	acc = AccessController.getContext();
    }

    /**
     * Appends the specified URL to the list of URLs to search for
     * classes and resources.
     *
     * @param url the URL to be added to the search path of URLs
     */
    protected void addURL(URL url) {
	ucp.addURL(url);
    }

    /**
     * Returns the search path of URLs for loading classes and resources.
     * This includes the original list of URLs specified to the constructor,
     * along with any URLs subsequently appended by the addURL() method.
     * @return the search path of URLs for loading classes and resources.
     */
    public URL[] getURLs() {
	return ucp.getURLs();
    }

    /*ibm@55138 start*/
    /* Stores a list of classes for which is show detailed class loading */
    private static Vector showClassLoadingFor = null;
    /* Caches whether the vector showClassLoadingFor is empty */
    private static boolean showLoadingMessages = false;
    /* Property to use to setup the detailed classloading output */
    private final static String showClassLoadingProperty = "ibm.cl.verbose"; /*ibm@55302*/

    /*
     * Initializes the showClassLoadingFor vector. All expressions are precompiled
     */
    static
    {
        Vector showClassLoadingFor = new Vector();
        String classes = System.getProperty(showClassLoadingProperty);
        /* If the property exists then process the supplied file expressions */
        if (classes != null) {
            StringTokenizer classMatchers = new StringTokenizer(classes, ",");
            while (classMatchers.hasMoreTokens()) {
                String classMatcher = classMatchers.nextToken();
                /* Do the replacements to allow more readable expressions to be supplied */
                String classMatcherExp = classMatcher.replaceAll("\\.", "\\.");
                classMatcherExp = classMatcherExp.replaceAll("\\*", ".*");
                Pattern pattern;
                /* Add the compiled pattern to the vector */
                try {
                    pattern = Pattern.compile(classMatcherExp);
                    showClassLoadingFor.addElement(pattern);
                } catch (PatternSyntaxException e) {
                    /*
                     * The user has supplied something which has is not
                     * a legal regular expression (or isn't now that it has been
                     * transformed!) Warn the user and ignore this expression
                     */
                    System.err.println("Illegal class matching expression \"" + classMatcher +
                        "\" supplied by property " + showClassLoadingProperty);
                }
            }
        }
        /*
         * Cache whether a check should be made to see whether to show loading messages for
         * a particular class
         */
        if (!showClassLoadingFor.isEmpty()) {
            showLoadingMessages = true;
        }
        URLClassLoader.showClassLoadingFor = showClassLoadingFor;
    }


    /*
     * Returns whether the class loading should be explicitly shown for a
     * particular class. This is determined by the system property ibm.cl.verbose
     * which contains a comma separated list of file expressions.
     * A file expression being a regular expression but with .* substituted for *
     * and \. substituted for . to allow a more readable form to be used
     * If no property exists or contains no expressions then showClassLoadingFor
     * will be an empty vector and this will be the only test each time this function
     * is called. Otherwise name will be matched against each expression in the vector
     * and if a match is found true is returned, otherwise false
     */
    private boolean showClassLoading(String name)
    {
        /* If there are supplied expressions try and match this class name against them */
        if (URLClassLoader.showLoadingMessages) {
            Enumeration patterns = URLClassLoader.showClassLoadingFor.elements();
            while (patterns.hasMoreElements()) {
                Pattern pattern = (Pattern)patterns.nextElement();
                Matcher matcher = pattern.matcher(name);
                if (matcher.matches()) {
                    return true;
                }
            }
        }
        /* Either no expressions or no matches */
        return false;
    }
    /*ibm@55138 end*/

    /**
     * Finds and loads the class with the specified name from the URL search
     * path. Any URLs referring to JAR files are loaded and opened as needed
     * until the class is found.
     *
     * @param name the name of the class
     * @return the resulting class
     * @exception ClassNotFoundException if the class could not be found
     */
    protected Class<?> findClass(final String name)
	 throws ClassNotFoundException
    {
        try {
            boolean scl = showClassLoading(name);
            if (scl) {                                                          /*ibm@55138*/
                ClassLoaderDiagnosticsHelper.attemptingToLoadClass(this, name);/*ibm@55138*/
            }                                                                   /*ibm@55138*/

            /*ibm@87929 start */
            /* Try to find the class from the shared cache using the class name.  If we found the class 
             * and if we have its corresponding metadata (codesource and manifest entry) already cached, 
             * then we define the class passing in these parameters.  If however, we do not have the 
             * metadata cached, then we define the class as normal.  Also, if we do not find the class
             * from the shared class cache, we define the class as normal.
             */
            if (usingSharedClasses()) {
                SharedClassIndexHolder sharedClassIndexHolder = new SharedClassIndexHolder(); /*ibm@94142*/
                byte[] sharedClazz = sharedClassURLClasspathHelper.findSharedClass(name, sharedClassIndexHolder);
                
                if (sharedClazz != null) {
                    int indexFoundData = sharedClassIndexHolder.index;
                    /*ibm@94142 starts*/
                    /*ClassLoaderDiagnosticsHelper.traceFindClassEvent1(this, 
                                                         name, indexFoundData);*/ /*ibm@97113*/

                    SharedClassMetaData metadata = sharedClassMetaDataCache.getSharedClassMetaData(indexFoundData); 
                    if (metadata != null) {
                        /*ClassLoaderDiagnosticsHelper.traceFindClassEvent2(this, 
                                               name, indexFoundData, metadata);*/ /*ibm@97113*/
                        try {
                            Class clazz = defineClass(name,sharedClazz, 
                                               metadata.getCodeSource(), 
                                               metadata.getManifest());
                            if (scl) {
                                ClassLoaderDiagnosticsHelper.loadedClass(this, name);
                            }
                            return clazz;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }                    
                    /*ClassLoaderDiagnosticsHelper.traceFindClassEvent3(this, 
                                                         name, indexFoundData);*/ /*ibm@97113*/
                    /*ibm@94142 ends*/
                }
                /*ClassLoaderDiagnosticsHelper.traceFindClassEvent4(this, name);*/ /*ibm@94142*/ /*ibm@97113*/
            }
            /*ibm@87929 end*/

            ClassFinder loader = new ClassFinder(name, this);    /*ibm@80916.1*/
            Class clazz = (Class)AccessController.doPrivileged(loader, acc);
            if (clazz == null) {                                     /*ibm@802*/
                if (scl) {                                                      /*ibm@55138*/
                    ClassLoaderDiagnosticsHelper.failedToLoadClass(this, name); /*ibm@55138*/
                }                                                               /*ibm@55138*/
                throw new ClassNotFoundException(name);              /*ibm@802*/
            }
            if (scl) {                                                          /*ibm@55138*/
                ClassLoaderDiagnosticsHelper.loadedClass(this, name);           /*ibm@55138*/
            }
            return clazz;                                            /*ibm@802*/
        } catch (java.security.PrivilegedActionException pae) {
            throw (ClassNotFoundException) pae.getException();
        }
    }

    /*
     * Defines a Class using the class bytes obtained from the specified
     * Resource. The resulting Class must be resolved before it can be
     * used.
     */
    private Class defineClass(String name, Resource res) throws IOException {
        Class clazz = null;                 /*ibm@87929*/
        CodeSource cs = null;               /*ibm@87929*/
        Manifest man = null;                /*ibm@87929*/
	int i = name.lastIndexOf('.');
	URL url = res.getCodeSourceURL();   
	if (i != -1) {
	    String pkgname = name.substring(0, i);
	    // Check if package already loaded.
	    Package pkg = getPackage(pkgname);
	    man = res.getManifest();        /*ibm@87929*/
	    if (pkg != null) {
		// Package found, so check package sealing.
		if (pkg.isSealed()) {
		    // Verify that code source URL is the same.
		    if (!pkg.isSealed(url)) {
			throw new SecurityException(
			    "sealing violation: package " + pkgname + " is sealed");
		    }
		} else {
		    // Make sure we are not attempting to seal the package
		    // at this code source URL.
		    if ((man != null) && isSealed(pkgname, man)) {
			throw new SecurityException(
			    "sealing violation: can't seal package " + pkgname + 
			    ": already loaded");
		    }
		}
	    } else {
		if (man != null) {
		    definePackage(pkgname, man, url);
		} else {
                    definePackage(pkgname, null, null, null, null, null, null, null);
                }
	    }
	}
	// Now read the class bytes and define the class
	java.nio.ByteBuffer bb = res.getByteBuffer();
	if (bb != null) {
	    // Use (direct) ByteBuffer:
	    CodeSigner[] signers = res.getCodeSigners();
	    cs = new CodeSource(url, signers);                  /*ibm@87929*/
            clazz = defineClass(name, bb, cs);                  /*ibm@87929*/
	} else {
	    byte[] b = res.getBytes();
	    // must read certificates AFTER reading bytes.
	    CodeSigner[] signers = res.getCodeSigners();
	    cs = new CodeSource(url, signers);                  /*ibm@87929*/
	    clazz = defineClass(name, b, 0, b.length, cs);      /*ibm@87929*/
	}

        /*ibm@87929 start*/
        /*
         * Since we have already stored the class path index (of where this resource came from), we can retrieve 
         * it here.  The storing is done in getResource() in URLClassPath.java.  The index is the specified 
         * position in the URL search path (see getLoader()).  The storeSharedClass() call below, stores the 
         * class in the shared class cache for future use.  
         */
        if (usingSharedClasses()) {
            /*ibm@94142 starts*/
            /* Determine the index into the search path for this class */
            int index = res.getClasspathLoadIndex();
            /* Check to see if we have already cached metadata for this index */
            SharedClassMetaData metadata = sharedClassMetaDataCache.getSharedClassMetaData(index); 
            /* If we have not already cached the metadata for this index... */
            if (metadata == null) {
                /* ... create a new metadata entry */
                metadata = new SharedClassMetaData(cs, man);
                /* Cache the metadata for this index for future use */
                sharedClassMetaDataCache.setSharedClassMetaData(index, metadata);
                /*ClassLoaderDiagnosticsHelper.traceDefineClassEvent1(this, index, 
                                                                     metadata);*/ /*ibm@97113*/
            }            

            boolean storeSuccessful = false;
            try {
                /* Store class in shared class cache for future use */
                storeSuccessful = 
                  sharedClassURLClasspathHelper.storeSharedClass(clazz, index);
            } catch (Exception e) {
                e.printStackTrace();
            }    
            /* ibm@97113 starts
            if (storeSuccessful) {
                ClassLoaderDiagnosticsHelper.traceDefineClassEvent2(this, 
                                                      clazz.toString(), index);
            } else {
                ClassLoaderDiagnosticsHelper.traceDefineClassEvent3(this, 
                                                      clazz.toString(), index);
            }
            ibm@97113 ends*/
            /*ibm@94142 ends*/
        }

        return clazz;
        /*ibm@87929 end*/
    }

    /*ibm@87929 start */
    /*
     * Defines a class using the class bytes, codesource and manifest 
     * obtained from the specified shared class cache. The resulting 
     * class must be resolved before it can be used.  This method is 
     * used only in a Shared classes context. 
     */
    private Class defineClass(String name, byte[] sharedClazz, CodeSource codesource, Manifest man) throws IOException {
	int i = name.lastIndexOf('.');
	URL url = codesource.getLocation();
	if (i != -1) {
	    String pkgname = name.substring(0, i);
	    // Check if package already loaded.
	    Package pkg = getPackage(pkgname);
            if (pkg != null) {
		// Package found, so check package sealing.
		if (pkg.isSealed()) {
		    // Verify that code source URL is the same.
		    if (!pkg.isSealed(url)) {
			throw new SecurityException(
			    "sealing violation: package " + pkgname + " is sealed");
		    }
		} else {
		    // Make sure we are not attempting to seal the package
		    // at this code source URL.
		    if ((man != null) && isSealed(pkgname, man)) {
			throw new SecurityException(
			    "sealing violation: can't seal package " + pkgname + 
			    ": already loaded");
		    }
		}
	    } else {
		if (man != null) {
		    definePackage(pkgname, man, url);
		} else {
                    definePackage(pkgname, null, null, null, null, null, null, null);
                }
	    }
	}
	/* 
         * Now read the class bytes and define the class.  We don't need to call 
         * storeSharedClass(), since its already in our shared class cache.
         */
        return defineClass(name, sharedClazz, 0, sharedClazz.length, codesource);
    }
    /*ibm@87929 end*/

    /**
     * Defines a new package by name in this ClassLoader. The attributes
     * contained in the specified Manifest will be used to obtain package
     * version and sealing information. For sealed packages, the additional
     * URL specifies the code source URL from which the package was loaded.
     *
     * @param name  the package name
     * @param man   the Manifest containing package version and sealing
     *              information
     * @param url   the code source url for the package, or null if none
     * @exception   IllegalArgumentException if the package name duplicates
     *              an existing package either in this class loader or one
     *              of its ancestors
     * @return the newly defined Package object
     */
    protected Package definePackage(String name, Manifest man, URL url)
	throws IllegalArgumentException
    {
	String path = name.replace('.', '/').concat("/");
	String specTitle = null, specVersion = null, specVendor = null;
	String implTitle = null, implVersion = null, implVendor = null;
	String sealed = null;
	URL sealBase = null;

	Attributes attr = man.getAttributes(path);
	if (attr != null) {
	    specTitle   = attr.getValue(Name.SPECIFICATION_TITLE);
	    specVersion = attr.getValue(Name.SPECIFICATION_VERSION);
	    specVendor  = attr.getValue(Name.SPECIFICATION_VENDOR);
	    implTitle   = attr.getValue(Name.IMPLEMENTATION_TITLE);
	    implVersion = attr.getValue(Name.IMPLEMENTATION_VERSION);
	    implVendor  = attr.getValue(Name.IMPLEMENTATION_VENDOR);
	    sealed      = attr.getValue(Name.SEALED);
	}
	attr = man.getMainAttributes();
	if (attr != null) {
	    if (specTitle == null) {
		specTitle = attr.getValue(Name.SPECIFICATION_TITLE);
	    }
	    if (specVersion == null) {
		specVersion = attr.getValue(Name.SPECIFICATION_VERSION);
	    }
	    if (specVendor == null) {
		specVendor = attr.getValue(Name.SPECIFICATION_VENDOR);
	    }
	    if (implTitle == null) {
		implTitle = attr.getValue(Name.IMPLEMENTATION_TITLE);
	    }
	    if (implVersion == null) {
		implVersion = attr.getValue(Name.IMPLEMENTATION_VERSION);
	    }
	    if (implVendor == null) {
		implVendor = attr.getValue(Name.IMPLEMENTATION_VENDOR);
	    }
	    if (sealed == null) {
		sealed = attr.getValue(Name.SEALED);
	    }
	}
	if ("true".equalsIgnoreCase(sealed)) {
	    sealBase = url;
	}
	return definePackage(name, specTitle, specVersion, specVendor,
			     implTitle, implVersion, implVendor, sealBase);
    }

    /*
     * Returns true if the specified package name is sealed according to the
     * given manifest.
     */
    private boolean isSealed(String name, Manifest man) {
	String path = name.replace('.', '/').concat("/");
	Attributes attr = man.getAttributes(path);
	String sealed = null;
	if (attr != null) {
	    sealed = attr.getValue(Name.SEALED);
	}
	if (sealed == null) {
	    if ((attr = man.getMainAttributes()) != null) {
		sealed = attr.getValue(Name.SEALED);
	    }
	}
	return "true".equalsIgnoreCase(sealed);
    }

    /**
     * Finds the resource with the specified name on the URL search path.
     *
     * @param name the name of the resource
     * @return a <code>URL</code> for the resource, or <code>null</code> 
     * if the resource could not be found.
     */
    public URL findResource(final String name) {
	/*
	 * The same restriction to finding classes applies to resources
	 */
	URL url = 
	    (URL) AccessController.doPrivileged(new PrivilegedAction() {
                public Object run() {
                    return ucp.findResource(name, true);
                }
            }, acc);

	return url != null ? ucp.checkURL(url) : null;
    }

    /**
     * Returns an Enumeration of URLs representing all of the resources
     * on the URL search path having the specified name.
     *
     * @param name the resource name
     * @exception IOException if an I/O exception occurs
     * @return an <code>Enumeration</code> of <code>URL</code>s
     */
    public Enumeration<URL> findResources(final String name)
	throws IOException
    {
        final Enumeration e = ucp.findResources(name, true);

	return new Enumeration<URL>() {
	    private URL url = null;

	    private boolean next() {
		if (url != null) {
		    return true;
		}
		do {
		    URL u = (URL)
			AccessController.doPrivileged(new PrivilegedAction() {
			    public Object run() {
				if (!e.hasMoreElements())
                               	    return null;
                            	return e.nextElement();
			    }
			}, acc);
		    if (u == null) 
			break;
		    url = ucp.checkURL(u);
		} while (url == null);
		return url != null;
	    }

	    public URL nextElement() {
		if (!next()) {
		    throw new NoSuchElementException();
		}
		URL u = url;
		url = null;
		return u;
	    }

	    public boolean hasMoreElements() {
		return next();
	    }
	};
    }

    /**
     * Returns the permissions for the given codesource object.
     * The implementation of this method first calls super.getPermissions
     * and then adds permissions based on the URL of the codesource.
     * <p>
     * If the protocol is "file"
     * and the path specifies a file, then permission to read that
     * file is granted. If protocol is "file" and the path is
     * a directory, permission is granted to read all files
     * and (recursively) all files and subdirectories contained in
     * that directory.
     * <p>
     * If the protocol is not "file", then
     * to connect to and accept connections from the URL's host is granted.
     * @param codesource the codesource
     * @return the permissions granted to the codesource
     */
    protected PermissionCollection getPermissions(CodeSource codesource)
    {
	PermissionCollection perms = super.getPermissions(codesource);

	URL url = codesource.getLocation();

	Permission p;
	URLConnection urlConnection;

	try {
	    urlConnection = url.openConnection();
	    p = urlConnection.getPermission();
	} catch (java.io.IOException ioe) {
	    p = null;
	    urlConnection = null;
	}

	if (p instanceof FilePermission) {
	    // if the permission has a separator char on the end,
	    // it means the codebase is a directory, and we need
	    // to add an additional permission to read recursively
	    String path = p.getName();
	    if (path.endsWith(File.separator)) {
		path += "-";
		p = new FilePermission(path, SecurityConstants.FILE_READ_ACTION);
	    }
	} else if ((p == null) && (url.getProtocol().equals("file"))) {
	    String path = url.getFile().replace('/', File.separatorChar);
            path = ParseUtil.decode(path);
	    if (path.endsWith(File.separator))
		path += "-";
	    p =  new FilePermission(path, SecurityConstants.FILE_READ_ACTION);
	} else {
	    URL locUrl = url;
	    if (urlConnection instanceof JarURLConnection) {
		locUrl = ((JarURLConnection)urlConnection).getJarFileURL();
	    }
	    String host = locUrl.getHost();
	    if (host == null)
		host = "localhost";
	    p = new SocketPermission(host,
		SecurityConstants.SOCKET_CONNECT_ACCEPT_ACTION);
	}

	// make sure the person that created this class loader
	// would have this permission

	if (p != null) {
	    final SecurityManager sm = System.getSecurityManager();
	    if (sm != null) {
		final Permission fp = p;
		AccessController.doPrivileged(new PrivilegedAction() {
		    public Object run() throws SecurityException {
			sm.checkPermission(fp);
			return null;
		    }
		}, acc);
	    }
	    perms.add(p);
	}
	return perms;
    }

    /**
     * Creates a new instance of URLClassLoader for the specified
     * URLs and parent class loader. If a security manager is
     * installed, the <code>loadClass</code> method of the URLClassLoader
     * returned by this method will invoke the
     * <code>SecurityManager.checkPackageAccess</code> method before
     * loading the class.
     *
     * @param urls the URLs to search for classes and resources
     * @param parent the parent class loader for delegation
     * @return the resulting class loader
     */
    public static URLClassLoader newInstance(final URL[] urls,
					     final ClassLoader parent) {
	// Save the caller's context
	AccessControlContext acc = AccessController.getContext();
	// Need a privileged block to create the class loader
	URLClassLoader ucl =
	    (URLClassLoader) AccessController.doPrivileged(new PrivilegedAction() {
		public Object run() {
		    return new FactoryURLClassLoader(urls, parent);
		}
	    });
	// Now set the context on the loader using the one we saved,
	// not the one inside the privileged block...
	ucl.acc = acc;
	return ucl;
    }

    /**
     * Creates a new instance of URLClassLoader for the specified
     * URLs and default parent class loader. If a security manager is
     * installed, the <code>loadClass</code> method of the URLClassLoader
     * returned by this method will invoke the
     * <code>SecurityManager.checkPackageAccess</code> before
     * loading the class.
     *
     * @param urls the URLs to search for classes and resources
     * @return the resulting class loader
     */
    public static URLClassLoader newInstance(final URL[] urls) {
	// Save the caller's context
	AccessControlContext acc = AccessController.getContext();
	// Need a privileged block to create the class loader
	URLClassLoader ucl = (URLClassLoader)
	    AccessController.doPrivileged(new PrivilegedAction() {
		public Object run() {
		    return new FactoryURLClassLoader(urls);
		}
	    });

	// Now set the context on the loader using the one we saved,
	// not the one inside the privileged block...
	ucl.acc = acc;
	return ucl;
    }
    
  final class ClassFinder implements PrivilegedExceptionAction           /*ibm@802*/
  {
     private String name;                                                /*ibm@802*/
     private ClassLoader classloader;                                    /*ibm@55138*/

     public ClassFinder(String name, ClassLoader loader) {           /*ibm@80916.1*/
        this.name = name;                                            /*ibm@80916.1*/
        this.classloader = loader;                                   /*ibm@80916.1*/
     }
    
     public Object run() throws ClassNotFoundException {                 /*ibm@802*/
        String path = name.replace('.', '/').concat(".class");           /*ibm@802*/
        try {                                                            /*ibm@802*/
            Resource res = ucp.getResource(path, false, classloader, showClassLoading(name)); /*ibm@802*//*ibm@55138*/
            if (res != null)                                             /*ibm@802*/
                return defineClass(name, res);                           /*ibm@802*/
        } catch (IOException e) {                                        /*ibm@802*/
                throw new ClassNotFoundException(name, e);               /*ibm@802*/
        }                                                                /*ibm@802*/
        return null;                                                     /*ibm@802*/
     }
  }
}

final class FactoryURLClassLoader extends URLClassLoader {

    FactoryURLClassLoader(URL[] urls, ClassLoader parent) {
	super(urls, parent);
    }

    FactoryURLClassLoader(URL[] urls) {
	super(urls);
    }

    public final synchronized Class loadClass(String name, boolean resolve)
	throws ClassNotFoundException
    {
	// First check if we have permission to access the package. This
	// should go away once we've added support for exported packages.
	SecurityManager sm = System.getSecurityManager();
	if (sm != null) {
	    int i = name.lastIndexOf('.');
	    if (i != -1) {
		sm.checkPackageAccess(name.substring(0, i));
	    }
	}
	return super.loadClass(name, resolve);
    }
}

