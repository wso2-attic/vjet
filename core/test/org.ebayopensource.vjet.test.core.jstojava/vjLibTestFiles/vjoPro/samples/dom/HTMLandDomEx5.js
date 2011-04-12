vjo.ctype('vjoPro.samples.dom.HTMLandDomEx5') //< public
.needs('vjoPro.dsf.document.Element')
.protos({
/**
* @return void
* @access public
* @param {String} psShowDivId
* @param {String} psHideDivId
* @param {String} psMessageDivId
*
*/
//> public constructs(String psShowDivId,String psHideDivId,String psMessageDivId)
constructs : function(psShowDivId, psHideDivId, psMessageDivId) {
this.sShowDivId = psShowDivId;
this.sHideDivId = psHideDivId;
this.sMessageDivId = psMessageDivId;
},

/**
* @return void
* @access public
*
*/
//> public void toggle()
toggle:function(){
var E = vjoPro.dsf.document.Element;
E.toggleHideShow(this.sShowDivId);
E.toggleHideShow(this.sHideDivId);
E.toggleHideShow(this.sMessageDivId);
},

/**
* @access public
* @param {boolean} pbToggle
*
*/
//> public void toggleWithParam(boolean pbToggle)
toggleWithParam:function(pbToggle){
var E = vjoPro.dsf.document.Element;
E.toggleHideShow(this.sShowDivId, !pbToggle);
E.toggleHideShow(this.sHideDivId, pbToggle);
E.toggleHideShow(this.sMessageDivId, pbToggle);
}

})
.endType();
