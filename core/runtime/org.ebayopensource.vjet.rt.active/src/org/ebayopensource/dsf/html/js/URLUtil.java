/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.js;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.ebayopensource.dsf.common.exceptions.DsfRuntimeException;

public class URLUtil {
	/**
	 * Get the absolute url from the base URL
	 */
	static public String getAbsoluteURL(String url, String base_url) {
		if (url == null) {
			return null;
		}
		//remove the all <return> in the url string
		int size = url.length();
		StringBuffer buf = new StringBuffer(size);
		for (int i = 0; i < size; i++) {
			char c = url.charAt(i);
			switch (c) {
				case '\n' :
					break; //skipt
				default :
					buf.append(c);
					break;
			}
		}
		url = buf.toString();

		String r;
		if (URLUtil.isFullURL(url)) {
			r = url;
		} else if (URLUtil.isAbsoluteURL(url)) {
			if (base_url == null || !URLUtil.isFullURL(base_url))
				r = url;
			else {
				r = URLUtil.getHostURL(base_url);
				r = URLUtil.appendPath(r, url);
			}
		} else if (URLUtil.isAbsoluteURLWithHost(url)) {
			if (base_url == null || !URLUtil.isFullURL(base_url))
				r = url;
			else {
				String p = URLUtil.getURLProtocol(base_url);
				r = p + ":" + url;
			}
		} else if (URLUtil.isRelativeURL(url)) {
			if (base_url == null)
				throw new IllegalArgumentException(
					"Base url cannot be null for url: " + url);
			r = URLUtil.appendPath(base_url, url);
		} else {
			throw new IllegalArgumentException("Invalid url: " + url);
		}

		return r;
	}

	/**
	 * Append URL with a sub path. It make sure the <code>/</code>'s are
	 * handled properly
	 */
	static public String appendPath(String url, String sub) {
		if (url == null || sub == null) {
			if (url == null)
				return sub;
			else
				return url;
		}
		if (!url.endsWith("/"))
			url += "/";
		if (sub.startsWith("/"))
			sub = sub.substring(1);
		return url + sub;
	}

	/**
	 * Return the sub path.
	 */
	static public String getSubPath(String root, String path) {
		String r = root;
		String p = path;

		if (!r.endsWith("/"))
			r = r + "/";
		if (!p.endsWith("/"))
			p = p + "/";

		if (!r.startsWith("/"))
			r = "/" + r + "/";
		if (!p.startsWith("/"))
			p = "/" + p;

		if (!p.startsWith(r)) {
			throw new DsfRuntimeException(
				"url path \"" + path + "\" is under root \"" + root + "\"");
		}
		String sub = p.substring(r.length());
		if (!path.endsWith("/"))
			sub = sub.substring(0, p.length() - 1);
		return sub;
	}

	/**
	 * Check if specified string is well formed
	 */
	static public boolean isWellFormedURL(String url_str) {
		if (url_str == null)
			return false;
		try {
			new URL(url_str);
			return true;
		} catch (MalformedURLException e) {
			return false;
		}
	}

	/**
	 * create URL from a URL string
	 */
	static public URL createURL(String url_str) {
		try {
			URL url = new URL(url_str);
			return url;
		} catch (MalformedURLException e) {
			throw new DsfRuntimeException("Invalid URL string", e);
		}
	}

	/**
	 * Encode a string into x-www-form-urlencoded format.
	 */
	static public String URLEncode(String str) {
		return URLEncode(str, null);
	}

	static public String URLEncode(String str, String encoding) {
		if (str == null)
			return null;
		if (encoding != null) {
			StringBuffer sb = new StringBuffer();
			try {
				byte[] bytes = str.getBytes(encoding);
				int length = bytes.length;
				for (int i = 0; i < length; i++) {
					char c = (char) bytes[i];
					if ((c >= 'a' && c <= 'z')
						|| (c >= 'A' && c <= 'Z')
						|| (c >= '0' && c <= '9')
						|| c == '.') {
						sb.append(c);
					} else if (c == ' ') {
						sb.append('+');
					} else {
						String encodedStr = Integer.toHexString(c);
						int size = encodedStr.length();
						if (size >= 2) {
							encodedStr = encodedStr.substring(size - 2, size);
							sb.append("%").append(encodedStr);
						} else
							sb.append(c);
					}
				}
			} catch (java.io.UnsupportedEncodingException e) {
			}
			return sb.toString();
		}
		return java.net.URLEncoder.encode(str);
	}

