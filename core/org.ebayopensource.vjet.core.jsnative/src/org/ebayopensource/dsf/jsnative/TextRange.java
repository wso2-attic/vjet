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
import org.ebayopensource.dsf.jsnative.anno.JsArray;
import org.ebayopensource.dsf.jsnative.anno.JsMetatype;
import org.ebayopensource.dsf.jsnative.anno.OverLoadFunc;
import org.ebayopensource.dsf.jsnative.anno.Property;
import org.mozilla.mod.javascript.IWillBeScriptable;

/**
 * JavaScript object representing document.selection.TextRange object. (IE
 * specific)
 */
@JsMetatype
public interface TextRange extends IWillBeScriptable {
	/**
	 * 
	 * Returns the HTML fragment for the selected text range.
	 */
	@BrowserSupport({ BrowserType.IE_6P })
	@Property
	String getHtmlText();

	/**
	 * 
	 * Retrieves the text contained within the range
	 */
	@BrowserSupport({ BrowserType.IE_6P })
	@Property
	String getText();

	/**
	 * 
	 * Retrieves the width of the rectangle that bounds the text range.
	 */
	@BrowserSupport({ BrowserType.IE_6P })
	@Property
	int getBoundingWidth();

	/**
	 * 
	 * Retrieves the height of the rectangle that bounds the text range.
	 */
	@BrowserSupport({ BrowserType.IE_6P })
	@Property
	int getBoundingHeight();

	/**
	 * 
	 * Retrieves the left of the rectangle that bounds the text range.
	 */
	@BrowserSupport({ BrowserType.IE_6P })
	@Property
	int getBoundingLeft();

	/**
	 * 
	 * Retrieves the top of the rectangle that bounds the text range.
	 */
	@BrowserSupport({ BrowserType.IE_6P })
	@Property
	int getBoundingTop();

	/**
	 * 
	 * Retrieves the left coordinate of the rectangle that bounds the text
	 * range.
	 */
	@BrowserSupport({ BrowserType.IE_6P })
	@Property
	int getOffsetLeft();

	/**
	 * 
	 * Retrieves the top coordinate of the rectangle that bounds the text range.
	 */
	@BrowserSupport({ BrowserType.IE_6P })
	@Property
	int getOffsetTop();

	/**
	 * Moves the insertion point to the beginning or end of the current range.
	 */
	@BrowserSupport({ BrowserType.IE_6P })
	@OverLoadFunc
	void collapse();

	@BrowserSupport({ BrowserType.IE_6P })
	@OverLoadFunc
	void collapse(boolean start);

	/**
	 * Compares an end point of a TextRange object with an end point of another
	 * range.
	 * 
	 * @param Type
	 *            Required. String that specifies one of the following
	 *            values:StartToEnd Compare the start of the TextRange object
	 *            with the end of the oRange parameter. StartToStart Compare the
	 *            start of the TextRange object with the start of the oRange
	 *            parameter. EndToStart Compare the end of the TextRange object
	 *            with the start of the oRange parameter. EndToEnd Compare the
	 *            end of the TextRange object with the end of the oRange
	 *            parameter.
	 * @param oRange
	 *            Required. TextRange object that specifies the range to compare
	 *            with the object.
	 */
	@BrowserSupport({ BrowserType.IE_6P })
	@Function
	int compareEndPoints(String sType, TextRange oRange);

	/**
	 * Returns a duplicate of the TextRange.
	 * 
	 * @return Returns a TextRange object.
	 */
	@BrowserSupport({ BrowserType.IE_6P })
	@Function
	TextRange duplicate();

	/**
	 * Executes a command on the current document, current selection, or the
	 * given range.
	 * 
	 * @param sCommand
	 *            Required. String that specifies the command to execute. This
	 *            command can be any of the command identifiers that can be
	 *            executed in script.
	 * @param bUserInterface
	 *            Optional. Boolean that specifies one of the following values.
	 *            false Default. Do not display a user interface. Must be
	 *            combined with vValue, if the command requires a value. true
	 *            Display a user interface if the command supports one.
	 * @param vValue
	 *            Optional. Variant that specifies the string, number, or other
	 *            value to assign. Possible values depend on the command.
	 */
	@BrowserSupport({ BrowserType.IE_6P })
	@OverLoadFunc
	boolean execCommand(String sCommand, boolean bUserInterface, Object vValue);

	@BrowserSupport({ BrowserType.IE_6P })
	@OverLoadFunc
	boolean execCommand(String sCommand, boolean bUserInterface);

	@BrowserSupport({ BrowserType.IE_6P })
	@OverLoadFunc
	boolean execCommand(String sCommand);

