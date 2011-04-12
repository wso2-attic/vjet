package vjo.javaconversion;

import java.lang.UnsupportedOperationException;

import vjo.lang.* ;

import vjo.io.InputStream;

// for java.lang.Class

public class ClassUtil {
	public static InputStream getResourceAsStream(Class clz, String resName) {
		throw new UnsupportedOperationException("class:getResourceAsStream(String)") ;
//		ClassLoader loader = clz.getClassLoaderImpl();
//		if (loader == ClassLoader.systemClassLoader)
//			return ClassLoader.getSystemResourceAsStream(clz.toResourceName(resName));
//		else
//			return loader.getResourceAsStream(clz.toResourceName(resName));
	}
}
