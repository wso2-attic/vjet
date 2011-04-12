vjo.ctype('vjoPro.samples.dom.HTMLandDomEx42') //< public
.needs(['vjoPro.dsf.document.Element','vjoPro.dsf.document.Shim'])
.protos({

/**
* @return void
* @access public
* @param {String} psId
*
*/
//> public constructs(String psId)
constructs:function(psId) {
this.sId = psId;
},

/**
* @return boolean
* @access public
*
*/
//> public boolean showLayer()
showLayer:function(){
var E = vjoPro.dsf.document.Element;
E.toggleVisibility(this.sId);
var oDiv = E.get(this.sId);
if (oDiv.bIsVisible) {
this.iframeShim = vjoPro.dsf.document.Shim.add(oDiv);
} else {
if (this.iframeShim) {
vjoPro.dsf.document.Shim.remove(oDiv, this.iframeShim);
}
}
}

})
.endType();
