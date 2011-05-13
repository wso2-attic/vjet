/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsnative;

import org.ebayopensource.dsf.javatojs.anno.ARename;
import org.ebayopensource.dsf.jsnative.anno.BrowserSupport;
import org.ebayopensource.dsf.jsnative.anno.BrowserType;
import org.ebayopensource.dsf.jsnative.anno.DOMSupport;
import org.ebayopensource.dsf.jsnative.anno.DomLevel;
import org.ebayopensource.dsf.jsnative.anno.Dynamic;
import org.ebayopensource.dsf.jsnative.anno.Function;
import org.ebayopensource.dsf.jsnative.anno.GlobalProperty;
import org.ebayopensource.dsf.jsnative.anno.JsMetatype;
import org.ebayopensource.dsf.jsnative.anno.JsSupport;
import org.ebayopensource.dsf.jsnative.anno.JsVersion;
import org.ebayopensource.dsf.jsnative.anno.JstExclude;
import org.ebayopensource.dsf.jsnative.anno.OverLoadFunc;
import org.ebayopensource.dsf.jsnative.anno.Property;
import org.ebayopensource.dsf.jsnative.anno.ProxyFunc;
import org.ebayopensource.dsf.jsnative.events.Event;
import org.mozilla.mod.javascript.IWillBeScriptable;

/**
 * JavaScript object representing a browser window.
 */
@JsSupport(JsVersion.MOZILLA_ONE_DOT_ZERO)
@Dynamic
@JsMetatype
public interface Window extends IWillBeScriptable {

	// Properties

	/**
	 * The closed property returns a boolean value that specifies whether the
	 * window has been closed.
	 * 
	 * @return true if the window has been closed
	 */
	@JsSupport(JsVersion.MOZILLA_ONE_DOT_ONE)
	@Property
	boolean getClosed();

	/**
	 * Returns the default text in the statusbar of the window
	 * 
	 * @return String
	 */
	@Property
	String getDefaultStatus();

	/**
	 * Sets the default text in the statusbar of the window
	 * 
	 * @param status
	 */
	@Property
	void setDefaultStatus(String status);

	/**
	 * Returns the Document object for this window
	 * 
	 * @return Document
	 */
	@Property
	HtmlDocument getDocument();

	/**
	 * Returns all named frames in the window
	 * 
	 * @return
	 */
	@Property
	HtmlCollection getFrames();

	/**
	 * Returns all named frames in the window
	 * 
	 * @return
	 */
	@Property
	Frames getChildWindows();

	/**
	 * Returns the History object associated with this window
	 * 
	 * @return History
	 */
	@JsSupport(JsVersion.MOZILLA_ONE_DOT_ONE)
	@Property
	@GlobalProperty
	History getHistory();

	/**
	 * Returns the Location object associated with this window
	 * 
	 * @return Location
	 */
	@Property
	@GlobalProperty
	Location getLocation();

	/**
	 * Sets the Location object associated with this window
	 * 
	 * @param location
	 */
	@Property
	void setLocation(Location location);

	/**
	 * Returns the name of the window
	 * 
	 * @return String
	 */
	@Property
	String getName();

	/**
	 * Sets the name of the window
	 * 
	 * @param name
	 */
	@Property
	void setName(String name);

	/**
	 * Retunrs the Navigator object associated to this window
	 * 
	 * @return
	 */
	@Property
	@GlobalProperty
	Navigator getNavigator();

	/**
	 * Reference to the window that opened the current window
	 * 
	 * @return Window
	 */
	@Property
	Window getOpener();

	/**
	 * Returns the parent window
	 * 
	 * @return Window
	 */
	@Property
	Window getParent();

	/**
	 * Returns a reference to the current window
	 * 
	 * @return Window
	 */
	@Property
	Window getSelf();

	/**
	 * Returns the text in the statusbar of a window
	 * 
	 * @return String
	 */
	@Property
	String getStatus();

	/**
	 * Sets the text in the statusbar of a window
	 * 
	 * @param status
	 */
	@Property
	void setStatus(String status);

	/**
	 * Returns the title text
	 * 
	 * @return String
	 */
	@Property
	String getTitle();

	/**
	 * Returns the title text
	 * 
	 * @return String
	 */
	@Property
	void setTitle(String title);

	/**
	 * Returns the topmost ancestor window
	 * 
	 * @return Window
	 */
	@Property
	Window getTop();

	/**
	 * returns the number of frames in the window
	 * 
	 * @return int
	 */
	@Property
	int getLength();

