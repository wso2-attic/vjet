/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsnative;

import org.ebayopensource.dsf.jsnative.anno.BrowserSupport;
import org.ebayopensource.dsf.jsnative.anno.BrowserType;
import org.ebayopensource.dsf.jsnative.anno.Function;
import org.ebayopensource.dsf.jsnative.anno.JsMetatype;
import org.ebayopensource.dsf.jsnative.anno.JsSupport;
import org.ebayopensource.dsf.jsnative.anno.JsVersion;
import org.ebayopensource.dsf.jsnative.anno.OverLoadFunc;
import org.ebayopensource.dsf.jsnative.anno.Property;
import org.ebayopensource.dsf.jsnative.anno.ProxyFunc;
import org.mozilla.mod.javascript.IWillBeScriptable;

/**
 * JavaScript object representing window's history object.
 */
@JsSupport(JsVersion.MOZILLA_ONE_DOT_ZERO)
@JsMetatype
public interface History extends IWillBeScriptable {

	// Properties

	/**
	 * Returns the number of elements in the history list
	 * 
	 * @return
	 */
	@Property
	int getLength();

	/**
	 * Loads the previous URL in the history list
	 */
	@Function
	void back();

	/**
	 * Loads the next URL in the history list
	 */
	@Function
	void forward();

	@ProxyFunc("go")
	void __go(Object numberOrUrl, Object notreq0, Object notreq1,
			Object notreq2, Object notreq3);

	/**
	 * Loads a specific page in the history list
	 * 
	 * @param number
	 *            is an Integer number in history array or a URL to go to.
	 */
	@OverLoadFunc
	void go(int number);

	/*
	 * A string indicates an exact URL in the History list.
	 */
	@BrowserSupport(BrowserType.IE_6P)
	@OverLoadFunc
	void go(String url);

	/**
	 * The pushState() method
	 * 
	 * pushState() takes three parameters: a state object, a title (which is
	 * currently ignored), and (optionally) a URL. Let's examine each of these
	 * three parameters in more detail:
	 * 
	 * @param state
	 *            object Ñ The state object is a JavaScript object which is
	 *            associated with the new history entry created by pushState().
	 *            Whenever the user navigates to the new state, a popstate event
	 *            is fired, and the state property of the event contains a copy
	 *            of the history entry's state object.
	 * 
	 *            The state object can be anything that you can pass to
	 *            JSON.stringify. Because Firefox saves state objects to the
	 *            user's disk so they can be restored after the user restarts
	 *            her browser, we impose a size limit of 640k characters on the
	 *            JSON representation of a state object. If you pass a state
	 *            object whose JSON representation is larger than this to
	 *            pushState(), the method will throw an exception. If you need
	 *            more space than this, you're encouraged to use sessionStorage
	 *            and/or localStorage.
	 * @param title
	 *            Ñ Firefox currently ignores this parameter, although it may
	 *            use it in the future. Passing the empty string here should be
	 *            safe against future changes to the method. Alternatively, you
	 *            could pass a short title for the state to which you're moving.
	 * @param URL
	 *            Ñ The new history entry's URL is given by this parameter. Note
	 *            that the browser won't attempt to load this URL after a call
	 *            to pushState(), but it might attempt to load the URL later,
	 *            for instance after the user restarts her browser. The new URL
	 *            does not need to be absolute; if it's relative, it's resolved
	 *            relative to the current URL. The new URL must be of the same
	 *            origin as the current URL; otherwise, pushState() will throw
	 *            an exception. This parameter is optional; if it isn't
	 *            specified, it's set to the document's current URL.
	 * 
	 *            In a sense, calling pushState() is similar to setting
	 *            window.location = "#foo", in that both will also create and
	 *            activate another history entry associated with the current
	 *            document. But pushState() has a few advantages:
	 * 
	 *            The new URL can be any URL in the same origin as the current
	 *            URL. In contrast, setting window.location keeps you at the
	 *            same document only if you modify only the hash. You don't have
	 *            to change the URL if you don't want to. In contrast, setting
	 *            window.location = "#foo"; only creates a new history entry if
	 *            the current hash isn't #foo. You can associate arbitrary data
	 *            with your new history entry. With the hash-based approach, you
	 *            need to encode all of the relevant data into a short string.
	 * 
	 *            Note that pushState() never causes a hashchange event to be
	 *            fired, even if the new URL differs from the old URL only in
	 *            its hash.
	 */
	@ProxyFunc("pushState")
	void __pushState(Object state, Object title, Object URL,
			Object notreq2, Object notreq3);

	
	@BrowserSupport(BrowserType.FIREFOX_4P)
	@OverLoadFunc
	void pushState(Object state, String title, String URL);

	@BrowserSupport(BrowserType.FIREFOX_4P)
	@OverLoadFunc
	void pushState(Object state, String title);

	@BrowserSupport(BrowserType.FIREFOX_4P)
	@OverLoadFunc
	void pushState(Object state);

	/**
	 * history.replaceState() operates exactly like history.pushState() except
	 * that replaceState() modifies the current history entry instead of
	 * creating a new one.
	 * 
	 * replaceState() is particularly useful when you want to update the state
	 * object or URL of the current history entry in response to some user
	 * action.
	 */

	@ProxyFunc("replaceState")
	void __replaceState(Object state, Object title, Object URL,
			Object notreq2, Object notreq3);
	
	@BrowserSupport(BrowserType.FIREFOX_4P)
	@OverLoadFunc
	void replaceState(Object state, String title, String URL);

	@BrowserSupport(BrowserType.FIREFOX_4P)
	@OverLoadFunc
	void replaceState(Object state, String title);

	@BrowserSupport(BrowserType.FIREFOX_4P)
	@OverLoadFunc
	void replaceState(Object state);

}
