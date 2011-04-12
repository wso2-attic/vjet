//DLC_enableDLCEvents is an only optional method required by DLC.
function DLC_enableDLCEvents() {
	var forms = document.forms;
	for (var i = 0; i < forms.length; i++) {
		forms[i].onsubmit = DLC_onSubmit;
	}
	var inputs = document.getElementsByTagName("input");
	for (var i = 0; i < inputs.length; i++) {
		var input = inputs[i];
		if (input.type == "checkbox" || input.type == "radio") {
			//input.onclick = DLC_onClick;
		}
		else if (input.type != "submit") {
			if (input.type == "text" || input.type == "password") {
				input.onkeyup = DLC_onKeyUp;
				input.onchange = DLC_onChange;
			}
			//input.onblur = DLC_onBlur;
		} else if (input.type == "submit") {
			input.onclick = function(){return false};//disable default form submission behavior
		}

	}
	var selects = document.getElementsByTagName("select");
	for (var i = 0; i < selects.length; i++) {
		selects[i].onchange = DLC_onChange;
	}
	var textareas = document.getElementsByTagName("textarea");
	for (var i = 0; i < textareas.length; i++) {
		textareas[i].onchange = DLC_onChange;
		textareas[i].onkeyup = DLC_onKeyUp;
	}

	var inputs = document.getElementsByTagName("button");
	for (var i = 0; i < inputs.length; i++) {
		var input = inputs[i];
		if (input.type == "submit") {
			input.onclick = function(){return false};//disable default form submission behavior
		}
	}


	document.onclick = DLC_docOnClick;
//	document.onkeyup = DLC_onKeyUp;
	//document.ondblclick = DLC_docOnDblClick;
}

//DLC client methods
function DLC_onClick(evt) { 
	evt = evt || window.event; 
	var elem = evt.target || evt.srcElement;

	DLC.sendMessage("click:" + DLC_getElemRef(elem) + ":" + DLC_getNativeEventInfo(evt));		
	//window.event.cancelBubble = true;
}

function DLC_docOnClick(evt) {
	evt = evt || window.event; 
	var elem = evt.target || evt.srcElement;
	
	if (elem.type != undefined && elem.type == "radio") {
		//DLC.sendMessage("radioChange:" 
		//	+ DLC_getElemRef(elem) + ".checked=" + elem.checked +":" + DLC_getNativeEventInfo(evt));
		DLC.sendMessage("change:" 
		+ DLC_getElemRef(elem) + ".checked=" + elem.checked +":" + DLC_getNativeEventInfo(evt));
	}
	else if (elem.type != undefined && elem.type == "checkbox") {
		DLC.sendMessage("click:" 
			+ DLC_getElemRef(elem) + ".checked=" + elem.checked +":" + DLC_getNativeEventInfo(evt));
		DLC.sendMessage("change:" 
			+ DLC_getElemRef(elem) + ".checked=" + elem.checked +":" + DLC_getNativeEventInfo(evt));
		//window.event.cancelBubble = true;
	}
	else if (elem.type != undefined && elem.type == "submit") {
		//DLC.sendMessage("submitButtonClick:" 
		//	+ DLC_getElemRef(elem)+ ":" + DLC_getNativeEventInfo(evt));
		DLC.sendMessage("click:" 
			+ DLC_getElemRef(elem)+ ":" + DLC_getNativeEventInfo(evt));
	}
	else if (elem.tagName == "A") {
		//DLC.sendMessage("linkClick:" + DLC_getElemRef(elem) + ":" + DLC_getNativeEventInfo(evt));
		DLC.sendMessage("click:" + DLC_getElemRef(elem) + ":" + DLC_getNativeEventInfo(evt));
		return false;
	//}else if (elem.tagName == "INPUT") {
	}else{
		DLC.sendMessage("click:" + DLC_getElemRef(elem) + ":" + DLC_getNativeEventInfo(evt));
	}
	
}

