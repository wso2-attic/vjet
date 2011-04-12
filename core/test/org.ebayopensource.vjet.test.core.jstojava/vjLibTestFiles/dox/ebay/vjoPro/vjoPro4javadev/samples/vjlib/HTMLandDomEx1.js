vjo.ctype("dox.ebay.vjoPro.vjoPro4javadev.samples.vjlib.HTMLandDomEx1")
.needs("vjoPro.dsf.Element")
.props({

//> public boolean getElem(String psId)
getElem:function(psId){
var elem = vjoPro.dsf.Element.get(psId);
alert(elem);
}

}).endType();
