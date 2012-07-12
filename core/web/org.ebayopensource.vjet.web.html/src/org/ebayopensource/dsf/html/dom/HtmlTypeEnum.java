/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.dom;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.ebayopensource.dsf.dom.DText;
import org.ebayopensource.dsf.html.events.IDomType;
import org.ebayopensource.dsf.common.enums.BaseEnum;

public final class HtmlTypeEnum extends BaseEnum implements IDomType {
	private static final long serialVersionUID = 1L;
	
	private static int s_currentOrdinal=0;
	private static final Map<String, HtmlTypeEnum> s_tagNameToEnum 
		= new HashMap<String, HtmlTypeEnum>();
	private static final Map<Class<?>, HtmlTypeEnum> s_classToEnum 
		= new HashMap<Class<?>, HtmlTypeEnum>();
	private static final Map<HtmlTypeEnum, Class<?>> s_enumToClass 
		= new HashMap<HtmlTypeEnum, Class<?>>();

	// The following was code gened with the following script:
	// grep = HtmlType.java|grep -v HTML_DOCUMENT|awk
	//   '{print "\tpublic static final HtmlTypeEnum "$2" = new HtmlTypeEnum(HtmlType."$2",\""tolower($2)"\", D"substr($2,1,1)tolower(substr($2,2))".class);"}'

	// from DOM
	public static final HtmlTypeEnum TEXT = new HtmlTypeEnum(null,DText.class);
	//fix bug 1812
//	public static final HtmlTypeEnum PROCESSINGINSTRUCTION = 
//		new HtmlTypeEnum(null,DProcessingInstruction.class);

