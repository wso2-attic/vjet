vjo.ctype("vjoPro.dsf.docx.Positioning")
.props({
/**
* Gets the number of pixels that the content is scrolled to the left and
* top. This function will return an Array structure which has two elements -
* the first one is the pixel number of scorlled content to left, and the
* other one is the number to top.
*
* Examples:
* <code>
*    var offsets = getScrollLeftTop();
*    alert("The left offset is: " + offsets[0]);
*    alert("The top offset is: " + offsets[1]);
* </code>
*
* @return {int[]}
*         an array structure that contains number of pixels of scorlled
*         content to left and top
*/
//> public int[] getScrollLeftTop();
getScrollLeftTop : function() {
var d = document, rv = [0,0], db = d.body, de = d.documentElement;
if (db)
{
rv[0] += db.scrollLeft;
rv[1] += db.scrollTop;
}
if (de)
{
//using xhtml1-transitional.dtd de scrolltop needs to be appended
rv[0] += de.scrollLeft;
rv[1] += de.scrollTop;
}
return rv;
},

/**
* Gets the number of pixels that the upper left corner of the current
* element is offset to the left within the offsetParent node.
*
* @param {HtmlElement} elem
*        an element to be got the number of pixels
* @return {int}
*        the number of pixels of the element's offset to the left
*/
//> public int getOffsetLeft(HTMLElement);
getOffsetLeft : function(poElem) {
var e = poElem, l = 0;
while (e)
{
l += e.offsetLeft;
e = e.offsetParent;
}
return l;
},

/**
* Gets the number of pixels that the upper left corner of the current
* element is offset to the top within the offsetParent node.
*
* @param {HtmlElement} elem
*        an element to be got the number of pixels
* @return {int}
*        the number of pixels of the element's offset to the top
*/
//> public int getOffsetTop(HTMLElement);
getOffsetTop : function(poElem)	{
var e = poElem, t = 0;
while (e)
{
t += e.offsetTop;
e = e.offsetParent;
}
return t;
},

/**
* Gets the number of pixels of the content's width.
*
* @return {int}
*         the number of pixels of the content's width
*/
//> public int getClientWidth();
getClientWidth : function()	{
var s = self, d = document, de =  d.documentElement, w;
if (s.innerWidth)
{
w = s.innerWidth;
}
else if (de && de.clientWidth)
{
w = de.clientWidth;
}
else
{
w = d.body.clientWidth;
}
return w;
},

/**
* Gets the number of pixels of the content's height.
*
* @return {int}
*         the number of pixels of the content's height
*/
//> public int getClientHeight();
getClientHeight : function() {
var s = self, d = document, de =  d.documentElement, h;
if (s.innerHeight)
{
h = s.innerHeight;
}
else if (de && de.clientHeight)
{
h = de.clientHeight;
}
else
{
h = d.body.clientHeight;
}
return h;
},

/**
* Gets the number of pixels that the event element's offset to the left and
* top. This function will return an array structure which has two elements
* - the first one is the pixels number of event element's offset to left,
* and the other one is to top.
* <p>
* Examples:
* <code>
*    var eventOffsets = getScrollLeftTop(event);
*    alert("The event left offset is: " + eventOffsets[0]);
*    alert("The event top offset is: " + eventOffsets[1]);
* </code>
*
* @return {int[]}
*         an array structure that contains number of pixels of event element's
*         offset to left and top
*/
//> public int[] getEventLeftTop(Object);
getEventLeftTop : function(poEvent)	{
//Height offset isn't exact in non-IE.  It doesn't accomodate for toolbars and
//status bar.  Must be calculated by outer and inner height, minus a fixed height
//for status bar
var u = "undefined", evt = window.event || poEvent,
xOff = (typeof(screenLeft) != u) ? screenLeft : screenX,
yOff = (typeof(screenTop) != u) ? screenTop : (screenY + (outerHeight - innerHeight) - 25);

return [evt.screenX - xOff,evt.screenY - yOff];
}
})
.endType();
