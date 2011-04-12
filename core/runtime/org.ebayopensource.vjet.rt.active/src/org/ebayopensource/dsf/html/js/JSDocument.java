/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.js;

import java.util.HashMap;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.ebayopensource.dsf.dom.DElement;
import org.ebayopensource.dsf.html.dom.DBody;
import org.mozilla.mod.javascript.Context;
import org.mozilla.mod.javascript.Function;
import org.mozilla.mod.javascript.Scriptable;
import org.mozilla.mod.javascript.ScriptableObject;
import org.mozilla.mod.javascript.Undefined;

public class JSDocument extends ScriptableObject {

	private StringBuffer m_buffer = new StringBuffer();

	/** Creates new JSDocument */
	private JSWindow window = null;
	private Context m_cx = null;
	private Scriptable m_scope = null;
	private JSBuilderListener m_builderListener = null;

	public JSDocument() {
	}

	/** A Rhino constructor. Do not put init code here.
	 *
	 */
	public void jsConstructor() {
	}

	public JSDocument(JSWindow window) {
		this.window = window;
		this.m_cx = window.getContext();
		this.m_scope = window.getScope();
		this.forms = new JSFormsArray(window);

		try {
			ScriptableObject.defineClass(m_scope, JSDocument.class);
		} catch (Exception ex) {
		}
		setParentScope(m_scope);
		setPrototype(window.getPrototype());

		String[] methodNames = { "open", "close", "writeln", "write", "getElementById"};

		defineProperty("forms", JSDocument.class, ScriptableObject.READONLY);
		defineProperty("cookie", JSDocument.class, ScriptableObject.DONTENUM);
		defineProperty("window", JSDocument.class, ScriptableObject.DONTENUM);
		defineProperty("location", JSDocument.class, ScriptableObject.DONTENUM);
		defineProperty("all", JSDocument.class, ScriptableObject.READONLY);
		defineProperty("images", JSDocument.class, ScriptableObject.DONTENUM);
		defineProperty("anchors", JSDocument.class, ScriptableObject.DONTENUM);
		defineProperty("applets", JSDocument.class, ScriptableObject.DONTENUM);
		defineProperty("charset", JSDocument.class, ScriptableObject.DONTENUM);
		defineProperty("children", JSDocument.class, ScriptableObject.DONTENUM);
		defineProperty("title", JSDocument.class, ScriptableObject.DONTENUM);
		defineProperty("referrer", JSDocument.class, ScriptableObject.DONTENUM);
		defineProperty("domain", JSDocument.class, ScriptableObject.DONTENUM);
		defineProperty("URL", JSDocument.class, ScriptableObject.DONTENUM);
		defineProperty("url", JSDocument.class, ScriptableObject.DONTENUM);
		defineProperty("body", JSDocument.class, ScriptableObject.DONTENUM);
		defineProperty(
			"onmousedown",
			JSDocument.class,
			ScriptableObject.DONTENUM);

		defineFunctionProperties(
			methodNames,
			JSDocument.class,
			ScriptableObject.DONTENUM);
	}

	public JSWindow jsGet_window() {
		return window;
	}

	public Scriptable getWindow() {
		return window;
	}

	private Object[] getArrayFromChildren(final NodeList kids) {
		int length = kids.getLength();
		Object[] array = new Object[length];
		for (int i = 0; i < length; i++) {
			array[i] = kids.item(i);
		}
		return array;
	}

	// Property: alinkColor, R/O -------------------------------------------------
	public Scriptable getAlinkColor() {
		DBody bodyElement = window.getHTMLDocument().getBody();
		String colorString = bodyElement.getHtmlAlink();
		if (colorString != null && colorString.trim().length() > 0) {
			return (Context.toObject(colorString, m_scope));
		} else {
			return (Context.toObject("#0000FF", m_scope));
		}
	}

	public Scriptable jsGet_alinkColor() {
		return getAlinkColor();
	}

	// Property: all[], R/O -------------------------------------------------
	private JSAllArray browserAllArray = null;
	public Scriptable getAll() {
		if (browserAllArray == null) {
			browserAllArray = new JSAllArray(window);
		}
		return (browserAllArray);
	}

	public Scriptable jsGet_all() {
		return getAll();
	}

