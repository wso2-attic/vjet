package vjo.java.sun.text.resources;

/*jadclipse*/// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
//Jad home page: http://www.kpdus.com/jad.html
//Decompiler options: packimports(3) radix(10) lradix(10) 
//Source File Name:   LocaleData.java
import java.lang.AssertionError ;

import vjo.java.lang.* ;

import vjo.java.lang.System ;
import vjo.java.lang.StringBuilder ;

import vjo.java.io.File ;

import vjo.java.javaconversion.CodeNotConvertedException;

import vjo.java.security.AccessController;
import vjo.java.security.PrivilegedAction;
import vjo.util.*;

import vjo.java.sun.security.action.GetPropertyAction;

import vjo.java.net.URI ;
import vjo.java.net.URL ;
import vjo.java.net.URISyntaxException;
import vjo.java.net.URLClassLoader ;

//import java.net.*;

//import java.util.zip.ZipEntry;
//import java.util.zip.ZipInputStream;

//import sun.misc.Launcher;

public class LocaleData {

	public LocaleData() {
	}

	public static Locale[] getAvailableLocales(String s) {
		if (localeList == null)
			synchronized (vjo.sun.text.resources.LocaleData.class) {
				if (localeList == null)
					localeList = createLocaleList();
			}
		Locale alocale[] = new Locale[localeList.length];
		System.arraycopy(localeList, 0, alocale, 0, localeList.length);
		return alocale;
	}

	public static ResourceBundle getLocaleElements(Locale locale) {
		return getBundle("sun.text.resources.LocaleElements", locale);
	}

	public static ResourceBundle getDateFormatZoneData(Locale locale) {
		return getBundle("sun.text.resources.DateFormatZoneData", locale);
	}

	private static ResourceBundle getBundle(final String s, final Locale locale) {
		return (ResourceBundle) AccessController
				.doPrivileged(new PrivilegedAction() {

					public Object run() {
						return ResourceBundle.getBundle(s, locale);
					}

//MrP - JAD casuality
//					final String val$baseName;
//
//					final Locale val$locale;
//
//					{
//						baseName = s;
//						locale = locale1;
//						super();
//					}
//					final String baseName = s ;
//					final Locale locale1 = locale ;
				});
	}

	private static Locale[] createLocaleList() {
		String s = (String) AccessController
				.doPrivileged(new GetPropertyAction("sun.boot.class.path"));
		String s1 = (String) AccessController
				.doPrivileged(new GetPropertyAction("java.class.path"));
		if (s1 != null && s1.length() != 0)
			s = (new StringBuilder()).append(s).append(File.pathSeparator)
					.append(s1).toString();
		String s2;
		for (; s != null && s.length() != 0; classPathSegments.insertElementAt(
				s2, 0)) {
			int i = s.lastIndexOf(File.pathSeparatorChar);
			s2 = s.substring(i + 1);
			if (i == -1)
				s = null;
			else
				s = s.substring(0, i);
		}

		ClassLoader classloader = Launcher.getLauncher().getClassLoader();
		URLClassLoader urlclassloader = (URLClassLoader) classloader
				.getParent();
		URL aurl[] = urlclassloader.getURLs();
		for (int j = 0; j < aurl.length; j++) {
			try {
				URI uri = new URI(aurl[j].toString());
				classPathSegments.insertElementAt(uri.getPath(), 0);
				continue;
			} catch (URISyntaxException urisyntaxexception) {
			}
			if (!assertionsDisabled)
				throw new AssertionError();
		}

		String as[] = (String[]) (String[]) AccessController
				.doPrivileged(new PrivilegedAction() {

					public Object run() {
						return LocaleData.getClassList("sun.text.resources",
								"LocaleElements_");
					}

				});
		int k = "LocaleElements_".length();
		Locale alocale[] = new Locale[as.length];
		for (int l = 0; l < as.length; l++) {
			boolean flag1 = false;
			int k1 = as[l].indexOf('_', k);
			String s3 = "";
			String s4 = "";
			String s5 = "";
			if (k1 == -1) {
				s3 = as[l].substring(k);
			} else {
				s3 = as[l].substring(k, k1);
				int i1 = as[l].indexOf('_', k1 + 1);
				if (i1 == -1) {
					s4 = as[l].substring(k1 + 1);
				} else {
					s4 = as[l].substring(k1 + 1, i1);
					if (i1 < as[l].length())
						s5 = as[l].substring(i1 + 1);
				}
			}
			alocale[l] = new Locale(s3, s4, s5);
		}

		for (boolean flag = true; flag;) {
			flag = false;
			int j1 = 0;
			while (j1 < as.length - 1) {
				if (alocale[j1].getLanguage().compareTo(
						alocale[j1 + 1].getLanguage()) > 0) {
					Locale locale = alocale[j1];
					alocale[j1] = alocale[j1 + 1];
					alocale[j1 + 1] = locale;
					flag = true;
				} else if (alocale[j1].getLanguage().equals(
						alocale[j1 + 1].getLanguage())
						&& alocale[j1].getCountry().compareTo(
								alocale[j1 + 1].getCountry()) > 0) {
					Locale locale1 = alocale[j1];
					alocale[j1] = alocale[j1 + 1];
					alocale[j1 + 1] = locale1;
					flag = true;
				}
				j1++;
			}
		}

		return alocale;
	}

