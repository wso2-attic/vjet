vjo.ctype("dox.ebay.vjoPro.vjoPro4jsdev.samples.vjlib.FieldFocusExample")
.needs('vjoPro.dsf.Element')
.props({
//> public boolean focusTextbox(String psId)
focusTextbox:function(psId){
vjoPro.dsf.Element.get(psId).focus();
return true;
}

}).endType();
