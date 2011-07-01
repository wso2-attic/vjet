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
import org.ebayopensource.dsf.jsnative.anno.SupportedBy;
import org.ebayopensource.dsf.jst.BaseJstNode;
import org.ebayopensource.dsf.jst.IJstAnnotation;
import org.ebayopensource.dsf.jst.expr.AssignExpr;
import org.ebayopensource.dsf.jst.expr.JstArrayInitializer;
import org.ebayopensource.dsf.jst.token.IExpr;
import org.ebayopensource.vjet.eclipse.codeassist.compliance.PredefinedBrowsersPreferenceKeys.IBrowserKey;
import org.ebayopensource.vjet.eclipse.core.VjetPlugin;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.dltk.mod.core.DLTKCore;
import org.osgi.service.prefs.BackingStoreException;

/**
 * Used for filtering methods and properties during code completion.
 */
public class SupportedByAnnotationAcceptor {

	private static final String SUPPORTED_BY_ANNO = SupportedBy.class
			.getSimpleName();
	private static final String BROWSERS_ANNO_VALUE = "browsers";
	private static final String DOM_LEVEL_ANNO_VALUE = "domLevel";

	private static Map<IProject, SupportedByAnnotationAcceptor> acceptors = new HashMap<IProject, SupportedByAnnotationAcceptor>();

	private IEclipsePreferences pref;
	private ProjectScope prjScope;
	private InstanceScope instScope;

	private List<IBrowserKey> targetedBrowsers = new ArrayList<IBrowserKey>();

	/**
	 * Gets SupportedBy acceptor for specified project.
	 * 
	 * @param prj
	 *            project
	 * @return acceptor SupportedBy acceptor
	 */
	public synchronized static SupportedByAnnotationAcceptor getAcceptor(
			IProject prj) {

		SupportedByAnnotationAcceptor acceptor;
		if (acceptors.containsKey(prj)) {
			acceptor = acceptors.get(prj);
		} else {
			acceptor = new SupportedByAnnotationAcceptor(prj);
			acceptors.put(prj, acceptor);
		}

		acceptor.renew();

		return acceptor;
	}

	private SupportedByAnnotationAcceptor(IProject prj) {

		// project scope
		prjScope = new ProjectScope(prj);

		// instance scope
		instScope = new InstanceScope();
	}

	private void setPrefValues(IEclipsePreferences pref) {

		for (IBrowserKey browserKey : PredefinedBrowsersPreferenceKeys
				.getPredefinedKeys()) {

			if (pref.getBoolean(browserKey.isTargetedBrowserPredefKey(), false)) {
				targetedBrowsers.add(browserKey);
			}

		}

	}

	private void renew() {

		boolean prjSettingEnabled = false;

		targetedBrowsers.clear();

		// renew preferences
		pref = prjScope.getNode(VjetPlugin.PLUGIN_ID);

		try {

			prjSettingEnabled = pref.keys().length > 0;

		} catch (BackingStoreException bse) {

			// could not check project specified settings
			DLTKCore.error(bse.getMessage());
			prjSettingEnabled = false;

		}

		if (prjSettingEnabled) {

			setPrefValues(pref);

		} else {

			// specified project setting isn't enabled or couln't be checked
			pref = instScope.getNode(VjetPlugin.PLUGIN_ID);
			setPrefValues(pref);
		}

	}

	private IJstAnnotation find(String annoName,
			List<IJstAnnotation> annotations) {
		for (IJstAnnotation anno : annotations) {
			if (anno.getName().getName().equalsIgnoreCase(annoName)) {
				return anno;
			}
		}

		return null;
	}

	private boolean acceptBrowserVersion(BrowserType dsfBrowser,
			IBrowserKey predefKey) {

		String storedValue = pref.get(predefKey
				.takeTargetedBrowserVerPredefKey(), null);

		int predefVer = PredefinedBrowsersPreferenceKeys
				.parseVersionNumber(storedValue);

		int annoVer = dsfBrowser.isPlus() ? -dsfBrowser.getVersion()
				: dsfBrowser.getVersion();

		int sub = Math.abs(predefVer) - Math.abs(annoVer);

		boolean accepted = (sub == 0) || (annoVer < 0 && sub > 0);

		return accepted;

	}

	private BrowserType[] parseBrowserAnnotation(AssignExpr annoValue) {

		ArrayList<BrowserType> browsers = new ArrayList<BrowserType>();

		JstArrayInitializer arrIni = (JstArrayInitializer) annoValue.getExpr();

		for (BaseJstNode node : arrIni.getChildren()) {

			String browserName = node.toString();
			browsers.add(BrowserType.valueOf(browserName.substring(browserName
					.indexOf('.') + 1)));
		}

		return browsers.toArray(new BrowserType[] {});
	}

	private boolean acceptBrowserAnno(AssignExpr annoValue) {

		boolean accepted = true;

		// get all browsers supported by annotation
		BrowserType[] supportedAnnoBrowsers = parseBrowserAnnotation(annoValue);

		for (IBrowserKey targetedBrowser : targetedBrowsers) {

			BrowserType coincidentBrowser = null;
			for (BrowserType annoBrowser : supportedAnnoBrowsers) {

				if (targetedBrowser.sameAs(annoBrowser)) {
					coincidentBrowser = annoBrowser;
					break;
				}
			}

			accepted = coincidentBrowser != null
					&& acceptBrowserVersion(coincidentBrowser, targetedBrowser);

			if (!accepted) {
				break;
			}

		}

		return accepted;

	}

	private boolean acceptDomLevelAnno(AssignExpr assign) {
		// TODO Not for now
		return true;
	}

	/**
	 * Accepts SupportedBy annotation.
	 * 
	 * @param anno
	 *            jstMethod annotations
	 * @return true if SupportedBy annotation from the list is compliance with
	 *         corresponding preference settings otherwise false.
	 */
	public boolean accept(List<IJstAnnotation> anno) {

		IJstAnnotation supportedBy = find(SUPPORTED_BY_ANNO, anno);

		boolean accepted = false;

		if (supportedBy != null) {

			List<IExpr> annoValues = supportedBy.values();
			for (IExpr exp : annoValues) {

				if (exp instanceof AssignExpr) {

					AssignExpr assign = (AssignExpr) exp;

					String annoValueName = assign.getLHS().toLHSText();

					if (annoValueName.equals(BROWSERS_ANNO_VALUE)) {

						accepted = acceptBrowserAnno(assign);

					} else if (annoValueName.equals(DOM_LEVEL_ANNO_VALUE)) {

						accepted = acceptDomLevelAnno(assign);

					} else {
						// unidentified annotation detected
						accepted = true;
					}

					if (!accepted) {
						break;
					}

				}
			}

		} else {
			return !accepted;
		}

		return accepted;
	}
}
