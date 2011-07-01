vjo.ctype("vjoPro.dsf.utils.HistoryManager")
.needs(["vjoPro.dsf.Enc",
"vjoPro.dsf.ServiceEngine",
"vjoPro.dsf.EventDispatcher",
" vjoPro.dsf.Message",
"vjoPro.dsf.utils.UriBuilder"])
/**
* This class is used to keep the browser's back/forward button avaliable when
* the AJAX feature is introduced into the page.
*/
.protos({

URIBuilder : vjoPro.dsf.utils.UriBuilder,
EventDispatcher : vjoPro.dsf.EventDispatcher,

/**
* The constructor of the HistoryManager.
*
* @constructor
* @param {HistoryManagerModel} model
*        the dataModel of the HistoryManager
*
*/
//> public void constructs(HistoryManagerModel);
constructs : function(model) {

var self = this;

this.model = model;this.modules = new Object();
this.href = new this.URIBuilder(document.location.href);

if (navigator.userAgent.indexOf("MSIE") > -1) {

this.iframe = document.getElementById(this.model.frameId);
if (!this.iframe.contentWindow.document.body.innerText) this.pushFrame(this.href.hash);

this.EventDispatcher.addEventListener(this.iframe,"load",function(event) { return self.onFrameLoad(event); });

}

this.EventDispatcher.addEventListener(window,"load",function(event) { return self.onLoad(event); });
this.EventDispatcher.addEventListener(window,"unload",function(event) { return self.onUnload(event); });

},

//> private void onload(Object);
onLoad : function(event) {

var self = this;

this.states = new Object();

this.onHistoryChange(this.href.hash);
this.ticker = window.setInterval(function() { self.onTicker(); },50);

},

//> private void onUnload(Object);
onUnload : function(event) {
this.iframe = null;
},

//> private void onTicker();
onTicker : function() {
var hash = this.getHash();
if (hash != this.href.hash) this.onHistoryChange(hash);
},

//> private Object getHash();
getHash : function() {
return document.location.hash.match("(#)?(.*)")[2];
},

//> private void pushFrame(Object, String);
pushFrame : function(hash,title) {

this.href.hash = hash;

title = ((title)?title:document.title);
title = ((title)?title.replace(/"/g,'\\"'):this.href.getUri());

var script = '<scr' + 'ipt type="text/javascript">';
script += 'document.title = "' + title + '";';
script += 'document.write("' + this.href.getUri() + '");';
script += '</scr' + 'ipt>';

var frame = this.iframe.contentWindow.document;
frame.open();frame.write(script);frame.close();

},

//> private void onFrameLoad(Object);
onFrameLoad : function(event) {
var href = new this.URIBuilder(this.iframe.contentWindow.document.body.innerText);
if (href.hash != this.href.hash) this.onHistoryChange(document.location.hash = href.hash);
},

//> private void serialize(Object);
serialize : function(object) {
try { return JSON.stringify(object); }
catch(except) { return "" }
},

//> private void deserialize(Object);
deserialize : function(object) {
try { return (object)?eval("(" + object + ")"):new Object(); }
catch(except) { return new Object(); }
},

/**
* Updates the state of a ajax response.
*
* @param {Object} key
*        the full AJAX query url
* @param {Object} state
*        the state of the ajax response
*/
//> public void putState(Object, Object);
putState : function(key,state) {
this.states[key] = this.deserialize(this.serialize(state));
},

/**
* Gets the state of the ajax response.
*
* @param {Object} key
*        the full AJAX query url
* @return {Object}
*        the state of the ajax response
*/
//> public Object getState(Object);
getState : function(key) {
return this.states[key];
},

/**
* Appends a ajax request to the browser's history list, which allows you
* use 'back/forward' button as same as on the normal html page.
*
* @param {String} name
*        the string id of the ajax call
* @param {Object} state
*        the state for current ajax call
* @param {String} title
*        the window title for current ajax call
*/
//> public void pushHistory(String, Object, String);
pushHistory : function(name,state,title) {

this.modules[name] = state;

var hash = vjoPro.dsf.Enc.encodeURIComponent(this.serialize(this.modules))
if (hash == this.href.hash) return;

window.location.hash = hash;
if (this.iframe) this.pushFrame(hash,title);

},

//> private void onHistoryChange(Object);
onHistoryChange : function(hash) {

this.href.hash = hash;

var message = new vjoPro.dsf.Message("HISTORY_CHANGE");
message.state = this.deserialize(vjoPro.dsf.Enc.decodeURIComponent(this.href.hash));

vjoPro.dsf.ServiceEngine.handleRequest(message);

}

})
.endType();

