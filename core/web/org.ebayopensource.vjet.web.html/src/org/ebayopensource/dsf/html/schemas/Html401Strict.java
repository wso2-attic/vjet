/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.schemas;

import java.util.HashMap;
import java.util.Map;

import org.ebayopensource.dsf.html.dom.HtmlTypeEnum;

public class Html401Strict {

	private static final AttributeInfoEnumeration SHAPE_ATTR_INFO =
		new AttributeInfoEnumeration(
			"shape",
			ShapeEnum.class,
			AttributeDefault.IMPLIED,
			ShapeEnum.RECT);
	private static final AttributeInfoEnumeration DIR_ATTR_INFO =
		new AttributeInfoEnumeration("dir", DirEnum.class);
	private static final AttributeInfoBoolean ATTR_CHECKED =
		new AttributeInfoBoolean("checked");
	private static final AttributeInfoBoolean ATTR_COMPACT =
		new AttributeInfoBoolean("compact");
	private static final AttributeInfoBoolean ATTR_DISABLED =
		new AttributeInfoBoolean("disabled");
	private static final AttributeInfoBoolean ATTR_ISMAP =
		new AttributeInfoBoolean("ismap");
	private static final AttributeInfoBoolean ATTR_READONLY =
		new AttributeInfoBoolean("readonly");

	private static final IAttributeInfoMap ATTRS_CELL_HALIGN = createCellHAlignAttrs();
	private static final IAttributeInfoMap ATTRS_CELL_VALIGN = createCellVAlignAttrs();
	private static final IAttributeInfoMap ATTRS_CORE = createCoreAttrs();
	private static final IAttributeInfoMap ATTRS_EVENTS = createEventsAttrs();
	private static final IAttributeInfoMap ATTRS_I18N = createI18nAttrs();
	private static final IAttributeInfoMap ATTRS_RESERVED = createReservedAttrs();
	private static final IAttributeInfoMap ATTRS_ATTRS = createAttrsAttrs();

	private static final HtmlTypeSet SET_FONT_STYLE = createFontStyleSet();
	private static final HtmlTypeSet SET_FORM_CTRL = createFormCtrlSet();
	private static final HtmlTypeSet SET_HEADING = createHeadingSet();
	private static final HtmlTypeSet SET_PHRASE = createPhraseSet();
	private static final HtmlTypeSet SET_SPECIAL = createSpecialSet();
	private static final HtmlTypeSet SET_INLINE = createInlineSet();
	private static final HtmlTypeSet SET_LIST = createListSet();
	private static final HtmlTypeSet SET_PREFORMATTED = createPreformattedSet();
	private static final HtmlTypeSet SET_BLOCK = createBlockSet();
	private static final HtmlTypeSet SET_FLOW = createFlowSet();
	private static final HtmlTypeSet SET_HEAD_CONTENT = createHeadContentSet();
	private static final HtmlTypeSet SET_HEAD_MISC = createHeadMiscSet();

	private static final ISchema s_instance=createSchema();

