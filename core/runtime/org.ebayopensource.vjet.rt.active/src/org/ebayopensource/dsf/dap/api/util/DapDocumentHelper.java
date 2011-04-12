/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dap.api.util;

import org.ebayopensource.dsf.active.dom.html.AHtmlType;
import org.ebayopensource.dsf.active.dom.html.AText;
import org.ebayopensource.dsf.dap.rt.JsBase;
import org.ebayopensource.dsf.dom.DText;
import org.ebayopensource.dsf.jsnative.*;

public final class DapDocumentHelper extends JsBase {
	
	//
	// Singleton
	//
	private static final DapDocumentHelper s_instance = new DapDocumentHelper();
	private DapDocumentHelper(){}
	public static DapDocumentHelper getInstance(){
		return s_instance;
	}
	
	//
	// HTML 4.0 creators
	//
	public HtmlAnchor a(){
		return a(null);
	}
	public HtmlAnchor a(String value){
		return (HtmlAnchor)simple("a", value);
	}
	
	public HtmlAbbr abbr(){
		return abbr(null) ;
	}
	public HtmlAbbr abbr(String value){
		return (HtmlAbbr)simple("abbr", value) ;
	}
	
	public HtmlAcronym acronym(){
		return acronym(null);
	}
	public HtmlAcronym acronym(String value){
		return (HtmlAcronym)simple("acronym", value);
	}
	
	public HtmlAddress address(){
		return (HtmlAddress)document().createElement("address");
	}
	
	public HtmlApplet applet(){
		return (HtmlApplet)document().createElement("applet");
	}

	public HtmlArea area(){
		return (HtmlArea)document().createElement("area");
	}

	public HtmlB b(){
		return (HtmlB)document().createElement("b");
	}

	public HtmlBase base(){
		return (HtmlBase)document().createElement("base");
	}

	public HtmlBaseFont basefont(){
		return (HtmlBaseFont)document().createElement("basefont");
	}

	public HtmlBdo bdo(){
		return bdo(null);
	}
	public HtmlBdo bdo(String value){
		return (HtmlBdo)simple("bdo", value);
	}

	public HtmlBgSound bgsound(){
		return (HtmlBgSound)document().createElement("bgsound");
	}
	public HtmlBgSound bgsound(String src){
		HtmlBgSound r = (HtmlBgSound)document().createElement("bgsound");
		r.setSrc(src) ;
		return r ;
	}

	public HtmlBig big(){
		return (HtmlBig)document().createElement("big");
	}

	public HtmlBlockquote blockquote(){
		return (HtmlBlockquote)document().createElement("blockquote");
	}

	public HtmlBody body(){
		return (HtmlBody)document().createElement("body");
	}

	public HtmlBr br(){
		return (HtmlBr)document().createElement("br");
	}

	public HtmlButton button(){
		return (HtmlButton)document().createElement("button");
	}
	public HtmlButton button(String label) {	
		HtmlButton r = (HtmlButton)document().createElement("button");
		Text t = new AText(null, new DText(label)) ;
		r.appendChild(t) ;
		return r ;
	}
	
	public HtmlTableCaption caption() {
		return (HtmlTableCaption)document().createElement("caption");
	}

	public HtmlCenter center(){
		return (HtmlCenter)document().createElement("center");
	}

	public HtmlCite cite(){
		return cite(null) ;
	}
	public HtmlCite cite(String value){
		return (HtmlCite)simple("cite", value) ;
	}

	public HtmlCode code(){
		return code(null);
	}
	public HtmlCode code(String value){
		return (HtmlCode)simple("code", value);
	}
	
	public HtmlTableCol col(){
		return (HtmlTableCol)document().createElement("col");
	}

	public HtmlColgroup colgroup(){
		return (HtmlColgroup)document().createElement("colgroup");
	}

	public HtmlDd dd(){
		return (HtmlDd)document().createElement("dd");
	}

	public HtmlDel del(){
		return (HtmlDel)document().createElement("del");
	}

	public HtmlDfn dfn(){
		return (HtmlDfn)document().createElement("dfn");
	}

	public HtmlDir dir(){
		return (HtmlDir)document().createElement("dir");
	}

	public HtmlDiv div(){
		return (HtmlDiv)document().createElement("div");
	}