	/**
	 * Displays help information for the given command identifier.
	 * 
	 * @param cmdID
	 *            Required. String that contains an identifier of a command. It
	 *            can be any command identifier given in the list of Command
	 *            Identifiers.
	 * @param pfRet
	 *            Required. Pointer to a Boolean that receives true if
	 *            successful, or false otherwise.
	 */
	@BrowserSupport({ BrowserType.IE_6P })
	@Function
	void execCommandShowHelp(String cmdID, Function pfRet);

	/**
	 * Expands the range so that partial units are completely contained.
	 * 
	 * @param sUnit
	 *            Required. String that specifies the units to move in the
	 *            range, using one one of the following values: character
	 *            Expands a character. word Expands a word. A word is a
	 *            collection of characters terminated by a space or another
	 *            white-space character, such as a tab. sentence Expands a
	 *            sentence. A sentence is a collection of words terminated by an
	 *            ending punctuation character, such as a period. textedit
	 *            Expands to enclose the entire range.
	 * 
	 * @return Boolean that returns one of the following values: true The range
	 *         was successfully expanded. false The range was not expanded.
	 */
	@BrowserSupport({ BrowserType.IE_6P })
	@Function
	boolean expand(String sUnit);

	/**
	 * @param sText
	 *            Required. String that specifies the text to find.
	 * @param iSearchScope
	 *            Optional. Integer that specifies the number of characters to
	 *            search from the starting point of the range. A positive
	 *            integer indicates a forward search; a negative integer
	 *            indicates a backward search.
	 * 
	 * @param iFlags
	 *            Optional. Integer that specifies one or more of the following
	 *            flags to indicate the type of search:
	 * 
	 *            0 Default. Match partial words. 1 Match in reverse. 2 Match
	 *            whole words only. 4 Match case. 0x20000 Match bytes.
	 *            0x20000000 Match diacritical marks. 0x40000000 Match Kashida
	 *            character. 0x80000000 Match AlefHamza character.
	 */

	@BrowserSupport({ BrowserType.IE_6P })
	@OverLoadFunc
	boolean findText(String sText, int iSearchScope, int iFlags);

	@BrowserSupport({ BrowserType.IE_6P })
	@OverLoadFunc
	boolean findText(String sText, int iSearchScope);

	@BrowserSupport({ BrowserType.IE_6P })
	@OverLoadFunc
	boolean findText(String sText);

	/**
	 * Retrieves a bookmark (opaque string) that can be used with moveToBookmark
	 * to return to the same range.
	 * 
	 * @return String. Returns the bookmark if successfully retrieved, or null
	 *         otherwise.
	 */
	@BrowserSupport({ BrowserType.IE_6P })
	@Function
	String getBookmark();

	/**
	 * Retrieves an object that specifies the bounds of a collection of
	 * TextRectangle objects.
	 * 
	 * @return Returns a TextRectangle object. Each rectangle has four integer
	 *         properties (top, left, right, and bottom) that represent a
	 *         coordinate of the rectangle, in pixels.
	 */
	@BrowserSupport({ BrowserType.IE_6P })
	@Function
	TextRectangle getBoundingClientRect();

	/**
	 * Retrieves a collection of rectangles that describes the layout of the
	 * contents of an object or range within the client. Each rectangle
	 * describes a single line.
	 * 
	 * @return Returns the TextRectangle collection. Each rectangle has four
	 *         integer properties (top, left, right, and bottom) that each
	 *         represent a coordinate of the rectangle, in pixels.
	 */
	@BrowserSupport({ BrowserType.IE_6P })
	@Function
	@JsArray(TextRectangle.class)
	TextRectangleList getClientRects();

	/**
	 * Returns a value indicating whether one range is contained within another.
	 * 
	 * @param oRange
	 *            Required. TextRange object that might be contained.
	 * @return Boolean that returns one of the following possible values. true
	 *         oRange is contained within or is equal to the TextRange object on
	 *         which the method is called. false oRange is not contained within
	 *         the TextRange object on which the method is called.
	 */
	@BrowserSupport({ BrowserType.IE_6P })
	@Function
	boolean inRange(TextRange oRange);

	/**
	 * Returns a value indicating whether the specified range is equal to the
	 * current range.
	 * 
	 * @param oCompareRange
	 *            Required. TextRange object to compare with the current
	 *            TextRange object.
	 * 
	 * @return Boolean that returns one of the following possible values. true
	 *         oCompareRange is equal to the parent object. false oCompareRange
	 *         is not equal to the parent object.
	 */
	@BrowserSupport({ BrowserType.IE_6P })
	@Function
	boolean isEqual(TextRange oRange);