function DLC_onDblClick(evt) {
	evt = evt || window.event; 
	var elem = evt.target || evt.srcElement;
	if (elem.tagName == "A") {
		//DLC.sendMessage("linkDblClick:" + DLC_getElemRef(elem) +":" + DLC_getNativeEventInfo(evt));
		DLC.sendMessage("dblclick:" + DLC_getElemRef(elem) +":" + DLC_getNativeEventInfo(evt));
		window.event.cancelBubble = true;
		return false;
	}else{
		DLC.sendMessage("dblclick:" + DLC_getElemRef(elem) + ":" + DLC_getNativeEventInfo(evt));
		window.event.cancelBubble = true;
	}
}

function DLC_onFocus(evt) {
	evt = evt || window.event; 
	var elem = evt.target || evt.srcElement;
	DLC.sendMessage("focus:" 
		+ DLC_getElemRef(elem) +":" + DLC_getNativeEventInfo(evt));
}

function DLC_onContextMenu(evt) {
	evt = evt || window.event; 
	var elem = evt.target || evt.srcElement;
	DLC.sendMessage("contextmenu:" 
		+ DLC_getElemRef(elem) +":" + DLC_getNativeEventInfo(evt));
}


function DLC_onBlur(evt) {
	evt = evt || window.event; 
	var elem = evt.target || evt.srcElement;
	DLC.sendMessage("blur:" 
		+ DLC_getElemRef(elem) +":" + DLC_getNativeEventInfo(evt));
}

function DLC_onChange(evt) {
	evt = evt || window.event; 
	var elem = evt.target || evt.srcElement;
	if (elem.type != undefined && elem.type == "radio") {
		DLC.sendMessage("change:" 
			+ DLC_getElemRef(elem) + ".checked=" + elem.checked+":" + DLC_getNativeEventInfo(evt));
	}
	else if(elem.type!="undefined" && elem.type=="select-one"){
		DLC.sendMessage("change:" + DLC_getElemRef(elem) + '.selectedIndex=' + elem.selectedIndex+":" + DLC_getNativeEventInfo(evt));
	}
	else if(elem.type!="undefined" && elem.type=="select-multiple"){
		var s = "";
		for(var i=0;i<elem.options.length;i++){
			if(elem.options[i].selected){
				if(s!=""){
					s += ",";
				}
				s += i; 
			}
		}
		DLC.sendMessage("change:" + DLC_getElemRef(elem) + '.selectedIndex=' + s+":" + DLC_getNativeEventInfo(evt));
	}
	else {
		DLC.sendMessage("change:" 
			+ DLC_getElemRef(elem) + '.value="' + elem.value + '"'+":" + DLC_getNativeEventInfo(evt));
	}
}

function DLC_onMouseOver(evt) {
	evt = evt || window.event; 
	var elem = evt.target || evt.srcElement;
	DLC.sendMessage("mouseover:" + DLC_getElemRef(elem) + ":" + DLC_getNativeEventInfo(evt));
}

function DLC_onMouseOut(evt) {
	evt = evt || window.event; 
	var elem = evt.target || evt.srcElement;
	DLC.sendMessage("mouseout:" + DLC_getElemRef(elem) + ":" + DLC_getNativeEventInfo(evt));
}

var DLC_DRAG_OBJ = null;
var DLC_MOUSE_OFFSET = null;
var DLC_DND_SAVED_MOUSEMOVE = null;
function DLC_makeDraggable(elem) {
	if (!elem) {
		return;
	}
	elem.onmousedown = function(evt) {
		evt = evt || window.event; 
		DLC_DRAG_OBJ = this;
		var elemPos = DLC_getPosition(this);
		var mousePos = DLC_mousePosition(evt);
		DLC_MOUSE_OFFSET = {x:mousePos.x - elemPos.x, y:mousePos.y -elemPos.y};
		DLC_DND_SAVED_MOUSEMOVE = document.onmousemove;
		document.onmousemove = DLC_mouseMove;
		return false;
	}
}

