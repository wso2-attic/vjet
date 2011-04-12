/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.dom;

/**
 * Represents all the of the HTML attributes.  We are going for a superset of
 * all versions of HTML so this list "is" meant to be exhaustive.
 * 
 * Generally we will try to make all the entries in alphabetical order.
 * 
 * The IE Specific attributes list follows the HTML specification list. 
 * 
 * The HTML 5.0 specific attributes are at the end
 */
public enum EHtmlAttr {
	FIRST("__first__", "setDummy", "getDummy"),	// MUST ALWAYS BE THE FIRST ENUM
	
	abbr("abbr", "setHtmlAbbr", "getHtmlAbbr"),
	accesskey("accesskey", "setHtmlAccessKey", "getHtmlAccessKey"),
	accept("accept", "setHtmlAccept", "getHtmlAccept"),
	accept_charset("accept-charset", "setHtmlAcceptCharset", "getHtmlAcceptCharset"),  // actually accept-charset
	action("action", "setHtmlAction", "getHtmlAction"),
	align("align", "setHtmlAlign", "getHtmlAlign"),
	alink("alink", "setHtmlAlink", "getHtmlAlink"),
	alt("alt", "setHtmlAlt", "getHtmlAlt"),
	archive("archive", "setHtmlArchive", "getHtmlArchive"),
	axis("axis", "setHtmlAccess", "getHtmlAccess"),

	background("background", "setHtmlBackground", "getHtmlBackground"),
	basefont("basefont", "setHtmlBaseFont", "getHtmlBaseFont"),
	bgcolor("bgcolor", "setHtmlBgColor", "getBgColor"),
	border("border", "setHtmlBorder", "getHtmlBorder"),

	cellpadding("cellpadding", "setHtmlCellPadding", "getHtmlCellPadding"),
	cellspacing("cellspacing", "setHtmlCellSpacing", "getHtmlCellSpacing"),
	_class("class", "setHtmlClassName", "getHtmlClassName"),	// actually class
	className("className", "setHtmlClassName", "getHtmlClassName"),
	_char("char", "setHtmlCh", "getHtmlCh"),	// actually char
	charoff("charoff", "setHtmlChOff", "getHtmlChOff"),
	charset("charset", "setHtmlCharset", "getHtmlCharset"),
	checked("checked", "setHtmlChecked", "getHtmlChecked"),
	cite("cite", "setHtmlCite", "getHtmlCite"),
	classid("classid", "setHtmlClassId", "getHtmlClassId"),  // DObject
	clear("clear", "setHtmlClear", "getHtmlClear"),
	clientHeight("clientHeight", "setClientHeight", "getClientHeight"),
	clientLeft("clientLeft", "setClientLeft", "getClientLeft"),
	clientTop("clientTop", "setClientTop", "getClientTop"),
	clientWidth("clientWidth", "setClientWidth", "getClientWidth"),
	code("code", "setHtmlCode", "getHtmlCode"),
	codebase("codebase", "setHtmlCodeBase", "getHtmlCodeBase"),
	codetype("codetype", "setHtmlCodeType", "getHtmlCodeType"),  // DObject
	color("color", "setHtmlColor", "getHtmlColor"),
	cols("cols", "setHtmlCols", "getHtmlCols"),
	colspan("colspan", "setHtmlColSpan", "getHtmlColSpan"),
	compact("compact", "setHtmlCompact", "getHtmlCompact"),
	content("content", "setHtmlContent", "getHtmlContent"),
	coords("coords", "setHtmlCoords", "getHtmlCoords"),

	data("data", "setHtmlData", "getHtmlData"),  // DObject
	datetime("datetime", "setHtmlDateTime", "getHtmlDateTime"),
	declare("declare", "setHtmlDeclare", "getHtmlDeclare"),  // DObject
	defaultchecked("defaultchecked", "setHtmlDefaultChecked", "getHtmlDefaultChecked"),
	defaultSelected("defaultSelected", "setHtmlDefaultSelected", "getHtmlDefaultSelected"), // DOption
	defaultvalue("defaultvalue", "setHtmlDefaultValue", "getHtmlDefaultValue"),
	/**
	    * For ADOM use ONLY
    */
	defaultValue("defaultValue", "setHtmlDefaultValue", "getHtmlDefaultValue"),
	defer("defer", "setHtmlDefer", "getHtmlDefer"),  // DScript
	dir("dir", "setHtmlDir", "getHtmlDir"),
	disabled("disabled", "setHtmlDisabled", "getHtmlDisabled"),