	// Property: anchors[], R/O -------------------------------------------------
	private JSAnchorsArray browserAnchorsArray = null;
	public Scriptable getAnchors() {
		if (browserAnchorsArray == null) {
			browserAnchorsArray = new JSAnchorsArray(window);
		}
		return (browserAnchorsArray);
	}

	public Scriptable jsGet_anchors() {
		return getAnchors();
	}

	// Property: applets[], R/O -------------------------------------------------
	private JSAppletsArray browserAppletsArray = null;
	public Scriptable getApplets() {
		if (browserAppletsArray == null) {
			browserAppletsArray = new JSAppletsArray(window);
		}
		return (browserAppletsArray);
	}

	public Scriptable jsGet_applets() {
		return getApplets();
	}

	// Property: body, R/O -------------------------------------------------
	private JSElement bodyWraper = null;
	public Scriptable getBody() {
		if (bodyWraper == null) {
			DBody bodyElement = window.getHTMLDocument().getBody();
			bodyWraper = new JSElement(window, bodyElement);
		}
		return bodyWraper;
	}

	// Property: bgColor, R/O -------------------------------------------------
	public Scriptable getBgColor() {
		//return (Context.toObject(bgColor, scope));
		DBody bodyElement = window.getHTMLDocument().getBody();
		String colorString = bodyElement.getHtmlBgColor();
		if (colorString != null && colorString.trim().length() > 0) {
			return (Context.toObject(colorString, m_scope));
		} else {
			return (Context.toObject("#FFFFFF", m_scope));
		}
	}

	public Scriptable jsGet_bgColor() {
		return getBgColor();
	}

	// Property: charset, R/O -------------------------------------------------
	private String charset = "windows-1252";
	public Scriptable getCharset() {
		return (Context.toObject(charset, m_scope));
	}

	public Scriptable jsGet_Charset() {
		return (Context.toObject(charset, m_scope));
	}

	// Property: children, R/O -------------------------------------------------
	public Scriptable getChildren() {
		return (
			Context.toObject(
				getArrayFromChildren(
					window.getHTMLDocument().getChildNodes()),
				m_scope));
	}

	public Scriptable jsGet_children() {
		return getChildren();
	}

	// Property: cookie, R/W -------------------------------------------------
	private String cookie = "";
	public Scriptable getCookie() {
		JsHackDetectionCtx.ctx().setCookieAccessing(true);
		String value = null;
		if (window.windowState == JSWindow.IN_LOADING)
			value = cookie;
		else
			value =
				window.getJSListener().doAction(
					JSAction.GET_COOKIE,
					"document",
					cookie,
					null);

		return (Context.toObject(value, m_scope));
	}

	public Scriptable jsGet_cookie() {
		return getCookie();
	}

	public void setCookie(String s) {
		JsHackDetectionCtx.ctx().setCookieAccessing(true);
		if (window.windowState == JSWindow.IN_LOADING)
			cookie += (cookie.length() == 0 ? "" : " ") + s;
		else
			window.getJSListener().doAction(
				JSAction.SET_COOKIE,
				"document",
				s,
				null);
		return;
	}

	public void jsSet_cookie(String s) {
		setCookie(s);
	}

	/** Get the string representation of the document.cookie.
	 *
	 * @return The string representation of the document.cookie. "name=value" pairs separated by ";".
	*/
	public String getCookie2() {
		return (cookie);
	}

	/** Set the document.cookie.
	 *
	 * @param s "name=value" pairs separated by ";".
	 */
	public void setCookie2(String s) {
		cookie = s;
		return;
	}

	// Property: defaultCharset, R/O -------------------------------------------------
	private String defaultCharset = "windows-1252";
	public Scriptable getDefaultCharset() {
		return (Context.toObject(defaultCharset, m_scope));
	}

	public Scriptable jsGet_DefaultCharset() {
		return (Context.toObject(defaultCharset, m_scope));
	}

	// Property: domain, R/O -------------------------------------------------
	private String domain = "";
	public Scriptable getDomain() {
		if (window.getURL() != null) {
			domain = window.getURL().getHost();
		}
		if (domain == null) {
			domain = "";
		}
		return (Context.toObject(domain, m_scope));
	}

	public Scriptable jsGet_omain() {
		return getDomain();
	}

	public void setDomain(Object o) {
		domain = o.toString();
	}

	public void jsSet_Domain(Object o) {
		domain = o.toString();
	}

