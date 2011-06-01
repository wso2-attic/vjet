/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.active.client;

import org.ebayopensource.dsf.active.event.IBrowserBinding;
import org.ebayopensource.dsf.jsnative.HtmlElement;
import org.ebayopensource.dsf.jsnative.TextRange;
import org.ebayopensource.dsf.jsnative.TextRectangle;
import org.ebayopensource.dsf.jsnative.TextRectangleList;
import org.ebayopensource.dsf.jsnative.anno.BrowserType;
import org.ebayopensource.dsf.jsnative.anno.Function;

public class ATextRange extends ActiveObject implements TextRange {

	private static final long serialVersionUID = 505441596754466444L;

	private IBrowserBinding m_browserBinding;

	private final static String BOUND_HEIGHT_JS = "document.selection.createRange().boundingHeight";

	private final static String BOUND_LEFT_JS = "document.selection.createRange().boundingLeft";

	private final static String BOUND_TOP_JS = "document.selection.createRange().boundingTop";

	private final static String BOUND_WIDTH_JS = "document.selection.createRange().boundingWidth";

	private final static String OFFSET_LEFT_JS = "document.selection.createRange().offsetLeft";

	private final static String OFFSET_TOP_JS = "document.selection.createRange().offsetTop";

	private final static String HTML_TEXT_JS = "document.selection.createRange().htmlText";

	private final static String TEXT_JS = "document.selection.createRange().text";

	public ATextRange(BrowserType browserType, IBrowserBinding bowserBinding) {
		m_browserBinding = bowserBinding;
		populateScriptable(ATextRange.class, browserType);
	}

	public int getBoundingHeight() {
		String val;
		if (m_browserBinding != null) {
			val = m_browserBinding.executeJs(BOUND_HEIGHT_JS);
			try {
				return Integer.parseInt(val);
			} catch (Exception e) {
				// ignore exception
				return 0;
			}
		}
		return 0;
	}

	public int getBoundingLeft() {
		String val;
		if (m_browserBinding != null) {
			val = m_browserBinding.executeJs(BOUND_LEFT_JS);
			try {
				return Integer.parseInt(val);
			} catch (Exception e) {
				// ignore exception
				return 0;
			}
		}
		return 0;
	}

	public int getBoundingTop() {
		String val;
		if (m_browserBinding != null) {
			val = m_browserBinding.executeJs(BOUND_TOP_JS);
			try {
				return Integer.parseInt(val);
			} catch (Exception e) {
				// ignore exception
				return 0;
			}
		}
		return 0;
	}

	public int getBoundingWidth() {
		String val;
		if (m_browserBinding != null) {
			val = m_browserBinding.executeJs(BOUND_WIDTH_JS);
			try {
				return Integer.parseInt(val);
			} catch (Exception e) {
				// ignore exception
				return 0;
			}
		}
		return 0;

	}

	public String getHtmlText() {
		if (m_browserBinding != null) {
			return m_browserBinding.executeJs(HTML_TEXT_JS);
		}
		return null;
	}

	public int getOffsetLeft() {
		String val;
		if (m_browserBinding != null) {
			val = m_browserBinding.executeJs(OFFSET_LEFT_JS);
			try {
				return Integer.parseInt(val);
			} catch (Exception e) {
				// ignore exception
				return 0;
			}
		}
		return 0;
	}

	public int getOffsetTop() {
		String val;
		if (m_browserBinding != null) {
			val = m_browserBinding.executeJs(OFFSET_TOP_JS);
			try {
				return Integer.parseInt(val);
			} catch (Exception e) {
				// ignore exception
				return 0;
			}
		}
		return 0;
	}

	public String getText() {
		if (m_browserBinding != null) {
			return m_browserBinding.executeJs(TEXT_JS);
		}
		return null;
	}
	
	public Object valueOf(String type) {
		if (type.equals("boolean")) {
			return Boolean.TRUE;
		}
		else if (type.equals("string")) {
			return getClass().getName();
		}
		return null;
	}

	@Override
	public void collapse() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void collapse(boolean start) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int compareEndPoints(String sType, TextRange oRange) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public TextRange duplicate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean execCommand(String sCommand, boolean bUserInterface,
			Object vValue) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean execCommand(String sCommand, boolean bUserInterface) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean execCommand(String sCommand) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void execCommandShowHelp(String cmdID, Function pfRet) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean expand(String sUnit) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean findText(String sText, int iSearchScope, int iFlags) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean findText(String sText, int iSearchScope) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean findText(String sText) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getBookmark() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TextRectangle getBoundingClientRect() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TextRectangleList getClientRects() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean inRange(TextRange oRange) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEqual(TextRange oRange) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int move(String sUnit, int iCount) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int move(String sUnit) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int moveEnd(String sUnit, int iCount) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int moveEnd(String sUnit) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int moveStart(String sUnit, int iCount) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int moveStart(String sUnit) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean moveToBookmark(String sBookmark) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void moveToElementText(HtmlElement oElement) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveToPoint(int iX, int iY) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public HtmlElement parentElement() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void pasteHTML(String sHTMLText) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean queryCommandEnabled(String sCmdID) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean queryCommandIndeterm(String sCmdID) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean queryCommandState(String sCmdID) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean queryCommandSupported(String sCmdID) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String queryCommandText(String cmdID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object queryCommandValue(String sCmdID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void scrollIntoView(boolean bAlignToTop) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void scrollIntoView() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void select() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setEndPoint(String sType, TextRange oTextRange) {
		// TODO Auto-generated method stub
		
	}
}
