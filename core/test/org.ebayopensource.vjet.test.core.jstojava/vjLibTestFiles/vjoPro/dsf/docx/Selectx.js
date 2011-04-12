vjo.ctype("vjoPro.dsf.docx.Selectx")
.needs("vjoPro.dsf.docx.Elementx")
.props({
E : vjoPro.dsf.docx.Elementx,

/**
* Adds a new option to a select list.
*
* @param {Object} select
*        the referernce of the select element
* @param {String} val
*        the value of the newly added option. It will be the value of
*        <code>value</code> attribute of the option, such as,
*        <code>&lt;option value="val"&gt;</code>
* @param {String} text
*        the display text of the newly added option. It looks like
*        <code>&lt;option&gt;text&lt;/option&gt;</code>
*
*/
//> public void addOption(String,String,String);
//> public void addOption(HTMLSelectElement,String,String);
addOption : function(poSelect, psVal, psText) {
var t = this, e = t.get(poSelect), o, os;
if(e)
{
o = t.createOption(psText,psVal);
os = e.options;
os[os.length] = o;
}
},

/**
* Creates a HTML option element for the select control.
*
* @param {String} text
*        the display text of the newly added option. It looks like
*        <code>&lt;option&gt;text&lt;/option&gt;</code>
* @param {String} val
*        the value of the newly added option. It will be the value of
*        <code>value</code> attribute of the option, such as,
*        <code>&lt;option value="val"&gt;</code>
* @return {Object}
*        the newly created option element. It looks like
*        <code>&lt;option value="val"&gt;text&lt;/option&gt;</code>
*/
//> public HTMLOptionElement createOption(String,String,boolean,boolean);
//> public HTMLOptionElement createOption(String,String);
createOption : function(psText, psVal) {
return this.createOption(psText,psVal,false,false);
},


/**
* Cleans all options in a given select element
*
* @param {Object} select
*        a reference of the select element that all options will be removed
*        from
*/
//> public void clear(String);
//> public void clear(HTMLSelectElement);
clear : function(poSelect) {
var e = this.get(poSelect), os, i, l;
if(e){
os = e.options;
l = os.length;
for(i=l-1;i>=0;i--){
os[i] = null;
}
}
},

//> private Object get(String);
get : function(poSelect){
var e = poSelect;
if(typeof(poSelect)=="string"){
e = this.E.get(poSelect);
}
return e;
}

})
.endType();