function DLC_mouseUp(evt) {
	evt = evt || window.event;
	var mousePos = DLC_mousePosition(evt);
	var elem = evt.target || evt.srcElement;
	if (DLC_DRAG_OBJ) {
		DLC.sendMessage("drop:" 
			+ DLC_getElemRef(DLC_DRAG_OBJ) + ":["
			+ (mousePos.x - DLC_MOUSE_OFFSET.x)
			+ ","
			+ (mousePos.y - DLC_MOUSE_OFFSET.y)
			+ "]");
		document.onmousemove = DLC_DND_SAVED_MOUSEMOVE;
	}else{
		DLC.sendMessage("mouseup:" + DLC_getElemRef(elem) + ":" + DLC_getNativeEventInfo(evt));
	}
	DLC_DRAG_OBJ = null;
}

function DLC_mouseDown(evt) {
	evt = evt || window.event; 
	var mousePos = DLC_mousePosition(evt);
	var elem = evt.target || evt.srcElement;
	DLC.sendMessage("mousedown:" + DLC_getElemRef(elem) + ":" + DLC_getNativeEventInfo(evt));
}

function DLC_selectStart(evt) {
	evt = evt || window.event; 
	var mousePos = DLC_mousePosition(evt);
	var elem = evt.target || evt.srcElement;
		/*
		DLC.sendMessage("mousedown:" 
			+ DLC_getElemRef(elem) + ":["
			+ (mousePos.x)
			+ ","
			+ (mousePos.y)
			+ "]");
		
		DLC.sendMessage("mousedown:" + DLC_getElemRef(elem) + ":" + DLC_getNativeEventInfo(evt));
		*/
}

function DLC_mouseMove(evt) {
	evt = evt || window.event;
	var mousePos = DLC_mousePosition(evt);
	var elem = evt.target || evt.srcElement;
	if (DLC_DRAG_OBJ) {
		DLC.sendMessage("drag:" 
			+ DLC_getElemRef(DLC_DRAG_OBJ) + ":["
			+ (mousePos.x - DLC_MOUSE_OFFSET.x)
			+ ","
			+ (mousePos.y - DLC_MOUSE_OFFSET.y)
			+ "]");
		return false;
	}else{
		if (elem != null) {
			DLC.sendMessage("mousemove:" + DLC_getElemRef(elem) + ":" + DLC_getNativeEventInfo(evt));
		}
   	}
}

function DLC_mousePosition(evt){
	if(evt.pageX || evt.pageY){
		return {x:evt.pageX, y:evt.pageY};
	}
	return {
		x:evt.clientX + document.body.scrollLeft - document.body.clientLeft,
		y:evt.clientY + document.body.scrollTop  - document.body.clientTop
	};
}

function DLC_onKeyUp(evt) {
	evt = evt || window.event; 
	var elem = evt.target || evt.srcElement;
	DLC.sendMessage("keyup:" 
		+ DLC_getElemRef(elem) + '.value="' + elem.value + '"' +":" + DLC_getNativeEventInfo(evt));
}

function DLC_onKeyDown(evt) {
	evt = evt || window.event; 
	var elem = evt.target || evt.srcElement;
	DLC.sendMessage("keydown:" 
		+ DLC_getElemRef(elem) + '.value="' + elem.value + '"' +":" + DLC_getNativeEventInfo(evt));
}

function DLC_onKeyPress(evt) {
	evt = evt || window.event; 
	var elem = evt.target || evt.srcElement;
	DLC.sendMessage("keypress:" 
		+ DLC_getElemRef(elem) + '.value="' + elem.value + '"' +":" + DLC_getNativeEventInfo(evt));
}

function DLC_onReset(evt) {
	evt = evt || window.event; 
	var elem = evt.target || evt.srcElement;
	DLC.sendMessage("reset:" + DLC_getElemRef(elem) +":" + DLC_getNativeEventInfo(evt));
}


var opera_reszise_tid = null;

function DLC_onResize(evt) {
	var newWindowWidth = (window.innerWidth || document.body.offsetWidth);
	var newWindowHeight = (window.innerHeight || document.body.offsetHeight);
	if (newWindowWidth != DLC.windowWidth || newWindowHeight != DLC.windowHeight) {
		DLC.windowWidth = newWindowWidth;
		DLC.windowHeight = newWindowHeight;
		setTimeout("DLC_reportOnResize()", 500);
	}
	if (window.opera) {
		if (opera_reszise_tid) {
			clearTimeout(opera_reszise_tid);
		}
		opera_reszise_tid = setTimeout('DLC_onResize()',500);
	}
}

