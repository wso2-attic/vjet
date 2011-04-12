vjo.ctype("vjoPro.dsf.utils.Popup")
/**
* Pops up a new window with specified url.
*/
.props({
/**
* Popped up a window with specified url. This function is calling the
* <code>window.open()</code> to implement the functionality.
*
* @param {String} url
*        A string url requested for the new window
* @param {String} name
*        A name of the new window
* @param {int} width
*        A width value of the new window
* @param {int} height
*        A height value of the new window
* @param {int} left
*        An offset value of the new window to the left
* @param {int} top
*        An offset value of the new window to the top
* @param {boolean} scrollabar
*        A boolean value enable/disable the scrollbar of the new window
* @param {boolean} resizable
*        A boolean value indecates whether or not the new window is resizable
* @return {Object}
*        The reference of the new created window
*/
//> public Object show(String,String,int,int,int,int,Boolean,Boolean);
//> public Object show(String,String,int,int);
show : function(psUrl, psName, piWidth, piHeight, piLeft, piTop, pbScrollbars, pbResizable) {
var p = "toolbar=0,location=0,status=0,menubar=0",l,t,w;
if(!piLeft && piWidth)
l = parseInt(screen.availWidth - piWidth)/2;
else
l = piLeft;
if(!piTop && piHeight)
t = parseInt(screen.availHeight - piHeight)/2;
else
t = piTop;
if(piWidth)
p += "," + "width=" + piWidth;
if(piHeight)
p += "," + "height=" + piHeight;
p += "," + "screenX=" + l + "," + "left=" + l;
p += "," + "screenY=" + t + "," + "top=" + t;
p += "," + "scrollbars=" + pbScrollbars?1:0;
p += "," + "resizable=" + pbResizable?1:0;
w = window.open(psUrl, psName, p);
if (w)
w.focus();
return w;
}
})
.endType();

