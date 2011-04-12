vjo.ctype("dox.ebay.vjoPro.vjoPro4javadev.samples.vjlib.FormGetSetAttributes")
.needs("vjoPro.dsf.document.Form")

.props({

//>public boolean getFormObj(String psName)
getFormObj:function(psName){
var frmObj = vjoPro.dsf.document.Form.get(psName);
alert("Form object="+frmObj);
return true;
},

//>public boolean getAndSetFormAction(String psName, String psAction)
getAndSetFormAction:function(psName, psAction){
vjoPro.dsf.document.Form.setAction(psName, psAction);
alert("Form action="+vjoPro.dsf.document.Form.getAction(psName));
return true;
},

//>public boolean getAndSetFormAttribute(String psName, String psAttrName, String psAttrValue)
getAndSetFormAttribute:function(psName, psAttrName, psAttrValue){
vjoPro.dsf.document.Form.setAttr(psName, psAttrName, psAttrValue);
alert("Form attribute value="+vjoPro.dsf.document.Form.getAttr(psName, psAttrName));
return true;
},

//>public boolean getFormElem(String psName, String psElemName)
getFormElem:function(psName, psElemName){
alert(vjoPro.dsf.document.Form.getElem(psName, psElemName).value);
return true;
},

//>public void submitForm(String psName)
submitForm:function(psName){
vjoPro.dsf.document.Form.submit(psName);
}
})
.endType();
