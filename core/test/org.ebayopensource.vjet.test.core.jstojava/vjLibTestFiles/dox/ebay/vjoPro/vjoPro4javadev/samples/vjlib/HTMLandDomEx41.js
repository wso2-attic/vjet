vjo.ctype("dox.ebay.vjoPro.vjoPro4javadev.samples.vjlib.HTMLandDomEx41")
.needs("vjoPro.dsf.Element")
.protos({

sId:"",//<String

//> public void constructs(String psId)
constructs:function(psId) {
this.sId = psId;
},

//> public boolean showLayer()
showLayer:function(){
var E = this.vj$.Element;//<Type::Element
E.toggleVisibility(this.sId);
}

}).endType();