function DLC_reportOnResize() {
	DLC.sendMessage("resize:(" 
		+ DLC.windowWidth + ","
		+ DLC.windowHeight + ")");
}

function DLC_onSubmit(evt) {
	evt = evt || window.event; 
	var elem = evt.target || evt.srcElement;
	DLC.sendMessage("submit:" + DLC_getElemRef(elem) +":" + DLC_getNativeEventInfo(evt));
	return false;
}

function DLC_onSubmitButtonClick(elem, pid) {
	var hiddenInput = document.createElement("input");
	hiddenInput.type = "hidden";
	hiddenInput.name = "dlc_pid";
	hiddenInput.value = pid;
	elem.form.appendChild(hiddenInput);
	
	if (!elem.name || !elem.value) {
		return;
	}

	if (!elem.form) {
		return;
	}
	var hiddenInput = document.createElement("input");
	hiddenInput.type = "hidden";
	hiddenInput.name = elem.name;
	hiddenInput.value = elem.value;
	elem.form.appendChild(hiddenInput);
}

function DLC_onLinkClick(elem, pid) {
	var url = elem.href;
	if (url.indexOf("?") == -1) {
		url = url + "?";
	}
	else {
		url += "&";
	}
	document.location.href = url + "dlc_pid=" + pid;
}

function DLC_reload(eid) {
	var url = document.location.href;
	var eidIndex = url.indexOf("eid=");
	if (eidIndex > 0) {
		url = url.substring(0, eidIndex)
	}
	else {
		if (url.indexOf("?") == -1) {
			url = url + "?";
		}
		else {
			url += "&";
		}	
	}
	document.location.href = url + "eid=" + eid;
}

function DLC_insertHtml(reference, html, insertBefore) {
	var bOption = false;
	if (typeof(reference) == "string") {
		reference = document.getElementById(reference);
	}
	var tmpRoot = document.createElement("div");
	if(html.toLowerCase().indexOf("<option")!=-1){
		html = "<select>"+html+"</select>";
		bOption = true;
	}
	//if html has style tag, it's ignoring the style tag.
	var h = html.toLowerCase();
	var start = h.indexOf("<style>");
	var end = h.indexOf("</style>");
	var style, css;
	
	if(start!=-1 && end !=-1){
		html = h.substring(0,start) + "<div id='styletag'></div>";
		html += h.substring(end+8);
		css = h.substring(start+7,end);
		style = document.createElement("style");
		style.setAttribute('type', 'text/css');
		if(style.styleSheet){// IE
			style.styleSheet.cssText = css;
		} else {// w3c
			var cssText = document.createTextNode(css);
			style.appendChild(cssText);
		}
		
	}
	
	tmpRoot.innerHTML = html;
	var numChild = tmpRoot.childNodes.length;
	for (var i = numChild - 1; i >= 0; i--) {
		var newElem = tmpRoot.childNodes[i];
		if(bOption){
			newElem = newElem.options[0]; 
		}		
		if (insertBefore) {
			reference.parentNode.insertBefore(newElem, reference); 
		}
		else {
			reference.parentNode.insertBefore(newElem, reference.nextSibling);
			insertBefore = true;
		}
		reference = newElem;
	}
	if(style){
		var sty = document.getElementById('styletag');
		var parent = sty.parentNode;
		if(sty){
			parent.replaceChild(style,sty);
		}
	}
	
	DLC_enableDLCEvents();
}