	/**
	 * Retruns the Screen object associated with the window.
	 * 
	 * @return Screen
	 */
	@Property
	@GlobalProperty
	Screen getScreen();

	/**
	 * Reference to the current Window
	 * 
	 * @return Window
	 */
	@GlobalProperty
	@Property
	Window getWindow();

	/**
	 * Returns width of window's document display area
	 * 
	 * @return int
	 */
	@BrowserSupport( { BrowserType.FIREFOX_1P })
	@JsSupport( { JsVersion.MOZILLA_ONE_DOT_TWO })
	@ARename(name = "innerWidth || document.body.offsetWidth")
	@Property
	int getInnerWidth();

	/**
	 * Returns height of window's document display area
	 * 
	 * @return int
	 */
	@JsSupport(JsVersion.MOZILLA_ONE_DOT_TWO)
	@BrowserSupport( { BrowserType.FIREFOX_1P })
	@ARename(name = "innerHeight || document.body.offsetHeight")
	@Property
	int getInnerHeight();

	/**
	 * Returns width of browser window
	 * 
	 * @return int
	 */
	@JsSupport(JsVersion.MOZILLA_ONE_DOT_TWO)
	@BrowserSupport( { BrowserType.FIREFOX_1P })
	@Property
	int getOuterWidth();

	/**
	 * Returns height of browser window
	 * 
	 * @return int
	 */
	@JsSupport(JsVersion.MOZILLA_ONE_DOT_TWO)
	@BrowserSupport( { BrowserType.FIREFOX_1P })
	@Property
	int getOuterHeight();

	/**
	 * Returns number of pixels the current document has been scrolled to the
	 * right
	 * 
	 * @return int
	 */
	@JsSupport(JsVersion.MOZILLA_ONE_DOT_TWO)
	@BrowserSupport( { BrowserType.FIREFOX_1P })
	@ARename(name = "pageXOffset || document.body.scrollLeft")
	@Property
	int getPageXOffset();

	/**
	 * Returns number of pixels the current document has been scrolled down
	 * 
	 * @return int
	 */
	@JsSupport(JsVersion.MOZILLA_ONE_DOT_TWO)
	@BrowserSupport( { BrowserType.FIREFOX_1P })
	@ARename(name = "pageYOffset || document.body.scrollTop")
	@Property
	int getPageYOffset();

	/**
	 * Returns the current distance in pixels from the left side of the screen
	 * 
	 * @return int
	 */
	@BrowserSupport( { BrowserType.IE_6P, BrowserType.OPERA_7P,
			BrowserType.SAFARI_3P })
	@ARename(name = "screenLeft || window.screenX")
	@Property
	int getScreenLeft();

	/**
	 * Returns the distance from the top of the screen
	 * 
	 * @return int
	 */
	@BrowserSupport( { BrowserType.IE_6P, BrowserType.OPERA_7P,
			BrowserType.SAFARI_3P })
	@ARename(name = "screenTop || window.screenY")
	@Property
	int getScreenTop();

	/**
	 * Returns the current distance in pixels from the left side of the screen
	 * 
	 * @return int
	 */
	@JsSupport(JsVersion.MOZILLA_ONE_DOT_TWO)
	@BrowserSupport( { BrowserType.FIREFOX_1P, BrowserType.SAFARI_3P })
	@ARename(name = "screenX || window.screenLeft")
	@Property
	int getScreenX();

	/**
	 * Returns the distance from the top of the screen
	 * 
	 * @return int
	 */
	@JsSupport(JsVersion.MOZILLA_ONE_DOT_TWO)
	@BrowserSupport( { BrowserType.FIREFOX_1P, BrowserType.SAFARI_3P })
	@ARename(name = "screenY || window.screenTop")
	@Property
	int getScreenY();

	/**
	 * window.external property.
	 * 
	 * @return External
	 */
	@BrowserSupport( { BrowserType.IE_6P })
	@Property
	External getExternal();

	/**
	 * Get event details for IE
	 * 
	 * @return Event
	 */
	@BrowserSupport( { BrowserType.IE_6P })
	@Property
	Event getEvent();

	/**
	 * Set IE event
	 * 
	 * @param event
	 */
	@BrowserSupport( { BrowserType.IE_6P })
	@Property
	void setEvent(Object event);

	/**
	 * Returns the onblur event handler code on the current element.
	 * 
	 * @see http://www.w3schools.com/jsref/jsref_onblur.asp
	 */
	@DOMSupport(DomLevel.ZERO)
	@Property(name = "onblur")
	Object getOnBlur();

