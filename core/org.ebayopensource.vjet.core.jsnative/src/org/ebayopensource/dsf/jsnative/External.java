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
import org.ebayopensource.dsf.jsnative.anno.Dynamic;
import org.ebayopensource.dsf.jsnative.anno.Function;
import org.ebayopensource.dsf.jsnative.anno.JsMetatype;
import org.ebayopensource.dsf.jsnative.anno.OverLoadFunc;
import org.ebayopensource.dsf.jsnative.anno.Property;
import org.mozilla.mod.javascript.IWillBeScriptable;

/**
 * JavaScript object representing window.external object. (IE specific)
 */
@JsMetatype
@Dynamic
public interface External extends IWillBeScriptable {

	// Properties

	/**
	 * Retrieves whether content is able to handle events.
	 */
	@BrowserSupport({ BrowserType.IE_6P })
	@Property
	boolean getFrozen();

	/**
	 * Allows access to an additional object model provided by host applications
	 * of the Windows Internet Explorer browser components.
	 * 
	 * @return
	 */
	@BrowserSupport({ BrowserType.IE_6P })
	@Property
	boolean getMenuArguments();

	/**
	 * Sets or retrieves a value on visibility change.
	 */
	@BrowserSupport({ BrowserType.IE_6P })
	@Property
	Object getOnvisibilitychange();

	@BrowserSupport({ BrowserType.IE_6P })
	@Property
	void setOnvisibilitychange(Object p);

	/**
	 * Sets or retrieves whether to show the scrollbar.
	 * 
	 * @param Boolean
	 *            that specifies or receives whether to show the scrollbar.
	 */
	@BrowserSupport({ BrowserType.IE_6P })
	@Property
	boolean getScrollbar();

	@BrowserSupport({ BrowserType.IE_6P })
	@Property
	void setScrollbar(boolean p);

	/**
	 * Sets or retrieves whether selectable content is valid.
	 * 
	 * @param selectableContent
	 *            Boolean that specifies or receives whether selectable content
	 *            is valid.
	 */
	@BrowserSupport({ BrowserType.IE_6P })
	@Property
	boolean getSelectableContent();

	@BrowserSupport({ BrowserType.IE_6P })
	@Property
	void setSelectableContent(boolean p);

	/**
	 * Retrieves a string representing the version in the format
	 * "N.nnnn platform", where N is an integer representing the major version
	 * number, nnnn is a number of characters representing the minor version
	 * number, and platform is the platform (win32, mac, alpha, and so on).
	 * 
	 * @param sVer
	 *            String that receives version.
	 * 
	 */

	@BrowserSupport({ BrowserType.IE_6P })
	@Property
	boolean getVersion();

	/**
	 * Retrieves whether content is visible.
	 */
	@BrowserSupport({ BrowserType.IE_6P })
	@Property
	boolean getVisibility();

	// Methods

	/**
	 * Obsolete. Presents a dialog box that enables the user to add the
	 * specified channel, or to change the channel URL, if it is already
	 * installed.
	 * 
	 * @param elem
	 */
	@BrowserSupport({ BrowserType.IE_6P })
	@Function
	void AddChannel(String sURLToCDF);

	/**
	 * Adds a Web site or image to the Microsoft Active Desktop.
	 * 
	 * @param sURL
	 *            Required. A String that specifies the location of the Web site
	 *            or image to be added to the Active Desktop.
	 * @param sType
	 *            Required. A String containing one of the following values that
	 *            specify the type of item to add. image Specifies the component
	 *            is an image. website Specifies the component is a Web site.
	 * 
	 * @param iLeft
	 *            Optional. Integer that specifies the position of the left
	 *            edge, in screen coordinates.
	 * @param iTop
	 *            Optional. Integer that specifies the position of the top edge,
	 *            in screen coordinates.
	 * @param iWidth
	 *            Optional. Integer that specifies the width, in screen units.
	 * @param iHeight
	 *            Optional. Integer that specifies the height, in screen units.
	 * 
	 */
	// AddDesktopComponent(sURL, sType [, iLeft] [, iTop] [, iWidth] [,
	// iHeight])
	@BrowserSupport({ BrowserType.IE_6P })
	@OverLoadFunc
	void AddDesktopComponent(String sURL, String sType);