function DLC_appendHtml(parent, html, index) {
	if (typeof(parent) == "string") {
		parent = document.getElementById(parent);
	}
	var tmpRoot = document.createElement("div");
	tmpRoot.innerHTML = html;
	var numChild = tmpRoot.childNodes.length;

	//IE specific bug
	// if html is <script type="text/javascript" src="http://include.ebaystatic.com/v4js/en_US/v/SYS14_vjo_v_1_en_US.js"></script>
	//numChild value is becoming 0
	if(numChild==0 && html.indexOf('<script')==0){
		html = "<br>"+html;
		tmpRoot.innerHTML = html;
		var br = tmpRoot.childNodes[0];
		br.parentNode.removeChild(br);
		numChild = tmpRoot.childNodes.length;
	}

	var reference = null;
	if (index != undefined && parent.childNodes.length > index) {
		reference = parent.childNodes[index];
	}
	for (var i = numChild - 1; i >= 0; i--) {
		var newElem = tmpRoot.childNodes[i];
		parent.insertBefore(newElem, reference); 
		reference = newElem;
	}
	
	if(numChild==0){
		tmpRoot.innerHTML = DLC_fixTableIssue(html);
		if(parent.tagName == "TABLE"){
			parent.replaceChild(tmpRoot.childNodes[0].childNodes[0],parent.tBodies[0]);
		}else if(parent.tagName == "TBODY"){
			parent.appendChild(tmpRoot.childNodes[0].childNodes[0].rows[0]);
		}else if(parent.tagName == "TR"){
			parent.appendChild(tmpRoot.childNodes[0].childNodes[0].childNodes[0].childNodes[0]);
		}
	}
	DLC_enableDLCEvents();
}

function DLC_fixTableIssue(html){
	var h = html;
	h = h.toLowerCase();
	if(h.indexOf("<tbody")!=-1){
		html = "<table>"+html+"</table>";
	}else if(h.indexOf("<tr")!=-1){
		html = "<table><tbody>"+html+"</tbody></table>";
	}else if(h.indexOf("<td")!=-1){
		html = "<table><tbody><tr>"+html+"</tr></tbody></table>";
	}
	return html;
}

function DLC_updateElement(reference,html) {
	DLC_insertHtml(reference, html, true);
	DLC_removeElement(reference);
}

function DLC_updateElementById(id,html) {
	var elem = document.getElementById(id); 
	if (elem){
		DLC_updateElement(elem,html);
	}
}

function DLC_removeElement(reference){
	if (typeof(reference) == "string") {
		reference = document.getElementById(reference);
	}
	if(typeof(reference)!="undefined"){
		reference.parentNode.removeChild(reference);
	}
}

function DLC_setWidth(elem, width){
	elem.style.width = width;
}

function DLC_setHeight(elem, height){
	elem.style.height = height;
}

function DLC_resizeWindow(width, height) {
	if (window.opera) {
		return;
	}
	DLC.windowWidth = width;
	DLC.windowHeight = height;
	if (window.innerWidth) {
		window.innerWidth = width;
		window.innerHeight = height;
	}
	else {
		var currentWidth = document.body.offsetWidth;
		var currentHeight = document.body.offsetHeight;
		window.resizeBy((width - currentWidth), (height - currentHeight));
	}
}

function DLC_getPosition(elem) { 
	var curleft = 0;
	var curtop = 0;
	var obj = elem;
	if (obj.offsetParent) { 
		do { 
			curleft += obj.offsetLeft; 
			curtop += obj.offsetTop; 
		} while (obj = obj.offsetParent); 
	} 
	//DLC.sendMessage("position:[" + curleft + "," + curtop + "]");
	return {x:curleft, y:curtop};
} 


function DLC_windowInfo() {
	DLC.sendMessage("window:size=" + DLC_getWindowSize());
}

function DLC_getWindowSize() {
	return "(" 
		+ (window.innerWidth || document.body.offsetWidth) + ","
		+ (window.innerHeight || document.body.offsetHeight) + ")";
}

function DLC_screenInfo() {
	DLC.sendMessage("screen:size=" + DLC_getScreenSize());
}

function DLC_getScreenSize() {
	return "(" + screen.width + "," + screen.height + ")";
}

function DLC_getElemRef(elem) {
	if (elem.id != undefined && elem.id != '') {
		return 'document.getElementById("' + elem.id + '")';
	}
	return DLC_getPath(elem);
}

function DLC_getPath(elem) {
	if (elem == null) {
		return "null";
	}
	if (elem === document || elem == document.body || elem.parentNode == document) {
		//handle both html and body elements
		return "document.body";
	}

	return DLC_getPath(elem.parentNode) + ".childNodes[" + DLC_getIndex(elem) + "]";
}

