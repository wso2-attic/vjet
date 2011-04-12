/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.js;

import java.net.URL;

import org.mozilla.mod.javascript.Context;
import org.mozilla.mod.javascript.Scriptable;
import org.mozilla.mod.javascript.ScriptableObject;

public class JSLocation extends ScriptableObject {

	/** Creates new JSLocation */
	private JSWindow window = null;
	private Context cx = null;
	private Scriptable scope = null;

	public JSLocation(JSWindow window) {
		this.window = window;
		this.cx = window.getContext();
		this.scope = window.getScope();

		String[] functions = new String[4];
		functions[0] = "replace";
		functions[1] = "valueOf";
		functions[2] = "reload";
		functions[3] = "toString";

		defineFunctionProperties(
			functions,
			JSLocation.class,
			ScriptableObject.DONTENUM);

		defineProperty("href", JSLocation.class, ScriptableObject.DONTENUM);
		defineProperty("host", JSLocation.class, ScriptableObject.DONTENUM);
		defineProperty("hostname", JSLocation.class, ScriptableObject.DONTENUM);
		defineProperty("pathname", JSLocation.class, ScriptableObject.DONTENUM);
		defineProperty("port", JSLocation.class, ScriptableObject.DONTENUM);
		defineProperty("protocol", JSLocation.class, ScriptableObject.DONTENUM);
		defineProperty("search", JSLocation.class, ScriptableObject.DONTENUM);

	}

	public String getClassName() {
		return ("JSLocation");
	}

	// Property: hash, R/W -------------------------------------------------
	private String hash = null;
	public Scriptable getHash() {
		if (hash != null) {
			return (Context.toObject(hash, scope));
		} else {
			String r = window.getURL().getRef();
			if (r == null) {
				r = "";
			} else {
				r = "#" + r;
			}
			return (Context.toObject(r, scope));
		}
	}

	public void setHash(Object obj) {
		hash = obj.toString();
		return;
	}

	// Property: host, R/W -------------------------------------------------
	private String host = null;
	public Scriptable getHost() {
		if (host != null) {
			return (Context.toObject(host, scope));
		} else {
			URL url = window.getURL();
			int portNumber = url.getPort();
			String hostStr = "";
			if (portNumber > 0) {
				hostStr = ":" + portNumber;
			}
			return (Context.toObject((url.getHost() + hostStr), scope));
		}
	}

	public void setHost(Object obj) {
		host = obj.toString();
		return;
	}

	// Property: hostname, R/W -------------------------------------------------
	String hostname = null;
	public Scriptable getHostname() {
		if (hostname != null) {
			return (Context.toObject(hostname, scope));
		} else {
			String h = window.getURL().getHost();
			if (h == null) {
				h = "";
			}
			return (Context.toObject(h, scope));
		}
	}

	public void setHostname(Object obj) {
		hostname = obj.toString();
		return;
	}

	// Property: href, R/W -------------------------------------------------
	String href = null;
	Scriptable scriptableHref = null;
	public Scriptable getHref() {
		if (href != null) {
			if (scriptableHref == null) {
				scriptableHref = Context.toObject(href, scope);
			}
			return (scriptableHref);
		} else {
			href = window.getURL().toString();
			if (href == null) {
				href = "";
			}
			scriptableHref = Context.toObject(href, scope);
			return (scriptableHref);
		}
	}

	public void setHref(Object obj) {
		
		replace(obj);
		return;
	}

	// Property: pathname, R/W -------------------------------------------------
	String pathname = null;
	public Scriptable getPathname() {
		if (pathname != null) {
			return (Context.toObject(pathname, scope));
		} else {
			String p = getURLPath(window.getURL().toString());
			//window.getURL().getPath();
			if (p == null) {
				p = "";
			}
			return (Context.toObject(p, scope));
		}
	}

	public void setPathname(Object obj) {
		pathname = obj.toString();
		return;
	}

	// Property: port, R/W -------------------------------------------------
	private String port = null;
	public Scriptable getPort() {
		if (port != null) {
			return (Context.toObject(port, scope));
		} else {
			int portNumber = window.getURL().getPort();
			if (portNumber > 0) {
				return (Context.toObject(("" + portNumber), scope));
			} else {
				return (Context.toObject("", scope));
			}
		}
	}

	public void setPort(Object obj) {
		port = obj.toString();
		return;
	}

	// Property: protocol, R/W -------------------------------------------------
	private String protocol = null;
	public Scriptable getProtocol() {
		if (protocol != null) {
			return (Context.toObject(protocol, scope));
		} else {
			String p = window.getURL().getProtocol();
			if (p == null) {
				p = "";
			} else {
				p = p + ":";
			}
			return (Context.toObject(p, scope));
		}
	}

	public void setProtocol(Object obj) {
		protocol = obj.toString();
		return;
	}

	// Property: search, R/W -------------------------------------------------
	private String search = null;
	public Scriptable getSearch() {
		if (search != null) {
			return (Context.toObject(search, scope));
		} else {
			String q = getURLQuery(window.getURL().toString());
			//window.getURL().getQuery();
			if (q == null) {
				q = "";
			}
			return (Context.toObject(q, scope));
		}
	}

	public void setSearch(Object obj) {
		search = obj.toString();
		return;
	}

	// Functions ---------------------------------------------------------------
	public void reload(Object force) {
		if (window.windowState == JSWindow.IN_SERVER)
			window.getJSListener().doAction(
				JSAction.LOCATION_RELOAD,
				"location",
				null,
				null);
		return;
	}

	private String replacement = null;
	public void replace(Object s) {
		JsHackDetectionCtx.ctx().setLocationChange(s.toString());
		JSDebug.println("JSLocation:replace: " + s);
		if (s != null) {
			if (window.windowState == JSWindow.IN_LOADING)
				replacement = s.toString();
			else
				window.getJSListener().doAction(
					JSAction.SET_LOCATION,
					"LOCATION",
					s.toString(),
					null);
		}
		return;
	}

	/** Get the target web link from this window. It is set by:
	 * [window.]location[.href], location.replace(new page).
	 *
	 * @return The target web address.
	 */
	public String getReplacement() {
		return (replacement);
	}

	private String getURLPath(String u) {
		if (u == null || u.trim().length() <= 0)
			return "";

		String s = u;
		int i = s.lastIndexOf(';');
		if (i > -1)
			s = s.substring(0, i);

		i = s.lastIndexOf('#');
		if (i > -1)
			s = s.substring(0, i);

		i = s.indexOf("://");
		if (i > -1)
			s = s.substring(i + 3);

		i = s.indexOf('/');
		if (i > -1)
			s = s.substring(i);
		else
			s = "/";

		i = s.indexOf('?');
		if (i > -1)
			s = s.substring(0, i);

		return (s);
	}

	private String getURLQuery(String u) {
		if (u == null || u.trim().length() <= 0)
			return "";

		String s = u;
		int i = s.indexOf('?');
		if (i < 0)
			return "";

		s = s.substring(i);
		i = s.lastIndexOf('#');
		if (i > -1)
			s = s.substring(0, i);

		return (s);
	}

	public Object valueOf(String type) {
		if (type.equals("boolean"))
			return Boolean.TRUE;
		else if (type.equals("string"))
			return getHref();
		else if (type.equals("object"))
			return this;
		else if (type.equals("number"))
			return "0";

		return null;
	}

	public String toString() {
		href = window.getURL().toString();
		return href;
	}
}
