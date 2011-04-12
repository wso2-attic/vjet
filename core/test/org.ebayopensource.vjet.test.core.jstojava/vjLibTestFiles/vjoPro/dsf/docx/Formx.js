vjo.ctype("vjoPro.dsf.docx.Formx")
.props({
/**
* Gets the form with the specific id
*
* @param {String or HtmlForm} ref
*       if ref is string, get the form with the id.  else return the form passed in
* @return {Object}
*        the form element. If no such element, return <code>null</code>
*/
//> private HTMLFormElement getx(String);
getx : function(ref) {
if (typeof(ref) == "string"){
var f = document.forms[ref];
return f?f:null;
}
else{
return ref;
}
},

/**
* Gets an element in a specified form.
*
* @param {String or HtmlForm} ref
*       if ref is string, id of the form.  otherwise, ref is the form itself
* @param {String} elemName
*        a name of the element to be get
* @return {HtmlElement}
*        the element in the form. If no such element, return <code>null</code>
*/
//> public HTMLElement getElem(String,String);
//> public HTMLElement getElem(HTMLFormElement,String);
getElem : function(ref,psElemName) {
var f =this.getx(ref), e;
if(f)
e = f.elements[psElemName];
return e?e:null;
},

/**
* Submits the form.
*
* @param {String} name
*        the name of the form
*/
//> public void submit(String);
submit : function(psName) {
var f = this.getx(psName);
if(f)
f.submit();
},

/**
* Sets the form action value.
*
* @param {String} name
*        the name of the form
* @param {String} action
*        a value of the action to be set
*/
//> public void setAction(String,String);
setAction : function(psName, psAction) {
this.setAttr(psName, "action", psAction);
},

/**
* Gets the value of the action from a form.
*
* @param {String} name
*        the name of the form
* @return {String}
*        a value of the form action
*/
//> public String getAction(String);
getAction : function(psName) {
return this.getAttr(psName,"action");
},

/**
* Sets the form target value.
*
* @param {String} name
*        the name of the form
* @param {String} target
*        a value of the target to be set
*/
//> public void setTarget(String,String);
setTarget : function(psName, psTarget) {
this.setAttr(psName, "target", psTarget);
},

/**
* Gets the value of the target from a form
*
* @param {String} name
*        the name of the form
* @return {String}
*        a value of the form target
*/
//> public String getTarget(String);
getTarget : function(psName) {
return this.getAttr(psName,"target");
},

/**
* Sets the value to a specified form attribute.
*
* @param {String} name
*        the name of the form
* @param {String} attrName
*        the name of the attribute of the form
* @param {String} attrValue
*        a value of the attribute to be set
*/
//> public void setAttr(String,String,String);
setAttr : function(psName, psAttrName, psAttrValue)	{
var f = this.getx(psName);
if(f)
eval("f." + psAttrName.toLowerCase() + "=psAttrValue;");
},

/**
* Gets a specified form attribute value.
*
* @param {String} name
*        the name of the form
* @param {String} attrName
*        a name of the attribute of the form
* @return {String}
*        the value of the attribute
*/
//> public String getAttr(String,String);
getAttr : function(psName, psAttrName) {
var f = this.getx(psName), v = null;
if(f)
v = eval("f." + psAttrName.toLowerCase());
return v;
}
})
.endType();