	public HtmlDl dl(){
		return (HtmlDl)document().createElement("dl");
	}

	public HtmlDt dt(){
		return (HtmlDt)document().createElement("dt");
	}

	public HtmlEm em(){
		return (HtmlEm)document().createElement("em");
	}

	public HtmlEmbed embed(){
		return (HtmlEmbed)document().createElement("embed");
	}

	public HtmlFieldSet fieldset(){
		return (HtmlFieldSet)document().createElement("fieldset");
	}

	public HtmlFont font(){
		return (HtmlFont)document().createElement("font");
	}

	public HtmlForm form(){
		return (HtmlForm)document().createElement("form");
	}

	public HtmlFrame frame(){
		return (HtmlFrame)document().createElement("frame");
	}
	public HtmlFrame frame(String... src){
		HtmlFrame frame = (HtmlFrame)document().createElement("frame");
		if (src.length > 0) {
			frame.setSrc(src[0]) ;
		}
		return frame ;
	}

	public HtmlFrameSet frameset(){
		return (HtmlFrameSet)document().createElement("frameset");
	}
	
	private HtmlHeading heading(String tag, String value) {
		return (HtmlHeading)simple(tag, value);
	}
	
	public HtmlHeading h1(){
		return h1(null);
	}
	public HtmlHeading h1(String value){
		return heading("h1", value);
	}
	
	public HtmlHeading h2(){
		return h1(null);
	}
	public HtmlHeading h2(String value){
		return heading("h2", value);
	}
	
	public HtmlHeading h3(){
		return h3(null);
	}
	public HtmlHeading h3(String value){
		return heading("h3", value);
	}
	
	public HtmlHeading h4(){
		return h4(null);
	}
	public HtmlHeading h4(String value){
		return heading("h4", value);
	}
	
	public HtmlHeading h5(){
		return h5(null);
	}
	public HtmlHeading h5(String value){
		return heading("h5", value);
	}
	
	public HtmlHeading h6(){
		return h6(null);
	}
	public HtmlHeading h6(String value){
		return heading("h6", value);
	}
	
	public HtmlHead head(){
		return (HtmlHead)document().createElement("head");
	}

	public HtmlHr hr(){
		return (HtmlHr)document().createElement("hr");
	}

	//TODO: can you create Html element?
	public HtmlHtml html(){
		return (HtmlHtml)document().createElement("html");
	}

	public HtmlI i(){
		return i(null);
	}
	public HtmlI i(String value){
		return (HtmlI)simple("i", value);
	}

	public HtmlIFrame iframe(){
		return (HtmlIFrame)document().createElement("iframe");
	}

	public HtmlImage img(){
		return (HtmlImage)document().createElement("img");
	}

	public HtmlInput input(String value) {
		return (HtmlInput)simple("ins", value) ;
	}
	public HtmlInput input(){
		return (HtmlInput)document().createElement("input");
	}

	public HtmlIns ins(){
		return ins(null) ;
	}
	public HtmlIns ins(String value){
		return (HtmlIns)simple("ins", value) ;
	}

	public HtmlIsIndex isindex(){
		return (HtmlIsIndex)document().createElement("isindex");
	}

	public HtmlKbd kbd(){
		return kbd(null);
	}
	public HtmlKbd kbd(String value){
		return (HtmlKbd)simple("kbd", value);
	}

	public HtmlLabel label(){
		return label(null);
	}
	public HtmlLabel label(String value){
		return (HtmlLabel)simple("label", value);
	}

	public HtmlLegend legend(){
		return legend(null);
	}
	public HtmlLegend legend(String value){
		return (HtmlLegend)simple("legend", value);
	}

	public HtmlLi li(){
		return li(null);
	}
	public HtmlLi li(String value){
		return (HtmlLi)simple("li", value);
	}

	public HtmlLink link(){
		return (HtmlLink)document().createElement("link");
	}

	public HtmlMap map(){
		return (HtmlMap)document().createElement("map");
	}

	public HtmlMarquee marquee(){
		return (HtmlMarquee)document().createElement("marquee");
	}

	public HtmlMenu menu(){
		return (HtmlMenu)document().createElement("menu");
	}

	public HtmlMeta meta(){
		return (HtmlMeta)document().createElement("meta");
	}

