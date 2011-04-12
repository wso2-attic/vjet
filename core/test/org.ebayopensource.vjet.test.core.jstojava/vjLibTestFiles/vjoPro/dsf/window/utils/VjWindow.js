vjo.ctype("vjoPro.dsf.window.utils.VjWindow")
.props({
/**
* Opens a new browser window with specified arguments. This function calls
* <code>window.open()</code> and provides three addtional frequently used
* setting attributes.
*
* @param {jva.net.URL|String} url
*        A specified URL of the page to open. If it is <code>null</code>,
*        nothing would be shown in the new window
* @param {String} windowName
*        An optional value string which will be passed as the second
*        parameter to <code>window.open()</code>. This string contains the
*        information about window's name and open target
* @param {String} features
*        An optional value string which will be passed as the third
*        parameter to <code>window.open()</code>. This string contains the
*        information about the display styles of the new window
* @param {boolean} replace
*        A boolean value that specifies whether the URL creates a new entry
*        or replaces the current entry in the history list.
* @param {boolean} center
*        A boolean value that specifies whether the new window need to be
*        shown in the center of the screen
* @param {int} width
*        A int value that specifies the width of the new window
* @param {int} height
*        A int value that specifies the height of the new window
* @return {Object}
*        The reference of the newly opened window
*/
//> public Object open(String,String,String,boolean,boolean,int,int);
//> public Object open(Object,String,String,boolean,boolean,int,int);
open : function(pUrl,pWindowName,pFeatures,pReplace,pCenter,pWidth,pHeight) {
if (pCenter) {
var iLeft = (window.screen.width-pWidth)/2;
var iTop = (window.screen.height-pHeight)/2;
pFeatures += ",left=" + iLeft + ",top=" + iTop;
}
return window.open(pUrl,pWindowName,pFeatures,pReplace);
},

/**
* Locates the window to a new destination url. This function applies passed
* url to <code>document.location.href</code>.
*
* @param {java.net.URL|String} url
*        A url string that the window will navigate to.
*/
//> public void location(String);
//> public void location(Object);
location : function(pUrl) {
document.location.href=pUrl;
},

/**
* Displays an alert box with a message and an OK button.
*
* @param {Object} message
*        A message object to be shown in the alert box
*/
//> public void alert(Object);
alert : function(pMessage) {
window.alert(pMessage);
},

/**
* Displays a dialog box with a message and an OK and a Cancel button.
*
* @param {Object} message
*        A message object to be shown in the dialog box
*/
//> public boolean confirm(Object);
confirm : function(pMessage) {
return window.confirm(pMessage);
}
})
.endType();


