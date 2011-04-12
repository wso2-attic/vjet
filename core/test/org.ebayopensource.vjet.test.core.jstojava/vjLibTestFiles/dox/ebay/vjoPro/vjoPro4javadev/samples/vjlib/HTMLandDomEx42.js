vjo.ctype("dox.ebay.vjoPro.vjoPro4javadev.samples.vjlib.HTMLandDomEx42")
.needs("vjoPro.dsf.Element")
.needs("vjoPro.dsf.document.Shim")
.protos({

psId:"",//<String

//> public void constructs(String psId)
constructs:function(psId) {
this.sId = psId;
},

//> public boolean showLayer()
showLayer:function(){
var E = this.vj$.Element;//<Type::Element
E.toggleVisibility(this.sId);
var oDiv = E.get(this.sId);
if (oDiv.bIsVisible) {
this.iframeShim = this.vj$.Shim.add(oDiv);
} else {
if (this.iframeShim) {
 this.vj$.Shim.remove(oDiv, this.iframeShim);
}
}
}

}).endType();