	// Property: embeds[], R/O -------------------------------------------------
	private JSEmbedArray browserEmbedsArray = null;
	public Scriptable getEmbeds() {
		if (browserEmbedsArray == null) {
			browserEmbedsArray = new JSEmbedArray(window);
		}
		return (browserEmbedsArray);
	}

	public Scriptable jsGet_embeds() {
		return getEmbeds();
	}

	// Property: fgColor, R/O -------------------------------------------------
	public Scriptable getFgColor() {
		DBody bodyElement = window.getHTMLDocument().getBody();
		String colorString = bodyElement.getHtmlText();
		if (colorString != null && colorString.trim().length() > 0) {
			return (Context.toObject(colorString, m_scope));
		} else {
			return (Context.toObject("#000000", m_scope));
		}
	}

	public Scriptable jsGet_fgColor() {
		return getFgColor();
	}

	// Property: forms[], R/O -------------------------------------------------
	private JSFormsArray forms = null;
	public Scriptable getForms() {
		return forms;
	}

	public Scriptable jsGet_forms() {
		return forms;
	}

	// Property: images[], R/O -------------------------------------------------
	private JSImageArray browserImagesArray = null;
	public Scriptable getImages() {
		if (browserImagesArray == null) {
			browserImagesArray = new JSImageArray(window);
		}
		return (browserImagesArray);
	}

	public Scriptable jsGet_images() {
		return getImages();
	}

	// Property: lastModified, R/O -------------------------------------------------
	private String lastModified = "01/01/2000 12:34:56";
	public Scriptable getLastModified() {
		return (Context.toObject(lastModified, m_scope));
	}

	public Scriptable jsGet_lastModified() {
		return (Context.toObject(lastModified, m_scope));
	}

	public void setLastModified(String s) {
		lastModified = s;
	}

	public void jsSet_lastModified(String s) {
		lastModified = s;
	}

	// Property: layers[], R/O -------------------------------------------------
	public Scriptable getLayers() {
		return (null); // IE does not support layers
	}

	public Scriptable jsGet_layers() {
		return (null); // IE does not support layers
	}

	// Property: linkColor, R/O -------------------------------------------------
	public Scriptable getLinkColor() {
		//return (Context.toObject(linkColor, scope));
		DBody bodyElement = window.getHTMLDocument().getBody();
		String colorString = bodyElement.getHtmlLink();
		if (colorString != null && colorString.trim().length() > 0) {
			return (Context.toObject(colorString, m_scope));
		} else {
			return (Context.toObject("#0000FF", m_scope));
		}
	}

	public Scriptable jsGet_linkColor() {
		return getLinkColor();
	}

	// Property: links[], R/O -------------------------------------------------
	private JSLinkArray browserLinksArray = null;
	public Scriptable getLinks() {
		if (browserLinksArray == null) {
			browserLinksArray = new JSLinkArray(window);
		}
		return (browserLinksArray);
	}

	public Scriptable jsGet_links() {
		return getLinks();
	}

	// Property: location, R/O -------------------------------------------------
	public Scriptable getLocation() {
		return (window.getLocation());
	}

	public Scriptable jsGet_location() {
		return (window.getLocation());
	}

	public void setLocation(Object l) {
		JsHackDetectionCtx.ctx().setLocationChange(l.toString());
		window.getJSLocation().replace(l);
		window.getJSListener().doAction(
			JSAction.SET_LOCATION,
			"document",
			l.toString(),
			null);
	}

	public void jsSet_location(Object l) {
		setLocation(l);
	}

	// Property: parentWindow, R/O -------------------------------------------------
	public Scriptable getParentWindow() {
		return (window);
	}

	public Scriptable jsGet_parentWindow() {
		return (window);
	}

	// Property: plugins[], R/O -------------------------------------------------
	public Scriptable getPlugins() {
		return (getEmbeds());
	}

	public Scriptable jsGet_plugins() {
		return getPlugins();
	}

	// Property: readyState, R/O -------------------------------------------------
	public Scriptable getReadyState() {
		return (Context.toObject("complete", m_scope));
	}

	public Scriptable jsGet_readyState() {
		return getReadyState();
	}

	// Property: referrer, R/O -------------------------------------------------
	private String referrer = "";
	public Scriptable getReferrer() {
		return (Context.toObject(referrer, m_scope));
	}

	public Scriptable jsGet_referrer() {
		return getReferrer();
	}

	public void setReferrer(String s) {
		referrer = s;
	}

	public void jsSet_referrer(String s) {
		setReferrer(s);
	}

