/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsnative.anno;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ebayopensource.dsf.jsnative.*;
import org.ebayopensource.dsf.jsnative.events.CompositionEvent;
import org.ebayopensource.dsf.jsnative.events.CustomEvent;
import org.ebayopensource.dsf.jsnative.events.DataTransfer;
import org.ebayopensource.dsf.jsnative.events.DocumentEvent;
import org.ebayopensource.dsf.jsnative.events.DragEvent;
import org.ebayopensource.dsf.jsnative.events.Event;
import org.ebayopensource.dsf.jsnative.events.EventException;
import org.ebayopensource.dsf.jsnative.events.EventListener;
import org.ebayopensource.dsf.jsnative.events.EventTarget;
import org.ebayopensource.dsf.jsnative.events.KeyboardEvent;
import org.ebayopensource.dsf.jsnative.events.LSLoadEvent;
import org.ebayopensource.dsf.jsnative.events.LSProgressEvent;
import org.ebayopensource.dsf.jsnative.events.MouseEvent;
import org.ebayopensource.dsf.jsnative.events.TextEvent;
import org.ebayopensource.dsf.jsnative.events.UIEvent;
import org.ebayopensource.dsf.jsnative.file.Blob;
import org.ebayopensource.dsf.jsnative.file.File;
import org.ebayopensource.dsf.jsnative.file.FileList;

public class JsNativeMeta {
	
	private static Map<String, Class<?>> s_jsEcmaTypes = new HashMap<String, Class<?>>();
	private static Map<String, Class<?>> s_jsBrowserTypes = new HashMap<String, Class<?>>();
	private static Map<String, Class<?>> s_aliasMap = new HashMap<String, Class<?>>();
	private static Map<String, String> s_classToAliasMap = new HashMap<String, String>();
	
	static {
		
		// global objects
		addGlobalObjects();
				
		// Window, etc
		addClientSideObjects();
		
		// DOM HTML
		addDomHtmlObjects();
		
		// DOM events
		addDomEventObjects();
	}
	
	/**
	 * Answers if a given type is JavaScript native type
	 * @param name simple name or fully qualified name of a class
	 * @return
	 */
	public static boolean isJsNativeType(String name) {
		return JsNativeMeta.getClass(name) != null;
	}
	
	/**
	 * Return JsNative class for given type name
	 * @param name simple name or fully qualified name of a class
	 * @return instance of Class or <code>null</code>
	 */
	public static Class<?> getClass(String name) {
		// look in alias names first
		Class<?> c = s_aliasMap.get(name);
		if (c != null) {
			return c;
		}
		c = getEcmaJsObject(name);
		if (c != null) {
			return c;
		}
		return getBrowserJsObject(name);
	}
	
	/**
	 * Return JsNative class for given type name
	 * @param fully qualified name of a class
	 * @return instance of Class or <code>null</code>
	 */
	public static Class<?> getNativeClass(String nativeTypeName) {
		if (nativeTypeName == null) return null;
		
		for (Class<?> itm : s_jsEcmaTypes.values()) {
			if (nativeTypeName.equals(itm.getName())) {
				return itm;
			}
		}
		
		for (Class<?> itm : s_jsBrowserTypes.values()) {
			if (nativeTypeName.equals(itm.getName())) {
				return itm;
			}
		}

		return null;
		
	}

	/**
	 * Return JsNative simple name for given type full name
	 * @param name fully qualified name of a class
	 * @return alias of the type or <code>null</code>
	 */
	public static String getNativeTypeAlias(String typeName) {
		// look in alias
		return s_classToAliasMap.get(typeName);
	}
	
	/**
	 * Return JsNative class for given browser javascript type name
	 * @param name simple name or fully qualified name of a class
	 * @return instance of Class or <code>null</code>
	 */
	public static Class<?> getBrowserJsObject(String name) {
		return s_jsBrowserTypes.get(shortName(name));
	}

	/**
	 * Return JsNative class for given Ecma script type name
	 * @param name simple name or fully qualified name of a class
	 * @return instance of Class or <code>null</code>
	 */
	public static Class<?> getEcmaJsObject(String name) {
		return s_jsEcmaTypes.get(shortName(name));
	}
	
	public static Collection<Class<?>> getAllClasses() {
		List<Class<?>> list = new ArrayList<Class<?>>(s_jsEcmaTypes.values());
		list.addAll(s_jsBrowserTypes.values());
		return list;
	}
	