	@BrowserSupport({ BrowserType.IE_6P })
	@OverLoadFunc
	void AddDesktopComponent(String sURL, String sType, int iLeft);
	
	@BrowserSupport({ BrowserType.IE_6P })
	@OverLoadFunc
	void AddDesktopComponent(String sURL, String sType, int iLeft, int iTop);

	@BrowserSupport({ BrowserType.IE_6P })
	@OverLoadFunc
	void AddDesktopComponent(String sURL, String sType, int iLeft, int iTop, int iWidth);

	@BrowserSupport({ BrowserType.IE_6P })
	@OverLoadFunc
	void AddDesktopComponent(String sURL, String sType, int iLeft, int iTop, int iWidth,
			int iHeight);

	/**
	 * Prompts the user with a dialog box to add the specified URL to the
	 * Favorites list.
	 * 
	 * @param sURL
	 *            Required. A String that specifies the URL of the favorite to
	 *            add to the Favorites list.
	 * @param sTitle
	 *            Optional. String that specifies the suggested title to use in
	 *            the Favorites list. The user can change the title in the Add
	 *            Favorite dialog box. If sTitle is not specified, sURL is used
	 *            as the title of the favorite.
	 */

	@BrowserSupport({ BrowserType.IE_6P })
	@OverLoadFunc
	void AddFavorite(String sURL);

	@BrowserSupport({ BrowserType.IE_6P })
	@OverLoadFunc
	void AddFavorite(String sURL, String sTitle);

	/**
	 * Adds a URL to the Favorites Bar.
	 * 
	 * @param URL
	 *            Required. The URL of the page to add.
	 * @param Title
	 *            Required. The Title for this page.
	 * @param Type
	 *            Optional. String that specifies one of the following, or null.
	 *            feed The content is a Really Simple Syndication (RSS) feed.
	 *            slice The content is a Web Slice.
	 */

	@BrowserSupport({ BrowserType.IE_6P })
	@OverLoadFunc
	void AddToFavoritesBar(String URL, String Title);

	@BrowserSupport({ BrowserType.IE_6P })
	@OverLoadFunc
	void AddToFavoritesBar(String URL, String Title, String Type);

	/**
	 * Adds a search provider to the registry.
	 * 
	 * @param sURL
	 *            Required. String that specifies an absolute or relative URL to
	 *            the OpenSearch Description file for the search provider.
	 */
	@BrowserSupport({ BrowserType.IE_6P })
	@OverLoadFunc
	void AddSearchProvider(String sURL);

	/**
	 * User initiated action to add a service.
	 * 
	 * @param sURL
	 *            Required. Specifies the URL for an XML Activity. This URL has
	 *            to be a navigable HTTP or HTTPS URL.
	 */
	@BrowserSupport({ BrowserType.IE_6P })
	@OverLoadFunc
	void AddService(String sURL);

	/**
	 * Saves the specified form in the AutoComplete data store.
	 * 
	 * @param oForm
	 *            Required. Object that specifies a reference to a form element.
	 */
	@BrowserSupport({ BrowserType.IE_6P })
	@Function
	void AutoCompleteSaveForm(HtmlForm oForm);

	/**
	 * Saves the specified form in the AutoComplete data store.
	 * 
	 * @param sUserQuery
	 *            Required. A String that specifies a domain address that begins
	 *            with www., and ends with .com, .org, .net, or .edu.
	 * @param sURL
	 *            Required. A String that specifies the Web page to display if
	 *            the domain address created from sUserQuery is invalid. The
	 *            default Internet Explorer error page is displayed if a value
	 *            is not provided.
	 * @param sTarget
	 *            Optional. A String that specifies the target window or frame
	 *            where the results are displayed. The default value is the
	 *            current window.
	 */
	@BrowserSupport({ BrowserType.IE_6P })
	@OverLoadFunc
	void AutoScan(String sUserQuery, String sURL);

	@BrowserSupport({ BrowserType.IE_6P })
	@OverLoadFunc
	void AutoScan(String sUserQuery, String sURL, String sTarget);

	/**
	 * 
	 * Not supported. Retrieves the Uniform Resource Identifier (URI) of an
	 * alternate product image.
	 */
	@BrowserSupport({ BrowserType.IE_6P })
	@Function
	String BrandImageUri();

