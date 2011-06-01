/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.active.client;

import java.text.MessageFormat;

import org.ebayopensource.dsf.active.dom.html.AHtmlForm;
import org.ebayopensource.dsf.active.event.IBrowserBinding;
import org.ebayopensource.dsf.jsnative.External;
import org.ebayopensource.dsf.jsnative.HtmlForm;
import org.ebayopensource.dsf.jsnative.anno.BrowserType;

public class AExternal extends ActiveObject implements External {
	private static final long serialVersionUID = 6596437860066205967L;

	private final String AUTO_COMPLETE_SAVE_FORM_JS = "window.external.AutoCompleteSaveForm({0})";

	private IBrowserBinding m_browserBinding;

	private BrowserType m_browserType;

	public AExternal(BrowserType browserType, IBrowserBinding bowserBinding) {
		m_browserBinding = bowserBinding;
		m_browserType = browserType;
		populateScriptable(AExternal.class, browserType);
	}

	public void AutoCompleteSaveForm(HtmlForm elem) {
		if(m_browserBinding!=null){
			m_browserBinding.executeJs(MessageFormat.format(
				AUTO_COMPLETE_SAVE_FORM_JS, ((AHtmlForm)elem).getReferenceAsJs()));
		}
	}

	public Object valueOf(String type) {
		if (type.equals("boolean")) {
			return Boolean.TRUE;
		} else if (type.equals("string")) {
			return getClass().getName();
		}
		return null;
	}

	@Override
	public boolean getFrozen() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getMenuArguments() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object getOnvisibilitychange() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnvisibilitychange(Object p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean getScrollbar() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setScrollbar(boolean p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean getSelectableContent() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setSelectableContent(boolean p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean getVersion() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getVisibility() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void AddChannel(String sURLToCDF) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void AddDesktopComponent(String sURL, String sType) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void AddDesktopComponent(String sURL, String sType, int iLeft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void AddDesktopComponent(String sURL, String sType, int iLeft,
			int iWidth) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void AddDesktopComponent(String sURL, String sType, int iLeft,
			int iWidth, int iHeight) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void AddFavorite(String sURL) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void AddFavorite(String sURL, String sTitle) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void AddToFavoritesBar(String URL, String Title) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void AddToFavoritesBar(String URL, String Title, String Type) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void AddSearchProvider(String sURL) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void AddService(String sURL) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void AutoScan(String sUserQuery, String sURL) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void AutoScan(String sUserQuery, String sURL, String sTarget) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String BrandImageUri() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void bubbleEvent() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void ContentDiscoveryReset() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void CustomizeClearType(boolean fSet) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void CustomizeSettings(boolean fSQM, boolean fPhishing,
			String sLocale) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String DefaultSearchProvider() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void DiagnoseConnection() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void ImportExportFavorites(boolean bImportExport,
			String sImportExportPath) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean InPrivateFilteringEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean IsSearchMigrated() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public long IsSearchProviderInstalled(String url) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long IsServiceInstalled(String URL, String Verb) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean IsSubscribed(String sURLToCDF) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean msActiveXFilteringEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean msAddSiteMode() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void msAddTrackingProtectionList(String URL, String bstrFilterName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean msIsSiteMode() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean msIsSiteModeFirstRun(boolean fPreserveState) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void msSiteModeActivate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msSiteModeAddButtonStyle(String uiButtonID, String bstrIconUrl,
			String pvarTooltip) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msSiteModeAddButtonStyle(String uiButtonID, String bstrIconUrl) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msSiteModeAddJumpListItem(String bstrName,
			String bstrActionUri, String bstrIconUri, String bstrWindowType) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msSiteModeAddJumpListItem(String bstrName,
			String bstrActionUri, String bstrIconUri) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int msSiteModeAddThumbBarButton(String bstrIconURL,
			String bstrTooltip) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void msSiteModeClearIconOverlay() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msSiteModeClearJumpList() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msSiteModeCreateJumpList(String bstrHeader) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msSiteModeSetIconOverlay(String bstrIconUrl,
			String bstrDescription) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msSiteModeShowButtonStyle(String uiButtonID, String uiStyleID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msSiteModeShowJumpList() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msSiteModeShowThumbBar() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msSiteModeUpdateThumbBarButton(int uiButtonID,
			boolean fEnabled, boolean fVisible) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msSiteModeUpdateThumbBarButton(int uiButtonID, boolean fEnabled) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean msTrackingProtectionEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void NavigateAndFind(String sLocation, String sQuery,
			String sTargetFrame) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean PhishingEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void raiseEvent(String name, Object eventData) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean RunOnceHasShown() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void RunOnceRequiredSettingsComplete(boolean fComplete) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void RunOnceShown() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String SearchGuideUrl() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setContextMenu(Object menuItemPairs) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object ShowBrowserUI(String sUI, Object nullValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void SkipRunOnce() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void SkipTabsWelcome() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean SqmEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void AddDesktopComponent(String sURL, String sType, int iLeft,
			int iTop, int iWidth, int iHeight) {
		// TODO Auto-generated method stub
		
	}
}