	/**
	 * Sets the onblur event handler code on the current element.
	 * 
	 * @param functionRef
	 * @see http://www.w3schools.com/jsref/jsref_onblur.asp
	 */
	@DOMSupport(DomLevel.ZERO)
	@Property(name = "onblur")
	void setOnBlur(Object functionRef);

	/**
	 * Returns the onfocus event handler code on the current element.
	 * 
	 * @see http://www.w3schools.com/jsref/jsref_onfocus.asp
	 */
	@DOMSupport(DomLevel.ZERO)
	@Property(name = "onfocus")
	Object getOnFocus();

	/**
	 * Sets the onfocus event handler code on the current element.
	 * 
	 * @param functionRef
	 * @see http://www.w3schools.com/jsref/jsref_onfocus.asp
	 */
	@DOMSupport(DomLevel.ZERO)
	@Property(name = "onfocus")
	void setOnFocus(Object functionRef);

	/**
	 * Returns the onload event handler code on the current element.
	 * 
	 * @see http://www.w3schools.com/jsref/jsref_onload.asp
	 */
	@DOMSupport(DomLevel.ZERO)
	@Property(name = "onload")
	Object getOnLoad();

	/**
	 * Sets the onload event handler code on the current element.
	 * 
	 * @param functionRef
	 * @see http://www.w3schools.com/jsref/jsref_onload.asp
	 */
	@DOMSupport(DomLevel.ZERO)
	@Property(name = "onload")
	void setOnLoad(Object functionRef);

	/**
	 * Returns the onunload event handler code on the current element.
	 * 
	 * @see http://www.w3schools.com/jsref/jsref_onunload.asp
	 */
	@DOMSupport(DomLevel.ZERO)
	@Property(name = "onunload")
	Object getOnUnload();

	/**
	 * Sets the onunload event handler code on the current element.
	 * 
	 * @param functionRef
	 * @see http://www.w3schools.com/jsref/jsref_onunload.asp
	 */
	@DOMSupport(DomLevel.ZERO)
	@Property(name = "onunload")
	void setOnUnload(Object functionRef);

	/**
	 * Returns the onresize event handler code on the current element.
	 * 
	 * @see http://www.w3schools.com/jsref/jsref_onresize.asp
	 */
	@DOMSupport(DomLevel.ZERO)
	@Property(name = "onresize")
	Object getOnResize();

	/**
	 * Sets the onresize event handler code on the current element.
	 * 
	 * @param functionRef
	 * @see http://www.w3schools.com/jsref/jsref_onresize.asp
	 */
	@DOMSupport(DomLevel.ZERO)
	@Property(name = "onresize")
	void setOnResize(Object functionRef);

	/**
	 * Returns Opera object
	 */
	@BrowserSupport( { BrowserType.OPERA_7P })
	@Property
	Opera getOpera();

	// Functions

	/**
	 * Displays an alert box with a message and an OK button
	 * 
	 * @param message
	 */
	@Function
	void alert(Object message);

	/**
	 * Removes focus from the current window
	 */
	@JsSupport(JsVersion.MOZILLA_ONE_DOT_ONE)
	@Function
	void blur();

	/**
	 * Cancels a timeout set with setInterval()
	 * 
	 * @param id
	 *            The ID value returned by setInterval()
	 */
	@JsSupport(JsVersion.MOZILLA_ONE_DOT_TWO)
	@Function
	void clearInterval(int id);

	/**
	 * Cancels a timeout set with setTimeout()
	 * 
	 * @param id
	 *            The ID value returned by setTimeout()
	 */
	@Function
	void clearTimeout(int id);

	/**
	 * Closes the current window
	 */
	@Function
	void close();

	/**
	 * Displays a dialog box with a message and an OK and a Cancel button
	 * 
	 * @param message
	 *            string message
	 * @return true if OK is pressed
	 */
	@Function
	boolean confirm(String message);

	/**
	 * Sets focus to the current window
	 */
	@JsSupport(JsVersion.MOZILLA_ONE_DOT_ONE)
	@Function
	void focus();

	/**
	 * Moves a window relative to its current position
	 * 
	 * @param x
	 *            int number of pixels
	 * @param y
	 *            int number of pixels
	 */
	@JsSupport(JsVersion.MOZILLA_ONE_DOT_TWO)
	@Function
	void moveBy(int x, int y);

	/**
	 * Moves a window to the specified position
	 * 
	 * @param x
	 *            int number of pixels
	 * @param y
	 *            int number of pixels
	 */
	@JsSupport(JsVersion.MOZILLA_ONE_DOT_TWO)
	@Function
	void moveTo(int x, int y);