	/**
	 * Decode a string from x-www-form-urlencoded format.
	 */
	static public String URLDecode(String str) throws Exception {
		if (str == null)
			return null;
		return java.net.URLDecoder.decode(str);
	}

	public static String URLDecode(String s, String encoding) {
		StringBuffer sb = new StringBuffer();
		int length = s.length();
		for (int i = 0; i < length; i++) {
			char c = s.charAt(i);
			switch (c) {
				case '+' :
					sb.append(' ');
					break;
				case '%' :
					try {
						sb.append(
							(char) Integer.parseInt(
								s.substring(i + 1, i + 3),
								16));
					} catch (NumberFormatException e) {
					}
					i += 2;
					break;
				default :
					sb.append(c);
					break;
			}
		}
		//convert to external encoding
		String result = sb.toString();
		try {
			result = new String(result.getBytes("8859_1"), encoding);
		} catch (java.io.UnsupportedEncodingException e) {
		}

		return result;
	}

	/**
	 * Returns the query string for an URL str. If there is no query string, it returns
	 * an empty string.
	 */
	static public String getQuery(String url_str) {
		if (url_str == null)
			return "";

		int i = url_str.indexOf('?');
		if (i < 0)
			return "";
		else
			return url_str.substring(i + 1);
	}

	/**
	 * Returns the URL string up to the query string.
	 */
	static public String getURLWithoutQuery(String url_str) {
		if (url_str == null)
			return "";

		int i = url_str.indexOf('?');
		if (i < 0)
			return url_str;
		else
			return url_str.substring(0, i);
	}

	/**
	 * Returns the URL host
	 */
	static public String getHostURL(String url_str) {
		if (url_str == null)
			return null;

		String s = url_str;
		int i = s.indexOf("://");
		if (i > -1)
			i += 3;
		else
			i = 0;
		i = s.indexOf('/', i);
		if (i > -1)
			s = s.substring(0, i);
		return s;
	}

	/**
	 * Returns the URL host
	 */
	static public String getURLHost(String url_str) {
		if (url_str == null)
			return null;

		String s = url_str;
		int i = s.indexOf("://");
		if (i > -1) {
			s = s.substring(i + 3);
		}
		i = s.indexOf('/');
		if (i > -1) {
			s = s.substring(0, i);
		}
		return s;
	}

	/**
	 * Returns the URL host
	 */
	static public String getURLProtocol(String url_str) {
		if (url_str == null)
			return null;

		String s = url_str;
		int i = s.indexOf("://");
		if (i > -1) {
			s = s.substring(0, i);
			return s;
		} else
			return null;
	}

	/**
	 * Returns the path portion of the URL without path parameters and anchor(;...)
	 */
	static public String getURLPath(String url_str) {
		if (url_str == null)
			return null;

		String s = url_str;
		int i = s.lastIndexOf(';');
		if (i > -1)
			s = s.substring(0, i);

		i = s.lastIndexOf('#');
		if (i > -1)
			s = s.substring(0, i);

		i = s.indexOf("://");
		if (i > -1) {
			s = s.substring(3 + i);
		}
		i = s.indexOf('/');
		if (i > -1) {
			s = s.substring(i);
		} else {
			return "/";
		}
		i = s.indexOf('?');
		if (i > -1) {
			s = s.substring(0, i);
		}
		return s;
	}

	/**
	 * get the base URL from a connection
	 */
	static public String getBaseURL(URLConnection conn) {
		String url = conn.getURL().toExternalForm();
		return getBaseURL(url);
	}

	/**
	 * Returns the base URL with ending slash
	 */
	static public String getBaseURL(String url_str) {
		if (url_str == null)
			return null;

		String s = url_str;
		int i = s.lastIndexOf(';');
		if (i > -1)
			s = s.substring(0, i);

		i = s.lastIndexOf('#');
		if (i > -1)
			s = s.substring(0, i);

		i = s.lastIndexOf('?');
		if (i > -1)
			s = s.substring(0, i);

		i = s.indexOf("://");
		if (i > 0)
			i += 4;
		int j = s.lastIndexOf('/');
		if (j > i) {
			s = s.substring(0, j);
		}
		if (!s.endsWith("/"))
			s = s + "/";
		return s;
	}

