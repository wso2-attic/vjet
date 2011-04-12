/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.active.dom.html;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.ebayopensource.dsf.html.dom.HtmlTypeEnum;
import org.ebayopensource.dsf.jsnative.*;

public interface AHtmlType<T extends HtmlElement> {
	
	T create(HtmlDocument doc);
	String getTagName();
	Class<?> getTypeClass();
	Class<?> getJsNativeClass();
	HtmlTypeEnum getType();
	
	AHtmlType<HtmlAnchor> A = 
		new Type<HtmlAnchor>(HtmlTypeEnum.A, AHtmlAnchor.class);
	AHtmlType<HtmlAbbr> ABBR = 
		new Type<HtmlAbbr>(HtmlTypeEnum.ABBR, AHtmlAbbr.class);
	AHtmlType<HtmlAcronym> ACRONYM = 
		new Type<HtmlAcronym>(HtmlTypeEnum.ACRONYM, AHtmlAcronym.class);
	AHtmlType<HtmlAddress> ADDRESS  = 
		new Type<HtmlAddress>(HtmlTypeEnum.ADDRESS, AHtmlAddress.class);
	AHtmlType<HtmlApplet> APPLET = 
		new Type<HtmlApplet>(HtmlTypeEnum.APPLET, AHtmlApplet.class);
	AHtmlType<HtmlArea> AREA = 
		new Type<HtmlArea>(HtmlTypeEnum.AREA, AHtmlArea.class);
	AHtmlType<HtmlB> B = 
		new Type<HtmlB>(HtmlTypeEnum.B, AHtmlB.class);
	AHtmlType<HtmlBase> BASE = 
		new Type<HtmlBase>(HtmlTypeEnum.BASE, AHtmlBase.class);
	AHtmlType<HtmlBaseFont> BASEFONT = 
		new Type<HtmlBaseFont>(HtmlTypeEnum.BASEFONT, AHtmlBaseFont.class);
	AHtmlType<HtmlBdo> BDO = 
		new Type<HtmlBdo>(HtmlTypeEnum.BDO, AHtmlBdo.class);
	AHtmlType<HtmlBig> BIG = 
		new Type<HtmlBig>(HtmlTypeEnum.BIG, AHtmlBig.class);
	AHtmlType<HtmlBgSound> BGSOUND = 
		new Type<HtmlBgSound>(HtmlTypeEnum.BGSOUND, AHtmlBgSound.class);
	AHtmlType<HtmlBlockquote> BLOCKQUOTE = 
		new Type<HtmlBlockquote>(HtmlTypeEnum.BLOCKQUOTE, AHtmlBlockquote.class);
	AHtmlType<HtmlBody> BODY = 
		new Type<HtmlBody>(HtmlTypeEnum.BODY, AHtmlBody.class);
	AHtmlType<HtmlBr> BR = 
		new Type<HtmlBr>(HtmlTypeEnum.BR, AHtmlBr.class);
	AHtmlType<HtmlButton> BUTTON = 
		new Type<HtmlButton>(HtmlTypeEnum.BUTTON, AHtmlButton.class);
	AHtmlType<HtmlTableCaption> CAPTION = 
		new Type<HtmlTableCaption>(HtmlTypeEnum.CAPTION, AHtmlTableCaption.class);
	AHtmlType<HtmlCenter> CENTER = 
		new Type<HtmlCenter>(HtmlTypeEnum.CENTER, AHtmlCenter.class);
	AHtmlType<HtmlCite> CITE = 
		new Type<HtmlCite>(HtmlTypeEnum.CITE, AHtmlCite.class);
	AHtmlType<HtmlCode> CODE = 
		new Type<HtmlCode>(HtmlTypeEnum.CODE, AHtmlCode.class);
	AHtmlType<HtmlTableCol> COL = 
		new Type<HtmlTableCol>(HtmlTypeEnum.COL, AHtmlTableCol.class);
	AHtmlType<HtmlColgroup> COLGROUP = 
		new Type<HtmlColgroup>(HtmlTypeEnum.COLGROUP, AHtmlColgroup.class);
	AHtmlType<HtmlDel> DEL = 
		new Type<HtmlDel>(HtmlTypeEnum.DEL, AHtmlDel.class);
	AHtmlType<HtmlDd> DD = 
		new Type<HtmlDd>(HtmlTypeEnum.DD, AHtmlDd.class);
	AHtmlType<HtmlDfn> DFN = 
		new Type<HtmlDfn>(HtmlTypeEnum.DFN, AHtmlDfn.class);
	AHtmlType<HtmlDl> DL = 
		new Type<HtmlDl>(HtmlTypeEnum.DL, AHtmlDl.class);
	AHtmlType<HtmlDir> DIR = 
		new Type<HtmlDir>(HtmlTypeEnum.DIR, AHtmlDir.class);
	AHtmlType<HtmlDiv> DIV = 
		new Type<HtmlDiv>(HtmlTypeEnum.DIV, AHtmlDiv.class);
	AHtmlType<HtmlDt> DT = 
		new Type<HtmlDt>(HtmlTypeEnum.DT, AHtmlDt.class);
	AHtmlType<HtmlEm> EM = 
		new Type<HtmlEm>(HtmlTypeEnum.EM, AHtmlEm.class);	
	AHtmlType<HtmlEmbed> EMBED = 
		new Type<HtmlEmbed>(HtmlTypeEnum.EMBED, AHtmlEmbed.class);
	AHtmlType<HtmlFieldSet> FIELDSET = 
		new Type<HtmlFieldSet>(HtmlTypeEnum.FIELDSET, AHtmlFieldSet.class);
	AHtmlType<HtmlFont> FONT = 
		new Type<HtmlFont>(HtmlTypeEnum.FONT, AHtmlFont.class);
	AHtmlType<HtmlForm> FORM = 
		new Type<HtmlForm>(HtmlTypeEnum.FORM, AHtmlForm.class);
	AHtmlType<HtmlFrame> FRAME = 
		new Type<HtmlFrame>(HtmlTypeEnum.FRAME, AHtmlFrame.class);
	AHtmlType<HtmlFrameSet> FRAMESET = 
		new Type<HtmlFrameSet>(HtmlTypeEnum.FRAMESET, AHtmlFrameSet.class);
	AHtmlType<HtmlHeading> H1 = 
		new Type<HtmlHeading>(HtmlTypeEnum.H1, AHtmlHeading.class);
	AHtmlType<HtmlHeading> H2 = 
		new Type<HtmlHeading>(HtmlTypeEnum.H2, AHtmlHeading.class);
	AHtmlType<HtmlHeading> H3 = 
		new Type<HtmlHeading>(HtmlTypeEnum.H3, AHtmlHeading.class);
	AHtmlType<HtmlHeading> H4 = 
		new Type<HtmlHeading>(HtmlTypeEnum.H4, AHtmlHeading.class);
	AHtmlType<HtmlHeading> H5 = 
		new Type<HtmlHeading>(HtmlTypeEnum.H5, AHtmlHeading.class);
	AHtmlType<HtmlHeading> H6 = 
		new Type<HtmlHeading>(HtmlTypeEnum.H6, AHtmlHeading.class);
	AHtmlType<HtmlHead> HEAD = 
		new Type<HtmlHead>(HtmlTypeEnum.HEAD, AHtmlHead.class);
	AHtmlType<HtmlHr> HR = 
		new Type<HtmlHr>(HtmlTypeEnum.HR, AHtmlHr.class);
	AHtmlType<HtmlHtml> HTML = 
		new Type<HtmlHtml>(HtmlTypeEnum.HTML, AHtmlHtml.class);
	AHtmlType<HtmlI> I = 
		new Type<HtmlI>(HtmlTypeEnum.I, AHtmlI.class);
	AHtmlType<HtmlIns> INS = 
		new Type<HtmlIns>(HtmlTypeEnum.INS, AHtmlIns.class);
	AHtmlType<HtmlIFrame> IFRAME = 
		new Type<HtmlIFrame>(HtmlTypeEnum.IFRAME, AHtmlIFrame.class);
	AHtmlType<HtmlImage> IMG = 
		new Type<HtmlImage>(HtmlTypeEnum.IMG, AHtmlImage.class);
	AHtmlType<HtmlInput> INPUT = 
		new Type<HtmlInput>(HtmlTypeEnum.INPUT, AHtmlInput.class);
	AHtmlType<HtmlIsIndex> ISINDEX = 
		new Type<HtmlIsIndex>(HtmlTypeEnum.ISINDEX, AHtmlIsIndex.class);
	AHtmlType<HtmlKbd> KBD = 
		new Type<HtmlKbd>(HtmlTypeEnum.KBD, AHtmlKbd.class);
	AHtmlType<HtmlLabel> LABEL = 
		new Type<HtmlLabel>(HtmlTypeEnum.LABEL, AHtmlLabel.class);
	AHtmlType<HtmlLegend> LEGEND = 
		new Type<HtmlLegend>(HtmlTypeEnum.LEGEND, AHtmlLegend.class);
	AHtmlType<HtmlLi> LI = 
		new Type<HtmlLi>(HtmlTypeEnum.LI, AHtmlLi.class);
	AHtmlType<HtmlLink> LINK = 
		new Type<HtmlLink>(HtmlTypeEnum.LINK, AHtmlLink.class);
	AHtmlType<HtmlMap> MAP = 
		new Type<HtmlMap>(HtmlTypeEnum.MAP, AHtmlMap.class);
	AHtmlType<HtmlMarquee> MARQUEE = 
		new Type<HtmlMarquee>(HtmlTypeEnum.MARQUEE, AHtmlMarquee.class);
	AHtmlType<HtmlMenu> MENU = 
		new Type<HtmlMenu>(HtmlTypeEnum.MENU, AHtmlMenu.class);
	AHtmlType<HtmlMeta> META = 
		new Type<HtmlMeta>(HtmlTypeEnum.META, AHtmlMeta.class);
	AHtmlType<HtmlMod> MOD = 
		new Type<HtmlMod>(HtmlTypeEnum.MOD, AHtmlMod.class);
	AHtmlType<HtmlNoBr> NOBR = 
		new Type<HtmlNoBr>(HtmlTypeEnum.NOBR, AHtmlNoBr.class);
	AHtmlType<HtmlNoFrames> NOFRAMES = 
		new Type<HtmlNoFrames>(HtmlTypeEnum.NOFRAMES, AHtmlNoFrames.class);
	AHtmlType<HtmlNoScript> NOSCRIPT = 
		new Type<HtmlNoScript>(HtmlTypeEnum.NOSCRIPT, AHtmlNoScript.class);
	AHtmlType<HtmlOption> OPTION = 
		new Type<HtmlOption>(HtmlTypeEnum.OPTION, AHtmlOption.class);
	AHtmlType<HtmlObject> OBJECT = 
		new Type<HtmlObject>(HtmlTypeEnum.OBJECT, AHtmlObject.class);
	AHtmlType<HtmlOl> OL = 
		new Type<HtmlOl>(HtmlTypeEnum.OL, AHtmlOl.class);
	AHtmlType<HtmlOptGroup> OPTGROUP = 
		new Type<HtmlOptGroup>(HtmlTypeEnum.OPTGROUP, AHtmlOptGroup.class);
	AHtmlType<HtmlParagraph> P = 
		new Type<HtmlParagraph>(HtmlTypeEnum.P, AHtmlParagraph.class);
	AHtmlType<HtmlParam> PARAM = 
		new Type<HtmlParam>(HtmlTypeEnum.PARAM, AHtmlParam.class);
	AHtmlType<HtmlPre> PRE = 
		new Type<HtmlPre>(HtmlTypeEnum.PRE, AHtmlPre.class);
	AHtmlType<HtmlQuote> Q = 
		new Type<HtmlQuote>(HtmlTypeEnum.Q, AHtmlQuote.class);
//	AHtmlType<HtmlRt_IE> RT_IE = 
//		new Type<HtmlRt_IE>(HtmlTypeEnum.RT_IE, AHtmlRt_IE.class);
//	AHtmlType<HtmlRuby_IE> RUBY_IE = 
//		new Type<HtmlRuby_IE>(HtmlTypeEnum.RUBY_IE, AHtmlRuby_IE.class);
	AHtmlType<HtmlS> S = 
		new Type<HtmlS>(HtmlTypeEnum.S, AHtmlS.class);
	AHtmlType<HtmlSamp> SAMP = 
		new Type<HtmlSamp>(HtmlTypeEnum.SAMP, AHtmlSamp.class);
	AHtmlType<HtmlScript> SCRIPT = 
		new Type<HtmlScript>(HtmlTypeEnum.SCRIPT, AHtmlScript.class);
	AHtmlType<HtmlSelect> SELECT = 
		new Type<HtmlSelect>(HtmlTypeEnum.SELECT, AHtmlSelect.class);
	AHtmlType<HtmlSmall> SMALL = 
		new Type<HtmlSmall>(HtmlTypeEnum.SMALL, AHtmlSmall.class);
	AHtmlType<HtmlSpan> SPAN = 
		new Type<HtmlSpan>(HtmlTypeEnum.SPAN, AHtmlSpan.class);
	AHtmlType<HtmlStrong> STRONG = 
		new Type<HtmlStrong>(HtmlTypeEnum.STRONG, AHtmlStrong.class);
	AHtmlType<HtmlStrike> STRIKE = 
		new Type<HtmlStrike>(HtmlTypeEnum.STRIKE, AHtmlStrike.class);
	AHtmlType<HtmlStyle> STYLE = 
		new Type<HtmlStyle>(HtmlTypeEnum.STYLE, AHtmlStyle.class);
	AHtmlType<HtmlSub> SUB = 
		new Type<HtmlSub>(HtmlTypeEnum.SUB, AHtmlSub.class);
	AHtmlType<HtmlSup> SUP = 
		new Type<HtmlSup>(HtmlTypeEnum.SUP, AHtmlSup.class);
	AHtmlType<HtmlTable> TABLE = 
		new Type<HtmlTable>(HtmlTypeEnum.TABLE, AHtmlTable.class);
	AHtmlType<HtmlTableSection> TBODY = 
		new Type<HtmlTableSection>(HtmlTypeEnum.TBODY, AHtmlTableSection.class);
	AHtmlType<HtmlTableCell> TD = 
		new Type<HtmlTableCell>(HtmlTypeEnum.TD, AHtmlTableCell.class);
	AHtmlType<HtmlTextArea> TEXTAREA = 
		new Type<HtmlTextArea>(HtmlTypeEnum.TEXTAREA, AHtmlTextArea.class);
	AHtmlType<HtmlTableSection> TFOOT = 
		new Type<HtmlTableSection>(HtmlTypeEnum.TFOOT, AHtmlTableSection.class);
	AHtmlType<HtmlTh> TH = 
		new Type<HtmlTh>(HtmlTypeEnum.TH, AHtmlTh.class);
	AHtmlType<HtmlTableSection> THEAD = 
		new Type<HtmlTableSection>(HtmlTypeEnum.THEAD, AHtmlTableSection.class);
	AHtmlType<HtmlTitle> TITLE = 
		new Type<HtmlTitle>(HtmlTypeEnum.TITLE, AHtmlTitle.class);
	AHtmlType<HtmlTableRow> TR = 
		new Type<HtmlTableRow>(HtmlTypeEnum.TR, AHtmlTableRow.class);
	AHtmlType<HtmlTt> TT = 
		new Type<HtmlTt>(HtmlTypeEnum.TT, AHtmlTt.class);
	AHtmlType<HtmlU> U = 
		new Type<HtmlU>(HtmlTypeEnum.U, AHtmlU.class);
	AHtmlType<HtmlUl> UL = 
		new Type<HtmlUl>(HtmlTypeEnum.UL, AHtmlUl.class);
	AHtmlType<HtmlVar> VAR = 
		new Type<HtmlVar>(HtmlTypeEnum.VAR, AHtmlVar.class);
	AHtmlType<HtmlWbr> WBR = 
		new Type<HtmlWbr>(HtmlTypeEnum.WBR, AHtmlWbr.class);
	AHtmlType<HtmlXml> XML = 
		new Type<HtmlXml>(HtmlTypeEnum.XML, AHtmlXml.class);
	AHtmlType<HtmlXmp> XMP = 
		new Type<HtmlXmp>(HtmlTypeEnum.XMP, AHtmlXmp.class);
	
