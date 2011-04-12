vjo.ctype("vjoPro.dsf.window.utils.VjWindowUtils")
.props({
/**
* Gets the height value of the browser window.
*
* @return {int}
*         The hight value of the browser window
*/
//> public int getBrowserWindowHeight();
getBrowserWindowHeight : function()	{
var s = this;//<Type::VjWindowUtils
var d = document;
var de = d.documentElement;
if (s.innerHeight){
// all except Explorer
return s.innerHeight;
}else if (de && de.clientHeight){
// Explorer 6 Strict Mode
return de.clientHeight;
}
return d.body.clientHeight;
},

/**
* Gets the width value of the browser window.
*
* @return {int}
*         The width value of the browser window
*/
//> public int getBrowserWindowWidth();
getBrowserWindowWidth : function() {
var s = this;//Type::VjWindowUtils
var d = document;//<Type::HTMLDocument
var de =  d.documentElement;
if (s.innerWidth){
// all except Explorer
return s.innerWidth;
}else if (de && de.clientWidth){
// Explorer 6 Strict Mode
return de.clientWidth;
}
return d.body.clientWidth;
},

/**
* Gets the distance from the top-left corner of the page's current
* displayed part to the top-left corner of the page.
*
* @return {Object}
*         An array contains the position information as [X,Y]
*/
//> public Object getScrollXY();
getScrollXY : function() {
var scrOfX = 0, scrOfY = 0;
if( typeof( window.pageYOffset ) == 'number' ) {
//Netscape compliant
scrOfY = window.pageYOffset;
scrOfX = window.pageXOffset;
} else if( document.body && ( document.body.scrollLeft || document.body.scrollTop ) ) {
//DOM compliant
scrOfY = document.body.scrollTop;
scrOfX = document.body.scrollLeft;
} else if( document.documentElement && ( document.documentElement.scrollLeft || document.documentElement.scrollTop ) ) {
//IE6 standards compliant mode
scrOfY = document.documentElement.scrollTop;
scrOfX = document.documentElement.scrollLeft;
}
return [ scrOfX, scrOfY ];
},

//> public String toPixels(int);
toPixels : function(number) {
return number + "px";
},

/**
* Gets the vertical distance from the top-left corner of the page's current
* displayed part to the top side of the page.
*
* @return {int}
*         The value of the distance
* @see #clientTop
* @see #browserTop
*/
//> public int scrollTop();
scrollTop : function() {

if (window.pageYOffset != null) { return window.pageYOffset; }

if (document.documentElement) { return Math.max(document.documentElement.scrollTop,document.body.scrollTop); }
else { return document.body.scrollTop; }

},

/**
* Gets the horizontal distance from the top-left corner of the page's
* current displayed part to the left side of the page.
*
* @return {int}
*         The value of the distance
* @see #clientLeft
* @see #browserLeft
*/
//> public int scrollLeft();
scrollLeft : function() {

if (window.pageXOffset != null) { return window.pageXOffset; }

if (document.documentElement) { return Math.max(document.documentElement.scrollLeft,document.body.scrollLeft); }
else { return document.body.scrollLeft; }

},

/**
* Gets the page's actual width which includes the invisible part of the page.
*
* @return {int}
*         The width value of the page.
* @see #scrollHeight
* @see #clientWidth
*/
//> public int scrollWidth();
scrollWidth : function() {
if (document.documentElement) { return document.documentElement.scrollWidth; }
else { return Math.max(document.body.scrollWidth,document.body.offsetWidth); }
},

/**
* Gets the page's actual height which includes the invisible part of the page.
*
* @return {int}
*         The height value of the page
* @see #scrollWidth
* @see #clientHeight
*/
//> public int scrollHeight();
scrollHeight : function() {
if (document.documentElement) { return document.documentElement.scrollHeight; }
else { return Math.max(document.body.scrollHeight,document.body.offsetHeight); }
},

/**
* Gets the vertical distance from the top-left corner of the page to the top
* side of the page. It always returns <code>0</code>
*
* @return {int}
*         The value of the distance. It's always <code>0</code>
* @see #scrollTop
* @see #browserTop
*/
//> public int clientTop();
clientTop : function() {
if (document.documentElement) { return document.documentElement.clientTop; }
else { return document.body.clientTop; }
},

/**
* Gets the horizontal distance from the top-left corner of the page to the
* left side of the page. It always returns <code>0</code>
*
* @return {int}
*         The value of the distance. It's always <code>0</code>
* @see #scrollLeft
* @see #browserLeft
*/
//> public int clientLeft();
clientLeft : function() {
if (document.documentElement) {  return document.documentElement.clientLeft; }
else { return document.body.clientLeft; }
},

/**
* Gets the page's display width in which the invisible part is not included.
*
* @return {int}
*         The width value of the page's display part.
* @see    #clientHeight
* @see    #scrollWidth
*/
//> public int clientWidth();
clientWidth : function() {
var documentElement = document.documentElement;
if (documentElement && window.innerWidth) { return Math.min(documentElement.clientWidth,window.innerWidth); }
else if (documentElement && documentElement.clientWidth ) { return documentElement.clientWidth; }
else if (window.innerWidth) { return window.innerWidth; }
else if (document.body.clientWidth) { return document.body.clientWidth; }
else { return document.body.offsetWidth; }
},

/**
* Gets the page's display height in which the invisible part is not included.
*
* @return {int}
*         The height value of the page's display part.
* @see    #clientWidth
* @see    #scrollHeight
*/
//> public int clientHeight();
clientHeight : function() {
var documentElement = document.documentElement;
if (documentElement && window.innerHeight) { return Math.min(documentElement.clientHeight,window.innerHeight); }
else if (documentElement && documentElement.clientHeight) { return documentElement.clientHeight; }
else if (window.innerHeight) { return window.innerHeight; }
else if (document.body.clientHeight) { return document.body.clientHeight; }
else { return document.body.offsetHeight; }
},

/**
* Gets the vertical distance from the top-left corner of the page to the top
* side of the screen.
*
* @return {int}
*         The value of the distance
* @see #scrollTop
* @see #clientTop
*/
//> public int browserTop();
browserTop : function() {
return (window.innerHeight)?window.screenY + (window.outerHeight - window.innerHeight):window.screenTop;
},

/**
* Gets the horizontal distance from the top-left corner of the page to the left
* side of the screen.
*
* @return {int}
*         The value of the distance
* @see #scrollLeft
* @see #browserLeft
*/
//> public int browserLeft();
browserLeft : function() {
return (window.innerWidth)?window.screenX + (window.outerWidth - window.innerWidth):window.screenLeft;
},

/**
* Gets the vertical distance from the mouse position to the top side of
* the page when a event is triggered.
*
* @return {int}
*         The value of the distance
*/
//> public int eventTop(MouseEvent);
eventTop : function(event) {

if (event.pageY != null) { return event.pageY; }

if (document.documentElement) { return event.clientY + Math.max(document.documentElement.scrollTop,document.body.scrollTop); }
else { return event.clientY + document.body.scrollTop; }

},

/**
* Gets the horizontal distance from the mouse position to the left side of
* the page when a event is triggered.
*
* @return {int}
*         The value of the distance
*/
//> public int eventLeft(MouseEvent);
eventLeft : function(event) {

if (event.pageX != null) { return event.pageX; }

if (document.documentElement) { return event.clientX + Math.max(document.documentElement.scrollLeft,document.body.scrollLeft); }
else { return event.clientX + document.body.scrollLeft; }

},

/**
* Gets the vertical distance from the top-left corner of the specified
* element to the top side of the page.
*
* @param {Object}
*        A DOM element
* @return {int}
*         The value of the distance
*/
//> public int offsetTop(HTMLElement);
offsetTop : function(element) {
var clientTop = (document.documentElement && document.documentElement.clientTop)?document.documentElement.clientTop:0;
for (var offsetTop = 0;(element != null);element = element.offsetParent) { offsetTop += element.offsetTop; }
return offsetTop + clientTop;
},

/**
* Gets the horizontal distance from the top-left corner of the specified
* element to the left side of the page.
*
* @param {Object}
*        A DOM element
* @return {int}
*         The value of the distance
*/
//> public int offsetLeft(HTMLElement);
offsetLeft : function(element) {
var clientLeft = (document.documentElement && document.documentElement.clientLeft)?document.documentElement.clientLeft:0;
for (var offsetLeft = 0;(element != null);element = element.offsetParent) { offsetLeft += element.offsetLeft; }
return offsetLeft + clientLeft;
},

/**
* Opens a new browser window with specified arguments. This function calls
* <code>window.open()</code>.
*
* @param {String} url
*        A specified URL of the page to open. If it is <code>null</code>,
*        nothing would be shown in the new window
* @param {String} windowName
*        An optional value string which will be passed as the second
*        parameter to <code>window.open()</code>. This string contains the
*        information about window's name and open target
* @param {vjo.dsf.window.utils.JsWindowFeatures} features
*        An optional value string which will be passed as the third
*        parameter to <code>window.open()</code>. This string contains the
*        information about the display styles of the new window
* @return {Object}
*        The reference of the newly opened window
*/
//> public Object openWindow(String,String,vjo.dsf.window.utils.JsWindowFeatures);
openWindow : function(url,name,features) {

var parameters = new Array();

features.top = this.browserTop() + Math.round((this.clientHeight() - features.height)/2) + 25;
features.left = this.browserLeft() + Math.round((this.clientWidth() - features.width)/2);

for (var key in features) parameters.push(key.concat("=",features[key]));
return window.open(url,name,parameters.join(","),true);

}

})
.endType();

