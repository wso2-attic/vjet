vjo.ctype("dox.ebay.vjoPro.vjoPro4jsdev.samples.vjlib.HTMLandDomEx6")
.needs('vjoPro.dsf.Element')
.needs('vjoPro.dsf.utils.URL')
.props({

//> public void readArgument(String psUrlId, String psArgNameId)
readArgument : function(psUrlId, psArgNameId) {
var sUrl = vjoPro.dsf.Element.get(psUrlId).value;
var sArgName = vjoPro.dsf.Element.get(psArgNameId).value;
alert(vjoPro.dsf.utils.URL.getArg(sUrl, sArgName));
},

//> public void addArgument(String psUrlId, String psArgNameId, String psArgValId)
addArgument : function(psUrlId, psArgNameId, psArgValId) {
var E = vjoPro.dsf.Element.get(psUrlId);
var sArgName = vjoPro.dsf.Element.get(psArgNameId).value;
var sArgVal = vjoPro.dsf.Element.get(psArgValId).value;
E.value = vjoPro.dsf.utils.URL.addArg(E.value, sArgName, sArgVal);
}

}).endType();
