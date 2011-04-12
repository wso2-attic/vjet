vjo.ctype("vjoPro.dsf.docx.ElementUIx")
.needs("vjoPro.dsf.docx.Elementx")
.props({
E : vjoPro.dsf.docx.Elementx,
/**
*
* Specifies whether or not a DOM element should be displayed. This function
* uses the <code>style.display</code> property. If the element is not
* displayed, it does not affect the page layout.
*
* @param {String or HtmlElement} ref
*        A String id of the DOM element to be shown/hidden
*	 Or the element to be shown/hidden
* @param {boolean} display
*        A boolean value representing whether or not to show the element
* @see   vjoPro.dsf.Element.toggleVisibility
*/
//> public void toggleHideShow(String,boolean);
//> public void toggleHideShow(String);
//> public void toggleHideShow(HTMLElement,boolean);
//> public void toggleHideShow(HTMLElement);
toggleHideShow : function(ref, pbDisplay) {
var e = this.E.getx(ref), s, d, u = "undefined";
if (e)
{
s = e.style;
d = s.display;
if (typeof(pbDisplay)===u)
{
pbDisplay = (d === "" || d === "block") ? false : true;
}
e.bIsShown = pbDisplay;
s.display = (pbDisplay) ? "block" : "none";
}
},

/**
* Appends a specified DOM element to the <code>BODY</code> element.
*
* @param {String or HtmlElement} ref
*        A String id of the DOM element
*	 or the HtmlElement itself
*/
//> public void promoteToBody(String);
promoteToBody : function(ref) {
var e = this.E.getx(ref), b = document.body;
if(e && b && e.parentNode && (e.parentNode !== b)){
e.parentNode.removeChild(e);
b.appendChild(e);
}
},

/**
* Specifies whether or not a DOM element should be visible. This function
* uses the <code>style.visibility</code> property. Making the element
* invisible will affect the layout.
*
* @param {String or HtmlElement} ref
*        A String id of the DOM element
*	 or the HtmlElement itself
* @param {boolean} display
*        A boolean value representing whether or not the element should be
*        visible
* @see   vjoPro.dsf.Element.toggleVisibility
*/
//> public void toggleVisibility(String,boolean);
//> public void toggleVisibility(String);
//> public void toggleVisibility(HTMLElement,boolean);
//> public void toggleVisibility(HTMLElement);
toggleVisibility : function(ref, pbVisible) {
var e = this.E.getx(ref), v, s, u = "undefined";
if (e)
{
s = e.style;
v = s.visibility;
if (typeof(pbVisible)===u)
{
pbVisible = (v === "") ? false : true;
}

e.bIsVisible = pbVisible;
s.visibility = (pbVisible) ? "" : "hidden";
}
},

/**
* Sets a DOM element whther or not to be enable. If the element is disabled,
* no action would be invoked by any event on it.
*
* @param {String} id
*        A String id of the DOM element
*	 or the HtmlElement itself
* @param {boolean} enable
*        A boolean value representing whether or not the element should be
*        enable
*/
//> public void enable(String,boolean);
enable : function(psId, pbEnable) {
var e = this.E.get(psId);
if (e)
e.disabled = !pbEnable;
},

/**
* Sets how far the left edge of an element is to the right of the left edge
* of the parent element.
*
* @param {String or HtmlElement} ref
*        A String id of the DOM element
*	 or the HtmlElement itself
* @param {String} left
*        A String that represents the number of the left offset
* @return {String}
*        A String that represents the number of the left offset which is
*        same as the parameter <code>left</code>
*/
//> public String left(String,String);
//> public String left(HTMLElement,String);
left : function(ref, psLeft) {
return this.setLTWH(ref, psLeft, "Left");
},

/**
* Sets how far the top edge of an element is above the top edge of the
* parent element.
*
* @param {String or HtmlElement} ref
*        A String id of the DOM element
*	 or the HtmlElement itself
* @param {String} top
*        A String represents the number of the top offset
* @return {String}
*        A String represents the number of the top offset which is same as
*        the parameter <code>top</code>
*/
//> public String top(String,String);
//> public String top(HTMLElement,String);
top : function(ref, psTop)	{
return this.setLTWH(ref, psTop, "Top");
},

/**
* Sets the width of the element.
*
* @param {String or HtmlElement} ref
*        A String id of the DOM element
*	 or the HtmlElement itself
* @param {String} width
*        A String that represents the number of the width
* @return {String}
*        A String that represents the number of the width which is same as
*        the parameter <code>width</code>
*/
//> public String width(String,String);
//> public String width(HTMLElement,String);
width : function(ref, psWidth)	{
return this.setLTWH(ref, psWidth, "Width");
},

/**
* Sets the height of the element.
*
* @param {String or HtmlElement} ref
*        A String id of the DOM element
*	 or the HtmlElement itself
* @param {String} height
*        A String that represents the number of the height
* @return {String}
*        A String that represents the number of the height which is same as
*        the parameter <code>height</code>
*/
//> public String height(String,String);
//> public String height(HTMLElement,String);
height : function(ref, psHeight) {
return this.setLTWH(psId, psHeight, "Height");
},

//> protected String top(String,String,String);
//> protected String top(HTMLElement,String,String);
setLTWH : function(ref, psVal, psName) {
var e = this.E.getx(ref);
if (e)
{
if ((psVal != null) && !isNaN(parseInt(psVal)))
e.style[psName.toLowerCase()] = psVal;
var rtnVal = e["offset" + psName]; return rtnVal;
}
},
//> public void toggleHideShowRow(String,Boolean);
//> public void toggleHideShowRow(String);
//> public void toggleHideShowRow(HTMLElement,Boolean);
//> public void toggleHideShowRow(HTMLElement);
toggleHideShowRow : function(ref, pbDisplay) {
var e = this.E.getx(ref), ua = navigator.userAgent.toString().toLowerCase(), s, d, u = "undefined",
p = (ua.indexOf('firefox')!=-1)?"table-row":"block";
if (e)
{
s = e.style;
d = s.display;
if (typeof(pbDisplay)===u)
{
pbDisplay = (d === "" || d === p) ? new Boolean(false) : new Boolean(true);
}
e.bIsShown = pbDisplay;
s.display = (pbDisplay) ? p : "none";
}
}
})
.endType();