	// HTML 5.0 Types
	AHtmlType<HtmlArticle> ARTICLE = 
		new Type<HtmlArticle>(HtmlTypeEnum.ARTICLE, AHtmlArticle.class);
	
	AHtmlType<HtmlAside> ASIDE = 
		new Type<HtmlAside>(HtmlTypeEnum.ASIDE, AHtmlAside.class);
	
	AHtmlType<HtmlAudio> AUDIO = 
		new Type<HtmlAudio>(HtmlTypeEnum.AUDIO, AHtmlAudio.class);
	
	AHtmlType<HtmlBb> BB = 
		new Type<HtmlBb>(HtmlTypeEnum.BB, AHtmlBb.class);
	
	AHtmlType<HtmlCanvas> CANVAS = 
		new Type<HtmlCanvas>(HtmlTypeEnum.CANVAS, AHtmlCanvas.class);
	
	AHtmlType<HtmlCommand> COMMAND = 
		new Type<HtmlCommand>(HtmlTypeEnum.COMMAND, AHtmlCommand.class);
	
	AHtmlType<HtmlDataGrid> DATAGRID = 
		new Type<HtmlDataGrid>(HtmlTypeEnum.DATAGRID, AHtmlDataGrid.class);
	
	AHtmlType<HtmlDataList> DATALIST = 
		new Type<HtmlDataList>(HtmlTypeEnum.DATALIST, AHtmlDataList.class);
	