	/**
	 * Propagates an event up its containment hierarchy.
	 */
	@BrowserSupport({ BrowserType.IE_6P })
	@Function
	void bubbleEvent();

	/**
	 * Resets the list of feeds, search providers, and Web Slices associated
	 * with the page.
	 */
	@BrowserSupport({ BrowserType.IE_6P })
	@Function
	void ContentDiscoveryReset();

	/**
	 * Not supported. Sets a registry value to turn ClearType on or off.
	 * 
	 * @param fSet
	 *            One of the following required values: true Always use
	 *            ClearType for HTML. false Do not use ClearType for HTML.
	 * 
	 */
	@BrowserSupport({ BrowserType.IE_7 })
	@Function
	void CustomizeClearType(boolean fSet);

	/**
	 * Not supported. Saves the user settings from a "first run" page.
	 * 
	 * @param fSQM
	 *            Required. Boolean that specifies true to enable Software
	 *            Quality Monitoring (SQM), or false to opt out.
	 * @param fPhishing
	 *            Required. Boolean that specifies true to turn on Microsoft
	 *            Phishing Filter, or false to turn it off.
	 * @param sLocale
	 *            Required. String that specifies the default region and
	 *            language setting for the browser.
	 */
	@BrowserSupport({ BrowserType.IE_7 })
	@Function
	void CustomizeSettings(boolean fSQM, boolean fPhishing, String sLocale);

	/**
	 * Not supported. Retrieves the name of the user's default search provider.
	 * 
	 * @return String that contains the default search provider name.
	 * 
	 */
	@BrowserSupport({ BrowserType.IE_7 })
	@Function
	String DefaultSearchProvider();

	/**
	 * Not supported. Attempts to diagnose problems with the network connection.
	 * 
	 * @return String that contains the default search provider name.
	 * 
	 */
	@BrowserSupport({ BrowserType.IE_7 })
	@Function
	void DiagnoseConnection();

	/**
	 * Deprecated. Handles the import and export of Windows Internet Explorer
	 * favorites.
	 * 
	 * Microsoft Internet Explorer 6 for Windows XP Service Pack 2 (SP2) and
	 * later. This method is no longer available.
	 * 
	 * @param bImportExport
	 *            Required. A Boolean that specifies one of the following
	 *            possible values.
	 * 
	 *            true Import is requested. false Export is requested.
	 * 
	 * @param sImportExportPath
	 *            Required. A String that specifies the location (URL) to import
	 *            or export, depending on bImportExport. If a value is an empty
	 *            string, a file dialog box is displayed.
	 * 
	 */
	@BrowserSupport({ BrowserType.IE_6P })
	@Function
	void ImportExportFavorites(boolean bImportExport, String sImportExportPath);

	/**
	 * Detects whether the user has enabled InPrivate Filtering.
	 * 
	 * @return Returns true if the user has enabled InPrivate Filtering; false
	 *         otherwise.
	 * 
	 */
	@BrowserSupport({ BrowserType.IE_6P })
	@Function
	boolean InPrivateFilteringEnabled();

	/**
	 * Not supported. Determines whether autosearch settings were migrated from
	 * a previous version of Microsoft Internet Explorer.
	 * 
	 * @return Returns one of the following values: true Search provider
	 *         settings were migrated. false Search provider settings were not
	 *         migrated.
	 * 
	 */
	@BrowserSupport({ BrowserType.IE_7 })
	@Function
	boolean IsSearchMigrated();

	/**
	 * Determines if a search provider has been installed for the current user
	 * and whether it is set as default.
	 * 
	 * @param sUrl
	 *            Required. String that specifies a prefix to the URL for the
	 *            search provider.
	 * @return Pointer to an unsigned long integer value that contains return
	 *         value. 0 The specified search provider is not installed for the
	 *         current user. 1 The specified search provider is installed for
	 *         the current user. 2 The specified search provider is installed
	 *         and is set as the default search provider.
	 */

	@BrowserSupport({ BrowserType.IE_7 })
	@Function
	long IsSearchProviderInstalled(String url);