	private static String shortName(String name) {
		int i = name.lastIndexOf('.');
		if ( i == -1) {
			return name;
		}
		return name.substring(i+1);
	}

	private static void add(Class<?> c) {
		s_jsEcmaTypes.put(c.getSimpleName(), c);
		Alias alias = (Alias) c.getAnnotation(Alias.class);
		if (alias != null) {
			s_aliasMap.put(alias.value(), c);
			s_classToAliasMap.put(c.getName(), alias.value());
		}
	}
	
	private static void addbt(Class<?> c) {
		s_jsBrowserTypes.put(c.getSimpleName(), c);
		Alias alias = (Alias) c.getAnnotation(Alias.class);
		if (alias != null) {
			s_aliasMap.put(alias.value(), c);
			s_classToAliasMap.put(c.getName(), alias.value());
		}
	}

	private static void addDomEventObjects() {
		addbt(Event.class);
		addbt(EventTarget.class);
		addbt(EventListener.class);
		addbt(DocumentEvent.class);
		addbt(EventException.class);
		addbt(LSLoadEvent.class);
		addbt(LSProgressEvent.class);
		addbt(MouseEvent.class);
		addbt(KeyboardEvent.class);
		addbt(TextEvent.class);
		addbt(UIEvent.class);
		addbt(CompositionEvent.class);
		addbt(CustomEvent.class);
		addbt(DragEvent.class);
		
		
		// file api
		addbt(DataTransfer.class);
		addbt(FileList.class);
		addbt(File.class);
		addbt(Blob.class);
	}

	private static void addDomHtmlObjects() {
		addbt(Attr.class);
		addbt(Node.class);
		addbt(NodeList.class);
		addbt(CharacterData.class);
		addbt(Text.class);
		addbt(TextRange.class);
		addbt(Element.class);
		addbt(Document.class);
		addbt(Comment.class);
		addbt(CDATASection.class);
		addbt(Notation.class);
		addbt(NameList.class);
		addbt(NamedNodeMap.class);
		addbt(Entity.class);
		addbt(DOMStringList.class);
		addbt(DOMLocator.class);
		addbt(DOMImplementationSource.class);
		addbt(DOMImplementationList.class);
		addbt(DOMImplementation.class);
		addbt(HtmlDOMImplementation.class);
		addbt(DOMErrorHandler.class);
		addbt(DOMError.class);
		addbt(TypeInfo.class);
		addbt(ProcessingInstruction.class);
		addbt(DOMConfiguration.class);
		addbt(DocumentType.class);
		addbt(EntityReference.class);
		addbt(UserDataHandler.class);
		addbt(DocumentFragment.class);
		addbt(DOMException.class);
		addbt(XMLHttpRequest.class);
		addbt(XDomainRequest.class);
		addbt(ActiveXObject.class);
		addbt(Option.class);
		addbt(Image.class);
		
		// HtmlDocument
		addbt(HtmlDocument.class);
		// HtmlElement
		addbt(HtmlElement.class);
		// HtmlUnknown
		addbt(HtmlUnknown.class);
		
		addbt(HtmlCollection.class);
		addbt(HtmlOptionsCollection.class);
		addbt(HtmlElementStyle.class);
		addbt(ElementView.class);
		addbt(TextRectangle.class);
		addbt(TextRectangleList.class);
		
		addbt(TextTrack.class);
		addbt(TrackList.class);
		addbt(TimeRanges.class);
		addbt(TextTrackCue.class);
		addbt(BehaviorUrnsCollection.class);
		addbt(MutableTextTrack.class);
		addbt(MultipleTrackList.class);
		addbt(MediaError.class);
		addbt(MediaController.class);
		addbt(ExclusiveTrackList.class);
		
		addbt(MediaList.class);
		addbt(StyleSheet.class);
		addbt(CssStyleSheet.class);
		addbt(StyleSheetList.class);
		addbt(CssRule.class);
		addbt(CssStyleRule.class);
		addbt(CssPageRule.class);
		addbt(CssImportRule.class);
		addbt(CssFontFaceRule.class);
		addbt(CssCharsetRule.class);
		addbt(CssMediaRule.class);
		addbt(CssStyleDeclaration.class);
		addbt(CSSRuleList.class);
		addbt(CssValue.class);
		
		addbt(Range.class);
		addbt(DocumentRange.class);
		// IE specific
		addbt(HtmlDlgSafeHelper.class);
		addbt(DomParser.class);
		addbt(DomParseFilter.class);
		addbt(DomInput.class);
		addbt(DomInputStream.class);
		
		// HTML Elements
		loadHtmlElements();
		
	}
	