	/**
	 * Opens a new browser window
	 */
	@OverLoadFunc
	Window open();

	/**
	 * Opens a new browser window
	 * 
	 * @param url
	 *            Specifies the URL of the page to open. If no URL is specified,
	 *            a new window with about:blank is opened
	 * @return
	 */
	@OverLoadFunc
	Window open(String url);

	/**
	 * Opens a new browser window
	 * 
	 * @param url
	 *            Specifies the URL of the page to open.
	 * @param windowName
	 *            Specifies the target attribute or the name of the window. <br>
	 *            The following values are supported:
	 *            <ul>
	 *            <li>_blank - URL is loaded into a new window. This is default</li>
	 *            <li>_parent - URL is loaded into the parent frame</li>
	 *            <li>_self - URL replaces the current page</li>
	 *            <li>_top - URL replaces any framesets that may be loaded</li>
	 *            <li>name - The name of the window</li>
	 *            </ul>
	 * @return
	 */
	@OverLoadFunc
	Window open(String url, String windowName);

	/**
	 * Opens a new browser window
	 * 
	 * @param url
	 *            Specifies the URL of the page to open. If no URL is specified,
	 *            a new window with about:blank is opened
	 * @param windowName
	 *            Specifies the target attribute or the name of the window. <br>
	 *            The following values are supported:
	 *            <ul>
	 *            <li>_blank - URL is loaded into a new window. This is default</li>
	 *            <li>_parent - URL is loaded into the parent frame</li>
	 *            <li>_self - URL replaces the current page</li>
	 *            <li>_top - URL replaces any framesets that may be loaded</li>
	 *            <li>name - The name of the window</li>
	 *            </ul>
	 * @param features
	 *            A comma-separated list of items.
	 * @return
	 */
	@OverLoadFunc
	Window open(String url, String windowName, String features);

	/**
	 * Opens a new browser window
	 * 
	 * @param url
	 *            Specifies the URL of the page to open. If no URL is specified,
	 *            a new window with about:blank is opened
	 * @param windowName
	 *            Specifies the target attribute or the name of the window. <br>
	 *            The following values are supported:
	 *            <ul>
	 *            <li>_blank - URL is loaded into a new window. This is default</li>
	 *            <li>_parent - URL is loaded into the parent frame</li>
	 *            <li>_self - URL replaces the current page</li>
	 *            <li>_top - URL replaces any framesets that may be loaded</li>
	 *            <li>name - The name of the window</li>
	 *            </ul>
	 * @param features
	 *            A comma-separated list of items.
	 * @param replace
	 *            Specifies whether the URL creates a new entry or replaces the
	 *            current entry in the history list. <br>
	 *            The following values are supported:
	 *            <ul>
	 *            <li>true - URL replaces the current document in the history
	 *            list</li>
	 *            <li>false - URL creates a new entry in the history list</li>
	 *            </ul>
	 * @return
	 */
	@OverLoadFunc
	Window open(String url, String windowName, String features, boolean replace);

	@ProxyFunc("open")
	Window __open(Object arg1, Object arg2, Object arg3, Object arg4,
			Object arg5);

	/**
	 * Prints the contents of the current window
	 */
	@JsSupport(JsVersion.MOZILLA_ONE_DOT_FIVE)
	@Function
	void print();

	/**
	 * Displays a dialog box that prompts the user for input
	 * 
	 * @return String
	 */
	@OverLoadFunc
	String prompt();

	/**
	 * Displays a dialog box that prompts the user for input
	 * 
	 * @param message
	 *            The message to display in the dialog box. Default is "".
	 * @return String
	 */
	@OverLoadFunc
	String prompt(String message);

	/**
	 * Displays a dialog box that prompts the user for input
	 * 
	 * @param message
	 *            The message to display in the dialog box. Default is "".
	 * @param defaultReply
	 *            The default input text
	 * @return String
	 */
	@OverLoadFunc
	String prompt(String message, String defaultReply);

	@ProxyFunc("prompt")
	String __prompt(Object arg1, Object arg2, Object arg3, Object arg4,
			Object arg5);

	/**
	 * Resizes a window by the specified pixels
	 * 
	 * @param width
	 *            How many pixels to resize the width by. Can be a positive or a
	 *            negative number
	 * @param height
	 *            How many pixels to resize the height by. Can be a positive or
	 *            a negative number
	 */
	@JsSupport(JsVersion.MOZILLA_ONE_DOT_TWO)
	@Function
	void resizeBy(int width, int height);