	/**
	 * Returns the root URL string with ending <code>/</code>.
	 */
	static public String getRootURL(String url_str) {
		if (!hasProtocol(url_str))
			throw new IllegalArgumentException(
				"URL " + url_str + " does not has protocol");

		int i = url_str.indexOf("://");
		if (i < 0)
			throw new IllegalArgumentException(
				"URL " + url_str + " is not valid");
		int j = url_str.indexOf("/", i + 3);
		String r;
		if (j < 0)
			r = url_str;
		else
			r = url_str.substring(0, j + 1);
		return r;
	}

	/**
	 * Check if the given url string is a complete URL
	 */
	static public boolean isFullURL(String str) {
		return hasProtocol(str);
	}

	/**
	 * Check if the given url string is the absolute URL starting with
	 * <code>/<code> and without protocol nor host portion
	 */
	static public boolean isAbsoluteURL(String str) {
		return (str.startsWith("/") && !str.startsWith("//"));
	}

	/**
	 * Check if the given url string is the absolute URL starting with
	 * <code>/<code> and without protocol nor host portion
	 */
	static public boolean isAbsoluteURLWithHost(String str) {
		return (str.startsWith("//"));
	}

	/**
	 * Check if the given url string is the relative URL without starting
	 * <code>/<code> and without protocol nor host portion
	 */
	static public boolean isRelativeURL(String str) {
		return (!str.startsWith("/") && !hasProtocol(str));
	}

	/**
	 * Check if the given url string has protocol portion.
	 */
	static public boolean hasProtocol(String str) {
		return (str != null)
			&& (str.startsWith("https://")
				|| str.startsWith("http://")
				|| str.startsWith("file://"));

	}

	public static InputStream getInputStream(HttpURLConnection conn) {
		return getInputStream(conn, false); //only get valid inputStream
	}

	public static InputStream getInputStream(
		HttpURLConnection conn,
		boolean includeError) {
		InputStream stream = null;
		try {
			int code = HttpURLConnection.HTTP_OK;
			try {
				code = conn.getResponseCode();
			}
			//catch (java.io.FileNotFoundException exp) {
			catch (java.io.IOException exp) {
				//this exception is due to JDK 1.3.1 bug when 401 returns
				int index = 0;
				String errMessage = null;
				while ((errMessage = conn.getHeaderField(index)) != null) {
					if (errMessage.indexOf(" 401 ") != -1) {
						code = HttpURLConnection.HTTP_UNAUTHORIZED;
						break;
					}
					index++;
				}
			}

			if (code == HttpURLConnection.HTTP_UNAUTHORIZED) {
				//create a stream for sending the default login page
				stream =
					new ByteArrayInputStream(
						getLoginForm(conn.getURL().toString()).getBytes());
			} else {
				stream = conn.getInputStream();
				if (stream == null && includeError)
					stream = conn.getErrorStream();
			}
		} catch (IOException e) {
		}
		return stream;
	}

	static String getLoginForm(String urlStr) {
		//create default login page for HttpRequest login 
		String loginForm =
			"<H>Logon to : "
				+ getURLHost(urlStr)
				+ "</H>"
				+ "<FORM name=login method=AUTHORIZATION action="
				+ urlStr
				+ ">"
				+ "<TABLE><TR>"
				+ "<TD>User Name : </TD>"
				+ "<TD><INPUT size=10 name=username></TD>"
				+ "</TR><TR>"
				+ "<TD>Password : </TD>"
				+ "<TD><INPUT type=password size=10 name=password></TD>"
				+ "</TR></TABLE>"
				+ "<INPUT type=submit value=Submit>"
				+ "</FORM>";

		return loginForm;

	}

	static public boolean isScript(String href) {
		if (href.length() == 0)
			return false;
		char h0 = href.charAt(0);
		if ((h0 == 'j' || h0 == 'J' || h0 == 'v' || h0 == 'V')
			&& href.indexOf("cript:") > 0) {
			return true;
		}
		return false;
	}

}