	enctype("enctype", "setHtmlEnctype", "getHtmlEnctype"),

	face("face", "setHtmlFace", "getHtmlFace"),
	_for("for", "setHtmlFor", "getHtmlFor"),  // from label  // actually for
	frame("frame", "setHtmlFrame", "getHtmlFrame"),
	frameborder("frameborder", "setHtmlFrameBorder", "getHtmlFrameBorder"),

	headers("headers", "setHtmlHeaders", "getHtmlHeaders"),
	height("height", "setHtmlHeight", "getHtmlHeight"),
	href("href", "setHtmlHref", "getHtmlHref"),
	hreflang("hreflang", "setHtmlHreflang", "getHtmlHreflang"),
	hspace("hspace", "setHtmlHspace", "getHtmlHspace"),
	http_equiv("http-equiv", "setHtmlHttpEquiv", "getHtmlHttpEquiv"),	// actually http-equiv

	id("id", "setHtmlId", "getHtmlId"),
	ismap("ismap", "setHtmlIsMap", "getHtmlIsMap"),

	label("label", "setHtmlIsLabel", "getHtmlIsLabel"), // DOption
	lang("lang", "setHtmlLang", "getHtmlLang"),
	language("language", "setHtmlLanguage", "getHtmlLanguage"),  // DScript
	length("length", "setHtmlLength", "getHtmlLength"),
	link("link", "setHtmlLink", "getHtmlLink"),
	longdesc("longdesc", "setHtmlLongdesc", "getHtmlLongdesc"),
	lowsrc("lowsrc", "setHtmlLowSrc", "getHtmlLowSrc"),

	marginheight("marginheight", "setHtmlMarginHeight", "getHtmlMarginHeight"),
	marginwidth("marginwidth", "setHtmlMarginWidth", "getHtmlMarginWidth"),
	maxlength("maxlength", "setHtmlMaxLength", "getHtmlMaxLength"),
	media("media", "setHtmlMedia", "getHtmlMedia"),
	method("method", "setHtmlMethod", "getHtmlMethod"),
	multiple("multiple", "setHtmlMultiple", "getHtmlMultiple"), // DSelect

	name("name", "setHtmlName", "getHtmlName"),
	nohref("nohref", "setHtmlNohref", "getHtmlNohref"),
	noresize("noresize", "setHtmlNoResize", "getHtmlNoResize"),
	noshade("noshade", "setHtmlNoShade", "getHtmlNoShade"),
	nowrap("nowrap", "setHtmlNoWrap", "getHtmlNoWrap"),

