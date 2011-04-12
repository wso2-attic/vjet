vjo.ctype("vjoPro.dsf.utils.UriBuilder")
.needs("vjoPro.dsf.Enc")
.props({
/**
* Gets the meta tag with specified attribute name and value.
*
* @param {String} name
*        the attribute name of the meta tag
* @param {String} value
*        the value of the specified attribute
* @return {String}
*        the reference of the meta tag. If no such meta exists, return
*        <code>null</code>
*/
//>public String meta(String, String);
meta : function(name,value) {
var tags = document.getElementsByTagName("meta");
for (var idx = 0;(idx < tags.length);idx++) {
if (tags[idx].getAttribute(name) == value) return tags[idx];
}
return null;
}

})
.protos({
/**
* The constructor function, which calls {@link #parse(String, boolean)} to
* create the instance.
*
* @constructor
* @param {String} uri
*        a uri string to be parsed
* @param {boolean} decode
*        a boolean value that indicates whether it is SEO uri
* @see   #parse(String, boolean)
*/
//> public void constructs(String, boolean);
constructs : function(uri,decode) {
this.parse(uri,decode);
},

/**
* Gets request info from a given url. Following info will be avaliable after
* the parse action:
* <li>protocol
* <li>host
* <li>port
* <li>href
* <li>query string
* <li>hash
*
* @param {String} uri
*        a uri string to be parsed
* @param {boolean} decode
*        a boolean value that indicates whether it is SEO uri
*/
//> public void parse(String, boolean);
parse : function(uri,decode) {

this.uri = uri;
this.params = new Object();

var match = this.uri.match(this.uriMatch);
if (match == null) return;

this.protocol = this.match(match,2);

this.host = this.match(match,3);
this.port = this.match(match,5);

this.href = this.match(match,6);
this.query = this.match(match,8);

if (decode) this.decodeSeo();

if (this.href.match(/eBayISAPI.dll/i)) this.parseIsapi(this.query);
else this.parseQuery(this.query);

this.href = this.decodeUri(this.href);
this.hash = this.match(match,10);

},

//> private String match(Object, int);
match : function(match,ndx) {
return ((match.length > ndx) && match[ndx])?match[ndx]:"";
},

//> private void parseQuery(String);
parseQuery : function(query) {
this.decodeParams(query);
},

//> private void parseIsapi(String);
parseIsapi : function(query) {
var params = (query)?query.split("&"):new Array();
this.isapi = params.shift();this.query = params.join("&");
this.decodeParams(this.query);
},

/**
* Adds a name-value pair as a parameter. The function allows duplicate
* attributes with different values. The name-value pair is registered in a
* parameter array. You can specify this parameter array and by default this
* class has a internal array which is used to build the uri.
*
* @param {String} name
*        the name of the parameter
* @param {String} value
*        the value of the parameter
* @param {Object} params
*        the specified parameter array. The builder will use a internal
*        paramter array if this parameter is <code>null</code>
*
*/
//> public void appendParam(String, String, Object);
appendParam : function(name,value,params) {

params = (params)?params:this.params;

if (params[name] == null) params[name] = value;
else if (typeof(params[name]) == "object") params[name].push(value);
else params[name] = new Array(params[name],value);

},

/**
* Adds all paramters from a parameter array to this buider's internal
* paramter array, which is used to build the uri.
* <p>
* Notes: This will not overwrite the existing paramters. If the paramters
* are duplicate with the existing one, the value will be appended as an
* other value of the same paramter name.
*
* @param {Object} params
*        the custom parameter array from which the parameter will be added
*        to the builder's internal array
* @see   #appendParam(String, String, Object)
*/
//> public void appendParams(Object);
appendParams : function(params) {
for (var name in params) {
var param = params[name];
if (typeof(param) != "object") this.appendParam(name,param);
else for (var idx = 0;(idx < param.length);idx++) this.appendParam(name,param[idx]);
}
},

/**
* Parses the paramters from the query string to the builder's internal
* parameter array.
*
* @param {String} query
*        the qurey string to be parsed
*/
//> public void decodeParams(String);
decodeParams : function(query) {

var pairs = (query)?query.split("&"):new Array();
for (var idx = 0;(idx < pairs.length);idx++) {

var pair = pairs[idx].split("="),name = this.decodeParam(pair[0]);
var value = (pair.length > 1)?this.decodeParam(pair[1].replace(/\+/g,"%20")):"";

this.appendParam(name,value);

}

},

/**
* Builds the qurey string from a parameter array.
*
* @param {Object} params
*        a specified parameter array. This function will use the builder's
*        internal parameter array if you leave this parameter as
*        <code>null</code>
* @String {String}
*        the combined qurey string
*/
//> public String encodeParams(Object);
encodeParams : function(params) {

var pairs = new Array();
var params = (params)?params:this.params;

for (var name in params) {
if (typeof(params[name]) != "object") pairs.push(this.encodeParam(name).concat("=",this.encodeParam(params[name])));
else for (var idx = 0;(idx < params[name].length);idx++) pairs.push(this.encodeParam(name).concat("=",this.encodeParam(params[name][idx])));
}

return pairs.join("&");

},

/**
* Parses the paramters from the SEO uri query string to the builder's
* internal parameter array.
*
*/
//> public void decodeSeo();
decodeSeo : function() {

var match = this.href.match(/(.*)_W0QQ(.*)/);
if (match == null) return;

this.href = match[1];

var pairs = match[2].split("QQ");
for (var idx = 0;(idx < pairs.length);idx++) {

var pair = pairs[idx].split("Z");

var name = this.decodeParam(pair[0].replace(this.seoParam,"%$1"));
var value = (pair.length > 1)?this.decodeParam(pair[1].replace(this.seoParam,"%$1")):"";

this.appendParam(name,value);

}

},

/**
* Parses the paramters from the form element to a parameter array.
*
* @param {Object} form
*        the form element to be parsed
* @return {Object}
*        the parameter array contains all parameters pair from the form
*/
//> public Object decodeForm(Object);
decodeForm : function(form) {

var params = new Object();

var elements = form.elements;
for (var idx = 0;(idx < elements.length);idx++) {

var element = elements[idx];
if (element.disabled) continue;

var type = element.type,name = element.name;value = element.value;
if (type.match(/text|hidden|textarea|password|file/)) this.appendParam(name,value,params);
else if (type.match(/radio|checkbox/) && element.checked) this.appendParam(name,value,params);
else if (type.match(/select-one|select-multiple/)) this.appendSelect(element,params);

}

return params;

},

/**
* Gets the options from a select HTML control to a parameter array.
*
* @param {Object} select
*        the select HTML control to be parsed
* @param {Object} params
*        the parameter array which holds the options information. If this
*        parameter is <code>null</code>, the builder's internal array will
*        be used
*/
//> public void appendSelect(Object, Object);
appendSelect : function(select,params) {
var options = select.options;
for (var idx = 0;(idx < options.length);idx++) {
if (options[idx].selected) this.appendParam(select.name,options[idx].value,params);
}
},

/**
* Gets the combined uri from the known information.
*
* @return {String}
*         the combined uri string
*/
//> public String getUri();
getUri : function() {

var uri = (this.protocol)?this.protocol.concat("://"):"";

if (this.host) uri = uri.concat(this.host);
if (this.port) uri = uri.concat(":",this.port);

if (this.href) uri = uri.concat(this.encodeUri(this.href));
if (this.isapi) uri = uri.concat("?",this.isapi);

var query = this.encodeParams(this.params);
if (query) uri = uri.concat(this.isapi?"&":"?",query);
if (this.hash) uri = uri.concat("#",this.hash);

return this.uri = uri;

}

})
.inits(function(){

var explorer = (navigator.userAgent.indexOf("MSIE") >= 0);

var content = vjoPro.dsf.utils.UriBuilder.meta(explorer?"httpEquiv":"http-equiv","Content-Type");
var charset	= (content)?content.getAttribute("content"):null;

this.prototype.encodeUri = (charset && charset.match(/utf/gi))?vjoPro.dsf.Enc.encodeURI:window.escape;
this.prototype.decodeUri = (charset && charset.match(/utf/gi))?vjoPro.dsf.Enc.decodeURI:window.unescape;

this.prototype.encodeParam = (charset && charset.match(/utf/gi))?vjoPro.dsf.Enc.encodeURIComponent:window.escape;
this.prototype.decodeParam = (charset && charset.match(/utf/gi))?vjoPro.dsf.Enc.decodeURIComponent:window.unescape;

this.prototype.uriMatch = new RegExp("(([^:]*)://([^:/?]*)(:([0-9]+))?)?([^?#]*)([?]([^#]*))?(#(.*))?");
this.prototype.seoParam = new RegExp("Q([0-9a-fA-F][0-9a-fA-F])","g");

})
.endType();