function DLC_getCurrentStyleValue(elem, name) {
	if (elem == null) {
		return null;
	}
	var val = "";
	if (elem.currentStyle) {
		val = elem.currentStyle[name];
	}
	if (window.getComputedStyle) {
		val = document.defaultView.getComputedStyle(elem, "")[name];
	}
	if (val == 'auto') {
		val = "";
	}
	return val;
}
		
function DLC_getIndex(elem) {
	if (elem.previousSibling == null) {
		return 0;
	}
	return DLC_getIndex(elem.previousSibling) + 1;
}

function DLC_forward() {
	DLC.setCookie("DLC_HIS", "true");
	history.forward();
}

function DLC_back() {
	DLC.setCookie("DLC_HIS", "true");
	history.back();
}

function DLC_toHtml(elem) {
	if (!elem || elem == document) {
		return "<html>"+document.documentElement.innerHTML+"</html>";
	}
	if (true) {
		var tmpDiv = document.createElement("div");
		tmpDiv.appendChild(elem.cloneNode(true));
		return tmpDiv.innerHTML;	
	}
}

function DLC_setClassName(reference, clzName){
	if (typeof(reference) == "string") {
		reference = document.getElementById(reference);
	}
	reference.className = clzName;
}

window.onresize = DLC_onResize;
if (window.opera) {
	opera_reszise_tid = setTimeout('DLC_onResize()',500);
}

function DLC_onScroll(evt) {
	evt = evt || window.event; 
	var elem = evt.target || evt.srcElement;
	if(!elem){
		DLC.sendMessage("scroll:body");
	}else{
		DLC.sendMessage("scroll:" + DLC_getElemRef(elem));
	}
}

function DLC_onImageLoad(evt) {
	evt = evt || window.event; 
	var elem = evt.target || evt.srcElement;
	//DLC.sendMessage("imageLoad:" +DLC_getElemRef(elem));
	DLC.sendMessage("load:" +DLC_getElemRef(elem));
}

function DLC_bindImageLoad(elem){
	if(elem && elem.width >0 && elem.height >0){
		//send the message
		DLC.sendMessage("imageLoad:" +DLC_getElemRef(elem));
	} else{
		//bind onload event
		elem.onload = DLC_imageOnLoad;
	}
}

function DLC_onScriptLoad(evt) {
	evt = evt || window.event; 
	var elem = evt.target || evt.srcElement;
	//DLC.sendMessage("scriptLoad:" +DLC_getElemRef(elem).replace("document.body","document.documentElement"));
	DLC.sendMessage("load:" +DLC_getElemRef(elem).replace("document.body","document.documentElement"));
}

function DLC_bindScriptLoad(elem){
	if(!elem.readyState || elem.readyState == "loaded" || elem.readyState == "complete"){
		//send the message
		//DLC.sendMessage("scriptLoad:" +DLC_getElemRef(elem).replace("document.body","document.documentElement"));
		DLC.sendMessage("load:" +DLC_getElemRef(elem).replace("document.body","document.documentElement"));
	} else{
		//bind onload event
		elem.onload = DLC_onScriptLoad;
	}
}

function DLC_onScriptLoad(evt) {
	evt = evt || window.event; 
	var elem = evt.target || evt.srcElement;
	//DLC.sendMessage("scriptReadyStateChange:" +DLC_getElemRef(elem).replace("document.body","document.documentElement"));
	DLC.sendMessage("readyStateChange:" +DLC_getElemRef(elem).replace("document.body","document.documentElement"));
}


function DLC_bindScriptReadyStateChange(elem) {
	if(!elem.readyState || elem.readyState == "loaded" || elem.readyState == "complete"){
		//send the message
		//DLC.sendMessage("scriptReadyStateChange:" +DLC_getElemRef(elem).replace("document.body","document.documentElement"));
		DLC.sendMessage("readyStateChange:" +DLC_getElemRef(elem).replace("document.body","document.documentElement"));
	} else{
		//bind onload event
		elem.onreadystatechange = DLC_onScriptReadyStateChange;
	}
}

