vjo.ctype('vjoPro.samples.dom.HTMLandDomEx41') //< public
.needs('vjoPro.dsf.document.Element')
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
}

})
.endType();