	AHtmlType<HtmlDetails> DETAILS = 
		new Type<HtmlDetails>(HtmlTypeEnum.DETAILS, AHtmlDetails.class);
	
	AHtmlType<HtmlDialog> DIALOG = 
		new Type<HtmlDialog>(HtmlTypeEnum.DIALOG, AHtmlDialog.class);
	
	AHtmlType<HtmlFigure> FIGURE = 
		new Type<HtmlFigure>(HtmlTypeEnum.FIGURE, AHtmlFigure.class);
	
	AHtmlType<HtmlFooter> FOOTER = 
		new Type<HtmlFooter>(HtmlTypeEnum.FOOTER, AHtmlFooter.class);
	
	AHtmlType<HtmlHeader> HEADER = 
		new Type<HtmlHeader>(HtmlTypeEnum.HEADER, AHtmlHeader.class);
	
	AHtmlType<HtmlHGroup> HGROUP = 
		new Type<HtmlHGroup>(HtmlTypeEnum.HGROUP, AHtmlHGroup.class);
	
	AHtmlType<HtmlKeyGen> KEYGEN = 
		new Type<HtmlKeyGen>(HtmlTypeEnum.KEYGEN, AHtmlKeyGen.class);
	
	AHtmlType<HtmlMark> MARK = 
		new Type<HtmlMark>(HtmlTypeEnum.MARK, AHtmlMark.class);
	
