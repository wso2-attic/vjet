vjo.ctype("dox.ebay.vjoPro.vjoPro4jsdev.samples.vjlib.HTMLandDomEx41")
.needs('vjoPro.dsf.Element')
.protos({

//> public void constructs(String psId)
constructs:function(psId) {
this.sId = psId;
},

//> public boolean showLayer()
showLayer:function(){
var E = vjoPro.dsf.Element;
E.toggleVisibility(this.sId);
}

}).endType();