	// strictly HTML
	// The IE Specific HTML Tags are listed in the section following this one.
	public static final HtmlTypeEnum A=new HtmlTypeEnum("a",DA.class);
	public static final HtmlTypeEnum ABBR=new HtmlTypeEnum("abbr",DAbbr.class);
	public static final HtmlTypeEnum ACRONYM=new HtmlTypeEnum("acronym",DAcronym.class);
	public static final HtmlTypeEnum ADDRESS=new HtmlTypeEnum("address",DAddress.class);
	public static final HtmlTypeEnum APPLET=new HtmlTypeEnum("applet",DApplet.class);
	public static final HtmlTypeEnum AREA=new HtmlTypeEnum("area",DArea.class);
	public static final HtmlTypeEnum B=new HtmlTypeEnum("b",DB.class);
	public static final HtmlTypeEnum BASE=new HtmlTypeEnum("base",DBase.class);
	public static final HtmlTypeEnum BASEFONT=new HtmlTypeEnum("basefont",DBaseFont.class);
	public static final HtmlTypeEnum BDO=new HtmlTypeEnum("bdo",DBdo.class);
	public static final HtmlTypeEnum BIG=new HtmlTypeEnum("big",DBig.class);
	public static final HtmlTypeEnum BLOCKQUOTE=new HtmlTypeEnum("blockquote",DBlockQuote.class);
	public static final HtmlTypeEnum BODY=new HtmlTypeEnum("body",DBody.class);
	public static final HtmlTypeEnum BR=new HtmlTypeEnum("br",DBr.class);
	public static final HtmlTypeEnum BUTTON=new HtmlTypeEnum("button",DButton.class);
	public static final HtmlTypeEnum CAPTION=new HtmlTypeEnum("caption",DCaption.class);
	public static final HtmlTypeEnum CENTER=new HtmlTypeEnum("center",DCenter.class);
	public static final HtmlTypeEnum CITE=new HtmlTypeEnum("cite",DCite.class);
	public static final HtmlTypeEnum CODE=new HtmlTypeEnum("code",DCode.class);
	public static final HtmlTypeEnum COL=new HtmlTypeEnum("col",DCol.class);
	public static final HtmlTypeEnum COLGROUP=new HtmlTypeEnum("colgroup",DColGroup.class);
	public static final HtmlTypeEnum DD=new HtmlTypeEnum("dd",DDd.class);
	public static final HtmlTypeEnum DEL=new HtmlTypeEnum("del",DDel.class);
	public static final HtmlTypeEnum DFN=new HtmlTypeEnum("dfn",DDfn.class);
	public static final HtmlTypeEnum DIR=new HtmlTypeEnum("dir",DDir.class);
	public static final HtmlTypeEnum DIV=new HtmlTypeEnum("div",DDiv.class);
	public static final HtmlTypeEnum DL=new HtmlTypeEnum("dl",DDl.class);
	public static final HtmlTypeEnum DT=new HtmlTypeEnum("dt",DDt.class);
	public static final HtmlTypeEnum EM=new HtmlTypeEnum("em",DEm.class);
	public static final HtmlTypeEnum FIELDSET=new HtmlTypeEnum("fieldset",DFieldSet.class);
	public static final HtmlTypeEnum FONT=new HtmlTypeEnum("font",DFont.class);
	public static final HtmlTypeEnum FORM=new HtmlTypeEnum("form",DForm.class);
	public static final HtmlTypeEnum FRAME=new HtmlTypeEnum("frame",DFrame.class);
	public static final HtmlTypeEnum FRAMESET=new HtmlTypeEnum("frameset",DFrameSet.class);
	public static final HtmlTypeEnum H1=new HtmlTypeEnum("h1",DH1.class);
	public static final HtmlTypeEnum H2=new HtmlTypeEnum("h2",DH2.class);
	public static final HtmlTypeEnum H3=new HtmlTypeEnum("h3",DH3.class);
	public static final HtmlTypeEnum H4=new HtmlTypeEnum("h4",DH4.class);
	public static final HtmlTypeEnum H5=new HtmlTypeEnum("h5",DH5.class);
	public static final HtmlTypeEnum H6=new HtmlTypeEnum("h6",DH6.class);
	public static final HtmlTypeEnum HEAD=new HtmlTypeEnum("head",DHead.class);
	public static final HtmlTypeEnum HR=new HtmlTypeEnum("hr",DHr.class);
	public static final HtmlTypeEnum HTML=new HtmlTypeEnum("html",DHtml.class);
	public static final HtmlTypeEnum I=new HtmlTypeEnum("i",DI.class);
	public static final HtmlTypeEnum IFRAME=new HtmlTypeEnum("iframe",DIFrame.class);
	public static final HtmlTypeEnum IMG=new HtmlTypeEnum("img",DImg.class);
	public static final HtmlTypeEnum INPUT=new HtmlTypeEnum("input",DInput.class);
	public static final HtmlTypeEnum INS=new HtmlTypeEnum("ins",DIns.class);
	public static final HtmlTypeEnum ISINDEX=new HtmlTypeEnum("isindex",DIsIndex.class);
	public static final HtmlTypeEnum KBD=new HtmlTypeEnum("kbd",DKbd.class);
	public static final HtmlTypeEnum LABEL=new HtmlTypeEnum("label",DLabel.class);
	public static final HtmlTypeEnum LEGEND=new HtmlTypeEnum("legend",DLegend.class);
	public static final HtmlTypeEnum LI=new HtmlTypeEnum("li",DLi.class);
	public static final HtmlTypeEnum LINK=new HtmlTypeEnum("link",DLink.class);
	public static final HtmlTypeEnum MAP=new HtmlTypeEnum("map",DMap.class);
	public static final HtmlTypeEnum MENU=new HtmlTypeEnum("menu",DMenu.class);
	public static final HtmlTypeEnum META=new HtmlTypeEnum("meta",DMeta.class);
	public static final HtmlTypeEnum MOD=new HtmlTypeEnum("mod",DDel.class);
	public static final HtmlTypeEnum NOFRAMES=new HtmlTypeEnum("noframes",DNoFrames.class);
	public static final HtmlTypeEnum NOSCRIPT=new HtmlTypeEnum("noscript",DNoScript.class);
	public static final HtmlTypeEnum OBJECT=new HtmlTypeEnum("object",DObject.class);
	public static final HtmlTypeEnum OL=new HtmlTypeEnum("ol",DOl.class);
	public static final HtmlTypeEnum OPTGROUP=new HtmlTypeEnum("optgroup",DOptGroup.class);
	public static final HtmlTypeEnum OPTION=new HtmlTypeEnum("option",DOption.class);
	public static final HtmlTypeEnum P=new HtmlTypeEnum("p",DP.class);
	public static final HtmlTypeEnum PARAM=new HtmlTypeEnum("param",DParam.class);
	public static final HtmlTypeEnum PRE=new HtmlTypeEnum("pre",DPre.class);
	public static final HtmlTypeEnum Q=new HtmlTypeEnum("q",DQ.class);
	public static final HtmlTypeEnum S=new HtmlTypeEnum("s",DS.class);
	public static final HtmlTypeEnum SAMP=new HtmlTypeEnum("samp",DSamp.class);
	public static final HtmlTypeEnum SCRIPT=new HtmlTypeEnum("script",DScript.class);
	public static final HtmlTypeEnum SELECT=new HtmlTypeEnum("select",DSelect.class);
	public static final HtmlTypeEnum SMALL=new HtmlTypeEnum("small",DSmall.class);
	public static final HtmlTypeEnum SPAN=new HtmlTypeEnum("span",DSpan.class);
	public static final HtmlTypeEnum STRIKE=new HtmlTypeEnum("strike",DStrike.class);
	public static final HtmlTypeEnum STRONG=new HtmlTypeEnum("strong",DStrong.class);
	public static final HtmlTypeEnum STYLE=new HtmlTypeEnum("style",DStyle.class);
	public static final HtmlTypeEnum SUB=new HtmlTypeEnum("sub",DSub.class);
	public static final HtmlTypeEnum SUP=new HtmlTypeEnum("sup",DSup.class);
	public static final HtmlTypeEnum TABLE=new HtmlTypeEnum("table",DTable.class);
	public static final HtmlTypeEnum TBODY=new HtmlTypeEnum("tbody",DTBody.class);
	public static final HtmlTypeEnum TD=new HtmlTypeEnum("td",DTd.class);
	public static final HtmlTypeEnum TEXTAREA=new HtmlTypeEnum("textarea",DTextArea.class);
	public static final HtmlTypeEnum TFOOT=new HtmlTypeEnum("tfoot",DTFoot.class);
	public static final HtmlTypeEnum TH=new HtmlTypeEnum("th",DTh.class);
	public static final HtmlTypeEnum THEAD=new HtmlTypeEnum("thead",DTHead.class);
	public static final HtmlTypeEnum TITLE=new HtmlTypeEnum("title",DTitle.class);
	public static final HtmlTypeEnum TR=new HtmlTypeEnum("tr",DTr.class);
	public static final HtmlTypeEnum TT=new HtmlTypeEnum("tt",DTt.class);
	public static final HtmlTypeEnum U=new HtmlTypeEnum("u",DU.class);
	public static final HtmlTypeEnum UL=new HtmlTypeEnum("ul",DUl.class);
	public static final HtmlTypeEnum VAR=new HtmlTypeEnum("var",DVar.class);
//	public static final HtmlTypeEnum HTML_DOCUMENT = new HtmlTypeEnum(HtmlType.HTML_DOCUMENT,null,DHtmlDocument.class);
//	public static final HtmlTypeEnum TEXT = new HtmlTypeEnum(NodeType.TEXT,null,DText.class);