	// Property: title, R/O -------------------------------------------------
	public Scriptable getTitle() {
		return (Context.toObject(window.getHTMLDocument().getTitle(), m_scope));
	}

	public Scriptable jsGet_title() {
		return getTitle();
	}

	// Property: url, R/O -------------------------------------------------
	public Scriptable getUrl() {
		return (Context.toObject(window.getURL().toString(), m_scope));
	}

	public Scriptable jsGet_url() {
		return getUrl();
	}

	// Add this duplicated function for document.URL
	public Scriptable getURL() {
		return (Context.toObject(window.getURL().toString(), m_scope));
	}

	public Scriptable jsGet_URL() {
		return getURL();
	}

	// Property: vlinkColor, R/O -------------------------------------------------
	public Scriptable getVlinkColor() {
		DBody bodyElement = window.getHTMLDocument().getBody();
		String colorString = bodyElement.getHtmlVlink();
		if (colorString != null && colorString.trim().length() > 0) {
			return (Context.toObject(colorString, m_scope));
		} else {
			return (Context.toObject("#800080", m_scope));
		}
	}

	public Scriptable jsGet_vlinkColor() {
		return getVlinkColor();
	}

	// Event Handlers: -------------------------------------------------
	private Object onAbortHandler = null;
	public void setOnAbort(Object o) {
		onAbortHandler = o;
	}

	public void jsSet_onAbort(Object o) {
		onAbortHandler = o;
	}

	public Object getOnAbort() {
		return (onAbortHandler);
	}

	public Object jsGet_onAbort() {
		return getOnAbort();
	}

	public void setOnabort(Object o) {
		onAbortHandler = o;
	}

	public void jsSet_onabort(Object o) {
		setOnabort(o);
	}

	public Object getOnabort() {
		return (onAbortHandler);
	}

	public Object jsGet_onabort() {
		return getOnabort();
	}

	private Object m_onBlurHandler = null;
	public void setOnBlur(Object o) {
		m_onBlurHandler = o;
	}

	public void jsSet_onBlur(Object o) {
		setOnBlur(o);
	}

	public Object getOnBlur() {
		return (m_onBlurHandler);
	}

	public Object jsGet_onBlur() {
		return getOnBlur();
	}

	public void setOnblur(Object o) {
		m_onBlurHandler = o;
	}

	public void jsSet_onblur(Object o) {
		setOnblur(o);
	}

	public Object getOnblur() {
		return (m_onBlurHandler);
	}

	public Object jsGet_onblur() {
		return getOnblur();
	}

	private Object m_onChangeHandler = null;
	public void setOnChange(Object o) {
		m_onChangeHandler = o;
	}

	public void jsSet_onChange(Object o) {
		setOnChange(o);
	}

	public Object getOnChange() {
		return (m_onChangeHandler);
	}

	public Object jsGet_onChange() {
		return getOnChange();
	}

	public void setOnchange(Object o) {
		m_onChangeHandler = o;
	}

	public void jsSet_onchange(Object o) {
		setOnchange(o);
	}

	public Object getOnchange() {
		return (m_onChangeHandler);
	}

	public Object jsGet_onchange() {
		return getOnchange();
	}

	private Object m_onClickHandler = null;
	public void setOnClick(Object o) {
		m_onClickHandler = o;
	}

	public void jsSet_onClick(Object o) {
		setOnClick(o);
	}

	public Object getOnClick() {
		return (m_onClickHandler);
	}

	public Object jsGet_onClick() {
		return getOnClick();
	}

	public void setOnclick(Object o) {
		m_onClickHandler = o;
	}

	public void jsSet_onclick(Object o) {
		setOnclick(o);
	}

	public Object getOnclick() {
		return (m_onClickHandler);
	}

	public Object jsGet_onclick() {
		return getOnclick();
	}

	private Object m_onDblClickHandler = null;
	public void setOnDblClick(Object o) {
		m_onDblClickHandler = o;
	}

	public void jsSet_onDblClick(Object o) {
		setOnDblClick(o);
	}

	public Object getOnDblClick() {
		return (m_onDblClickHandler);
	}

	public Object jsGet_onDblClick() {
		return getOnDblClick();
	}

	public void setOndblclick(Object o) {
		m_onDblClickHandler = o;
	}

	public void jsSet_ondblclick(Object o) {
		setOndblclick(o);
	}