	public HtmlMod mod(){
		return (HtmlMod)document().createElement("mod");
	}

	public HtmlNoBr nobr(){
		return (HtmlNoBr)document().createElement("nobr");
	}

	public HtmlNoFrames noframes(){
		return (HtmlNoFrames)document().createElement("noframes");
	}

	public HtmlObject object(){
		return (HtmlObject)document().createElement("object");
	}

	public HtmlOl ol(){
		return (HtmlOl)document().createElement("ol");
	}
	public HtmlOptGroup optgroup(){
		return (HtmlOptGroup)document().createElement("optgroup");
	}
	public HtmlOptGroup optgroup(String label){
		HtmlOptGroup r = optgroup() ;
		r.setLabel(label) ;
		return r ;
	}

	public HtmlOption option() {
		return (HtmlOption)document().createElement("option");
	}
	public HtmlOption option(final String text) {
		HtmlOption option = (HtmlOption)document().createElement("option");
		option.setText(text) ;
		return option ;
	}
	public HtmlOption option(final String text, final String value) {
		HtmlOption option = option(text) ;
		option.setValue(value) ;
		return option ;
	}

	public HtmlParagraph p(){
		return p(null);
	}
	public HtmlParagraph p(String value){
		return (HtmlParagraph)simple("p", value);
	}

	public HtmlParam param(){
		return (HtmlParam)document().createElement("param");
	}

	public HtmlPre pre(){
		return pre(null);
	}
	public HtmlPre pre(String value){
		return (HtmlPre)simple("pre", value);
	}

	public HtmlQuote q(){
		return q(null);
	}
	public HtmlQuote q(String value){
		return (HtmlQuote)simple("q", value);
	}

//	public HtmlRt_IE rt_ie(){
//		return rt_ie(null);
//	}
//	public HtmlRt_IE rt_ie(String value){
//		return (HtmlRt_IE)simple("rt", value);
//	}
//
//	public HtmlRuby_IE ruby_ie(){
//		return ruby_ie(null);
//	}
//	public HtmlRuby_IE ruby_ie(String value){
//		return (HtmlRuby_IE)simple("ruby", value);
//	}

	public HtmlS s(){
		return s(null);
	}
	public HtmlS s(String value){
		return (HtmlS)simple("s", value);
	}

	public HtmlSamp samp(){
		return samp(null);
	}
	public HtmlSamp samp(String value){
		return (HtmlSamp)simple("samp", value);
	}

	public HtmlScript script(){
		return (HtmlScript)document().createElement("script");
	}

	public HtmlSelect select(){
		return (HtmlSelect)document().createElement("select");
	}

	public HtmlSmall small(){
		return small(null);
	}
	public HtmlSmall small(String value){
		return (HtmlSmall)simple("small", value);
	}

	public HtmlSpan span(){
		return (HtmlSpan)document().createElement("span");
	}
	public HtmlSpan span(String className){
		HtmlSpan r = span() ;
		r.setClassName(className) ;
		return r; 
	}

	public HtmlStrike strike(){
		return strike(null);
	}
	public HtmlStrike strike(String value){
		return (HtmlStrike)simple("strike", value);
	}

	public HtmlStrong strong(){
		return strong(null);
	}
	public HtmlStrong strong(String value){
		return (HtmlStrong)simple("strong", value);
	}

	public HtmlStyle style(){
		return (HtmlStyle)document().createElement("style");
	}

	public HtmlSub sub(){
		return sub(null);
	}
	public HtmlSub sub(String value){
		return (HtmlSub)simple("sub", value);
	}

	public HtmlSup sup(){
		return sup(null);
	}
	public HtmlSup sup(String value){
		return (HtmlSup)simple("sup", value);
	}

	public HtmlTable table(){
		return (HtmlTable)document().createElement("table");
	}

	public HtmlTextArea textarea(){
		return textarea(null);
	}
	public HtmlTextArea textarea(String value){
		return (HtmlTextArea)simple("textarea", value);
	}
	
	public HtmlTableSection tbody(){
		return (HtmlTableSection)document().createElement("tbody");
	}
	
	public HtmlTableSection tfoot(){
		return (HtmlTableSection)document().createElement("tfoot");
	}
	
	public HtmlTableCell td(){
		return (HtmlTableCell)document().createElement("td");
	}

