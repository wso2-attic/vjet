/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.lib;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ebayopensource.dsf.jst.IJstSerializer;
import org.ebayopensource.dsf.jst.ts.util.ISdkEnvironment;
import org.ebayopensource.dsf.common.resource.ResourceUtil;

/**
 * Helper class for JsNative serialized resource
 *
 */
public class ResourceHelper implements IResourceResolver {
	
	private static String JS_NATIVE_ANCHOR = 
		"org.ebayopensource.jsnative.generated.JsNativeAnchor";
	private static String VJO_JAVA_LIB_ANCHOR = 
		"vjo.generated.JstLibAnchor";
	private static String VJO_LIB_ANCHOR = 
		"org.ebayopensource.vjolib.generated.VjoLibAnchor";
	private static String JS_NATIVE_GLOBAL = 
		JS_NATIVE_ANCHOR + "_GLOBAL";
	
	private static Map<String,Class> s_anchorClass = new HashMap<String,Class>();
	private static Map<String,String> s_jstLibFileName = new HashMap<String,String>();
	
	private static ISdkEnvironment s_sdkEnvironment;
	private static List<String> sdkLibs = new ArrayList<String>();
	
	private IJstSerializer m_jstSerializer = null;
	
	//
	// Singleton
	//
	private static ResourceHelper s_instance = new ResourceHelper();
	protected ResourceHelper(){}
	public static ResourceHelper getInstance(){
		return s_instance;
		
	}
	/**
	 * Returns the input stream for JsNative resource that contains serialized 
	 * JstTypes for all JsNative types (client-side, DOM, and global types)
	 * @return InputStream
	 */
	@Override
	public InputStream getJsNativeSerializedStream() {
		try {
			Class anchorClass = getAnchorClass(JS_NATIVE_ANCHOR);
			String jsNativeFile = getJsBrowserFileName();
			return ResourceUtil.getMandatoryResourceAsStream(anchorClass, jsNativeFile);
		} catch (Exception e) {
			throw new RuntimeException("Could not load JsNative resource", e);
		}
	}
	
	/**
	 * Returns the input stream for JsNative resource that contains serialized 
	 * JstTypes for global JsNative types (Array, Boolean, Number, String, etc.)
	 * @return InputStream
	 */
	@Override
	public InputStream getJsNativeGlobalSerializedStream() {
		try {
			Class anchorClass = getAnchorClass(JS_NATIVE_ANCHOR);
			String jsNativeFile = getJsNativeGlobalObjectsFileName();
			return ResourceUtil.getMandatoryResourceAsStream(anchorClass, jsNativeFile);
		} catch (Exception e) {
			throw new RuntimeException("Could not load JsNative resource", e);
		}
	}
	
	/**
	 * Returns the input stream for a file containing serialized JstTypes for
	 * VjoJavaLib
	 * @return InputStream
	 */
	@Override
	public InputStream getVjoJavaLibSerializedStream() {
		try {
			Class anchorClass = getAnchorClass(VJO_JAVA_LIB_ANCHOR);
			String jstFilename = getVjoJstLibFileName();
			return ResourceUtil.getMandatoryResourceAsStream(anchorClass, jstFilename);
		} catch (Exception e) {
			throw new RuntimeException("Could not load VjoJavaLib resource", e);
		}
	}
		
	/**
	 * Returns the anchor class
	 * @return Class
	 */
	public Class getAnchorClass(String className) {
		Class anchor = s_anchorClass.get(className);
		//Load from SDK environment
		try {
			if (s_sdkEnvironment != null) {
				anchor = s_sdkEnvironment.getAnchorClass(className);
				sdkLibs.add(className);
				synchronized (s_anchorClass) {
					s_anchorClass.put(className, anchor);
				}
			}
		} catch (ClassNotFoundException e) {
			//TODO add log here
		}
		
		//Load from Bundle environment
		if (anchor == null) {
			try {
				anchor = loadClass(className);
				synchronized (s_anchorClass) {
					s_anchorClass.put(className, anchor);
				}
			} catch (ClassNotFoundException e) {
				throw new RuntimeException(className + " class doesn't exist");
			}
		}
		return anchor;
	}
	