	public Object getOndblclick() {
		return (m_onDblClickHandler);
	}

	public Object jsGet_ondblclick() {
		return getOndblclick();
	}

	private Object m_onErrorHandler = null;
	public void setOnError(Object o) {
		m_onErrorHandler = o;
	}

	public void jsSet_onError(Object o) {
		setOnError(o);
	}

	public Object getOnError() {
		return (m_onErrorHandler);
	}

	public Object jsGet_onError() {
		return getOnError();
	}

	public void setOnerror(Object o) {
		m_onErrorHandler = o;
	}

	public void jsSet_onerror(Object o) {
		setOnerror(o);
	}

	public Object getOnerror() {
		return (m_onErrorHandler);
	}

	public Object jsGet_onerror() {
		return getOnerror();
	}

	private Object m_onFocusHandler = null;
	public void setOnFocus(Object o) {
		m_onFocusHandler = o;
	}

	public void jsSet_onFocus(Object o) {
		setOnFocus(o);
	}

	public Object getOnFocus() {
		return (m_onFocusHandler);
	}

	public Object jsGet_onFocus() {
		return getOnFocus();
	}

	public void setOnfocus(Object o) {
		m_onFocusHandler = o;
	}

	public void jsSet_onfocus(Object o) {
		setOnfocus(o);
	}

	public Object getOnfocus() {
		return (m_onFocusHandler);
	}

	public Object jsGet_onfocus() {
		return getOnfocus();
	}

	private Object m_onKeyDownHandler = null;
	public void setOnKeyDown(Object o) {
		m_onKeyDownHandler = o;
	}

	public void jsSet_onKeyDown(Object o) {
		setOnKeyDown(o);
	}

	public Object getOnKeyDown() {
		return (m_onKeyDownHandler);
	}

	public Object jsGert_onKeyDown() {
		return getOnKeyDown();
	}

	public void setOnkeydown(Object o) {
		m_onKeyDownHandler = o;
	}

	public void jsSet_onkeydown(Object o) {
		setOnkeydown(o);
	}

	public Object getOnkeydown() {
		return (m_onKeyDownHandler);
	}

	public Object jsGet_onkeydown() {
		return getOnkeydown();
	}

	private Object m_onKeyPressHandler = null;
	public void setOnKeyPress(Object o) {
		m_onKeyPressHandler = o;
	}

	public void jsSet_onKeyPress(Object o) {
		setOnKeyPress(o);
	}

	public Object getOnKeyPress() {
		return (m_onKeyPressHandler);
	}

	public Object jsGet_onKeyPress() {
		return getOnKeyPress();
	}

	public void setOnkeypress(Object o) {
		m_onKeyPressHandler = o;
	}

	public void jsSet_onkeypress(Object o) {
		setOnkeypress(o);
	}

	public Object getOnkeypress() {
		return (m_onKeyPressHandler);
	}

	public Object jsGet_onkeypress() {
		return getOnkeypress();
	}

	private Object m_onKeyUpHandler = null;
	public void setOnKeyUp(Object o) {
		m_onKeyUpHandler = o;
	}

	public void jsSet_onKeyUp(Object o) {
		setOnKeyUp(o);
	}

	public Object getOnKeyUp() {
		return (m_onKeyUpHandler);
	}

	public Object jsGet_onKeyUp() {
		return getOnKeyUp();
	}

	public void setOnkeyup(Object o) {
		m_onKeyUpHandler = o;
	}

	public void jsSet_onkeyup(Object o) {
		setOnkeyup(o);
	}

	public Object getOnkeyup() {
		return (m_onKeyUpHandler);
	}

	public Object jsGet_onkeyup() {
		return getOnkeyup();
	}

	private Object m_onLoadHandler = null;
	public void setOnLoad(Object o) {
		m_onLoadHandler = o;
		if (m_onLoadHandler instanceof Function) {
			try {
				((Function) m_onLoadHandler).call(
					m_cx,
					m_scope,
					this,
					new Object[0]);
			} catch (Exception e) {
				JSDebug.println(e.getMessage());
			}
		}
	}

	public void jsSet_onLoad(Object o) {
		setOnLoad(o);
	}

	public Object getOnLoad() {
		return (m_onLoadHandler);
	}

	public Object jsGet_onLoad() {
		return getOnLoad();
	}

	public void setOnload(Object o) {
		setOnLoad(o);
	}