	/**
	 * Determines if a search provider has been installed for the current user
	 * and whether it is set as default.
	 * 
	 * @param URL
	 *            Required. The main document of the activity that you want to
	 *            query for.
	 * @param Verb
	 *            Required. The type of action that this activity performs.
	 *            Case-insensitive.
	 * @return One of the following values: 0 The service is not yet installed.
	 *         1 The service is installed. 2 The service is installed and it is
	 *         the default for its verb type.
	 * 
	 */

	@BrowserSupport({ BrowserType.IE_7 })
	@Function
	long IsServiceInstalled(String URL, String Verb);

	/**
	 * Obsolete. Retrieves a value indicating whether the client subscribes to
	 * the given channel.
	 * 
	 * @param sURLToCDF
	 *            Required. A String that specifies the URL of a Channel
	 *            Definition Format (CDF) file to check for a subscription.
	 * @return Boolean that receives one of the following possible values. true
	 *         The client subscribes to the channel. false No subscription
	 *         exists for the CDF file.
	 */

	@BrowserSupport({ BrowserType.IE_6 })
	@Function
	boolean IsSubscribed(String sURLToCDF);

	/**
	 * Determines whether Microsoft ActiveX Filtering is enabled by the user.
	 * 
	 * @return Returns true if ActiveX controls are disallowed, false otherwise.
	 */

	@BrowserSupport({ BrowserType.IE_9 })
	@Function
	boolean msActiveXFilteringEnabled();

	/**
	 * Creates a pinned site shortcut to the current webpage on the Windows
	 * Start menu.
	 * 
	 * @return Boolean that receives one of the following possible values. true
	 *         The client subscribes to the channel. false No subscription
	 *         exists for the CDF file.
	 */

	@BrowserSupport({ BrowserType.IE_9 })
	@Function
	boolean msAddSiteMode();

	/**
	 * @param URL
	 *            Required. The address of the Tracking Protection list.
	 * @param bstrFilterName
	 *            Required. The display name.
	 * @return void
	 */

	@BrowserSupport({ BrowserType.IE_9 })
	@Function
	void msAddTrackingProtectionList(String URL, String bstrFilterName);

	/**
	 * Determines whether the current page was launched as a pinned site.
	 * 
	 * @param Returns
	 *            true if the current page was launched as a pinned site; false
	 *            otherwise.
	 */

	@BrowserSupport({ BrowserType.IE_9 })
	@Function
	boolean msIsSiteMode();

	/**
	 * Determines whether a pinned site was launched for the first time.
	 * 
	 * @param fPreserveState
	 *            One of the following required values:false Read and clear the
	 *            first-run state. true Only read the state, do not clear it.
	 */
	@BrowserSupport({ BrowserType.IE_9 })
	@Function
	boolean msIsSiteModeFirstRun(boolean fPreserveState);

	/**
	 * Flashes the pinned site taskbar button.
	 */
	@BrowserSupport({ BrowserType.IE_9 })
	@Function
	void msSiteModeActivate();

	/**
	 * 
	 * Defines an alternate icon image and tooltip for the specified button.
	 * 
	 * @param uiButtonID
	 *            Required. The ID of a button that is previously installed with
	 *            msSiteModeAddThumbBarButton.
	 * @param bstrIconUrl
	 *            Required. An absolute or relative URI of an icon resource.
	 * @param pvarTooltip
	 *            Optional. A description of the button.
	 * 
	 *            msSiteModeAddButtonStyle(uiButtonID, bstrIconUrl [,
	 *            pvarTooltip])
	 */
	@BrowserSupport({ BrowserType.IE_9 })
	@OverLoadFunc
	void msSiteModeAddButtonStyle(String uiButtonID, String bstrIconUrl,
			String pvarTooltip);

	@BrowserSupport({ BrowserType.IE_9 })
	@OverLoadFunc
	void msSiteModeAddButtonStyle(String uiButtonID, String bstrIconUrl);