	/**
	 * Returns the name of the file what contains serialized 
	 * JstTypes for VjoJavaLib
	 * @return String
	 */
	public String getVjoJstLibFileName() {
		String name = s_jstLibFileName.get(VJO_JAVA_LIB_ANCHOR);
		if (name == null) {
			try {
				Field prefix = getAnchorClass(VJO_JAVA_LIB_ANCHOR).getDeclaredField(
					"JST_LIB_FILE_NAME");
				name = prefix.get(null).toString();
				synchronized (s_jstLibFileName) {
					s_jstLibFileName.put(VJO_JAVA_LIB_ANCHOR, name);
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			} 
		}
		return name;
	}
	
	/**
	 * Returns the name of the file what contains serialized 
	 * JstTypes for VjoLib
	 * @return String
	 */
	public String getVjoLibFileName() {
		String name = s_jstLibFileName.get(VJO_LIB_ANCHOR);
		if (name == null) {
			try {
				Field prefix = getAnchorClass(VJO_LIB_ANCHOR).getDeclaredField(
					"VJO_API_FILE_NAME");
				name = prefix.get(null).toString();
				synchronized (s_jstLibFileName) {
					s_jstLibFileName.put(VJO_LIB_ANCHOR, name);
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			} 
		}
		return name;
	}

	/**
	 * Returns the name of the file what contains serialized 
	 * JstTypes for all JsNative types (browser types)
	 * @return String
	 */
	public String getJsBrowserFileName() {
		String name = s_jstLibFileName.get(JS_NATIVE_ANCHOR);
		if (name == null) {
			try {
				Field prefix = getAnchorClass(JS_NATIVE_ANCHOR).getDeclaredField(
					"JS_NATIVE_PREFIX");
				Field suffix = getAnchorClass(JS_NATIVE_ANCHOR).getDeclaredField(
					"JS_NATIVE_SER_FILE_SUFFIX");
				name = prefix.get(null).toString()+suffix.get(null).toString();
				synchronized (s_jstLibFileName) {
					s_jstLibFileName.put(JS_NATIVE_ANCHOR, name);
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			} 
		}
		return name;
	}
	
	/**
	 * Returns the name of the file what contains serialized 
	 * JstTypes for global JsNative types (Array, Boolean, Number, String, etc.)
	 * @return String
	 */
	public String getJsNativeGlobalObjectsFileName() {
		String name = s_jstLibFileName.get(JS_NATIVE_GLOBAL);
		if (name == null) {
			try {
				Field prefix = getAnchorClass(JS_NATIVE_ANCHOR).getDeclaredField(
					"JS_NATIVE_GLOBAL_PREFIX");
				Field suffix = getAnchorClass(JS_NATIVE_ANCHOR).getDeclaredField(
					"JS_NATIVE_SER_FILE_SUFFIX");
				name = prefix.get(null).toString()+suffix.get(null).toString();
				synchronized (s_jstLibFileName) {
					s_jstLibFileName.put(JS_NATIVE_GLOBAL, name);
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			} 
		}
		return name;
	}

	@Override
	public InputStream getVjoLibSerializedStream() {
		try {
			Class anchorClass = getAnchorClass(VJO_LIB_ANCHOR);
			String jstFilename = getVjoLibFileName();
			return ResourceUtil.getMandatoryResourceAsStream(anchorClass, jstFilename);
		} catch (Exception e) {
			throw new RuntimeException("Could not load VjoLib resource", e);
		}
	}
	
	@Override
	public IJstSerializer getJstSerializer() {
		return m_jstSerializer;
	}

	//
	// API
	//
	public void setJstSerializer(final IJstSerializer jstSerializer){
		m_jstSerializer = jstSerializer;
	}

	public ResourceHelper setSdkEnvironment(ISdkEnvironment environment) {
		s_sdkEnvironment = environment;
		return this;
	}
	
	public static boolean ifLibFromSdk(String className) {
		return sdkLibs.contains(className);
	}
	
	public static String getStringContent(final URL url) {
		final InputStream inputStream;
		try {
			inputStream = url.openStream();
		} catch (IOException e) {
			throw new RuntimeException(url.toExternalForm(), e);
		}
		final ByteArrayOutputStream os = new ByteArrayOutputStream(4096);
		final byte [] buffer = new byte[4096];
		try {
			try {
				do {
					final int numBytesXferred = inputStream.read(buffer);
					if (numBytesXferred == -1 ) {
						break ; // EOF
					}
					assert numBytesXferred >= 0;
					os.write(buffer, 0, numBytesXferred);
				} while (true);
			} finally {
				inputStream.close();
			}
			final String scriptText = os.toString("utf-8");
			return scriptText;
		} catch (IOException e) {
			throw new RuntimeException("can not load '" +
				url.toExternalForm() + "'", e);
		}
	}

	//
	// Protected
	//
	protected Class<?> loadClass(final String clsName) throws ClassNotFoundException{
		return Class.forName(clsName);
	}
}
