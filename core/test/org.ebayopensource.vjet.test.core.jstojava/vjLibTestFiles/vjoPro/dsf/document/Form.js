vjo.ctype("vjoPro.dsf.document.Form")
.props({
/**
* Gets an element in the form by specified name.
*
* @param {String} name
*        the name of a element to be get
* @return {Object}
*        the element in the form. If no such element, return <code>null</code>
*/
//> public HTMLFormElement get(String);
get : function(psName) {
var f = document.forms[psName];//<HTMLFormElement
return f?f:null;
},

/**
* Gets an element in a specified form.
*
* @param {String} name
*        the name of the form that contains the element
* @param {String} elemName
*        a name of the element to be get
* @return {Object}
*        the element in the form. If no such element, return <code>null</code>
*/
//> public HTMLElement getElem(String,String);
getElem : function(psFormName,psElemName) {
var f =this.get(psFormName);//<HTMLFormElement
var e = null;//<HTMLElement
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
var f = this.get(psName);//<HTMLFormElement
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
var f = this.get(psName);
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
var f = this.get(psName);//<HTMLFormElement
var v = null; //<String
if(f)
v = eval("f." + psAttrName.toLowerCase());
return v;
}
})
.endType();

