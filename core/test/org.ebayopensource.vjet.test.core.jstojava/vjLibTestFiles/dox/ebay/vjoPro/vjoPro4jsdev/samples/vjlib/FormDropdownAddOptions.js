vjo.ctype("dox.ebay.vjoPro.vjoPro4jsdev.samples.vjlib.FormDropdownAddOptions")
.needs("vjoPro.dsf.document.Select")
.props({
//> public boolean addToSelect(String psId, String psValId)
addToSelect:function(psId, psValId){
var e = vjoPro.dsf.Element.get(psId);
var sVal = vjoPro.dsf.Element.get(psValId).value;
vjoPro.dsf.document.Select.addOption(e, sVal, sVal);
return true;
}

})
.endType();
