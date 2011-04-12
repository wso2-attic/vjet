/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.js;

public class Device {
	/**
	 * Device types
	 */
	public static final int NO_DEVICE = -1;
	public static final int WML_UP_4 = 0;
	public static final int WML_NOK_7 = 1;
	public static final int WML2_OW = 2;
	public static final int HDML_UP_3 = 3;
	public static final int PALM7 = 4;
	public static final int I_MODE = 5;
	public static final int XML = 6;
	public static final int SPEECH = 7;
	public static final int VOICE_XML = 8;
	public static final int HTML = 9;
	public static final int CE_HTML = 10;
	public static final int J_PHONE = 11;
	public static final int GUI_TOOL = 12;

	public static final String TYPE_LABEL[] =
		{
			"WML_UP4",
			"WML_NOKIA7",
			"WML2_OW",
			"HDML_UP3",
			"HTML_PALM7",
			"HTML_iMODE",
			"XML",
			"SPEECH",
			"XML_VOICE",
			"HTML",  // ignoreHtmlKeyword
			"HTML_CE",
			"HTML_JPHONE",
			"GUI_TOOL" };

	public static final String FILE_SUFFIX[] =
		{ ".wml", ".hdml", ".html", ".xml", ".wav", ".vxml" };

	public static final String CONTENT_TYPE[] =
		{
			"text/vnd.wap.wml",
			"text/vnd.wap.wml",
			"text/vnd.wap.wml",
			"text/x-hdml",
		//   "text/html",
		"text/html", "text/xhtml", "", "text/vxml"
		//   "text/html",

		//   "text/html",
		//     "text/html"
	};

	public static final String ML_BR[] = { "<br/>", "<br>", "<BR>", "<BR/>" };

	public static final String LINE_BREAK[] =
		{
			"<br/>\n",
			"<br/>\n",
			"<br/>\n",
			"<BR>\n",
			"<br>\n",
			"",
			"",
			"<break/>\n",
			};

	static final String ML_ELT_APOS[] = { "&apos;", "\'" };
	static final String ML_ELT_DOL[] = { "$$", "&dol;", "$" };
	static final String ML_ELT_NBSP[] = { "&nbsp;", " " };
}