	private static ISchema createSchema(){
		final Map< HtmlTypeEnum, IElementInfo > map =
				new HashMap< HtmlTypeEnum, IElementInfo >();
		addA(map);
		addAbbr(map);
		addAcronym(map);
		addAddress(map);
		addArea(map);
		addB(map);
		addBase(map);
		addBdo(map);
		addBig(map);
		addBlockQuote(map);
		addBody(map);
		addBr(map);
		addButton(map);
		addCaption(map);
		addCite(map);
		addCode(map);
		addCol(map);
		addColGroup(map);
		addDD(map);
		addDelAndIns(map);
		addDfn(map);
		addDiv(map);
		addDl(map);
		addDT(map);
		addEm(map);
		addFieldSet(map);
		addForm(map);
		addHeadings(map);
		addHead(map);
		addHr(map);
		addHtml(map);
		addI(map);
		addImg(map);
		addInput(map);
		addKbd(map);
		addLabel(map);
		addLegend(map);
		addLi(map);
		addLink(map);
		addMap(map);
		addMeta(map);
		addNoScript(map);
		addObject(map);
		addOl(map);
		addOptGroup(map);
		addOption(map);
		addP(map);
		addParam(map);
		addPre(map);
		addQ(map);
		addSamp(map);
		addScript(map);
		addSelect(map);
		addSmall(map);
		addSpan(map);
		addStrong(map);
		addStyle(map);
		addSubAndSup(map);
		addTable(map);
		addTbodyTfootThead(map);
		addTdAndTh(map);
		addTextArea(map);
		addTitle(map);
		addTr(map);
		addTt(map);
		addUl(map);
		final SchemaImp schema = new SchemaImp(map);
		return schema;
	}
	/** <!ELEMENT A - - (%inline;)* -(A)       -- anchor -->
	 * <!ATTLIST A
	 * %attrs;                              -- %coreattrs, %i18n, %events --
	 * charset     %Charset;      #IMPLIED  -- char encoding of linked resource --
	 * type        %ContentType;  #IMPLIED  -- advisory content type --
	 * name        CDATA          #IMPLIED  -- named link end --
	 * href        %URI;          #IMPLIED  -- URI for linked resource --
	 * hreflang    %LanguageCode; #IMPLIED  -- language code --
	 * rel         %LinkTypes;    #IMPLIED  -- forward link types --
	 * rev         %LinkTypes;    #IMPLIED  -- reverse link types --
	 * accesskey   %Character;    #IMPLIED  -- accessibility key character --
	 * shape       %Shape;        rect      -- for use with client-side image maps --
	 * coords      %Coords;       #IMPLIED  -- for use with client-side image maps --
	 * tabindex    NUMBER         #IMPLIED  -- position in tabbing order --
	 * onfocus     %Script;       #IMPLIED  -- the element got the focus --
	 * onblur      %Script;       #IMPLIED  -- the element lost the focus --
	 * >
	 */
	private static void addA(final Map<HtmlTypeEnum, IElementInfo > map) {
		final HtmlTypeSet includeElements = new HtmlTypeSet();
		includeElements.merge(SET_INLINE);
		final HtmlTypeSet excludeElements = new HtmlTypeSet();
		excludeElements.add(HtmlTypeEnum.A);
		final IAttributeInfoMap attrs = new AttributeInfoMapImpl();
		add(attrs, ATTRS_ATTRS);
		attrs.put(new AttributeInfoImpl("charset"));
		attrs.put(new AttributeInfoImpl("type"));
		attrs.put(new AttributeInfoImpl("name"));
		attrs.put(new AttributeInfoImpl("href"));
		attrs.put(new AttributeInfoImpl("hreflang", AttributeDataType.NAME));
		attrs.put(new AttributeInfoImpl("rel"));
		attrs.put(new AttributeInfoImpl("rev"));
		attrs.put(new AttributeInfoImpl("accesskey"));
		attrs.put(SHAPE_ATTR_INFO);
		attrs.put(new AttributeInfoImpl("coords"));
		attrs.put(new AttributeInfoImpl("tabindex", AttributeDataType.NUMBER));
		attrs.put(new AttributeInfoImpl("onfocus"));
		attrs.put(new AttributeInfoImpl("onblur"));
		final ElementInfo elementInfo = new ElementInfo(HtmlTypeEnum.A,
			attrs,
			new ContentModelSets(includeElements,excludeElements));
		map.put(elementInfo.getType(), elementInfo);
	}
	/**
	 * <!ENTITY % phrase "EM | STRONG | DFN | CODE |
	 * SAMP | KBD | VAR | CITE | ABBR | ACRONYM" >
	 * <!ELEMENT (%fontstyle;|%phrase;) - - (%inline;)*>
	 * <!ATTLIST (%fontstyle;|%phrase;)
	 * %attrs;                              -- %coreattrs, %i18n, %events --
	 * >
	 * @param map
	 */
	private static void addAbbr(final Map< HtmlTypeEnum, IElementInfo > map) {
		final ElementInfo elementInfo = new ElementInfo(
			HtmlTypeEnum.ABBR, ATTRS_ATTRS,
			new ContentModelSets(SET_INLINE,null));
		map.put(elementInfo.getType(), elementInfo);
	}
	/**
	 * <!ENTITY % phrase "EM | STRONG | DFN | CODE |
	 * SAMP | KBD | VAR | CITE | ABBR | ACRONYM" >
	 * <!ELEMENT (%fontstyle;|%phrase;) - - (%inline;)*>
	 * <!ATTLIST (%fontstyle;|%phrase;)
	 * %attrs;                              -- %coreattrs, %i18n, %events --
	 * >
	 * @param map
	 */
	private static void addAcronym(final Map< HtmlTypeEnum, IElementInfo > map) {
		final ElementInfo elementInfo = new ElementInfo(
			HtmlTypeEnum.ACRONYM, ATTRS_ATTRS,
			new ContentModelSets(SET_INLINE,null));
		map.put(elementInfo.getType(), elementInfo);
	}
	/** <!ELEMENT ADDRESS - - ((%inline;)|P)*  -- information on author -->
	 * <!ATTLIST ADDRESS
	 * %attrs;                              -- %coreattrs, %i18n, %events --
	 * >
	 */
	private static void addAddress(final Map< HtmlTypeEnum, IElementInfo > map) {
		final HtmlTypeSet includeElements = new HtmlTypeSet();
		includeElements.merge(SET_INLINE);
		includeElements.add(HtmlTypeEnum.P);
		final ElementInfo elementInfo = new ElementInfo(
			HtmlTypeEnum.ADDRESS, ATTRS_ATTRS,
			new ContentModelSets(includeElements,null));
		map.put(elementInfo.getType(), elementInfo);
	}
	/** <!ELEMENT AREA - O EMPTY               -- client-side image map area -->
	 * <!ATTLIST AREA
	 * %attrs;                              -- %coreattrs, %i18n, %events --
	 * shape       %Shape;        rect      -- controls interpretation of coords --
	 * coords      %Coords;       #IMPLIED  -- comma-separated list of lengths --
	 * href        %URI;          #IMPLIED  -- URI for linked resource --
	 * target      %FrameTarget;  #IMPLIED  -- render in this frame --
	 * nohref      (nohref)       #IMPLIED  -- this region has no action --
	 * alt         %Text;         #REQUIRED -- short description --
	 * tabindex    NUMBER         #IMPLIED  -- position in tabbing order --
	 * accesskey   %Character;    #IMPLIED  -- accessibility key character --
	 * onfocus     %Script;       #IMPLIED  -- the element got the focus --
	 * onblur      %Script;       #IMPLIED  -- the element lost the focus --
	 * >
	 */
	private static void addArea(final Map< HtmlTypeEnum, IElementInfo > map) {
		final IAttributeInfoMap attrs = new AttributeInfoMapImpl();
		add(attrs,ATTRS_ATTRS);
		attrs.put(SHAPE_ATTR_INFO);
		attrs.put(new AttributeInfoImpl("coords"));
		attrs.put(new AttributeInfoImpl("href"));
		attrs.put(new AttributeInfoImpl("target"));
		attrs.put(new AttributeInfoBoolean("nohref"));
		attrs.put(new AttributeInfoImpl("alt"));
		attrs.put(new AttributeInfoImpl("tabindex",AttributeDataType.NUMBER));
		attrs.put(new AttributeInfoImpl("accesskey"));
		attrs.put(new AttributeInfoImpl("onfocus"));
		attrs.put(new AttributeInfoImpl("onblur"));
		final ElementInfo elementInfo = new ElementInfo(
			HtmlTypeEnum.AREA, attrs, IContentModel.EMPTY, true, false);
		map.put(elementInfo.getType(), elementInfo);	
	}
	/** <!ENTITY % fontstyle "TT | I | B | BIG | SMALL">
	 * <!ELEMENT (%fontstyle;|%phrase;) - - (%inline;)*>
	 * <!ATTLIST (%fontstyle;|%phrase;)
	 * %attrs;                              -- %coreattrs, %i18n, %events --
	 * >
	 */
	private static void addB(final Map< HtmlTypeEnum, IElementInfo > map) {
		final ElementInfo elementInfo = new ElementInfo(
			HtmlTypeEnum.B, ATTRS_ATTRS,
			new ContentModelSets(SET_INLINE,null));
		map.put(elementInfo.getType(), elementInfo);
	}
	/** <!ELEMENT BASE - O EMPTY               -- document base URI -->
	 * <!ATTLIST BASE
	 * href        %URI;          #IMPLIED  -- URI that acts as base URI --
	 * target      %FrameTarget;  #IMPLIED  -- render in this frame --
	 * >
	 */
	private static void addBase(final Map< HtmlTypeEnum, IElementInfo > map) {
		final IAttributeInfoMap attrs = new AttributeInfoMapImpl();
		attrs.put(new AttributeInfoImpl("href"));
		attrs.put(new AttributeInfoImpl("target"));
		final ElementInfo elementInfo = new ElementInfo(
			HtmlTypeEnum.BASE, attrs, IContentModel.EMPTY, true, false);
		map.put(elementInfo.getType(), elementInfo);	
	}
	/** <!ELEMENT BDO - - (%inline;)*          -- I18N BiDi over-ride -->
	 * <!ATTLIST BDO
	 * %coreattrs;                          -- id, class, style, title --
	 * lang        %LanguageCode; #IMPLIED  -- language code --
	 * dir         (ltr|rtl)      #REQUIRED -- directionality --
	 * >
	 */
	private static void addBdo(final Map< HtmlTypeEnum, IElementInfo > map) {
		final IAttributeInfoMap attrs = new AttributeInfoMapImpl();
		add(attrs,ATTRS_CORE);
		attrs.put(new AttributeInfoImpl("id", AttributeDataType.ID));
		attrs.put(new AttributeInfoImpl("lang", AttributeDataType.NAME));
		attrs.put(DIR_ATTR_INFO);
		final ElementInfo elementInfo = new ElementInfo(
			HtmlTypeEnum.BDO, attrs, new ContentModelSets(SET_INLINE));
		map.put(elementInfo.getType(), elementInfo);	
	}
	/** <!ENTITY % fontstyle "TT | I | B | BIG | SMALL">
	 * <!ELEMENT (%fontstyle;|%phrase;) - - (%inline;)*>
	 * <!ATTLIST (%fontstyle;|%phrase;)
	 * %attrs;                              -- %coreattrs, %i18n, %events --
	 * >
	 */
	private static void addBig(final Map< HtmlTypeEnum, IElementInfo > map) {
		final ElementInfo elementInfo = new ElementInfo(
			HtmlTypeEnum.BIG, ATTRS_ATTRS,
			new ContentModelSets(SET_INLINE,null));
		map.put(elementInfo.getType(), elementInfo);
	}
	/** <!ELEMENT BLOCKQUOTE - - (%flow;)*     -- long quotation -->
	 * <!ATTLIST BLOCKQUOTE
	 * %attrs;                              -- %coreattrs, %i18n, %events --
	 * cite        %URI;          #IMPLIED  -- URI for source document or msg --
	 * >
	 */
	private static void addBlockQuote(final Map< HtmlTypeEnum, IElementInfo > map) {
		final IAttributeInfoMap attrs = new AttributeInfoMapImpl();
		add(attrs,ATTRS_ATTRS);
		attrs.put(new AttributeInfoImpl("cite"));
		final ElementInfo elementInfo = new ElementInfo(
			HtmlTypeEnum.BLOCKQUOTE, attrs,
			new ContentModelSets(SET_FLOW,null));
		map.put(elementInfo.getType(), elementInfo);
	}
	/** <!ELEMENT BODY O O (%flow;)* +(INS|DEL) -- document body -->
	 * <!ATTLIST BODY
	 * %attrs;                              -- %coreattrs, %i18n, %events --
	 * onload          %Script;   #IMPLIED  -- the document has been loaded --
	 * onunload        %Script;   #IMPLIED  -- the document has been removed --
	 * background      %URI;      #IMPLIED  -- texture tile for document background --
	 * >
	 */
	private static void addBody(final Map< HtmlTypeEnum, IElementInfo > map) {
		final IAttributeInfoMap attrs = new AttributeInfoMapImpl();
		add(attrs,ATTRS_ATTRS);
		attrs.put(new AttributeInfoImpl("onload"));
		attrs.put(new AttributeInfoImpl("onunload"));
		final HtmlTypeSet includeElements = new HtmlTypeSet();
		includeElements.merge(SET_FLOW);
		includeElements.add(HtmlTypeEnum.INS);
		includeElements.add(HtmlTypeEnum.DEL);
		final ElementInfo elementInfo = new ElementInfo(
			HtmlTypeEnum.BODY, attrs,
			new ContentModelSets(includeElements));
		map.put(elementInfo.getType(), elementInfo);		
	}
	/** <!ELEMENT BR - O EMPTY                 -- forced line break --
	 * <!ATTLIST BR
	 * %coreattrs;                          -- id, class, style, title --
	 * clear       (left|all|right|none) none -- control of text flow --
	 * >
	 */
	private static void addBr(final Map< HtmlTypeEnum, IElementInfo > map) {
		final IAttributeInfoMap attrs = new AttributeInfoMapImpl();
		add(attrs,ATTRS_CORE);
		attrs.put(new AttributeInfoEnumeration("clear", ClearEnum.class));
		final ElementInfo elementInfo = new ElementInfo(
			HtmlTypeEnum.BR, attrs, IContentModel.EMPTY, true, false);
		map.put(elementInfo.getType(), elementInfo);		
	}
	/** <!ELEMENT BUTTON - -
	 * (%flow;)* -(A|%formctrl;|FORM|ISINDEX|FIELDSET|IFRAME)
	 * -- push button -->
	 * <!ATTLIST BUTTON
	 * %attrs;                              -- %coreattrs, %i18n, %events --
	 * name        CDATA          #IMPLIED
	 * value       CDATA          #IMPLIED  -- sent to server when submitted --
	 * type        (button|submit|reset) submit -- for use as form button --
	 * disabled    (disabled)     #IMPLIED  -- unavailable in this context --
	 * tabindex    NUMBER         #IMPLIED  -- position in tabbing order --
	 * accesskey   %Character;    #IMPLIED  -- accessibility key character --
	 * onfocus     %Script;       #IMPLIED  -- the element got the focus --
	 * onblur      %Script;       #IMPLIED  -- the element lost the focus --
	 * %reserved;                           -- reserved for possible future use --
	 * >
	 */
	private static void addButton(final Map< HtmlTypeEnum, IElementInfo > map) {
		final IAttributeInfoMap attrs = new AttributeInfoMapImpl();
		add(attrs,ATTRS_ATTRS);
		attrs.put(new AttributeInfoImpl("name"));
		attrs.put(new AttributeInfoImpl("value"));
		attrs.put(new AttributeInfoEnumeration("type", ButtonTypeEnum.class));
		attrs.put(new AttributeInfoImpl("disabled"));
		attrs.put(new AttributeInfoImpl("tabindex", AttributeDataType.NUMBER));
		attrs.put(new AttributeInfoImpl("accesskey"));
		attrs.put(new AttributeInfoImpl("onfocus"));
		attrs.put(new AttributeInfoImpl("onblur"));
		add(attrs, ATTRS_RESERVED);
		final HtmlTypeSet excludeElements = new HtmlTypeSet();
		excludeElements.add(HtmlTypeEnum.A);
		excludeElements.merge(SET_FORM_CTRL);
		excludeElements.add(HtmlTypeEnum.FORM);
		excludeElements.add(HtmlTypeEnum.ISINDEX);
		excludeElements.add(HtmlTypeEnum.FIELDSET);
		excludeElements.add(HtmlTypeEnum.IFRAME);
		final ElementInfo elementInfo = new ElementInfo(
			HtmlTypeEnum.BUTTON, attrs,
			new ContentModelSets(SET_FLOW, excludeElements));
		map.put(elementInfo.getType(), elementInfo);		
	}
	/** <!ELEMENT CAPTION  - - (%inline;)*     -- table caption -->
	 * <!ATTLIST CAPTION
	 * %attrs;                              -- %coreattrs, %i18n, %events --
	 * >
	 */
	private static void addCaption(final Map< HtmlTypeEnum, IElementInfo > map) {
		final IAttributeInfoMap attrs = new AttributeInfoMapImpl();
		add(attrs,ATTRS_ATTRS);
		final ElementInfo elementInfo = new ElementInfo(
			HtmlTypeEnum.CAPTION, attrs,
			new ContentModelSets(SET_INLINE));
		map.put(elementInfo.getType(), elementInfo);		
	}
	/**
	 * <!ENTITY % phrase "EM | STRONG | DFN | CODE |
	 * SAMP | KBD | VAR | CITE | ABBR | ACRONYM" >
	 * <!ELEMENT (%fontstyle;|%phrase;) - - (%inline;)*>
	 * <!ATTLIST (%fontstyle;|%phrase;)
	 * %attrs;                              -- %coreattrs, %i18n, %events --
	 * >
	 * @param map
	 */
	private static void addCite(final Map< HtmlTypeEnum, IElementInfo > map) {
		final ElementInfo elementInfo = new ElementInfo(
			HtmlTypeEnum.CITE, ATTRS_ATTRS,
			new ContentModelSets(SET_INLINE,null));
		map.put(elementInfo.getType(), elementInfo);
	}
	/**
	 * <!ENTITY % phrase "EM | STRONG | DFN | CODE |
	 * SAMP | KBD | VAR | CITE | ABBR | ACRONYM" >
	 * <!ELEMENT (%fontstyle;|%phrase;) - - (%inline;)*>
	 * <!ATTLIST (%fontstyle;|%phrase;)
	 * %attrs;                              -- %coreattrs, %i18n, %events --
	 * >
	 * @param map
	 */
	private static void addCode(final Map< HtmlTypeEnum, IElementInfo > map) {
		final ElementInfo elementInfo = new ElementInfo(
			HtmlTypeEnum.CODE, ATTRS_ATTRS,
			new ContentModelSets(SET_INLINE,null));
		map.put(elementInfo.getType(), elementInfo);
	}
	/** <!ELEMENT COL      - O EMPTY           -- table column -->
	 * <!ATTLIST COL                          -- column groups and properties --
	 * %attrs;                              -- %coreattrs, %i18n, %events --
	 * span        NUMBER         1         -- COL attributes affect N columns --
	 * width       %MultiLength;  #IMPLIED  -- column width specification --
	 * %cellhalign;                         -- horizontal alignment in cells --
	 * %cellvalign;                         -- vertical alignment in cells --
	 * >
	 */
	private static void addCol(final Map< HtmlTypeEnum, IElementInfo > map) {
		final IAttributeInfoMap attrs = new AttributeInfoMapImpl();
		add(attrs,ATTRS_ATTRS);
		attrs.put(new AttributeInfoImpl("span", AttributeDataType.NUMBER));
		attrs.put(new AttributeInfoImpl("width"));
		add(attrs,ATTRS_CELL_HALIGN);
		add(attrs,ATTRS_CELL_VALIGN);
		final ElementInfo elementInfo = new ElementInfo(
			HtmlTypeEnum.COL, attrs, IContentModel.EMPTY, true, false);
		map.put(elementInfo.getType(), elementInfo);		
	}
	/** <!ELEMENT COLGROUP - O (COL)*          -- table column group -->
	 * <!ATTLIST COLGROUP
	 * %attrs;                              -- %coreattrs, %i18n, %events --
	 * span        NUMBER         1         -- default number of columns in group --
	 * width       %MultiLength;  #IMPLIED  -- default width for enclosed COLs --
	 * %cellhalign;                         -- horizontal alignment in cells --
	 * %cellvalign;                         -- vertical alignment in cells --
	 * >
	 */
	private static void addColGroup(final Map< HtmlTypeEnum, IElementInfo > map) {
		final HtmlTypeSet includeElements = new HtmlTypeSet();
		includeElements.add(HtmlTypeEnum.COL);
		final IAttributeInfoMap attrs = new AttributeInfoMapImpl();
		add(attrs,ATTRS_ATTRS);
		attrs.put(new AttributeInfoImpl("span", AttributeDataType.NUMBER));
		attrs.put(new AttributeInfoImpl("width"));
		add(attrs,ATTRS_CELL_HALIGN);
		add(attrs,ATTRS_CELL_VALIGN);
		final ElementInfo elementInfo = new ElementInfo(
			HtmlTypeEnum.COLGROUP,
			attrs,
			new ContentModelSets(includeElements),
			true,
			false);
		map.put(elementInfo.getType(), elementInfo);		
	}
	/** <!ELEMENT DD - O (%flow;)*             -- definition description -->
	 * <!ATTLIST (DT|DD)
	 * %attrs;                              -- %coreattrs, %i18n, %events --
	 * >
	 */
	private static void addDD(final Map< HtmlTypeEnum, IElementInfo > map) {
		final ElementInfo elementInfo = new ElementInfo(
			HtmlTypeEnum.DD,
			ATTRS_ATTRS,
			new ContentModelSets(SET_FLOW), 
			true,
			false);
		map.put(elementInfo.getType(), elementInfo);		
	}
	/** <!ELEMENT (INS|DEL) - - (%flow;)*      -- inserted text, deleted text -->
	 * <!ATTLIST (INS|DEL)
	 * %attrs;                              -- %coreattrs, %i18n, %events --
	 * cite        %URI;          #IMPLIED  -- info on reason for change --
	 * datetime    %Datetime;     #IMPLIED  -- date and time of change --
	 * >
	 */
	private static void addDelAndIns(final Map< HtmlTypeEnum, IElementInfo > map) {
		final IAttributeInfoMap attrs = new AttributeInfoMapImpl();
		add(attrs,ATTRS_ATTRS);
		attrs.put(new AttributeInfoImpl("cite"));
		attrs.put(new AttributeInfoImpl("datetime"));
		final ElementInfo del = new ElementInfo(
			HtmlTypeEnum.DEL,
			attrs,
			new ContentModelSets(SET_FLOW));
		map.put(del.getType(), del);		
		final ElementInfo ins = new ElementInfo(
			HtmlTypeEnum.INS,
			attrs,
			new ContentModelSets(SET_FLOW));
		map.put(ins.getType(), ins);		
	}
	/**
	 * <!ENTITY % phrase "EM | STRONG | DFN | CODE |
	 * SAMP | KBD | VAR | CITE | ABBR | ACRONYM" >
	 * <!ELEMENT (%fontstyle;|%phrase;) - - (%inline;)*>
	 * <!ATTLIST (%fontstyle;|%phrase;)
	 * %attrs;                              -- %coreattrs, %i18n, %events --
	 * >
	 * @param map
	 */
	private static void addDfn(final Map< HtmlTypeEnum, IElementInfo > map) {
		final ElementInfo elementInfo = new ElementInfo(
			HtmlTypeEnum.DFN, ATTRS_ATTRS,
			new ContentModelSets(SET_INLINE,null));
		map.put(elementInfo.getType(), elementInfo);
	}
	/** <!ELEMENT DIV - - (%flow;)*            -- generic language/style container -->
	 * <!ATTLIST DIV
	 * %attrs;                              -- %coreattrs, %i18n, %events --
	 * %reserved;                           -- reserved for possible future use --
	 * >
	 */
	private static void addDiv(final Map< HtmlTypeEnum, IElementInfo > map) {
		final IAttributeInfoMap attrs = new AttributeInfoMapImpl();
		add(attrs,ATTRS_ATTRS);
		add(attrs,ATTRS_RESERVED);
		final ElementInfo elementInfo = new ElementInfo(
			HtmlTypeEnum.DIV,
			attrs,
			new ContentModelSets(SET_FLOW));
		map.put(elementInfo.getType(), elementInfo);
	}
	/** <!ELEMENT DL - - (DT|DD)+              -- definition list -->
	 * <!ATTLIST DL
	 * %attrs;                              -- %coreattrs, %i18n, %events --
	 * compact     (compact)      #IMPLIED  -- reduced interitem spacing --
	 * >
	 */
	private static void addDl(final Map< HtmlTypeEnum, IElementInfo > map) {
		final IAttributeInfoMap attrs = new AttributeInfoMapImpl();
		add(attrs,ATTRS_ATTRS);
		attrs.put(ATTR_COMPACT);
		final HtmlTypeSet includeElements = new HtmlTypeSet();
		includeElements.add(HtmlTypeEnum.DT);
		includeElements.add(HtmlTypeEnum.DD);
		final ElementInfo elementInfo = new ElementInfo(
			HtmlTypeEnum.DL,
			attrs,
			new ContentModelSets(includeElements));
		map.put(elementInfo.getType(), elementInfo);
	}
	/** <!ELEMENT DT - O (%inline;)*           -- definition term -->
	 * <!ATTLIST (DT|DD)
	 * %attrs;                              -- %coreattrs, %i18n, %events --
	 * >
	 */
	private static void addDT(final Map< HtmlTypeEnum, IElementInfo > map) {
		final ElementInfo elementInfo = new ElementInfo(
			HtmlTypeEnum.DT,
			ATTRS_ATTRS,
			new ContentModelSets(SET_INLINE), 
			true,
			false);
		map.put(elementInfo.getType(), elementInfo);		
	}
	/**
	 * <!ENTITY % phrase "EM | STRONG | DFN | CODE |
	 * SAMP | KBD | VAR | CITE | ABBR | ACRONYM" >
	 * <!ELEMENT (%fontstyle;|%phrase;) - - (%inline;)*>
	 * <!ATTLIST (%fontstyle;|%phrase;)
	 * %attrs;                              -- %coreattrs, %i18n, %events --
	 * >
	 * @param map
	 */
	private static void addEm(final Map< HtmlTypeEnum, IElementInfo > map) {
		final ElementInfo elementInfo = new ElementInfo(
			HtmlTypeEnum.EM, ATTRS_ATTRS,
			new ContentModelSets(SET_INLINE,null));
		map.put(elementInfo.getType(), elementInfo);
	}
	/** <!ELEMENT FIELDSET - - (#PCDATA,LEGEND,(%flow;)*) -- form control group -->
	 * <!ATTLIST FIELDSET
	 * %attrs;                              -- %coreattrs, %i18n, %events --
	 * >
	 */
	private static void addFieldSet(final Map< HtmlTypeEnum, IElementInfo > map) {
		final HtmlTypeSet includeElements = new HtmlTypeSet();
		includeElements.add(HtmlTypeEnum.TEXT); // Is this how to add #PCDATA???
		includeElements.add(HtmlTypeEnum.LEGEND);
		includeElements.merge(SET_FLOW);
		final ElementInfo elementInfo = new ElementInfo(
			HtmlTypeEnum.FIELDSET,
			ATTRS_ATTRS,
			new ContentModelSets(includeElements));
		map.put(elementInfo.getType(), elementInfo);
	}
	/** <!ELEMENT FORM - - (%flow;)* -(FORM)   -- interactive form -->
	 * <!ATTLIST FORM
	 * %attrs;                              -- %coreattrs, %i18n, %events --
	 * action      %URI;          #REQUIRED -- server-side form handler --
	 * method      (GET|POST)     GET       -- HTTP method used to submit the form--
	 * enctype     %ContentType;  "application/x-www-form-urlencoded"
	 * accept      %ContentTypes; #IMPLIED  -- list of MIME types for file upload --
	 * name        CDATA          #IMPLIED  -- name of form for scripting --
	 * onsubmit    %Script;       #IMPLIED  -- the form was submitted --
	 * onreset     %Script;       #IMPLIED  -- the form was reset --
	 * target      %FrameTarget;  #IMPLIED  -- render in this frame --
	 * accept-charset %Charsets;  #IMPLIED  -- list of supported charsets --
	 * >
	 */
	private static void addForm(final Map< HtmlTypeEnum, IElementInfo > map) {
		final IAttributeInfoMap attrs = new AttributeInfoMapImpl();
		add(attrs,ATTRS_ATTRS);
		attrs.put(new AttributeInfoImpl("action"));
		attrs.put(new AttributeInfoEnumeration("method",
			FormMethodEnum.class, AttributeDefault.IMPLIED,FormMethodEnum.GET));
		attrs.put(new AttributeInfoImpl("enctype"));
		attrs.put(new AttributeInfoImpl("accept"));
		attrs.put(new AttributeInfoImpl("name"));
		attrs.put(new AttributeInfoImpl("onsubmit"));
		attrs.put(new AttributeInfoImpl("onreset"));
		attrs.put(new AttributeInfoImpl("target"));
		attrs.put(new AttributeInfoImpl("accept-charset"));
		final HtmlTypeSet excludeElements = new HtmlTypeSet();
		excludeElements.add(HtmlTypeEnum.FORM);
		final ElementInfo elementInfo = new ElementInfo(
			HtmlTypeEnum.FORM,
			attrs,
			new ContentModelSets(SET_FLOW,excludeElements));
		map.put(elementInfo.getType(), elementInfo);
	}
	/**
	 * <!ENTITY % heading "H1|H2|H3|H4|H5|H6">
	 * <!ELEMENT (%heading;)  - - (%inline;)* -- heading -->
	 * <!ATTLIST (%heading;)
	 * %attrs;                              -- %coreattrs, %i18n, %events --
	 * >
	 */
	private static void addHeadings(final Map< HtmlTypeEnum, IElementInfo > map) {
		final IAttributeInfoMap attrs = new AttributeInfoMapImpl();
		add(attrs,ATTRS_ATTRS);
		map.put(HtmlTypeEnum.H1, new ElementInfo(
			HtmlTypeEnum.H1,
			attrs,
			new ContentModelSets(SET_INLINE) ));
		map.put(HtmlTypeEnum.H2, new ElementInfo(
			HtmlTypeEnum.H2,
			attrs,
			new ContentModelSets(SET_INLINE) ));
		map.put(HtmlTypeEnum.H3, new ElementInfo(
			HtmlTypeEnum.H3,
			attrs,
			new ContentModelSets(SET_INLINE) ));
		map.put(HtmlTypeEnum.H4, new ElementInfo(
			HtmlTypeEnum.H4,
			attrs,
			new ContentModelSets(SET_INLINE) ));
		map.put(HtmlTypeEnum.H5, new ElementInfo(
			HtmlTypeEnum.H5,
			attrs,
			new ContentModelSets(SET_INLINE) ));
		map.put(HtmlTypeEnum.H6, new ElementInfo(
			HtmlTypeEnum.H6,
			attrs,
			new ContentModelSets(SET_INLINE) ));
	}
	/**
	 * <!ELEMENT HEAD O O (%head.content;) +(%head.misc;) -- document head -->
	 * <!ATTLIST HEAD
	 * %i18n;                               -- lang, dir --
	 * profile     %URI;          #IMPLIED  -- named dictionary of meta info --
	 * >
	 */
	private static void addHead(final Map< HtmlTypeEnum, IElementInfo > map) {
		final IAttributeInfoMap attrs = new AttributeInfoMapImpl();
		add(attrs,ATTRS_I18N);
		attrs.put(new AttributeInfoImpl("profile"));		
		final HtmlTypeSet includeElements = new HtmlTypeSet();
		includeElements.merge(SET_HEAD_CONTENT);
		includeElements.merge(SET_HEAD_MISC);
		final ElementInfo elementInfo = new ElementInfo(
			HtmlTypeEnum.HEAD,
			attrs,
			new ContentModelSets(includeElements));
		map.put(elementInfo.getType(), elementInfo);
	}
	/** <!ELEMENT HR - O EMPTY -- horizontal rule -->
	 * <!ATTLIST HR
	 * %attrs;                              -- %coreattrs, %i18n, %events --
	 * noshade     (noshade)      #IMPLIED
	 * size        %Pixels;       #IMPLIED
	 * width       %Length;       #IMPLIED
	 * >
	 */
	private static void addHr(final Map< HtmlTypeEnum, IElementInfo > map) {
		final IAttributeInfoMap attrs = new AttributeInfoMapImpl();
		add(attrs,ATTRS_ATTRS);
		final ElementInfo elementInfo = new ElementInfo(
			HtmlTypeEnum.HR,
			attrs,
			IContentModel.EMPTY,
			true,
			false);
		map.put(elementInfo.getType(), elementInfo);		
	}
	/** <!ENTITY % fontstyle "TT | I | B | BIG | SMALL">
	 * <!ELEMENT (%fontstyle;|%phrase;) - - (%inline;)*>
	 * <!ATTLIST (%fontstyle;|%phrase;)
	 * %attrs;                              -- %coreattrs, %i18n, %events --
	 * >
	 */
	private static void addI(final Map< HtmlTypeEnum, IElementInfo > map) {
		final ElementInfo elementInfo = new ElementInfo(
			HtmlTypeEnum.I, ATTRS_ATTRS,
			new ContentModelSets(SET_INLINE,null));
		map.put(elementInfo.getType(), elementInfo);
	}
	/**<!ELEMENT IMG - O EMPTY                -- Embedded image -->
	 * <!ATTLIST IMG
	 * %attrs;                              -- %coreattrs, %i18n, %events --
	 * src         %URI;          #REQUIRED -- URI of image to embed --
	 * alt         %Text;         #REQUIRED -- short description --
	 * longdesc    %URI;          #IMPLIED  -- link to long description
	 *                                        (complements alt) --
	 * name        CDATA          #IMPLIED  -- name of image for scripting --
	 * height      %Length;       #IMPLIED  -- override height --
	 * width       %Length;       #IMPLIED  -- override width --
	 * usemap      %URI;          #IMPLIED  -- use client-side image map --
	 * ismap       (ismap)        #IMPLIED  -- use server-side image map --
	 * >
	 */
	private static void addImg(final Map< HtmlTypeEnum, IElementInfo > map) {
		final IAttributeInfoMap attrs = new AttributeInfoMapImpl();
		add(attrs,ATTRS_ATTRS);
		attrs.put(new AttributeInfoImpl("src"));		
		attrs.put(new AttributeInfoImpl("alt"));		
		attrs.put(new AttributeInfoImpl("longdesc"));
		attrs.put(new AttributeInfoImpl("name"));		
		attrs.put(new AttributeInfoImpl("height"));		
		attrs.put(new AttributeInfoImpl("width"));		
		attrs.put(new AttributeInfoImpl("usemap"));		
		attrs.put(ATTR_ISMAP);		
		final ElementInfo elementInfo = new ElementInfo(
			HtmlTypeEnum.IMG,
			attrs,
			IContentModel.EMPTY,
			true,
			false);
		map.put(elementInfo.getType(), elementInfo);		
	}
	/**<!ELEMENT INPUT - O EMPTY              -- form control -->
	 * <!ATTLIST INPUT
	 * %attrs;                              -- %coreattrs, %i18n, %events --
	 * type        %InputType;    TEXT      -- what kind of widget is needed --
	 * name        CDATA          #IMPLIED  -- submit as part of form --
	 * value       CDATA          #IMPLIED  -- Specify for radio buttons and checkboxes --
	 * checked     (checked)      #IMPLIED  -- for radio buttons and check boxes --
	 * disabled    (disabled)     #IMPLIED  -- unavailable in this context --
	 * readonly    (readonly)     #IMPLIED  -- for text and passwd --
	 * size        CDATA          #IMPLIED  -- specific to each type of field --
	 * maxlength   NUMBER         #IMPLIED  -- max chars for text fields --
	 * src         %URI;          #IMPLIED  -- for fields with images --
	 * alt         CDATA          #IMPLIED  -- short description --
	 * usemap      %URI;          #IMPLIED  -- use client-side image map --
	 * ismap       (ismap)        #IMPLIED  -- use server-side image map --
	 * tabindex    NUMBER         #IMPLIED  -- position in tabbing order --
	 * accesskey   %Character;    #IMPLIED  -- accessibility key character --
	 * onfocus     %Script;       #IMPLIED  -- the element got the focus --
	 * onblur      %Script;       #IMPLIED  -- the element lost the focus --
	 * onselect    %Script;       #IMPLIED  -- some text was selected --
	 * onchange    %Script;       #IMPLIED  -- the element value was changed --
	 * accept      %ContentTypes; #IMPLIED  -- list of MIME types for file upload --
	 * %reserved;                           -- reserved for possible future use --
	 * >
	 */
	private static void addInput(final Map< HtmlTypeEnum, IElementInfo > map) {
		final IAttributeInfoMap attrs = new AttributeInfoMapImpl();
		add(attrs,ATTRS_ATTRS);
		attrs.put(new AttributeInfoEnumeration("type",
			InputTypeEnum.class, AttributeDefault.IMPLIED, InputTypeEnum.TEXT));		
		attrs.put(new AttributeInfoImpl("name"));
		attrs.put(new AttributeInfoImpl("value"));
		attrs.put(ATTR_CHECKED);
		attrs.put(ATTR_DISABLED);
		attrs.put(ATTR_READONLY);
		attrs.put(new AttributeInfoImpl("size"));		
		attrs.put(new AttributeInfoImpl("maxlength", AttributeDataType.NUMBER));		
		attrs.put(new AttributeInfoImpl("src"));		
		attrs.put(new AttributeInfoImpl("alt"));		
		attrs.put(new AttributeInfoImpl("usemap"));		
		attrs.put(ATTR_ISMAP);		
		attrs.put(new AttributeInfoImpl("tabindex", AttributeDataType.NUMBER));		
		attrs.put(new AttributeInfoImpl("accesskey"));		
		attrs.put(new AttributeInfoImpl("onfocus"));		
		attrs.put(new AttributeInfoImpl("onblur"));		
		attrs.put(new AttributeInfoImpl("onselect"));		
		attrs.put(new AttributeInfoImpl("onchange"));		
		attrs.put(new AttributeInfoImpl("accept"));		
		add(attrs,ATTRS_RESERVED);
		final ElementInfo elementInfo = new ElementInfo(
			HtmlTypeEnum.INPUT,
			attrs,
			IContentModel.EMPTY,
			true,
			false);
		map.put(elementInfo.getType(), elementInfo);		
	}
	/** <!ENTITY % phrase "EM | STRONG | DFN | CODE |
	 * SAMP | KBD | VAR | CITE | ABBR | ACRONYM" >
	 * <!ELEMENT (%fontstyle;|%phrase;) - - (%inline;)*>
	 * <!ATTLIST (%fontstyle;|%phrase;)
	 * %attrs;                              -- %coreattrs, %i18n, %events --
	 * >
	 */
	private static void addKbd(final Map< HtmlTypeEnum, IElementInfo > map) {
		final ElementInfo elementInfo = new ElementInfo(
			HtmlTypeEnum.KBD, ATTRS_ATTRS,
			new ContentModelSets(SET_INLINE,null));
		map.put(elementInfo.getType(), elementInfo);
	}
	/** <!ELEMENT LABEL - - (%inline;)* -(LABEL) -- form field label text -->
	 * <!ATTLIST LABEL
	 * %attrs;                              -- %coreattrs, %i18n, %events --
	 * for         IDREF          #IMPLIED  -- matches field ID value --
	 * accesskey   %Character;    #IMPLIED  -- accessibility key character --
	 * onfocus     %Script;       #IMPLIED  -- the element got the focus --
	 * onblur      %Script;       #IMPLIED  -- the element lost the focus --
	 * >
	 */
	private static void addLabel(final Map< HtmlTypeEnum, IElementInfo > map) {
		final IAttributeInfoMap attrs = new AttributeInfoMapImpl();
		add(attrs,ATTRS_ATTRS);
		attrs.put(new AttributeInfoImpl("for", AttributeDataType.IDREF));
		attrs.put(new AttributeInfoImpl("accesskey"));
		attrs.put(new AttributeInfoImpl("onfocus"));
		attrs.put(new AttributeInfoImpl("onblur"));
		final HtmlTypeSet excludeElements = new HtmlTypeSet();
		excludeElements.add(HtmlTypeEnum.LABEL);
		final ElementInfo elementInfo = new ElementInfo(
			HtmlTypeEnum.LABEL,
			attrs,
			new ContentModelSets(SET_INLINE,excludeElements));
		map.put(elementInfo.getType(), elementInfo);
	}
	/**<!ELEMENT LEGEND - - (%inline;)*       -- fieldset legend -->
	 * <!ENTITY % LAlign "(top|bottom|left|right)">
	 * <!ATTLIST LEGEND
	 * %attrs;                              -- %coreattrs, %i18n, %events --
	 * accesskey   %Character;    #IMPLIED  -- accessibility key character --
	 * >
	 */
	private static void addLegend(final Map< HtmlTypeEnum, IElementInfo > map) {
		final IAttributeInfoMap attrs = new AttributeInfoMapImpl();
		add(attrs,ATTRS_ATTRS);
		attrs.put(new AttributeInfoImpl("accesskey"));
		final ElementInfo elementInfo = new ElementInfo(
			HtmlTypeEnum.LEGEND,
			attrs,
			new ContentModelSets(SET_INLINE));
		map.put(elementInfo.getType(), elementInfo);
	}
	/**<!ELEMENT LI - O (%flow;)*             -- list item -->
	 * <!ATTLIST LI
	 * %attrs;                              -- %coreattrs, %i18n, %events --
	 * type        %LIStyle;      #IMPLIED  -- list item style --
	 * value       NUMBER         #IMPLIED  -- reset sequence number --
	 * >
	 * <!ENTITY % LIStyle "CDATA" -- constrained to: "(%ULStyle;|%OLStyle;)" -->
	 * <!ENTITY % ULStyle "(disc|square|circle)">
	 * <!ENTITY % OLStyle "CDATA"      -- constrained to: "(1|a|A|i|I)" -->
	 */
	private static void addLi(final Map< HtmlTypeEnum, IElementInfo > map) {
		final IAttributeInfoMap attrs = new AttributeInfoMapImpl();
		add(attrs,ATTRS_ATTRS);
		attrs.put(new AttributeInfoImpl("type")); // should be constrained		
		attrs.put(new AttributeInfoImpl("value", AttributeDataType.NUMBER));		
		final ElementInfo elementInfo = new ElementInfo(
			HtmlTypeEnum.LI,
			attrs,
			new ContentModelSets(SET_FLOW),
			true,
			false);
		map.put(elementInfo.getType(), elementInfo);		
	}
	/**<!ELEMENT LINK - O EMPTY               -- a media-independent link -->
	 * <!ATTLIST LINK
	 * %attrs;                              -- %coreattrs, %i18n, %events --
	 * charset     %Charset;      #IMPLIED  -- char encoding of linked resource --
	 * href        %URI;          #IMPLIED  -- URI for linked resource --
	 * hreflang    %LanguageCode; #IMPLIED  -- language code --
	 * type        %ContentType;  #IMPLIED  -- advisory content type --
	 * rel         %LinkTypes;    #IMPLIED  -- forward link types --
	 * rev         %LinkTypes;    #IMPLIED  -- reverse link types --
	 * media       %MediaDesc;    #IMPLIED  -- for rendering on these media --
	 * target      %FrameTarget;  #IMPLIED  -- render in this frame --
	 * >
	 */
	private static void addLink(final Map< HtmlTypeEnum, IElementInfo > map) {
		final IAttributeInfoMap attrs = new AttributeInfoMapImpl();
		add(attrs,ATTRS_ATTRS);
		attrs.put(new AttributeInfoImpl("charset"));		
		attrs.put(new AttributeInfoImpl("href"));		
		attrs.put(new AttributeInfoImpl("hreflang", AttributeDataType.NAME));		
		attrs.put(new AttributeInfoImpl("type"));		
		attrs.put(new AttributeInfoImpl("rel"));		
		attrs.put(new AttributeInfoImpl("rev"));		
		attrs.put(new AttributeInfoImpl("media"));		
		attrs.put(new AttributeInfoImpl("target"));		
		final ElementInfo elementInfo = new ElementInfo(
			HtmlTypeEnum.LINK,
			attrs,
			IContentModel.EMPTY,
			true,
			false);
		map.put(elementInfo.getType(), elementInfo);		
	}
	/**<!ELEMENT MAP - - ((%block;) | AREA)+ -- client-side image map -->
	 * <!ATTLIST MAP
	 * %attrs;                              -- %coreattrs, %i18n, %events --
	 * name        CDATA          #REQUIRED -- for reference by usemap --
	 * >
	 */
	private static void addMap(final Map< HtmlTypeEnum, IElementInfo > map) {
		final IAttributeInfoMap attrs = new AttributeInfoMapImpl();
		add(attrs,ATTRS_ATTRS);
		attrs.put(new AttributeInfoImpl("name"));
		final HtmlTypeSet includeElements = new HtmlTypeSet();
		includeElements.merge(SET_BLOCK);
		includeElements.add(HtmlTypeEnum.AREA);
		final ElementInfo elementInfo = new ElementInfo(
			HtmlTypeEnum.MAP,
			attrs,
			new ContentModelSets(includeElements));
		map.put(elementInfo.getType(), elementInfo);
	}
	/** <!ELEMENT META - O EMPTY               -- generic metainformation -->
	 * <!ATTLIST META
	 * %i18n;                               -- lang, dir, for use with content --
	 * http-equiv  NAME           #IMPLIED  -- HTTP response header name  --
	 * name        NAME           #IMPLIED  -- metainformation name --
	 * content     CDATA          #REQUIRED -- associated information --
	 * scheme      CDATA          #IMPLIED  -- select form of content --
	 * >
	 */
	private static void addMeta(final Map< HtmlTypeEnum, IElementInfo > map) {
		final IAttributeInfoMap attrs = new AttributeInfoMapImpl();
		add(attrs,ATTRS_I18N);
		attrs.put(new AttributeInfoImpl("http-equiv",AttributeDataType.NAME));		
		attrs.put(new AttributeInfoImpl("name",AttributeDataType.NAME));		
		attrs.put(new AttributeInfoImpl("content"));		
		attrs.put(new AttributeInfoImpl("scheme"));		
		final ElementInfo elementInfo = new ElementInfo(
			HtmlTypeEnum.META,
			attrs,
			IContentModel.EMPTY,
			true,
			false);
		map.put(elementInfo.getType(), elementInfo);		
	}
	/**<!ELEMENT NOSCRIPT - - (%flow;)*
	 * -- alternate content container for non script-based rendering -->
	 * <!ATTLIST NOSCRIPT
	 * %attrs;                              -- %coreattrs, %i18n, %events --
	 * >
	 */
	private static void addNoScript(final Map< HtmlTypeEnum, IElementInfo > map) {
		final IAttributeInfoMap attrs = new AttributeInfoMapImpl();
		add(attrs,ATTRS_ATTRS);
		final ElementInfo elementInfo = new ElementInfo(
			HtmlTypeEnum.NOSCRIPT,
			attrs,
			new ContentModelSets(SET_FLOW));
		map.put(elementInfo.getType(), elementInfo);
	}
	/** <!ELEMENT OBJECT - - (PARAM | %flow;)*
	 * -- generic embedded object -->
	 * <!ATTLIST OBJECT
	 * %attrs;                              -- %coreattrs, %i18n, %events --
	 * declare     (declare)      #IMPLIED  -- declare but don't instantiate flag --
	 * classid     %URI;          #IMPLIED  -- identifies an implementation --
	 * codebase    %URI;          #IMPLIED  -- base URI for classid, data, archive--
	 * data        %URI;          #IMPLIED  -- reference to object's data --
	 * type        %ContentType;  #IMPLIED  -- content type for data --
	 * codetype    %ContentType;  #IMPLIED  -- content type for code --
	 * archive     CDATA          #IMPLIED  -- space-separated list of URIs --
	 * standby     %Text;         #IMPLIED  -- message to show while loading --
	 * height      %Length;       #IMPLIED  -- override height --
	 * width       %Length;       #IMPLIED  -- override width --
	 * usemap      %URI;          #IMPLIED  -- use client-side image map --
	 * name        CDATA          #IMPLIED  -- submit as part of form --
	 * tabindex    NUMBER         #IMPLIED  -- position in tabbing order --
	 * %reserved;                           -- reserved for possible future use --
	 * >
	 */
	private static void addObject(final Map< HtmlTypeEnum, IElementInfo > map) {
		final IAttributeInfoMap attrs = new AttributeInfoMapImpl();
		add(attrs,ATTRS_ATTRS);
		attrs.put(new AttributeInfoBoolean("declare"));
		attrs.put(new AttributeInfoImpl("classid"));
		attrs.put(new AttributeInfoImpl("codebase"));
		attrs.put(new AttributeInfoImpl("data"));
		attrs.put(new AttributeInfoImpl("type"));
		attrs.put(new AttributeInfoImpl("codetype"));
		attrs.put(new AttributeInfoImpl("archive"));
		attrs.put(new AttributeInfoImpl("standby"));
		attrs.put(new AttributeInfoImpl("height"));
		attrs.put(new AttributeInfoImpl("width"));
		attrs.put(new AttributeInfoImpl("usemap"));
		attrs.put(new AttributeInfoImpl("name"));
		attrs.put(new AttributeInfoImpl("tabindex", AttributeDataType.NUMBER));
		attrs.put(new AttributeInfoImpl("border"));
		attrs.put(new AttributeInfoImpl("hspace"));
		attrs.put(new AttributeInfoImpl("vspace"));
		add(attrs,ATTRS_RESERVED);
		final HtmlTypeSet includeElements = new HtmlTypeSet();
		includeElements.merge(SET_FLOW);
		includeElements.add(HtmlTypeEnum.PARAM);
		final ElementInfo elementInfo = new ElementInfo(
			HtmlTypeEnum.OBJECT,
			attrs,
			new ContentModelSets(includeElements));
		map.put(elementInfo.getType(), elementInfo);
	}
	/** <!ELEMENT OL - - (LI)+                 -- ordered list -->
	 * <!ATTLIST OL
	 * %attrs;                              -- %coreattrs, %i18n, %events --
	 * type        %OLStyle;      #IMPLIED  -- numbering style --
	 * compact     (compact)      #IMPLIED  -- reduced interitem spacing --
	 * start       NUMBER         #IMPLIED  -- starting sequence number --
	 * >
	 * <!ENTITY % OLStyle "CDATA"      -- constrained to: "(1|a|A|i|I)" -->
	 */
	private static void addOl(final Map< HtmlTypeEnum, IElementInfo > map) {
		final IAttributeInfoMap attrs = new AttributeInfoMapImpl();
		add(attrs,ATTRS_ATTRS);
		attrs.put(new AttributeInfoEnumeration("type", OlStyleEnum.class));
		attrs.put(ATTR_COMPACT);
		attrs.put(new AttributeInfoImpl("start", AttributeDataType.NUMBER));
		final HtmlTypeSet includeElements = new HtmlTypeSet();
		includeElements.add(HtmlTypeEnum.LI);
		final ElementInfo elementInfo = new ElementInfo(
			HtmlTypeEnum.OL,
			attrs,
			new ContentModelSets(includeElements));
		map.put(elementInfo.getType(), elementInfo);
	}
	/**<!ELEMENT OPTGROUP - - (OPTION)+ -- option group -->
	 * <!ATTLIST OPTGROUP
	 * %attrs;                              -- %coreattrs, %i18n, %events --
	 * disabled    (disabled)     #IMPLIED  -- unavailable in this context --
	 * label       %Text;         #REQUIRED -- for use in hierarchical menus --
	 * >
	 */
	private static void addOptGroup(final Map< HtmlTypeEnum, IElementInfo > map){
		final IAttributeInfoMap attrs = new AttributeInfoMapImpl();
		add(attrs,ATTRS_ATTRS);
		attrs.put(ATTR_DISABLED);
		attrs.put(new AttributeInfoImpl("label"));
		final HtmlTypeSet includeElements = new HtmlTypeSet();
		includeElements.add(HtmlTypeEnum.OPTION);
		final ElementInfo elementInfo = new ElementInfo(
			HtmlTypeEnum.OPTGROUP,
			attrs,
			new ContentModelSets(includeElements));
		map.put(elementInfo.getType(), elementInfo);
	}
	/**<!ELEMENT OPTION - O (#PCDATA)         -- selectable choice -->
	 * <!ATTLIST OPTION
	 * %attrs;                              -- %coreattrs, %i18n, %events --
	 * selected    (selected)     #IMPLIED
	 * disabled    (disabled)     #IMPLIED  -- unavailable in this context --
	 * label       %Text;         #IMPLIED  -- for use in hierarchical menus --
	 * value       CDATA          #IMPLIED  -- defaults to element content --
	 * >
	 */
	private static void addOption(final Map< HtmlTypeEnum, IElementInfo > map){
		final IAttributeInfoMap attrs = new AttributeInfoMapImpl();
		add(attrs,ATTRS_ATTRS);
		attrs.put(new AttributeInfoBoolean("selected"));
		attrs.put(ATTR_DISABLED);
		attrs.put(new AttributeInfoImpl("label"));
		attrs.put(new AttributeInfoImpl("value"));
		final HtmlTypeSet includeElements = new HtmlTypeSet();
		includeElements.add(HtmlTypeEnum.TEXT); // Is this how to add #PCDATA???
		final ElementInfo elementInfo = new ElementInfo(
			HtmlTypeEnum.OPTION,
			attrs,
			new ContentModelSets(includeElements),
			true,
			false);
		map.put(elementInfo.getType(), elementInfo);
	}
	/** <!ELEMENT P - O (%inline;)*            -- paragraph -->
	 * <!ATTLIST P
	 * %attrs;                              -- %coreattrs, %i18n, %events --
	 * >
	 */
	private static void addP(final Map< HtmlTypeEnum, IElementInfo > map){
		final IAttributeInfoMap attrs = new AttributeInfoMapImpl();
		add(attrs,ATTRS_ATTRS);
		final ElementInfo elementInfo = new ElementInfo(
			HtmlTypeEnum.P,
			attrs,
			new ContentModelSets(SET_INLINE),
			true,
			false);
		map.put(elementInfo.getType(), elementInfo);
	}
	/**<!ELEMENT PARAM - O EMPTY              -- named property value -->
	 * <!ATTLIST PARAM
	 * id          ID             #IMPLIED  -- document-wide unique id --
	 * name        CDATA          #REQUIRED -- property name --
	 * value       CDATA          #IMPLIED  -- property value --
	 * valuetype   (DATA|REF|OBJECT) DATA   -- How to interpret value --
	 * type        %ContentType;  #IMPLIED  -- content type for value
	 *                                         when valuetype=ref --
	 * >
	 */
	private static void addParam(final Map< HtmlTypeEnum, IElementInfo > map){
		final IAttributeInfoMap attrs = new AttributeInfoMapImpl();
		add(attrs,ATTRS_ATTRS);
		attrs.put(new AttributeInfoImpl("id", AttributeDataType.ID));
		attrs.put(new AttributeInfoImpl(
			"name", AttributeDataType.ID, AttributeDefault.REQUIRED, null));
		attrs.put(new AttributeInfoImpl("value"));
		attrs.put(new AttributeInfoEnumeration("valuetype",
			ParamValueTypeEnum.class,
			AttributeDefault.IMPLIED,
			ParamValueTypeEnum.DATA));
		attrs.put(new AttributeInfoImpl("type"));
		final ElementInfo elementInfo = new ElementInfo(
			HtmlTypeEnum.PARAM,
			attrs,
			IContentModel.EMPTY,
			true,
			false);
		map.put(elementInfo.getType(), elementInfo);
	}
	/** <!ELEMENT PRE - - (%inline;)* -(%pre.exclusion;) -- preformatted text -->
	 * <!ATTLIST PRE
	 * %attrs;                              -- %coreattrs, %i18n, %events --
	 * width       NUMBER         #IMPLIED
	 * >
	 * <!ENTITY % pre.exclusion "IMG|OBJECT|APPLET|BIG|SMALL|SUB|SUP|FONT|BASEFONT">
	 */
	private static void addPre(final Map< HtmlTypeEnum, IElementInfo > map) {
		final IAttributeInfoMap attrs = new AttributeInfoMapImpl();
		add(attrs,ATTRS_ATTRS);
		attrs.put(new AttributeInfoImpl("width", AttributeDataType.NUMBER));
		final HtmlTypeSet excludeElements = new HtmlTypeSet();
		excludeElements.add(HtmlTypeEnum.IMG);
		excludeElements.add(HtmlTypeEnum.OBJECT);
		excludeElements.add(HtmlTypeEnum.APPLET);
		excludeElements.add(HtmlTypeEnum.BIG);
		excludeElements.add(HtmlTypeEnum.SMALL);
		excludeElements.add(HtmlTypeEnum.SUB);
		excludeElements.add(HtmlTypeEnum.SUP);
		excludeElements.add(HtmlTypeEnum.FONT);
		excludeElements.add(HtmlTypeEnum.BASEFONT);
		final ElementInfo elementInfo = new ElementInfo(
			HtmlTypeEnum.PRE,
			attrs,
			new ContentModelSets(SET_INLINE, excludeElements));
		map.put(elementInfo.getType(), elementInfo);
	}
	/** <!ELEMENT Q - - (%inline;)*            -- short inline quotation -->
	 * <!ATTLIST Q
	 * %attrs;                              -- %coreattrs, %i18n, %events --
	 * cite        %URI;          #IMPLIED  -- URI for source document or msg --
	 * >
	 */
	private static void addQ(final Map< HtmlTypeEnum, IElementInfo > map) {
		final IAttributeInfoMap attrs = new AttributeInfoMapImpl();
		add(attrs,ATTRS_ATTRS);
		attrs.put(new AttributeInfoImpl("cite"));
		final ElementInfo elementInfo = new ElementInfo(
			HtmlTypeEnum.Q,
			attrs,
			new ContentModelSets(SET_INLINE));
		map.put(elementInfo.getType(), elementInfo);
	}
	/**
	 * <!ENTITY % phrase "EM | STRONG | DFN | CODE |
	 * SAMP | KBD | VAR | CITE | ABBR | ACRONYM" >
	 * <!ELEMENT (%fontstyle;|%phrase;) - - (%inline;)*>
	 * <!ATTLIST (%fontstyle;|%phrase;)
	 * %attrs;                              -- %coreattrs, %i18n, %events --
	 * >
	 */
	private static void addSamp(final Map< HtmlTypeEnum, IElementInfo > map) {
		final ElementInfo elementInfo = new ElementInfo(
			HtmlTypeEnum.SAMP, ATTRS_ATTRS,
			new ContentModelSets(SET_INLINE,null));
		map.put(elementInfo.getType(), elementInfo);
	}
	/** <!ELEMENT SCRIPT - - %Script;          -- script statements -->
	 * <!ATTLIST SCRIPT
	 * charset     %Charset;      #IMPLIED  -- char encoding of linked resource --
	 * type        %ContentType;  #REQUIRED -- content type of script language --
	 * language    CDATA          #IMPLIED  -- predefined script language name --
	 * src         %URI;          #IMPLIED  -- URI for an external script --
	 * defer       (defer)        #IMPLIED  -- UA may defer execution of script --
	 * event       CDATA          #IMPLIED  -- reserved for possible future use --
	 * for         %URI;          #IMPLIED  -- reserved for possible future use --
	 * >
	 * <!ENTITY % Script "CDATA" -- script expression -->
	 */
	private static void addScript(final Map< HtmlTypeEnum, IElementInfo > map) {
		final IAttributeInfoMap attrs = new AttributeInfoMapImpl();
		attrs.put(new AttributeInfoImpl("charset"));
		attrs.put(new AttributeInfoImpl("type"));
		attrs.put(new AttributeInfoImpl("language"));
		attrs.put(new AttributeInfoImpl("src"));
		attrs.put(new AttributeInfoBoolean("defer"));
		attrs.put(new AttributeInfoImpl("event"));
		attrs.put(new AttributeInfoImpl("for"));
		final HtmlTypeSet includeElements = new HtmlTypeSet();
		includeElements.add(HtmlTypeEnum.TEXT); // Is this how to add #PCDATA???
		final ElementInfo elementInfo = new ElementInfo(
			HtmlTypeEnum.SCRIPT,
			attrs,
			new ContentModelSets(includeElements),
			true,
			false);
		map.put(elementInfo.getType(), elementInfo);
	}
	/** <!ELEMENT SELECT - - (OPTGROUP|OPTION)+ -- option selector -->
	 * <!ATTLIST SELECT
	 * %attrs;                              -- %coreattrs, %i18n, %events --
	 * name        CDATA          #IMPLIED  -- field name --
	 * size        NUMBER         #IMPLIED  -- rows visible --
	 * multiple    (multiple)     #IMPLIED  -- default is single selection --
	 * disabled    (disabled)     #IMPLIED  -- unavailable in this context --
	 * tabindex    NUMBER         #IMPLIED  -- position in tabbing order --
	 * onfocus     %Script;       #IMPLIED  -- the element got the focus --
	 * onblur      %Script;       #IMPLIED  -- the element lost the focus --
	 * onchange    %Script;       #IMPLIED  -- the element value was changed --
	 * %reserved;                           -- reserved for possible future use --
	 * >
	 */
	private static void addSelect(final Map< HtmlTypeEnum, IElementInfo > map) {
		final IAttributeInfoMap attrs = new AttributeInfoMapImpl();
		add(attrs,ATTRS_ATTRS);
		attrs.put(new AttributeInfoImpl("name"));
		attrs.put(new AttributeInfoImpl("size", AttributeDataType.NUMBER));
		attrs.put(new AttributeInfoBoolean("multiple"));
		attrs.put(ATTR_DISABLED);
		attrs.put(new AttributeInfoImpl("tabindex", AttributeDataType.NUMBER));
		attrs.put(new AttributeInfoImpl("onfocus"));
		attrs.put(new AttributeInfoImpl("onblur"));
		attrs.put(new AttributeInfoImpl("onchange"));
		add(attrs,ATTRS_RESERVED);
		final HtmlTypeSet includeElements = new HtmlTypeSet();
		includeElements.add(HtmlTypeEnum.OPTGROUP);
		includeElements.add(HtmlTypeEnum.OPTION);
		final ElementInfo elementInfo = new ElementInfo(
			HtmlTypeEnum.SELECT,
			attrs,
			new ContentModelSets(includeElements));
		map.put(elementInfo.getType(), elementInfo);
	}
	/** <!ENTITY % fontstyle "TT | I | B | BIG | SMALL">
	 * <!ELEMENT (%fontstyle;|%phrase;) - - (%inline;)*>
	 * <!ATTLIST (%fontstyle;|%phrase;)
	 * %attrs;                              -- %coreattrs, %i18n, %events --
	 * >
	 */
	private static void addSmall(final Map< HtmlTypeEnum, IElementInfo > map) {
		final ElementInfo elementInfo = new ElementInfo(
			HtmlTypeEnum.SMALL, ATTRS_ATTRS,
			new ContentModelSets(SET_INLINE,null));
		map.put(elementInfo.getType(), elementInfo);
	}
	/** <!ELEMENT SPAN - - (%inline;)*         -- generic language/style container -->
	 * <!ATTLIST SPAN
	 * %attrs;                              -- %coreattrs, %i18n, %events --
	 * %reserved;			       -- reserved for possible future use --
	 * >
	 */
	private static void addSpan(final Map< HtmlTypeEnum, IElementInfo > map) {
		final IAttributeInfoMap attrs = new AttributeInfoMapImpl();
		add(attrs,ATTRS_ATTRS);
		add(attrs,ATTRS_RESERVED);
		final ElementInfo elementInfo = new ElementInfo(
			HtmlTypeEnum.SPAN,
			attrs,
			new ContentModelSets(SET_INLINE));
		map.put(elementInfo.getType(), elementInfo);
	}
	/**
	 * <!ENTITY % phrase "EM | STRONG | DFN | CODE |
	 * SAMP | KBD | VAR | CITE | ABBR | ACRONYM" >
	 * <!ELEMENT (%fontstyle;|%phrase;) - - (%inline;)*>
	 * <!ATTLIST (%fontstyle;|%phrase;)
	 * %attrs;                              -- %coreattrs, %i18n, %events --
	 * >
	 */
	private static void addStrong(final Map< HtmlTypeEnum, IElementInfo > map) {
		final ElementInfo elementInfo = new ElementInfo(
			HtmlTypeEnum.STRONG, ATTRS_ATTRS,
			new ContentModelSets(SET_INLINE,null));
		map.put(elementInfo.getType(), elementInfo);
	}
	/** <!ELEMENT STYLE - - %StyleSheet        -- style info -->
	 * <!ATTLIST STYLE
	 * %i18n;                               -- lang, dir, for use with title --
	 * type        %ContentType;  #REQUIRED -- content type of style language --
	 * media       %MediaDesc;    #IMPLIED  -- designed for use with these media --
	 * title       %Text;         #IMPLIED  -- advisory title --
	 * >
	 * <!ENTITY % StyleSheet "CDATA" -- style sheet data -->
	 */
	private static void addStyle(final Map< HtmlTypeEnum, IElementInfo > map) {
		final IAttributeInfoMap attrs = new AttributeInfoMapImpl();
		add(attrs,ATTRS_I18N);
		attrs.put(new AttributeInfoImpl(
			"type", AttributeDataType.CDATA, AttributeDefault.REQUIRED, null));
		attrs.put(new AttributeInfoImpl("media"));
		attrs.put(new AttributeInfoImpl("title"));
		final HtmlTypeSet includeElements = new HtmlTypeSet();
		includeElements.add(HtmlTypeEnum.TEXT); // Is this how to add #PCDATA???
		final ElementInfo elementInfo = new ElementInfo(
			HtmlTypeEnum.STYLE,
			attrs,
			new ContentModelSets(includeElements));
		map.put(elementInfo.getType(), elementInfo);
	}
	/** <!ELEMENT (SUB|SUP) - - (%inline;)*    -- subscript, superscript -->
	 * <!ATTLIST (SUB|SUP)
	 * %attrs;                              -- %coreattrs, %i18n, %events --
	 * >
	 */
	private static void addSubAndSup(final Map< HtmlTypeEnum, IElementInfo > map) {
		final IAttributeInfoMap attrs = new AttributeInfoMapImpl();
		add(attrs,ATTRS_ATTRS);
		final ElementInfo sub = new ElementInfo(
			HtmlTypeEnum.SUB,
			attrs,
			new ContentModelSets(SET_INLINE));
		map.put(sub.getType(), sub);
		final ElementInfo sup = new ElementInfo(
			HtmlTypeEnum.SUP,
			attrs,
			new ContentModelSets(SET_INLINE));
		map.put(sup.getType(), sup);
	}
	/** <!ELEMENT TABLE - - (CAPTION?, (COL*|COLGROUP*), THEAD?, TFOOT?, TBODY+)>
	 * <!ATTLIST TABLE                        -- table element --
	 * %attrs;                              -- %coreattrs, %i18n, %events --
	 * summary     %Text;         #IMPLIED  -- purpose/structure for speech output--
	 * width       %Length;       #IMPLIED  -- table width --
	 * border      %Pixels;       #IMPLIED  -- controls frame width around table --
	 * frame       %TFrame;       #IMPLIED  -- which parts of frame to render --
	 * rules       %TRules;       #IMPLIED  -- rulings between rows and cols --
	 * cellspacing %Length;       #IMPLIED  -- spacing between cells --
	 * cellpadding %Length;       #IMPLIED  -- spacing within cells --
	 * %reserved;                           -- reserved for possible future use --
	 * datapagesize CDATA         #IMPLIED  -- reserved for possible future use --
	 * >
	 * <!ENTITY % TFrame "(void|above|below|hsides|lhs|rhs|vsides|box|border)">
	 * <!ENTITY % TRules "(none | groups | rows | cols | all)">
	 */
	private static void addTable(final Map< HtmlTypeEnum, IElementInfo > map) {
		final IAttributeInfoMap attrs = new AttributeInfoMapImpl();
		add(attrs,ATTRS_ATTRS);
		attrs.put(new AttributeInfoImpl("summary"));
		attrs.put(new AttributeInfoImpl("width"));
		attrs.put(new AttributeInfoImpl("border"));
		attrs.put(new AttributeInfoEnumeration("frame", TableFrameEnum.class));
		attrs.put(new AttributeInfoEnumeration("rules", TableRulesEnum.class));
		attrs.put(new AttributeInfoImpl("cellspacing"));
		attrs.put(new AttributeInfoImpl("cellpadding"));
		add(attrs, ATTRS_RESERVED);
		attrs.put(new AttributeInfoImpl("datapagesize"));
		final HtmlTypeSet includeElements = new HtmlTypeSet();
		includeElements.add(HtmlTypeEnum.CAPTION);
		includeElements.add(HtmlTypeEnum.COL);
		includeElements.add(HtmlTypeEnum.COLGROUP);
		includeElements.add(HtmlTypeEnum.THEAD);
		includeElements.add(HtmlTypeEnum.TFOOT);
		includeElements.add(HtmlTypeEnum.TBODY);
		final ElementInfo elementInfo = new ElementInfo(
			HtmlTypeEnum.TABLE,
			attrs,
			new ContentModelSets(includeElements));
		map.put(elementInfo.getType(), elementInfo);
	}
	/** <!ELEMENT TBODY    O O (TR)+           -- table body -->
	 * <!ELEMENT TFOOT    - O (TR)+           -- table footer -->
	 * <!ELEMENT THEAD    - O (TR)+           -- table header -->
	 * <!ATTLIST (THEAD|TBODY|TFOOT)          -- table section --
	 * %attrs;                              -- %coreattrs, %i18n, %events --
	 * %cellhalign;                         -- horizontal alignment in cells --
	 * %cellvalign;                         -- vertical alignment in cells --
	 * >
	 */
	private static void addTbodyTfootThead(final Map< HtmlTypeEnum, IElementInfo > map) {
		final IAttributeInfoMap attrs = new AttributeInfoMapImpl();
		add(attrs,ATTRS_ATTRS);
		add(attrs,ATTRS_CELL_HALIGN);
		add(attrs,ATTRS_CELL_VALIGN);
		final HtmlTypeSet includeElements = new HtmlTypeSet();
		includeElements.add(HtmlTypeEnum.TR);
		map.put(HtmlTypeEnum.TBODY, new ElementInfo(
			HtmlTypeEnum.TBODY,
			attrs,
			new ContentModelSets(includeElements),
			false,
			false));
		map.put(HtmlTypeEnum.TFOOT, new ElementInfo(
			HtmlTypeEnum.TFOOT,
			attrs,
			new ContentModelSets(includeElements),
			true,
			false));
		map.put(HtmlTypeEnum.THEAD, new ElementInfo(
			HtmlTypeEnum.THEAD,
			attrs,
			new ContentModelSets(includeElements),
			true,
			false));
	}
	/** <!ELEMENT (TH|TD)  - O (%flow;)*       -- table header cell, table data cell-->
	 * <!ATTLIST (TH|TD)                      -- header or data cell --
	 * %attrs;                              -- %coreattrs, %i18n, %events --
	 * abbr        %Text;         #IMPLIED  -- abbreviation for header cell --
	 * axis        CDATA          #IMPLIED  -- comma-separated list of related headers--
	 * headers     IDREFS         #IMPLIED  -- list of id's for header cells --
	 * scope       %Scope;        #IMPLIED  -- scope covered by header cells --
	 * rowspan     NUMBER         1         -- number of rows spanned by cell --
	 * colspan     NUMBER         1         -- number of cols spanned by cell --
	 * %cellhalign;                         -- horizontal alignment in cells --
	 * %cellvalign;                         -- vertical alignment in cells --
	 * >
	 * <!ENTITY % Scope "(row|col|rowgroup|colgroup)">
	 */
	private static void addTdAndTh(final Map< HtmlTypeEnum, IElementInfo > map) {
		final IAttributeInfoMap attrs = new AttributeInfoMapImpl();
		add(attrs,ATTRS_ATTRS);
		attrs.put(new AttributeInfoImpl("abbr"));
		attrs.put(new AttributeInfoImpl("axis"));
		attrs.put(new AttributeInfoImpl("headers", AttributeDataType.IDREFS));
		attrs.put(new AttributeInfoEnumeration(
			"scope", TableCellScopeEnum.class));
		attrs.put(new AttributeInfoImpl("rowspan",
			AttributeDataType.NUMBER, AttributeDefault.IMPLIED, "1"));
		attrs.put(new AttributeInfoImpl("colspan",
			AttributeDataType.NUMBER, AttributeDefault.IMPLIED, "1"));
		add(attrs,ATTRS_CELL_HALIGN);
		add(attrs,ATTRS_CELL_VALIGN);
		map.put(HtmlTypeEnum.TD, new ElementInfo(
			HtmlTypeEnum.TD,
			attrs,
			new ContentModelSets(SET_FLOW),
			true,
			false));
		map.put(HtmlTypeEnum.TH, new ElementInfo(
			HtmlTypeEnum.TH,
			attrs,
			new ContentModelSets(SET_FLOW),
			true,
			false));
	}
	/** <!ELEMENT TEXTAREA - - (#PCDATA)       -- multi-line text field -->
	 * <!ATTLIST TEXTAREA
	 * %attrs;                              -- %coreattrs, %i18n, %events --
	 * name        CDATA          #IMPLIED
	 * rows        NUMBER         #REQUIRED
	 * cols        NUMBER         #REQUIRED
	 * disabled    (disabled)     #IMPLIED  -- unavailable in this context --
	 * readonly    (readonly)     #IMPLIED
	 * tabindex    NUMBER         #IMPLIED  -- position in tabbing order --
	 * accesskey   %Character;    #IMPLIED  -- accessibility key character --
	 * onfocus     %Script;       #IMPLIED  -- the element got the focus --
	 * onblur      %Script;       #IMPLIED  -- the element lost the focus --
	 * onselect    %Script;       #IMPLIED  -- some text was selected --
	 * onchange    %Script;       #IMPLIED  -- the element value was changed --
	 * %reserved;                           -- reserved for possible future use --
	 * >
	 */
	private static void addTextArea(final Map< HtmlTypeEnum, IElementInfo > map) {
		final IAttributeInfoMap attrs = new AttributeInfoMapImpl();
		add(attrs,ATTRS_ATTRS);
		attrs.put(new AttributeInfoImpl("name"));
		attrs.put(new AttributeInfoImpl("rows", AttributeDataType.NUMBER));
		attrs.put(new AttributeInfoImpl("cols", AttributeDataType.NUMBER));
		attrs.put(ATTR_DISABLED);
		attrs.put(ATTR_READONLY);
		attrs.put(new AttributeInfoImpl("tabindex", AttributeDataType.NUMBER));
		attrs.put(new AttributeInfoImpl("accesskey"));
		attrs.put(new AttributeInfoImpl("onfocus"));
		attrs.put(new AttributeInfoImpl("onblur"));
		attrs.put(new AttributeInfoImpl("onselect"));
		attrs.put(new AttributeInfoImpl("onchange"));
		add(attrs, ATTRS_RESERVED);
		final HtmlTypeSet includeElements = new HtmlTypeSet();
		includeElements.add(HtmlTypeEnum.TEXT); // Is this how to add #PCDATA???
		final ElementInfo elementInfo = new ElementInfo(
			HtmlTypeEnum.TEXTAREA,
			attrs,
			new ContentModelSets(includeElements));
		map.put(elementInfo.getType(), elementInfo);
	}
	/** <!ELEMENT TITLE - - (#PCDATA) -(%head.misc;) -- document title -->
	 * <!ATTLIST TITLE %i18n>
	 */
	private static void addTitle(final Map< HtmlTypeEnum, IElementInfo > map) {
		final HtmlTypeSet includeElements = new HtmlTypeSet();
		includeElements.add(HtmlTypeEnum.TEXT); // Is this how to add #PCDATA???
		includeElements.merge(SET_HEAD_MISC);
		final ElementInfo elementInfo = new ElementInfo(
			HtmlTypeEnum.TITLE,
			ATTRS_I18N,
			new ContentModelSets(SET_HEAD_MISC));
		map.put(elementInfo.getType(), elementInfo);
	}
	/** <!ELEMENT TR       - O (TH|TD)+        -- table row -->
	 * <!ATTLIST TR                           -- table row --
	 * %attrs;                              -- %coreattrs, %i18n, %events --
	 * %cellhalign;                         -- horizontal alignment in cells --
	 * %cellvalign;                         -- vertical alignment in cells --
	 * >
	 */
	private static void addTr(final Map< HtmlTypeEnum, IElementInfo > map) {
		final IAttributeInfoMap attrs = new AttributeInfoMapImpl();
		add(attrs,ATTRS_ATTRS);
		add(attrs,ATTRS_CELL_HALIGN);
		add(attrs,ATTRS_CELL_VALIGN);
		final HtmlTypeSet includeElements = new HtmlTypeSet();
		includeElements.add(HtmlTypeEnum.TH);
		includeElements.add(HtmlTypeEnum.TD);
		map.put(HtmlTypeEnum.TR, new ElementInfo(
			HtmlTypeEnum.TR,
			attrs,
			new ContentModelSets(includeElements),
			true,
			false));
	}
	/** <!ENTITY % fontstyle "TT | I | B | BIG | SMALL">
	 * <!ELEMENT (%fontstyle;|%phrase;) - - (%inline;)*>
	 * <!ATTLIST (%fontstyle;|%phrase;)
	 * %attrs;                              -- %coreattrs, %i18n, %events --
	 * >
	 */
	private static void addTt(final Map< HtmlTypeEnum, IElementInfo > map) {
		final ElementInfo elementInfo = new ElementInfo(
			HtmlTypeEnum.TT, ATTRS_ATTRS,
			new ContentModelSets(SET_INLINE,null));
		map.put(elementInfo.getType(), elementInfo);
	}
	/** <!ELEMENT UL - - (LI)+                 -- ordered list -->
	 * <!ATTLIST UL
	 * %attrs;                              -- %coreattrs, %i18n, %events --
	 * type        %ULStyle;      #IMPLIED  -- bullet  style --
	 * compact     (compact)      #IMPLIED  -- reduced interitem spacing --
	 * >
	 * <!ENTITY % ULStyle "(disc|square|circle)">
	 */
	private static void addUl(final Map< HtmlTypeEnum, IElementInfo > map) {
		final IAttributeInfoMap attrs = new AttributeInfoMapImpl();
		add(attrs,ATTRS_ATTRS);
		attrs.put(new AttributeInfoEnumeration("type", UlStyleEnum.class));
		attrs.put(ATTR_COMPACT);
		final HtmlTypeSet includeElements = new HtmlTypeSet();
		includeElements.add(HtmlTypeEnum.LI);
		final ElementInfo elementInfo = new ElementInfo(
			HtmlTypeEnum.UL,
			attrs,
			new ContentModelSets(includeElements));
		map.put(elementInfo.getType(), elementInfo);
	}
	private static void addHtml(final Map< HtmlTypeEnum, IElementInfo > map) {
		final IAttributeInfoMap attrs = new AttributeInfoMapImpl();
		final AttributeInfoImpl version = new AttributeInfoImpl(
			"version",
			AttributeDataType.CDATA,
			AttributeDefault.FIXED,
			"-//W3C//DTD HTML 4.01 Transitional//EN");
		attrs.put(version);
		add(attrs, ATTRS_I18N);
		final HtmlTypeSet includeElements = new HtmlTypeSet();
		includeElements.add(HtmlTypeEnum.HEAD);
		includeElements.add(HtmlTypeEnum.BODY);
		final ElementInfo elementInfo = new ElementInfo(
			HtmlTypeEnum.HTML, attrs, new ContentModelSets(includeElements,null));
		map.put(elementInfo.getType(), elementInfo);
	}
	public static ISchema getInstance(){
		return s_instance;
	}
	/**
	 * <!ENTITY % attrs "%coreattrs; %i18n; %events;">
	 */
	private static IAttributeInfoMap createAttrsAttrs() {
		final IAttributeInfoMap map = new AttributeInfoMapImpl();
		add(map,ATTRS_CORE);
		add(map,ATTRS_I18N);
		add(map,ATTRS_EVENTS);
		return AttributeInfoMapImpl.createUnmodifiable(map);
	}
	/**
	 * <!ENTITY % coreattrs
	 *     "id    ID             #IMPLIED  -- document-wide unique id --
	 *     class  CDATA          #IMPLIED  -- space-separated list of classes --
	 *     style  %StyleSheet;   #IMPLIED  -- associated style info --
	 *     title  %Text;         #IMPLIED  -- advisory title --"
	 *     >
	 */
	private static IAttributeInfoMap createCoreAttrs() {
		final IAttributeInfoMap map = new AttributeInfoMapImpl();
		map.put(new AttributeInfoImpl("id", AttributeDataType.ID));
		map.put(new AttributeInfoImpl("class"));
		map.put(new AttributeInfoImpl("style"));
		map.put(new AttributeInfoImpl("text"));
		return AttributeInfoMapImpl.createUnmodifiable(map);
	}
	/** <!ENTITY % cellhalign
	 * "align      (left|center|right|justify|char) #IMPLIED
	 * char       %Character;    #IMPLIED  -- alignment char, e.g. char=':' --
	 * charoff    %Length;       #IMPLIED  -- offset for alignment char --"
	 * >
	 */
	private static IAttributeInfoMap createCellHAlignAttrs() {
		final IAttributeInfoMap map = new AttributeInfoMapImpl();
		map.put(new AttributeInfoEnumeration("align", CellHAlignEnum.class));
		map.put(new AttributeInfoImpl("char"));
		map.put(new AttributeInfoImpl("charoff"));
		return AttributeInfoMapImpl.createUnmodifiable(map);
	}
	/** <!ENTITY % cellvalign
	 * "valign     (top|middle|bottom|baseline) #IMPLIED"
	 * >
	 */
	private static IAttributeInfoMap createCellVAlignAttrs() {
		final IAttributeInfoMap map = new AttributeInfoMapImpl();
		map.put(new AttributeInfoEnumeration("valign", CellVAlignEnum.class));
		return AttributeInfoMapImpl.createUnmodifiable(map);
	}
	/**
	 * <!ENTITY % events
	 * "onclick     %Script;       #IMPLIED  -- a pointer button was clicked --
	 * ondblclick  %Script;       #IMPLIED  -- a pointer button was double clicked--
	 * onmousedown %Script;       #IMPLIED  -- a pointer button was pressed down --
	 * onmouseup   %Script;       #IMPLIED  -- a pointer button was released --
	 * onmouseover %Script;       #IMPLIED  -- a pointer was moved onto --
	 * onmousemove %Script;       #IMPLIED  -- a pointer was moved within --
	 * onmouseout  %Script;       #IMPLIED  -- a pointer was moved away --
	 * onkeypress  %Script;       #IMPLIED  -- a key was pressed and released --
	 * onkeydown   %Script;       #IMPLIED  -- a key was pressed down --
	 * onkeyup     %Script;       #IMPLIED  -- a key was released --"
	 * >
	 */
	private static IAttributeInfoMap createEventsAttrs() {
		final IAttributeInfoMap map = new AttributeInfoMapImpl();
		map.put(new AttributeInfoImpl("onclick"));
		map.put(new AttributeInfoImpl("ondblclick"));
		map.put(new AttributeInfoImpl("onmousedown"));
		map.put(new AttributeInfoImpl("onmouseup"));
		map.put(new AttributeInfoImpl("onmouseover"));
		map.put(new AttributeInfoImpl("onmousemove"));
		map.put(new AttributeInfoImpl("onmouseout"));
		map.put(new AttributeInfoImpl("onkeypress"));
		map.put(new AttributeInfoImpl("onkeydown"));
		map.put(new AttributeInfoImpl("onkeyup"));
		return AttributeInfoMapImpl.createUnmodifiable(map);
	}
	/**
	 * <!ENTITY % i18n
	 *     "lang %LanguageCode; #IMPLIED  -- language code --
	 *     dir   (ltr|rtl)      #IMPLIED  -- direction for weak/neutral text --"
	 *     >
	 * @return
	 */
	private static IAttributeInfoMap createI18nAttrs() {
		final IAttributeInfoMap map = new AttributeInfoMapImpl();
		map.put(new AttributeInfoImpl("lang",AttributeDataType.NAME));
		map.put(DIR_ATTR_INFO);
		return AttributeInfoMapImpl.createUnmodifiable(map);
	}
	/** <!ENTITY % reserved "">
	 */
	private static IAttributeInfoMap createReservedAttrs() {
		final IAttributeInfoMap map = new AttributeInfoMapImpl();
		return AttributeInfoMapImpl.createUnmodifiable(map);
	}
	/** <!ENTITY % fontstyle "TT | I | B | BIG | SMALL">
 	 */
	private static HtmlTypeSet createFontStyleSet(){
		final HtmlTypeSet set = new HtmlTypeSet();
		set.add(HtmlTypeEnum.TT);
		set.add(HtmlTypeEnum.I);
		set.add(HtmlTypeEnum.B);
		set.add(HtmlTypeEnum.BIG);
		set.add(HtmlTypeEnum.SMALL);
		return set;
	}
	/** <!ENTITY % phrase "EM | STRONG | DFN | CODE |
	 * SAMP | KBD | VAR | CITE | ABBR | ACRONYM" >
	 */ 
	private static HtmlTypeSet createPhraseSet(){
		final HtmlTypeSet set = new HtmlTypeSet();
		set.add(HtmlTypeEnum.EM);
		set.add(HtmlTypeEnum.STRONG);
		set.add(HtmlTypeEnum.DFN);
		set.add(HtmlTypeEnum.CODE);
		set.add(HtmlTypeEnum.SAMP);
		set.add(HtmlTypeEnum.KBD);
		set.add(HtmlTypeEnum.VAR);
		set.add(HtmlTypeEnum.CITE);
		set.add(HtmlTypeEnum.ABBR);
		return set;
	}
	/** <!ENTITY % special
	 * "A | IMG | APPLET | OBJECT | FONT | BASEFONT | BR | SCRIPT |
	 * MAP | Q | SUB | SUP | SPAN | BDO | IFRAME">
     */
	private static HtmlTypeSet createSpecialSet(){
		final HtmlTypeSet set = new HtmlTypeSet();
		set.add(HtmlTypeEnum.A);
		set.add(HtmlTypeEnum.IMG);
		set.add(HtmlTypeEnum.APPLET);
		set.add(HtmlTypeEnum.OBJECT);
		set.add(HtmlTypeEnum.FONT);
		set.add(HtmlTypeEnum.BASEFONT);
		set.add(HtmlTypeEnum.BR);
		set.add(HtmlTypeEnum.SCRIPT);
		set.add(HtmlTypeEnum.MAP);
		set.add(HtmlTypeEnum.Q);
		set.add(HtmlTypeEnum.SUB);
		set.add(HtmlTypeEnum.SUP);
		set.add(HtmlTypeEnum.SPAN);
		set.add(HtmlTypeEnum.BDO);
		set.add(HtmlTypeEnum.IFRAME);
		return set;
	}
	/** <!ENTITY % formctrl "INPUT | SELECT | TEXTAREA | LABEL | BUTTON">
	 */
	private static HtmlTypeSet createFormCtrlSet(){
		final HtmlTypeSet set = new HtmlTypeSet();
		set.add(HtmlTypeEnum.INPUT);
		set.add(HtmlTypeEnum.SELECT);
		set.add(HtmlTypeEnum.TEXTAREA);
		set.add(HtmlTypeEnum.LABEL);
		set.add(HtmlTypeEnum.BUTTON);
		return set;
	}
	/** <!ENTITY % heading "H1|H2|H3|H4|H5|H6">
	 */
	private static HtmlTypeSet createHeadingSet(){
		final HtmlTypeSet set = new HtmlTypeSet();
		set.add(HtmlTypeEnum.H1);
		set.add(HtmlTypeEnum.H2);
		set.add(HtmlTypeEnum.H3);
		set.add(HtmlTypeEnum.H4);
		set.add(HtmlTypeEnum.H5);
		set.add(HtmlTypeEnum.H6);
		return set;
	}
	/** <!ENTITY % head.content "TITLE & ISINDEX? & BASE?">
	 */
	private static HtmlTypeSet createHeadContentSet() {
		final HtmlTypeSet set = new HtmlTypeSet();
		set.add(HtmlTypeEnum.TITLE);
		set.add(HtmlTypeEnum.ISINDEX);
		set.add(HtmlTypeEnum.BASE);
		return set;		
	}
	/** <!ENTITY % head.misc "SCRIPT|STYLE|META|LINK|OBJECT" -- repeatable head elements -->
	 */
	private static HtmlTypeSet createHeadMiscSet() {
		final HtmlTypeSet set = new HtmlTypeSet();
		set.add(HtmlTypeEnum.SCRIPT);
		set.add(HtmlTypeEnum.STYLE);
		set.add(HtmlTypeEnum.META);
		set.add(HtmlTypeEnum.LINK);
		set.add(HtmlTypeEnum.OBJECT);
		return set;		
	}
	/** <!ENTITY % list "UL | OL |  DIR | MENU">
	 */
	private static HtmlTypeSet createListSet(){
		final HtmlTypeSet set = new HtmlTypeSet();
		set.add(HtmlTypeEnum.UL);
		set.add(HtmlTypeEnum.OL);
		set.add(HtmlTypeEnum.DIR);
		set.add(HtmlTypeEnum.MENU);
		return set;
	}
	/** <!ENTITY % preformatted "PRE">
	 */
	private static HtmlTypeSet createPreformattedSet(){
		final HtmlTypeSet set = new HtmlTypeSet();
		set.add(HtmlTypeEnum.PRE);
		return set;
	}
	/** <!ENTITY % inline "#PCDATA | %fontstyle; | %phrase; | %special; |
	 * %formctrl;">
	 */
	private static HtmlTypeSet createInlineSet(){
		final HtmlTypeSet set = new HtmlTypeSet();
		set.add(HtmlTypeEnum.TEXT); // Is this how to add #PCDATA???
		set.merge(SET_FONT_STYLE);
		set.merge(SET_FORM_CTRL);
		set.merge(SET_PHRASE);
		set.merge(SET_SPECIAL);
		return set;
	}
	/** <!ENTITY % block
	 * "P | %heading; | %list; | %preformatted; | DL | DIV | CENTER |
	 * NOSCRIPT | NOFRAMES | BLOCKQUOTE | FORM | ISINDEX | HR |
	 * TABLE | FIELDSET | ADDRESS">
	 */
	private static HtmlTypeSet createBlockSet(){
		final HtmlTypeSet set = new HtmlTypeSet();
		set.add(HtmlTypeEnum.P);
		set.merge(SET_HEADING);
		set.merge(SET_LIST);
		set.merge(SET_PREFORMATTED);
		set.add(HtmlTypeEnum.DL);
		set.add(HtmlTypeEnum.DIV);
		set.add(HtmlTypeEnum.CENTER);
		set.add(HtmlTypeEnum.NOSCRIPT);
		set.add(HtmlTypeEnum.NOFRAMES);
		set.add(HtmlTypeEnum.BLOCKQUOTE);
		set.add(HtmlTypeEnum.FORM);
		set.add(HtmlTypeEnum.ISINDEX);
		set.add(HtmlTypeEnum.HR);
		set.add(HtmlTypeEnum.TABLE);
		set.add(HtmlTypeEnum.FIELDSET);
		set.add(HtmlTypeEnum.ADDRESS);
		return set;
	}
	/** <!ENTITY % flow "%block; | %inline;">
	 */
	private static HtmlTypeSet createFlowSet(){
		final HtmlTypeSet set = new HtmlTypeSet();
		set.merge(SET_BLOCK);
		set.merge(SET_INLINE);
		return set;
	}
	private static void add(IAttributeInfoMap destination, IAttributeInfoMap source){
		for (final IAttributeInfo attr:source){
			destination.put(attr);
		}
	}
}