	public HtmlTh th(){
		return (HtmlTh)document().createElement("th");
	}
	
	public HtmlTableSection thead(){
		return (HtmlTableSection)document().createElement("thead");
	}

	public HtmlTitle title(){
		return title(null);
	}
	public HtmlTitle title(String value){
		return (HtmlTitle)simple("title", value);
	}

	public HtmlTableRow tr(){
		return (HtmlTableRow)document().createElement("tr");
	}

	public HtmlTt tt(){
		return tt(null);
	}
	public HtmlTt tt(String value){
		return (HtmlTt)simple("tt", value);
	}

	public HtmlU u(){
		return u(null);
	}
	public HtmlU u(String value){
		return (HtmlU)simple("u", value);
	}

	public HtmlUl ul(){
		return (HtmlUl)document().createElement("ul");
	}

	public HtmlVar var(){
		return var(null);
	}
	public HtmlVar var(String value){
		return (HtmlVar)simple("var", value);
	}

	public HtmlWbr wbr(){
		return (HtmlWbr)document().createElement("wbr");
	}
	
	//
	// HTML 4.0 Type specific getters to keep users from having to cast
	//
	public HtmlAnchor getA(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlAnchor){
			return (HtmlAnchor)e;
		}
		return null;
	}
	public HtmlAbbr getAbbr(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlAbbr){
			return (HtmlAbbr)e;
		}
		return null;
	}
	public HtmlAcronym getAcronym(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlAcronym){
			return (HtmlAcronym)e;
		}
		return null;
	}
	public HtmlAddress getAddress(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlAddress){
			return (HtmlAddress)e;
		}
		return null;
	}
	public HtmlApplet getApplet(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlApplet){
			return (HtmlApplet)e;
		}
		return null;
	}
	public HtmlArea getArea(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlArea){
			return (HtmlArea)e;
		}
		return null;
	}
	public HtmlB getB(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlB){
			return (HtmlB)e;
		}
		return null;
	}
	public HtmlBase getBase(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlBase){
			return (HtmlBase)e;
		}
		return null;
	}
	public HtmlBaseFont getBaseFont(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlBaseFont){
			return (HtmlBaseFont)e;
		}
		return null;
	}
	public HtmlBdo getBdo(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlBdo){
			return (HtmlBdo)e;
		}
		return null;
	}
	public HtmlBgSound getBgSound(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlBgSound){
			return (HtmlBgSound)e;
		}
		return null;
	}
	public HtmlBig getBig(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlBig){
			return (HtmlBig)e;
		}
		return null;
	}
	public HtmlBlockquote getBlockquote(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlBlockquote){
			return (HtmlBlockquote)e;
		}
		return null;
	}
	public HtmlBody getBody(){
		return document().getBody();
	}
	public HtmlBr getBr(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlBr){
			return (HtmlBr)e;
		}
		return null;
	}
	public HtmlButton getButton(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlButton){
			return (HtmlButton)e;
		}
		return null;
	}
	public HtmlTableCaption getCaption(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlTableCaption){
			return (HtmlTableCaption)e;
		}
		return null;
	}
	public HtmlCenter getCenter(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlCenter){
			return (HtmlCenter)e;
		}
		return null;
	}
	public HtmlCite getCite(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlCite){
			return (HtmlCite)e;
		}
		return null;
	}
	public HtmlCode getCode(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlCode){
			return (HtmlCode)e;
		}
		return null;
	}
	public HtmlTableCol getCol(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlTableCol){
			return (HtmlTableCol)e;
		}
		return null;
	}
	public HtmlColgroup getColgroup(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlColgroup){
			return (HtmlColgroup)e;
		}
		return null;
	}
	public HtmlDd getDd(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlDd){
			return (HtmlDd)e;
		}
		return null;
	}
	public HtmlDel getDel(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlDel){
			return (HtmlDel)e;
		}
		return null;
	}
	public HtmlDfn getDfn(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlDfn){
			return (HtmlDfn)e;
		}
		return null;
	}
	public HtmlDir getDir(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlDir){
			return (HtmlDir)e;
		}
		return null;
	}
	public HtmlDiv getDiv(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlDiv){
			return (HtmlDiv)e;
		}
		return null;
	}
	public HtmlDl getDl(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlDl){
			return (HtmlDl)e;
		}
		return null;
	}
	public HtmlDt getDt(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlDt){
			return (HtmlDt)e;
		}
		return null;
	}
	public HtmlEm getEm(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlEm){
			return (HtmlEm)e;
		}
		return null;
	}
	public HtmlEmbed getEmbed(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlEmbed){
			return (HtmlEmbed)e;
		}
		return null;
	}
	public HtmlFieldSet getFieldSet(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlFieldSet){
			return (HtmlFieldSet)e;
		}
		return null;
	}
	public HtmlFont getFont(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlFont){
			return (HtmlFont)e;
		}
		return null;
	}
	public HtmlForm getForm(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlForm){
			return (HtmlForm)e;
		}
		return null;
	}
	public HtmlFrame getFrame(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlFrame){
			return (HtmlFrame)e;
		}
		return null;
	}
	public HtmlFrameSet getFrameset(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlFrameSet){
			return (HtmlFrameSet)e;
		}
		return null;
	}
	public HtmlHeading getH1(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlHeading){
			return (HtmlHeading)e;
		}
		return null;
	}
	public HtmlHeading getH2(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlHeading){
			return (HtmlHeading)e;
		}
		return null;
	}
	public HtmlHeading getH3(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlHeading){
			return (HtmlHeading)e;
		}
		return null;
	}
	public HtmlHeading getH4(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlHeading){
			return (HtmlHeading)e;
		}
		return null;
	}
	public HtmlHeading getH5(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlHeading){
			return (HtmlHeading)e;
		}
		return null;
	}
	public HtmlHeading getH6(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlHeading){
			return (HtmlHeading)e;
		}
		return null;
	}
	public HtmlHead getHead(){
		NodeList list = document().getElementsByTagName("head");
		if (list != null && list.getLength()>0){
			HtmlElement e = (HtmlElement)list.item(0);
			if (e instanceof HtmlHead){
				return (HtmlHead)e;
			}
		}
		return null;
	}
	public HtmlHr getHr(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlHr){
			return (HtmlHr)e;
		}
		return null;
	}
	//TODO: why do we need a method for getting the Html?
	public HtmlHtml getHtml(){
		NodeList list = document().getElementsByTagName("html");
		if (list != null && list.getLength()>0){
			HtmlElement e = (HtmlElement)list.item(0);
			if (e instanceof HtmlHtml){
				return (HtmlHtml)e;
			}
		}
		return null;
	}
	public HtmlI getI(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlI){
			return (HtmlI)e;
		}
		return null;
	}
	public HtmlIFrame getIFrame(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlIFrame){
			return (HtmlIFrame)e;
		}
		return null;
	}
	public HtmlImage getImg(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlImage){
			return (HtmlImage)e;
		}
		return null;
	}
	public HtmlInput getInput(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlInput){
			return (HtmlInput)e;
		}
		return null;
	}
	public HtmlIns getIns(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlIns){
			return (HtmlIns)e;
		}
		return null;
	}
	public HtmlIsIndex getIsIndex(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlIsIndex){
			return (HtmlIsIndex)e;
		}
		return null;
	}
	public HtmlKbd getKbd(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlKbd){
			return (HtmlKbd)e;
		}
		return null;
	}
	public HtmlLabel getLabel(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlLabel){
			return (HtmlLabel)e;
		}
		return null;
	}
	public HtmlLegend getLegend(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlLegend){
			return (HtmlLegend)e;
		}
		return null;
	}
	public HtmlLi getLi(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlLi){
			return (HtmlLi)e;
		}
		return null;
	}
	public HtmlLink getLink(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlLink){
			return (HtmlLink)e;
		}
		return null;
	}
	public HtmlMap getMap(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlMap){
			return (HtmlMap)e;
		}
		return null;
	}
	public HtmlMarquee getMarquee(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlMarquee){
			return (HtmlMarquee)e;
		}
		return null;
	}
	public HtmlMenu getMenu(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlMenu){
			return (HtmlMenu)e;
		}
		return null;
	}
	//you cannot get a particular Meta in JS by id
	//you can do so by name
	public HtmlMeta getMeta(String name){
		NodeList list = document().getElementsByTagName("meta");
		if(list==null){
			return null;
		}
		else{
			for(int i = 0; i < list.getLength(); i++){
				HtmlMeta node = (HtmlMeta)list.item(i);
				if(node.getName().equals(name)){
					return node;
				}
			}
		}
		return null;
	}
	public HtmlMod getMod(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlMod){
			return (HtmlMod)e;
		}
		return null;
	}
	public HtmlNoBr getNoBr(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlNoBr){
			return (HtmlNoBr)e;
		}
		return null;
	}
	public HtmlNoFrames getNoFrames(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlNoFrames){
			return (HtmlNoFrames)e;
		}
		return null;
	}
	public HtmlObject getObject(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlObject){
			return (HtmlObject)e;
		}
		return null;
	}
	public HtmlOl getOl(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlOl){
			return (HtmlOl)e;
		}
		return null;
	}
	public HtmlOptGroup getOptGroup(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlOptGroup){
			return (HtmlOptGroup)e;
		}
		return null;
	}
	public HtmlOption getOption(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlOption){
			return (HtmlOption)e;
		}
		return null;
	}
	public HtmlParagraph getP(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlParagraph){
			return (HtmlParagraph)e;
		}
		return null;
	}
	public HtmlParam getParam(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlParam){
			return (HtmlParam)e;
		}
		return null;
	}
	public HtmlPre getPre(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlPre){
			return (HtmlPre)e;
		}
		return null;
	}
	public HtmlQuote getQ(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlQuote){
			return (HtmlQuote)e;
		}
		return null;
	}