	object("object", "setHtmlObject", "getHtmlObject"),
	offsetLeft("offsetLeft", "setOffsetLeft", "getOffsetLeft"),
	offsetTop("offsetTop", "setOffsetTop", "getOffsetTop"),
	offsetHeight("offsetHeight", "setOffsetHeight", "getOffsetHeight"),
	offsetParent("offsetParent", "setOffsetParent", "getOffsetParent"),
	offsetWidth("offsetWidth", "setOffsetWidth", "getOffsetWidth"),
	onblur("onblur", "setHtmlOnBlur", "getHtmlOnBlur"),
	onchange("onchange", "setHtmlOnChange", "getHtmlOnChange"),
	onclick("onclick", "setHtmlOnClick", "getHtmlOnClick"),
	ondblclick("ondblclick", "setHtmlOnDoubleClick", "getHtmlOnDoubleClick"),
	onfocus("onfocus", "setHtmlOnFocus", "getHtmlOnFocus"),
	onkeydown("onkeydown", "setHtmlOnKeyDown", "getHtmlOnKeyDown"),
	onkeypress("onkeypress", "setHtmlOnKeyPress", "getHtmlOnKeyPress"),
	onkeyup("onkeyup", "setHtmlOnKeyUp", "getHtmlOnKeyUp"),
	onload("onload", "setHtmlOnLoad", "getHtmlOnLoad"),
	onmousedown("onmousedown", "setHtmlOnMouseDown", "getHtmlOnMouseDown"),
	onmouseup("onmouseup", "setHtmlOnMouseUp", "getHtmlOnMouseUp"),
	onmouseover("onmouseover", "setHtmlOnMouseOver", "getHtmlOnMouseOver"),
	onmousemove("onmousemove", "setHtmlOnMouseMove", "getHtmlOnMouseMove"),
	onmouseout("onmouseout", "setHtmlOnMouseOut", "getHtmlOnMouseOut"),
	onreset("onreset", "setHtmlOnReset", "getHtmlOnReset"),
	onselect("onselect", "setHtmlOnSelect", "getHtmlOnSelect"),
	onsubmit("onsubmit", "setHtmlOnSubmit", "getHtmlOnSubmit"),
	onunload("onunload", "setHtmlOnUnload", "getHtmlOnUnload"),

	profile("profile", "setHtmlProfile", "getHtmlProfile"),
	prompt("prompt", "setHtmlPrompt", "getHtmlPrompt"),

	readonly("readonly", "setHtmlReadOnly", "getHtmlReadOnly"),
	/**
    * For ADOM use ONLY
    */
	readOnly("readOnly", "setHtmlReadOnly", "getHtmlReadOnly"), //For ADOM
	rel("rel", "setHtmlRel", "getHtmlRel"),
	rev("rev", "setHtmlRev", "getHtmlRev"),
	rows("rows", "setHtmlRows", "getHtmlRows"),
	rowspan("rowspan", "setHtmlRowSpan", "getHtmlRowSpan"),
	rules("rules", "setHtmlRules", "getHtmlRules"), // DTable

	scope("scope", "setHtmlScope", "getHtmlScope"),
	scrolling("scrolling", "setHtmlScrolling", "getHtmlScrolling"),
	scrollLeft("scrollLeft", "setScrollLeft", "getScrollLeft"),
	scrollTop("scrollTop", "setScrollTop", "getScrollTop"),
	scrollHeight("scrollHeight", "setScrollHeight", "getScrollHeight"),
	scrollWidth("scrollWidth", "setScrollWidth", "getScrollWidth"),
	scheme("scheme", "setHtmlScheme", "getHtmlScheme"),
	selected("selected", "setHtmlSelected", "getHtmlSelected"),  // DOption
	shape("shape", "setHtmlShape", "getHtmlShape"),
	size("size", "setHtmlSize", "getHtmlSize"),
	span("span", "setHtmlSpan", "getHtmlSpan"),
	src("src", "setHtmlSrc", "getHtmlSrc"),
	standby("standby", "setHtmlStandby", "getHtmlStandby"),  // DObject
	start("start", "setHtmlStart", "getHtmlStart"), // DOl
	style("style", "setHtmlStyleAsString", "getHtmlStyleAsString"),
	summary("summary", "setHtmlSummary", "getHtmlSummary"),  // DTable

	tabindex("tabindex", "setHtmlTabIndex", "getHtmlTabIndex"),
	target("target", "setHtmlTarget", "getHtmlTarget"),
	text("text", "setHtmlText", "getHtmlText"),
	title("title", "setHtmlTitle", "getHtmlTitle"),  // DStyle
	type("type", "setHtmlType", "getHtmlType"),

	usemap("usemap", "setHtmlUseMap", "getHtmlUseMap"),

	valign("valign", "setHtmlValign", "getHtmlValign"),
	value("value", "setHtmlValue", "getHtmlValue"),
	valuetype("valuetype", "setHtmlValueType", "getHtmlValueType"), 
	version("version", "setHtmlVersion", "getHtmlVersion"),
	vlink("vlink", "setHtmlVlink", "getHtmlVlink"),
	vspace("vspace", "setHtmlVspace", "getHtmlVspace"),

