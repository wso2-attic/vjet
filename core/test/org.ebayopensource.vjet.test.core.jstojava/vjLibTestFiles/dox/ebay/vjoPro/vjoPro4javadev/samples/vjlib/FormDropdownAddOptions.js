vjo.ctype("dox.ebay.vjoPro.vjoPro4javadev.samples.vjlib.FormDropdownAddOptions")
.needs("vjoPro.dsf.document.Select")
.needs("vjoPro.dsf.document.Element")
.props({
//> public boolean addToSelect(String psId, String psValId)
addToSelect:function(psId, psValId){
var e = vjoPro.dsf.document.Element.get(psId);
var sVal = vjoPro.dsf.document.Element.get(psValId).value;
vjoPro.dsf.document.Select.addOption(e, sVal, sVal);
return true;
}

})
.endType();
