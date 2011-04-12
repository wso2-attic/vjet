vjo.ctype("dox.ebay.vjoPro.vjoPro4jsdev.samples.vjlib.HTMLandDomEx5")
.needs('vjoPro.dsf.Element')
.protos({

//> public void constructs(String psShowDivId, String psHideDivId, String psMessageDivId)
constructs : function(psShowDivId, psHideDivId, psMessageDivId) {
this.sShowDivId = psShowDivId;
this.sHideDivId = psHideDivId;
this.sMessageDivId = psMessageDivId;
},

//> public void toggle()
toggle:function(){
var E = vjoPro.dsf.Element;
E.toggleHideShow(this.sShowDivId);
E.toggleHideShow(this.sHideDivId);
E.toggleHideShow(this.sMessageDivId);
},

//> public void toggleWithParam(boolean pbToggle)
toggleWithParam:function(pbToggle){
var E = vjoPro.dsf.Element;
E.toggleHideShow(this.sShowDivId, !pbToggle);
E.toggleHideShow(this.sHideDivId, pbToggle);
E.toggleHideShow(this.sMessageDivId, pbToggle);
}

}).endType();