function DLC_onReady(evt) {
	evt = evt || window.event; 
	var elem = evt.target || evt.srcElement;
	DLC.sendMessage("onreadystatechange:" + DLC_getElemRef(elem) + ":" + DLC_getNativeEventInfo(evt));
}

function DLC_onSelect(evt) {
	evt = evt || window.event; 
	var elem = evt.target || evt.srcElement;
	DLC.sendMessage("select:" 
		+ DLC_getElemRef(elem) +":" + DLC_getNativeEventInfo(evt));
}

function DLC_onUnLoad(evt) {
	DLC.sendMessage("unload:body");
}

function DLC_onError(evt) {
	evt = evt || window.event; 
	var elem = evt.target || evt.srcElement;
	DLC.sendMessage("error:" + DLC_getElemRef(elem) + ":" + DLC_getNativeEventInfo(evt));
}

function DLC_onLive(evt) {
	evt = evt || window.event; 
	var elem = evt.target || evt.srcElement;
	DLC.sendMessage("onlive:" + DLC_getElemRef(elem) + ":" + DLC_getNativeEventInfo(evt));
}

function DLC_getNativeEventInfo(evt){
	evt = evt || window.event; 
	var elem = evt.target || evt.srcElement;
	var mcoor = DLC_mousePosition(evt);
	var button = -1, un="undefined", keyCode = which = charCode = 0;
	if(typeof(evt.button)!=un){
		button = evt.button; 
	}else if(typeof(evt.which)!=un){
		button = evt.which;
	}
	/* finding related target */
	var relatedTarget = "";
	if(evt.type=="mouseover"){
		relatedTarget = evt.relatedTarget || evt.fromElement;
	}else if(evt.type=="mouseout"){
		relatedTarget = evt.relatedTarget || evt.toElement;
	}
	/* getting the path of the related target */
	if(relatedTarget){
		relatedTarget = DLC_getElemRef(relatedTarget);
	}
	//finding key code
	if(typeof(evt.keyCode)!=un){
		keyCode = evt.keyCode; 
	}
	if(typeof(evt.which)!=un){
		which = evt.which;
	}
	if(typeof(evt.charCode)!=un){
		charCode = evt.charCode;
	}
	
	var screenX = 0;
	var screenY = 0;
	if(typeof(evt.screenX)!="undefined"){
		screenX = evt.screenX;
	}
	if(typeof(evt.screenY)!="undefined"){
		screenY = evt.screenY;
	}
	
	var keyIdentifier = "";
	var keyLocation = 0;
	if(typeof(evt.keyIdentifier)!="undefined"){
		keyIdentifier = evt.keyIdentifier;
	}
	if(typeof(evt.keyLocation)!="undefined"){
		keyLocation = evt.keyLocation;
	}
	var modifierState = false;
	if(typeof(evt.keyLocation)!="undefined" && typeof(evt.modifierState)=="function"){
		modifierState  = evt.modifierState(evt.keyLocation);
	}
	var cancelable = false;
	if(typeof(evt.cancelable)!="undefined"){
		cancelable = evt.cancelable;
	}
	var timeStamp = 0;
	if(typeof(evt.timeStamp)!="undefined"){
		timeStamp = evt.timeStamp;
	}
	var detail = 0;
	if(typeof(evt.detail)!="undefined"){
		detail = evt.detail;
	}
	
	var mouseX = 0;
	var mouseY = 0;
	if(!isNaN(mcoor.x)){
		mouseX = mcoor.x;
	}	
	if(!isNaN(mcoor.y)){
		mouseY = mcoor.y;
	}
	var altKey = false;
	if(typeof(evt.altKey)!="undefined"){
		altKey = evt.altKey;
	} 
	var ctrlKey = false;
	if(typeof(evt.ctrlKey)!="undefined"){
		ctrlKey = evt.ctrlKey;
	} 
	var shiftKey = false;
	if(typeof(evt.shiftKey)!="undefined"){
		shiftKey = evt.shiftKey;
	} 
	var metaKey = false;
	if(typeof(evt.metaKey)!="undefined"){
		metaKey = evt.metaKey;
	} 
	
	var cancelBubble = false;
	if(typeof(evt.cancelBubble)!="undefined"){
		cancelBubble = 	evt.cancelBubble;	
	}else if(typeof(evt.bubbles)!="undefined"){
		cancelBubble = !evt.bubbles;	
	}
	
	var pageX = 0, pageY = 0;
	if(typeof(evt.pageX)!="undefined"){
		pageX = evt.pageX;	
	}
	if(typeof(evt.pageY)!="undefined"){
		pageY = evt.pageY;	
	}

	var clientX = 0, clientY = 0;
	if(typeof(evt.clientX)!="undefined"){
		clientX = evt.clientX;	
	}
	if(typeof(evt.clientY)!="undefined"){
		clientY = evt.clientY;	
	}

//	var scrollLeft = 0, scrollTop = 0;
//	if(typeof(evt.scrollLeft)!="undefined"){
//		scrollLeft = evt.scrollLeft;	
//	}
//	if(typeof(evt.scrollTop)!="undefined"){
//		scrollTop = evt.scrollTop;	
//	}
//
//	var clientLeft = 0, clientTop = 0;
//	if(typeof(evt.clientLeft)!="undefined"){
//		clientLeft = evt.clientLeft;	
//	}
//	if(typeof(evt.clientTop)!="undefined"){
//		clientTop = evt.clientTop;	
//	}

	var info = "[";
	info +=	mouseX 
	info += "," 
	info += mouseY;
	info += "," 
	info +=	screenX
	info += "," 
	info += screenY
	info += "," 
	info += altKey
	info += "," 
	info += shiftKey
	info += "," 
	info += ctrlKey
	info += "," 
	info += cancelBubble
	info += "," 
	info += button 
	info += "," 
	info += relatedTarget 
	info += "," 
	info += DLC_getElemRef(elem) 
	info += ","
	info += metaKey
	info += "," 
	info += keyCode 
	info += "," 
	info += keyIdentifier 
	info += "," 
	info += keyLocation 
	info += "," 
	info += modifierState
	info += "," 
	info += cancelable
	info += "," 
	info += timeStamp
	info += "," 
	info += detail
	info += "," 
	info += pageX
	info += "," 
	info += pageY
	info += "," 
	info += clientX
	info += "," 
	info += clientY
	info += "," 
	info += which
	info += "," 
	info += charCode
	info +="]";
	return info;
}