	AHtmlType<HtmlMeter> METER = 
		new Type<HtmlMeter>(HtmlTypeEnum.METER, AHtmlMeter.class);
	
	AHtmlType<HtmlNav> NAV = 
		new Type<HtmlNav>(HtmlTypeEnum.NAV, AHtmlNav.class);
	
	AHtmlType<HtmlOutput> OUTPUT = 
		new Type<HtmlOutput>(HtmlTypeEnum.OUTPUT, AHtmlOutput.class);
	
	AHtmlType<HtmlProgress> PROGRESS = 
		new Type<HtmlProgress>(HtmlTypeEnum.PROGRESS, AHtmlProgress.class);
	
	AHtmlType<HtmlRuby> RUBY = 
		new Type<HtmlRuby>(HtmlTypeEnum.RUBY, AHtmlRuby.class);
	
	AHtmlType<HtmlRp> RP = 
		new Type<HtmlRp>(HtmlTypeEnum.RP, AHtmlRp.class);
	
	AHtmlType<HtmlRt> RT = 
		new Type<HtmlRt>(HtmlTypeEnum.RT, AHtmlRt.class);
	
	AHtmlType<HtmlSection> SECTION = 
		new Type<HtmlSection>(HtmlTypeEnum.SECTION, AHtmlSection.class);
	
