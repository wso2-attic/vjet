var browserMappings = {
	'abbr' :   'HTMLAbbrElement',
	'acronym' :   'HTMLAcronymElement',
	'address' :   'HTMLAddressElement',
	'a' :   'HTMLAnchorElement',
	'applet' :   'HTMLAppletElement',
	'area' :   'HTMLAreaElement',
	'article' :   'HTMLArticleElement',
	'aside' :   'HTMLAsideElement',
	'audio' :   'HTMLAudioElement',
	'b' :   'HTMLBElement',
	'base' :   'HTMLBaseElement',
	'basefont' :   'HTMLBaseFontElement',
	'bb' :   'HTMLBbElement',
	'bdo' :   'HTMLBDOElement',
	'blockquote' :   'HTMLBlockquoteElement',
	'body' :   'HTMLBodyElement',
	'br' :   'HTMLBRElement',
	'button' :   'HTMLButtonElement',
	'canvas' :   'HTMLCanvasElement',
	'center' :   'HTMLCenterElement',
	'caption' :   'HTMLTableCaptionElement',
	'caption' :   'HTMLTableCaptionElement',
	'cite' :   'HTMLCiteElement',
	'code' :   'HTMLCodeElement',
	'col' :   'HTMLTableColElement',
	'colgroup' :   'HTMLColgroupElement',
	'dd' :   'HTMLDdElement',
	'del' :   'HTMLDelElement',
	'details' :   'HTMLDetailsElement',
	'dfn' :   'HTMLDfnElement',
	'dir' :   'HTMLDirectoryElement',
	'div' :   'HTMLDivElement',
	'dl' :   'HTMLDListElement',
	'dt' :   'HTMLDtElement',
	'datagrid' :   'HTMLDataGridElement',
	'datalist' :   'HTMLDataListElement',
	'details' :   'HTMLDetailsElement',
	'dialog' :   'HTMLDialogElement',
	'em' :   'HTMLEmElement',
	'embed' :   'HTMLEmbedElement',
	'fieldset' :   'HTMLFieldSetElement',
	'figure' :   'HTMLFigureElement',
	'font' :   'HTMLFontElement',
	'footer' :   'HTMLFooterElement',
	'form' :   'HTMLFormElement',
	'frame' :   'HTMLFrameElement',
	'head' :   'HTMLHeadElement',
	'header' :   'HTMLHeaderElement',
	'hgroup' :   'HTMLHGroupElement',
	'h1' :   'HTMLHeadingElement',
	'h2' :   'HTMLHeadingElement',
	'h3' :   'HTMLHeadingElement',
	'h4' :   'HTMLHeadingElement',
	'h5' :   'HTMLHeadingElement',
	'h6' :   'HTMLHeadingElement',
	'hr' :   'HTMLHRElement',
	'html' :   'HTMLHtmlElement',
	'i' :   'HTMLIElement',
	'iframe' :   'HTMLIFrameElement',
	'img' :   'HTMLImageElement',
	'input' :   'HTMLInputElement',
	'ins' :   'HTMLInsElement',
	'isindex' :   'HTMLIsIndexElement',
	'kbd' :   'HTMLKbdElement',
	'keygen' :   'HTMLKeyGenElement',
	'label' :   'HTMLLabelElement',
	'legend' :   'HTMLLegendElement',
	'li' :   'HTMLLIElement',
	'link' :   'HTMLLinkElement',
	'map' :   'HTMLMapElement',
	'mark' :   'HTMLMarkElement',
	'marquee' :   'HTMLMarqueeElement',
	'menu' :   'HTMLMenuElement',
	'meta' :   'HTMLMetaElement',
	'meter' :   'HTMLMeterElement',
	'mod' :   'HTMLModElement',
	'nav' :   'HTMLNavElement',
	'nobr' :   'HTMLNoBrElement',
	'noframes' :   'HTMLNoFramesElement',
	'noscript' :   'HTMLNoScriptElement',
	'object' :   'HTMLObjectElement',
	'ol' :   'HTMLOListElement',
	'optgroup' :   'HTMLOptGroupElement',
	'option' :   'HTMLOptionElement',
	'output' :   'HTMLOutputElement',
	'p' :   'HTMLParagraphElement',
	'param' :   'HTMLParamElement',
	'pre' :   'HTMLPreElement',
	'progress' :   'HTMLProgressElement',
	'quote' :   'HTMLQuoteElement',
	'rp' :   'HTMLRpElement',
	'rt' :   'HTMLRtElement',
	'ruby' :   'HTMLRubyElement',
	's' :   'HTMLSElement',
	'samp' :   'HTMLSampElement',
	'script' :   'HTMLScriptElement',
	'section' :   'HTMLSectionElement',
	'select' :   'HTMLSelectElement',
	'small' :   'HTMLSmallElement',
	'source' :   'HTMLSourceElement',
	'span' :   'HTMLSpanElement',
	'strike' :   'HTMLStrikeElement',
	'strong' :   'HTMLStrongElement',
	'style' :   'HTMLStyleElement',
	'sub' :   'HTMLSubElement',
	'sup' :   'HTMLSupElement',
	'table' :   'HTMLTableElement',
	'textarea' :   'HTMLTextAreaElement',
	'th' :   'HTMLThElement',
	'time' :   'HTMLTimeElement',
	'title' :   'HTMLTitleElement',
	'tt' :   'HTMLTtElement',
	'u' :   'HTMLUElement',
	'ul' :   'HTMLUListElement',
	'var' :   'HTMLVarElement',
	'video' :   'HTMLVideoElement',
	'wbr' :   'HTMLWbrElement'
};

