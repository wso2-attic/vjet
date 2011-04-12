/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.spyglass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.ebayopensource.dsf.common.context.BaseSubCtx;
import org.ebayopensource.dsf.common.context.ContextHelper;
import org.ebayopensource.dsf.common.context.DsfCtx;
import org.ebayopensource.dsf.dom.DNode;

/**
 * A thread local context (associated with DsfCtx) to manage SpyGlass related
 * info
 * 
 * 
 * @since e605
 */
public class SpyGlassCtx extends BaseSubCtx {

	public static SpyGlassCtx ctx() {
		SpyGlassCtx context = CtxAssociator.getCtx();
		if (context == null) {
			context = new SpyGlassCtx();
			setCtx(context);
		}
		return context;
	}

	public static void setCtx(final SpyGlassCtx context) {
		CtxAssociator.setCtx(context);
	}

	private SpyGlassCtx() {
		// empty on purpose
	}

	private static class CtxAssociator extends ContextHelper {
		private static final String CTX_NAME = SpyGlassCtx.class
				.getSimpleName();

		protected static SpyGlassCtx getCtx() {
			return (SpyGlassCtx) getSubCtx(DsfCtx.ctx(), CTX_NAME);
		}

		protected static void setCtx(final SpyGlassCtx ctx) {
			setSubCtx(DsfCtx.ctx(), CTX_NAME, ctx);
		}
	}

	// ============
	// Content
	// ============

	private boolean m_isRunOnServer = true;

	private boolean m_needComponentViewerCSS;

	private boolean m_needHtmlViewerCSS;

	private boolean m_replaceOutput;

	private String m_content4Output;

	//showdiag CAL log
	private String m_calLog = null;

	//showdiag customer diags content
	private final Map<String, ArrayList<DNode>> m_customerTitledDiags = new HashMap<String, ArrayList<DNode>>();

	//showdiag standard diags content
	private final Map<ShowDiagType, ArrayList<DNode>> m_diagnostics = new HashMap<ShowDiagType, ArrayList<DNode>>();

	//has the diags content
	private boolean m_hasDiagnostics = false;

	//the showdiag request flag
	private boolean m_isShowDiag = false;

	public boolean isNeedHtmlViewerCSS() {
		return m_needHtmlViewerCSS;
	}

	public void setNeedHtmlViewerCSS(boolean needHtmlViewerCSS) {
		this.m_needHtmlViewerCSS = needHtmlViewerCSS;
	}

	public boolean isNeedComponentViewerCSS() {
		return m_needComponentViewerCSS;
	}

	public void setNeedComponentViewerCSS(boolean needComponentViewerCSS) {
		this.m_needComponentViewerCSS = needComponentViewerCSS;
	}

	public String getContent4Output() {
		return m_content4Output;
	}

	public void setContent4Output(String output) {
		m_content4Output = output;
	}

	public boolean isReplaceOutput() {
		return m_replaceOutput;
	}

	public void setReplaceOutput(boolean output) {
		m_replaceOutput = output;
	}

	public boolean isRunOnServer() {
		return m_isRunOnServer;
	}

	public void setRunOnServer(boolean runOnServer) {
		m_isRunOnServer = runOnServer;
	}

	public void reset() {

		m_isShowDiag = false;
		clearAllDiagnostic();
	}

	public void addCustomerTitledDiagnostic(String title, DNode diag) {
		if (!m_isShowDiag || null == title || null == diag) {
			return;
		}

		// addDiagnostic(ShowDiagType.CUSTOMER, diag);

		ArrayList<DNode> nodes = m_customerTitledDiags.get(title);

		if (null == nodes) {
			nodes = new ArrayList<DNode>(1);
		}

		nodes.add(diag);
		m_customerTitledDiags.put(title, nodes);
		m_hasDiagnostics = true;

	}

	public void addDiagnostic(ShowDiagType type, DNode diag) {
		if (!m_isShowDiag || null == type || null == diag) {
			return;
		}

		ArrayList<DNode> nodes = m_diagnostics.get(type);
		if (null == nodes) {
			nodes = new ArrayList<DNode>(2);
		}

		nodes.add(diag);
		m_diagnostics.put(type, nodes);
		m_hasDiagnostics = true;
	}

	public void clearAllDiagnostic() {
		m_hasDiagnostics = false;
		m_diagnostics.clear();
		m_customerTitledDiags.clear();
	}

	public void clearCustomerTitledDiagnostic(String Title) {
		if (null == m_customerTitledDiags || m_customerTitledDiags.isEmpty()) {
			m_hasDiagnostics = false;
			return;
		}

		if (null == m_customerTitledDiags.get(Title)) {
			return;
		}

		m_customerTitledDiags.remove(Title);

		confirmHasDiagnostics();

	}

	public void clearDiagnostic(ShowDiagType type) {
		if (null == m_diagnostics || m_diagnostics.isEmpty()) {
			m_hasDiagnostics = false;
			return;
		}

		if (null == m_diagnostics.get(type)) {
			return;
		}

		m_diagnostics.remove(type);

		confirmHasDiagnostics();

	}

	private void confirmHasDiagnostics() {
		if (m_diagnostics.isEmpty() && m_customerTitledDiags.isEmpty()) {
			m_hasDiagnostics = false;
		} else {
			m_hasDiagnostics = true;
		}
	}

	public String getCalLogUrl() {
		return m_calLog;
	}

	/**
	 * @return the m_customerNode
	 */
	public Map<String, ArrayList<DNode>> getCustomerTitledDiagnostics() {
		return m_customerTitledDiags;
	}

	public Map<ShowDiagType, ArrayList<DNode>> getDiagnostics() {
		return m_diagnostics;
	}

	public ArrayList<DNode> getDiagnostics(ShowDiagType type) {
		if (null == m_diagnostics || null == type) {
			return null;
		}
		return m_diagnostics.get(type);
	}

	public boolean hasDiagnostics() {
		return m_hasDiagnostics;
	}

	public boolean isShowDiag() {
		return m_isShowDiag;
	}

	public void setCalLogUrl(String calLogUrl) {
		m_calLog = calLogUrl;
	}

	public void setShowDiag(boolean isShowDiag) {
		m_isShowDiag = isShowDiag;
	}

}