	width("width", "setHtmlWidth", "getHtmlWidth"),
	
	// IE - For IE Specific HTML Tags. 
	// DBgSound, DCustom, DEmbed, DMarquee, DNoBr, DRt, DRuby, DWbr, DXml, DXmp
	//
	// This section includes those that are not already defined above, as part
	// of the HTML specific attrs.
	atomicselection("atomicselection", "setHtmlAtomicSelection", "getHtmlAtomicSelection"),
	
	balance("balance", "setHtmlBalance", "getHtmlBalance"),
	behavior("behavior", "setHtmlBehavior", "getHtmlBehavior"),
	
	contenteditable("contenteditable", "setHtmlContentEditable", "getHtmlContentEditable"),
	
	datafld("datafld", "setHtmlDataFld", "getHtmlDataFld"),
	dataformatas("dataformatas", "setHtmlDataFormatAs", "getHtmlDataFormatAs"),
	datasrc("datasrc", "setHtmlDataSrc", "getHtmlDataSrc"),
	direction("direction", "setHtmlDirection", "getHtmlDirection"),
	
	hidden("hidden", "setHtmlHidden", "getHtmlHidden"),
	hidefocus("hidefocus", "setHtmlHideFocus", "getHtmlHideFocus"),
	
	loop("loop", "setHtmlLoop", "getHtmlLoop"),
	
	onactivate("onactivate", "setHtmlOnActivate", "getHtmlOnActivate"),  
	onafterupdate("onafterupdate", "setHtmlOnAfterUpdate", "getHtmlOnAfterUpdate"),
	onbeforeactivate("onbeforeactivate", "setHtmlOnBeforeActivate", "getHtmlOnBeforeActivate"),
	onbeforecopy("onbeforecopy", "setHtmlOnBeforeCopy", "getHtmlOnBeforeCopy"),
	onbeforecut("onbeforecut", "setHtmlOnBeforeCut", "getHtmlOnBeforeCut"),
	onbeforedeactivate("onbeforedeactivate", "setHtmlOnBeforeDeactivate", "getHtmlOnBeforeDeactivate"),
	onbeforeeditfocus("onbeforeeditfocus", "setHtmlOnBeforeEditFocus", "getHtmlOnBeforeEditFocus"),
	onbeforepaste("onbeforepaste", "setHtmlOnBeforePaste", "getHtmlOnBeforePaste"),
	onbeforeupdate("onbeforeupdate", "setHtmlOnBeforeUpdate", "getHtmlOnBeforeUpdate"),
	onbounce("onbounce", "setHtmlOnBounce", "getHtmlOnBounce"),
	oncontextmenu("oncontextmenu", "setHtmlOnContextMenu","getHtmlOnContextMenu"),
	oncontrolselect("oncontrolselect", "setHtmlOnControlSelect", "getHtmlOnControlSelect"),
	oncopy("oncopy", "setHtmlOnCopy", "getHtmlOnCopy"),
	oncut("oncut", "setHtmlOnCut", "getHtmlOnCut"),
	ondeactivate("ondeactivate", "setHtmlOnDeactivate","getHtmlOnDeactivate"),
	ondrag("ondrag", "setHtmlOnDrag", "getHtmlOnDrag"),
	ondragend("ondragend", "setHtmlOnDragEnd", "getHtmlOnDragEnd"),
	ondragenter("ondragenter", "setHtmlOnDragEnter", "getHtmlOnDragEnter"),
	ondragleave("ondragleave", "setHtmlOnDragLeave", "getHtmlOnDragLeave"),
	ondragover("ondragover", "setHtmlOnDragOver", "getHtmlOnDragOver"),
	ondragstart("ondragstart", "setHtmlOnDragStart", "getHtmlOnDragStart"),
	ondrop("ondrop", "setHtmlOnDrop", "getHtmlOnDrop"),
	onerrorupdate("onerrorupdate", "setHtmlOnErrorUpdate", "getHtmlOnErrorUpdate"),
	onfilterchange("onfilterchange", "setHtmlOnFilterChange", "getHtmlOnFilterChange"),
	onfinish("onfinish", "setHtmlOnFinish", "getHtmlOnFinish"),
	onfocusin("onfocusin", "setHtmlOnFocusIn", "getHtmlOnFocusIn"),
	onfocusout("onfocusout", "setHtmlOnFocusOut", "getHtmlOnFocusOut"),
	onhelp("onhelp", "setHtmlOnHelp", "getHtmlOnHelp"),
	onlayoutcomplete("onlayoutcomplete", "setHtmlOnLayoutComplete", "getHtmlOnLayoutComplete"),
	onlosecapture("onlosecapture", "setHtmlOnLoseCapture", "getHtmlOnLoseCapture"),
	onmouseenter("onmouseenter", "setHtmlOnMouseEnter", "getHtmlOnMouseEnter"),
	onmouseleave("onmouseleave", "setHtmlOnMouseLeave", "getHtmlOnMouseLeave"),
	onmousewheel("onmousewheel", "setHtmlOnMouseWheel", "getHtmlOnMouseWheel"),
	onmove("onmove", "setHtmlOnMove", "getHtmlOnMove"),
	onmoveend("onmoveend", "setHtmlOnMoveEnd", "getHtmlOnMoveEnd"),
	onmovestart("onmovestart", "setHtmlOnMoveStart", "getHtmlOnMoveStart"),
	onpaste("onpaste", "setHtmlOnPaste", "getHtmlOnPaste"),
	onpropertychange("onpropertychange", "setHtmlOnPropertyChange", "getHtmlOnPropertyChange"),
	onreadystatechange("onreadystatechange", "setHtmlOnReadyStateChange", "getHtmlOnReadyStateChange"),
	onresize("onresize", "setHtmlOnResize", "getHtmlOnResize"),
	onresizeend("onresizeend", "setHtmlOnResizeEnd", "getHtmlOnResizeEnd"),
	onresizestart("onresizestart", "setHtmlOnResizeStart", "getHtmlOnResizeStart"),
	onscroll("onscroll", "setHtmlOnScroll", "getHtmlOnScroll"),
	onselectstart("onselectstart", "setHtmlOnSelectStart", "getHtmlOnSelectStart"),
	onstart("onstart", "setHtmlOnStart", "getHtmlOnStart"),
	ontimeerror("ontimeerror", "setHtmlOnTimeError", "getHtmlOnTimeError"),
	