	private static void loadHtmlElements(){
//		Iterable<IActiveElementType> iter = AHtmlType.Type.valueIterable2();
//		for (IActiveElementType htmlType : iter) {
//			Class<?> htmlClass = htmlType.getTypeClass();
//			Class<?>[] interfaces = htmlClass.getInterfaces();
//			for (Class<?> inf : interfaces) {
//				if (inf.getPackage().getName().startsWith(
//						"org.ebayopensource.dsf.jsnative")) {
//					addbt(inf);
//				}
//			}
//		}
		
/* This will generate the following list
Iterable<AHtmlType<?>> iter = AHtmlType.Type.valueIterable();
for (AHtmlType htmlType : iter) {
	Class<?> htmlClass = htmlType.getTypeClass();
	Class<?>[] interfaces = htmlClass.getInterfaces();
	Set set = new HashSet() ;
	for (Class<?> inf : interfaces) {
		if (inf.getPackage().getName().startsWith(
				"org.ebayopensource.dsf.jsnative"))
		{
			if (!set.contains(inf)) 
				set.add(inf) ;
				System.out.println("addbt(" + inf.getSimpleName() + ".class);") ;
		}
		}
	}
	}
}
*/
		addbt(HtmlKeyGen.class);
		addbt(HtmlColgroup.class);
		addbt(HtmlNoScript.class);
		addbt(HtmlDataGrid.class);
		addbt(HtmlCenter.class);
		addbt(HtmlKbd.class);
		addbt(HtmlTime.class);
		addbt(HtmlMeter.class);
		addbt(HtmlButton.class);
		addbt(HtmlHeading.class);
		addbt(HtmlHeading.class);
		addbt(HtmlHeading.class);
		addbt(HtmlOl.class);
		addbt(HtmlHeading.class);
		addbt(HtmlVideo.class);
		addbt(HtmlHeading.class);
		addbt(HtmlOption.class);
		addbt(HtmlHeading.class);
		addbt(HtmlForm.class);
		addbt(HtmlMenu.class);
		addbt(HtmlMod.class);
		addbt(HtmlTable.class);
		addbt(HtmlHeader.class);
		addbt(HtmlCite.class);
		addbt(HtmlTextArea.class);
		addbt(HtmlNoFrames.class);
		addbt(HtmlBig.class);
		addbt(HtmlWbr.class);
		addbt(HtmlSmall.class);
		addbt(HtmlDt.class);
		addbt(HtmlOptGroup.class);
		addbt(HtmlBdo.class);
		addbt(HtmlParam.class);
		addbt(HtmlLink.class);
		addbt(HtmlAside.class);
		addbt(HtmlMeta.class);
		addbt(HtmlDd.class);
		addbt(HtmlStrike.class);
		addbt(HtmlTitle.class);
		addbt(HtmlFigure.class);
		addbt(HtmlCommand.class);
		addbt(HtmlMap.class);
		addbt(HtmlDl.class);
		addbt(HtmlDel.class);
		addbt(HtmlFieldSet.class);
		addbt(HtmlNoBr.class);
		addbt(HtmlHGroup.class);
		addbt(HtmlUl.class);
		addbt(HtmlDataList.class);
		addbt(HtmlB.class);
		addbt(HtmlAnchor.class);
		addbt(HtmlBlockquote.class);
		addbt(HtmlTableCaption.class);
		addbt(HtmlI.class);
		addbt(HtmlFooter.class);
		addbt(HtmlU.class);
		addbt(HtmlArticle.class);
		addbt(HtmlS.class);
		addbt(HtmlTt.class);
		addbt(HtmlLegend.class);
		addbt(HtmlQuote.class);
		addbt(HtmlParagraph.class);
		addbt(HtmlBase.class);
		addbt(HtmlEm.class);
		addbt(HtmlDfn.class);
		addbt(HtmlProgress.class);
		addbt(HtmlBody.class);
		addbt(HtmlTableCol.class);
		addbt(HtmlTableRow.class);
		addbt(HtmlImage.class);
		addbt(HtmlTableCell.class);
		addbt(HtmlBr.class);
		addbt(HtmlTh.class);
		addbt(HtmlBaseFont.class);
		addbt(HtmlSamp.class);
		addbt(HtmlBb.class);
		addbt(HtmlHead.class);
		addbt(HtmlMarquee.class);
		addbt(HtmlXmp.class);
		addbt(HtmlFont.class);
		addbt(HtmlSelect.class);
		addbt(HtmlIns.class);
		addbt(HtmlCanvas.class);
		addbt(HtmlAbbr.class);
		addbt(HtmlMark.class);
		addbt(HtmlXml.class);
		addbt(HtmlLabel.class);
		addbt(HtmlCode.class);
		addbt(HtmlScript.class);
		addbt(HtmlTableSection.class);
		addbt(HtmlEmbed.class);
		addbt(HtmlLi.class);
		addbt(HtmlInput.class);
		addbt(HtmlIFrame.class);
		addbt(HtmlFrame.class);
		addbt(HtmlStrong.class);
		addbt(HtmlRt.class);
		addbt(HtmlOutput.class);
		addbt(HtmlSpan.class);
		addbt(HtmlHr.class);
		addbt(HtmlRp.class);
		addbt(HtmlSub.class);
		addbt(HtmlRuby.class);
		addbt(HtmlVar.class);
		addbt(HtmlDiv.class);
		addbt(HtmlObject.class);
		addbt(HtmlSup.class);
		addbt(HtmlArea.class);
		addbt(HtmlDetails.class);
		addbt(HtmlStyle.class);
		addbt(HtmlDir.class);
		addbt(HtmlApplet.class);
		addbt(HtmlIsIndex.class);
		addbt(HtmlDialog.class);
		addbt(HtmlTableSection.class);
		addbt(HtmlMedia.class);
		addbt(HtmlAudio.class);
		addbt(HtmlAcronym.class);
		addbt(HtmlSection.class);
		addbt(HtmlBgSound.class);
		addbt(HtmlSource.class);
		addbt(HtmlFrameSet.class);
		addbt(HtmlTableSection.class);
		addbt(HtmlAddress.class);
		addbt(HtmlPre.class);
		addbt(HtmlNav.class);
		addbt(HtmlHtml.class);
		

	}