	public void jsSet_onload(Object o) {
		setOnload(o);
	}

	public Object getOnload() {
		return (m_onLoadHandler);
	}

	public Object jsGet_onload() {
		return getOnload();
	}

	private Object m_onMouseDownHandler = null;
	public void setOnMouseDown(Object o) {
		m_onMouseDownHandler = o;
	}

	public void jsSet_onMouseDown(Object o) {
		setOnMouseDown(o);
	}

	public Object getOnMouseDown() {
		return (m_onMouseDownHandler);
	}

	public Object jsGet_onMouseDown() {
		return getOnMouseDown();
	}

	public void setOnmousedown(Object o) {
		m_onMouseDownHandler = o;
	}

	public void jsSet_onmousedown(Object o) {
		setOnmousedown(o);
	}

	public Object getOnmousedown() {
		return (m_onMouseDownHandler);
	}

	public Object isGet_onmousedown() {
		return getOnmousedown();
	}

	private Object m_onMouseOutHandler = null;
	public void setOnMouseOut(Object o) {
		m_onMouseOutHandler = o;
	}

	public void jsSet_onMouseOut(Object o) {
		setOnMouseOut(o);
	}

	public Object getOnMouseOut() {
		return (m_onMouseOutHandler);
	}

	public Object jsGet_onMouseOut() {
		return getOnMouseOut();
	}

	public void setOnmouseout(Object o) {
		m_onMouseOutHandler = o;
	}

	public void jsSet_onmouseout(Object o) {
		setOnmouseout(o);
	}

	public Object getOnmouseout() {
		return (m_onMouseOutHandler);
	}

	public Object jsGet_onmouseout() {
		return getOnmouseout();
	}

	private Object m_onMouseOverHandler = null;
	public void setOnMouseOver(Object o) {
		m_onMouseOverHandler = o;
	}

	public void jsSet_onMouseOver(Object o) {
		setOnMouseOver(o);
	}

	public Object getOnMouseOver() {
		return (m_onMouseOverHandler);
	}

	public Object jsGet_onMouseOver() {
		return getOnMouseOver();
	}

	public void setOnmouseover(Object o) {
		m_onMouseOverHandler = o;
	}

	public void jsSet_onmouseover(Object o) {
		m_onMouseOverHandler = o;
	}

	public Object getOnmouseover() {
		return (m_onMouseOverHandler);
	}

	public Object jsGet_onmouseover() {
		return (m_onMouseOverHandler);
	}

	private Object m_onMouseUpHandler = null;
	public void setOnMouseUp(Object o) {
		m_onMouseUpHandler = o;
	}

	public void jsSet_onMouseUp(Object o) {
		setOnMouseUp(o);
	}

	public Object getOnMouseUp() {
		return (m_onMouseUpHandler);
	}

	public Object jsGet_onMouseUp() {
		return getOnMouseUp();
	}

	public void setOnmouseup(Object o) {
		m_onMouseUpHandler = o;
	}

	public void jsSet_onmouseup(Object o) {
		setOnmouseup(o);
	}

	public Object getOnmouseup() {
		return (m_onMouseUpHandler);
	}

	public Object jsGet_onmouseup() {
		return getOnmouseup();
	}

	private Object m_onResetHandler = null;
	public void setOnReset(Object o) {
		m_onResetHandler = o;
	}

	public void jsSet_onReset(Object o) {
		setOnReset(o);
	}

	public Object getOnReset() {
		return (m_onResetHandler);
	}

	public Object jsGet_onReset() {
		return getOnReset();
	}

	public void setOnreset(Object o) {
		m_onResetHandler = o;
	}

	public void jsSet_onreset(Object o) {
		setOnreset(o);
	}

	public Object getOnreset() {
		return (m_onResetHandler);
	}

	public Object jsGet_onreset() {
		return getOnreset();
	}

	private Object m_onResizeHandler = null;
	public void setOnResize(Object o) {
		m_onResizeHandler = o;
	}

	public void jsSet_onResize(Object o) {
		setOnResize(o);
	}

	public Object getOnResize() {
		return (m_onResizeHandler);
	}

	public Object jsGet_onResize() {
		return getOnResize();
	}

	public void setOnresize(Object o) {
		m_onResizeHandler = o;
	}

	public void jsSet_onresize(Object o) {
		setOnresize(o);
	}

	public Object getOnresize() {
		return (m_onResizeHandler);
	}