	/**
	 * Collapses the given text range and moves the empty range by the given
	 * number of units.
	 * 
	 * @param sUnit
	 *            Required. String that specifies the units to move, using one
	 *            of the following values:
	 * 
	 *            character Moves one or more characters. word Moves one or more
	 *            words. A word is a collection of characters terminated by a
	 *            space or some other white-space character, such as a tab.
	 *            sentence Moves one or more sentences. A sentence is a
	 *            collection of words terminated by a punctuation character,
	 *            such as a period. textedit Moves to the start or end of the
	 *            original range.
	 * @param iCount
	 *            Optional. Integer that specifies the number of units to move.
	 *            This can be positive or negative. The default is 1.
	 * 
	 * @return Integer that returns the number of units moved.
	 */

	@BrowserSupport({ BrowserType.IE_6P })
	@OverLoadFunc
	int move(String sUnit, int iCount);

	@BrowserSupport({ BrowserType.IE_6P })
	@OverLoadFunc
	int move(String sUnit);

	/**
	 * Changes the end position of the range.
	 * 
	 * @param sUnit
	 *            Required. String that specifies the units to move, using one
	 *            of the following values:
	 * 
	 *            character Moves one or more characters. word Moves one or more
	 *            words. A word is a collection of characters terminated by a
	 *            space or some other white-space character, such as a tab.
	 *            sentence Moves one or more sentences. A sentence is a
	 *            collection of words terminated by a punctuation character,
	 *            such as a period. textedit Moves to the start or end of the
	 *            original range.
	 * @param iCount
	 *            Optional. Integer that specifies the number of units to move.
	 *            This can be positive or negative. The default is 1.
	 * 
	 * @return Integer that returns the number of units moved.
	 */

	@BrowserSupport({ BrowserType.IE_6P })
	@OverLoadFunc
	int moveEnd(String sUnit, int iCount);

	@BrowserSupport({ BrowserType.IE_6P })
	@OverLoadFunc
	int moveEnd(String sUnit);

	/**
	 * Changes the start position of the range
	 * 
	 * @param sUnit
	 *            Required. String that specifies the units to move, using one
	 *            of the following values:
	 * 
	 *            character Moves one or more characters. word Moves one or more
	 *            words. A word is a collection of characters terminated by a
	 *            space or some other white-space character, such as a tab.
	 *            sentence Moves one or more sentences. A sentence is a
	 *            collection of words terminated by a punctuation character,
	 *            such as a period. textedit Moves to the start or end of the
	 *            original range.
	 * @param iCount
	 *            Optional. Integer that specifies the number of units to move.
	 *            This can be positive or negative. The default is 1.
	 * 
	 * @return Integer that returns the number of units moved.
	 */

	@BrowserSupport({ BrowserType.IE_6P })
	@OverLoadFunc
	int moveStart(String sUnit, int iCount);

	@BrowserSupport({ BrowserType.IE_6P })
	@OverLoadFunc
	int moveStart(String sUnit);

	/**
	 * Moves to a bookmark.
	 * 
	 * @param sBookmark
	 *            Required. String that specifies the bookmark to move to.
	 * @return Boolean that returns one of the following possible values: true
	 *         Successfully moved to the bookmark. false Move to the bookmark
	 *         failed.
	 */
	@BrowserSupport({ BrowserType.IE_6P })
	@Function
	boolean moveToBookmark(String sBookmark);

	/**
	 * 
	 * Moves the text range so that the start and end positions of the range
	 * encompass the text in the given element.
	 * 
	 * @param oElement
	 *            Required. Object that specifies the element object to move to.
	 * @return void
	 */
	@BrowserSupport({ BrowserType.IE_6P })
	@Function
	void moveToElementText(HtmlElement oElement);

	/**
	 * 
	 * Moves the start and end positions of the text range to the given point.
	 * 
	 * @param iX
	 *            Required. Integer that specifies the horizontal offset
	 *            relative to the upper-left corner of the window, in pixels.
	 * @param iY
	 *            Required. Integer that specifies the vertical offset relative
	 *            to the upper-left corner of the window, in pixels.
	 */
	@BrowserSupport({ BrowserType.IE_6P })
	@Function
	void moveToPoint(int iX, int iY);

	/**
	 * @return Returns the parent element object if successful, or null
	 *         otherwise.
	 */
	@BrowserSupport({ BrowserType.IE_6P })
	@Function
	HtmlElement parentElement();