	private static void addClientSideObjects() {
		addbt(Window.class);
		addbt(History.class);
		addbt(Navigator.class);
		addbt(Screen.class);
		addbt(Location.class);
		addbt(External.class);
		addbt(Selection.class);
		addbt(Plugin.class);
		addbt(PluginArray.class);
		addbt(MimeType.class);
		addbt(Frames.class);
		addbt(XMLHttpRequest.class);	
		addbt(Opera.class);
	}

	private static void addGlobalObjects() {
		add(org.ebayopensource.dsf.jsnative.global.Boolean.class);
		add(org.ebayopensource.dsf.jsnative.global.PrimitiveBoolean.class);
		add(org.ebayopensource.dsf.jsnative.global.Number.class);
		add(org.ebayopensource.dsf.jsnative.global.Math.class);
		add(org.ebayopensource.dsf.jsnative.global.Object.class);
		add(org.ebayopensource.dsf.jsnative.global.String.class);
		add(org.ebayopensource.dsf.jsnative.global.Array.class);
		add(org.ebayopensource.dsf.jsnative.global.Date.class);
		add(org.ebayopensource.dsf.jsnative.global.Error.class);
		add(org.ebayopensource.dsf.jsnative.global.EvalError.class);
		add(org.ebayopensource.dsf.jsnative.global.Function.class);
		add(org.ebayopensource.dsf.jsnative.global.Global.class);
		add(org.ebayopensource.dsf.jsnative.global.RangeError.class);
		add(org.ebayopensource.dsf.jsnative.global.ReferenceError.class);
		add(org.ebayopensource.dsf.jsnative.global.SyntaxError.class);
		add(org.ebayopensource.dsf.jsnative.global.TypeError.class);
		add(org.ebayopensource.dsf.jsnative.global.URIError.class);
		add(org.ebayopensource.dsf.jsnative.global.Enumerator.class);
		add(org.ebayopensource.dsf.jsnative.global.Arguments.class);
		add(org.ebayopensource.dsf.jsnative.global.RegExp.class);
		add(org.ebayopensource.dsf.jsnative.global.ObjLiteral.class);
	}

}