	/**
	 * 
	 * Adds a new entry to the Jump List of a taskbar button.
	 * 
	 * @param bstrName
	 *            Required. An entry title that is displayed in the Jump List.
	 * @param bstrActionUri
	 *            Required. An absolute or relative URL that opens when the item
	 *            is clicked.
	 * @param bstrIconUri
	 *            Required. An absolute or relative URL of an icon file that is
	 *            displayed next to the title in the Jump List.
	 * @param bstrWindowType
	 *            Optional. A String that specifies one of the following values.
	 *            self The link opens in the current tab in the current pinned
	 *            site window. tab Default. The link opens in a new tab in the
	 *            current pinned site window. window The link opens a new pinned
	 *            site window.
	 * 
	 */
	@BrowserSupport({ BrowserType.IE_9 })
	@OverLoadFunc
	void msSiteModeAddJumpListItem(String bstrName, String bstrActionUri,
			String bstrIconUri, String bstrWindowType);

	@BrowserSupport({ BrowserType.IE_9 })
	@OverLoadFunc
	void msSiteModeAddJumpListItem(String bstrName, String bstrActionUri,
			String bstrIconUri);

	/**
	 * Adds a button to the Thumbnail Toolbar.
	 * 
	 * @param bstrIconURL
	 *            Required. Absolute or relative URL of an icon resource file.
	 * @param bstrTooltip
	 *            Required. The button name, which is displayed as a tooltip on
	 *            hover.
	 * 
	 */
	@BrowserSupport({ BrowserType.IE_9 })
	@Function
	int msSiteModeAddThumbBarButton(String bstrIconURL, String bstrTooltip);

	/**
	 * Removes the icon overlay from the taskbar button.
	 */
	@BrowserSupport({ BrowserType.IE_9 })
	@Function
	void msSiteModeClearIconOverlay();

	/**
	 * Deletes the Jump List.
	 */
	@BrowserSupport({ BrowserType.IE_9 })
	@Function
	void msSiteModeClearJumpList();

	/**
	 * Creates a new group of items on the Jump List.
	 */
	@BrowserSupport({ BrowserType.IE_9 })
	@Function
	void msSiteModeCreateJumpList(String bstrHeader);

	/**
	 * Adds an icon overlay to the pinned site taskbar button.
	 * 
	 * @param bstrIconUrl
	 *            Required. Absolute URL of an icon resource file.
	 * @param bstrDescription
	 *            Optional. A String that provides an accessible description of
	 *            the icon overlay.
	 */
	@BrowserSupport({ BrowserType.IE_9 })
	@Function
	void msSiteModeSetIconOverlay(String bstrIconUrl, String bstrDescription);

	/**
	 * @param uiButtonID
	 *            Required. The ID of a button that is previously installed with
	 *            msSiteModeAddThumbBarButton.
	 * @param uiStyleID
	 *            Required. The style ID that msSiteModeAddButtonStyle returns.
	 */

	@BrowserSupport({ BrowserType.IE_9 })
	@Function
	void msSiteModeShowButtonStyle(String uiButtonID, String uiStyleID);

	/**
	 * Shows updates to the list of items in a Jump List.
	 */
	@BrowserSupport({ BrowserType.IE_9 })
	@Function
	void msSiteModeShowJumpList();

	/**
	 * Enables the Thumbnail Toolbar in the thumbnail preview of a pinned site.
	 */
	@BrowserSupport({ BrowserType.IE_9 })
	@Function
	void msSiteModeShowThumbBar();

	/**
	 * Changes the state of a Thumbnail Toolbar button.
	 * 
	 * @param uiButtonID
	 *            Required. Integer that specifies the button ID that is
	 *            returned from msSiteModeAddThumbBarButton.
	 * @param fEnabled
	 *            Required. A Boolean value that specifies true to enable the
	 *            button; or false to disable it.
	 * @param fVisible
	 *            Required. A Boolean value that specifies true to make the
	 *            button visible; or false to hide it.
	 */
	@BrowserSupport({ BrowserType.IE_9 })
	@OverLoadFunc
	void msSiteModeUpdateThumbBarButton(int uiButtonID, boolean fEnabled,
			boolean fVisible);

	@BrowserSupport({ BrowserType.IE_9 })
	@OverLoadFunc
	void msSiteModeUpdateThumbBarButton(int uiButtonID, boolean fEnabled);

	/**
	 * Determines whether any Tracking Protection lists are enabled by the user.
	 */
	@BrowserSupport({ BrowserType.IE_9 })
	@Function
	boolean msTrackingProtectionEnabled();

