vjo.ctype('vjoPro.samples.classes.log.Log') //< public
.needs(['vjoPro.dsf.document.Positioning','vjoPro.dsf.document.Element'])
.protos({
/**
* @access public
*/
//> public constructs()
constructs : function() {
this.sDiv = "LogCtr";
this.sLog = this.sDiv + "-Logger";
this.sClear = this.sDiv + "-Clr";
this.oD = vjoPro.dsf.document;
var d = this.oD.Element.get(this.sDiv);
if (!d) this.init();
},
init : function () {
var ctr, div, cst = "position:absolute;border:1px solid #000000;background-color:#cccccc;",
dst = "background-color:#ffffff;border:1px solid #000000;height:200px;overflow:auto;width:300px;";
if (/MSIE/.test(navigator.userAgent) && !window.opera) {
ctr = document.createElement('<div style="'+cst+'"></div>');
div = document.createElement('<div style="'+dst+'"></div>');
} else {
div = document.createElement('div');
div.setAttribute("style",dst);
ctr = document.createElement('div');
ctr.setAttribute("style",cst);
}
ctr.id = this.sDiv;
div.id = this.sLog;
var title = document.createElement('div');
title.innerHTML = "<a id='"+this.sClear+"' href='#'>clear</a>";
ctr.appendChild(title);
ctr.appendChild(div);
document.body.appendChild(ctr);
this.oDiv = this.oD.Element.get(this.sDiv);
this.oLog = this.oD.Element.get(this.sLog);
var cw = this.oD.Positioning.getClientWidth();
this.oDiv.style.left = cw-305+"px";
var self = this;
vjoPro.dsf.EventDispatcher.add(this.sClear,'click',function () { self.clear(); })
},
/**
* @return Void
* @access public
* @param {String} psTxt
* @JsEventHandler
*/
//> public void log(String psTxt)
log : function (psTxt) {
if (psTxt) {
var div = document.createElement('div');
var log = document.createElement('span');
log.style.backgroundColor="#A7CC25";
log.innerHTML = " log :";
var msg = document.createElement('span');
msg.innerHTML = "&nbsp;"+psTxt;
div.appendChild(log);
div.appendChild(msg);
this.oLog.appendChild(div);
}
},
/**
* @return Void
* @access public
* @param {String} psTxt
* @param {com.ebay.dsf.resource.html.event.handler.JsHandlerObjectEnum} poMsg
*/
//> public void logMessage(String psTxt,com.ebay.dsf.resource.html.event.handler.JsHandlerObjectEnum poMsg)
logMessage : function (psTxt,poMsg) {
if (!poMsg) {
this.log("message is undefined or null");
}
this.log(psTxt + " " + JSON.stringify(poMsg));
},

clear : function () {
this.oLog.innerHTML = "";
}
})
.endType();
