vjo.ctype("dox.ebay.vjoPro.vjoPro4javadev.samples.vjlib.FieldFocusExample")
.needs("vjoPro.dsf.document.Element")
.props({

//> public boolean focusTextbox(String psId)
focusTextbox:function(psId){
vjoPro.dsf.document.Element.get(psId).focus();
return true;
}

})
.endType();