	/**
	 * Pastes HTML text into the given text range, replacing any previous text
	 * and HTML elements in the range.
	 * 
	 * @param sHTMLText
	 *            Required. String that specifies the HTML text to paste. The
	 *            string can contain text and any combination of the HTML tags
	 *            described in HTML Elements.
	 */
	@BrowserSupport({ BrowserType.IE_6P })
	@Function
	void pasteHTML(String sHTMLText);

	/**
	 * @return Returns a Boolean value that indicates whether a specified
	 *         command can be successfully executed using execCommand, given the
	 *         current state of the document.
	 * 
	 * @param sCmdID
	 *            Required. String that specifies a command identifier.
	 * 
	 */
	@BrowserSupport({ BrowserType.IE_6P })
	@Function
	boolean queryCommandEnabled(String sCmdID);

	/**
	 * @return Returns a Boolean value that indicates whether the specified
	 *         command is in the indeterminate state.
	 * @param sCmdID
	 *            Required. String that specifies a command identifier.
	 */
	@BrowserSupport({ BrowserType.IE_6P })
	@Function
	boolean queryCommandIndeterm(String sCmdID);

	/**
	 * Returns a Boolean value that indicates the current state of the command.
	 * 
	 * @param sCmdID
	 *            Required. String that specifies a command identifier.
	 * 
	 */
	@BrowserSupport({ BrowserType.IE_6P })
	@Function
	boolean queryCommandState(String sCmdID);

	/**
	 * Returns a Boolean value that indicates whether the current command is
	 * supported on the current range.
	 * 
	 * @param sCmdID
	 *            Required. String that specifies a command identifier.
	 * 
	 */
	@BrowserSupport({ BrowserType.IE_6P })
	@Function
	boolean queryCommandSupported(String sCmdID);

	/**
	 * Retrieves the string associated with a command.
	 * 
	 * @param cmdID
	 *            Required. String that contains the identifier of a command.
	 *            This can be any command identifier given in the list of
	 *            Command Identifiers.
	 * @return Pointer to a String where the text associated with the command
	 *         will be stored.
	 */
	@BrowserSupport({ BrowserType.IE_6P })
	@Function
	String queryCommandText(String cmdID);

	/**
	 * Returns the current value of the document, range, or current selection
	 * for the given command.
	 * 
	 * @param sCmdID
	 *            Required. String that specifies a command identifier.
	 * @return Variant that returns the command value for the document, range,
	 *         or current selection, if supported. Possible values depend on
	 *         sCmdID. If not supported, this method returns a Boolean set to
	 *         false.
	 */
	@BrowserSupport({ BrowserType.IE_6P })
	@Function
	Object queryCommandValue(String sCmdID);

	/**
	 * Causes the object to scroll into view, aligning it either at the top or
	 * bottom of the window.
	 * 
	 * @param bAlignToTop
	 *            Optional. Boolean that specifies one of the following values:
	 * 
	 *            true Default. Scrolls the object so that top of the object is
	 *            visible at the top of the window. false Scrolls the object so
	 *            that the bottom of the object is visible at the bottom of the
	 *            window.
	 * 
	 */
	@BrowserSupport({ BrowserType.IE_6P })
	@OverLoadFunc
	void scrollIntoView(boolean bAlignToTop);

	@BrowserSupport({ BrowserType.IE_6P })
	@OverLoadFunc
	void scrollIntoView();

	/**
	 * Makes the selection equal to the current object.
	 * 
	 */
	@BrowserSupport({ BrowserType.IE_6P })
	@Function
	void select();

	/**
	 * Sets the endpoint of one range based on the endpoint of another range.
	 * 
	 * @param sType
	 *            Required. String that specifies the endpoint to transfer using
	 *            one of the following values.
	 * 
	 *            StartToEnd Move the start of the TextRange object to the end
	 *            of the specified oTextRange parameter. 
	 *            StartToStart Move the
	 *            start of the TextRange object to the start of the specified
	 *            oTextRange parameter. 
	 *            EndToStart Move the end of the TextRange
	 *            object to the start of the specified oTextRange parameter.
	 *            EndToEnd Move the end of the TextRange object to the end of
	 *            the specified oTextRange parameter.
	 * @param oTextRange
	 *            Required. TextRange object from which the source endpoint is
	 *            to be taken.
	 */
	
	@BrowserSupport({ BrowserType.IE_6P })
	@Function void setEndPoint(String sType, TextRange oTextRange);


	/**
	 * Only for Rhino support
	 * 
	 * @param type
	 * @return
	 */
	@BrowserSupport({ BrowserType.RHINO_1P })
	@Function
	Object valueOf(String type);

}
