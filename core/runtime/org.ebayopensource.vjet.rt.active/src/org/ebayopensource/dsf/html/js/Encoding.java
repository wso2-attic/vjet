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
import java.util.Map;

public class Encoding {

	public static final int SOURCE_UNKNOWN = 0; // nothing specified
	public static final int SOURCE_DEFAULT = 1; // UTF-8
	public static final int SOURCE_EUROPE = 2; // ISO-8859-1
	public static final int SOURCE_JP_AUTO = 3; // japanese auto detect
	public static final int SOURCE_SJIS = 4; // shift-jis
	public static final int SOURCE_SJIS2 = 5; // shift-jis also
	public static final int SOURCE_NEW_JIS = 6; // iso 2022 new jis
	public static final int SOURCE_EUC_JP = 7; // JIS x 0201, 0208
	public static final int SOURCE_EUC_JP2 = 8; // JIS x 0201, 0208 als
	public static final int SOURCE_BIG5 = 9; // CNS 11 643
	public static final int SOURCE_EUC_CN = 10; // GB2312
	public static final int SOURCE_EUC_KR = 11; // KS C 5601
	public static final int SOURCE_EUC_TW = 12; // CNS 11 643
	public static final int SOURCE_CYRILLIC = 13; // cyrillic
	public static final int SOURCE_ARABIC = 14; // ISO-8859-6
	public static final int SOURCE_GREEK = 15; // greek
	public static final int SOURCE_HEBREW = 16; // ISO-8859-8
	public static final int SOURCE_WIN_EEUR = 17; // eastern eur
	public static final int SOURCE_WIN_CY = 18; // cyrillic
	public static final int SOURCE_WIN_GR = 19; // greek
	public static final int SOURCE_WIN_TK = 20; // turkish
	public static final int SOURCE_WIN_IL = 21; // HEBREW
	public static final int SOURCE_WIN_AR = 22; // Arabic
	public static final int SOURCE_WIN_BL = 23; // Baltic
	public static final int SOURCE_WIN_VN = 24; // Vietnamese

	public static final String JAVA_ENCODING[] = {
		// These are codes for Java's encoding parameters
		// see: http://java.sun.com/products/jdk/1.2/docs/guide/internat/encoding.doc.html
		// most nonASCII need i18n.jar
		"ISO8859_1", // allow null or unspecified to work
		"UTF8",
			"ISO8859_1",
			"JISAutoDetect",
			"SJIS",
			"SJIS",
			"ISO2022JN",
			"EUC_JP",
			"EUC_JP",
			"Big5",  //NOPMD
		//NOPMD
		"GBK",
			"EUC_KR",
			"",
			"ISO8859_5",
			"ISO8859_6",
			"ISO8859_7",
			"ISO8859_8",
			"Cp1250",
		//
		"Cp1251", //
		"Cp1253", //
		"Cp1254",
		//  MOULI: The original Encoding "CP...." was causing Bug 571.
		"Cp1255", //         Changed to "Cp...."
		"Cp1256", //
		"Cp1257", //
		"Cp1258" //
	};

	public static final String HDML[] = {
		// these are mime-type charset=codes for Phone.com HDML
		null, "US-ASCII", "ISO-8859-1", "SHIFT_JIS", //NOPMD
		"EUC-JP",
			"Big5",
			"GB2312",
			"korean",
			"",
			"ISO-8859-5",
			"ISO-8859-6",
			"ISO-8859-7",
			"ISO-8859-8",
			"windows-1250",
			"windows-1251",  //NOPMD
		//NOPMD
		"windows-1253", //NOPMD
		"windows-1254", //NOPMD
		"windows-1255", //NOPMD
		"windows-1256", "windows-1257", "windows-1258" };

	public static final String WML[] = {
		// these are mime-type charset=codes for Phone.com WML
		null,
			"UTF-8",
			"ISO-8859-1",
			"SHIFT_JIS",
			"SHIFT_JIS",
			"SHIFT_JIS",
			"SHIFT_JIS",
			"EUC-JP",
			"EUC-JP",
			"Big5",
			"GB2312",
			"KS_C_5601_1987",
		// phone.com may want "korean"???, nokia not ok??
		"Big5",
			"ISO-8859-5",
			"ISO-8859-6",
			"ISO-8859-7",
			"ISO-8859-8",
			"windows-1250",
			"windows-1251",
			"windows-1253",
			"windows-1254",
			"windows-1255",
			"windows-1256",
			"windows-1257",
			"windows-1258" };

	public static final String HTML[] = {
		// these are html charset=codes for http-equiv charset=...
		null, "UTF-8", "iso-8859-1", //NOPMD
		"japan-auto-detect",
			"x-sjis",
			"shift_jis",
			"iso-2022-jp",
			"x-euc-jp",
			"euc-jp",
			"big5",
			"gb2312",
			"euc-kr",
			"x-euc-tw",
			"iso-8859-5",
			"iso-8859-6",
			"iso-8859-7",
			"iso-8859-8",
			"windows-1250",
			"windows-1251",
			"windows-1253",
			"windows-1254",
			"windows-1255",
			"windows-1256",
			"windows-1257",
			"windows-1258" };