//	public HtmlRt_IE getRt_IE(String id){
//		HtmlElement e = document().getElementById(id);
//		if (e instanceof HtmlRt_IE){
//			return (HtmlRt_IE)e;
//		}
//		return null;
//	}
//	public HtmlRuby_IE getRuby_IE(String id){
//		HtmlElement e = document().getElementById(id);
//		if (e instanceof HtmlRuby_IE){
//			return (HtmlRuby_IE)e;
//		}
//		return null;
//	}
	public HtmlS getS(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlS){
			return (HtmlS)e;
		}
		return null;
	}
	public HtmlSamp getSamp(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlSamp){
			return (HtmlSamp)e;
		}
		return null;
	}
	public HtmlScript getScript(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlScript){
			return (HtmlScript)e;
		}
		return null;
	}
	public HtmlSelect getSelect(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlSelect){
			return (HtmlSelect)e;
		}
		return null;
	}
	public HtmlSmall getSmall(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlSmall){
			return (HtmlSmall)e;
		}
		return null;
	}
	public HtmlSpan getSpan(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlSpan){
			return (HtmlSpan)e;
		}
		return null;
	}
	public HtmlStrike getStrike(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlStrike){
			return (HtmlStrike)e;
		}
		return null;
	}
	public HtmlStrong getStrong(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlStrong){
			return (HtmlStrong)e;
		}
		return null;
	}
	public HtmlStyle getStyle(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlStyle){
			return (HtmlStyle)e;
		}
		return null;
	}
	public HtmlSub getSub(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlSub){
			return (HtmlSub)e;
		}
		return null;
	}
	public HtmlSup getSup(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlSup){
			return (HtmlSup)e;
		}
		return null;
	}
	public HtmlTable getTable(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlTable){
			return (HtmlTable)e;
		}
		return null;
	}
	public HtmlTextArea getTextArea(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlTextArea){
			return (HtmlTextArea)e;
		}
		return null;
	}
	public HtmlTableSection getTBody(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlTableSection){
			return (HtmlTableSection)e;
		}
		return null;
	}
	public HtmlTableSection getTFoot(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlTableSection){
			return (HtmlTableSection)e;
		}
		return null;
	}
	public HtmlTableCell getTd(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlTableCell){
			return (HtmlTableCell)e;
		}
		return null;
	}
	public HtmlTh getTh(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlTh){
			return (HtmlTh)e;
		}
		return null;
	}
	public HtmlTableSection getTHead(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlTableSection){
			return (HtmlTableSection)e;
		}
		return null;
	}
	
	public HtmlTitle getTitle(){
		NodeList list = document().getElementsByTagName("title");
		if (list != null && list.getLength()>0){
			HtmlElement e = (HtmlElement)list.item(0);
			if (e instanceof HtmlTitle){
				return (HtmlTitle)e;
			}
		}
		return null;
	}
	public HtmlTableRow getTr(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlTableRow){
			return (HtmlTableRow)e;
		}
		return null;
	}
	public HtmlTt getTt(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlTt){
			return (HtmlTt)e;
		}
		return null;
	}
	public HtmlU getU(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlU){
			return (HtmlU)e;
		}
		return null;
	}
	public HtmlUl getUl(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlUl){
			return (HtmlUl)e;
		}
		return null;
	}
	public HtmlVar getVar(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlVar){
			return (HtmlVar)e;
		}
		return null;
	}
	public HtmlWbr getWbr(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlWbr){
			return (HtmlWbr)e;
		}
		return null;
	}
	
	//
	// start HTML 5.0 creators
	//
	public HtmlArticle article(){
		return (HtmlArticle)document().createElement("article");
	}
	
	public HtmlAside aside(){
		return (HtmlAside)document().createElement("aside");
	}
	
	public HtmlAudio audio(){
		return (HtmlAudio)document().createElement("audio");
	}
	
	public HtmlBb bb(){
		return (HtmlBb)document().createElement("bb");
	}
	
	public HtmlCanvas canvas(){
		return (HtmlCanvas)document().createElement("canvas");
	}
	
	public HtmlCommand command(){
		return (HtmlCommand)document().createElement("command");
	}
	
	public HtmlDataList dataList() {
		return (HtmlDataList)document().createElement("datalist");
	}
	
	public HtmlDetails details(){
		return (HtmlDetails)document().createElement("details");
	}
	
	public HtmlDialog dialog(){
		return (HtmlDialog)document().createElement("dialog");
	}
	
	public HtmlFigure figure(){
		return (HtmlFigure)document().createElement("figure");
	}
	
	public HtmlFooter footer(){
		return (HtmlFooter)document().createElement("footer");
	}
	
	public HtmlHeader header(){
		return (HtmlHeader)document().createElement("header");
	}
	
	public HtmlKeyGen keygen(){
		return (HtmlKeyGen)document().createElement("keygen");
	}
	
	public HtmlMark mark(){
		return (HtmlMark)document().createElement("mark");
	}
	
	public HtmlMeter meter(){
		return (HtmlMeter)document().createElement("meter");
	}
	
	public HtmlNav nav(){
		return (HtmlNav)document().createElement("nav");
	}
	
	public HtmlOutput output(){
		return (HtmlOutput)document().createElement("output");
	}
	
	public HtmlProgress progress(){
		return (HtmlProgress)document().createElement("progress");
	}
	
	public HtmlRuby ruby(){
		return (HtmlRuby)document().createElement("ruby");
	}
	
	public HtmlRp rp(){
		return (HtmlRp)document().createElement("rp");
	}
	
	public HtmlRt rt(){
		return (HtmlRt)document().createElement("rt");
	}
	
	public HtmlSection section(){
		return (HtmlSection)document().createElement("section");
	}
	
	public HtmlSource source(){
		return (HtmlSource)document().createElement("source");
	}
	
	public HtmlTime time(){
		return (HtmlTime)document().createElement("time");
	}
	
	public HtmlVideo video(){
		return (HtmlVideo)document().createElement("video");
	}	
	//
	// end HTML 5.0 creators
	//
	
	//
	// start HTML 5.0 getters
	//
	public HtmlArticle getArticle(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlArticle){
			return (HtmlArticle)e;
		}
		return null;
	}
	
	public HtmlAside getAside(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlAside){
			return (HtmlAside)e;
		}
		return null;
	}
	
	public HtmlAudio getAudio(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlAudio){
			return (HtmlAudio)e;
		}
		return null;
	}
	
	public HtmlBb getBb(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlBb){
			return (HtmlBb)e;
		}
		return null;
	}
	
	public HtmlCanvas getCanvas(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlCanvas){
			return (HtmlCanvas)e;
		}
		return null;
	}
	
	public HtmlCommand getCommand(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlCommand){
			return (HtmlCommand)e;
		}
		return null;
	}
	
	public HtmlDataGrid getDataGrid(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlDataGrid){
			return (HtmlDataGrid)e;
		}
		return null;
	}
	
	public HtmlDataList getDataList(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlDataList){
			return (HtmlDataList)e;
		}
		return null;
	}
	
	public HtmlDetails getDetails(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlDetails){
			return (HtmlDetails)e;
		}
		return null;
	}
	
	public HtmlDialog getDialog(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlDialog){
			return (HtmlDialog)e;
		}
		return null;
	}
	
	public HtmlFigure getFigure(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlFigure){
			return (HtmlFigure)e;
		}
		return null;
	}
	
	public HtmlFooter getFooter(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlFooter){
			return (HtmlFooter)e;
		}
		return null;
	}
	
	public HtmlHeader getHeader(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlHeader){
			return (HtmlHeader)e;
		}
		return null;
	}
	
	public HtmlKeyGen getKeyGen(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlKeyGen){
			return (HtmlKeyGen)e;
		}
		return null;
	}
	
	public HtmlMark getMark(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlMark){
			return (HtmlMark)e;
		}
		return null;
	}
	
	public HtmlMeter getMeter(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlMeter){
			return (HtmlMeter)e;
		}
		return null;
	}
	
	public HtmlNav getNav(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlNav){
			return (HtmlNav)e;
		}
		return null;
	}
	
	public HtmlOutput getOutput(String id){
		HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlOutput){
			return (HtmlOutput)e;
		}
		return null;
	}
	
	public HtmlProgress getProgress(final String id){
		final HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlProgress){
			return (HtmlProgress)e;
		}
		return null;
	}
	
	public HtmlRuby getRuby(final String id){
		final HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlRuby){
			return (HtmlRuby)e;
		}
		return null;
	}
	
	
	public HtmlRp getRp(final String id){
		final HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlRp){
			return (HtmlRp)e;
		}
		return null;
	}
	
	
	public HtmlRt getRt(final String id){
		final HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlRt){
			return (HtmlRt)e;
		}
		return null;
	}
	
	
	public HtmlSection getSection(final String id){
		final HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlSection){
			return (HtmlSection)e;
		}
		return null;
	}
	
	
	public HtmlSource getSource(final String id){
		final HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlSource){
			return (HtmlSource)e;
		}
		return null;
	}
	
	
	public HtmlTime getTime(final String id){
		final HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlTime){
			return (HtmlTime)e;
		}
		return null;
	}
	
	
	public HtmlVideo getVideo(final String id){
		final HtmlElement e = document().getElementById(id);
		if (e instanceof HtmlVideo){
			return (HtmlVideo)e;
		}
		return null;
	}
	
	//
	// end HTML 5.0 getters
	//
	
	// Other API
	//
	public  <T extends HtmlElement> T getElement(final String id){
		return (T)document().getElementById(id);
	}
	
	@Deprecated
	public  <T extends HtmlElement> T getElement(final String id, final AHtmlType<T> type){
		return (T)document().getElementById(id);
	}
	
	public Text createTextNode(final String text) {
		return document().createTextNode(text);
	}	
	