	AHtmlType<HtmlSource> SOURCE = 
		new Type<HtmlSource>(HtmlTypeEnum.SOURCE, AHtmlSource.class);
	
	AHtmlType<HtmlTime> TIME = 
		new Type<HtmlTime>(HtmlTypeEnum.TIME, AHtmlTime.class);
	
	AHtmlType<HtmlVideo> VIDEO = 
		new Type<HtmlVideo>(HtmlTypeEnum.VIDEO, AHtmlVideo.class);
	
	// end HTML 5.0 Types

	class Type<T extends HtmlElement> implements AHtmlType<T> {
		private static final Map<String, AHtmlType<?>> s_types = new HashMap<String, AHtmlType<?>>(64);

		private final HtmlTypeEnum m_type;
		private final Class<?> m_class;
		
		// Force initialization of types defined in AHtmlType interface
		static {
			try {
				Class.forName(AHtmlType.class.getName());
			} catch (ClassNotFoundException e) {
				//NOPMD should never happen
				e.printStackTrace(); 	//KEEPME
			}
		}
		
		Type(HtmlTypeEnum type, Class<?> clz) {
			m_type = type;
			m_class = clz;
			s_types.put(type.getName(), this);
		}
		
		public String getTagName() {
			return m_type.getName();
		}
		
		public Class<?> getTypeClass() {
			return m_class;
		}
		
		public Class<?> getJsNativeClass() {
			for (Class<?> itf:  m_class.getInterfaces()){
				if (Node.class.isAssignableFrom(itf)){
					return itf;
				}
			}
			throw new RuntimeException("JsNative interface is not found in:" + m_class.getName());
		}
		
		@SuppressWarnings("unchecked")
		public T create(HtmlDocument doc) {
			return (T)doc.createElement(getTagName());
		}

		public HtmlTypeEnum getType() {
			return m_type;
		}
		
		public static AHtmlType<?> get(String tagName) {
			return s_types.get(tagName.toLowerCase());
		}
		
		public static Iterable<AHtmlType<?>> valueIterable(){
			return s_iterable;
		}
		
		private static Iterable<AHtmlType<?>> s_iterable = new Iterable<AHtmlType<?>>(){
			public Iterator<AHtmlType<?>> iterator() {
				return s_types.values().iterator();
			}		
		};
	}
	
	public static class Helper {
		public static AHtmlType<?> get(String tagName) {
			return Type.get(tagName.toLowerCase());
		}
	}
}
