vjo.ctype("dox.ebay.vjoPro.vjoPro4javadev.samples.vjlib.FormAutoClearTextField")
.needs("vjoPro.dsf.document.Text")
.props({
//> public boolean clearTextbox(String psId)
clearTextbox:function(psId){
vjoPro.dsf.document.Text.autoClear(psId);
return true;
}

})
.endType();