	public Object jsGet_onresize() {
		return getOnresize();
	}

	private Object m_onSubmitHandler = null;
	public void setOnSubmit(Object o) {
		m_onSubmitHandler = o;
	}

	public void jsSet_onSubmit(Object o) {
		setOnSubmit(o);
	}

	public Object getOnSubmit() {
		return (m_onSubmitHandler);
	}

	public Object jsGet_onSubmit() {
		return getOnSubmit();
	}

	public void setOnsubmit(Object o) {
		m_onSubmitHandler = o;
	}

	public void jsSet_onsubmit(Object o) {
		setOnsubmit(o);
	}

	public Object getOnsubmit() {
		return (m_onSubmitHandler);
	}

	public Object jsGet_onsubmit() {
		return getOnsubmit();
	}

	// Functions: -------------------------------------------------
	/** Netscape Navigator method. Request events of specified types. No implementation.
	 *
	 * @param e The event.
	 */
	public void captureEvents(Object e) {
		return;
	}

	public void jsFunction_captureEvents(Object e) {
		return;
	}

	public void clear() {
		clearBuffer();
	}

	public void jsFunction_clear() {
		clearBuffer();
	}

	public void open(Object mimeType) {
		clearBuffer();
	}

	public void jsFunction_open(Object mimeType) {
		clearBuffer();
	}

	/** Closes a document stream opened by open() and forces rendering.
	 */
	public void close() {
		//clearBuffer();
	}

	public void jsFunction_close() {
		//clearBuffer();
	}

	/** Write a string of text to a document stream opened by open() .
	 *  The text is parsed into the document's structure model.
	 *
	 * @param str The string to be parsed into some structure in the document
	 *            structure model.
	 */

	public void jsFunction_write(String str) {
		m_buffer.append(str);
		callBuilderListener();
	}

	public JSElement getElementById(String id) {
		Node node = window.getHTMLDocument().getElementById(id);
		return window.getWrapper(node, true);
	}

	public void write(
		Object o1,
		Object o2,
		Object o3,
		Object o4,
		Object o5,
		Object o6,
		Object o7,
		Object o8,
		Object o9) {
		if (!(o1 instanceof Undefined))
			m_buffer.append(o1.toString());
		if (!(o2 instanceof Undefined))
			m_buffer.append(o2.toString());
		if (!(o3 instanceof Undefined))
			m_buffer.append(o3.toString());
		if (!(o4 instanceof Undefined))
			m_buffer.append(o4.toString());
		if (!(o5 instanceof Undefined))
			m_buffer.append(o5.toString());
		if (!(o6 instanceof Undefined))
			m_buffer.append(o6.toString());
		if (!(o7 instanceof Undefined))
			m_buffer.append(o7.toString());
		if (!(o8 instanceof Undefined))
			m_buffer.append(o8.toString());
		if (!(o9 instanceof Undefined))
			m_buffer.append(o9.toString());
		String textStr = m_buffer.toString().toLowerCase();
		if (textStr.startsWith("<script"))
			startWritingScript = true;
		if (startWritingScript) {
			if (textStr.indexOf("</script>") != -1)
				startWritingScript = false;
		}
		if (!isWellFormated(m_buffer)) {
			JsHackDetectionCtx.ctx().setScriptHacking(true);
			return;
		}

		if (!startWritingScript)
			callBuilderListener();
	}

	static boolean isWellFormated(StringBuffer buffer) {
		int numStart = 0;
		int numEnd = 0;
		int length = buffer.length();
		for (int i = 0; i < length; i++) {
			char c = buffer.charAt(i);
			if (c == '<')
				numStart++;
			else if (c == '>')
				numEnd++;
		}
		if (numStart != numEnd)
			return false;
		return true;
	}

	/** Write a string of text followed by a newline character to a document
	 *  stream opened by open() . The text is parsed into the document's
	 *  structure model.
	 *
	 * @param text The string to be parsed into some structure in the document
	 *             structure model.
	 */
	boolean startWritingScript = false;
	public void writeln(Object text) {
		String textStr = text.toString();
		if (textStr.toLowerCase().startsWith("<script"))
			startWritingScript = true;
		if (startWritingScript) {
			if (textStr.toLowerCase().indexOf("</script>") != -1)
				startWritingScript = false;
		}
		m_buffer.append(text.toString()).append("\n");
		if (!isWellFormated(m_buffer)) {
			JsHackDetectionCtx.ctx().setScriptHacking(true);
			return;
		}

		if (!startWritingScript)
			callBuilderListener();
	}