var factoryFunctionMappings = {
	'HTMLDocument:createElement' : function(){ 
		var arg = arguments[0].toString(); 
	
		// if(typeof(arg)!="undefined"){ 
		// modify for single quote and double arg = arg.replace("\'",""); 
		 arg = arg.replace("\'","");
		 arg = arg.replace("\"","");
		 arg = arg.replace("\"","");
		
		var mapping = browserMappings[arg.toLowerCase()];
		if(mapping!=null){
			return mapping;
		}
		//} }, 'Document:getElementsByTagName' : function(arg){ var arg = arguments[0]; 
		// if(typeof(arg)!="undefined"){ 
		// modify for single quote and double 
		// arg = arg.replace("\'",""); 
		// arg = arg.replace("\"",""); return browserMappings[arg.toLowerCase()] + '[]'; 
		// } } }
	},
	'Document:getElementsByTagName' : function(arg){
		var arg = arguments[0];
		
		 arg = arg.replace("\'","");
		 arg = arg.replace("\"","");
		 arg = arg.replace("\"","");
	//	  if(typeof(arg)!="undefined"){  // modify for single quote and double
			// arg = arg.replace("\'","");
			// arg = arg.replace("\"","");
		 //var mapping = browserMappings[arg.toLowerCase()] + '[]'; 
		 var mapping = '[' +browserMappings[arg.toLowerCase()] + '[] + NodeList]';
			
		 if(mapping!=null){
				return mapping;
			}
		 
		
	//	  }
		
	},
	'HTMLDocument:createEvent' : function(){
		var arg = arguments[0].toString(); 
		if(arg=="CompositionEvent"){
			return "CompositionEvent";
		}
	}
}

	
var functionArgMappings = {
    // child_process.ChildProcess
    'HTMLDocument:addEventListener' : {
	   'click' : ['boolean fn(MouseEvent e)'] 
	}
}
/*
	abort 	Sync 	No 	Element 	Event 	No 	none
blur 	Sync 	No 	Element 	FocusEvent 	No 	none
click 	Sync 	Yes 	Element 	MouseEvent 	Yes 	DOMActivate event
compositionstart 	Sync 	Yes 	Element 	CompositionEvent 	Yes 	Launch text composition system
compositionupdate 	Sync 	Yes 	Element 	CompositionEvent 	No 	none
compositionend 	Sync 	Yes 	Element 	CompositionEvent 	No 	none
dblclick 	Sync 	Yes 	Element 	MouseEvent 	No 	none
DOMActivate 	Sync 	Yes 	Element 	UIEvent 	Yes 	none
DOMAttributeNameChanged 	Sync 	Yes 	Element 	MutationNameEvent 	No 	none
DOMAttrModified 	Sync 	Yes 	Element 	MutationEvent 	No 	none
DOMCharacterDataModified 	Sync 	Yes 	Text, Comment, CDATASection, ProcessingInstruction 	MutationEvent 	No 	none
DOMElementNameChanged 	Sync 	Yes 	Element 	MutationNameEvent 	No 	none
DOMFocusIn 	Sync 	Yes 	Element 	FocusEvent 	No 	none
DOMFocusOut 	Sync 	Yes 	Element 	FocusEvent 	No 	none
DOMNodeInserted 	Sync 	Yes 	Element, Attr, Text, Comment, CDATASection, DocumentType, EntityReference, ProcessingInstruction 	MutationEvent 	No 	none
DOMNodeInsertedIntoDocument 	Sync 	No 	Element, Attr, Text, Comment, CDATASection, DocumentType, EntityReference, ProcessingInstruction 	MutationEvent 	No 	none
DOMNodeRemoved 	Sync 	Yes 	Element, Attr, Text, Comment, CDATASection, DocumentType, EntityReference, ProcessingInstruction 	MutationEvent 	No 	none
DOMNodeRemovedFromDocument 	Sync 	No 	Element, Attr, Text, Comment, CDATASection, DocumentType, EntityReference, ProcessingInstruction 	MutationEvent 	No 	none
DOMSubtreeModified 	Sync 	Yes 	defaultView, Document, DocumentFragment, Element, Attr 	MutationEvent 	No 	none
error 	Async 	No 	Element 	Event 	No 	none
focus 	Sync 	No 	Element 	FocusEvent 	No 	none
focusin 	Sync 	Yes 	Element 	FocusEvent 	No 	none
focusout 	Sync 	Yes 	Element 	FocusEvent 	No 	none
keydown 	Sync 	Yes 	Document, Element 	KeyboardEvent 	Yes 	Varies: keypress event; launch text composition system; blur and focus events; DOMActivate event; other event
keypress 	Sync 	Yes 	Document, Element 	KeyboardEvent 	Yes 	Varies: textinput event; launch text composition system; blur and focus events; DOMActivate event; other event
keyup 	Sync 	Yes 	Document, Element 	KeyboardEvent 	Yes 	none
load 	Async 	No 	defaultView, Document, Element 	Event 	No 	none
mousedown 	Sync 	Yes 	Element 	MouseEvent 	Yes 	none
mouseenter 	Sync 	No 	Element 	MouseEvent 	No 	none
mouseleave 	Sync 	No 	Element 	MouseEvent 	No 	none
mousemove 	Sync 	Yes 	Element 	MouseEvent 	Yes 	none
mouseout 	Sync 	Yes 	Element 	MouseEvent 	Yes 	none
mouseover 	Sync 	Yes 	Element 	MouseEvent 	Yes 	none
mouseup 	Sync 	Yes 	Element 	MouseEvent 	Yes 	none
resize 	Sync 	No 	defaultView, Document 	UIEvent 	No 	none
scroll 	Async 	No / Yes 	defaultView, Document, Element 	UIEvent 	No 	none
select 	Sync 	Yes 	Element 	Event 	No 	none
textinput 	Sync 	Yes 	Element 	TextEvent 	Yes 	none
unload 	Sync 	No 	defaultView, Document, Element 	Event 	No 	none
wheel 	Async 	Yes 	defaultView, Document, Element 	WheelEvent 	Yes 	Scroll (or zoom) the document
*/