	/**
	 * Resizes a window to the specified width and height
	 * 
	 * @param width
	 *            The width of the window (in pixels)
	 * @param height
	 *            The height of the window (in pixels)
	 */
	@JsSupport(JsVersion.MOZILLA_ONE_DOT_TWO)
	@Function
	void resizeTo(int width, int height);

	/**
	 * Scrolls the content by the specified number of pixels
	 * 
	 * @param x
	 *            How many pixels to scroll by, along the x-axis
	 * @param y
	 *            How many pixels to scroll by, along the y-axis
	 */
	@JsSupport(JsVersion.MOZILLA_ONE_DOT_TWO)
	@Function
	void scrollBy(int x, int y);

	/**
	 * Scrolls the content to the specified coordinates
	 * 
	 * @param x
	 *            The position to scroll to, along the x-axis
	 * @param y
	 *            The position to scroll to, along the y-axis
	 */
	@JsSupport(JsVersion.MOZILLA_ONE_DOT_TWO)
	@Function
	void scrollTo(int x, int y);

	/**
	 * Evaluates an expression at specified intervals
	 * 
	 * @param code
	 *            function or the code to be executed
	 * @param msecs
	 *            the number of milliseconds
	 * @return The ID value returned by setInterval() is used as the parameter
	 *         for the clearInterval() method
	 */
	@JsSupport(JsVersion.MOZILLA_ONE_DOT_TWO)
	@Function
	int setInterval(Object code, int msecs);

	/**
	 * Evaluates an expression after a specified number of milliseconds
	 * 
	 * @param code
	 *            function or the code to be executed
	 * @param msecs
	 *            The number of milliseconds to wait before executing the code
	 * @return The ID value returned by setTimeout() is used as the parameter
	 *         for the clearTimeout() method
	 */
	@Function
	int setTimeout(Object code, int msecs);

	/**
	 * Returns computed style of the element.
	 * 
	 * @param elem
	 * @param pseudoElem
	 * @return
	 */
	@BrowserSupport( { BrowserType.FIREFOX_1P })
	@Function
	HtmlElementStyle getComputedStyle(HtmlElement elem, String pseudoElem);

	@DOMSupport(DomLevel.TWO)
	@BrowserSupport( { BrowserType.FIREFOX_1P, BrowserType.OPERA_9P })
	@Function
	void addEventListener(String type, Object listener, boolean useCapture);

	@DOMSupport(DomLevel.TWO)
	@BrowserSupport( { BrowserType.IE_6P })
	@Function
	void attachEvent(String type, Object listener);

	/**
	 * Factory method to create a XMLHttpRequest
	 * 
	 * @return XMLHttpRequest
	 */
	XMLHttpRequest newXmlHttpReq();

	/**
	 * Factory method to create an Option
	 * 
	 * @return Option
	 */
	Option newOption();

	/**
	 * Factory method to create an Option
	 * 
	 * @param text
	 *            Specifies the text for the option
	 * @return Option
	 */
	Option newOption(String text);

	/**
	 * Factory method to create an Option
	 * 
	 * @param text
	 *            Specifies the text for the option
	 * @param value
	 *            Specifies the value for the option
	 * @return Option
	 */
	Option newOption(String text, String value);

	/**
	 * Factory method to create an Option
	 * 
	 * @param text
	 *            Specifies the text for the option
	 * @param value
	 *            Specifies the value for the option
	 * @param defaultSelected
	 *            A boolean specifying whether this option is initially selected
	 * @return Option
	 */
	Option newOption(String text, String value, boolean defaultSelected);

	/**
	 * Factory method to create an Option
	 * 
	 * @param text
	 *            Specifies the text for the option
	 * @param value
	 *            Specifies the value for the option
	 * @param defaultSelected
	 *            A boolean specifying whether this option is initially selected
	 * @param selected
	 *            boolean that specifies whether this option is currently
	 *            selected
	 * @return Option
	 */
	Option newOption(String text, String value, boolean defaultSelected,
			boolean selected);

	/**
	 * Factory method to create an Image
	 * 
	 * @return Image
	 */
	Image newImage();

	/**
	 * Factory method to create an Image
	 * 
	 * @return Image
	 */
	Image newImage(int width);

	/**
	 * Factory method to create an Image
	 * 
	 * @return Image
	 */
	Image newImage(int width, int height);

	@BrowserSupport(BrowserType.NONE)
	@JstExclude
	public BrowserType getBrowserType();
}
