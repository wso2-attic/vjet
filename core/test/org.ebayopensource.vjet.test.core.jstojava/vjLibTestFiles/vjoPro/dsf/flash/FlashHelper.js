vjo.ctype("vjoPro.dsf.flash.FlashHelper")
.needs('vjoPro.dsf.flash.SwfObjectDef')
.props({
/**
* Adds the flash object to the page.
*
* @param {java.util.HashMap} cfg
*        the config parameters of the flash object
*/
//> public void addFlash(Object);
addFlash : function (pCfg) {
var origTitle = window.title;
var so = new this.vj$.SWFObject(pCfg.url, "flash_" + pCfg.containerName,  pCfg.width, pCfg.height, pCfg.minVersion, pCfg.bgcolor, false);//<SWFObject
so.addParam("wmode", pCfg.wmode);
if (pCfg.allowScriptAccess){
so.addParam("allowscriptaccess", pCfg.allowScriptAccess);
}
so.addVariable("ebayConfig", encodeURIComponent(pCfg.ebayConfig || ""));
so.addVariable("ebayData", encodeURIComponent(pCfg.ebayData || ""));
so.addVariable("ebayContent", encodeURIComponent(pCfg.ebayContent || ""));
if (!so.write(pCfg.containerName))
{
var o = document.getElementById(pCfg.containerName);
if (o)
o.innerHTML = pCfg.noFlashMsg || "";
}
window.title = origTitle;
},

/**
* Adds the unstructure flash object to the page. If no config object is
* prepared for the flash object, you can use this method specify the
* parameters for the flash object
*
* @param {java.util.HashMap} flashVars
*        a hash map structure which holds the flash object required parameters
* @param {String} url
*        the url of the flash object
* @param {String} id
*        the string id for the flash object
* @param {String} width
*        the showing width of the flash object
* @param {String} height
*        the showing height of the flash object
* @param {String} minVersion
*        the minimum support version of the flash control for this object
* @param {String} bgcolor
*        the background color of this object
* @param {String} wmode
*        the display mode of the flash object in window. It supports
*        <code>window</code>,<code>opaque</code> and <code>transparent</code>.
*        You can check the flash documentation for more informations
* @param {String} scriptAccess
*        a string parameter specify how the flash object accessing the
*        JavaScript. It supports <code>always</code>, <code>never</code>
*        & <code>samedomain</code>. You can check the flash documentation
*        for more informations
*/
//> public void addUnstructuredFlash(Object,String,String,String,String,String,String,String,String);
addUnstructuredFlash : function(flashVars, url, id, width, height, minVersion, bgcolor, wmode, scriptAccess){
var origTitle = window.title;
var swf = new SWFObject(url, "flash_"+id, width, height, minVersion, bgcolor, false);
swf.addParam("wmode", wmode);
swf.addParam("allowscriptaccess", scriptAccess);
for (var name in flashVars){
swf.addVariable(name, encodeURIComponent(flashVars[name]));
}
swf.write(id);
window.title = origTitle;
}
})
.endType();
