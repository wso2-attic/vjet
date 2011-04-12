vjo.ctype("dox.ebay.vjoPro.vjoPro4javadev.samples.vjlib.FormSelectText")
.needs("vjoPro.dsf.document.Element")
.props({

//>public boolean selectTextbox(String psId)
selectTextbox:function(psId){
vjoPro.dsf.document.Element.get(psId).select();
return true;
}

})
.endType();