function Task(dlc, id, delay, isSetInterval) {
  this.dlc = dlc;
  this.id = id;
  this.delay = delay || 0;
  this.clientId = undefined;
  this.scheduleFun = function(fn, delay) {if (isSetInterval) {return setInterval(fn, delay)}; return setTimeout(fn,delay);};
  this.clearFun = function(id) { if (isSetInterval) {clearInterval(id);} else {clearTimeout(id);}};
};

function Timer(dlc){
  this.dlc = dlc;
  this.tasks = {};

  this.setTimeOut = function(id, delay) {
	var task = new Task(this.dlc, id, delay, false);
	this.tasks[id] = task;
	this.schedule(task);
  };
  this.setInterval = function(id, delay) {
	var task = new Task(this.dlc, id, delay, true);
	this.tasks[id] = task;
	this.schedule(task, setInterval);
  };
  
  this.schedule = function(task) {
	  var dlc = task.dlc, taskId = task.id, delay = task.delay;
	  var run = function() {
	    var id = taskId;
	    if(id) dlc.handler.sendMessage("[task]" + id);
	  };
	  task.clientId = task.scheduleFun(run, delay);
  }
  this.clear = function(id) {
	var task = this.tasks[id];
	if(task) task.clearFun(task.clientId);
    this.tasks[id] = undefined;
  };
  this.clearAll = function() {
	for(id in this.tasks) {
	  var task = this.tasks[id];
	  if(task) this.clear(task);
	  this.tasks[id] = undefined;
	}
  };
}

var TM = undefined;
if(typeof(DLC)=="object" && typeof(DLC.addEnabler)=="function") {
	DLC.addEnabler(DLC_enableDLCEvents);
	DLC.addDisabler(DLC_onUnLoad);
	TM = new Timer(DLC);
}