	// HTML 5.0 Tags
	public static final HtmlTypeEnum ARTICLE=new HtmlTypeEnum("article",DArticle.class);
	public static final HtmlTypeEnum ASIDE=new HtmlTypeEnum("aside",DAside.class);
	public static final HtmlTypeEnum AUDIO=new HtmlTypeEnum("audio",DAudio.class);
	public static final HtmlTypeEnum BB=new HtmlTypeEnum("bb",DBb.class);
	public static final HtmlTypeEnum CANVAS=new HtmlTypeEnum("canvas",DCanvas.class);
	public static final HtmlTypeEnum COMMAND=new HtmlTypeEnum("command",DCommand.class);
	public static final HtmlTypeEnum DATAGRID=new HtmlTypeEnum("datagrid",DDataGrid.class);
	public static final HtmlTypeEnum DATALIST=new HtmlTypeEnum("datalist",DDataList.class);
	public static final HtmlTypeEnum DATATEMPLATE=new HtmlTypeEnum("datatemplate",DDataTemplate.class);
	public static final HtmlTypeEnum DETAILS=new HtmlTypeEnum("details",DDetails.class);
	public static final HtmlTypeEnum DIALOG=new HtmlTypeEnum("dialog",DDialog.class);
//	public static final HtmlTypeEnum EMBED=new HtmlTypeEnum("embed",DEmbed.class);
	public static final HtmlTypeEnum EVENTSOURCE=new HtmlTypeEnum("eventsource",DEventSource.class);
	public static final HtmlTypeEnum FIGURE=new HtmlTypeEnum("figure",DFigure.class);
	public static final HtmlTypeEnum FIGCAPTION=new HtmlTypeEnum("figcaption",DFigCaption.class);
	public static final HtmlTypeEnum FOOTER=new HtmlTypeEnum("footer",DFooter.class);
	public static final HtmlTypeEnum HEADER=new HtmlTypeEnum("header",DHeader.class);
	public static final HtmlTypeEnum HGROUP=new HtmlTypeEnum("hgroup",DHGroup.class);
	public static final HtmlTypeEnum M=new HtmlTypeEnum("m",DM.class);
	public static final HtmlTypeEnum KEYGEN=new HtmlTypeEnum("keygen",DKeyGen.class);
	public static final HtmlTypeEnum MARK=new HtmlTypeEnum("mark",DMark.class);
	public static final HtmlTypeEnum METER=new HtmlTypeEnum("meter",DMeter.class);
	public static final HtmlTypeEnum NAV=new HtmlTypeEnum("nav",DNav.class);
	public static final HtmlTypeEnum NEST=new HtmlTypeEnum("nest",DNest.class);
	public static final HtmlTypeEnum OUTPUT=new HtmlTypeEnum("output",DOutput.class);
	public static final HtmlTypeEnum PROGRESS=new HtmlTypeEnum("progress",DProgress.class);
	public static final HtmlTypeEnum RULE=new HtmlTypeEnum("rule",DRule.class);
	public static final HtmlTypeEnum RUBY=new HtmlTypeEnum("ruby",DRuby.class);
	public static final HtmlTypeEnum RP=new HtmlTypeEnum("rp",DRp.class);
	public static final HtmlTypeEnum RT=new HtmlTypeEnum("rt",DRt.class);
	public static final HtmlTypeEnum SECTION=new HtmlTypeEnum("section",DSection.class);
	public static final HtmlTypeEnum SOURCE=new HtmlTypeEnum("source",DSource.class);
	public static final HtmlTypeEnum SUMMARY=new HtmlTypeEnum("summary",DSummary.class);
	public static final HtmlTypeEnum TIME=new HtmlTypeEnum("time",DTime.class);
	public static final HtmlTypeEnum VIDEO=new HtmlTypeEnum("video",DVideo.class);
	