	public static final String FILE_ENCODING_SUFFIX[] =
		{
			"",
			".utf8",
			".iso1",
			".sjis",
			".sjis",
			".sjis",
			".eucj",
			".eucj",
			".big5",
			".gb2312",
			".euck",
			".euct",
			".iso5",
			".iso6",
			".iso7",
			".iso8",
			".w50",
			".w51",
			".w53",
			".w54",
			".w55",
			".w56",
			".w57",
			".w58" };

	public static final String COUNTRY_DEFAULT[][] =
		{ { "jp", "japan-auto-detect" }, {
			"fr", "iso-8859-1" }, {
			"de", "iso-8859-1" }, {
			"it", "iso-8859-1" }, {
			"es", "iso-8859-1" }, {
			"gr", "windows-1253" }, {
			"ch", "iso-8859-1" }, {
			"kr", "euc-kr" }
	};

	public static Map s_defaultCharsetPerLanguage = new HashMap();
	static {
		s_defaultCharsetPerLanguage.put("en", "iso-8859-1");
		s_defaultCharsetPerLanguage.put("es", "iso-8859-1");
		s_defaultCharsetPerLanguage.put("fr", "iso-8859-1");
		s_defaultCharsetPerLanguage.put("fi", "iso-8859-1");
		s_defaultCharsetPerLanguage.put("de", "iso-8859-1");
		s_defaultCharsetPerLanguage.put("it", "iso-8859-1");
		s_defaultCharsetPerLanguage.put("da", "iso-8859-1");
		s_defaultCharsetPerLanguage.put("nl", "iso-8859-1");
		s_defaultCharsetPerLanguage.put("ja", "shift_jis");
		s_defaultCharsetPerLanguage.put("ru", "windows-1251");
		s_defaultCharsetPerLanguage.put("sr", "windows-1251");
		s_defaultCharsetPerLanguage.put("sk", "windows-1251");
		s_defaultCharsetPerLanguage.put("sl", "windows-1251");
		s_defaultCharsetPerLanguage.put("uz", "windows-1251");
		s_defaultCharsetPerLanguage.put("gr", "windows-1253");
		s_defaultCharsetPerLanguage.put("he", "windows-1255");
		s_defaultCharsetPerLanguage.put("kr", "euc-kr");
		s_defaultCharsetPerLanguage.put("zh-cn", "gb2312");
		s_defaultCharsetPerLanguage.put("zh-tw", "big5");
		s_defaultCharsetPerLanguage.put("zh", "big5");
	}

	public static String getDefaultCharset(String language) {
		// first try to get charset on full parameter (lang - country maybe, such as chinese simplified vs traditional)
		String charset = (String) s_defaultCharsetPerLanguage.get(language);
		// if no match, try just the language (to acommodate western multiple dialects)
		if (charset == null) {
			charset =
				(String) s_defaultCharsetPerLanguage.get(
					language.substring(0, 2));
		}

		return charset;
	}

	public static String getCharset(int index, int clientML) {
		String charset;
		switch (clientML) {
			case Device.WML_UP_4 :
			case Device.WML_NOK_7 :
				charset = Encoding.WML[index];
				break;
			case Device.HDML_UP_3 :
				charset = Encoding.HDML[index];
				break;
			default :
				charset = Encoding.HTML[index];
				break;
		}
		return charset;
	}

	public static int detectSourceEncoding(String sourceCharset) {
		return detectSourceEncoding(sourceCharset, true);
	}

	public static int detectSourceEncoding(
		String sourceCharset,
		boolean forSource) {
		// note the discovered source charset
		if (sourceCharset == null || sourceCharset.length() == 0) {
			return SOURCE_UNKNOWN;
		}

		int i;
		int sourceEncoding = SOURCE_UNKNOWN;
		for (i = 0; i < Encoding.HTML.length; i++) {
			if (Encoding.HTML[i] != null
				&& Encoding.HTML[i].equalsIgnoreCase(sourceCharset)) {
				break;
			}

		}

		if (i < Encoding.HTML.length) {
			sourceEncoding = i;
			if (forSource
				&& (sourceEncoding == SOURCE_SJIS
					|| sourceEncoding == SOURCE_SJIS2
					|| sourceEncoding == SOURCE_NEW_JIS
					|| sourceEncoding == SOURCE_EUC_JP
					|| sourceEncoding == SOURCE_EUC_JP2)) {
				sourceEncoding = SOURCE_JP_AUTO;
			}

		}
		return sourceEncoding;
	}

	public static String countryDefaultCharset(String urlBase) {
		String localUrlBase = urlBase; 
		String charset = null;
		int i;
		// find country code in base
		i = localUrlBase.length() - 1;
		if (i >= 0 && localUrlBase.charAt(i) == '/') // drop any trailing '/'
			{
				localUrlBase = urlBase.substring(0, i);
		}
		i = localUrlBase.lastIndexOf(".");
		if (i >= 0) // trim before the .country
			{
				localUrlBase = urlBase.substring(i + 1);
		}
		// find country in table
		for (i = 0; i < COUNTRY_DEFAULT.length; i++) {
			if (COUNTRY_DEFAULT[i][0].equalsIgnoreCase(localUrlBase)) {

				charset = COUNTRY_DEFAULT[i][1];
				break;
			}
		}

		return charset;
	}

}