	pluginspage("pluginspage", "setHtmlPluginsPage", "getHtmlPluginsPage"),
	
	scrollamount("scrollamount", "setHtmlScrollAmount", "getHtmlScrollAmount"),
	scrolldelay("scrolldelay", "setHtmlScrollDelay", "getHtmlScrollDelay"),
	
	truespeed("truespeed", "setHtmlTrueSpeed", "getHtmlTrueSpeed"),
	
	units("units", "setHtmlUnits", "getHtmlUnits"),
	unselectable("unselectable", "setHtmlUnselectable", "getHtmlUnselectable"),
	
	volume("volume", "setHtmlVolume", "getHtmlVolume"),	
	
	// vvvvvvvvvv ----- HTML 5.0 Attributes start  ------- vvvvvvvv
	
	// Intrisinc/Common attributes
	contentEditable("contenteditable", "setContentEditable", "getContentEditable"), 
	contextMenu("contextmenu", "setContextMenu", "getContextMenu"),
	draggable("draggable", "setDraggable", "getDraggable"),
//	irrelevant("irrelevant", "setIrrelevant", "getIrrelevant"),
	ref("ref", "setRef", "getRef"),
//	registrationMark("registrationmark", "setRegistrationMark", "getRegistrationMark"),
//	template("template", "setTemplate", "getTemplate"),
	
