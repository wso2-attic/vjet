/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.codeassist.compliance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ebayopensource.dsf.jsnative.anno.BrowserType;

/** 
 * Stores all registered browsers keys.
 * @see SupportedByAnnotationAcceptor
 */
public class PredefinedBrowsersPreferenceKeys {

	private static final List<IBrowserKey> PREDEF_KEYS = new ArrayList<IBrowserKey>();

	static final IBrowserKey MSIE_KEY;
	static final IBrowserKey FF_KEY;
	static final IBrowserKey OPERA_KEY;
	static final IBrowserKey SAFARI_KEY;

	private static final String MSIE = "Internet Explorer";
	private static final Version MSIE_6X = new Version(6);
	private static final Version MSIE_6XP = new Version(6, true);
	private static final Version MSIE_7X = new Version(7);
	private static final Version MSIE_7XP = new Version(7, true);
	private static final Version MSIE_8X = new Version(8);
	private static final Version MSIE_8XP = new Version(8, true);

	private static final String FF = "Firefox/Mozilla";
	private static final Version FF_1X = new Version(1);
	private static final Version FF_1XP = new Version(1, true);
	private static final Version FF_2X = new Version(2);
	private static final Version FF_2XP = new Version(2, true);
	private static final Version FF_3X = new Version(3);
	private static final Version FF_3XP = new Version(3, true);

	private static final String OPERA = "Opera";
	private static final Version OPERA_7X = new Version(7);
	private static final Version OPERA_7XP = new Version(7, true);
	private static final Version OPERA_8X = new Version(8);
	private static final Version OPERA_8XP = new Version(8, true);
	private static final Version OPERA_9X = new Version(9);
	private static final Version OPERA_9XP = new Version(9, true);

	private static final String SAFARI = "Safari";
	private static final Version SAFARI_3 = new Version(3);
	private static final Version SAFARI_3P = new Version(3, true);

	static {

		MSIE_KEY = register(MSIE, new Version[] { MSIE_6X, MSIE_6XP, MSIE_7X,
				MSIE_7XP, MSIE_8X, MSIE_8XP }, BrowserType.IE_6);

		FF_KEY = register(FF, new Version[] { FF_1X, FF_1XP, FF_2X, FF_2XP,
				FF_3X, FF_3XP }, BrowserType.FIREFOX_1);

		OPERA_KEY = register(OPERA, new Version[] { OPERA_7X, OPERA_7XP,
				OPERA_8X, OPERA_8XP, OPERA_9X, OPERA_9XP }, BrowserType.OPERA_7);

		SAFARI_KEY = register(SAFARI, new Version[] { SAFARI_3, SAFARI_3P },
				BrowserType.SAFARI_3);

	}

	public static IBrowserKey[] getPredefinedKeys() {
		return PREDEF_KEYS.toArray(new IBrowserKey[] {});
	}

	private static class Version {

		private static final String ABOVE = ".x and above";
		private static final String ONLY = ".x only";

		private static final Map<String, Version> vers = new HashMap<String, Version>();

		int version;
		boolean plus;

		Version(int version) {
			this(version, false);
		}

		Version(int version, boolean plus) {
			this.version = version;
			this.plus = plus;
			vers.put(toString(), this);
		}

		public String toString() {
			return version + (plus ? ABOVE : ONLY);
		}

		static int parseVersionNumber(String value) {

			if (vers.containsKey(value)) {

				Version ver = vers.get(value);
				return ver.plus ? -ver.version : ver.version;

			}

			return 0;
		}
	}

	public static interface IBrowserKey {

		String getBrowser();

		String[] getVersions();

		String isTargetedBrowserPredefKey();

		String takeTargetedBrowserVerPredefKey();

		boolean sameAs(BrowserType type);
	}

	static int parseVersionNumber(String value) {
		return Version.parseVersionNumber(value);
	}

	private static IBrowserKey register(final String browser,
			final Version[] vers, final BrowserType type) {

		IBrowserKey registeredKey = new IBrowserKey() {

			public String getBrowser() {
				return browser;
			}

			public String[] getVersions() {

				String[] versions = new String[vers.length];
				for (int iter = 0; iter < vers.length; iter++) {
					versions[iter] = vers[iter].toString();
				}

				return versions;
			}

			public String isTargetedBrowserPredefKey() {
				return "is_targeted_browser_" + browser;
			}

			public String takeTargetedBrowserVerPredefKey() {
				return "take_targeted_browser_ver_" + browser;
			}

			public boolean sameAs(BrowserType typeToCompare) {
				return type.getName().equals(typeToCompare.getName());
			}

		};

		PREDEF_KEYS.add(registeredKey);

		return registeredKey;
	}

}