	private static String[] getClassList(String s, String s1) {
		Vector vector = new Vector();
		String s2 = (new StringBuilder()).append(
				s.replace('.', File.separatorChar)).append(File.separatorChar)
				.toString();
		String s3 = (new StringBuilder()).append(s.replace('.', '/')).append(
				'/').toString();
		for (int i = 0; i < classPathSegments.size(); i++) {
			String s4 = (String) classPathSegments.elementAt(i);
			File file = new File(s4);
			if (!file.exists())
				continue;
			if (file.isFile()) {
				scanFile(file, s3, vector, s1);
				continue;
			}
			if (!file.isDirectory())
				continue;
			String s5;
			if (s4.endsWith(File.separator))
				s5 = (new StringBuilder()).append(s4).append(s2).toString();
			else
				s5 = (new StringBuilder()).append(s4)
						.append(File.separatorChar).append(s2).toString();
			File file1 = new File(s5);
			if (file1.exists() && file1.isDirectory())
				scanDir(file1, vector, s1);
		}

		String as[] = new String[vector.size()];
		vector.copyInto(as);
		return as;
	}

	private static void addClass(String s, Vector vector, String s1) {
		if (s != null && s.startsWith(s1) && !vector.contains(s))
			vector.addElement(s);
	}

	private static String midString(String s, String s1, String s2) {
		String s3;
		if (s.startsWith(s1) && s.endsWith(s2))
			s3 = s.substring(s1.length(), s.length() - s2.length());
		else
			s3 = null;
		return s3;
	}

	private static void scanDir(File file, Vector vector, String s) {
		String as[] = file.list();
		for (int i = 0; i < as.length; i++)
			addClass(midString(as[i], "", ".class"), vector, s);

	}

	private static void scanFile(File file, String s, Vector vector, String s1) {
//MrP - needs implementation...
		throw new CodeNotConvertedException("LocaleData::scanFile(File file, String s, Vector vector, String s1)") ;
//		try {
//			ZipInputStream zipinputstream = new ZipInputStream(
//					new FileInputStream(file));
//			do {
//				ZipEntry zipentry;
//				if ((zipentry = zipinputstream.getNextEntry()) == null)
//					break;
//				String s2 = zipentry.getName();
//				if (s2.startsWith(s) && s2.endsWith(".class"))
//					addClass(midString(s2, s, ".class"), vector, s1);
//			} while (true);
//		} catch (FileNotFoundException filenotfoundexception) {
//			System.out.println((new StringBuilder()).append("file not found:")
//					.append(filenotfoundexception).toString());
//		} catch (IOException ioexception) {
//			System.out.println((new StringBuilder()).append(
//					"file IO Exception:").append(ioexception).toString());
//		} catch (Exception exception) {
//			System.out.println((new StringBuilder()).append("Exception:")
//					.append(exception).toString());
//		}
	}

	private static Vector classPathSegments = new Vector();

	private static Locale localeList[];

	private static final String PACKAGE = "sun.text.resources";

	private static final String PREFIX = "LocaleElements_";

	private static final char ZIPSEPARATOR = 47;

//MrP - JAD casuality
//	static final boolean $assertionsDisabled = !sun / text / resources
//	/ LocaleData.desiredAssertionStatus();
	static final boolean assertionsDisabled = true ;
}


/*
	DECOMPILATION REPORT

	Decompiled from: C:\opt\java5-ibm-2007-12-13\jre\lib\core.jar
	Total time: 31 ms
	Jad reported messages/errors:
Overlapped try statements detected. Not all exception handlers will be resolved in the method getAvailableLocales
	Exit status: 0
	Caught exceptions:
*/