	// Event attributes
	onabort("onabort", "setOnAbort", "getOnAbort"),
	onafterprint("onafterprint", "setOnAfterPrint", "getOnAfterPrint"),
	onbeforeonload("onbeforeonload", "setOnBeforeOnLoad", "getOnBeforeOnLoad"),
	onbeforeprint("onbeforeprint", "setOnBeforePrint", "getOnBeforePrint"),
	onbeforeunload("onbeforeunload", "setOnBeforeUnLoad", "getOnBeforeUnLoad"),
	oncanplay("oncanplay", "setOnCanPlay", "getOnCanPlay"),
	oncanplaythrough("oncanplaythrough", "setOnCanPlayThrough", "getOnCanPlayThrough"),
	ondurationchange("ondurationchange", "setOnDurationChange", "getOnDurationChange"),
	onemptied("onemptied", "setOnEmptied", "getOnEmptied"),
	onended("onended", "setOnEnded", "getOnEnded"),
	onerror("onerror", "setOnError", "getOnError"),
	onformchange("onformchange", "setOnFormChange", "getOnFormChange"),
	onforminput("onforminput", "setOnFormInput", "getOnFormInput"),
	onhaschange("onhaschange", "setOnHasChange", "getOnHasChange"),
	oninput("oninput", "setOnInput", "getOnInput"),
	oninvalid("oninvalid", "setOnInvalid", "getOnInvalid"),
	onloadeddata("onloadeddata", "setOnLoadedData", "getOnLoadedData"),
	onloadedmetadata("onloadedmetadata", "setOnLoadedMetadata", "getOnLoadedMetadata"),
	onloadstart("onloadstart", "setOnLoadStart", "getOnLoadStart"),
	onmessage("onmessage", "setOnMessage", "getOnMessage"),
	onoffline("onoffline", "setOnOffline", "getOnOffline"),
	ononline("ononline", "setOnOnline", "getOnOnline"),
	onpause("onpause", "setOnPause", "getOnPause"),
	onplay("onplay", "setOnPlay", "getOnPlay"),
	onplaying("onplaying", "setOnPlaying", "getOnPlaying"),
	onpopstate("onpopstate", "setOnPopState", "getOnPopState"),
	onprogress("onprogress", "setOnProgress", "getOnProgress"),
	onratechange("onratechange", "setOnRateChange", "getOnRateChange"),
	onredo("onredo", "setOnRedo", "getOnRedo"),
	onseeked("onseeked", "setOnSeeked", "getOnSeeked"),
	onseeking("onseeking", "setOnSeeking", "getOnSeeking"),
	onshow("onshow", "setOnShow", "getOnShow"),
	onstalled("onstalled", "setOnStalled", "getOnStalled"),
	onstorage("onstorage", "setOnStorage", "getOnStorage"),
	onsuspend("onsuspend", "setOnSuspend", "getOnSuspend"),
	ontimeupdate("ontimeupdate", "setOnTimeUpdate", "getOnTimeUpdate"),
	onundo("onundo", "setOnUndo", "getOnUndo"),
	onvolumechange("onvolumechange", "setOnVolumeChange", "getOnVolumeChange"),
	onwaiting("onwaiting", "setOnWaiting", "getOnWaiting"),
	