	public void jsFunction_writeln(Object text) {
		m_buffer.append(text.toString()).append("\n");
		callBuilderListener();
	}

	private void clearBuffer() {
		m_buffer.delete(0, m_buffer.length());
	}

	/** Return the content
	 *
	 * @return String.
	 */
	public String getGeneratedContent() {
		String s = m_buffer.toString();
		clearBuffer();
		return (s);
	}

	/** A method required by ScriptableObject. Usually same as the class name.
	 *
	 * @return The class name without package.
	 */
	public String getClassName() {
		return "JSDocument";
	}

	public void updateForms() {
		forms.updateForms();
		int numForms = forms.numForms();
		for (int i = 0; i < numForms; i++) {
			String name = forms.getForm(i).name;
			defineForm(name);
		}
	}

	public void addJSForm(DElement formElem) {
		forms.addJSForm(formElem);
		String name = formElem.getAttribute("name");
		if (name.length() > 0)
			defineForm(name);
	}

	public void addJSForm(String formId) {
		forms.addDefaultJSForm(formId);
	}

	public void addJSFormChildElem(DElement formChildElem) {
		forms.addJSFormChildElem(formChildElem);
	}

	public void defineForm(String name) {

		try {
			Class[] getArg = new Class[1];
			getArg[0] = ScriptableObject.class;
			Class[] setArg = new Class[2];
			setArg[0] = ScriptableObject.class;
			setArg[1] = Object.class;
			defineProperty(
				name,
				this,
				getClass().getDeclaredMethod("getMyField", getArg),
				getClass().getDeclaredMethod("setMyField", setArg),
			//bDoc.getClass().getDeclaredMethod
			ScriptableObject.DONTENUM);
		} catch (NoSuchMethodException propExp) {
		}
	}

	public Object getMyField(ScriptableObject obj) {
		return NOT_FOUND;
	}

	public void setMyField(ScriptableObject obj, Object value) {
		JSDebug.println("***setMyField : " + obj.toString());
	}

	static HashMap constants = new HashMap(13);
	static {
		constants.put("Object", "Object");
		constants.put("layers", "layers");
		constants.put("getElementById", "getElementById");
		constants.put("SyntaxError", "SyntaxError");
		constants.put("ConversionError", "ConversionError");
		constants.put("TypeError", "TypeError");
		constants.put("ReferenceError", "ReferenceError");
	}

	public Object get(java.lang.String name, Scriptable start) {
		Object obj = super.get(name, start);
		if (obj == NOT_FOUND && constants.get(name) != null)
			return NOT_FOUND;

		if (obj == NOT_FOUND)
			obj = findFormObject(name);
		if (obj == NOT_FOUND)
			obj = window.get(name, start);
		return obj;
	}

	public Object findFormObject(String name) {
		Object form = forms.getForm(name);
		if (form == null)
			return NOT_FOUND;
		return form;
	}

	public Object findHtmlObjectById(String name, Scriptable start) {
		getAll();
		Object elem = browserAllArray.get(name, start);
		if (elem == NOT_FOUND)
			return NOT_FOUND;
		return elem;
	}

	public Scriptable findFormElement(String formId, String name) {
		int numForms = forms.numForms();
		for (int i = 0; i < numForms; i++) {
			JSForm form = forms.getForm(i);
			if (form.formId.equals(formId))
				if (name == null || name.length() == 0) // return form object
					return form;
				else { // want specific form element object
					Object child = form.findElemObject(name);
					if (child != null) //found child
						return (Scriptable) child;
				}
		}
		return forms.getFormById(formId);
	}

	public String getSelectedIndex(
		String formId,
		String name,
		String optionValue) {
		Scriptable obj = findFormElement(formId, name);
		if (obj != null && obj instanceof JSSelect)
			return String.valueOf(
				((JSSelect) obj).getOptionIndex(optionValue));
		else
			return "0";
	}

	public void setJsBuilderListener(JSBuilderListener builderListener) {
		this.m_builderListener = builderListener;
	}

	public JSBuilderListener getJsBuilderListener() {
		return m_builderListener;
	}

	public void callBuilderListener() {
		if (m_builderListener != null) {
			m_builderListener.doneDocumentWrite();
		}
	}
}
