vjo.ctype("dox.ebay.vjoPro.vjoPro4jsdev.samples.vjlib.FormSelectText")
.needs('vjoPro.dsf.Element')
.props({
//> public boolean selectTextbox(String psId)
selectTextbox:function(psId){
vjoPro.dsf.Element.get(psId).select();
return true;
}

}).endType();