//	public HtmlOption createOption(
//			final String text, 
//			final String value) {
//		
//		return createOption(text, value, false, false);
//	}	
//	
//	public HtmlOption createOption(
//			final String text, 
//			final int value) {
//		
//		return createOption(text, String.valueOf(value), false, false);
//	}	
//	
//	public HtmlOption createOption(
//			final String text, 
//			final String value, 
//			boolean isDefaultSelected, 
//			boolean isSelected) {
//		
//		HtmlOption option = createElement(AHtmlType.OPTION);
//		option.setText(text);
//		option.setValue(value);
//		option.setDefaultSelected(isDefaultSelected);
//		option.setSelected(isSelected);
//		
//		return option;
//	}	
//	
//	public HtmlSelect clearOptions(final HtmlSelect selectElement){
//		if (selectElement == null || selectElement.getLength() == 0){
//			return selectElement;
//		}
//		for (int i = selectElement.getLength()-1; i>=0; i--){
//			selectElement.removeByIndex(i);
//		}
//		return selectElement;
//	}
	
	//
	// Private
	//
	private <T extends HtmlElement> T createElement(AHtmlType<T> type) {
		return type.create(document());
	}
	
	private HtmlElement simple(String tag, String value) {
		HtmlElement r = document().createElement(tag);
		if (value != null) {
			r.setInnerHTML(value) ;
		}
		return r ;
	}
}