	/**
	 * @param sLocation
	 *            Required. A String that specifies the URL of a Web page.
	 * @param sQuery
	 *            Required. A String that specifies the text to highlight on the
	 *            Web page specified by sLocation.
	 * @param sTargetFrame
	 *            Required. String that specifies the name of the target frame
	 *            to query.
	 */
	@BrowserSupport({ BrowserType.IE_6P })
	@Function
	void NavigateAndFind(String sLocation, String sQuery, String sTargetFrame);

	/**
	 * Not supported. Determines whether Microsoft Phishing Filter is enabled.
	 * 
	 * @return Returns one of the following possible values: true Phishing
	 *         Filter is enabled. false Phishing Filter is disabled.
	 */
	@BrowserSupport({ BrowserType.IE_7 })
	@Function
	boolean PhishingEnabled();

	/**
	 * Triggers an event, as specified.
	 * 
	 * @param name
	 *            Required. Specifies a valid event name as a string.
	 * @param eventData
	 *            Required. Specifies event data.
	 * 
	 * @see fireEvent use this instead
	 */

	@BrowserSupport({ BrowserType.IE_6P })
	@Function
	void raiseEvent(String name, Object eventData);

	/**
	 * Not supported. Determines whether the "first run" page has been shown.
	 * 
	 * @return Returns one of the following values:true The "first run" page has
	 *         been shown at least once. false The "first run" page has not been
	 *         shown.
	 */
	@BrowserSupport({ BrowserType.IE_7 })
	@Function
	boolean RunOnceHasShown();

	/**
	 * Not supported. Sets a registry value to indicate whether the "first run"
	 * page completed successfully.
	 * 
	 * @param fComplete
	 *            One of the following required values:true The settings are
	 *            complete., false The settings are not complete.
	 */

	@BrowserSupport({ BrowserType.IE_7 })
	@Function
	void RunOnceRequiredSettingsComplete(boolean fComplete);

	/**
	 * Not supported. Sets a registry value to indicate that the "first run"
	 * page has been shown.
	 */
	@BrowserSupport({ BrowserType.IE_7 })
	@Function
	void RunOnceShown();

	/**
	 * Not supported. Retrieves the URL of a page that can be used to install
	 * additional search providers.
	 * 
	 * @return String that contains the alternate URL from the registry, or the
	 *         default URL if an alternate has not been set.
	 */
	@BrowserSupport({ BrowserType.IE_7 })
	@Function
	String SearchGuideUrl();

	/**
	 * Constructs a context menu, as specified.
	 * 
	 * @param menuItemPairs
	 *            Required. Specifies menu item pairs, which are command text
	 *            and commands contained within the context menu.
	 */
	@BrowserSupport({ BrowserType.IE_6 })
	@Function
	void setContextMenu(Object menuItemPairs);

	/**
	 * @param sUI
	 *            Required. A String that specifies a browser dialog box, using
	 *            one of the following values. LanguageDialog Opens the Language
	 *            Preference dialog box. OrganizeFavorites Opens the Organize
	 *            Favorites dialog box. PrivacySettings Microsoft Internet
	 *            Explorer 6 and later. Opens the Privacy Preferences dialog
	 *            box. ProgramAccessAndDefaults Windows XP Service Pack 1 (SP1)
	 *            and later. Opens the Set Program Access and Defaults dialog
	 *            box.
	 * @param null Required. Null value.
	 * @return Variant Return value is determined by the dialog box. 
	 */

	@BrowserSupport({ BrowserType.IE_6 })
	@Function
	Object ShowBrowserUI(String sUI, Object nullValue);
	
	
	/**
	 * Not supported. Enables the user to select "first run" settings at a later time.
	 */
	@BrowserSupport({ BrowserType.IE_7 })
	@Function void SkipRunOnce();
	
	/**
	 * Not supported. Disables the welcome screen that appears when opening a new tab in Internet Explorer 7.
	 */
	@BrowserSupport({ BrowserType.IE_7 })
	@Function void SkipTabsWelcome();
	
	/**
	 * Not supported. Determines whether Software Quality Monitoring (SQM) is enabled.
	 */
	@BrowserSupport({ BrowserType.IE_7 })
	@Function boolean SqmEnabled();

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
