vjo.ctype('vjoPro.dsf.utils.Ajax')
.needs(['vjoPro.dsf.EventDispatcher','vjoPro.dsf.Element','vjoPro.dsf.utils.Css'])
.props({
/**
* Applys Ajax response text to a specified DOM element.
*
* @param {String} elementId
*        the id of the element which is used to handle the response
*        text
* @param {String} htmlText
*        the raw html string. This will be applied to the element's attribute
*        <code>innerHTML</code> directly.
* @param {String} jsText
*        a JS fragment. The JS internal function <code>eval()</code> will be
*        called and use this as its paramter
* @param {String} cssText
*        a CSS fragment
*/
//> public void apply(String,String,String,String);
apply : function(psElementId, psHtmlText, psJsText, psCssText) {
var e = this.vj$.Element.get(psElementId);
if(e) {
//set html
if(psHtmlText) {
e.innerHTML = psHtmlText;
}

//set css
if(psCssText) {
this.vj$.Css.apply(psElementId,psCssText);
}

//eval js
if(psJsText) {
eval(vjo.versionJsText(psJsText));
}
}
},

/**
* Cleans all registered event handlers on the page and applys html, js and
* css. This function calls {@link #clean} and {@link #apply} in sequence.
*
* @param {String} elementId
*        the id of the element which is used to handle the response
*        text
* @param {String} htmlText
*        the raw html string. This will be applied to the element's attribute
*        <code>innerHTML</code> directly.
* @param {String} jsText
*        a JS fragment. The JS internal function <code>eval()</code> will be
*        called and use this as its paramter
* @param {String} cssText
*        a CSS fragment
* @param {JsObj} eventIdMap
*        the vjoPro registry Map
* @see #clean
* @see #apply
*/
//> public void cleanApply(String,String,String,String,Object);
cleanApply : function(psElementId, psHtmlText, psJsText, psCssText, poEventIdMap) {
//unregister old event handlers on the page and applys html, js and css
this.clean(poEventIdMap);
this.apply(psElementId, psHtmlText, psJsText, psCssText);
},

/**
* Unregisters old event handlers on the page.
*
* @param {JsObj} eventIdMap
*        the vjoPro registry Map
*/
//> public void clean(Object);
clean : function(poEventIdMap)
{
//unregister old event handlers on the page
var m = poEventIdMap,i,j,e;
for (i in m) {
e= m[i];
for (j=0;j<e.length;j++) {
this.vj$.EventDispatcher.detachHandlers(i,e[j]);
}
}
}
})
.endType();