	// IE Specific HTML Tags
	public static final HtmlTypeEnum BGSOUND=new HtmlTypeEnum("bgsound",DBgSound.class);
	public static final HtmlTypeEnum EMBED=new HtmlTypeEnum("embed",DEmbed.class);
	public static final HtmlTypeEnum MARQUEE=new HtmlTypeEnum("marquee",DMarquee.class);
	public static final HtmlTypeEnum NOBR=new HtmlTypeEnum("nobr",DNoBr.class);
//	public static final HtmlTypeEnum RT_IE=new HtmlTypeEnum("rt_ie", DRt_IE.class) ;
//	public static final HtmlTypeEnum RUBY_IE=new HtmlTypeEnum("ruby_ie",DRuby_IE.class);
	public static final HtmlTypeEnum WBR=new HtmlTypeEnum("wbr",DWbr.class);
	public static final HtmlTypeEnum XML=new HtmlTypeEnum("xml",DXml.class);
	public static final HtmlTypeEnum XMP=new HtmlTypeEnum("xmp",DXmp.class);
	
	private final Class<?> m_typeClass;
	private final char [] m_nameChars;
	private HtmlTypeEnum(String name, final Class<?> typeClass) {
		super(s_currentOrdinal, name);
		s_currentOrdinal++;
		m_typeClass = typeClass;
		if (name != null) {
			s_tagNameToEnum.put(name, this);
		}
		if (typeClass != null) {
			s_classToEnum.put(typeClass, this);
			s_enumToClass.put(this, typeClass) ;
		}
		if (name == null){
			m_nameChars = null;
		} else {
			m_nameChars = name.toCharArray();
		}
	}

	/** internal method, only for use within this library.  Any use
	 * by code above the library will result in a P1 bug being filed.  By
	 * using this method one agrees to the terms.
	 * @return char [] - char array for the name
	 */
	char [] getNameChars(){
		return m_nameChars;
	}
	
	//
	// API
	//
	public Class getTypeClass() {
		return m_typeClass;
	}
	
	public static int size() {
		return s_currentOrdinal;
	}
	
	public static HtmlTypeEnum getEnum(final String name) {
		return s_tagNameToEnum.get(name);
	}
	
	public static HtmlTypeEnum getEnum(final Class<?> typeClass) {
		return s_classToEnum.get(typeClass);
	}
	
	public static Class<?> getClass(final HtmlTypeEnum htmlTypeEnum) {
		return s_enumToClass.get(htmlTypeEnum);
	}
	
	public static Iterator<HtmlTypeEnum> valueIterator(){
		final Iterator<HtmlTypeEnum> iterator
			= BaseEnum.getIterator(HtmlTypeEnum.class);
		return iterator;
	}

	public static Iterable<HtmlTypeEnum> valueIterable(){
		return s_iterable;
	}
	
	//
	// Private
	//
	private static Iterable<HtmlTypeEnum> s_iterable = new Iterable<HtmlTypeEnum>(){
		// has no state, so it is multi-thread safe.
		public Iterator<HtmlTypeEnum> iterator() {
			return valueIterator();
		}		
	};
}