	// Tag attributes
	async("async", "setAsync", "getAsync"),
	autobuffer("autobuffer", "setAutoBuffer", "getAutoBuffer"),
	autocomplete("autocomplete", "setAutoComplete", "getAutoComplete"),
	autoplay("autoplay", "setAutoPlay", "getAutoPlay"),
	autofocus("autofocus", "setAutoFocus", "getAutoFocus"),
	challenge("challenge", "setChallenge", "getChallenge"),
	condition("condition", "setCondition", "getCondition"),
	controls("controls", "setControls", "getControls"),
	_default("_default", "setDefault", "getDefault"),
	end("end", "setEnd", "getEnd"),
	filter("filter", "setFilter", "getFilter"),
	form("form", "setForm", "getForm"),
	formaction("formaction", "setFormAction", "getFormAction"),
	formenctype("formenctype", "setFormEnctype", "getFormEnctype"),
	formmethod("formmethod", "setFormMethod", "getFormMethod"),
	formnovalidate("formnovalidate", "setFormNoValidate", "getFormNoValidate"),
	formtarget("formtarget", "setFormTarget", "getFormTarget"),
	high("high", "setHigh", "getHigh"),
	icon("icon", "setIcon", "getIcon"),
	item("item", "setItem", "getItem"),
	itemprop("itemprop", "setItemProp", "getItemProp"),
	keytype("keytype", "setKeyType", "getKeyType"),
	list("list", "setList", "getList"),
	loopend("loopend", "setLoopEnd", "getLoopEnd"),
	loopstart("loopstart", "setLoopstart", "getLoopstart"),
	low("low", "setLow", "getLow"),
	manifest("manifest", "setManifest", "getManifest"),
	max("max", "setMax", "getMax"),
	menu("menu", "setMenu", "getMenu"),
	min("min", "setMin", "getMin"),
	mode("mode", "setMode", "getMode"),
	novalidate("novalidate", "setNoValidate", "getNoValidate"),
	open("open", "setOpen", "getOpen"),
	optimum("optimum", "setOptimum", "getOptimum"),
	pattern("pattern", "setPattern", "getPattern"),
	ping("ping", "setPing", "getPing"),
	placeholder("placeholder", "setPlaceholder", "getPlaceholder"),
	playcount("playcount", "setPlayCount", "getPlayCount"),
	poster("poster", "setPoster", "getPoster"),
	pubdate("pubdate", "setPubDate", "getPubDate"),
	radiogroup("radiogroup", "setRadioGroup", "getRadioGroup"),
	required("required", "setRequired", "getRequired"),
	reversed("reversed", "setReversed", "getReversed"),
	sandbox("sandbox", "setSandbox", "getSandbox"),
	scoped("scoped", "setScoped", "getScoped"),
	seamless("seamless", "setSeamless", "getSeamless"),
	sizes("sizes", "setSizes", "getSizes"),
	spellcheck("spellcheck", "setSpellCheck", "getSpellCheck"),
	subject("subject", "setSubject", "getSubject"),
	
	// ^^^^^^^^^  ----- HTML 5.0 Attributes end ------ ^^^^^^^^^
	xmlns("xmlns", "setXmlns", "getXmlns"),
	
	LAST("__last__", "setDummy", "getDummy") ;  // MUST ALWAYS BE THE LAST

	//
	// Core class declaration
	//
	private final String m_attributeName ;
	private final String m_dhtml_setterName ;
	private final String m_dhtml_getterName ;
	
	public static void main(String[] args) {
		EHtmlAttr attr = Enum.valueOf(EHtmlAttr.class, "vspace") ;
		System.out.println(attr) ;
	}
	
	public static EHtmlAttr enumFor(String pureHtmlAttrName) {
		String cleanedUpJavaName = pureHtmlAttrName;
		
		if ("http-equiv".equals(pureHtmlAttrName)) {
			cleanedUpJavaName = "http_equiv" ;
		}
		else if ("for".equals(pureHtmlAttrName)) {
			cleanedUpJavaName = "_for" ;
		}
		else if ("char".equals(pureHtmlAttrName)) {
			cleanedUpJavaName = "_char" ;
		}
		else if ("class".equals(pureHtmlAttrName)) {
			cleanedUpJavaName = "_class" ;
		}
		else if ("accept-charset".equals(pureHtmlAttrName)) {
			cleanedUpJavaName = "accept_charset" ;
		}
		else if ("default".equals(pureHtmlAttrName)) {
			cleanedUpJavaName = "_default" ;
		}

		return Enum.valueOf(EHtmlAttr.class, cleanedUpJavaName) ;
	}
	
	//
	// Constructor(s)
	//
	EHtmlAttr(
		final String name, final String setterName, final String getterName)
	{
		m_attributeName = name ;
		m_dhtml_setterName = setterName ;
		m_dhtml_getterName = getterName ;
	}
	
	//
	// API
	//
	public String getSetterName() {
		return m_dhtml_setterName ;
	}
	
	public String getGetterName() {
		return m_dhtml_getterName ;
	}
	
	public String getAttributeName() { 
		return m_attributeName ;
	}	
	
	public char[] getAsChars() {
		return m_attributeName.toCharArray();
	}
	
	//
	// Override(s) from Object
	//
	@Override
	public String toString() {
		return m_attributeName + " - " + ordinal();
	}
}
